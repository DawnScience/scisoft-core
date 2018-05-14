/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.metadata;

import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDetector;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSource;

public interface XRMCMetadata extends MetadataType {

	public XRMCDetector getDetector();
	
	public XRMCSpectrum getSpectrum();
	
	public XRMCSource getSource();
}
