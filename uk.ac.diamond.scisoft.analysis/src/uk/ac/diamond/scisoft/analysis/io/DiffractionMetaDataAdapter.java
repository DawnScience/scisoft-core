/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.io;


import java.io.File;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;

public class DiffractionMetaDataAdapter extends ExtendedMetadataAdapter implements IDiffractionMetadata {

	
	public DiffractionMetaDataAdapter() {
		
	}

	public DiffractionMetaDataAdapter(File f) {
		super(f);
	}

	@Override
	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
		return null;
	}

	@Override
	public DetectorProperties getDetector2DProperties() {
		return null;
	}
	
	@Override
	public DiffractionMetaDataAdapter clone(){
		return null;
	}

	@Override
	public DetectorProperties getOriginalDetector2DProperties() {
		return null;
	}
	
	@Override
	public DiffractionCrystalEnvironment getOriginalDiffractionCrystalEnvironment() {
		return null;
	}
}
