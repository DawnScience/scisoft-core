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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.IOperationBean;
import org.eclipse.scanning.api.event.status.StatusBean;

/**
 * Bean to serialise with JSON and be sent to the server,
 * and deserialise in the processing headless runner.
 * 
 * JSON is used rather than the direct object because we may want to have
 * a python server, and JSON files allow the headless runner input file
 * to be built by hand
 */
public class OperationBean extends StatusBean implements IOperationBean {

	private String               filePath;              
	private String               datasetPath; // can be to NXdata node
	private String				 slicing; //can be null
	private List<String>[] 	  	 axesNames; //can be null
	private String 				 outputFilePath;
	private int[] 				 dataDimensions; //e.g. [1,2] for stack of images or [-1,-2]
	private Integer 			 scanRank = null; // used instead of the dataDimensions
	
	// The pipeline that we need to run
	// The pipeline is saved shared disk at
	// the moment in order to be run on the cluster.
	// This is not ideal because if the consumer 
	// and client do not share disk, it will not work.
	private String               processingPath;

	//memory for processing vm
	private String 				 xmx;
	//use multiple cores
	private int					 numberOfCores = 1;
	//make the output file readable (SWMR)
	private boolean				 readable = false;
	//node where the unique/finished datasets are located (live processing)
	private String				 dataKey;
	//clear processing file at finish
	private boolean   			 deleteProcessingFile = true;
	
	//URI to publish updates to
	private String publisherURI = null;

	@Override
	public void merge(StatusBean with) {
        super.merge(with);
        OperationBean db = (OperationBean)with;
        this.filePath             = db.filePath;
        this.datasetPath          = db.datasetPath;
        this.slicing              = db.slicing;
        this.axesNames 		      = db.axesNames == null ? null : db.axesNames.clone();
        this.outputFilePath       = db.outputFilePath;
        this.dataDimensions       = db.axesNames == null ? null : db.dataDimensions.clone(); 
        this.processingPath       = db.processingPath;
        this.xmx 			      = db.xmx;
        this.numberOfCores		  = db.numberOfCores;
        this.readable 		 	  = db.readable;
        this.dataKey         	  = db.dataKey;
        this.deleteProcessingFile = db.deleteProcessingFile;
        this.publisherURI         = db.publisherURI;
	}
	
	public String getDataKey() {
		return dataKey;
	}

	@Override
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
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

	public String getProcessingPath() {
		return processingPath;
	}

	@Override
	public void setProcessingPath(String processingPath) {
		this.processingPath = processingPath;
	}
	
	@Override
	public void setAxesNames(List<String>[] axesNames) {
		this.axesNames = axesNames;
	}
	
	public List<String>[] getAxesNames() {
		return this.axesNames;
	}
	
	public boolean isDeleteProcessingFile() {
		return deleteProcessingFile;
	}

	@Override
	public void setDeleteProcessingFile(boolean deleteProcessingFile) {
		this.deleteProcessingFile = deleteProcessingFile;
	}

	public String getXmx() {
		return xmx;
	}

	@Override
	public void setXmx(String xmx) {
		this.xmx = xmx;
	}
	
	public int[] getDataDimensionsForRank(int rank) {
		
		if (scanRank != null) {
			int detectorRank = rank-scanRank;
			int[] dd = new int[detectorRank];
			
			for (int i = 0; i < detectorRank; i++) {
				dd[i] = -(detectorRank-i);
			}
			
			return dd;
		}
		
		if (dataDimensions[0] > -1) return dataDimensions;
		int[] dims = new int[dataDimensions.length];
		for (int i = 0; i < dataDimensions.length; i++) dims[i] = rank + dataDimensions[i];
		Arrays.sort(dims);
		
		return dims;
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

	public int getNumberOfCores() {
		return numberOfCores;
	}

	public void setNumberOfCores(int numberOfCores) {
		this.numberOfCores = numberOfCores;
	}

	public String getPublisherURI() {
		return publisherURI;
	}

	public void setPublisherURI(String publisherURI) {
		this.publisherURI = publisherURI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(axesNames);
		result = prime * result + Arrays.hashCode(dataDimensions);
		result = prime * result + ((dataKey == null) ? 0 : dataKey.hashCode());
		result = prime * result + ((datasetPath == null) ? 0 : datasetPath.hashCode());
		result = prime * result + (deleteProcessingFile ? 1231 : 1237);
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + numberOfCores;
		result = prime * result + ((outputFilePath == null) ? 0 : outputFilePath.hashCode());
		result = prime * result + ((processingPath == null) ? 0 : processingPath.hashCode());
		result = prime * result + ((publisherURI == null) ? 0 : publisherURI.hashCode());
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
		if (!Arrays.equals(axesNames, other.axesNames))
			return false;
		if (!Arrays.equals(dataDimensions, other.dataDimensions))
			return false;
		if (dataKey == null) {
			if (other.dataKey != null)
				return false;
		} else if (!dataKey.equals(other.dataKey))
			return false;
		if (datasetPath == null) {
			if (other.datasetPath != null)
				return false;
		} else if (!datasetPath.equals(other.datasetPath))
			return false;
		if (deleteProcessingFile != other.deleteProcessingFile)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (numberOfCores != other.numberOfCores)
			return false;
		if (outputFilePath == null) {
			if (other.outputFilePath != null)
				return false;
		} else if (!outputFilePath.equals(other.outputFilePath))
			return false;
		if (processingPath == null) {
			if (other.processingPath != null)
				return false;
		} else if (!processingPath.equals(other.processingPath))
			return false;
		if (publisherURI == null) {
			if (other.publisherURI != null)
				return false;
		} else if (!publisherURI.equals(other.publisherURI))
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

	@Override
	public void setScanRank(Integer scanRank) {
		this.scanRank = scanRank;
	}
	
	public Integer getScanRank() {
		return scanRank;
	}

}
