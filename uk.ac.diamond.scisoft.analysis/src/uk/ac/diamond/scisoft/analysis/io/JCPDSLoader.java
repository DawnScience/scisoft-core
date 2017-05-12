/*-
 * Copyright 2017 Diamond Light Source Ltd.
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;

public class JCPDSLoader extends AbstractFileLoader {

	protected Map<String,String> metadataMap;
	Set<String> allowedKeywords = new HashSet<String>(Arrays.asList(
			"COMMENT",
			"K0",
			"K0P",
			"DK0DT",
			"DK0PDT",
			"SYMMETRY",
			"A",
			"B",
			"C",
			"ALPHA",
			"BETA",
			"GAMMA",
			"VOLUME",
			"ALPHAT",
			"DALPHADT",
			"DIHKL"
			));
	
	Set<String> symmetries = new HashSet<String>(Arrays.asList(
			"CUBIC",
			"TETRAGONAL",
			"HEXAGONAL",
			"RHOMBOHEDRAL",
			"ORTHORHOMBIC",
			"MONOCLINIC",
			"TRICLINIC"
			));

	// Data lists
	List<Double> d, i, h, k, l;
	
	public JCPDSLoader() {
		d = new ArrayList<Double>();
		i = new ArrayList<Double>();
		h = new ArrayList<Double>();
		k = new ArrayList<Double>();
		l = new ArrayList<Double>();
	}
	
	public JCPDSLoader(final String fileName) {
		this();
		setFile(fileName);
	}
	
	@Override
	public void setFile(String fileName) {		
		super.setFile(fileName);
	}
	
	@Override
	protected void clearMetadata() {
		metadataMap.clear();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		DataHolder result = new DataHolder();
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			
			// Read the VERSION: 4 line
			String versionLine = in.readLine();
			if (!versionLine.matches("VERSION:\\s*4"))
				throw new ScanFileHolderException("Incorrect JCPDS file version. Expected \"VERSION: 4\" got " + versionLine);
			// Put the fixed version on the metadata map
			metadataMap.put("VERSION", "4");
			
			String jLine;
			// Read keyword-data line pairs
			while ((jLine = in.readLine()) != null) {
				// Get the keyword, by reading until the next colon
				String keyword = jLine.trim().split(":")[0];
				// check the keyword is in the allowed set
				if (!allowedKeywords.contains(keyword)) continue;
				String restOfLine = jLine.replace(keyword+":", "").trim();
				if (!keyword.equals("DIHKL")) {
					metadataMap.put(keyword, restOfLine);
				} else {
					// DIHKL Data
					String[] dataItems = restOfLine.split("\\s+");
					final int nItems = 5;
					double[] doubleData = new double[nItems];
					int count = 0;
					for (String stringItem : dataItems) {
						doubleData[count] = Double.parseDouble(stringItem);
						count++;
						if (count >= nItems) break;
					}
					
					if (count !=  nItems)
						throw new ScanFileHolderException("Error parsing DIHKL line \"" + jLine + "\" in file " + fileName);
					
					d.add(doubleData[0]);
					i.add(doubleData[1]);
					h.add(doubleData[2]);
					k.add(doubleData[3]);
					l.add(doubleData[4]);
				}
			}
			
			
		} catch (Exception e) {
			throw new ScanFileHolderException("JCPDS cannot load from file " + fileName, e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				throw new ScanFileHolderException("Cannot close stream from file " + fileName, ioe);
			}
		}
		
		return result;
	}

}
