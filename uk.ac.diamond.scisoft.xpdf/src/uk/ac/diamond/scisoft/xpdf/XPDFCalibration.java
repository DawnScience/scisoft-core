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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

/**
 * A class for holding the information to calibrate the XPDF data.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 */
public class XPDFCalibration {

	private LinkedList<Double> calibrationConstants;
	private Double calibrationConstant0;
	private XPDFQSquaredIntegrator qSquaredIntegrator;
	private double selfScatteringDenominator;
	private Dataset multipleScatteringCorrection;
	private double nSampleIlluminatedAtoms;
	private ArrayList<Dataset> backgroundSubtracted;
	private XPDFAbsorptionMaps absorptionMaps;
	private XPDFCoordinates coords;
	private XPDFDetector tect;
	private XPDFBeamData beamData;
	private Dataset sampleFluorescence;
	private double fluorescenceScale;
	private Dataset sampleSelfScattering;

	private int dataDimensions;
	
	// Perform any kind of fluorescence correction
	private boolean doFluorescence;
	// Perform the full fluorescence calibration, calculating the optimum fluorescence scale
	private boolean doFluorescenceCalibration;

	// caching angular factors
	Map<XPDFCoordinates, Dataset> cachedDeTran, cachedPolar;
	
	/**
	 * Empty constructor.
	 */
	public XPDFCalibration() {
		calibrationConstants = new LinkedList<Double>();
		qSquaredIntegrator = null;
		selfScatteringDenominator = 1.0;
		multipleScatteringCorrection = null;
		nSampleIlluminatedAtoms = 1.0; // there must be at least one
		backgroundSubtracted = new ArrayList<Dataset>();
		doFluorescence = true;
		cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
		cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
	}
	
	/**
	 * Copy constructor.
	 * @param inCal
	 * 				calibration object to be copied.
	 */
	public XPDFCalibration(XPDFCalibration inCal) {
		if (inCal.calibrationConstants != null) {
			this.calibrationConstants = new LinkedList<Double>();
			for (double c3 : inCal.calibrationConstants)
				this.calibrationConstants.add(c3);
		}
		this.qSquaredIntegrator = (inCal.qSquaredIntegrator != null) ? inCal.qSquaredIntegrator : null;
		this.selfScatteringDenominator = inCal.selfScatteringDenominator;
		this.multipleScatteringCorrection = (inCal.multipleScatteringCorrection != null) ? inCal.multipleScatteringCorrection.copy(DoubleDataset.class) : null;
		this.nSampleIlluminatedAtoms = inCal.nSampleIlluminatedAtoms;
		if (inCal.backgroundSubtracted != null) {
			this.backgroundSubtracted = new ArrayList<Dataset>();
			for (Dataset data : inCal.backgroundSubtracted) {
				this.backgroundSubtracted.add(data);
			}
		}
		this.tect = (inCal.tect != null) ? new XPDFDetector(inCal.tect): null;
		this.beamData = (inCal.beamData != null) ? new XPDFBeamData(inCal.beamData) : null;
		this.sampleFluorescence = (inCal.sampleFluorescence != null) ? inCal.sampleFluorescence.clone() : null;
		this.fluorescenceScale = inCal.fluorescenceScale;
		this.doFluorescence = inCal.doFluorescence;
		this.dataDimensions = inCal.dataDimensions;
		this.coords = (inCal.coords != null) ? new XPDFCoordinates(inCal.coords) : null;
		this.cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
		this.cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
		this.sampleSelfScattering = (inCal.sampleSelfScattering != null) ? inCal.sampleSelfScattering : null;

	}

	public XPDFCalibration getShallowCopy() {
		XPDFCalibration theCopy = new XPDFCalibration();
		
		theCopy.qSquaredIntegrator = this.qSquaredIntegrator;
		theCopy.selfScatteringDenominator = this.selfScatteringDenominator;
		theCopy.multipleScatteringCorrection = this.multipleScatteringCorrection;
		theCopy.nSampleIlluminatedAtoms = this.nSampleIlluminatedAtoms;
		theCopy.backgroundSubtracted = this.backgroundSubtracted;
		theCopy.tect = this.tect;
		theCopy.beamData = this.beamData;
		theCopy.sampleFluorescence = this.sampleFluorescence;
		theCopy.fluorescenceScale = this.fluorescenceScale;
		theCopy.doFluorescence = this.doFluorescence;
		theCopy.dataDimensions = this.dataDimensions;
		theCopy.coords = this.coords;
		theCopy.absorptionMaps = this.absorptionMaps;
		theCopy.calibrationConstant0 = this.calibrationConstant0;
		theCopy.sampleSelfScattering = this.sampleSelfScattering;
		// in this shallow copy, the caches can even be shared
		theCopy.cachedDeTran = this.cachedDeTran;
		theCopy.cachedPolar = this.cachedPolar;
		
		return theCopy;
	}
	
