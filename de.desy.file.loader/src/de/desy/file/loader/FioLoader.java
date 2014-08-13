/*
 * Copyright 2013-2014 Thorsten Kracht, Gero Flucke (Deutsches Elektronen-Synchrotron)
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

package de.desy.file.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// lots of below added
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.ExtendedMetadata;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.io.IMetaLoader;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.io.Utils;

/**
 * This class loads a fio data files where:
 * !
! Comments 
%c 
  filename  = tbc3b_0139 
  date      = 11-Jul-2003 time     = 15:34:31 
  scan mot  = DUMMY 
  scan type = SCAN_DEVICE 
!
! Parameter 
%p 
 ENERGY = 7774.999 
 STEPWIDTH = 1 
 STOP_POSITION = 7775 
 START_POSITION = 7675 
 MEAN_I_DORIS = 107.9897 
 MEAN_COUNTERA = 2.29703 
 MEAN_VERPOL = 97045.38 
 MEAN_HORPOL = 9180.307 
 MEAN_POL = 82.71613 
! 
! Data 
%d 
 Col 1 Position [units]  FLOAT 
 Col 2 Detector [counts]  FLOAT 
 Col 3 Monitor [counts]  FLOAT 
 Col 4 Hori_pol [counts]  FLOAT 
 Col 5 Vert_Pol [counts]  FLOAT 
 Col 6 I_Doris [mA]  FLOAT 
           7675           142         99840          8879         91744      109.0392  
           7676          3363         99995          8653         92081      109.0229  
           7677          3235        100097          8824         92018      109.0015  
           7678          3268        100494          8953         92296      108.9815  
           7679          3290        100297          8863         92247      108.9605  

  */
public class FioLoader extends AbstractFileLoader implements IMetaLoader {
	
	transient protected static final Logger logger = LoggerFactory.getLogger(FioLoader.class);
	
	transient private static final String  FLOAT = "([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)|(0\\.)";
	//GF: transient private static final Pattern DATA  = Pattern.compile("^(("+FLOAT+")\\s+)+("+FLOAT+")$");
	//GF: Brute force way to allow also single column pattern (it is already trimmed, i.e no white space around): 
	transient private static final Pattern DATA  = Pattern.compile("^(("+FLOAT+")\\s+)+("+FLOAT+")$|^"+FLOAT+"$");

	protected String                    fileName;
	protected List<String>              header;
	protected Map<String,String>        fioParameters;
	protected Map<String, List<Double>> columns;

	private ExtendedMetadata metadata;

	public FioLoader() {
	}
	
	/**
	 * @param fileName
	 */
	public FioLoader(final String fileName) {
		setFile(fileName);
	}
	
