package org.dawnsci.surfacescatter;

public class NeXusStructureStrings {

	private static final String ENTRY = "entry";
	private static final String RAW_IMAGES_DATASET = "Raw_Images_Dataset";
	private static final String REDUCED_DATA_DATASET = "Reduced_Data_Dataset";
	private static final String OVERVIEW_OF_FRAMES = "Overview_Of_Frames";
	private static final String ALIASES = "Aliases";
	private static final String PARAMETERS = "Parameters";
	private static final String DIRECTORY_MODEL_PARAMETERS = "Directory Model Parameters";

	private static final String CORRECTED_INTENSITY_DATASET = "Corrected_Intensity_Dataset";
	private static final String RAW_INTENSITY_DATASET = "Raw_Intensity_Dataset";
	private static final String FHKL_DATASET = "Fhkl_Intensity_Dataset";

	private static final String CORRECTED_INTENSITY_DATASET_ERRORS = "Corrected_Intensity_Dataset_Errors";
	private static final String RAW_INTENSITY_DATASET_ERRORS = "Raw_Intensity_Dataset_Errors";
	private static final String FHKL_DATASET_ERRORS = "Fhkl_Intensity_Dataset_Errors";

	private static final String SCANNED_VARIABLE_DATASET = "Scanned_Variable_Dataset";
	private static final String TRACKER_ON = "Tracker On";

	private static final String[] image_Tif_File_Path_Array = new String[] { "image_Tif_File_Path_Array",
			"Image_Tif_File_Path" };

	private static final String[] source_dat_File_Array = new String[] { "source_dat_File_Array", "Source_Dat_File" };

	private static final String[] hArray = new String[] { "hArray", "h" };

	private static final String[] kArray = new String[] { "kArray", "k" };
	private static final String[] lArray = new String[] { "lArray", "l" };

	private static final String[] qArray = new String[] { "qArray", "q" };

	private static final String[] is_Good_Point_Array = new String[] { "is_Good_Point_Array", "Is Good Point" };

	private static final String[] lorentzian_Correction_Array = new String[] { "lorentzian_Correction_Array",
			"Lorentzian_Correction" };
	private static final String[] polarisation_Correction_Array = new String[] { "polarisation_Correction_Array",
			"Polarisation_Correction" };
	private static final String[] area_Correction_Array = new String[] { "area_Correction_Array", "Area_Correction" };
	private static final String[] reflectivity_Area_Correction_Array = new String[] {
			"reflectivity_Area_Correction_Array", "Reflectivity_Area_Correction" };

	private static final String[] roi_Location_Array = new String[] { "roi_Location_Array", "ROI_Location" };
	private static final String[] fitPowers_array = new String[] { "fitPowers_array", "Fit_Power" };

	private static final String[] boundaryBox_array = new String[] { "boundaryBox_array", "Boundary_Box" };
	private static final String[] tracking_Method_array = new String[] { "tracking_Method_array", "Tracker_Type" };
	private static final String[] background_Method_array = new String[] { "background_Method_array",
			"Background_Methodology" };

	private static final String[] unspliced_Corrected_Intensity_Array = new String[] {
			"unspliced_Corrected_Intensity_Array", "Unspliced_Corrected_Intensity" };

	private static final String[] unspliced_Corrected_Intensity_Error_Array = new String[] {
			"unspliced_Corrected_Intensity_Error_Array", "Unspliced_Corrected_Intensity_Error" };

	private static final String[] unspliced_Raw_Intensity_Array = new String[] { "unspliced_Raw_Intensity_Array",
			"Unspliced_Raw_Intensity" };
	private static final String[] unspliced_Raw_Intensity_Error_Array = new String[] {
			"unspliced_Raw_Intensity_Error_Array", "Unspliced_Raw_Intensity_Error" };

	private static final String[] unspliced_Fhkl_Intensity_Array = new String[] { "unspliced_Fhkl_Intensity_Array",
			"Unspliced_Fhkl_Intensity" };

	private static final String[] unspliced_Fhkl_Intensity_Error_Array = new String[] {
			"unspliced_Fhkl_Intensity_Error_Array", "Unspliced_Fhkl_Intensity_Error" };