	/**
	 * Gets the most recent calibration constant from the list.
	 * @return the most recent calibration constant.
	 */
	public double getCalibrationConstant() {
		return calibrationConstants.getLast();
	}

	/**
	 * Sets the initial calibration constant to be iterated.
	 * <p>
	 * Sets the initial calibration constant. If there is already an array,
	 * then a new Array is created.
	 * @param calibrationConstant
	 * 							the initial value of the calibration constant.
	 */
	public void initializeCalibrationConstant(double calibrationConstant) {
//		if (this.calibrationConstants == null || !this.calibrationConstants.isEmpty())
//			this.calibrationConstants = new LinkedList<Double>();
//		this.calibrationConstants.add(calibrationConstant);
		calibrationConstant0 = calibrationConstant;
	}

	/**
	 * Sets up the q² integrator class to use in the calculation of the constants
	 * @param qSquaredIntegrator
	 * 							a properly constructed q² integrator class to
	 * 							be used to integrate scattering in the sample.
	 */
	public void setqSquaredIntegrator(XPDFQSquaredIntegrator qSquaredIntegrator) {
		this.qSquaredIntegrator = new XPDFQSquaredIntegrator(qSquaredIntegrator);
	}

	/**
	 * Calculates the denominator used in calculating the calibration constant.
	 * <p> 
	 * Difference of the Krogh-Moe sum and integral of Thomson self-scattering
	 * for the sample, used as the denominator of the updating factor of the
	 * calibration constant.
	 * @param sample
	 * 				Sample for which the properties should be calculated.
	 * @param coordinates
	 * 					Angles and beam energy at which the properties should be calculated.
	 */
	public void setSelfScatteringDenominatorFromSample(XPDFTargetComponent sample, XPDFCoordinates coordinates) {
		selfScatteringDenominator = qSquaredIntegrator.ThomsonIntegral(sample.getSelfScattering(coordinates))
				- sample.getKroghMoeSum();
	}

	/**
	 * Sets a Dataset for the multiple scattering correction.
	 * <p>
	 * Set the multiple scattering correction. Presently takes a zero Dataset
	 * of the same shape as the angle arrays. 
	 * @param multipleScatteringCorrection
	 * 									A zero Dataset.
	 */
	public void setMultipleScatteringCorrection(Dataset multipleScatteringCorrection) {
		this.multipleScatteringCorrection = multipleScatteringCorrection.copy(DoubleDataset.class);
	}

	/**
	 * Sets the number of atoms illuminated in the sample.
	 * @param nSampleIlluminatedAtoms
	 * 								the number of atoms illuminated in the sample.
	 */
	public void setSampleIlluminatedAtoms(double nSampleIlluminatedAtoms) {
		this.nSampleIlluminatedAtoms = nSampleIlluminatedAtoms;
	}

	/**
	 * Sets the list of target component traces with their backgrounds subtracted.
	 * @param backgroundSubtracted
	 * 							A List of Datasets containing the ordered sample and containers.
	 */
	public void setBackgroundSubtracted(List<Dataset> backgroundSubtracted) {
		this.backgroundSubtracted = new ArrayList<Dataset>();
		for (Dataset data : backgroundSubtracted)
			this.backgroundSubtracted.add(data);
	}

	/**
	 * Sets the absorption maps.
	 * <p>
	 * Set the absorption maps object that holds the maps between the target
	 * components stored in the list of background subtracted traces. The
	 * ordinals used in the list and used to index the maps. 
	 * @param absorptionMaps
	 * 						The absorption maps in their holding class.
	 */
	public void setAbsorptionMaps(XPDFAbsorptionMaps absorptionMaps) {
		this.absorptionMaps = new XPDFAbsorptionMaps(absorptionMaps);
	}
	
	/**
	 * Sets the coordinates over which to calibrate the data.
	 * @param inCoords
	 * 				the {@link XPDFCoordinates} object to be used.
	 */
	public void setCoordinates(XPDFCoordinates inCoords) {
		this.coords = inCoords;
		this.dataDimensions = inCoords.getTwoTheta().getShape().length;
	}
	
