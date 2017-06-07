package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;

public class CellParametersTest {
	
	List<CellParameter> cells;
	
	@Before
	public void setUp(){
		cells = new ArrayList<CellParameter>();
	}
	
	@Test 
	public void meritSet(){
		CellParameter cell = new CellParameter();
		Double expVal = 1.0;
		cell.setFigureMerit(expVal);
		assertTrue(cell.getFigureMerit().equals(expVal));
	}
	
	@Test
	public void equalsTest(){
		
		CellParameter cell = new CellParameter();
		
		cell.setA(1);
		cell.setB(1);
		cell.setC(1);
		cell.setAl(1);
		cell.setBe(1);
		cell.setGa(1);
		
		assertTrue(cell.equals(cell));
	}
	
	@Test
	public void notEqualsTest(){
		
		CellParameter cell = new CellParameter();
		
		cell.setA(1);
		cell.setB(1);
		cell.setC(1);
		cell.setAl(1);
		cell.setBe(1);
		cell.setGa(1);
		
		CellParameter cellNot = new CellParameter();
		
		cellNot.setA(0);
		cellNot.setB(0);
		cellNot.setC(0);
		cellNot.setAl(0);
		cellNot.setBe(0);
		cellNot.setGa(0);
		
		assertFalse(cell.equals(cellNot));

	}
}
