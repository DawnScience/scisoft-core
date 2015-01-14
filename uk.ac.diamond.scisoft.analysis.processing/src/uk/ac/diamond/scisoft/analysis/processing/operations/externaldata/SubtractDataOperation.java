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
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyMaths;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class SubtractDataOperation extends AbstractOperation<SelectedFramesModel, OperationData> {

	private Dataset subtrahend;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractDataOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset bg = null;
		if (subtrahend == null) {
			bg = getImage(input);
			subtrahend = bg;
		} else {
			bg = subtrahend;
		}
		
		Dataset output = Maths.subtract(input, bg);
		output.setError(getErrorDataset(DatasetUtils.convertToDataset(input),bg));
		copyMetadata(input, output);
		
		return new OperationData(output);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	protected Dataset getImage(IDataset input) throws OperationException {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		ILazyDataset lz = ProcessingUtils.getLazyDataset(this, model.getFilePath(), model.getDatasetName());

		if (model.getStartFrame() == null && model.getEndFrame() == null) {
			if (Arrays.equals(lz.getShape(), ssm.getSourceInfo().getParent().getShape())) {
				return (Dataset)lz.getSlice(ssm.getSliceFromInput()).squeeze();
			} 
		}
			
		int s = model.getStartFrame() == null ? 0 : model.getStartFrame();
		int e = model.getEndFrame() == null ? Integer.MAX_VALUE -1 : model.getEndFrame();

		int[] dataDims = ssm.getDataDimensions();
		
		if (input.getRank() == 1 && dataDims.length == 2) {
			dataDims = new int[]{dataDims[0]};
		}
		
		return LazyMaths.mean(s,  e,lz,dataDims).squeeze();
		
	}
	
	private Dataset getErrorDataset(Dataset input, Dataset sub) {
		Dataset ie = input.getError();
		Dataset sube = sub.getError();
		if (ie != null) {
			if (sube != null) {
				DoubleDataset e = new DoubleDataset(input.getShape());
				
				IndexIterator it = e.getIterator();
				double val = 0;
				while (it.hasNext()) {
					val = Math.hypot(ie.getDouble(it.index), sube.getDouble(it.index));
					e.setAbs(it.index, val);
				}
					
				return e;
			} 
		}
		return ie;
	}
	
	@Override
	public void setModel(SelectedFramesModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					subtrahend = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}

}
