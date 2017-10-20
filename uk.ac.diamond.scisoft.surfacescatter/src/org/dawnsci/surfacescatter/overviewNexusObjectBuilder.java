package org.dawnsci.surfacescatter;

public class overviewNexusObjectBuilder {

	private String[] image_Tif_File_Path_Array;
	private String[] source_dat_File_Array;

	private double[] hArray;
	private double[] kArray;
	private double[] lArray;

	private double[] qArray;

	private boolean[] is_Good_Point_Array;

	private double[] lorentzian_Correction_Array;
	private double[] polarisation_Correction_Array;
	private double[] area_Correction_Array;
	private double[] reflectivity_Area_Correction_Array;

	private double[][] roi_Location_Array;
	private String[] fitPowers_array;

	private int[] boundaryBox_array;
	private String[] tracking_Method_array;
	private String[] background_Method_array;

	private double[] unspliced_Corrected_Intensity_Array;
	private double[] unspliced_Corrected_Intensity_Error_Array;

	private double[] unspliced_Raw_Intensity_Array;
	private double[] unspliced_Raw_Intensity_Error_Array;

	private double[] unspliced_Fhkl_Intensity_Array;
	private double[] unspliced_Fhkl_Intensity_Error_Array;

	private double[][] overlapping_Background_ROI_array;
	private double[][] static_Background_ROI_Array;

	public overviewNexusObjectBuilder(int noImages) {

		image_Tif_File_Path_Array = new String[noImages];
		source_dat_File_Array = new String[noImages];

		hArray = new double[noImages];
		kArray = new double[noImages];
		lArray = new double[noImages];

		qArray = new double[noImages];

		is_Good_Point_Array = new boolean[noImages];

		lorentzian_Correction_Array = new double[noImages];
		polarisation_Correction_Array = new double[noImages];
		area_Correction_Array = new double[noImages];
		reflectivity_Area_Correction_Array = new double[noImages];

		roi_Location_Array = new double[noImages][];
		fitPowers_array = new String[noImages];

		boundaryBox_array = new int[noImages];
		tracking_Method_array = new String[noImages];
		background_Method_array = new String[noImages];

		unspliced_Corrected_Intensity_Array = new double[noImages];
		unspliced_Corrected_Intensity_Error_Array = new double[noImages];

		unspliced_Raw_Intensity_Array = new double[noImages];
		unspliced_Raw_Intensity_Error_Array = new double[noImages];

		unspliced_Fhkl_Intensity_Array = new double[noImages];
		unspliced_Fhkl_Intensity_Error_Array = new double[noImages];

		overlapping_Background_ROI_array = new double[noImages][];
		static_Background_ROI_Array = new double[noImages][];

	}

	public void addToImage_Tif_File_Path_Array(int n, String s) {
		image_Tif_File_Path_Array[n] = s;

	}

	public void addToSource_dat_File_Array(int n, String s) {
		source_dat_File_Array[n] = s;

	}

	public void addToHArray(int n, double s) {
		hArray[n] = s;

	}

	public void addToKArray(int n, double s) {
		kArray[n] = s;

	}

	public void addToLArray(int n, double s) {
		lArray[n] = s;

	}

	public void addToQArray(int n, double s) {
		qArray[n] = s;

	}

	public void addToIs_Good_Point_Array(int n, boolean noImages) {
		is_Good_Point_Array[n] = noImages;

	}

	public void addToLorentzian_Correction_Array(int n, double m) {
		lorentzian_Correction_Array[n] = m;

	}

	public void addToPolarisation_Correction_Array(int n, double m) {
		polarisation_Correction_Array[n] = m;
	}

	public void addToArea_Correction_Array(int n, double m) {
		area_Correction_Array[n] = m;
	}

	public void addToReflectivity_Area_Correction_Array(int n, double m) {
		reflectivity_Area_Correction_Array[n] = m;
	}

	public void addToRoi_Location_Array(int n, double[] m) {
		roi_Location_Array[n] = m;
	}

	public void addToFitPowers_array(int n, String m) {
		fitPowers_array[n] = m;
	}

	public void addToBoundaryBox_array(int n, int m) {
		boundaryBox_array[n] = m;
	}

	public void addToTracking_Method_array(int n, String m) {
		tracking_Method_array[n] = m;
	}

	public void addToBackground_Method_array(int n, String m) {
		background_Method_array[n] = m;
	}

	public void addToUnspliced_Corrected_Intensity_Array(int n, double m) {
		unspliced_Corrected_Intensity_Array[n] = m;
	}

	public void addToUnspliced_Corrected_Intensity_Error_Array(int n, double m) {
		unspliced_Corrected_Intensity_Error_Array[n] = m;
	}

	public void addToUnspliced_Raw_Intensity_Array(int n, double m) {
		unspliced_Raw_Intensity_Array[n] = m;
	}

	public void addToUnspliced_Raw_Intensity_Error_Array(int n, double m) {
		unspliced_Raw_Intensity_Error_Array[n] = m;
	}

	public void addToUnspliced_Fhkl_Intensity_Array(int n, double m) {
		unspliced_Fhkl_Intensity_Array[n] = m;
	}

	public void addToUnspliced_Fhkl_Intensity_Error_Array(int n, double m) {
		unspliced_Fhkl_Intensity_Error_Array[n] = m;
	}

	public void addToOverlapping_Background_ROI_array(int n, double[] m) {
		overlapping_Background_ROI_array[n] = m;
	}

	public void addToStatic_Background_ROI_Array(int n, double[] m) {
		static_Background_ROI_Array[n] = m;
	}

	
	
	
}
