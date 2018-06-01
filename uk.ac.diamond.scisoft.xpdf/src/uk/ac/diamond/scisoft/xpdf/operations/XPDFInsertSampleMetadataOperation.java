/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentPlate;
import uk.ac.diamond.scisoft.xpdf.XPDFGeometryEnum;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Insert the sample properties metadata into the XPDF metadata.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFInsertSampleMetadataOperation extends XPDFInsertXMetadataOperation<XPDFInsertSampleMetadataModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFTargetComponent compMeta = new XPDFTargetComponent();
		XPDFComponentForm formMeta = new XPDFComponentForm();
		XPDFComponentGeometry geomMeta = null;

		// Read shape from the Model
		XPDFGeometryEnum shape = model.getShape();

		switch (shape) {
		case CYLINDER :
			geomMeta = new XPDFComponentCylinder();
			break;
		case PLATE:
			geomMeta = new XPDFComponentPlate();
			break;
		case DEFINED_BY_CONTAINER:
		default:
			geomMeta = null;
		}

		// Read size data from the Model
		double inner = model.getInner();
		double outer = model.getOuter();
		// samples have no streamality

		// Null geometry doesn't need distances setting
		if (geomMeta != null)
			geomMeta.setDistances(inner, outer);

		// get the XPDF metadata object, or create a new one
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		
		// Counting time and relative flux of the trace in the main Dataset
		XPDFBeamTrace sampleTraceMeta = new XPDFBeamTrace();
		sampleTraceMeta.setCountingTime(model.getCountingTime());
		sampleTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		sampleTraceMeta.setTrace(null);
		sampleTraceMeta.setAxisAngle(model.isAxisAngle());

		theXPDFMetadata.setSampleTrace(sampleTraceMeta);
		
		// Get the material data from the Model
		String material = model.getMaterial();
		double density = model.getDensity();
		double packingFraction = model.getPackingFraction();
		// Get sample name from the Model
		String name = model.getSampleName();

		formMeta.setMatName(material);
		formMeta.setDensity(density);
		formMeta.setPackingFraction(packingFraction);
		formMeta.setGeom(geomMeta);

		compMeta.setForm(formMeta);
		compMeta.setName(name);

		compMeta.setSample(true);

		theXPDFMetadata.setSampleData(compMeta);
		
		// The metadata having been got, if there are any beam data already
		// inserted, update the value of isAxisAngle.
		if (theXPDFMetadata.getBeam() != null) {
			theXPDFMetadata.getEmptyTrace().setAxisAngle(sampleTraceMeta.isAxisAngle());
		}
		if (theXPDFMetadata.getContainers() != null &&
				!theXPDFMetadata.getContainers().isEmpty()) {
			for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
				theXPDFMetadata.getContainerTrace(container).setAxisAngle(sampleTraceMeta.isAxisAngle());
			}
		}
				
		if (model.getIncoherentScatteringPath() != null && model.getIncoherentScatteringPath().length() > 0) {
			String iScatterPath = model.getIncoherentScatteringPath();
			String dataset = "/entry1/data/data";
			
			Dataset iScatterData = DatasetUtils.convertToDataset(ProcessingUtils.getDataset(this, iScatterPath, dataset));
			iScatterData.squeeze();
			theXPDFMetadata.pushIncoherentScattering(iScatterData);
		}
		
		input.setMetadata(theXPDFMetadata);

		// Error metadata for the trace
		String xyFilePath = "";
		boolean isErrorData = true;
		try {
			xyFilePath = model.getErrorFilePath();
		} catch (Exception e) {
			// If file not found, then unset isErrorData
			isErrorData = false;
		}
		if (isErrorData && xyFilePath != null && model.getErrorDataset().length() > 0) {
			try {
				Dataset dataErrors = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, model.getErrorDataset()));
				if (dataErrors != null) {
					checkDataAndAuxillaryDataMatch(input, dataErrors);
					input.setErrors(dataErrors);
				}
			} catch (Exception e) {
				// do nothing, add no error metadata
			}
		}

		
		return new OperationData(input);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertSampleMetadataOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}
