/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;

/**
 * Defines the origin of a dataset which is a slice of a parent dataset
 */
public interface OriginMetadata extends DimensionMetadata {

	public ILazyDataset getParent();
	
	public Slice[] getSlice();
	
}
