/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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
