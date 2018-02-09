/*-
 * Copyright 2015, 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.impl.AttributeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class ExampleDataUtils {

	
	public static String createExampleDataFile(String name, int[] shape) {
		
		try {
			File tmp;
			tmp = File.createTempFile(name, ".nxs");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			NexusFile file = new NexusFileFactoryHDF5().newNexusFile(tmp.getAbsolutePath());
			file.createAndOpenToWrite();
			GroupNode entryGroup = file.getGroup("/entry1", true);
			file.addAttribute(entryGroup, new AttributeImpl(NexusConstants.NXCLASS, NexusConstants.ENTRY));

			GroupNode dataGroup = file.getGroup(entryGroup, NexusConstants.DATA_DATA, NexusConstants.DATA, true);

			Dataset data = DatasetFactory.ones(shape);
			data.imultiply(10);
			data.setName(NexusConstants.DATA_DATA);
			DataNode dataNode = file.createData(dataGroup, data);

			file.addAttribute(dataNode, new AttributeImpl(NexusConstants.DATA_SIGNAL, 1));
			
			for (int i = 0; i < shape.length; i++) {
				
				Dataset ax = DatasetFactory.createRange(shape[i]);
				ax.imultiply(i);
				ax.setName("axis" + i);
				dataNode = file.createData(dataGroup, ax);
				file.addAttribute(dataNode, new AttributeImpl(NexusConstants.DATA_AXIS, i+1));
			}
			
			file.close();
			file.toString();
			
			return tmp.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
