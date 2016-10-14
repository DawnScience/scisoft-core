/*- * COPYRIGHT 2016 DIAMOND LIGHT SOURCE LTD.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
/**Calculates the geometric correction factors for X-ray reflectivity. Outputs a single double.
 * 
 * 
 */


public class GeometricCorrectionsReflectivityMethod {

	public static double reflectivityCorrectionsBatch(ILazyDataset dcdtheta, int k, SuperModel sm, IDataset input, double  angularfudgefactor, double BeamHeight, double footprint) throws Exception {
			
		double theta = ScanMetadataForDialog.getTheta(dcdtheta, k, input, sm);
		
		NormalDistribution beamfootprint  = new NormalDistribution(0, (1e-3*BeamHeight/2*Math.sqrt(2*Math.log(2) - 0.5)));
		double areaCorrection = 2*(beamfootprint.cumulativeProbability((footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180))/2));
		
		return areaCorrection;
	}
}

//TEST
