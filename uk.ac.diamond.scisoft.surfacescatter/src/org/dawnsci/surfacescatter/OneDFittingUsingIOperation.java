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
	private int[] len;
	private int[] pt;
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
			
//												 SuperModel sm, 
//												 IDataset input, 
//												 ExampleModel model, 
//												 DataModel dm, 
//												 int k,
//												 int selection,
//												 AnalaysisMethodologies.Methodology am) {

		len = model.getLenPt()[0];
		pt = model.getLenPt()[1];

		
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
//			return (IDataset) in1Background;
			return null;
		}
//		
//		IndexIterator it = in1Background.getIterator();
//		
//		while (it.hasNext()) {
//			double v = in1Background.getElementDoubleAbs(it.index);
//			if (v < 0) {
//				in1Background.setObjectAbs(it.index, 0);
//			}
//		}

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);


		
//		IndexIterator it1 = pBackgroundSubtracted.getIterator();
//		
//		while (it1.hasNext()) {
//			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
//			if (q < 0){
//			           pBackgroundSubtracted.setObjectAbs(it1.index, 0);
//			}
//		}
//		
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

//		sm.addLocationList(selection, location);
//	
//		dm.addBackgroundDatArray(in1Background);
//		dm.addLocationList(model.getDatImages().getShape()[0], k, location);
		
//		return pBackgroundSubtracted;
//		return null;
		
		return new OperationData(output, location, in1Background);

	}

	



	
}
