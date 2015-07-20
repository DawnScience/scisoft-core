package uk.ac.diamond.scisoft.analysis.peakfinding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.DummyPeakFinder;
import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.PeakFinderParameter;

public class PeakFindingDataTest {
	
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
	public void testActivateDeactivatePeakFinders() throws Exception {
		//Resources for test
		boolean gotDummy;
		assertFalse(peakFindData.hasActivePeakFinders());
		
		peakFindData.activatePeakFinder(dummyID);
		gotDummy = searchForPFID(dummyID);
		assertTrue(gotDummy);
		assertTrue(peakFindData.hasActivePeakFinders());
		
		peakFindData.deactivatePeakFinder(dummyID);
		gotDummy = searchForPFID(dummyID);
		assertFalse(gotDummy);
		assertFalse(peakFindData.hasActivePeakFinders());
		}
	
	private boolean searchForPFID(String pfID) {
		boolean gotPFID = false;
		Set<String> activePFs = (Set<String>) peakFindData.getActivePeakFinders();
		Iterator<String> pfIter = activePFs.iterator();
		while (pfIter.hasNext()) {
			String currID = pfIter.next();
			if (currID.equals(pfID)) gotPFID = true;
		}
		return gotPFID;
	}
	
	@Test
	public void testHasData() throws Exception {
		//No data
		assertFalse(peakFindData.hasData());
		
		//Only xData set
		peakFindData.setData(DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32),
				null);
		assertFalse(peakFindData.hasData());
		
		//yData initialised to 0 length dataset
		peakFindData.setData(DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32),
				DatasetFactory.zeros(new int[]{0}, Dataset.FLOAT32));
		assertFalse(peakFindData.hasData());
		
		//Should be correct
		peakFindData.setData(DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32),
				DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32));
		assertTrue(peakFindData.hasData());
	}
	
	@Test
	public void testGetPeakFinderParameters() throws Exception {
		peakFindData.activatePeakFinder(dummyID);
		
		//Test getting all the parameters
		Map<String, Set<IPeakFinderParameter>> allPFParams = peakFindData.getAllPFParameters();
		assertTrue(allPFParams.containsKey(dummyID));
		
		Set<IPeakFinderParameter> dummyPFParams = allPFParams.get(dummyID);	
		Iterator<IPeakFinderParameter> dummyPFParamIter = dummyPFParams.iterator();
		while (dummyPFParamIter.hasNext()) {
			IPeakFinderParameter currParam = dummyPFParamIter.next(); 
			if (currParam.getName().equals("testParamA")) {
				assertEquals(currParam.getValue(), 123.456);
			}
		}
		assertTrue(dummyPFParams.contains(new PeakFinderParameter("testParamB", false, 0)));
		
		//Test getting parameter by name
		Number namedParamValue = peakFindData.getPFParameterValueByName(dummyID, "testParamB");
		assertEquals(namedParamValue, 123);
	}
	
	@Test
	public void testSetPeakFinderParameters() throws Exception {
		peakFindData.activatePeakFinder(dummyID);
		
		//Set all the parameters at once
		Set<IPeakFinderParameter> newpfParams = new HashSet<IPeakFinderParameter>();
		Set<String> pfParamNames = peakFindData.getPFParameterNamesByPeakFinder(dummyID);
		assertEquals(pfParamNames.size(), 2);
		List<Number> pfNewParamValues = new ArrayList<Number>(Arrays.asList(654.321, 321));
		
		Iterator<String> pfParamNamesIter = pfParamNames.iterator();
		Iterator<Number> pfNewParamValuesIter = pfNewParamValues.iterator();
		while (pfParamNamesIter.hasNext()) {
			String currParamName = pfParamNamesIter.next();
			Number currParamValue = pfNewParamValuesIter.next();
			Boolean currParamIsInt = peakFindData.getPFParameterIsIntByName(dummyID, currParamName);
			newpfParams.add(new PeakFinderParameter(currParamName, currParamIsInt, currParamValue));
		}
		
		peakFindData.setPFParametersByPeakFinder(dummyID, newpfParams);
		
		Set<IPeakFinderParameter> dummyPFParams = peakFindData.getPFParametersByPeakFinder(dummyID);
		Iterator<IPeakFinderParameter> dummyPFParamsIter = dummyPFParams.iterator();
		while (dummyPFParamsIter.hasNext()) {
			IPeakFinderParameter currParam = dummyPFParamsIter.next();
			if (currParam.getName().equals("testParamA")) assertEquals(currParam.getValue(), 654.321);
			if (currParam.getName().equals("testParamB")) assertEquals(currParam.getValue(), 654.321);
		}
		
		peakFindData.setPFParameterByName(dummyID, "testParamA", 987.654);
		Double testParamAVal = (Double)peakFindData.getPFParameterValueByName(dummyID, "testParamA");
		assertEquals(testParamAVal, (Double)987.654);
	}
	
	/*
	 * The next tests check exceptions are thrown when IPeakFindingData is not 
	 * populated correctly
	 */
	@Test
	public void testActivateException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("already set active");
		peakFindData.activatePeakFinder(dummyID);
		peakFindData.activatePeakFinder(dummyID);
	}
	
	@Test
	public void testDeactivateException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("not set active");
		peakFindData.deactivatePeakFinder(dummyID);
		peakFindData.deactivatePeakFinder(dummyID);	
	}
	
	@Test
	public void testNoParametersToGetException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("No parameters recorded");
		peakFindData.getAllPFParameters();
	}
	
	@Test
	public void testNoPFForParamsException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("never been activated");
		peakFindData.getPFParametersByPeakFinder(dummyID+"badger");
	}
	
	@Test
	public void testNoParamInPeakFinderException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("No parameter name ");
		peakFindData.getPFParameterByName(dummyID, "totallyFakeParameter");
	}
	
	@Test
	public void testParameterAsDoubleException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("should be an Integer");
		peakFindData.setPFParameterByName(dummyID, "testParamB", 111.222);
	}
	
	/*
	 * The next tests check exceptions are thrown on data which changes 
	 * following a PeakFindinService.findPeaks(IPeakFindingData) run
	 */
	@Test
	public void testGetPeaksBeforeFindPeaksException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("findPeaks");
		peakFindData.getPeaks();
	}
	
	@Test
	public void testGetPeakByPeakFinderBeforeFindPeaksException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("findPeaks");
		peakFindData.getPeaks(dummyID);
	}
}
