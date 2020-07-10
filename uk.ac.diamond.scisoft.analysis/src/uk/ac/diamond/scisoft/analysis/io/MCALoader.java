/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MCALoader extends AbstractFileLoader {
	protected Map<String,String> mcaParameters;

	public MCALoader() {
	}

	public MCALoader(final String fileName) {
		setFile(fileName);
	}

	@Override
	public void setFile(String fileName) {
		mcaParameters = new HashMap<String,String>();
		
		super.setFile(fileName);
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		mcaParameters.clear();
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor)null);
	}
	
	/**
	 * Function that loads in the standard MCA datafile
	 * 
	 * @return The package which contains the data that has been loaded
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
		// first instantiate the return object.
		final DataHolder result = new DataHolder();
		// then try to read the file given
		Scanner scanner = null;
		try {
			File file = new File(fileName);
			scanner = new Scanner(file);
			String line;
			int channel_begin = Integer.MIN_VALUE;
			int channel_end = Integer.MIN_VALUE;
			int nchannels = Integer.MIN_VALUE;
			double zero = Double.NaN;
			double gain = Double.NaN;
	
		
			//skip empty lines if present
			do {
				line = scanner.nextLine().trim();
			} while (line.trim().equals(""));
			
			if (!line.substring(0, 2).equals("#F")) {
				//when this happens, we are not dealing with a valid MCA file
				throw new Exception("first line must start with #F");
			}
			
			//next continue reading until a single empty line is encountered
			do {
				line = scanner.nextLine().trim();
				if (line.equals(""))
					break;
				else if (line.charAt(0) != '#')
					throw new Exception("within global metadata block all lines shall start with #");
				
				String id = line.charAt(1) + "_GLOBAL";
				String value = line.substring(3);
				mcaParameters.put(id, value);
			} while (true);
			
			//for now assume that MCA files contain just one dataset...
			//if an example file shows up with multiple datasets, then the following code should be placed in a while loop
			line = scanner.next();
			if (!line.equals("#S")) {
				throw new Exception("a scan block shall start with #S");
			}
			String scan_number = scanner.next();
			//ignore the rest of the line
			scanner.nextLine();
			
			//now several more lines starting with '#' should follow
			do {
				line = scanner.next().trim();
				String id = null;
				if (line.equals("@A")) {
					break;
				} else if (line.charAt(0) != '#' || line.length() == 1) {
					throw new Exception("within scan metadata block all lines shall start with a '#', followed by at least one character that is not whitespace");
				} else if (line.substring(0, 2).equals("#@")) {
					id = line.substring(2);
				} else {
					id = line.substring(1);
				}
				
				switch (id) {
				case "CHANN":
					//there should be four ints following here
					nchannels = scanner.nextInt();
					channel_begin = scanner.nextInt();
					channel_end = scanner.nextInt();
					scanner.nextLine();
					mcaParameters.put("channel_begin_" + scan_number, Integer.toString(channel_begin));
					mcaParameters.put("channel_end_" + scan_number, Integer.toString(channel_end));
					break;
				case "CALIB":
					//there should be three doubles here
					//I cannot be sure based on the example file but the last value may be used for second degree polynomial calibration
					zero = scanner.nextDouble();
					gain = scanner.nextDouble();
					scanner.nextLine();
					mcaParameters.put("zero_" + scan_number, Double.toString(zero));
					mcaParameters.put("gain_" + scan_number, Double.toString(gain));
					break;
				default:
					mcaParameters.put(id + "_" + scan_number, scanner.nextLine().trim());
				}
			} while(true);
			
			// see if we got CHANN
			if (nchannels == Integer.MIN_VALUE) {
				throw new Exception("no information regarding number of channels found in scan metadata block");
			}
			
			//allocate memory for our data
			double[] array = new double[nchannels];
			
			int channel_index = 0;
			
			while (channel_index < nchannels) {
				String text = scanner.next();
				if (text.equals("\\"))
					continue;
				array[channel_index++] = Double.parseDouble(text);
			}
			
			scanner.close();
			
			if (zero != Double.NaN) {
				final Dataset energies = DatasetFactory.createRange(nchannels);
				energies.imultiply(gain).iadd(zero);
				energies.setName("Energy_" + scan_number);
				result.addDataset("Energy_" + scan_number, energies);
			}
			
			final Dataset counts =  DatasetFactory.createFromObject(array);
			counts.setName("Counts_" + scan_number);
			result.addDataset("Counts_" + scan_number, counts);
			
			if (loadMetadata) {
				metadata = new ExtendedMetadata(new File(fileName));
				metadata.setMetadata(mcaParameters);
				result.setMetadata(metadata);
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("MCALoader.loadFile exception loading  " + fileName, e);
		} finally {
			if (scanner != null)
				scanner.close();
		}
		
		return result;
	}
	
}
