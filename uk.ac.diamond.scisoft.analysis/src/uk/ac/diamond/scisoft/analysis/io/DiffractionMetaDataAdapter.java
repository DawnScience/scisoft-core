/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import java.io.File;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

/**
 * <b>Do not use</b> this where metadata can be accessible from Jython because the anonymous class adapter pattern
 * is generally not serializable (unless the host class is serializable and has a null constructor)
 */
public class DiffractionMetaDataAdapter extends ExtendedMetadataAdapter implements IDiffractionMetadata {

	private DiffractionCrystalEnvironment originalEnv,  clonedEnv;
	private DetectorProperties            originalProp, clonedProp;
	
	public DiffractionMetaDataAdapter() {
		super();
	}

	@Override
	public void initialize(String filename) {
		super.initialize(filename == null ?  null : new File(filename));
	}

	@Override
	public void initialize(String filename, DetectorProperties props, DiffractionCrystalEnvironment env) {
		initialize(filename);
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
