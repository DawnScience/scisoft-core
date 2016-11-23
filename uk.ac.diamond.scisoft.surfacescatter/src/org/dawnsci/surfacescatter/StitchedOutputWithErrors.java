package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Arrays;

import org.dawnsci.surfacescatter.ui.DatDisplayer;
import org.dawnsci.surfacescatter.ui.OverlapUIModel;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

public class StitchedOutputWithErrors {
	
	private static double attenuationFactor;
	private static double attenuationFactorFhkl;
	private static int DEBUG =1;

	public static IDataset[][] curveStitch (IPlottingSystem<Composite> plotSystem, 
											TableItem[] selectedTableItems,
											ArrayList<DataModel> dms, 
											SuperModel sm, 
											DatDisplayer datDisplayer ){
		
		ArrayList<IDataset> xArrayList = new ArrayList<>();
		ArrayList<IDataset> yArrayList = new ArrayList<>();
		ArrayList<IDataset> yArrayListFhkl = new ArrayList<>();
		
		ArrayList<IDataset> yArrayListError = new ArrayList<>();
		ArrayList<IDataset> yArrayListFhklError = new ArrayList<>();
		
		sm.resetSplicedCurves();
		
		
		for(int b = 0;b<selectedTableItems.length;b++){
				
				int p = (Arrays.asList(datDisplayer.getList().getItems())).indexOf(selectedTableItems[b].getText());
				
				if (dms.get(p).getyList() == null || dms.get(p).getxList() == null) {
					
				} 
				else {
					xArrayList.add(dms.get(p).xIDataset());
					yArrayList.add(dms.get(p).yIDataset());
					yArrayListFhkl.add(dms.get(p).yIDatasetFhkl());
					yArrayListError.add(dms.get(p).yIDatasetError());
					yArrayListFhklError.add(dms.get(p).yIDatasetFhklError());
				}
				
		}
		
		IDataset[] xArray= new IDataset[xArrayList.size()];
		IDataset[] yArray= new IDataset[yArrayList.size()];
		IDataset[] yArrayError= new IDataset[yArrayListError.size()];
		IDataset[] yArrayFhkl= new IDataset[yArrayListFhkl.size()];
		IDataset[] yArrayFhklError= new IDataset[yArrayListFhklError.size()];
		
		for (int b = 0; b< xArrayList.size(); b++){
			xArray[b] = xArrayList.get(b);
			yArray[b] = yArrayList.get(b);
			yArrayFhkl[b] = yArrayListFhkl.get(b);
			yArrayError[b]=yArrayListError.get(b);
			yArrayFhklError[b]=yArrayListFhklError.get(b);
		}
		
		IDataset[] xArrayCorrected = xArray.clone();
		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();
		
		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();
		
		
		IDataset[][] attenuatedDatasets = new IDataset[2][];
		
		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];
		
		int d = xArray.length;
		
		double[][] maxMinArray = new double[d][2];
		
		for(int k =0;k<d;k++){
				maxMinArray[k][0] = (double) xArray[k].max(null);
				maxMinArray[k][1] = (double) xArray[k].min(null);
		}
				
		attenuationFactor =1;
		attenuationFactorFhkl =1;
				
				for (int k=0; k<xArray.length-1;k++){
			
					
					ArrayList<Integer> overlapLower = new ArrayList<Integer>();
					ArrayList<Integer> overlapHigher = new ArrayList<Integer>();
					
					
					for(int l=0; l<xArrayCorrected[k].getSize();l++){
						if (xArrayCorrected[k].getDouble(l)>=maxMinArray[k][1]){
							overlapLower.add(l);
						}
					}
					for(int m=0; m<xArrayCorrected[k+1].getSize();m++){
						if (xArrayCorrected[k+1].getDouble(m)<maxMinArray[k][0]){
							overlapHigher.add(m);
						}
					}
							
					Dataset[] xLowerDataset =new Dataset[1];
					Dataset yLowerDataset =null;
					Dataset yLowerDatasetFhkl =null;
					Dataset[] xHigherDataset =new Dataset[1];
					Dataset yHigherDataset =null;
					Dataset yHigherDatasetFhkl =null;
						
					ArrayList<Double> xLowerList =new ArrayList<>();
					ArrayList<Double> yLowerList =new ArrayList<>();
					ArrayList<Double> yLowerListFhkl =new ArrayList<>();
					ArrayList<Double> xHigherList =new ArrayList<>();
					ArrayList<Double> yHigherList =new ArrayList<>();
					ArrayList<Double> yHigherListFhkl =new ArrayList<>();
					
					if (overlapLower.size() > 0 && overlapHigher.size() > 0){
					
						for (int l=0; l<overlapLower.size(); l++){
							xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
							yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
							yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));
							
							xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
							yLowerDataset = DatasetFactory.createFromObject(yLowerList);
							yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
						}
								
