/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.metadata.ARPESMetadata;

public class NexusARPESMetaTest {

	private static final String ENTRY1_ANALYSER_DATA = "/entry1/instrument/analyser/data";
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/NexusARPESTest/";

	@Test
	public void testARPESLoader() {
		IDataHolder dh = null;

		try {
			dh = LoaderFactory.getData(testFileFolder + "arpes_example.nxs");
		} catch (Exception e) {
			fail("Could not load file");
			return;
		}

		if (!dh.contains(ENTRY1_ANALYSER_DATA)) {
			fail("Required dataset " + ENTRY1_ANALYSER_DATA + " is not available");
		}

		ILazyDataset dataset = dh.getLazyDataset(ENTRY1_ANALYSER_DATA);

		ARPESMetadata arpesMetadata = null;

		try {
			arpesMetadata = dataset.getMetadata(ARPESMetadata.class).get(0);
		} catch (Exception e) {
			fail("Expected ARPES metadata not found");
			return;
		}

		assertEquals(10.0, arpesMetadata.getPassEnergy(), 0.1);
		assertEquals(6.1, arpesMetadata.getTemperature(), 0.1);
		assertEquals(0.0, arpesMetadata.getAngleAxisGlobalOffset(), 0.1);
		assertEquals(0.0, arpesMetadata.getEnergyAxisGlobalOffset(), 0.1);
		assertEquals(65.8, arpesMetadata.getPhotonEnergy(), 0.1);
		assertEquals(0.0, arpesMetadata.getWorkFunction(), 0.1);

		assertEquals(-0.953, (double) arpesMetadata.getAnalyserAngles().getSlice((Slice[]) null).mean(null), 0.001);
		assertEquals(0.0, (double) arpesMetadata.getAzimuthalAngles().getSlice((Slice[]) null).mean(null), 0.001);
		assertEquals(61.134, (double) arpesMetadata.getKineticEnergies().getSlice((Slice[]) null).mean(null), 0.001);
		assertEquals(0.000192, (double) arpesMetadata.getPolarAngles().getSlice((Slice[]) null).mean(null), 0.00001);
		assertEquals(0.0, (double) arpesMetadata.getTiltAngles().getSlice((Slice[]) null).mean(null), 0.001);

		// Commented items are not available in this file.
		// assertEquals(-0.953, (double)arpesMetadata.getBindingEnergies().getSlice((Slice[])null).mean(null), 0.001);
		// assertEquals(-0.953, (double)arpesMetadata.getEnergyAxisOffset().getSlice((Slice[])null).mean(null), 0.001);
		// assertEquals(-0.953, (double)arpesMetadata.getPhotoelectronMomentum().getSlice((Slice[])null).mean(null), 0.001);
	}
}
