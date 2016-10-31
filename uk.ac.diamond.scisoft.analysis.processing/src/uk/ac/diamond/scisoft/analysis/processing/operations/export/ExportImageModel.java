/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.export;


// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportImageModel.FileFormatEnum;


// The model for an Image exporting plug-in for the processing perspective
// Currently it will export either a 32-bit TIFF or a 16-bit PNG file. PNG saving may well result in data loss...
// It is intended for PUBLICATION saving only but this may be foolish to assume.

// @author Tim Snow

public class ExportImageModel extends AbstractOperationModel{

	
	// Let's give the user a fixed choice on the file format...
	enum FileFormatEnum {
		TIFF(1),
		PNG(2);
		
		private final int fileFormatEnum;
		
		FileFormatEnum(int fileFormatEnum) {
			this.fileFormatEnum = fileFormatEnum;
		}
		
		public int getFileFormat() {
			return this.fileFormatEnum;
		}
		
		@Override
		public String toString() {
			switch (this.fileFormatEnum) {
				case 1:		return String.format("TIFF File");
				case 2:		return String.format("PNG File");
				default:	return String.format("Error!");
			}
		}
	}

	
	// Should we be exporting a TIFF or PNG file?
	@OperationModelField(label = "File Type", hint = "Pick whether to export as PNG or TIFF files. PNG files will be exported as 16-bit, TIFF as 32-bit. EXPORTING TO PNG MAY RESULT IN LOSS OF INTENSITY RESOLUTION!", fieldPosition = 1)
	private FileFormatEnum fileFormatEnum = FileFormatEnum.TIFF;

	// Now the getters and setters
	public FileFormatEnum getFileFormatEnum() {
		return fileFormatEnum;
	}

	public void setFileFormatEnum(FileFormatEnum fileFormatEnum) {
		firePropertyChange("FileFormatEnum", this.fileFormatEnum, this.fileFormatEnum = fileFormatEnum);
	}

	
	// Where should we be exporting to?	
	@OperationModelField(label = "Output Directory", hint="Enter the path to output directory", file = FileType.EXISTING_FOLDER, fieldPosition = 2)
	private String outputDirectoryPath = "";

	// Now the getters and setters
	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}

	public void setOutputDirectoryPath(String outputDirectoryPath) {
		firePropertyChange("outputDirectoryPath", this.outputDirectoryPath, this.outputDirectoryPath = outputDirectoryPath);
	}


	// Should the files have a numerical prefix? Useful for multiframe files...
	@OperationModelField(label = "Pad with zeros", hint = "Should you want to have a fixed length for the appended frame number, useful for ordering in lists.", min = 1)
	private Integer paddingZeros = 5;

	// Now the getters and setters
	public Integer getPaddingZeros() {
		return paddingZeros;
	}

	public void setPaddingZeros(Integer paddingZeros) {
		firePropertyChange("paddingZeros", this.paddingZeros, this.paddingZeros = paddingZeros);
	}	
}