/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;

public enum PeakType {
	GAUSSIAN("Gaussian", Gaussian.class),
	PSEUDO_VOIGT("Pseudo-Voigt", PseudoVoigt.class),
	VOIGT("Voigt", Voigt.class),
	LORENTZIAN("Lorentz, Cauchy, Breit-Wigner", Lorentzian.class),
	PEARSON_VII("Pearson VII", PearsonVII.class),
	;

	private String name;
	private Class<? extends IPeak> clazz;

	PeakType(String name, Class<? extends IPeak> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return name;
	}

	public Class<? extends IPeak> getPeakClass() {
		return clazz;
	}

	public static APeak createPeak(PeakType type, IdentifiedPeak peak) {
		APeak p;
		switch (type) {
		case LORENTZIAN:
		case PEARSON_VII:
		case PSEUDO_VOIGT:
		case VOIGT:
		case GAUSSIAN:
			try {
				p = (APeak) type.getPeakClass().getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				p = new Gaussian();
			}
			break;
		default:
			p = new Gaussian();
			break;
		}
		if (peak != null) {
			p.setParameters(peak);
		}
		return p;
	}

	public static APeak createPeak(PeakType type, double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		APeak p = createPeak(type, null);
		p.internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);
		return p;
	}
}
