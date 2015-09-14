/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

/**
 * @author Timothy Spain (rkl37156), timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 */
public class XPDFAbsorptionMaps {
	private Map<String, Dataset> theMaps;
	
	/**
	 * Create an empty Map.
	 */
	public XPDFAbsorptionMaps() {
		theMaps = new HashMap<String, Dataset>();
	}
	
	/**
	 * Copy constructor.
	 * @param inMaps object to be copied.
	 */
	public XPDFAbsorptionMaps(XPDFAbsorptionMaps inMaps) {
		if (this.theMaps == null) {
			this.theMaps = new HashMap<String, Dataset>();
		} else {
			this.theMaps.clear();
		}
		this.theMaps.putAll(inMaps.theMaps);
	}
	
	/**
	 * Set an absorption correction map in the map collection. 
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @param inMap The Dataset holding the absorption map of the pair of objects.
	 */
	public void setAbsorptionMap(int iScatterer, int iAttenuator, Dataset inMap) {
		theMaps.put(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator), inMap);
	}

	/**
	 * Get a stored absorption map.
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @return the requested Dataset if it exists, else null.
	 */
	public Dataset getAbsorptionMap(int iScatterer, int iAttenuator) {
		return theMaps.get(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator));
	}
	
	/**
	 * Convert the pairs of integers to the keying string in a consistent manner. 
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @return The encoded string.
	 */
	private static String stringifier(int iScatterer, int iAttenuator) {
		return Integer.toString(iScatterer, Character.MAX_RADIX)+" "+Integer.toString(iAttenuator, Character.MAX_RADIX);
	}
}
