/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

public class OneDFittingUsingIOperation extends AbstractOperation<OneDFittingModel, OperationData> {
	
	private static Dataset output;
	private Dataset in1Background;


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.surfacescatter.OneDFittingUsingIOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}
	
	public OperationData process (IDataset input,
								  IMonitor monitor){

		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];

		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);
		
		IDataset[] background = new IDataset[2];
				
		background[0] = BoxSlicerRodScanUtilsForDialog.iBelowOrRightBox(input, 
																		len, 
																		pt,
																		model.getBoundaryBox(), 
																		model.getDirection());
		
		background[1] = BoxSlicerRodScanUtilsForDialog.iAboveOrLeftBox(input, 
																	   len, 
																	   pt,
																	   model.getBoundaryBox(), 
																	   model.getDirection());
		
		
		in1Background = DatasetFactory.zeros(in1.getShape(), Dataset.FLOAT64);
		
		in1Background = BackgroundSetting.rOIBackground1(background, 
														 in1Background,
														 len,
														 pt, 
														 model.getBoundaryBox(), 
														 AnalaysisMethodologies.toInt(model.getFitPower()), 
														 model.getDirection());
		
		
		if(Arrays.equals(in1Background.getShape(),(new int[] {2,2}))){

			return null;
		}

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
		
		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
		
		output.setName("Region of Interest, polynomial background removed");
		
		
		double[] location = new double[] { (double) model.getInitialLenPt()[1][1], 
				   							(double) model.getInitialLenPt()[1][0], 
				   							(double) (model.getInitialLenPt()[1][1] + model.getInitialLenPt()[0][1]), 
				   							(double) (model.getInitialLenPt()[1][0]),
				   							(double) model.getInitialLenPt()[1][1], 
				   							(double) model.getInitialLenPt()[1][0] + model.getInitialLenPt()[0][0], 
				   							(double) (model.getInitialLenPt()[1][1] + model.getInitialLenPt()[0][1]), 
				   							(double) (model.getInitialLenPt()[1][0] + model.getInitialLenPt()[0][0]) };


		
		return new OperationData(output, location, in1Background);

	}

	



	
}
