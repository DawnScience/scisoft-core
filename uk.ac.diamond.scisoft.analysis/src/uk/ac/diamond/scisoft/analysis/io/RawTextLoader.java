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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

/**
 * Class that loads 1D or 2D data by reading files as tab-delimited ACSII input
 */
public class RawTextLoader extends AbstractFileLoader {

	private String fileName = "";

	/**
	 * @param filename
	 */
	public RawTextLoader(String filename) {
		fileName = filename;
	}

	private static final Pattern SPLIT_REGEX = Pattern.compile("\\s+");

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String l = br.readLine();
			if (l == null) {
				throw new ScanFileHolderException("Cannot find any lines with data");
			}
			while (l.length() == 0) {
				l = br.readLine();
				if (l == null) {
					throw new ScanFileHolderException("Cannot find any lines with data");
				}
			}

			String[] values = SPLIT_REGEX.split(l.trim());
			List<Object[]> rows = new ArrayList<Object[]>();
			rows.add(parseRow(values));

			l = br.readLine();
			while (l != null) {
				if (l.length() != 0) {
					rows.add(parseRow(SPLIT_REGEX.split(l.trim())));
				}
				l = br.readLine();
			}

			final Dataset ds = DatasetFactory.createFromObject(rows);
			ds.squeeze(); // convert Nx1 to 1D dataset
			DataHolder dh = new DataHolder();
			dh.addDataset(DEF_IMAGE_NAME, ds);
			return dh;
		} catch (FileNotFoundException fnf) {
			throw new ScanFileHolderException("Cannot load file", fnf);
		} catch (Exception e) {
			throw new ScanFileHolderException("Cannot read file", e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * Parse row into an array
	 * @param data
	 */
	protected static Object[] parseRow(String[] data) {
		int cols = data.length;
		Object[] row = new Object[cols];

		for (int i = 0; i < cols; i++) {
			String text = data[i];
			Number parseValue = Utils.parseValue(text);
			if (parseValue != null) {
				row[i] = parseValue;
			} else {
				row[i] = text;
			}
		}

		return row;
	}
}
