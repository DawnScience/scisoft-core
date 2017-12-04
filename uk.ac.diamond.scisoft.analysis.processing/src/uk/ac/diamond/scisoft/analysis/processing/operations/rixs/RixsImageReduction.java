/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentNodeFactory;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterData1D;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterNoisyData1D;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.image.ImageUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.CORRELATE_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class RixsImageReduction extends RixsBaseOperation<RixsImageReductionModel> {

	private double[] energyDispersion = new double[2];
	private Dataset totalSum = null; // dataset of all event sums (so far)
	private List<Dataset> allSums = new ArrayList<>(); // list of dataset of event sums in each image
	private List<Dataset> allPositions = new ArrayList<>(); // list of dataset of event coords in each image
	private List<Dataset>[] allSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public String getFilenameSuffix() {
		return "rixs_spectra";
	}

	@Override
	void updateFromModel() {
		Arrays.fill(energyDispersion, Double.NaN);
		energyDispersion[0] = model.getEnergyDispersion();
		if (Double.isNaN(energyDispersion[0])) {
			if (model.getCalibrationFile() == null) {
				throw new OperationException(this, "Either energy dispersion or calibration file must be defined");
			}
			// energy dispersion in terms of eV/pixel
			double[] tmp = parseCalibrationFile(model.getCalibrationFile());
			System.arraycopy(tmp, 0, energyDispersion, 0, Math.min(2, tmp.length));
		}

		initializeFitLine(model.getFitFile());

		super.updateFromModel();
	}

	@Override
	protected void resetProcess(IDataset original) {
		totalSum = null;
		allSums.clear();
		allPositions.clear();
		allSpectra[0].clear();
		allSpectra[1].clear();
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("RIXS Image Reduction");
		log.append("====================");
	}

	private void initializeFitLine(String fitFile) {
		try {
			Tree tree = LoaderFactory.getData(fitFile).getTree();
			GroupNode root = tree.getGroupNode();
			GroupNode entry = (GroupNode) NexusTreeUtils.findFirstNode(root, "NXentry").getDestination();

			GroupNode pg = ProcessingUtils.checkForProcess(this, entry, ElasticLineFit.PROC_NAME);

			if (model.isRegionsFromFitFile()) {
				IPersistenceService service = LocalServiceManager.getPersistenceService();
				IPersistentNodeFactory pf = service.getPersistentNodeFactory();
				IOperation[] ops = pf.readOperationsFromTree(tree);
				for (IOperation o : ops) {
					IOperationModel m = o.getModel();
					if (m instanceof RixsBaseModel) {
						RixsBaseModel rbm = (RixsBaseModel) m;
						model.setRoiA(rbm.getRoiA());
						model.setRoiB(rbm.getRoiB());
						break;
					}
				}
			}

			// find /entry/auxiliary/*-RIXS elastic line fit/line?_[cm]
			GroupNode g = (GroupNode) entry.getGroupNode("auxiliary");
			for (NodeLink n : g) {
				if (n.getName().endsWith(ElasticLineFit.PROC_NAME) && n.isDestinationGroup()) {
					GroupNode fg = (GroupNode) n.getDestination();
					int r = fg.getNumberOfGroupNodes() / 3; // three datasets per line
					double[] p = new double[2];
					for (int i = 0; i < r; i++) {
						String l = "line_" + i;
						p[0] = NexusTreeUtils.parseDoubleArray(fg.getGroupNode(l + "_m").getDataNode("data"))[0];
						p[1] = NexusTreeUtils.parseDoubleArray(fg.getGroupNode(l + "_c").getDataNode("data"))[0];
						StraightLine line = lines[i] = new StraightLine(p);
					}
				}
			}

			// TODO may be set slope limits from process in pg

		} catch (Exception e) {
			throw new OperationException(this, "Cannot load file with elastic line fit", e);
		}
	}

//	@Override
//	protected void parseNXrixs(GroupNode detector, double pEnergy) {
//		// TODO read energy_dispersion
//		super.parseNXrixs(detector, pEnergy);
//		if (Double.isNaN(energyDispersion)) {
//			// energy dispersion in terms of eV/pixel
//			energyDispersion = NexusTreeUtils.parseDoubleArray(detector.getDataNode("energy_dispersion"))[0];
//		}
//	}
//

	private double[] parseCalibrationFile(String calibrationFile) {
		try {
			Tree t = LoaderFactory.getData(calibrationFile).getTree();

			GroupNode root = t.getGroupNode();
			// entry1:NXentry
			//     before_scan:NXcollection
			//         andorPreampGain:NXcollection/andorPreampGain [1, 2, 4]
			//         pgmEnergy:NXcollection/ [energy in eV, always single value, even for an energy scan]

			GroupNode entry = (GroupNode) NexusTreeUtils.findFirstNode(root, "NXentry").getDestination();

			ProcessingUtils.checkForProcess(this, entry, ElasticLineEnergyCalibration.PROCESS_NAME);

			GroupNode rg = entry.getGroupNode("result");
			if (rg == null) {
				throw new NexusException("File does not contain a result group");
			}

			DataNode d = rg.getDataNode("data");
			if (d == null) {
				throw new NexusException("File does not contain a result dataset");
			}

			return NexusTreeUtils.parseDoubleArray(d);
		} catch (Exception e) {
			log.append("Could not parse Nexus file %s:%s", calibrationFile, e);
		}

		return new double[] {-1, -1};
	}


	@Override
	IDataset processImageRegion(int r, IDataset original, Dataset in) {
		Dataset[] result = makeSpectrum(r, in, 0.25);
		Dataset spectrum = result[1];
		spectrum.setName("spectrum_" + r);

		// work out energy scale (needs calibration)
		Dataset e = DatasetFactory.createRange(spectrum.getSize());
		e.iadd(offset[1]-result[0].getDouble()); // TODO discretize???
		e.imultiply(-energyDispersion[r]);
		e.setName("Energy loss");
		ProcessingUtils.setAxes(spectrum, e);

		auxData.add(spectrum);

		allSpectra[r].add(spectrum);
		return spectrum;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		Dataset original = DatasetUtils.convertToDataset(input);
		OperationData od = super.process(original, monitor);

		// find photon events in entire image
		List<Dataset> events = ImageUtils.findWindowedPeaks(original, model.getWindow(), countsPerPhoton * model.getLowThreshold(), countsPerPhoton * model.getHighThreshold());
		Dataset eSum = events.get(0);
		// accumulate event sums and photons
		if (totalSum == null) {
			totalSum = eSum;
		} else {
			totalSum = DatasetUtils.concatenate(new IDataset[] {totalSum, eSum}, 0);
		}
		log.append("Found %d photon events, current total = %s", eSum.getSize(), totalSum.getSize());
		allSums.add(eSum);
		allPositions.add(events.get(1));

		double max = totalSum.max(true).doubleValue();
		IntegerDataset bins = DatasetFactory.createRange(IntegerDataset.class, totalSum.min(true).doubleValue(), max+1, 1);
		bins.setName("Event sum");
		Histogram histo = new Histogram(bins);
		Dataset h = histo.value(totalSum).get(0);
		h.setName("Number of events");

		Dataset a = Maths.log10(h);
		a.setName("Log10 of " + h.getName());
		ProcessingUtils.setAxes(a, bins);
		displayData.add(a);

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();
		if (si != null) {
			int smax = si.getTotalSlices();
			log.append("At frame %d/%d", si.getSliceNumber(), smax);
			System.err.printf("At frame %d/%d\n", si.getSliceNumber(), smax);
			summaryData.clear(); // do not save anything yet

			if (si.getSliceNumber() == smax) {
				summaryData.add(a);

				// After last image, calculate splitting levels
				Dataset x = bins.getSlice(new Slice(-1));
				Dataset dh = Maths.derivative(x, h, 3);
				ProcessingUtils.setAxes(dh, bins);
				displayData.set(0, dh);
				List<Double> z = DatasetUtils.crossings(x, dh, 0);
				log.append("Histogram derivative zero-crossings = %s", z);
				int single = (int) Math.floor(z.get(0));
				int multiple = single + countsPerPhoton; // (int) Math.floor(z.get(1)); // TODO
				log.append("Setting limits for single photon as [%d, %d)", single, multiple);
				
				int bin = model.getBins();
				int bmax = bin * original.getShapeRef()[model.getEnergyIndex()];

				// per image, separate by sum
				int[][][] allSingle = new int[roiMax][smax][];
				int[][][] allMultiple = new int[roiMax][smax][];
				List<Double> cX = new ArrayList<>();
				List<Double> cY = new ArrayList<>();
				for (int i = 0; i < smax; i++) {
					for (int r = 0; r < roiMax; r++) {
						cX.clear();
						cY.clear();

						StraightLine line = getStraightLine(r);
						IRectangularROI roi = getROI(r);

						Dataset sums = allSums.get(i);
						Dataset posn = allPositions.get(i);
						int[] hSingle = new int[bmax];
						int[] hMultiple = new int[bmax];
						allSingle[r][i] = hSingle;
						allMultiple[r][i] = hMultiple;
						double el0 = line.val(0); // elastic line intercept
						for (int j = 0, jmax = sums.getSize(); j < jmax; j++) {
							double px = posn.getDouble(j, 1);
							double py = posn.getDouble(j, 0);

							if (roi != null && !roi.containsPoint(px, py)) {
								continue; // separate by regions
							}

							// add coords
							if (i == 0) {
								cX.add(px);
								cY.add(py);
							}

							// correct for tilt
							py += line.val(px) - el0;

							double ps = sums.getDouble(j);
							if (ps >= single) {
								int ip = (int) Math.floor(bin * py); // discretize position
								if (ip >= 0 && ip < bmax) {
									if (ps < multiple) {
										hSingle[ip]++;
									} else {
										hMultiple[ip]++;
									}
								}
							}
						}

						// add coords
						if (i == 0) {
							int side = cX.size();
							if (side > 0) {
								Dataset t;
								t = DatasetFactory.createFromList(cY);
								ProcessingUtils.setAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
								// for DExplore's 2d scatter point
//								t = DatasetFactory.zeros(side, side);
//								ProcessingUtils.addAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"),
//										ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cY), "y"));
								t.setName("photon_positions_" + r);
								summaryData.add(t);
							}
						}
					}
				}

				Dataset[] energies = new Dataset[2];
				Dataset[] sSpectra = new Dataset[2];
				Dataset[] mSpectra = new Dataset[2];
				Dataset[] sEvents = new Dataset[2];
				Dataset[] mEvents = new Dataset[2];

				for (int r = 0; r < roiMax; r++) {
					double el0 = getStraightLine(r).val(0); // elastic line intercept
					Dataset er = DatasetFactory.createRange(bmax);
					er.iadd(-bin*el0); // adjust zero TODO sign wrong??
					er.imultiply(-energyDispersion[r]/bin);
					er.setName("Energy loss");
					energies[r] = er;
					Dataset t = DatasetFactory.createFromObject(allSingle[r]);
					t.setName("single_photon_spectrum_" + r);
					ProcessingUtils.setAxes(t, null, er);
					sSpectra[r] = t;
					summaryData.add(t);

					Dataset nf = t.sum(1); // to work out per-image as single fraction of total events
					nf.setName("single_photon_count_" + r);
					sEvents[r] = nf;
					summaryData.add(nf);

					t = DatasetFactory.createFromObject(allMultiple[r]);
					t.setName("multiple_photon_spectrum_" + r);
					ProcessingUtils.setAxes(t, null, er);
					mSpectra[r] = t;
					summaryData.add(t);

					t = t.sum(1);
					t.setName("multiple_photon_count_" + r);
					mEvents[r] = t;
					summaryData.add(t);

					t = Maths.add(t, nf);
					nf = Maths.divide(nf.cast(DoubleDataset.class), t);
					nf.setName("single_events_fraction_" + r);
					summaryData.add(nf);
				}

				// total and correlated spectra
				for (int r = 0; r < roiMax; r++) {
					Dataset sp = null;
					IDataset[] sArray = toArray(allSpectra[r]);
					sp = accumulate(sArray);
					sp.setName("total_spectrum_" + r);
					summaryData.add(sp);

					double ts = (Double) ((Number) sEvents[r].sum()).doubleValue();
					double tm = (Double) ((Number) mEvents[r].sum()).doubleValue();
					log.append("Events: single/total = %g/%g = %g ", ts, tm, ts/(ts + tm));
					summaryData.add(ProcessingUtils.createNamedDataset(ts/(ts + tm), "total_single_events_fraction_" + r));

					Dataset ax = null;
					try {
						ax = DatasetUtils.sliceAndConvertLazyDataset(sp.getFirstMetadata(AxesMetadata.class).getAxis(0)[0]);
					} catch (DatasetException e) {
					}

					DatasetToDatasetFunction reg = getCorrelateShifter(false);
					List<? extends IDataset> results = reg.value(sArray);
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = results.get(2*i + 1);
					}
					sp = accumulate(sArray);
					sp.setName("correlated_spectrum_" + r);
					ProcessingUtils.setAxes(sp, ax);
					summaryData.add(sp);

					List<Double> shift = new ArrayList<>();
					for (int i = 0; i < sArray.length; i++) {
						shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
					}
					summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_spectrum_shift_" + r));

					Dataset e = energies[r];
					sp = sSpectra[r].sum(0);
					sp.setName("total_single_photon_spectrum_" + r);
					ProcessingUtils.setAxes(sp, e);
					summaryData.add(sp);

					sp = mSpectra[r].sum(0);
					sp.setName("total_multiple_photon_spectrum_" + r);
					ProcessingUtils.setAxes(sp, e);
					summaryData.add(sp);

					reg = getCorrelateShifter(true);
					sArray = new IDataset[sSpectra[r].getShapeRef()[0]];
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = sSpectra[r].getSliceView(new Slice(i, i+1)).squeeze();
					}
					results = reg.value(sArray);
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = results.get(2*i + 1);
					}
					sp = accumulate(sArray);
					sp.setName("correlated_single_photon_spectrum_" + r);
					ProcessingUtils.setAxes(sp, e);
					summaryData.add(sp);
					shift.clear();
					for (int i = 0; i < sArray.length; i++) {
						shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
					}
					summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_single_photon_spectrum_shift_" + r));

					sArray = new IDataset[mSpectra[r].getShapeRef()[0]];
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = mSpectra[r].getSliceView(new Slice(i, i+1)).squeeze();
					}
					results = reg.value(sArray);
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = results.get(2*i + 1);
					}
					sp = accumulate(sArray);
					sp.setName("correlated_multiple_photon_spectrum_" + r);
					ProcessingUtils.setAxes(sp, e);
					summaryData.add(sp);
					shift.clear();
					for (int i = 0; i < sArray.length; i++) {
						shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
					}
					summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_multiple_photon_spectrum_shift_" + r));
				}
			}
		}

		OperationDataForDisplay odd;
		if (od instanceof OperationDataForDisplay) {
			odd = (OperationDataForDisplay) od;
		} else {
			odd = new OperationDataForDisplay(od.getData());
			odd.setShowSeparately(true);
		}

		odd.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		odd.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		odd.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));
		return odd;
	}

	private IDataset[] toArray(List<Dataset> d) {
		int imax = d.size();
		IDataset[] a = new IDataset[imax];
		for (int i = 0; i < imax; i++) {
			a[i] = d.get(i).getView(true).squeeze();
		}
		return a;
	}

	private Dataset accumulate(IDataset... d) {
		Dataset sp = null;
		for (IDataset s : d) {
			if (s == null) {
				continue;
			}
			if (sp == null) {
				sp = DatasetUtils.convertToDataset(s).clone();
			} else {
				sp.iadd(s);
			}
		}
		return sp;
	}

	private DatasetToDatasetFunction getCorrelateShifter(boolean noisy) {
		boolean all = model.getCorrelateOption() == CORRELATE_OPTION.ALL_PAIRS;
		if (noisy) {
			RegisterNoisyData1D reg = new RegisterNoisyData1D();
			reg.setFilter(DatasetFactory.ones(5).imultiply(1./5));
			reg.setPeakCentroidThresholdFraction(0.85);
			reg.setFitAll(all);
			return reg;
		}

		RegisterData1D reg = new RegisterData1D();
		reg.setFilter(DatasetFactory.ones(5).imultiply(1./5));
		reg.setWindowFunction(0.25);
		return reg;
	}
}
