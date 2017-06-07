package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.AbstractPowderIndexerProcess;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;

public class AutoIndexerProcessTest {
	
	public class DummyExecutableIndexer  extends AbstractPowderIndexerProcess {

		protected final Logger logger = LoggerFactory.getLogger(DummyExecutableIndexer.class); 
		
		public DummyExecutableIndexer() {
			binName = "testAsynRead.sh"; 

			URL url = getClass().getResource(binName);
			File f = new File(url.getPath());
			String absPath = f.getAbsolutePath().toString();
			String dirPath = absPath.substring(0, absPath.length() - binName.length());

			indexerStore = dirPath;
			outFileTitle = "tmpDummyIndexer";
		}

		@Override
		public void commsSpecificIndexer(BufferedWriter bw, String path) {
			//Not Appplicable
		}

		@Override
		public void generateIndexFile(String fullPath) {
			//Not Appplicable
		}

		@Override
		public List<CellParameter> extractResults(String resultFilePath) {
			//Expected empty on a call
			return this.plausibleCells;
		}

		@Override
		public String getResultsDataPath() {
			//Not Appplicable
			return null;
		}

		@Override
		public boolean isPeakDataValid(IDataset peakData) {
			//Not Appplicable
			return false;
		}

		@Override
		public Map<String, IPowderIndexerParam> getInitialParamaters() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	//This data is not set to 2 theta 
	Double[] testdata1 = { 10.7569, 9.72681, 7.22681, 7.20256, 6.62982, 6.62892, 6.48072, 6.47279, 5.37845, 4.99818,
			4.99703, 4.9365, 4.92599, 4.86341, 4.83522, 4.83123, 4.71355, 4.70008, 4.66244, 4.65622 };

	private DummyExecutableIndexer processIndexer;
	
	@Before 
	public void setUp() {
		processIndexer = new DummyExecutableIndexer();
	}
	
	@Test 
	public void configureTest() {
		//TODO:
		Assert.fail();
	}
	
	@Test 
	public void stepIndexerTest() {
		//TODO:
		Assert.fail();
	}
	
	@Test
	public void stopIndexerTest() {
		DummyExecutableIndexer indexer = new DummyExecutableIndexer();
		
		indexer.configureIndexer();
		indexer.runIndexer();
		assertTrue(indexer.getStatus() != null);
		indexer.stopIndexer();
		assertTrue(indexer.getStatus() == null);
	}
	
//	@Test //(timeout=7000) //Slighty arbitary number... IT IS NOT! its the exact time of the executable for testAsynRead + a degree of process time by a gneerous second...
//	public void asynchonousStepCallTest() {
//		DummyExecutableIndexer indexer = new DummyExecutableIndexer();
//		
//		DoubleDataset data = (DoubleDataset) DatasetFactory.createFromObject(testdata1);
//		
//		indexer.setPeakData(data);
//		
//		indexer.configureIndexer();
//		
//		indexer.runIndexer();
//		
//		//indexer.checkifreadlineisnotpossible
//		String status = null;
//		
//		Double accurayError = 4000.0; //need a better wya to determine error range
//		
//		StopWatch timer = new StopWatch();//TODO: maybe just setup using junit testing stopwatch rather than improting apache commons method
//		
//		while (status == null){
//			status = indexer.getStatus();
//		}
//
//		//Waiting for ability to read
//		assertEquals("Hello in 2", status);
//		status = null;
//		
//		timer.start();
//		while (status == null){
//			status = indexer.getStatus();
//		}
//		assertEquals(2000, timer.getTime(), accurayError);
//		timer.stop();
//		timer.reset();
//		
//		assertEquals("Hello in 4", status);
//		status = null;
//	
//		timer.start();
//		while (status == null){
//			status = indexer.getStatus();			
//		}
//		assertEquals(4000, timer.getTime(), accurayError);
//		timer.stop();
//		timer.reset();
//		
//		assertEquals("Hello That is all", status);
//	}
}
