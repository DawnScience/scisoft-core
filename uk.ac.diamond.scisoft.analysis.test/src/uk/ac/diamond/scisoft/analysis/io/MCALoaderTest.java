package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class MCALoaderTest {

	@Test
	public void testLoader() throws Exception {
		MCALoader loader = new MCALoader("testfiles/gda/analysis/io/MCALoaderTest/20160114_11_23_23.mca");
		DataHolder dh = loader.loadFile();
		IMetadata metadata = dh.getMetadata();
		double gain_1 = Double.parseDouble((String) metadata.getMetaValue("gain_1"));
		double zero_1 = Double.parseDouble((String) metadata.getMetaValue("zero_1"));
		assertEquals(0.008024, gain_1, 1E-5);
		assertEquals(-0.01266, zero_1, 1E-5);
		assertEquals(2, dh.namesSize());
		DoubleDataset Energy_1 = (DoubleDataset) dh.getDataset("Energy_1");
		DoubleDataset Counts_1 = (DoubleDataset) dh.getDataset("Counts_1");
		assertEquals(2048, Energy_1.getSize());
		assertEquals(2048, Counts_1.getSize());
		DoubleDataset Energy_1_constructed = (DoubleDataset) DatasetFactory.createRange(Energy_1.getSize(), Dataset.FLOAT64);
		Energy_1_constructed.imultiply(gain_1).iadd(zero_1);
		assertArrayEquals(Energy_1.getData(), Energy_1_constructed.getData(), 1E-5);
		assertEquals(2.00, Counts_1.get(DatasetUtils.findIndexGreaterThanOrEqualTo(Energy_1, 1.03)), 1E-5);
	}
}
