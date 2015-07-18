package uk.ac.diamond.scisoft.analysis.peakfinding;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Before;
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
	private IPeakFindingData peakFindData;
	private String dummyID = DummyPeakFinder.class.getName();
	
	@BeforeClass
	public static void setupNonOSGiService() throws Exception {
		peakFindServ = (IPeakFindingService)Activator.getService(IPeakFindingService.class);
		
		//Grab the all the PeakFinders in u.a.d.s.a.peakfinding.peakfinders
		peakFindServ.addPeakFindersByClass(peakFindServ.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders");
		
	}
	
	@Before
	public void createPeakFindData() {
		peakFindData = new PeakFindingData();
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
	
	/*
	 * The next tests check exceptions are thrown when interactions between 
	 * IPeakFindingData and IPeakFindingService go wrong
	 */
	//Must run before first run of setData otherwise it passes!
	@Test
	public void testDataNotSetFindPeaksException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("Data has not been initialised");
		peakFindServ.findPeaks(peakFindData);
	}
	
	/*
	 * The next tests check that the peak finders interact with the peak 
	 * finding service and the peak finding DTO correctly
	 */
	@Test
	public void testOneDummyPeakFinder() throws Exception {
		Map<Integer, Double>testData = DummyPeakFinder.getFakePeaks();
		
		peakFindData.activatePeakFinder(dummyID);
		peakFindData.setData(DatasetFactory.ones(new int[1], Dataset.INT), DatasetFactory.ones(new int[1], Dataset.INT));
		peakFindServ.findPeaks(peakFindData);
		Map<Integer, Double> peakPosnsSigs = peakFindData.getPeaks(dummyID);
		
		assertEquals(testData, peakPosnsSigs);
	}
}
