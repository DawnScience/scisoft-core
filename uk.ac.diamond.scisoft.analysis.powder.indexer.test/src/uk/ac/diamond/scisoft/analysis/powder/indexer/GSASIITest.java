package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.GsasIIWrapper;
import uk.ac.diamond.scisoft.xpdf.views.CrystalSystem;

public class GSASIITest {

	//TODO: will just not work if the gsasII module is not valaible in python file. Itll just crash on attempt
	
	class GsasIITestMode extends GsasIIWrapper {
		public GsasIITestMode() {
			urlGsasIIPyServer = getClass().getResource("dummyGsasII.py");
		}
	}
	
	@Test
	public void setBravaisSearch() {
		GsasIIWrapper test = new GsasIIWrapper();

		List<Boolean> activeBravais = Arrays.asList(true, true, true, false, false, false, false, false, false, false,
				false, true, false, false);

		test.setActiveBravais(activeBravais);

		assertEquals(activeBravais, test.getActiveBravais());

		test.terminatePyServer();
	}
	
	@Test
	public void serverpyDeactiviation() {
		GsasIIWrapper test = new GsasIIWrapper();
		
		test.configureIndexer();

		assertTrue(test.getStatus() != null);
		
		test.terminatePyServer();
		
		assertTrue(test.getStatus() == null);
	}
	
	@Test
	public void extractDataTest(){
		GsasIITestMode test = new GsasIITestMode();
		
		test.configureIndexer();
		test.runIndexer();
		List<CellParameter> expectedCells = new ArrayList<CellParameter>();
		
		int cellCount = 2;
		for (int i = 1; i < cellCount; ++i){
			CellParameter cell = new CellParameter();
			cell.setCrystalSystem(new CrystalSystem());

			Double merit = 1.0;
			Double a = 2.0;
			Double b = 3.0;
			Double c = 4.0;

			Double alp = 5.0;
			Double bet = 6.0;
			Double gam = 7.0;

			cell.setFigureMerit(merit);
			cell.setUnitCellLengths(a, b, c);
			cell.setUnitCellAngles(alp, bet, gam);
			
			expectedCells.add(cell);
		}
		
		List<CellParameter> cells = test.getResultCells();

		for (int i = 0; i < expectedCells.size(); ++i){
			assertTrue(expectedCells.get(i).equals(cells.get(i)));
		}
		
	}
	
	@Test
	public void expectedResultsData1Test() {
		GsasIIWrapper test= new GsasIIWrapper();
		
		DoubleDataset data = (DoubleDataset) DatasetFactory.createFromObject(CellIndexTestData.testdata1);
		test.setPeakData(data);
		
		test.configureIndexer();
		test.runIndexer();
		
		String status = "";
		while (status != null){
			status = test.getStatus();
		}
		
		List<CellParameter> resultCells = test.getResultCells();
		
		assertTrue(resultCells.size() > 0);
	}

}
