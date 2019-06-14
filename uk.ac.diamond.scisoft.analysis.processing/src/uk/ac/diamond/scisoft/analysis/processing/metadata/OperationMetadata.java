/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.metadata;


import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.MetadataType;

public interface OperationMetadata extends MetadataType {

	public String getOutputFilename();
	
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata);
	
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata, Integer start, Integer stop);

	/**
	 * @return array of operations in chain prior to one currently used
	 */
	IOperation<?, ?>[] getPriorOperations();

	/**
	 * Process configured operations with given file name and dataset name with given metadata,
	 * for given slice number limits, and using given visitor
	 * @param filename
	 * @param datasetName
	 * @param metadata
	 * @param start
	 * @param stop
	 * @param visitor
	 */
	void process(String filename, String datasetName, SliceFromSeriesMetadata metadata, Integer start,
			Integer stop, IExecutionVisitor visitor);
	
	/**
	 * Get the position of the current operation in the sequence
	 * @return position
	 */
	public int getCurrentOperationPosition();
}
