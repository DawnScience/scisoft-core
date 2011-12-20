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

import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.gda.monitor.IMonitor;

/**
 * This class should be used to load ESRF datafiles created by the Pilatus detector system
 * into the ScanFileHolder object. This has not been tested on general ESRF datafiles.
 * <p>
 * <b>Note</b>: the header data from this loader is left as strings
 */
public class PilatusEdfLoader extends AbstractFileLoader implements IMetaLoader {

	private String fileName;
	private Map<String, String> textMetadata = new HashMap<String, String>();
	
	/**
	 * @param fileName
	 */
	public PilatusEdfLoader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		
		IntegerDataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);

			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			
			// If the first line is not a { then we fail this loader.
			if (!line.trim().startsWith("{")) throw new ScanFileHolderException("EDF File should start with {"); 
			
			if (line.contains("{")) {
				
				// Read the meta data
				int index = readMetaData(br, line.length()+1, mon);
				
				// Now read the data
				int height = Integer.parseInt(textMetadata.get("Dim_1"));
				int width = Integer.parseInt(textMetadata.get("Dim_2"));
				data = new IntegerDataset(width, height);
				if ("UnsignedShort".equals(textMetadata.get("DataType"))) {
					if ("LowByteFirst".equals(textMetadata.get("ByteOrder")))
						Utils.readLeShort(fi, data, index);
					else
						Utils.readBeShort(fi, data, index);
				} else {
					if ("LowByteFirst".equals(textMetadata.get("ByteOrder")))
						Utils.readLeInt(fi, data, index);
					else
						Utils.readBeInt(fi, data, index);
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
		
		output.addDataset("ESRF Pilatus Data", data);
		return output;
	}

	@Override
	public void loadMetaData(final IMonitor mon) throws Exception {

		final BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		try {
			final String line       = br.readLine();
			readMetaData(br, line.length()+1, mon);
		} finally {
			br.close();
		}
	}
	
	@Override
	public IMetaData getMetaData() {
		return new MetaDataAdapter() {
			
			@Override
			public String getMetaValue(String key) {
				return textMetadata.get(key);	
			}
			
			@Override
			public Collection<String> getMetaNames() throws Exception{
				return textMetadata.keySet();
			}
			
			@Override
			public Map<String,int[]> getDataShapes() {
				int height = Integer.parseInt(textMetadata.get("Dim_1"));
				int width = Integer.parseInt(textMetadata.get("Dim_2"));
                final Map<String,int[]> ret = new HashMap<String,int[]>(1);
                ret.put("ESRF Pilatus Data", new int[]{width,height});
                return ret;
			}
			
			@Override
			public Collection<String> getDataNames() {
				return Collections.unmodifiableCollection(Arrays.asList(new String[]{"ESRF Pilatus Data"}));
			}
		};
	}
	
	private int readMetaData(final BufferedReader br, int index, final IMonitor mon) throws Exception {
		
		textMetadata.clear();
		while (true) {
			
			if (mon!=null) mon.worked(1);
			if (mon!=null&&mon.isCancelled()) throw new ScanFileHolderException("Loader cancelled during reading!");
			
			String line = br.readLine();
			index += line.length()+1;
			if (line.contains("}")) {
				break;
			}
			String[] keyvalue = line.split("=");
				
			if (keyvalue.length == 1) {
				textMetadata.put(keyvalue[0].trim(), "");
			} else {		
				int len = (keyvalue[1].endsWith(";")) ? keyvalue[1].length()-1 : keyvalue[1].length();
				String value = keyvalue[1].substring(0, len);
				textMetadata.put(keyvalue[0].trim(), value.trim());
			}
		}
		
		return index;
	}
}
