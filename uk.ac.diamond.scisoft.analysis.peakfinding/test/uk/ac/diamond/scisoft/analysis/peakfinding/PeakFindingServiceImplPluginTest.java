/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

public class PeakFindingServiceImplPluginTest {
	
	private static IPeakFindingService peakFindServ;
	
	@BeforeClass
	public static void setupOSGiService() {
		peakFindServ = (IPeakFindingService)Activator.getService(IPeakFindingService.class);
	}
	
	@Test
	public void testGetService() {
		assertNotNull(peakFindServ);
	}
	
	@Test
	public void testServiceHasPeakFinders() throws Exception {
		final Collection<String> peakFinderNames = peakFindServ.getRegisteredPeakFinders();
		assertNotNull(peakFinderNames);
		assertFalse(peakFinderNames.isEmpty());
	}
}
