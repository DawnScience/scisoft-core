/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Matcher;

import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.FIT_FILE_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class RixsImageReduction extends RixsImageReductionBase<RixsImageReductionModel> {

	private static final String PROCESSING = "processing";

	private String currentFitFile;
	private boolean useSummaryFits = false;
	private Dataset[] summaryStore;

	@Override
	void updateFromModel(boolean throwEx, String name) {
		super.updateFromModel(throwEx, name);

		// done regardless of fit file option but gets overwrite when not manual override
		useSummaryFits = model.isPerFrameFits();
		summaryStore = null;

		boolean isCalibration = false;
		String file = model.getFitFile();
		if (file == null) {
			file = model.getCalibrationFile();
			if (file != null) {
				isCalibration = true;
				if (!useSummaryFits) {
					try {
						initializeFitLine(file, true, 0, 1);
					} catch (OperationException e) {
						log.appendFailure("Cannot initialize fit line from '%s': %s", file, e);
					}
				}
			}
		}

		if (RixsImageReductionModel.SET_FITFILE_OPTION.equals(name) && model.getFitFileOption() == FIT_FILE_OPTION.MANUAL_OVERRIDE) {
			model.internalSetRegionsFromFile(false);
		}

		if (isCalibration && model.isRegionsFromFile()) {
			initializeROIsFromFile(file);
		}

		updateROICount();
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("RIXS Image Reduction");
		log.append("====================");

		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
		SliceInformation si = smd.getSliceInfo();
		if (si.isFirstSlice()) {
			String filePath = smd.getSourceInfo().getFilePath();
			if (model.getFitFileOption() == FIT_FILE_OPTION.MANUAL_OVERRIDE) {
				initializeFitLine(model.getFitFile(), !useSummaryFits, 0, 0);

				if (model.isRegionsFromFile()) {
					initializeROIsFromFile(model.getFitFile());
					updateROICount();
				}
			} else if (!filePath.equals(currentDataFile)) {
				currentDataFile = filePath;

				File file = new File(filePath);
				File currentDir = file.getParentFile();
				String currentName = file.getName();

				Matcher m = NUMBERED_FILE_REGEX.matcher(currentName);
				if (!m.matches()) {
					throw new OperationException(this, "Current file path does not end with scan number (before file extension)");
				}
				String digits = m.group(1);
				int scan = Integer.parseInt(digits);
				if (model.getFitFileOption() == FIT_FILE_OPTION.NEXT_SCAN) {
					scan++;
				} else if (model.getFitFileOption() == FIT_FILE_OPTION.PREVIOUS_SCAN) {
					scan--;
				}

				// ensure zero padding of scan number is correct
				String format = String.format("%s%%0%dd_processed_%s", currentName.substring(0, m.start(1)), digits.length(), ElasticLineReduction.SUFFIX);
				String prefix = String.format(format, scan);
				currentFitFile = getElasticFitFromFile(currentDir, prefix);
				if (!useSummaryFits) {
					initializeFitLine(currentFitFile, true, 0, 1);
				}

				if (model.isRegionsFromFile()) {
					initializeROIsFromFile(currentFitFile);
					updateROICount();
				}
			}
		}

		if (useSummaryFits && currentFitFile != null) {
			int i = si.getSliceNumber();
			initializeFitLine(currentFitFile, false, i, si.getTotalSlices());
		}
	}

	private String getElasticFitFromFile(final File currentDir, final String prefix) {
		log.append("Looking for processed elastic line fit of scan with prefix %s", prefix);

		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(prefix);
			}
		};

		String fitFile = null;
		if (model.getCalibrationFile() != null) { // try in calibration file's directory
			File calibDir = new File(model.getCalibrationFile()).getParentFile();
			fitFile = findLatestFitFile(filter, calibDir);
		}

		if (fitFile == null) {
			fitFile = findLatestFitFile(filter, currentDir);
			if (fitFile == null) {
				fitFile = findLatestFitFile(filter, new File(currentDir, PROCESSING));
				if (fitFile == null) {
					throw new OperationException(this, "Could not find fit file in data, calibration or processing directories");
				}
			}
		}

		log.append("Using fit file: %s", fitFile);
		return fitFile;
	}

	// find any processed fit file with scan number
	private String findLatestFitFile(FilenameFilter filter, File cwd) {
		File[] files = cwd.listFiles(filter);
		if (files == null) {
			return null;
		}
		long latest = 0;
		File last = null;
		for (File f : files) {
			if (last == null) {
				last = f;
				latest = f.lastModified();
			} else if (latest < f.lastModified()) {
				last = f;
				latest = f.lastModified();
			}
		}
		return last == null ? null : last.toString();
	}

	private void initializeFitLine(String elasticLineFile, boolean useAverage, int slice, int size) {
		if (elasticLineFile == null) {
			Double override = model.getSlopeOverride();
			if (override == null) {
				override = 0.;
			}
			lines[0] = new StraightLine();
			lines[0].setParameterValues(override);
			lines[1] = new StraightLine();
			lines[1].setParameterValues(override);
			return;
		}

		if (summaryStore != null) {
			readFitFromStore(slice, summaryStore);
			return;
		}
		try {
			Tree tree = LoaderFactory.getData(elasticLineFile).getTree();
			GroupNode root = tree.getGroupNode();
			GroupNode entry = NexusTreeUtils.findFirstEntryWithProcess(root);

			ProcessingUtils.checkForProcess(this, entry, ElasticLineReduction.PROCESS_NAME);

			// find /processed/[groupName]/*-RIXS elastic line reduction/line?_[cm]
			Dataset[] store = new Dataset[4];
			GroupNode g = (GroupNode) entry.getGroupNode(useAverage ? NexusFileExecutionVisitor.AUX_GROUP :
				NexusFileExecutionVisitor.SUM_GROUP);
			int r = extractFitParameters(store, g, 0);
			if (r == 0 && !useAverage) { // fallback in case summary data is missing when only one frame
				g = (GroupNode) entry.getGroupNode(NexusFileExecutionVisitor.AUX_GROUP);
				r = extractFitParameters(store, g, 0);
			}
			readFitFromStore(slice, store);
			if (!useAverage) {
				if (r > 0) {
					if (store[0].getSize() == size) {
						summaryStore = store;
					} else {
						useSummaryFits = false;
					}
				} else {
					useSummaryFits = false;
				}
			}

			// TODO may be set slope limits from process in process group by reading model
		} catch (Exception e) {
			throw new OperationException(this, "Cannot load file with elastic line fit", e);
		}
	}

	private int extractFitParameters(Dataset[] store, GroupNode g, int r) {
		for (NodeLink n : g) {
			if (n.getName().endsWith(ElasticLineReduction.PROCESS_NAME) && n.isDestinationGroup()) {
				GroupNode fg = (GroupNode) n.getDestination();
				for (r = 0; r < 2; r++) {
					Dataset d = getDataset(ElasticLineReduction.LINE_GRADIENT_FORMAT, fg, r);
					if (d == null) {
						break;
					}
					store[2*r]     = d;
					store[2*r + 1] = getDataset(ElasticLineReduction.LINE_INTERCEPT_FORMAT, fg, r);
				}
				break;
			}
		}
		return r;
	}

	private void readFitFromStore(int slice, Dataset[] store) {
		double[] p = new double[2];
		int r = 0;
		for (; r < 2; r++) {
			if (store[2*r] == null) {
				Arrays.fill(p, Double.NaN);
			} else {
				p[0] = store[2*r].getDouble(slice);
				p[1] = store[2*r + 1].getDouble(slice);
			}
			lines[r] = new StraightLine(p);
		}
		if (r == 1 && Double.isNaN(lines[0].getParameterValue(STRAIGHT_LINE_M))) {
			throw new OperationException(this, "Loaded elastic line fit is invalid");
		}
		if (r > 1 && Double.isNaN(lines[1].getParameterValue(STRAIGHT_LINE_M))) {
			lines[1].setParameterValues(lines[0].getParameterValues());
		}
	}

	private Dataset getDataset(String fmt, GroupNode pg, int r) {
		String name = String.format(fmt, r);
		GroupNode g = pg.getGroupNode(name);
		if (g == null) {
			return null;
		}
		try {
			return DatasetUtils.sliceAndConvertLazyDataset(g.getDataNode("data").getDataset());
		} catch (DatasetException e) {
			log.appendFailure("Failed to read %s: %s", name, e);
			return null;
		}
	}

	protected void addSummaryData() {
		super.addSummaryData();

		if (useSummaryFits || summaryStore == null) {
			for (int r = 0; r < 2; r++) {
				StraightLine l = getStraightLine(r);
				summaryData.add(ProcessingUtils.createNamedDataset(l.getParameterValue(STRAIGHT_LINE_M), ElasticLineReduction.LINE_GRADIENT_FORMAT, r));
				summaryData.add(ProcessingUtils.createNamedDataset(l.getParameterValue(STRAIGHT_LINE_C), ElasticLineReduction.LINE_INTERCEPT_FORMAT, r));
			}
		} else {
			for (int r = 0; r < 2; r++) {
				summaryData.add(ProcessingUtils.createNamedDataset(summaryStore[2*r], ElasticLineReduction.LINE_GRADIENT_FORMAT, r));
				summaryData.add(ProcessingUtils.createNamedDataset(summaryStore[2*r + 1], ElasticLineReduction.LINE_INTERCEPT_FORMAT, r));
			}
			
		}
	}

}
