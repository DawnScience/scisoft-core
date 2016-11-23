package org.dawnsci.surfacescatter.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.BoxSlicerRodScanUtilsForDialog;
import org.dawnsci.surfacescatter.ExampleModel;
import org.dawnsci.surfacescatter.TwoDFitting;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class TwoDFittingTest {

	public Dataset generateGaussian2DImage(Dataset input, 
			double a, 
			double x0, 
			double y0, 
			double sigmaX, 
			double sigmaY,
			double offsetX,
			double offsetY){
		
		Dataset output = DatasetFactory.ones(input);
		
		///running over x direction:
		for (int x =0; x< input.getShape()[0]; x++){
			///running over y direction:
			for (int y =0; y< input.getShape()[0]; y++){
				double xTerm = ((x + offsetX - x0)*(x + offsetX - x0))/(2*sigmaX*sigmaX);
				double yTerm = ((y + offsetY - y0)*(y + offsetY - y0))/(2*sigmaY*sigmaY);
				double z = a*Math.exp(-(xTerm + yTerm));
				output.set(z, x, y);
			}
		}
		
		return output;
	}
	
	
	public Dataset generateSimplePolynomialBackground(Dataset input, 
			int fitPower, 
			double offset){
			
			Dataset output = DatasetFactory.ones(input);
			
			///running over x direction:
			for (int x =0; x< input.getShape()[0]; x++){
				///running over y direction:
				for (int y =0; y< input.getShape()[0]; y++){
					///running over fitpower direction:
					double result = offset;
					if (fitPower !=0){
						for(int n = 0; n<=fitPower;n++){
							double p = Math.pow(x, n)*Math.pow(y, fitPower-n);
							result +=p;
						}
					}
					else{
					}
				output.set(result, y, x);
				}
			}
			
			return output;
		}
	
	
	@Test
	public void testTwoDFitting1() throws Exception {
		
		ExampleModel m = new ExampleModel();
		
		RectangularROI box= new RectangularROI();
		box.setLengths(10, 10);
		box.setPoint(40, 40);
		int[][] lenPt = new int[2][];
		lenPt[0] = new int[] {10,10};
		lenPt[1] = new int[] {45,45};
		m.setBox(box);
		m.setBoundaryBox(40);
		m.setFitPower(FitPower.TWO);
		m.setLenPt(lenPt);

		Dataset input1 = DatasetFactory.ones(new int [] {100,100});

		
		double a = 100;
		double x0 = 50;
		double y0 = 50;
		double sigmaX = 2;
		double sigmaY = 2;
		
		Dataset mainSignal = generateGaussian2DImage(input1, a, x0, y0, sigmaX, sigmaY, 0, 0);
		Dataset polynomialBackground = generateSimplePolynomialBackground(input1,1,0);
		
		Dataset testGeneratedImage = Maths.add(mainSignal,
				polynomialBackground);		
//		
//		for(int p=0;p<testGeneratedImage.getShape()[0];p++){
//			for(int q=0;q<testGeneratedImage.getShape()[1];q++){
//				double noisy = 0;
//				if(Math.random()>0.5){
//					double r = Math.random()*Math.sqrt(testGeneratedImage.getDouble(p, q));
//					noisy = testGeneratedImage.getDouble(p, q)+ r;
//					testGeneratedImage.set(noisy, p,q);
//					mainSignal.set(mainSignal.getDouble(p, q) +r ,p,q);
//				}
//				else{
//					double r = Math.random()*Math.sqrt(testGeneratedImage.getDouble(p, q));
//					noisy = testGeneratedImage.getDouble(p, q)- r;
//					testGeneratedImage.set(noisy, p,q);
//					mainSignal.set(mainSignal.getDouble(p, q) -r ,p,q);
//				}
//					
//			}
//		}
		
		Dataset benchmarkGeneratedImage = BoxSlicerRodScanUtilsForDialog.rOIBox(mainSignal,lenPt[0], lenPt[1]);
		
//		Dataset benchmarkGeneratedImage= generateGaussian2DImage(input2, a, x0, y0, sigmaX, sigmaY, 45, 45);
		
		double signalSum = (double) benchmarkGeneratedImage.sum();
		
		Dataset output = TwoDFitting.TwoDFitting1(testGeneratedImage, m);
		
		Dataset outputCheck = Maths.subtract(benchmarkGeneratedImage, output);
		
		double oc = ((double) outputCheck.sum());
		
		double check = oc/signalSum;
		
		System.out.println("check:  " + check);
		System.out.println("signalSum:  " + signalSum);
		System.out.println("outputCheck sum:  " + oc);
		System.out.println("output sum:  " + output.sum());


		
		assertTrue("check value exceds 0.5 resiual after 2D polynomial background subtraction"
				, Math.abs(check)<0.01);		
	}

}
