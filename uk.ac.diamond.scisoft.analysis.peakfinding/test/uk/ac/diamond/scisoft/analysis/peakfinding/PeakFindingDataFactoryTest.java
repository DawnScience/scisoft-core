package uk.ac.diamond.scisoft.analysis.peakfinding;

import static org.junit.Assert.*;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PeakFindingDataFactoryTest {

	@Rule
	public ExpectedException thrower = ExpectedException.none();
	
	@Test
	public void createPSet() throws Exception {		
		String[] names = new String[]{"paramA", "paramB"};
		Boolean[] isInts = new Boolean[]{true, false};
		Number[] vals = new Number[]{12, 14.2};
		
		Map<String, IPeakFinderParameter> pSet = PeakFindingDataFactory.createParameterSet(names, isInts, vals);
		assertEquals(2, pSet.size());
	}
	
	/*
	 * The next tests check errors are thrown when nonsense is given to the 
	 * factory
	 */
	@Test
	public void createPSetDiffLengthsException() throws Exception {
		thrower.expect(Exception.class);
		thrower.expectMessage("Lengths of supplied");
		
		String[] names = new String[]{"paramA", "paramB", "boris"};
		Boolean[] isInts = new Boolean[]{true, false};
		Number[] vals = new Number[]{12, 14.2, 65, 86};
		
		PeakFindingDataFactory.createParameterSet(names, isInts, vals);
	}

}
