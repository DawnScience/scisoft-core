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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;

/**
 * 
 * 
 * 
 * Example: 

#*********************************************************
# Data Capture: BL04J-CS-SCAN-01:SCAN at 14/12/2013 07:08.40
#*********************************************************
BL04J-AL-SLITS-02:TOP.VAL,Points,BL04J-EA-STK-03:IAMP4:I
2.5,0,0.451620042324
2.44936708861,1,0.452070713043
2.39873417722,2,0.451837450266
2.34810126582,3,0.451653629541
2.29746835443,4,0.451682329178

...

 */
public class CSVLoader extends DatLoader {

	public CSVLoader() {
		super();
	}
	
	/**
	 * @param fileName
	 */
	public CSVLoader(final String fileName) {
		super(fileName);
	}

	/**
	 * May override to support different file formats.
	 * @return the delimiter
	 */
	@Override
	protected String getDelimiter() {
		return "\\s*,\\s*";
	}

	@Override
	protected String parseHeaders(final BufferedReader in, final String name, IMonitor mon) throws Exception {

		String line = in.readLine();
		if (line == null)
			return null;

		if (line.trim().startsWith("&")) throw new Exception("Cannot load SRS files with EpicsCSVLoader!");
		if (metadataMap != null) metadataMap.clear();
		vals.clear();

		List<String> header = new ArrayList<String>(31);

		boolean foundHeaderLine = false;
		boolean wasScanLine     = false;
		// TODO clean up as this should not be a while loop
		while (true) {

			try {
				if ("".equals(line.trim())) continue;
				foundHeaderLine = true;

				if (!monitorIncrement(mon)) {
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}

				if (wasScanLine && DATE_LINE.matcher(line.trim()).matches()) {
					throw new ScanFileHolderException("This file is a multi-scan spec file - use SpecLoader instead!");
				}
				wasScanLine = SCAN_LINE.matcher(line.trim()).matches();

				header.add(line);

				if (line.contains(":")) {
					String key = line.substring(0,line.indexOf(":"));
					String value =line.substring(line.indexOf(":")+1, line.length());
					metadataMap.put(key.trim(),value.trim());
				}


			} finally {
				line = in.readLine();

				// Ignore empty lines.
				while (line != null && "".equals(line.trim())) {
					line = in.readLine();
				}

				if (!line.startsWith("#")) { // We found the header line
					
					if (DATA.matcher(line).matches()) break; // Data is not columns!
					
					// We bodge the last non-empty line with non-numerical data
					// to be a header line.
					header.add("# "+line);
					line = in.readLine(); // We must leave the last line read as a number, not header line.
					break;
				}
			}
		}

		if (header.size() < 1) {
			if (!foundHeaderLine) {
				createDefaultHeaders(line);
			}
			return line;
		}

        createHeaders(header, line, name);		
        
		return line;
	}
}
