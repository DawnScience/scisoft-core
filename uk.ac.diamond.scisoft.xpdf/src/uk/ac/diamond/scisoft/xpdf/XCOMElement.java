/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;

/**
 * A class to return the XCOM X-ray cross sections for an element.
 * <p>
 * Constructed from an XCOM data file, each object can return any of the XCOM 
 * cross-sections at a given energy. The possible cross sections are:
 * <ul>
 * <li>Coherent scattering
 * <li>Incoherent scattering
 * <li>photoelectric absorption
 * <li>nuclear pair production
 * <li>electronic pair production
 * <li>total cross-section
 * <li>total cross-section without coherent scattering
 * <p>
 * Each of these may be accessed by its own method, or by a master method which
 * also takes the name of the requested cross-section.
 * 
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2014-09-28
 *
 */
public class XCOMElement {
	
	private static final int nEnergiesXCom = 80;
	@SuppressWarnings("unused")
	private int atomicNumber;
	private double atomicMass;
	private int nEdges;
	private int nEnergies;
	private List<Integer> edgeIndices;
	// Don't care about edge names
	private List<Double> edgeEnergies;
	private List<Double> energies;
	private List<Double> logEnergies;
	private List<Double> coherentScatteringSigma;
	private List<Double> incoherentScatteringSigma;
	private List<Double> photoelectricSigma;
	private List<Double> nuclearPairSigma;
	private List<Double> electronPairSigma;
	private List<Integer> edgeMulitplicity; // TODO: more descriptive name
	private List<List<Double>> edgeData1; // TODO: more descriptive name
	private List<List<Double>> edgeData2; // TODO: more descriptive name
	/**
	 * Default constructor.
	 */
	public XCOMElement() {
		this.nEdges = 0;
		this.nEnergies = nEnergiesXCom + nEdges*2;
		this.edgeIndices = new ArrayList<Integer>();
		this.edgeEnergies = new ArrayList<Double>();
		this.energies = Arrays.asList(1.0E+00,1.5E+00,2.0E+00,3.0E+00,4.0E+00,5.0E+00,
				6.0E+00,8.0E+00,1.0E+01,1.5E+01,2.0E+01,3.0E+01,4.0E+01,
				5.0E+01,6.0E+01,8.0E+01,1.0E+02,1.5E+02,2.0E+02,3.0E+02,
				4.0E+02,5.0E+02,6.0E+02,8.0E+02,1.0E+03,1.022E+03,1.25E+03,
				1.5E+03,2.0E+03,2.044E+03,3.0E+03,4.0E+03,5.0E+03,6.0E+03,
				7.0E+03,8.0E+03,9.0E+03,1.0E+04,1.1E+04,1.2E+04,1.3E+04,
				1.4E+04,1.5E+04,1.6E+04,1.8E+04,2.0E+04,2.2E+04,2.4E+04,
				2.6E+04,2.8E+04,3.0E+04,4.0E+04,5.0E+04,6.0E+04,8.0E+04,
				1.0E+05,1.5E+05,2.0E+05,3.0E+05,4.0E+05,5.0E+05,6.0E+05,
				8.0E+05,1.0E+06,1.5E+06,2.0E+06,3.0E+06,4.0E+06,5.0E+06,
				6.0E+06,8.0E+06,1.0E+07,1.5E+07,2.0E+07,3.0E+07,4.0E+07,
				5.0E+07,6.0E+07,8.0E+07,1.0E+08);
		this.logEnergies = new ArrayList<Double>();
		for (double energy : this.energies) this.logEnergies.add(Math.log(energy));
		this.coherentScatteringSigma = new ArrayList<Double>();
		this.incoherentScatteringSigma = new ArrayList<Double>();
		this.photoelectricSigma = new ArrayList<Double>();
		this.nuclearPairSigma = new ArrayList<Double>();
		this.electronPairSigma = new ArrayList<Double>();
		this.edgeMulitplicity = new ArrayList<Integer>();
		this.edgeData1 = new ArrayList<List<Double>>();
		this.edgeData2 = new ArrayList<List<Double>>();
	}
	
