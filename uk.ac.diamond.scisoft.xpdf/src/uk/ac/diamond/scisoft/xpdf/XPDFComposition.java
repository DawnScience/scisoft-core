/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

public class XPDFComposition {
	Map<Integer, Integer> atomCount;
	double electronOverlap;
	static final double[] atomicMass = { 1.008664,
		1.00794,4.002602,6.941,9.012182,10.811,12.011,14.00674,
        15.9994,18.9984032,20.1797,22.989768,24.305,26.981539,
        28.0855,30.973762,32.066,35.4527,39.948,39.0983,40.078,
        44.95591,47.88,50.9415,51.996,54.93805,55.847,58.9332,
        58.6934,63.546,65.39,69.723,72.61,74.92159,78.96,79.904,
        83.8,85.4678,87.62,88.90585,91.224,92.90638,95.94,97.9072,
        101.07,102.9055,106.42,107.8682,112.411,114.818,118.71,
        121.757,127.6,126.90447,131.29,132.90543,137.327,138.9055,
        140.115,140.90765,144.24,144.9127,150.36,151.965,157.25,
        158.92534,162.5,164.93032,167.26,168.93421,173.04,174.967,
        178.49,180.9479,183.84,186.207,190.23,192.22,195.08,
        196.96654,200.59,204.3833,207.2,208.98037,208.9824,209.9871,
        222.0176,223.0185,226.0254,227.0278,232.0381,231.03588,
        238.0289,237.0482,244.0642,243.0614,247.0703,247.0703,
        251.0796,252.0816,257.0951
	};
	static final String[] elementSymbol = { "n",
		"H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si",
        "P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co",
        "Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I",
        "Xe","Cs","Ba","La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy",
        "Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au",
        "Hg","Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U",
        "Np","Pu","Am","Cm","Bk","Cf","Es","Fm"
	};
	static final String[] elementName = { "neutronium",
		"Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon",
        "Nitrogen","Oxygen","Fluorine","Neon","Sodium","Magnesium",
        "Aluminium","Silicon","Phosphorus","Sulphur","Chlorine","Argon",
        "Potassium","Calcium","Scandium","Titanium","Vanadium","Chromium",
        "Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium",
        "Germanium","Arsenic","Selenium","Bromine","Krypton","Rubidium",
        "Strontium","Yttrium","Zirconium","Niobium","Molybdenum",
        "Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium",
        "Indium","Tin","Antimony","Tellurium","Iodine","Xenon","Caesium",
        "Barium","Lanthanum","Cerium","Praseodymium","Neodymium",
        "Promethium","Samarium","Europium","Gadolinium","Terbium",
        "Dysprosium","Holmium","Erbium","Thulium","Ytterbium","Lutetium",
        "Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium",
        "Platinum","Gold","Mercury","Thallium","Lead","Bismuth","Polonium",
        "Astatine","Radon","Francium","Radium","Actinium","Thorium",
        "Protoactinium","Uranium","Neptunium","Plutonium","Americium",
        "Curium","Berkelium","Californium","Einsteinium","Fermium"
	};
			
	public XPDFComposition(String materialFormula) {
		this(mapFormula(materialFormula));
	}

	public XPDFComposition(Map<Integer, Integer> atomCount) {
		this.atomCount = atomCount;
		this.electronOverlap = 0.0;
	}
	
	public double getMeanAtomicMass() {
		double massSum = 0.0;
		int atomSum = 0;
		for (Map.Entry<Integer, Integer> element : atomCount.entrySet()) {
			massSum += element.getValue() * atomicMass[element.getKey()];
			atomSum += element.getValue();
		}
		return massSum/atomSum;
	}

