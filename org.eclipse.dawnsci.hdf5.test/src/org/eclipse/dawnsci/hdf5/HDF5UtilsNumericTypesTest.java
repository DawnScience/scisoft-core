/*-
 * Copyright 2022 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.ComplexFloatDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HDF5UtilsNumericTypesTest {

	@Parameters(name="{index}: dataset {0} should be {1}")
	public static Collection<?> numericTypes() {
		return Arrays.asList(new Object[][] {
			{"s8", ByteDataset.class},
			{"s16", ShortDataset.class},
			{"s32", IntegerDataset.class},
			{"s64", LongDataset.class},
			{"u8", ByteDataset.class},
			{"u16", ShortDataset.class},
			{"u32", IntegerDataset.class},
			{"u64", LongDataset.class},
			{"f16", FloatDataset.class}, // half aka fp16 aka binary16 (has 5 bit exponent, 11 bit significand (10 stored))
			{"bf16", FloatDataset.class}, // bfloat16 (has 8 bit exponent, 8 bit significand (7 stored))
			{"f32", FloatDataset.class},
			{"f64", DoubleDataset.class},
			{"c64", ComplexFloatDataset.class},
			{"c128", ComplexDoubleDataset.class},
		});
	}

	@Parameter(0)
	public String datasetName;

	@Parameter(1)
	public Class<? extends Dataset> datasetClass;

	private static ByteDataset expected;

	@BeforeClass
	public static void setExpected() {
		expected = DatasetFactory.createRange(ByteDataset.class, 32);
		expected.isubtract(16);
	}

	@Test
	public void testLittleEndian() throws Throwable {
		String endian = "le";
		if (InterfaceUtils.isFloating(datasetClass)) {
			checkFloats(endian);
		} else {
			checkIntegers(endian);
		}
	}

	@Test
	public void testBigEndian() throws Throwable {
		String endian = "be";
		if (InterfaceUtils.isFloating(datasetClass)) {
			checkFloats(endian);
		} else {
			checkIntegers(endian);
		}
	}

	private void checkIntegers(String endianness) throws ScanFileHolderException {
		Slice s = new Slice(1, 31, 2);
		s.setLength(32);
		Dataset d = HDF5Utils.loadDatasetWithClose("testfiles/numeric_types.h5", "integers_" + endianness + Node.SEPARATOR + datasetName,
				new int[] {s.getStart()}, new int[] {s.getNumSteps()}, new int[] {s.getStep()}, 
				-1, null, false);
		Dataset e = expected.getSliceView(s).cast(datasetClass);
		assertDatasetEquals(e, d);

		if (datasetName.startsWith("u")) {
			d = HDF5Utils.loadDatasetWithClose("testfiles/numeric_types.h5", "integers_" + endianness + Node.SEPARATOR + datasetName,
					new int[] {s.getStart()}, new int[] {s.getNumSteps()}, new int[] {s.getStep()}, 
					-1, null, true);
			assertDatasetEquals(DatasetUtils.makeUnsigned(e), d);
		}
	}

	private void checkFloats(String endianness) throws ScanFileHolderException {
		Slice s = new Slice(5, 25, 3);
		s.setLength(32);
		Dataset d = HDF5Utils.loadDatasetWithClose("testfiles/numeric_types.h5", "floats_" + endianness + Node.SEPARATOR + datasetName,
				new int[] {s.getStart()}, new int[] {s.getNumSteps()}, new int[] {s.getStep()}, 
				-1, null, false);

		Dataset e = expected.getSliceView(s).cast(datasetClass).idivide(32);
		e.set(Double.NEGATIVE_INFINITY, 1);
		e.set(Double.NaN, 3);
		assertDatasetEquals(e, d);
	}
}
