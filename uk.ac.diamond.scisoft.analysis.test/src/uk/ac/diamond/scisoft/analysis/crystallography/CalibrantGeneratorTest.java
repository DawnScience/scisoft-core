/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.crystallography;

import org.junit.Assert;
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
