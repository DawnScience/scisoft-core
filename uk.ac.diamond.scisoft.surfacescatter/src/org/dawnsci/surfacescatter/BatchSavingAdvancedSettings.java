package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.SavingFormatEnum.SaveFormatSetting;

public class BatchSavingAdvancedSettings {

	private SaveFormatSetting sfs;
	private boolean allPoints = true;
	private boolean goodPoints = true;
	
	public BatchSavingAdvancedSettings(SaveFormatSetting sfs, boolean allPoints, boolean goodPoints){
		this.sfs = sfs;
		this.allPoints = allPoints;
		this.goodPoints = goodPoints;
		
	}
	
	public BatchSavingAdvancedSettings(SaveFormatSetting sfs){
		this.sfs = sfs;	
	}
	
	public SaveFormatSetting getSfs() {
		return sfs;
	}
	public void setSfs(SaveFormatSetting sfs) {
		this.sfs = sfs;
	}
	public boolean isAllPoints() {
		return allPoints;
	}
	public void setAllPoints(boolean allPoints) {
		this.allPoints = allPoints;
	}
	public boolean isGoodPoints() {
		return goodPoints;
	}
	public void setGoodPoints(boolean goodPoints) {
		this.goodPoints = goodPoints;
	}
	
	
	
	
}
