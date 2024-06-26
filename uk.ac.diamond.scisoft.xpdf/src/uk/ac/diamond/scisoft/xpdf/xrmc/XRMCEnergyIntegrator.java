/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.xrmc;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

import uk.ac.diamond.scisoft.xpdf.XPDFDetector;

public class XRMCEnergyIntegrator {

	private Dataset xrmcData;
	private XPDFDetector det;
	private DetectorProperties detProp;
	private Dataset energies;
	// scattering angles
	private Dataset twoTheta, phi;
	private int[] limits = null;
	
	private boolean doTransmissionCorrection = true;
	
	/**
	 * Sets the data output my XRMC
	 * @param data
	 * 			An array of data with axes of energy, x pixels, y pixels
	 */
	public void setXRMCData(Dataset data) {
		this.xrmcData = data;
		det = null;
		detProp = null;
	}
	
	/**
	 * Sets the XPDF detector information for the integration.
	 * @param det
	 * 			{@link XPDFDetector} object to be used for the integration
	 */
	public void setDetector(XPDFDetector det) {
		this.det = det;
	}

	/**
	 * Sets the values of the energy bins in the file.
	 * @param energies
	 * 				energies of the bins in the energy dimension of the data
	 */
	public void setEnergies(Dataset energies) {
		if (this.xrmcData != null && energies.getSize() != xrmcData.getShape()[0])
			throw new IllegalArgumentException("Incorrect number of energy bins.");
		
		this.energies = energies;
	}
	
	/**
	 * Changes whether to perform the energy-dependent transmission correction or not.
	 * @param doTransCorr
	 * 					if true, perform the detector transmission correction.
	 */
	public void setDoTransmissionCorrection(boolean doTransCorr) {
		this.doTransmissionCorrection = doTransCorr;
	}
	
	/**
	 * Sets the limits of the energy integration in bin index values
	 * @param limits
	 * 				limits to apply
	 */
	public void setLimits(int[] limits) {
		this.limits = limits;
	}
	
	/**
	 * Gets a two dimensional {@link Dataset} of counts for the defined detector.
	 * @return two dimensional {@link Dataset} of counts.
	 */
	public Dataset getDetectorCounts() {
		
		if (det == null || !doTransmissionCorrection)
			return sumWithLimits(xrmcData);
		else
			return integrateEnergy(correctTransmission(xrmcData));
	}
	
	/**
	 * Set the geometry of the detector relative to the sample.
	 * <p>
	 * If the geometry that is defined by this function is set, then the
	 * dataset returned by getDetectorCounts will be normalized by solid angle.
	 * @param origin
	 * 				location of the origin of the detector, relative to the
	 * 				sample. In millimetres, lateral, vertical, up-beam (+z is toward the source)
	 * @param eulerAngles
	 * 					orientation of the detector relative to x horizontal, 
	 * 					y vertical, z along the beam. Angles in radians, stored
	 * 					as pitch, yaw,  roll
	 * @param pixelSpacing
	 * 					size of the pixels in each direction. In millimetres, x, y
	 */
	public void setGeometry(Vector3d origin, Vector3d eulerAngles, Vector2d pixelSpacing, Vector3d beamUi, Vector3d beamUk, Vector3d detectorUi, Vector3d detectorUk) {
		
		// Get the image size of the xrmc data
		int[] shape = xrmcData.getShape();
		int nx = shape[1], ny = shape[2];

		setDetectorProperties(XRMCToDetectorProperties.calculateDetectorProperties(nx, ny, origin, beamUk, eulerAngles, pixelSpacing, detectorUi, detectorUk, beamUi));		

		twoTheta = DatasetFactory.zeros(nx, ny);
		phi = DatasetFactory.zeros(nx,  ny);
		
		Vector3d beamVector = beamUk;
		Vector3d polarizationVector = beamUi;
		Vector3d normalVector = detectorUk;
		// generate the arrays of the scattering angles. Angles taken to the pixel centre.
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				Vector3d r = detProp.pixelPosition(i+0.5, j+0.5);
				double dp = beamVector.dot(r);
				double lenR = r.length();
				double twoth = Math.acos(dp/lenR);
				twoTheta.set(twoth, i, j);
				
				double fie = Math.atan2(normalVector.dot(r), polarizationVector.dot(r)); 
				phi.set(fie, i, j);
			}
		}
	}
	
	/**
	 * Sets the {@link DetectorProperties} object.
	 * @param detProp
	 * 				{@link DetectorProperties} object to set.
	 */
	public void setDetectorProperties(DetectorProperties detProp) {
		this.detProp = detProp;
	}
	
	/** Sets the XRMCDetector information.
	 *  
	 * @param xdet
	 * 			the {@link XRMCDetector} object encapsulating the
	 * 			input file used to generate the data.  
	 */	
	public void setXRMCDetector(XRMCDetector xdet, XRMCSource xsrc) {
		// Create the range of energies from the detector properties
		int nEnergies = xdet.getNBins();
		double minEnergy = xdet.getEmin();
		double maxEnergy = xdet.getEmax();
		double dE = (maxEnergy - minEnergy)/nEnergies;
		this.setEnergies(DatasetFactory.createRange(minEnergy + dE/2, maxEnergy + dE/2, dE));

		// set the geometry of the detector, scaling the pixel size from μm to mm
		
		Vector3d originXRMC = xdet.labFromPixel(new Vector2d(0, 0)); // top left of the top left pixel: DetectorProperties origin, in XRMC lab frame
		Vector3d eulerXYZ = new Vector3d(xdet.getEulerAngles());
		Vector2d pixelSizeDataset = new Vector2d(xdet.getPixelSize());
		pixelSizeDataset.scale(1e-3);
		
		Vector3d beamUi = new Vector3d(xsrc.getUI());
		Vector3d beamUk = new Vector3d(xsrc.getUK());
		
		Vector3d detUi = new Vector3d(xdet.getDetectorXVector());
		Vector3d detUk = new Vector3d(xdet.getDetectorNormal());
		
		this.setGeometry(originXRMC, eulerXYZ, pixelSizeDataset, beamUi, beamUk, detUi, detUk);
	}

	
	private Dataset correctTransmission(Dataset energyResolved) {
		
		if (this.energies == null)
			throw new IllegalStateException("XRMCEnergyIntegrator: energy levels not set before correcting transmission");
		
		// Get the image size of the energy resolved data
		int[] shape = energyResolved.getShape();
		int nx = shape[1], ny = shape[2];
		
		
		for (int iEnergy = 0; iEnergy < this.energies.getSize(); iEnergy++) {
			Dataset detectorEfficiency = this.det.getTransmissionCorrection(twoTheta, energies.getDouble(iEnergy));
			Dataset dataAtEnergy = energyResolved.getSliceView(new int[]{iEnergy,  0,  0}, new int[] {iEnergy+1, nx, ny}, new int[]{1, 1, 1});
			dataAtEnergy.imultiply(detectorEfficiency); // In-place correction
		}
		return energyResolved;
	}
	
	private Dataset integrateEnergy(Dataset energyResolved) {
		return sumWithLimits(energyResolved); // no dE term, the values are photons per bin, with no per unit of bandwidth term.
	}
	
	private Dataset sumWithLimits(Dataset summand) {
		if (this.limits == null) {
			return summand.sum(0);
		} else {
			int[] shape = summand.getShape();
			return summand.getSliceView(new int[] {this.limits[0], 0, 0}, new int[] {this.limits[1], shape[1], shape[2]}, new int[] {1, 1, 1}).sum(0);
		}
	}
}
