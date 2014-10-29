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
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;

public abstract class AbstractPixelIntegrationOperation<T extends PixelIntegrationModel> extends AbstractOperation<T, OperationData> {

	private AbstractPixelIntegration integrator;
	private IDiffractionMetadata metadata;
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
			integrator = null;
		}

		if (integrator == null) integrator = createIntegrator((PixelIntegrationModel)model, metadata);

		ILazyDataset mask = getFirstMask(input);
		if (mask != null) {
			IDataset m = mask.getSlice().squeeze();
			integrator.setMask((Dataset)m);
		}

		final List<Dataset> out = integrator.integrate(input);

		Dataset data = out.remove(1);

		setAxes(data, out);

		return new OperationData(data);

	}
	
	@Override
	public void setModel(T model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					AbstractPixelIntegrationOperation.this.integrator = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	

	protected abstract void setAxes(IDataset data, List<Dataset> out);
	
	protected abstract AbstractPixelIntegration createIntegrator(PixelIntegrationModel model, IDiffractionMetadata md);
}
