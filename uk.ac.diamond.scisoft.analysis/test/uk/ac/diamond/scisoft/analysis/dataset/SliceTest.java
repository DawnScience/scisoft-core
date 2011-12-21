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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SliceTest {
	
	@Test
	public void testSlice() {
		Slice sl;
		sl = new Slice();
		assertEquals(":", sl.toString());
		sl = new Slice(12);
		assertEquals(":12", sl.toString());
		sl = new Slice(1, 12);
		assertEquals("1:12", sl.toString());
		sl = new Slice(0, 12);
		assertEquals(":12", sl.toString());
		sl = new Slice(null, 12);
		assertEquals(":12", sl.toString());
		sl = new Slice(11, 12);
		assertEquals("11", sl.toString());
		sl = new Slice(1, 12, 3);
		assertEquals("1:12:3", sl.toString());
		sl = new Slice(11, 12, 3);
		assertEquals("11", sl.toString());
		sl = new Slice(0, 12, 3);
		assertEquals(":12:3", sl.toString());
		sl = new Slice(1, 12).setLength(12);
		assertEquals("1:", sl.toString());
		sl = new Slice(1, null).setLength(12);
		assertEquals("1:", sl.toString());
		sl = new Slice(0, 12).setLength(12);
		assertEquals(":", sl.toString());
		sl = new Slice(1, 12, 3).setLength(12);
		assertEquals("1::3", sl.toString());
		sl = new Slice(0, 12, 3).setLength(12);
		assertEquals("::3", sl.toString());
		sl = new Slice(null, null, 3).setLength(12);
		assertEquals("::3", sl.toString());
		sl = new Slice(11, 12);
		assertEquals("11", sl.toString());
		sl = new Slice(0, 1);
		assertEquals("0", sl.toString());
		sl = new Slice(1);
		assertEquals("0", sl.toString());
	}

	@Test
	public void testSliceSteps() {
		Slice sl;

		sl = new Slice(null, null, 3).setLength(1);
		assertEquals(1, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(2);
		assertEquals(1, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(3);
		assertEquals(1, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(4);
		assertEquals(2, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(5);
		assertEquals(2, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(6);
		assertEquals(2, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(7);
		assertEquals(3, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(8);
		assertEquals(3, sl.getNumSteps());
		sl = new Slice(null, null, 3).setLength(9);
		assertEquals(3, sl.getNumSteps());

		sl = new Slice().setLength(12);
		assertEquals(12, sl.getNumSteps());
		sl = new Slice(null, null, 2).setLength(12);
		assertEquals(6, sl.getNumSteps());
		sl = new Slice(1, null).setLength(12);
		assertEquals(11, sl.getNumSteps());
		sl = new Slice(1, null, 2).setLength(12);
		assertEquals(6, sl.getNumSteps());
		sl = new Slice(1, null, -2).setLength(12);
		assertEquals(1, sl.getNumSteps());
		sl = new Slice(null, null, -1).setLength(12);
		assertEquals(12, sl.getNumSteps());
		sl = new Slice(null, null, -2).setLength(12);
		assertEquals(6, sl.getNumSteps());

		try {
			sl = new Slice();
			sl.getNumSteps();
			fail("No exception thrown");
		} catch (IllegalStateException ise) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalStateException");
		}

		sl = new Slice(12);
		assertEquals(12, sl.getNumSteps());
		sl = new Slice(1, 12);
		assertEquals(11, sl.getNumSteps());
		sl = new Slice(12, 1);
		assertEquals(0, sl.getNumSteps());
		sl = new Slice(1, 12, 2);
		assertEquals(6, sl.getNumSteps());
		sl = new Slice(1, 12, 12);
		assertEquals(1, sl.getNumSteps());
		sl = new Slice(12, 1, -1);
		assertEquals(11, sl.getNumSteps());
		sl = new Slice(1, 12, -1);
		assertEquals(0, sl.getNumSteps());
		sl = new Slice(12, 1, -2);
		assertEquals(6, sl.getNumSteps());
		sl = new Slice(12, 1, -12);
		assertEquals(1, sl.getNumSteps());

		sl = new Slice(null, 11, 1);
		assertEquals(11, sl.getNumSteps());
		try {
			sl = new Slice(11, null, -1);
			assertEquals(10, sl.getNumSteps());
			fail("No exception thrown");
		} catch (IllegalStateException ise) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalStateException");
		}

		sl = new Slice(null, null, 15).setLength(4096);
		assertEquals(274, sl.getNumSteps());
		
	}

	@Test
	public void testGetter() {
		Slice sl;

		sl = new Slice().setLength(12);
		assertEquals(null, sl.getStart());
		assertEquals(null, sl.getStop());
		assertEquals(1, sl.getStep());
		assertEquals(12, sl.getLength());

		sl = new Slice(10).setLength(12);
		assertEquals(null, sl.getStart());
		assertEquals(10, (int) sl.getStop());
		assertEquals(1, sl.getStep());
		assertEquals(12, sl.getLength());
	}

	@Test
	public void testSetter() {
		Slice sl;

		sl = new Slice().setLength(12);
		sl.setStart(1);
		assertEquals(1, (int) sl.getStart());
		sl.setStop(10);
		assertEquals(10, (int) sl.getStop());
		sl.setStep(2);
		assertEquals(2, sl.getStep());
		sl.setLength(11);
		assertEquals(11, sl.getLength());

		sl = new Slice().setLength(12);
		sl.setStart(3);
		sl.setStop(10);
		try {
			sl.setLength(9);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		try {
			sl.setStart(11);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		sl = new Slice().setLength(12);
		sl.setStart(3);
		sl.setStop(10);
		try {
			sl.setLength(9);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		try {
			sl.setStart(11);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}
		try {
			sl.setStop(0);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		sl = new Slice().setLength(12);
		sl.setStep(-2);
		sl.setStart(10);
		sl.setStop(3);
		try {
			sl.setLength(9);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		try {
			sl.setStart(2);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}
		try {
			sl.setStop(10);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		try {
			sl.setStep(0);
			fail("No exception thrown");
		} catch (IllegalArgumentException iae) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalArgumentException");
		}

		sl = new Slice(10).setLength(12);
		assertEquals(null, sl.getStart());
		assertEquals(10, (int) sl.getStop());
		assertEquals(1, sl.getStep());
		assertEquals(12, sl.getLength());
	}

	@Test
	public void testSliceEnd() {
		Slice sl;

		sl = new Slice().setLength(12);
		assertEquals(11, sl.getEnd());
		sl = new Slice(null, null, 2).setLength(12);
		assertEquals(10, sl.getEnd());
		sl = new Slice(1, null).setLength(12);
		assertEquals(11, sl.getEnd());
		sl = new Slice(1, null, 2).setLength(12);
		assertEquals(11, sl.getEnd());
		sl = new Slice(1, null, -2).setLength(12);
		assertEquals(1, sl.getEnd());
		sl = new Slice(null, null, -1).setLength(12);
		assertEquals(0, sl.getEnd());
		sl = new Slice(null, null, -2).setLength(12);
		assertEquals(1, sl.getEnd());

		sl = new Slice(12);
		assertEquals(11, sl.getEnd());
		sl = new Slice(1, 12);
		assertEquals(11, sl.getEnd());
		try {
			sl = new Slice(12, 1);
			assertEquals(1, sl.getEnd());
			fail("No exception thrown");
		} catch (IllegalStateException ise) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalStateException");
		}
		sl = new Slice(1, 12, 2);
		assertEquals(11, sl.getEnd());
		sl = new Slice(1, 12, 12);
		assertEquals(1, sl.getEnd());
		sl = new Slice(12, 1, -1);
		assertEquals(2, sl.getEnd());
		sl = new Slice(12, 1, -2);
		assertEquals(2, sl.getEnd());
		sl = new Slice(12, 1, -12);
		assertEquals(12, sl.getEnd());

		sl = new Slice(null, 12, 2);
		assertEquals(10, sl.getEnd());
		try {
			sl = new Slice(null, 12, -2);
			assertEquals(10, sl.getEnd());
			fail("No exception thrown");
		} catch (IllegalStateException ise) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalStateException");
		}
		try {
			sl = new Slice(null, null, -2);
			assertEquals(10, sl.getEnd());
			fail("No exception thrown");
		} catch (IllegalStateException ise) {
			// passed
		} catch (Exception e) {
			fail("Wrong exception type passed, this should give an IllegalStateException");
		}
	}

	@Test
	public void testConverters() {
		int[] start = new int[1];
		int[] stop  = new int[1];
		int[] step  = new int[1];
		int[] shape = new int[] {12};

		Slice.convertFromSlice(new Slice[] {null}, shape, start, stop, step);
		assertEquals(0, start[0]);
		assertEquals(12, stop[0]);
		assertEquals(1, step[0]);

		Slice.convertFromSlice(new Slice[] {new Slice(1,11,2).setLength(12)}, shape, start, stop, step);
		assertEquals(1, start[0]);
		assertEquals(11, stop[0]);
		assertEquals(2, step[0]);

		Slice.convertFromSlice(new Slice[] {new Slice().setLength(12)}, shape, start, stop, step);
		assertEquals(0, start[0]);
		assertEquals(12, stop[0]);
		assertEquals(1, step[0]);

		Slice[] sl = Slice.convertToSlice(start, stop, step);
		assertEquals(1, sl.length);
		assertEquals(0, (int) sl[0].getStart());
		assertEquals(12, (int) sl[0].getStop());
		assertEquals(1, sl[0].getStep());
	}

	@Test
	public void testShifts() {
		Slice sl;
		sl = new Slice().setLength(12);
		sl.setStop(2);
		assertEquals(2, sl.getNumSteps());
		assertFalse(sl.setPosition(5));
		assertEquals(5, (int) sl.getStart());
		assertEquals(7, (int) sl.getStop());

		sl.setStart(5);
		assertEquals(5, (int) sl.getStart());

		assertTrue(sl.setPosition(11));
	}
}
