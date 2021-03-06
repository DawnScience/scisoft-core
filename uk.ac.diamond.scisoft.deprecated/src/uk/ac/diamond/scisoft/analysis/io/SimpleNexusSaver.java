/*
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

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gda.data.nexus.tree.NexusTreeProvider;
import gda.data.nexus.tree.NexusTreeWriter;

/**
 * File saver for simple NeXus files.
 */
public class SimpleNexusSaver implements IFileSaver {
	transient private static final Logger logger = LoggerFactory.getLogger(SimpleNexusSaver.class);

	private String fileName = "";
	
	/**
	 * @param FileName
	 */
	public SimpleNexusSaver(String FileName) {
		fileName = FileName;
	}

	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		NexusFile file;
		try {
			// TODO Check to see if the file exists...
			// TODO then either delete it or fail gracefully!
			file = NexusFileHDF5.createNexusFile(fileName);
			GroupNode g = file.getGroup("/ScanFileHolder:NXentry/datasets:NXdata", true);

			String[] headings = dh.getNames();

			for (int i = 0; i < headings.length; i++) {

				if (g.containsDataNode(headings[i])) {
					logger.warn("Duplicate headings found - only writing the first one.");
				} else {
					Dataset data = DatasetUtils.convertToDataset(dh.getDataset(headings[i]));
					data.setName(headings[i]);
					file.createData(g, data);
				}
			}
			if(dh instanceof NexusTreeProvider){
				NexusTreeWriter.writeHere(file, g, ((NexusTreeProvider)dh).getNexusTree());
			}
			file.close();
		} catch (NexusException e) {
			logger.error("Problem writing to NeXus file {}", fileName);
			throw new ScanFileHolderException("Problem writing to NeXus file " + fileName);
		}

	}

}
