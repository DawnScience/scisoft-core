/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;

/**
 * Operation that allows model to have null fields which are then set when processing
 */
public class DefaultAutoOperation extends Junk1Dto1DOperationBase<DefaultAutoModel> {

	private String opFile;
	private IRectangularROI opRoiA;
	private Double opValue;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.DefaultAutoOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public String getName(){
		return "DefaultAutoOperation";
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();

		Dataset in = DatasetUtils.convertToDataset(input);
		int length = in.getShapeRef()[0];
		if (si.isFirstSlice()) {
			opFile = model.getFile();
			if (opFile == null) {
				opFile = "test.dat";
				addConfiguredField("file", opFile);
			}

			opRoiA = model.getRoiA();
			if (opRoiA == null) {
				opRoiA = new RectangularROI(length, 0);
				addConfiguredField("roiA", opRoiA);
			}

			opValue = model.getValue();
			if (opValue == null) {
				opValue = in.min(true).doubleValue();
				addConfiguredField("value", opValue);
			}
		}


		OperationData d = super.process(input, monitor);
		d.getData().setMetadata(ssm);

		length = opRoiA.getIntLength(0);
		int start = opRoiA.getIntPoint()[0];
		Dataset out =  DatasetUtils.convertToDataset(d.getData().getSlice(new Slice(start, start + length)));
		out.isubtract(opValue);

		OperationData od = new OperationData(out);
		if (si.isFirstSlice()) {
			setConfiguredFields(od);
		}

		return od;
	}

	public Double getValue() {
		return opValue;
	}
}
