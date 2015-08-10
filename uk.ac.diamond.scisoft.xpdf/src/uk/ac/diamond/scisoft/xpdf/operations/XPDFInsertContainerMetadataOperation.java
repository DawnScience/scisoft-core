/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFContainerMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFContainersMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetAbstractGeometryMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetComponentMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetCylinderMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetFormMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetPlateMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTraceMetadataImpl;

/**
 * Add a container to the target 
 */
public class XPDFInsertContainerMetadataOperation extends
		AbstractOperation<XPDFInsertContainerMetadataModel, OperationData> {

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
		boolean is_up = model.isUpstream();
		boolean is_down = model.isDownstream();
		
		geomMeta.setDistances(inner, outer);
		geomMeta.setStreamality(is_up, is_down);
				
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
		IDataset contTrace = ProcessingUtils.getLazyDataset(this, xyFilePath, "Column_2").getSlice();
		// The counting time and monitor relative flux are set directly on the
		// input Dataset, since they pertain to the data it holds
		XPDFTraceMetadataImpl containerTraceMeta = new XPDFTraceMetadataImpl();
		containerTraceMeta.setCountingTime(model.getCountingTime());
		containerTraceMeta.setMonitorRelativeFlux(model.getMonitorRelativeFlux());
		containerTraceMeta.setTrace(contTrace);

		compMeta.setTrace(containerTraceMeta);		

		compMeta.setSample(false);

		// compMeta is complete. Add it to the list of containers in input

		XPDFContainerMetadata containerList;
		try {
			List<XPDFContainerMetadata> containerListList = input.getMetadata(XPDFContainerMetadata.class);
			if (containerListList != null && !containerListList.isEmpty()) {
				containerList = containerListList.get(0);
			} else {
				containerList = new XPDFContainersMetadataImpl();
			}
		} catch (Exception e) {
			containerList = new XPDFContainersMetadataImpl();
		} 
		// containerList should now be a valid ContainerXPDFMetadata implementation
		// Add the new container to the cloned list
		containerList.addContainer(compMeta);
		// remove the existing list of containers, if any
		input.clearMetadata(XPDFContainerMetadata.class);
		// add the new list of containers
		input.setMetadata(containerList);
		
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
