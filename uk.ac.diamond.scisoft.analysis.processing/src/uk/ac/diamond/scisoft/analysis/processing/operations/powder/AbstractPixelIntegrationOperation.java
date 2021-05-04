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

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.DiffractionCoordinateCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public abstract class AbstractPixelIntegrationOperation<T extends PixelIntegrationModel> extends AbstractOperation<T, OperationData> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractPixelIntegrationOperation.class);
	
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

		IPixelIntegrationCache lcache = getCache(model, md, input.getShape());

		ILazyDataset mask = getFirstMask(input);
		IDataset m = null;
		if (mask != null) {
			try {
				m = mask.getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}

		final List<Dataset> out = PixelIntegration.integrate(input,m,lcache);

		Dataset data = out.remove(1);

		setAxes(data, out);
		
		//Persist Diffraction metadata along the pipe.
		addDiffractionMetadata(data, metadata != null ? metadata : md);

		return new OperationData(data);

	}
	
	private void checkCaches(IDiffractionMetadata md) {
		if (metadata == null) {
			metadata = md;
			cache = null;
		} else {
			boolean dee = metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			boolean dpe = metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = md;
				cache = null;
			}
		}
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
	
	private IPixelIntegrationCache getCache(T model, IDiffractionMetadata md, int[] shape) {
		
		if (useCache()) {
			
			checkCaches(md);
			
			DiffractionCoordinateCache.getInstance().setDisabled(false);
			IPixelIntegrationCache lcache = cache;
			if (lcache == null) {
				synchronized(this) {
					logger.debug("Blocking to build cache");
					lcache = cache;
					if (lcache == null) {
						cache = lcache = buildCache(model, metadata, shape);
					}
				}
			}
			return lcache;
		} else {
			DiffractionCoordinateCache.getInstance().setDisabled(true);
			return buildCache(model, md, shape);
		}
		
	}
	
	protected abstract IPixelIntegrationCache buildCache(T model, IDiffractionMetadata md, int[] shape);
	
	protected boolean useCache() {
		return true;
	}
}
