/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

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
public class EpicsCSVLoader extends DatLoader {

	/**
	 * May override to support different file formats.
	 * @return the delimiter
	 */
	@Override
	protected String getDelimiter() {
		return ",";
	}

	@Override
	protected String parseHeaders(final BufferedReader in, final String name, IMonitor mon) throws Exception {

		String line = in.readLine();
		if (line == null)
			return null;

		if (line.trim().startsWith("&")) throw new Exception("Cannot load SRS files with DatLoader!");
		metaData.clear();
		vals.clear();

		List<String> header = new ArrayList<String>(31);

		boolean foundHeaderLine = false;
		boolean wasScanLine     = false;
		// TODO clean up as this should not be a while loop
		while (line != null) {

			try {
				if ("".equals(line.trim())) continue;
				foundHeaderLine = true;

				if (mon!=null) mon.worked(1);
				if (mon!=null && mon.isCancelled()) {
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
					metaData.put(key.trim(),value.trim());
				}


			} finally {
				line = in.readLine();

				// Ignore empty lines.
				while (line != null && "".equals(line.trim())) {
					line = in.readLine();
				}

				if (line != null && !line.startsWith("#")) { // We found the header line
					
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