	// Calculate the composition dependent part of the Krogh-Moe summation
	public double getKroghMoeSummand() {
		double KMSum = 0.0;
		int atomSum = 0;

		for (Map.Entry<Integer, Integer> iZN1 : atomCount.entrySet()) {
			atomSum += iZN1.getValue();
			for (Map.Entry<Integer, Integer> iZN2 : atomCount.entrySet()) {
				KMSum += iZN1.getValue()*iZN2.getValue()*(iZN1.getKey()*iZN2.getKey() - electronOverlap);
			}
		}
		return KMSum/(atomSum*atomSum);
	}
	
	private static Map<Integer, Integer> mapFormula(String chemicalFormula) {
		// Local class for parsing chemical formulae into a map from atomic number to multiplicity
		return CompoundParser.indexElements(CompoundParser.countAtoms(CompoundParser.tokenize(chemicalFormula)));
	}

	public double getMassAttenuation(double beamEnergy) {
		double massAttenuation = 0.0;
		
		// TODO: replace the ceria experiment fixed values
		if (beamEnergy == 76.6) {
			// Both components have two oxygen atoms in their formula
			if (atomCount.containsKey(8) && atomCount.get(8) == 2) {
				if (atomCount.containsKey(14) && atomCount.get(14) == 1) {
					massAttenuation = 0.19991818715584905;
				} else if (atomCount.containsKey(58) && atomCount.get(58) == 1) {
					massAttenuation = 4.0599040920479297;
				}
			}
		}
		return massAttenuation;
	}

	public double getG0Minus1() {
		int nSum = 0, dSum = 0;
		int aCount = 0;
		for (Map.Entry<Integer, Integer> iZN : atomCount.entrySet()) {
			nSum += iZN.getValue()*iZN.getKey();
			dSum += iZN.getValue()*Math.pow(iZN.getKey(), 2);
			aCount += iZN.getValue();
		}
		double g0Minus1 = Math.pow(nSum, 2)/(double) dSum;
		// divide by the number of atoms to normalize correctly
		g0Minus1 /= aCount;
		return g0Minus1;
	}

	public Dataset getSelfScattering(XPDFCoordinates coordinates) {
		XPDFElectronCrossSections eXSections = new XPDFElectronCrossSections();
//		eXSections.setBeamEnergy(beamEnergy);
//		eXSections.setAngles(twoTheta);
		eXSections.setCoordinates(coordinates);
		
		// TODO: Form factors
		Dataset elasticSelfScattering = Maths.multiply(eXSections.getThomsonCrossSection(), 1);
		Dataset inelasticSelfScattering = Maths.multiply(eXSections.getKleinNishimaCrossSection(), 1);
		
		return Maths.add(elasticSelfScattering, inelasticSelfScattering);
	}
	
	public Dataset getElasticScatteringFactor(Dataset x) {
		Dataset fofx = DoubleDataset.zeros(x);
		
		for (Map.Entry<Integer, Integer> stoichiometry : atomCount.entrySet())
			fofx.iadd(Maths.multiply(stoichiometry.getValue(), XPDFElementalFormFactors.fofx(stoichiometry.getKey(), x)));
		
		return fofx;
	}
	
	public Dataset getElasticScatteringFactorSquared(Dataset x) {
		Dataset fsquaredofx = DoubleDataset.zeros(x);
		
		for (Map.Entry<Integer, Integer> stoichiometry : atomCount.entrySet())
			fsquaredofx.iadd(Maths.multiply(stoichiometry.getValue(), Maths.square(XPDFElementalFormFactors.fofx(stoichiometry.getKey(), x))));
		
		return fsquaredofx;
	}
	
	public Dataset getInelasticScatteringFactor(Dataset x) {
		Dataset Sofx = DoubleDataset.zeros(x);
		
		for (Map.Entry<Integer, Integer> stoichiometry : atomCount.entrySet())
			Sofx.iadd(Maths.multiply(stoichiometry.getValue(), XPDFElementalFormFactors.Sofx(stoichiometry.getKey(), x)));
		
		return Sofx;
	}

	
}


