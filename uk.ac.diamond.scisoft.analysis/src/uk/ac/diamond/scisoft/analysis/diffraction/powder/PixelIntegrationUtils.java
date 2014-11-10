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
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class PixelIntegrationUtils {
	
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
			double val = -Math.atan2(pos[0]+0.5-beamCentre[1],pos[1]+0.5-beamCentre[0]);
			if (radians) out.set(val, pos);
			else out.set(Math.toDegrees(val), pos);
		}
		
		return out;
	}
	
	public static void inPlaceOffsetAzimuthalArray(Dataset array, double offset, boolean radians) {
		
		double[] range = new double[2];
		double full = 0;
		
		if (radians) {
			range[0] = -Math.PI;
			range[1] = Math.PI;
			full = Math.PI*2;
		} else {
			range[0] = -180;
			range[1] = 180;
			full = 360;
		}
		
		IndexIterator it = array.getIterator();
		
		while (it.hasNext()) {
			double val = array.getElementDoubleAbs(it.index);
			val = val + offset;
			val = val > range[1] ? val-full : val;
			val = val < range[0] ? val+full : val;
			array.setObjectAbs(it.index, val);
		}
	}
	
	public static Dataset[] generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape, boolean radians) {
		
		Dataset aMax = DatasetFactory.zeros(shape, Dataset.FLOAT64);
		Dataset aMin = DatasetFactory.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = aMax.getPositionIterator();
		int[] pos = iter.getPos();
		double[] vals = new double[4];
		
		while (iter.hasNext()) {
			//find vals at pixel corners
			vals[0] = -Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]);
			vals[1] = -Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]);
			vals[2] = -Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]+1);
			vals[3] = -Math.atan2(pos[0]-beamCentre[1]+1,pos[1]-beamCentre[0]+1);
			
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
		return new Dataset[]{radialArrayMin,radialArrayMax};
	}
	
	public static Dataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis) {
		return generateRadialArray(shape, qSpace, xAxis, false);
	}
	
	private static Dataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis, boolean radians) {
		
		if (qSpace == null) return null;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		Dataset ra = DatasetFactory.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = ra.getPositionIterator();
		int[] pos = iter.getPos();

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
			ra.set(value, pos);
		}
		
		return ra;
	}
	
	public static int[] getShape(IDiffractionMetadata metadata) {
		return new int[]{metadata.getDetector2DProperties().getPy(), metadata.getDetector2DProperties().getPx()};
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
	
	public static void solidAngleCorrection(Dataset correctionArray, Dataset tth) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		Dataset cor = Maths.cos(tth);
		cor.ipower(3);
		correctionArray.idivide(cor);
	}
	
	public static void polarisationCorrection(Dataset correctionArray, Dataset tth, Dataset angle, double factor) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		//pol(th) = 1/2[1+cos2(tth) - f*cos(azimuthal)sin2(tth)
		
		Dataset cosSq = Maths.cos(tth);
		cosSq.ipower(2);
		
		//use 1-cos2(tth) instead of sin2(tth)
		Dataset sub = Maths.subtract(1, cosSq);
		sub.imultiply(Maths.cos(angle));
		sub.imultiply(factor);
		
		Dataset cor = Maths.add(cosSq, 1);
		cor.isubtract(sub);
		cor.idivide(2);
		correctionArray.idivide(cor);
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
}
