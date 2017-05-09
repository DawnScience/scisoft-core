/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import javax.vecmath.Vector3d;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(PixelIntegrationUtils.class);
	
	public enum IntegrationMode{NONSPLITTING,SPLITTING,SPLITTING2D,NONSPLITTING2D}
	
	public static Dataset generate2ThetaArrayRadians(IDiffractionMetadata md) {
		return generate2ThetaArrayRadians(getShape(md), md);
	}
	
	public static Dataset generate2ThetaArrayRadians(int[] shape, IDiffractionMetadata md) {
		
		QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());
		return generateRadialArray(shape, qSpace, XAxis.ANGLE, true);
		
	}
	
	public static Dataset generateQArray(IDiffractionMetadata md) {
		return generateQArray(getShape(md), md);
	}
	
	public static Dataset generateQArray(int[] shape, IDiffractionMetadata md) {
		QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());
		return generateRadialArray(shape, qSpace, XAxis.Q);
		
	}
	
	public static Dataset generateAzimuthalArray(IDiffractionMetadata metadata, boolean radians) {
		return generateAzimuthalArray(metadata.getDetector2DProperties().getBeamCentreCoords(), getShape(metadata), radians);
	}
	
	public static Dataset generateAzimuthalArray(int[] shape, IDiffractionMetadata metadata, boolean radians) {
		return generateAzimuthalArray(metadata.getDetector2DProperties().getBeamCentreCoords(), shape,radians);
	}
	
	public static Dataset generateAzimuthalArray(double[] beamCentre, int[] shape, boolean radians) {
		
		Dataset out = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		PositionIterator iter = out.getPositionIterator();

		int[] pos = iter.getPos();
		
		//+0.5 for centre of pixel
		while (iter.hasNext()) {
			double val = Math.atan2(pos[0]+0.5-beamCentre[1],pos[1]+0.5-beamCentre[0]);
			if (radians) out.set(val, pos);
			else out.set(Math.toDegrees(val), pos);
		}
		
		return out;
	}
	
	public static Dataset generateAzimuthalArray(double[] beamCentre, int[] shape, double min) {
		//Number of circles
		int n = (int)Math.floor((min+180)/360);
		double minInBase = (min - (360*n));
		
		Dataset out = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		PositionIterator iter = out.getPositionIterator();

		int[] pos = iter.getPos();
		
		//+0.5 for centre of pixel
		while (iter.hasNext()) {
			double val = Math.toDegrees(Math.atan2(pos[0]+0.5-beamCentre[1],pos[1]+0.5-beamCentre[0]));
			if (val < minInBase) val = val + 360;
			out.set(val+360*n, pos);
		}
		
		return out;
		
	}
	
	public static Dataset[] generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape, double min) {
		//Number of circles
		int n = (int)Math.floor((min+180)/360);
		double minInBase = (min - (360*n));
		Dataset aMax = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		Dataset aMin = DatasetFactory.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = aMax.getPositionIterator();
		int[] pos = iter.getPos();
		double[] vals = new double[4];
		
		while (iter.hasNext()) {
			//find vals at pixel corners
			vals[0] = Math.toDegrees(Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]));
			vals[1] = Math.toDegrees(Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]));
			vals[2] = Math.toDegrees(Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]+1));
			vals[3] = Math.toDegrees(Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]+1));
			if (vals[0] < minInBase) vals[0] = vals[0] + 360;
			if (vals[1] < minInBase) vals[1] = vals[1] + 360;
			if (vals[2] < minInBase) vals[2] = vals[2] + 360;
			if (vals[3] < minInBase) vals[3] = vals[3] + 360;
			
			Arrays.sort(vals);
			//if the pixel needs to be split over 180 degrees, over the discontinuity
			//Only split up to the discontinuity on the side with the largest range
			//Should only change the single row of pixels allow the discontinuity
