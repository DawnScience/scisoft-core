/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.cache;

import java.io.File;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.SliceObject;

/**
 * A key used by the loader factory to cache previously read data.
 */
public class CacheKey {

	private String  filePath;
	private long    timeStamp;
	private String  datasetName;
	private List<String> datasetNames;
	private SliceObject slice;
	private int imageNumber;
	private boolean metadata;

	/**
	 * Important, do not allow clients to create their own keys
	 */
	protected CacheKey() {
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath  = filePath;
		this.timeStamp = (new File(filePath)).lastModified();
	}

	/**
	 * @return time stamp of file in milliseconds relative to epoch
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Set time stamp
	 * @param timeStamp elapsed time in milliseconds since epoch
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	/**
	 * @return Returns the image number.
	 */
	public int getImageNumber() {
		return imageNumber;
	}

	/**
	 * @param imageNumber The image number to set.
	 */
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		result = prime * result + ((datasetName == null) ? 0 : datasetName.hashCode());
		result = prime * result + ((datasetNames == null) ? 0 : datasetNames.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + (metadata ? 1231 : 1237);
		result = prime * result + ((slice == null) ? 0 : slice.hashCode());
		result = prime * result + imageNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (timeStamp != other.timeStamp)
			return false;
		if (datasetName == null) {
			if (other.datasetName != null)
				return false;
		} else if (!datasetName.equals(other.datasetName))
			return false;
		if (datasetNames == null) {
			if (other.datasetNames != null)
				return false;
		} else if (!datasetNames.equals(other.datasetNames))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (metadata != other.metadata)
			return false;
		if (slice == null) {
			if (other.slice != null)
				return false;
		} else if (!slice.equals(other.slice))
			return false;
		if (imageNumber != other.imageNumber)
			return false;
		return true;
	}

	public List<String> getDatasetNames() {
		return datasetNames;
	}

	public void setDatasetNames(List<String> datasetNames) {
		this.datasetNames = datasetNames;
	}

	public SliceObject getSlice() {
		return slice;
	}

	public void setSlice(SliceObject slice) {
		this.slice = slice;
	}

	@Override
	public String toString() {
		return hashCode() + ":" + filePath + "; " + datasetName + ", " + datasetNames + ", " + metadata;
	}

	/**
	 * @return true if has metadata
	 */
	public boolean hasMetadata() {
		return metadata;
	}

	/**
	 * Set has or want metadata
	 * @param metadata
	 */
	public void setMetadata(boolean metadata) {
		this.metadata = metadata;
	}

	/**
	 * @param other
	 * @return true if file and date stamp are the same.
	 */
	public boolean isSameFile(CacheKey other) {
		if (timeStamp != other.timeStamp)               return false;
		if (!getFilePath().equals(other.getFilePath())) return false;
		return true;
	}
}
