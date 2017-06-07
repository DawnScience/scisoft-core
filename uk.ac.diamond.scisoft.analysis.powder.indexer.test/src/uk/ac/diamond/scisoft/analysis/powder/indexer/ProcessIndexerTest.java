package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertEquals;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.eclipse.january.dataset.IDataset;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.IPowderProcessingIndexer;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.Ntreor;


public class ProcessIndexerTest {

	public class ProcessingIndexerImpl implements IPowderProcessingIndexer{

		public List<CellParameter> runIndexing(IDataset inData) {
			//Not Applicable
			return null;
		}

		@Override
		public void commsSpecificIndexer(BufferedWriter bw, String path) {
			//Not Applicable
			
		}

		@Override
		public void generateIndexFile(String fullPath) {
			//Not Applicable
		}

		@Override
		public String getResultsDataPath() {
			//Not Applicable
			return null;
		}

		@Override
		public List<CellParameter> extractResults(String resultFilePath) {
			//Not Applicable
			return null;
		}
		
		
	};
	
	private ProcessingIndexerImpl processIndexer;
	
	@Before
	public void setUp() {
		processIndexer = new ProcessingIndexerImpl();
	}
		
	@Test
	public void extractIndexingTest() {

		//Generating Test File
		String filepath =  System.getProperty("java.io.tmpdir") + "/testExtractionIndexerProcess.dat";
		String identifer = "IMPORTANCE BELOW";
		
		int repeats = 3;
		int numInterestedLines = 2;
		
		try {
			PrintWriter writer = new PrintWriter(filepath, "UTF-8");

			for (int i = 0; i < repeats; ++i){
				writer.println(identifer);
				for (int j = 0; j < numInterestedLines; ++j){
					writer.println(Integer.toString(i) + Integer.toString(j)); 
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        //Now check extract indexing gathers this information
		Ntreor test = new Ntreor();
		List<String> extractedInfo = test.extractRawCellInfo(filepath, identifer, numInterestedLines);
		
		for (int i = 0; i < repeats; ++i){
			String interestContent = "";
			for (int j = 0; j < numInterestedLines; ++j){
				interestContent += Integer.toString(i)+Integer.toString(j);
			}
			assertEquals(interestContent, extractedInfo.get(i));
		}
	}
	
	
}
