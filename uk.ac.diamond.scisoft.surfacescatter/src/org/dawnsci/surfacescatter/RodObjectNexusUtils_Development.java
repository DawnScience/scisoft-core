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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
import org.eclipse.january.dataset.SliceND;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

public class RodObjectNexusUtils_Development {

	private static NexusFile nexusFileReference;

	public static void RodObjectNexusUtils(RodObjectNexusBuilderModel model) throws NexusException {

		ArrayList<FrameModel> fms = model.getFms();
		GeometricParametersModel gm = model.getGm();
		DirectoryModel drm = model.getDrm();

		IDataset[] rawImageArray = new IDataset[fms.size()];
		IDataset[] backgroundSubtractedImageArray = new IDataset[fms.size()];

		// OutputCurvesDataPackage ocdp = drm.getOcdp();
		CurveStitchDataPackage csdp = drm.getCsdp();

		int noImages = fms.size();
		int p = 0;

		GroupNode entry = TreeFactory.createGroupNode(p);
		p++;

		entry.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXentry"));

		//

		Map<String, Object[]> m = new HashMap<String, Object[]>();

		for (OverviewNexusObjectBuilderEnum oe : OverviewNexusObjectBuilderEnum.values()) {
			m.put(oe.getFirstName(), new Object[noImages]);
		}

		for (int imageFilepathNo = 0; imageFilepathNo < noImages; imageFilepathNo++) {

			FrameModel fm = fms.get(imageFilepathNo);

			int[][] submitLenPt = new int[2][];

			if (fm.getBackgroundMethdology() == Methodology.OVERLAPPING_BACKGROUND_BOX) {

				submitLenPt = drm.getBoxOffsetLenPt();

			}

			else if (fm.getBackgroundMethdology() == Methodology.SECOND_BACKGROUND_BOX) {

				submitLenPt = drm.getBackgroundLenPt();

			}

			GroupNode nxData = framePointWriter(fm, p, submitLenPt, rawImageArray, backgroundSubtractedImageArray, m);

			try {
				nxData.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXcollection"));

				entry.addGroupNode("point_" + imageFilepathNo, nxData);
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
		}

		SliceND slice0 = new SliceND(csdp.getSplicedCurveX().getShape());

		///// entering the geometrical model

		geometricalParameterWriter(gm, (long) p, drm, entry);

		p++;
		
		directoryModelGroupWriter((long) p, drm, entry);

		p++;

		overlapCurvesDataPackageGroupWriter((long) p, drm.getOcdp(), entry);
		
		p++;
		
		angleAliasWriter((long) p, entry);

		p++;
		//////// overview writer

		overviewGroupWriter((long) p, m, entry);
		p++;
		////////////

		GroupNode overlapRegions = TreeFactory.createGroupNode(p);
		p++;

		entry.addGroupNode("Overlap_Regions", overlapRegions);

		entry.getGroupNode("Overlap_Regions")
				.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXcollection"));
		/// Start creating the overlap region coding

		int overlapNumber = 0;
		try {
			for (OverlapDataModel ovm : csdp.getOverlapDataModels()) {

				if (!ovm.getLowerOverlapScannedValues().equals(null)) {
					if (ovm.getLowerOverlapScannedValues().length > 0) {

						GroupNode overlapData = TreeFactory.createGroupNode(p);

						p++;

						overlapData.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXsubentry"));
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
						overlapData.addAttribute(TreeFactory.createAttribute("Lower_Overlap_Fhkl_Values",
								ovm.getLowerOverlapFhklValues()));

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
						overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fhkl_Values",
								ovm.getUpperOverlapFhklValues()));

						// parameters for the quartic used to fit the upper overlap
						// region

						overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Corrected",
								ovm.getUpperOverlapFitParametersCorrected()));
						overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Raw",
								ovm.getUpperOverlapFitParametersRaw()));
						overlapData.addAttribute(TreeFactory.createAttribute("Upper_Overlap_Fit_Parameters_Fhkl",
								ovm.getUpperOverlapFitParametersFhkl()));

						// attentuation factors

