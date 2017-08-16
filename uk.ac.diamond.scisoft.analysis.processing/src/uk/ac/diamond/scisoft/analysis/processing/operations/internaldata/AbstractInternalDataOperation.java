/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;

import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.OperateOnDataAbstractOperation;

public abstract class AbstractInternalDataOperation extends OperateOnDataAbstractOperation<InternalDataModel> {

	@Override
	protected String getFilePath(IDataset input) {
		return input.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();
	}
}
