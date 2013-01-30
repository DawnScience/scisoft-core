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
 */
public class Fit2DLoader extends AbstractFileLoader implements IMetaLoader {

	private String fileName;
	private Map<String, String> textMetadata = new HashMap<String, String>();
	private Metadata metadata;
	private static final String DATA_NAME = "Fit2D Data";

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
			data = new FloatDataset(width, height);
			Utils.readFloat(fi, (FloatDataset) data, index);
				
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
		String headerEnd = "\\data_array:";
		
		String line = br.readLine();
		index += line.length()+1;
		
		while (!line.contains(headerEnd)) {
			
			if (mon!=null) mon.worked(1);
			if (mon!=null&&mon.isCancelled()) throw new ScanFileHolderException("Loader cancelled during reading!");
			
			addValuesToMetaData(line);
			
			line = br.readLine();
			index += line.length()+1;
		}
		
		addValuesToMetaData(line);
		
		return index;
	}
	
	private void addValuesToMetaData(String line) throws ScanFileHolderException {
		String[] keyvalue = line.split(":");
		
		boolean notArray = keyvalue[1].substring(0,8).contains("00000000");
		
		if (notArray) {
			switch (keyvalue[1].charAt(8)) {
				case 's':
					
					String hex = keyvalue[1].substring(9,17);
					int stringSize = Integer.valueOf(hex, 16);
					
					textMetadata.put(keyvalue[0].substring(1), keyvalue[1].substring(17,17+stringSize));
					
					return;
				case 'r':
					
					String hexr = keyvalue[1].substring(9,17);
					Integer intSize = Integer.valueOf(hexr, 16);
					textMetadata.put(keyvalue[0].substring(1), intSize.toString());
					
					return;
			}
		} else {
			if (!keyvalue[1].substring(8,10).contains("ar")) throw new ScanFileHolderException("Image data not found");
			
			String hexr = keyvalue[1].substring(10,18);
			Integer first = Integer.valueOf(hexr, 16);
			
			hexr = keyvalue[1].substring(18,26);
			Integer second = Integer.valueOf(hexr, 16);
			
			hexr = keyvalue[1].substring(26,34);
			Integer x = Integer.valueOf(hexr, 16);
			
			textMetadata.put("Dim_1", x.toString());
			
			hexr = keyvalue[1].substring(34,42);
			Integer y = Integer.valueOf(hexr, 16);
			textMetadata.put("Dim_2", y.toString());
		}
	}
}
