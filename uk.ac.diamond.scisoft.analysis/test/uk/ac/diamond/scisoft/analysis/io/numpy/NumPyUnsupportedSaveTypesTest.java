/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io.numpy;

import gda.analysis.io.ScanFileHolderException;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

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
		NumPyTest.saveNumPyFile(AbstractDataset.zeros(3, new int[] { 1, 2, 3 }, dtype), NumPyTest.getTempFile());
	}
}
