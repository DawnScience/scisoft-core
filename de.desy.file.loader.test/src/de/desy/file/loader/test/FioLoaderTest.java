package de.desy.file.loader.test;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.Test;

import static org.junit.Assert.*;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import de.desy.file.loader.FioLoader;

public class FioLoaderTest {

	@Test
	public void testLoader() throws Exception {
		FioLoader loader = new FioLoader("resources/some.fio");
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.size());
		Dataset d = dh.getDataset(0);
		assertEquals(544, d.getSize());
		assertEquals(11710, d.getDouble(10), 0);

		assertEquals(145.3369, dh.getDataset(2).getDouble(10), 0);
	}

	@Test
	public void testLazyLoader() throws Exception {
		FioLoader loader = new FioLoader("resources/some.fio");
		loader.setLoadAllLazily(true);
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.size());
		ILazyDataset d = dh.getLazyDataset(0);
		assertEquals(544, d.getSize());
		assertEquals(11710, d.getSlice().getDouble(10), 0);

		d = dh.getLazyDataset(2);
		assertEquals(145.3369, d.getSlice().getDouble(10), 0);
	}

	@Test
	public void testCachedLazyLoader() throws Exception {
		String file = "resources/some.fio";
		LoaderFactory.clear(file);
		LoaderFactory.registerLoader("fio", FioLoader.class);
		LoaderFactory.cacheData(LoaderFactory.getData(file, false, true, true, null));
		FioLoader loader = new FioLoader(file);
		loader.setLoadAllLazily(true);
		DataHolder dh = loader.loadFile();
		assertEquals(5, dh.size());
		ILazyDataset d = dh.getLazyDataset(0);
		assertEquals(544, d.getSize());
		assertEquals(11710, d.getSlice().getDouble(10), 0);

		d = dh.getLazyDataset(2);
		assertEquals(145.3369, d.getSlice().getDouble(10), 0);
	}
}
