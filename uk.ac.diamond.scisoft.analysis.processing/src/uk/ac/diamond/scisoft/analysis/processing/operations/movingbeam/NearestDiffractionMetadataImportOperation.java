/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

@Atomic
public class NearestDiffractionMetadataImportOperation extends AbstractOperationBase<NearestDiffractionMetadataImportModel, OperationData> {

	private volatile NearestMetadataCache cache;
	private PropertyChangeListener listener;
	
	@Override
	public void init() {
		this.cache = null;
	}
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam.NearestDiffractionMetadataImportOperation";
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
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		if (Objects.isNull(model.getFilePath()) || model.getFilePath().isEmpty())
			throw new OperationException(this,"Calibration scan path must be set");
		
		if (Objects.isNull(model.getCalibsFolder()) || model.getCalibsFolder().isEmpty())
			throw new OperationException(this, "Path to calibration .nxs files must be specified");
		
	
		//additional checks include that all the offset paths must be set 
		if (Objects.isNull(model.getPositionZeroDataset()) || Objects.isNull(model.getPositionOneDataset()))
			throw new OperationException(this, "Dataset paths corresponding to the axes for dimension 0 and 1 must be set");
	
		
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		NearestMetadataCache lcache = cache;
		if (Objects.isNull(lcache)) {
			synchronized(this) {
				lcache =cache;
				if (Objects.isNull(lcache)) {
					NearestMetadataCache newcache;
					try {
						newcache = new NearestMetadataCache(slice, model, ssm.getFilePath());
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
				if (Objects.isNull(newcache)) throw new OperationException(this, "Error initialising moving beam cache");
				cache = lcache = newcache;
				}
			}
		}
		
		try {
			
			slice.setMetadata(cache.getPositionMeta(ssm));
			slice.setMetadata(cache.getDiffractionMetadata(ssm));
		} catch (Exception e){
			throw new OperationException(this, "Error retrieving metadata from cache");
		}
		
		
		return new OperationData(slice);
		}
	
	@Override
	public void setModel(NearestDiffractionMetadataImportModel model) {

		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					cache = null;
				}
			};
		} else {
			((AbstractOperationModel) this.model).removePropertyChangeListener(listener);
		}

		((AbstractOperationModel) this.model).addPropertyChangeListener(listener);
	}

}
