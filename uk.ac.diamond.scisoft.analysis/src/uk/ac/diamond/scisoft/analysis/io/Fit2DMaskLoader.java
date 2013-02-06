/*-
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

import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * This class should be used to load fit2d Mask datafiles created by fit2d
 * into the ScanFileHolder object. This has not been tested on general fit2d datafiles.
 */
public class Fit2DMaskLoader extends AbstractFileLoader {
	
	private String fileName;
	private static final String DATA_NAME = "Fit2D Mask";

	/**
	 * @param fileName
	 */
	public Fit2DMaskLoader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		AbstractDataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		
		try {
			f = new File(fileName);
			fi = new FileInputStream(f);
			
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
			
			//hope there is nothing important here...
			fi.skip(1000);
			
			int dataSize = (x*y)/8;
			
			byte[] bufImage = new byte[dataSize];
			
			fi.read(bufImage);
			
			data = new BooleanDataset(x,y);
			data.setName("Mask");
			
			boolean[] bdata = ((BooleanDataset)data).getData();
			
			for (int i = 0; i< dataSize; i++){
				for (int j = 0; j < 8 ; j++)
				{
					bdata[(i*8) + j] = ((bufImage[i] >> j) & 1) !=1;
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
		
		output.addDataset(DATA_NAME, data);
		return output;
	}
}
