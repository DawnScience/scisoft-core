/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;

public class DiffractionMetadata extends ExtendedMetadata implements IDiffractionMetadata {
	private static final long serialVersionUID = IMetadata.serialVersionUID;

	private DetectorProperties props, oProps;
	private DiffractionCrystalEnvironment env, oEnv;

	public DiffractionMetadata(String filename, DetectorProperties props, DiffractionCrystalEnvironment env) {
		super((filename != null) ? new File(filename) : null);
		setDiffractionMetadata(props, env);
	}

	void setDiffractionMetadata(DetectorProperties props, DiffractionCrystalEnvironment env) {
		oProps = props;
		this.props = oProps == null ? null : oProps.clone();
		oEnv = env;
		this.env = oEnv == null ? null : oEnv.clone();
	}

	@Override
	public DetectorProperties getDetector2DProperties() {
		return props;
	}

	@Override
	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
		return env;
	}

	@Override
	public DetectorProperties getOriginalDetector2DProperties() {
		return oProps;
	}

	@Override
	public DiffractionCrystalEnvironment getOriginalDiffractionCrystalEnvironment() {
		return oEnv;
	}

	@Override
	public IDiffractionMetadata clone() {
		DiffractionMetadata c = (DiffractionMetadata) super.clone();
		c.oEnv = oEnv;
		c.env = env.clone();
		c.oProps = oProps;
		c.props = props.clone();
		return c;
	}
}
