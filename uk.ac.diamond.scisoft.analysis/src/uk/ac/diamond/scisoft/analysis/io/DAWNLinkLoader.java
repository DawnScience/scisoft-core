/*-
 * Copyright 2016 Diamond Light Source Ltd.
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.StringDataset;

public class DAWNLinkLoader extends AbstractFileLoader {

	private Map<String,String> metadataMap;
	private Set<String> datasetSet;
	private static String DIRECTORY = "DIR_NAME";
	private static String DATA_KEY = "DATASET_NAME";
	private static String FILE_NAME = "FILE_NAME";
	private static String SHAPE_KEY = "SHAPE";
	private int[] shape;
	
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
						throw new ScanFileHolderException("Not an DAWN file (no # header)");
					}
					
					metadataMap = new HashMap<>();
					datasetSet = new HashSet<>();
					
					
					String nextLine = in.readLine();
					while (line.startsWith("#")) {
						if (line.contains(":")){
							line = line.substring(1);
							String[] split = line.split(":");
							String key = split[0].trim();
							if (DATA_KEY.equals(key)) {
								datasetSet.add(split[1].trim());
							} else if (SHAPE_KEY.equals(key)) {
								setShape(split);
							}else {
							
								metadataMap.put(split[0].trim(),split[1].trim());
							}
						}
						
						if (!nextLine.startsWith("#")) {
							line = line.substring(2);
							cols = line.split("\t");
						}
						
						line = nextLine;
						nextLine = in.readLine();
						
					}
					
					if (!metadataMap.containsKey(DIRECTORY)) throw new ScanFileHolderException("No " + DIRECTORY +" in header");
					
					String base = metadataMap.get(DIRECTORY);
					if (!base.endsWith(File.separator)) base += File.separator;
					
					String[] split = line.split("\t");
					if (cols == null) throw new ScanFileHolderException("No header!");
					if (cols.length != split.length) throw new ScanFileHolderException("Headers do not match data!");
					
					List<String> filename = new ArrayList<String>();
					int filePos = -1;
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
							String s = split[i];

								base = validatePath(fileName,base, s);
								s = base+s;
								filename.add(s);
								filePos = i;
						}
					}
					
					line = nextLine;
					
					while (line != null && !line.isEmpty()) {
						split = line.split("\t");
						if (cols.length != split.length) {
							throw new ScanFileHolderException("Columns not the same length");
						}
						
						for (int i = 0; i < split.length; i++) {
							if (doubleKey[i]) {
								mapDouble.get(cols[i]).add(Utils.parseDouble(split[i]));
							}
							
							if (i == filePos) filename.add(base + split[i]);
						}
						
						line = in.readLine();
					}
					
					for (String key : mapDouble.keySet()){
						Dataset d = DatasetFactory.createFromList(mapDouble.get(key));
						d.setName(key);
						result.addDataset(key, d);
					}
					
					if (shape != null) {
						int size = filename.size();
						int total = 1;
						for (int i = 0; i < shape.length;i++) total *=shape[i];
						
						if (size != total) shape = null;
					}
					
					for (String key : datasetSet){
							StringDataset ds = DatasetFactory.createFromObject(StringDataset.class,filename, shape);
							ImageStackLoader l = new ImageStackLoader(ds, null,null,key);
							ILazyDataset lz = new LazyDataset(key, l.getDType(), l.getShape(), l);
							result.addDataset(key, lz);
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

	private void setShape(String[] strings) {
		if (strings.length < 2) return;
		
		String[] split = strings[1].split(",");
		int[] s = new int[split.length];
		try {
			for (int i = 0; i < s.length ; i++) {
				s[i] = Integer.parseInt(split[i].trim());
			}
			
			shape = s;
			
		} catch (Exception e) {
			//TODO
		}
	}
	
	@Override
	protected void clearMetadata() {

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

}
