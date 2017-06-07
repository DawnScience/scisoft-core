package uk.ac.diamond.scisoft.analysis.powder.indexer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.Dicvol;
import uk.ac.diamond.scisoft.xpdf.views.CrystalSystem;

public class DicvolTest {
		
	@Test 
	public void setBravaisSearch() {
		
		Dicvol test = new Dicvol();
		
		List<Boolean> expectedBravais = Arrays.asList(true, true, true, false,true,true);
		
		test.setActiveBravais(expectedBravais.get(0), expectedBravais.get(1), expectedBravais.get(2), expectedBravais.get(3), expectedBravais.get(4), expectedBravais.get(5));
		
		List<Boolean> activeBravais = test.getActiveBravais();
		
		assertTrue(expectedBravais.equals(activeBravais));
	}
	
	@Test
	public void extractFileSingleCell() {
		Dicvol test = new Dicvol(); 
		
		List<CellParameter> resultCells = test.extractResults("src/uk/ac/diamond/scisoft/analysis/powder/indexer/testDicvol1cell");
		
		CellParameter cell = new CellParameter();
		
		CrystalSystem system = new CrystalSystem();	//TODO: need to set as Triclinic		
		
		//Extract crystal system indexing found
		cell.setCrystalSystem(system); //Shouldnt really be having to set this
		
		////the cell A, B, C, Alpha, Beta, Gamma
		cell.setUnitCellLengths(8.3039,  10.9994, 15.6097);
		cell.setUnitCellAngles(89.441 , 83.247, 85.218);
		
				 //Extract & set figure of merit
		cell.setFigureMerit(228.3);
		
		//Should have only one
		assertEquals(1, resultCells.size());
		
		//Check if all values are equal...however is this assert equals gonna call a equality operator on cell paramters? do cell paramter have a overloaded equaltu operator...
		assertEquals(resultCells.get(0), cell);
	}
	
	@Test
	public void generateIndexTest() {
		Dicvol test = new Dicvol();
		
		Double[] singalPeakVal = {1.0};
		
		//Set as sigal value
		test.setPeakData( DatasetFactory.createFromObject(singalPeakVal));
		test.setOutFileTitle("tmpGenerateTest");
		
		//Index file location
		String path = System.getProperty("java.io.tmpdir") +  "/" + test.getOutFileTitle() + ".dat";
		test.generateIndexFile(path);
		
		//Establish file stream
		FileInputStream fis = null;
	    BufferedReader reader = null;
	    
	    try {
            fis = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(fis));
            
            String line = null; 
            
            //Check title Named correct on first line
            line = reader.readLine();
            //assertEquals(test.getTitle(), line);
         
        } catch (FileNotFoundException ex) {
        	//assert here to say everything is not okay
        	fail("File never created failure " + ex); //TODO: remove to catch exceptions if want to test I expect it too
        } catch (IOException ex) {
        	fail("File format missing values " + ex);
        }
	}
	
	@Test
	public void commSpecficIndexerTest() {
		String title = "testTitle";
		String path =  System.getProperty("java.io.tmpdir");
		
		Dicvol test = new Dicvol();
		
		test.setOutFileTitle(title);
		
		OutputStream dummyOut = new OutputStream() {
			private StringBuilder string = new StringBuilder();
			
			@Override
			public void write(int b) throws IOException {
			    this.string.append((char) b );
			}
			
			public String toString(){
			    return this.string.toString();
		    }
		};
		
		DataOutputStream out = new DataOutputStream(dummyOut);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
		
		test.commsSpecificIndexer(bw, path);
		
		
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String outted = dummyOut.toString();
		
		String expectedOut = "";
		
		expectedOut += path + "/" + title + "\n";
		expectedOut += path + "/" + title + "o" + "\n";
		
		assertEquals(expectedOut, outted);
	}
	
	@Test
	public void expectedResultsData1Test() {
		Dicvol test = new Dicvol();
		
		String outputname ="tp";
		
		test.setOutFileTitle(outputname);
		
		DoubleDataset data = (DoubleDataset) DatasetFactory.createFromObject(CellIndexTestData.testdata1);
		test.setPeakData((IDataset) data);
		
		test.configureIndexer();
		test.runIndexer();
		
		String status = "";
		while (status != null){
			status = test.getStatus();
		}
		
		String expectedResult = "src/uk/ac/diamond/scisoft/analysis/powder/indexer/dicExpectData1";
		String actualResults = test.getResultsDataPath();
		
		try {
			//TODO:  --- T O T A L   CALCULATION TIME :      0.004 SEC - remvoe from tmp file before compare
			assertEquals("Indexer prodcued different files", 
				    FileUtils.readFileToString(new File(expectedResult), "utf-8"), 
				    FileUtils.readFileToString(new File(actualResults), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
	
