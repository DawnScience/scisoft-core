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
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
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
	
	public void setNumberOfAzimuthalBins(int nBins) {
		if (nBins < 1) throw new IllegalArgumentException("Must be 1 or more");
		this.nBinsChi = nBins;
		binsChi = null;
	}
	
	@Override
	protected void processAndAddToResult(AbstractDataset intensity, AbstractDataset histo, List<AbstractDataset> result,
			double[] range, String name) {
		super.processAndAddToResult(intensity, histo, result,range, name);
		
		AbstractDataset axis = null;
		
		if (azimuthalRange == null) {
			axis = Maths.add(binsChi.getSlice(new int[]{1}, null ,null), binsChi.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(azimuthalRange[0], azimuthalRange[1], nbins, AbstractDataset.FLOAT64);
		}
		
		axis.setName("chi");
		result.add(axis);
	}
}
