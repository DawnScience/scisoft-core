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

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

public abstract class AbstractPixelIntegration1D extends AbstractPixelIntegration {
	
	boolean isAzimuthalIntegration = true;
	
	public AbstractPixelIntegration1D(IDiffractionMetadata metadata) {
		super(metadata);
	}
	
	public AbstractPixelIntegration1D(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
	}
	
	public boolean isAzimuthalIntegration() {
		return isAzimuthalIntegration;
	}
	
	public void setAzimuthalIntegration(boolean isAzimuthalIntegration) {
		this.isAzimuthalIntegration = isAzimuthalIntegration;
	}
	
	@Override
	protected void processAndAddToResult(Dataset intensity, Dataset histo, List<Dataset> result,
			 double[] binRange, String name) {
		
		if (isAzimuthalIntegration) {
			super.processAndAddToResult(intensity, histo, result, binRange, name);
			return;
		} 
		
		Dataset axis = null;
		
		if (binRange == null) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(binRange[0], binRange[1], nbins, Dataset.FLOAT64);
		}
		
		axis.setName("azimuthal angle (degrees)");
		
		intensity.idivide(histo);
		DatasetUtils.makeFinite(intensity);
		
		intensity.setName(name + "_integrated");
		result.add(axis);
		result.add(intensity);
	}
}