	private static final String[] overlapping_Background_ROI_array = new String[] { "overlapping_Background_ROI_array",
			"Overlapping_Background_ROI" };

	private static final String[] static_Background_ROI_Array = new String[] { "static_Background_ROI_Array",
			"Static_Background_ROI" };

	private static final String SCANNED_VARIABLE = "Scanned_Variable";

	private static final String NUMBER_IN_ORIGINAL_DAT = "Number_In_Original_Dat_File";

	private static final String FRAME_NUMBER = "Frame_Number";

	private static final String DAT_NUMBER = "Dat_File_Number";

	private static final String IMAGE_NUMBER = "Image_Number";

	private static final String CORRECTION_SELECTION = "Correction_Selection";

	private static final String reflectivity_Flux_Correction = "Reflectivity_Flux_Correction";

	private static final String qdcd = "qdcd";

	private static final String RAW_IMAGE = "Raw_Image";

	private static final String BACKGROUND_SUBTRACTED_IMAGE = "Background_Subtracted_Image";

	private static final String PROCESSING_METHOD = "Processing_Method";

	private static final String POINT = "point_";

	private static final String initialLenPt = "Initial Length/Position";

	private static final String trackerCoordinates = "Tracking Coordinates";

	private static final String initialTrackerCoordinates = "Coordinates for Tracker Initialisation";

	private static final String initialDatasetForEachDat = "Datasets For Tracker Training in Each Dat";

	private static final String lenPtForEachDat = "Length/Position Data For Tracker Training In Each Dat";

	private static final String sortedTheta = "Sorted Theta Values";

	private static final String interpolatedLenPts = "Interpolated Length/Position";
	
	private static final String temporaryBackgroundHolder = "Temporary Background For Background Subtraction";
	
	private static final String permanentBoxOffsetLenPt = "Box Offset For Tracker";
	
	private static final String permanentBackgroundLenPt = "Fixed Position Background Length/Position For Tracker";
	
	private static final String boxOffsetLenPt = "Offset for Overlap";
	
	private static final String backgroundROI = "Background Region Of Interest";
	
	private static final String backgroundROIArray = "Array of Background Regions Of Interest";
	
	private static final String backgroundLenPt = "Length/Position Data for background";
	
	private static final String seedLocation = "Tracker Seed Location";
	
	private static final String trackerLocationList = "List of Tracker Locations";
	
	private static final String interpolatorBoxes = "Interpolator Set Positions";
	
	private static final String rodName = "Rod Name";
	
	private static final String setPositions = "Set Positions";
	
	private static final String interpolatorRegions = "Regions of Interest Computed By Interpolation";
	
	private static final String setRegions = "Manually Set Regions of Interest For Interpolation";
	
	private static final String INTEGERS = "Integers";
	
	private static final String DATA_PACKAGE_FOR_OVERLAP_CALCULATION = "Data Package For Overlap Calculation";

	private static final String YLIST = "yList";
	
	private static final String YLIST_ERROR = "yList_Error";
	
	private static final String YLIST_FHKL = "yList_Fhkl";
	
	private static final String YLIST_FHKL_ERROR = "yList_Fhkl_Error";
	
	private static final String YLIST_RAW = "yList_Raw";
	
	private static final String YLIST_RAW_ERROR = "yList_Raw_Error";
	
	private static final String YLIST_FOR_EACH_DAT = "yList_For_Each_Dat";
	
	private static final String YLIST_ERROR_FOR_EACH_DAT = "yList_Error_Each_Dat";
	
	private static final String YLIST_FHKL_FOR_EACH_DAT = "yList_Fhkl_Each_Dat";
	
	private static final String YLIST_FHKL_ERROR_FOR_EACH_DAT = "yList_Fhkl_Error_Each_Dat";
	
	private static final String YLIST_RAW_FOR_EACH_DAT = "yList_Raw_Each_Dat";
	
	private static final String YLIST_RAW_ERROR_FOR_EACH_DAT = "yList_Raw_Error_Each_Dat";
	
	private static final String OUTPUT_DATA_ARRAY = "Output_Data_Array";
	
