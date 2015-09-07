/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation;

public class XPDFCalibration {

	LinkedList<Double> calibrationConstants;
	XPDFQSquaredIntegrator qSquaredIntegrator;
	double selfScatteringDenominator;
	Dataset multipleScatteringCorrection;
	double nSampleIlluminatedAtoms;
	ArrayList<Dataset> backgroundSubtracted;
	XPDFAbsorptionMaps absorptionMaps; 
	
	public XPDFCalibration() {
		calibrationConstants = new LinkedList<Double>();
		qSquaredIntegrator = null;
		selfScatteringDenominator = 1.0;
		multipleScatteringCorrection = null;
		nSampleIlluminatedAtoms = 1.0; // there must be at least one
		backgroundSubtracted = new ArrayList<Dataset>();
	}
	
	public XPDFCalibration(XPDFCalibration inCal) {
		if (inCal.calibrationConstants != null) {
			this.calibrationConstants = new LinkedList<Double>();
			for (double c3 : inCal.calibrationConstants)
				this.calibrationConstants.add(c3);
		}
		this.qSquaredIntegrator = (this.qSquaredIntegrator != null) ? inCal.qSquaredIntegrator : null;
		this.selfScatteringDenominator = inCal.selfScatteringDenominator;
		this.multipleScatteringCorrection = (inCal.multipleScatteringCorrection != null) ? new DoubleDataset(inCal.multipleScatteringCorrection) : null;
		this.nSampleIlluminatedAtoms = inCal.nSampleIlluminatedAtoms;
		if (inCal.backgroundSubtracted != null) {
			this.backgroundSubtracted = new ArrayList<Dataset>();
			for (Dataset data : inCal.backgroundSubtracted) {
				this.backgroundSubtracted.add(data);
			}
		}
	}

	public double getCalibrationConstant() {
		return calibrationConstants.getLast();
	}

	public void setInitialCalibrationConstant(double calibrationConstant) {
		this.calibrationConstants.add(calibrationConstant);
	}

	public void setqSquaredIntegrator(XPDFQSquaredIntegrator qSquaredIntegrator) {
		this.qSquaredIntegrator = new XPDFQSquaredIntegrator(qSquaredIntegrator);
	}

	// Difference of the Krogh-Moe sum and integral of Thomson self-scattering for the sample
	public void setSelfScatteringDenominatorFromSample(XPDFTargetComponent sample, Dataset twoTheta) {
		selfScatteringDenominator = qSquaredIntegrator.ThomsonIntegral(sample.getSelfScattering(twoTheta))
				- sample.getKroghMoeSum();
	}

	public void setMultipleScatteringCorrection(Dataset multipleScatteringCorrection) {
		this.multipleScatteringCorrection = new DoubleDataset(multipleScatteringCorrection);
	}

	public void setSampleIlluminatedAtoms(double nSampleIlluminatedAtoms) {
		this.nSampleIlluminatedAtoms = nSampleIlluminatedAtoms;
	}

	public void setBackgroundSubtracted(List<Dataset> backgroundSubtracted) {
		this.backgroundSubtracted = new ArrayList<Dataset>();
		for (Dataset data : backgroundSubtracted)
			this.backgroundSubtracted.add(data);
	}

	public void setAbsorptionMaps(XPDFAbsorptionMaps absorptionMaps) {
		this.absorptionMaps = new XPDFAbsorptionMaps(absorptionMaps);
	}
	
	public Dataset iterate() {
		// Divide by the calibration constant and subtract the multiple scattering correction
		List<Dataset> calCon = new ArrayList<Dataset>();
		for (Dataset componentTrace : backgroundSubtracted)
			calCon.add(Maths.divide(componentTrace, calibrationConstants.getLast()));
		
		// Mulcor should be a LinkedList, so that we can get the last element simply
		List<Dataset> mulCor = new ArrayList<Dataset>();
		if (multipleScatteringCorrection != null) {
			for (Dataset componentTrace : calCon)
				mulCor.add(Maths.subtract(componentTrace, multipleScatteringCorrection));
		} else {
			for (Dataset componentTrace : calCon)
				mulCor.add(Maths.subtract(componentTrace, 0));
		}
		Dataset absCor = applyCalibrationConstant(mulCor);

		// TODO: Add this back when the real calculations are done
		absCor.idivide(nSampleIlluminatedAtoms);

		// Integrate
		double numerator = qSquaredIntegrator.ThomsonIntegral(absCor);
		// Divide by denominator
		double aMultiplier = numerator/selfScatteringDenominator;
		// Make the new calibration constant
		double updatedCalibration = aMultiplier * calibrationConstants.getLast();
		// Add to the list
		calibrationConstants.add(updatedCalibration);

		return absCor;

	}
	
	private Dataset applyCalibrationConstant(List<Dataset> mulCor) {
		// Use size and indexing, rather than any fancy-pants iterators or such
		int nComponents = mulCor.size();
		
		// need to retain the original data for the next time around the loop,
		// so copy to absorptionTemporary, an ArrayList.
		List<Dataset> absorptionTemporary = new ArrayList<Dataset>();
		for (Dataset data : mulCor) {
			absorptionTemporary.add(new DoubleDataset(data));
		}
		
		// The objects are ordered outwards; 0 is the sample, nComponents-1 the
		// outermost container
		// The scatterers loop over all containers, but not the sample
		for (int iScatterer = nComponents-1; iScatterer > 0; iScatterer--){
			// The innermost absorber here considered. Does not include the
			// Scatterer, but does include the sample 
			for (int iInnermostAbsorber = iScatterer - 1; iInnermostAbsorber >= 0; iInnermostAbsorber--){
				// A product of absorption maps from (but excluding) the
				// scatterer, to (and including) the innermost absorber
				// Set the initial term of the partial product to be the absorber
				Dataset subsetAbsorptionCorrection = this.absorptionMaps.getAbsorptionMap(iScatterer, iInnermostAbsorber);
				for (int iAbsorber = iInnermostAbsorber+1; iAbsorber < iScatterer; iAbsorber++) 
					subsetAbsorptionCorrection.imultiply(this.absorptionMaps.getAbsorptionMap(iScatterer, iAbsorber));
				// Subtract the scattered radiation, corrected for absorption, from the attenuator's radiation
				absorptionTemporary.get(iInnermostAbsorber).isubtract(
						Maths.divide(
								absorptionTemporary.get(iScatterer),
								subsetAbsorptionCorrection.reshape(subsetAbsorptionCorrection.getSize())));
			}
		}
		
		// start with sample self-absorption
		Dataset absorptionCorrection = this.absorptionMaps.getAbsorptionMap(0, 0);
		for (int iAbsorber = 1; iAbsorber < nComponents; iAbsorber++)
			absorptionCorrection.imultiply(absorptionMaps.getAbsorptionMap(0, iAbsorber));
		
		Dataset absCor = Maths.divide(absorptionTemporary.get(0), absorptionCorrection.reshape(absorptionCorrection.getSize()));
		
		return absCor;
	}
}
