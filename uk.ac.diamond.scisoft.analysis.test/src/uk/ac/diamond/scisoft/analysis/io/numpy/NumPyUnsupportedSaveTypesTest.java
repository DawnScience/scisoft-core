/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.numpy;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test that unsupported data types are correctly errored on
 */
@RunWith(Parameterized.class)
public class NumPyUnsupportedSaveTypesTest {

	@Parameters
	public static Collection<Object[]> configs() {
		List<Object[]> params = new LinkedList<Object[]>();
		params.add(new Object[] { Dataset.ARRAYINT8 });
		params.add(new Object[] { Dataset.ARRAYINT16 });
		params.add(new Object[] { Dataset.RGB });
		params.add(new Object[] { Dataset.ARRAYINT32 });
		params.add(new Object[] { Dataset.ARRAYINT64 });
		params.add(new Object[] { Dataset.ARRAYFLOAT32 });
		params.add(new Object[] { Dataset.ARRAYFLOAT64 });
		return params;
	}

	private int dtype;

	public NumPyUnsupportedSaveTypesTest(int dtype) {
		this.dtype = dtype;
	}

	@Test(expected = ScanFileHolderException.class)
	public void testUnsupportedDataTypes() throws ScanFileHolderException, IOException {
		// We use 3 as the itemSize so that RGB data sets can be created
		NumPyTest.saveNumPyFile(DatasetFactory.zeros(3, new int[] { 1, 2, 3 }, dtype), NumPyTest.getTempFile(), false);
	}
}
