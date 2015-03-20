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

import gda.data.nexus.NexusException;
import gda.data.nexus.NexusFile;
import gda.data.nexus.NexusGlobals;

import java.util.Arrays;
import java.util.Enumeration;

import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

import uk.ac.diamond.scisoft.analysis.dataset.Nexus;

/**
 * File loader for RAW NeXus files. <br/>
 * Usage:<br/>
 * <code>>>> from uk.ac.diamond.scisoft.analysis.io import *<br/>
 * >>> dataholder = RawNexusLoader("/path/to/file/filename.nxs").loadFile() </code>
 */
public class RawNexusLoader extends AbstractFileLoader {

	public RawNexusLoader() {
		
	}
	
	/**
	 * @param FileName
	 */
	public RawNexusLoader(String FileName) {
		fileName = FileName;
	}
	
	@Override
	protected void clearMetadata() {
		metadata = null;
	}

	@Override
	public DataHolder loadFile() {
		return loadFile(true);
	}

	@SuppressWarnings("unchecked")
	public DataHolder loadFile(boolean loadData) {

		DataHolder dataHolder = new DataHolder();

		NexusFile file;
		try {
			file = new NexusFile(fileName, NexusGlobals.NXACC_READ);

			if (loadMetadata) {
				metadata = new Metadata();
				metadata.setFilePath(fileName);
			}

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
								if (loadData) {
									final int dtype = Nexus.getDType(iStart[1]);
									Dataset ds = DatasetFactory.zeros(shape, dtype);
									file.getdata(ds.getBuffer());
									ds.setName(dataName);
									dataHolder.addDataset(dataName, ds);
								}
								if (loadMetadata) {
									metadata.addDataInfo(dataName, shape);
								}
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
			if (loadMetadata) {
				dataHolder.setMetadata(metadata);
			}
			return dataHolder;

		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO Empty clause
		}

		return null;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		loadMetadata = true;
		loadFile(false);
	}
}
