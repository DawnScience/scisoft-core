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

import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.util.ArrayList;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LazyDataset;
import uk.ac.gda.monitor.IMonitor;

/**
 * This class loads an SRS data file and also images from a Dectris Pilatus detector 
 * <p>
 * <b>Note</b>: the metadata from this loader is left as strings
 */
public class ExtendedSRSLoader extends SRSLoader {

	public ExtendedSRSLoader(String filename) {
		super(filename);
	}

	private void appendPilatusData(DataHolder currentDataHolder) {

		ArrayList<String> files = new ArrayList<String>();

		// now we need to try to load in the the pilatus data
		if (currentDataHolder.contains("path")) {

			// build a list of all the pathnames
			File base = new File(fileName);
			File path = base.getParentFile();
			path = new File(base.getParentFile(), "pilatus100k");

			AbstractDataset pathvalues = currentDataHolder.getDataset("path");
			for (int i = 0; i < pathvalues.getShape()[0]; i++) {
				File file = new File(path, String.format("test%d.tif", pathvalues.getInt(i)));
				if(!file.exists()) file = new File(path, String.format("p%d.tif", pathvalues.getInt(i)));
				
				// finally add the item.
				files.add(file.getAbsolutePath());
			}

			ImageStackLoader loader;
			try {
				loader = new ImageStackLoader(files);
			} catch (Exception e) {
				logger.warn("Could not create ImageStackLoader, not populating pilatus stack");
				return;
			}

			LazyDataset lazyDataset = new LazyDataset("Pilatus", loader.dtype, loader.getShape(), loader);
			currentDataHolder.addDataset("Pilatus", lazyDataset);
		}
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		// load all the standard data in
		DataHolder data = super.loadFile(mon);

		appendPilatusData(data);

		return data;
	}

}
