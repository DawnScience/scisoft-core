/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.Arrays;
import java.util.List;

import org.eclipse.january.dataset.Dataset;

/**
 * The class for cylindrical components of the experimental target. This class
 * is <code>public</code> because it needs to be visible in the
 * uk...xpdf.operations package
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 *
 */
public abstract class XPDFComponentGeometry {

	// Larger and smaller measurements of the component
	protected double rInner, rOuter;
	// Is this component up/down stream of the sample. The sample component is
	// neither upstream nor downstream of itself
	protected boolean isUpstream, isDownstream;
	
	protected int gridSize;
	
	// Euler angles of the component in radians
	protected double[] eulerAngles;

	/**
	 * Empty constructor
	 */
	public XPDFComponentGeometry() {
		this.rInner = 0.0;
		this.rOuter = 0.0;
		this.isUpstream = false;
		this.isDownstream = false;
		this.gridSize = 4096;
		this.eulerAngles = new double[] {0, 0, 0};
	}

	/**
	 * Copy constructor for general geometries.
	 * @param inGeom
	 * 				component geometry to be copied
	 */
	public XPDFComponentGeometry(XPDFComponentGeometry inGeom) {
		this.rInner = inGeom.rInner;
		this.rOuter = inGeom.rOuter;
		this.isUpstream = inGeom.isUpstream;
		this.isDownstream = inGeom.isDownstream;
		this.gridSize = inGeom.gridSize;
		this.eulerAngles = Arrays.copyOf(inGeom.eulerAngles, 3);
	}

	/**
	 * Clone method.
	 */
	@Override
	protected abstract XPDFComponentGeometry clone();

	/**
	 * Sets the sizes of the geometry.
	 * @param inIn
	 * 			smaller distance (inner radius, downstream face,...)
	 * @param inOut
	 * 			larger distance (outer radius, upstream face,...)
	 */
	public void setDistances(double inIn, double inOut) {
		this.rInner = inIn;
		this.rOuter = inOut;
	}

	/**
	 * Sets the streamality of the geometry.
	 * @param is_up
	 * 			is it present upstream?
	 * @param is_down
	 * 			is it present downstream?
	 */
	public void setStreamality(boolean is_up, boolean is_down) {
		this.isUpstream = is_up;
		this.isDownstream = is_down;
	}

	/**
	 * The name of the shape.
	 * @return the name of the shape.
	 */
	abstract public String getShape();

	/**
	 * Gets the distances of the geometry.
	 * @return the distances defining the geometry as a two element double array.
	 */
	public double[] getDistances() {
		double[] distances = new double[2];
		distances[0] = rInner;
		distances[1] = rOuter;

		return distances;
	}

	/**
	 * Gets the streamality of the geometry.
	 * @return the distances defining the geometry as a two element boolean array.
	 */
	public boolean[] getStreamality() {
		boolean[] strity = new boolean[2];
		strity[0] = isUpstream;
		strity[1] = isDownstream;

		return strity;
	}

	/**
	 * Sets the size of the absorption grid.
	 * @param gridSize
	 * 				The total number of gridpoints to use in calculating the
	 * 				absorption coefficients.				
	 */
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
	
	/**
	 *  Sets the Euler angles in radians
	 * @param eulerAngles
	 * 					Euler angles in radians ordered as pitch, yaw, roll.
	 */
	public void setEulerAngles(double[] eulerAngles) {
		this.eulerAngles = Arrays.copyOf(eulerAngles, 3);
	}
	/**
	 *  Sets the individual Euler angles in radians
	 * @param pitch	Pitch angle in radians
	 * @param yaw	Yaw angle in radians
	 * @param roll	Roll angle in radians
	 */
	public void setEulerAngles(double pitch, double yaw, double roll) {
		this.setEulerAngles(new double[] {pitch, yaw, roll});
	}
	/**
	 *  Sets the individual Euler angles in degrees of arc
	 * @param pitch Pitch angle in degrees of arc
	 * @param yaw	Yaw angle in degrees of arc
	 * @param roll  Roll angle in degrees of arc
	 */
	public void setEulerAnglesinDegrees(double pitch, double yaw, double roll) {
		this.setEulerAngles(Math.toRadians(pitch), Math.toRadians(yaw), Math.toRadians(roll));
	}
	/**
	 *  Gets the Euler angles as an array
	 * @return Euler angles as (pitch, yaw, roll) in radians.
	 */
	public double[] getEulerAngles() {
		return Arrays.copyOf(eulerAngles, 3);
	}
	/**
	 *  Gets the Euler angles as an array in degrees of arc
	 * @return Euler angles as (pitch, yaw, roll) in degrees
	 */
	public double[] getEulerAnglesinDegrees() {
		return new double[] {Math.toDegrees(eulerAngles[0]), Math.toDegrees(eulerAngles[1]), Math.toDegrees(eulerAngles[2])};
	}
	
	
	// return the volume of this component illuminated by the beam geometry
	// encapsulated in beamData
	/**
	 * Returns the volume of this geometry illuminated by the beam.
	 * <p>
	 * Returns the volume of this component illuminated by the beam geometry
	 * encapsulated in beamData.
	 * @param beamData
	 * 				the beam doing the illuminating
	 * @return the number of atoms illuminated.
	 */
	public abstract double getIlluminatedVolume(XPDFBeamData beamData);

