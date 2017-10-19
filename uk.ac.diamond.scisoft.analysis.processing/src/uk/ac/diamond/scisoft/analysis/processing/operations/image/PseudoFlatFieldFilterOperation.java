/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.dataset.impl.Image;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

public class PseudoFlatFieldFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.PseudoFlatFieldFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		int dtype = DTypeUtils.getDType(dataset);
		int radius = model.getWidth();
		return Image.pseudoFlatFieldFilter(DatasetUtils.cast(dataset, dtype), radius);
	}
}
