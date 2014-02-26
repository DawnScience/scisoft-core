/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import gda.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

public class MerlinLoader extends AbstractFileLoader {

	private String fileName;
	private static final String DATA_NAME = "MerlinData";
	private static final String U16 = "U16";
	private static final String U32 = "U32";
	
	public MerlinLoader(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		IntegerDataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		try {
			
			f = new File(fileName);
			fi = new FileInputStream(f);
			char[] cbuf = new char[54];
			
			
			BufferedReader br = new BufferedReader(new FileReader(f));
			br.read(cbuf);
			
			String[] head = new String(cbuf).split(",");
			head.toString();
			
			if (!head[0].equals("MQ1")) {
				throw new ScanFileHolderException("Merlin File should start with MQ1!");
			}
			
			String imageStart = head[2];
			String imageX = head[4];
			String imageY = head[5];
			String depth = head[6];
			
			cbuf = new char[Integer.parseInt(imageStart)-54];
			br.read(cbuf);
			
			data = new IntegerDataset(new int[]{Integer.parseInt(imageX),Integer.parseInt(imageY)});

			if (depth.contains(U16)) {
				Utils.readBeShort(fi, data, Integer.parseInt(imageStart),false);
			} else {
				Utils.readBeInt(fi, data, Integer.parseInt(imageStart));
			}
			
			//br.skip(Integer.parseInt(imageX) * Integer.parseInt(imageY) * 4)
			
			
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
