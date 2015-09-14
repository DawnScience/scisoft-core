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

/**
 * The class for cylindrical components of the experimental target. This class
 * is <code>public</code> because it needs to be visible in the
 * uk...xpdf.operations package
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 *
 */
public class XPDFComponentCylinder extends XPDFComponentGeometry {

	/**
	 * Empty constructor
	 */
	public XPDFComponentCylinder() {
		super();
	}

	/**
	 * Copy constructor from another cylinder.
	 * @param inCyl
	 * 			cylinder to be copied
	 */
	public XPDFComponentCylinder(XPDFComponentCylinder inCyl) {
		super(inCyl);
	}

	/**
	 * Copy constructor from another geometric object.
	 * @param inGeom
	 * 				geometry to be copied
	 */
	public XPDFComponentCylinder(XPDFComponentGeometry inGeom) {
		super(inGeom);
	}

	/**
	 * Clone method.
	 */
	@Override
	protected XPDFComponentGeometry clone() {
		return new XPDFComponentCylinder(this);
	}

	/**
	 * Returns the shape of this cylinder.
	 */
	@Override
	public String getShape() {
		return "cylinder";
	}

	/**
	 * Calculates the illuminated volume.
	 * <p>
	 * Return the illuminated volume of this cylinder, given the beam data. The
	 *  beam is assumed to be centred on the cylinder. 
	 */
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

	/**
	 * Returns the path length upstream of the given points.
	 */
	@Override
	public Dataset getUpstreamPathLength(Dataset x, Dataset y, Dataset z) {
		// Thickness of the cylinder at x
		return thicknessAtDistanceFromRadius(x, z);
	}

	/**
	 * Returns the path length downstream of the given points.
	 */
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

	/**
	 * Calculates the absorption correction map when attenuatorGeometry is attenuating.
	 */
	@Override
	public Dataset calculateAbsorptionCorrections(Dataset gamma, Dataset delta,
			XPDFComponentGeometry attenuatorGeometry, double attenuationCoefficient,
			XPDFBeamData beamData,
			boolean doUpstreamAbsorption, boolean doDownstreamAbsorption) {
		double thickness = rOuter - rInner;
		
		// Account for the streamality of the (half?) cylinder
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
		// Calculate the number of grid points in each dimension. The total
		// number should be gridSize, and the grid boxes should be roughly 
		// isotropic on the surface of the cylinder.
		double aspectRatio = (xiMax-xiMin)*rOuter/thickness;
		double log2RSteps = Math.round(Math.log(gridSize/aspectRatio)/2/Math.log(2.0));
		double rSteps = Math.pow(2.0, log2RSteps);
		double xiSteps = gridSize/rSteps;
		double dR = thickness/rSteps;
		double dXi = (arc)/xiSteps;
		
		Dataset r1D = DoubleDataset.createRange(rInner+dR/2, rOuter-dR/2+dR/1e6, dR);
		Dataset xi1D = DoubleDataset.createRange(xiMin+dXi/2, xiMax-dXi/2+dXi/1e6, dXi);
		
		// Expand the one dimensional coordinates to a two dimensional grid
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
		
		// Create a mask of the illuminated atoms in the cyclinder.
		// TODO: There has to be a better way to make a mask Dataset
		Dataset illuminationPlate = DoubleDataset.ones(xPlate);
		for (int i=0; i<xPlate.getShape()[0]; i++){
			for (int k=0; k<xPlate.getShape()[1]; k++) {
				if (Math.abs(xPlate.getDouble(i, k)) > beamData.getBeamHeight()/2)
					illuminationPlate.set(0.0, i, k);
			}
		}
		
		Dataset illuminatedVolume = Maths.multiply(illuminationPlate, Maths.multiply(dR*dXi, rCylinder));
		
		// The upstream path length for each point is independent of scattering
		// angle.
		Dataset upstreamPathLength;
		Dataset downstreamPathLength;
		if (doUpstreamAbsorption) {
			upstreamPathLength = attenuatorGeometry.getUpstreamPathLength(xPlate, yPlate, zPlate);
		} else {
			upstreamPathLength = DoubleDataset.zeros(xPlate);
		}
		
		// For every direction, get the per-atom absorption of the radiation
		// scattered by this object, as attenuated by the attenuating object 
		// alone.
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

	/**
	 * For a circle, returns the chord distance from the -z boundary along the
	 * line that passes p from the centre of the circle.
	 * @param p
	 * 			Distance the line passes from the centre of the circle.
	 * @param z
	 * 			z coordinate of the desired point. 
	 * @return the Dataset of the path length for all the points provided.
	 */
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
