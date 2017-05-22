package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class CurveStitchWithErrorsAndFrames {

	private static double attenuationFactor;
	private static double attenuationFactorFhkl;
	private static double attenuationFactorRaw;
	private static int DEBUG =0;

	
	
	public static IDataset[] curveStitch4 (CurveStitchDataPackage csdp){


		ArrayList<OverlapDataModel> overlapDataModels = new ArrayList<>();	
		
		IDataset[] xArray= csdp.getxIDataset();
		IDataset[] yArray= csdp.getyIDataset();
		IDataset[] yArrayError= csdp.getyIDatasetError();
		IDataset[] yArrayFhkl= csdp.getyIDatasetFhkl();
		IDataset[] yArrayFhklError= csdp.getyIDatasetFhklError();
		IDataset[] yArrayRaw = csdp.getyRawIDataset();
		IDataset[] yArrayRawError = csdp.getyRawIDatasetError();
		
		IDataset[] xArrayCorrected = xArray.clone();
		
		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();
		IDataset[] yArrayCorrectedRaw= yArrayRaw.clone();
		
		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();
		IDataset[] yRawErrorArrayCorrected= yArrayRawError.clone();
		
		IDataset[][] attenuatedDatasets = new IDataset[2][];
		
		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];
		
		IDataset[][] attenuatedDatasetsRaw = new IDataset[2][];
		
		int d = xArray.length;
		
		double[][] maxMinArray = new double[d][2];
		
		for(int k =0;k<d;k++){
			maxMinArray[k][0] = (double) xArray[k].max(null);
			maxMinArray[k][1] = (double) xArray[k].min(null);
		}
		
		attenuationFactor =1;
		attenuationFactorFhkl =1;
		attenuationFactorRaw =1;
		
		for (int k=0; k<xArray.length-1;k++){
		
		OverlapDataModel odm = new OverlapDataModel();
		
		odm.setLowerDatName(csdp.getFilepaths()[k]);
		odm.setUpperDatName(csdp.getFilepaths()[k+1]);
		
		ArrayList<Integer> overlapLower = new ArrayList<Integer>();
		ArrayList<Integer> overlapHigher = new ArrayList<Integer>();
		
		for(int l=0; l<xArrayCorrected[k].getSize();l++){
			if (xArrayCorrected[k].getDouble(l)>=(maxMinArray[k+1][1] - 0.001*maxMinArray[k+1][1])){
				overlapLower.add(l);
			}
		}
		
		for(int m=0; m<xArrayCorrected[k+1].getSize();m++){
			if (xArrayCorrected[k+1].getDouble(m)<=(maxMinArray[k][0] + 0.001 * maxMinArray[k][0])){
				overlapHigher.add(m);
			}
		}
		
		int[] lowerOverlapPositions = new int[overlapLower.size()];
		
		for(int o = 0;o< overlapLower.size();o++){
		lowerOverlapPositions[o] = overlapLower.get(o); 
		}
		
		int[] higherOverlapPositions = new int[overlapHigher.size()];
		
		for(int o = 0;o< overlapHigher.size();o++){
		higherOverlapPositions[o] = overlapHigher.get(o); 
		}
		
		odm.setLowerOverlapPositions(lowerOverlapPositions);
		odm.setUpperOverlapPositions(higherOverlapPositions);
		
		Dataset[] xLowerDataset =new Dataset[1];
		Dataset yLowerDataset =null;
		Dataset yLowerDatasetFhkl =null;
		Dataset yLowerDatasetRaw =null;
		Dataset[] xHigherDataset =new Dataset[1];
		Dataset yHigherDataset =null;
		Dataset yHigherDatasetFhkl =null;
		Dataset yHigherDatasetRaw =null;
		
		ArrayList<Double> xLowerList =new ArrayList<>();
		ArrayList<Double> yLowerList =new ArrayList<>();
		ArrayList<Double> yLowerListFhkl =new ArrayList<>();
		ArrayList<Double> yLowerListRaw =new ArrayList<>();
		ArrayList<Double> xHigherList =new ArrayList<>();
		ArrayList<Double> yHigherList =new ArrayList<>();
		ArrayList<Double> yHigherListFhkl =new ArrayList<>();
		ArrayList<Double> yHigherListRaw =new ArrayList<>();
		
		if (overlapLower.size() > 0 && overlapHigher.size() > 0){
		
			for (int l=0; l<overlapLower.size(); l++){
				xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
				yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
				yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));
				yLowerListRaw.add(yArrayRaw[k].getDouble(overlapLower.get(l)));
				
				xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
				yLowerDataset = DatasetFactory.createFromObject(yLowerList);
				yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
				yLowerDatasetRaw = DatasetFactory.createFromObject(yLowerListRaw);
			}
			
			double[] lowerOverlapCorrectedValues = new double[overlapLower.size()];
			double[] lowerOverlapRawValues = new double[overlapLower.size()];
			double[] lowerOverlapFhklValues = new double[overlapLower.size()];
			double[] lowerOverlapScannedValues = new double[overlapLower.size()];
			
			
			for(int o = 0;o< overlapLower.size();o++){
				lowerOverlapScannedValues[o] = xLowerList.get(o);
				lowerOverlapCorrectedValues[o] = yLowerList.get(o);
				lowerOverlapRawValues[o] = yLowerListRaw.get(o);;
				lowerOverlapFhklValues[o] = yLowerListFhkl.get(o);	
			}
			
			odm.setLowerOverlapCorrectedValues(lowerOverlapCorrectedValues);
			odm.setLowerOverlapRawValues(lowerOverlapRawValues);
			odm.setLowerOverlapFhklValues(lowerOverlapFhklValues);
			odm.setLowerOverlapScannedValues(lowerOverlapScannedValues);
			
			for (int l=0; l<overlapHigher.size(); l++){
				xHigherList.add(xArray[k+1].getDouble(overlapHigher.get(l)));
				yHigherList.add(yArray[k+1].getDouble(overlapHigher.get(l)));
				yHigherListFhkl.add(yArrayFhkl[k+1].getDouble(overlapHigher.get(l)));
				yHigherListRaw.add(yArrayRaw[k+1].getDouble(overlapHigher.get(l)));
				
				xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
				yHigherDataset = DatasetFactory.createFromObject(yHigherList);
				yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
				yHigherDatasetRaw = DatasetFactory.createFromObject(yHigherListRaw);	
			}
			
			double[] upperOverlapCorrectedValues = new double[overlapHigher.size()];
			double[] upperOverlapRawValues = new double[overlapHigher.size()];
			double[] upperOverlapFhklValues = new double[overlapHigher.size()];
			double[] upperOverlapScannedValues = new double[overlapHigher.size()];
			
			
			for(int o = 0;o< overlapHigher.size();o++){
				upperOverlapScannedValues[o] = xHigherList.get(o);
				upperOverlapCorrectedValues[o] = yHigherList.get(o);
				upperOverlapRawValues[o] = yHigherListRaw.get(o);;
				upperOverlapFhklValues[o] = yHigherListFhkl.get(o);				
			}
			
			odm.setUpperOverlapCorrectedValues(upperOverlapCorrectedValues);
			odm.setUpperOverlapRawValues(upperOverlapRawValues);
			odm.setUpperOverlapFhklValues(upperOverlapFhklValues);
			odm.setUpperOverlapScannedValues(upperOverlapScannedValues);
			
			double[][] correctionRatio = PolynomialOverlapSXRD.correctionRatio2(xLowerDataset, yLowerDataset, 
			xHigherDataset, yHigherDataset, attenuationFactor,4);
			
			double[][]  correctionRatioFhkl = PolynomialOverlapSXRD.correctionRatio2(xLowerDataset, yLowerDatasetFhkl, 
			xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl,4);
			
			double[][]  correctionRatioRaw = PolynomialOverlapSXRD.correctionRatio2(xLowerDataset, yLowerDatasetRaw, 
			xHigherDataset, yHigherDatasetRaw, attenuationFactorRaw,4);
			
			attenuationFactor = correctionRatio[2][0];
			attenuationFactorFhkl = correctionRatioFhkl[2][0];
			attenuationFactorRaw = correctionRatioRaw[2][0];
			
			odm.setAttenuationFactor(attenuationFactor);
			odm.setAttenuationFactorFhkl(attenuationFactorFhkl);
			odm.setAttenuationFactorRaw(attenuationFactorRaw);
			
			
			odm.setLowerOverlapFitParametersCorrected(correctionRatio[0]);
			odm.setUpperOverlapFitParametersCorrected(correctionRatio[1]);
			
			odm.setLowerOverlapFitParametersFhkl(correctionRatioFhkl[0]);
			odm.setUpperOverlapFitParametersFhkl(correctionRatioFhkl[1]);
			
			odm.setLowerOverlapFitParametersRaw(correctionRatioRaw[0]);
			odm.setUpperOverlapFitParametersRaw(correctionRatioRaw[1]);
			
			
			overlapDataModels.add(odm);
		
		}
		
		yArrayCorrected[k+1] = Maths.multiply(yArray[k+1],attenuationFactor);
		yArrayCorrectedFhkl[k+1] = Maths.multiply(yArrayFhkl[k+1],attenuationFactorFhkl);
		yArrayCorrectedRaw[k+1] = Maths.multiply(yArrayRaw[k+1],attenuationFactorRaw);
		
		yArrayCorrectedError[k+1] = Maths.multiply(yArrayError[k+1],attenuationFactor);
		yArrayCorrectedFhklError[k+1] = Maths.multiply(yArrayFhklError[k+1],attenuationFactorFhkl);
		yRawErrorArrayCorrected[k+1] = Maths.multiply(yArrayRawError[k+1],attenuationFactorRaw);
		
		
		}
		
		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArrayCorrected;
		
		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArrayCorrected;
		
		attenuatedDatasetsRaw[0] = yArrayCorrectedRaw;
		attenuatedDatasetsRaw[1] = xArrayCorrected;
		
		
		Dataset[] sortedAttenuatedDatasets = new Dataset[10];
		
		sortedAttenuatedDatasets[0]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[0], 0)); ///yArray Intensity
		sortedAttenuatedDatasets[1]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[1], 0)); ///xArray
		sortedAttenuatedDatasets[2]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsFhkl[0], 0)); //////yArray Fhkl
		sortedAttenuatedDatasets[7]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsRaw[0], 0)); //////yArray Raw
		
		Dataset sortedYArrayCorrectedError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedError, 0)));
		Dataset sortedYArrayCorrectedFhklError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedFhklError, 0)));
		Dataset sortedYArrayCorrectedRawError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yRawErrorArrayCorrected, 0)));
		
		
		if (sortedAttenuatedDatasets[1].getSize() != 
		sortedAttenuatedDatasets[0].getSize()){
		} 
		
		if(sortedAttenuatedDatasets[1].getShape()[0] != sortedAttenuatedDatasets[0].getShape()[0]){
			System.out.println("error");
		}
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],///xArray
		sortedAttenuatedDatasets[0]);///yArray Intensity
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],///xArray
		sortedAttenuatedDatasets[2]);//////yArray Fhkl
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],///xArray
		sortedAttenuatedDatasets[7]);	//////yArray Raw	
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
		sortedYArrayCorrectedError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
		sortedYArrayCorrectedFhklError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
		sortedYArrayCorrectedRawError);
		
		
		Dataset sortedYArrayCorrectedErrorMax = Maths.add(sortedYArrayCorrectedError, sortedAttenuatedDatasets[0]);
		Dataset sortedYArrayCorrectedFhklErrorMax = Maths.add(sortedYArrayCorrectedFhklError, sortedAttenuatedDatasets[2]);
		Dataset sortedYArrayCorrectedRawErrorMax = Maths.add(sortedYArrayCorrectedRawError, sortedAttenuatedDatasets[7]);
		
		sortedAttenuatedDatasets[0].setErrors(sortedYArrayCorrectedError);
		sortedAttenuatedDatasets[2].setErrors(sortedYArrayCorrectedFhklError);
		sortedAttenuatedDatasets[7].setErrors(sortedYArrayCorrectedRawError);
		
		sortedAttenuatedDatasets[3] = sortedYArrayCorrectedErrorMax;
		sortedAttenuatedDatasets[4] = sortedYArrayCorrectedFhklErrorMax;
		sortedAttenuatedDatasets[8] =  sortedYArrayCorrectedRawErrorMax;
		
		
		Dataset sortedYArrayCorrectedErrorMin = Maths.subtract(sortedAttenuatedDatasets[0], sortedYArrayCorrectedError);
		Dataset sortedYArrayCorrectedFhklErrorMin = Maths.subtract(sortedAttenuatedDatasets[2], sortedYArrayCorrectedFhklError);
		Dataset sortedYArrayCorrectedRawErrorMin = Maths.subtract(sortedAttenuatedDatasets[7], sortedYArrayCorrectedRawError);
		
		
		sortedAttenuatedDatasets[5] = sortedYArrayCorrectedErrorMin;
		sortedAttenuatedDatasets[6] = sortedYArrayCorrectedFhklErrorMin;
		sortedAttenuatedDatasets[9] = sortedYArrayCorrectedRawErrorMin;
		
		if(MethodSetting.toInt(csdp.getCorrectionSelection()) == 1||
			MethodSetting.toInt(csdp.getCorrectionSelection()) == 2||	
			MethodSetting.toInt(csdp.getCorrectionSelection()) == 3){
		
			double normalisation = 1/sortedAttenuatedDatasets[0].getDouble(0);
			sortedAttenuatedDatasets[0] = 
			Maths.multiply(sortedAttenuatedDatasets[0], normalisation);
			
			sortedYArrayCorrectedError = Maths.multiply(sortedYArrayCorrectedError, normalisation);
			
			
			double normalisationFhkl = 1/sortedAttenuatedDatasets[2].getDouble(0);
			sortedAttenuatedDatasets[2] = 
			Maths.multiply(sortedAttenuatedDatasets[2], normalisation);
			
			sortedYArrayCorrectedFhklError = Maths.multiply(sortedYArrayCorrectedFhklError, normalisationFhkl);
			
			double normalisationRaw = 1/sortedAttenuatedDatasets[7].getDouble(0);
			sortedAttenuatedDatasets[7] = 
			Maths.multiply(sortedAttenuatedDatasets[7], normalisation);
			
			sortedYArrayCorrectedRawError = Maths.multiply(sortedYArrayCorrectedRawError, normalisationRaw);
			
			
			
			sortedAttenuatedDatasets[0].setErrors(sortedYArrayCorrectedError);
			sortedAttenuatedDatasets[2].setErrors(sortedYArrayCorrectedFhklError);
			sortedAttenuatedDatasets[7].setErrors(sortedYArrayCorrectedRawError);
		}
		
		csdp.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		csdp.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		csdp.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		csdp.setSplicedCurveYError(sortedYArrayCorrectedError);
		csdp.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		csdp.setSplicedCurveYErrorMax(sortedYArrayCorrectedErrorMax);
		csdp.setSplicedCurveYErrorMin(sortedYArrayCorrectedErrorMin);
		csdp.setSplicedCurveYFhklErrorMax(sortedYArrayCorrectedFhklErrorMax);
		csdp.setSplicedCurveYFhklErrorMin(sortedYArrayCorrectedFhklErrorMin);
		csdp.setSplicedCurveYRaw(sortedAttenuatedDatasets[7]);
		csdp.setSplicedCurveYRawError(sortedYArrayCorrectedRawError);
		csdp.setOverlapDataModels(overlapDataModels);
		
		
		return sortedAttenuatedDatasets;
	}	
	
}