	/**
	 * Sets the detector data class to calibrate against.
	 * @param inTect
	 * 			the {@link XPDFDetector} object to be used.
	 */
	public void setDetector(XPDFDetector inTect) {
		this.tect = inTect;
	}
	
	/**
	 * Sets the class which describes the incoming x-ray beam.
	 * @param inBeam
	 * 				the {@link XPDFBeamData} object to be used.
	 */
	public void setBeamData(XPDFBeamData inBeam) {
		this.beamData = inBeam;
	}
	
	/**
	 * Sets the fluorescence at the detector on the same coordinates as the data.
	 * @param sampleFluor
	 * 					the {@link Dataset} containing the fluorescence data 
	 */
	public void setSampleFluorescence(Dataset sampleFluor) {
		this.sampleFluorescence = sampleFluor;
	}
	
	/**
	 * Sets the target component which of the sample 
	 * @param sample
	 * 				the {@link XPDFTargetComponent} which will provide the sample self-scattering.
	 */
	public void setSelfScattering(XPDFTargetComponent sample) {
		this.sampleSelfScattering = sample.getSelfScattering(coords);
	}
	
	/**
	 * Iterates the calibration constant for five iterations.
	 * <p>
	 * Perform the iterations to converge the calibration constant of the data.
	 * The steps performed are:
	 * <ul>
	 * <li>divide by the old calibration constant
	 * <li>apply the multiple scattering correction
	 * <li>apply the calibration constant
	 * <li>divide by the number of atoms contributing to the signal to produce the absorption corrected data
	 * <li>calculate the ratio of the new to old calibration constants
	 * </ul>
	 * @return the absorption corrected data
	 */
	private Dataset iterate(List<Dataset> fluorescenceCorrected, boolean propagateErrors) {
		// Detector transmission correction
		Dataset transmissionCorrection;
		if (!cachedDeTran.containsKey(coords)) {
			transmissionCorrection = tect.getTransmissionCorrection(coords.getTwoTheta(), beamData.getBeamEnergy());
			cachedDeTran.put(coords, transmissionCorrection);
		} else {
			transmissionCorrection = cachedDeTran.get(coords);
		}
		
		List<Dataset> deTran = new ArrayList<Dataset>();
		for (Dataset componentTrace : fluorescenceCorrected) {
			Dataset deTranData = Maths.multiply(componentTrace, transmissionCorrection);
			// Error propagation
			if (propagateErrors && componentTrace.getError() != null)
				deTranData.setError(Maths.multiply(componentTrace.getError(), transmissionCorrection));
			deTran.add(deTranData);
		}
		
		// Divide by the calibration constant and subtract the multiple scattering correction
		List<Dataset> calCon = new ArrayList<Dataset>();
		for (Dataset componentTrace : deTran) {
			Dataset calConData = Maths.divide(componentTrace, calibrationConstants.getLast()); 
			// Error propagation
			if (propagateErrors && componentTrace.getError() != null)
				calConData.setError(Maths.divide(componentTrace.getError(), calibrationConstants.getLast()));
			// Set the data
			calCon.add(calConData);
		}

		boolean doMultipleScattering = false;
		Dataset absCor;
		if (doMultipleScattering) {
			// Mulcor should be a LinkedList, so that we can get the last element simply
			List<Dataset> mulCor = new ArrayList<Dataset>();
			if (multipleScatteringCorrection != null) {
				for (Dataset componentTrace : calCon) {
					Dataset mulCorData = Maths.subtract(componentTrace, multipleScatteringCorrection);
					// Error propagation: no change if the multiple scattering correction is taken as exact
					if (propagateErrors && componentTrace.getError() != null)
						mulCorData.setError(componentTrace.getError());
					mulCor.add(mulCorData);
				}
			} else {
				for (Dataset componentTrace : calCon) {
					Dataset mulCorData = Maths.subtract(componentTrace, 0);
					//Error propagation
					if (propagateErrors && componentTrace.getError() != null)
						mulCorData.setError(componentTrace.getError());
					mulCor.add(mulCorData);
				}
			}
			absCor = applyCalibrationConstant(mulCor);
		} else {
			absCor = applyCalibrationConstant(calCon);
		}

		absCor.idivide(nSampleIlluminatedAtoms);
		if (propagateErrors && absCor.getError() != null)
			absCor.getError().idivide(nSampleIlluminatedAtoms);

		Dataset absCorP = applyPolarizationConstant(absCor);
		
		// Integrate
		double numerator = qSquaredIntegrator.ThomsonIntegral(absCorP);
		// Divide by denominator
		double aMultiplier = numerator/selfScatteringDenominator;
		// Make the new calibration constant
		double updatedCalibration = aMultiplier * calibrationConstants.getLast();
		// Add to the list
		calibrationConstants.add(updatedCalibration);

		return absCorP;
	}

