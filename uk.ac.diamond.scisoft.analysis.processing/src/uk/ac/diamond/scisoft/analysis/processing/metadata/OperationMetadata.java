/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.metadata;


import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.MetadataType;

public interface OperationMetadata extends MetadataType {

	public String getOutputFilename();
	
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata);
	
	public Dataset process(String filename, String datasetName, SliceFromSeriesMetadata metadata, Integer start, Integer stop);
	
}
