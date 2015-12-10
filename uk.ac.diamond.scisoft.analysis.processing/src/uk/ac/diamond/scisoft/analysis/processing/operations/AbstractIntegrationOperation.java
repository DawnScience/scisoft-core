/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;

public abstract class AbstractIntegrationOperation<T extends IntegrationModel> extends AbstractOperation<T, OperationData> {
	
	protected IROI getRegion() {
		return model.getRegion();
	}
	@Override
	public void setModel(T model) {
		super.setModel(model);
	}

	
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.ONE; // Addition for instance
	}
}
