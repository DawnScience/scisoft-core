package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.january.dataset.Dataset;
//import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class CsdpGeneratorFromDrm {

	private CurveStitchDataPackage csdp;
	
	private DirectoryModel drm;
	
	public  CurveStitchDataPackage generateCsdpFromDrm (DirectoryModel drm){
		
		this.drm= drm;
		
		csdp = new CurveStitchDataPackage();
		
		OutputCurvesDataPackage ocdp = drm.getOcdp();
		
		int noOfDats = drm.getDatFilepaths().length;
		
		csdp.setFilepaths(drm.getDatFilepaths());
		
		IDataset[] xIDataset = iDatasetArrayGenerator(noOfDats,
													  drm.getDmxList());
		
		csdp.setxIDataset(xIDataset);
		
		IDataset[] yIDataset = iDatasetArrayGenerator(noOfDats,
				  									  ocdp.getyListForEachDat());

		csdp.setyIDataset(yIDataset);

		IDataset[] yIDatasetError = iDatasetArrayGenerator(noOfDats,
				  										   ocdp.getyListErrorForEachDat());

		csdp.setyIDatasetError(yIDatasetError);

		IDataset[] yIDatasetFhkl= iDatasetArrayGenerator(noOfDats,
															ocdp.getyListFhklForEachDat());

		csdp.setyIDatasetFhkl(yIDatasetFhkl);
		
		IDataset[] yIDatasetFhklError= iDatasetArrayGenerator(noOfDats,
			 	 											 ocdp.getyListFhklErrorForEachDat());

		csdp.setyIDatasetFhklError(yIDatasetFhklError);

		IDataset[] yRawIDataset= iDatasetArrayGenerator(noOfDats,
														ocdp.getyListRawIntensityForEachDat());

		csdp.setyRawIDataset(yRawIDataset);
		
		IDataset[] yRawIDatasetError= iDatasetArrayGenerator(noOfDats,
														 	 ocdp.getyListRawIntensityErrorForEachDat());

		csdp.setyRawIDatasetError(yRawIDatasetError);
		
		csdp.setCorrectionSelection(drm.getFms().get(0).getCorrectionSelection());
		
		return csdp;
	}
		
	
	public CurveStitchDataPackage getCsdp() {
		return csdp;
	}

	public void setCsdp(CurveStitchDataPackage csdp) {
		this.csdp = csdp;
	}

	public static IDataset iDatasetGenerator(ArrayList<Double> input){
		
		if (input==null){
			input = new ArrayList<Double>();
		}

		ArrayList<Double> outputC =  (ArrayList<Double>) input.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		outputC.removeAll(zero);
		
		IDataset yOut = DatasetFactory.createFromList(outputC);
		
		return yOut;
	}
	
	public  IDataset[] iDatasetArrayGenerator(int n, // number of Dats
			
			  							   			ArrayList<ArrayList<Double>> input){
		
		
		if (input==null){
			input = new ArrayList<ArrayList<Double>>();
			
			for(int r=0; r<n;r++){
				input.add(new ArrayList<Double>());
				
				for(int u =0; u<drm.getNoOfImagesInDatFile(r);u++ ){
					input.get(r).add(0.0);
				}
			}
		}
		
		IDataset[] output = new IDataset[input.size()];
		
		ArrayList<ArrayList<Double>> outputC =  (ArrayList<ArrayList<Double>>) input.clone();
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(0.0);
		
		outputC.removeAll(zero);
		
		
		for(int u = 0; u<input.size(); u++){
			IDataset yOut = DatasetFactory.createFromList(outputC.get(u));
			output[u] = yOut;
		}
		
		return output;
	}
	
	
	public ArrayList<IDataset> convert(IDataset[] al){
		
		ArrayList<IDataset> output = new ArrayList<>(); 
		
		for(int i =0 ; i< al.length; i++){
			output.add(al[i]);
		}
		
		return output;
	}
}
