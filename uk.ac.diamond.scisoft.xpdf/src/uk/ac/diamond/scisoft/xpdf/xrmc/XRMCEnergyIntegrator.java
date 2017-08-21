/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.xrmc;

import javax.vecmath.Matrix3d;
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
	/**
	 * Sets the data output my XRMC
	 * @param data
	 * 			An array of data with axes of energy, x pixels, y pixels
	 */
	public void setXRMCData(Dataset data) {
		this.xrmcData = data;
		det = null;
	}
	
	public void setDetector(XPDFDetector det) {
		this.det = det;
	}

	public void setEnergies(Dataset energies) {
		if (this.xrmcData != null && energies.getSize() != xrmcData.getShape()[0])
			throw new IllegalArgumentException("Incorrect number of energy bins.");
		
		this.energies = energies;
	}
	
	public Dataset getDetectorCounts() {
		
		if (det == null)
			return xrmcData.sum(0);
		else
			return correctGeometry(integrateEnergy(correctTransmission(xrmcData)));
	}
	
	/**
	 * Set the geometry of the detector relative to the sample.
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
	public void setGeometry(Vector3d origin, Dataset eulerAngles, Dataset pixelSpacing) {
		
		// Get the image size of the xrmc data
		int[] shape = xrmcData.getShape();
		int nx = shape[1], ny = shape[2];

		setDetectorProperties(new DetectorProperties(origin, nx, ny, pixelSpacing.getDouble(1), pixelSpacing.getDouble(0), new Matrix3d(1.,0.,0. ,0.,1.,0. ,0.,0.,1.)));
		detProp.setOrientationEulerXYZ(eulerAngles.getDouble(0), eulerAngles.getDouble(1), eulerAngles.getDouble(2));
		
		Vector3d beamVector = new Vector3d(0., 0., 1.);
		Vector3d polarizationVector = new Vector3d(1., 0., 0.);
		Vector3d normalVector = new Vector3d(0., 1., 0.);
		
		detProp.setBeamVector(beamVector);
		
		twoTheta = DatasetFactory.zeros(nx, ny);
		phi = DatasetFactory.zeros(nx,  ny);
		
		// generate the arrays of the scattering angles. Angles taken to the pixel centre.
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				Vector3d r = detProp.pixelPosition(nx+0.5, ny+0.5);
				double dp = beamVector.dot(r);
				double lenR = r.length();
				twoTheta.set(Math.acos(dp/lenR), i, j);
				
				phi.set(Math.atan2(normalVector.dot(r), polarizationVector.dot(r)), i, j);
				
			}
		}
	}
	
	public void setDetectorProperties(DetectorProperties detProp) {
		this.detProp = detProp;
	}
	
	
	private Dataset correctTransmission(Dataset energyResolved) {
		
		if (this.energies == null)
			throw new IllegalStateException("XRMCEnergyIntegrator: energy levels not set before correcting transmission");
		
		// Get the image size of the energy resolved data
		int[] shape = energyResolved.getShape();
		int nx = shape[1], ny = shape[2];
		
		
		for (int iEnergy = 0; iEnergy < this.energies.getSize(); iEnergy++) {
			Dataset transmission = this.det.getTransmissionCorrection(twoTheta, energies.getDouble(iEnergy));
			Dataset dataAtEnergy = energyResolved.getSliceView(new int[]{iEnergy,  0,  0}, new int[] {iEnergy+1, nx, ny}, new int[]{1, 1, 1});
			dataAtEnergy.idivide(transmission); // In-place correction
		}
		return energyResolved;
	}
	
	private Dataset integrateEnergy(Dataset energyResolved) {
		return energyResolved.sum(0); // no dE term, the values are photons per bin, with no per unit of bandwidth term.
	}
	
	private Dataset correctGeometry(Dataset flux) {
		return flux;
	}
}
