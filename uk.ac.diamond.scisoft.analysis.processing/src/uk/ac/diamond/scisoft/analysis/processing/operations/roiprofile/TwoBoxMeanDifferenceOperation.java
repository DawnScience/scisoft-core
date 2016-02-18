/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

@Atomic
public class TwoBoxMeanDifferenceOperation extends AbstractTwoBoxMeanOperation<TwoBoxModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.TwoBoxMeanDifferenceOperation";
	}

	@Override
	protected Dataset result(double mean1, double mean2) {
		double val = mean1 - mean2;
		Dataset ds = DatasetFactory.createFromObject(val);
		ds.setName("difference");
		return ds;
	}

}
