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

public class RefinedTwoDExponentialFittingUsingIOperation extends AbstractOperation<TwoDFittingModel, OperationData> {

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
		
		/////////////Exponential Fitting
		
		Dataset matrixExp = LinearLeastSquaresServicesForDialog.exponential2DLinearLeastSquaresMatrixGenerator(
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
		
		DoubleDataset testExp = (DoubleDataset)LinearAlgebra.solveSVD(matrixExp, loggedZ);
		double[] paramsExp = testExp.getData();
		
		///////////////////////////
		
//		///////////////Linear Fit

		if (Arrays.equals(fittingBackground[0].getShape(), (new int[] { 2, 2 }))) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			return new OperationData(fittingBackground[0], location);
		}

		Dataset matrixPoly = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
							1, fittingBackground[0], fittingBackground[1]);

		DoubleDataset testPoly = (DoubleDataset) LinearAlgebra.solveSVD(matrixPoly, fittingBackground[2]);
		double[] paramsPoly = testPoly.getData();
		
		///////////////////////////
		
//		///////////////Mixed Fit	
		
		Dataset matrixRef = LinearLeastSquaresServicesForDialog.
				refinedExponential2DLinearLeastSquaresMatrixGenerator(
				fittingBackground[0], 
				fittingBackground[1],
				fittingBackground[2],
				paramsPoly,
				paramsExp);
		
		DoubleDataset testRef = (DoubleDataset) LinearAlgebra.solveSVD(matrixRef, fittingBackground[2]);
		double[] paramsRef = testRef.getData();
		
		IDataset in1Background = getRefinedExponentialOutputValues(paramsPoly,
																   paramsExp,
																   paramsRef,
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

		output.setName("Region of Interest, 2D Exponential background removed");
			
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
	
	public IDataset getRefinedExponentialOutputValues (double[] paramsPoly,
													   double[] paramsExp,
													   double[] paramsRef,
													   int boundaryBox,
													   int len0, 
													   int len1) {

		double[] a = paramsExp;
		double[] b = paramsPoly;
		double[] c = paramsRef;
		
		IDataset output = DatasetFactory.zeros(new int[] {len1, 
								   len0});//new DoubleDataset(len[1], len[0]);
		
		for (int i=boundaryBox; i<len1+boundaryBox; i++){
			for (int j=boundaryBox; j<len0+boundaryBox; j++){
				
				double valueExp = -Math.exp(a[0]) + Math.exp(a[1])*Math.exp(a[2]*i + a[3]*j);
				
				if(Double.isNaN(valueExp)){
					valueExp = 0;
				}
				else if(Double.isInfinite(valueExp)){
					valueExp = -1000000000;
				}
				
				double valuePoly = b[0] 
						+ b[1]*i + b[2]*j + b[3]*i*j;
				
				double valueRef = c[0]*valuePoly + c[1]*valueExp;  
				
				output.set(valueRef, i-boundaryBox, j-boundaryBox);
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
