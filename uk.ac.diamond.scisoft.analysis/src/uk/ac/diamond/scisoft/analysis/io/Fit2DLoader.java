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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.gda.monitor.IMonitor;

/**
 * This class should be used to load fit2d datafiles created by fit2d
 * into the ScanFileHolder object. This has not been tested on general fit2d datafiles.
 * <p>
 * <b>Note</b>: the header data from this loader is left as strings
 * 
 * 
 * TODO FIXME - Loader not functioning as yet.
 * 
 */
public class Fit2DLoader extends AbstractFileLoader implements IMetaLoader {

	private String fileName;
	private Map<String, String> textMetadata = new HashMap<String, String>();
	private Metadata metadata;
	private static final String DATA_NAME = "ESRF Pilatus Data";

	/**
	 * @param fileName
	 */
	public Fit2DLoader(String fileName) {
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

			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			
			// If the first line is not a { then we fail this loader.
			if (!line.trim().startsWith("\\")) throw new ScanFileHolderException("Fit2D File should start with \\ !"); 
			

			// Read the meta data
			int index = readMetaData(br, line.length()+1, mon);

			// Now read the data
			int height = Integer.parseInt(textMetadata.get("Dim_1"));
			int width = Integer.parseInt(textMetadata.get("Dim_2"));
			
			String dataType = textMetadata.get("DataType");
			if (dataType.equals("Float")) {
				data = new FloatDataset(width, height);
				Utils.readFloat(fi, (FloatDataset) data, index);
			} else {
				data = new IntegerDataset(width, height);
				boolean le = "LowByteFirst".equals(textMetadata.get("ByteOrder")); 
				if (dataType.contains("Short")) {
					boolean signed = dataType.startsWith("Signed");
					if (le)
						Utils.readLeShort(fi, (IntegerDataset) data, index, signed);
					else
						Utils.readBeShort(fi, (IntegerDataset) data, index, signed);
				} else {
					if (le)
						Utils.readLeInt(fi, (IntegerDataset) data, index);
					else
						Utils.readBeInt(fi, (IntegerDataset) data, index);
				}
			}
			data.setName(DEF_IMAGE_NAME);
				
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
		if (loadMetadata) {
			createMetadata();
			data.setMetadata(metadata);
			output.setMetadata(metadata);
		}
		return output;
	}

	@Override
	public void loadMetaData(final IMonitor mon) throws Exception {

		final BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		try {
			final String line       = br.readLine();
			readMetaData(br, line.length()+1, mon);
			createMetadata();
		} finally {
			br.close();
		}
	}
	
	private void createMetadata() {
		metadata = new Metadata(textMetadata);
		metadata.setFilePath(fileName);
		metadata.addDataInfo(DATA_NAME, Integer.parseInt(textMetadata.get("Dim_2")),
				Integer.parseInt(textMetadata.get("Dim_1")));
	}

	@Override
	public IMetaData getMetaData() {
		return metadata;
	}
	
	private int readMetaData(final BufferedReader br, int index, final IMonitor mon) throws Exception {
		
		textMetadata.clear();
		while (true) {
			
			if (mon!=null) mon.worked(1);
			if (mon!=null&&mon.isCancelled()) throw new ScanFileHolderException("Loader cancelled during reading!");
			
			String line = br.readLine();
			index += line.length()+1;
			if (!line.trim().startsWith("\\")) {
				break;
			}
			
			// Image meta data starts with \$, user meta data starts with \
			
			String[] keyvalue = line.split(":");		
			
			if (keyvalue.length == 1) {
				textMetadata.put(keyvalue[0].trim(), "");
			} else {
				// First 17 characters of value are information about the value
				// The first 9 of them say the type e.g. 00000000s - string
				// and 00000000r a real
				String meta  = keyvalue[1].substring(0,17);
				String value = keyvalue[1].length()>17 ? keyvalue[1].substring(17) : "";
				value = value.trim();
				if (meta.startsWith("00000000r")) {
					String hex = meta.substring(9);
					value = Long.valueOf(hex, 16).toString();
				}
				textMetadata.put(keyvalue[0].trim(), value);
			}
		}
		
		return index;
	}
}
