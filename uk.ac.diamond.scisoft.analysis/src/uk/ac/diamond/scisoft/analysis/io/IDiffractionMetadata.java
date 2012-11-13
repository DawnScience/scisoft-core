/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;

/**
 * This interface is used to mark meta data which conforms to the diffraction meta
 * data specification. The following keys will exist in the IMetaData and must return
 * meaningful values.
 */
public interface IDiffractionMetadata extends IMetaData {

	public DetectorProperties getDetector2DProperties();
	
	public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment();
	
	public DetectorProperties getOriginalDetector2DProperties();

	public DiffractionCrystalEnvironment getOriginalDiffractionCrystalEnvironment();
	
	@Override
	public IDiffractionMetadata clone();
	
	static class Creator {
		/**
		 * Static method to obtain a DiffractionMetaDataAdapter populated with default values to
		 * act as a starting point for images without metadata
		 */
		public static IDiffractionMetadata getDiffractionMetadata(int[] shape) {
			
			final DetectorProperties detprop = DetectorProperties.getDefaultDetectorProperties(shape);
			final DiffractionCrystalEnvironment diffenv = DiffractionCrystalEnvironment.getDefaultDiffractionCrystalEnvironment();
			
			return new DiffractionMetaDataAdapter() {
				private static final long serialVersionUID = DiffractionMetaDataAdapter.serialVersionUID;

				@Override
				public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
					return diffenv;
				}

				@Override
				public DetectorProperties getDetector2DProperties() {
					return detprop;
				}

				@Override
				public DiffractionMetaDataAdapter clone() {
					return new DiffractionMetaDataAdapter() {
						private static final long serialVersionUID = DiffractionMetaDataAdapter.serialVersionUID;

						@Override
						public DiffractionCrystalEnvironment getDiffractionCrystalEnvironment() {
							return diffenv.clone();
						}

						@Override
						public DetectorProperties getDetector2DProperties() {
							return detprop.clone();
						}
					};
				}
			};
			
		}
	}
}
