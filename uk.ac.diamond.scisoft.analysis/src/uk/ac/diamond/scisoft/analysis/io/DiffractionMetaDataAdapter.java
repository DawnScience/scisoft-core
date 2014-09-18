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
import uk.ac.diamond.scisoft.analysis.metadata.IDiffractionMetadata;

/**
 * <b>Do not use</b> this where metadata can be accessible from Jython because the anonymous class adapter pattern
 * is generally not serializable (unless the host class is serializable and has a null constructor)
 */
public class DiffractionMetaDataAdapter extends ExtendedMetadataAdapter implements IDiffractionMetadata {

	private DiffractionCrystalEnvironment originalEnv,  clonedEnv;
	private DetectorProperties            originalProp, clonedProp;
	
	public DiffractionMetaDataAdapter() {
		
	}

	public DiffractionMetaDataAdapter(File f) {
		super(f);
	}

	@Override
	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
		return clonedEnv;
	}

	@Override
	public DetectorProperties getDetector2DProperties() {
		return clonedProp;
	}
	
	@Override
	public IDiffractionMetadata clone(){
		DiffractionMetaDataAdapter ad = new DiffractionMetaDataAdapter();
		if (getDetector2DProperties()!=null)         ad.clonedProp = getDetector2DProperties().clone();
		if (getOriginalDetector2DProperties()!=null) ad.originalProp = getOriginalDetector2DProperties();
		
		if (getDiffractionCrystalEnvironment()!=null)ad.clonedEnv = getDiffractionCrystalEnvironment().clone();
		if (getOriginalDiffractionCrystalEnvironment()!=null)ad.originalEnv = getOriginalDiffractionCrystalEnvironment();
	    return ad;
	}
	
	@Override
	public DetectorProperties getOriginalDetector2DProperties() {
		return originalProp;
	}
	
	@Override
	public DiffractionCrystalEnvironment getOriginalDiffractionCrystalEnvironment() {
		return originalEnv;
	}
}
