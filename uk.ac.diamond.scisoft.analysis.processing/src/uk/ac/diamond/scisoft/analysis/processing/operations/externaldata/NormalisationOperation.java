/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class NormalisationOperation extends AbstractOperation<ExternalDataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.NormalisationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		//will not be null
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		Dataset in = DatasetUtils.convertToDataset(input);
		
		String path = model.getFilePath();
		if (path == null) path = ssm.getFilePath();
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, path, model.getDatasetName());

		IDataset val = ssm.getMatchingSlice(lz).squeeze();
		
		if (val.getRank() != 0) throw new OperationException(this, "External data shape invalid");
		
		DoubleDataset out = (DoubleDataset)DatasetFactory.zeros(input.getShape(), Dataset.FLOAT64);
		Dataset er = DatasetUtils.convertToDataset(in.getError());
		DoubleDataset oute = in.getError() == null ? null : (DoubleDataset)DatasetFactory.zeros(input.getShape(), Dataset.FLOAT64);
		
		out.setError(oute);
		
		double tmp = 0;
		double tmpv = val.getDouble();
		
		for (int i = 0; i< input.getSize(); i++) {
			tmp = in.getElementDoubleAbs(i);
			out.setAbs(i, tmp/tmpv);
			
			if (oute != null) oute.setAbs(i, er.getElementDoubleAbs(i)/tmpv);
			
		}

		copyMetadata(in, out);
		
		return new OperationData(out);
	}
	
}
