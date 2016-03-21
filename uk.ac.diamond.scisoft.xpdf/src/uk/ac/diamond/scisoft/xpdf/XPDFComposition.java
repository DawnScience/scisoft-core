/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import com.github.tschoonj.xraylib.Xraylib;
import com.github.tschoonj.xraylib.compoundData;

/**
 * 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFComposition {
	/**
	 * Map from the atomic numbers present in the compound, to the count of
	 * atoms present in the fundamental formula
	 */
	private Map<Integer, Double> atomCount;
	private double electronOverlap;
//	/**
//	 * Mean atomic mass of the elements.
//	 */
//	private static final double[] atomicMass = { 1.008664,
//		1.00794,4.002602,6.941,9.012182,10.811,12.011,14.00674,
//        15.9994,18.9984032,20.1797,22.989768,24.305,26.981539,
//        28.0855,30.973762,32.066,35.4527,39.948,39.0983,40.078,
//        44.95591,47.88,50.9415,51.996,54.93805,55.847,58.9332,
//        58.6934,63.546,65.39,69.723,72.61,74.92159,78.96,79.904,
//        83.8,85.4678,87.62,88.90585,91.224,92.90638,95.94,97.9072,
//        101.07,102.9055,106.42,107.8682,112.411,114.818,118.71,
//        121.757,127.6,126.90447,131.29,132.90543,137.327,138.9055,
//        140.115,140.90765,144.24,144.9127,150.36,151.965,157.25,
//        158.92534,162.5,164.93032,167.26,168.93421,173.04,174.967,
//        178.49,180.9479,183.84,186.207,190.23,192.22,195.08,
//        196.96654,200.59,204.3833,207.2,208.98037,208.9824,209.9871,
//        222.0176,223.0185,226.0254,227.0278,232.0381,231.03588,
//        238.0289,237.0482,244.0642,243.0614,247.0703,247.0703,
//        251.0796,252.0816,257.0951
//	};
//	/**
//	 * Chemical symbols of the elements.
//	 */
//	@SuppressWarnings("unused")
//	private static final String[] elementSymbol = { "n",
//		"H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si",
//        "P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co",
//        "Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
//        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I",
//        "Xe","Cs","Ba","La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy",
//        "Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au",
//        "Hg","Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U",
//        "Np","Pu","Am","Cm","Bk","Cf","Es","Fm"
//	};
//	/**
//	 * English names of the elements.
//	 */
//	@SuppressWarnings("unused")
//	private static final String[] elementName = { "neutronium",
//		"Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon",
//        "Nitrogen","Oxygen","Fluorine","Neon","Sodium","Magnesium",
//        "Aluminium","Silicon","Phosphorus","Sulphur","Chlorine","Argon",
//        "Potassium","Calcium","Scandium","Titanium","Vanadium","Chromium",
//        "Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium",
//        "Germanium","Arsenic","Selenium","Bromine","Krypton","Rubidium",
//        "Strontium","Yttrium","Zirconium","Niobium","Molybdenum",
//        "Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium",
//        "Indium","Tin","Antimony","Tellurium","Iodine","Xenon","Caesium",
//        "Barium","Lanthanum","Cerium","Praseodymium","Neodymium",
//        "Promethium","Samarium","Europium","Gadolinium","Terbium",
//        "Dysprosium","Holmium","Erbium","Thulium","Ytterbium","Lutetium",
//        "Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium",
//        "Platinum","Gold","Mercury","Thallium","Lead","Bismuth","Polonium",
//        "Astatine","Radon","Francium","Radium","Actinium","Thorium",
//        "Protoactinium","Uranium","Neptunium","Plutonium","Americium",
//        "Curium","Berkelium","Californium","Einsteinium","Fermium"
//	};
			
	/**
	 * Copy constructor.
	 */
	public XPDFComposition(XPDFComposition inComp) {
		this.atomCount = new HashMap<Integer, Double>(inComp.atomCount);
		this.electronOverlap = inComp.electronOverlap;
	}

	/**
	 * Construct from a chemical formula.
	 * @param materialFormula
	 * 						The chemical formula of the substance in ASCII
	 * 						format (for example, water is H2O, hexane is C6H14)
	 */
	public XPDFComposition(String materialFormula) {
		this.atomCount = new HashMap<Integer, Double>();
		if (materialFormula == null || materialFormula.length() == 0)
			return;
		// Get xraylib to do the heavy lifting
		compoundData cD = Xraylib.CompoundParser(materialFormula);
		// cD has a list of elements and mass fractions
		double[] numberFractions = new double[cD.nElements];
		double sumNum = 0;
		for (int i = 0; i < cD.nElements; i++) {
			numberFractions[i] = cD.massFractions[i]/Xraylib.AtomicWeight(cD.Elements[i]);
			sumNum += numberFractions[i];
		}
//		double sumAtoms = 0;
		for (int i = 0; i< cD.nElements; i++) {
			numberFractions[i] *= cD.nAtomsAll/sumNum;
//			sumAtoms += numberFractions[i];
			this.atomCount.put(cD.Elements[i], numberFractions[i]);
		}

	}

	/**
	 * Constructor from a map of atomic number and atom count.
	 * @param atomCount
	 * 				Map of atomic number to count in the substance.
	 */
	public XPDFComposition(Map<Integer, Double> atomCount) {
		this.atomCount = atomCount;
		this.electronOverlap = 0.0;
	}
	
	/**
	 * Returns the mean atomic mass of this compound.
	 * @return mean atomic mass of the compound, averaged over all atoms.
	 */
	public double getMeanAtomicMass() {
		double massSum = 0.0;
		double atomSum = 0.0;
		for (Map.Entry<Integer, Double> element : atomCount.entrySet()) {
			massSum += element.getValue() * Xraylib.AtomicWeight(element.getKey());
			atomSum += element.getValue();
		}
		return massSum/atomSum;
	}

	/**
	 * Calculates the composition dependent part of the Krogh-Moe summation.
	 * @return the part of the Krogh-Moe sum that depends on the composition,
	 * 			including the electron overlap.
	 */
	public double getKroghMoeSummand() {
		double KMSum = 0.0;
		double atomSum = 0;

		for (Map.Entry<Integer, Double> iZN1 : atomCount.entrySet()) {
			atomSum += iZN1.getValue();
			for (Map.Entry<Integer, Double> iZN2 : atomCount.entrySet()) {
				KMSum += iZN1.getValue()*iZN2.getValue()*(iZN1.getKey()*iZN2.getKey() - electronOverlap);
			}
		}
		return KMSum/(atomSum*atomSum);
	}

