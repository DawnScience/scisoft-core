/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Interface for the metadata required by the XPDF processing pipeline
 */
public interface XPDFMetadata extends MetadataType {

	/**
	 * get a list of the containers in the sample, as XPDFTargetComponents
	 */
	List<XPDFTargetComponent> getContainers();

	/**
	 * reorder the containers in the metadata, according to the passed map. The key is the original ordinal, the value
	 * is the new ordinal
	 */
	void reorderContainers(Map<Integer, Integer> newOrder);
	
	/**
	 * get the sample as XPDFTargetComponent
	 */
	XPDFTargetComponent getSample();

	/**
	 * get the data on the beam as XPDFBeam
	 */
	XPDFBeamData getBeam();
}