//			(vals[0] < -Math.PI/2 && vals[3] > Math.PI/2)
			
			
			if ( vals[3] - vals[0] > 180) {
				//FIXME do best to handle discontinuity here - saves changing the integration routine
				//may not be as accurate - might need to make the integration aware.
				//currently just squeeze all the signal in one side
				
				if ((minInBase+360)-vals[3] > vals[0]-minInBase) {
					vals[0] = vals[3];
					vals[3] = minInBase+360;
				} else {
					vals[3] = vals[0];
					vals[0] = minInBase;
					
				}
			}
			
			aMax.set(vals[3]+360*n, pos);
			aMin.set(vals[0]+360*n, pos);

		}
		
		return new Dataset[]{aMin,aMax};
	}
	
	public static Dataset[] generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape, boolean radians) {
		
		Dataset aMax = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		Dataset aMin = DatasetFactory.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = aMax.getPositionIterator();
		int[] pos = iter.getPos();
		double[] vals = new double[4];
		
		while (iter.hasNext()) {
			//find vals at pixel corners
			vals[0] = Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]);
			vals[1] = Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]);
			vals[2] = Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]+1);
			vals[3] = Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]+1);
			
			Arrays.sort(vals);
			
			
			if (vals[0] < -Math.PI/2 && vals[3] > Math.PI/2) {
				//FIXME do best to handle discontinuity here - saves changing the integration routine
				//may not be as accurate - might need to make the integration aware.
				//currently just squeeze all the signal in one side
				
				if (Math.PI-vals[3] > Math.PI + vals[0]) {
					vals[3] = Math.PI;
					vals[0] = vals[2];
				} else {
					vals[0] = -Math.PI;
					vals[3] = vals[1];
				}
			}
			
			if (radians) {
				aMax.set(vals[3], pos);
				aMin.set(vals[0], pos);
			} else {
				aMax.set(Math.toDegrees(vals[3]), pos);
				aMin.set(Math.toDegrees(vals[0]), pos);
			}
		}
		
		return new Dataset[]{aMin,aMax};
	}
	
	public static  Dataset[] generateMinMaxRadialArray(int[] shape, QSpace qSpace, XAxis xAxis) {
		
		if (qSpace == null) return null;
		
		Dataset[] cached = getFromCache(qSpace, xAxis, false);
		
		if (cached != null) {
			logger.info("Coords from cache");
			return cached;
		}
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		Dataset radialArrayMax = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		Dataset radialArrayMin = DatasetFactory.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = radialArrayMax.getPositionIterator();
		int[] pos = iter.getPos();

		double[] vals = new double[4];
		double w = qSpace.getWavelength();
		while (iter.hasNext()) {
			
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			
			if (xAxis != XAxis.PIXEL) {
				vals[0] = qSpace.qFromPixelPosition(pos[1], pos[0]).length();
				vals[1] = qSpace.qFromPixelPosition(pos[1]+1, pos[0]).length();
				vals[2] = qSpace.qFromPixelPosition(pos[1], pos[0]+1).length();
				vals[3] = qSpace.qFromPixelPosition(pos[1]+1, pos[0]+1).length();
			} else {
				vals[0] = Math.hypot(pos[1]-beamCentre[0], pos[0]-beamCentre[1]);
				vals[1] = Math.hypot(pos[1]+1-beamCentre[0], pos[0]-beamCentre[1]);
				vals[2] = Math.hypot(pos[1]-beamCentre[0], pos[0]+1-beamCentre[1]);
				vals[3] = Math.hypot(pos[1]+1-beamCentre[0], pos[0]+1-beamCentre[1]);
			}
			
			Arrays.sort(vals);

			switch (xAxis) {
			case ANGLE:
				radialArrayMax.set(Math.toDegrees(Math.asin(vals[3] * w/(4*Math.PI))*2),pos);
				radialArrayMin.set(Math.toDegrees(Math.asin(vals[0] * w/(4*Math.PI))*2),pos);
				break;
			case Q:
			case PIXEL:
				radialArrayMax.set(vals[3],pos);
				radialArrayMin.set(vals[0],pos);
				break;
			case RESOLUTION:
				radialArrayMax.set((2*Math.PI)/vals[0],pos);
				radialArrayMin.set((2*Math.PI)/vals[3],pos);
				break;
			}
		}
		
		Dataset[] output = new Dataset[]{radialArrayMin,radialArrayMax};
		
		putInCache(qSpace, xAxis, false, output);
		
		return output;
	}
	
	public static Dataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis) {
		return generateRadialArray(shape, qSpace, xAxis, false);
	}
	
	private static Dataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis, boolean radians) {
		
		
		
		if (qSpace == null) return null;
	
		Dataset[] cached = getFromCache(qSpace, xAxis, true);
		
		if (cached != null) {
			logger.info("Coords from cache");
			return cached[0];
		}
		
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		DoubleDataset ra = DatasetFactory.zeros(DoubleDataset.class, shape);

		PositionIterator iter = ra.getPositionIterator();
		int[] pos = iter.getPos();
		long t = System.currentTimeMillis();
		while (iter.hasNext()) {
			
			Vector3d q;
			double value = 0;
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			q = qSpace.qFromPixelPosition(pos[1]+0.5,pos[0]+0.5);
			
			switch (xAxis) {
			case ANGLE:
				value = qSpace.scatteringAngle(q);
				if (!radians)value = Math.toDegrees(value);
				break;
			case Q:
				value = q.length();
				break;
			case RESOLUTION:
				value = (2*Math.PI)/q.length();
				break;
			case PIXEL:
				value = Math.hypot(pos[1]-beamCentre[0]+0.5,pos[0]-beamCentre[1]+0.5);
				break; 
			}
			ra.setItem(value, pos);
		}
		
		putInCache(qSpace, xAxis, true, new Dataset[]{ra});
		
		System.out.println(System.currentTimeMillis()-t);
		
		return ra;
	}
	
	private static Dataset[] getFromCache(QSpace q, XAxis axis, boolean centre) {
		DiffractionCoordinateCache cacheInstance = DiffractionCoordinateCache.getInstance();
		DiffractionMetadata md = new DiffractionMetadata("", q.getDetectorProperties(), new DiffractionCrystalEnvironment(q.getWavelength()));
		Object object = cacheInstance.get(md, axis, centre);
		
		if (object == null) return null;
		
		try {
			if (centre) {
				return new Dataset[]{(Dataset)object};
			}
				
			return (Dataset[])object;
			
		} catch (Exception e) {
			logger.error("Bad object in the cache");
		}
		
		return null;
	}
	
	private static void putInCache(QSpace q, XAxis axis, boolean centre, Dataset[] object) {
		DiffractionCoordinateCache cacheInstance = DiffractionCoordinateCache.getInstance();
		DiffractionMetadata md = new DiffractionMetadata("", q.getDetectorProperties(), new DiffractionCrystalEnvironment(q.getWavelength()));
		cacheInstance.put(md, axis, centre,centre ? object[0] : object);
	}
	
	public static int[] getShape(IDiffractionMetadata metadata) {
		return new int[]{metadata.getDetector2DProperties().getPy(), metadata.getDetector2DProperties().getPx()};
	}
	
	public static int calculateNumberOfBins(IDiffractionMetadata metadata) {
		
		int[] shape = getShape(metadata);
		double[] beamCentre = metadata.getDetector2DProperties().getBeamCentreCoords();

		if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& beamCentre[0] < shape[1] && beamCentre[0] > 0) {
			double[] farCorner = new double[]{0,0};
			if (beamCentre[1] < shape[0]/2.0) farCorner[0] = shape[0];
			if (beamCentre[0] < shape[1]/2.0) farCorner[1] = shape[1];
			
			return (int)Math.hypot(beamCentre[0]-farCorner[1], beamCentre[1]-farCorner[0]);
		} else if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& (beamCentre[0] > shape[1] || beamCentre[0] < 0)) {
				return shape[1];
		} else if (beamCentre[0] < shape[1] && beamCentre[0] > 0
				&& (beamCentre[1] > shape[0] || beamCentre[1] < 0)) {
				return shape[0];
		} else {
			return (int)Math.hypot(shape[1], shape[0]);
		}
	}
	
	public static Dataset generate2Dfrom1D(IDataset[] xy1d, Dataset array2Dx) {
		
		DoubleDataset[] inXy1D = new DoubleDataset[2];
		inXy1D[0] = (DoubleDataset) DatasetUtils.cast(xy1d[0], Dataset.FLOAT64);
		inXy1D[1] = (DoubleDataset) DatasetUtils.cast(xy1d[1], Dataset.FLOAT64);
		
		double min = inXy1D[0].min().doubleValue();
		double max = inXy1D[0].max().doubleValue();
		
		SplineInterpolator si = new SplineInterpolator();
		PolynomialSplineFunction poly = si.interpolate(inXy1D[0].getData(),inXy1D[1].getData());
		Dataset image = DatasetFactory.zeros(array2Dx.getShape(),Dataset.FLOAT64);
		double[] buf = (double[])image.getBuffer();
		
		IndexIterator iterator = array2Dx.getIterator();
		
		while (iterator.hasNext()) {
			double e = array2Dx.getElementDoubleAbs(iterator.index);
			if (e <= max && e >= min) buf[iterator.index] = poly.value(e);
			
		}
		
		return image;
	}
	
	public static double solidAngleCorrection(double correctionValue, double tth) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		double cor = Math.cos(tth);
		cor = Math.pow(cor, 3);
		return correctionValue/cor;
	}
	
	/**
	 * Corrects an intensity value for the effect of polarisation on the
	 * scattering intensity
	 * @param correctionValue
	 * 						the value to be corrected
	 * @param tth
	 * 			The 2θ scattering angle between the incident and scattered
	 * 			beams
	 * @param angle
	 * 				The azimuthal angle between the scattered beam and the
	 * 				polarisation of the incident beam
	 * @param factor
	 *				The fraction of the beam which is polarised. Takes
	 *				the value 1.0 for a perfectly polarised beam, and
	 *				0.0 for an unpolarised beam.
	 * @return The scattered intensity without the effects of polarisation.
	 */
	public static double polarisationCorrection(double correctionValue, double tth, double angle, double factor) {
		//R Kahn et al, J Appl Cryst 15 330 (1982)
		//pol(th) = 1/2[1+cos2(tth) - f*cos(2*azimuthal)sin2(tth)
		
		double cosSq = Math.pow(Math.cos(tth),2);
		//use 1-cos2(tth) instead of sin2(tth)
//		double sub = (1-cosSq)*Math.cos(angle*2)*factor;
//		
//		double cor = (cosSq +1 - sub)/2;
		double corCommon = polarisationCorrectionFactor(cosSq, (1-cosSq)*Math.cos(angle*2), factor);
		return correctionValue/corCommon;
	}
	
	/**
	 * Corrects an intensity value according to the polarisation.
	 * @param uncorrectedValue
	 * 							the value to be polarisation corrected
	 * @param incidentBeam
	 * 					the direction vector of the incident beam
	 * @param scatteredBeam
	 * 					the direction vector of the scattered beam
	 * @param polarizationPlaneNormal
	 * 								the normal vector to the plane of polarisation
	 * @param polarizationFactor
	 *							The fraction of the beam which is polarised. Takes
	 *							the value 1.0 for a perfectly polarised beam, and
	 *							0.0 for an unpolarised beam.
	 * @return The scattered intensity without the effects of polarisation.
	 */
	public static double polarisationCorrection(double uncorrectedValue, Dataset scatteredBeam, Dataset incidentBeam, Dataset polarizationPlaneNormal, double polarizationFactor) {
		Dataset s0 = Maths.divide(incidentBeam, LinearAlgebra.norm(incidentBeam)),
				s1 = Maths.divide(scatteredBeam, LinearAlgebra.norm(scatteredBeam)),
				pn = Maths.divide(polarizationPlaneNormal, LinearAlgebra.norm(polarizationPlaneNormal));
		
		double cosineTerm = Maths.square(LinearAlgebra.dotProduct(s1, s0)).getDouble(0);
		double sineTerm = 1 - cosineTerm;
		double azimuthalTerm = 2*Maths.square(LinearAlgebra.dotProduct(s1, pn)).getDouble(0);
		double azimuthalSineTerm  = sineTerm - azimuthalTerm;
		
		double cor = polarisationCorrectionFactor(cosineTerm, azimuthalSineTerm, polarizationFactor);
		return uncorrectedValue/cor;
	}
	
	// does the common mathematics between the angle and vector versions of the polarization correction for a single value
	private static double polarisationCorrectionFactor(double cosineTerm, double azimuthalSineTerm, double polarisationFactor) {
		return 1./2 * (1 + cosineTerm - polarisationFactor * azimuthalSineTerm);
	}
	
	public static double detectorTranmissionCorrection(double correctionValue, double tth, double transmissionFactor) {
		//J. Zaleski, G. Wu and P. Coppens, J. Appl. Cryst. (1998). 31, 302-304
		//K = [1 - exp(lnT/cos(a))]/(1-T)
		double cor = (1-Math.exp(Math.log(transmissionFactor)/Math.cos(tth)))/(1-transmissionFactor);
		
		return correctionValue/cor;
	}
	
	public static void solidAngleCorrection(Dataset correctionArray, Dataset tth) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		Dataset cor = Maths.cos(tth);
		cor.ipower(3);
		correctionArray.idivide(cor);
	}
	
	/**
 	 * Applies the polarisation correction to a Dataset.
	 * <p>
	 * The beam and scattered beam parameters are defined by angles, stored in
	 * {@link Dataset}s.
	 *
	 * @param correctionArray
	 * 						A {@link Dataset} of values to be corrected.
	 * @param tth
	 * 			A {@link Dataset} of the 2θ scattering angle for each data
	 * 			point.
	 * @param angle
	 * 				A {@link Dataset} of the azimuthal angle of the scattered
	 * 				beam relative to the plane of polarisation of the incident
	 * 				beam. 
	 * @param factor
	 *				The fraction of the beam which is polarised. Takes
	 *				the value 1.0 for a perfectly polarised beam, and
	 *				0.0 for an unpolarised beam.
	 * 
	 */
	public static void polarisationCorrection(Dataset correctionArray, Dataset tth, Dataset angle, double factor) {
		//R Kahn et al, J Appl Cryst 15 330 (1982)
		//pol(th) = 1/2[1+cos2(tth) - f*cos(2*azimuthal)sin2(tth)
		
		Dataset cosineTerm = Maths.cos(tth);
		cosineTerm.ipower(2);
		
		//use 1-cos2(tth) instead of sin2(tth)
		Dataset azimuthalSineTerm = Maths.subtract(1, cosineTerm);
		azimuthalSineTerm.imultiply(Maths.cos(Maths.multiply(angle,2)));
//		azimuthalSineTerm.imultiply(factor);
//		
//		Dataset cor = Maths.add(cosineTerm, 1);
//		cor.isubtract(azimuthalSineTerm);
//		cor.idivide(2);
		Dataset corCommon = polarisationCorrectionFactor(cosineTerm, azimuthalSineTerm, factor);
		correctionArray.idivide(corCommon);
	}

	/**
	 * Applies the polarisation correction to a Dataset.
	 * <p>
	 * The beam and scattered beam parameters are defined by vectors, stored in
	 * {@link Dataset}s.
	 * @param correctionArray
	 * 						A {@link Dataset} of n values to be corrected.
	 * @param scatteredBeam
	 * 						A {@link Dataset} of n×3 values describing the
	 * 						direction vectors of the beam for each data value.
	 * @param incidentBeam
	 * 						A {@link Dataset} of 3 values representing the
	 * 						direction vector of the incident beam
	 * @param polarizationPlaneNormal
	 * 						A {@link Dataset} of 3 values describing the normal
	 * 						vector to the plan of polarisation of the incident
	 * 						beam. Should be orthogonal to the incident beam
	 * 						vector.
	 * @param polarizationFactor
	 * 						The fraction of the beam which is polarised. Takes
	 * 						the value 1.0 for a perfectly polarised beam, and
	 * 						0.0 for an unpolarised beam.
	 */
	public static void polarisationCorrection(Dataset correctionArray, Dataset scatteredBeam, Dataset incidentBeam, Dataset polarizationPlaneNormal, double polarizationFactor) {
		// Normalize the static data
		Dataset s0 = Maths.divide(incidentBeam, LinearAlgebra.norm(incidentBeam)),
				pn = Maths.divide(polarizationPlaneNormal, LinearAlgebra.norm(polarizationPlaneNormal));
		
		// Normalize the scattered beam data
		Dataset s1 = scatteredBeam;
		
		// Dot products of the scattered beam directions with the incident beam and polarization normal
		Dataset s1s0 = LinearAlgebra.dotProduct(s1, s0);
		Dataset s1pn = LinearAlgebra.dotProduct(s1, pn);
		
		Dataset cosineTerm = Maths.square(s1s0);
		Dataset sineTerm = Maths.subtract(1, cosineTerm);
		Dataset azimuthalTerm = Maths.multiply(2, s1pn);
		Dataset azimuthalSineTerm = Maths.subtract(sineTerm, azimuthalTerm);
		
		Dataset cor = polarisationCorrectionFactor(cosineTerm, azimuthalSineTerm, polarizationFactor);
		
		correctionArray.idivide(cor);
	}
	
	// Performs the common calculations for the angle and vector forms of the polarization correction for Datasets
	private static Dataset polarisationCorrectionFactor(Dataset cosineTerm, Dataset azimuthalSineTerm, double polarizationFactor) {
		return (Maths.add(1, cosineTerm).isubtract(Maths.multiply(polarizationFactor, azimuthalSineTerm))).idivide(2);
	}
	
	public static void detectorTranmissionCorrection(Dataset correctionArray, Dataset tth, double transmissionFactor) {
		//J. Zaleski, G. Wu and P. Coppens, J. Appl. Cryst. (1998). 31, 302-304
		//K = [1 - exp(lnT/cos(a))]/(1-T)
		Dataset cor = Maths.cos(tth);
		cor = Maths.divide(Math.log(transmissionFactor), cor);
		cor = Maths.exp(cor);
		cor = Maths.subtract(1, cor);
		cor.idivide(1-transmissionFactor);
		correctionArray.idivide(cor);
	}
	
	public static void lorentzCorrection(Dataset correctionArray, Dataset tth) {
		//Norby J. Appl. Cryst. (1997). 30, 21-30
		//1/sin2(theta)cos(theta)
		Dataset th = Maths.divide(tth, 2);
		Dataset s2 = Maths.sin(th);
		s2.ipower(2);
		th = Maths.cos(th);
		s2.imultiply(th);
		correctionArray.idivide(s2);
	}
	
//	private static final class CacheKey {
//		
//		private IDiffractionMetadata meta;
//		private XAxis axis;
//		private boolean minMax;
//		
//		public CacheKey(IDiffractionMetadata meta, XAxis axis, boolean minMax) {
//			this.meta = meta;
//			this.axis = axis;
//			this.minMax = minMax;
//		}
//		
//	}
}
