/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.examples.slice;

import static org.junit.Assert.fail;

import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.junit.Assert;
import org.junit.Test;

public class SliceNDTest {
	@Test
	public void testSliceND() {
		int[] step;
		int[] lstart;
		int[] lstop;
		SliceND slice;

		step = new int[] {};
		lstart = new int[] {};
		lstop = new int[] {};
		slice = new SliceND(new int[] {}, null, null, step);
		Assert.assertArrayEquals(new int[] {}, slice.getShape());

		try {
			slice = new SliceND(new int[] {1}, null, null, step);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("As expected: " + e);
		}

		try {
			slice = new SliceND(new int[] {3}, null, null, new int[1]);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("As expected: " + e);
		}

		step = new int[] {2};
		try {
			slice = new SliceND(new int[] {2, 3}, null, null, step);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("As expected: " + e);
		}

		lstart = new int[1];
		lstop = new int[1];
		slice = new SliceND(new int[] {7}, null, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = 0;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = 3;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {2}, slice.getShape());
		Assert.assertArrayEquals(new int[] {3}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = -4;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {2}, slice.getShape());
		Assert.assertArrayEquals(new int[] {3}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = -8;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = 7;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {7}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstart[0] = 8;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {7}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstop[0] = 7;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());

		lstop[0] = -3;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {2}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {4}, slice.getStop());

		lstop[0] = 0;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {0}, slice.getStop());

		lstop[0] = -6;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {1}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {1}, slice.getStop());

		lstop[0] = -8;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {0}, slice.getStop());

		lstop[0] = 9;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {7}, slice.getStop());


		step = new int[] {-2};
		try {
			slice = new SliceND(new int[] {2, 3}, null, null, step);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("As expected: " + e);
		}

		slice = new SliceND(new int[] {7}, null, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = 0;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {1}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = 3;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {2}, slice.getShape());
		Assert.assertArrayEquals(new int[] {3}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = -4;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {2}, slice.getShape());
		Assert.assertArrayEquals(new int[] {3}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = -8;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = -7;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {1}, slice.getShape());
		Assert.assertArrayEquals(new int[] {0}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = 7;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstart[0] = 8;
		slice = new SliceND(new int[] {7}, lstart, null, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());


		lstop[0] = 0;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {3}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {0}, slice.getStop());

		lstop[0] = 1;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {3}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {1}, slice.getStop());

		lstop[0] = -1;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {6}, slice.getStop());

		lstop[0] = -2;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {1}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {5}, slice.getStop());

		lstop[0] = -3;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {1}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {4}, slice.getStop());

		lstop[0] = -8;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

		lstop[0] = -6;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {3}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {1}, slice.getStop());

		lstop[0] = 8;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {0}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {6}, slice.getStop());

		lstop[0] = -8;
		slice = new SliceND(new int[] {7}, null, lstop, step);
		Assert.assertArrayEquals(new int[] {4}, slice.getShape());
		Assert.assertArrayEquals(new int[] {6}, slice.getStart());
		Assert.assertArrayEquals(new int[] {-1}, slice.getStop());

	}
}
