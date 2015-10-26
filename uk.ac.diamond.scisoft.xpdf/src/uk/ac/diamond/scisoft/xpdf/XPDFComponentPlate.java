/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.List;

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
public class XPDFComponentPlate extends XPDFComponentGeometry {

	/**
	 * Empty constructor
	 */
	public XPDFComponentPlate() {
		super();
	}
	
	/**
	 * Copy constructor from another plate.
	 * @param inPlate
	 * 			plate to be copied
	 */
	public XPDFComponentPlate(XPDFComponentPlate inPlate) {
		super(inPlate);
	}
	
	/**
	 * Copy constructor from another geometric object.
	 * @param inGeom
	 * 				geometry to be copied
	 */
	public XPDFComponentPlate(XPDFComponentGeometry inGeom) {
		super(inGeom);
	}
	
	/**
	 * Clone method.
	 */
	@Override
	protected XPDFComponentGeometry clone() {
		return new XPDFComponentPlate(this);
	}

	/**
	 * Returns the shape of this plate.
	 */
	@Override
	public String getShape() {
		return "plate";
	}

	/**
	 * Calculates the illuminated volume.
	 * <p>
	 * Return the illuminated volume of this plate, given the beam data. The
	 *  beam is assumed to be centred on the plate. 
	 */
	@Override
	public double getIlluminatedVolume(XPDFBeamData beamData) {
		// Matching DK's python code, even though the dimensions are incorrect
		// TODO: Multiply by another length
		return beamData.getBeamHeight()*beamData.getBeamWidth();
	}

	/**
	 * Returns the path length upstream of the given points.
	 */
	@Override
	public Dataset getUpstreamPathLength(Dataset x, Dataset y, Dataset z) {
		// Thickness of the plate
		return Maths.multiply(DoubleDataset.ones(x), Math.abs(rInner -rOuter));
	}

	/**
	 * Returns the path length downstream of the given points.
	 */
	@Override
	public Dataset getDownstreamPathLength(Dataset x, Dataset y, Dataset z,
			double gamma, double delta) {
		// Thickness of the plate, multiplied by the secant of the scattering angles
		return Maths.multiply(DoubleDataset.ones(x), Math.abs(rInner - rOuter)/(Math.cos(gamma)*Math.cos(delta)));
	}

	/**
	 * Calculates the absorption correction map when attenuatorGeometry is attenuating.
	 */
	@Override
	public Dataset calculateAbsorptionCorrections(Dataset gamma, Dataset delta,
			XPDFComponentGeometry attenuatorGeometry, double attenuationCoefficient,
			XPDFBeamData beamData,
			boolean doUpstreamAbsorption, boolean doDownstreamAbsorption) {

		// Height of the plate that needs to be considered
		double windowHeight = Math.max(rOuter*Math.tan((double) delta.max()), beamData.getBeamHeight()/2);
		
		double thickness = rOuter - rInner;
		
		double log2XSteps = Math.round(Math.log((double) gridSize*windowHeight/thickness/2)/2/Math.log(2.0));
		int xSteps = (int)Math.round(Math.pow(2.0, log2XSteps));
		int zSteps = gridSize/xSteps;
		double dX = windowHeight/xSteps;
		double dZ = thickness/zSteps;
		
		Dataset x1D = DoubleDataset.createRange(-windowHeight+dZ/2, windowHeight-dZ/2, dZ);
		Dataset z1D = DoubleDataset.createRange(rInner+dX/2, rOuter-dX/2, dX);
		
		// Account for the streamality of the plate. Can a plate be both upstream and downstream?
		if (isUpstream && isDownstream) {
			Dataset z1DTemp = z1D;
			z1D = new DoubleDataset(2*z1D.getSize());
			for (int i = 0; i < zSteps; i++) {
				z1D.set(z1DTemp.getDouble(i), i);
				z1D.set(z1DTemp.getDouble(i), 2*zSteps-1-i);
			}
		} else if (isUpstream) {
			Maths.multiply(z1D, -1);
		} else if (isDownstream) {
			; // Do nothing, the coordinates are correct
		} else {
			// No streamality is no attenuation. Return an attenuation array of 0
			return DoubleDataset.zeros(gamma);
		}
		
		// TODO: Is this the best way to expand a Dataset?
		Dataset xPlate = new DoubleDataset(x1D.getSize(), z1D.getSize());
		Dataset zPlate = new DoubleDataset(x1D.getSize(), z1D.getSize());
		for (int i = 0; i<xSteps; i++) {
			for (int k = 0; k<zSteps; k++) {
				xPlate.set(x1D.getDouble(i), i, k);
				zPlate.set(z1D.getDouble(k), i, k);
			}
		}
		
		// TODO: There has to be a better way to make a mask Dataset
		Dataset illuminationPlate = DoubleDataset.ones(xPlate);
		for (int i=0; i<xPlate.getShape()[0]; i++){
			for (int k=0; k<zPlate.getShape()[1]; k++) {
				if (Math.abs(xPlate.getDouble(i, k)) > beamData.getBeamHeight()/2)
					illuminationPlate.set(0.0, i, k);
			}
		}
		
		Dataset illuminatedVolume = Maths.multiply(illuminationPlate, dX*dZ);
		
		Dataset yPlate = Maths.multiply(xPlate, 0.0);
		
		Dataset upstreamPathLength, downstreamPathLength;
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

	@Override
	public Dataset calculateFluorescence(Dataset gamma, Dataset delta,
			List<XPDFComponentGeometry> attenuators,
			List<Double> attenuationsIn, List<Double> attenuationsOut,
			XPDFBeamData beamData, boolean doIncomingAbsorption,
			boolean doOutgoingAbsorption) {
		// TODO Auto-generated method stub
		return null;
	}

}
