/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.SliceND;
import org.junit.Test;

public class StringTest {

	/**
	 * Test string dataset can be overwritten
	 * 
	 * @throws IOException
	 * @throws NexusException
	 * @throws DatasetException
	 * @throws ScanFileHolderException 
	 */
	@Test
	public void testString() throws IOException, NexusException, DatasetException, ScanFileHolderException {
		
		String name = "string";
		String path = "/entry/data";
		String fullPath = path + Node.SEPARATOR + name;
		String test1 = "This is a string";
		String test2 = "Better string";
		
		File file = File.createTempFile("nexus", ".h5");
		NexusFileHDF5 nexus = new NexusFileHDF5(file.getAbsolutePath());
		nexus.createAndOpenToWrite();
		Dataset stringdata = DatasetFactory.createFromObject(test1);
		stringdata.setName(name);
		HDF5Utils.writeDataset(file.getAbsolutePath(), path, stringdata);
		nexus.close();
		
		nexus = new NexusFileHDF5(file.getAbsolutePath());
		nexus.openToWrite(false);
		DataNode data = nexus.getData(fullPath);
		ILazyWriteableDataset lwds = data.getWriteableDataset();
		IDataset slice = lwds.getSlice();
		assertEquals(test1, slice.getString());
		lwds.setSlice(null, DatasetFactory.createFromObject(test2), new SliceND(lwds.getShape()));
		nexus.close();
		
		nexus = new NexusFileHDF5(file.getAbsolutePath());
		nexus.openToRead();
		DataNode d = nexus.getData(fullPath);
		slice = d.getDataset().getSlice();
		nexus.close();
		assertEquals(test2, slice.getString());
		
	}
	
}
