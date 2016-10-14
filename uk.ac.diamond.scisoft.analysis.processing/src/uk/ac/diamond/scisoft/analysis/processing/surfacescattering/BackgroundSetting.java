/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.surfacescattering;


import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class BackgroundSetting{
	
	public static Dataset rOIBackground1(IDataset[] Background, Dataset in1Background, 
			int[] len, int[] pt,  int boundaryBox, int fitpower, Direction direction1){
		
		Integer direction = 1;
		int length = len[1];
		int length1 = len[0];
		
		
		switch (direction1){
		case X:
			length = len[1];
			length1 = len[0];
			direction = 1;
			break;
		case Y:
			length = len[0];
			length1 = len[1];
			direction = 0;
			break;
		}
		
		Dataset backGroundData = DatasetUtils.concatenate(Background, direction);
		
		int[] i1 = new int[2 * boundaryBox];
		int[] i2 = new int[length1];

		for (int j = 0; j < 2 * boundaryBox; j++) {
			i1[j] = j;
		}

		for (int j = 0; j < i2.length; j++) {
			i2[j] = j;
		}

		


		Dataset[] strip = new Dataset[length];
		Dataset c = DatasetFactory.zeros(new int[] { 2 * boundaryBox}, Dataset.FLOAT64);
		//Make axis for fit
		IDataset begin = DatasetFactory.createRange(boundaryBox, Dataset.INT32);
		IDataset end = DatasetFactory.createRange((double) (boundaryBox + length),(double) (2*boundaryBox + length), 1, Dataset.INT32);
		Dataset fullBack = DatasetUtils.concatenate(new IDataset[]{begin,end}, 0);
		
		
		
		for (int i = 0; i < length; i++) {
			
			for (int j = 0; j < 2 * boundaryBox; j++) {
				switch (direction1){
					case X:
					
						double test = backGroundData.getDouble(i,j);
						c.set(test, j);
						break;
					case Y:
						
						test = backGroundData.getDouble(j, i);
						c.set(test, j);
						break;
				}				
			}
			Dataset temp[] = {fullBack};
			
			Polynomial polyFit = Fitter.polyFit(temp, c, 1e-5,fitpower);
			//Dataset[] e = new Dataset[1];
			
			int[] tempshape = {length1 , 0};
			
			Dataset e = DatasetFactory.zeros(tempshape, Dataset.INT64);
					//(int[] {length1,0}, Dataset.INT);
			e = DatasetFactory.createLinearSpace(boundaryBox, length1 + boundaryBox, length1, Dataset.INT);

			strip[i] = (Dataset) polyFit.calculateValues(e);
				
			
			IndexIterator it = strip[i].getIterator();
			
			while (it.hasNext()) {
				double v = in1Background.getElementDoubleAbs(it.index);
				if (v < 0) in1Background.setObjectAbs(it.index, 0);
			}
						
			
			for (int k = 0; k < length1; k++) {
				switch (direction1){
				case X:
					in1Background.set(strip[i].getObject(k), i, k);
					break;
				case Y:
					in1Background.set(strip[i].getObject(k), k, i);
					break;
				}
			}
			//Test
		}
		
	
	return in1Background;
	}
//
}
