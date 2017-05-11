/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

/**
 * A class to hold the immutable fields for the XPDF calibration, and the
 * methods for their initialization.
 * 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 *
 */
public class XPDFCalibrationBase {

	protected Double calibrationConstant0;
	protected XPDFQSquaredIntegrator qSquaredIntegrator;
	protected double selfScatteringDenominator;
	protected Dataset multipleScatteringCorrection;
	protected double nSampleIlluminatedAtoms;
	protected XPDFAbsorptionMaps absorptionMaps;
	protected XPDFCoordinates coords;
	protected XPDFDetector tect;
	protected XPDFBeamData beamData;
	protected Dataset sampleFluorescence;
	protected Dataset sampleSelfScattering;
	protected double fixedFluorescence;

	protected int dataDimensions;
	
	// Perform any kind of fluorescence correction
	protected boolean doFluorescence;
	// Perform the full fluorescence calibration, calculating the optimum fluorescence scale
	protected boolean doFluorescenceCalibration;
	
	private IPixelIntegrationCache cachePI;

	/**
	 * Empty constructor.
	 */
	public XPDFCalibrationBase() {
		qSquaredIntegrator = null;
		selfScatteringDenominator = 1.0;
		multipleScatteringCorrection = null;
		nSampleIlluminatedAtoms = 1.0; // there must be at least one
		doFluorescence = true;
		cachePI = null;
	}

	/**
	 * Copy constructor.
	 * @param inCal
	 * 				calibration object to be copied.
	 */
	public XPDFCalibrationBase(XPDFCalibrationBase inCal) {
//		if (inCal.calibrationConstants != null) {
//			this.calibrationConstants = new LinkedList<Double>();
//			for (double c3 : inCal.calibrationConstants)
//				this.calibrationConstants.add(c3);
//		}
		this.calibrationConstant0 = (inCal.calibrationConstant0 != null) ? inCal.calibrationConstant0 : null;
		this.qSquaredIntegrator = (inCal.qSquaredIntegrator != null) ? inCal.qSquaredIntegrator : null;
		this.selfScatteringDenominator = inCal.selfScatteringDenominator;
		this.multipleScatteringCorrection = (inCal.multipleScatteringCorrection != null) ? inCal.multipleScatteringCorrection.copy(DoubleDataset.class) : null;
		this.nSampleIlluminatedAtoms = inCal.nSampleIlluminatedAtoms;
//		if (inCal.backgroundSubtracted != null) {
//			this.backgroundSubtracted = new ArrayList<Dataset>();
//			for (Dataset data : inCal.backgroundSubtracted) {
//				this.backgroundSubtracted.add(data);
//			}
//		}
		this.tect = (inCal.tect != null) ? new XPDFDetector(inCal.tect): null;
		this.beamData = (inCal.beamData != null) ? new XPDFBeamData(inCal.beamData) : null;
		this.sampleFluorescence = (inCal.sampleFluorescence != null) ? inCal.sampleFluorescence.clone() : null;
//		this.fluorescenceScale = inCal.fluorescenceScale;
		this.doFluorescence = inCal.doFluorescence;
		this.doFluorescenceCalibration = inCal.doFluorescenceCalibration;
		this.dataDimensions = inCal.dataDimensions;
		this.coords = (inCal.coords != null) ? new XPDFCoordinates(inCal.coords) : null;
//		this.cachedDeTran = new HashMap<XPDFCoordinates, Dataset>();
//		this.cachedPolar = new HashMap<XPDFCoordinates, Dataset>();
		this.sampleSelfScattering = (inCal.sampleSelfScattering != null) ? inCal.sampleSelfScattering : null;
		this.absorptionMaps = (inCal.absorptionMaps != null) ? inCal.absorptionMaps : null;
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
		fixedFluorescence = fixedFluorescenceScale;
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
	// The cache is only cleared when the object dies. Which is fine.
	protected IPixelIntegrationCache getPICache(Dataset q, IDiffractionMetadata md, int[] shape) {
		if (cachePI == null) {
			PixelIntegrationBean pIBean = new PixelIntegrationBean();
			pIBean.setUsePixelSplitting(false);
			pIBean.setNumberOfBinsRadial(q.getSize());
			pIBean.setxAxis(XAxis.Q);
			pIBean.setRadialRange(new double[] {(double) q.min(), (double) q.max()});
			pIBean.setAzimuthalRange(null);
			pIBean.setTo1D(true);
			pIBean.setLog(false);
			pIBean.setShape(shape);

			cachePI = new PixelIntegrationCache(md, pIBean);
		}
		
		return cachePI;
	}
	

}
