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
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTraceMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetAbstractGeometryMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetComponentMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetCylinderMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetFormMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetPlateMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTraceMetadataImpl;

public class XPDFInsertSampleMetadataOperation extends AbstractOperation<XPDFInsertSampleMetadataModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFTargetComponentMetadataImpl compMeta = new XPDFTargetComponentMetadataImpl();
		XPDFTargetFormMetadataImpl formMeta = new XPDFTargetFormMetadataImpl();
		XPDFTargetAbstractGeometryMetadataImpl geomMeta = null;

		// Read shape from the Model
		String shape = model.getShape();

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFTargetCylinderMetadataImpl();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFTargetPlateMetadataImpl();
		}
		// Read size data from the Model
		double inner = model.getInner();
		double outer = model.getOuter();
		// samples have no streamality

		geomMeta.setDistances(inner, outer);

		// Get the material data from the Model
		String material = model.getMaterial();
		double density = model.getDensity();
		double packingFraction = model.getPackingFraction();

		formMeta.setMaterialName(material);
		formMeta.setDensity(density);
		formMeta.setPackingFraction(packingFraction);
		formMeta.setGeometry(geomMeta);

		compMeta.setForm(formMeta);

		// Get sample name from the Model
		String name = model.getSampleName();

		compMeta.setName(name);

		// Counting time and relative flux of the trace in the main Dataset
		XPDFTraceMetadataImpl sampleTraceMeta = new XPDFTraceMetadataImpl();
		sampleTraceMeta.setCountingTime(model.getCountingTime());
		sampleTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		sampleTraceMeta.setTrace(null);
		
		compMeta.setTrace(sampleTraceMeta);		
		
		compMeta.setSample(true);
		
		input.setMetadata(compMeta);
		
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
