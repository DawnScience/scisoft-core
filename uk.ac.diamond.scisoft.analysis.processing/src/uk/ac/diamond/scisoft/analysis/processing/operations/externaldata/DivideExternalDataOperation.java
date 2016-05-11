/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

@Atomic
public class DivideExternalDataOperation extends OperateOnDataAbstractOperation<ExternalDataModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DivideExternalDataOperation";
	}

	@Override
	protected Dataset doMathematics(Dataset a, double b) {
		return ErrorPropagationUtils.divideWithUncertainty(a, b);
	}
	
	@Override
	protected String getFilePath(IDataset input) {
		return ((ExternalDataModel)model).getFilePath();
	}

}