	/**
	 * Eliminates the scattered radiation from all containers from the data.
	 * <p>
	 * Subtract the scattered radiation from each container from the multiple-
	 * scattering corrected data.
	 * @param mulCor
	 * 				list of the radiation scattered from the full target and
	 * 				each container, ordered innermost outwards, with the sample
	 * 				as the first element. 
	 * @return the absorption corrected sample data.
	 */
	private Dataset applyCalibrationConstant(List<Dataset> mulCor) {
		// Use size and indexing, rather than any fancy-pants iterators or such
		int nComponents = mulCor.size();
		
		// need to retain the original data for the next time around the loop,
		// so copy to absorptionTemporary, an ArrayList.
		List<Dataset> absorptionTemporary = new ArrayList<Dataset>();
		for (Dataset data : mulCor) {
			Dataset absTempData = data.copy(DoubleDataset.class);
			if (data.getError() != null)
				absTempData.setError(data.getError());
			absorptionTemporary.add(absTempData);
		}
		
		// The objects are ordered outwards; 0 is the sample, nComponents-1 the
		// outermost container
		// The scatterers loop over all containers, but not the sample
		for (int iScatterer = nComponents-1; iScatterer > 0; iScatterer--){
			// The innermost absorber here considered. Does not include the
			// Scatterer, but does include the sample 
			for (int iInnermostAbsorber = iScatterer - 1; iInnermostAbsorber >= 0; iInnermostAbsorber--) {
				// A product of absorption maps from (but excluding) the
				// scatterer, to (and including) the innermost absorber
				// Set the initial term of the partial product to be the absorber
				Dataset subsetAbsorptionCorrection = this.absorptionMaps.getAbsorptionMap(iScatterer, iInnermostAbsorber);
				for (int iAbsorber = iInnermostAbsorber+1; iAbsorber < iScatterer; iAbsorber++)
					subsetAbsorptionCorrection = Maths.multiply(subsetAbsorptionCorrection, this.absorptionMaps.getAbsorptionMap(iScatterer, iAbsorber));
//					subsetAbsorptionCorrection.imultiply(this.absorptionMaps.getAbsorptionMap(iScatterer, iAbsorber));
				// Subtract the scattered radiation, corrected for absorption, from the attenuator's radiation
				absorptionTemporary.get(iInnermostAbsorber).isubtract(
//						Maths.divide(
						Maths.multiply(
								absorptionTemporary.get(iScatterer),
//								subsetAbsorptionCorrection.reshape(subsetAbsorptionCorrection.getSize())));
								subsetAbsorptionCorrection.squeeze()));

				// Error propagation. If either is present, then set an error on the result. Non-present errors are taken as zero (exact).
				if (absorptionTemporary.get(iInnermostAbsorber).getError() != null ||
						absorptionTemporary.get(iScatterer).getError() != null) {
					Dataset innerError = (absorptionTemporary.get(iInnermostAbsorber).getError() != null) ?
							absorptionTemporary.get(iInnermostAbsorber).getError() :
							DatasetFactory.zeros(absorptionTemporary.get(iInnermostAbsorber));
					Dataset scatterError = (absorptionTemporary.get(iScatterer).getError() != null) ?
//							Maths.divide(
							Maths.multiply(
									absorptionTemporary.get(iScatterer).getError(),
									subsetAbsorptionCorrection.reshape(subsetAbsorptionCorrection.getSize())) :
//									subsetAbsorptionCorrection.squeeze()) :
									DatasetFactory.zeros(absorptionTemporary.get(iScatterer));
					absorptionTemporary.get(iInnermostAbsorber).setError(Maths.sqrt(Maths.add(Maths.square(innerError), Maths.square(scatterError))));
				}
				
			}
		}
		
		// start with sample self-absorption
		Dataset absorptionCorrection = this.absorptionMaps.getAbsorptionMap(0, 0);
		for (int iAbsorber = 1; iAbsorber < nComponents; iAbsorber++)
			absorptionCorrection = Maths.multiply(absorptionCorrection, absorptionMaps.getAbsorptionMap(0, iAbsorber));
		
//		Dataset absCor = Maths.divide(absorptionTemporary.get(0), absorptionCorrection.reshape(absorptionCorrection.getSize()));
		Dataset absCor = Maths.divide(absorptionTemporary.get(0), absorptionCorrection.getSliceView().squeeze());
		// Error propagation
		if (absorptionTemporary.get(0).getError() != null) {
//			absCor.setError(Maths.divide(absorptionTemporary.get(0).getError(), absorptionCorrection.reshape(absorptionCorrection.getSize())));
			absCor.setError(Maths.divide(absorptionTemporary.get(0).getError(), absorptionCorrection.getSliceView().squeeze()));
		}
		
		return absCor;
	}

