/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.stream.IntStream;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/*
 * Build from energy or wavelength, and angles, and able to return those values, or x or q.
 */
/**
 * Centralized calculation of the momentum transfer coordinate.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFCoordinates {

	private double wavelength;
	private Dataset twoTheta;
	private Dataset phi;
	private Dataset gamma;
	private Dataset delta;
	private Dataset dAngle; // d(2θ)/di for 1D; dΩ, solid angle, for 2D. 
	private Dataset q;
	private Dataset x;
	private boolean isAngleAuthorative;
	// Energy-wavelength conversion in keV Angstroms
	private static final double hckeVAA = 12.39841974;//(17)
	private Dataset sinTwoTheta, cosTwoTheta;
	
	/**
	 * Empty constructor.
	 */
	public XPDFCoordinates() {
		wavelength = 0.0;
		twoTheta = null;
		q = null;
		x = null;
		isAngleAuthorative = true;
		sinTwoTheta = null;
		cosTwoTheta = null;
	}
	
	/**
	 * Copy constructor.
	 * @param inCoords
	 * 				Coordinate set to be copied
	 */
	public XPDFCoordinates(XPDFCoordinates inCoords) {
		this.wavelength = inCoords.wavelength;
		this.twoTheta = (inCoords.twoTheta != null) ? inCoords.twoTheta.copy(DoubleDataset.class) : null;
		this.phi = (inCoords.phi != null) ? inCoords.phi.copy(DoubleDataset.class) : null;
		this.gamma = (inCoords.gamma != null) ? inCoords.gamma.copy(DoubleDataset.class) : null;
		this.delta = (inCoords.delta != null) ? inCoords.delta.copy(DoubleDataset.class) : null;
		this.q = (inCoords.q != null) ? inCoords.q.copy(DoubleDataset.class) : null;
		this.x = (inCoords.x != null) ? inCoords.x.copy(DoubleDataset.class) : null;
		this.isAngleAuthorative = inCoords.isAngleAuthorative;
		this.sinTwoTheta = null;
		this.cosTwoTheta = null;
	}
	
	/**
	 * Generates the XPDF coordinates from a input file.
	 * <p>
	 * In the 1D case, is obtains the axis metadata, and uses the
	 * {@link XPDFTrace.isAxisAngle()} information to calculate either the angle
	 * of the q dependence of the data.
	 * @param input
	 */
	public XPDFCoordinates(Dataset input) {
		XPDFMetadata theXPDFMetadata = input.getFirstMetadata(XPDFMetadata.class);
		this.wavelength = theXPDFMetadata.getBeam().getBeamWavelength();

		if (input.getShape().length == 1) {
			AxesMetadata axes = input.getFirstMetadata(AxesMetadata.class);
			try {
			if (theXPDFMetadata.getSampleTrace().isAxisAngle()) {
				this.setTwoTheta(Maths.toRadians(DatasetUtils.sliceAndConvertLazyDataset(axes.getAxis(0)[0])));
				dAngle = differentiate1DDataset(twoTheta);
				q = null;
				x = null;
			} else {
				this.setQ(DatasetUtils.sliceAndConvertLazyDataset(axes.getAxis(0)[0]));
				twoTheta = null;
			} } catch (DatasetException e) {
				throw new IllegalArgumentException("Could not get data from lazy dataset", e);
			}
		} else {
			// 2D case. The caller should check for valid metadata first
			DiffractionMetadata dMD = input.getFirstMetadata(DiffractionMetadata.class);
			DetectorProperties dP = dMD.getDetector2DProperties();
			
			Dataset localGamma = DatasetFactory.zeros(input, DoubleDataset.class);
			Dataset localDelta = localGamma.clone();
			dAngle = localGamma.clone();
			
			double pxArea = dP.getVPxSize() * dP.getHPxSize();
			
			for (int i = 0; i < input.getShape()[0]; i++) {
				for (int j = 0; j < input.getShape()[1]; j++) {
					Vector3d pixelPosition = dP.pixelPosition(i, j);
					
					double rho = Math.sqrt(Math.pow(pixelPosition.y, 2) + Math.pow(pixelPosition.z, 2));
					localGamma.set(Math.atan2(-pixelPosition.x, rho), i, j);
					localDelta.set(Math.atan2(pixelPosition.y, pixelPosition.z), i, j);
					dAngle.set(pxArea/pixelPosition.lengthSquared(), i, j); // No problem setting dAngle directly
				}
			}
			
			this.setGammaDelta(localGamma, localDelta);
			
		}
		this.sinTwoTheta = null;
		this.cosTwoTheta = null;
	}
	
	/**
	 * Set the energy of the photons.
	 * @param inEnergy
	 * 				beam photon energy in keV.
	 */
	public void setEnergy(double inEnergy) {
		this.wavelength = hckeVAA/inEnergy;
		invalidateData();
	}
	
	/**
	 * Set the wavelength of the photons.
	 * @param inLambda
	 * 				beam photon wavelength in Angstroms.
	 */
	public void setWavelength(double inLambda) {
		this.wavelength = inLambda;
		invalidateData();
	}

	/**
	 * Set the beam wavelength from a XPDFBeamData object.
	 * @param inBeam
	 * 				beam data to be used.
	 */
	public void setBeamData(XPDFBeamData inBeam) {
		this.setEnergy(inBeam.getBeamEnergy());
	}
	
	/**
	 * Sets the scattering 2θ angle.
	 * <p>
	 * Sets the polar scattering angle for both the 1D and 2D cases. 
	 * @param twoTheta
	 */
	public void setTwoTheta(Dataset twoTheta) {
		this.twoTheta = twoTheta;
		if (phi == null) this.phi = DatasetFactory.zeros(this.twoTheta, DoubleDataset.class).fill(Math.toRadians(210));
		setGammaDeltaFrom2ThetaPhi();
		this.isAngleAuthorative = true;
		invalidateData();
	}

	/**
	 * Sets the azimuthal angle around the beam.
	 * <p>
	 * Sets the azimuthal angle in the case of 2D data. When using 1D data, do
	 * not set this, setTwoTheta() will provide the correct default. The
	 * 0 angle is in the plane of the synchrotron.
	 * @param phi
	 * 			Azimuthal angle data to set
	 */
	public void setPhi(Dataset phi) {
		this.phi = phi;
		if (twoTheta != null)
			setGammaDeltaFrom2ThetaPhi();
		this.isAngleAuthorative = true;
		invalidateData();
	}
	
	/**
	 * Converts from the scattering polar coordinates to the detector angular coordinates 
	 */
	private void setGammaDeltaFrom2ThetaPhi() {
		gamma = Maths.arcsin(Maths.negative(Maths.multiply(Maths.sin(twoTheta), Maths.cos(phi))));
		delta = Maths.arctan(Maths.multiply(Maths.tan(twoTheta), Maths.sin(phi)));
		// Expand to two dimensions if twoTheta is one dimensional
		if (twoTheta.getShape().length == 1) {
			gamma.setShape(gamma.getSize(), 1);
			delta.setShape(delta.getSize(), 1);
		}
	}
	
	/**
	 * Set the total scattering angle based on horizontal and vertical scattering angles.
	 * @param gamma
	 * 			Horizontal scattering angle
	 * @param delta
	 * 			Vertical scattering angle
	 */
	public void setGammaDelta(Dataset gamma, Dataset delta) {
		this.gamma = gamma;
		this.delta = delta;
//		this.twoTheta = Maths.arccos(Maths.multiply(Maths.cos(delta), Maths.cos(gamma)));
//		this.phi = Maths.arctan2(Maths.negative(Maths.sin(this.delta)), Maths.tan(this.gamma));
		
		twoTheta = DatasetFactory.createFromObject(IntStream.range(0,gamma.getSize()).parallel().
				mapToDouble(i -> Math.acos(Math.cos(delta.getElementDoubleAbs(i)) * Math.cos(gamma.getElementDoubleAbs(i)))).toArray(), gamma.getShape());
		phi = DatasetFactory.createFromObject(IntStream.range(0,gamma.getSize()).parallel().
				mapToDouble(i -> Math.atan2(-Math.sin(delta.getElementDoubleAbs(i)), Math.tan(gamma.getElementDoubleAbs(i)))).toArray(), gamma.getShape());
		
		this.isAngleAuthorative = true;
		invalidateData();
	}
	
	/**
	 * Sets the momentum transfer Q coordinate.
	 * <p>
	 * Only to be used with 1D data. For 2D data, the angles must be set. 
	 * @param q
	 * 			Q coordinate to set
	 */
	public void setQ(Dataset q) {
		this.q = q;
		this.x = Maths.divide(this.q, 4*Math.PI);
		this.isAngleAuthorative = false;
	}
	
	/**
	 * Sets the momentum transfer X coordinate.
	 * <p>
	 * Only to be used with 1D data. For 2D data, the angles must be set. 
	 * @param x
	 * 			X coordinate to set
	 */
	public void setX(Dataset x) {
		this.x = x;
		this.q = Maths.multiply(this.x, 4*Math.PI);
		this.isAngleAuthorative = false;
	}
	
	/**
	 * Returns the total scattering angle.
	 * @return the total scattering angle in radians.
	 */
	public Dataset getTwoTheta() {
		if (this.twoTheta == null)
			this.twoTheta = Maths.multiply(2, Maths.arcsin(Maths.multiply(this.x, this.wavelength)));
		return this.twoTheta;
	}

	/**
	 * Returns the sine of the total scattering angle.
	 * @return the sine of the total scattering angle.
	 */
	public Dataset getSinTwoTheta() {
		if (sinTwoTheta == null)
			calculateSinCos();
		return sinTwoTheta;
	}
	
	/**
	 * Returns the cosine of the total scattering angle.
	 * @return the cosine of the total scattering angle.
	 */
	public Dataset getCosTwoTheta() {
		if (cosTwoTheta == null)
			calculateSinCos();
		return cosTwoTheta;
	}
	
	
	/**
	 * Returns the azimuthal scattering angle
	 * @return the azimuthal scattering angle in radians.
	 */
	public Dataset getPhi() {
		if (phi == null)
			return DatasetFactory.zeros(getTwoTheta(), DoubleDataset.class);
		else
			return phi;
	}
	
	/**
	 * Returns the gamma coordinate.
	 * <p>
	 * In the 2D case, directly returns the γ coordinate. In the 1D case with
	 * momentum transfer as the authoritative coordinate, returns 0. In either
	 * case, the returned Dataset is always two dimensional. 
	 * @return
	 * 		the γ array.
	 */
	public Dataset getGamma() {
		if (!isAngleAuthorative)
			return DatasetFactory.zeros(getDelta(), DoubleDataset.class);
		else
			return gamma;
	}
	
	/**
	 * Returns the delta coordinate.
	 * <p>
	 * In the 2D case, directly returns the δ coordinate. In the 1D case, if
	 * the momentum transfer is the authoritative coordinate, returns 2θ. In
	 * either case, the returned Dataset is always two dimensional.  
	 * @return
	 * 		the δ array. 
	 */
	public Dataset getDelta() {
		if (!isAngleAuthorative) {
			Dataset delta1D = getTwoTheta();
			return delta1D.reshape(delta1D.getSize(), 1);
		} else {
			return delta;
		}
	}
	
	/**
	 * Calculates and returns sin 2θ/λ
	 * @return the value x = sin 2θ/λ
	 */
	public Dataset getX() {
		if (this.x == null)
			this.x = Maths.divide(Maths.sin(Maths.multiply(0.5, this.twoTheta)), this.wavelength);
		return this.x;
	}
	
	/**
	 * Calculates and returns the momentum transfer, q 
	 * @return the momentum transfer of a beam photon at each detector angle. q = 4π sin 2θ/λ.
	 */
	public Dataset getQ() {
		if (this.q == null) 
			this.q = Maths.multiply(this.getX(), 4*Math.PI);
		return this.q;
	}
	
	/**
	 * Returns the energy.
	 * <p>
	 * Returns the energy as a help to functions where we have the coordinates,
	 * but do not want to pass in the beam data as well. 
	 * @return beam energy in keV.
	 */
	public double getEnergy() {
		return hckeVAA/this.wavelength;
	}

	private void invalidateData() {
		if (isAngleAuthorative) {
			q = null;
			x = null;
		} else {
			twoTheta = null;
			phi = null;
			gamma = null;
			delta = null;
			sinTwoTheta = null;
			cosTwoTheta = null;
		}
	}

	/**
	 * Returns a description of the problems preventing the generation of a valid XPDFCoordinates object.
	 * <p>
	 * Given a Dataset to check, return a description of the problems with the
	 * Dataset that would prevent it from generating a valid XPDFCoordinates
	 * object. If there are no such problems, then return a null String.  
	 * @param input
	 * 				the Dataset to check
	 * @return a String describing problems with the data for generating a valid XPDFCoordinates object.
	 */
	public static String coordinateMetadataProblems(Dataset input) {
		if (input.getShape().length == 1) {
			try {
				if (input.getMetadata(AxesMetadata.class) == null ||
					input.getMetadata(AxesMetadata.class).isEmpty() ||
					input.getMetadata(AxesMetadata.class).get(0) == null ||
					((AxesMetadata) input.getMetadata(AxesMetadata.class).get(0)).getAxis(0)[0] == null)
					return "Axis metadata not found";

			} catch (Exception e) {
				return "Error getting axis metadata";
			}
		} else {
			try { 
				if (input.getMetadata(DiffractionMetadata.class) == null ||
						input.getMetadata(DiffractionMetadata.class).isEmpty() ||
						input.getMetadata(DiffractionMetadata.class).get(0) == null ||
						((DiffractionMetadata) input.getMetadata(DiffractionMetadata.class).get(0)).getDetector2DProperties() == null)
					return "Detector calibration not found";
			} catch (Exception e) {
				return "Error getting detector calibration";
			}
		}
		return null;
	}
	
	/**
	 * Returns the coordinate increment for integration.
	 * <p>
	 * The coordinate increment in 1D is the rate of change of scattering angle
	 * with grid point d(2θ)/di. In 2D, it is the solid angle of the pixel.
	 * @return dimensionally relevant angle increment Dataset.
	 */
	public Dataset getAngleIncrement() {
		if (dAngle == null) dAngle = differentiate1DDataset(getTwoTheta());
		return dAngle;
	}
	
	/**
	 * Returns the increment for performing integrals over q.
	 * <p>
	 * Multiplying the return value of this function with the function to be
	 * integrated wrt q, and then applying the integration kernel, will give a
	 * correct integral over q in both one and two dimensions.
	 * @return
	 */
	public Dataset getQIncrement() {
		// The result is dq/dθ * dAngle in both one and two dimensions.
			return Maths.multiply(getAngleIncrement(), Maths.multiply(4*Math.PI/(2*wavelength), Maths.cos(Maths.multiply(0.5, twoTheta))));
	}
	
	/**
	 * Finite difference approximation to a 1d Dataset.
	 * <p>
	 * Second order correct finite difference approximation to the first
	 * derivative of a 1D Dataset, with respect to the index
	 * @param y
	 * 			Values of the function.
	 * @return second-order correct approximation to derivative of the function.
	 */
	private static Dataset differentiate1DDataset(Dataset y) {
		Dataset deriv = DatasetFactory.zeros(y, DoubleDataset.class);
		if (y.getSize() > 1) {
			if (y.getSize() == 2) {
				double dderiv = y.getDouble(1) - y.getDouble(0);
				deriv.set(dderiv, 0);
				deriv.set(dderiv, 1);
			} else {
				// Three points or more
				int iLast = y.getSize()-1;
				// End points
				deriv.set((-3*y.getDouble(0) + 4*y.getDouble(1) - y.getDouble(2))/2, 0);
				deriv.set((y.getDouble(iLast-2) - 4*y.getDouble(iLast-1) + 3*y.getDouble(iLast))/2, iLast);
				// The rest of the points
				for (int i = 1; i < iLast; i++)
					deriv.set((y.getDouble(i+1) - y.getDouble(i-1))/2, i);
			}
		}
		return deriv;
	}

	private void calculateSinCos() {
		this.sinTwoTheta = Maths.sin(twoTheta);
		this.cosTwoTheta = Maths.cos(twoTheta);
	}
	
}
