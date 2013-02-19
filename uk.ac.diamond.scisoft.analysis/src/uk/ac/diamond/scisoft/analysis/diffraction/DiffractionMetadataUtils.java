/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

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
			throw new IllegalArgumentException("Incompatable metadata");
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