	/**
	 * Constructor from atomic number.
	 */
	public XCOMElement(int atomicZ) {
		
		String rawData = XCOMData.get(atomicZ);
		Scanner s = new Scanner(rawData);
		// Line 1: atomic number and mass
		this.atomicNumber = s.nextInt();
		this.atomicMass = s.nextDouble();
		// Line 2: number of edges, number of energy samples
		this.nEdges = s.nextInt();
		this.nEnergies = s.nextInt();
		// Line 3: indices of the edges in the energy array. The original array
		// has a 1-based index, so subtract 1.
		this.edgeIndices = new ArrayList<Integer>(nEdges);
		for (int iEdge = 0; iEdge < nEdges; iEdge++)
			this.edgeIndices.add(iEdge, s.nextInt()-1);
		// Line 4; names of the edges. Discarded
		for (int iEdge = 0; iEdge < nEdges; iEdge++)
			s.next();
		// Line 5: energies of the edges, eV in the rawData, want to store keV.
		this.edgeEnergies = new ArrayList<Double>(nEdges);
		for (int iEdge = 0; iEdge < nEdges; iEdge++)
		this.edgeEnergies.add(iEdge, s.nextDouble()*1e-3);

		// Next nEnergy values: values of the energy coordinate, store as is
		// and as natural logarithm.
		this.logEnergies = new ArrayList<Double>(nEnergies);
		this.energies = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++) {
			double energy = s.nextDouble()*1e-3;
			this.energies.add(iEnergy, energy);
			this.logEnergies.add(iEnergy, Math.log(energy));
		}

