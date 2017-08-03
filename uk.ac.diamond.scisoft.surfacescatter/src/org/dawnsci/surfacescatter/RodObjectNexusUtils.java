/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


// Package declaration


package org.dawnsci.surfacescatter;


// Imports from Java
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;


//Let's save this file.
public class RodObjectNexusUtils{


	private NexusFile nexusFileReference;

	public RodObjectNexusUtils(RodObjectNexusBuilderModel model){

		ArrayList<FrameModel> fms = model.getFms();
		GeometricParametersModel gm = model.getGm();
		DirectoryModel drm = model.getDrm();
		
		OutputCurvesDataPackage ocdp = drm.getOcdp();
		CurveStitchDataPackage csdp = drm.getCsdp();
		
		int noImages = fms.size();
		int p= 0;
		
//		ArrayList<GroupNode> nodes = new ArrayList<>();
		
		GroupNode entry = TreeFactory.createGroupNode(p);
		p++;
		
		for(int imageFilepathNo =0 ; imageFilepathNo < noImages ; imageFilepathNo++){
			
			FrameModel f = fms.get(imageFilepathNo);
	    	
			
			GroupNode nxData = TreeFactory.createGroupNode(p);

			nxData.addAttribute(TreeFactory.createAttribute("Image_Tif_File_Path", f.getTifFilePath()));
			nxData.addAttribute(TreeFactory.createAttribute("Source_Dat_File", f.getDatFilePath()));
			nxData.addAttribute(TreeFactory.createAttribute("h", f.getH()));
			nxData.addAttribute(TreeFactory.createAttribute("k", f.getK()));
			nxData.addAttribute(TreeFactory.createAttribute("l", f.getL()));
			
			nxData.addAttribute(TreeFactory.createAttribute("Lorentzian_Correction", f.getLorentzianCorrection()));
			nxData.addAttribute(TreeFactory.createAttribute("Polarisation_Correction", f.getPolarisationCorrection()));
			nxData.addAttribute(TreeFactory.createAttribute("Area_Correction", f.getAreaCorrection()));
			
			nxData.addAttribute(TreeFactory.createAttribute("Fhkl", csdp.getSplicedCurveYFhkl().getDouble(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Corrected_Intensity", csdp.getSplicedCurveY().getDouble(imageFilepathNo)));
			
			nxData.addAttribute(TreeFactory.createAttribute("ROI_Location", f.getRoiLocation()));
			
			//getting the background subtraction setup
//			int datNo = sm.getSortedDatIntsInOrderDataset().getInt(imageFilepathNo);
			
//			ExampleModel em = model.getModels().get(datNo);
			
			nxData.addAttribute(TreeFactory.createAttribute("Fit_Power", AnalaysisMethodologies.toString(f.getFitPower())));
			nxData.addAttribute(TreeFactory.createAttribute("Boundary_Box", f.getBoundaryBox()));
			nxData.addAttribute(TreeFactory.createAttribute("Tracker_Type", TrackingMethodology.toString(f.getTrackingMethodology())));
			nxData.addAttribute(TreeFactory.createAttribute("Background_Methodology", AnalaysisMethodologies.toString(f.getBackgroundMethdology())));
	
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Corrected_Intensity", ocdp.getyList().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Corrected_Intensity_Error", Maths.power(ocdp.getyList().get(imageFilepathNo), 0.5)));
			
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Raw_Intensity", ocdp.getyListRawIntensity().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Raw_Intensity_Error", Maths.power(ocdp.getyListRawIntensity().get(imageFilepathNo), 0.5)));
			
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Fhkl_Intensity", ocdp.getyListFhkl().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Fhkl_Intensity_Error", Maths.power(ocdp.getyListFhkl().get(imageFilepathNo),0.5)));
			

			if(f.getBackgroundMethdology() == Methodology.OVERLAPPING_BACKGROUND_BOX){
			
				int[] offsetLen = drm.getBoxOffsetLenPt()[0];
				int[] offsetPt = drm.getBoxOffsetLenPt()[1];
								
				double[] location = f.getRoiLocation();
				
				int[][] lenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
				int[] len = lenPt[0];
				int[] pt = lenPt[1];
				
				
				int pt0 = pt[0] + offsetPt[0];
				int pt1 = pt[1] + offsetPt[1];
				int[] backPt = new int[] {pt0, pt1}; 
				
				
				int len0 = len[0] + offsetLen[0];
				int len1 = len[1] + offsetLen[1];
				int[] backLen = new int[] {len0, len1}; 
			
				int[][] backLenPt = new int[][] {backLen, backPt};
				
				double[] backLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(backLenPt);
			
				nxData.addAttribute(TreeFactory.createAttribute("Overlapping_Background_ROI", backLocation));
				
			}

			else if(f.getBackgroundMethdology() == Methodology.SECOND_BACKGROUND_BOX){
		
				double[] staticBackground = LocationLenPtConverterUtils.lenPtToLocationConverter(drm.getBackgroundLenPt());
				
				nxData.addAttribute(TreeFactory.createAttribute("Static_Background_ROI", staticBackground));
				
			}
			
			p++;
			
			// Then we add the raw image
			DataNode rawImageDataNode = new DataNodeImpl(p);
			
			SliceND slice = new SliceND(f.getRawImageData().getShape());
			IDataset j = DatasetFactory.createFromObject(0);
			try {
				j = f.getRawImageData().getSlice(slice);
				rawImageDataNode.setDataset(j.squeeze());
				
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
			nxData.addDataNode("Raw_Image", rawImageDataNode);
			
			p++;

//	 		nodes.add(nxData);
			entry.addGroupNode("point_"+imageFilepathNo, nxData);
		}
		
		// Then we add the overall stitched rod intensities 
		DataNode fhklNode = new DataNodeImpl(p);
		SliceND slice00= new SliceND(csdp.getSplicedCurveYFhkl().getShape());
		
		IDataset splicedfhkl = csdp.getSplicedCurveYFhkl().getSlice(slice00);
		splicedfhkl.setErrors(csdp.getSplicedCurveYFhklError());
		
		fhklNode.setDataset(splicedfhkl);
		entry.addDataNode("Fhkl_Dataset", fhklNode);
		p++;
		
		DataNode fhklErrorNode = new DataNodeImpl(p);
		SliceND slice03= new SliceND(csdp.getSplicedCurveYFhklError().getShape());
		
		IDataset splicedFhklError = csdp.getSplicedCurveYFhklError().getSlice(slice03);
		
		fhklErrorNode.setDataset(splicedFhklError);
		entry.addDataNode("Fhkl_Error_Dataset", fhklErrorNode);
		p++;
		
		// Then we add the overall corrected rod intensities 
		DataNode correctedIntensityNode = new DataNodeImpl(p);
		
		SliceND slice01= new SliceND(csdp.getSplicedCurveY().getShape());
		
		IDataset splicedCorrected = csdp.getSplicedCurveY().getSlice(slice01);
		splicedCorrected.setErrors(csdp.getSplicedCurveYError());
		
		correctedIntensityNode.setDataset(splicedCorrected);
		entry.addDataNode("Corrected_Intensity_Dataset", correctedIntensityNode);
		p++;
		
		DataNode correctedIntensityErrorNode = new DataNodeImpl(p);
		SliceND slice04= new SliceND(csdp.getSplicedCurveYError().getShape());
		
		IDataset splicedCorrectedError = csdp.getSplicedCurveYError().getSlice(slice04);
		
		correctedIntensityErrorNode.setDataset(splicedCorrectedError);
		entry.addDataNode("Corrected_Intensity_Error_Dataset", correctedIntensityErrorNode);
		p++;
		
		
		// Then we add the overall raw rod intensities 
		DataNode rawIntensityNode = new DataNodeImpl(p);
		
		SliceND slice02= new SliceND(csdp.getSplicedCurveYRaw().getShape());
		
		IDataset splicedRaw = csdp.getSplicedCurveYRaw().getSlice(slice02);
		splicedRaw.setErrors(csdp.getSplicedCurveYRawError());
		
		rawIntensityNode.setDataset(splicedRaw);
		entry.addDataNode("Raw_Intensity_Dataset", rawIntensityNode);
		p++;
		
		
		DataNode rawIntensityErrorNode = new DataNodeImpl(p);
		SliceND slice05= new SliceND(csdp.getSplicedCurveYRawError().getShape());
		
		IDataset splicedCorrectedRawError = csdp.getSplicedCurveYRawError().getSlice(slice05);
		
		rawIntensityErrorNode.setDataset(splicedCorrectedRawError);
		entry.addDataNode("Raw_Intensity_Error_Dataset", rawIntensityErrorNode);
		p++;
		
		
		// Then we add the overall stitched rod scanned variables 
		DataNode scannedVariableNode = new DataNodeImpl(p);
		
		SliceND slice0= new SliceND(csdp.getSplicedCurveX().getShape());
		
		scannedVariableNode.setDataset(csdp.getSplicedCurveX().getSlice(slice0));
		entry.addDataNode("Scanned_Variable_Dataset", scannedVariableNode);
		
		scannedVariableNode.addAttribute(TreeFactory.createAttribute("Scanned_Variable_Name", gm.getxName()));
		p++;
		
		entry.addAttribute(TreeFactory.createAttribute("Rod Name", drm.getRodName()));
		
		GroupNode overlapRegions = TreeFactory.createGroupNode(p);
		p++;
		
		
		
		///// entering the geometrical model
		
		geometricalParameterReader(gm, entry);
		
		
		entry.addGroupNode("Overlap_Regions", overlapRegions);
		
		///Start creating the overlap region coding
		
		int overlapNumber =0;
		
		for(OverlapDataModel ovm :csdp.getOverlapDataModels()){
		
			if(!ovm.getLowerOverlapScannedValues().equals(null)){
				if(ovm.getLowerOverlapScannedValues().length > 0){
									
					GroupNode overlapData = TreeFactory.createGroupNode(p);
					p++;
					
					
					
					//lower overlap data
					
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_.Dat_Name", ovm.getLowerDatName()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Positions", ovm.getLowerOverlapPositions()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Scanned_Values", ovm.getLowerOverlapScannedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Corrected_Intensities", ovm.getLowerOverlapCorrectedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Raw_Intensities", ovm.getLowerOverlapRawValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fhkl_Values", ovm.getLowerOverlapFhklValues()));
					
					//	parameters for the quartic used to fit the lower overlap region
	
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Corrected", ovm.getLowerOverlapFitParametersCorrected()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Raw", ovm.getLowerOverlapFitParametersRaw()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Fhkl", ovm.getLowerOverlapFitParametersFhkl()));
					
					//upper overlap data
					
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_.Dat_Name", ovm.getUpperDatName()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Positions", ovm.getUpperOverlapPositions()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Scanned_Values", ovm.getUpperOverlapScannedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Corrected_Intensities", ovm.getUpperOverlapCorrectedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Raw_Intensities", ovm.getUpperOverlapRawValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fhkl_Values", ovm.getUpperOverlapFhklValues()));
					
					//	parameters for the quartic used to fit the upper overlap region
	
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Corrected", ovm.getUpperOverlapFitParametersCorrected()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Raw", ovm.getUpperOverlapFitParametersRaw()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Fhkl", ovm.getUpperOverlapFitParametersFhkl()));

					//attentuation factors

					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Corrected_Intensities", ovm.getAttenuationFactor()));
					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Fhkl_Intensities", ovm.getAttenuationFactorFhkl()));
					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Raw_Intensities", ovm.getAttenuationFactorRaw()));			
					
					overlapRegions.addGroupNode("Overlap_Region_" + overlapNumber, overlapData);
					
					overlapNumber++;
				}
			}
		}
		
		try {
			// Create the file and get ready to write

			File f = new File(model.getFilepath());

			if(f.exists()) { 
			
			    f.delete();
			}
			try{
				nexusFileReference = NexusFileHDF5.openNexusFile(model.getFilepath());
			}
			catch(Exception u){
				nexusFileReference = NexusFileHDF5.createNexusFile(model.getFilepath());
			}
			nexusFileReference.addNode("/", entry);

			nexusFileReference.close();
		} catch (NexusException nexusFileError) {
			try {
				nexusFileReference.close();
			} catch (NexusException closingerror) {
				System.out.println("This error occured when attempting to close the NeXus file: " + closingerror.getMessage());
				
			}
			System.out.println("This error occured when attempting to open the NeXus file: " + nexusFileError.getMessage());
		}
	}
	
	
	private void geometricalParameterReader(GeometricParametersModel gm, 
											GroupNode entry){
		
		Method[] methods = gm.getClass().getMethods();
		
		for(Method m : methods){
			
			String mName = m.getName();
			CharSequence s = "get";
			
			
			if(mName.contains(s) && !mName.equals("getClass")){
				
				String name = StringUtils.substringAfter(mName, "get");
//				Class c = m.getReturnType();
				
				
				try {
					entry.addAttribute(TreeFactory.createAttribute(name,String.valueOf(m.invoke(gm, (Object[]) null))));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					System.out.println("error for:   " + name);
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
	}
}
	
	
