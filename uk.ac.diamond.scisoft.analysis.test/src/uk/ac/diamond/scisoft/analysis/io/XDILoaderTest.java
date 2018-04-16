package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Test;


public class XDILoaderTest {

	@Test
	public void testGoodXDILoader() throws Exception {
		XDILoader loader = new XDILoader("testfiles/gda/analysis/io/XDILoaderTest/cu_metal_10K.xdi");
		IDataHolder dh = loader.loadFile();
		assertEquals(2, dh.size());
		assertEquals(612, dh.getDataset(0).getSize());
		IMetadata metadata = dh.getMetadata();
		assertEquals("K", metadata.getMetaValue("edge"));
		assertEquals("K", metadata.getMetaValue("Element.edge"));
		assertEquals("Cu", metadata.getMetaValue("element"));
		assertEquals("Cu", metadata.getMetaValue("Element.symbol"));
	}

	@Test
	public void testBadXDILoader() {
		try {
			XDILoader loader = new XDILoader("testfiles/gda/analysis/io/XDILoaderTest/bad_01.xdi");
			loader.loadFile();
			fail("This should fail!");
		} catch (Exception e) {
			
		}
	}
}
