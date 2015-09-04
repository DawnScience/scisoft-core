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

public class XPDFAbsorptionMaps {
	private Map<String, Dataset> theMaps;
	
	public XPDFAbsorptionMaps() {
		theMaps = new HashMap<String, Dataset>();
	}
	
	public XPDFAbsorptionMaps(XPDFAbsorptionMaps inMaps) {
		if (this.theMaps == null) {
			this.theMaps = new HashMap<String, Dataset>();
		} else {
			this.theMaps.clear();
		}
		this.theMaps.putAll(inMaps.theMaps);
	}
	
	public void setAbsorptionMap(int iScatterer, int iAttenuator, Dataset inMap) {
		theMaps.put(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator), inMap);
	}

	public Dataset getAbsorptionMap(int iScatterer, int iAttenuator) {
		return theMaps.get(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator));
	}
	
	private static String stringifier(int iScatterer, int iAttenuator) {
		return Integer.toString(iScatterer, Character.MAX_RADIX)+" "+Integer.toString(iAttenuator, Character.MAX_RADIX);
	}
}
