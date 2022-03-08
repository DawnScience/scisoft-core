package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PeakFinderParameterTest {
	
	@Test
	public void testEquality() throws Exception {
		PeakFinderParameter paramA = new PeakFinderParameter("Test", true, 14);
		PeakFinderParameter paramB = new PeakFinderParameter("Test", true, 14);
		PeakFinderParameter paramC = new PeakFinderParameter("TestAgain", true, 72);
		
		assertTrue(paramA.equals(paramB));
		assertTrue(paramA.hashCode() == paramB.hashCode());
		assertFalse(paramA.equals(paramC));
		assertTrue(paramA.hashCode() == paramC.hashCode());
		
		assertTrue(paramA.equals(new PeakFinderParameter("Test", false, 0)));
	}
	
	@Test
	public void testSetValue() throws Exception {
		PeakFinderParameter paramA = new PeakFinderParameter("Test", true, 14);
		PeakFinderParameter paramB = new PeakFinderParameter("Test", false, 14.);
		
		//Check that there are no restriction on isInt false parameters
		assertEquals(14., paramB.getValue());
		paramB.setValue(14.76);
		assertEquals(14.76, paramB.getValue());
		
		//Now we're expecting an exception
		Exception e = assertThrows(Exception.class, () -> paramA.setValue(14.76));
		assertThat(e.getMessage(), containsString("should be an Integer"));

	}

}
