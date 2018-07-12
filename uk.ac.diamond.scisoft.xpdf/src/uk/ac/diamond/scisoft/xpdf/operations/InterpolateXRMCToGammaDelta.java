/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.List;

import javax.vecmath.Vector2d;

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

public class InterpolateXRMCToGammaDelta extends AbstractOperation<InterpolateXRMCToGDModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.InterpolateXRMCToGammaDelta";
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
		// Create the arrays to calculate the interpolation
		
		int[] shape = input.getShape();
		XRMCMetadata xrmcMetadata = input.getFirstMetadata(XRMCMetadata.class);
		XRMCDetector xdet = xrmcMetadata.getDetector();
		
		Dataset gamma = DatasetFactory.zeros(shape);
		Dataset delta = DatasetFactory.zeros(shape);

		// Get the angles
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				Vector2d pxPos = new Vector2d(j+0.5, i+0.5);
				Vector2d angles = xdet.anglesFromPixel(pxPos);
				gamma.set(angles.x, i, j);
				delta.set(angles.y, i, j);
			}
		}

		// Here we bail if either of the 'Show $coordinate' boxes have been checked
		if (model.isShowGamma()) {
			return new OperationData(gamma);
		}
		
		if (model.isShowDelta()) {
			return new OperationData(delta);
		}
		
		// Interpolate to a (γ,δ) grid

		AngularLimits angularLimits = new AngularLimits();

		if (model.getGammaRange() == null || model.getDeltaRange() == null)
			angularLimits.calculate(gamma, delta);
		
		if (model.getStepSize() != null) {
			angularLimits.gammaStep = angularLimits.deltaStep = model.getStepSize();
		}
		
		if (model.getGammaRange() != null) {
			angularLimits.gammaMin = model.getGammaRange()[0];
			angularLimits.gammaMax = model.getGammaRange()[1];
		}
		if (model.getDeltaRange() != null) {
			angularLimits.deltaMin = model.getDeltaRange()[0];
			angularLimits.deltaMax = model.getDeltaRange()[1];
		}
		
		Dataset deltaRange = DatasetFactory.createRange(angularLimits.deltaMin, angularLimits.deltaMax, angularLimits.deltaStep);
		Dataset gammaRange = DatasetFactory.createRange(angularLimits.gammaMin, angularLimits.gammaMax, angularLimits.gammaStep);
		
		GenericPixelIntegrationCache gdpic = new GenericPixelIntegrationCache(gamma, delta, gammaRange, deltaRange);
		List<Dataset> piResults = PixelIntegration.integrate(DatasetUtils.convertToDataset(input), null, gdpic);
		
		Dataset gdData = piResults.get(1);
		gdData.setMetadata(xrmcMetadata);
		
		// Construct the AxesMetadata
		AxesMetadata axMan = null;
		try {
			axMan = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException mE) {
			throw new OperationException(this, "Could not create AxesMetadata: " + mE.toString());
		}
//		axMan.initialize(2);
		axMan.addAxis(0, gammaRange);
		axMan.addAxis(1, deltaRange);
		gdData.setMetadata(axMan);
		
		return new OperationData(gdData);
	}

	
	private class AngularLimits {
		double gammaMin;
		double gammaMax;
		double deltaMin;
		double deltaMax;
		double gammaStep;
		double deltaStep;
	
		public AngularLimits() {
			gammaMin = Double.MAX_VALUE;
			gammaMax = Double.MIN_VALUE;
			deltaMin = Double.MAX_VALUE;
			deltaMax = Double.MIN_VALUE;
			gammaStep = 0.0;
			deltaStep = 0.0;
		}

		public void calculate(Dataset gamma, Dataset delta) {

			int[] shape = gamma.getShape();

			double gamma00 = gamma.getDouble(0, 0);
			double gammaNx = gamma.getDouble(0, shape[1]-1);
			double nGamma = shape[0];
			gammaStep = (gammaNx - gamma00)/(nGamma-1);
			
			double delta0 = 0;
			// Limits of delta
			for (int i = 0; i < shape[1]; i++) {
				if (Math.abs(delta.getDouble(0, i)) >= Math.abs(gamma.getDouble(0, i)))
					delta0 = Math.abs(delta.getDouble(0, i));
			}
			double delta00 = -delta0;
			double deltaNx = delta0;
			double nDelta = shape[0];
			deltaStep = (deltaNx - delta00)/(nDelta-1);

			
			// Make the axes isotropic at the origin
			double isoStep = Math.min(gammaStep, deltaStep);
			gammaStep = isoStep;
			deltaStep = isoStep;
			
			// Delta is the more restrictive coordinate
			deltaMin = delta00;
			deltaMax = deltaNx + deltaStep; 

			// Make a square array
			gammaMin = deltaMin;
			gammaMax = deltaMax;
		
		}
	}
}