						for (int l=0; l<overlapHigher.size(); l++){
							xHigherList.add(xArray[k+1].getDouble(overlapHigher.get(l)));
							yHigherList.add(yArray[k+1].getDouble(overlapHigher.get(l)));
							yHigherListFhkl.add(yArrayFhkl[k+1].getDouble(overlapHigher.get(l)));
							
							xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
							yHigherDataset = DatasetFactory.createFromObject(yHigherList);
							yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
						}
							
						double correctionRatio = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDataset, 
								xHigherDataset, yHigherDataset, attenuationFactor,4);
						
						double  correctionRatioFhkl = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDatasetFhkl, 
								xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl,4);
					
						attenuationFactor = correctionRatio;
						attenuationFactorFhkl = correctionRatioFhkl;
					
					}
//					////////////////need to deal with the lack of overlap here
						
					yArrayCorrected[k+1] = Maths.multiply(yArray[k+1],attenuationFactor);
					yArrayCorrectedFhkl[k+1] = Maths.multiply(yArrayFhkl[k+1],attenuationFactorFhkl);
					
					yArrayCorrectedError[k+1] = Maths.multiply(yArrayError[k+1],attenuationFactor);
					yArrayCorrectedFhklError[k+1] = Maths.multiply(yArrayFhklError[k+1],attenuationFactorFhkl);
					
					System.out.println("attenuation factor:  " + attenuationFactor + "   k:   " +k);
					System.out.println("attenuation factor Fhkl:  " + attenuationFactorFhkl + "   k:   " +k);
						
					}

		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArrayCorrected;
			
		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArrayCorrected;

		Dataset[] sortedAttenuatedDatasets = new Dataset[7];
		
		sortedAttenuatedDatasets[0]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[0], 0)); ///yArray Intensity
		sortedAttenuatedDatasets[1]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[1], 0)); ///xArray
		sortedAttenuatedDatasets[2]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsFhkl[0], 0)); //////yArray Fhkl

		Dataset sortedYArrayCorrectedError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedError, 0)));
		Dataset sortedYArrayCorrectedFhklError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedFhklError, 0)));
		
		
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[0]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[2]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedFhklError);
		
		Dataset sortedYArrayCorrectedErrorMax = Maths.add(sortedYArrayCorrectedError, sortedAttenuatedDatasets[0]);
		Dataset sortedYArrayCorrectedFhklErrorMax = Maths.add(sortedYArrayCorrectedFhklError, sortedAttenuatedDatasets[2]);
		
		sortedAttenuatedDatasets[3] = sortedYArrayCorrectedErrorMax;
		sortedAttenuatedDatasets[4] = sortedYArrayCorrectedFhklErrorMax;
		
		Dataset sortedYArrayCorrectedErrorMin = Maths.subtract(sortedAttenuatedDatasets[0], sortedYArrayCorrectedError);
		Dataset sortedYArrayCorrectedFhklErrorMin = Maths.subtract(sortedAttenuatedDatasets[2], sortedYArrayCorrectedFhklError);

		sortedAttenuatedDatasets[5] = sortedYArrayCorrectedErrorMin;
		sortedAttenuatedDatasets[6] = sortedYArrayCorrectedFhklErrorMin;
		
		
		sm.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		sm.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		sm.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		sm.setSplicedCurveYError(sortedYArrayCorrectedError);
		sm.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		sm.setSplicedCurveYErrorMax(sortedYArrayCorrectedErrorMax);
		sm.setSplicedCurveYErrorMin(sortedYArrayCorrectedErrorMin);
		sm.setSplicedCurveYFhklErrorMax(sortedYArrayCorrectedFhklErrorMax);
		sm.setSplicedCurveYFhklErrorMin(sortedYArrayCorrectedFhklErrorMin);
		
		return null;
	}
	
	public  static IDataset[] curveStitch (IPlottingSystem<Composite> plotSystem, 
										   ArrayList<IDataset> xArrayList,
										   ArrayList<IDataset> yArrayList,
										   ArrayList<IDataset> yArrayListError,
										   ArrayList<IDataset> yArrayListFhkl,
										   ArrayList<IDataset> yArrayListFhklError,
										   ArrayList<DataModel> dms, 
										   SuperModel sm,
										   DatDisplayer datDisplayer, 
										   OverlapUIModel model ){
		
		sm.resetSplicedCurves();
		
		IDataset[] xArray= new IDataset[xArrayList.size()];
		IDataset[] yArray= new IDataset[yArrayList.size()];
		IDataset[] yArrayError= new IDataset[yArrayListError.size()];
		IDataset[] yArrayFhkl= new IDataset[yArrayListFhkl.size()];
		IDataset[] yArrayFhklError= new IDataset[yArrayListFhklError.size()];
		
		for (int b = 0; b< xArrayList.size(); b++){
			xArray[b] = xArrayList.get(b);
			yArray[b] = yArrayList.get(b);
			yArrayFhkl[b] = yArrayListFhkl.get(b);
			yArrayError[b]=yArrayListError.get(b);
			yArrayFhklError[b]=yArrayListFhklError.get(b);
		}
		
		IDataset[] xArrayCorrected = xArray.clone();
		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();
		
		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();
		
		
		
		IDataset[][] attenuatedDatasets = new IDataset[2][];
		
		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];
		
		int d =  model.getROIList().size();
		
		double[][] maxMinArray = new double[d][2];
		
		for(int k =0;k<=d-1;k++){
			
			if(DEBUG ==1){
				System.out.println("k in Stitchedoutput:" + k);
			}
			
			if( model.getROIListElement(k)!= null){
				
				
				System.out.println("k in Stitchedoutput:" + k);
				IRectangularROI box = model.getROIListElement(k).getBounds();////////////////////////aaaaaarrrrrrggggghhhjhj
//				model.getROIListElement(k).getPoint();
				
							
				maxMinArray[k][0] = box.getPointX()+ box.getLength(0);
				maxMinArray[k][1] = box.getPointX();
			}
//			maxMinArray[k][0] = (double) xArray[k].max(null);
//			maxMinArray[k][1] = (double) xArray[k].min(null);
		}
				
		attenuationFactor =1;
		attenuationFactorFhkl =1;
				
				for (int k=0; k<model.getROIList().size();k++){
					
					System.out.println("k: " +k);
					
					if( model.getROIListElement(k)!= null){
						
						System.out.println("k after check: " +k);
						
						ArrayList<Integer> overlapLower = new ArrayList<Integer>();
						ArrayList<Integer> overlapHigher = new ArrayList<Integer>();
						
						
						for(int l=0; l<xArrayCorrected[k].getSize();l++){
							if (xArrayCorrected[k].getDouble(l)>=maxMinArray[k][1]){
								overlapLower.add(l);
							}
						}
						for(int m=0; m<xArrayCorrected[k+1].getSize();m++){
							if (xArrayCorrected[k+1].getDouble(m)<maxMinArray[k][0]){
								overlapHigher.add(m);
							}
						}
								
						Dataset[] xLowerDataset =new Dataset[1];
						Dataset yLowerDataset =null;
						Dataset yLowerDatasetFhkl =null;
						Dataset[] xHigherDataset =new Dataset[1];
						Dataset yHigherDataset =null;
						Dataset yHigherDatasetFhkl =null;
							
						ArrayList<Double> xLowerList =new ArrayList<>();
						ArrayList<Double> yLowerList =new ArrayList<>();
						ArrayList<Double> yLowerListFhkl =new ArrayList<>();
						ArrayList<Double> xHigherList =new ArrayList<>();
						ArrayList<Double> yHigherList =new ArrayList<>();
						ArrayList<Double> yHigherListFhkl =new ArrayList<>();
						
						if (overlapLower.size() > 0 && overlapHigher.size() > 0){
						
							for (int l=0; l<overlapLower.size(); l++){
								xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
								yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
								yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));
								
								xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
								yLowerDataset = DatasetFactory.createFromObject(yLowerList);
								yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
							}
									
							for (int l=0; l<overlapHigher.size(); l++){
								xHigherList.add(xArray[k+1].getDouble(overlapHigher.get(l)));
								yHigherList.add(yArray[k+1].getDouble(overlapHigher.get(l)));
								yHigherListFhkl.add(yArrayFhkl[k+1].getDouble(overlapHigher.get(l)));
								
								xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
								yHigherDataset = DatasetFactory.createFromObject(yHigherList);
								yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
							}
								
							double correctionRatio = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDataset, 
									xHigherDataset, yHigherDataset, attenuationFactor,4);
							
							double  correctionRatioFhkl = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDatasetFhkl, 
									xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl,4);
							
							System.out.println("k just before correctionratio: " +k);
							
							attenuationFactor = correctionRatio;
							attenuationFactorFhkl = correctionRatioFhkl;
						}
					
					}
