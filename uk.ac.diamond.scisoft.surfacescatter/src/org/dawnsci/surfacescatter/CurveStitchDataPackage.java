package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.eclipse.january.dataset.IDataset;

public class CurveStitchDataPackage {

	
	////Inputs:
	
	private String[] filepaths; // Dat filepaths
	
	private IDataset[] xIDataset;
	private IDataset[] yIDataset;
	private IDataset[] yIDatasetError;
	private IDataset[] yIDatasetFhkl;
	private IDataset[] yIDatasetFhklError;	
	private IDataset[] yRawIDataset;
	private IDataset[] yRawIDatasetError;
	private MethodSetting correctionSelection = MethodSetting.SXRD;
	
	////Outputs:
	
	private IDataset splicedCurveY;
	private IDataset splicedCurveX;
	private IDataset splicedCurveYFhkl;
	private IDataset splicedCurveYError;
	private IDataset splicedCurveYFhklError;
	private IDataset splicedCurveYErrorMax;
	private IDataset splicedCurveYErrorMin;
	private IDataset splicedCurveYFhklErrorMax;
	private IDataset splicedCurveYFhklErrorMin;
	private IDataset splicedCurveYRaw;
	private IDataset splicedCurveYRawError;
	private ArrayList<OverlapDataModel> overlapDataModels;
	
	public IDataset[] getxIDataset() {
		return xIDataset;
	}
	public void setxIDataset(IDataset[] xIDataset) {
		this.xIDataset = xIDataset;
	}
	public IDataset[] getyIDataset() {
		return yIDataset;
	}
	public void setyIDataset(IDataset[] yIDataset) {
		this.yIDataset = yIDataset;
	}
	public IDataset[] getyIDatasetFhkl() {
		return yIDatasetFhkl;
	}
	public void setyIDatasetFhkl(IDataset[] yIDatasetFhkl) {
		this.yIDatasetFhkl = yIDatasetFhkl;
	}
	public IDataset[] getyIDatasetError() {
		return yIDatasetError;
	}
	public void setyIDatasetError(IDataset[] yIDatasetError) {
		this.yIDatasetError = yIDatasetError;
	}
	public IDataset[] getyIDatasetFhklError() {
		return yIDatasetFhklError;
	}
	public void setyIDatasetFhklError(IDataset[] yIDatasetFhklError) {
		this.yIDatasetFhklError = yIDatasetFhklError;
	}
	public IDataset[] getyRawIDataset() {
		return yRawIDataset;
	}
	public void setyRawIDataset(IDataset[] yRawIDataset) {
		this.yRawIDataset = yRawIDataset;
	}
	public IDataset[] getyRawIDatasetError() {
		return yRawIDatasetError;
	}
	public void setyRawIDatasetError(IDataset[] yRawIDatasetError) {
		this.yRawIDatasetError = yRawIDatasetError;
	}
	public IDataset getSplicedCurveY() {
		return splicedCurveY;
	}
	public void setSplicedCurveY(IDataset splicedCurveY) {
		this.splicedCurveY = splicedCurveY;
	}
	public IDataset getSplicedCurveX() {
		return splicedCurveX;
	}
	public void setSplicedCurveX(IDataset splicedCurveX) {
		this.splicedCurveX = splicedCurveX;
	}
	public IDataset getSplicedCurveYFhkl() {
		return splicedCurveYFhkl;
	}
	public void setSplicedCurveYFhkl(IDataset splicedCurveYFhkl) {
		this.splicedCurveYFhkl = splicedCurveYFhkl;
	}
	public IDataset getSplicedCurveYError() {
		return splicedCurveYError;
	}
	public void setSplicedCurveYError(IDataset splicedCurveYError) {
		this.splicedCurveYError = splicedCurveYError;
	}
	public IDataset getSplicedCurveYFhklError() {
		return splicedCurveYFhklError;
	}
	public void setSplicedCurveYFhklError(IDataset splicedCurveYFhklError) {
		this.splicedCurveYFhklError = splicedCurveYFhklError;
	}
	public IDataset getSplicedCurveYErrorMax() {
		return splicedCurveYErrorMax;
	}
	public void setSplicedCurveYErrorMax(IDataset splicedCurveYErrorMax) {
		this.splicedCurveYErrorMax = splicedCurveYErrorMax;
	}
	public IDataset getSplicedCurveYErrorMin() {
		return splicedCurveYErrorMin;
	}
	public void setSplicedCurveYErrorMin(IDataset splicedCurveYErrorMin) {
		this.splicedCurveYErrorMin = splicedCurveYErrorMin;
	}
	public IDataset getSplicedCurveYFhklErrorMax() {
		return splicedCurveYFhklErrorMax;
	}
	public void setSplicedCurveYFhklErrorMax(IDataset splicedCurveYFhklErrorMax) {
		this.splicedCurveYFhklErrorMax = splicedCurveYFhklErrorMax;
	}
	public IDataset getSplicedCurveYFhklErrorMin() {
		return splicedCurveYFhklErrorMin;
	}
	public void setSplicedCurveYFhklErrorMin(IDataset splicedCurveYFhklErrorMin) {
		this.splicedCurveYFhklErrorMin = splicedCurveYFhklErrorMin;
	}
	public IDataset getSplicedCurveYRaw() {
		return splicedCurveYRaw;
	}
	public void setSplicedCurveYRaw(IDataset splicedCurveYRaw) {
		this.splicedCurveYRaw = splicedCurveYRaw;
	}
	public IDataset getSplicedCurveYRawError() {
		return splicedCurveYRawError;
	}
	public void setSplicedCurveYRawError(IDataset splicedCurveYRawError) {
		this.splicedCurveYRawError = splicedCurveYRawError;
	}
	public ArrayList<OverlapDataModel> getOverlapDataModels() {
		return overlapDataModels;
	}
	public void setOverlapDataModels(ArrayList<OverlapDataModel> overlapDataModels) {
		this.overlapDataModels = overlapDataModels;
	}
	public String[] getFilepaths() {
		return filepaths;
	}
	public void setFilepaths(String[] filepaths) {
		this.filepaths = filepaths;
	}
	public MethodSetting getCorrectionSelection() {
		return correctionSelection;
	}
	public void setCorrectionSelection(MethodSetting correctionSelection) {
		this.correctionSelection = correctionSelection;
	}
	
}
