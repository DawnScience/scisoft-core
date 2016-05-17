/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.sxrdrods;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class BoxSlicerRodScans {

	public static Dataset rOIBox(IDataset input , IMonitor monitor, int[] len, int[] pt) throws OperationException{ 
	//// This creates in1, which is the ROI of interest
		SliceND slice1 = new SliceND(input.getShape());
		slice1.setSlice(1, pt[0], pt[0] + len[0], 1);
		slice1.setSlice(0, pt[1], pt[1] + len[1], 1);
		IDataset small1 = input.getSlice(slice1);
		Dataset in1 = DatasetUtils.convertToDataset(small1);
		return in1;
	}
	//
	public static IDataset iAboveOrLeftBox (IDataset input , IMonitor monitor, 
			int[] len, int[] pt, int boundaryBox, Direction direction) throws OperationException{
		   
		switch (direction){
		   	case Y:
		   		SliceND slice2 = new SliceND(input.getShape());
				slice2.setSlice(1, pt[0], pt[0] + len[0], 1);
				slice2.setSlice(0, pt[1] + len[1], pt[1] + len[1] + boundaryBox, 1);
				IDataset small2 = input.getSlice(slice2);
				return small2;
		   	case X:
		   		SliceND slice4 = new SliceND(input.getShape());
				slice4.setSlice(1, pt[0] + len[0], pt[0]+ len[0] + boundaryBox, 1);
				slice4.setSlice(0, pt[1], pt[1] + len[1], 1);
				IDataset small4 = input.getSlice(slice4);
				return small4;
		   }
		   return null;
	}
	
	public static IDataset iBelowOrRightBox (IDataset input , IMonitor monitor, 
			int[] len, int[] pt, int boundaryBox, Direction direction) throws OperationException{
		   switch (direction){
		   	case Y:
		   		SliceND slice0 = new SliceND(input.getShape());
				slice0.setSlice(1, pt[0], pt[0] + len[0], 1);
				slice0.setSlice(0, pt[1] - boundaryBox, pt[1], 1);
				IDataset small0 = input.getSlice(slice0);
				return small0;
		   	case X:
		   		SliceND slice3 = new SliceND(input.getShape());
				slice3.setSlice(1, pt[0] - boundaryBox, pt[0], 1);
				slice3.setSlice(0, pt[1], pt[1] +len[1], 1);
				IDataset small3 = input.getSlice(slice3);
				return small3;
		   }
		   return null;
	}	
	
	
}
