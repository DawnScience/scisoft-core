/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

/**
 * Enumerations for down-sample datatypes
 */
public enum DownsampleDatatype {
	BOOL,
	INTEGER,
	INTEGER8,
	INTEGER16,
	INTEGER32,
	INTEGER64,
	FLOAT,
	FLOAT32,
	FLOAT64;

	private static Map<DownsampleDatatype, Integer> datatype;

	static {
		Map<DownsampleDatatype, Integer> aMap = new HashMap<DownsampleDatatype, Integer>();
		aMap.put(BOOL, Dataset.BOOL);
		aMap.put(INTEGER, Dataset.INT);
		aMap.put(INTEGER16, Dataset.INT16);
		aMap.put(INTEGER32, Dataset.INT32);
		aMap.put(INTEGER64, Dataset.INT64);
		aMap.put(FLOAT, Dataset.FLOAT);
		aMap.put(FLOAT32, Dataset.FLOAT32);
		aMap.put(FLOAT64, Dataset.FLOAT64);
		datatype = Collections.unmodifiableMap(aMap);
	}

	public static int getDatasetType(DownsampleDatatype type) {
		return datatype.get(type);
	}
}
