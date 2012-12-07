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

import gda.analysis.io.IFileSaver;
import gda.analysis.io.ScanFileHolderException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.gda.monitor.IMonitor;

/**
 * Basic class that loads in data from the Oxford Instruments Ruby detector
 */
public class CrysalisLoader extends AbstractFileLoader implements IFileSaver, IMetaLoader {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(CrysalisLoader.class);

	private String fileName = "";
    private Map<String, String> textMetadata;
    
	public CrysalisLoader() {
		textMetadata = new HashMap<String, String>(3);
	}
	/**
	 * @param FileName
	 */
	public CrysalisLoader(String FileName) {
		textMetadata = new HashMap<String, String>(3);
		setFile(FileName);
	}
	
	public void setFile(final String fileName) {
		this.fileName = fileName;
		textMetadata.clear();
	}

	/**
	 * The loading function
	 * 
	 * @return The loaded data object
	 * @throws ScanFileHolderException 
	 */
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {

		IntegerDataset data = null;
		File f = null;
		FileInputStream fi = null;
		// Try to load the file
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new FileReader(f));
			char[] headChunk = new char[255];
			br.read(headChunk,0,255);
			String testStr = new String(headChunk);
			if (testStr.contains("OD"))
			{
				br.close();
				fi.close();
				fi = new FileInputStream(f);
				br = new BufferedReader(new FileReader(f));
				
				String[] lines = new String[5];
				for (int i = 0; i < lines.length; i++) {
					lines[i] = br.readLine();
				}
				if (!lines[1].contains("COMPRESSION= NO")) {
					throw new IllegalArgumentException("File is compressed  - " + fileName);
				}
				Pattern p = Pattern.compile("NX= *(\\d+).*");
				Matcher m = p.matcher(lines[2]);
				if (!m.matches()) {
					throw new IllegalArgumentException("NX not found  - " + fileName);
				}
				int nx = Integer.valueOf(m.group(1));
				textMetadata.put("nx", ""+nx);
				
				p = Pattern.compile(".*NY= *(\\d+).*");
				m = p.matcher(lines[2]);
				if (!m.matches()) {
					throw new IllegalArgumentException("NY not found  - " + fileName);
				}
				int ny = Integer.valueOf(m.group(1));
				textMetadata.put("ny", ""+ny);

				p = Pattern.compile("NHEADER= *(\\d+).*");
				m = p.matcher(lines[3]);
				if (!m.matches()) {
					throw new IllegalArgumentException("NHEADER not found  - " + fileName);
				}
				int nheader = Integer.valueOf(m.group(1));
				textMetadata.put("nheader", ""+nheader);

				char[] cbuf = new char[nheader];
				for (int i = 0; i < cbuf.length; i++) {
					fi.read();
				}

				int height = nx; // read NX value from header
				int width = ny; // read NY value from header

				data = new IntegerDataset(height, width);
				Utils.readLeInt(fi, data, 0);
				data.setName(DEF_IMAGE_NAME);
			} else {
				throw new ScanFileHolderException("Not a valid Crysalis file");
			}
		} catch (Exception e) {
			logger.error("File failed to load {} with error: {}" , fileName, e);
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

		// create the holder and then put to the output.
		DataHolder output = new DataHolder();

		output.addDataset("Ruby_Image", data);
		if (loadMetadata) {
			data.setMetadata(getMetaData());
			output.setMetadata(data.getMetadata());
		}

		return output;

	}

	/**
	 * Fulfils the interface for the save function
	 * 
	 * @param dh
	 */
	@Override
	public void saveFile(DataHolder dh) {
		File f = null;
		FileOutputStream fo = null;
		try {
			f = new File(fileName);
			fo = new FileOutputStream(f);
			AbstractDataset ds = dh.getDataset(0);
			int[] dims = ds.getShape();
			int height = dims[0];
			int width = dims[1];
			Utils.writeLeInt(fo, height);
			Utils.writeLeInt(fo, width);
			int[] index = new int[2];
			for (int j = 0; j < height; j++) { // row-major ordering
				index[0] = j;
				for (int i = 0; i < width; i++) {
					index[1] = i;
					int val = ds.getInt(index);
					Utils.writeLeInt(fo, val);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (fo != null) {
				try {
					fo.close();
				} catch (IOException ex) {
					// do nothing
				}
				fo = null;
			}
		}
	}
	@Override
	public void loadMetaData(final IMonitor mon) throws Exception {
		DataHolder dh = loadFile(mon);
		// Reads data anyway - TODO fix to have read header one day.
		dh.clear();
	}
	
	@Override
	public IMetaData getMetaData() {
		Metadata md = new Metadata(textMetadata);
		int width = Integer.parseInt(textMetadata.get("nx"));
		int height = Integer.parseInt(textMetadata.get("ny"));
		md.setDataInfo("Crysalis Img", height, width);
		return md;
	}
}
