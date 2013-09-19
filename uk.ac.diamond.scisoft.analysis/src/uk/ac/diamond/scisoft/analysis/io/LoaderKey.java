/*-
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.util.List;

/**
 * A key used by the loader factory to cache previously read data.
 */
public class LoaderKey {

	private String  filePath;
	private long    dateStamp;
	private String  datasetName;
	private List<String> datasetNames;
	private SliceObject slice;
	private boolean metadata;
	private boolean loadImageStacks;

	public LoaderKey() {
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * Gets date stamp of file, may throw exception
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath  = filePath;
		this.dateStamp = (new File(filePath)).lastModified();
	}

	public long getDatStamp() {
		return dateStamp;
	}

	public void setDataStamp(long dataStamp) {
		this.dateStamp = dataStamp;
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
		result = prime * result + ((datasetName == null) ? 0 : datasetName.hashCode());
		result = prime * result + ((datasetNames == null) ? 0 : datasetNames.hashCode());
		result = prime * result + (int) (dateStamp ^ (dateStamp >>> 32));
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + (loadImageStacks ? 1231 : 1237);
		result = prime * result + (metadata ? 1231 : 1237);
		result = prime * result + ((slice == null) ? 0 : slice.hashCode());
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
		LoaderKey other = (LoaderKey) obj;
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
		if (dateStamp != other.dateStamp)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (loadImageStacks != other.loadImageStacks)
			return false;
		if (metadata != other.metadata)
			return false;
		if (slice == null) {
			if (other.slice != null)
				return false;
		} else if (!slice.equals(other.slice))
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
	public boolean isSameFile(LoaderKey other) {
		if (dateStamp != other.dateStamp)               return false;
		if (!getFilePath().equals(other.getFilePath())) return false;
		return true;
	}

	public boolean isLoadImageStacks() {
		return loadImageStacks;
	}

	public void setLoadImageStacks(boolean loadImageStacks) {
		this.loadImageStacks = loadImageStacks;
	}
}
