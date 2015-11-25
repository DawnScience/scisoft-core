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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;

public class AlbaLinkFileLoader extends AbstractFileLoader {

	
	private Map<String,String> metadataMap;
	private static String BASE_DIR = "BaseDir";
	
	@Override
	public IDataHolder loadFile() throws ScanFileHolderException {
		fileName.toString();
		
		// first instantiate the return object.
				final DataHolder result = new DataHolder();
				
				// then try to read the file given
				BufferedReader in = null;
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
					
					String line = in.readLine();
					String[] cols = null;
					if (line == null)
						throw new ScanFileHolderException("No lines found");
					if (!line.startsWith("#")) {
						throw new ScanFileHolderException("Not an alba file (no # header)");
					}
					
					metadataMap = new HashMap<>();
					
					line = in.readLine();
					String nextLine = in.readLine();
					while (line.startsWith("#")) {
						if (line.contains(":")){
							line = line.substring(1);
							String[] split = line.split(":");
							metadataMap.put(split[0].trim(),split[1].trim());
//							System.out.println(split[0].trim() + " " + split[1].trim());
						}
						
						if (!nextLine.startsWith("#")) {
							line = line.substring(2);
							cols = line.split("\t");
						}
						
						line = nextLine;
						nextLine = in.readLine();
						
					}
					
					if (!metadataMap.containsKey(BASE_DIR)) throw new ScanFileHolderException("No " + BASE_DIR +" in header");
					
					String base = metadataMap.get(BASE_DIR);
					
					String[] split = line.split("\t");
					
					if (cols.length != split.length) throw new ScanFileHolderException("Headers do not match data!");
					Map<String, List<String>> mapString = new HashMap<String, List<String>>();
					Map<String, List<Double>> mapDouble = new HashMap<String, List<Double>>();
					
					boolean[] doubleKey = new boolean[split.length];
					for (int i = 0; i < split.length; i++) {
						double d = 0;
						try {
							d = Double.parseDouble(split[i]);
							doubleKey[i] = true;
						} catch (Exception e) {
							doubleKey[i] = false;
						}
						
						if (doubleKey[i]) {
							List<Double> l = new ArrayList<Double>();
							l.add(d);
							mapDouble.put(cols[i], l);
						} else {
							List<String> l = new ArrayList<String>();
							String s = split[i];
							if (!"None".equals(s)) {
								if (s.endsWith("EDF")){
									s = s.subSequence(0, s.length()-3) + "edf";
								}
								base = validatePath(fileName,base, s);
								s = base+s;
							}
							l.add(s);
							mapString.put(cols[i], l);
						}
					}
					
					line = nextLine;
					
					while (line != null) {
						split = line.split("\t");
						if (cols.length != split.length) {
							throw new ScanFileHolderException("Columns not the same length");
						}
						
						for (int i = 0; i < split.length; i++) {
							if (doubleKey[i]) {
								mapDouble.get(cols[i]).add(Utils.parseDouble(split[i]));
							} else {
								String s = split[i];
								if (!"None".equals(s)) {
									if (s.endsWith("EDF")){
										s = s.subSequence(0, s.length()-3) + "edf";
									}
									s = base+s;
								}
								mapString.get(cols[i]).add(s);
							}
						}
						
						line = in.readLine();
					}
					
					mapString.toString();
					for (String key : mapDouble.keySet()){
						result.addDataset(key, DatasetFactory.createFromList(mapDouble.get(key)));
					}
					
					for (String key : mapString.keySet()){
						try {
							ImageStackLoader l = new ImageStackLoader(mapString.get(key), null);
							ILazyDataset lz = new LazyDataset(key, l.getDtype(), l.getShape(), l);
							result.addDataset(key, lz);
						} catch (Exception e) {
						}
						
						
						
					}
					
					
				} catch (Exception e) {
					throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName, e);
					
				} finally {
					try {
						if (in!=null) in.close();
					} catch (IOException e) {
						throw new ScanFileHolderException("Cannot close stream from file  " + fileName, e);
					}
				}
		return result;
	}

	private String validatePath(String filename, String base, String s) {
		File f = new File(base + s);
		if (f.exists()) return base;
		
		f = new File(filename);
		String p = f.getParent();
		f = new File(p + File.separator+ s);
		if (f.exists()) return p + File.separator;
		
		f = new File(base);
		String bp = f.getName();
		f = new File(p + File.separator+ bp + File.separator + s);
		if (f.exists()) return p + File.separator+ bp;
		
		return base;
	}

	@Override
	protected void clearMetadata() {


	}

}
