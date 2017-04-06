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
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

public class TwoDExponentialFittingUsingIOperation extends AbstractOperation<TwoDFittingModel, OperationData> {

	private static Dataset output;
	private static int DEBUG = 0;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.surfacescatter.TwoDExponentialFittingUsingIOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) {

		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];
		
		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input, len, pt);

		if (Arrays.equals(in1.getShape(), new int[] { len[1], len[0] }) == false) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			Dataset errorDat = DatasetFactory.zeros(new int[] { 2, 2 });

			return new OperationData(errorDat, location);
		}

		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxes(input, len, pt,
				model.getBoundaryBox());

		if (Arrays.equals(fittingBackground[0].getShape(), (new int[] { 2, 2 }))) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			return new OperationData(fittingBackground[0], location);
		}
		
		Dataset matrix = LinearLeastSquaresServicesForDialog.exponential2DLinearLeastSquaresMatrixGenerator(
									fittingBackground[0], 
									fittingBackground[1],
									fittingBackground[2]);
		
		Dataset loggedZ = Maths.log(fittingBackground[2]);
		
		for(int u = 0; u<fittingBackground[2].getSize(); u++){
			if(Double.isInfinite(loggedZ.getDouble(u))){
				loggedZ.set(-1000000, u);
			}
			if(Double.isNaN(loggedZ.getDouble(u))){
				loggedZ.set(0, u);
			}
		}
		
		
		DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, loggedZ);
		double[] params = test.getData();
		
		IDataset in1Background = getExponentialOutputValues(params, 
													model.getBoundaryBox(), 
													len[0], 
													len[1]);

		Dataset pBackgroundSubtracted = DatasetFactory.createFromObject(in1);
		
		try{
			pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
		}
		catch(Exception b){
			System.out.println(b.getMessage());
		}
		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);

		output.setName("Region of Interest, 2D Gaussian background removed");
			
		return new OperationData(output, (IDataset) in1Background);
	}
	

	public IDataset getExponentialOutputValues (double[] a, 
											 int boundaryBox,
											 int len0, 
											 int len1) {
		
		IDataset output = DatasetFactory.zeros(new int[] {len1, 
														   len0});//new DoubleDataset(len[1], len[0]);
		
		for (int i=boundaryBox; i<len1+boundaryBox; i++){
			for (int j=boundaryBox; j<len0+boundaryBox; j++){
				
				double value = -Math.exp(a[0]) + Math.exp(a[1])*Math.exp(a[2]* i + a[3]* j);
				
				output.set(value, i-boundaryBox, j-boundaryBox);
			}
		}
	
		

		for(int u = 0; u<output.getShape()[0]; u++){
			for(int v = 0; v<output.getShape()[1]; v++){
				if(Double.isInfinite(output.getDouble(u, v))){
					output.set(-1000000, u, v);
				}
				if(Double.isNaN(output.getDouble(u, v))){
					output.set(0, u, v);
				}
			}
		}
		
		
		return output;
	}
	
	
	
	private void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}
