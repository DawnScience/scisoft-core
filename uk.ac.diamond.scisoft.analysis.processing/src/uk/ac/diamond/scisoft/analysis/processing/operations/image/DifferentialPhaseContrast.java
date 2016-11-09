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

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.impl.function.Centroid;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

@Atomic
public class DifferentialPhaseContrast extends AbstractOperation<DifferentialPhaseContrastModel, OperationData> {
	/**
	 * Idea is that user can specify a pixel size and detector distance and the 
	 * result comes back in radians
	 */
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.DifferentialPhaseContrast";
	}

	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) {
		
		Centroid c = new Centroid();
		List<Double> value = c.value(dataset);
		double[] outx = new double[]{value.get(1).doubleValue()};
		Dataset outx1 = Maths.arctan(Maths.multiply(outx,model.getPix()/model.getDist()));
		double[] outy = new double[]{value.get(1).doubleValue()};
		Dataset outy1 = Maths.arctan(Maths.multiply(outy,model.getPix()/model.getDist()));
		
		Dataset x = DatasetFactory.createFromObject(outx1);
		x.setName("dpc_x");
		Dataset y = DatasetFactory.createFromObject(outy1);
		y.setName("dpc_y");
		
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
