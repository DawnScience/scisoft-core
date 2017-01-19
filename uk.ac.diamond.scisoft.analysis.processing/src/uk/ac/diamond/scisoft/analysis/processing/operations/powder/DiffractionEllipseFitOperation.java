/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IParametricROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalFitROI;
import org.eclipse.dawnsci.analysis.dataset.roi.PolylineROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

import uk.ac.diamond.scisoft.analysis.diffraction.DSpacing;
import uk.ac.diamond.scisoft.analysis.diffraction.PeakFittingEllipseFinder;
import uk.ac.diamond.scisoft.analysis.diffraction.PowderRingsUtils;

public class DiffractionEllipseFitOperation extends AbstractOperation<DiffractionEllipseFitModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.powder.DiffractionEllipseFitOperation";
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		double d = (2*Math.PI)/model.getqValue();
		double p = (2*Math.PI)/(model.getqValue()+model.getqDelta());
		double m = (2*Math.PI)/(model.getqValue()-model.getqDelta());
		
		IDiffractionMetadata dm = getFirstDiffractionMetadata(input);
		if (dm == null) throw new OperationException(this, "No calibration information!");
		
		IParametricROI[] inOut = new IParametricROI[2];
		
		IParametricROI conic = (IParametricROI)DSpacing.conicFromDSpacing(dm.getDetector2DProperties(), dm.getDiffractionCrystalEnvironment(), d);
		inOut[0] = (IParametricROI)DSpacing.conicFromDSpacing(dm.getDetector2DProperties(), dm.getDiffractionCrystalEnvironment(), m);
		inOut[1] =(IParametricROI)DSpacing.conicFromDSpacing(dm.getDetector2DProperties(), dm.getDiffractionCrystalEnvironment(), p);
		
		PolylineROI points = PeakFittingEllipseFinder.findPointsOnConic(DatasetUtils.convertToDataset(input), null, conic, inOut, 256, null);
		
		double rms = -1;
		double[] semi = new double[2];
		double[] point = new double[2];
		double ang = 0;
		
		if (points != null && points.getNumberOfPoints() > 3) {

			EllipticalFitROI efroi = PowderRingsUtils.fitAndTrimOutliers(null, points, 2, false);
			rms = efroi.getRMS();
			semi = efroi.getSemiAxes();
			point = efroi.getPoint();
			ang = efroi.getAngleDegrees();
			
		}
		
		Dataset r = DatasetFactory.createFromObject(new double[]{rms});
		r.setName("rms");
		
		Dataset ax = DatasetFactory.createFromObject(semi);
		ax.setName("semi-axes");
		
		Dataset po = DatasetFactory.createFromObject(point);
		po.setName("centre");
		
		Dataset a = DatasetFactory.createFromObject(new double[]{ang});
		a.setName("angle");

		return new OperationData(input, new Serializable[]{r,ax,po,a});
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
