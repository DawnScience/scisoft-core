/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
//package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;
//
//import java.util.List;
//
//import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
//import org.eclipse.dawnsci.analysis.api.processing.Atomic;
//import org.eclipse.dawnsci.analysis.api.processing.OperationException;
//import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
//import org.eclipse.january.MetadataException;
//import org.eclipse.january.dataset.Dataset;
//import org.eclipse.january.dataset.IDataset;
//import org.eclipse.january.metadata.AxesMetadata;
//import org.eclipse.january.metadata.MetadataFactory;
//
//import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
//import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
//import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
//import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AbstractPixelIntegrationOperation;
//import uk.ac.diamond.scisoft.analysis.processing.operations.powder.PixelIntegrationModel;
//
//@Atomic
//public class RemapToQOperation extends AbstractPixelIntegrationOperation<PixelIntegrationModel> {
//
//
//	// Let's give this process an ID tag
//	@Override
//	public String getId() {
//		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.RemapToQOperation";
//	}
//
//
//	@Override
//	public OperationRank getInputRank() {
//		return OperationRank.TWO;
//	}
//
//
//	@Override
//	public OperationRank getOutputRank() {
//		return OperationRank.TWO;
//	}
//
//
//	@Override
//	protected void setAxes(IDataset data, List<Dataset> out) {
//		
//		AxesMetadata amd;
//		try {
//			amd = MetadataFactory.createMetadata(AxesMetadata.class, 2);
//		} catch (MetadataException e) {
//			throw new OperationException(this, e);
//		}
//		Dataset first = out.get(1);
//		Dataset second = out.get(0);
//		amd.setAxis(0, first);
//		amd.setAxis(1, second);
//		data.setMetadata(amd);
//		return;
//
//	}
//
////	
//	@Override
//	protected IPixelIntegrationCache getCache(PixelIntegrationModel model, IDiffractionMetadata md, int[] shape) {
//
//		IPixelIntegrationCache lcache = cache;
//		if (lcache == null) {
//			synchronized(this) {
//				lcache = cache;
//				if (lcache == null) {
//					PixelIntegrationBean bean = new PixelIntegrationBean();
//					bean.setUsePixelSplitting(false);
//					bean.setxAxis(null);
//					bean.setRadialRange(null);
//					bean.setAzimuthalRange(null);
//					bean.setAzimuthalIntegration(true);
//					bean.setTo1D(false);
//					bean.setShape(shape);
//					bean.setLog(false);
//					cache = lcache = new PixelIntegrationCache(metadata, bean);
//				}
//			}
//		}
//		return lcache;
//	}
//}
//


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;



import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;


// @author Tim Snow

public class RemapToQOperation extends AbstractOperation<EmptyModel, OperationData> {


	protected volatile IPixelIntegrationCache cache;
	protected volatile AxesMetadata axisMetadata;
	protected IDiffractionMetadata metadata;
	private static final Logger logger = LoggerFactory.getLogger(RemapToQOperation.class);

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
		
		if (this.metadata == null) {
			this.metadata = md;
			cache = null;
			Dataset qCache = PixelIntegrationUtils.generateQArray(this.metadata);
			Dataset qCacheRolled = qCache.getTransposedView(1, 0);
			try {
				axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 2);
			} catch (MetadataException e) {
				logger.error("Cannot create a two dimensional metadata object", e);
			}
			
			double[] beamPixelLocation = this.metadata.getDetector2DProperties().getBeamCentreCoords();
			int beamX = (int) Math.round(beamPixelLocation[0]);
			int beamY = (int) Math.round(beamPixelLocation[1]);
			
			this.axisMetadata.setAxis(0, qCacheRolled.getSlice(new Slice(beamX, beamX + 1)));
			this.axisMetadata.setAxis(1, qCache.getSlice(new Slice(beamY, beamY + 1)));
		} else {
			boolean dee = this.metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			boolean dpe = this.metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = md;
				cache = null;
				Dataset qCache = PixelIntegrationUtils.generateQArray(this.metadata);
				Dataset qCacheRolled = qCache.getTransposedView(1, 0);
				try {
					axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 2);
				} catch (MetadataException e) {
					logger.error("Cannot create a two dimensional metadata object", e);
				}
				
				double[] beamPixelLocation = this.metadata.getDetector2DProperties().getBeamCentreCoords();
				int beamX = (int) Math.round(beamPixelLocation[0]);
				int beamY = (int) Math.round(beamPixelLocation[1]);
				
				this.axisMetadata.setAxis(0, qCacheRolled.getSlice(new Slice(beamX, beamX + 1)));
				this.axisMetadata.setAxis(1, qCache.getSlice(new Slice(beamY, beamY + 1)));
			}
		}
		
		dataset.setMetadata(axisMetadata);
		
		return new OperationData(dataset);	
	}
}
