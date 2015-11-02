package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class XMSOLoaderTest {

	@Test
	public void testXMSOLoaderString() throws Exception {
		XMSOLoader loader = new XMSOLoader("testfiles/gda/analysis/io/XMSOLoaderTest/srm1155.xmso");
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.namesSize());
		Dataset d = dh.getDataset(0);
		assertEquals(2048, d.getSize());
		assertEquals(0.149149, d.getDouble(10), 1E-7);
		assertEquals(61.0761, dh.getDataset(1).getDouble(0), 1E-7);
		assertEquals(71.3896, dh.getDataset(2).getDouble(1), 1E-7);
		assertEquals(72.0899, dh.getDataset(3).getDouble(2), 1E-7);
		assertEquals(72.143, dh.getDataset(4).getDouble(3), 1E-7);
	}

}
