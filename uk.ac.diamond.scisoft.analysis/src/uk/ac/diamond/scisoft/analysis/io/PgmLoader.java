/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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
import java.util.StringTokenizer;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;
import uk.ac.gda.monitor.IMonitor;

/**
 * This class should be used to load .pgm datafiles (Portable Grey Map)
 * into the ScanFileHolder object.
 * <p>
 * <b>Note</b>: the header data from this loader is left as strings
 */
public class PgmLoader extends AbstractFileLoader implements IMetaLoader {

	private String fileName;
	private Map<String, String> textMetadata = new HashMap<String, String>();
	
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

		AbstractDataset data = null;
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
				Utils.readBeShort(fi, (IntegerDataset) data, index);
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
		output.addDataset("Portable Grey Map", data);
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
		int index   = line.length()+1;
		String token;
		StringTokenizer s1 = new StringTokenizer(line);			
		token = s1.nextToken();
		textMetadata.put("MagicNumber", token);
		if (token.startsWith("P5")) {
			if (!s1.hasMoreTokens()) {
				line = br.readLine();
				index += line.length()+1;
				s1 = new StringTokenizer(line);	
			}
			token = s1.nextToken();
			textMetadata.put("Width", token);
			width = Integer.parseInt(token);
			if (!s1.hasMoreTokens()) {
				line = br.readLine();
				index += line.length()+1;
				s1 = new StringTokenizer(line);	
			}
			token = s1.nextToken();
			textMetadata.put("Height", token);
			height = Integer.parseInt(token);
			if (!s1.hasMoreTokens()) {
				line = br.readLine();
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
	public void loadMetaData(final IMonitor mon) throws Exception {

		final BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		try {
		    readMetaData(br, mon);
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
			public Collection<String> getDataNames() {
				return Collections.unmodifiableCollection(Arrays.asList(new String[]{"Portable Grey Map"}));
			}
		};
	}
	
	public String getHeaderValue(String key) {
		return textMetadata.get(key);	
	}

}
