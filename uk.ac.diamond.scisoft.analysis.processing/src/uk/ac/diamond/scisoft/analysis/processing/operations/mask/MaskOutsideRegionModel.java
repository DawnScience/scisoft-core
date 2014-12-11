/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.IntegrationModel;

public class MaskOutsideRegionModel extends IntegrationModel {

	public MaskOutsideRegionModel() {
		region = new RectangularROI();
	}
	
}
