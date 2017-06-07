package uk.ac.diamond.scisoft.analysis.powder.indexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.Ntreor;
import uk.ac.diamond.scisoft.xpdf.views.CrystalSystem;

public class NtreorTest {

	@Test
	public void testSetData() {
		Ntreor test = new Ntreor();

		Double[] signalPeakVal = { 1.0 };
		Dataset data = DatasetFactory.createFromObject(signalPeakVal);
		test.setPeakData(data);

		assertEquals(data, test.getPeakData());
	}

	@Test
	public void commSpecficIndexerTest() {
		String title = "testTitle";
		String path = System.getProperty("java.io.tmpdir");

		Ntreor test = new Ntreor();

		test.setOutFileTitle(title);

		OutputStream dummyOut = new OutputStream() {
			private StringBuilder string = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				this.string.append((char) b);
			}

			public String toString() {
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

		expectedOut += "N\n";
		expectedOut += path + "/" + title + ".dat" + "\n";
		expectedOut += path + "/" + title + ".imp" + "\n";
		expectedOut += path + "/" + title + ".con" + "\n";
		expectedOut += path + "/" + title + ".short" + "\n";
		expectedOut += "0\n";
		expectedOut += "N\n";

		assertEquals(expectedOut, outted);
	}

	@Test
	public void extractionFileProcessSingularCell() {
		Ntreor test = new Ntreor();
		test.setOutFileTitle("testNtreor");

		List<CellParameter> resultCells = test
				.extractResults("src/uk/ac/diamond/scisoft/analysis/powder/indexer/testNtreor1Cell.short");

		CellParameter cell = new CellParameter();

		CrystalSystem system = new CrystalSystem(); // TODO: need to set as  Triclinic

		// Extract crystal system indexing found
		cell.setCrystalSystem(system); // Shouldnt really be having to set this

		//// the cell A, B, C, Alpha, Beta, Gamma
		cell.setUnitCellLengths(10.27759, 11.35499, 7.35266);
		cell.setUnitCellAngles(108.67804, 108.84137, 83.88913);

		// Extract & set figure of merit
		cell.setFigureMerit(28);

		// Should have only one result
		assertEquals(1, resultCells.size());

		// Check if all values are equal...however is this assert equals gonna
		// call a equality operator on cell paramters? do cell paramter have a
		// overloaded equaltu operator...
		assertEquals(resultCells.get(0), cell);
	}

	//CHECK PATH BEFORE RUN THESE TESTS
	@Test
	public void generateIndexTest() {
		Ntreor test = new Ntreor();

		Double[] singalPeakVal = { 1.0 };

		// Set as signal value
		test.setPeakData(DatasetFactory.createFromObject(singalPeakVal));
		test.setOutFileTitle("tmpGenerateTest");

		// Index file location
		String path = System.getProperty("java.io.tmpdir") + "/" + test.getOutFileTitle() + ".dat";
		test.generateIndexFile(path);

		// Establish file stream
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			fis = new FileInputStream(path);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = null; // output

			// Check title Named correct on first line
			line = reader.readLine();
			assertEquals(test.getOutFileTitle(), line);

			// Check peaks were all correctly added
			for (Object peakVal : singalPeakVal) {
				line = reader.readLine();
				// Every line should be eqaul to the nput valuess
				assertEquals(peakVal, Double.parseDouble(line));
			}

			// Should be empty line to seperate commands
			line = reader.readLine();
			assertTrue(line.isEmpty());

			Map<String, IPowderIndexerParam> keyVals = test.getParameters();
			// Check if keyset are correctly added
			for (Object key : keyVals.keySet()) {
				line = reader.readLine();

				// line = line.split("\\s+"); //remove whitespace .. although
				// there shouldn't be any

				String[] hasKeyVal = line.split("="); // should be able to split
														// on equals

				// Expected length for each line is to be 2. This being as
				// should only have one key and one value on a line for NTreor
				// to function correctly
				assertEquals("Format of Keys assigned to values incorrect", 2, hasKeyVal.length);

				// Check has a a comma at the end on the line
				char endCh = line.charAt(line.length() - 1);
				assertEquals(',', endCh);
			}

			// Check end callers worked
			line = reader.readLine();
			assertEquals("END*", line);

			line = reader.readLine();
			assertEquals("0.00", line); 

		} catch (FileNotFoundException ex) {
			fail("File never created failure " + ex);
		} catch (IOException ex) {
			fail("File format missing values " + ex);
		}

	}
	
	@Test
	public void expectedResultsData1Test() {
		Ntreor test = new Ntreor();
		
		String outputname ="testedData1";
		
		test.setOutFileTitle(outputname);
		
		DoubleDataset data = (DoubleDataset) DatasetFactory.createFromObject(CellIndexTestData.testdata1);
		test.setPeakData((IDataset) data);
		
		test.configureIndexer();
		test.runIndexer();
		
		//TODO: wait until completion
		String status = "";
		while (status != null){
			status = test.getStatus();
		}
		
		String expectedResult = "src/uk/ac/diamond/scisoft/analysis/powder/indexer/ntreorExpectedtestData1.short";
		String actualResults = test.getResultsDataPath();
		
		try {
			assertEquals("Indexer prodcued different files", 
				    FileUtils.readFileToString(new File(expectedResult), "utf-8"), 
				    FileUtils.readFileToString(new File(actualResults), "utf-8"));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
