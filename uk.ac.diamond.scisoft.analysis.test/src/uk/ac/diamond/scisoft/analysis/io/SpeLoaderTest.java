package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class SpeLoaderTest {

	@Test
	public void testLoader() throws Exception {
		SpeLoader loader = new SpeLoader("testfiles/gda/analysis/io/SpeLoaderTest/tst_00005.spe");
		DataHolder dh = loader.loadFile();
		assertEquals(1, dh.namesSize());
		Dataset d = dh.getDataset(0);
		assertEquals(2048, d.getSize());
		assertEquals(1, d.getDouble(23), 0);
	}
}
