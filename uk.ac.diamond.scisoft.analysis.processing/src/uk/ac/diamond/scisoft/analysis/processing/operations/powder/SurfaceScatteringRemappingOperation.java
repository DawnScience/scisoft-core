/*-
 * Copyright 2016 Diamond Light Source Ltd.
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

import javax.naming.InterruptedNamingException;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.SurfacePixelIntegrationCache;

@Atomic
public class SurfaceScatteringRemappingOperation extends AbstractOperation<SurfaceScatteringRemappingModel, OperationData> {

	private IPixelIntegrationCache cache;
	private IDiffractionMetadata metadata;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.SurfaceScatteringRemappingOperation";
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
		
		IDiffractionMetadata md = input.getFirstMetadata(IDiffractionMetadata.class);
		
		if (md == null) throw new OperationException(this, "Remapping requires a calibration to be loaded");

		if (model.getParRange() != null && model.getParRange().length != 2) throw new OperationException(this, "Ranges must have 2 values!");
		if (model.getPerpRange() != null && model.getPerpRange().length != 2) throw new OperationException(this, "Ranges must have 2 values!");
		
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

		IPixelIntegrationCache lcache = getCache(model, metadata, input.getShape());

		ILazyDataset mask = getFirstMask(input);
		IDataset m = null;
		if (mask != null) {
			try {
				m = mask.getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}

		final List<Dataset> integrated = PixelIntegration.integrate(input,m,lcache);
		
		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		Dataset data = integrated.get(1);
		Dataset first = integrated.get(2);
		Dataset second = integrated.get(0);
		amd.setAxis(0, first);
		amd.setAxis(1, second);
		data.setMetadata(amd);
		
		return new OperationData(data);
	}
	
	private IPixelIntegrationCache getCache(
			SurfaceScatteringRemappingModel model, IDiffractionMetadata md, int[] shape) {

		IPixelIntegrationCache lcache = cache;
		if (lcache == null) {
			synchronized(this) {
				lcache = cache;
				if (lcache == null) {
					
					int[] outShape = shape.clone();
					if (model.getBinsPara() != null) outShape[1] = model.getBinsPara();
					if (model.getBinsPerp() != null) outShape[0] = model.getBinsPerp();
					
					SurfacePixelIntegrationCache c = 
							new SurfacePixelIntegrationCache(md, shape, 
									model.getPitch(), model.getRoll(),
									outShape[1], outShape[0], model.getParRange(), model.getPerpRange());

					cache = lcache = c;
				}
			}
		}
		return lcache;
	}

	@Override
	public void setModel(SurfaceScatteringRemappingModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					SurfaceScatteringRemappingOperation.this.cache = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}

}
