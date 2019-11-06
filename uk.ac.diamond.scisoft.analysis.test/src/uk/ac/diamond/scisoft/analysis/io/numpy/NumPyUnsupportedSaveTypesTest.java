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
import org.eclipse.january.dataset.CompoundByteDataset;
import org.eclipse.january.dataset.CompoundDoubleDataset;
import org.eclipse.january.dataset.CompoundFloatDataset;
import org.eclipse.january.dataset.CompoundIntegerDataset;
import org.eclipse.january.dataset.CompoundLongDataset;
import org.eclipse.january.dataset.CompoundShortDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.RGBDataset;
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
		params.add(new Object[] { CompoundByteDataset.class});
		params.add(new Object[] { CompoundShortDataset.class});
		params.add(new Object[] { RGBDataset.class});
		params.add(new Object[] { CompoundIntegerDataset.class});
		params.add(new Object[] { CompoundLongDataset.class});
		params.add(new Object[] { CompoundFloatDataset.class});
		params.add(new Object[] { CompoundDoubleDataset.class });
		return params;
	}

	private Class<? extends Dataset> clazz;

	public NumPyUnsupportedSaveTypesTest(Class<? extends Dataset> clazz) {
		this.clazz = clazz;
	}

	@Test(expected = ScanFileHolderException.class)
	public void testUnsupportedDataTypes() throws ScanFileHolderException, IOException {
		// We use 3 as the itemSize so that RGB data sets can be created
		NumPyTest.saveNumPyFile(DatasetFactory.zeros(3, clazz, new int[] { 1, 2, 3 }), NumPyTest.getTempFile(), false);
	}
}
