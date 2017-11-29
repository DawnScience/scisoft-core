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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ElasticLineEnergyCalibration extends ElasticLineFit {

	private boolean useSpectrum = true;
	private boolean isImageGood = false;
	private List<Dataset>[] goodSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS energy dispersion calibration";

	@Override
	public String getFilenameSuffix() {
		return "elastic_line_calib";
	}

	@Override
	void initializeProcess(IDataset original) {
		super.initializeProcess(original);
		// get position
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (smd.getSliceInfo().getSliceNumber() == 1) {
			goodSpectra[0].clear();
			goodSpectra[1].clear();
		}
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		OperationData od = super.process(input, monitor);
		od.setLog(log);
		log.clear();
		log.append("Energy calibration");
		log.append("==================");

		// hold state in this object's super class then
		// then process when a running count matches 
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();

		// aggregate aux data???

		if (si != null) {
			OperationDataForDisplay odd;
			if (od instanceof OperationDataForDisplay) {
				odd = (OperationDataForDisplay) od;
			} else {
				odd = new OperationDataForDisplay();
				odd.setShowSeparately(true);
			}
			int smax = si.getTotalSlices();
			log.append("At frame %d/%d", si.getSliceNumber(), smax);

			// TODO make this live-friendly by show result per frame
			// needs to give fake results for first slice
			if (si.getSliceNumber() == smax) {
				summaryData.clear();
				displayData.clear();
//				odd.setAuxData();
				boolean useBothROIs = model.getRoiA() != null && model.getRoiB() != null;
				int rmax = useBothROIs ? 2 : 1;
				double[] dispersion = new double[rmax];
				for (int r = 0; r < rmax; r++) {
					if (goodPosition[r].size() <= 2) {
						log.append("Not enough good lines (%d) found for ROI %d", goodPosition[r].size(), r);
						continue;
					}

					List<?>[] coords;
					if (useSpectrum) {
						coords = fitSpectraPeakPositions(r);
					} else {
						coords = new List<?>[] {goodPosition[r], goodIntercept[r]};
					}

					double[] res = fitIntercepts(r, coords);
					dispersion[r] = 1./res[1];
					log.append("Dispersion is %g for residual %g", dispersion[r], res[0]);
				}

				Dataset out = ProcessingUtils.createNamedDataset(dispersion, "energy_dispersion").reshape(1, rmax);
				if (displayData.size() > 0) {
					IDataset[] fit = displayData.toArray(new IDataset[displayData.size()]);
					odd.setDisplayData(fit);
					
					for (int i = 0; i < fit.length; i++) {
						summaryData.add(fit[i]);
					}
				}
				odd.setData(out);
				odd.setLog(log);
				odd.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
				odd.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));

				copyMetadata(input, out);
				out.clearMetadata(AxesMetadata.class);
				out.clearMetadata(SliceFromSeriesMetadata.class);
				SliceFromSeriesMetadata outssm = ssm.clone();
				for (int i = 0, imax = ssm.getParent().getRank(); i < imax; i++) {
					if (!outssm.isDataDimension(i)) {
						outssm.reducedDimensionToSingular(i);
					}
				}

				out.setMetadata(outssm);
				return odd;
			}
		}

//		od.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		od.setData(null);
		od.setLog(log);
		od.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		od.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));
		return od;
	}

	@Override
	IDataset processImageRegion(int r, IDataset original, Dataset in) {
		IDataset dataset = super.processImageRegion(r, original, in);

		if (useSpectrum) { // use spectrum fitted with Gaussian for calibration fit
			Dataset[] result = makeSpectrum(r, in, model.getMaxSlope());
			Dataset spectrum = result[1];
			spectrum.setName("elastic_spectrum_" + r);
//			auxData.add(spectrum); // put in summary data instead

			if (isImageGood) {
				goodSpectra[r].add(spectrum);
			}
		}
		return dataset;
	}

	@Override
	protected void addAuxData(int i, double m, double c, double r) {
		super.addAuxData(i, m, c, r);

		if (useSpectrum) {
			isImageGood = Double.isFinite(c);
		}
	}

	/**
	 * Fit to intercepts of intercepts of 
	 * @param r
	 * @param coords
	 * @return residual and gradient
	 */
	private double[] fitIntercepts(int r, List<?>[] coords) {
		Dataset energy = DatasetFactory.createFromList(coords[0]);
		Dataset intercept = DatasetFactory.createFromList(coords[1]);
		energy.setName("Energy");
		intercept.setName("intercept_" + r);
		double smax = 2*Math.abs(intercept.peakToPeak().doubleValue()) / Math.abs(energy.peakToPeak().doubleValue());
		StraightLine iLine = new StraightLine(-smax, smax, -Double.MAX_VALUE, Double.MAX_VALUE);

		double res = fitFunction(iLine, energy, intercept);
		generateFitForDisplay(iLine, energy, intercept, "intercept_fit_" + r);
		return new double[] {res, iLine.getParameterValue(0)};
	}

	private List<?>[] fitSpectraPeakPositions(int r) {
//		Gaussian peak = new Gaussian();
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

//		log.append("Sum %s/%s", v.sum(), v.toString(true));
//		log.append("Initial peak:\n%s", peak);

		Dataset spectrum = goodSpectra[r].get(0);
		initializeFunctionParameters(peak, spectrum);
		int size = spectrum.getSize();
		DoubleDataset x = DatasetFactory.createRange(size);
		x.setName("Energy Index");

		List<Double> gEnergy = new ArrayList<>();
		List<Double> gPosn = new ArrayList<>();
		List<Double> energy = goodPosition[r];

		int ns = goodSpectra[r].size();
		Dataset gSpectrum = null;
		Dataset gSpectrumFit = null;
		double[] ip = null;
		for (int i = 0; i < ns; i++) {
			spectrum = goodSpectra[r].get(i);
			log.append("Fitting elastic peak: %d/%d", i, ns);
			if (ip != null) {
				ip[0] = spectrum.argMax(true); // shift fit
				peak.setParameterValues(ip);
			}
			double res = Double.POSITIVE_INFINITY;
			try {
				res = fitFunction(peak, x, spectrum);
				if (Double.isFinite(res)) {
					gEnergy.add(energy.get(i));
					gPosn.add(peak.getParameterValue(0));
					if (ip == null) {
						ip = peak.getParameterValues();
					}
					DoubleDataset fit = peak.calculateValues(x);
					if (gSpectrum == null) {
						gSpectrum = spectrum.clone().reshape(1, size);
						gSpectrumFit = fit.reshape(1, size);
					} else {
						gSpectrum = DatasetUtils.concatenate(new IDataset[] {gSpectrum, spectrum.reshape(1, size)}, 0);
						gSpectrumFit = DatasetUtils.concatenate(new IDataset[] {gSpectrumFit, fit.reshape(1, size)}, 0);
					}
				}
			} catch (Exception e) {
			}
			if (!Double.isFinite(res)) {
				log.append("Fitting elastic peak: FAILED");
			}
		}

		Dataset ge = DatasetFactory.createFromList(gEnergy);
		ge.setName("Scan energy");
		gSpectrum.setName("elastic_spectrum_" + r);
		ProcessingUtils.setAxes(gSpectrum, ge);
		summaryData.add(gSpectrum);
		gSpectrumFit.setName("elastic_spectrum_fit_" + r);
		ProcessingUtils.setAxes(gSpectrumFit, ge);
		summaryData.add(gSpectrumFit);
		return new List<?>[] {gEnergy, gPosn};
	}
}
