/*
 * Copyright © 2011 Diamond Light Source Ltd.
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

package gda.analysis.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

/**
 * Class that saves data from DataHolder and writes the output as delimited
 * ACSII output
 * 
 */
public class RawOutput implements IFileSaver {

	private String fileName = "";

	/**
	 * Takes the dataset from a data holder which is an array of doubles
	 * and output them as a width X height array called 'filename'.dat. The
	 * double is written to 12 dp. If there are multiple datasets in a
	 * ScanFileHolder then the class will save each in a separate file.
	 * 
	 * @param filename
	 */
	public RawOutput(String filename) {
		fileName = filename;
	}

	/**
	 * 
	 * @see gda.analysis.io.IFileSaver#saveFile(DataHolder)
	 */

	@Override
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		File f = null;
		for (int i = 0, imax = dh.size(); i < imax; i++) {
			try {
				String name = null;
				String end = null;
				if (imax == 1) {
					name = fileName;
				} else {
					try {
						name = fileName.substring(0,
								(fileName.lastIndexOf(".")));
						end = fileName.substring(fileName.lastIndexOf("."));
					} catch (Exception e) {
						name = fileName;
					}

					NumberFormat format = new DecimalFormat("00000");
					name = name + format.format(i + 1) + end;
				}

				f = new File(name);

				AbstractDataset data = dh.getDataset(i);
				int[] dims = data.getShape();
				int height = dims[0];
				int width = dims[1];

				BufferedWriter bw = new BufferedWriter(new FileWriter(f));

				for (int rows = 0; rows < height; rows++) {
					for (int columns = 0; columns < width; columns++) {
						bw.write(String.valueOf(data.getDouble(rows, columns)));
						bw.write('\t');
					}
					bw.newLine();
				}
				bw.close();

			} catch (Exception e) {
				throw new ScanFileHolderException("Error saving file '"
						+ fileName + "'", e);
			}
		}
	}

}
