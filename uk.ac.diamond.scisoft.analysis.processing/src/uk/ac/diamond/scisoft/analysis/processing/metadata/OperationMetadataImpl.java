/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.metadata;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.processing.visitor.AveragingOutputExecutionVisitor;

@SuppressWarnings("rawtypes")
public class OperationMetadataImpl implements OperationMetadata {

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = LoggerFactory.getLogger(OperationMetadataImpl.class);
	
	private String filename;
	private IOperation current;
	private IOperation[] operations;
	
	
	public OperationMetadataImpl(String filename, IOperation[] operations, IOperation current) {
		this.filename = filename;
		this.operations = operations;
		this.current = current;
	}

	@Override
	public String getOutputFilename() {
		return filename;
	}
	
	@Override
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata) {
		return process(filename, datasetName, metadata, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata, Integer start, Integer stop) {
		
		SliceND sampling = null;
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(current, filename, datasetName);
		lz = lz.getSliceView();
		IOperation[] pOps = getPreviousOperations();
		
		SourceInformation si = new SourceInformation(filename, datasetName, lz);
		lz.setMetadata(new SliceFromSeriesMetadata(si));
		
		AveragingOutputExecutionVisitor vis = new AveragingOutputExecutionVisitor();
		
		IOperationService opServ = LocalServiceManager.getOperationService();
		IOperationContext context = opServ.createContext();
		context.setData(lz);
		int[] dataDims = updateDataDimensions(metadata.getDataDimensions(), metadata.getSourceInfo().getParent().getRank(), lz.getRank());
		context.setDataDimensions(dataDims);
		context.setVisitor(vis);
		context.setSeries(pOps);
		
		
		if (start != null || stop != null) {
			int fd = DataUtils.calculateFastestDimension(dataDims, lz.getShape());
			if (fd != -1) {
				sampling = new SliceND(lz.getShape());
				sampling.setSlice(fd, start, stop, 1);
			}
		}
		
		context.setSlicing(sampling);
		opServ.execute(context);
		
		return vis.getAverage();
	}
	
	@Override
	public OperationMetadata clone() {

		return new OperationMetadataImpl(filename, operations, current);
		
	}
	
	private int[] updateDataDimensions(int[] dataDimensions, int initRank, int newRank) {
		//assume c style NDarrays, i.e. fastest dimension is last dimension
		if (initRank == newRank) return dataDimensions.clone();
		if (dataDimensions.length > newRank) throw new RuntimeException("Data dimensions larger than dataset rank!");
		int[] dd = new int[dataDimensions.length];
		for (int i = 0; i < dd.length; i++) dd[i] = dataDimensions[i] - (newRank - initRank);
		return dd;
		
	}
	
	private IOperation[] getPreviousOperations(){
		int i = 0;
		for (;i<operations.length;i++) if (operations[i] == current) break;
		IOperation[] ops = new IOperation[i];
		for (int j = 0 ; j < ops.length; j++) ops[j] = cloneOperation(operations[j]);
		return ops;
	}
	
	private IOperation cloneOperation(IOperation op) {
		
		IOperation clone = null;
		
		try {
			clone= LocalServiceManager.getOperationService().create(op.getId());
			IOperationModel model = clone.getModel();
			BeanUtils.copyProperties(model, op.getModel());
		} catch (Exception e) {
			logger.error("TODO put description of error here", e);
			return null;
		}
		
		return clone;
	}


}