	/**
	 * Removes the effect of polarization on the data. 
	 * @param absCor
	 * 				The data uncorrected for the effects of polarization
	 * @return the data corrected for the effects of polarization.
	 */
	private Dataset applyPolarizationConstant(Dataset absCor) {
		final double sineFudge = 0.99;		

		Dataset polCor;
		if (!cachedPolar.containsKey(coords)) {		
			polCor = 
			Maths.multiply(
					0.5,
					Maths.add(
							1,
							Maths.subtract(
									Maths.square(coords.getCosTwoTheta()),
									Maths.multiply(0.5*sineFudge, Maths.square(coords.getSinTwoTheta()))
							)
					)
			);
			cachedPolar.put(coords, polCor);
		} else {
			polCor = cachedPolar.get(coords);
		}
								
		Dataset absCorP = Maths.multiply(absCor, polCor); 
		
		return absCorP; 
	}

	/**
	 * Performs the XPDF calibration.
	 * <p>
	 * Performs the XPDF calibration, including determining the optimum fluorescence scale.
	 * @param nIterations
	 * 					the number of iterations to make to calculate the
	 * 					multiplicative calibration constant.
	 * @return the calibrated XPDF data
	 */
	public Dataset calibrate(int nIterations, int nThreads) {
		if (doFluorescence && doFluorescenceCalibration)
			calibrateFluorescence(nIterations, nThreads);
		Dataset absCor = iterateCalibrate(nIterations, true);
		System.err.println("Fluorescence scale = " + fluorescenceScale);
		System.err.println("Calibration constant = " + calibrationConstants.getLast());
		return absCor;
	}
	
	/**
	 * Performs the calibration iterations.
	 * <p>
	 * Perfoms the part of the calibration following the fluorescence scale determination. 
	 * @param nIterations
	 * 					the number of iterations to make to calculate the
	 * 					multiplicative calibration constant.
	 * @param propagateErrors
	 * 						propagate errors, if they are found
	 * @return the calibrated XPDF data
	 */
	private Dataset iterateCalibrate(int nIterations, boolean propagateErrors) {
		List<Dataset> solAng = new ArrayList<Dataset>();
		for (Dataset targetComponent : backgroundSubtracted) {
			Dataset cosTwoTheta = coords.getCosTwoTheta();
			// result = data /(cos 2θ)^3
			Dataset solAngData = Maths.divide(Maths.divide(targetComponent, cosTwoTheta), Maths.multiply(cosTwoTheta, cosTwoTheta));
//			Dataset solAngData = Maths.multiply(1.0, targetComponent);
			if (propagateErrors && targetComponent.getError() != null)
				solAngData.setError(targetComponent.getError());
			solAng.add(solAngData);
		}
		
		// fluorescence correction
		List<Dataset> fluorescenceCorrected = new ArrayList<Dataset>();
//		for (Dataset targetComponent : backgroundSubtracted) {
		// Only correct fluorescence in the sample itself
		Dataset targetComponent = solAng.get(0);

		if (doFluorescence) {
//			Dataset fluorescenceCorrectedData = Maths.subtract(targetComponent, Maths.multiply(fluorescenceScale, sampleFluorescence.reshape(targetComponent.getSize())));
					Dataset fluorescenceCorrectedData = Maths.subtract(targetComponent, Maths.multiply(fluorescenceScale, sampleFluorescence.squeeze()));
			if (propagateErrors && targetComponent.getError() != null)
				if (sampleFluorescence.getError() != null)
					fluorescenceCorrectedData.setError(
							Maths.sqrt(
									Maths.add(
											Maths.square(targetComponent.getError()),
											Maths.square(sampleFluorescence.getError())
											)
									)
							);
				else
					fluorescenceCorrectedData.setError(targetComponent.getError());
			fluorescenceCorrected.add(fluorescenceCorrectedData);
		} else {
			fluorescenceCorrected.add(targetComponent);
		}
		for (int iContainer = 1; iContainer < backgroundSubtracted.size(); iContainer++)
			fluorescenceCorrected.add(backgroundSubtracted.get(iContainer));
		
		Dataset absCor = null;
		// Initialize the list of calibration constants with the predefined initial value.
		calibrationConstants = new LinkedList<Double>(Arrays.asList(new Double[] {calibrationConstant0}));
		for (int i = 0; i < nIterations; i++)
			absCor = this.iterate(fluorescenceCorrected, propagateErrors);
		return absCor;
	}
	
