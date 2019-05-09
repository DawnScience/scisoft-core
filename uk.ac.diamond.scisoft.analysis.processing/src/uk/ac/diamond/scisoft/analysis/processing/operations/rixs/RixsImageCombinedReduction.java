/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageCombinedReductionModel.SCAN_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class RixsImageCombinedReduction extends RixsImageReductionBase<RixsImageCombinedReductionModel> {

	private String elasticScanPath;
	private ElasticLineReduction eop;
	private boolean useSingleFit; // true if using fit from first image only

	public RixsImageCombinedReduction() {
		lines[0] = new StraightLine();
		lines[1] = new StraightLine();

		eop = new ElasticLineReduction();
		eop.setModel(new ElasticLineReductionModel());
	}

	@Override
	public String getFilenameSuffix() {
		return "combined_rixs_spectra";
	}

	@Override
	void updateFromModel(boolean throwEx) {
		super.updateFromModel(throwEx);

		ElasticLineReductionModel em = eop.getModel();

		// use same settings as RIM
		em.setRoiA(model.getRoiA());
		em.setRoiB(model.getRoiB());
		em.setEnergyDirection(model.getEnergyDirection());
		em.setUseCutoff(model.isUseCutoff());
		em.setCutoff(model.getCutoff());
		em.setCutoffSize(model.getCutoffSize());
		em.setClipSpectra(model.isClipSpectra());
		em.setSlopeOverride(model.getSlopeOverride());
		em.setCountsPerPhoton(model.getCountsPerPhoton());

		em.setDelta(model.getDelta());
		// TODO maybe other ERM fields...
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("RIXS Image Reduction");
		log.append("====================");

		updateROICount();

		if (model.getSlopeOverride() != null) {
			lines[0].setParameterValues(model.getSlopeOverride());
			lines[1].setParameterValues(model.getSlopeOverride());
			return;
		}

		// TODO integrated scan file case

		// get elastic line scan file path
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
		String filePath = smd.getSourceInfo().getFilePath();
		boolean fileChanged = !filePath.equals(currentDataFile); 
		if (fileChanged || elasticScanPath == null) {
			currentDataFile = filePath;
			useSingleFit = false; // reset to check in new file
		}

		if (model.getScanOption() == SCAN_OPTION.SAME_SCAN) {
			elasticScanPath = currentDataFile;
		} else {
			File file = new File(filePath);
			File currentDir = file.getParentFile();
			String currentName = file.getName();

			Matcher m = NUMBERED_FILE_REGEX.matcher(currentName);
			if (!m.matches()) {
				throw new OperationException(this, "Current file path does not end with scan number");
			}
			String digits = m.group(1);
			int scan = Integer.parseInt(digits);
			if (model.getScanOption() == SCAN_OPTION.NEXT_SCAN) {
				scan++;
			} else if (model.getScanOption() == SCAN_OPTION.PREVIOUS_SCAN) {
				scan--;
			}
			String format = String.format("%s%%0%dd%s", currentName.substring(0, m.start(1)), digits.length(), currentName.substring(m.end(1)));

			elasticScanPath = new File(currentDir, String.format(format, scan)).getAbsolutePath();
		}

		if (!useSingleFit) {
			// extract operation chain to apply to elastic line images
			OperationMetadata omd = original.getFirstMetadata(OperationMetadata.class);
			List<IOperation<?, ?>> ops = new ArrayList<>();
			for (IOperation<?, ?> o : omd.getPriorOperations()) {
				ops.add(o);
			}
			ops.add(eop);
	
			// do all fits at once (TODO cannot do this for integrated case, especially with live processing)
			OperationMetadata om = new OperationMetadataImpl(getClass().getName(), ops.toArray(new IOperation[ops.size()]), null);
			MyVisitor vis = new MyVisitor();
			om.process(elasticScanPath, smd.getDatasetName(), smd, null, null, vis);
			log.append(vis.operationResult.getLog());

			// TODO record fit results when using NFExecutionVisitor

			SliceInformation si = smd.getSliceInfo();
			useSingleFit = extractFitData(si.getTotalSlices(), si.getSliceNumber(), vis.operationResult.getAuxData());
		}
	}

	static class MyVisitor extends IExecutionVisitor.Stub {
		public OperationData operationResult;
		
		@Override
		public void executed(OperationData result, IMonitor monitor) throws Exception {
			operationResult = result;
		}
	};

	private double[][] fitStore = null;

	private boolean extractFitData(int size, int n, Serializable[] data) {
		boolean isSingle = false;
		double[] fit = new double[4];
		Arrays.fill(fit, Double.NaN);
		for (Serializable s : data) {
			if (s instanceof Dataset) {
				Dataset d = (Dataset) s;
				Matcher m = ElasticLineReduction.LINE_REGEXP.matcher(d.getName());
				if (m.matches()) {
					isSingle = d.getSize() == 1 || d.getSize() < size;
					int r = 2 * Integer.parseInt(m.group(1));
					assert r <= 2;
					if ("c".equals(m.group(2))) { // so m=0,2, c =1,3
						r++;
					}
					fit[r] = isSingle ? d.getDouble() : d.getDouble(n);
				}
			}
		}

		int i = 0, j = 2;
		if (Double.isNaN(fit[0])) {
			if (Double.isNaN(fit[2])) {
				throw new OperationException(this, "No fit available!");
			} else {
				i = 2;
				j = 2;
			}
		} else if (Double.isNaN(fit[2])) {
			j = 0;
		}

		lines[0].setParameterValues(fit[i], fit[i+1]);
		lines[1].setParameterValues(fit[j], fit[j+1]);

		if (n == 0) { // create store for elastic line fits
			fitStore = new double[4][isSingle ? 1 : size];
			if (isSingle) {
				for (int r = 0; r < 2; r++) {
					fitStore[2*r][0] = lines[r].getParameterValue(0);
					fitStore[2*r + 1][0] = lines[r].getParameterValue(1);
				}
			}
		}
		if (!isSingle) {
			for (int r = 0; r < 2; r++) {
				fitStore[2*r][n] = lines[r].getParameterValue(0);
				fitStore[2*r + 1][n] = lines[r].getParameterValue(1);
			}
		}

		return isSingle;
	}

	@Override
	protected void addSummaryData() {
		super.addSummaryData();

		for (int r = 0; r < 2; r++) {
			summaryData.add(ProcessingUtils.createNamedDataset(fitStore[2*r], ElasticLineReduction.LINE_GRADIENT_FORMAT, r));
			summaryData.add(ProcessingUtils.createNamedDataset(fitStore[2*r + 1], ElasticLineReduction.LINE_INTERCEPT_FORMAT, r));
		}
	}
}