		// Next nEnergy values: coherent scattering cross-section in barns,
		// store as natural logarithm
		this.coherentScatteringSigma = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++)
			this.coherentScatteringSigma.add(iEnergy, Math.log(s.nextDouble()));
		// Next nEnergy values: incoherent scattering cross-section in barns,
		// store as natural logarithm.
		this.incoherentScatteringSigma = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++)
			this.incoherentScatteringSigma.add(iEnergy, Math.log(s.nextDouble()));
		// Next nEnergy values: photoelectric absorption cross-section in
		// barns, store as natural logarithm.
		this.photoelectricSigma = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++)
			this.photoelectricSigma.add(iEnergy, Math.log(s.nextDouble()));
		// Next nEnergy values: nuclear pair production cross-section in barns,
		// store as natural logarithm.
		this.nuclearPairSigma = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++)
			this.nuclearPairSigma.add(iEnergy, Math.log(s.nextDouble()));
		// Next nEnergy values: electronic pair production cross-section in
		// barns, store as natural logarithm.
		this.electronPairSigma = new ArrayList<Double>(nEnergies);
		for (int iEnergy = 0; iEnergy < nEnergies; iEnergy++)
			this.electronPairSigma.add(iEnergy, Math.log(s.nextDouble()));
		
		if (nEdges > 0) {
			// number of edges again
			s.nextInt();
			// number of coefficients per edge
			this.edgeMulitplicity = new ArrayList<Integer>(nEdges);
			for (int iEdge = 0; iEdge < nEdges; iEdge++)
				edgeMulitplicity.add(iEdge, s.nextInt());
			// next sum(edgeMultiplicity) values: edgeData1.
			// edge energy grid points in MeV. Stored as natural logarithm of
			// keV.
			this.edgeData1 = new ArrayList<List<Double>>(nEdges);
			for (int iEdge = 0; iEdge < nEdges; iEdge++) {
				edgeData1.add(iEdge, new ArrayList<Double>(edgeMulitplicity.get(iEdge)));
				for (int i = 0; i < edgeMulitplicity.get(iEdge); i++)
					edgeData1.get(iEdge).add(i, Math.log(s.nextDouble()*1e3));
			}
			// next sum(edgeMultiplicity) values: edgeData2.
			// edge photoelectric cross-sections in barns, stored as natural 
			// logarithm.
			this.edgeData2 = new ArrayList<List<Double>>(nEdges);
			for (int iEdge = 0; iEdge < nEdges; iEdge++) {
				edgeData2.add(iEdge, new ArrayList<Double>(edgeMulitplicity.get(iEdge)));
				for (int i = 0; i < edgeMulitplicity.get(iEdge); i++)
					edgeData2.get(iEdge).add(i, Math.log(s.nextDouble()));
			}
		}		
		s.close();
	}

	/**
	 * Calculates an elemental cross section.
	 * <p>
	 * Calculates one of the available cross-sections for the element. The possible cross-sections are:
	 * <ul>
	 * <li>"coherent"
	 * <li>"incoherent"
	 * <li>"photoelectric"
	 * <li>"nuclear pair production"
	 * <li>"electronic pair production"
	 * <li>"total"
	 * <li>"total without coherent"
	 * </ul>
	 * @param photonEnergy
	 * 					energy of the incident photon in keV.
	 * @param crossSectionType
	 * 						String description of the desired cross-section.
	 * @return the calculated cross section in barns = 1e-30 m²
	 */
	public double getCrossSection(double photonEnergy, String crossSectionType) {
		switch (crossSectionType) {
		case "coherent":
			return getInCoherentCrossSection(photonEnergy, coherentScatteringSigma);
		case "incoherent":
			return getInCoherentCrossSection(photonEnergy, incoherentScatteringSigma);
		case "photoelectric":
			return getPhotoelectricCrossSection(photonEnergy);
		case "nuclear pair production":
			return 0.0;
		case "electronic pair production":
			return 0.0;
		case "total":
			return getCrossSection(photonEnergy, "total without coherent") + 
					getCrossSection(photonEnergy, "coherent");
		case "total without coherent":
			return getCrossSection(photonEnergy, "incoherent") + 
					getCrossSection(photonEnergy, "photoelectric") + 
					getCrossSection(photonEnergy, "nuclear pair production") + 
					getCrossSection(photonEnergy, "electronic pair production");
		default:
			return 0.0;
		}
	}
	
	/**
	 * Calculates the coherent or incoherent scattering.
	 * @param photonEnergy
	 * 					energy of the incident photon.
	 * @param scatteringSigma
	 * 						table of log values to interpolate
	 * @return coherent or incoherent electron scattering cross-section in barns per atom.
	 */
	private double getInCoherentCrossSection(double photonEnergy, List<Double> scatteringSigma) {

		double logPhotonEnergy = Math.log(photonEnergy);
		SplineInterpolator splint = new SplineInterpolator();
		if (logPhotonEnergy < logEnergies.get(0)) 
			return scatteringSigma.get(0);
		else if (logPhotonEnergy > logEnergies.get(nEnergies-1))
			return scatteringSigma.get(nEnergies-1);
		else
			return Math.exp(splint.interpolate(
					ArrayUtils.toPrimitive(logEnergies.toArray(new Double[nEnergies])),
					ArrayUtils.toPrimitive(scatteringSigma.toArray(new Double[nEnergies]))).value(logPhotonEnergy));
	}

	/**
	 * Calculates the the photoelectric attenuation.
	 * @param photonEnergy
	 * 					energy of the incident photon
	 * @return photoelectric attenuation cross-section of the element in barns per atom.
	 */
	private double getPhotoelectricCrossSection(double photonEnergy) {
		
		double logPhotonEnergy = Math.log(photonEnergy);
		if  (logPhotonEnergy < logEnergies.get(0))
			return photoelectricSigma.get(0);
		else if (logPhotonEnergy > logEnergies.get(nEnergies-1))
			return photoelectricSigma.get(nEnergies-1);
		else
			if (nEdges == 0 || photonEnergy > this.edgeEnergies.get(0)) {
				// An energy above the K-edge is just log-log spline interpolated
				List<Double> logE = logEnergies, logSigma = photoelectricSigma;
				
				if (nEdges != 0) {
					logE = logEnergies.subList(edgeIndices.get(0), logEnergies.size());
					logSigma = photoelectricSigma.subList(edgeIndices.get(0), logEnergies.size());
				}
				double tresult = Math.exp((new SplineInterpolator()).interpolate(
						ArrayUtils.toPrimitive(logE.toArray(new Double[logE.size()])),
						ArrayUtils.toPrimitive(logSigma.toArray(new Double[logSigma.size()]))).value(logPhotonEnergy));
				return tresult;

			} else {
				// In amongst the edges
				// 
				List<Double> reverseEdgeEnergies = new ArrayList<Double>(edgeEnergies);
				Collections.reverse(reverseEdgeEnergies);
				int iEdge = Collections.binarySearch(reverseEdgeEnergies, photonEnergy);
				// Select the edge data to use. on the edge (exactly) is taken as being below the edge.
				iEdge = (iEdge < 0) ? -iEdge-1 : iEdge;
				double tresult = Math.exp((new LinearInterpolator()).interpolate(
						ArrayUtils.toPrimitive(edgeData1.get(iEdge).toArray(new Double[edgeMulitplicity.get(iEdge)])),
						ArrayUtils.toPrimitive(edgeData2.get(iEdge).toArray(new Double[edgeMulitplicity.get(iEdge)]))).value(logPhotonEnergy));
				return tresult;
			}
	}

	/**
	 * Calculates an elemental cross section.
	 * <p>
	 * Calculates one of the available attenuation coefficients for the element. The possible coefficients are:
	 * <ul>
	 * <li>"coherent"
	 * <li>"incoherent"
	 * <li>"photoelectric"
	 * <li>"nuclear pair production"
	 * <li>"electronic pair production"
	 * <li>"total"
	 * <li>"total without coherent"
	 * </ul>
	 * @param photonEnergy
	 * 					energy of the incident photon in keV.
	 * @param AttenuationType
	 * 						String description of the attenuation coefficient.
	 * @return the calculated attenuation coefficient in cm²/g
	 */
	public double getAttenuation(double photonEnergy, String AttenuationType) {
		final double nAvogadro = 6.022140857e23;
		final double cm2perbarn = 1e-24;
		
		return  getCrossSection(photonEnergy, AttenuationType) * 
				nAvogadro /
				this.atomicMass *
				cm2perbarn;				
	}
	
}
