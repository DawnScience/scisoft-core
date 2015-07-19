package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PeakFinderParameterTest {
	
	@Rule
	public ExpectedException thrower = ExpectedException.none();
	
	@Test
	public void testEquality() throws Exception {
		PeakFinderParameter paramA = new PeakFinderParameter("Test", true, 14);
		PeakFinderParameter paramB = new PeakFinderParameter("Test", true, 14);
		PeakFinderParameter paramC = new PeakFinderParameter("TestAgain", true, 72);
		
		assertTrue(paramA.equals(paramB));
		assertTrue(paramA.hashCode() == paramB.hashCode());
		assertFalse(paramA.equals(paramC));
		assertTrue(paramA.hashCode() == paramC.hashCode());
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
		thrower.expect(Exception.class);
		thrower.expectMessage("should be an Integer");
		paramA.setValue(14.76);
	}

}
