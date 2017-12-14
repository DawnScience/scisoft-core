package org.dawnsci.surfacescatter;

public class BatchRodDataTransferObject {

	private String[] datFiles;
	private String imageFolderPath;
	private String paramFiles;
	private String nexusSaveFilePaths;
	private String saveFolder;
	private String rodName;
	private boolean useTrajectory;
	private boolean useStareMode;

	public boolean isUseStareMode() {
		return useStareMode;
	}

	public void setUseStareMode(boolean useStareMode) {
		this.useStareMode = useStareMode;
	}

	public String[] getDatFiles() {
		return datFiles;
	}

	public void setDatFiles(String[] datFiles) {
		this.datFiles = datFiles;
	}

	public String getImageFolderPath() {
		return imageFolderPath;
	}

	public void setImageFolderPath(String imageFolderPath) {
		this.imageFolderPath = imageFolderPath;
	}

	public String getParamFiles() {
		return paramFiles;
	}

	public void setParamFiles(String paramFiles) {
		this.paramFiles = paramFiles;
	}

	public String getNexusSaveFilePaths() {
		return nexusSaveFilePaths;
	}

	public void setNexusSaveFilePaths(String nexusSaveFilePaths) {
		this.nexusSaveFilePaths = nexusSaveFilePaths;
	}

	public String getRodName() {
		return rodName;
	}

	public void setRodName(String rodName) {
		this.rodName = rodName;
	}

	public boolean isUseTrajectory() {
		return useTrajectory;
	}

	public void setUseTrajectory(boolean useTrajectory) {
		this.useTrajectory = useTrajectory;
	}

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

}
