/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;


@Atomic
public class DiffractionMetadataImportOperation extends AbstractOperationBase<DiffractionMetadataImportModel, OperationData> {

	private volatile IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);

		slice.setMetadata(getMeta(model, ssm.getParent(), ssm.getDatasetName()));
		return new OperationData(slice);
	}
	
	private IDiffractionMetadata getMeta(DiffractionMetadataImportModel mod, ILazyDataset parent, String name) {

		IDiffractionMetadata lmeta = metadata;
		if (lmeta == null) {
			synchronized(this) {
				lmeta = metadata;
				if (lmeta == null) {
					IDiffractionMetadata md;
					try {
						md = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(mod.getFilePath(), parent, name);
					} catch (DatasetException e) {
						throw new OperationException(this, e);
					}
					if (md == null) throw new OperationException(this, "File does not contain metadata");
					metadata = lmeta = md;
					
				}
			}
		}
		return lmeta;
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
	public void setModel(DiffractionMetadataImportModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					metadata = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}

}
