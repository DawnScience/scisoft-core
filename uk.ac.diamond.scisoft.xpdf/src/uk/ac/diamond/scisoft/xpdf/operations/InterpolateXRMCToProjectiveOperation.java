/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.GenericPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDetector;

public class InterpolateXRMCToProjectiveOperation extends AbstractOperation<InterpolateXRMCToProjectiveModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.InterpolateXRMCToProjectiveOperation";
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
		int[] shape = input.getShape();
		XRMCMetadata xrmcMetadata = input.getFirstMetadata(XRMCMetadata.class);
		XRMCDetector xdet = xrmcMetadata.getDetector();
		
		Dataset xp = DatasetFactory.zeros(shape);
		Dataset yp = DatasetFactory.zeros(shape);
		
		Vector3d beamUi = new Vector3d(xrmcMetadata.getSource().getUI());
		Vector3d beamUk = new Vector3d(xrmcMetadata.getSource().getUK());
		Vector3d beamUj = new Vector3d();
		beamUj.cross(beamUk, beamUi);
		
		// Get the angles
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				Vector2d pxPos = new Vector2d(j+0.5, i+0.5);
				Vector3d labPos = xdet.labFromPixel(pxPos);

				double pixelX = labPos.dot(beamUi);
				double pixelY = labPos.dot(beamUj);
				double pixelZ = labPos.dot(beamUk);

				xp.set(pixelX/pixelZ, i, j);
				yp.set(pixelY/pixelZ, i, j);
			}
		}

		// Step size of the array
		int[] size = (model.getSize() == null) ? input.getShape() : model.getSize();
		
		// Get limits or calculate limits
		Dataset xpRange;
		Dataset ypRange;

		if (model.getXpRange() == null) {
			double mi = (double) xp.min();
			double ma = (double) xp.max();
			double steps = (ma-mi)/size[0];
			xpRange = DatasetFactory.createRange(mi, ma+steps, steps);
		} else {
			xpRange = DatasetFactory.createRange(model.getXpRange()[0], model.getXpRange()[1], size[0]);
		}
		
		if (model.getYpRange() == null) {
			double mi = (double) yp.min();
			double ma = (double) yp.max();
			double steps = (ma-mi)/size[1];
			ypRange = DatasetFactory.createRange(mi, ma+steps, steps);
		} else {
			ypRange = DatasetFactory.createRange(model.getYpRange()[0], model.getYpRange()[1], size[1]);
		}

		GenericPixelIntegrationCache gdpic = new GenericPixelIntegrationCache(xp, yp, xpRange, ypRange);
		Dataset projectiveData = PixelIntegration.integrate(DatasetUtils.convertToDataset(input), null, gdpic).get(1);
		
		projectiveData.setMetadata(xrmcMetadata);
		
		// Construct the AxesMetadata
		AxesMetadata axMan = null;
		try {
			axMan = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException mE) {
			throw new OperationException(this, "Could not create AxesMetadata: " + mE.toString());
		}
		axMan.addAxis(0, xpRange);
		axMan.addAxis(1, ypRange);
		projectiveData.setMetadata(axMan);
		
		return new OperationData(projectiveData);
	}

}
