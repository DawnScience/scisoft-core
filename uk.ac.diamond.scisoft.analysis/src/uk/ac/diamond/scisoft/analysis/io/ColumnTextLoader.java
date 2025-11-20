/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loader to read in 1D data as columns of text
 * 
 * Comments are lines starting with "#"
 */
public class ColumnTextLoader extends AbstractFileLoader {
	private static final Logger logger = LoggerFactory.getLogger(ColumnTextLoader.class);

	public static final char COMMA = ',';
	public static final char SEMICOLON = ';';
	public static final char SPACE = ' ';
	public static final char TAB = '\t';

	static final String COMMENT = "#";

	private static final Pattern NUMBERS = Pattern.compile("^" + Utils.FLOATING_POINT_NUMBER + ".*");

	private char delimiter = TAB;
	private boolean trim;

	private List<String> headers;
	private String[] headings;

	private int columns;

	ColumnTextLoader() {
	}

	/**
	 * A loader for tab-separated values
	 * @param filename
	 */
	public ColumnTextLoader(String filename) {
		fileName = filename;
	}

	/**
	 * @param filename
	 * @param space if true then use a space character as a delimiter
	 */
	public ColumnTextLoader(String filename, boolean space) {
		fileName = filename;
		delimiter = space ? SPACE : TAB;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Set whether to trim values
	 * @param trim if true then remove spaces from start and end of values
	 */
	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	/**
	 * @param br
	 * @param ignoreComments
	 * @return next line trimmed or null
	 * @throws IOException
	 */
	protected static String getNextLine(BufferedReader br, final boolean ignoreComments) throws IOException {
		String l = null;
		do {
			l = br.readLine();
			if (l == null) {
				return l;
			}
			l = l.trim();
		} while (l.isEmpty() || (ignoreComments && l.startsWith(COMMENT)));

		return l;
	}

	protected String readHeader(BufferedReader br, DataHolder dh, String l) throws IOException {
		if (NUMBERS.matcher(l).matches()) {
			headings = null;
			columns = 0;
			return l;
		}

		headers = new ArrayList<>();
		do { // consume until first line that starts with numbers
			if (l.startsWith(COMMENT)) {
				l = l.substring(1);
			}

			headers.add(l);
			l = getNextLine(br, false);
		} while (!NUMBERS.matcher(l).matches());

		String first = headers.get(0);
		if (first.startsWith("Sep=")) { // Excel supports this
			headers.remove(0);
			delimiter = first.charAt(4);
			logger.debug("Found delimiter specified in file as '{}'", delimiter);
		}

		int n = headers.size();
		if (n > 0) { // attempt to find column headings from last line
			String last = headers.remove(n - 1);
			char sep = delimiter != COMMA && last.indexOf(COMMA) >= 0 ? COMMA : delimiter;
			List<String> header = Utils.splitDoubleQuoted(last, sep, trim);
			columns = header.size();
			headings = new String[columns];
			for (int i = 0; i < columns; i++) {
				String h = header.get(i).trim();
				if (!h.isEmpty()) {
					headings[i] = Utils.doubleUnquote(h);
				}
			}
			makeUnique(headings);
		}

		return l;
	}

	private static void makeUnique(String[] hdgs) {
		int l = hdgs.length;
		for (int i = 0; i < l; i++) {
			String h = hdgs[i];
			if (h != null) {
				int k = 1;
				for (int j = i + 1; j < l; j++) {
					String t = hdgs[j];
					while (h.equals(t)) {
						t = String.format("%s_%d", h, k++);
					}
					hdgs[j] = t;
				}
			}
		}
	}

	private void createMetadata(DataHolder dh) {
		Map<String, Serializable> hdrs = new LinkedHashMap<>();
		metadata = new Metadata(hdrs);
		for (Entry<String, int[]> e : dh.getDatasetShapes().entrySet()) {
			metadata.addDataInfo(e.getKey(), e.getValue());
		}

		if (headers != null) {
			parseHeaders(hdrs, headers);
		}

		dh.setMetadata(metadata);
	}

	/**
	 * Override this to customize header line parsing
	 * @param hdrs
	 * @param hdrLines
	 */
	protected void parseHeaders(Map<String, Serializable> hdrs, List<String> hdrLines) {
		int i = 0;
		for (String h : hdrLines) {
			hdrs.put(String.format("%d", i++), h);
		}
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
			String l = getNextLine(br, false);
			if (l == null) {
				logger.error("Cannot find any lines with data");
				throw new ScanFileHolderException("Cannot find any lines with data");
			}

			l = readHeader(br, dh, l);
			if (l == null) {
				logger.error("Cannot find any lines after header with data");
				throw new ScanFileHolderException("Cannot find any lines after header with data");
			}

			// set up arrays
			List<List<Object>> data = new ArrayList<>();

			if (!loadLazily) {
				for (int i = 0; i < columns; i++) {
					data.add(new ArrayList<>());
				}
			}

			int r = 0; // row number
			Object[] row = new Object[columns];
			do {
				if (loadLazily) {
					if (columns == 0) {
						columns = Utils.splitDoubleQuoted(l, delimiter, trim).size();
					}
				} else {
					List<String> values = Utils.splitDoubleQuoted(l, delimiter, trim);
					int cols = values.size();
					if (cols > columns) {
						if (columns != 0) {
							logger.warn("Ragged rows: data row {} is longer than {}", r, columns);
						}
						for (int n = columns; n < cols; n++) {
							List<Object> c = new ArrayList<>();
							for (int i = 0; i < r; i++) {
								c.add(null);
							}
							data.add(c);
						}
						columns = cols;
						row = new Object[columns];
					} else if (cols < columns) {
						logger.warn("Ragged rows: data row {} is shorter than {}", r, columns);
					}
	
					Utils.parseValues(values.toArray(new String[cols]) , row);
					for (int i = 0; i < cols; i++) {
						data.get(i).add(row[i]);
					}
				}

				l = getNextLine(br, true);
				r++;
			} while (l != null);

			// make datasets including ragged columns
			for (int i = 0; i < columns; i++) {
				String h;
				if (headings == null || headings.length < i || headings[i] == null) {
					h = String.format(ColumnTextSaver.COLUMN_HEADING, i);
				} else {
					h = headings[i];
				}

				ILazyDataset d;
				if (loadLazily) {
					d = createLazyDataset(new ColumnTextLoader(fileName), h, null, r);
				} else {
					List<Object> c = data.get(i);
					for (int j = c.size() - 1; j > 0; j--) { // trim columns
						Object o = c.get(j);
						if (o == null || (o instanceof String && ((String) o).isEmpty())) {
							c.remove(j);
						} else {
							break;
						}
					}

					d = DatasetFactory.createFromList(c);
					// TODO handle string datasets for images...
				}
				d.setName(h);
				dh.addDataset(h, d);
			}

			if (loadMetadata) {
				createMetadata(dh);
			}
		} catch (FileNotFoundException e) {
			logger.error("Cannot load file", fileName);
			throw new ScanFileHolderException("Cannot load file " + fileName, e);
		} catch (IOException e) {
			logger.error("Cannot read file", fileName);
			throw new ScanFileHolderException("Cannot read file " + fileName, e);
		}

		return dh;
	}

	@Override
	protected void clearMetadata() {
	}
}
