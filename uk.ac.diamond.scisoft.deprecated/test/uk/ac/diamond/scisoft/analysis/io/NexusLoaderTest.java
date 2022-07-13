/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.data.nexus.extractor.NexusExtractor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.io.IMetaLoader;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assume.assumeTrue;

/**
 */
public class NexusLoaderTest {
	
	final static String TestFileFolder = "testfiles/gda/analysis/io/NexusLoaderTest/";
	
	private static String testScratchDirectoryName = null;
	
	/**
	 * Setups of environment for the tests
	 * @param name of test
	 * 
	 * @throws Exception if the test fails
	 */
	public void setUp(String name) throws Exception {
		testScratchDirectoryName = IOTestUtils.setUpTest(NexusLoaderTest.class, name, true);
	}

	@Test
	public void testLoadDifficultHdf5File() throws Exception {
		final IDataHolder dh = LoaderFactory.getData(IOTestUtils.getGDALargeTestFilesLocation()+"/NexusUITest/ID22-ODA-MapSpectra.h5", null);
		assert dh!=null;
		
		final IDataset set = dh.getDataset("NXdata.data");
		assert set.getSize()==61*171*1699;
	}

	@Test
	public void testLoadAnotherDifficultHdf5File() throws Exception {
		final IDataHolder dh = LoaderFactory.getData(IOTestUtils.getGDALargeTestFilesLocation()+"/NexusUITest/DCT_201006-good.h5", null);
		assert dh!=null;
		
		final IDataset set = dh.getDataset("NXdata.data");
		assert set.getSize()==61*171*1699;
	}
	

	/**
	 * Utility function to skip a JUnit test if the specified condition is true.
	 * If called from a method annotated with @Test, and condition is true, the @Test method will halt and be ignored (skipped).
	 * If called from a method annotated with @Before or @BeforeClass, all @Test methods of the class are ignored (skipped).
	 * 
	 * Existing test runners (we're talking JUnit 4.5 and Ant 1.7.1, as bundled with Eclipse 3.5.1, don't have the concept of a
	 * skipped test (tests are classified as either a pass or fail). Tests that fail an assumption are reported as passed.
	 * 
	 * Internally, a failing assumption throws an AssumptionViolatedException (in JUnit 4,5; this may have changed in later releases).
	 * 
	 * @param condition - boolean specifying whether the test method or test class is to be skipped
	 * @param reason - explanation of why the test is skipped
	 */
	private static void skipTestIf(boolean condition, String reason) {
		if (condition) {
			System.out.println("JUnit test skipped: " + reason);
			assumeTrue(false);
		}
	}
	
	private void makeTestFile(String fileName, int[] dims) throws NexusException {
		NexusFile file = NexusFileHDF5.createNexusFile(fileName);
		GroupNode g = file.getGroup("/dummy:dummy", true);
		{
			int totalLength = NexusExtractor.calcTotalLength(dims);
			Dataset data = DatasetFactory.createRange(totalLength).reshape(dims);
			data.setName("heading1");
			file.createData(g, data);
		}

		g = file.getGroup("/ScanFileHolder:NXentry/datasets:NXdata", true);
		{
			int totalLength = NexusExtractor.calcTotalLength(dims);
			Dataset data = DatasetFactory.createRange(totalLength).reshape(dims);
			data.setName("heading1");
			file.createData(g, data);
		}
		{
			int totalLength = NexusExtractor.calcTotalLength(dims);
			Dataset data = DatasetFactory.createRange(totalLength).reshape(dims);
			data.imultiply(2);
			data.setName("heading2");
			file.createData(g, data);
		}
		file.close();
	}

