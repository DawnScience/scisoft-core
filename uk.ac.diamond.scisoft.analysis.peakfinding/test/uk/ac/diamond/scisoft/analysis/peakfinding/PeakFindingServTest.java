package uk.ac.diamond.scisoft.analysis.peakfinding;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.DummyPeakFinder;

public class PeakFindingServTest {
	
	/**
	 * This has been heavily based on OperationsTest (u.a.d.s.a.processing.test).
	 * The role of this class is to test with JUnit, therefore not using extension points
	 */
	
	private static IPeakFindingService peakFindServ;
	private String dummyID = DummyPeakFinder.class.getName();
	
	@BeforeClass
	public static void setupNonOSGiService() throws Exception {
		peakFindServ = (IPeakFindingService)Activator.getService(IPeakFindingService.class);
		
		//Grab the all the PeakFinders in u.a.d.s.a.peakfinding.peakfinders
		peakFindServ.addPeakFindersByClass(peakFindServ.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders");
		
	}
	
	@Rule
	public ExpectedException thrower = ExpectedException.none();
	
	@Test
	public void testGetService() {
		assertNotNull(peakFindServ);
	}
	
	@Test
	public void testServiceHasPeakFinders() throws Exception {
		final Collection<String> peakFinderNames = peakFindServ.getRegisteredPeakFinders();
		assertNotNull(peakFinderNames);
		assertFalse(peakFinderNames.isEmpty());
	}
	
	@Test
	public void testActivatePeakFinders() throws Exception {
		//Resources for test
		boolean gotDummy;
		
		peakFindServ.activatePeakFinder(dummyID);
		gotDummy = searchForPFID(dummyID);
		assertTrue(gotDummy);
		
		peakFindServ.deactivatePeakFinder(dummyID);
		gotDummy = searchForPFID(dummyID);
		assertFalse(gotDummy);	
		}
	
	private boolean searchForPFID(String pfID) {
		boolean gotPFID = false;
		Set<String> activePFs = (Set<String>) peakFindServ.getActivePeakFinders();
		Iterator<String> pfIter = activePFs.iterator();
		while (pfIter.hasNext()) {
			String currID = pfIter.next();
			if (currID.equals(pfID)) gotPFID = true;
		}
		return gotPFID;
	}
	
	@Test
	public void testActivateException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("already set active");
		peakFindServ.activatePeakFinder(dummyID);
		peakFindServ.activatePeakFinder(dummyID);
	}
	
	@Test
	public void testDeactivateException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("not set active");
		peakFindServ.deactivatePeakFinder(dummyID);
		peakFindServ.deactivatePeakFinder(dummyID);	
	}
	
	//Must run before first run of setData otherwise it passes!
	@Test
	public void testDataNotSetFindPeaksException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("Data has not been initialised");
		peakFindServ.findPeaks();
	}
	
	//Must run before first run of findPeaks otherwise it passes!
	@Test
	public void testGetPeaksBeforeFindPeaksException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("findPeaks");
		peakFindServ.getPeaks(dummyID);
	}
	
	@Test
	public void testOneDummyPeakFinder() throws Exception {
		Map<Integer, Double>testData = new TreeMap<Integer, Double>();
		testData.put(1, 0.6);
		testData.put(2, 1.2);
		testData.put(3, 0.9);
		testData.put(5, 1.7);
		testData.put(7, 2.5);
		testData.put(11, 0.9);
		testData.put(13, 0.6);
		
		peakFindServ.activatePeakFinder(dummyID);
		peakFindServ.setData(DatasetFactory.ones(new int[1], Dataset.INT), DatasetFactory.ones(new int[1], Dataset.INT));
		peakFindServ.findPeaks();
		Map<Integer, Double> peakPosnsSigs = peakFindServ.getPeaks(dummyID);
		
		assertEquals(testData, peakPosnsSigs);
	}
}
