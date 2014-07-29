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

/**
 * This metadata describes any error associated with a dataset
 */
public interface ErrorMetadata extends MetadataType {

	/**
	 * Get error dataset
	 * @return error dataset
	 */
	public ILazyDataset getError();
}
