package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.dawnsci.surfacescatter.IntensityDisplayEnum.IntensityDisplaySetting;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class GoodPointStripper {

	
	public IDataset[][] goodPointStripper(CurveStitchDataPackage csdp, 
							 IntensityDisplaySetting ids){
		
		
		IDataset[] goodPointIDatasets = csdp.getGoodPointIDataset();
		
		IDataset[] xIDatasets = csdp.getxIDataset();
		
		int noOfDats =xIDatasets.length; 
		
		IDataset[] yIDatasets = csdp.getyIDataset();
		IDataset[] yIDatasetsErrors = csdp.getyIDatasetError();
		
		switch (ids){
			case Corrected_Intensity:
				yIDatasets = csdp.getyIDataset();
				yIDatasetsErrors = csdp.getyIDatasetError();
				break;
				
			case Raw_Intensity:
				yIDatasets = csdp.getyRawIDataset();
				yIDatasetsErrors = csdp.getyRawIDatasetError();
				break;
				
			case Fhkl:
				yIDatasets = csdp.getyIDatasetFhkl();
				yIDatasetsErrors = csdp.getyIDatasetFhklError();
				break;
				
			default:
				//defensive;
		}
		
		IDataset[][] output = new IDataset[noOfDats][];
		
		for(int i = 0; i<noOfDats; i++){
			
			ArrayList<Double> xHolder =new ArrayList<>();
			ArrayList<Double> yHolder =new ArrayList<>();
			ArrayList<Double> yErrorHolder =new ArrayList<>();
			
			for(int j =0; j<goodPointIDatasets[i].getSize(); j++){
				if(goodPointIDatasets[i].getBoolean(j)){
					
					xHolder.add(xIDatasets[i].getDouble(j));
					yHolder.add(yIDatasets[i].getDouble(j));
					yErrorHolder.add(yIDatasetsErrors[i].getDouble(j));
				}
			}
			
			IDataset xH = DatasetFactory.createFromList(xHolder); 
			IDataset yH = DatasetFactory.createFromList(yHolder);
			IDataset yEH = DatasetFactory.createFromList(yErrorHolder);
			
			IDataset[] chunk = new IDataset[] {xH, yH, yEH};
			
			output[i] = chunk;
		}
		
		return output;
	}
	
	
}
