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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Should act like the SRSLoader, but for Xas format ascii files. This is because the associated editor subclasses the
 * SRSEditor.
 */
public class XasAsciiLoader extends SRSLoader {

	private static final Logger logger = LoggerFactory.getLogger(XasAsciiLoader.class);

	private static final String COMMENT_PREFIX = "#";

	public XasAsciiLoader(String fileName) {
		setFile(fileName);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		// then try to read the file given
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));
			String dataStr;
			String previousHeaderLine = "";
			boolean readingHeader = true;
			boolean readingFooter = false;
			List<?>[] columnData = null;
			int count = 0;
			while ((dataStr = reader.readLine()) != null) {

				// ignore blank
				if (dataStr.isEmpty()) {
					continue;
				}

				// first block of commented out lines are the header
				if (dataStr.startsWith(COMMENT_PREFIX) && readingHeader) {
					readHeaderLine(dataStr);
					previousHeaderLine = dataStr;
					continue;
				}
				readingHeader = false;

				// next block of commented out lines after any break in comments will be the footer
				if (dataStr.startsWith(COMMENT_PREFIX) && !readingHeader) {
					readingFooter = true;
					readFooterLine(dataStr);
					continue;
				}

				// if get here then its data
				if (readingFooter) {
					// should not get here after reading a block of commented out lines for a second time: invalid
					// format
					throw new ScanFileHolderException("Cannot read file");
				}

				// the first time we get here, the previous line should have been the header
				if (columnData == null && datasetNames.size() == 0) {
					columnData = parseHeaderString(previousHeaderLine);
				}

				if (columnData != null) {
					if (!loadLazily)
						parseColumns(splitLine(dataStr.trim()), columnData);
					count++;
				} else {
					logger.warn("Dropped possible data owing to lack of column headers: {}", dataStr);
				}
			}

			DataHolder result = new DataHolder();
			if (loadLazily) {
				for (String n : datasetNames) {
					result.addDataset(n, createLazyDataset(new XasAsciiLoader(fileName), n, null, count));
				}
			} else {
				try {
					convertToDatasets(result, datasetNames, columnData, isStoreStringValues(), isUseImageLoaderForStrings(), (new File(this.fileName)).getParent());
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
			if (loadMetadata) {
				createMetadata();
				result.setMetadata(metadata);
			}

			return result;
		} catch (Exception e) {
			throw new ScanFileHolderException("XasAsciiLoader.loadFile exception loading  " + fileName, e);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("Cannot read file", e);
			}
		}
	}

	private List<?>[] parseHeaderString(String previousHeaderLine) {
		// remove leading hash
		previousHeaderLine = previousHeaderLine.substring(1).trim();
		String parts[] = splitLine(previousHeaderLine);
		datasetNames.clear();
		datasetNames.addAll(Arrays.asList(parts));
		return new List<?>[parts.length];
	}

	private String[] splitLine(String line) {
		return line.split("[\t ]+");
	}

	private void readFooterLine(String dataStr) {
		// do not differentiate for the moment
		readHeaderLine(dataStr);
	}

	private void readHeaderLine(String dataStr) {
		// remove leading hash
		dataStr = dataStr.trim().substring(1);
		int colonLoc = dataStr.indexOf(":");
		if (colonLoc == -1) {
			// must be a comment without any metadata
			return;
		}
		// split by colon
		String key = dataStr.substring(0, colonLoc);
		String value = dataStr.substring(colonLoc+1);
		textMetadata.put(key.trim(), value.trim());
	}

	@Override
	protected void writeHeader(BufferedWriter out, IDataHolder dh) throws IOException {
		// now write out the data names
		out.write(COMMENT_PREFIX);
		int imax = dh.size();
		for (int i = 0; i < imax; i++) {
			out.write(dh.getName(i) + "\t");
		}
		out.write("\n");
	}
}
