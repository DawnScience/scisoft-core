/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

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
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ExampleExternalDataSubtractionOperation extends
		AbstractOperation<ExampleExternalDataModel, OperationData> {

	private PropertyChangeListener listener;
	private Dataset data;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.examples.ExampleExternalDataSubtractionOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		//will not be null
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		//lz also not null (Exception thrown if data not found)
		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, model.getFilePath(), model.getDatasetName());
		
		return null;
	}
	
	@Override
	public void setModel(ExampleExternalDataModel model) {
		
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					data = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		super.setModel(model);
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
}
