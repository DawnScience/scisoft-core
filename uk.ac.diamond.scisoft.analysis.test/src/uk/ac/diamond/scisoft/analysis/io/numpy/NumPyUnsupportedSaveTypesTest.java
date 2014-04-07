/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io.numpy;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;

/**
 * Test that unsupported data types are correctly errored on
 */
@RunWith(Parameterized.class)
public class NumPyUnsupportedSaveTypesTest {

	@Parameters
	public static Collection<Object[]> configs() {
		List<Object[]> params = new LinkedList<Object[]>();
		params.add(new Object[] { AbstractDataset.ARRAYINT8 });
		params.add(new Object[] { AbstractDataset.ARRAYINT16 });
		params.add(new Object[] { AbstractDataset.RGB });
		params.add(new Object[] { AbstractDataset.ARRAYINT32 });
		params.add(new Object[] { AbstractDataset.ARRAYINT64 });
		params.add(new Object[] { AbstractDataset.ARRAYFLOAT32 });
		params.add(new Object[] { AbstractDataset.ARRAYFLOAT64 });
		return params;
	}

	private int dtype;

	public NumPyUnsupportedSaveTypesTest(int dtype) {
		this.dtype = dtype;
	}

	@Test(expected = ScanFileHolderException.class)
	public void testUnsupportedDataTypes() throws ScanFileHolderException, IOException {
		// We use 3 as the itemSize so that RGB data sets can be created
		NumPyTest.saveNumPyFile(AbstractDataset.zeros(3, new int[] { 1, 2, 3 }, dtype), NumPyTest.getTempFile(), false);
	}
}
