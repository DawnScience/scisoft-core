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

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class PixelIntegrationCompare1D extends AbstractPixelIntegrationTestBase {
	
	final static String pathPyFAINonSplitting = "testfiles/pyfai_non_splitting.dat";
	final static String pathPyFAISplitting = "testfiles/pyfai_split.dat";


	@Test
	public void compareNonSplitting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 2000);
		npsi.setAxisType(XAxis.ANGLE);
		
		List<AbstractDataset> out = npsi.integrate(data);
		
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(pathPyFAINonSplitting);
			
		} catch (Exception e) {
			Assert.fail("Could not load test reduced data");
			return;
		}

		IDataset x = dh.getDataset("Column_1");
		IDataset y = dh.getDataset("Column_2");
		
		AbstractDataset difx = Maths.subtract(x, out.get(0));
		AbstractDataset dify = Maths.subtract(y, out.get(1));
		
		double xm = difx.max().doubleValue();
		double ym = dify.max().doubleValue();
		
		//Check results are not too different
		Assert.assertEquals(0, xm, 0.00001);
		Assert.assertTrue(ym < 60);
		
	}
	
	@Test
	public void compareSplitting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta, 2000);

		
		List<AbstractDataset> out = npsi.integrate(data);
		
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(pathPyFAISplitting);
			
		} catch (Exception e) {
			Assert.fail("Could not load test reduced data");
			return;
		}

		IDataset x = dh.getDataset("Column_1");
		IDataset y = dh.getDataset("Column_2");
		
		AbstractDataset difx = Maths.subtract(x, out.get(0));
		AbstractDataset dify = Maths.subtract(y, out.get(1));
		
		double xm = difx.max().doubleValue();
		double ym = dify.max().doubleValue();
		
		//Check results are not too different
		Assert.assertEquals(0, xm, 0.00001);
		Assert.assertTrue(ym < 60);
		
	}
	
}
