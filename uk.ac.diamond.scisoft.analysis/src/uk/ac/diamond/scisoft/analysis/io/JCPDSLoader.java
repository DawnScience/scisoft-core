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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.DatasetFactory;

import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;

public class JCPDSLoader extends AbstractFileLoader {

	protected Map<String,String> metadataMap;

	public enum Keyword {
		COMMENT("COMMENT"), K0("K0"), K0P("K0P"), DK0DT("DK0DT"), DK0PDT("DK0PDT"), SYMMETRY("SYMMETRY"),
		A("A"), B("B"), C("C"), ALPHA("ALPHA"), BETA("BETA"), GAMMA("GAMMA"),
		VOLUME("VOLUME"), ALPHAT("ALPHAT"), DALPHADT("DALPHADT"), DIHKL("DIHKL");
		
		
		private String name;
		
		private Keyword(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	private String[] pluckNames(Keyword[] keywords) {
		ArrayList<String> names = new ArrayList<>();
		for(Keyword keyword: keywords) {
			names.add(keyword.getName());
		}
		return names.toArray(new String[names.size()]);
	}
	Set<String> allowedKeywords = new HashSet<>(Arrays.asList(pluckNames(Keyword.values())));
	
	
	public enum Symmetry {
		CUBIC("CUBIC"), TETRAGONAL("TERAGONAL"), HEXAGONAL("HEXAGONAL"), RHOMBOHEDRAL("RHOMBOHEDRAL"),
		ORTHORHOMBIC("ORTHORHOMBIC"), MONOCLINIC("MONOCLINIC"), TRICILINIC("TRICLINIC");
		
		private String name;
		
		private Symmetry(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private String[] pluckNames(Symmetry[] symmetries) {
		ArrayList<String> names = new ArrayList<>();
		for(Symmetry symmetry: symmetries) {
			names.add(symmetry.getName());
		}
		return names.toArray(new String[names.size()]);
	}
	
	Set<String> symmetries = new HashSet<>(Arrays.asList(pluckNames(Symmetry.values())));

	// Data lists
	List<Double> d, i, h, k, l;
	
	public JCPDSLoader() {
		metadataMap = new HashMap<String, String>();
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
			FileInputStream fIS = new FileInputStream(fileName);
			InputStreamReader iSR = new InputStreamReader(fIS, "UTF-8");
			in = new BufferedReader(iSR);
//			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			
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
				if (!keyword.equals(Keyword.DIHKL.getName())) {
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
			
			result.setDataset("d", DatasetFactory.createFromList(d));
			result.setDataset("i", DatasetFactory.createFromList(i));
			result.setDataset("h", DatasetFactory.createFromList(h));
			result.setDataset("k", DatasetFactory.createFromList(k));
			result.setDataset("l", DatasetFactory.createFromList(l));
			
			// unit cell volume
			// This is one of the more useful pieces of metadata, and may be
			// encoded directly. If it is not, however, then it can be 
			// calculated from the encoded symmetry and unit cell parameters
			if (!metadataMap.containsKey(Keyword.VOLUME.getName())) {
				String symString = metadataMap.get(Keyword.SYMMETRY.getName());
				UnitCell unitCell;
				double a, b, c, alpha, beta, gamma;
				switch (symString) {
				case("CUBIC"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					unitCell = new UnitCell(new double[] {a, a, a}, new double[] {90, 90, 90});
					break;
				case("TETRAGONAL"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					c = Double.parseDouble(metadataMap.get(Keyword.C.getName()));
					unitCell = new UnitCell(new double[] {a, a, c}, new double[] {90, 90, 90});
					break;
				case("HEXAGONAL"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					c = Double.parseDouble(metadataMap.get(Keyword.C.getName()));
					unitCell = new UnitCell(new double[] {a, a, c}, new double[] {90, 90, 120});
					break;
				case("RHOMOHEDRAL"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					gamma = Double.parseDouble(metadataMap.get(Keyword.GAMMA.getName()));
					unitCell = new UnitCell(new double[] {a, a, a}, new double[] {90, 90, gamma});
					break;
				case("ORTHORHOMBIC"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					b = Double.parseDouble(metadataMap.get(Keyword.B.getName()));
					c = Double.parseDouble(metadataMap.get(Keyword.C.getName()));
					unitCell = new UnitCell(new double[] {a, b, c}, new double[] {90, 90, 90});
					break;
				case("MONOCLINIC"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					b = Double.parseDouble(metadataMap.get(Keyword.B.getName()));
					c = Double.parseDouble(metadataMap.get(Keyword.C.getName()));
					beta = Double.parseDouble(metadataMap.get(Keyword.BETA.getName()));
					unitCell = new UnitCell(new double[] {a, b, c}, new double[] {90, beta, 90});
					break;
				case("TRICLINIC"):
					a = Double.parseDouble(metadataMap.get(Keyword.A.getName()));
					b = Double.parseDouble(metadataMap.get(Keyword.B.getName()));
					c = Double.parseDouble(metadataMap.get(Keyword.C.getName()));
					alpha = Double.parseDouble(metadataMap.get(Keyword.ALPHA.getName()));
					beta = Double.parseDouble(metadataMap.get(Keyword.BETA.getName()));
					gamma = Double.parseDouble(metadataMap.get(Keyword.GAMMA.getName()));
					unitCell = new UnitCell(new double[] {a, b, c}, new double[] {alpha, beta, gamma});
					break;
				default:
					unitCell = new UnitCell(1.0);
				}
				
				metadataMap.put("VOLUME", Double.toString(unitCell.volume()));
			}
			
			// metadata
			metadata = new ExtendedMetadata(new File(fileName));
			metadata.setMetadata(metadataMap);
			
			for (String name : new String[] {"d", "i", "j", "k", "l"})
				metadata.addDataInfo(name, d.size());
			
			result.setMetadata(metadata);
			
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
