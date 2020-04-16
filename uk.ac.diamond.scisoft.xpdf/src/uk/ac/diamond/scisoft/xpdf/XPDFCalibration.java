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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;

/**
 * A class for holding the information to calibrate the XPDF data.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 */
public class XPDFCalibration extends XPDFCalibrationBase {

	private Double calibrationConstant0;
	private LinkedList<Double> calibrationConstants;
	private ArrayList<Dataset> backgroundSubtracted;
	private double fluorescenceScale;
	
	private static final int MAX_ITERATIONS = 20;
	
//	// Perform any kind of fluorescence correction
	private boolean doFluorescence;
//	// Perform the full fluorescence calibration, calculating the optimum fluorescence scale
	private boolean doFluorescenceCalibration;

	private final static Logger logger = LoggerFactory.getLogger(XPDFCalibration.class);
	
	// caching angular factors
	Map<XPDFCoordinates, Dataset> cachedDeTran, cachedPolar;
	
	// caching (per run) unchanging corrections.
	// For sample data: solid angle correction
	// For container data: solid angle & transmission
	Dataset solAngSample;
	List<Dataset> transCorContainers;
	
	// Adjustable parameter for beam polarization
	private double polarizationFraction = 1.0;
	
	/**
	 * Empty constructor.
	 */
	public XPDFCalibration() {
		calibrationConstants = new LinkedList<Double>();
		backgroundSubtracted = new ArrayList<Dataset>();
		doFluorescence = true;
		cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
		cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
		solAngSample = null;
		transCorContainers = new ArrayList<Dataset>();
	}
	
	/**
	 * Copy constructor.
	 * @param inCal
	 * 				calibration object to be copied.
	 */
	public XPDFCalibration(XPDFCalibration inCal) {
		// copy the base data
		super((XPDFCalibrationBase) inCal);

		// copy the extended data
		this.calibrationConstant0 = (inCal.calibrationConstant0 != null) ? inCal.calibrationConstant0 : null;
		if (inCal.calibrationConstants != null) {
			this.calibrationConstants = new LinkedList<Double>();
			for (double c3 : inCal.calibrationConstants)
				this.calibrationConstants.add(c3);
		}
		if (inCal.backgroundSubtracted != null) {
			this.backgroundSubtracted = new ArrayList<Dataset>();
			for (Dataset data : inCal.backgroundSubtracted) {
				this.backgroundSubtracted.add(data);
			}
		}
		this.fluorescenceScale = inCal.fluorescenceScale;
		this.doFluorescence = inCal.doFluorescence;
		this.cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
		this.cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
		solAngSample = null;
		transCorContainers = new ArrayList<Dataset>();

		// scaling factors
		this.polarizationFraction = inCal.polarizationFraction;

	}

	/**
	 * Copy constructor from an instance of an {@link XPDFCalibrationBase}.
	 */
	public XPDFCalibration(XPDFCalibrationBase inBase) {
		super(inBase);
		// Initialize derived class fields
		fluorescenceScale = inBase.fixedFluorescence;
		calibrationConstants = new LinkedList<Double>();
		calibrationConstants.add(calibrationConstant0);
		backgroundSubtracted = new ArrayList<Dataset>();
		cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
		cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
		solAngSample = null;
		transCorContainers = new ArrayList<Dataset>();
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
		theCopy.doFluorescenceCalibration = this.doFluorescenceCalibration;
		theCopy.dataDimensions = this.dataDimensions;
		theCopy.coords = this.coords;
		theCopy.absorptionMaps = this.absorptionMaps;
		theCopy.calibrationConstant0 = this.calibrationConstant0;
		theCopy.sampleSelfScattering = this.sampleSelfScattering;
		// in this shallow copy, the caches can even be shared
		theCopy.cachedDeTran = this.cachedDeTran;
		theCopy.cachedPolar = this.cachedPolar;
		theCopy.solAngSample = this.solAngSample;
		theCopy.transCorContainers = this.transCorContainers;
		theCopy.cachePI = (this.cachePI != null) ? this.cachePI : null;
		
		// scaling factors
		theCopy.polarizationFraction = this.polarizationFraction;
		
		return theCopy;
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
		calibrationConstant0 = calibrationConstant;
	}

