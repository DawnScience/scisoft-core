/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that saves 1D or 2D data from DataHolder by writing the output as tab-delimited ACSII output
 */
public class RawTextSaver implements IFileSaver {
	protected static final Logger logger = LoggerFactory.getLogger(RawTextSaver.class);

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
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		File f = null;
		for (int i = 0, imax = dh.size(); i < imax; i++) {
			ILazyDataset ld = dh.getLazyDataset(i);
			int[] shape = ld.getShape();
			int rank = shape.length;
			if (rank == 1) {
				ld.setShape(shape[0], 1);
				shape = ld.getShape();
			} else if (rank > 2) {
				logger.error("Cannot saved dataset '{}': this loader only supports 1D or 2D datasets", ld.getName());
				continue;
			}
			IDataset data = (ld instanceof IDataset) ? (IDataset) ld : ld.getSlice();

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
				logger.error("Error saving file '{}': {}", fileName, e);
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
		// Does nothing
	}
}
