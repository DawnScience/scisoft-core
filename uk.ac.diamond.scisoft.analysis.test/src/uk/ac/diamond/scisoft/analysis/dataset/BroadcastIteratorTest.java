/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import org.junit.Assert;
import org.junit.Test;

public class BroadcastIteratorTest {

	@Test
	public void testBroadcastShape() {
		AbstractDataset a;
		a = DoubleDataset.ones();
		checkBroadcastShape(a, "scalar as scalar", new int[0], new int[0]);
		checkBroadcastShape(a, "scalar as [1]", new int[] {1}, new int[] {1}, 1);
		checkBroadcastShape(a, "scalar as [3]", new int[] {1}, new int[] {3}, 3);
		checkBroadcastShape(a, "scalar as [3,4]", new int[] {1,1}, new int[] {3,4}, 3, 4);

		a = DoubleDataset.ones(1);
		checkBroadcastShape(a, "[1] as scalar", new int[] {1}, new int[] {});
		checkBroadcastShape(a, "[1] as [1]", new int[] {1}, new int[] {1}, 1);
		checkBroadcastShape(a, "[1] as [3]", new int[] {1}, new int[] {3}, 3);
		checkBroadcastShape(a, "[1] as [3,4]", new int[] {1,1}, new int[] {3,4}, 3, 4);

		a = DoubleDataset.ones(1,1);
		checkBroadcastShape(a, "[1,1] as scalar", new int[] {1,1}, new int[] {});
		checkBroadcastShape(a, "[1,1] as [1]", new int[] {1,1}, new int[] {1,1}, 1);
		checkBroadcastShape(a, "[1,1] as [3]", new int[] {1,1}, new int[] {1,3}, 3);
		checkBroadcastShape(a, "[1,1] as [1,3]", new int[] {1,1}, new int[] {1,3}, 1, 3);
		checkBroadcastShape(a, "[1,1] as [3,4]", new int[] {1,1}, new int[] {3,4}, 3, 4);

		a = DoubleDataset.ones(3);
		checkBroadcastShape(a, "[3] as scalar", null, null);
		checkBroadcastShape(a, "[3] as [1]", new int[] {3}, new int[] {1}, 1);
		checkBroadcastShape(a, "[3] as [3]", new int[] {3}, new int[] {3}, 3);
		checkBroadcastShape(a, "[3] as [1,3]", new int[] {1,3}, new int[] {1,3}, 1, 3);
		checkBroadcastShape(a, "[3] as [3,4]", null, null, 3, 4);

		a = DoubleDataset.ones(3,1);
		checkBroadcastShape(a, "[3,1] as scalar", null, null);
		checkBroadcastShape(a, "[3,1] as [1]", new int[] {3,1}, new int[] {1,1}, 1);
		checkBroadcastShape(a, "[3,1] as [3]", new int[] {3,1}, new int[] {1,3}, 3);
		checkBroadcastShape(a, "[3,1] as [1,3]", new int[] {3,1}, new int[] {1,3}, 1, 3);
		checkBroadcastShape(a, "[3,1] as [3,4]", new int[] {3,1}, new int[] {3,4}, 3, 4);
		checkBroadcastShape(a, "[3,1] as [6,3,4]", new int[] {1,3,1}, new int[] {6,3,4}, 6, 3, 4);
		checkBroadcastShape(a, "[3,1] as [3,4,6]", null, null, 3, 4, 6);

		a = DoubleDataset.ones(1,3);
		checkBroadcastShape(a, "[1,3] as scalar", null, null);
		checkBroadcastShape(a, "[1,3] as [1]", new int[] {1,3}, new int[] {1,1}, 1);
		checkBroadcastShape(a, "[1,3] as [3]", new int[] {1,3}, new int[] {1,3}, 3);
		checkBroadcastShape(a, "[1,3] as [1,3]", new int[] {1,3}, new int[] {1,3}, 1, 3);
		checkBroadcastShape(a, "[1,3] as [3,4]", null, null, 3, 4);
		checkBroadcastShape(a, "[1,3] as [4,3]", new int[] {1,3}, new int[] {4,3}, 4,3);
		checkBroadcastShape(a, "[1,3] as [6,4,3]", new int[] {1,1,3}, new int[] {6,4,3}, 6, 4, 3);
		checkBroadcastShape(a, "[1,3] as [3,4,6]", null, null, 3, 4, 6);
	}

	private void checkBroadcastShape(Dataset a, String msg, int[] bShape, int[] cShape, int... newShape) {
		int[][] answer = bShape == null && cShape == null ? null : new int[][] { bShape, cShape };
		int[][] result = BroadcastIterator.calcBroadcastShapes(a.getShapeRef(), a.getSize(), newShape);
		Assert.assertArrayEquals("Broadcasting " + msg, answer, result);
	}

	@Test
	public void testBroadcast() {
		Dataset a = DatasetFactory.createRange(5, Dataset.FLOAT64).reshape(5, 1);
		Dataset b = DatasetFactory.createRange(2, 8, 1, Dataset.FLOAT64).reshape(1, 6);

		BroadcastIterator it = new BroadcastIterator(a, b);
		Assert.assertArrayEquals("Broadcast shape", new int[] {5, 6}, it.getShape());

		Dataset c = DatasetFactory.zeros(it.getShape(), Dataset.FLOAT64);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				it.hasNext();
				c.set(it.aValue * it.bValue, i, j);
				Assert.assertEquals(a.getDouble(i, 0), it.aValue, 1e-15);
				Assert.assertEquals(b.getDouble(0, j), it.bValue, 1e-15);
				Assert.assertEquals(c.getDouble(i, j), i*(j + 2.0), 1e-15);
			}
		}
	}
}
