package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class ReflectivityNormalisation {

	public static void reflectivityNormalisation1 (CurveStitchDataPackage csdp){
		////////////////////////////////////////
		IDataset a1 =csdp.getSplicedCurveY();
		IDataset a2 =csdp.getSplicedCurveYError();
		
		double a3 = normalisationFactor(csdp.getSplicedCurveY(), csdp.getSplicedGoodPointIDataset());
		
		IDataset splicedCurveY = Maths.multiply(a1, a3);
		IDataset splicedCurveYError = Maths.multiply(a2, a3);
		
		splicedCurveY.setErrors(splicedCurveYError);
		
		csdp.setSplicedCurveY(splicedCurveY);
		csdp.setSplicedCurveYError(splicedCurveYError);
		///////////////////////////////////////////////////
		IDataset a4 =csdp.getSplicedCurveYRaw();
		IDataset a5 =csdp.getSplicedCurveYRawError();
		
		double a6 = normalisationFactor(csdp.getSplicedCurveYRaw(), csdp.getSplicedGoodPointIDataset());
		
		IDataset splicedCurveYRaw = Maths.multiply(a4, a6);
		IDataset splicedCurveYRawError = Maths.multiply(a5, a6);
		
		splicedCurveYRaw.setErrors(splicedCurveYRawError);
		
		csdp.setSplicedCurveYRaw(splicedCurveYRaw);
		csdp.setSplicedCurveYRawError(splicedCurveYRawError);
		///////////////////////////////////////////////////
		IDataset a7 =csdp.getSplicedCurveYFhkl();
		IDataset a8 =csdp.getSplicedCurveYFhklError();
		
		double a9 = normalisationFactor(csdp.getSplicedCurveYFhkl(), csdp.getSplicedGoodPointIDataset());
		
		IDataset splicedCurveYFhkl = Maths.multiply(a7, a9);
		IDataset splicedCurveYFhklError = Maths.multiply(a8, a9);
		
		splicedCurveYFhkl.setErrors(splicedCurveYFhklError);
		
		csdp.setSplicedCurveYFhkl(splicedCurveYFhkl);
		csdp.setSplicedCurveYFhklError(splicedCurveYFhklError);
		
	}
	
	
	private static double normalisationFactor(IDataset in, IDataset goodPoints){
		
		double output =1;
		
		double temp=0;
		
		int noToAverage = 3;
		
		int counter =0;

		for(int i = 0; i<in.getSize(); i++){
			if(goodPoints.getBoolean(i)) {
				double j = in.getDouble(i);
				temp += 1/j;
				counter++;	
			}
			if(counter >= noToAverage) {
				break;
			}
			
		}
		
		output = temp/noToAverage;
		
		return output;
	}
	
	public static void reflectivityNormalisationToAPoint (CurveStitchDataPackage csdp,
															AxisEnums.yAxes y,
															double normalisationPoint){
		////////////////////////////////////////
		IDataset a1 =DatasetFactory.createFromObject(0);
		IDataset a2 =DatasetFactory.createFromObject(0);
		
		double a3 = 1/normalisationPoint;
		
		IDataset[] output = new IDataset[2];
		
		switch(y){
			case SPLICEDY:
				a1 =csdp.getSplicedCurveY();
				a2 =csdp.getSplicedCurveYError();
				output = multiplyYYe(a1, a2, a3);
				csdp.setSplicedCurveY(output[0]);
				csdp.setSplicedCurveYError(output[1]);
				break;
			case SPLICEDYFHKL:
				a1 =csdp.getSplicedCurveYFhkl();
				a2 =csdp.getSplicedCurveYFhklError();
				output = multiplyYYe(a1, a2, a3);
				csdp.setSplicedCurveYFhkl(output[0]);
				csdp.setSplicedCurveYFhklError(output[1]);
				break;
			case SPLICEDYRAW:
				a1 =csdp.getSplicedCurveYRaw();
				a2 =csdp.getSplicedCurveYRawError();
				output = multiplyYYe(a1, a2, a3);
				csdp.setSplicedCurveYRaw(output[0]);
				csdp.setSplicedCurveYRawError(output[1]);
				break;
			default:
				break;
		}
		
	}
	
	private static IDataset[] multiplyYYe(IDataset a1,
									   	  IDataset a2,
									   	  double a3){
		
		IDataset splicedCurveY = Maths.multiply(a1, a3);
		IDataset splicedCurveYError = Maths.multiply(a2, a3);
		
		splicedCurveY.setErrors(splicedCurveYError);
		
		
		return new IDataset[] {splicedCurveY, splicedCurveYError};
		
	}
									   			  
									   			  
									   			  
	
	
}
