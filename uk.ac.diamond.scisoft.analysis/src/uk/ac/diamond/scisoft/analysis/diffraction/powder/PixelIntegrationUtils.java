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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

public class PixelIntegrationUtils {
	
	public static AbstractDataset generate2ThetaArrayRadians(int[] shape, IDiffractionMetadata md) {
		
		QSpace qSpace = new QSpace(md.getDetector2DProperties(), md.getDiffractionCrystalEnvironment());

		AbstractDataset radialArray = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		PositionIterator iter = radialArray.getPositionIterator();
		int[] pos = iter.getPos();

		while (iter.hasNext()) {
			
			Vector3d q;
			double value = 0;
			//FIXME or not fix me, but I would expect centre to be +0.5, but this
			//clashes with much of the rest of DAWN
			
			q = qSpace.qFromPixelPosition(pos[1], pos[0]);
			value = qSpace.scatteringAngle(q);
			radialArray.set(value, pos);
		}
		
		return radialArray;
	}
	
	public static AbstractDataset generateAzimuthalArrayRadians(double[] beamCentre, int[] shape, boolean centre) {
		
		AbstractDataset out = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);
		
		PositionIterator iter = out.getPositionIterator();

		int[] pos = iter.getPos();

		double offset = 0;
		
		if (!centre) offset = 0.5;
		
		while (iter.hasNext()) {
			out.set(Math.atan2(pos[0]-beamCentre[1]-offset,pos[1]-beamCentre[0]-offset), pos);
		}
		
		return out;
	}
	
	public static AbstractDataset[] generateMinMaxAzimuthalArrayRadians(double[] beamCentre, int[] shape) {
		
		AbstractDataset aMax = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);
		AbstractDataset aMin = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		PositionIterator iter = aMax.getPositionIterator();
		int[] pos = iter.getPos();
		double[] vals = new double[4];
		
		while (iter.hasNext()) {
			
			vals[0] = Math.atan2(pos[0]-beamCentre[1]-0.5,pos[1]-beamCentre[0]-0.5);
			vals[1] = Math.atan2(pos[0]-beamCentre[1]+0.5,pos[1]-beamCentre[0]-0.5);
			vals[2] = Math.atan2(pos[0]-beamCentre[1]-0.5,pos[1]-beamCentre[0]+0.5);
			vals[3] = Math.atan2(pos[0]-beamCentre[1]+0.5,pos[1]-beamCentre[0]+0.5);
			
			Arrays.sort(vals);
			
			aMax.set(vals[3], pos);
			aMin.set(vals[0], pos);
		}
		
		return new AbstractDataset[]{aMin,aMax};
	}
	
	public static void solidAngleCorrection(AbstractDataset correctionArray, AbstractDataset tth) {
		//L.B. Skinner et al Nuc Inst Meth Phys Res A 662 (2012) 61-70
		AbstractDataset cor = Maths.cos(tth);
		cor.ipower(3);
		correctionArray.imultiply(cor);
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
		correctionArray.imultiply(cor);
	}

}