	private static final String BACKGROUND_DATA_ARRAY = "Background_Data_Array";
	
	private static final String NO_DATS = "Number_of_Dat_Files";
	
	public static String getNoDats() {
		return NO_DATS;
	}

	public static String getOutputDataArray() {
		return OUTPUT_DATA_ARRAY;
	}

	public static String getBackgroundDataArray() {
		return BACKGROUND_DATA_ARRAY;
	}

	
	public static String getDataPackageForOverlapCalculation() {
		return DATA_PACKAGE_FOR_OVERLAP_CALCULATION;
	}

	public static String getYlist() {
		return YLIST;
	}

	public static String getYlistError() {
		return YLIST_ERROR;
	}

	public static String getYlistFhkl() {
		return YLIST_FHKL;
	}

	public static String getYlistFhklError() {
		return YLIST_FHKL_ERROR;
	}

	public static String getYlistRaw() {
		return YLIST_RAW;
	}

	public static String getYlistRawError() {
		return YLIST_RAW_ERROR;
	}

	public static String getYlistForEachDat() {
		return YLIST_FOR_EACH_DAT;
	}

	public static String getYlistErrorForEachDat() {
		return YLIST_ERROR_FOR_EACH_DAT;
	}

	public static String getYlistFhklForEachDat() {
		return YLIST_FHKL_FOR_EACH_DAT;
	}

	public static String getYlistFhklErrorForEachDat() {
		return YLIST_FHKL_ERROR_FOR_EACH_DAT;
	}

	public static String getYlistRawForEachDat() {
		return YLIST_RAW_FOR_EACH_DAT;
	}

	public static String getYlistRawErrorForEachDat() {
		return YLIST_RAW_ERROR_FOR_EACH_DAT;
	}

	
	public static String getInitiallenpt() {
		return initialLenPt;
	}

	public static String getTrackercoordinates() {
		return trackerCoordinates;
	}

	public static String getInitialtrackercoordinates() {
		return initialTrackerCoordinates;
	}

	public static String getInitialdatasetforeachdat() {
		return initialDatasetForEachDat;
	}

	public static String getLenptforeachdat() {
		return lenPtForEachDat;
	}

	public static String getSortedtheta() {
		return sortedTheta;
	}

	public static String getInterpolatedlenpts() {
		return interpolatedLenPts;
	}

	public static String getTemporarybackgroundholder() {
		return temporaryBackgroundHolder;
	}

	public static String getPermanentboxoffsetlenpt() {
		return permanentBoxOffsetLenPt;
	}

	public static String getPermanentbackgroundlenpt() {
		return permanentBackgroundLenPt;
	}

	public static String getBoxoffsetlenpt() {
		return boxOffsetLenPt;
	}

	public static String getBackgroundroi() {
		return backgroundROI;
	}

	public static String getBackgroundroiarray() {
		return backgroundROIArray;
	}

	public static String getBackgroundlenpt() {
		return backgroundLenPt;
	}

	public static String getSeedlocation() {
		return seedLocation;
	}

	public static String getTrackerlocationlist() {
		return trackerLocationList;
	}

	public static String getInterpolatorboxes() {
		return interpolatorBoxes;
	}

	public static String getRodname() {
		return rodName;
	}

	public static String getSetpositions() {
		return setPositions;
	}
	
	public static String getImageNumber() {
		return IMAGE_NUMBER;
	}

	public static String getNumberInOriginalDat() {
		return NUMBER_IN_ORIGINAL_DAT;
	}

	public static String getFrameNumber() {
		return FRAME_NUMBER;
	}

	public static String getDatNumber() {
		return DAT_NUMBER;
	}

	public static String getTrackerOn() {
		return TRACKER_ON;
	}

	public static String getScannedVariableDataset() {
		return SCANNED_VARIABLE_DATASET;
	}

	public static String getEntry() {
		return ENTRY;
	}

	public static String getRawImagesDataset() {
		return RAW_IMAGES_DATASET;
	}

	public static String getReducedDataDataset() {
		return REDUCED_DATA_DATASET;
	}

	public static String getAliases() {
		return ALIASES;
	}

