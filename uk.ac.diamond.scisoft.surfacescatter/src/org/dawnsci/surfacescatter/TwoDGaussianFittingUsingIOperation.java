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
import org.eclipse.january.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.NDGaussianFitResult;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;

public class TwoDGaussianFittingUsingIOperation extends AbstractOperation<TwoDFittingModel, OperationData> {

	private static Dataset output;
	private static int DEBUG = 0;
	private IDataset in1Background;
	private NDGaussianFitResult result;
	private static Dataset Av;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.surfacescatter.TwoDGaussianFittingUsingIOperation";
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

		Dataset[] fittingBackground = BoxSlicerRodScanUtilsForDialog.LeftRightTopBottomBoxesForGaussian(input, len, pt,
				model.getBoundaryBox());

		if (Arrays.equals(fittingBackground[0].getShape(), (new int[] { 2, 2 }))) {
			IDataset location = DatasetFactory.ones(new int[] {2,2});
			return new OperationData(fittingBackground[0], location);
		}
		
		try{
		
							result = NDGaussianSimpleFit2D(fittingBackground[2],
																fittingBackground[0],
																fittingBackground[1]);

		}
		catch(Exception y){
			System.out.println(y.getMessage());
		}
		
		
		Gaussian backgroundX = new Gaussian(result.getPos()[0],
											result.getFwhm()[0],
											result.getArea()[0]);
		
		Gaussian backgroundY = new Gaussian(result.getPos()[1],
											result.getFwhm()[1],
											result.getArea()[1]);
		
		in1Background = getGaussianOutputValues(model.getLenPt()[0],
												model.getBoundaryBox(),
												backgroundX,
												backgroundY);

		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);

		output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);

		output.setName("Region of Interest, 2D Gaussian background removed");
			
		return new OperationData(output, (IDataset) in1Background);
	}
	

	public IDataset getGaussianOutputValues (int[] len, 
											 int boundaryBox, 
											 Gaussian gX,
											 Gaussian gY) {
		
		IDataset output1 = DatasetFactory.zeros(new int[] {len[1], len[0]});//new DoubleDataset(len[1], len[0]);
		
		for (int k=boundaryBox; k<boundaryBox+len[1]; k++){
			for (int l=boundaryBox; l<boundaryBox+len[0]; l++){
				
				double x = k;
				double y = l;
			
				DoubleDataset tempXCalc = gY.calculateValues(DatasetFactory.createFromObject(x));
				DoubleDataset tempYCalc = gX.calculateValues(DatasetFactory.createFromObject(y));
				
				double temp = tempXCalc.get() * tempYCalc.get();
				
				output1.set(temp, k-boundaryBox, l-boundaryBox);
			}
		}
	
		return output1;
	}
	
	
	public static NDGaussianFitResult NDGaussianSimpleFit2D(Dataset twoDData, 
															Dataset xAxis,
															Dataset yAxis) {
		

		Dataset[] axis = new Dataset[] {xAxis, yAxis};
		
		// first resolve the problem into n 1D problems
		AFunction[] results = new AFunction[2];
		
		for (int i = 0; i < 2; i++) {
			
			for (int j = 0; j < 1; j++) {
				if (j < i) {
					Av = DatasetFactory.zeros(yAxis);
					for (int a =0; a<xAxis.getSize(); a++){
						double temp=0;
						double l=0;
						for (int b =0; b<xAxis.getSize(); b++){
							if(Double.isNaN(twoDData.getDouble(a, b)) == false){
								temp += twoDData.getDouble(a, b);
								l+=1;
							}
							
						}
						Av.set((temp/l), a);
					}
					
				} else {
					Av = DatasetFactory.zeros(yAxis);
					for (int a =0; a<yAxis.getSize(); a++){
						double temp=0;
						double l=0;
						for (int b =0; b<xAxis.getSize(); b++){
							if(Double.isNaN(twoDData.getDouble(a, b)) == false){
								double r = twoDData.getDouble(a, b);
								temp += twoDData.getDouble(a, b);
								l+=1;
							}
							
						}
						Av.set((temp/l), a);
					}
				}
			}
			try{
				results[i] = Fitter.GaussianFit(Av, axis[i]);
				
				
			}
			catch(Exception o ){
				System.out.println(o.getMessage());
			}
		}
		
		return new NDGaussianFitResult(results);
	}

	
	private void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}