	public void setFile(final String fileName) {
		this.fileName  = fileName;
		this.header   = new ArrayList<String>();
		this.fioParameters = new HashMap<String,String>();
		
		// Important must use LinkedHashMap as order assumes is insertion order.
		this.columns   = new LinkedHashMap<String, List<Double>>();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor)null);
	}
	
	/**
	 * Function that loads in the standard fio datafile
	 * 
	 * @return The package which contains the data that has been loaded
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
        final DataHolder result = loadFile(null, mon);
		return result;
	}

	private DataHolder loadFile(final String name, final IMonitor mon) throws ScanFileHolderException {
		// first instantiate the return object.
		final DataHolder result = new DataHolder();
		// then try to read the file given
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			
			boolean readingFooter = false;
			
			String line	= parseHeaders(in, name, mon);
			// GF: if name != null, read only that data column (?):
			int columnIndex = -1; // GF moved here from data members and initialise
			if (name != null && columns.containsKey(name)) { // find index of column with name
				for (String key : columns.keySet()) {
					++columnIndex;
					if (key == name) break;
				}
			}
			// Read data
			DATA: while (line != null) {
				
				if (mon!=null) mon.worked(1);
				if (mon!=null && mon.isCancelled()) {
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}
				
				line = line.trim();
				if (!readingFooter && DATA.matcher(line).matches()) {
					
					if (line.startsWith("#")) {
						readingFooter = true;
						break DATA;
					}
					// TODO: move following check outwards?
					if (columns.isEmpty()) throw new ScanFileHolderException("Cannot read header for data set names!");
					
					final String[] values = line.split("\\s+");
					if (columnIndex > -1) { //  && name!=null) { // read only this column (? TODO: check!) 
						final String value = values[columnIndex]; // TODO: check index out of range?
						columns.get(name).add(Utils.parseDouble(value.trim()));
					} else {
						//logger.debug("GF loadFile: columnIndex '{}', name is '{}'", columnIndex, name == null ? "a null" : name);
						if (values.length != columns.size()) {
							throw new ScanFileHolderException("Data and header must be the same size!");
						}
						final Iterator<String> it = columns.keySet().iterator();
						for (String value : values) {
							columns.get(it.next()).add(Utils.parseDouble(value.trim())); 
						}
					}
					
				} else if (!readingFooter) {
					// TODO: what is the consequence?
					//       what if no line is 'successful'? (as with old DATA pattern and single column files) 
					logger.error("FioLoader: Line (with data) '{}' does not match expected pattern '{}'!", line, DATA);
				}
				
				line = in.readLine();
			}
			

			for (String n : columns.keySet()) {
				final Dataset set =  DatasetFactory.createFromList(columns.get(n));
				set.setName(n);
				result.addDataset(n, set);
			}		

			if (loadMetadata) {
				createMetadata();
				result.setMetadata(metadata);
			}
			return result;
			
		} catch (Exception e) {
			throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName, e);
			
		} finally {
			try {
				if (in!=null) in.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("Cannot close stream from file  " + fileName, e);
			}
		}
	}

	@Override
	public void loadMetaData(final IMonitor mon) throws Exception {

		final BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		int count = 1;
		try {
			parseHeaders(br, null, mon);
			// We assume the rest of the lines not starting with # are all
			// data lines in getting the meta data. We do not parse these lines.
			String line=null;
			while ((line = br.readLine()) != null) {	
				line = line.trim();
				if (line.startsWith("#")) break;
				count++;
			}
			
		} finally {
			br.close();
		}
		createMetadata(count);
	}
	
	private void createMetadata() {
		createMetadata(-1);
	}
	
	private void createMetadata(int approxSize) {
		metadata = new ExtendedMetadata(new File(fileName));
		metadata.setMetadata(fioParameters);
		for (Entry<String, List<Double>> e : columns.entrySet()) {
			if (approxSize>-1 &&  e.getValue().size()<1) {
			    metadata.addDataInfo(e.getKey(), approxSize);
			} else {
			    metadata.addDataInfo(e.getKey(), e.getValue().size());
			}
		}
	}

	@Override
	public IMetaData getMetaData() {
		return metadata;
	}
	/**
	 * @param in
	 * @param name
	 * @param mon
	 * @return last line
	 * @throws Exception
	 */
	private String parseHeaders(final BufferedReader in, final String name, IMonitor mon) throws Exception {
		Boolean isComment = false;
		Boolean isParameter = false;
		Boolean isColumnDesc = false;
		int commentNo = 1;
		
		String line;
		fioParameters.clear();
		header.clear();
		columns.clear();
		
		while (true) {
			try{
				line = in.readLine();
				String lineTrim = line.trim();
				if( lineTrim.startsWith( "!") || lineTrim.equals("")){
					//logger.debug("GF in parsing header: skip line {}", line);
					continue;
				}
				header.add(line);
				if (mon!=null) mon.worked(1);
				if (mon!=null && mon.isCancelled()) {
					// GF: throw within a try block...???
					throw new ScanFileHolderException("Loader cancelled during reading!");
				}
				if( lineTrim.startsWith("%c")){
					//logger.debug("GF in parsing header: %c line is '{}'", line);
					isComment = true;
					isParameter = false;
					isColumnDesc = false;
					continue;
				}
				if( lineTrim.startsWith("%p")){
					//logger.debug("GF in parsing header: %p line is '{}'", line);
					isComment = false;
					isParameter = true;
					isColumnDesc = false;
					continue;
				}				
				if( lineTrim.startsWith("%d")){
					//logger.debug("GF in parsing header: %d line is '{}'", line);
					isComment = false;
					isParameter = false;
					isColumnDesc = true;
					continue;
				}
				if( isColumnDesc){
					if( !lineTrim.startsWith("Col")){
						//logger.debug("GF in parsing header: non-'Col' line '{}' (but isColumnDesc)", line);
						break;
					}
				}
				//  Col 1 Position [units]  FLOAT
				if( isColumnDesc){
					//logger.debug("GF header parsing: isColumnDesc: {}", line);
					String[] parts = lineTrim.split(" ");
					if( columns.containsKey(parts[2].trim())){
						columns.put( parts[2].trim() + "_1", new ArrayList<Double>(1000));
					}
					else {
						columns.put( parts[2].trim(), new ArrayList<Double>(1000));
					}
					continue;
				}
				if( isParameter){
					//logger.debug("GF header parsing: isParameter: {}", line);
					String[] parts = line.split("=");
					fioParameters.put(parts[0].trim(),parts[1].trim());
					continue;
				}
				if( isComment){
					//logger.debug("GF header parsing: isComment: {}", line);
					fioParameters.put( "com_" + commentNo, line);
					commentNo++;
				}
			} finally { // GF: Why the try block at all???
			// nothing...
			}
		}
		return line;
	}
}
