package de.desy.file.loader.test;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

import static org.junit.Assert.*;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import de.desy.file.loader.FioLoader;

public class FioLoaderTest {

	@Test
	public void testLoader() throws Exception {
		FioLoader loader = new FioLoader("test/some.fio");
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.namesSize());
		Dataset d = dh.getDataset(0);
		assertEquals(544, d.getSize());
		assertEquals(11710, d.getDouble(10), 0);
	}

	@Test
	public void testLazyLoader() throws Exception {
		FioLoader loader = new FioLoader("test/some.fio");
		loader.setLoadAllLazily(true);
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.namesSize());
		ILazyDataset d = dh.getLazyDataset(0);
		assertEquals(544, d.getSize());
		assertEquals(11710, d.getSlice().getDouble(10), 0);
	}
}