class CompoundParser {

	/*
	 * Names of the elements as an unmodifiable list. the neutron is added as
	 * element 0, so that index equals atomic number
	 */
	private static List<String> elements = Collections.unmodifiableList(Arrays
			.asList("n",
					"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na",
					"Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc",
					"Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga",
					"Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb",
					"Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb",
					"Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm",
					"Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu",
					"Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl",
					"Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa",
					"U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm"));

	
// Is this necessary? No	
//	public CompoundParser() {
//	}
	// RL tokenization of the compound formula
	public static List<String> tokenize(String compoundIn) {
//		List<String> fakeTok = Arrays.asList( CompoundParser.elements.get(1) );
		//return fakeTok;
		List<String> tokens = new Vector<String>();
		
		if (compoundIn != null) {
			// Read a character from the end of the String
			while (compoundIn.length() > 0) {
				int charRemove = 0;
				String tok = compoundIn.substring(compoundIn.length() - 1);

				// Characterise the character in the string. Digits and lower
				// case letters are special cases, otherwise a single character
				// is a token (caps and parens)
				if (Character.isDigit(tok.charAt(0))) {
					// Get all the characters from the end of the String until the next non-digit
					String digits = "0123456789";
					charRemove = compoundIn.length() - CompoundParser.lastIndexOfAnyBut(compoundIn, digits) - 1;
				} else if (Character.isLowerCase(tok.charAt(0))) {
					charRemove = 2;
				} else {
					charRemove = 1;
				}
				tokens.add(0, compoundIn.substring(compoundIn.length() - charRemove , compoundIn.length()));
				compoundIn = compoundIn.substring(0, compoundIn.length() - charRemove);
			}
		}
		return tokens;
	}
	
	
	// Help to take a *valid* string, and return the last index of all the
	// characters that do not occur in the search string
	private static int lastIndexOfAnyBut(String str, String searchChars) {
		
		int lastInd;
		for (lastInd= str.length()-1; (lastInd >= 0) && (searchChars.indexOf(str.charAt(lastInd)) != -1) ; --lastInd)
			;

		return lastInd;
	}


	public static Map<String, Integer> countAtoms(List<String> tokens) {

		Map<String, Integer> atomCount = new HashMap<String, Integer>();
		
		// Read from the end of the list of tokens. If a number is encountered,
		// push a number and a non-number. If a non-number is encountered, push
		// 1 and the non-number
		
		Stack<Integer> mults = new Stack<Integer>();
		mults.push(1);
		
		while (tokens.size() > 0) {
			String toke = tokens.get(tokens.size()-1);
			if (toke.equals("(")) {
				mults.pop();
				tokens.remove(tokens.size()-1);
				continue;
			} else if ("0123456789".indexOf(toke.substring(toke.length()-1)) != -1) {
				mults.push(Integer.parseInt(toke)*mults.peek());
				tokens.remove(tokens.size()-1);
				toke = tokens.get(tokens.size()-1);
			} else {
				mults.push(1*mults.peek());
			}

			// close paren: Remove the token. Cycle
			if (!toke.equals(")")) {
			    // element. Add the atoms to the accumulator. pop the last
				// number. Remove the token. Cycle
				if (atomCount.containsKey(toke)) {
					int oldCount = atomCount.get(toke);
					atomCount.put(toke, oldCount+mults.peek());
				} else {
					atomCount.put(toke, mults.peek());
				}
				mults.pop();
			}
			tokens.remove(tokens.size()-1);
		}

		return atomCount;
	}
	
	public static Map<Integer, Integer> indexElements(Map<String, Integer> siMap) {
	
		Map<Integer, Integer> iMap = new HashMap<Integer, Integer>();

		for	(String eleSym : siMap.keySet()) {
			int zed = elements.indexOf(eleSym);
			iMap.put(zed, siMap.get(eleSym));
		}
		
		return iMap;
	}
	
}
