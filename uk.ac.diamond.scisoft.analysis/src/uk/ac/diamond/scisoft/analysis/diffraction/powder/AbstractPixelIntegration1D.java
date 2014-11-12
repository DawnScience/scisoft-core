/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		
		Dataset error = intensity.getError();
		if (error != null) {
			error.idivide(histo);
			DatasetUtils.makeFinite(error);
		}
		
		if (isAzimuthalIntegration) {
			super.processAndAddToResult(intensity, histo, result, binRange, name);
			
		} else {
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
		
		result.get(1).setError(error);
		
	}
}
