/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.eclipse.january.dataset.IDataset;

public class ReflectivityCorrections {

	public static double reflectivityCorrectionsBatch(IDataset input, double  angularfudgefactor, double BeamHeight, double footprint) throws Exception {
	
		double theta;
		
		theta = ScanMetadata.getTheta(input);
		
		System.out.println("theta:  " + " , " + theta);
		
		NormalDistribution beamfootprint  = new NormalDistribution(0, (1e-3*BeamHeight/2*Math.sqrt(2*Math.log(2) - 0.5)));
		double areaCorrection = 2*(beamfootprint.cumulativeProbability((footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180))));
			
		return areaCorrection;
	}
}
