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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageCombinedReductionModel.SCAN_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class RixsImageCombinedReduction extends RixsImageReductionBase<RixsImageCombinedReductionModel> {

	private String elasticScanPath;
	private ElasticLineReduction eop;
	private boolean useSingleFit; // true if using fit from first image only
	private boolean usePriorOps; // flag to check if prior operations will be used
	private List<IDataset> iSummaryData = new ArrayList<>();

	/**
	 * New names for fields that are externally links to detector data in other files
	 */
	private static final String ELASTIC_IMAGE = "elastic_image";

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
	void updateFromModel(boolean throwEx, String name) {
		super.updateFromModel(throwEx, name);

		ElasticLineReductionModel em = eop.getModel();

		// use same settings as RIM
		em.setRoiA(model.getRoiA());
		em.setRoiB(model.getRoiB());
		em.setEnergyDirection(model.getEnergyDirection());
		em.setCropROI(model.isCropROI());
		em.setUseCutoff(model.isElUseCutoff());
		em.setCutoff(model.getElCutoff());
		em.setCutoffSize(model.getElCutoffSize());
		em.setClipSpectra(model.isClipSpectra());
		em.setSlopeOverride(model.getSlopeOverride());
		em.setCountsPerPhoton(model.getCountsPerPhoton());

		em.setDelta(model.getDelta());
		// TODO maybe other ERM fields...

		usePriorOps = true; // reset
		iSummaryData.clear();
		SCAN_OPTION scanOpt = model.getScanOption();
		if (scanOpt != SCAN_OPTION.OVERRIDE_FILE || "setElScanFile".equals(name)) {
			elasticScanPath = null;
		}
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("RIXS Image Reduction");
		log.append("====================");

		updateROICount();

		// get elastic line scan file path
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);

		// find elastic image if available
		String filePath = smd.getSourceInfo().getFilePath();
		GroupNode nxDetector = ProcessingUtils.getNXdetector(this, filePath);

		if (nxDetector.containsDataNode(ELASTIC_IMAGE) && !model.isIgnoreLinkedScan()) {
			ILazyDataset elasticImage = nxDetector.getDataNode(ELASTIC_IMAGE).getDataset();
			String eFilePath = ProcessingUtils.getFirstOriginatingFile(elasticImage);
			if (eFilePath != null) {
				elasticScanPath = eFilePath;
				log.append("Found %s which links to %s", ELASTIC_IMAGE, eFilePath);
			}
		}

		boolean fileChanged = !filePath.equals(currentDataFile);
		if (fileChanged || elasticScanPath == null) {
			currentDataFile = filePath;
			useSingleFit = false; // reset to check in new file
		}

		if (elasticScanPath == null) {
			SCAN_OPTION scanOpt = model.getScanOption();
			if (scanOpt == SCAN_OPTION.SAME_SCAN) {
				elasticScanPath = currentDataFile;
			} else if (scanOpt == SCAN_OPTION.OVERRIDE_FILE) {
				elasticScanPath = model.getElScanFile();
				if (elasticScanPath == null) {
					throw new OperationException(this, "Elastic line scan file must be set when scan is OVERRIDE_FILE");
				}
			} else {
				File file = new File(filePath);
				File currentDir = file.getParentFile();
				String currentName = file.getName();
	
				String newName = ProcessingUtils.getNextScanString(currentName, getScanDelta(), "%s%s%s");
				if (newName == null) {
					throw new OperationException(this, "Current file path does not end with scan number");
				}
	
				elasticScanPath = new File(currentDir, newName).getAbsolutePath();
			}
		}
		addConfiguredField("elScanFile", elasticScanPath);

		if (!useSingleFit) {
			OperationData edata = null;
			try {
				edata = fitElasticLine(original, smd);
			} catch (OperationException e) { // if elastic line count time does not match dark image time
				usePriorOps = false;
				log.append("Could not use prior operations: %s", e.toString());
				edata = fitElasticLine(original, smd);
			}

			if (edata != null) {
				for (Serializable s : edata.getSummaryData()) {
					if (s != null) {
						iSummaryData.add(DatasetFactory.createFromObject(s));
					}
				}
			}

			SliceInformation si = smd.getSliceInfo();
			useSingleFit = extractFitData(si.getTotalSlices(), si.getSliceNumber(), edata.getAuxData());
		}
	}

	protected int getScanDelta() {
		int delta;
		switch (model.getScanOption()) {
		case NEXT_SCAN:
			delta = 1;
			break;
		case PREVIOUS_SCAN:
			delta = -1;
			break;
		case SAME_SCAN:
		default:
			delta = 0;
			break;
		}
		return delta;
	}

	private OperationData fitElasticLine(IDataset original, SliceFromSeriesMetadata smd) {
		// extract operation chain to apply to elastic line images
		OperationMetadata omd = original.getFirstMetadata(OperationMetadata.class);
		List<IOperation<?, ?>> ops = new ArrayList<>();
		if (usePriorOps) {
			for (IOperation<?, ?> o : omd.getPriorOperations()) {
				ops.add(o);
			}
		}
		ops.add(eop);

		// do all fits at once (TODO cannot do this for integrated case, especially with live processing)
		OperationMetadata om = new OperationMetadataImpl(getClass().getName(), ops.toArray(new IOperation[ops.size()]), null);
		MyVisitor vis = new MyVisitor();
		om.process(elasticScanPath, smd.getDatasetName(), smd, null, null, vis);
		log.append(vis.operationResult.getLog());
		return vis.operationResult;
	}

	static class MyVisitor extends IExecutionVisitor.Stub {
		public OperationData operationResult;
		
		@Override
		public void executed(OperationData result, IMonitor monitor) throws Exception {
			operationResult = result;
		}
	};

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

		return isSingle;
	}

	@Override
	protected void addSummaryData() {
		super.addSummaryData();

		// collate and, if necessary, stack summary data from elastic fits
		Map<String, List<Dataset>> namedSummary = new LinkedHashMap<>();
		for (IDataset i : iSummaryData) {
			Dataset d = DatasetUtils.convertToDataset(i);
			String n = d.getName();
			List<Dataset> l = namedSummary.get(n);
			if (l == null) {
				l = new ArrayList<>();
				namedSummary.put(n, l);
			}
			l.add(d);
		}
		for (Entry<String, List<Dataset>> e: namedSummary.entrySet()) {
			List<Dataset> l = e.getValue();
			int n = l.size();
			Dataset s;
			if (n == 1) {
				s = l.get(0);
			} else {
				s = stack(l.toArray(new Dataset[n]));
				s.setName(e.getKey());
			}
			summaryData.add(s);
		}
	}
}
