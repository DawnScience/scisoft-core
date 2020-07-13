/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PowderDataUtils {
	
	/**
	 * Setup the logging facilities
	 */

	public static Dataset convert1DDataset(Dataset axisData, XAxis inAxisType, XAxis outAxisType, double lambda) throws Exception {
		int[] shape = axisData.getShapeRef();
		int rank = shape.length;
		
		if (rank > 1) {
			throw new Exception("Cannot convert dataset with more than one dimension.");
		}
		
		Dataset result = DatasetFactory.zeros(shape);
		
		//TODO FIXME Change the names given to the axis labels to use the ones in the enum.
		if (outAxisType.equals(XAxis.ANGLE)) {
			for (int i = 0; i < axisData.getSize(); i++) {
				Double xAxisVal = axisData.getDouble(i);
				result.set(inAxisType.convertToANGLE(xAxisVal, lambda), i);
			}
			result.setName("2theta");
		} else if (outAxisType.equals(XAxis.Q)) {
			for (int i = 0; i < axisData.getSize(); i++) {
				Double xAxisVal = axisData.getDouble(i);
				result.set(inAxisType.convertToQ(xAxisVal, lambda), i);
			}
			result.setName("Q");
		} else if (outAxisType.equals(XAxis.RESOLUTION)) {
			for (int i = 0; i < axisData.getSize(); i++) {
				Double xAxisVal = axisData.getDouble(i);
				result.set(inAxisType.convertToRESOLUTION(xAxisVal, lambda), i);
			}
			result.setName("d-spacing");
		} else {
			throw new Exception("Unknown axis type conversion requested.");
		}
		
		return result;
	}
}