	/*
	 * Path length of the object upstream of the sample, as measured at the
	 * point (x, y) in the beam. x is in the direction measured as beam width, y
	 * in the direction measured as beam height.
	 */
	/**
	 * Returns the path length upstream of the given points.
	 * <p>
	 * Path length of the object upstream of the sample, as measured at the
	 * point (x, y) in the beam. x is in the direction measured as beam width, y
	 * in the direction measured as beam height.
	 * @param x
	 * 			distance perpendicular to the beam, and for a cylinder to the
	 * 			cylindrical axis.
	 * @param y
	 * 			distance perpendicular to the beam and to x.
	 * @param z
	 * 			distance along the beam. z increases in the direction the beam
	 * 			propagates.
	 * @return the path length upstream of every point defined.
	 */
	public abstract Dataset getUpstreamPathLength(Dataset x, Dataset y, Dataset z);

	/**
	 * Returns the path length downstream of the given points, in the given direction.
	 * <p>
	 * Path length of the object downstream of the sample, as measured at the
	 * point x,y,z in the beam. x,y are as above, whereas z is the depth into
	 * the target assembly in the direction of the beam, with zero at the
	 * sample.
	 * <p>
	 * The path length is measured in the direction γ, δ, where γ is the
	 * vertical scattering angle and δ is the horizontal scattering angle.
	 * @param x
	 * 			distance perpendicular to the beam, and for a cylinder to the
	 * 			cylindrical axis.
	 * @param y
	 * 			distance perpendicular to the beam and to x.
	 * @param z
	 * 			distance along the beam. z increases in the direction the beam
	 * 			propagates.
	 * @return the path length downstream of every point defined.
	 */
	 public abstract Dataset getDownstreamPathLength(Dataset x, Dataset y,
	 Dataset z, double gamma, double delta);

	 /**
	  * Calculates the absorption correction map when attenuatorGeometry is attenuating.
	  * <p>
	  * Given an attenuator geometry and its attenuation coefficient (in cm²/g),
	  * as well as its existence up- and downstream, calculate the absorption
	  * map for the horizontal and vertical scattering angles given in the
	  * input datasets.
	  *	<p>
	  * The absorption is measured in the scattering direction γ, δ, where γ is the
	  * vertical scattering angle and δ is the horizontal scattering angle. These angles are provided as 2 dimensional Datasets, not necessarily forming a regular grid
	  * @param gamma
	  * 			vertical scattering angle in radians.
	  * @param delta
	  * 			horizontal scattering angle in radians.
	  * @param attenuatorGeometry
	  * 						geometry of the object doing the absorption.
	  * @param attenuationCoefficient
	  * 							attenuation coefficient of the substance in cm²/g.
	  * @param beamData
	  * 			properties of the beam.
	  * @param doUpstreamAbsorption
	  * 						perform upstream absorption for this pair of objects.
	  * @param doDownstreamAbsorption
	  * 						perform downstream absorption for this pair of objects.
	  * @return the absorption correction map.
	  */
	public abstract Dataset calculateAbsorptionCorrections(Dataset gamma, Dataset delta,
			XPDFComponentGeometry attenuatorGeometry, double attenuationCoefficient,
			XPDFBeamData beamData,
			boolean doUpstreamAbsorption, boolean doDownstreamAbsorption);

	public boolean isEqualToForAbsorption(XPDFComponentGeometry inGeometry) {
		return (inGeometry != null) &&
				rInner == inGeometry.rInner &&
				rOuter == inGeometry.rOuter &&
				isUpstream == inGeometry.isUpstream &&
				isDownstream == inGeometry.isDownstream &&
				gridSize == inGeometry.gridSize &&
				getShape() == inGeometry.getShape();
	}

	/**
	 * Calculates the fluorescence of an object.
	 * <p>
	 * Calculates the fluorescence of an object after passing through all the
	 * listed attenuators in the given directions. The attenuators also
	 * attenuate the incoming beam, which recapitulates the upstream absorption
	 * maps calculation.  
	 * @param gamma
	 * 				horizontal scattering angles in radians.
	 * @param delta
	 * 				vertical scattering angle in radians.
	 * @param attenuators
	 * 					a list of the attenuator geometries to be considered.
	 * @param attenuationsIn
	 * 						the attenuation coefficients of the attenuator
	 * 						materials at the beam energy. 
	 * @param attenuationsOut
	 * 						the attenuation coefficients of the attenuator
	 * 						materials at the fluorescence energy.
	 * @param beamData
	 * 				properties of the inbound beam.
	 * @param doIncomingAbsorption
	 * 							perform the attenuation on the beam.
	 * @param doOutgoingAbsorption
	 * 							perform the attenuation on the fluorescence.
	 * @return the fluorescence intensity as a function of angle
	 */
	public abstract Dataset calculateFluorescence(Dataset gamma, Dataset delta,
			List<XPDFComponentGeometry> attenuators,
			List<Double> attenuationsIn, List<Double> attenuationsOut,
			XPDFBeamData beamData,
			boolean doIncomingAbsorption, boolean doOutgoingAbsorption);	
	
}
