/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Image;

public class PseudoFlatFieldFilterOperation extends AbstractSimpleImageOperation<KernelWidthModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.PseudoFlatFieldFilterOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		int dtype = AbstractDataset.getDType(dataset);
		int radius = ((KernelWidthModel)model).getWidth();
		return Image.pseudoFlatFieldFilter(DatasetUtils.cast(dataset, dtype), radius);
	}
}
