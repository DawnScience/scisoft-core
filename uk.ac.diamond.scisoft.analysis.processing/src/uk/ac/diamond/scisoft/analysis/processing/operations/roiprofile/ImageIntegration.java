/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

public class ImageIntegration extends BoxImageIntegrationCommon<ImageIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.imageIntegration";
	}

	@Override
	protected RectangularROI getRect(IDataset input) {
		return new RectangularROI((double) input.getShape()[1], (double) input.getShape()[0], 0.0);
	}

	@Override
	protected Dataset selectData(Dataset x, Dataset y) {
		Dataset selectedData = (model.getDirection() == Direction.X) ? x : y;
		
		if (model.isDoAverage()) {
			double scaling = (model.getDirection() == Direction.X) ? (double) y.getSize() : (double) x.getSize();
			selectedData.idivide(scaling);
		}
			
		return selectedData;
	}

}
