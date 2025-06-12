package org.eclipse.dawnsci.nexus.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link NexusScanInfo}.
 */
class NexusScanInfoTest {

	@Test void shouldAddDetectorNames() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setDetectorNames(List.of("det1", "det2"));
		Collection<String> detectorNames = systemUnderTest.getDetectorNames();
		assertTrue(detectorNames.contains("det1"));
		assertTrue(detectorNames.contains("det2"));
		assertEquals(2, detectorNames.size());
	}

	@Test void shouldAddScannableNames() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setScannableNames(List.of("scn1", "scn2", "scn_other"));
		Collection<String> scnNames = systemUnderTest.getScannableNames();
		assertTrue(scnNames.contains("scn1"));
		assertTrue(scnNames.contains("scn2"));
		assertTrue(scnNames.contains("scn_other"));
		assertEquals(3, scnNames.size());
	}

	@Test void shouldAddPerPointMonitorNames() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setPerPointMonitorNames(List.of("mn1", "mn_other"));
		Collection<String> ppmNames = systemUnderTest.getPerPointMonitorNames();
		assertTrue(ppmNames.contains("mn1"));
		assertTrue(ppmNames.contains("mn_other"));
		assertEquals(2, ppmNames.size());
	}

	@Test void shouldAddPerScanMonitorNames() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setPerScanMonitorNames(List.of("mn1", "mn_other"));
		Collection<String> psmNames = systemUnderTest.getPerScanMonitorNames();
		assertTrue(psmNames.contains("mn1"));
		assertTrue(psmNames.contains("mn_other"));
		assertEquals(2, psmNames.size());
	}

	@Test void shouldNotOverwriteHigherPriorityRole() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setDetectorNames(List.of("det1"));
		systemUnderTest.setScannableNames(List.of("det1", "scn1", "scn2"));
		systemUnderTest.setPerPointMonitorNames(List.of("scn1", "scn3"));

		Collection<String> detectorNames = systemUnderTest.getDetectorNames();
		assertTrue(detectorNames.contains("det1"));
		assertEquals(1, detectorNames.size());

		Collection<String> scnNames = systemUnderTest.getScannableNames();
		assertFalse(scnNames.contains("det1"));
		assertTrue(scnNames.contains("scn1"));
		assertTrue(scnNames.contains("scn2"));
		assertEquals(2, scnNames.size());

		Collection<String> ppmNames = systemUnderTest.getPerPointMonitorNames();
		assertFalse(ppmNames.contains("scn1"));
		assertTrue(ppmNames.contains("scn3"));
		assertEquals(1, ppmNames.size());
	}

	@Test void shouldOverwriteLowerPriorityRole() {
		NexusScanInfo systemUnderTest = new NexusScanInfo();
		systemUnderTest.setPerPointMonitorNames(List.of("scn1", "scn3"));
		systemUnderTest.setScannableNames(List.of("det1", "scn1", "scn2"));
		systemUnderTest.setDetectorNames(List.of("det1"));

		Collection<String> detectorNames = systemUnderTest.getDetectorNames();
		assertTrue(detectorNames.contains("det1"));
		assertEquals(1, detectorNames.size());

		Collection<String> scnNames = systemUnderTest.getScannableNames();
		assertFalse(scnNames.contains("det1"));
		assertTrue(scnNames.contains("scn1"));
		assertTrue(scnNames.contains("scn2"));
		assertEquals(2, scnNames.size());

		Collection<String> ppmNames = systemUnderTest.getPerPointMonitorNames();
		assertFalse(ppmNames.contains("scn1"));
		assertTrue(ppmNames.contains("scn3"));
		assertEquals(1, ppmNames.size());
	}

}
