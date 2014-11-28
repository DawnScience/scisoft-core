/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

/**
 * This class should be used to load fit2d Mask datafiles created by fit2d
 * into the ScanFileHolder object. This has not been tested on general fit2d datafiles.
 */
public class Fit2DMaskLoader extends AbstractFileLoader {
	
	private static final String DATA_NAME = "Fit2D Mask";

	/**
	 * @param fileName
	 */
	public Fit2DMaskLoader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	protected void clearMetadata() {
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		ILazyDataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		
		try {
			f = new File(fileName);
			fi = new FileInputStream(f);
			
			int[] shape = readHeader(fi);

			if (loadLazily) {
				data = createLazyDataset(DATA_NAME, Dataset.BOOL, shape, new Fit2DMaskLoader(fileName));
			} else {
				//hope there is nothing important here...
				fi.skip(1000);
				
				int dataSize = (shape[0]*shape[1])/8;
				
				byte[] bufImage = new byte[dataSize];
				
				fi.read(bufImage);
				
				data = new BooleanDataset(shape);
				data.setName("Mask");
				
				boolean[] bdata = ((BooleanDataset)data).getData();
				
				for (int i = 0; i < dataSize; i++) {
					for (int j = 0; j < 8; j++) {
						bdata[(i * 8) + j] = ((bufImage[i] >> j) & 1) != 1;
					}
				}
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("File failed to load " + fileName, e);
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException ex) {
					// do nothing
				}
				fi = null;
			}
		}
		if (loadMetadata) {
			metadata = new Metadata();
			metadata.setFilePath(fileName);
			metadata.addDataInfo(DATA_NAME, data.getShape());
			output.setMetadata(metadata);
		}
		output.addDataset(DATA_NAME, data);
		return output;
	}

	int[] readHeader(FileInputStream fi) throws IOException, ScanFileHolderException {
		byte[] buf = new byte[4];
		char[] magicNumber = new char[4];
		
		for (int i =0; i < 4; i++) {
			fi.read(buf);
			//bit of a hack, reading a 8bit char followed by 3 null chars
			magicNumber[i] =  ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).getChar();
		}
		
		if (magicNumber[0] != 'M' &&
			magicNumber[1] != 'A' &&
			magicNumber[2] != 'S' &&
			magicNumber[3] != 'K') {
		  throw new ScanFileHolderException("Fit2D Mask  file should start with MASK");
		}

		fi.read(buf);
		int x = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).getInt();
		fi.read(buf);
		int y = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).getInt();
		return new int[] {x, y};
	}
}
