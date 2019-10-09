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

/**
 * Bean to serialise with JSON and be sent to the server,
 * and deserialise in the processing headless runner.
 * 
 * JSON is used rather than the direct object because we may want to have
 * a python server, and JSON files allow the headless runner input file
 * to be built by hand
 */
public class OperationBean implements IOperationBean {

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
	//detector is overwriting data
	private boolean 			 monitorForOverwrite = false;
	
	//time out for live processing
	private int timeOut = 60000;

	//URI to publish updates to
	private String publisherURI = null;
	
	private boolean linkEntry = true;

	private String runDirectory;
	private String name;
	
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
				dd[i] = rank-(detectorRank-i);
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
	public void setScanRank(Integer scanRank) {
		this.scanRank = scanRank;
	}
	
	public Integer getScanRank() {
		return scanRank;
	}
	
	public int getTimeOut() {
		return timeOut;
	}

	@Override
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public void setLinkParentEntry(boolean linkEntry) {
		this.linkEntry = linkEntry;
	}
	
	public boolean getLinkParentEntry() {
		return linkEntry;
	}
	
	/**
	 * Set whether the raw data is going to be overwritten
	 * 
	 * @param overwrite
	 */
	@Override
	public void setMonitorForOverwrite(boolean overwrite) {
		monitorForOverwrite = overwrite;
	}
	
	public boolean isMonitorForOverwrite(){
		return monitorForOverwrite;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public void setRunDirectory(String runDirectory) {
		this.runDirectory = runDirectory;
		
	}
	
	public String getRunDirectory() {
		return runDirectory;
	}

	public String getName() {
		return name;
	}
}
