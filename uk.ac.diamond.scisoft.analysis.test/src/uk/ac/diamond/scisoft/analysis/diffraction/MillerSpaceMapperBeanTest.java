package uk.ac.diamond.scisoft.analysis.diffraction;

import java.io.File;
import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.dataset.Dataset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;
import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapper.MillerSpaceMapperBean;

public class MillerSpaceMapperBeanTest {
	static String testFileFolder = "testfiles/gda/analysis/io/";

	@BeforeClass
	static public void setUpClass() {
		testFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		testFileFolder = testFileFolder.concat("DiffractionMapping/i16/");
	}

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testWriteRead() {
		MillerSpaceMapperBean orig = new MillerSpaceMapperBean();
		orig.setInputs("blah");

		ObjectMapper mapper = new ObjectMapper();
		String testName = "output.json";
		File f = null;
		try {
			f = tempFolder.newFile(testName);
			mapper.writeValue(f, orig);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not write file");
		}

		try {
			MillerSpaceMapperBean copy = mapper.readValue(f, MillerSpaceMapperBean.class);
			Assert.assertEquals(orig, copy);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not read file");
		}
	}

	@Test
	public void createTestJSON() {
		MillerSpaceMapperBean orig = MillerSpaceMapper.createI16MapperBean();
		orig.setInputs("/scratch/images/i16/562926.nxs", "/scratch/images/i16/562927.nxs", "/scratch/images/i16/562928.nxs");
		orig.setOutput("/scratch/tmp/562926.h5");
		orig.setSplitterName("inverse");
		orig.setSplitterParameter(0.5);
		orig.setScaleFactor(2);
		orig.setReduceToNonZero(true);
		orig.setMillerStep(0.002);

		ObjectMapper mapper = new ObjectMapper();
		String testName = "testfiles/i16.json";
		File f = null;
		try {
			f = new File(testName);
			mapper.writeValue(f, orig);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not write file");
		}
	}

	@Test
	public void loadIncompleteJSON() {
		MillerSpaceMapperBean orig = MillerSpaceMapper.createI16MapperBean();
		orig.setInputs("/scratch/images/i16/562926.nxs", "/scratch/images/i16/562927.nxs", "/scratch/images/i16/562928.nxs");
		orig.setOutput("/scratch/tmp/562926.h5");
		orig.setSplitterName("inverse");
		orig.setSplitterParameter(0.5);
		orig.setScaleFactor(2);
		orig.setReduceToNonZero(true);
		orig.setMillerStep(0.002);

		ObjectMapper mapper = new ObjectMapper();
		String testName = "testfiles/i16-short.json";
		File f = null;
		try {
			f = new File(testName);
			MillerSpaceMapperBean copy = mapper.readValue(f, MillerSpaceMapperBean.class);
			Assert.assertEquals(orig, copy);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not read file");
		}
	}

	@Test
	public void loadExtraItemsJSON() {
		MillerSpaceMapperBean orig = MillerSpaceMapper.createI16MapperBean();
		orig.setInputs("/scratch/images/i16/562926.nxs", "/scratch/images/i16/562927.nxs", "/scratch/images/i16/562928.nxs");
		orig.setOutput("/scratch/tmp/562926.h5");
		orig.setSplitterName("inverse");
		orig.setSplitterParameter(0.5);
		orig.setScaleFactor(2);
		orig.setReduceToNonZero(true);
		orig.setMillerStep(0.002);

		ObjectMapper mapper = new ObjectMapper();
		String testName = "testfiles/i16-extra.json";
		File f = null;
		try {
			f = new File(testName);
			mapper.readValue(f, MillerSpaceMapperBean.class);
			Assert.fail("Should not have been able to read file");
		} catch (IOException e1) {
			// should throw exception
		}
	}

	@Test
	public void testMSMProcessingMapAndSaveInParts() throws ScanFileHolderException, IOException, NexusException {
		File dst = File.createTempFile("MSMProcessingParts_hdf5", ".h5");
		String dstPath = dst.getAbsolutePath();
		String n = testFileFolder + "588193.nxs";
		String[] inputPaths = { n, n };

		MillerSpaceMapperBean mapperBean = MillerSpaceMapper.createI16MapperBean();
		MillerSpaceMapper.setBeanWithAutoBox(mapperBean, inputPaths, dstPath, "inverse", 0.5, 2., true, new double[] {0.005}, null);
		MillerSpaceMapper mapper = new MillerSpaceMapper(mapperBean);
		mapper.mapToVolumeFile(true);

		Dataset[] a = HDF5Utils.readAttributes(dstPath, "/");
		Dataset[] b = HDF5Utils.readAttributes(dstPath, "/processed");
		Dataset[] c = HDF5Utils.readAttributes(dstPath, "/processed/reciprocal_space");
		Dataset[] d = HDF5Utils.readAttributes(dstPath, "entry0");
		Dataset[] e = HDF5Utils.readAttributes(dstPath, "entry1");

		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/date"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/parameters"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/program"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/reciprocal_space/volume"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/reciprocal_space/weight"));

		boolean found = false;
		for (Dataset dataset : a) {
			if (dataset.getName().equals("default")) {
				Assert.assertEquals("processed", dataset.getString());
				found = true;
				break;
			}
		}
		Assert.assertTrue(found);

		found = false;
		for (Dataset dataset : b) {
			if (dataset.getName().equals("default")) {
				Assert.assertEquals("reciprocal_space", dataset.getString());
				found = true;
				break;
			}
		}
		Assert.assertTrue(found);

		Assert.assertEquals("NXdata", c[0].getString());
		Assert.assertEquals("NXentry", d[0].getString());
		Assert.assertEquals("NXentry", e[0].getString());
	}
}
