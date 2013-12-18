/*
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;

/**
 * This interface is used to mark meta data which conforms to the diffraction meta
 * data specification. The following keys will exist in the IMetaData and must return
 * meaningful values.
 */
public interface IDiffractionMetadata extends IMetaData {

	public DetectorProperties getDetector2DProperties();
	
	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment();
	
	public DetectorProperties getOriginalDetector2DProperties();

	public DiffractionCrystalEnvironment getOriginalDiffractionCrystalEnvironment();
	
	@Override
	public IDiffractionMetadata clone();
}
