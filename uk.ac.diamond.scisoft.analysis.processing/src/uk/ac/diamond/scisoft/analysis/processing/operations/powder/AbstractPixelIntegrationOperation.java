/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public abstract class AbstractPixelIntegrationOperation<T extends PixelIntegrationModel> extends AbstractOperation<T, OperationData> {

	protected volatile IPixelIntegrationCache cache;
	protected IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return this.getClass().getName();
	}

	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);

		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
		if (metadata == null || (!metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment())&& !metadata.getDetector2DProperties().equals(md.getDetector2DProperties()))) {
			metadata = md;
			cache = null;
		}

		IPixelIntegrationCache lcache = getCache((PixelIntegrationModel)model, metadata);

		ILazyDataset mask = getFirstMask(input);
		IDataset m = null;
		if (mask != null) {
			m = mask.getSlice().squeeze();

		}

		final List<Dataset> out = PixelIntegration.integrate(input,m,lcache);

		Dataset data = out.remove(1);

		setAxes(data, out);
		
		//Persist Diffraction metadata along the pipe.
		addDiffractionMetadata(data, metadata);

		return new OperationData(data);

	}
	
	@Override
	public void setModel(T model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					AbstractPixelIntegrationOperation.this.cache = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	/**
	 * Creates new DiffractionMetadata object and adds it to the output of the 
	 * integration operation. Currently this keeps both the 
	 * DiffractionCrystalEnvironment and the DetectorProperties from the 
	 * original experiment. Detector properties might need to be updated...
	 * 
	 * @param data Output data from integration operation
	 * @param dmd Existing diffraction metadata
	 */
	protected void addDiffractionMetadata(IDataset data, IDiffractionMetadata dmd) {
		//Get metadatas from current environment
		DiffractionCrystalEnvironment integDCE = dmd.getDiffractionCrystalEnvironment();
		DetectorProperties integProps = dmd.getDetector2DProperties();
		
		DiffractionMetadata integratedDMD = new DiffractionMetadata(null, integProps, integDCE);
		data.addMetadata(integratedDMD);
	}
	
	protected abstract void setAxes(IDataset data, List<Dataset> out);
	
	protected abstract IPixelIntegrationCache getCache(PixelIntegrationModel model, IDiffractionMetadata md);
}
