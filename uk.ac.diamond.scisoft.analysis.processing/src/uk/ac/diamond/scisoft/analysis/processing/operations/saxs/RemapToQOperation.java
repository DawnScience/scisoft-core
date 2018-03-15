/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.metadata.UnitMetadataImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.measure.unit.NonSI;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;


// @author Tim Snow

public class RemapToQOperation extends AbstractOperation<EmptyModel, OperationData> {


	protected IDiffractionMetadata metadata;
	private static final Logger logger = LoggerFactory.getLogger(RemapToQOperation.class);
	private Dataset qCacheX;
	private Dataset qCacheY;
	
	
	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.RemapToQOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}
	
	
	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	
	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(dataset);
		
		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
		boolean dee = false;
		boolean dpe = false;
		
		if (this.metadata != null) {
			dee = this.metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			dpe = this.metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
		}
		
		if (!dpe || !dee) {
			this.metadata = md;

			Dataset qCache = PixelIntegrationUtils.generateQArray(this.metadata);
			Dataset qCacheRolled = qCache.getTransposedView(1, 0);
			
			double[] beamPixelLocation = this.metadata.getDetector2DProperties().getBeamCentreCoords();
			int beamX = (int) Math.round(beamPixelLocation[0]);
			int beamY = (int) Math.round(beamPixelLocation[1]);
			
			qCacheX = qCacheRolled.getSlice(new Slice(beamX, beamX + 1)).squeeze();
			qCacheY = qCache.getSlice(new Slice(beamY, beamY + 1)).squeeze();
			qCacheX.setName(XAxis.Q.getXAxisLabel());
			qCacheY.setName(XAxis.Q.getXAxisLabel());
		}
		
		AxesMetadata axisMetadata;
		
		try {
			axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		
		UnitMetadataImpl xAxisUnit = new UnitMetadataImpl(NonSI.ANGSTROM.inverse());
		qCacheX.setMetadata(xAxisUnit);
		qCacheY.setMetadata(xAxisUnit);
		
		axisMetadata.setAxis(0, qCacheX);
		axisMetadata.setAxis(1, qCacheY);
		dataset.setMetadata(axisMetadata);
		
		return new OperationData(dataset);	
	}
}
