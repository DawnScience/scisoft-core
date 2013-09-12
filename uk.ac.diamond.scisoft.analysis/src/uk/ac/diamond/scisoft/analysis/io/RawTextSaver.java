/*
 * Copyright 2012 Diamond Light Source Ltd.
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

import gda.analysis.io.IFileSaver;
import gda.analysis.io.ScanFileHolderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

/**
 * Class that saves 1D or 2D data from DataHolder by writing the output as tab-delimited ACSII output
 */
public class RawTextSaver implements IFileSaver {

	private String fileName = "";
	protected char delimiter = '\t';
	protected String cellFormat;
	

	/**
	 * Takes the dataset from a data holder and output them as a height x width array called 'filename'.txt.
	 * If there are multiple datasets in a ScanFileHolder then the class will save each in a separate file.
	 * 
	 * @param filename
	 */
	public RawTextSaver(String filename) {
		fileName = filename;
	}

	@Override
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		File f = null;
		for (int i = 0, imax = dh.size(); i < imax; i++) {
			AbstractDataset data = dh.getDataset(i);
			int[] shape = data.getShape();
			int rank = shape.length;
			if (rank == 1) {
				data = data.reshape(shape[0], 1);
				shape = data.getShape();
			} else if (rank > 2) {
				throw new ScanFileHolderException("Cannot saved dataset: this loader only supports 1D or 2D datasets");
			}

			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				String name = null;
				String end = null;
				if (imax == 1) {
					name = fileName;
				} else {
					try {
						name = fileName.substring(0, (fileName.lastIndexOf(".")));
						end = fileName.substring(fileName.lastIndexOf("."));
					} catch (Exception e) {
						name = fileName;
					}

					NumberFormat format = new DecimalFormat("00000");
					name = name + format.format(i + 1) + end;
				}

				f = new File(name);

				int height = shape[0];
				int width = shape[1];

				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);
				
				writeHeader(bw);
				
				for (int rows = 0; rows < height; rows++) {
					for (int columns = 0; columns < width - 1; columns++) {
						if (cellFormat != null) {
							bw.write(String.format(cellFormat, data.getObject(rows, columns)));
						} else {
							bw.write(data.getString(rows, columns));
						}
						bw.write(delimiter);
					}
					bw.write(data.getString(rows, width - 1));
					bw.newLine();
				}
			} catch (Exception e) {
				throw new ScanFileHolderException("Error saving file '" + fileName + "'", e);
			} finally {
				if (bw != null)
					try {
						bw.close();
					} catch (IOException e) {
					}
				if (fw != null)
					try {
						fw.close();
					} catch (IOException e) {
					}
			}
		}
	}
	
	@SuppressWarnings("unused")
	protected void writeHeader(BufferedWriter writer) throws IOException {
		//Does nothing
	}
}
