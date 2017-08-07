/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;

public class TestHDF5DataUtils {
	
	public static final String ROOT = "/entry";

	public static void makeHDF5File(String path, Map<String, int[]> nameShapeMap) throws NexusException {
		try (NexusFile file = NexusFileHDF5.createNexusFile(path)) {
			GroupNode group = file.getGroup(ROOT,true);
			int j = 0;
			for (Entry<String, int[]> entry : nameShapeMap.entrySet()) {
				
				int size = 1;
				for (int i : entry.getValue()) size*=i;
				
				DoubleDataset r = DatasetFactory.createRange(size);
				r.iadd(j);
				r.setShape(entry.getValue());
				r.setName(entry.getKey());
				
				file.createData(group,r);
			}
		}
	}
}
