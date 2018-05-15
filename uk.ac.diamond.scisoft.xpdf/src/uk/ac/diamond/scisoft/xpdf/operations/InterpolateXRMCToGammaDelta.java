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
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.GenericPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDetector;

public class InterpolateXRMCToGammaDelta extends AbstractOperation<EmptyModel, OperationData> {

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
		Dataset x = DatasetFactory.zeros(shape);
		Dataset y = DatasetFactory.zeros(shape);
		Dataset z = DatasetFactory.zeros(shape);
		Dataset	phi = DatasetFactory.zeros(shape);
		Dataset tth = DatasetFactory.zeros(shape);

		// Get the angles
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				Vector2d pxPos = new Vector2d(j+0.5, i+0.5);
				Vector2d angles = xdet.anglesFromPixel(pxPos);
				gamma.set(angles.x, i, j);
				delta.set(angles.y, i, j);
				Vector2d phtth = new Vector2d(xdet.polarAnglesFromPixel(pxPos));
				phi.set(phtth.x, i, j);
				tth.set(phtth.y, i, j);
				Vector3d position = xdet.labFromPixel(pxPos);
				x.set(position.x, i, j);
				y.set(position.y, i, j);
				z.set(position.z, i, j);
			}
		}

		// Interpolate to a (γ,δ) grid
		double gamma00 = gamma.getDouble(0, 0);
		double gammaNx = gamma.getDouble(0, shape[1]-1);
		double nGamma = shape[0];
		double gammaStep = (gammaNx - gamma00)/(nGamma-1);

		double delta0 = 0;
		// Limits of delta
		for (int i = 0; i < shape[1]; i++) {
			if (Math.abs(delta.getDouble(0, i)) >= Math.abs(gamma.getDouble(0, i)))
				delta0 = Math.abs(delta.getDouble(0, i));
		}
		double delta00 = -delta0;
		double deltaNx = delta0;
		double nDelta = shape[0];
		double deltaStep = (deltaNx - delta00)/(nDelta-1);

		// Make the axes isotropic at the origin
		double isoStep = Math.min(gammaStep, deltaStep);
		Dataset gammaRange;
		Dataset deltaRange = DatasetFactory.createRange(delta00, deltaNx+isoStep, isoStep);
		gammaRange = deltaRange.clone();
		
		GenericPixelIntegrationCache gdpic = new GenericPixelIntegrationCache(gamma, delta, gammaRange, deltaRange);
		List<Dataset> piResults = PixelIntegration.integrate(DatasetUtils.convertToDataset(input), null, gdpic);
		
		return new OperationData(piResults.get(1));
	}

}
