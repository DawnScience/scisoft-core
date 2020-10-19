/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Benedikt Daurer

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;

@Atomic
public class TopBottomProfileSubtractionOperation extends AbstractOperation<TopBottomProfileSubtractionModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.TopBottomProfileSubtractionOperation";
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
	protected OperationData process(IDataset iDataset, IMonitor monitor) throws OperationException {

		// Read input image
		Dataset correctedData = DatasetUtils.convertToDataset(iDataset.getSlice());
		
		// window fraction to be used for defining average line profile
		double fraction = model.getWindowFraction();
		
		// center offset
		int offset = model.getCenterOffset();
		
		// Get the shape of the image
		int[] shape = correctedData.getShapeRef();
		int sy = shape[0];
		int hy = sy/2 + offset;
		
		// Calculate the window size
		int w = (int) (fraction * hy);
		
		// Do profile subtraction separately for each half
		if (w >0) {
			
			// Get top/bottom window
			Dataset top = correctedData.getSliceView(new Slice(w));
			Dataset bot = correctedData.getSliceView(new Slice(-w,null));
			
			// Calculate the average common line profile
			Dataset topProfile = top.mean(0,true);
			Dataset botProfile = bot.mean(0,true);
	
			// Subtract profile from top/bottom half
			Dataset topHalf = correctedData.getSliceView(new Slice(hy));
	        topHalf.isubtract(topProfile); 
			Dataset botHalf = correctedData.getSliceView(new Slice(hy,null));
	        botHalf.isubtract(botProfile); 
		}
		
		OperationData toReturn = new OperationData(correctedData);
		
		return toReturn;
	}

}