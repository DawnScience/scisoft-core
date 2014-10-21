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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

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
	
	public AbstractPixelIntegration2D(IDiffractionMetadata metadata, Integer nBins, Integer nBinsAzimuthal) {
		super(metadata);
		if (nBins != null) this.nbins = nBins;
		if (nBinsAzimuthal != null) this.nBinsChi = nBinsAzimuthal;
	}
	
	public void setNumberOfAzimuthalBins(int nBins) {
		if (nBins < 1) throw new IllegalArgumentException("Must be 1 or more");
		this.nBinsChi = nBins;
		binsChi = null;
	}
	
	@Override
	protected void processAndAddToResult(Dataset intensity, Dataset histo, List<Dataset> result,
			double[] range, String name) {
		super.processAndAddToResult(intensity, histo, result,range, name);
		
		Dataset axis = null;
		
		if (azimuthalRange == null) {
			axis = Maths.add(binsChi.getSlice(new int[]{1}, null ,null), binsChi.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(azimuthalRange[0], azimuthalRange[1], nBinsChi, Dataset.FLOAT64);
		}
		
		axis.setName("azimuth");
		result.add(axis);
	}
}
