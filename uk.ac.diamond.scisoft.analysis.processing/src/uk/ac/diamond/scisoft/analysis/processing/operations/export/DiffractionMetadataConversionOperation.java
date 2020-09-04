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
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PONIDiffractionMetadataDescriptor;
import uk.ac.diamond.scisoft.analysis.processing.operations.export.DiffractionMetadataConversionModel.SupportedFormat;

public class DiffractionMetadataConversionOperation
		extends AbstractOperationBase<DiffractionMetadataConversionModel, OperationData> implements IExportOperation {

	private volatile IDiffractionMetadata metadata;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.DiffractionMetadataConversionOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);

		File inFile = new File(ssm.getFilePath());

		File folderPath = (model.getOutputFolder() == null) ? inFile.getParentFile() : model.getOutputFolder();
		String fileName = inFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" + String
				.format("%0" + String.valueOf(model.getPaddingZeros()) + "d", ssm.getSliceInfo().getSliceNumber());
		String ext = model.getFormat().getExtension();

		File outFile = new File(folderPath, fileName + ext);

		metadata = slice.getFirstMetadata(IDiffractionMetadata.class);
		if (metadata == null) {
			throw new IllegalArgumentException("No diffraction metadata found");
		}

		try {
			exportMetaDataToFormat(outFile, model.getFormat());
		} catch (IOException e) {
			throw new OperationException(this, "Error when writing the geometry to file", e);
		}

		return new OperationData(slice);
	}

	private void exportMetaDataToFormat(File outputPath, SupportedFormat fmt) throws IOException {

		if (this.metadata != null && fmt.equals(SupportedFormat.PONI)) {
			synchronized (this) {
				PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(metadata);
				try (FileOutputStream outStream = new FileOutputStream(outputPath)) {
					outStream.write(poni.getStringDescription().getBytes());
				}
			}

		}
	}


}
