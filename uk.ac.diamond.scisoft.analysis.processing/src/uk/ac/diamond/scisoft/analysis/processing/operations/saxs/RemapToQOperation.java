/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;


// @author Tim Snow


public class RemapToQOperation extends AbstractOperation<EmptyModel, OperationData> {


	protected IDiffractionMetadata metadata;
	private static final Logger logger = LoggerFactory.getLogger(RemapToQOperation.class);
	AxesMetadata axisMetadata;
	
	
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
			
			// Find out how big our dataset is and create its q-space as well as defining the units of q
			int[] datasetShape = dataset.getShape();
			QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());
			UnitMetadata axisUnit;
			try {
				axisUnit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.ANGSTROM.inverse());
			} catch (MetadataException e) {
				throw new OperationException(this, "Could not create axes' unit metadata", e);
			}
			
			// Construct the axis datasets, then find the relevant q values and subsequently set the units and titles
			DoubleDataset xAxes = DatasetFactory.zeros(DoubleDataset.class, datasetShape[0], datasetShape[1]);
			DoubleDataset yAxes = DatasetFactory.zeros(DoubleDataset.class, datasetShape[0], datasetShape[1]);
			
			for (int yAxisIter = 0; yAxisIter < datasetShape[0]; yAxisIter ++) {
				for (int xAxisIter = 0; xAxisIter < datasetShape[1]; xAxisIter ++) {
					Vector3d qPosition = qSpace.qFromPixelPosition(xAxisIter + 0.5, yAxisIter + 0.5);
					xAxes.set(-qPosition.x, yAxisIter, xAxisIter); // Invert sign of values to make plot look reasonable
					yAxes.set(qPosition.y, yAxisIter, xAxisIter);
				}
			}
			
			xAxes.setMetadata(axisUnit);
			yAxes.setMetadata(axisUnit);
			xAxes.setName("Q_x");
			yAxes.setName("Q_y");
			
			// Now try to initialise the axis metadata object
			try {
				axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 2);
			} catch (MetadataException e) {
				logger.error("Unable to initialise axes metadata for remap to q processing plugin");
				throw new OperationException(this, e);
			}
			
			// And populate it
			axisMetadata.addAxis(0, yAxes);
			axisMetadata.addAxis(1, xAxes);
		}
		
		// Then set the axis metadata, overwriting anything that might be there already
		dataset.setMetadata(axisMetadata.clone());
		
		return new OperationData(dataset);	
	}
}