	/**
	 * Gets the most recent calibration constant from the list.
	 * @return the most recent calibration constant.
	 */
	public double getCalibrationConstant() {
		return calibrationConstants.getLast();
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
	 * Sets the polarization factor of the incident beam. Should usually be set to 1.0.
	 */
	public void setPolarizationFactor(double polarizationFactor) {
		this.polarizationFraction = polarizationFactor;
		// Changing the polarization factor implies re-calculating the polarization correction.
		cachedPolar.clear();
	}
	
	/**
	 * Return form factors for comparison with the calibrated data
	 */
	public IDataset getSampleSelfScattering() {
		return this.sampleSelfScattering;
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
	private Dataset iterate(List<Dataset> deTran, boolean propagateErrors) {
		
		// Divide by the calibration constant and subtract the multiple scattering correction
		List<Dataset> calCon = new ArrayList<Dataset>();
		double lastCalCon = calibrationConstants.getLast();
		for (Dataset componentTrace : deTran) {
//			Dataset calConData = Maths.divide(componentTrace, calibrationConstants.getLast());
			Dataset calConData = DatasetFactory.createFromObject(IntStream.range(0, componentTrace.getSize()).parallel().mapToDouble(i-> componentTrace.getElementDoubleAbs(i) / lastCalCon).toArray(), componentTrace.getShape()); 
		
			// Error propagation
			if (propagateErrors && componentTrace.getErrors() != null)
				calConData.setErrors(Maths.divide(componentTrace.getErrors(), calibrationConstants.getLast()));
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
					if (propagateErrors && componentTrace.getErrors() != null)
						mulCorData.setErrors(componentTrace.getErrors());
					mulCor.add(mulCorData);
				}
			} else {
				for (Dataset componentTrace : calCon) {
//					Dataset mulCorData = Maths.subtract(componentTrace, 0);
					Dataset mulCorData = DatasetFactory.createFromObject(IntStream.range(0, componentTrace.getSize()).parallel().mapToDouble(i -> componentTrace.getElementDoubleAbs(i) - 0.0), componentTrace.getShape());
					//Error propagation
					if (propagateErrors && componentTrace.getErrors() != null)
						mulCorData.setErrors(componentTrace.getErrors());
					mulCor.add(mulCorData);
				}
			}
			absCor = removeAbsorption(mulCor);
		} else {
			absCor = removeAbsorption(calCon);
		}

		absCor.idivide(nSampleIlluminatedAtoms);
		if (propagateErrors && absCor.getErrors() != null)
			absCor.getErrors().idivide(nSampleIlluminatedAtoms);

		Dataset absCorP = applyPolarizationCorrection(absCor);
		
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
	private Dataset removeAbsorption(List<Dataset> mulCor) {
		// Use size and indexing, rather than any fancy-pants iterators or such
		int nComponents = mulCor.size();
		
		// need to retain the original data for the next time around the loop,
		// so copy to absorptionTemporary, an ArrayList.
		List<Dataset> absorptionTemporary = new ArrayList<Dataset>();
		for (Dataset data : mulCor) {
			Dataset absTempData = data.copy(DoubleDataset.class);
			if (data.getErrors() != null)
				absTempData.setErrors(data.getErrors());
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
				Dataset subsetAbsorptionCorrection = this.absorptionMaps.getAbsorptionMap(iScatterer, iInnermostAbsorber).clone();
				for (int iAbsorber = iInnermostAbsorber+1; iAbsorber < iScatterer; iAbsorber++) {
//					subsetAbsorptionCorrection = Maths.multiply(subsetAbsorptionCorrection, this.absorptionMaps.getAbsorptionMap(iScatterer, iAbsorber));
					subsetAbsorptionCorrection.imultiply(this.absorptionMaps.getAbsorptionMap(iScatterer, iAbsorber));
				}
				// Subtract the scattered radiation, corrected for absorption, from the attenuator's radiation
//				absorptionTemporary.get(iInnermostAbsorber).isubtract(
////						Maths.divide(
//						Maths.multiply(
//								absorptionTemporary.get(iScatterer),
////								subsetAbsorptionCorrection.reshape(subsetAbsorptionCorrection.getSize())));
//								subsetAbsorptionCorrection.squeeze()));

				Dataset innermostTemporary = absorptionTemporary.get(iInnermostAbsorber),
						scattererTemporary = absorptionTemporary.get(iScatterer);
				absorptionTemporary.set(iInnermostAbsorber, DatasetFactory.createFromObject(IntStream.range(0, innermostTemporary.getSize()).parallel().mapToDouble( i -> innermostTemporary.getElementDoubleAbs(i) - scattererTemporary.getElementDoubleAbs(i) * subsetAbsorptionCorrection.getElementDoubleAbs(i)).toArray(), innermostTemporary.getShape()));
				
				// Error propagation. If either is present, then set an error on the result. Non-present errors are taken as zero (exact).
				if (absorptionTemporary.get(iInnermostAbsorber).getErrors() != null ||
						absorptionTemporary.get(iScatterer).getErrors() != null) {
					Dataset innerError = (absorptionTemporary.get(iInnermostAbsorber).getErrors() != null) ?
							absorptionTemporary.get(iInnermostAbsorber).getErrors() :
							DatasetFactory.zeros(absorptionTemporary.get(iInnermostAbsorber));
					Dataset scatterError = (absorptionTemporary.get(iScatterer).getErrors() != null) ?
//							Maths.divide(
							Maths.multiply(
									absorptionTemporary.get(iScatterer).getErrors(),
									subsetAbsorptionCorrection.reshape(subsetAbsorptionCorrection.getSize())) :
//									subsetAbsorptionCorrection.squeeze()) :
									DatasetFactory.zeros(absorptionTemporary.get(iScatterer));
					absorptionTemporary.get(iInnermostAbsorber).setErrors(Maths.sqrt(Maths.add(Maths.square(innerError), Maths.square(scatterError))));
				}
				
			}
		}
		
		// start with sample self-absorption
		Dataset absorptionCorrection = this.absorptionMaps.getAbsorptionMap(0, 0).clone();
		for (int iAbsorber = 1; iAbsorber < nComponents; iAbsorber++)
//			absorptionCorrection = Maths.multiply(absorptionCorrection, absorptionMaps.getAbsorptionMap(0, iAbsorber));
			absorptionCorrection.imultiply(absorptionMaps.getAbsorptionMap(0, iAbsorber));
		
//		Dataset absCor = Maths.divide(absorptionTemporary.get(0), absorptionCorrection.reshape(absorptionCorrection.getSize()));
		Dataset sampleTemporary = absorptionTemporary.get(0);
//		Dataset absCor = Maths.divide(absorptionTemporary.get(0), absorptionCorrection.getSliceView().squeeze());
		Dataset absCor = DatasetFactory.createFromObject(IntStream.range(0, sampleTemporary.getSize()).parallel().mapToDouble(i -> sampleTemporary.getElementDoubleAbs(i) / absorptionCorrection.getElementDoubleAbs(i)).toArray(), sampleTemporary.getShape());
		// Error propagation
		if (absorptionTemporary.get(0).getErrors() != null) {
//			absCor.setError(Maths.divide(absorptionTemporary.get(0).getError(), absorptionCorrection.reshape(absorptionCorrection.getSize())));
			absCor.setErrors(Maths.divide(absorptionTemporary.get(0).getErrors(), absorptionCorrection.getSliceView().squeeze()));
		}
		
		return absCor;
	}

