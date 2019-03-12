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

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;

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
	
	private static String normalNumbers = "0123456789.";
	private static String subscriptNumbers = "₀₁₂₃₄₅₆₇₈₉.";

	private static double comptonScaling = 1.0;
			
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

		// replace subscripts with their ASCII equivalents, which look nice, but Xraylib does not like.
		String asciified = new String(materialFormula);
		for (int i = 0; i < normalNumbers.length(); i++)
			asciified = asciified.replace(subscriptNumbers.charAt(i), normalNumbers.charAt(i));
		// I keep using hyphens to denote empty compositions, and xraylib keeps not liking them.
		asciified = asciified.replace("-", "");
		
		// Get xraylib to do the heavy lifting
		compoundData cD = Xraylib.CompoundParser(materialFormula.trim());
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

	public double getFormulaMass() {
		double massSum = 0.0;
		for (Map.Entry<Integer, Double> element : atomCount.entrySet()) {
			massSum += element.getValue() * Xraylib.AtomicWeight(element.getKey());
		}
		return massSum;
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

	/**
	 * Returns the mass attenuation of the compound at the given energy. 
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
	 * Returns the self-scattering, given the energy of the beam and angles of interest.
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

		inelasticSelfScattering.imultiply(comptonScaling);
		
		return Maths.add(elasticSelfScattering, inelasticSelfScattering);
	}
	
	/**
	 * Calculates the elastic electron scattering of the compound.
	 * @param x
	 * 			sin 2θ/λ 
	 * @return the mass weighted mean elastic scattering form factor.
	 */
	public Dataset getElasticScatteringFactor(Dataset x) {
		Dataset fofx = DatasetFactory.zeros(x, DoubleDataset.class);
		
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
		Dataset fsquaredofx = DatasetFactory.zeros(x, DoubleDataset.class);
		
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
		Dataset Sofx = DatasetFactory.zeros(x, DoubleDataset.class);
		
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
		
		if (useSubscripts)
			for (int i = 0; i < normalNumbers.length(); i++ )
				number = number.replace(normalNumbers.charAt(i), subscriptNumbers.charAt(i));
		
		return number;
	}
	
	public static void setComptonScaling(double scaling) {
		comptonScaling = scaling;
	}
}