	public static String getParameters() {
		return PARAMETERS;
	}

	public static String getCorrectedIntensityDataset() {
		return CORRECTED_INTENSITY_DATASET;
	}

	public static String getRawIntensityDataset() {
		return RAW_INTENSITY_DATASET;
	}

	public static String getFhklDataset() {
		return FHKL_DATASET;
	}

	public static String getCorrectedIntensityDatasetErrors() {
		return CORRECTED_INTENSITY_DATASET_ERRORS;
	}

	public static String getRawIntensityDatasetErrors() {
		return RAW_INTENSITY_DATASET_ERRORS;
	}

	public static String getFhklDatasetErrors() {
		return FHKL_DATASET_ERRORS;
	}

	public static String[] getImageTifFilePathArray() {
		return image_Tif_File_Path_Array;
	}

	public static String[] getSourceDatFileArray() {
		return source_dat_File_Array;
	}

	public static String[] getHarray() {
		return hArray;
	}

	public static String[] getKarray() {
		return kArray;
	}

	public static String[] getLarray() {
		return lArray;
	}

	public static String[] getQarray() {
		return qArray;
	}

	public static String[] getIsGoodPointArray() {
		return is_Good_Point_Array;
	}

	public static String[] getLorentzianCorrectionArray() {
		return lorentzian_Correction_Array;
	}

	public static String[] getPolarisationCorrectionArray() {
		return polarisation_Correction_Array;
	}

	public static String[] getAreaCorrectionArray() {
		return area_Correction_Array;
	}

	public static String[] getReflectivityAreaCorrectionArray() {
		return reflectivity_Area_Correction_Array;
	}

	public static String[] getRoiLocationArray() {
		return roi_Location_Array;
	}

	public static String[] getFitpowersArray() {
		return fitPowers_array;
	}

	public static String[] getBoundaryboxArray() {
		return boundaryBox_array;
	}

	public static String[] getTrackingMethodArray() {
		return tracking_Method_array;
	}

	public static String[] getBackgroundMethodArray() {
		return background_Method_array;
	}

	public static String[] getUnsplicedCorrectedIntensityArray() {
		return unspliced_Corrected_Intensity_Array;
	}

	public static String[] getUnsplicedCorrectedIntensityErrorArray() {
		return unspliced_Corrected_Intensity_Error_Array;
	}

	public static String[] getUnsplicedRawIntensityArray() {
		return unspliced_Raw_Intensity_Array;
	}

	public static String[] getUnsplicedRawIntensityErrorArray() {
		return unspliced_Raw_Intensity_Error_Array;
	}

	public static String[] getUnsplicedFhklIntensityArray() {
		return unspliced_Fhkl_Intensity_Array;
	}

	public static String[] getUnsplicedFhklIntensityErrorArray() {
		return unspliced_Fhkl_Intensity_Error_Array;
	}

	public static String[] getOverlappingBackgroundRoiArray() {
		return overlapping_Background_ROI_array;
	}

	public static String[] getStaticBackgroundRoiArray() {
		return static_Background_ROI_Array;
	}

	public static String getOverviewOfFrames() {
		return OVERVIEW_OF_FRAMES;
	}

	public static String getScannedVariable() {
		return SCANNED_VARIABLE;
	}

	public static String getReflectivityFluxCorrection() {
		return reflectivity_Flux_Correction;
	}

	public static String getCorrectionSelection() {
		return CORRECTION_SELECTION;
	}

	public static String getQdcd() {
		return qdcd;
	}

	public static String getRawImage() {
		return RAW_IMAGE;
	}

	public static String getBackgroundSubtractedImage() {
		return BACKGROUND_SUBTRACTED_IMAGE;
	}

	public static String getProcessingMethod() {
		return PROCESSING_METHOD;
	}

	public static String getPoint() {
		return POINT;
	}

	public static String getInterpolatorregions() {
		return interpolatorRegions;
	}

	public static String getSetregions() {
		return setRegions;
	}

	public static String getDirectoryModelParameters() {
		return DIRECTORY_MODEL_PARAMETERS;
	}

	public static String getIntegers() {
		return INTEGERS;
	}

}
