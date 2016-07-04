/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * A static class providing a method to regrid rectangular gridded two-dimensional data.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class XPDFRegrid {

	/**
	 * Regrids a two dimensional {@link Dataset} to a specified size.
	 * <p>
	 * The new grids match on the first and last element in each direction. The
	 * regridding algorithm uses bilinear interpolation. 
	 * @param input
	 * 				input data
	 * @param newX
	 * 			new length of the x axis
	 * @param newY
	 * 			new length of the y axis
	 * @return
	 * 		the gridded data, with shape {newX, newY}.
	 */
	public static Dataset two(Dataset input, int newX, int newY) {
		
		int oldX = input.getShape()[0], oldY = input.getShape()[1];
		
		Dataset newXInOldX = Maths.multiply(Maths.divide(DatasetFactory.createRange(DoubleDataset.class, newX), newX-1), oldX-1);
		Dataset newYInOldY = Maths.multiply(Maths.divide(DatasetFactory.createRange(DoubleDataset.class, newY), newY-1), oldY-1);

		Dataset output = DatasetFactory.zeros(DoubleDataset.class, newX, newY);
		
		for (int i = 0; i < newX-1; i++) {
			int xIndex = (int) newXInOldX.getDouble(i);
			double xF = newXInOldX.getDouble(i) - xIndex;

			for (int j = 0; j < newY-1; j++) {
				int yIndex = (int) newYInOldY.getDouble(j);
				double yF = newYInOldY.getDouble(j) - yIndex;
				
				output.set(
						(double) input.getObject(xIndex, yIndex) * (1-xF)*(1-yF) + 
						(double) input.getObject(xIndex+1, yIndex) * (xF)*(1-yF) +
						(double) input.getObject(xIndex, yIndex+1) * (1-xF)*(yF) +
						(double) input.getObject(xIndex+1, yIndex+1) * (xF)*(yF), i, j);
			}
			// Deal with the last row
			output.set(
					(double) input.getObject(xIndex, oldY-1) * (1-xF) + 
					(double) input.getObject(xIndex+1, oldY-1) * (xF), i, newY-1);
		}
		// Deal with the last column
		for (int j = 0; j < newY-1; j++) {
			int yIndex = (int) newYInOldY.getDouble(j);
			double yF = newYInOldY.getDouble(j) - yIndex;
			
			output.set(
					(double) input.getObject(oldX-1, yIndex) * (1-yF) + 
					(double) input.getObject(oldX-1, yIndex+1) * (yF), newX-1, j);
		}
		// Deal with the last element
		output.set((double) input.getObject(oldX-1, oldY-1), newX-1, newY-1);

		return output;
		
	}
	
}
