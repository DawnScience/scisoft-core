package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.xpdf.views.XPDFPhase;
import uk.ac.diamond.scisoft.xpdf.views.XPDFPhaseForm;
import uk.ac.diamond.scisoft.xpdf.views.XPDFPhaseForm.Forms;

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
		
		cell.setUnitCellLengths(1, 1, 1);
		cell.setUnitCellAngles(1, 1, 1);
		
		assertTrue(cell.equals(cell));
	}
	
	@Test
	public void notEqualsTest(){
		
		CellParameter cell = new CellParameter();
		
		cell.setUnitCellLengths(1, 1, 1);
		cell.setUnitCellAngles(1, 1, 1);
		
		CellParameter cellNot = new CellParameter();
		
		cellNot.setUnitCellLengths(0, 0, 0);
		cellNot.setUnitCellAngles(0, 0, 0);
		
		assertFalse(cell.equals(cellNot));

	}
}
