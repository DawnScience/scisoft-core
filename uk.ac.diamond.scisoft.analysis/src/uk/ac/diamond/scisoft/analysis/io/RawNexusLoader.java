/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Arrays;
import java.util.Enumeration;

import org.nexusformat.NexusException;
import org.nexusformat.NexusFile;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Nexus;

/**
 * File loader for RAW NeXus files. <br/>
 * Usage:<br/>
 * <code>>>> from uk.ac.diamond.scisoft.analysis.io import *<br/>
 * >>> dataholder = RawNexusLoader("/path/to/file/filename.nxs").loadFile() </code>
 */
public class RawNexusLoader extends AbstractFileLoader {

	private String fileName = "";

	public RawNexusLoader() {
		
	}
	
	/**
	 * @param FileName
	 */
	public RawNexusLoader(String FileName) {
		fileName = FileName;
	}
	
	public void setFile(final String fileName) {
		this.fileName = fileName;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataHolder loadFile() {

		DataHolder dataHolder = new DataHolder();

		NexusFile file;
		try {
			file = new NexusFile(fileName, NexusFile.NXACC_READ);

			// file.opengroup("entry1", "NXentry");

			// Look for an NXentry...
			Enumeration<String> topKeys = file.groupdir().keys();
			while (topKeys.hasMoreElements()) {
				String topName = topKeys.nextElement();
				String topClass = (String) file.groupdir().get(topName);
				if (topClass.compareTo("NXentry") == 0) {
					// Specify class explicitly to make sure we are opening the correct one. Otherwise it will throw an
					// exception.
					file.opengroup(topName, "NXentry");
					Enumeration<String> keys = file.groupdir().keys();
					while (keys.hasMoreElements()) {
						String name = keys.nextElement();
						String className = (String) file.groupdir().get(name);
						if (className.compareTo("NXdata") == 0) {
							file.opengroup(name, "NXdata");
							// Now lets get a list of the data items
							Enumeration<String> dataKeys = file.groupdir().keys();
							while (dataKeys.hasMoreElements()) {

								// Hashtable h = file.attrdir();
								// Enumeration e = h.keys();
								// while (e.hasMoreElements()) {
								// String attname = (String)e.nextElement();
								// AttributeEntry atten = (AttributeEntry)h.get(attname);
								// JythonServerFacade.getInstance().print("Found SDS attribute: " + attname +
								// " type: "+ atten.type + " ,length: " + atten.length);
								//
								// }

								String dataName = dataKeys.nextElement();
								file.opendata(dataName);
								int[] iDim = new int[20];
								int[] iStart = new int[2];
								file.getinfo(iDim, iStart);
								// JythonServerFacade.getInstance().print(
								// "Found " + dataName + " with: rank = " + iStart[0] + " type = " + iStart[1]
								// + " dims = " + iDim[0] + ", " + iDim[1]);

								// h = file.attrdir();
								// e = h.keys();
								// while (e.hasMoreElements()) {
								// String attname = (String)e.nextElement();
								// AttributeEntry atten = (AttributeEntry)h.get(attname);
								// JythonServerFacade.getInstance().print("Found SDS attribute: " + attname +
								// " type: "+ atten.type + " ,length: " + atten.length);
								//
								// }

								// Now lets create an array of the dimensions for creating the dataset.
								final int rank = iStart[0];
								int[] shape = Arrays.copyOf(iDim, rank);
								final int dtype = Nexus.getDType(iStart[1]);
								AbstractDataset ds = AbstractDataset.zeros(shape, dtype);
								file.getdata(ds.getBuffer());
								ds.setName(dataName);
								dataHolder.addDataset(dataName, ds);

								file.closedata();
							}
							// Close NXdata
							file.closegroup();
						}
					}
					// Close the NXentry
					file.closegroup();
				}
			}
			// for (Iterator iterator = file.groupdir().values().iterator(); iterator.hasNext();) {
			// String type = (String) iterator.next();
			// JythonServerFacade.getInstance().print(type);
			// }

			file.close();

			return dataHolder;

		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO Empty clause
		}

		return null;
	}

}
