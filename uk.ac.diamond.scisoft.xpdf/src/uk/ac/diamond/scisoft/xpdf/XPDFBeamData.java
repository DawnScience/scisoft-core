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
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved

/**
 * Beam data for the XPDFProcessor class.
 * 
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 *
 */
public class XPDFBeamData {

	double beamEnergy;
	double beamWidth;
	double beamHeight;
	XPDFBeamTrace trace;
	private static final double hckeVAA = 12.39841974;// (17)

	/**
	 * Constructor for the empty beam. No beam, no data.
	 */
	public XPDFBeamData() {
		// Zero beam data values
		this.beamEnergy = 0.0;
		this.beamHeight = 0.0;
		this.beamWidth = 0.0;
		this.trace = null;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param inBeam
	 *            object to be copied.
	 */
	public XPDFBeamData(XPDFBeamData inBeam) {
		this.beamEnergy = inBeam.beamEnergy;
		this.beamHeight = inBeam.beamHeight;
		this.beamWidth = inBeam.beamWidth;
		this.trace = (XPDFBeamTrace) inBeam.trace.clone();
	}

	/**
	 * clone. Uses the copy constructor.
	 */
	@Override
	protected XPDFBeamData clone() {
		return new XPDFBeamData(this);
	}

	/**
	 * Getter for the beam energy.
	 * 
	 * @return the previously set beam energy in keV.
	 */
	public double getBeamEnergy() {
		return beamEnergy;
	}

	/**
	 * Setter for the beam energy.
	 * 
	 * @param beamEnergy
	 *            beam energy in keV.
	 */
	public void setBeamEnergy(double beamEnergy) {
		this.beamEnergy = beamEnergy;
	}

	/**
	 * Setter for the beam energy, using the wavelength.
	 * 
	 * @param beamWavelength
	 *            beam wavelength in angstroms.
	 */
	public void setBeamWavelength(double beamWavelength) {
		this.beamEnergy = hckeVAA / beamWavelength;
	}

	/**
	 * Get the beam wavelength from the beam energy.
	 * 
	 * @return beam wavelength in angstroms.
	 */
	public double getBeamWavelength() {
		return hckeVAA / this.beamEnergy;
	}

	/**
	 * Getter for the beam width.
	 * 
	 * @return beam width in millimetres.
	 */
	public double getBeamWidth() {
		return beamWidth;
	}

	/**
	 * Setter for the beam width.
	 * 
	 * @param beamWidth
	 *            beam width in millimetres.
	 */
	public void setBeamWidth(double beamWidth) {
		this.beamWidth = beamWidth;
	}

	/**
	 * Getter for the beam height.
	 * 
	 * @return beam height in millimetres.
	 */
	public double getBeamHeight() {
		return beamHeight;
	}

	/**
	 * Setter for the beam height.
	 * 
	 * @param beamHeight
	 *            beam height in millimetres
	 */
	public void setBeamHeight(double beamHeight) {
		this.beamHeight = beamHeight;
	}

	/**
	 * Getter for the trace associated with the empty beam.
	 * 
	 * @return the XPDFBeamTrace associated with the empty beam.
	 */
	public XPDFBeamTrace getTrace() {
		return trace;
	}

	/**
	 * Set the trace associated with the empty beam.
	 * 
	 * @param trace
	 *            the XPDFBeamTrace associated with the empty beam.
	 */
	public void setTrace(XPDFBeamTrace trace) {
		this.trace = trace;
	}

	/**
	 * Return the momentum transfer parameter of this beam at the given angles.
	 * <p>
	 * Given a Dataset of angles in radians, the method returns the momentum
	 * transfer the photons of this beam undergo to scatter at this angle.
	 * 
	 * @param twoTheta
	 * 				the conventional scattering angle in radians.
	 * @return the momentum transfer of this beam at.
	 */
	// The q(2θ) calculation is in this class because of the energy dependence.
	public Dataset getQFromTwoTheta(Dataset twoTheta) {
		Dataset x = Maths.divide(Maths.sin(Maths.divide(twoTheta, 2)),
				this.getBeamWavelength());
		Dataset q = Maths.multiply(4 * Math.PI, x);
		return q;
	}

}
