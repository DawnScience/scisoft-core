package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	private NDGaussianFitResult[] result;
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
		
		
		result = new NDGaussianFitResult[2];
		
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
		

		
		long startTime = System.currentTimeMillis();


		for(int i = 0; i<2; i++){
			try{
				result[i] = NDGaussianSimpleFit2DArbSlices(fittingBackground[2], i);
			}
			catch(Exception y){
				System.out.println(y.getMessage());
			}
		}
		
		

	

		long endTime = System.currentTimeMillis();
		

		System.out.println("get arb slices result took:  " + (endTime - startTime) + " milliseconds");
		
		
		
		startTime = System.currentTimeMillis();

		in1Background = getGaussianOutputValuesArbSlices(model.getLenPt()[0],
												model.getBoundaryBox());


		endTime = System.currentTimeMillis();
		

		System.out.println("get arb background took:  " + (endTime - startTime) + " milliseconds");
		
//		in1Background = getGaussianOutputValues(model.getLenPt()[0],
//												model.getBoundaryBox(),
//												backgroundX,		 
//												backgroundY);

		
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
				
				double temp = (tempXCalc.get() + tempYCalc.get())/2;
				
				output1.set(temp, k-boundaryBox, l-boundaryBox);
			}
		}
	
		return output1;
	}
	
	public IDataset getGaussianOutputValuesArbSlices (int[] len, 
													  int boundaryBox) {

		IDataset output1 = DatasetFactory.zeros(new int[] {len[1], len[0]});//new DoubleDataset(len[1], len[0]);

		for (int k=boundaryBox; k<boundaryBox+len[1]; k++){
			for (int l=boundaryBox; l<boundaryBox+len[0]; l++){
				
				double x = k;
				double y = l;

				Gaussian backgroundX = new Gaussian(result[0].getPos()[k],
						result[0].getFwhm()[k],
						result[0].getArea()[k]);

				Gaussian backgroundY = new Gaussian(result[1].getPos()[l],
						result[1].getFwhm()[l],
						result[1].getArea()[l]);
				
				DoubleDataset tempXCalc = backgroundY.calculateValues(DatasetFactory.createFromObject(x));
				DoubleDataset tempYCalc = backgroundX.calculateValues(DatasetFactory.createFromObject(y));

				double temp = (tempXCalc.get() + tempYCalc.get())/2;

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
					Av = DatasetFactory.zeros(xAxis);
					for (int a =0; a<xAxis.getSize(); a++){
						double temp=0;
						double l=0;
						for (int b =0; b<yAxis.getSize(); b++){
							if(Double.isNaN(twoDData.getDouble(b, a)) == false){
								temp += twoDData.getDouble(b, a);
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
//				Fitter.NDGaussianSimpleFit(data, axis)
				
			}
			catch(Exception o ){
				System.out.println(o.getMessage());
			}
		}
		
		return new NDGaussianFitResult(results);
	}

	
	
	
	public static NDGaussianFitResult NDGaussianSimpleFit2DArbSlices(Dataset twoDData, 
																	 int axisNo) {
		
		int otherAxis = 1- axisNo;

		// first resolve the problem into n 1D problems
		AFunction[] results = new AFunction[twoDData.getShape()[axisNo]];
		int [] coords = new int[2];
		
		for (int a =0; a< twoDData.getShape()[axisNo]; a++){

			List<Double> zs = new ArrayList<Double>();
			List<Integer> zAxis = new ArrayList<Integer>();


			for (int b =0; b<twoDData.getShape()[otherAxis]; b++){

				///get the correct position
				
				coords[otherAxis] = b;
				coords[axisNo] = a;

				if(Double.isNaN(twoDData.getDouble(coords[0], coords[1])) == false){

					zs.add(twoDData.getDouble(coords[0], coords[1]));
					zAxis.add(b);
				}

			}

			Dataset zsDataset = DatasetFactory.createFromObject(zs);
			Dataset zAxisDataset = DatasetFactory.createFromObject(zAxis);

			try{
				results[a] = Fitter.GaussianFit(zsDataset, zAxisDataset);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}


		NDGaussianFitResult result = new NDGaussianFitResult(results);


		return result;
	}

	
	
	
	private void debug(String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}
}
