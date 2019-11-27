package uk.ac.diamond.scisoft.analysis.diffraction;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapper;
import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapper.MillerSpaceMapperBean;

public class MillerSpaceMapperBeanTest {
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
}

