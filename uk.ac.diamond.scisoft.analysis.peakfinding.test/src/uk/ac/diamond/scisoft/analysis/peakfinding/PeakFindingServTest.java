package uk.ac.diamond.scisoft.analysis.peakfinding;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.Collection;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		peakFindData = new PeakFindingData(peakFindServ);
	}
	
	/*
	 * A couple of support methods for the 
	 */
	private void setFakeDataOnDTO() {
		peakFindData.setData(DatasetFactory.createRange(FloatDataset.class, 0, 10, 1), 
				DatasetFactory.createRange(FloatDataset.class, 0, 10, 1));
	}
	
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
	public void testGetPeakFinderParameters () throws Exception {
		Map<String, IPeakFinderParameter> pfParams = peakFindServ.getPeakFinderParameters(dummyID);
		assertEquals(2, pfParams.size());
	}
	
	/*
	 * The next tests check exceptions are thrown when interactions between 
	 * IPeakFindingData and IPeakFindingService go wrong
	 */
	@Test
	public void testDataNotSetFindPeaksException() throws Exception {
		Exception e = assertThrows(Exception.class, () -> peakFindServ.findPeaks(peakFindData));
		assertThat(e.getMessage(), containsString("No data"));
	}

	@Test
	public void testNoActivePeakFindersException() throws Exception {
		setFakeDataOnDTO();
		Exception e = assertThrows(IllegalArgumentException.class, () -> peakFindServ.findPeaks(peakFindData));
		assertThat(e.getMessage(), containsString("No peak finders"));
	}
	
	/*
	 * The next tests check that the peak finders interact with the peak 
	 * finding service and the peak finding DTO correctly
	 */
	@Test
	public void testOneDummyPeakFinder() throws Exception {
		Map<Integer, Double>testData = DummyPeakFinder.getFakePeaks();
		
		peakFindData.activatePeakFinder(dummyID);
		setFakeDataOnDTO();
		peakFindServ.findPeaks(peakFindData);
		Map<Integer, Double> peakPosnsSigs = peakFindData.getPeaks(dummyID);
		
		assertEquals(testData, peakPosnsSigs);
	}
}