	/**
	 * Calibrates the fluorescence.
	 * <p>
	 * This method loops over a range of different fluorescence multipliers.
	 * The fluorescence is multiplied by this value, and subtracted from the
	 * normalized data. A smoothed version of the resulting calibrated data is
	 * then compared to the theoretical self-scattering. The multiplier that
	 * gives the smallest difference is used. 
	 * @param nIterations
	 * 					the number of iterations to use in the calibration.
	 */
	private void calibrateFluorescence(int nIterations, int nThreads) {
		
		if (this.sampleFluorescence == null) return;
		
		// Set the fluorescence scales for one and two dimensions
		final double minScale, maxScale, nSteps;
		if (this.dataDimensions == 1) {
			minScale = 1;
			maxScale = 1001;
			nSteps = 100;
		} else {
			minScale = 1;
			maxScale = 601;
			nSteps = 20;
		}
		final double stepScale = (maxScale-minScale)/nSteps;

		boolean doGridded = false;
		
		if (!doGridded) {
			// New bisection solver
			ExecutorService annihilator = Executors.newSingleThreadExecutor();
			double granularity = (maxScale - minScale) / nSteps / 2;
			double xLow = minScale, xHigh = maxScale;
			double fLow = evaluateSingleFluorescence(annihilator, xLow, nIterations),
					fHigh = evaluateSingleFluorescence(annihilator, xHigh, nIterations);
			// If the selected range should not change sign, expand it until it does
			while (Math.signum(fHigh) == Math.signum(fLow)) {
				// Double the range, centred on the same point
				double xDifference = xHigh - xLow;
				xLow = xLow - xDifference / 2;
				xLow = Math.max(0, xLow);
				xHigh = xLow + xDifference * 2;
			
				// Bisection cannot proceed if it cannot find a root, and in
				// this case it does no good to expand the range without limit.
				// If the bisection range exceeds 4× the gridded range, then
				// stop and perform the gridded estimation of the fluorescence
				// constant.
				if ( (xHigh - xLow) > 4*(maxScale - minScale) ) {
					doGridded = true;
					break;
				}
				
				// Calculate the differences at the end points of the expanded range
				Map<Double, Double> differences = evaluateSeveralFluoroScales(
						Arrays.asList(ArrayUtils.toObject(new double[] { xLow, xHigh })), nIterations,
						Math.min(nThreads, 2));
				fLow = differences.get(xLow);
				fHigh = differences.get(xHigh);
				System.err
						.println("Bisection fluoro scales " + Double.toString(xLow) + " to " + Double.toString(xHigh));
			}

			if (!doGridded) {
				boolean doQuadrisection = false;
				double xLinear = xHigh, xLinearLast = xLow;
				// Reduce the range, while maintaining the condition that fHigh and fLow have opposite signs
				while (Math.abs(xLinear - xLinearLast) > granularity) {

					xLinearLast = xLinear;

					double xMid, fMid;

					if (doQuadrisection) {
						// Parallel quadrisection
						double xInterval = (xHigh - xLow) / 4;
						double xQuarter = xLow + xInterval;
						xMid = xQuarter + xInterval;
						double x3Quarters = xHigh - xInterval;
						// Calculate the difference values at the three quarter points
						double[] xes = new double[] { xQuarter, xMid, x3Quarters };
						Map<Double, Double> midScales = evaluateSeveralFluoroScales(
								Arrays.asList(ArrayUtils.toObject(xes)), nIterations, Math.min(nThreads, 3));
						fMid = midScales.get(xMid);

						// Do the first bisection
						if (Math.signum(fMid) == Math.signum(fLow)) {
							xLow = xMid;
							fLow = fMid;
							xMid = x3Quarters;
						} else {
							xHigh = xMid;
							fHigh = fMid;
							xMid = xQuarter;
						}
						fMid = midScales.get(xMid);
					} else {
						// Serial bisection
						xMid = (xHigh + xLow) / 2;
						fMid = evaluateSingleFluorescence(annihilator, xMid, nIterations);
					}

					// Do the bisection
					if (Math.signum(fMid) == Math.signum(fLow)) {
						xLow = xMid;
						fLow = fMid;
					} else {
						xHigh = xMid;
						fHigh = fMid;
					}

					// Calculate the linear interpolation of zero difference
					xLinear = xLow - (xHigh - xLow) / (fHigh - fLow) * fLow;

					System.err.println("Bisection fluoro scales " + Double.toString(xLow) + " to "
							+ Double.toString(xHigh) + ". Linear solution: " + Double.toString(xLinear));
				}
				// Linear interpolation of x over this range
				//		double xZero = xLow - (xHigh - xLow)/(fHigh - fLow) * fLow;
				this.fluorescenceScale = xLinear;
			}
		}
		// Old gridded code
		if (doGridded) {

			Map<Double, Double> scaleToDifference = evaluateSeveralFluoroScales(Arrays.asList(ArrayUtils.toObject(DatasetFactory.createRange(DoubleDataset.class, minScale, maxScale, stepScale).getData())), nIterations, nThreads);
			
			double minimalScale = 0;
			double minimalDifference = Double.POSITIVE_INFINITY; 
			// Get the scale with the minimum difference
			for(Map.Entry<Double, Double> entry : scaleToDifference.entrySet()) {
				System.err.println("F = " + Double.toString(entry.getKey()) + ", C = " + Double.toString(entry.getValue()));
				if (Math.abs(entry.getValue()) < minimalDifference) {
					minimalDifference = Math.abs(entry.getValue());
					minimalScale = entry.getKey();
				}
			}
			this.fluorescenceScale = minimalScale;
			System.err.println("Gridded fluoro scale = " + this.fluorescenceScale);
		}
	}

