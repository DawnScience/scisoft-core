/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
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
	
	protected Dataset getMask(int[] shape){
		BooleanDataset mask = new BooleanDataset(shape);
		
		for (int i = 100; i < 1000; i++) 
			for (int j = 100; j < 1000; j++) 
				mask.set(true, new int[]{j,i});
		
		return mask;
	}

}
