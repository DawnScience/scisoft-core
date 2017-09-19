/*- * COPYRIGHT 2016 DIAMOND LIGHT SOURCE LTD.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;


import org.apache.commons.math3.distribution.NormalDistribution;
import org.eclipse.january.dataset.ILazyDataset;
/**Calculates the geometric correction factors for X-ray reflectivity. Outputs a single double.
 * 
 * 
 */


public class GeometricCorrectionsReflectivityMethod {

	public static double reflectivityCorrectionsBatch(ILazyDataset dcdtheta, int k, double  angularfudgefactor, double beamHeight, double footprint) throws Exception {
			
		double theta = ScanMetadataForDialog.getTheta(dcdtheta, k);
		
		NormalDistribution beamfootprint  = new NormalDistribution(0, (beamHeight/2*Math.sqrt(2*Math.log(2))));
		
		
		
		double areaCorrection =1/(beamfootprint.cumulativeProbability(0.5*footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180)));
//		beamfootprint.cumulativeProbability((footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180))/2
		
		
		System.out.println("(beamfootprint.cumulativeProbability(0.5*footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180))  :  "+ (beamfootprint.cumulativeProbability(0.5*footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180))));
		
		System.out.println("(0.5*footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180)   :    " + 0.5*footprint*Math.sin((theta + angularfudgefactor)*Math.PI/180));
		
		
		System.out.println(" theta   : " + theta + "    areaCorrection  :  " + areaCorrection);
		
		return areaCorrection;
	}
}

//TEST
