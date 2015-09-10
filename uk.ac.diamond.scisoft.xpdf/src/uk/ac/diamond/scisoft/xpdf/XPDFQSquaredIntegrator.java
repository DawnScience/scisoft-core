/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * 
 * Perform the q² integrals, having had the momentum transfer array and the
 * beam energy set.
 * 
 * @author Timothy Spain
 *
 */
public class XPDFQSquaredIntegrator {

	Dataset q;
	XPDFElectronCrossSections eXSections;
	//	static final double classicalElectronRadius = 2.8179403267e-15;//(27)
	static final double classicalElectronRadius = 1.0;
	static final double electronMasskeV = 510.998910;//(13)
	static final double breitDiracPower = 2.0;
	
	public XPDFQSquaredIntegrator() {
		q = null;
		eXSections = null;
	}

	public XPDFQSquaredIntegrator(XPDFCoordinates coordinates) { //Dataset twoTheta, XPDFBeamData beamData) {
		q = coordinates.getQ();
		//		q = beamData.getQFromTwoTheta(twoTheta);
		eXSections = new XPDFElectronCrossSections();
		eXSections.setCoordinates(coordinates);
		//		eXSections.setAngles(twoTheta);
//		eXSections.setBeamEnergy(beamData.getBeamEnergy());
	}
	
	public XPDFQSquaredIntegrator(XPDFQSquaredIntegrator qq) {
		this.q = qq.q;
		this.eXSections = (qq.eXSections != null) ? qq.eXSections : null;
	}

	// Calculate an integral over q², given a function and some angles
	public double qSquaredIntegral(Dataset fn) {
		Dataset dq = XPDFQSquaredIntegrator.differentiate1DDataset(q);
		double dqScalar = dq.getDouble(0);
		Dataset integrand = Maths.multiply(Maths.multiply(Maths.multiply(q, q), fn), dqScalar);
		double integral = quadrate1DDataset(integrand);
		return integral;
	}
	
	public double ThomsonIntegral(Dataset fn) {
		double qqIntegral = qSquaredIntegral(Maths.divide(fn, eXSections.getThomsonCrossSection()));
		return qqIntegral;
	}
	
	// TODO: a more sophisticated quadrature formula than the rectangle rule
	private static double quadrate1DDataset(Dataset integrand) {
		return (double) integrand.sum();
	}

	// Second order correct finite difference approximation to the first
	// derivative of a 1-d Dataset, with respect to the index
	private static Dataset differentiate1DDataset(Dataset y) {
		Dataset deriv = DoubleDataset.zeros(y);
		if (y.getSize() > 1) {
			if (y.getSize() == 2) {
				double dderiv = y.getDouble(1) - y.getDouble(0);
				deriv.set(dderiv, 0);
				deriv.set(dderiv, 1);
			} else {
				// Three points or more
				int iLast = y.getSize()-1;
				// End points
				deriv.set((-3*y.getDouble(0) + 4*y.getDouble(1) - y.getDouble(2))/2, 0);
				deriv.set((y.getDouble(iLast-2) - 4*y.getDouble(iLast-1) + 3*y.getDouble(iLast))/2, iLast);
				// The rest of the points
				for (int i = 1; i < iLast; i++)
					deriv.set((y.getDouble(i+1) - y.getDouble(i-1))/2, i);
			}
		}
		return deriv;
	}

}
