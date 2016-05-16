/*-
 * Copyright 2016 Diamond Light Source Ltd.
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
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

public abstract class FrameMathsOperation<T extends SelectedFramesModel> extends AbstractOperation<SelectedFramesModel, OperationData> {

	protected Dataset data;
	protected PropertyChangeListener listener;

	protected abstract Dataset getData(IDataset ds);
	
	protected abstract Dataset performOperation(Dataset input, Dataset other);

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		Dataset d = null;
		if (data == null) {
			d = getData(input);
		} else {
			d = data;
		}

		if (d.getError() != null && model.getScaling() != 1) {
			d = ErrorPropagationUtils.multiplyWithUncertainty(d, DatasetFactory.createFromObject(model.getScaling()));
		}

		Dataset output = performOperation(DatasetUtils.convertToDataset(input), d);
		copyMetadata(input, output);

		return new OperationData(output);
	}


	@Override
	public void setModel(SelectedFramesModel model) {

		super.setModel(model);
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

		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}