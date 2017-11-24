/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.ModelUtils;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


public class SubtractRemappedExternalDataModel extends AbstractOperationModel {
	
	// First an enum to determine what format our input data is
	enum DataType {
		NeXus(1),
		DAT(2);
		
		private final int dataType;
		
		DataType(int dataType) {
			this.dataType = dataType;
		}
		
		public int getDatatype() {
			return this.dataType;
		}
		
		@Override
		public String toString() {
			switch (this.dataType) {
				case 1:		return String.format("NeXus File");
				case 2:		return String.format("DAT File");
				default:	return String.format("Error!");
			}
		}
	}
	
	private boolean loadingNeXusFile = true;
	
	// Should we be integrating over a half or one Pi radians?
	@OperationModelField(label = "I vs q data format", hint = "NeXus or DAT (Tab-delimited text) format", fieldPosition = 1)
	private DataType dataType = DataType.NeXus;

	// Now the getters and setters
	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		if (dataType == DataType.NeXus) {
			this.loadingNeXusFile = true;
		} else {
			this.loadingNeXusFile = false;
		}
		
		firePropertyChange("dataType", this.dataType, this.dataType = dataType);
	}
	
	
	@OperationModelField(hint="Enter the path to the data file, leave blank to use the file being processed", file = FileType.EXISTING_FILE, label = "File", fieldPosition = 2)
	private String filePath = "";

	public String getFilePath() {
		return filePath;
	}
	
	
	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
		
	}
	
	@OperationModelField(dataset = "filePath", label = "If required, dataset path", fieldPosition = 3, enableif = "dataType==\"NeXus\"")
	String filePathDataset = "";
	
	public String getFilePathDataset() {
		return filePathDataset;
	}

	public void setFilePathDataset(String filePathDataset) {
		firePropertyChange("filePathDataset", this.filePathDataset, this.filePathDataset = filePathDataset);
	}
}