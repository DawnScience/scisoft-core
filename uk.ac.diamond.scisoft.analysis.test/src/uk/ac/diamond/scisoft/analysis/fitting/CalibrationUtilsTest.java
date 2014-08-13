/*-
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;

public class CalibrationUtilsTest {
	
	ArrayList<APeak> peaks= new ArrayList<APeak>();
	
	@Before
	public void initialize() {
		for (int i = 0; i < 20; i++) {
			peaks.add(new Gaussian(i,1,1));
		}
	}

	@Test
	public void testGetPeakList() {
		ArrayList<APeak> peaks = new ArrayList<APeak>();
		for (int i = 0; i < 20; i++) {
			peaks.add(new Gaussian(i,1,1));
		}
		
		Dataset result = CalibrationUtils.getPeakList(peaks);
		
		for (int i = 0; i < 20; i++) {
			Assert.assertEquals(result.getDouble(i), i, 0.001);
		}
	}
	
	@Test
	public void selectSpecifiedPeaks() {
		
		Dataset testpoints = DatasetFactory.createRange(0, 19, 2.2, Dataset.FLOAT64);
		
		Dataset calibPoints = CalibrationUtils.selectSpecifiedPeaks(testpoints, peaks);
		
		for (int i = 0; i < testpoints.getShape()[0]; i++) {
			Assert.assertEquals(Math.round(testpoints.getDouble(i)), calibPoints.getDouble(i), 0.001);
		}
	}
	
	

}
