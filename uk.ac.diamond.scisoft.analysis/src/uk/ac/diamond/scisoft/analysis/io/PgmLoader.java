/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.Metadata;

/**
 * This class should be used to load .pgm datafiles (Portable Grey Map)
 * into the ScanFileHolder object.
 * <p>
 * <b>Note</b>: the header data from this loader is left as strings
 */
public class PgmLoader extends AbstractFileLoader {

	private Map<String, String> textMetadata = new HashMap<String, String>();
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
	protected void clearMetadata() {
		metadata = null;
		textMetadata.clear();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {

		ILazyDataset data = null;
		DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);

			BufferedReader br = new BufferedReader(new FileReader(f));
			
			int[] vals = readMetaData(br, mon, textMetadata);
			int index  = vals[0];
			int width  = vals[1];
			int height = vals[2];
			int maxval = vals[3];

			if (loadLazily) {
				data = createLazyDataset(new LazyLoaderStub() {
					@Override
					public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
						try {
							Dataset data = loadDataset(fileName);
							return data == null ? null : data.getSliceView(slice);
						} catch (ScanFileHolderException e) {
							throw new IOException(e);
						}
					}
				}, DEF_IMAGE_NAME, maxval < 256 ? ShortDataset.class: IntegerDataset.class, height, width);
			} else {
				// Now read the data
				if (maxval < 256) {
					data = DatasetFactory.zeros(ShortDataset.class, height, width);
					Utils.readByte(fi, (ShortDataset) data, index);
				} else {
					data = DatasetFactory.zeros(IntegerDataset.class, height, width);
					Utils.readBeShort(fi, (IntegerDataset) data, index, false);
				}
				data.setName(DEF_IMAGE_NAME);
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
		if (loadMetadata) {
		    createMetadata();
			data.setMetadata(metadata);
			output.setMetadata(metadata);
		}
		return output;
	}

	private static Dataset loadDataset(String fileName) throws ScanFileHolderException {
		IDataHolder holder = LoaderFactory.fetchData(fileName, false);
		if (holder != null) {
			IDataset data = holder.getDataset(DEF_IMAGE_NAME);
			if (data != null) {
				return DatasetUtils.convertToDataset(data);
			}
		}
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);

			BufferedReader br = new BufferedReader(new FileReader(f));
			
			int[] vals = readMetaData(br, null, new HashMap<String, String>());
			int index  = vals[0];
			int width  = vals[1];
			int height = vals[2];
			int maxval = vals[3];
			
			Dataset data;
			// Now read the data
			if (maxval < 256) {
				data = DatasetFactory.zeros(ShortDataset.class, height, width);
				Utils.readByte(fi, (ShortDataset) data, index);
			} else {
				data = DatasetFactory.zeros(IntegerDataset.class, height, width);
				Utils.readBeShort(fi, (IntegerDataset) data, index, false);
			}
			data.setName(DEF_IMAGE_NAME);

			if (holder == null) {
				holder = new DataHolder();
				holder.setLoaderClass(PgmLoader.class);
				holder.setFilePath(fileName);
				LoaderFactory.cacheData(holder);
			}
			holder.addDataset(DEF_IMAGE_NAME, data);
			return data;
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
	}
	
	private static int[] readMetaData(BufferedReader br, IMonitor mon, Map<String, String> textMetadata) throws Exception {
		int width  = 0;
		int height = 0;
		int maxval = 0;

		textMetadata.clear();
		if (!monitorIncrement(mon)) {
			throw new ScanFileHolderException("Loader cancelled during reading!");
		}

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

	private void createMetadata() {
		metadata = new Metadata();
		metadata.initialize(textMetadata);
		metadata.setFilePath(fileName);
		metadata.addDataInfo(DATA_NAME, Integer.parseInt(textMetadata.get("Height")), Integer.parseInt(textMetadata.get("Width")));
	}
	
	public String getHeaderValue(String key) {
		return textMetadata.get(key);	
	}
}
