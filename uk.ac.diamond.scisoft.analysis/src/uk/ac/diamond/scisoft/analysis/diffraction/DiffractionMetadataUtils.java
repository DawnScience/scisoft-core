/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

public class DiffractionMetadataUtils {
	
	/**
	 * Static method to replace the values in the old DiffractionCrystalEnvironment with the new
	 */
	public static void copyNewOverOld(DiffractionCrystalEnvironment newDCE, DiffractionCrystalEnvironment oldDCE) {
		
		oldDCE.setExposureTime(newDCE.getExposureTime());
		oldDCE.setPhiRange(newDCE.getPhiRange());
		oldDCE.setPhiStart(newDCE.getPhiStart());
		oldDCE.setWavelength(newDCE.getWavelength());
	}
	
	/**
	 * Static method to replace the values in the old DetectorProperties with the new
	 * @throws IllegalArgumentException if the number of pixels in x and y between the old and new is different 
	 */
	public static void copyNewOverOld(DetectorProperties newDP, DetectorProperties oldDP) throws IllegalArgumentException {

		oldDP.setOrigin(new Vector3d(newDP.getOrigin()));
		oldDP.setBeamVector(new Vector3d(newDP.getBeamVector()));
		oldDP.setVPxSize(newDP.getVPxSize());
		oldDP.setHPxSize(newDP.getHPxSize());
		oldDP.setOrientation(newDP.getOrientation());

		if (oldDP.getPx() ==newDP.getPx() &&
				oldDP.getPy()==newDP.getPy()) {
			oldDP.setPx(newDP.getPx());
			oldDP.setPy(newDP.getPy());
		} else {
			throw new IllegalArgumentException("Incompatable detector size in metadata: All parameters replaced except number of pixels in x and y");
		}
	}
	
	/**
	 * Static method to replace the values in the DiffractionCrystalEnvironment and DetectorProperties
	 *  of an old IDiffractionmetaData with a new
	 * @throws IllegalArgumentException if the number of pixels in x and y between the old and new is different
	 */
	public static void copyNewOverOld(IDiffractionMetadata newDM, IDiffractionMetadata oldDM) throws IllegalArgumentException{
		copyNewOverOld(newDM.getDiffractionCrystalEnvironment(), oldDM.getDiffractionCrystalEnvironment());
		copyNewOverOld(newDM.getDetector2DProperties(), oldDM.getDetector2DProperties());
	}
}