	/**
	 *  test of non-regular expressions in the selection tree
	 *  
	 * @throws Exception if the test fails
	 */
	@Test
	public void testconvertToAscii1() throws Exception {
		setUp("testconvertToAscii1");

		System.out.println("LD path is " + System.getenv("LD_LIBRARY_PATH"));
		String outputFile = testScratchDirectoryName+"/NexusLoaderTest_convertToAscii1_Actual.txt";
		NexusLoader.convertToAscii(TestFileFolder + "NexusLoaderTest.nxs", TestFileFolder + "NexusLoaderTestSelection1.xml",
				 null, outputFile, null);

		// Check binary files are the same
		assertArrayEquals(
				Files.readAllBytes(Paths.get(TestFileFolder + "NexusLoaderTest_convertToAscii1_Expected.txt")),
				Files.readAllBytes(Paths.get(outputFile)));
	}
	/**
	 *  test of using null for selection tree - should get everything under NXentry
	 *  
	 * @throws Exception if the test fails
	 */
	@Test
	public void testconvertToAscii2() throws Exception {
		setUp("testconvertToAscii2");

		makeTestFile(testScratchDirectoryName + "/testconvertToAscii2.nxs", new int [] {5,10});
		LinkedList<String> dataSetNames  = new LinkedList<String>();
		dataSetNames.add("datasets.heading1");
		dataSetNames.add("datasets.heading2");
		String outputFile = testScratchDirectoryName+"/NexusLoaderTest_convertToAscii2_Actual.txt";
		NexusLoader.convertToAscii(testScratchDirectoryName + "/testconvertToAscii2.nxs", null, null,
				outputFile, dataSetNames);
		
		// Check binary files are the same
		assertArrayEquals(
				Files.readAllBytes(Paths.get(TestFileFolder + "NexusLoaderTest_convertToAscii2_Expected.txt")),
				Files.readAllBytes(Paths.get(outputFile)));
		(new File(TestFileFolder + "testconvertToAscii2.nxs")).delete();
	}
	/**
	 *  test of using an empty string for the selection tree file name - should get all data and metadata
	 *  under NXentry
	 * testconvertToAscii3 comment
	 *  
	 * @throws Exception if the test fails
	 */
	@Test
	public void testconvertToAscii3() throws Exception {
		setUp("testconvertToAscii3");

		makeTestFile(testScratchDirectoryName + "/testconvertToAscii3.nxs", new int [] {5,10});
		
		String outputFile = testScratchDirectoryName+"/NexusLoaderTest_convertToAscii3_Actual.txt";
		NexusLoader.convertToAscii(testScratchDirectoryName + "/testconvertToAscii3.nxs", "","",
				outputFile, null);
		// Check binary files are the same
		assertArrayEquals(
				Files.readAllBytes(Paths.get(TestFileFolder + "NexusLoaderTest_convertToAscii3_Expected.txt")),
				Files.readAllBytes(Paths.get(outputFile)));
		(new File(TestFileFolder + "testconvertToAscii3.nxs")).delete();
	}
	/**
	 *  test of regular expressions in the selection tree
	 *  
	 * @throws Exception if the test fails
	 */
	@Test
	public void testconvertToAscii4() throws Exception {
		setUp("testconvertToAscii4");

		String outputFile = testScratchDirectoryName+"/NexusLoaderTest_convertToAscii4_Actual.txt";
		NexusLoader.convertToAscii(TestFileFolder + "NexusLoaderTest.nxs", TestFileFolder + "NexusLoaderTestSelection2.xml",
				TestFileFolder + "NexusLoaderTestSelection2.xml", outputFile, null);
		// Check binary files are the same
		assertArrayEquals(
				Files.readAllBytes(Paths.get(TestFileFolder + "NexusLoaderTest_convertToAscii4_Expected.txt")),
				Files.readAllBytes(Paths.get(outputFile)));
	}
	/**
	 *  test of regular expressions in the selection tree - saving to SRS
	 *  
	 * @throws Exception if the test fails
	 */
	@Test
	public void testconvertToSRS1() throws Exception {
		setUp("testconvertToSRS1");

		String outputFile = testScratchDirectoryName+"/NexusLoaderTest_convertToSRS1_Actual.txt";
		NexusLoader.convertToSRS(TestFileFolder + "NexusLoaderTest.nxs", TestFileFolder + "NexusLoaderTestSelection2.xml",
				TestFileFolder + "NexusLoaderTestSelection2.xml", outputFile, null);
		// Check binary files are the same
		assertArrayEquals(
				Files.readAllBytes(Paths.get(TestFileFolder + "NexusLoaderTest_convertToSRS1_Expected.txt")),
				Files.readAllBytes(Paths.get(outputFile)));
	}
	
	/*
	 * 
	 */
	@Test
	public void testDataHolderUsage() throws Exception {
		setUp("testScanFileHolderUsage");
		makeTestFile(testScratchDirectoryName + "/testScanFileHolderUsage.nxs", new int [] {5,10});
		Vector<String> dataSetNames = new Vector<String>();
		dataSetNames.add("datasets.heading1");
		dataSetNames.add("datasets.heading2");
		DataHolder dh = new NexusLoader(testScratchDirectoryName + "/testScanFileHolderUsage.nxs", dataSetNames).loadFile();
		Dataset ds1 = dh.getDataset("datasets.heading1");
		Dataset ds2 = dh.getDataset("datasets.heading2");
		Assert.assertEquals("datasets.heading1", ds1.getName());
		Assert.assertEquals(0.0, ds1.getDouble(0), 0.0001);
		Assert.assertEquals(49.0, ds1.getDouble(4, 9), 0.0001);
		Assert.assertEquals(0.0, ds2.getDouble(0), 0.0001);
		Assert.assertEquals(98.0, ds2.getDouble(4, 9), 0.0001);
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(TestFileFolder + "NexusLoaderTest.nxs", null);
		if (dh==null || dh.getNames().length<1) throw new Exception();
	}

