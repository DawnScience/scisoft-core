/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.january.dataset.Maths;

public class MinimumFramesOperation extends AbstractFramesOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.MinimumFramesOperation";
	}

	// do not use the method reference -> see https://jira.diamond.ac.uk/browse/SCI-6339
	public MinimumFramesOperation() {
		super((oldDataset, newDataset) -> Maths.minimum(oldDataset, newDataset));
	}
}
