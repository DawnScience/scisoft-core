/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Victor Rogalev

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.spectroscopy.RxesRemapMath;

/** Operation class to remap RXES data from "Incident Photon energy (Bragg) vs. Emission energy" to 
 * "Incident Photon energy (Bragg) vs. Loss (Transfer) energy"
 * 
 * @return Operation data
 */
public class RxesRemapOperation extends AbstractOperation<EmptyModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.RxesRemapOperation";
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		IDataset result = null;
		IDataset inputAxesX = null;
		IDataset inputAxesY = null;
		final Logger logger = LoggerFactory.getLogger(OperationData.class);
		ILazyDataset[] inputAxes = getFirstAxes(input);

		// X-axis[0](Bragg energies) must be greater than Y-axis[0](XES	energies) 
		try {
			if (inputAxes[0].getSlice().getDouble(0, 0) < inputAxes[1].getSlice().getDouble(0, 0)) {
				input =	input.getTransposedView().getSlice(); 
				inputAxes = getFirstAxes(input);
				logger.info("InputAxes[0] detected as Emission and InputAxes[1] as Bragg -> swapped and transposed!");
				}
		}
		catch (DatasetException e1) { 
			throw new OperationException(this, e1);
			}

		try {
			inputAxesX = inputAxes[0].getSlice();  // get rid of laziness already here
			inputAxesY = inputAxes[1].getSlice();  // get rid of laziness already here
			result = RxesRemapMath.RemapLinearInterp(input, inputAxesX, inputAxesY);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}

		return new OperationData(result);
	}

}