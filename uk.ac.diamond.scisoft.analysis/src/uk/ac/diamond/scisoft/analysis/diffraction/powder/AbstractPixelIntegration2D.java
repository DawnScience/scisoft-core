/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

public abstract class AbstractPixelIntegration2D extends AbstractPixelIntegration {

	int nBinsChi;
	DoubleDataset binsChi;
	
	public AbstractPixelIntegration2D(IDiffractionMetadata metadata) {
		super(metadata);
		this.nBinsChi = nbins;
	}
	
	public AbstractPixelIntegration2D(IDiffractionMetadata metadata, int nBins, int nBinsAzimuthal) {
		super(metadata, nBins);
		this.nBinsChi = nBinsAzimuthal;
	}
}
