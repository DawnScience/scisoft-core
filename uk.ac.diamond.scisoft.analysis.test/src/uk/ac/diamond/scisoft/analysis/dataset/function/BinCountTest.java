/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;

import java.util.List;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Test;

public class BinCountTest {

	private static Dataset input = DatasetFactory.createFromObject(new int[] {2, 1, 2, 4, 6, 0});
	private static Dataset output = DatasetFactory.createFromObject(new int[] {1, 1, 2, 0, 1, 0, 1});

	@Test
	public void testStandardBinCount() {
		checkStandardBinCount(input);
		checkStandardBinCount(input.reshape(3, 2));
	}

	private void checkStandardBinCount(Dataset i) {
		Dataset c = checkBinCount(i, 0, null);
		assertDatasetEquals(output, c);

		c = checkBinCount(i, 5, null);
		assertDatasetEquals(output, c);

		c = checkBinCount(i, 10, null);
		Dataset e = output.clone();
		e.resize(10);
		assertDatasetEquals(e, c);
	}

	@Test
	public void testWeightedBinCount() {
		checkWeightedBinCount(input);
		checkWeightedBinCount(input.reshape(2, 3));
	}

	public void checkWeightedBinCount(Dataset i) {
		Dataset w = DatasetFactory.createFromObject(new int[] {1, 1, 2, 4, 1, 0});
		Dataset o = DatasetFactory.createFromObject(LongDataset.class, (Object) new int[] {0, 1, 3, 0, 4, 0, 1});
		checkWeightedBinCount(i, w.reshape(i.getShapeRef()), o);

		checkWeightedBinCount(i, Maths.multiply(w.reshape(i.getShapeRef()), 0.5), Maths.multiply(o, 0.5));
	}

	private void checkWeightedBinCount(Dataset i, Dataset w, Dataset o) {
		Dataset c = checkBinCount(i, 0, w);
		assertDatasetEquals(o, c);

		c = checkBinCount(i, 5, w);
		assertDatasetEquals(o, c);

		c = checkBinCount(i, 10, w);
		Dataset e = o.clone();
		e.resize(10);
		assertDatasetEquals(e, c);
	}

	private Dataset checkBinCount(Dataset i, int minLength, Dataset weights) {
		BinCount bc = new BinCount();

		if (minLength > 0) {
			bc.setMinimumLength(minLength);
		}
		bc.setWeights(weights);

		List<Dataset> r = bc.value(i);
		assertEquals(1, r.size());

		return r.get(0);
	}

	@Test
	public void testBadInput() {
		Dataset i = input.clone();
		i.set(-1);
		assertNull(checkBinCount(i, 0, null));

		assertNull(checkBinCount(DatasetFactory.ones(10), 0, null));

		assertNull(checkBinCount(input, 0, DatasetFactory.ones(3)));
	}
}
