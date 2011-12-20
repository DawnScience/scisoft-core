/*
 * Copyright © 2009 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.IFileSaver;
import gda.analysis.io.ScanFileHolderException;
import gda.data.nexus.tree.NexusTreeProvider;
import gda.data.nexus.tree.NexusTreeWriter;

import java.util.Enumeration;

import org.nexusformat.NexusException;
import org.nexusformat.NexusFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Nexus;

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
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		NexusFile file;
		boolean duplicate = false;

		try {
			// TODO Check to see if the file exists...
			// TODO then either delete it or fail gracefully!
			file = new NexusFile(fileName, NexusFile.NXACC_CREATE5);
			file.makegroup("ScanFileHolder", "NXentry");
			file.opengroup("ScanFileHolder", "NXentry");

			file.makegroup("datasets", "NXdata");
			file.opengroup("datasets", "NXdata");

			String[] headings = dh.getNames();

			for (int i = 0; i < headings.length; i++) {

				// First lets check to see if this item already exists in the NeXus file.
				Enumeration<?> keys = file.groupdir().keys();
				while (keys.hasMoreElements()) {
					String name = (String) keys.nextElement();
					if (name.equalsIgnoreCase(headings[i])) {
						duplicate = true;
					}
				}

				if (duplicate) {
					logger.warn("Duplicate headings found - only writing the first one.");
				} else {
					AbstractDataset data = dh.getDataset(headings[i]);
					int[] shape = data.getShape();
					file.makedata(headings[i], Nexus.getGroupDataType(data.getDtype()), shape.length, shape);
					file.opendata(headings[i]);
					file.putdata(data.getBuffer());
					file.closedata();
				}
			}
			if(dh instanceof NexusTreeProvider){
				NexusTreeWriter.writeHere(file, ((NexusTreeProvider)dh).getNexusTree());
			}
			file.close();
		} catch (NexusException e) {
			logger.error("Problem writing to NeXus file {}", fileName);
			throw new ScanFileHolderException("Problem writing to NeXus file " + fileName);
		}

	}

}
