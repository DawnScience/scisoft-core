package org.dawnsci.surfacescatter;

public class NeXusStructureStrings {

	private static final String ENTRY = "entry";
	private static final String RAW_IMAGES_DATASET = "Raw_Images_Dataset";
	private static final String REDUCED_DATA_DATASET = "Reduced_Data_Dataset";
	private static final String ALIASES = "Aliases";
	private static final String PARAMETERS = "Parameters";
	
	private static final String CORRECTED_INTENSITY_DATASET = "Corrected_Intensity_Dataset";
	private static final String RAW_INTENSITY_DATASET = "Raw_Intensity_Dataset";
	private static final String FHKL_DATASET = "Fhkl_Intensity_Dataset";
	
	private static final String CORRECTED_INTENSITY_DATASET_ERRORS = "Corrected_Intensity_Dataset_Errors";
	private static final String RAW_INTENSITY_DATASET_ERRORS = "Raw_Intensity_Dataset_Errors";
	private static final String FHKL_DATASET_ERRORS = "Fhkl_Intensity_Dataset_Errors";

	private static final String SCANNED_VARIABLE_DATASET = "Scanned_Variable_Dataset";
	
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
}
