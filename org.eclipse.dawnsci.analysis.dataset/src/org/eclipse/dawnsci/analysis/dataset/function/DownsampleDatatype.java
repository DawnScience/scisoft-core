/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.dataset.function;

import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;

/**
 * Enumerations for down-sample datatypes
 */
public enum DownsampleDatatype {
	BOOL(BooleanDataset.class),
	INTEGER(IntegerDataset.class),
	INTEGER8(ByteDataset.class),
	INTEGER16(ShortDataset.class),
	INTEGER32(IntegerDataset.class),
	INTEGER64(LongDataset.class),
	FLOAT(DoubleDataset.class),
	FLOAT32(FloatDataset.class),
	FLOAT64(DoubleDataset.class),;

	Class<? extends Dataset> clazz;

	DownsampleDatatype(Class<? extends Dataset>  clazz) {
		this.clazz = clazz;
	}

	public Class<? extends Dataset> getInterface() {
		return clazz;
	}
}
