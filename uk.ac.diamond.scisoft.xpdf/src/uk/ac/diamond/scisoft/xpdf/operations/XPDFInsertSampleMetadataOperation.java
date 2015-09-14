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
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentPlate;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Insert the sample properties metadata into the XPDF metadata.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFInsertSampleMetadataOperation extends XPDFInsertXMetadataOperation<XPDFInsertSampleMetadataModel, OperationData> {

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
		// samples have no streamality

		geomMeta.setDistances(inner, outer);

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
		String name = model.getSampleName();

		compMeta.setName(name);

		// Counting time and relative flux of the trace in the main Dataset
		XPDFBeamTrace sampleTraceMeta = new XPDFBeamTrace();
		sampleTraceMeta.setCountingTime(model.getCountingTime());
		sampleTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		sampleTraceMeta.setTrace(null);
		
		compMeta.setTrace(sampleTraceMeta);		
		
		compMeta.setSample(true);
		
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		theXPDFMetadata.setSampleData(compMeta);
		
		input.setMetadata(theXPDFMetadata);
		
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
