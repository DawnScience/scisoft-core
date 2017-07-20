package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class CsdpGeneratorFromDrm {

	private CurveStitchDataPackage csdp;
	
	private DirectoryModel drm;
	
	public CurveStitchDataPackage generateCsdpFromDrm (DirectoryModel drm){
		
		this.drm= drm;
		
		csdp = new CurveStitchDataPackage();
		
		OutputCurvesDataPackage ocdp = drm.getOcdp();
		
		int noOfDats = drm.getDatFilepaths().length;
		
		csdp.setFilepaths(drm.getDatFilepaths());
		
		ArrayList<ArrayList<Boolean>> goodPointLists = new ArrayList<>();
		
		for(int i=0; i< drm.getDatFilepaths().length; i++){
			goodPointLists.add(new ArrayList<>());
			
			int g = drm.getDmxList().get(i).size();
			
			for(int j =0; j<g ;j++){
				goodPointLists.get(i).add(true);
			}
		}
		
		ArrayList<Boolean> splicedGoodPointArray = new ArrayList<>();
 		
		for(FrameModel fm : drm.getFms()){
			
			int datNo = fm.getDatNo();
			int noInDatFile = fm.getNoInOriginalDat();
			boolean goodPoint = fm.isGoodPoint();
			
			goodPointLists.get(datNo).set(noInDatFile, goodPoint);
			
			splicedGoodPointArray.add(fm.isGoodPoint());
			
		}
		
		IDataset splicedGoodPointIDataset = DatasetFactory.createFromList(splicedGoodPointArray);
		csdp.setSplicedGoodPointIDataset(splicedGoodPointIDataset);
		
		IDataset[] goodPointDatArray =goodPointIDatasetArrayGenerator(noOfDats, 
																	  ocdp.getyListForEachDat());
		
		csdp.setGoodPointIDataset(goodPointDatArray);
		
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
		
		if(xIDataset.length !=
				yIDataset.length){
			System.out.println("error in lengths, in csdp generator");
		}
		
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
		
		zero.add(-10000000000.0);
		
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
				
				for(int u =0; u<=drm.getNoOfImagesInDatFile(r);u++ ){
					input.get(r).add(-10000000000.0);
				}
			}
		}
		ArrayList<ArrayList<Double>> outputC = new ArrayList<>();
		
		for (ArrayList<Double> w : input){
			
			ArrayList<Double> q =new ArrayList<>();
			
			for(Double c : w){
				q.add(c);
			}
			outputC.add(q);
		}
		
		ArrayList<Double> zero = new ArrayList<Double>();
		
		zero.add(-10000000000.0);
		
		for(ArrayList<Double> t : outputC){
			t.removeAll(zero);
		}
		
		
		int r =0;
		
		for(int s = 0; s<outputC.size(); s++){
			if(outputC.get(s)!=null && outputC.get(s).size()>0){
				r++;
			}
		}
		
		IDataset[] output = new IDataset[r];
		
		r=0;
		for(int u = 0; u<input.size(); u++){
			if(outputC.get(u)!=null && outputC.get(u).size()>0){
				IDataset yOut = DatasetFactory.createFromList(outputC.get(u));
				output[r] = yOut;
				r++;
			}
		}
		
		return output;
	}
	
	
	
	public  IDataset[] goodPointIDatasetArrayGenerator(int n, // number of Dats
			  											ArrayList<ArrayList<Double>> input){


		
		
		if (input==null){
			input = new ArrayList<ArrayList<Double>>();
		
			for(int r=0; r<n;r++){
				input.add(new ArrayList<Double>());
			
				for(int u =0; u<=drm.getNoOfImagesInDatFile(r);u++ ){
					input.get(r).add(-10000000000.0);
					
				}
			}
		}
		ArrayList<ArrayList<Double>> outputC = new ArrayList<>();
		
		for (ArrayList<Double> w : input){
			
			ArrayList<Double> q =new ArrayList<>();
			
			for(Double c : w){
				q.add(c);
			}
				outputC.add(q);
		}
		
		ArrayList<ArrayList<Boolean>> goodPointArrays = new ArrayList<>();
		
		for(int i = 0; i<n; i++){
			
			boolean containsGoodPoint  = false;
			
			for(double l: outputC.get(i)){
				if(l != -10000000000.0){
					containsGoodPoint = true;
				}
			}
			
			if(containsGoodPoint){
				ArrayList<Boolean> aq = new ArrayList<>();
							
				for(int j= 0; j< outputC.get(i).size(); j++){
					if(outputC.get(i).get(j) != -10000000000.0){
						for(FrameModel fm: drm.getFms()){
							if(fm.getDatNo() == i &&  fm.getNoInOriginalDat() == j){
								aq.add(fm.isGoodPoint());
							}
						}
					}
				}
					
				goodPointArrays.add(aq);
			}
		}
				
		IDataset[] output = new IDataset[goodPointArrays.size()];
		
		for(int y = 0; y<output.length; y++){
			
			output[y]=DatasetFactory.createFromList(goodPointArrays.get(y));
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
