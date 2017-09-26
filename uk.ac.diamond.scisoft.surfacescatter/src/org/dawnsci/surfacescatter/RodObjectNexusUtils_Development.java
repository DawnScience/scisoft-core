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
import org.eclipse.dawnsci.analysis.tree.impl.AttributeImpl;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

//Let's save this file.
public class RodObjectNexusUtils_Development {

	public static void RodObjectNexusUtils(RodObjectNexusBuilderModel model) {

		ArrayList<FrameModel> fms = model.getFms();
		GeometricParametersModel gm = model.getGm();
		DirectoryModel drm = model.getDrm();

		IDataset[] rawImageArray = new IDataset[fms.size()];

		OutputCurvesDataPackage ocdp = drm.getOcdp();
		CurveStitchDataPackage csdp = drm.getCsdp();

		int noImages = fms.size();
		int p = 0;

		GroupNode entry = TreeFactory.createGroupNode(p);
		p++;

		entry.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXentry"));
		//

		for (int imageFilepathNo = 0; imageFilepathNo < noImages; imageFilepathNo++) {

			FrameModel fm = fms.get(imageFilepathNo);

			GroupNode nxData = TreeFactory.createGroupNode(p);

			nxData.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXsubentry"));

			nxData.addAttribute(TreeFactory.createAttribute("Image_Tif_File_Path", fm.getTifFilePath()));
			nxData.addAttribute(TreeFactory.createAttribute("Source_Dat_File", fm.getDatFilePath()));

			nxData.addAttribute(TreeFactory.createAttribute("h", fm.getH()));
			nxData.addAttribute(TreeFactory.createAttribute("k", fm.getK()));
			nxData.addAttribute(TreeFactory.createAttribute("l", fm.getL()));

			nxData.addAttribute(TreeFactory.createAttribute("q", fm.getQ()));

			nxData.addAttribute(TreeFactory.createAttribute("Is Good Point", String.valueOf(fm.isGoodPoint())));

			nxData.addAttribute(TreeFactory.createAttribute("Lorentzian_Correction", fm.getLorentzianCorrection()));
			nxData.addAttribute(TreeFactory.createAttribute("Polarisation_Correction", fm.getPolarisationCorrection()));
			nxData.addAttribute(TreeFactory.createAttribute("Area_Correction", fm.getAreaCorrection()));

			nxData.addAttribute(
					TreeFactory.createAttribute("Reflectivity_Area_Correction", fm.getReflectivityAreaCorrection()));
			nxData.addAttribute(TreeFactory.createAttribute("Area_Correction", fm.getAreaCorrection()));

			nxData.addAttribute(
					TreeFactory.createAttribute("Fhkl", csdp.getSplicedCurveYFhkl().getDouble(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Corrected_Intensity",
					csdp.getSplicedCurveY().getDouble(imageFilepathNo)));

			nxData.addAttribute(TreeFactory.createAttribute("ROI_Location", fm.getRoiLocation()));

			nxData.addAttribute(
					TreeFactory.createAttribute("Fit_Power", AnalaysisMethodologies.toString(fm.getFitPower())));
			nxData.addAttribute(TreeFactory.createAttribute("Boundary_Box", fm.getBoundaryBox()));
			nxData.addAttribute(TreeFactory.createAttribute("Tracker_Type",
					TrackingMethodology.toString(fm.getTrackingMethodology())));
			nxData.addAttribute(TreeFactory.createAttribute("Background_Methodology",
					AnalaysisMethodologies.toString(fm.getBackgroundMethdology())));

			nxData.addAttribute(
					TreeFactory.createAttribute("Unspliced_Corrected_Intensity", ocdp.getyList().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Corrected_Intensity_Error",
					Maths.power(ocdp.getyList().get(imageFilepathNo), 0.5)));

			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Raw_Intensity",
					ocdp.getyListRawIntensity().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Raw_Intensity_Error",
					Maths.power(ocdp.getyListRawIntensity().get(imageFilepathNo), 0.5)));

			nxData.addAttribute(
					TreeFactory.createAttribute("Unspliced_Fhkl_Intensity", ocdp.getyListFhkl().get(imageFilepathNo)));
			nxData.addAttribute(TreeFactory.createAttribute("Unspliced_Fhkl_Intensity_Error",
					Maths.power(ocdp.getyListFhkl().get(imageFilepathNo), 0.5)));

			if (fm.getBackgroundMethdology() == Methodology.OVERLAPPING_BACKGROUND_BOX) {

				int[] offsetLen = drm.getBoxOffsetLenPt()[0];
				int[] offsetPt = drm.getBoxOffsetLenPt()[1];

				double[] location = fm.getRoiLocation();

				int[][] lenPt = LocationLenPtConverterUtils.locationToLenPtConverter(location);
				int[] len = lenPt[0];
				int[] pt = lenPt[1];

				int pt0 = pt[0] + offsetPt[0];
				int pt1 = pt[1] + offsetPt[1];
				int[] backPt = new int[] { pt0, pt1 };

				int len0 = len[0] + offsetLen[0];
				int len1 = len[1] + offsetLen[1];
				int[] backLen = new int[] { len0, len1 };

				int[][] backLenPt = new int[][] { backLen, backPt };

				double[] backLocation = LocationLenPtConverterUtils.lenPtToLocationConverter(backLenPt);

				nxData.addAttribute(TreeFactory.createAttribute("Overlapping_Background_ROI", backLocation));

			}

			else if (fm.getBackgroundMethdology() == Methodology.SECOND_BACKGROUND_BOX) {

				double[] staticBackground = LocationLenPtConverterUtils
						.lenPtToLocationConverter(drm.getBackgroundLenPt());

				nxData.addAttribute(TreeFactory.createAttribute("Static_Background_ROI", staticBackground));

			}

			p++;

			// Then we add the raw image
			DataNode rawImageDataNode = new DataNodeImpl(p);

			SliceND slice = new SliceND(fm.getRawImageData().getShape());
			IDataset j = DatasetFactory.createFromObject(0);
			try {
				j = fm.getRawImageData().getSlice(slice);
				rawImageArray[imageFilepathNo] = j;
				rawImageDataNode.setDataset(j.clone().squeeze());
			} catch (DatasetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			nxData.addDataNode("Raw_Image", rawImageDataNode);

			p++;

			try {

				nxData.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, NexusTreeUtils.NX_SAMPLE));

				entry.addGroupNode("point_" + imageFilepathNo, nxData);
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
		}

