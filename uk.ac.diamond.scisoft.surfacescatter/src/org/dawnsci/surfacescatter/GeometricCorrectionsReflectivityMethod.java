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
import org.eclipse.january.dataset.Maths;
/**Calculates the geometric correction factors for X-ray reflectivity. Outputs a single double.
 * 
 * 
 */


public class GeometricCorrectionsReflectivityMethod {

	public static double reflectivityCorrectionsBatchGaussianPofile(ILazyDataset dcdtheta, int k, double  angularfudgefactor, double beamHeight, double footprint) throws Exception {
			
		double theta = ScanMetadataForDialog.getTheta(dcdtheta, k);
		
		NormalDistribution beamfootprint  = new NormalDistribution(0, (beamHeight/2*Math.sqrt(2*Math.log(2))));
		
		double areaCorrection =1/(2*((beamfootprint.cumulativeProbability(0.5*footprint*Math.sin((0.5*theta + angularfudgefactor)*Math.PI/180)))-.5));
		
		return areaCorrection;
	}
	
	
	public static double reflectivityCorrectionsBatchSimpleScaling(ILazyDataset dcdtheta, int k, double beamHeight, double footprint) throws Exception {
		
		double theta = ScanMetadataForDialog.getTheta(dcdtheta, k);
		
		double spilloverAngle =Math.asin(beamHeight/(footprint))*180/Math.PI;
		
		double areaCorrection = spilloverAngle/(theta);
		
		if(theta > spilloverAngle){
			areaCorrection = 1;
		}
	
		return areaCorrection;
	}
	
	
}

//TEST
