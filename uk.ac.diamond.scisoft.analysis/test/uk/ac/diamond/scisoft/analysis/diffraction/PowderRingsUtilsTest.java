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

package uk.ac.diamond.scisoft.analysis.diffraction;

import gda.util.TestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.ADSCImageLoader;
import uk.ac.diamond.scisoft.analysis.roi.CircularFitROI;
import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalFitROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;

public class PowderRingsUtilsTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
	}

	// NIST Silicon SRM 640C
	static double wavelength = 0.15405929; // in nm
	static double lattice = 0.5431020504;  // in nm
	UnitCell siliconCell;
	MillerSpace mSpace;

	@Before
	public void setUpSilicon() {
		siliconCell = new UnitCell(lattice);
//		mSpace = new MillerSpace(siliconCell, null);
	}

	@Test
	public void findEllipse() {
		AbstractDataset image = null;
		try {
			image = new ADSCImageLoader(TestFileFolder + "ADSCImageTest/Si_200_1_0001.img").loadFile().getDataset(0);
		} catch (Exception e) {
			Assert.fail("Could not open image");
		}

		CircularROI roi = new CircularROI(631, 1528.6, 1533.9);
		PolylineROI points = PowderRingsUtils.findPOIsNearCircle(image, roi);
		System.err.println(points);
		System.err.println(new CircularFitROI(points));
		System.err.println(new EllipticalFitROI(points));
	}
}