	/**
	 * Removes the effect of polarization on the data. 
	 * @param absCor
	 * 				The data uncorrected for the effects of polarization
	 * @return the data corrected for the effects of polarization.
	 */
	private Dataset applyPolarizationCorrection(Dataset absCor) {
		final double sineFudge = 0.99;		
//		final double polarizationFraction = 1.0;
		Dataset polCor;

		if (!cachedPolar.containsKey(coords)) {		
			// The azimuthal dependence or the mean value thereof, f(φ)
			Dataset azimuthalFactor;
			
			if (absCor.getRank() == 1) {
				// One dimensional: f(φ) = c/2 sin² 2θ
				azimuthalFactor = Maths.multiply(polarizationFraction, Maths.square(coords.getSinTwoTheta()));
			} else {
				// Two dimensional: f(φ) = f_pol cos 2φ sin² 2θ
				azimuthalFactor =
						Maths.multiply(
								polarizationFraction,
								Maths.multiply(
										Maths.cos(Maths.multiply(2, coords.getPhi())),
										Maths.square(coords.getSinTwoTheta())
								)
						);
			}
			// 1/2 (1 + cos² 2θ - f(φ))
			polCor = Maths.multiply(0.5, Maths.add(1, Maths.subtract(Maths.square(coords.getCosTwoTheta()), azimuthalFactor)));
			cachedPolar.put(coords, polCor);
		} else {
			polCor = cachedPolar.get(coords);
		}
								
//		Dataset absCorP = Maths.multiply(absCor, polCor);
		Dataset absCorP = DatasetFactory.createFromObject(IntStream.range(0, absCor.getSize()).parallel().mapToDouble(i -> absCor.getElementDoubleAbs(i) * polCor.getElementDoubleAbs(i)).toArray(), absCor.getShape());
		
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
	public Dataset calibrate(int nIterations, int nThreads, IOperation<?, ?> op) {
		calculateInitialCorrections();
//		if (doFluorescence) {
//			double originalScale = this.fluorescenceScale;
//			calibrateFluorescence(nIterations, nThreads, op);
//			if (!doFluorescenceCalibration)
//				this.fluorescenceScale = originalScale;
//		}
		if (doFluorescence && doFluorescenceCalibration)
			calibrateFluorescence(nIterations, nThreads, op);
		Dataset absCor = iterateCalibrate(nIterations, true, op);
		logger.debug("Fluorescence scale = " + fluorescenceScale);
		// The last value on the list was the corrected value, not used for the calculation of absCor
		calibrationConstants.pop();
		logger.debug("Calibration constant = " + calibrationConstants.getLast());
		invalidateInitialCorrections();
		return absCor;
	}
	
	/**
	 * Performs the calibration iterations.
	 * <p>
	 * Performs the part of the calibration following the fluorescence scale determination. 
//	 * @param nIterations
	 * 					the number of iterations to make to calculate the
	 * 					multiplicative calibration constant.
	 * @param propagateErrors
	 * 						propagate errors, if they are found
	 * @return the calibrated XPDF data
	 */
	private Dataset iterateCalibrate(int nIterations, boolean propagateErrors, IOperation<?, ?> op) {

		List<Dataset> deTran;
		if (solAngSample == null || transCorContainers == null || transCorContainers.isEmpty()) {
			logger.debug("Calculating initial corrections");
			deTran = doInitialCorrections(propagateErrors);
		} else {
			logger.debug("Utilising pre-calculated initial corrections");
			deTran = utilizePrecalculatedCorrections(propagateErrors);
		}

		
		Dataset absCor = null;
		// Initialize the list of calibration constants with the predefined initial value.
		calibrationConstants = new LinkedList<Double>(Arrays.asList(new Double[] {calibrationConstant0}));
		// Iterate until a hardcoded precision is achieved or the maximum number of iterations is reached
		final double calibrationPrecision = 1e-6;
		int count = 0;
		double calConRatio;
		do{
			absCor = this.iterate(deTran, propagateErrors);
			count++;
			calConRatio = calibrationConstants.getLast()/calibrationConstants.get(calibrationConstants.size()-2); 
		} while (Math.abs(calConRatio - 1) > calibrationPrecision && count < nIterations);
//		for (int i = 0; i < nIterations; i++)
//			absCor = this.iterate(fluorescenceCorrected, propagateErrors);
		return absCor;
	}
	
	/**
	 * Calculates the corrections that do not change through the iterations of this calibration
	 */
	private void calculateInitialCorrections() {
		logger.debug("Pre-calculating initial corrections");
		// Pre-calculate the transmission correction
		Dataset transmissionCorrection;
		if (!cachedDeTran.containsKey(coords)) {
			transmissionCorrection = tect.getTransmissionCorrection(coords.getTwoTheta(), beamData.getBeamEnergy());
			cachedDeTran.put(coords, transmissionCorrection);
		} else {
			transmissionCorrection = cachedDeTran.get(coords);
		}
		
		Dataset oneOverCosTwoThetaCubed = DatasetFactory.createFromObject(IntStream.range(0, coords.getCosTwoTheta().getSize()).parallel().mapToDouble(
						i -> 1/(coords.getCosTwoTheta().getElementDoubleAbs(i)*
						coords.getCosTwoTheta().getElementDoubleAbs(i)*
						coords.getCosTwoTheta().getElementDoubleAbs(i))
						).toArray(), coords.getCosTwoTheta().getShape());
		
		
		// Solid Angle Correction
		// sample, index = 0
		solAngSample = copyingSolidAngle(backgroundSubtracted.get(0), oneOverCosTwoThetaCubed);
		// containers, index > 0
		for (int iCont = 1; iCont < backgroundSubtracted.size(); iCont++) {
			transCorContainers.add(copyingTransmissionCorrection(copyingSolidAngle(backgroundSubtracted.get(iCont), oneOverCosTwoThetaCubed), transmissionCorrection));
		}
	}
	
	private Dataset copyingSolidAngle(Dataset targetComponent, Dataset oneOverCosTwoThetaCubed) {
		// result = data /(cos 2θ)^3
		return Maths.multiply(targetComponent, oneOverCosTwoThetaCubed);
	}
	
	private Dataset copyingTransmissionCorrection(Dataset solAng, Dataset transmissionCorrection) {

		return DatasetFactory.createFromObject(IntStream.range(0, solAng.getSize()).parallel().mapToDouble(i-> solAng.getElementDoubleAbs(i) * transmissionCorrection.getElementDoubleAbs(i)).toArray(), solAng.getShape()); 

	}

	private void inPlaceTransmissionCorrection(Dataset targetComponent, Dataset transmissionCorrection) {
		targetComponent.imultiply(transmissionCorrection);
	}
	
	private List<Dataset> utilizePrecalculatedCorrections(boolean propagateErrors) {
		// Sample data: apply the fluorescence scaling (if required), and then the transmission correction
		Dataset transCorSample;
		if (doFluorescence) {
			transCorSample= Maths.multiply(-fluorescenceScale, sampleFluorescence.squeeze());
			transCorSample.iadd(solAngSample);
		} else {
			transCorSample = solAngSample;
		}
		// sample transmission correction
		// Detector transmission correction
		Dataset transmissionCorrection;
		if (!cachedDeTran.containsKey(coords)) {
			transmissionCorrection = tect.getTransmissionCorrection(coords.getTwoTheta(), beamData.getBeamEnergy());
			cachedDeTran.put(coords, transmissionCorrection);
		} else {
			transmissionCorrection = cachedDeTran.get(coords);
		}
		
		inPlaceTransmissionCorrection(transCorSample, transmissionCorrection);
		
		List<Dataset> deTran = new ArrayList<Dataset>();
		deTran.add(transCorSample);
		
		// Container transmission correction
		for (Dataset deTranSample: transCorContainers)
			deTran.add(deTranSample);
		
		return deTran;
		
	}
	
	private void invalidateInitialCorrections() {
		solAngSample = null;
		transCorContainers.clear();
	}
	
	/**
	 * If the initial corrections have not been performed, then do them.
	 * @return list of transmission corrected Datasets
	 */
	private List<Dataset> doInitialCorrections(boolean propagateErrors) {
		List<Dataset> solAng = new ArrayList<Dataset>();
		for (Dataset targetComponent : backgroundSubtracted) {
			Dataset cosTwoTheta = coords.getCosTwoTheta();
			// result = data /(cos 2θ)^3
			Dataset oneOverCosTwoThetaCubed = DatasetFactory.createFromObject(IntStream.range(0, cosTwoTheta.getSize()).parallel().mapToDouble(
							i -> 1/(cosTwoTheta.getElementDoubleAbs(i)*
							cosTwoTheta.getElementDoubleAbs(i)*
							cosTwoTheta.getElementDoubleAbs(i))
							).toArray(), cosTwoTheta.getShape());

			Dataset solAngData = copyingSolidAngle(targetComponent, oneOverCosTwoThetaCubed);
			if (propagateErrors && targetComponent.getErrors() != null)
				solAngData.setErrors(targetComponent.getErrors());
			solAng.add(solAngData);
		}
		
		// fluorescence correction
		List<Dataset> fluorescenceCorrected = new ArrayList<Dataset>();
//		for (Dataset targetComponent : backgroundSubtracted) {
		// Only correct fluorescence in the sample itself
		Dataset targetComponent = solAng.get(0);

		if (doFluorescence) {
			// corrected = data - scale*fluorescence
			// Try to save a Dataset by first calculating the fluorescence subtrahend
			Dataset fluorescenceCorrectedData = Maths.multiply(-fluorescenceScale, sampleFluorescence.squeeze());
			fluorescenceCorrectedData.iadd(targetComponent);
			
			if (propagateErrors && targetComponent.getErrors() != null)
				if (sampleFluorescence.getErrors() != null)
					fluorescenceCorrectedData.setErrors(
							Maths.sqrt(
									Maths.add(
											Maths.square(targetComponent.getErrors()),
											Maths.square(sampleFluorescence.getErrors())
											)
									)
							);
				else
					fluorescenceCorrectedData.setErrors(targetComponent.getErrors());
			fluorescenceCorrected.add(fluorescenceCorrectedData);
		} else {
			fluorescenceCorrected.add(targetComponent);
		}
		for (int iContainer = 1; iContainer < solAng.size(); iContainer++)
			fluorescenceCorrected.add(solAng.get(iContainer));

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
			Dataset deTranData = copyingTransmissionCorrection(componentTrace, transmissionCorrection);
			
			// Error propagation
			if (propagateErrors && componentTrace.getErrors() != null)
				deTranData.setErrors(Maths.multiply(componentTrace.getErrors(), transmissionCorrection));
			deTran.add(deTranData);
		}
		return deTran;
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
	private void calibrateFluorescence(int nIterations, int nThreads, IOperation<?,?> op) {
		
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
			double fLow = evaluateSingleFluorescence(annihilator, xLow, nIterations, op),
					fHigh = evaluateSingleFluorescence(annihilator, xHigh, nIterations, op);
			// If the selected range should not change sign, expand it until it does
			int count = 0;
			while (Math.signum(fHigh) == Math.signum(fLow) && count < MAX_ITERATIONS) {
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
						Math.min(nThreads, 2),op);
				fLow = differences.get(xLow);
				fHigh = differences.get(xHigh);
				logger.debug("Bisection fluoro scales " + Double.toString(xLow) + " to " + Double.toString(xHigh));
				
				count++;
			}
			
			if (count == MAX_ITERATIONS) {
				throw new OperationException(op);
			}

			if (!doGridded) {
				boolean doQuadrisection = false;
				double xLinear = xHigh, xLinearLast = xLow;
				// Reduce the range, while maintaining the condition that fHigh and fLow have opposite signs
				int counter = 0;
				while (Math.abs(xLinear - xLinearLast) > granularity && counter < MAX_ITERATIONS) {

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
								Arrays.asList(ArrayUtils.toObject(xes)), nIterations, Math.min(nThreads, 3),op);
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
						fMid = evaluateSingleFluorescence(annihilator, xMid, nIterations,op);
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
					counter++;
					logger.debug("Bisection fluoro scales " + Double.toString(xLow) + " to "
							+ Double.toString(xHigh) + ". Linear solution: " + Double.toString(xLinear));
				}
				
				if (counter == MAX_ITERATIONS) {
					throw new OperationException(op);
				}
				
				// Linear interpolation of x over this range
				//		double xZero = xLow - (xHigh - xLow)/(fHigh - fLow) * fLow;
				this.fluorescenceScale = xLinear;
			}
		}
		// Old gridded code
		if (doGridded) {

			Map<Double, Double> scaleToDifference = evaluateSeveralFluoroScales(Arrays.asList(ArrayUtils.toObject(DatasetFactory.createRange(DoubleDataset.class, minScale, maxScale, stepScale).getData())), nIterations, nThreads,op);
			
			double minimalScale = 0;
			double minimalDifference = Double.POSITIVE_INFINITY; 
			// Get the scale with the minimum difference
			for(Map.Entry<Double, Double> entry : scaleToDifference.entrySet()) {
				logger.debug("F = " + Double.toString(entry.getKey()) + ", C = " + Double.toString(entry.getValue()));;
				if (Math.abs(entry.getValue()) < minimalDifference) {
					minimalDifference = Math.abs(entry.getValue());
					minimalScale = entry.getKey();
				}
			}
			this.fluorescenceScale = minimalScale;
			logger.debug("Gridded fluoro scale = " + this.fluorescenceScale);
		}
	}

	// Bundle all the execution and waiting code and especially their try/catches into a function
	private double evaluateSingleFluorescence(ExecutorService executor, double x, int nIterations, IOperation<?,?> op) {
		Map<Double, Double> map = evaluateSeveralFluoroScales(Arrays.asList(new Double[] {x}), nIterations, 1, op);
		if (map.isEmpty()) throw new OperationException(op, "Fluoroscent scale calulation failed!");
		return map.values().toArray(new Double[1])[0];
	}
	
	// Common code to evaluate several fluorescence scales at the same time
	private Map<Double, Double> evaluateSeveralFluoroScales(Collection<Double> scales, int nIterations, int nThreads, IOperation<?,?> op) {
		ExecutorService ravager = (scales.size() == 1) ? Executors.newSingleThreadExecutor() : Executors.newFixedThreadPool(nThreads);

		AtomicInteger completionCounter = new AtomicInteger(0);
		final int numberToCalculate = scales.size();
		// Set of all results
		Map<Double, Future<Double>> futureMap = new HashMap<Double, Future<Double>>();
		// Submit to the executor	
		for (double scale : scales)
			futureMap.put(scale, ravager.submit(new FluorescenceEvaluator(this, absorptionMaps, scale, calibrationConstant0, nIterations, completionCounter, op)));

		Map<Double, Double> scaleToDifference = new HashMap<Double, Double>();

		// Spin, checking if all the threads have completed
		while (completionCounter.get() < numberToCalculate)
			try {
				Thread.sleep(100);
			} catch (InterruptedException iE) {
				; // Do nothing: go to check on the results again 
			}

		// Get all the results
		
		for (Map.Entry<Double, Future<Double>> scaleNFuture : futureMap.entrySet()) {
		
			try {
				scaleToDifference.put(scaleNFuture.getKey(), scaleNFuture.getValue().get());
			} catch (ExecutionException eE) {
				logger.error("Fluoro scale error", eE);
				scaleToDifference.clear();
				break;
			} catch (InterruptedException iE) {
				logger.error("Fluoro scale error", iE);
				scaleToDifference.clear();
				break;
			}
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
//			Dataset covolver = Maths.divide(DatasetFactory.ones(DoubleDataset.class, smoothLength), smoothLength);
			Dataset convolver = DatasetFactory.ones(DoubleDataset.class, smoothLength).idivide(smoothLength);
			smoothed = Signal.convolveForOverlap(absCor, convolver, new int[] {0});
			truncatedSelfScattering = sampleSelfScattering.getSlice(new int[] {smoothLength/2}, new int[] {smoothed.getSize()+smoothLength/2}, new int[] {1});
			truncatedQ = coords.getQ().getSlice(new int[] {smoothLength/2}, new int[] {smoothed.getSize()+smoothLength/2}, new int[] {1});
		} else {
			List<Dataset> out = PixelIntegration.integrate(absCor, m, lcache);
			smoothed = out.remove(1);

			out = PixelIntegration.integrate(sampleSelfScattering, m, lcache);
			truncatedSelfScattering = out.remove(1);
			
			//truncatedSelfScattering = InterpolatorUtils.remap1D(sampleSelfScattering, coords.getQ(), truncatedQ);
		}
//		Dataset difference = Maths.subtract(smoothed, truncatedSelfScattering);
//		return (double) Maths.multiply(difference, truncatedQ).sum();

		
		smoothed.isubtract(truncatedSelfScattering);
		smoothed.imultiply(truncatedQ);
		return ((Number) smoothed.sum()).doubleValue();
		
	}
	
	class FluorescenceEvaluator implements Callable<Double>{
		
		XPDFCalibration fluorCalibration;
		int nIterations;
		double scale;
		AtomicInteger counter;
		IOperation<?, ?> op;
		
		public FluorescenceEvaluator(XPDFCalibration source, XPDFAbsorptionMaps absorptionMaps, double scale, double calCon0, int nIterations, AtomicInteger counter, IOperation<?, ?> op) {
			fluorCalibration = source.getShallowCopy();
			fluorCalibration.setFixedFluorescence(scale);
			this.scale = scale;
			this.nIterations = nIterations;
			this.counter = counter;
			this.op = op;
		}
		
		public Double call() {
			try {
				Dataset absCor = fluorCalibration.iterateCalibrate(nIterations, false, op);
				double difference = fluorCalibration.integrateFluorescence(absCor);
				return difference;
			} finally {
				counter.incrementAndGet();
			}
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

	public double getFluorescenceScale() {
		return fluorescenceScale;
	}
	
	public void setSelfScatteringDenominatorFromSample(XPDFTargetComponent sample) {
		super.setSelfScatteringDenominatorFromSample(sample, this.coords);
	}
}
