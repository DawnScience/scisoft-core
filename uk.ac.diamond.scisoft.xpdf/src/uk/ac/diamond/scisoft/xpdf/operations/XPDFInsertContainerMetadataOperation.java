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
import org.eclipse.january.DatasetException;
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
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Add the metadata for a container to the XPDF metadata.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFInsertContainerMetadataOperation extends
		XPDFInsertXMetadataOperation<XPDFInsertContainerMetadataModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {
		XPDFTargetComponent compMeta = new XPDFTargetComponent();
		XPDFComponentForm formMeta = new XPDFComponentForm();
		XPDFComponentGeometry geomMeta = null;

		// Read shape from the Model
		XPDFGeometryEnum shape = model.getShape();

		switch (shape) {
		case CYLINDER:
			geomMeta = new XPDFComponentCylinder();
			break;
		case PLATE:
			geomMeta = new XPDFComponentPlate();
			break;
		case DEFINED_BY_CONTAINER:
		default:
			throw new OperationException(this, "Containers must have a defined shape");	
		}

		// Read size data from the Model
		double inner = model.getInner();
		double outer = model.getOuter();
		boolean is_up = model.isUpstream();
		boolean is_down = model.isDownstream();
		double roll = model.getContainerAngle();
		
		geomMeta.setDistances(inner, outer);
		geomMeta.setStreamality(is_up, is_down);
		geomMeta.setEulerAnglesinDegrees(0, 0, roll);
		
		// Get the material data from the Model
		String material = model.getMaterial();
		double density = model.getDensity();
		double packingFraction = model.getPackingFraction();
		
		formMeta.setMatName(material);
		formMeta.setDensity(density);
		formMeta.setPackingFraction(packingFraction);
		formMeta.setGeom(geomMeta);

		compMeta.setForm(formMeta);

		// Get sample name from the Model
		String name = model.getContainerName();

		compMeta.setName(name);

		// Trace metadata
		String xyFilePath = "";
		try {
			xyFilePath = model.getFilePath();
		} catch (Exception e) {
			throw new OperationException(this, "Could not find " + xyFilePath);
		}

		// Load the container trace from the designated xy file
		if (model.getDataset().length() <= 0) throw new OperationException(this, "Undefined dataset");
		Dataset contTrace;
		try {
			contTrace = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, model.getDataset()));
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		// the container data shouldn't have extraneous dimensions
		contTrace.squeezeEnds();
		checkDataAndAuxillaryDataMatch(input, contTrace);
		
		try {
			if (model.getErrorDataset().length() > 0) throw new OperationException(this, "Undefined error dataset");
			Dataset contErrors = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(this, model.getErrorFilePath(), model.getErrorDataset()));
			if (contErrors != null) {
				checkDataAndAuxillaryDataMatch(contTrace, contErrors);
				contTrace.setErrors(contErrors);
			}
		} catch (OperationException e) {
			// catch and ignore; add no errors to the Dataset.
		} catch (DatasetException e) {
			// catch and ignore; add no errors to the Dataset.
		}
		
		// The counting time and monitor relative flux are set directly on the
		// input Dataset, since they pertain to the data it holds
		XPDFBeamTrace containerTraceMeta = new XPDFBeamTrace();
		containerTraceMeta.setCountingTime(model.getCountingTime());
		containerTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		containerTraceMeta.setTrace(contTrace);
		// Assumes the axis is the same as the experimental data, if present.
		if (input.getFirstMetadata(XPDFMetadata.class) != null && 
				input.getFirstMetadata(XPDFMetadata.class).getSampleTrace() != null )
			containerTraceMeta.setAxisAngle(input.getFirstMetadata(XPDFMetadata.class).getSampleTrace().isAxisAngle());

		compMeta.setSample(false);

		// compMeta is complete. Add it to the list of containers in input
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		// add the container to the metadata
		theXPDFMetadata.addContainer(compMeta);
		theXPDFMetadata.setContainerTrace(compMeta, containerTraceMeta);
		
		if (model.getIncoherentScatteringPath() != null && model.getIncoherentScatteringPath().length() > 0) {
			String iScatterPath = model.getIncoherentScatteringPath();
			String dataset = "/entry1/data/data";
			
			Dataset iScatterData = DatasetUtils.convertToDataset(ProcessingUtils.getDataset(this, iScatterPath, dataset));
			
			theXPDFMetadata.pushIncoherentScattering(iScatterData);
		}

		input.setMetadata(theXPDFMetadata);
		
		return new OperationData(input);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertContainerMetadataOperation";
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
