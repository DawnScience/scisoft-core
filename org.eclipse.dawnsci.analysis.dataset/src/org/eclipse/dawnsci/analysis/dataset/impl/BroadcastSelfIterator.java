/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.dataset.impl;

import java.util.Arrays;

/**
 * Base class for broadcast iterators where the second dataset could be broadcast to the first and it is also read into either bLong or bDouble fields.
 * For speed, there are public members. Note, index is not updated
 */
public abstract class BroadcastSelfIterator extends BroadcastIteratorBase {

	public static BroadcastSelfIterator createIterator(Dataset a, Dataset b) {
		if (Arrays.equals(a.getShapeRef(), b.getShapeRef()) && a.getStrides() == null && b.getStrides() == null) {
			return new ContiguousSingleIterator(a, b);
		}
		return new BroadcastSingleIterator(a, b);
	}

	private static void checkItemSize(Dataset a, Dataset b) {
		final int isa = a.getElementsPerItem();
		final int isb = b.getElementsPerItem();
		if (isa != isb && isa != 1 && isb != 1) {
			// exempt single-value dataset case too
			if ((isa == 1 || b.getSize() != 1) && (isb == 1 || a.getSize() != 1) ) {
				throw new IllegalArgumentException("Can not broadcast where number of elements per item mismatch and one does not equal another");
			}
		}
	}

	protected BroadcastSelfIterator(Dataset a, Dataset b) {
		super(a, b);
		read = AbstractDataset.isDTypeNumerical(b.getDtype());
		asDouble = aDataset.hasFloatingPointElements();
		checkItemSize(a, b);
	}

	@Override
	protected void storeCurrentValues() {
		if (bIndex >= 0) {
			if (asDouble) {
				bDouble = bDataset.getElementDoubleAbs(bIndex);
			} else {
				bLong = bDataset.getElementLongAbs(bIndex);
			}
		}
	}
}
