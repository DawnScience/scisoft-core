/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalIntegrationDifferenceModel.OperationToPerform;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class AzimuthalIntegrationDifferenceOperation extends AbstractOperation<AzimuthalIntegrationDifferenceModel, OperationData> {
	
	protected volatile IPixelIntegrationCache cache;
	protected IDiffractionMetadata metadata;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalIntegrationDifferenceOperation";
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
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);
		
		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
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
		
		ILazyDataset mask = getFirstMask(input);
		IDataset m = null;
		if (mask != null) {
			try {
				m = mask.getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		IPixelIntegrationCache lcache = getCache(metadata, input.getShape());
		
		final List<Dataset> out = PixelIntegration.integrate(input,m,lcache);
		
		Dataset image = PixelIntegrationUtils.generate2Dfrom1D(new Dataset[] {out.get(0), out.get(1)},lcache.getXAxisArray()[0]);
		
		OperationToPerform operationSelected = model.getOperationSelected();
		
		switch (operationSelected) {
			case SUBTRACT:		image = Maths.subtract(input, image);
								break;
			case DIVIDE:		image = Maths.divide(input, image);
								break;
			case DIFFERENCE:	image = Maths.divide(Maths.subtract(input, image), input);
								break;
			default:			RuntimeException unexpectedSelectionException = new RuntimeException();
								throw unexpectedSelectionException;
		}
		
		copyMetadata(input, image);
		
		return new OperationData(image);
	}
	
	
	protected IPixelIntegrationCache getCache(IDiffractionMetadata md, int[] shape) {
		
		IPixelIntegrationCache lcache = cache;
		if (lcache == null) {
			synchronized(this) {
				lcache = cache;
				if (lcache == null) {
					PixelIntegrationBean bean = new PixelIntegrationBean();
					bean.setUsePixelSplitting(false);
					bean.setxAxis(XAxis.Q);
					bean.setAzimuthalIntegration(true);
					bean.setTo1D(true);
					bean.setShape(shape);
					bean.setSanitise(true);
					cache = lcache = new PixelIntegrationCache(metadata, bean);
				}
			}
		}
		return lcache;
	}
}
