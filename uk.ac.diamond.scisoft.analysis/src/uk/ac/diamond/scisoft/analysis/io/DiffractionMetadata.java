/*-
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;

public class DiffractionMetadata extends ExtendedMetadata implements IDiffractionMetadata {
	private static final long serialVersionUID = IMetaData.serialVersionUID;

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