//					////////////////need to deal with the lack of overlap here
						
					yArrayCorrected[k+1] = Maths.multiply(yArray[k+1],attenuationFactor);
					yArrayCorrectedFhkl[k+1] = Maths.multiply(yArrayFhkl[k+1],attenuationFactorFhkl);
					
					yArrayCorrectedError[k+1] = Maths.multiply(yArrayError[k+1],attenuationFactor);
					yArrayCorrectedFhklError[k+1] = Maths.multiply(yArrayFhklError[k+1],attenuationFactorFhkl);
					
					System.out.println("attenuation factor:  " + attenuationFactor + "   k:   " +k);
					System.out.println("attenuation factor Fhkl:  " + attenuationFactorFhkl + "   k:   " +k);
						
					}

		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArrayCorrected;
			
		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArrayCorrected;

		Dataset[] sortedAttenuatedDatasets = new Dataset[7];
		
		sortedAttenuatedDatasets[0]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[0], 0)); ///yArray Intensity
		sortedAttenuatedDatasets[1]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[1], 0)); ///xArray
		sortedAttenuatedDatasets[2]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsFhkl[0], 0)); //////yArray Fhkl

		Dataset sortedYArrayCorrectedError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedError, 0)));
		Dataset sortedYArrayCorrectedFhklError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedFhklError, 0)));
		
		
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[0]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[2]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedFhklError);
		
		Dataset sortedYArrayCorrectedErrorMax = Maths.add(sortedYArrayCorrectedError, sortedAttenuatedDatasets[0]);
		Dataset sortedYArrayCorrectedFhklErrorMax = Maths.add(sortedYArrayCorrectedFhklError, sortedAttenuatedDatasets[2]);
		
		sortedAttenuatedDatasets[3] = sortedYArrayCorrectedErrorMax;
		sortedAttenuatedDatasets[4] = sortedYArrayCorrectedFhklErrorMax;
		
		Dataset sortedYArrayCorrectedErrorMin = Maths.subtract(sortedAttenuatedDatasets[0], sortedYArrayCorrectedError);
		Dataset sortedYArrayCorrectedFhklErrorMin = Maths.subtract(sortedAttenuatedDatasets[2], sortedYArrayCorrectedFhklError);

		sortedAttenuatedDatasets[5] = sortedYArrayCorrectedErrorMin;
		sortedAttenuatedDatasets[6] = sortedYArrayCorrectedFhklErrorMin;
		
		
		sm.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		sm.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		sm.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		sm.setSplicedCurveYError(sortedYArrayCorrectedError);
		sm.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		sm.setSplicedCurveYErrorMax(sortedYArrayCorrectedErrorMax);
		sm.setSplicedCurveYErrorMin(sortedYArrayCorrectedErrorMin);
		sm.setSplicedCurveYFhklErrorMax(sortedYArrayCorrectedFhklErrorMax);
		sm.setSplicedCurveYFhklErrorMin(sortedYArrayCorrectedFhklErrorMin);
		
		return sortedAttenuatedDatasets;
	}
	
	public static IDataset[][] curveStitch3 (ArrayList<IDataset> xArrayList,
											 ArrayList<IDataset> yArrayList,
											 ArrayList<IDataset> yArrayListError,
											 ArrayList<IDataset> yArrayListFhkl,
											 ArrayList<IDataset> yArrayListFhklError,
											 SuperModel sm){ 

		sm.resetSplicedCurves();

		IDataset[] xArray= new IDataset[xArrayList.size()];
		IDataset[] yArray= new IDataset[yArrayList.size()];
		IDataset[] yArrayError= new IDataset[yArrayListError.size()];
		IDataset[] yArrayFhkl= new IDataset[yArrayListFhkl.size()];
		IDataset[] yArrayFhklError= new IDataset[yArrayListFhklError.size()];
		
		for (int b = 0; b< xArrayList.size(); b++){
			xArray[b] = xArrayList.get(b);
			yArray[b] = yArrayList.get(b);
			yArrayFhkl[b] = yArrayListFhkl.get(b);
			yArrayError[b]=yArrayListError.get(b);
			yArrayFhklError[b]=yArrayListFhklError.get(b);
		}
		
		IDataset[] xArrayCorrected = xArray.clone();
		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();
		
		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();
		
		
		IDataset[][] attenuatedDatasets = new IDataset[2][];
		
		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];
		
		int d = xArray.length;
		
		double[][] maxMinArray = new double[d][2];
		
		for(int k =0;k<d;k++){
				maxMinArray[k][0] = (double) xArray[k].max(null);
				maxMinArray[k][1] = (double) xArray[k].min(null);
		}
				
		attenuationFactor =1;
		attenuationFactorFhkl =1;
				
				for (int k=0; k<xArray.length-1;k++){
			
					
					ArrayList<Integer> overlapLower = new ArrayList<Integer>();
					ArrayList<Integer> overlapHigher = new ArrayList<Integer>();
					
					
					for(int l=0; l<xArrayCorrected[k].getSize();l++){
						if (xArrayCorrected[k].getDouble(l)>=maxMinArray[k][1]){
							overlapLower.add(l);
						}
					}
					for(int m=0; m<xArrayCorrected[k+1].getSize();m++){
						if (xArrayCorrected[k+1].getDouble(m)<maxMinArray[k][0]){
							overlapHigher.add(m);
						}
					}
							
					Dataset[] xLowerDataset =new Dataset[1];
					Dataset yLowerDataset =null;
					Dataset yLowerDatasetFhkl =null;
					Dataset[] xHigherDataset =new Dataset[1];
					Dataset yHigherDataset =null;
					Dataset yHigherDatasetFhkl =null;
						
					ArrayList<Double> xLowerList =new ArrayList<>();
					ArrayList<Double> yLowerList =new ArrayList<>();
					ArrayList<Double> yLowerListFhkl =new ArrayList<>();
					ArrayList<Double> xHigherList =new ArrayList<>();
					ArrayList<Double> yHigherList =new ArrayList<>();
					ArrayList<Double> yHigherListFhkl =new ArrayList<>();
					
					if (overlapLower.size() > 0 && overlapHigher.size() > 0){
					
						for (int l=0; l<overlapLower.size(); l++){
							xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
							yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
							yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));
							
							xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
							yLowerDataset = DatasetFactory.createFromObject(yLowerList);
							yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
						}
								
						for (int l=0; l<overlapHigher.size(); l++){
							xHigherList.add(xArray[k+1].getDouble(overlapHigher.get(l)));
							yHigherList.add(yArray[k+1].getDouble(overlapHigher.get(l)));
							yHigherListFhkl.add(yArrayFhkl[k+1].getDouble(overlapHigher.get(l)));
							
							xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
							yHigherDataset = DatasetFactory.createFromObject(yHigherList);
							yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
						}
							
						double correctionRatio = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDataset, 
								xHigherDataset, yHigherDataset, attenuationFactor,4);
						
						double  correctionRatioFhkl = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDatasetFhkl, 
								xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl,4);
					
						attenuationFactor = correctionRatio;
						attenuationFactorFhkl = correctionRatioFhkl;
					
					}
