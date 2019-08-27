/*
 * Copyright (c) 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ImportMaskModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ImportMaskOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;


@Atomic
public class ImportMaskInternalFilepathOperation<T extends ImportMaskInternalFilepathModel> extends AbstractOperation<T, OperationData>{

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.ImportMaskInternalFilepathOperation";
	}
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (model.getFilePathDataset() == null) throw new IllegalArgumentException("Internal mask file path may not be null");
		
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		IDataset datasetContainingFilePath = ProcessingUtils.getDataset(this, ssm.getFilePath(), model.getFilePathDataset());
		
		String filePath = null;
				
		if (datasetContainingFilePath != null) {
			filePath = DatasetUtils.cast(datasetContainingFilePath, Dataset.STRING).getString();
		}
		
		ImportMaskModel maskModel = new ImportMaskModel();
		maskModel.setFilePath(filePath);
		
		ImportMaskOperation<ImportMaskModel> maskOperation = new ImportMaskOperation<ImportMaskModel>();
		maskOperation.setModel(maskModel);
		
		return maskOperation.process(input, monitor);
	}
}
