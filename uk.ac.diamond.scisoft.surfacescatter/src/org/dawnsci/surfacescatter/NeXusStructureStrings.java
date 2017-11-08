package org.dawnsci.surfacescatter;

public class NeXusStructureStrings {

	private static final String ENTRY = "entry";
	private static final String RAW_IMAGES_DATASET = "Raw_Images_Dataset";
	private static final String REDUCED_DATA_DATASET = "Reduced_Data_Dataset";
	private static final String OVERVIEW_OF_FRAMES = "Overview_Of_Frames";
	private static final String ALIASES = "Aliases";
	private static final String PARAMETERS = "Parameters";

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

}