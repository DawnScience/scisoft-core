package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.dawnsci.surfacescatter.ProcessingMethodsEnum.ProccessingMethod;
import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

public class FrameModel {

	//reciprocal space location
	
	private double h;
	private double k;
	private double l;
	
	private double scannedVariable;
	
	//number as read in
	
	private int imageNumber;
			
	//file location

	private String tifFilePath;
	private String datFilePath;
	private int datNo;
			
	///physical corrections
	
	
	private double lorentzianCorrection;
	private double polarisationCorrection;
	private double areaCorrection;
	private MethodSetting correctionSelection = MethodSetting.SXRD;
	
	///background subtraction parameters
	
	private double[] roiLocation;
	private FitPower fitPower;
	private int boundaryBox;
	private TrackerType1 trackingMethodology  = TrackerType1.TLD;
	private Methodology backgroundMethodology;
	private double[] overlapping_Background_ROI;
	private double[] static_Background_ROI;
	private ProccessingMethod processingMethodSelection;
	
	//raw image
	private ILazyDataset rawImageData;
	
	
	//results
	private double unspliced_Corrected_Intensity;			
	private double unspliced_Corrected_Intensity_Error;
					
	private double unspliced_Raw_Intensity;
	private double unspliced_Raw_Intensity_Error;
					
	private double unspliced_Fhkl_Intensity;
	private double unspliced_Fhkl_Intensity_Error;
					
	private IDataset backgroundSubtractedImage; 
	
	private int noInOriginalDat;
	
	
	public int getNoInOriginalDat() {
		return noInOriginalDat;
	}
	public void setNoInOriginalDat(int noInOriginalDat) {
		this.noInOriginalDat = noInOriginalDat;
	}
	public Methodology getBackgroundMethodology() {
		return backgroundMethodology;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public double getL() {
		return l;
	}
	public void setL(double l) {
		this.l = l;
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public String getTifFilePath() {
		return tifFilePath;
	}
	public void setTifFilePath(String tifFilePath) {
		this.tifFilePath = tifFilePath;
	}
	public String getDatFilePath() {
		return datFilePath;
	}
	public void setDatFilePath(String datFilePath) {
		this.datFilePath = datFilePath;
	}
	public double getLorentzianCorrection() {
		return lorentzianCorrection;
	}
	public void setLorentzianCorrection(double lorentzianCorrection) {
		this.lorentzianCorrection = lorentzianCorrection;
	}
	public double getPolarisationCorrection() {
		return polarisationCorrection;
	}
	public void setPolarisationCorrection(double polarisationCorrection) {
		this.polarisationCorrection = polarisationCorrection;
	}
	public double getAreaCorrection() {
		return areaCorrection;
	}
	public void setAreaCorrection(double areaCorrection) {
		this.areaCorrection = areaCorrection;
	}
	public double[] getRoiLocation() {
		return roiLocation;
	}
	public void setRoiLocation(double[] roiLocation) {
		this.roiLocation = roiLocation;
	}
	public FitPower getFitPower() {
		return fitPower;
	}
	public void setFitPower(FitPower fitPower) {
		this.fitPower = fitPower;
	}
	public int getBoundaryBox() {
		return boundaryBox;
	}
	public void setBoundaryBox(int boundaryBox) {
		this.boundaryBox = boundaryBox;
	}
	public TrackerType1 getTrackingMethodology() {
		return trackingMethodology;
	}
	public void setTrackingMethodology(TrackerType1 trackerType1) {
		this.trackingMethodology = trackerType1;
	}
	public Methodology getBackgroundMethdology() {
		return backgroundMethodology;
	}
	public void setBackgroundMethodology(Methodology backgroundMethdology) {
		this.backgroundMethodology = backgroundMethdology;
	}
	public double[] getOverlapping_Background_ROI() {
		return overlapping_Background_ROI;
	}
	public void setOverlapping_Background_ROI(double[] overlapping_Background_ROI) {
		this.overlapping_Background_ROI = overlapping_Background_ROI;
	}
	public double[] getStatic_Background_ROI() {
		return static_Background_ROI;
	}
	public void setStatic_Background_ROI(double[] static_Background_ROI) {
		this.static_Background_ROI = static_Background_ROI;
	}
	public ILazyDataset getRawImageData() {
		if(rawImageData == null){
			rawImageData = DatasetFactory.createFromObject(0);		
		}
		
		return rawImageData;
	}
	public void setRawImageData(ILazyDataset rawImageData) {
		this.rawImageData = rawImageData;
	}
	public double getUnspliced_Corrected_Intensity() {
		return unspliced_Corrected_Intensity;
	}
	public void setUnspliced_Corrected_Intensity(double unspliced_Corrected_Intensity) {
		this.unspliced_Corrected_Intensity = unspliced_Corrected_Intensity;
	}
	public double getUnspliced_Corrected_Intensity_Error() {
		return unspliced_Corrected_Intensity_Error;
	}
	public void setUnspliced_Corrected_Intensity_Error(double unspliced_Corrected_Intensity_Error) {
		this.unspliced_Corrected_Intensity_Error = unspliced_Corrected_Intensity_Error;
	}
	public double getUnspliced_Raw_Intensity() {
		return unspliced_Raw_Intensity;
	}
	public void setUnspliced_Raw_Intensity(double unspliced_Raw_Intensity) {
		this.unspliced_Raw_Intensity = unspliced_Raw_Intensity;
	}
	public double getUnspliced_Raw_Intensity_Error() {
		return unspliced_Raw_Intensity_Error;
	}
	public void setUnspliced_Raw_Intensity_Error(double unspliced_Raw_Intensity_Error) {
		this.unspliced_Raw_Intensity_Error = unspliced_Raw_Intensity_Error;
	}
	public double getUnspliced_Fhkl_Intensity() {
		return unspliced_Fhkl_Intensity;
	}
	public void setUnspliced_Fhkl_Intensity(double unspliced_Fhkl_Intensity) {
		this.unspliced_Fhkl_Intensity = unspliced_Fhkl_Intensity;
	}
	public double getUnspliced_Fhkl_Intensity_Error() {
		return unspliced_Fhkl_Intensity_Error;
	}
	public void setUnspliced_Fhkl_Intensity_Error(double unspliced_Fhkl_Intensity_Error) {
		this.unspliced_Fhkl_Intensity_Error = unspliced_Fhkl_Intensity_Error;
	}
	public int getDatNo() {
		return datNo;
	}
	public void setDatNo(int datNo) {
		this.datNo = datNo;
	}
	public double getScannedVariable() {
		return scannedVariable;
	}
	public void setScannedVariable(double scannedVariable) {
		this.scannedVariable = scannedVariable;
	}
	public ProccessingMethod getProcessingMethodSelection() {
		return processingMethodSelection;
	}
	public void setProcessingMethodSelection(ProccessingMethod processingMethodSelection) {
		this.processingMethodSelection = processingMethodSelection;
	}
	public MethodSetting getCorrectionSelection() {
		return correctionSelection;
	}
	public void setCorrectionSelection(MethodSetting correctionSelection) {
		this.correctionSelection = correctionSelection;
	}
	public IDataset getBackgroundSubtractedImage() {
		return backgroundSubtractedImage;
	}
	public void setBackgroundSubtractedImage(IDataset backgroundSubtractedImage) {
		this.backgroundSubtractedImage = backgroundSubtractedImage;
	}
}