//					////////////////need to deal with the lack of overlap here
						
					yArrayCorrected[k+1] = Maths.multiply(yArray[k+1],attenuationFactor);
					yArrayCorrectedFhkl[k+1] = Maths.multiply(yArrayFhkl[k+1],attenuationFactorFhkl);
					
					yArrayCorrectedError[k+1] = Maths.multiply(yArrayError[k+1],attenuationFactor);
					yArrayCorrectedFhklError[k+1] = Maths.multiply(yArrayFhklError[k+1],attenuationFactorFhkl);
					
					System.out.println("attenuation factor:  " + attenuationFactor + "   k:   " +k);
					System.out.println("attenuation factor Fhkl:  " + attenuationFactorFhkl + "   k:   " +k);
						
					}

		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArrayCorrected;
			
		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArrayCorrected;

		Dataset[] sortedAttenuatedDatasets = new Dataset[7];
		
		sortedAttenuatedDatasets[0]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[0], 0)); ///yArray Intensity
		sortedAttenuatedDatasets[1]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[1], 0)); ///xArray
		sortedAttenuatedDatasets[2]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsFhkl[0], 0)); //////yArray Fhkl

		Dataset sortedYArrayCorrectedError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedError, 0)));
		Dataset sortedYArrayCorrectedFhklError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedFhklError, 0)));
		
		
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[0]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[2]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedFhklError);
		
		Dataset sortedYArrayCorrectedErrorMax = Maths.add(sortedYArrayCorrectedError, sortedAttenuatedDatasets[0]);
		Dataset sortedYArrayCorrectedFhklErrorMax = Maths.add(sortedYArrayCorrectedFhklError, sortedAttenuatedDatasets[2]);
		
		sortedAttenuatedDatasets[3] = sortedYArrayCorrectedErrorMax;
		sortedAttenuatedDatasets[4] = sortedYArrayCorrectedFhklErrorMax;
		
		Dataset sortedYArrayCorrectedErrorMin = Maths.subtract(sortedAttenuatedDatasets[0], sortedYArrayCorrectedError);
		Dataset sortedYArrayCorrectedFhklErrorMin = Maths.subtract(sortedAttenuatedDatasets[2], sortedYArrayCorrectedFhklError);

		sortedAttenuatedDatasets[5] = sortedYArrayCorrectedErrorMin;
		sortedAttenuatedDatasets[6] = sortedYArrayCorrectedFhklErrorMin;
		
		
		sm.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		sm.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		sm.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		sm.setSplicedCurveYError(sortedYArrayCorrectedError);
		sm.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		sm.setSplicedCurveYErrorMax(sortedYArrayCorrectedErrorMax);
		sm.setSplicedCurveYErrorMin(sortedYArrayCorrectedErrorMin);
		sm.setSplicedCurveYFhklErrorMax(sortedYArrayCorrectedFhklErrorMax);
		sm.setSplicedCurveYFhklErrorMin(sortedYArrayCorrectedFhklErrorMin);
		
		return null;
	}
	
	public static IDataset[] curveStitch4 (ArrayList<DataModel> dms, 
											SuperModel sm){
		
		ArrayList<IDataset> xArrayList = new ArrayList<>();
		ArrayList<IDataset> yArrayList = new ArrayList<>();
		ArrayList<IDataset> yArrayListFhkl = new ArrayList<>();	
		ArrayList<IDataset> yArrayListError = new ArrayList<>();
		ArrayList<IDataset> yArrayListFhklError = new ArrayList<>();

		sm.resetSplicedCurves();


		for(int p = 0;p<dms.size();p++){
			
			xArrayList.add(dms.get(p).xIDataset());
			yArrayList.add(dms.get(p).yIDataset());
			yArrayListFhkl.add(dms.get(p).yIDatasetFhkl());
			yArrayListError.add(dms.get(p).yIDatasetError());
			yArrayListFhklError.add(dms.get(p).yIDatasetFhklError());				
		}


		IDataset[] xArray= new IDataset[xArrayList.size()];
		IDataset[] yArray= new IDataset[yArrayList.size()];
		IDataset[] yArrayError= new IDataset[yArrayListError.size()];
		IDataset[] yArrayFhkl= new IDataset[yArrayListFhkl.size()];
		IDataset[] yArrayFhklError= new IDataset[yArrayListFhklError.size()];

		for (int b = 0; b< xArrayList.size(); b++){
			xArray[b] = xArrayList.get(b);
			yArray[b] = yArrayList.get(b);
			yArrayFhkl[b] = yArrayListFhkl.get(b);
			yArrayError[b]=yArrayListError.get(b);
			yArrayFhklError[b]=yArrayListFhklError.get(b);
		}

		IDataset[] xArrayCorrected = xArray.clone();
		IDataset[] yArrayCorrected = yArray.clone();
		IDataset[] yArrayCorrectedFhkl = yArrayFhkl.clone();

		IDataset[] yArrayCorrectedError = yArrayError.clone();
		IDataset[] yArrayCorrectedFhklError = yArrayFhklError.clone();


		IDataset[][] attenuatedDatasets = new IDataset[2][];

		IDataset[][] attenuatedDatasetsFhkl = new IDataset[2][];

		int d = xArray.length;

		double[][] maxMinArray = new double[d][2];

		for(int k =0;k<d;k++){
			maxMinArray[k][0] = (double) xArray[k].max(null);
			maxMinArray[k][1] = (double) xArray[k].min(null);
		}

		attenuationFactor =1;
		attenuationFactorFhkl =1;

		for (int k=0; k<xArray.length-1;k++){


			ArrayList<Integer> overlapLower = new ArrayList<Integer>();
			ArrayList<Integer> overlapHigher = new ArrayList<Integer>();


			for(int l=0; l<xArrayCorrected[k].getSize();l++){
				if (xArrayCorrected[k].getDouble(l)>=maxMinArray[k][1]){
					overlapLower.add(l);
				}
			}	
			for(int m=0; m<xArrayCorrected[k+1].getSize();m++){
				if (xArrayCorrected[k+1].getDouble(m)<maxMinArray[k][0]){
					overlapHigher.add(m);
				}
			}

			Dataset[] xLowerDataset =new Dataset[1];
			Dataset yLowerDataset =null;
			Dataset yLowerDatasetFhkl =null;
			Dataset[] xHigherDataset =new Dataset[1];
			Dataset yHigherDataset =null;
			Dataset yHigherDatasetFhkl =null;
			
			ArrayList<Double> xLowerList =new ArrayList<>();
			ArrayList<Double> yLowerList =new ArrayList<>();
			ArrayList<Double> yLowerListFhkl =new ArrayList<>();
			ArrayList<Double> xHigherList =new ArrayList<>();
			ArrayList<Double> yHigherList =new ArrayList<>();
			ArrayList<Double> yHigherListFhkl =new ArrayList<>();
			
			if (overlapLower.size() > 0 && overlapHigher.size() > 0){
				
				for (int l=0; l<overlapLower.size(); l++){
					xLowerList.add(xArray[k].getDouble(overlapLower.get(l)));
					yLowerList.add(yArray[k].getDouble(overlapLower.get(l)));
					yLowerListFhkl.add(yArrayFhkl[k].getDouble(overlapLower.get(l)));

					xLowerDataset[0] = DatasetFactory.createFromObject(xLowerList);
					yLowerDataset = DatasetFactory.createFromObject(yLowerList);
					yLowerDatasetFhkl = DatasetFactory.createFromObject(yLowerListFhkl);
				}

				for (int l=0; l<overlapHigher.size(); l++){
					xHigherList.add(xArray[k+1].getDouble(overlapHigher.get(l)));
					yHigherList.add(yArray[k+1].getDouble(overlapHigher.get(l)));
					yHigherListFhkl.add(yArrayFhkl[k+1].getDouble(overlapHigher.get(l)));

					xHigherDataset[0] = DatasetFactory.createFromObject(xHigherList);
					yHigherDataset = DatasetFactory.createFromObject(yHigherList);
					yHigherDatasetFhkl = DatasetFactory.createFromObject(yHigherListFhkl);
				}

				double correctionRatio = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDataset, 
						xHigherDataset, yHigherDataset, attenuationFactor,4);

				double  correctionRatioFhkl = PolynomialOverlapSXRD.correctionRatio(xLowerDataset, yLowerDatasetFhkl, 
						xHigherDataset, yHigherDatasetFhkl, attenuationFactorFhkl,4);

				attenuationFactor = correctionRatio;
				attenuationFactorFhkl = correctionRatioFhkl;

			}
			//////////////////need to deal with the lack of overlap here

			yArrayCorrected[k+1] = Maths.multiply(yArray[k+1],attenuationFactor);
			yArrayCorrectedFhkl[k+1] = Maths.multiply(yArrayFhkl[k+1],attenuationFactorFhkl);
			
			yArrayCorrectedError[k+1] = Maths.multiply(yArrayError[k+1],attenuationFactor);
			yArrayCorrectedFhklError[k+1] = Maths.multiply(yArrayFhklError[k+1],attenuationFactorFhkl);
			
			System.out.println("attenuation factor:  " + attenuationFactor + "   k:   " +k);
			System.out.println("attenuation factor Fhkl:  " + attenuationFactorFhkl + "   k:   " +k);
			
		}

		attenuatedDatasets[0] = yArrayCorrected;
		attenuatedDatasets[1] = xArrayCorrected;

		attenuatedDatasetsFhkl[0] = yArrayCorrectedFhkl;
		attenuatedDatasetsFhkl[1] = xArrayCorrected;
		
		Dataset[] sortedAttenuatedDatasets = new Dataset[7];
		
		sortedAttenuatedDatasets[0]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[0], 0)); ///yArray Intensity
		sortedAttenuatedDatasets[1]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasets[1], 0)); ///xArray
		sortedAttenuatedDatasets[2]=DatasetUtils.convertToDataset(DatasetUtils.concatenate(attenuatedDatasetsFhkl[0], 0)); //////yArray Fhkl
		
		Dataset sortedYArrayCorrectedError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedError, 0)));
		Dataset sortedYArrayCorrectedFhklError= (DatasetUtils.convertToDataset(DatasetUtils.concatenate(yArrayCorrectedFhklError, 0)));
		
		
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[0]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedAttenuatedDatasets[2]);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedError);
		
		DatasetUtils.sort(sortedAttenuatedDatasets[1],
				sortedYArrayCorrectedFhklError);

		Dataset sortedYArrayCorrectedErrorMax = Maths.add(sortedYArrayCorrectedError, sortedAttenuatedDatasets[0]);
		Dataset sortedYArrayCorrectedFhklErrorMax = Maths.add(sortedYArrayCorrectedFhklError, sortedAttenuatedDatasets[2]);
		
		sortedAttenuatedDatasets[3] = sortedYArrayCorrectedErrorMax;
		sortedAttenuatedDatasets[4] = sortedYArrayCorrectedFhklErrorMax;
		
		Dataset sortedYArrayCorrectedErrorMin = Maths.subtract(sortedAttenuatedDatasets[0], sortedYArrayCorrectedError);
		Dataset sortedYArrayCorrectedFhklErrorMin = Maths.subtract(sortedAttenuatedDatasets[2], sortedYArrayCorrectedFhklError);
		
		sortedAttenuatedDatasets[5] = sortedYArrayCorrectedErrorMin;
		sortedAttenuatedDatasets[6] = sortedYArrayCorrectedFhklErrorMin;


		sm.setSplicedCurveY(sortedAttenuatedDatasets[0]);
		sm.setSplicedCurveX(sortedAttenuatedDatasets[1]);
		sm.setSplicedCurveYFhkl(sortedAttenuatedDatasets[2]);
		sm.setSplicedCurveYError(sortedYArrayCorrectedError);
		sm.setSplicedCurveYFhklError(sortedYArrayCorrectedFhklError);
		sm.setSplicedCurveYErrorMax(sortedYArrayCorrectedErrorMax);
		sm.setSplicedCurveYErrorMin(sortedYArrayCorrectedErrorMin);
		sm.setSplicedCurveYFhklErrorMax(sortedYArrayCorrectedFhklErrorMax);
		sm.setSplicedCurveYFhklErrorMin(sortedYArrayCorrectedFhklErrorMin);
		
		return sortedAttenuatedDatasets;
	}	
}


