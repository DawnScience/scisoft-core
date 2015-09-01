/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

//public because it needs to be visible in the uk...xpdf.operations package
public class XPDFComponentCylinder extends XPDFComponentGeometry {

	public XPDFComponentCylinder() {
		super();
	}

	public XPDFComponentCylinder(XPDFComponentCylinder inCyl) {
		super(inCyl);
	}

	public XPDFComponentCylinder(XPDFComponentGeometry inGeom) {
		super(inGeom);
	}

	@Override
	protected XPDFComponentGeometry clone() {
		return new XPDFComponentCylinder(this);
	}

	@Override
	public String getShape() {
		return "cylinder";
	}

	@Override
	public double getIlluminatedVolume(XPDFBeamData beamData) {
		// Mathematics to match DK's python version. Never mind the
		// four-dimensional volume

		double h_2 = beamData.getBeamHeight() / 2;
		double illuminatedHeight = Math.PI;

		if (rOuter >= h_2)
			illuminatedHeight -= 2 * Math.cos(h_2 / rOuter) + h_2
					* Math.sqrt(h_2 * h_2 + rOuter * rOuter);

		return illuminatedHeight * beamData.getBeamWidth() * rOuter * rOuter;
	}

	@Override
	public Dataset getUpstreamPathLength(Dataset x, Dataset y, Dataset z) {
		// Thickness of the cylinder at x
		return thicknessAtDistanceFromRadius(x, z);
	}

	@Override
	public Dataset getDownstreamPathLength(Dataset x, Dataset y, Dataset z,
			double gamma, double delta) {
	// The capillary is held vertically, such that γ=π/2 is along it. 
		Dataset d =
				Maths.subtract(
						Maths.multiply(x, Math.cos(delta)),
						Maths.multiply(z, -Math.sin(delta))
						);
		Dataset w = 
				Maths.add(
						Maths.multiply(x, Math.sin(delta)),
						Maths.multiply(z, -Math.cos(delta))
						);

		// Don't forget the secant factor
		return Maths.divide(
				thicknessAtDistanceFromRadius(d, w), Math.cos(gamma));
	}

	@Override
	public Dataset calculateAbsorptionCorrections(Dataset gamma, Dataset delta,
			XPDFComponentGeometry attenuatorGeometry, double attenuationCoefficient,
			XPDFBeamData beamData,
			boolean doUpstreamAbsorption, boolean doDownstreamAbsorption) {
		double thickness = rOuter - rInner;
		
		// Account for the streamality of the (half) cylinder
		double arc = 0.0, xiMin = 0.0, xiMax = 0.0;
		if (doUpstreamAbsorption && doDownstreamAbsorption) {
			arc = 2*Math.PI;
			xiMin = -Math.PI;
			xiMax = Math.PI;
		} else {
			if (!(doUpstreamAbsorption || doDownstreamAbsorption))
				return DoubleDataset.zeros(gamma);
			arc = Math.PI;
			if (doDownstreamAbsorption) {
				xiMin = -Math.PI/2;
				xiMax = Math.PI/2;
			} else if (doUpstreamAbsorption) {
				xiMin = -3*Math.PI/2;
				xiMax = Math.PI/2;
			} else {
				; // You really shouldn't be here
			}
		}
		double aspectRatio = (xiMax-xiMin)*rOuter/thickness;
		double log2RSteps = Math.round(Math.log(gridSize/aspectRatio)/2/Math.log(2.0));
		double rSteps = Math.pow(2.0, log2RSteps);
		double xiSteps = gridSize/rSteps;
		double dR = thickness/rSteps;
		double dXi = (arc)/xiSteps;
		
		Dataset r1D = DoubleDataset.createRange(rInner+dR/2, rOuter-dR/2+dR/1e6, dR);
		Dataset xi1D = DoubleDataset.createRange(xiMin+dXi/2, xiMax-dXi/2+dXi/1e6, dXi);
		
		// TODO: Is this the best way to expand a Dataset?
		Dataset rCylinder = new DoubleDataset(r1D.getSize(), xi1D.getSize());
		Dataset xiCylinder = new DoubleDataset(r1D.getSize(), xi1D.getSize());
		for (int i = 0; i<rSteps; i++) {
			for (int k = 0; k<xiSteps; k++) {
				rCylinder.set(r1D.getDouble(i), i, k);
				xiCylinder.set(xi1D.getDouble(k), i, k);
			}
		}

		Dataset xPlate = Maths.multiply(rCylinder, Maths.sin(xiCylinder));
		Dataset yPlate = DoubleDataset.zeros(xPlate);
		Dataset zPlate = Maths.multiply(rCylinder, Maths.cos(xiCylinder));
		
		// TODO: There has to be a better way to make a mask Dataset
		Dataset illuminationPlate = DoubleDataset.ones(xPlate);
		for (int i=0; i<xPlate.getShape()[0]; i++){
			for (int k=0; k<xPlate.getShape()[1]; k++) {
				if (Math.abs(xPlate.getDouble(i, k)) > beamData.getBeamHeight()/2)
					illuminationPlate.set(0.0, i, k);
			}
		}
		
		Dataset illuminatedVolume = Maths.multiply(illuminationPlate, Maths.multiply(dR*dXi, rCylinder));
		
		Dataset upstreamPathLength;
		Dataset downstreamPathLength;
		if (doUpstreamAbsorption) {
			upstreamPathLength = attenuatorGeometry.getUpstreamPathLength(xPlate, yPlate, zPlate);
		} else {
			upstreamPathLength = DoubleDataset.zeros(xPlate);
		}
		
		Dataset absorptionCorrection = new DoubleDataset(gamma);
		// Loop over all detector angles
		for (int i = 0; i<gamma.getShape()[0]; i++) {
			for (int k = 0; k<gamma.getShape()[1]; k++) {
				if (doDownstreamAbsorption)
					downstreamPathLength = attenuatorGeometry.getDownstreamPathLength(xPlate, yPlate, zPlate, gamma.getDouble(i, k), delta.getDouble(i, k));
				else
					downstreamPathLength = DoubleDataset.zeros(xPlate);
				
				absorptionCorrection.set( (double)
						Maths.multiply(
								Maths.exp(
										Maths.multiply(
												-attenuationCoefficient, 
												Maths.add(
														upstreamPathLength,
														downstreamPathLength
														)
												)
										),
										illuminatedVolume
								).sum() / (double) illuminatedVolume.sum(), i, k);
			}
		}
		
		return absorptionCorrection;
		}

	private Dataset thicknessAtDistanceFromRadius(Dataset p, Dataset z) {
		// Given a distance from the radius vector, calculate the path length
		// parallel to the radius

		Dataset zOuter = Maths.sqrt(
				Maths.subtract(
						rOuter*rOuter,
						Maths.square(Maths.minimum(Maths.abs(p), rOuter))
						)
				);
		Dataset zInner = Maths.sqrt(
				Maths.subtract(
						rInner*rInner,
						Maths.square(Maths.minimum(Maths.abs(p), rInner))
						)
				);
		
		Dataset l = Maths.add(Maths.add(Maths.add(
				Maths.maximum(Maths.multiply(z,  -1), zOuter), 
				Maths.minimum(z, zOuter)),
				Maths.minimum(z, Maths.multiply(zInner, -1))), 
				Maths.maximum(Maths.multiply(z, -1), Maths.multiply(zInner, -1)));
		
		return Maths.abs(l);
	}

}
