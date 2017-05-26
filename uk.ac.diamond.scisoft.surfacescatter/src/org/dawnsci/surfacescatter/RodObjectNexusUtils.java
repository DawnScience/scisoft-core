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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;

// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.export.Export1DNXcanSASModel;


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
		
//		IDataset[] hArray = new IDataset[noImages];
//	    IDataset[] kArray = new IDataset[noImages];
//	    IDataset[] lArray = new IDataset[noImages];
//	    
//	    for (int id = 0; id < noImages; id++) {
//	    
//	    	FrameModel f = fms.get(id);
//	    	
//	    	ILazyDataset h = SXRDGeometricCorrections.geth(models.get(id));
//			ILazyDataset k = SXRDGeometricCorrections.getk(models.get(id));
//			ILazyDataset l = SXRDGeometricCorrections.getl(models.get(id));
//			
//			hArray[id] = (IDataset) h;
//			kArray[id] = (IDataset) k;
//			lArray[id] = (IDataset) l;
//			
//	    }
//	    
//	    Dataset hArrayCon = DatasetUtils.concatenate(hArray, 0);
//	    Dataset kArrayCon = DatasetUtils.concatenate(kArray, 0);
//	    Dataset lArrayCon = DatasetUtils.concatenate(lArray, 0);	
//			
//	    hArrayCon.sort(0);
//	    kArrayCon.sort(0);
//	    lArrayCon.sort(0);
		
		ArrayList<GroupNode> nodes = new ArrayList<>();
		
//		int noIma
		
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
				
//				double[] location = drm.getLocationList().get(f.getDatNo()).get(f.getNoInOriginalDat());
				
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

	 		nodes.add(nxData);
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
		
		// Then we add the overall corrected rod intensities 
		DataNode correctedIntensityNode = new DataNodeImpl(p);
		
		SliceND slice01= new SliceND(csdp.getSplicedCurveY().getShape());
		
		IDataset splicedCorrected = csdp.getSplicedCurveY().getSlice(slice01);
		splicedCorrected.setErrors(csdp.getSplicedCurveYError());
		
		correctedIntensityNode.setDataset(splicedCorrected);
		entry.addDataNode("Corrected_Intensity_Dataset", correctedIntensityNode);
		p++;
		
		// Then we add the overall raw rod intensities 
		DataNode rawIntensityNode = new DataNodeImpl(p);
		
		SliceND slice02= new SliceND(csdp.getSplicedCurveYRaw().getShape());
		
		IDataset splicedRaw = csdp.getSplicedCurveYRaw().getSlice(slice02);
		splicedRaw.setErrors(csdp.getSplicedCurveYRawError());
		
		rawIntensityNode.setDataset(splicedRaw);
		entry.addDataNode("Raw_Intensity_Dataset", rawIntensityNode);
		p++;
		
		
		// Then we add the overall stitched rod scanned variables 
		DataNode scannedVariableNode = new DataNodeImpl(p);
		
		SliceND slice0= new SliceND(csdp.getSplicedCurveX().getShape());
		
		scannedVariableNode.setDataset(csdp.getSplicedCurveX().getSlice(slice0));
		entry.addDataNode("Scanned_Variable_Dataset", scannedVariableNode);
		
		scannedVariableNode.addAttribute(TreeFactory.createAttribute("Scanned_Variable_Name", gm.getxName()));
		p++;
		
		
		GroupNode overlapRegions = TreeFactory.createGroupNode(p);
		p++;
		
		entry.addGroupNode("Overlap_Regions", overlapRegions);
		
		///Start creating the overlap region coding
		
		int overlapNumber =0;
		
		for(OverlapDataModel ovm :drm.getOverlapDataModels()){
		
			if(!ovm.getLowerOverlapScannedValues().equals(null)){
				if(ovm.getLowerOverlapScannedValues().length > 0){
									
					GroupNode overlapData = TreeFactory.createGroupNode(p);
					p++;
					
					overlapRegions.addGroupNode("Overlap_Region_" + overlapNumber, overlapData);
					
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
}
	
	
