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

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.junit.Test;

public class NexusDiffractionMetaTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/NexusDiffractionTest/";
	
	@Test
	public void testNCDReductionResults1Detector() {
		
		NexusDiffractionMetaReader nr = new NexusDiffractionMetaReader(testFileFolder + "results_i22-102527_Pilatus2M_280313_112434.nxs");
		
		IDiffractionMetadata md = nr.getDiffractionMetadataFromNexus(new int[]{1679,1475});
		
		assertTrue(nr.anyValuesRead());
		
		double[] beamCentre = md.getDetector2DProperties().getBeamCentreCoords();
		assertEquals(702.2209567198178, beamCentre[0],0.01);
		assertEquals(94.02399999999989, beamCentre[1],0.01);
		assertEquals(9660.957924056238, md.getDetector2DProperties().getBeamCentreDistance(),0.01);
		assertEquals(1, md.getDiffractionCrystalEnvironment().getWavelength(),0.01);
		
	}
	
	@Test
	public void testNCDReductionResults2Detectors() {
		
		NexusDiffractionMetaReader nr = new NexusDiffractionMetaReader(testFileFolder + "background_i22-119185_HotwaxsPilatus2M_090513_115327.nxs");
		
		IDiffractionMetadata md = nr.getDiffractionMetadataFromNexus(new int[]{1679,1475});
		
		assertTrue(nr.anyValuesRead());
		
		double[] beamCentre = md.getDetector2DProperties().getBeamCentreCoords();
		assertEquals(718.46, beamCentre[0],0.01);
		assertEquals(92.3614931237721, beamCentre[1],0.01);
		assertEquals(1.5498, md.getDiffractionCrystalEnvironment().getWavelength(),0.01);
	}
	
	@Test
	public void testI22Nexus() {
		NexusDiffractionMetaReader nr = new NexusDiffractionMetaReader(testFileFolder + "i22-119583.nxs");
		IDiffractionMetadata md = nr.getDiffractionMetadataFromNexus(new int[]{1679,1475});
		assertTrue(nr.anyValuesRead());
		double[] beamCentre = md.getDetector2DProperties().getBeamCentreCoords();
		assertEquals(100.0, beamCentre[0],0.01);
		assertEquals(100.00, beamCentre[1],0.01);
		assertEquals(5486.400000000001, md.getDetector2DProperties().getBeamCentreDistance(),0.01);
		assertEquals(1, md.getDiffractionCrystalEnvironment().getWavelength(),0.01);
	}
	
	@Test
	public void testPersisted() {
		NexusDiffractionMetaReader nr = new NexusDiffractionMetaReader(testFileFolder + "persisted.nxs");

		IDiffractionMetadata md = nr.getDiffractionMetadataFromNexus(new int[]{2527,2463});
		assertTrue(nr.anyValuesRead());
		assertTrue(nr.isPartialRead());
		assertTrue(nr.isCompleteRead());
		
		double[] beamCentre = md.getDetector2DProperties().getBeamCentreCoords();
		assertEquals(1225.28, beamCentre[0],0.01);
		assertEquals(1223.32, beamCentre[1],0.01);
		assertEquals(199.9999999999999, md.getDetector2DProperties().getBeamCentreDistance(),0.01);
		assertEquals(0.9762999999999995, md.getDiffractionCrystalEnvironment().getWavelength(),0.01);
	}
	

}
