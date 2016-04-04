/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

import java.io.File;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;

public class ExampleDataUtils {

	
	public static String createExampleDataFile(String name, int[] shape) {
		
		try {
			File tmp;
			tmp = File.createTempFile(name, ".nxs");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			IHierarchicalDataFile file = HierarchicalDataFactory.getWriter(tmp.getAbsolutePath());
			
			String entry1 = file.group("entry1");
			file.setNexusAttribute(entry1, "NXentry");
			String nxdata = file.group("data", entry1);
			file.setNexusAttribute(nxdata, "NXdata");
			
			Dataset data = DatasetFactory.ones(shape, Dataset.FLOAT64);
			data.imultiply(10);
			
			String ds = file.createDataset("data", data, nxdata, true);
			file.setIntAttribute(ds, "signal", 1);
			
			for (int i = 0; i < shape.length; i++) {
				
				Dataset ax = DatasetFactory.createRange(shape[i], Dataset.FLOAT64);
				ax.imultiply(i);
				
				String da = file.createDataset("axis" + i, ax, nxdata, true);
				file.setIntAttribute(da, "axis", i+1);
				
			}
			
			file.close();
			file.toString();
			
			return tmp.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
