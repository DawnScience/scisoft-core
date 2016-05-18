/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.bean;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.processing.IOperationBean;
import org.eclipse.scanning.api.event.status.StatusBean;

/**
 * Bean to serialise with JSON and be sent to the server.
 * 
 * JSON is used rather than the direct object because we may want to have
 * a python server.
 * 
 * @author Matthew Gerring
 *
 */
public class OperationBean extends StatusBean implements IOperationBean {
	
	// The data
	private String               filePath;              
	private String               datasetPath;
	private String				 slicing;
	private Map<Integer, String> axesNames;
	private String 				 outputFilePath;
	private int[] 				 dataDimensions;
	
	// The pipeline that we need to run
	// The pipeline is saved shared disk at
	// the moment in order to be run on the cluster.
	// This is not ideal because if the consumer 
	// and client do not share disk, it will not work.
	private String               persistencePath;

	private long                 parallelTimeout=5000;
	private String 				 xmx;
	private boolean				 readable = false;
	private String				 dataKey;
	
	public String getDataKey() {
		return dataKey;
	}

	@Override
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	// Tidying stuff
	private boolean deletePersistenceFile = true;
	
	public OperationBean(){
		
	}

	@Override
	public void merge(StatusBean with) {
        super.merge(with);
        OperationBean db = (OperationBean)with;
        this.filePath        = db.filePath;
        this.datasetPath     = db.datasetPath;
        this.slicing         = db.slicing;
        this.persistencePath = db.persistencePath;
        this.parallelTimeout = db.parallelTimeout;
        this.deletePersistenceFile = db.deletePersistenceFile;
        this.xmx = db.xmx;
	}
	

	public String getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(String fileName) {
		this.filePath = fileName;
	}
	
	public String getOutputFilePath() {
		return outputFilePath;
	}

	@Override
	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public String getDatasetPath() {
		return datasetPath;
	}

	@Override
	public void setDatasetPath(String datasetPath) {
		this.datasetPath = datasetPath;
	}

	public String getSlicing() {
		return slicing;
	}

	@Override
	public void setSlicing(String slicing) {
		this.slicing = slicing;
	}

	public String getPersistencePath() {
		return persistencePath;
	}

	@Override
	public void setPersistencePath(String persistencePath) {
		this.persistencePath = persistencePath;
	}
	
	@Override
	public void setAxesNames(Map<Integer, String> axesNames) {
		this.axesNames = axesNames;
	}
	
	public Map<Integer, String> getAxesNames() {
		return this.axesNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((axesNames == null) ? 0 : axesNames.hashCode());
		result = prime * result + Arrays.hashCode(dataDimensions);
		result = prime * result + ((datasetPath == null) ? 0 : datasetPath.hashCode());
		result = prime * result + (deletePersistenceFile ? 1231 : 1237);
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((outputFilePath == null) ? 0 : outputFilePath.hashCode());
		result = prime * result + (int) (parallelTimeout ^ (parallelTimeout >>> 32));
		result = prime * result + ((persistencePath == null) ? 0 : persistencePath.hashCode());
		result = prime * result + (readable ? 1231 : 1237);
		result = prime * result + ((slicing == null) ? 0 : slicing.hashCode());
		result = prime * result + ((xmx == null) ? 0 : xmx.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationBean other = (OperationBean) obj;
		if (axesNames == null) {
			if (other.axesNames != null)
				return false;
		} else if (!axesNames.equals(other.axesNames))
			return false;
		if (!Arrays.equals(dataDimensions, other.dataDimensions))
			return false;
		if (datasetPath == null) {
			if (other.datasetPath != null)
				return false;
		} else if (!datasetPath.equals(other.datasetPath))
			return false;
		if (deletePersistenceFile != other.deletePersistenceFile)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (outputFilePath == null) {
			if (other.outputFilePath != null)
				return false;
		} else if (!outputFilePath.equals(other.outputFilePath))
			return false;
		if (parallelTimeout != other.parallelTimeout)
			return false;
		if (persistencePath == null) {
			if (other.persistencePath != null)
				return false;
		} else if (!persistencePath.equals(other.persistencePath))
			return false;
		if (readable != other.readable)
			return false;
		if (slicing == null) {
			if (other.slicing != null)
				return false;
		} else if (!slicing.equals(other.slicing))
			return false;
		if (xmx == null) {
			if (other.xmx != null)
				return false;
		} else if (!xmx.equals(other.xmx))
			return false;
		return true;
	}

	public long getParallelTimeout() {
		return parallelTimeout;
	}

	@Override
	public void setParallelTimeout(long parallelTimeout) {
		this.parallelTimeout = parallelTimeout;
	}

	public boolean isDeletePersistenceFile() {
		return deletePersistenceFile;
	}

	@Override
	public void setDeletePersistenceFile(boolean deletePersistenceFile) {
		this.deletePersistenceFile = deletePersistenceFile;
	}

	public String getXmx() {
		return xmx;
	}

	@Override
	public void setXmx(String xmx) {
		this.xmx = xmx;
	}

	public int[] getDataDimensions() {
		return dataDimensions;
	}

	@Override
	public void setDataDimensions(int[] dataDimensions) {
		this.dataDimensions = dataDimensions;
	}

	public boolean isReadable() {
		return readable;
	}

	@Override
	public void setReadable(boolean readable) {
		this.readable = readable;
	}
}
