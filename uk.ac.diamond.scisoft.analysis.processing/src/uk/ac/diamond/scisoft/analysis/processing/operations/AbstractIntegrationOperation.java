/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IROI;

public abstract class AbstractIntegrationOperation<T extends IntegrationModel> extends AbstractOperation<T, OperationData> {

	@OperationModelField(hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
	private IROI region;


	@Override
	public void setModel(T model) {
		super.setModel(model);
		try {
			this.region = (IROI)model.get("region");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			region = null;
		}
	}
	
	protected IROI getRegion() {
		return region;
	}
	
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.ONE; // Addition for instance
	}
}
