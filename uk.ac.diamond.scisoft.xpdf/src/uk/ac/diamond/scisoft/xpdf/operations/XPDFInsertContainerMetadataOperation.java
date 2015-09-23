/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentPlate;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Add the metadata for a container to the XPDF metadata.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
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
		String shape = model.getShape();

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFComponentCylinder();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFComponentPlate();
		}
		// Read size data from the Model
		double inner = model.getInner();
		double outer = model.getOuter();
		boolean is_up = model.isUpstream();
		boolean is_down = model.isDownstream();
		
		geomMeta.setDistances(inner, outer);
		geomMeta.setStreamality(is_up, is_down);
				
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
		Dataset contTrace = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, "Column_2"));

		// Error metadata for the trace
		boolean isErrorData = true;
		try {
			xyFilePath = model.getErrorFilePath();
		} catch (Exception e) {
			// If file not found, then unset isErrorData
			isErrorData = false;
		}
		if (isErrorData && xyFilePath != null) {
			Dataset contErrors = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, "Column_2"));
			if (contErrors != null) {
				contTrace.setError(contErrors);
			}
		}

		
		
		// The counting time and monitor relative flux are set directly on the
		// input Dataset, since they pertain to the data it holds
		XPDFBeamTrace containerTraceMeta = new XPDFBeamTrace();
		containerTraceMeta.setCountingTime(model.getCountingTime());
		containerTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		containerTraceMeta.setTrace(contTrace);
		// Assumes the axis is the sample as the experimental data, if present.
		if (input.getFirstMetadata(XPDFMetadata.class) != null && 
				input.getFirstMetadata(XPDFMetadata.class).getSample() != null )
			containerTraceMeta.setAxisAngle(input.getFirstMetadata(XPDFMetadata.class).getSample().getTrace().isAxisAngle());

		compMeta.setTrace(containerTraceMeta);		

		compMeta.setSample(false);

		// compMeta is complete. Add it to the list of containers in input
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		// add the container to the metadata
		theXPDFMetadata.addContainer(compMeta);
		
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
