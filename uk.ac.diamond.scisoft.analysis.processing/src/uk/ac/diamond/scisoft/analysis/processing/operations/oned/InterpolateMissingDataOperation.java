/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

@Atomic
public class InterpolateMissingDataOperation extends AbstractOperation<InterpolateMissingDataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.InterpolateMissingDataOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		Dataset inputData = new DoubleDataset(DatasetUtils.convertToDataset(input));
		copyMetadata(input, inputData);
		Double mdi = model.getMdi();

		return new OperationData(interpolateMissingData(inputData, mdi));
	}
	
	public static Dataset interpolateMissingData(Dataset inputData, Double mdi) {
		BooleanDataset isMissing = Comparisons.logicalNot(Comparisons.isFinite(inputData));
		// Also add missing data values, if defined
		if (mdi != null) {
			isMissing = Comparisons.logicalOr(isMissing, Comparisons.equalTo(inputData, mdi));
		}
		// RLE the missing data blocks. Stored in a map of first element to length of block
		Map<Integer, Integer> missingDataBlocks = new TreeMap<Integer, Integer>();
		IndexIterator iter = isMissing.getIterator();
		while (iter.hasNext()) {
			if (isMissing.getAbs(iter.index)) {
				int missingBlockStart = iter.index;
				int missingBlockLength = 1;
				while(iter.hasNext()) {
					if (isMissing.getAbs(iter.index)) {
						missingBlockLength++;
					} else {
						missingDataBlocks.put(missingBlockStart, missingBlockLength);
						break;
					}
				}
			}
		}
		// Get the abscissae of the data, or build one
		ILazyDataset[] abscissae = getFirstAxes(inputData);
		Dataset abscissa = (abscissae[0] != null) ? DatasetUtils.sliceAndConvertLazyDataset(abscissae[0]) : DoubleDataset.createRange(inputData.getSize());
		
		
		// To interpolate the missing data, we can assume an ordered grid,
		// since missing data can be just left out of a collection of points
		for (Entry<Integer, Integer> entry : missingDataBlocks.entrySet()) {
			int nextFiniteIndex = entry.getKey()+entry.getValue();
			int lastFiniteIndex = entry.getKey()-1;
			// Deal with end points
			if (entry.getKey() == 0) {
				inputData.setSlice(inputData.getObjectAbs(nextFiniteIndex), new int[]{0}, new int[]{nextFiniteIndex}, new int[]{1});
			} else if (entry.getKey() + entry.getValue() == inputData.getSize()) {
				inputData.setSlice(inputData.getObjectAbs(lastFiniteIndex), new int[]{lastFiniteIndex+1}, new int[]{inputData.getSize()}, new int[]{1});
			} else {
				// Construct the parameters of the interpolation y(x) = a·x + b
				double a = (inputData.getElementDoubleAbs(nextFiniteIndex) - inputData.getElementDoubleAbs(lastFiniteIndex))/(abscissa.getElementLongAbs(nextFiniteIndex) - abscissa.getElementDoubleAbs(lastFiniteIndex));
				double b = inputData.getElementDoubleAbs(lastFiniteIndex) - a * abscissa.getElementDoubleAbs(lastFiniteIndex);
				
				for (int index = entry.getKey(); index < entry.getKey() + entry.getValue(); index++) {
					inputData.setObjectAbs(index, a*abscissa.getElementDoubleAbs(index)+b);
				}
			}
		}
		return inputData;
	}
}
