package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
//import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.swt.widgets.Composite;

public class SecondConstantROIUsingIOperation 
	extends AbstractOperation<SecondConstantROIBackgroundSubtractionModel, OperationData> {

		private static Dataset output;
		private DoubleDataset in1Background;
		
		@Override
		public String getId() {
			return "uk.ac.diamond.scisoft.surfacescatter.SecondConstantROIUsingIOperation";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.TWO ;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.TWO ;
		}
		
		
		public OperationData process (IDataset input, IMonitor monitor) 
				throws OperationException {
			
			input.squeeze();
			
			int[] len = model.getLenPt()[0];
			int[] pt = model.getLenPt()[1];
					
			Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);
	        	        
			Dataset backgroundDataset = BoxSlicerRodScanUtilsForDialog.rOIBox(input, 
					model.getBackgroundLenPt()[0], 
					model.getBackgroundLenPt()[1]);
	        
	        IndexIterator it0 = backgroundDataset.getIterator();
			
	        double bgSum = 0;
	        
	        while (it0.hasNext()) {
				bgSum += backgroundDataset.getElementDoubleAbs(it0.index);
	        }
	        
	        double bgAv = bgSum/(backgroundDataset.count()); 
	        
	        in1Background = DatasetFactory.zeros(in1.getShape());
	        
			Dataset pBackgroundSubtracted = Maths.subtract(in1, bgAv, null);
					
			IndexIterator it1 = pBackgroundSubtracted.getIterator();
			
			while (it1.hasNext()) {
				double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
				in1Background.setObjectAbs(it1.index, bgAv);

			}
				
			output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
				
			output.setName("Region of Interest, constant background removed");

			int[][] backgroundLenPt = model.getBackgroundLenPt();
			
			return new OperationData(output, 
									 null, 
									 (new RectangularROI(backgroundLenPt[1][0],
											 			  backgroundLenPt[1][1],
											 			  backgroundLenPt[0][0],
											 			  backgroundLenPt[0][1],
											 			  0)),
									 in1Background);
			
		}
	
}
