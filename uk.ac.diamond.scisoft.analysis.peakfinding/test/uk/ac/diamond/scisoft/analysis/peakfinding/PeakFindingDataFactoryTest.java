package uk.ac.diamond.scisoft.analysis.peakfinding;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.DummyPeakFinder;
import uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders.MaximaDifference;

public class PeakFindingDataFactoryTest {
	
	private static IPeakFindingService peakFindServ;
	private String dummyID = DummyPeakFinder.class.getName();
	private String maxDiffID = MaximaDifference.class.getName();
	
	//Dummy values for creating PeakFindingData DTOs
	private static List<String> activePFs;
	private Integer nPeaks = DummyPeakFinder.getFakePeaks().size();
	private IDataset xData = DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32);
	private IDataset yData = DatasetFactory.createRange(0, 10, 1, Dataset.FLOAT32);
	

	@BeforeClass
	public static void setupNonOSGiService() throws Exception {
		peakFindServ = (IPeakFindingService)Activator.getService(IPeakFindingService.class);
		
		//Grab the all the PeakFinders in u.a.d.s.a.peakfinding.peakfinders
		peakFindServ.addPeakFindersByClass(peakFindServ.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders");
	}
	
	@Before
	public static void createVars() {
		activePFs = new ArrayList<String>();
	}
	
	@Rule
	public ExpectedException thrower = ExpectedException.none();
	
	@Test
	public void createPeakFindingData() {
		
		
		activePFs.add(dummyID);
		IPeakFindingData pfd = PeakFindingDataFactory.createPeakFindingData(peakFindServ, activePFs, nPeaks, xData, yData);
		
		assertTrue(pfd.hasActivePeakFinders());
		assertTrue(pfd.hasData());
		assertEquals(nPeaks, pfd.getNPeaks());
		assertEquals(xData, pfd.getData()[0]);
		assertEquals(yData, pfd.getData()[1]);
		
		activePFs.add(maxDiffID);
		IPeakFindingData pfd2 = PeakFindingDataFactory.createPeakFindingData(peakFindServ, activePFs, nPeaks, xData, yData);
		
		assertTrue(pfd2.hasActivePeakFinders());
		Set<String> pfd2Actives = (Set<String>) pfd2.getActivePeakFinders();
		assertTrue(pfd2Actives.contains(dummyID));
		assertTrue(pfd2Actives.contains(maxDiffID));
	}
	
	@Test
	public void createPSetFromServ() throws Exception {
		Map<String, Number> vals = new TreeMap<String, Number>();
		vals.put("testParamA", 14.2);
		vals.put("testParamB", 12);
				
		Map<String, IPeakFinderParameter> pSet = PeakFindingDataFactory.createParameterSet(peakFindServ, dummyID, vals);
		assertEquals(2, pSet.size());
		assertEquals(14.2, pSet.get("testParamA").getValue());
		assertEquals(12, pSet.get("testParamB").getValue());
	}
	
	@Test
	public void createPSetFromDTO() throws Exception {
		activePFs.add(dummyID);
		
		IPeakFindingData pfDTO = PeakFindingDataFactory.createPeakFindingData(peakFindServ, activePFs, nPeaks, xData, yData);
		
		Map<String, Number> vals = new TreeMap<String, Number>();
		vals.put("testParamA", 14.2);
		vals.put("testParamB", 12);
		Map<String, IPeakFinderParameter> pSet = PeakFindingDataFactory.createParameterSet(pfDTO, dummyID, vals);
		assertEquals(2, pSet.size());
		assertEquals(14.2, pSet.get("testParamA").getValue());
		assertEquals(12, pSet.get("testParamB").getValue());
		
	}
	


}