		SliceND slice0 = new SliceND(csdp.getSplicedCurveX().getShape());

		Dataset rawImageConcat = DatasetUtils.concatenate(rawImageArray, 0);

		GroupNode overlapRegions = TreeFactory.createGroupNode(p);
		p++;

		///// entering the geometrical model

		geometricalParameterWriter(gm, (long) p, drm, entry);

		p++;

		angleAliasWriter((long) p, drm, entry);

		p++;

		entry.addGroupNode("Overlap_Regions", overlapRegions);

		/// Start creating the overlap region coding

		int overlapNumber = 0;

		for (OverlapDataModel ovm : csdp.getOverlapDataModels()) {

			if (!ovm.getLowerOverlapScannedValues().equals(null)) {
				if (ovm.getLowerOverlapScannedValues().length > 0) {

					GroupNode overlapData = TreeFactory.createGroupNode(p);
					p++;

					// lower overlap data

					overlapData.addAttribute(TreeFactory.createAttribute("Lower_.Dat_Name", ovm.getLowerDatName()));
					overlapData.addAttribute(
							TreeFactory.createAttribute("Lower_Overlap_Positions", ovm.getLowerOverlapPositions()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Scanned_Values",
							ovm.getLowerOverlapScannedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Corrected_Intensities",
							ovm.getLowerOverlapCorrectedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Raw_Intensities",
							ovm.getLowerOverlapRawValues()));
					overlapData.addAttribute(
							TreeFactory.createAttribute("Lower_Overlap_Fhkl_Values", ovm.getLowerOverlapFhklValues()));

					// parameters for the quartic used to fit the lower overlap
					// region

					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Corrected",
							ovm.getLowerOverlapFitParametersCorrected()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Raw",
							ovm.getLowerOverlapFitParametersRaw()));
					overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fit_Parameters_Fhkl",
							ovm.getLowerOverlapFitParametersFhkl()));

					// upper overlap data

					overlapData.addAttribute(TreeFactory.createAttribute("Upper_.Dat_Name", ovm.getUpperDatName()));
					overlapData.addAttribute(
							TreeFactory.createAttribute("Upper_Overlap_Positions", ovm.getUpperOverlapPositions()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Scanned_Values",
							ovm.getUpperOverlapScannedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Corrected_Intensities",
							ovm.getUpperOverlapCorrectedValues()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Raw_Intensities",
							ovm.getUpperOverlapRawValues()));
					overlapData.addAttribute(
							TreeFactory.createAttribute("Upper_Overlap_Fhkl_Values", ovm.getUpperOverlapFhklValues()));

					// parameters for the quartic used to fit the upper overlap
					// region

					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Corrected",
							ovm.getUpperOverlapFitParametersCorrected()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Raw",
							ovm.getUpperOverlapFitParametersRaw()));
					overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Fhkl",
							ovm.getUpperOverlapFitParametersFhkl()));

					// attentuation factors

					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Corrected_Intensities",
							ovm.getAttenuationFactor()));
					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Fhkl_Intensities",
							ovm.getAttenuationFactorFhkl()));
					overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Raw_Intensities",
							ovm.getAttenuationFactorRaw()));

					overlapRegions.addGroupNode("Overlap_Region_" + overlapNumber, overlapData);

					overlapNumber++;
				}
			}
		}

		File f = new File(model.getFilepath());

		if (f.exists()) {
			f.delete();
			f = new File(model.getFilepath());
		}

		try {
			NexusFile nexusFileReference = NexusFileHDF5.createNexusFile(model.getFilepath());

			nexusFileReference.addNode("/entry", entry);

			nexusFileReference.createData("/entry/Raw_Images_Dataset/", "rawImagesDataset", rawImageConcat, true);
			nexusFileReference.createData("/entry/Raw_Images_Dataset/", gm.getxName(),
					csdp.getSplicedCurveX().getSlice(slice0), true);

			String[] axesArray = new String[2];

			ArrayList<String> axes = new ArrayList<>();
			axes.add(gm.getxName());

			GroupNode group2 = nexusFileReference.getGroup("/entry/Raw_Images_Dataset", true);

			nexusFileReference.addAttribute(group2, new AttributeImpl("signal", "rawImagesDataset"));

			Dataset integers = DatasetFactory.createLinearSpace(IntegerDataset.class, (double) 0, (double) fms.size(),
					fms.size());
			axes.add("integers");

			nexusFileReference.createData("/entry/Raw_Images_Dataset/", "integers", integers, true);
			try {
				nexusFileReference.createData("/entry/Raw_Images_Dataset/", "q",
						csdp.getSplicedCurveQ().getSlice(slice0), true);
				axes.add("q");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			axes.toArray(axesArray);

			nexusFileReference.addAttribute(group2, new AttributeImpl("axes", axesArray));

			SliceND slice00 = new SliceND(csdp.getSplicedCurveYFhkl().getShape());
			IDataset splicedfhkl = csdp.getSplicedCurveYFhkl().getSlice(slice00);
			splicedfhkl.setErrors(csdp.getSplicedCurveYFhklError());

			nexusFileReference.createData("/entry/Reduced_Data_Dataset/", "Fhkl_Dataset", splicedfhkl, true);

			SliceND slice01 = new SliceND(csdp.getSplicedCurveY().getShape());
			IDataset splicedCorrected = csdp.getSplicedCurveY().getSlice(slice01);
			splicedCorrected.setErrors(csdp.getSplicedCurveYError());

			nexusFileReference.createData("/entry/Reduced_Data_Dataset/", "Corrected_Intensity_Dataset",
					splicedCorrected, true);

			SliceND slice02 = new SliceND(csdp.getSplicedCurveYRaw().getShape());
			IDataset splicedRaw = csdp.getSplicedCurveYRaw().getSlice(slice02);
			splicedRaw.setErrors(csdp.getSplicedCurveYRawError());

			nexusFileReference.createData("/entry/Reduced_Data_Dataset/", "Raw_Intensity_Dataset", splicedCorrected,
					true);

			nexusFileReference.createData("/entry/Reduced_Data_Dataset/", gm.getxName(),
					csdp.getSplicedCurveX().getSlice(slice0), true);
			nexusFileReference.createData("/entry/Reduced_Data_Dataset/", "integers", integers, true);
			try {
				nexusFileReference.createData("/entry/Reduced_Data_Dataset/", "q",
						csdp.getSplicedCurveQ().getSlice(slice0), true);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			GroupNode group = nexusFileReference.getGroup("/entry/Reduced_Data_Dataset", true);

			nexusFileReference.addAttribute(group, new AttributeImpl("axes", axesArray));
			nexusFileReference.addAttribute(group, new AttributeImpl("signal", "Corrected_Intensity_Dataset"));

			GroupNode group1 = nexusFileReference.getGroup("/entry/Raw_Images_Dataset", true);

			nexusFileReference.addAttribute(group, new AttributeImpl("NX_class", "NXdata"));
			nexusFileReference.addAttribute(group1, new AttributeImpl("NX_class", "NXdata"));

			nexusFileReference.close();
		} catch (NexusException e) {

			System.out.println("This error occured when attempting to close the NeXus file: " + e.getMessage());
		}

	}

	private static void geometricalParameterWriter(GeometricParametersModel gm,
			// GroupNode entry,
			long oid, DirectoryModel drm,
			// NexusFile nexusFile
			GroupNode entry) {

		GroupNode parameters = TreeFactory.createGroupNode(oid);

		Method[] methods = gm.getClass().getMethods();

		for (Method m : methods) {

			String mName = m.getName();
			CharSequence s = "get";

			if (mName.contains(s) && !mName.equals("getClass")) {

				String name = StringUtils.substringAfter(mName, "get");

				try {
					parameters.addAttribute(
							TreeFactory.createAttribute(name, String.valueOf(m.invoke(gm, (Object[]) null))));

				} catch (IllegalAccessException e) {
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (InvocationTargetException e) {
					System.out.println(e.getMessage());
				}
				
			}
		}

		parameters.addAttribute(TreeFactory.createAttribute("Rod Name", drm.getRodName()));

//		parameters.addAttribute(TreeFactory.createAttribute("Rod Name", drm.getRodName()));
		
		parameters.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		entry.addGroupNode("Parameters", parameters);
	}

	private static void angleAliasWriter(long oid, DirectoryModel drm, GroupNode entry) {

		GroupNode alias = TreeFactory.createGroupNode(oid);

		for (SXRDAngleAliasEnum m : SXRDAngleAliasEnum.values()) {

			if(m != SXRDAngleAliasEnum.NULL){
				alias.addAttribute(TreeFactory.createAttribute(m.getAngleVariable(), m.getAngleAlias()));
			}
		}

		for (ReflectivityAngleAliasEnum r : ReflectivityAngleAliasEnum.values()) {
			
			if(r != ReflectivityAngleAliasEnum.NULL){
				alias.addAttribute(TreeFactory.createAttribute(r.getAngleVariable(), r.getAngleAlias()));
			}
		}

		for (ReflectivityFluxParametersAliasEnum f : ReflectivityFluxParametersAliasEnum.values()) {

			if(f != ReflectivityFluxParametersAliasEnum.NULL){
				alias.addAttribute(TreeFactory.createAttribute(f.getFluxVariable(), f.getFluxAlias()));
			}
		}

		alias.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		entry.addGroupNode("Aliases", alias);
	}

}
