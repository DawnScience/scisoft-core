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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic class that loads in data from the Oxford Instruments Ruby detector
 */
public class CrysalisLoader extends AbstractFileLoader implements IFileSaver {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(CrysalisLoader.class);

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

	@Override
	protected void clearMetadata() {
		metadata = null;
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

		ILazyDataset data = null;
		BufferedReader br = null;
		File f = null;
		FileInputStream fi = null;
		// Try to load the file
		try {

			f = new File(fileName);
			br = new BufferedReader(new FileReader(f));
			br.mark(8192);
			char[] magic = new char[8];
			br.read(magic);
			if (!new String(magic).startsWith("OD")) {
				throw new ScanFileHolderException("Not a valid Crysalis file");
			}
			br.reset();
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
			textMetadata.put("nx", "" + nx);

			p = Pattern.compile(".*NY= *(\\d+).*");
			m = p.matcher(lines[2]);
			if (!m.matches()) {
				throw new IllegalArgumentException("NY not found  - " + fileName);
			}
			int ny = Integer.valueOf(m.group(1));
			textMetadata.put("ny", "" + ny);

			p = Pattern.compile("NHEADER= *(\\d+).*");
			m = p.matcher(lines[3]);
			if (!m.matches()) {
				throw new IllegalArgumentException("NHEADER not found  - " + fileName);
			}
			final int nheader = Integer.valueOf(m.group(1));
			textMetadata.put("nheader", "" + nheader);

			fi = new FileInputStream(f);
			fi.skip(nheader);

			int[] shape = new int[] { nx, ny };

			if (loadLazily) {
				data = createLazyDataset(DEF_IMAGE_NAME, Dataset.INT32, shape, new LazyLoaderStub() {
					@Override
					public IDataset getDataset(IMonitor mon, SliceND slice)
							throws Exception {
						Dataset data = loadDataset(fileName, nheader, slice.getSourceShape());
						return data.getSliceView(slice);
					}
				});
			} else {
				data = new IntegerDataset(shape);
				Utils.readLeInt(fi, (IntegerDataset) data, 0);
			}
			data.setName(DEF_IMAGE_NAME);

			if (loadMetadata)
				createMetadata(data);
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
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					// do nothing
				}
				br = null;
			}
		}

		// create the holder and then put to the output.
		DataHolder output = new DataHolder();

		output.addDataset(DEF_IMAGE_NAME, data);
		if (loadMetadata) {
			data.setMetadata(metadata);
			output.setMetadata(metadata);
		}

		return output;

	}

	private static Dataset loadDataset(String fileName, int header, int[] shape) throws ScanFileHolderException {
		IDataHolder holder = LoaderFactory.fetchData(fileName, false);
		if (holder != null) {
			return (Dataset) holder.getDataset(0);
		}
		Dataset data = null;
		FileInputStream fi = null;
		// Try to load the file
		try {

			fi = new FileInputStream(new File(fileName));

			fi.skip(header);

			data = new IntegerDataset(shape);
			Utils.readLeInt(fi, (IntegerDataset) data, 0);
			data.setName(DEF_IMAGE_NAME);
			holder = new DataHolder();
			holder.setFilePath(fileName);
			holder.addDataset(DEF_IMAGE_NAME, data);
			LoaderFactory.cacheData(holder);
			return data;
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
	}

	private void createMetadata(ILazyDataset data) {
		metadata = new Metadata(textMetadata);
		metadata.addDataInfo(DEF_IMAGE_NAME, data.getShape());
		metadata.setFilePath(fileName);
	}

	/**
	 * Fulfils the interface for the save function
	 * 
	 * @param dh
	 */
	@Override
	public void saveFile(IDataHolder dh) {
		File f = null;
		FileOutputStream fo = null;
		try {
			f = new File(fileName);
			fo = new FileOutputStream(f);
			IDataset ds = dh.getDataset(0);
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
}
