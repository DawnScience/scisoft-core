/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import java.io.Serializable;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.function.Centroid;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

@Atomic
public class CentroidOperation extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.CentroidOperation";
	}

	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) {
		
		Centroid c = new Centroid();
		List<Double> value = c.value(dataset);
		Dataset x = DatasetFactory.createFromObject(new double[]{value.get(1).doubleValue()});
		x.setName("centroid_x");
		Dataset y = DatasetFactory.createFromObject(new double[]{value.get(0).doubleValue()});
		y.setName("centroid_y");
		
		return new OperationData(dataset, new Serializable[]{x,y});
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
