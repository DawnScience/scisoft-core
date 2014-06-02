/*-
 * Copyright 2014 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.Arrays;

import javax.vecmath.Vector3d;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class PixelIntegrationUtils {
	
	public static AbstractDataset generate2ThetaArrayRadians(IDiffractionMetadata md) {
		return generate2ThetaArrayRadians(getShape(md), md);
	}
	
	public static AbstractDataset generate2ThetaArrayRadians(int[] shape, IDiffractionMetadata md) {
		
		QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());
		return generateRadialArray(shape, qSpace, XAxis.ANGLE, true);
		
	}
	
	public static AbstractDataset generateQArray(IDiffractionMetadata md) {
		return generateQArray(getShape(md), md);
	}
	
	public static AbstractDataset generateQArray(int[] shape, IDiffractionMetadata md) {
		QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());
		return generateRadialArray(shape, qSpace, XAxis.Q);
		
	}
	
	public static AbstractDataset generateAzimuthalArray(IDiffractionMetadata metadata, boolean radians) {
		return generateAzimuthalArray(metadata.getDetector2DProperties().getBeamCentreCoords(), getShape(metadata), radians);
	}
	
	public static AbstractDataset generateAzimuthalArray(int[] shape, IDiffractionMetadata metadata, boolean radians) {
		return generateAzimuthalArray(metadata.getDetector2DProperties().getBeamCentreCoords(), shape,radians);
	}
	
	public static AbstractDataset generateAzimuthalArray(double[] beamCentre, int[] shape, boolean radians) {
		
		AbstractDataset out = AbstractDataset.zeros(shape, Dataset.FLOAT64);
		PositionIterator iter = out.getPositionIterator();

		int[] pos = iter.getPos();
		//FIXME half pixel issue
		
		while (iter.hasNext()) {
			double val = Math.atan2(pos[0]-beamCentre[1],pos[1]-beamCentre[0]);
			if (radians) out.set(val, pos);
			else out.set(Math.toDegrees(val), pos);
		}
		
		return out;
	}
	
	public static AbstractDataset[] generateMinMaxAzimuthalArray(double[] beamCentre, int[] shape, boolean radians) {
		
		AbstractDataset aMax = AbstractDataset.zeros(shape, Dataset.FLOAT64);
		AbstractDataset aMin = AbstractDataset.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = aMax.getPositionIterator();
		int[] pos = iter.getPos();
		double[] vals = new double[4];
		
		while (iter.hasNext()) {
			//FIXME half pixel issue
			vals[0] = Math.atan2(pos[0]-beamCentre[1]-0.5,pos[1]-beamCentre[0]-0.5);
			vals[1] = Math.atan2(pos[0]-beamCentre[1]+0.5,pos[1]-beamCentre[0]-0.5);
			vals[2] = Math.atan2(pos[0]-beamCentre[1]-0.5,pos[1]-beamCentre[0]+0.5);
			vals[3] = Math.atan2(pos[0]-beamCentre[1]+0.5,pos[1]-beamCentre[0]+0.5);
			
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
		
		return new AbstractDataset[]{aMin,aMax};
	}
	
	public static  AbstractDataset[] generateMinMaxRadialArray(int[] shape, QSpace qSpace, XAxis xAxis) {
		
		if (qSpace == null) return null;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		AbstractDataset radialArrayMax = AbstractDataset.zeros(shape, Dataset.FLOAT64);
		AbstractDataset radialArrayMin = AbstractDataset.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = radialArrayMax.getPositionIterator();
		int[] pos = iter.getPos();

		double[] vals = new double[4];
		double w = qSpace.getWavelength();
		while (iter.hasNext()) {
			
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			
			if (xAxis != XAxis.PIXEL) {
				vals[0] = qSpace.qFromPixelPosition(pos[1]-0.5, pos[0]-0.5).length();
				vals[1] = qSpace.qFromPixelPosition(pos[1]+0.5, pos[0]-0.5).length();
				vals[2] = qSpace.qFromPixelPosition(pos[1]-0.5, pos[0]+0.5).length();
				vals[3] = qSpace.qFromPixelPosition(pos[1]+0.5, pos[0]+0.5).length();
			} else {
				vals[0] = Math.hypot(pos[1]-0.5-beamCentre[0], pos[0]-0.5-beamCentre[1]);
				vals[1] = Math.hypot(pos[1]+0.5-beamCentre[0], pos[0]-0.5-beamCentre[1]);
				vals[2] = Math.hypot(pos[1]-0.5-beamCentre[0], pos[0]+0.5-beamCentre[1]);
				vals[3] = Math.hypot(pos[1]+0.5-beamCentre[0], pos[0]+0.5-beamCentre[1]);
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
		return  new AbstractDataset[]{radialArrayMin,radialArrayMax};
	}
	
	public static AbstractDataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis) {
		return generateRadialArray(shape, qSpace, xAxis, false);
	}
	
	private static AbstractDataset generateRadialArray(int[] shape, QSpace qSpace, XAxis xAxis, boolean radians) {
		
		if (qSpace == null) return null;
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		AbstractDataset ra = AbstractDataset.zeros(shape, Dataset.FLOAT64);

		PositionIterator iter = ra.getPositionIterator();
		int[] pos = iter.getPos();

		while (iter.hasNext()) {
			
			Vector3d q;
			double value = 0;
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			q = qSpace.qFromPixelPosition(pos[1], pos[0]);
			
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
				value = Math.hypot(pos[1]-beamCentre[0],pos[0]-beamCentre[1]);
				break; 
			}
			ra.set(value, pos);
		}
		
		return ra;
	}
	
	public static int[] getShape(IDiffractionMetadata metadata) {
		return new int[]{metadata.getDetector2DProperties().getPy(), metadata.getDetector2DProperties().getPx()};
	}
	
	public static AbstractDataset generate2Dfrom1D(IDataset[] xy1d, AbstractDataset array2Dx) {
		
		DoubleDataset[] inXy1D = new DoubleDataset[2];
		inXy1D[0] = (DoubleDataset) DatasetUtils.cast(xy1d[0], Dataset.FLOAT64);
		inXy1D[1] = (DoubleDataset)DatasetUtils.cast(xy1d[1], Dataset.FLOAT64);
		
		double min = inXy1D[0].min().doubleValue();
		double max = inXy1D[0].max().doubleValue();
		
		SplineInterpolator si = new SplineInterpolator();
		PolynomialSplineFunction poly = si.interpolate(inXy1D[0].getData(),inXy1D[1].getData());
		AbstractDataset image = AbstractDataset.zeros(array2Dx.getShape(),Dataset.FLOAT64);
		double[] buf = (double[])image.getBuffer();
		
		IndexIterator iterator = array2Dx.getIterator();
		
		while (iterator.hasNext()) {
			double e = array2Dx.getElementDoubleAbs(iterator.index);
			if (e <= max && e >= min) buf[iterator.index] = poly.value(e);
			
		}
		
		return image;
	}
	
	public static void solidAngleCorrection(AbstractDataset correctionArray, AbstractDataset tth) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		AbstractDataset cor = Maths.cos(tth);
		cor.ipower(3);
		correctionArray.idivide(cor);
	}
	
	public static void polarisationCorrection(AbstractDataset correctionArray, AbstractDataset tth, AbstractDataset angle, double factor) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		//pol(th) = 1/2[1+cos2(tth) - f*cos(azimuthal)sin2(tth)
		
		AbstractDataset cosSq = Maths.cos(tth);
		cosSq.ipower(2);
		
		//use 1-cos2(tth) instead of sin2(tth)
		AbstractDataset sub = Maths.subtract(1, cosSq);
		sub.imultiply(Maths.cos(angle));
		sub.imultiply(factor);
		
		AbstractDataset cor = Maths.add(cosSq, 1);
		cor.isubtract(sub);
		cor.idivide(2);
		correctionArray.idivide(cor);
	}
	
	public static void detectorTranmissionCorrection(AbstractDataset correctionArray, AbstractDataset tth, double transmissionFactor) {
		//J. Zaleski, G. Wu and P. Coppens, J. Appl. Cryst. (1998). 31, 302-304
		//K = [1 - exp(lnT/cos(a))]/(1-T)
		AbstractDataset cor = Maths.cos(tth);
		cor = Maths.divide(Math.log(transmissionFactor), cor);
		cor = Maths.exp(cor);
		cor = Maths.subtract(1, cor);
		cor.idivide(1-transmissionFactor);
		correctionArray.idivide(cor);
	}
	
	public static void lorentzCorrection(AbstractDataset correctionArray, AbstractDataset tth) {
		//Norby J. Appl. Cryst. (1997). 30, 21-30
		//1/sin2(theta)cos(theta)
		AbstractDataset th = Maths.divide(tth, 2);
		AbstractDataset s2 = Maths.sin(th);
		s2.ipower(2);
		th = Maths.cos(th);
		s2.imultiply(th);
		correctionArray.idivide(s2);
	}
}
