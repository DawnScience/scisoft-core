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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class AbstractPixelIntegrationTestBase {
	
	//FIXME Should test against values from other popular data reduction programs
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";
	
	protected IDataset getData() {
		final String path = testFileFolder+"/test1.f2d";
		IDataset data = null;
		try {
			IDataHolder dataHolder = LoaderFactory.getData(path, null);
			data = dataHolder.getDataset(0);
		} catch (Exception e) {
		}
 		
		return data;
	}
	
//	protected IDiffractionMetadata getDiffractionMetadata() {
//		Vector3d origin = new Vector3d(0, 0, 1);
//		
//		Matrix3d or = new Matrix3d(0.18664903618587297, 0.9824227797520605, -0.002760274464467916,
//				-0.9823153683880198, 0.1866694453418516, 0.014527050783780414,
//				0.014786964515874404, 0.0, 0.9998906668633357);
//		
//		Vector3d bv = new Vector3d(-150.01354345745716, 270.6990771036326, 368.69731120612636);
//		
//		DetectorProperties dp = new DetectorProperties(bv, origin, 2048, 2048, 0.2, 0.2, or);
//		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(0.4257394899146627);
//		
//		return new DiffractionMetadata("test",dp, ce);
//		
//	}
	
	protected IDiffractionMetadata getDiffractionMetadata() {
		Vector3d origin = new Vector3d(0, 0, 1);
		
		Matrix3d or = new Matrix3d(0.18664903618587297, 0.9824227797520605, -0.002760274464467916,
				-0.9823153683880198, 0.1866694453418516, 0.014527050783780414,
				0.014786964515874404, 0.0, 0.9998906668633357);
		
		Vector3d bv = new Vector3d(-150.0135434574571, 270.6990771036326, 368.6973112061264);
		
		DetectorProperties dp = new DetectorProperties(bv, origin, 2048, 2048, 0.2, 0.2, or);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(0.42566937014852557);
		
		return new DiffractionMetadata("test",dp, ce);
		
	}
	
	protected AbstractDataset getMask(int[] shape){
		BooleanDataset mask = new BooleanDataset(shape);
		
		for (int i = 100; i < 1000; i++) 
			for (int j = 100; j < 1000; j++) 
				mask.set(true, new int[]{j,i});
		
		return mask;
	}

}
