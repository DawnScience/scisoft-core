package org.dawnsci.surfacescatter;

import java.util.Map;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Dataset;

public enum OverviewNexusObjectBuilderEnum {

	image_Tif_File_Path_Array(NeXusStructureStrings.getImageTifFilePathArray()[0],
			NeXusStructureStrings.getImageTifFilePathArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getImageTifFilePathArray()[0])[fm.getFmNo()] = fm
							.getTifFilePath(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory
							.createAttribute(NeXusStructureStrings.getImageTifFilePathArray()[1], fm.getTifFilePath())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setTifFilePath(getStringAttribute(NeXusStructureStrings.getImageTifFilePathArray()[1], nxData))),

	source_dat_File_Array(NeXusStructureStrings.getSourceDatFileArray()[0],
			NeXusStructureStrings.getSourceDatFileArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getSourceDatFileArray()[0])[fm.getFmNo()] = fm
							.getDatFilePath(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getSourceDatFileArray()[1], fm.getDatFilePath())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setDatFilePath(getStringAttribute(NeXusStructureStrings.getSourceDatFileArray()[1], nxData))),

	hArray(NeXusStructureStrings.getHarray()[0], NeXusStructureStrings.getHarray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getHarray()[0])[fm.getFmNo()] = fm.getH(),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getHarray()[1], fm.getH())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setH(getDoubleAttribute(NeXusStructureStrings.getHarray()[1], nxData))),

	kArray(NeXusStructureStrings.getKarray()[0], NeXusStructureStrings.getKarray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getKarray()[0])[fm.getFmNo()] = fm.getK(),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getKarray()[1], fm.getK())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setK(getDoubleAttribute(NeXusStructureStrings.getKarray()[1], nxData))),

	lArray(NeXusStructureStrings.getLarray()[0], NeXusStructureStrings.getLarray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getLarray()[0])[fm.getFmNo()] = fm.getL(),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLarray()[1], fm.getL())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setL(getDoubleAttribute(NeXusStructureStrings.getLarray()[1], nxData))),

	qArray(NeXusStructureStrings.getQarray()[0], NeXusStructureStrings.getQarray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getQarray()[0])[fm.getFmNo()] = fm.getQ(),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getQarray()[1], fm.getQ())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setQ(getDoubleAttribute(NeXusStructureStrings.getQarray()[1], nxData))),

	is_Good_Point_Array(NeXusStructureStrings.getIsGoodPointArray()[0], NeXusStructureStrings.getIsGoodPointArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getIsGoodPointArray()[0])[fm.getFmNo()] = String
							.valueOf(fm.isGoodPoint()),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getIsGoodPointArray()[1], String.valueOf(fm.isGoodPoint()))),
			(GroupNode nxData, FrameModel fm) -> fm
					.setGoodPoint(getBooleanAttribute(NeXusStructureStrings.getIsGoodPointArray()[1], nxData))),

	lorentzian_Correction_Array(NeXusStructureStrings.getLorentzianCorrectionArray()[0],
			NeXusStructureStrings.getLorentzianCorrectionArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getLorentzianCorrectionArray()[0])[fm.getFmNo()] = fm
							.getLorentzianCorrection(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getLorentzianCorrectionArray()[1], fm.getLorentzianCorrection())),
			(GroupNode nxData, FrameModel fm) -> fm.setLorentzianCorrection(
					getDoubleAttribute(NeXusStructureStrings.getLorentzianCorrectionArray()[1], nxData))),

	polarisation_Correction_Array(NeXusStructureStrings.getPolarisationCorrectionArray()[0],
			NeXusStructureStrings.getPolarisationCorrectionArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getPolarisationCorrectionArray()[0])[fm
							.getFmNo()] = fm.getPolarisationCorrection(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getPolarisationCorrectionArray()[1], fm.getPolarisationCorrection())),
			(GroupNode nxData, FrameModel fm) -> fm.setPolarisationCorrection(
					getDoubleAttribute(NeXusStructureStrings.getPolarisationCorrectionArray()[1], nxData))),

	area_Correction_Array(NeXusStructureStrings.getAreaCorrectionArray()[0],
			NeXusStructureStrings.getAreaCorrectionArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getAreaCorrectionArray()[0])[fm.getFmNo()] = fm
							.getAreaCorrection(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getAreaCorrectionArray()[1], fm.getAreaCorrection())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setAreaCorrection(getDoubleAttribute(NeXusStructureStrings.getAreaCorrectionArray()[1], nxData))),

	reflectivity_Area_Correction_Array(NeXusStructureStrings.getReflectivityAreaCorrectionArray()[0],
			NeXusStructureStrings.getReflectivityAreaCorrectionArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getReflectivityAreaCorrectionArray()[0])[fm
							.getFmNo()] = fm.getReflectivityAreaCorrection(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getReflectivityAreaCorrectionArray()[1], fm.getReflectivityAreaCorrection())),
			(GroupNode nxData, FrameModel fm) -> fm.setReflectivityAreaCorrection(
					getDoubleAttribute(NeXusStructureStrings.getReflectivityAreaCorrectionArray()[1], nxData))),

	fitPowers_Array(NeXusStructureStrings.getFitpowersArray()[0], NeXusStructureStrings.getFitpowersArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getFitpowersArray()[0])[fm
							.getFmNo()] = AnalaysisMethodologies.toString(fm.getFitPower()),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getFitpowersArray()[1], AnalaysisMethodologies.toString(fm.getFitPower()))),
			(GroupNode nxData, FrameModel fm) -> fm
					.setFitPower(getDoubleAttribute(NeXusStructureStrings.getFitpowersArray()[1], nxData))),

	roi_Location_Array(NeXusStructureStrings.getRoiLocationArray()[0], NeXusStructureStrings.getRoiLocationArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getRoiLocationArray()[0])[fm.getFmNo()] = fm
							.getRoiLocation(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getRoiLocationArray()[1], fm.getRoiLocation())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setRoiLocation(getDoubleArrayAttribute(NeXusStructureStrings.getRoiLocationArray()[1], nxData))),

	boundaryBox_Array(NeXusStructureStrings.getBoundaryboxArray()[0], NeXusStructureStrings.getBoundaryboxArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getBoundaryboxArray()[0])[fm.getFmNo()] = fm
							.getBoundaryBox(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getBoundaryboxArray()[1], fm.getBoundaryBox())),
			(GroupNode nxData, FrameModel fm) -> fm.setBoundaryBox(
					getDoubleAttribute(NeXusStructureStrings.getBoundaryboxArray()[1], nxData))),

	trackingMethod_Array(NeXusStructureStrings.getTrackingMethodArray()[0],
			NeXusStructureStrings.getTrackingMethodArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getTrackingMethodArray()[0])[fm
							.getFmNo()] = TrackingMethodology.toString(fm.getTrackingMethodology()),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getTrackingMethodArray()[1],
							TrackingMethodology.toString(fm.getTrackingMethodology()))),
					(GroupNode nxData, FrameModel fm) -> fm
					.setTrackingMethodology(getStringAttribute(NeXusStructureStrings.getTrackingMethodArray()[1], nxData))),


	backgroundMethod_Array(NeXusStructureStrings.getBackgroundMethodArray()[0],
			NeXusStructureStrings.getBackgroundMethodArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getBackgroundMethodArray()[0])[fm
							.getFmNo()] = AnalaysisMethodologies.toString(fm.getBackgroundMethdology()),
			(GroupNode nxData, FrameModel fm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getBackgroundMethodArray()[1],
							AnalaysisMethodologies.toString(fm.getBackgroundMethdology()))),
					(GroupNode nxData, FrameModel fm) -> fm
					.setBackgroundMethodology(getStringAttribute(NeXusStructureStrings.getBackgroundMethodArray()[1], nxData))),


	overlapping_Background_ROI_array(NeXusStructureStrings.getOverlappingBackgroundRoiArray()[0],
			NeXusStructureStrings.getOverlappingBackgroundRoiArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getOverlappingBackgroundRoiArray()[0])[fm
							.getFmNo()] = fm.getOverlapping_Background_ROI(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getOverlappingBackgroundRoiArray()[1],
							(double[]) fm.getOverlapping_Background_ROI())),
			(GroupNode nxData, FrameModel fm) -> fm
			.setOverlapping_Background_ROI(getDoubleArrayAttribute(NeXusStructureStrings.getOverlappingBackgroundRoiArray()[1], nxData))),


	static_Background_ROI_Array(NeXusStructureStrings.getStaticBackgroundRoiArray()[0],
			NeXusStructureStrings.getStaticBackgroundRoiArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getStaticBackgroundRoiArray()[0])[fm.getFmNo()] = fm
							.getStatic_Background_ROI(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getStaticBackgroundRoiArray()[1], (double[]) fm.getStatic_Background_ROI())),
			(GroupNode nxData, FrameModel fm) -> fm
			.setStatic_Background_ROI(getDoubleArrayAttribute(NeXusStructureStrings.getStaticBackgroundRoiArray()[1], nxData))),
	
	unspliced_Corrected_Intensity(NeXusStructureStrings.getUnsplicedCorrectedIntensityArray()[0],
			NeXusStructureStrings.getUnsplicedCorrectedIntensityArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedCorrectedIntensityArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Corrected_Intensity(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedCorrectedIntensityArray()[1], fm.getUnspliced_Corrected_Intensity())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Corrected_Intensity(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedCorrectedIntensityArray()[1], nxData))),

	unspliced_Corrected_Intensity_Error(NeXusStructureStrings.getUnsplicedCorrectedIntensityErrorArray()[0],
			NeXusStructureStrings.getUnsplicedCorrectedIntensityErrorArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedCorrectedIntensityErrorArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Corrected_Intensity_Error(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedCorrectedIntensityErrorArray()[1], fm.getUnspliced_Corrected_Intensity_Error())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Corrected_Intensity_Error(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedCorrectedIntensityErrorArray()[1], nxData))),
	
	unspliced_Raw_Intensity(NeXusStructureStrings.getUnsplicedRawIntensityArray()[0],
			NeXusStructureStrings.getUnsplicedRawIntensityArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedRawIntensityArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Raw_Intensity(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedRawIntensityArray()[1], fm.getUnspliced_Raw_Intensity())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Raw_Intensity(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedRawIntensityArray()[1], nxData))),

	unspliced_Raw_Intensity_Error(NeXusStructureStrings.getUnsplicedRawIntensityErrorArray()[0],
			NeXusStructureStrings.getUnsplicedRawIntensityErrorArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedRawIntensityErrorArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Raw_Intensity_Error(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedRawIntensityErrorArray()[1], fm.getUnspliced_Raw_Intensity_Error())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Raw_Intensity_Error(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedRawIntensityErrorArray()[1], nxData))),
	
	unspliced_Fhkl_Intensity(NeXusStructureStrings.getUnsplicedFhklIntensityArray()[0],
			NeXusStructureStrings.getUnsplicedFhklIntensityArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedFhklIntensityArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Fhkl_Intensity(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedFhklIntensityArray()[1], fm.getUnspliced_Fhkl_Intensity())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Fhkl_Intensity(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedFhklIntensityArray()[1], nxData))),

	unspliced_Fhkl_Intensity_Error(NeXusStructureStrings.getUnsplicedFhklIntensityErrorArray()[0],
			NeXusStructureStrings.getUnsplicedFhklIntensityErrorArray()[1],
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getUnsplicedFhklIntensityErrorArray()[0])[fm
							.getFmNo()] = fm.getUnspliced_Raw_Intensity_Error(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUnsplicedFhklIntensityErrorArray()[1], fm.getUnspliced_Fhkl_Intensity_Error())),
			(GroupNode nxData, FrameModel fm) -> fm.setUnspliced_Fhkl_Intensity_Error(
					getDoubleAttribute(NeXusStructureStrings.getUnsplicedFhklIntensityErrorArray()[1], nxData))),

	scanned_Variable(NeXusStructureStrings.getScannedVariable(),
			NeXusStructureStrings.getScannedVariable(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getScannedVariable())[fm
							.getFmNo()] = fm.getScannedVariable(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getScannedVariable(), fm.getScannedVariable())),
			(GroupNode nxData, FrameModel fm) -> fm.setScannedVariable(
					getDoubleAttribute(NeXusStructureStrings.getScannedVariable(), nxData))),
	
	number_In_Original_Dat(NeXusStructureStrings.getNumberInOriginalDat(),
			NeXusStructureStrings.getNumberInOriginalDat(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getNumberInOriginalDat())[fm
							.getFmNo()] = fm.getNoInOriginalDat(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getNumberInOriginalDat(), fm.getNoInOriginalDat())),
			(GroupNode nxData, FrameModel fm) -> fm.setNoInOriginalDat(
					getDoubleAttribute(NeXusStructureStrings.getNumberInOriginalDat(), nxData))),

	frame_number(NeXusStructureStrings.getFrameNumber(),
			NeXusStructureStrings.getFrameNumber(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getFrameNumber())[fm
							.getFmNo()] = fm.getFmNo(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getFrameNumber(), fm.getFmNo())),
			(GroupNode nxData, FrameModel fm) -> fm.setFmNo(
					getDoubleAttribute(NeXusStructureStrings.getFrameNumber(), nxData))),


	image_number(NeXusStructureStrings.getImageNumber(),
			NeXusStructureStrings.getImageNumber(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getImageNumber())[fm
							.getFmNo()] = fm.getImageNumber(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getImageNumber(), fm.getImageNumber())),
			(GroupNode nxData, FrameModel fm) -> fm.setImageNumber(
					getDoubleAttribute(NeXusStructureStrings.getImageNumber(), nxData))),

	reflectivity_flux_correction(NeXusStructureStrings.getReflectivityFluxCorrection(),
			NeXusStructureStrings.getReflectivityFluxCorrection(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getReflectivityFluxCorrection())[fm.getFmNo()] = fm.getReflectivityFluxCorrection(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getReflectivityFluxCorrection(), fm.getReflectivityFluxCorrection())),
			(GroupNode nxData, FrameModel fm) -> fm.setReflectivityFluxCorrection(
					getDoubleAttribute(NeXusStructureStrings.getReflectivityFluxCorrection(), nxData))),

	correction_selection(NeXusStructureStrings.getCorrectionSelection(),
			NeXusStructureStrings.getCorrectionSelection(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getCorrectionSelection())[fm.getFmNo()] = fm
							.getCorrectionSelection(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getCorrectionSelection(), fm.getCorrectionSelection())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setCorrectionSelection(getStringAttribute(NeXusStructureStrings.getCorrectionSelection(), nxData))),
	
	qdcd(NeXusStructureStrings.getQdcd(),
			NeXusStructureStrings.getQdcd(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getQdcd())[fm
							.getFmNo()] = fm.getQdcd(),
			(GroupNode nxData,
					FrameModel fm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getQdcd(), fm.getQdcd())),
			(GroupNode nxData, FrameModel fm) -> fm.setQdcd(
					getDoubleAttribute(NeXusStructureStrings.getQdcd(), nxData))),

	processing_method(NeXusStructureStrings.getProcessingMethod(),
			NeXusStructureStrings.getProcessingMethod(),
			(Map<String, Object[]> m,
					FrameModel fm) -> m.get(NeXusStructureStrings.getProcessingMethod())[fm.getFmNo()] = fm
							.getProcessingMethodSelection(),
			(GroupNode nxData, FrameModel fm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getProcessingMethod(), fm.getProcessingMethodSelection())),
			(GroupNode nxData, FrameModel fm) -> fm
					.setProcessingMethodSelection(getStringAttribute(NeXusStructureStrings.getProcessingMethod(), nxData))),
	
	
	;

	private String firstName;
	private String secondName;
	private fmExtract fe;
	private frameGroupNodePopulateFromFrameModel fg;
	private frameModelPopulateFromGroupNode fp;

	OverviewNexusObjectBuilderEnum(String a, String a2, fmExtract fe, frameGroupNodePopulateFromFrameModel fg,
			frameModelPopulateFromGroupNode fp) {

		this.firstName = a;
		this.secondName = a2;
		this.fe = fe;
		this.fg = fg;
		this.fp = fp;

	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public fmExtract getFmExtractMethod() {
		return fe;

	}

	public frameGroupNodePopulateFromFrameModel getFrameGroupNodePopulateFromFrameModelMethod() {
		return fg;

	}
	
	public frameModelPopulateFromGroupNode getFrameModelPopulateFromGroupNode() {
		
		return fp; 
	}
	
	public void frameModelPopulateFromGroupNodeMethod(GroupNode n, FrameModel fm) {
		
		fp.frameModelPopulateFromGroupNode1(n,fm); 
	}

	public void frameExtractionMethod(OverviewNexusObjectBuilderEnum o, Map<String, Object[]> m, FrameModel fm) {
		o.fe.fmExtract1(m, fm);
	}

	public void frameGroupNodePopulateFromFrameModelMethod(OverviewNexusObjectBuilderEnum o, GroupNode nxData,
			FrameModel fm) {
		o.fg.frameGroupNodePopulateFromFrameModel1(nxData, fm);
	}

	private static String getStringAttribute(String desired, GroupNode g) {
		StringDataset sd = DatasetUtils.cast(StringDataset.class, g.getAttribute(desired).getValue());
		return sd.get(0);
	}

	private static double getDoubleAttribute(String desired, GroupNode g) {
		DoubleDataset sd = DatasetUtils.cast(DoubleDataset.class, g.getAttribute(desired).getValue());
		return sd.get(0);
	}

	private static double[] getDoubleArrayAttribute(String desired, GroupNode g) {
		DoubleDataset sd = DatasetUtils.cast(DoubleDataset.class, g.getAttribute(desired).getValue());

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();
		
		return (double[])roiAttributeDat.getObject(0);
	}

	private static boolean getBooleanAttribute(String desired, GroupNode g) {
		BooleanDataset sd = DatasetUtils.cast(BooleanDataset.class, g.getAttribute(desired).getValue());
		return sd.get(0);
	}

	public String getFirstNameFromSecond(String in) {

		for (OverviewNexusObjectBuilderEnum o : OverviewNexusObjectBuilderEnum.values()) {
			if (in.equals(o.getSecondName())) {
				return o.firstName;
			}
		}

		return null;
	}

	public String getSecondNameFromFirst(String in) {

		for (OverviewNexusObjectBuilderEnum o : OverviewNexusObjectBuilderEnum.values()) {
			if (in.equals(o.getFirstName())) {
				return o.secondName;
			}
		}

		return null;
	}

	public OverviewNexusObjectBuilderEnum getFromFirstName(String in) {

		for (OverviewNexusObjectBuilderEnum o : OverviewNexusObjectBuilderEnum.values()) {
			if (in.equals(o.getFirstName())) {
				return o;
			}
		}

		return null;
	}

	public OverviewNexusObjectBuilderEnum getFromSecondName(String in) {

		for (OverviewNexusObjectBuilderEnum o : OverviewNexusObjectBuilderEnum.values()) {
			if (in.equals(o.getSecondName())) {
				return o;
			}
		}

		return null;
	}

	@FunctionalInterface
	public interface fmExtract {
		void fmExtract1(Map<String, Object[]> m, FrameModel fm);
	}

	@FunctionalInterface
	public interface frameGroupNodePopulateFromFrameModel {
		void frameGroupNodePopulateFromFrameModel1(GroupNode nxData, FrameModel fm);
	}

	@FunctionalInterface
	public interface frameModelPopulateFromGroupNode {
		void frameModelPopulateFromGroupNode1(GroupNode nxData, FrameModel fm);
	}

}
