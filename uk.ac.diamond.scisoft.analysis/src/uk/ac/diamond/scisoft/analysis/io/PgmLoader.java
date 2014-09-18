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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;
import uk.ac.diamond.scisoft.analysis.metadata.IMetaLoader;
import uk.ac.diamond.scisoft.analysis.metadata.IMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.Metadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * This class should be used to load .pgm datafiles (Portable Grey Map)
 * into the ScanFileHolder object.
 * <p>
 * <b>Note</b>: the header data from this loader is left as strings
 */
public class PgmLoader extends AbstractFileLoader implements IMetaLoader {

	private String fileName;
	private Map<String, String> textMetadata = new HashMap<String, String>();
	private Metadata metadata;
	private static final String DATA_NAME = "Portable Grey Map";

	public PgmLoader() {
		
	}
	/**
	 * @param fileName
	 */
	public PgmLoader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {

		Dataset data = null;
		DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);

			BufferedReader br = new BufferedReader(new FileReader(f));
			
			int[] vals = readMetaData(br, mon);
			int index  = vals[0];
			int width  = vals[1];
			int height = vals[2];
			int maxval = vals[3];

			// Now read the data
			if (maxval < 256) {
				data = new ShortDataset(height, width);
				Utils.readByte(fi, (ShortDataset) data, index);
			} else {
				data = new IntegerDataset(height, width);
				Utils.readBeShort(fi, (IntegerDataset) data, index, false);
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

	private int[] readMetaData(BufferedReader br, IMonitor mon) throws Exception {
		int width  = 0;
		int height = 0;
		int maxval = 0;

		textMetadata.clear();
		if (mon!=null) mon.worked(1);
		if (mon!=null&&mon.isCancelled()) throw new ScanFileHolderException("Loader cancelled during reading!");

		String line = br.readLine();
		if (line == null) {
			throw new ScanFileHolderException("End of file reached with no metadata found");
		}
		int index   = line.length()+1;
		String token;
		StringTokenizer s1 = new StringTokenizer(line);
		token = s1.nextToken();
		textMetadata.put("MagicNumber", token);
		if (token.startsWith("P5")) {
			if (!s1.hasMoreTokens()) {
				do {
					line = br.readLine();
					if (line == null) {
						throw new ScanFileHolderException("End of file reached with width not found");
					}
					index += line.length() + 1;
				} while (line.startsWith("#")); // ignore comment lines
				s1 = new StringTokenizer(line);
			}
			token = s1.nextToken();
			textMetadata.put("Width", token);
			width = Integer.parseInt(token);
			if (!s1.hasMoreTokens()) {
				line = br.readLine();
				if (line == null) {
					throw new ScanFileHolderException("End of file reached with height not found");
				}
				index += line.length()+1;
				s1 = new StringTokenizer(line);	
			}
			token = s1.nextToken();
			textMetadata.put("Height", token);
			height = Integer.parseInt(token);
			if (!s1.hasMoreTokens()) {
				line = br.readLine();
				if (line == null) {
					throw new ScanFileHolderException("End of file reached with max value not found");
				}
				index += line.length()+1;
				s1 = new StringTokenizer(line);	
			}
			token = s1.nextToken();
			textMetadata.put("Maxval", token);
			maxval = Integer.parseInt(token);
		}
		
		return new int[]{index, width, height, maxval};
	}

	@Override
	public void loadMetadata(final IMonitor mon) throws Exception {

		final BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		try {
		    readMetaData(br, mon);
		    createMetadata();
		} finally {
			br.close();
		}
	}

	private void createMetadata() {
		metadata = new Metadata(textMetadata);
		metadata.setFilePath(fileName);
		metadata.addDataInfo(DATA_NAME, Integer.parseInt(textMetadata.get("Height")), Integer.parseInt(textMetadata.get("Width")));
	}
	
	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	public String getHeaderValue(String key) {
		return textMetadata.get(key);	
	}
}
