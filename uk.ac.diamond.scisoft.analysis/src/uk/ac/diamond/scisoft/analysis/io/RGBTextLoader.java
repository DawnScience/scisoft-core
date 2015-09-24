/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * 
 * 
 * 
 * Example: 

row  column  Ar_xrf_unscaled  K_xrf_unscaled  Cl_xrf_unscaled  Ca_xrf_unscaled  Ce_xrf_unscaled  Ti_xrf_unscaled  V_xrf_unscaled  Fe_xrf_unscaled  Mn_xrf_unscaled  Cr_xrf_unscaled  Co_xrf_unscaled  Ni_xrf_unscaled  scatter  background  chisq
0 0 173.81963077 1.29729793554 3.83005477322 0.914347216879 1e-09 0.102392125737 1e-09 3.02543228179 1.10814733705 0.372624381661 2.08535828192 0.219508611323 347234.098112 8295.21358152 10324646.6928
0 1 175.211568719 1.44185575166 3.9178963535 1.09782950671 1e-09 0.0180421249378 1e-09 2.6340029543 0.931949998446 0.404540757423 1.83044414394 8.24120938324e-15 346870.094329 9077.29733961 10017632.8616
0 2 173.866160166 1.19868607109 4.15823207716 1.12900318722 0.0218866707022 0.113848032836 1e-09 2.61287145548 0.970297414369 0.711087984021 1.87973921098 3.1098903333e-15 346100.090776 8657.58050723 9855097.14912
0 3 175.909245543 1.45269758143 3.90135401274 0.824817101676 1e-09 1e-09 0.0128264719347 2.63339777667 0.866839798839 0.341574622009 1.74006191919 1e-09 345862.799776 9005.63367451 9295303.96962
0 4 175.324507563 1.34641889113 4.05894070693 1.02130759816 0.000660052754905 2.9899749731e-14 1.99698415027e-14 2.8069395633 0.800589203662 0.462781342019 1.95515582614 5.7532971166e-15 347806.06597 8962.5742507 9236537.43893
0 5 174.262427255 1.33230241079 3.99947378303 0.930469892923 4.77826315293e-15 4.83442510142e-15 1e-09 2.83546093296 0.774301910441 0.627571442702 1.89990374252 1e-09 346823.861814 8778.01932027 10066210.1721
0 6 174.588171405 1.18056536682 4.23086381067 0.930273127859 1e-09 1e-09 0.112975822957 2.80857449181 0.901617141473 0.653253841355 1.82982933528 1.02626380999e-14 346947.053695 8597.27212654 9736541.93031
0 7 173.682176313 1.84349653362 4.08356499358 0.73680699294 1e-09 1e-09 1e-09 2.87368647263 0.9616415351 0.587446970928 2.08057653539 3.42087936663e-15 344476.893761 8647.71542553 9471127.94004
0 8 174.832908074 1.47879967648 4.1298199776 1.22010002277 0.137646170711 0.0838558690521 1e-09 2.48996797639 0.509932259653 0.226104621076 1.7213561889 2.37906610497e-14 346664.501114 9438.97882247 10638211.1648
...

 */
public class RGBTextLoader extends CSVLoader {

	public RGBTextLoader() {
		super();
	}

	/**
	 * @param fileName
	 */
	public RGBTextLoader(final String fileName) {
		super(fileName);
	}

	@Override
	protected String getDelimiter() {
		return "\\s* \\s*";
	}

	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {

		// first instantiate the return object.
		DataHolder result = new DataHolder();

		// then try to read the file given
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			boolean readingFooter = false;

			String line = parseHeaders(in, null, mon);
			int columns = vals.size();
			if (columns == 0)
				throw new ScanFileHolderException("Cannot read header for data set names!");
			DATA: while (line != null) {
				if (!monitorIncrement(mon)) {
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}

				line = line.trim();
				if (!readingFooter && DATA.matcher(line).matches()) {

					if (line.startsWith("#")) {
						readingFooter = true;
						break DATA;
					}

					if (!loadLazily) {
						final String[] values = line.split(getDelimiter());
						if (values.length != columns) {
							throw new ScanFileHolderException("Data and header must be the same size!");
						}
						final Iterator<String> it = vals.keySet().iterator();
						for (String value : values) {
							vals.get(it.next()).add(Utils.parseDouble(value.trim()));
						}
					}
				}
				line = in.readLine();
			}
			return createRGBTextDataset(result);
		} catch (Exception e) {
			throw new ScanFileHolderException("RGBTextLoader.loadFile exception loading  " + fileName, e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("Cannot close stream from file  " + fileName, e);
			}
		}
	}

	/**
	 * Given row and colum in vals, return all the 2d datasets
	 * 
	 * @param result
	 * @return DataHolder
	 */
	private DataHolder createRGBTextDataset(DataHolder result) {
		List<Double> row = vals.get("row");
		List<Double> column = vals.get("column");
		double rmax = Collections.max(row);
		double cmax = Collections.max(column);
		for (final String n : vals.keySet()) {
			if (!n.equals("row") && !n.equals("column")) {
				int width = (int)rmax + 1;
				int height = (int) cmax + 1;
				DoubleDataset data = new DoubleDataset(new int[] { width, height });
				data.setName(n);
				int idx = 0;
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						data.setItem(vals.get(n).get(idx), i, j);
						idx++;
					}
				}
				result.addDataset(n, data);
			}
		}
		return result;
	}

	@Override
	protected void createValues(Map<String, List<Double>> v, String header) {
		// Two or more spaces or a comma and zero more more space
		final String[] headers = header.substring(0).trim().split("\\s{2,}|\\,\\s*|\\t");
		for (String name : headers) {
			name = removeQuotations(name);
			v.put(name, new ArrayList<Double>(89));
		}
	}
}
