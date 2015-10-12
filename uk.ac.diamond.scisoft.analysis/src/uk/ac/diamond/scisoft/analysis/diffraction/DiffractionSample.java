/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;

import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;

public class DiffractionSample {

	DiffractionCrystalEnvironment env;
	UnitCell uc;

	public DiffractionSample(DiffractionCrystalEnvironment environment, UnitCell unitCell) {
		this.env = environment;
		this.uc = unitCell;
	}

	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
		return env;
	}

	public UnitCell getUnitCell() {
		return uc;
	}
}