	// Bundle all the execution and waiting code and especially their try/catches into a function
	private double evaluateSingleFluorescence(ExecutorService executor, double x, int nIterations) {
		return evaluateSeveralFluoroScales(Arrays.asList(new Double[] {x}), nIterations, 1).values().toArray(new Double[1])[0];
	}
	
	// Common code to evaluate several fluorescence scales at the same time
	private Map<Double, Double> evaluateSeveralFluoroScales(Collection<Double> scales, int nIterations, int nThreads) {
		ExecutorService ravager = (scales.size() == 1) ? Executors.newSingleThreadExecutor() : Executors.newFixedThreadPool(nThreads);

		// Set of all results
		Set<Future<Map<Double, Double>>> futureSet = new HashSet<Future<Map<Double, Double>>>();
		// Submit to the executor	
		for (double scale : scales)
			futureSet.add(ravager.submit(new FluorescenceEvaluator(this, absorptionMaps, scale, calibrationConstant0, nIterations)));

		Map<Double, Double> scaleToDifference = new HashMap<Double, Double>();

		// Spin, checking for results
		while (!futureSet.isEmpty()) {
			Set<Future<Map<Double, Double>>> doneThisTimeRound = new HashSet<Future<Map<Double, Double>>>();
			for (Future<Map<Double, Double>> future : futureSet)
				if (future.isDone()) {
					try {
						scaleToDifference.putAll(future.get());
						doneThisTimeRound.add(future);
					} catch (Exception e) {
						// Do nothing!
						// FIXME Do something!
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException iE) {
						; // Do nothing: go to check on the results again 
					}
				}
			futureSet.removeAll(doneThisTimeRound);
		}

		ravager.shutdown();

		return scaleToDifference;
	}
	
