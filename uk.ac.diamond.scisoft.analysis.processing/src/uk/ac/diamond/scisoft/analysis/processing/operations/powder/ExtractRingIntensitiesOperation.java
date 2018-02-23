/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class ExtractRingIntensitiesOperation extends AbstractOperation<DiffractionEllipseFitModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.ExtractRingIntensitiesOperation";
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
		
		IDiffractionMetadata dm = getFirstDiffractionMetadata(input);
		if (dm == null) throw new OperationException(this, "No calibration information!");
		
		Dataset[] qAz = getCoordData(dm, input.getShape());
		
		double min = model.getqValue() - model.getqDelta();
		double max = model.getqValue() + model.getqDelta();
		
		Dataset q = qAz[0];
		Dataset tth = qAz[1];
		
		IndexIterator it = q.getIterator();
		
		int count = 0;
		
		while (it.hasNext()) {
			double v = q.getElementDoubleAbs(it.index);
			
			if (v >= min && v <= max) {
				count++;
			}
		}
		
		DoubleDataset qs = DatasetFactory.zeros(DoubleDataset.class, new int[] {count});
		qs.setName("q");
		DoubleDataset tths = DatasetFactory.zeros(DoubleDataset.class, new int[] {count});
		tths.setName("azimuthal angle");
		DoubleDataset sig = DatasetFactory.zeros(DoubleDataset.class, new int[] {count});
		sig.setName("intensity");
		
		Dataset in = DatasetUtils.convertToDataset(input);
		
		it = in.getIterator();
		
		count = 0;
		int innerCount = 0;
		
		while (it.hasNext()) {
			double i = in.getElementDoubleAbs(it.index);
			double v = q.getElementDoubleAbs(count);
			double t = tth.getElementDoubleAbs(count);
			
			if (v >= min && v <= max) {
				qs.setAbs(innerCount, v);
				tths.setAbs(innerCount, t);
				sig.setAbs(innerCount, i);
				innerCount++;
			}
			count++;
		}
		
		try {
			AxesMetadata m = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			m.addAxis(0, qs);
			m.addAxis(0, tths);
			sig.addMetadata(m);
		} catch (MetadataException e) {
			throw new OperationException(this, "Could not generate axes metadata");
		}
		
		return new OperationData(sig);
	}

	private Dataset[] getCoordData(IDiffractionMetadata dm, int[] shape) {
		Dataset qArray = PixelIntegrationUtils.generateRadialArray(shape, new QSpace(dm.getDetector2DProperties(), dm.getDiffractionCrystalEnvironment()), XAxis.Q);
		Dataset az = PixelIntegrationUtils.generateAzimuthalArray(shape,dm,false);
		
		return new Dataset[] {qArray,az};
	}

}
