/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.crystallography.CalibrantGenerator.Cubic;

public class CalibrantGeneratorTest {

	@Test
	public void testCubic() {
		
		CalibrantSpacing space = CalibrantGenerator.createCubicStandard("Lab6 SRM660b", 4.15689, 3, Cubic.SIMPLE);
		space.toString();
		//Assert.assertEquals();
	}
	
	@Test
	public void testHexagonal() {
		
		CalibrantSpacing space = CalibrantGenerator.createRhombohedralStandard("Cr2O3 SRM674b", 4.958979, 13.59592,  4);
		space.toString();
		//Assert.assertEquals();
	}


}