	/*
	 * Jira ticket GDA-3118
	 * A nexus file can contain data for multiple detectors. Each of these can have point specific data with the same name. 
	 * The NexusLoader takes the name as the DataSet name and does not allow duplicates so only the data for the first detector is loaded
	 */
	@Test
	public void testMultipleDetectorSameDataElements() throws Exception {
		final String TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder != null){
			DataHolder shf  = new NexusLoader(TestFileFolder + "/multiDetector.nxs").loadFile();
			String [] headings = shf.getNames();
			Assert.assertEquals("Number of headings is incorrect", 120, headings.length); 
			Assert.assertEquals("EDXD_Element_23.edxd_q", headings[71]);
			Assert.assertEquals("EDXD_Element_23.data", headings[69]);
			Assert.assertEquals("bottom", headings[72]);
			Assert.assertEquals("scaler1.scaler1-9", headings[113]);
			Assert.assertEquals("scaler1.I0 EH1", headings[82]);
			
		}
	}

	/*
	 * Jira ticket GDA-3118
	 * A nexus file can contain data for multiple detectors. Each of these can have point specific data with the same name. 
	 * The NexusLoader takes the name as the DataSet name and does not allow duplicates so only the data for the first detector is loaded
	 */
	@Test
	public void testMultipleDetectorSameDataElementsNameDataSets() throws Exception {
		final String TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder != null){
			Vector<String> dataSetNames = new Vector<String>();
			dataSetNames.add("bottom");
			dataSetNames.add("EDXD_Element_23.edxd_q");
			dataSetNames.add("EDXD_Element_23.data");
			dataSetNames.add("scaler1.scaler1-9");
			dataSetNames.add("scaler1.I0 EH1");
			DataHolder shf = new NexusLoader(TestFileFolder + "/multiDetector.nxs", dataSetNames).loadFile();
			String [] headings = shf.getNames();
			Assert.assertEquals("Number of headings is incorrect", 5, headings.length); 
			Assert.assertEquals("bottom", headings[0]);
			Assert.assertEquals("EDXD_Element_23.edxd_q", headings[1]);
			Assert.assertEquals("EDXD_Element_23.data", headings[2]);
			Assert.assertEquals("scaler1.scaler1-9", headings[3]);
			Assert.assertEquals("scaler1.I0 EH1", headings[4]);
			
		}
	}

	/*
	 * Jira ticket GDA-3118
	 * A nexus file can contain data for multiple detectors. Each of these can have point specific data with the same name. 
	 * The NexusLoader takes the name as the DataSet name and does not allow duplicates so only the data for the first detector is loaded
	 */
	@Test
	public void testMultipleDetectorGetDataSetNames() throws Exception {
		final String TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder != null) {
			List<String> dataSetNames = NexusLoader.getDatasetNames(TestFileFolder + "/multiDetector.nxs", null);
			String[] headings = dataSetNames.toArray(new String[] {});
			Assert.assertEquals("Number of headings is incorrect", 120, headings.length);
			Assert.assertEquals("EDXD_Element_23.edxd_q", headings[71]);
			Assert.assertEquals("EDXD_Element_23.data", headings[69]);
			Assert.assertEquals("bottom", headings[72]);
			Assert.assertEquals("scaler1.scaler1-9", headings[113]);
			Assert.assertEquals("scaler1.I0 EH1", headings[82]);

		}
	}

	@Test
	public void testMeta() throws Exception {
		final String TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		IMetaLoader l = new NexusLoader(TestFileFolder + "/multiDetector.nxs");
		l.loadMetadata(null);
		IMetadata meta = l.getMetadata();
		String[] headings = meta.getDataNames().toArray(new String[meta.getDataNames().size()]);
		Assert.assertEquals("Number of headings is incorrect", 120, headings.length);
		Assert.assertEquals("EDXD_Element_23.edxd_q", headings[71]);
		Assert.assertEquals("EDXD_Element_23.data", headings[69]);
		Assert.assertEquals("bottom", headings[72]);
		Assert.assertEquals("scaler1.scaler1-9", headings[113]);
		Assert.assertEquals("scaler1.I0 EH1", headings[82]);
	}
}
