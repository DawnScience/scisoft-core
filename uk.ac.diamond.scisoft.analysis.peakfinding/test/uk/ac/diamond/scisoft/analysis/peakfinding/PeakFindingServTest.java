package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

import org.junit.Test;

public class PeakFindingServTest {
	
	/**
	 * This has been heavily based on OperationsTest (u.a.d.s.a.processing.test).
	 * The role of this class is to test with JUnit, therefore not using extension points
	 */
	
	private static IPeakFindingService peakFindServ;
	
	@BeforeClass
	public static void setupNonOSGiService() throws Exception {
		peakFindServ = (IPeakFindingService)Activator.getService(IPeakFindingService.class);
		
		//Grab the all the PeakFinders in u.a.d.s.a.peakfinding.peakfinders
		peakFindServ.addPeakFindersByClass(peakFindServ.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.peakFinding.peakFinders");
		
	}
	
	@Test
	public void testGetService() {
		assertNotNull(peakFindServ);
	}
	
	@Test
	public void testServiceHasPeakFinders() {
		final Collection<String> peakFinderNames = peakFindServ.getPeakFinderNames();
		assertNotNull(peakFinderNames);
		assertTrue(peakFinderNames.isEmpty());
	}

}
