/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DiffractionMetadataConversionModel extends AbstractOperationModel {
	enum SupportedFormat {
		PONI(".poni");
		private String extension;

		SupportedFormat(String extension) {
			this.extension = extension;
		}

		public String getExtension() {
			return this.extension;
		}
	}

	@OperationModelField(hint = "Choose from known calibration conventions", label = "Convert to")
	private SupportedFormat format = SupportedFormat.PONI;

	@OperationModelField(hint = "Enter a folder in which to place the new calibration", file = FileType.EXISTING_FOLDER, label = "Output Folder", fieldPosition = 2)
	private File outputFolder = null;

	@OperationModelField(label = "Pad with zeros", description = "Should you want to have a fixed length for the appended frame number, useful for ordering in lists.",hint="must be >0", min = 1)
	private Integer paddingZeros = 5;

	// Now the getters and setters
	public Integer getPaddingZeros() {
		return paddingZeros;
	}

	public SupportedFormat getFormat() {
		return this.format;
	}

	public File getOutputFolder() {
		return this.outputFolder;
	}

	public void setFormat(SupportedFormat fmt) {
		firePropertyChange("format", this.format, this.format = fmt);
	}

	public void setOutputFolder(File file) {
		firePropertyChange("outputPath", this.outputFolder, this.outputFolder = file);
	}

	public void setPaddingZeros(Integer paddingZeros) {
		firePropertyChange("paddingZeros", this.paddingZeros, this.paddingZeros = paddingZeros);
	}
}
