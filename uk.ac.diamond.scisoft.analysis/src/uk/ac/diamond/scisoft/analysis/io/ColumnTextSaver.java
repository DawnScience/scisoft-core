/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that saves 1D or 2D data from DataHolder by writing the output as delimited text output
 */
public class ColumnTextSaver implements IFileSaver {
	protected static final Logger logger = LoggerFactory.getLogger(ColumnTextSaver.class);

	public static final String COLUMN_HEADING = "Column_%d";

	private String fileName = "";
	protected char delimiter = '\t';

	protected String cellFormat;

	private String[] preHeading = null;
	private String[] headings = null;

	public static final char COMMA = ColumnTextLoader.COMMA;
	public static final char SEMICOLON = ColumnTextLoader.SEMICOLON;
	public static final char SPACE = ColumnTextLoader.SPACE;
	public static final char TAB = ColumnTextLoader.TAB;

	/**
	 * Takes either several 1D datasets or a single 2D dataset from a data holder and output a height x width array called 'filename'.txt.
	 * @param filename
	 */
	public ColumnTextSaver(String filename) {
		fileName = filename;
	}

	/**
	 * Set format string for cell (see {@link String#format(String, Object...)})
	 * @param cellFormat
	 */
	public void setCellFormat(String cellFormat) {
		this.cellFormat = cellFormat;
	}

	/**
	 * @return character that delimits or separates each column
	 */
	public char getDelimiter() {
		return delimiter;
	}

	/**
	 * Set character that delimits or separates each column
	 * @param delimiter
	 */
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Set header lines before column headers
	 * @param headers
	 */
	public void setHeaders(String... headers) {
		this.preHeading = headers;
	}

	/**
	 * Set heading on each column
	 * @param headings
	 */
	public void setHeadings(String... headings) {
		this.headings = headings;
	}

	/**
	 * Set heading on each column
	 * @param hdrs
	 */
	public void setHeadings(List<String> hdrs) {
		this.headings = hdrs.toArray(new String[hdrs.size()]);
	}

	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		IDataset d = null;
		int i = 0;
		do {
			d = dh.getDataset(i++);
		} while (d == null && i < dh.size());
		if (d == null) {
			logger.warn("No datasets in holder so nothing created or saved");
			return;
		}

		d = d.getSliceView().squeezeEnds(); // 1st dataset
		d.setName(dh.getName(i - 1));
		int[] shape = d.getShape();
		if (shape.length > 2) {
			logger.error("Cannot save as first dataset {} has rank greater than 2", d.toString());
			throw new ScanFileHolderException("Cannot save as first dataset has rank greater than 2");
		}

		boolean is1D = shape.length < 2;
		boolean is0D = shape.length == 0;

		int rows = is0D ? 1 : shape[0];

		List<Dataset> data = new ArrayList<>();
		data.add(DatasetUtils.convertToDataset(d));
		while (i < dh.size()) {
			IDataset t = dh.getDataset(i++);
			if (t != null) {
				if (is1D) {
					t = t.getSliceView().squeezeEnds();
					t.setName(dh.getName(i - 1));
					if (t.getRank() > 1) {
						logger.error("Cannot save as ({}/{}) dataset {} has rank greater than 2", i - 1, dh.size(), t.toString());
						throw new ScanFileHolderException("Cannot save as a dataset has rank greater than 2");
					}
					rows = Math.max(rows, t.getSize());
					data.add(DatasetUtils.convertToDataset(t));
				} else {
					logger.error("First dataset was 2D so not saving {}", t.getName());
				}
			}
		}

		int columns = data.size();
		if (!is1D) {
			if (i > 1) {
				logger.warn("Saving first 2D dataset {} only (omitting {} datasets)", d.toString(), i - 1);
			}
			columns = shape[1];
		}

		File f = new File(fileName);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			writeHeader(bw, columns, data, is1D);

			Dataset t = is1D ? null : data.get(0);
			final SliceND s = t == null ? null : new SliceND(t.getShapeRef(), new Slice(1));
			for (int r = 0; r < rows; r++) {
				int last = columns - 1;
				if (t == null || s == null) { // 1D
					for (int c = 0; c < columns; c++) {
						t = data.get(c);
						if (r < t.getSize()) {
							if (t.getElementClass().equals(String.class)) {
								String text = is0D ? t.getString() : t.getString(r);
								int j = text.indexOf(delimiter);
								if (j >= 0) {
									text = Utils.doubleQuote(text);
								}
								bw.write(text);
							} else if (cellFormat != null) {
								bw.write(String.format(cellFormat, is0D ? t.getObject() : t.getObject(r)));
							} else {
								bw.write(is0D ? t.getString() : t.getString(r));
							}
						}
						if (c != last) {
							bw.write(delimiter);
						}
					}
				} else { // 2D
					s.setSlice(0, r, r + 1, 1);
					Dataset row = t.getSliceView(s);
					for (int c = 0; c < columns; c++) {
						if (t.getElementClass().equals(String.class)) {
							String text = row.getString(c);
							int j = text.indexOf(delimiter);
							if (j >= 0) {
								text = Utils.doubleQuote(text);
							}
							bw.write(text);
						} else if (cellFormat != null) {
							bw.write(String.format(cellFormat, row.getObject(c)));
						} else {
							bw.write(row.getString(c));
						}
						if (c != last) {
							bw.write(delimiter);
						}
					}
				}
				bw.newLine();
			}
		} catch (Exception e) {
			String msg = String.format("Error saving file '%s'", fileName);
			logger.error(msg, e);
			throw new ScanFileHolderException(msg, e);
		}
	}

	private void writeHeader(BufferedWriter writer, int columns, List<Dataset> data, boolean is1d) throws IOException {
		if (preHeading != null) {
			for (String h : preHeading) {
				if (!h.startsWith(ColumnTextLoader.COMMENT)) {
					writer.write(ColumnTextLoader.COMMENT);
				}
				writer.write(h);
				writer.newLine();
			}
		}
		int last = columns - 1;
		if (headings != null) {
			if (headings.length != columns) {
				logger.warn("There is a mismatch between the number of headings ({}) and data columns ({})", headings.length, columns);
			}
			writer.write(createRow(headings));
			writer.newLine();
		} else if (is1d) {
			for (int c = 0; c < columns; c++) {
				Dataset d = data.get(c);
				String n = d.getName();
				if (n == null || n.isEmpty()) {
					n = String.format(COLUMN_HEADING, c);
				}
				writer.write(n);
				if (c != last) {
					writer.write(delimiter);
				}
			}
			writer.newLine();
		}
	}

	/**
	 * Create row
	 * @param items
	 * @return delimited row of items
	 */
	public String createRow(String... items) {
		StringBuilder row = new StringBuilder();
		for (String n : items) {
			int j = n.indexOf(delimiter);
			if (j >= 0) {
				n = Utils.doubleQuote(n);
			}
			row.append(n);
			row.append(delimiter);
		}

		int last = Math.max(0, row.length() - 1);
		return row.substring(0, last);
	}
}
