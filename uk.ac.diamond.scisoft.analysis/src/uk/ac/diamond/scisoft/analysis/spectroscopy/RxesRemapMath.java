/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Victor Rogalev

package uk.ac.diamond.scisoft.analysis.spectroscopy;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;


public class RxesRemapMath {
	/** Method to remap RXES data from "Incident Photon energy (Bragg) vs. Emission energy" to 
	 * "Incident Photon energy (Bragg) vs. Loss (Transfer) energy"
	 * 
	 * @return Remapped data as DoubleDataset with Metadata
	 */
	public static IDataset RemapLinearInterp(IDataset input, IDataset inputAxesX, IDataset inputAxesY) throws Exception {
		
		final Logger logger = LoggerFactory.getLogger(RxesRemapMath.class);
	
		// check direction where Bragg energy is constant => normally it is [1] so interpolation direction is also [1]
		// if it is not the case -> transpose axes and data but don't permute!
		try {
			if (Math.abs(inputAxesX.getDouble(0, 0)-inputAxesX.getDouble(0, -1))>Math.abs(inputAxesX.getDouble(0, 0)-inputAxesX.getDouble(-1, 0))){
				logger.info("Interpolation direction is not standard!");
				inputAxesX = inputAxesX.getTransposedView();
				inputAxesY = inputAxesY.getTransposedView();
				input =	input.getTransposedView().getSlice();
			}
		} catch (Exception e2) {
			throw new Exception("Failed to transpose axes or data", e2);
		}
		
		final double newDelta;
		final int newSize;
		final double minY;
		final double maxY;		
		// At this point we know for sure that InputAxesX is Bragg, InputAxesY is Emission and interpolation direction is [1]
		DoubleDataset eLoss = DatasetUtils.copy(DoubleDataset.class, inputAxesX);
		try {
			eLoss = eLoss.isubtract(inputAxesY);
			minY = eLoss.min().doubleValue();
			maxY = eLoss.max().doubleValue();
			newDelta = Math.max(
					Math.abs(inputAxesY.getDouble(0,1)-inputAxesY.getDouble(0,0)),
					Math.abs(inputAxesY.getDouble(1,0)-inputAxesY.getDouble(0,0))); // delta same as in Emission axis
			newSize = (int) ((maxY-minY)/newDelta+1);
		} catch (Exception e) {
			throw new Exception("Error while preparation phase for interpolation", e);
		}

		// make new axes + result
		DoubleDataset result = null;
		result = DatasetFactory.zeros(new int[] {inputAxesX.getShape()[0], newSize}); // resulting dataset with intensities - 2D
		DoubleDataset newAxisLossEnergy = DatasetFactory.createLinearSpace(DoubleDataset.class, minY, maxY, newSize); // Convert to loss energy
		
		// make interpolation
		Dataset rv;
		for (int i = 0 ; i < inputAxesX.getShape()[0] ; i++) {
			IDataset oldLossIntensity = input.getSliceView(new int[] {i,0}, new int[] {i+1,inputAxesX.getShape()[1]}, null).squeeze().getSlice();
			rv = Interpolation1D.splineInterpolation(
					eLoss.getSlice(new Slice(i,i+1),new Slice(0,eLoss.getShape()[1])).flatten(),
					oldLossIntensity,
					newAxisLossEnergy);
			result.setSlice(rv, new int[] {i,0}, new int[] {i+1,newSize}, null);
		};
		
		// Set axes and flip along interpolation direction to match literature standards
		result = (DoubleDataset) result.getSliceView(null, null, new int[] {1,-1});
		IDataset newAxisX = inputAxesX.getSliceView(null, null, new int[] {1,-1});
		newAxisX.setName("Photon energy (eV)");
		IDataset newAxisY = newAxisLossEnergy.getSliceView(null, null, new int[] {-1});
		newAxisY.setName("Energy loss (eV)");
		UnitMetadata unit = null;
		unit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.ELECTRON_VOLT);
		newAxisX.setMetadata(unit);
		newAxisY.setMetadata(unit);

		
		AxesMetadata axm;
		axm = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		axm.setAxis(0, newAxisX);
		axm.setAxis(1, newAxisY);
		result.setMetadata(axm);
		
		return result.getTransposedView();
	}
}
