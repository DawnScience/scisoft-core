/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class TextureFromCakeOperation extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.TextureFromCakeOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		AxesMetadata md = input.getFirstMetadata(AxesMetadata.class);
		
		if (md == null) throw new OperationException(this, "Input data has no axes");
		
		ILazyDataset[] axes = md.getAxes();
		
		if (axes.length != 2) throw new OperationException(this, "Unexpected number of axes");
		
		if (axes[0] == null || axes[1] == null) throw new OperationException(this, "Both dimensions must have axes (is the input data caked?)");
		
		Dataset angle = null;
		Dataset q = null;
		
		try {
			angle = DatasetUtils.sliceAndConvertLazyDataset(axes[0]);
			q = DatasetUtils.sliceAndConvertLazyDataset(axes[1]);
		} catch (DatasetException e) {
			throw new OperationException(this, "Could not slice axes");
		}
		
		if (q == null || angle == null) throw new OperationException(this, "Could not slice axes");
		
		Dataset in = DatasetUtils.convertToDataset(input);
		
		Dataset intensity_sum = in.sum(0, true);
		
		Dataset cosAngle = Maths.cos(Maths.toRadians(angle));
		
		Dataset cosIn = Maths.multiply(in, cosAngle);
		
		Dataset weighted_intensity_sum = cosIn.sum(0, true);
		
		double sumAngle = ((Number) cosAngle.sum()).doubleValue();
		
		weighted_intensity_sum.idivide(sumAngle);
		intensity_sum.idivide(in.getShape()[0]);
		weighted_intensity_sum.idivide(intensity_sum).isubtract(1);
		weighted_intensity_sum.setName("Texture");
		Dataset q_new = q.getSlice().squeeze();
		
		try {
			AxesMetadata axmd = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			axmd.addAxis(0, q_new);
			weighted_intensity_sum.setMetadata(axmd);
		} catch (MetadataException e) {
			throw new OperationException(this, "Could not create metadata!");
		}
		
		return new OperationData(weighted_intensity_sum);
	}

}