//	/**
//	 * Transforms an ASCII chemical formula into a atomic number to atom count map.
//	 * @param chemicalFormula
//	 * 						A string holding the ASCII chemcial formula.
//	 * @return the map from atomic number to count of atoms in the compound.
//	 */
//	private static Map<Integer, Integer> mapFormula(String chemicalFormula) {
//		// Local class for parsing chemical formulae into a map from atomic number to multiplicity
//		return CompoundParser.indexElements(CompoundParser.countAtoms(CompoundParser.tokenize(chemicalFormula)));
//	}

	/**
	 * eturns the mass attenuation of the compound at the given energy. 
	 * @param beamEnergy
	 * 					The energy of the incident beam in keV.
	 * @return the mass attenuation in cm²/g
	 */
	public double getMassAttenuation(double beamEnergy) {

		double massAttenuation = 0.0;		
		double formulaMass = 0.0;
		for (Map.Entry<Integer, Double> stoichiometry : atomCount.entrySet()) {
			massAttenuation += Xraylib.AtomicWeight(stoichiometry.getKey())*stoichiometry.getValue()*XPDFMassAttenuation.get(beamEnergy, stoichiometry.getKey());
			formulaMass += Xraylib.AtomicWeight(stoichiometry.getKey())*stoichiometry.getValue();
		}
		massAttenuation /= formulaMass;
		return massAttenuation;
	}

	/**
	 * Calculates the value of g0-1 for the compound.
	 * @return g0-1
	 */
	public double getG0Minus1() {
		int nSum = 0, dSum = 0;
		double aCount = 0;
		for (Map.Entry<Integer, Double> iZN : atomCount.entrySet()) {
			nSum += iZN.getValue()*iZN.getKey();
			dSum += iZN.getValue()*Math.pow(iZN.getKey(), 2);
			aCount += iZN.getValue();
		}
		double g0Minus1 = Math.pow(nSum, 2)/(double) dSum;
		// divide by the number of atoms to normalize correctly
		g0Minus1 /= aCount;
		return g0Minus1;
	}

	/**
	 * Return the self-scattering, given the energy of the beam and angles of interest.
	 * @param coordinates
	 * 					the coordinates of the detector, and energy of the beam.
	 * @return the self-scattering cross-section in units of square classical electron radii.
	 */
	public Dataset getSelfScattering(XPDFCoordinates coordinates) {
		XPDFElectronCrossSections eXSections = new XPDFElectronCrossSections();
		eXSections.setCoordinates(coordinates);
		
//		Dataset elasticSelfScattering = Maths.multiply(eXSections.getThomsonCrossSection(), this.getElasticScatteringFactorSquared(coordinates.getX()));
//		Dataset inelasticSelfScattering = Maths.multiply(eXSections.getKleinNishinaCrossSection(), this.getInelasticScatteringFactor(coordinates.getX()));

		Dataset elasticSelfScattering = Maths.multiply(XPDFElectronCrossSections.getThomsonCrossSection(coordinates), this.getElasticScatteringFactorSquared(coordinates.getX()));
		Dataset inelasticSelfScattering = Maths.multiply(XPDFElectronCrossSections.getKleinNishinaCrossSection(coordinates, coordinates.getEnergy()), this.getInelasticScatteringFactor(coordinates.getX()));
		
		return Maths.add(elasticSelfScattering, inelasticSelfScattering);
	}
	
	/**
	 * Calculates the elastic electron scattering of the compound.
	 * @param x
	 * 			sin 2θ/λ 
	 * @return the mass weighted mean elastic scattering form factor.
	 */
	public Dataset getElasticScatteringFactor(Dataset x) {
		Dataset fofx = DoubleDataset.zeros(x);
		
		double totalAtoms = 0;
		for (Map.Entry<Integer, Double> stoichiometry : atomCount.entrySet()) {
			fofx.iadd(Maths.multiply(stoichiometry.getValue(), XPDFElementalFormFactors.fofx(stoichiometry.getKey(), x)));
			totalAtoms += stoichiometry.getValue();
		}
		fofx.idivide(totalAtoms);
		return fofx;
	}
	
	/**
	 * Calculates the squared elastic electron scattering form factor of the compound.
	 * @param x
	 * 			sin 2θ/λ
	 * @return the mass weighted mean squared elastic scattering form factor.
	 */
	public Dataset getElasticScatteringFactorSquared(Dataset x) {
		Dataset fsquaredofx = DoubleDataset.zeros(x);
		
		double totalAtoms = 0;
		for (Map.Entry<Integer, Double> stoichiometry : atomCount.entrySet()) {
			fsquaredofx.iadd(Maths.multiply(stoichiometry.getValue(), Maths.square(XPDFElementalFormFactors.fofx(stoichiometry.getKey(), x))));
			totalAtoms += stoichiometry.getValue();
		}
		fsquaredofx.idivide(totalAtoms);
		return fsquaredofx;
	}
	
	/**
	 * Calculates the inelastic electron scattering form factor of the compound.
	 * @param x
	 * 			sin 2θ/λ
	 * @return the mass weighted mean inelastic scattering form factor.
	 */
	public Dataset getInelasticScatteringFactor(Dataset x) {
		Dataset Sofx = DoubleDataset.zeros(x);
		
		double totalAtoms = 0;
		for (Map.Entry<Integer, Double> stoichiometry : atomCount.entrySet()) {
			Sofx.iadd(Maths.multiply(stoichiometry.getValue(), XPDFElementalFormFactors.sofx(stoichiometry.getKey(), x)));
			totalAtoms += stoichiometry.getValue();
		}
		Sofx.idivide(totalAtoms);
		return Sofx;
	}

	/**
	 * Determines whether two compositions are similar enough to count as equal
	 * for the purposes of absorption.
	 * @param inCompo
	 * 				the composition to compare with this
	 * @return are the two compositions equivalent?
	 */
	public boolean isEqualToForAbsorption(XPDFComposition inCompo) {
		if (inCompo == null)
			return false;
		final double maximumDifference = 1e-6;

		// check there are the same number of elements in each compound 
		if (atomCount.size() != inCompo.atomCount.size())
			return false;
		
		// Check all elements present in this are present in inCompo and vice versa
		if (!inCompo.atomCount.keySet().containsAll(atomCount.keySet()) ||
				!atomCount.keySet().containsAll(inCompo.atomCount.keySet()))
			return false;
		
		for (Map.Entry<Integer, Double> entry : atomCount.entrySet())
			if (entry.getValue() - inCompo.atomCount.get(entry.getKey()) > maximumDifference)
				return false;
		
		return true;
	}

	/**
	 * Returns the fraction of atoms with in the compound with atomic number z.
	 * <p>
	 * Given an atomic number, z, return the fraction of atoms are of this
	 * element. If z is not part of the composition, then return 0. 
	 * @param z
	 * 			the atomic number to be queried.
	 * @return the fraction of atoms with this atomic number.
	 */
	public double atomFraction(int z) {
		if (atomCount.containsKey(z)) {
			double num = atomCount.get(z);
			double den = 0;
			for (double nZ : atomCount.values())
				den += nZ;
			return num/den;
		} else 
			return 0.0;
	}

	/**
	 * Weights the composition by a constant factor
	 * @param weight
	 * 				the weigthing factor to apply
	 */
	public void weight(double weight) {
		for (Map.Entry<Integer, Double> entry : atomCount.entrySet()) {
			atomCount.put(entry.getKey(), entry.getValue()*weight);
		}
	}

	/**
	 * Adds all the atoms of another composition to this one.
	 * @param compo2
	 * 				the composition whose atoms to add
	 */
	public void add(XPDFComposition compo2) {
		for (Map.Entry<Integer, Double> entry : compo2.atomCount.entrySet()) {
			if (atomCount.containsKey(entry.getKey()))
				atomCount.put(entry.getKey(), atomCount.get(entry.getKey()) + entry.getValue());
			else
				atomCount.put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public String toString() {
		return getHallNotation(true);
	}
	
	/**
	 * Returns a list of data about the strongest x-ray fluorescences.
	 * @param energy
	 * 				energy of the exciting beam in keV.
	 * @param nLines
	 * 				the maximum number of lines to return.
	 * @return the fluorescence data as a List of {@link XPDFFluorescentLine}s. 
	 */
	public List<XPDFFluorescentLine> getFluorescences(double energy, int nLines) {
		// TODO: xraylib fluorescence lines
		List<XPDFFluorescentLine> fluorescences = new ArrayList<XPDFFluorescentLine>();
//		int[] lineIndices = new int[] {Xraylib.KA1_LINE, Xraylib.KA2_LINE, Xraylib.KB1_LINE, Xraylib.LA1_LINE, Xraylib.LA2_LINE};
		
		// Loop over elements present
		for (int z : atomCount.keySet()) {
			// Loop over fluorescence line indices
			for (int line : getLineIndices(z)) {
				double lineEnergy = Xraylib.LineEnergy(z, line);
				double lineBarns = Xraylib.CSb_FluorLine_Kissel_Cascade(z, line, energy);
				final double minEnergy = 1.0;
				final double minBarns = 10.0;
				if (lineEnergy > minEnergy && lineBarns > minBarns)
					fluorescences.add(new XPDFFluorescentLine(lineEnergy, lineBarns, z));
			}
		}
		
		
//		fluorescences.clear();
//		
//		// Beginning of hard-coded data
//		if (atomCount.keySet().size() == 2 && 
//				atomCount.containsKey(58) && atomCount.get(58) == 1 &&
//				atomCount.containsKey(8) && atomCount.get(8) == 2)
//			// ceria, CeO2
//			fluorescences = Arrays.asList(
//					new XPDFFluorescentLine(34.7196, 444.94994401, 58),
//					new XPDFFluorescentLine(34.2788, 243.00829748, 58),
//					new XPDFFluorescentLine(39.2576, 87.69485581, 58),
//					new XPDFFluorescentLine(4.8401, 48.11886857, 58),
//					new XPDFFluorescentLine(5.2629, 28.32794357, 58));
//
//		else if (atomCount.keySet().size() == 3 &&
//				atomCount.containsKey(56) && atomCount.get(56) == 1 &&
//				atomCount.containsKey(22) && atomCount.get(22) == 1 &&
//				atomCount.containsKey(8) && atomCount.get(8) == 3)
//			// barium titanate, BaTiO3
//			fluorescences = Arrays.asList(
//					new XPDFFluorescentLine(32.1936, 388.27213844, 56),
//					new XPDFFluorescentLine(31.817, 210.42217958, 56),
//					new XPDFFluorescentLine(36.378, 75.39646264, 56),
//					new XPDFFluorescentLine(4.4663, 37.42569601, 56),
//					new XPDFFluorescentLine(4.8275, 21.92040699, 56));
//
//		else if (atomCount.keySet().size() == 1 &&
//				 atomCount.containsKey(74))
//			// tungsten, W
//			fluorescences = Arrays.asList(
//					new XPDFFluorescentLine(59.3182, 1019.37506384, 74),
//					new XPDFFluorescentLine(57.981, 586.69404138, 74),
//					new XPDFFluorescentLine(67.244, 219.28240133, 74),
//					new XPDFFluorescentLine(8.3976, 239.26301778, 74),
//					new XPDFFluorescentLine(9.6724, 156.7324558, 74));
//
//		else if (atomCount.keySet().size() == 1 &&
//				atomCount.containsKey(28))
//			// nickel, Ni
//			fluorescences = Arrays.asList(
//					new XPDFFluorescentLine(7.4781, 12.68440462, 28));			
//
//		else
//			fluorescences.clear();
//
//		// end of hard-coded data
		return fluorescences;
	}

	private List<Integer> getLineIndices(int z) {
		List<Integer> lineIndices = new ArrayList<Integer>();//Arrays.asList(new Integer[] {Xraylib.KA1_LINE, Xraylib.KA2_LINE, Xraylib.KB1_LINE, Xraylib.LA1_LINE, Xraylib.LA2_LINE});
		
		if (z > 4)
			lineIndices.add(Xraylib.KA2_LINE);
		if (z > 6)
			lineIndices.add(Xraylib.KA1_LINE);
		if (z > 14)
			lineIndices.add(Xraylib.KB1_LINE);
		if (z > 22)
			lineIndices.add(Xraylib.LB1_LINE);
		if (z > 24)
			lineIndices.add(Xraylib.LA1_LINE);
		return lineIndices;	
	}
	
	public double getPhotoionizationAttenuation(double beamEnergy) {
		double massAttenuation = 0.0;		
		double formulaMass = 0.0;
		for (Map.Entry<Integer, Double> stoichiometry : atomCount.entrySet()) {
			massAttenuation += Xraylib.AtomicWeight(stoichiometry.getKey())*stoichiometry.getValue()*XPDFMassAttenuation.getPhoto(beamEnergy, stoichiometry.getKey());
			formulaMass += Xraylib.AtomicWeight(stoichiometry.getKey())*stoichiometry.getValue();
		}
		massAttenuation /= formulaMass;
		return massAttenuation;
	}

	/**
	 * Returns the chemical formula of this phase.
	 * <p>
	 * Returns the chemical formula of this phase in Hall notation.
	 * @return chemical formula in Hall notation.
	 */
	public String getHallNotation(boolean useSubscripts) {
		
		final int nElements = 100; // Up to and including fermium
		
		final String[] elementSymbol = { "n",
		"H","He","Li","Be","B","C","N","O","F","Ne",
		"Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca",
		"Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn",
		"Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn",
        "Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd",
        "Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb",
        "Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg",
        "Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th",
        "Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm"};
		
		final int[] alphabeticElements = {
				89, 47, 13, 95, 18, 33, 85, 79, 5, 56,
				4, 83, 97, 35, 6, 20, 48, 58, 98, 17,
				96, 27, 24, 55, 29, 66, 68, 99, 63, 9,
				26, 100, 87, 31, 64, 32, 1, 2, 72, 80,
				67, 53, 49, 77, 19, 36, 57, 3, 71, 12,
				25, 42, 7, 11, 41, 60, 10, 28, 93, 8,
				76, 15, 91, 82, 46, 61, 84, 59, 78, 94,
				88, 37, 75, 45, 86, 44, 16, 51, 21, 34,
				14, 62, 50, 38, 73, 65, 43, 52, 90, 22,
				81, 69, 92, 23, 74, 54, 39, 70, 30, 40
		};

		
		String hall = "";
		Map<Integer, Double> atomCount = new HashMap<Integer, Double>(this.atomCount);
		
		// Do the special case for organic compounds
		if (atomCount.containsKey(6)) {
			hall += elementSymbol[6] + prettifyDouble(atomCount.get(6), useSubscripts);
			atomCount.remove(6);
			if (atomCount.containsKey(1)) {
				hall += elementSymbol[1] + prettifyDouble(atomCount.get(1), useSubscripts);
				atomCount.remove(1);
			}
		}
		
		// Do all the remaining elements in order
		for (int i = 0; i < nElements; i++) { 
			int z = alphabeticElements[i];
			if (atomCount.containsKey(z))
				hall += elementSymbol[z] + prettifyDouble(atomCount.get(z), useSubscripts);
		}
		return (hall.length() != 0) ? hall : "-";
	}
	
	// Format the atom multiplicity as nicely as we can
	private String prettifyDouble(double n, boolean useSubscripts) {
		if (n == 1.0)
			return "";
		
		// round to the nearest 1/1024 (can this be done in the DecimalFormat?)
		double factor = 1024.0;
		double nr = Math.round(factor*n)/factor;
		
		DecimalFormat formattor = new DecimalFormat( (nr == (double) Math.round(nr)) ? "0" : "0.000");
		
		String number = formattor.format(nr);
		if ("1".equals(number)) number = "";
		String normalNumbers = "0123456789.";
		String subscriptNumbers = "₀₁₂₃₄₅₆₇₈₉.";
		
		if (useSubscripts)
			for (int i = 0; i < normalNumbers.length(); i++ )
				number = number.replace(normalNumbers.charAt(i), subscriptNumbers.charAt(i));
		
		return number;
	}
	
}

///**
// * Private class to do the chemical formula parsing.
// * @author Timothy Spain timothy.spain@diamond.ac.uk
// * @since 2015-09-14
// *
// */
//class CompoundParser {
//
//	/**
//	 * Static list of element names.
//	 * <p>
//	 * Names of the elements as an unmodifiable list. The neutron is added as
//	 * element 0, so that index equals atomic number
//	 */
//	private static List<String> elements = Collections.unmodifiableList(Arrays
//			.asList("n",
//					"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na",
//					"Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc",
//					"Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga",
//					"Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb",
//					"Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb",
//					"Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm",
//					"Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu",
//					"Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl",
//					"Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa",
//					"U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm"));
//
//	
//	/**
//	 * RL tokenization of the compound formula
//	 * @param compoundIn
//	 * 					Compound chemical formula, presented in ASCII (for 
//	 * 					example water is H2O, glucose is C6H12O6)
//	 * @return the list of tokens, comprising element names, brackets and multiplicities.
//	 */
//	public static List<String> tokenize(String compoundIn) {
////		List<String> fakeTok = Arrays.asList( CompoundParser.elements.get(1) );
//		//return fakeTok;
//		List<String> tokens = new Vector<String>();
//		
//		if (compoundIn != null) {
//			// Read a character from the end of the String
//			while (compoundIn.length() > 0) {
//				int charRemove = 0;
//				String tok = compoundIn.substring(compoundIn.length() - 1);
//
//				// Characterise the character in the string. Digits and lower
//				// case letters are special cases, otherwise a single character
//				// is a token (caps and parens)
//				if (Character.isDigit(tok.charAt(0))) {
//					// Get all the characters from the end of the String until the next non-digit
//					String digits = "0123456789";
//					charRemove = compoundIn.length() - CompoundParser.lastIndexOfAnyBut(compoundIn, digits) - 1;
//				} else if (Character.isLowerCase(tok.charAt(0))) {
//					charRemove = 2;
//				} else {
//					charRemove = 1;
//				}
//				tokens.add(0, compoundIn.substring(compoundIn.length() - charRemove , compoundIn.length()));
//				compoundIn = compoundIn.substring(0, compoundIn.length() - charRemove);
//			}
//		}
//		return tokens;
//	}
//	
//	
//	/**
//	 * Returns the index of the last character that does not appear in the search string.
//	 * <p>
//	 * Takes a valid string, and return the last index of all the
//	 * characters that do not occur in the search string.
//	 * @param str
//	 * 			string to be interrogated.
//	 * @param searchChars
//	 * 					string of the characters to be ignored.
//	 * @return
//	 * 		Index of the last character tha does not occur in the search string
//	 */
//	private static int lastIndexOfAnyBut(String str, String searchChars) {
//		
//		int lastInd;
//		for (lastInd= str.length()-1; (lastInd >= 0) && (searchChars.indexOf(str.charAt(lastInd)) != -1) ; --lastInd)
//			;
//
//		return lastInd;
//	}
//
//
//	/**
//	 * Returns a map from elements symbols to counts of atoms.
//	 * @param tokens
//	 * 				the ASCII chemical formula represented as a list of tokens.
//	 * @return map from chemical symbol to multiplicity of atoms in the compound.
//	 */
//	public static Map<String, Integer> countAtoms(List<String> tokens) {
//
//		Map<String, Integer> atomCount = new HashMap<String, Integer>();
//		
//		// Read from the end of the list of tokens. If a number is encountered,
//		// push a number and a non-number. If a non-number is encountered, push
//		// 1 and the non-number
//		
//		Stack<Integer> mults = new Stack<Integer>();
//		mults.push(1);
//		
//		while (tokens.size() > 0) {
//			String toke = tokens.get(tokens.size()-1);
//			if (toke.equals("(")) {
//				mults.pop();
//				tokens.remove(tokens.size()-1);
//				continue;
//			} else if ("0123456789".indexOf(toke.substring(toke.length()-1)) != -1) {
//				mults.push(Integer.parseInt(toke)*mults.peek());
//				tokens.remove(tokens.size()-1);
//				toke = tokens.get(tokens.size()-1);
//			} else {
//				mults.push(1*mults.peek());
//			}
//
//			// close paren: Remove the token. Cycle
//			if (!toke.equals(")")) {
//			    // element. Add the atoms to the accumulator. pop the last
//				// number. Remove the token. Cycle
//				if (atomCount.containsKey(toke)) {
//					int oldCount = atomCount.get(toke);
//					atomCount.put(toke, oldCount+mults.peek());
//				} else {
//					atomCount.put(toke, mults.peek());
//				}
//				mults.pop();
//			}
//			tokens.remove(tokens.size()-1);
//		}
//
//		return atomCount;
//	}
//	
//	/**
//	 * Converts a map of element symbols-to-multiplicities to a map of atomic number-to-multiplicities.
//	 * @param siMap
//	 * 			map of element symbols to multiplicities.
//	 * @return map of atomic number-to-multiplicities.
//	 */
//	public static Map<Integer, Integer> indexElements(Map<String, Integer> siMap) {
//	
//		Map<Integer, Integer> iMap = new HashMap<Integer, Integer>();
//
//		for	(String eleSym : siMap.keySet()) {
//			int zed = elements.indexOf(eleSym);
//			iMap.put(zed, siMap.get(eleSym));
//		}
//		
//		return iMap;
//	}
//	
//}