	private double integrateFluorescence(Dataset absCor) {
		Dataset smoothed, truncatedSelfScattering = DatasetFactory.zeros(DoubleDataset.class, null);
		final double fractionOfRange = 1/5.0;
		final int smoothLength = (int) Math.floor(absCor.getSize()*fractionOfRange);

		Dataset truncatedQ = DatasetFactory.createRange(DoubleDataset.class, 8, 32, 1.6);
		
		// Set up the pixel integration information
		IPixelIntegrationCache lcache;
		if (backgroundSubtracted.get(0).getShape().length > 1) {
			lcache = getPICache(truncatedQ, AbstractOperationBase.getFirstDiffractionMetadata(backgroundSubtracted.get(0)), backgroundSubtracted.get(0).getShape());
		} else {
			lcache = null;
		}
		
		// Get the mask from the background subtracted sample data
		ILazyDataset mask = AbstractOperationBase.getFirstMask(backgroundSubtracted.get(0));
		IDataset m = null;
		try {
			m = mask != null ? mask.getSlice().squeeze() : null;
		} catch (DatasetException e) {
		}

		// See how well the processed data matches the target. The output
		// of this step should be a smoothed version of absCor and the
		// self-scattering of the sample at the same abscissa values 
		if (absCor.getShape().length == 1) {
			// One dimensional version
			Dataset covolver = Maths.divide(DatasetFactory.ones(DoubleDataset.class, smoothLength), smoothLength);
			smoothed = Signal.convolveForOverlap(absCor, covolver, new int[] {0});
			truncatedSelfScattering = sampleSelfScattering.getSlice(new int[] {smoothLength/2}, new int[] {smoothed.getSize()+smoothLength/2}, new int[] {1});
			truncatedQ = coords.getQ().getSlice(new int[] {smoothLength/2}, new int[] {smoothed.getSize()+smoothLength/2}, new int[] {1});
		} else {
			List<Dataset> out = PixelIntegration.integrate(absCor, m, lcache);
			smoothed = out.remove(1);

			out = PixelIntegration.integrate(sampleSelfScattering, m, lcache);
			truncatedSelfScattering = out.remove(1);
			
			//truncatedSelfScattering = InterpolatorUtils.remap1D(sampleSelfScattering, coords.getQ(), truncatedQ);
		}
		Dataset difference = Maths.subtract(smoothed, truncatedSelfScattering);
		return (double) Maths.multiply(difference, truncatedQ).sum();
		
	}
	
	class FluorescenceEvaluator implements Callable<Map<Double, Double>>{
		
		XPDFCalibration fluorCalibration;
		int nIterations;
		double scale;
		
		public FluorescenceEvaluator(XPDFCalibration source, XPDFAbsorptionMaps absorptionMaps, double scale, double calCon0, int nIterations) {
			fluorCalibration = source.getShallowCopy();
			fluorCalibration.setFixedFluorescence(scale);
			this.scale = scale;
			this.nIterations = nIterations;

		}
		
		public Map<Double, Double> call() {
			Dataset absCor = fluorCalibration.iterateCalibrate(nIterations, false);
			double difference = fluorCalibration.integrateFluorescence(absCor);
			Map<Double, Double> diffMap = new HashMap<Double, Double>(1);
			diffMap.put(scale, difference);
			return diffMap;
		}
	}
	
	/**
	 * Sets whether the calibration should estimate and subtract the fluorescence from the data. 
	 * @param doIt
	 * 			true indicates that the fluorescence subtraction should be performed.
	 */
	public void setDoFluorescence(boolean doIt) {
		doFluorescence = doIt;
	}
	
	/**
	 * Sets the calibration to perform the full fluorescence calibration.
	 * <p>
	 * Tells the calibration to do the full fluorescence calibration, including
	 * calculating the optimum fluorescence scale. 
	 */
	public void performFullFluorescence() {
		doFluorescenceCalibration = true;
	}
	
	/**
	 * Sets the calibration to use the fixed scale fluorescence calibration.
	 * @param fixedFluorescenceScale
	 * 								value to fix the fluorescence scale to
	 */
	public void setFixedFluorescence(double fixedFluorescenceScale) {
		doFluorescenceCalibration = false;
		fluorescenceScale = fixedFluorescenceScale;
	}
	
	/**
	 * Generates a {@link IPixelIntegrationCache} to be used by the azimuthal integration.
	 * @param q
	 * 			the q axis to integrate on to.
	 * @param md
	 * 			the {@link IDiffractionMetadata} that provides the detector calibration.
	 * @param shape
	 * 			the shape of the data to be integrated.
	 * @return the new {@link IPixelIntegrationCache}.
	 */
	private IPixelIntegrationCache getPICache(Dataset q, IDiffractionMetadata md, int[] shape) {
		PixelIntegrationBean pIBean = new PixelIntegrationBean();
		pIBean.setUsePixelSplitting(false);
		pIBean.setNumberOfBinsRadial(q.getSize());
		pIBean.setxAxis(XAxis.Q);
		pIBean.setRadialRange(new double[] {(double) q.min(), (double) q.max()});
		pIBean.setAzimuthalRange(null);
		pIBean.setTo1D(true);
		pIBean.setLog(false);
		pIBean.setShape(shape);
		
		return new PixelIntegrationCache(md, pIBean);
	}
	
	public double getFluorescenceScale() {
		return fluorescenceScale;
	}
}
