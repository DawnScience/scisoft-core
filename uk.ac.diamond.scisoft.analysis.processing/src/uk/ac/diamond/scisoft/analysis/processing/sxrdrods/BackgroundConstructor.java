/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.sxrdrods;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class BackgroundConstructor {
//
		
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

			for (int j = 0; j < len[1]; j++) {
				i2[j] = j;
			}

			


			Dataset[] strip = new Dataset[length];
			Dataset c = DatasetFactory.zeros(2 * boundaryBox);
			//Make axis for fit
			IDataset begin = DatasetFactory.createRange(IntegerDataset.class, boundaryBox);
			IDataset end = DatasetFactory.createRange(IntegerDataset.class, (double) (boundaryBox + length), (double) (2*boundaryBox + length), 1);
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
				
//				int[] tempshape = {length1 , 0};
//				
//				Dataset e = DatasetFactory.zeros(IntegerDataset.class, tempshape);
					
				IntegerDataset e = DatasetFactory.createLinearSpace(IntegerDataset.class, boundaryBox, length1 + boundaryBox, length1);

				strip[i] = (Dataset) polyFit.calculateValues(e);
					
				IndexIterator it1 = strip[i].getIterator();
				
				while (it1.hasNext()) {
					double q = strip[i].getElementDoubleAbs(it1.index);
					if (q < 0) strip[i].setObjectAbs(it1.index, 0);
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

}



