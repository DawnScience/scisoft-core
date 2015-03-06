/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads a SRS data files
 * <p>
 * <b>Note</b>: the metadata from this loader is left as strings
 */
public class SRSLoader extends AbstractFileLoader implements IFileSaver {
	
	protected static final Logger logger = LoggerFactory.getLogger(SRSLoader.class);

	protected Map<String, String> textMetadata = new HashMap<String, String>();
	protected List<String> extraHeaders = new ArrayList<String>();
	
	private boolean storeStringValues = false;
	private boolean useImageLoaderForStrings = true;

	public SRSLoader() {
	}
	
	/**
	 * @param FileName
	 */
	public SRSLoader(String FileName) {
		fileName = FileName;
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		textMetadata.clear();
		extraHeaders.clear();
	}

	/**
	 * 
	 * @return if columns containing strings are added to the DataHolder as a StringDataSet. 
	 *         Retrieve from DataHolder using getLazyDataSet
	 *         
	 */
	public boolean isStoreStringValues() {
		return storeStringValues;
	}

	public void setStoreStringValues(boolean storeStringValues) {
		this.storeStringValues = storeStringValues;
	}

	
	/**
	 * 
	 * @return if columns containing strings that point to image file are treated as such and added to DataHolder
	 * 
	 */	
	public boolean isUseImageLoaderForStrings() {
		return useImageLoaderForStrings;
	}

