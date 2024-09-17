package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

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
import com.fasterxml.jackson.databind.SerializationFeature;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class MillerSpaceMapperBeanTest {
	static String testFileFolder = "testfiles/gda/analysis/io/";

	@BeforeClass
	static public void setUpClass() {
		testFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		testFileFolder = testFileFolder.concat("DiffractionMapping/i16/");
	}

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private void testRead(ObjectMapper mapper, MillerSpaceMapperBean orig, File f) {
		try {
			MillerSpaceMapperBean copy = mapper.readValue(f, MillerSpaceMapperBean.class);
			assertEquals(orig, copy);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not read file");
		}
	}

	private void testWriteRead(MillerSpaceMapperBean orig, File f) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		try {
			mapper.writeValue(f, orig);
		} catch (IOException e1) {
			e1.printStackTrace();
			Assert.fail("Could not write file");
		}

		testRead(mapper, orig, f);
	}

	@Test
	public void testWriteRead() throws Exception {
		MillerSpaceMapperBean orig = new MillerSpaceMapperBean();
		orig.setInputs("blah");
		orig.setImages("1::4");
		File f = tempFolder.newFile("output.json");
		testWriteRead(orig, f);
	}

	private MillerSpaceMapperBean createBean(boolean makeNew) {
		MillerSpaceMapperBean orig = new MillerSpaceMapperBean();
		orig.setInputs("/scratch/images/i16/562926.nxs", "/scratch/images/i16/562927.nxs", "/scratch/images/i16/562928.nxs");
		orig.setOutput("/scratch/tmp/562926.h5");
		orig.setSplitterName("inverse");
		orig.setSplitterParameter(0.5);
		orig.setScaleFactor(2);
		orig.setReduceToNonZero(true);
		orig.setStep(0.002);
		if (makeNew) {
			orig.setImages("1::4");
		}
		return orig;
	}

	@Test
	public void createTestJSON() {
		String testName = "test-scratch/i16.json";
		File f = new File(testName);
		File p = f.getParentFile();
		if (!p.isDirectory()) {
			p.mkdir();
		}
		testWriteRead(createBean(false), f);
	}

	private void loadJSON(String testFile, boolean makeNew) {
		MillerSpaceMapperBean orig = createBean(makeNew);

		ObjectMapper mapper = new ObjectMapper();
		testRead(mapper, orig, new File(testFile));
	}

	@Test
	public void loadCurrentJSON() {
		loadJSON("testfiles/i16.json", false);
	}

	@Test
	public void loadOldJSON() {
		loadJSON("testfiles/i16-old.json", false);
	}

	@Test
	public void loadIncompleteJSON() {
		loadJSON("testfiles/i16-short.json", false);
	}

	@Test(expected=IOException.class)
	public void loadExtraItemsJSON() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String testName = "testfiles/i16-extra.json";
		File f = null;
		f = new File(testName);
		mapper.readValue(f, MillerSpaceMapperBean.class);
		Assert.fail("Should not have been able to read file");
	}

	@Test
	public void loadNewJSON() throws Exception {
		loadJSON("testfiles/i16-new.json", true);
	}

	@Test
	public void testMSMProcessingMapAndSaveInParts() throws ScanFileHolderException, IOException, NexusException {
		File dst = File.createTempFile("MSMProcessingParts_hdf5", ".h5");
		dst.deleteOnExit();

		String dstPath = dst.getAbsolutePath();
		String n = testFileFolder + "588193.nxs";
		String[] inputPaths = { n, n };

		MillerSpaceMapperBean mapperBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputPaths, dstPath, "inverse", 0.5, 2., true, false, 0.005);
		MillerSpaceMapper mapper = new MillerSpaceMapper(mapperBean);
		mapper.mapToOutputFile(true);

		Dataset[] a = HDF5Utils.readAttributes(dstPath, "/");
		Dataset[] b = HDF5Utils.readAttributes(dstPath, "/processed");
		Dataset[] c = HDF5Utils.readAttributes(dstPath, "/processed/reciprocal_space");
		Dataset[] d = HDF5Utils.readAttributes(dstPath, "entry0");
		Dataset[] e = HDF5Utils.readAttributes(dstPath, "entry1");

		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/date"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/parameters"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/program"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/reciprocal_space/volume"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/reciprocal_space/weight"));

		boolean found = false;
		for (Dataset dataset : a) {
			if (dataset.getName().equals("default")) {
				assertEquals("processed", dataset.getString());
				found = true;
				break;
			}
		}
		assertTrue(found);

		found = false;
		for (Dataset dataset : b) {
			if (dataset.getName().equals("default")) {
				assertEquals("reciprocal_space", dataset.getString());
				found = true;
				break;
			}
		}
		assertTrue(found);

		assertEquals("NXdata", c[0].getString());
		assertEquals("NXentry", d[0].getString());
		assertEquals("NXentry", e[0].getString());
	}

	@Test
	public void testMSCoordinatesSSingle() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-ssingle-", new int[][] {
			{94, 293}, // mid-point
		});
		assertArrayEquals(new int[] {1, 3}, shapes[0]);
	}

	@Test
	public void testMSCoordinatesSAll() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-sall-", new int[][] {
			{-1, 94, 293}, // mid-point
		});
		assertArrayEquals(new int[] {51, 3}, shapes[0]);
	}

	@Test
	public void testMSCoordinatesSSelect() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-sselect-", new int[][] {
			{25, 94, 293}, // mid-point
		});
		assertArrayEquals(new int[] {1, 3}, shapes[0]);
	}


	@Test
	public void testMSCoordinatesSingle() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-single-", new int[][] {
			{0, 0}, // three points down leading diagonal of first frame
			{94, 293},
			{194, 486},
		});
		assertArrayEquals(new int[] {3, 3}, shapes[0]);
	}

	@Test
	public void testMSCoordinatesAll() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-all-", new int[][] {
			{-1, 0, 0}, // three points down leading diagonal of all frames
			{-1, 94, 293},
			{-1, 194, 486},
		});
		assertArrayEquals(new int[] {3*51, 3}, shapes[0]);
	}

	@Test
	public void testMSCoordinatesSelect() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(false, "MScoords-select-", new int[][] {
			{50, 0, 0}, // three points down leading diagonal of selected frames (single, all, single)
			{-1, 94, 293},
			{25, 194, 486},
		});
		assertArrayEquals(new int[] {51 + 2, 3}, shapes[0]);
	}

	@Test
	public void testQSCoordinatesSingle() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(true, "QScoords-single-", new int[][] {
			{0, 0}, // three points down leading diagonal of first frame
			{94, 293},
			{194, 486},
		});
		assertArrayEquals(new int[] {3, 3}, shapes[0]);
	}

	@Test
	public void testQSCoordinatesAll() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(true, "QScoords-all-", new int[][] {
			{-1, 0, 0}, // three points down leading diagonal of all frames
			{-1, 94, 293},
			{-1, 194, 486},
		});
		assertArrayEquals(new int[] {3*51, 3}, shapes[0]);
	}

	@Test
	public void testQSCoordinatesSelect() throws ScanFileHolderException, IOException, NexusException {
		int[][] shapes = testMSMCoordinates(true, "QScoords-select-", new int[][] {
			{50, 0, 0}, // three points down leading diagonal of selected frames (single, all, single)
			{-1, 94, 293},
			{25, 194, 486},
		});
		assertArrayEquals(new int[] {51 + 2, 3}, shapes[0]);
	}

	private int[][] testMSMCoordinates(boolean mapQ, String name, int[][] indexes) throws ScanFileHolderException, IOException, NexusException {
		File dst = File.createTempFile(name, ".nxs");
		String dstPath = dst.getAbsolutePath();
		dst.deleteOnExit();

		String n = testFileFolder + "588193.nxs"; // 51 frames
		String[] inputPaths = { n };

		// pil100k is 195x487
		MillerSpaceMapperBean mapperBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputPaths, dstPath, "inverse", 0.5, 2., true, mapQ, 0.005);
		mapperBean.setPixelIndexes(indexes);
		MillerSpaceMapper mapper = new MillerSpaceMapper(mapperBean);
		mapper.calculateCoordinates();

		Dataset[] a = HDF5Utils.readAttributes(dstPath, "/");
		Dataset[] b = HDF5Utils.readAttributes(dstPath, "/processed");
		String datapath = mapQ ? "q_space" : "reciprocal_space";
		String path = "/processed/" + datapath;
		Dataset[] c = HDF5Utils.readAttributes(dstPath, path);

		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/date"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/parameters"));
		assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/program"));
		assertTrue(HDF5Utils.hasDataset(dstPath, path + "/coordinates"));

		boolean found = false;
		for (Dataset dataset : a) {
			if (dataset.getName().equals("default")) {
				assertEquals("processed", dataset.getString());
				found = true;
				break;
			}
		}
		assertTrue(found);

		found = false;
		for (Dataset dataset : b) {
			if (dataset.getName().equals("default")) {
				assertEquals(datapath, dataset.getString());
				found = true;
				break;
			}
		}
		assertTrue(found);

		assertEquals("NXdata", c[0].getString());
		return HDF5Utils.getDatasetShape(dstPath, path + "/coordinates");
	}

	@Test
	public void testVolumeOrientation() {
		MillerSpaceMapperBean b = new MillerSpaceMapperBean();
		Matrix3d o = MillerSpaceMapperBean.getVolumeOrientation(b);
		assertNull(o);

		b.setThirdAxis(new double[] {0, 0, 1});
		b.setAziPlaneNormal(new double[] {0, 1, 0});
		o = MillerSpaceMapperBean.getVolumeOrientation(b);
		Matrix3d e = new Matrix3d();
		e.setIdentity();
		assertTrue(MatrixUtils.isClose(e, o, 1e-14, 1e-12));

		// check we can map to volume
		b.setThirdAxis(new double[] {3, 1, 0});
		b.setAziPlaneNormal(new double[] {0, 0, 1});
		o = MillerSpaceMapperBean.getVolumeOrientation(b);
		o.invert();

		Vector3d v = new Vector3d(3, 1, 0);
		o.transform(v);
		v.normalize();
		Vector3d t = new Vector3d(0, 0, 1);
		assertTrue(MatrixUtils.isClose(t, v, 1e-14, 1e-12));
	}
}