						overlapData.addAttribute(TreeFactory.createAttribute(
								"Attenuation_Factor_For_Corrected_Intensities", ovm.getAttenuationFactor()));
						overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Fhkl_Intensities",
								ovm.getAttenuationFactorFhkl()));
						overlapData.addAttribute(TreeFactory.createAttribute("Attenuation_Factor_For_Raw_Intensities",
								ovm.getAttenuationFactorRaw()));

						overlapRegions.addGroupNode("Overlap_Region_" + overlapNumber, overlapData);

						overlapNumber++;

					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		File f = new File(model.getFilepath());

		if (f.exists()) {
			f.delete();
			f = new File(model.getFilepath());
		}

		try {
			nexusFileReference = NexusFileHDF5.createNexusFile(model.getFilepath());

			final String entryString = "/" + NeXusStructureStrings.getEntry();
			final String rawImagesString = entryString + "/" + NeXusStructureStrings.getRawImagesDataset() + "/";
			final String reducedDataString = entryString + "/" + NeXusStructureStrings.getReducedDataDataset() + "/";

			try {
				nexusFileReference.addNode(entryString, entry);
			} catch (Exception ui) {
				System.out.println(ui.getMessage());
			}

			Dataset rawImageConcat = retryConcat(rawImageArray, model.getNoRods(), 0);

			nexusFileReference.createData(rawImagesString, "rawImagesDataset", rawImageConcat, true);
			nexusFileReference.createData(rawImagesString, gm.getxName(), csdp.getSplicedCurveX().getSlice(slice0),
					true);

			String[] axesArray = new String[3];

			ArrayList<String> axes = new ArrayList<>();

			axes.add(gm.getxName());

			GroupNode group2 = nexusFileReference.getGroup(rawImagesString, true);

			nexusFileReference.addAttribute(group2, new AttributeImpl("signal", "rawImagesDataset"));

			Dataset integers = DatasetFactory.createLinearSpace(IntegerDataset.class, (double) 0, (double) fms.size(),
					fms.size());

			axes.add(NeXusStructureStrings.getIntegers());

			nexusFileReference.createData(rawImagesString, NeXusStructureStrings.getIntegers(), integers, true);

			try {
				nexusFileReference.createData(rawImagesString, "q", csdp.getSplicedCurveQ().getSlice(slice0), true);
				axes.add("q");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			axes.toArray(axesArray);

			nexusFileReference.addAttribute(group2, new AttributeImpl("axes", axesArray));

			SliceND slice00 = new SliceND(csdp.getSplicedCurveYFhkl().getShape());

			IDataset splicedFhkl = csdp.getSplicedCurveYFhkl().getSlice(slice00);
			splicedFhkl.setErrors(csdp.getSplicedCurveYFhklError());

			IDataset splicedFhklErrors = csdp.getSplicedCurveYFhklError().getSlice(slice00);

			String fhkl_Dataset = NeXusStructureStrings.getFhklDataset();

			nexusFileReference.createData(reducedDataString, fhkl_Dataset, splicedFhkl, true);

			String fhkl_Dataset_Errors = NeXusStructureStrings.getFhklDatasetErrors();

			nexusFileReference.createData(reducedDataString, fhkl_Dataset_Errors, splicedFhklErrors, true);

			SliceND slice01 = new SliceND(csdp.getSplicedCurveY().getShape());
			IDataset splicedCorrected = csdp.getSplicedCurveY().getSlice(slice01);
			splicedCorrected.setErrors(csdp.getSplicedCurveYError());

			IDataset splicedCorrectedErrors = csdp.getSplicedCurveYError().getSlice(slice00);

			String corrected_Intensity_Dataset = NeXusStructureStrings.getCorrectedIntensityDataset();

			nexusFileReference.createData(reducedDataString, corrected_Intensity_Dataset, splicedCorrected, true);

			String corrected_Dataset_Errors = NeXusStructureStrings.getCorrectedIntensityDatasetErrors();

			nexusFileReference.createData(reducedDataString, corrected_Dataset_Errors, splicedCorrectedErrors, true);

			SliceND slice02 = new SliceND(csdp.getSplicedCurveYRaw().getShape());
			IDataset splicedRaw = csdp.getSplicedCurveYRaw().getSlice(slice02);
			splicedRaw.setErrors(csdp.getSplicedCurveYRawError());

			IDataset splicedRawErrors = csdp.getSplicedCurveYRawError().getSlice(slice00);

			String raw_Intensity_Dataset = NeXusStructureStrings.getRawIntensityDataset();

			nexusFileReference.createData(reducedDataString, raw_Intensity_Dataset, splicedCorrected, true);

			String raw_Dataset_Errors = NeXusStructureStrings.getRawIntensityDatasetErrors();

			nexusFileReference.createData(reducedDataString, raw_Dataset_Errors, splicedRawErrors, true);

			nexusFileReference.createData(reducedDataString, gm.getxName(), csdp.getSplicedCurveX().getSlice(slice0),
					true);

			nexusFileReference.createData(reducedDataString, NeXusStructureStrings.getIntegers(), integers, true);
			try {
				nexusFileReference.createData(reducedDataString, "q", csdp.getSplicedCurveQ().getSlice(slice0), true);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			Dataset scannedVarStr = DatasetFactory.createFromObject(gm.getxName());

			nexusFileReference.createData(reducedDataString, NeXusStructureStrings.getScannedVariableDataset(),
					scannedVarStr, true);

			GroupNode group = nexusFileReference.getGroup(rawImagesString, true);

			nexusFileReference.addAttribute(group, new AttributeImpl("axes", axesArray));

			GroupNode group1 = nexusFileReference.getGroup(reducedDataString, true);

			nexusFileReference.addAttribute(group, new AttributeImpl("NX_class", "NXdata"));
			nexusFileReference.addAttribute(group1, new AttributeImpl("NX_class", "NXdata"));
			nexusFileReference.addAttribute(group1, new AttributeImpl("signal", "Corrected_Intensity_Dataset"));
			nexusFileReference.addAttribute(group1, new AttributeImpl("axes", axesArray));

		} catch (NexusException e) {

			System.out.println("This error occured when attempting to close the NeXus file: " + e.getMessage());
		} finally {
			if (nexusFileReference != null) {
				nexusFileReference.close();
			} else {

			}
		}

	}

	private static void geometricalParameterWriter(GeometricParametersModel gm,
			long oid, DirectoryModel drm,
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

		parameters.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		parameters.addAttribute(TreeFactory.createAttribute("Tracker On", String.valueOf(drm.isTrackerOn())));

		entry.addGroupNode(NeXusStructureStrings.getParameters(), parameters);
	}

	private static GroupNode framePointWriter(FrameModel fm, int p, int[][] backgroundLenPt, IDataset[] rawImageArray,
			IDataset[] backgroundSubtractedImageArray, Map<String, Object[]> m) {

		GroupNode nxData = TreeFactory.createGroupNode(p);

		nxData.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXsubentry"));

		if (fm.getBackgroundMethdology() == Methodology.OVERLAPPING_BACKGROUND_BOX) {

			int[] offsetLen = backgroundLenPt[0];
			int[] offsetPt = backgroundLenPt[1];

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

			fm.setOverlapping_Background_ROI(backLocation);

		}

		else if (fm.getBackgroundMethdology() == Methodology.SECOND_BACKGROUND_BOX) {

			double[] staticBackground = LocationLenPtConverterUtils.lenPtToLocationConverter(backgroundLenPt);

			fm.setStatic_Background_ROI(staticBackground);

		}

		for (OverviewNexusObjectBuilderEnum oe : OverviewNexusObjectBuilderEnum.values()) {
			try {
				oe.frameGroupNodePopulateFromFrameModelMethod(oe, nxData, fm);
			} catch (Exception j) {
				System.out.println(j.getMessage());
			}
			try {
				oe.frameExtractionMethod(oe, m, fm);
			} catch (Exception ji) {
				System.out.println(ji.getMessage());
			}
		}

		// Then we add the raw image
		DataNode rawImageDataNode = new DataNodeImpl(0);

		SliceND slice = new SliceND(fm.getRawImageData().getShape());

		try {
			IDataset j = fm.getRawImageData().getSlice(slice);
			rawImageArray[fm.getFmNo()] = j;
			rawImageDataNode.setDataset(j.clone().squeeze());
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		nxData.addDataNode(NeXusStructureStrings.getRawImage(), rawImageDataNode);

		DataNode backgroundSubtractedImageDataNode = new DataNodeImpl(1);

		SliceND slice1 = new SliceND(fm.getBackgroundSubtractedImage().getShape());
		IDataset jb = DatasetFactory.createFromObject(0);
		try {
			jb = fm.getBackgroundSubtractedImage().getSlice(slice1);
			backgroundSubtractedImageArray[fm.getFmNo()] = jb;
			backgroundSubtractedImageDataNode.setDataset(jb.clone().squeeze());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		nxData.addDataNode(NeXusStructureStrings.getBackgroundSubtractedImage(), backgroundSubtractedImageDataNode);

		return nxData;

	}

	private static void angleAliasWriter(long oid, GroupNode entry) {

		GroupNode alias = TreeFactory.createGroupNode(oid);

		for (SXRDAngleAliasEnum m : SXRDAngleAliasEnum.values()) {

			if (m != SXRDAngleAliasEnum.NULL) {
				alias.addAttribute(TreeFactory.createAttribute(m.getAngleVariable(), m.getAngleAlias()));
			}
		}

		for (ReflectivityAngleAliasEnum r : ReflectivityAngleAliasEnum.values()) {

			if (r != ReflectivityAngleAliasEnum.NULL) {
				alias.addAttribute(TreeFactory.createAttribute(r.getAngleVariable(), r.getAngleAlias()));
			}
		}

		for (ReflectivityFluxParametersAliasEnum f : ReflectivityFluxParametersAliasEnum.values()) {

			if (f != ReflectivityFluxParametersAliasEnum.NULL) {
				alias.addAttribute(TreeFactory.createAttribute(f.getFluxVariable(), f.getFluxAlias()));
			}
		}

		alias.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		entry.addGroupNode(NeXusStructureStrings.getAliases(), alias);
	}

	private static void overviewGroupWriter(long oid, Map<String, Object[]> m, GroupNode entry) {

		GroupNode overview = TreeFactory.createGroupNode(oid);

		for (OverviewNexusObjectBuilderEnum oe : OverviewNexusObjectBuilderEnum.values()) {

			String name = oe.getSecondName();

			try {
				Object[] s = m.get(oe.getFirstName());

				switch (s[0].getClass().getName()) {

				case "java.lang.String":
					// if(oe !=OverviewNexusObjectBuilderEnum.image_Tif_File_Path_Array) {
					String[] out0 = new String[s.length];
					shortArrayBuilder(out0, s, name, overview);
					// }
					break;
				case "java.lang.Integer":
					Integer[] out1 = new Integer[s.length];
					shortArrayBuilder(out1, s, name, overview);
					break;
				case "java.lang.Double":
					Double[] out2 = new Double[s.length];
					shortArrayBuilder(out2, s, name, overview);
					break;
				case "[D":
					shortArrayBuilder(s, name, overview);
					break;
				case "java.lang.Boolean":
					Boolean[] out4 = new Boolean[s.length];
					shortArrayBuilder(out4, s, name, overview);
					break;
				default:
					// defensive
					break;
				}

			} catch (Exception hj) {
				System.out.println(hj.getMessage() + oe.getFirstName());
			}
		}

		overview.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		try {
			entry.addGroupNode(NeXusStructureStrings.getOverviewOfFrames(), overview);
		} catch (Exception vb) {
			System.out.println(vb.getMessage());
		}

	}

	private static void directoryModelGroupWriter(long oid, DirectoryModel drm, GroupNode entry) {

		GroupNode drmGroup = TreeFactory.createGroupNode(oid);

		for (DirectoryModelNodeEnum dmne : DirectoryModelNodeEnum.values()) {

			try {
				dmne.directoryGroupNodePopulateFromDirectoryModelMethod(drmGroup, drm);			
			} catch (Exception j) {
				System.out.println(j.getMessage() + "  DirectoryModelNodeEnum name:  " + dmne.getFirstName());
			}
		}

		DataNode backgroundSubtractedImageDataNode = new DataNodeImpl(1);

		SliceND slice1 = new SliceND(drm.getTemporaryBackgroundHolder().getShape());
		IDataset jb = DatasetFactory.createFromObject(0);
		try {
			jb = drm.getTemporaryBackgroundHolder().getSlice(slice1);
			backgroundSubtractedImageDataNode.setDataset(jb.clone().squeeze());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		drmGroup.addDataNode(NeXusStructureStrings.getTemporarybackgroundholder(), backgroundSubtractedImageDataNode);

		
		
		
		drmGroup.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));

		try {
			entry.addGroupNode(NeXusStructureStrings.getDirectoryModelParameters(), drmGroup);
		} catch (Exception vb) {
			System.out.println(vb.getMessage());
		}
		
	}
	
	

	private static void overlapCurvesDataPackageGroupWriter(long oid, OutputCurvesDataPackage ocdp, GroupNode entry) {

		GroupNode ocdpGroup = TreeFactory.createGroupNode(oid);

		for (OutputCurvesDataPackageEnum dmne : OutputCurvesDataPackageEnum.values()) {

			try {
				dmne.ocdpGroupNodePopulateFromOutputCurvesDataPackageMethod(ocdpGroup, ocdp);			
			} catch (Exception j) {
				System.out.println(j.getMessage() + "  OutputCurvesDataPackageEnum name:  " + dmne.getFirstName());
			}
		}

	
		ocdpGroup.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, "NXparameters"));		
		
		try {
			entry.addGroupNode(NeXusStructureStrings.getDataPackageForOverlapCalculation(), ocdpGroup);
		} catch (Exception vb) {
			System.out.println(vb.getMessage());
		}
		
	}

	private static void shortArrayBuilder(Object[] out, Object[] in, String name, GroupNode overview) {

		for (int w = 0; w < in.length; w++) {
			out[w] = in[w];
		}

		overview.addAttribute(TreeFactory.createAttribute(name, out));
	}

	private static void shortArrayBuilder(String[] out, Object[] in, String name, GroupNode overview) {

		for (int w = 0; w < in.length; w++) {
			out[w] = (String) in[w];
		}

		Dataset r = DatasetFactory.createFromObject(0);

		try {
			r = DatasetFactory.createFromObject(out);
		} catch (Exception y) {
			System.err.println(y.getMessage());
		}

		overview.addAttribute(TreeFactory.createAttribute(name, r));
	}

	private static void shortArrayBuilder(Object[] in, String name, GroupNode overview) {

		double[][] o1 = new double[in.length][];

		for (int w = 0; w < in.length; w++) {

			try {
				Object h = in[w];
				double[] d = (double[]) h;
				o1[w] = d;

			} catch (Exception u) {
				System.out.println(u.getMessage());
			}

		}

		overview.addAttribute(TreeFactory.createAttribute(name, o1));
	}

	private static Dataset retryConcat(IDataset[] rawImageArray, int cutOff, int flag) {

		IDataset[] rawImageArray1 = rawImageArray.clone();

		Dataset rawImageConcat = DatasetFactory.createFromObject(0);

		try {
			
				return DatasetUtils.concatenate(rawImageArray1, 0);

			
		} catch (Exception e) {

			try {
				if ((new Random()).nextInt(10) >= 5 && flag < cutOff + 1) {
					Thread.sleep((new Random()).nextInt(10000) + 1000);
					flag++;
					System.out.println("flag:  " + flag);

					return DatasetUtils.concatenate(rawImageArray1, 0);

				}
				if (flag >= cutOff + 1) {
					return DatasetUtils.concatenate(rawImageArray1, 0);

				}
			} catch (InterruptedException f) {
			}

			retryConcat(rawImageArray1, cutOff, flag);
		}

		return rawImageConcat;
	}
	
	private static Dataset localConcatenate(IDataset[] in, int dim) {

		for (IDataset i : in) {

			if (i == null) {
				return null;
			}

			if (i.getSize() == 0) {
				return null;
			}
		}

		return DatasetUtils.convertToDataset(DatasetUtils.concatenate(in, dim));
	}
}