	public void setUseImageLoaderForStrings(boolean useImageLoaderForStrings) {
		this.useImageLoaderForStrings = useImageLoaderForStrings;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	private static final Pattern SPLIT_REGEX = Pattern.compile("\\s+");
	private static final Pattern NUMBER_REGEX = Pattern.compile("^[-+]?[\\d]*\\.?\\d+.*");

	private static final String QUOTE_CHAR = "\"";

	/**
	 * Function that loads in the standard SRS datafile
	 * 
	 * @return The package which contains the data that has been loaded
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		
		// first instantiate the return object.
		DataHolder result = new DataHolder();

		// then try to read the file given
		LineNumberReader in = null;
		
		try {
			in = new LineNumberReader(new FileReader(fileName));
			String dataStr;
			// an updated header reader grabs all the metadata
			readMetadata(in, mon);

			// read in the names of the different datasets which will be needed
			List<String> vals = readColumnHeaders(in);
			
			List<?> [] columns = new List<?>[vals.size()];

			// now add the data to the appropriate vectors
			int count = 0;
			while ((dataStr = in.readLine()) != null) {
				if (!monitorIncrement(mon)) {
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}
				dataStr = dataStr.trim();
				if (NUMBER_REGEX.matcher(dataStr).matches()) {
					if (!loadLazily) {
						parseColumns(SPLIT_REGEX.split(dataStr), columns);
					}
					count++;
				} else {
					// more metadata?
					in.reset();
					readMetadata(in, mon);
					in.readLine(); // throw away line
				}
				in.mark(MARK_LIMIT);
			}

			if (loadLazily) {
				for (String n : vals) {
					result.addDataset(n, createLazyDataset(n, -1, new int[] {count}, new SRSLoader(fileName)));
				}
			} else {
				convertToDatasets(result, vals, columns, isStoreStringValues(), isUseImageLoaderForStrings(), (new File(this.fileName)).getParent());
			}
			if (result.toLazyMap().isEmpty()) throw new Exception("Cannot parse "+fileName+" into datasets!");

			if (loadMetadata) {
				createMetadata();
				result.setMetadata(metadata);
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("SRSLoader.loadFile exception loading  " + fileName, e);
		} finally {
			try {
				if (in!=null) in.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("Cannot read file", e);
			}
			
		}

		return result;
	}

	private List<String> readColumnHeaders(LineNumberReader in) throws IOException {
		String headStr = in.readLine();
		if (headStr == null)
			throw new IOException("End of file reached too soon");

		headStr = headStr.trim(); // remove whitespace to prevent the following split on white
		String[] vals = SPLIT_REGEX.split(headStr);

		List<String> names = new ArrayList<String>();
		String quote = null;
		for (int i = 0; i < vals.length; i++) { // remove quotes
			String n = vals[i].trim();
			if (quote != null) {
				if (n.endsWith(QUOTE_CHAR)) {
					quote += " " + n.substring(0, n.length()-QUOTE_CHAR.length());
					names.add(quote);
					quote = null;
				} else {
					quote += " " + n;
				}
			} else {
				if (n.startsWith(QUOTE_CHAR)) {
					quote = n.substring(QUOTE_CHAR.length());
				} else {
					names.add(n);
				}
			}
		}
		// TODO check quote is null
		datasetNames.clear();
		datasetNames.addAll(names);
		dataShapes.clear();
		return names;
	}

	/**
	 * Parse columns into lists
	 * @param data
	 * @param columns
	 * @throws ScanFileHolderException
	 */
	@SuppressWarnings("unchecked")
	protected void parseColumns(String[] data, List<?>[] columns) throws ScanFileHolderException {
		int cols = data.length;
		if (cols > columns.length) {
			cols = columns.length;
		}

		for (int i = 0; i < cols; i++) {
			
			String text = data[i];
			if (columns[i] != null) {
				List<?> list = columns[i];
				Object first = list.get(0);
				if (first instanceof Number) {
					List<Number> listN = (List<Number>) list;
					listN.add(Utils.parseValue(text));
				} else if (first instanceof String) {
					List<String> listN = (List<String>) list;
					listN.add(text);
				} else {
					throw new ScanFileHolderException("Type unknown");
				}
			} else {
				Number parseValue = Utils.parseValue(text);
				if (parseValue != null) {
					columns[i] = new ArrayList<Number>();
					((List<Number>) columns[i]).add(parseValue);
				} else {
					columns[i] = new ArrayList<String>();
					// we need to add a value in so that test of type for future lines detect a String type
					((List<String>) columns[i]).add(text);
				}
			}
		}

	}

	/**
	 * Create all the datasets (1D)
	 * @param holder
	 * @param names column headings
	 * @param columns array of lists of data
	 * @param storeStrings
	 * @param useImageLoader
	 */
	protected final void convertToDatasets(DataHolder holder, List<String> names, List<?>[] columns, boolean storeStrings, boolean useImageLoader, String file_directory) {
		for (int i = 0, imax = names.size(); i < imax; i++) {
			if (columns[i] != null) {
				String name = names.get(i);
				final Dataset ds = DatasetFactory.createFromObject(columns[i]);
				ds.setName(name);
				if (ds.getDtype() == Dataset.STRING) {
					StringDataset sds = (StringDataset) ds;
					if (storeStrings) {
						holder.addDataset(name, ds);
					}
					if (useImageLoader) {
						ImageStackLoaderEx loader;
						try {
							String[] oldpaths = sds.getData();
							String[] paths = new String[oldpaths.length];
							for (int j = 0; j < paths.length ; j++) {
								if(!(new File(oldpaths[j])).exists())
									paths[j] = (new File(file_directory, oldpaths[j])).getAbsolutePath();
								else
									paths[j] = oldpaths[j];
							}
							loader = new ImageStackLoaderEx(sds.getShape(), paths);
							name += "_image";
							LazyDataset lazyDataset = new LazyDataset(name, loader.getDtype(), loader.getShape(), loader);
							holder.addDataset(name, lazyDataset);
							if (dataShapes!=null) dataShapes.put(name, lazyDataset.getShape());
						} catch (Exception ex) {
							logger.warn("Unable to treat " + sds.getAbs(0) + " as an image file", ex);
						}
					}
				} else {
					holder.addDataset(name, ds);
				}
			}
		}
	}

	private static final String EQUAL = "=";
	private static final int MARK_LIMIT = 1024;

	protected void readMetadata(LineNumberReader in, IMonitor mon) throws ScanFileHolderException {
		
		textMetadata.clear();
		
		// handling metadata in the file header
		try {
			String line;
			in.setLineNumber(0);
			in.mark(MARK_LIMIT);

			while (true) {
				if (!monitorIncrement(mon)) {
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}
				line = in.readLine();
				if (line == null || line.contains("&END")) {
					return;
				}
				if (line.length() == 0) {
					continue;
				}
				if (line.contains("MetaDataAtStart")) { // stop at end of header
					String[] bits = line.split("</?MetaDataAtStart>");
					if (bits.length > 0) {
						for (String s : bits) {
							if (s.contains(EQUAL)) {
								parseString(s);
							} else {
								extraHeaders.add(s);
							}
						}
						continue;
					}
				}
				
				if (line.contains(EQUAL)) {
					parseString(line);
				} else {
					if (NUMBER_REGEX.matcher(line).matches()) {
						int l = in.getLineNumber(); // backtrack to line before last line
						if (l > 1) l -= 2;
						else l = 0;
						in.reset();
						for (int i = 0; i < l; i++) {
							in.readLine();
						}
						return;
					}
					extraHeaders.add(line);
				}
			}
		} catch (IOException e) {
			logger.error("Problem parsing header of SRS file {}", fileName);
			throw new ScanFileHolderException("There was a problem parsing header information", e);
		}
	}

	private void parseString(String line) {
		String key = null;
		String value = line.trim();
		int i;
		
		while ((i = value.indexOf(EQUAL)) >= 0) {
			key = value.substring(0, i);
			value = value.substring(i + 1).trim();
			i = findStringAndAddMetadata(key, value);
			if (i < 0)
				break;

			if (i >= value.length()) {
				key = null;
				break;
			}

			if (value.charAt(i) == ',') // drop comma
				i++;
			value = value.substring(i).trim();
			key = null;
		}
		if (key != null) {
			findStringAndAddMetadata(key, value);
		}
	}

	private int findStringAndAddMetadata(String key, String value) {
		String strippedValue = extractQuotedString(value, '\'');
		if (strippedValue == null) {
			strippedValue = extractQuotedString(value, '"');
			if (strippedValue == null) {
				int i = value.indexOf(',');
				if (i >= 0) {
					strippedValue = value.substring(0, i);
				}
			}
		}
		if (strippedValue == null) {
			textMetadata.put(key, value);
			return value.length();
		}

		textMetadata.put(key, strippedValue);
		return value.indexOf(strippedValue) + strippedValue.length() + 1;
	}

	private String extractQuotedString(String line, char quote) {
		int start = findQuoteChar(line, quote, -1);
		if (start < 0)
			return null;

		int end = findQuoteChar(line, quote, start);
		if (end < 0) { 
			logger.warn("String was not quoted correctly: {} from {} in {}", new Object[] {line.substring(start), line, fileName});
			return line.substring(start+1);
		}

		return line.substring(start+1, end);
	}

	private int findQuoteChar(String line, char quote, int start) {
		do {
			start = line.indexOf(quote, ++start);
			if (start <= 0)
				break;
		} while (line.charAt(start-1) == '\\');
		return start;
	}

	/**
	 * Function which saves to the basic SRS format with no header, but the correct Header tags
	 * 
	 * @param dh
	 *            The data holder containing the data to be saved out
	 * @throws ScanFileHolderException
	 */
	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

			writeHeader(out, dh);

			int imax = dh.size();

			// now write out all of the data
			int rows = dh.getDataset(0).getSize();
			Dataset[] datasets = new Dataset[imax];
			for (int i = 0; i < imax; i++) {
				datasets[i] = DatasetUtils.convertToDataset(dh.getDataset(i));
			}

			for (int j = 0; j < rows; j++) {
				for (int i = 0; i < imax; i++) {
					try {
						out.write(datasets[i].getElementDoubleAbs(j) + "\t");
					} catch (ArrayIndexOutOfBoundsException e) {
						out.write(0.0 + "\t"); // add in zeros if other datasets have less elements
					}
				}
				out.write("\n");
			}

			out.close();

		} catch (Exception e) {
			logger.error("Problem saving SRS file {}", fileName);
			throw new ScanFileHolderException("SRSLoader.saveFile exception saving to " + fileName, e);
		}
	}

	protected void writeHeader(BufferedWriter out, IDataHolder dh) throws IOException {
		out.write("&SRS\n");
		writeMetadata(out, dh);
		out.write("&END\n");

		// now write out the data names
		int imax = dh.size();
		for (int i = 0; i < imax; i++) {
			String name = dh.getName(i).trim();
			if (name.indexOf(' ') >= 0) {
				name = QUOTE_CHAR + name + QUOTE_CHAR;
			}
			out.write(name + "\t");
		}
		out.write("\n");
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	protected void writeMetadata(BufferedWriter out, @SuppressWarnings("unused") IDataHolder holder) throws IOException {
		String[] metadataKeys = getKeysToSave();
		if (!textMetadata.isEmpty() && metadataKeys != null) {
			for (String k : metadataKeys) {
				Object value = textMetadata.get(k);
				if (value == null) {
					if (textMetadata.containsKey(k)) {
						logger.warn("Metadata item (key {}) was null", k);
					} else {
						logger.warn("Metadata key {} is not contained in list", k);
					}
				} else {
					out.write(k + "=" + value.toString() + "\n");
				}
			}
		}
		if (!extraHeaders.isEmpty()) {
			for (String each : extraHeaders) {
				out.write(each + "\n");
			}
		}
	}

	/**
	 * @return array of keys of metadata items to save 
	 */
	protected String[] getKeysToSave() {
		return null;
	}
	
	protected List<String> datasetNames      = new ArrayList<String>(7);
	protected Map<String,int[]>   dataShapes = new HashMap<String,int[]>(7);

	protected void createMetadata() {
		metadata = new ExtendedMetadata(new File(fileName));
		metadata.setMetadata(textMetadata);

		for (String n : datasetNames) {
			metadata.addDataInfo(n, null);
		}

		for (Entry<String, int[]> e : dataShapes.entrySet()) {
			metadata.addDataInfo(e.getKey(), e.getValue());
		}
	}
}
