/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import uk.ac.diamond.scisoft.analysis.roi.LinearROI;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class LinearROIHelper extends ROIHelper<LinearROI> {
	public static final String LEN = "len";
	public static final String ANG = "ang";
	public static final String CROSS_HAIR = "crossHair";

	public LinearROIHelper() {
		super(LinearROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		LinearROI roi = (LinearROI) obj;
		Map<String, Object> outMap = super.flatten(roi, LinearROI.class.getCanonicalName(), rootFlattener);
		outMap.put(LEN, rootFlattener.flatten(roi.getLength()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		outMap.put(CROSS_HAIR, rootFlattener.flatten(roi.isCrossHair()));

		return outMap;
	}

	@Override
	public LinearROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		double[] spt = (double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT));
		Boolean isPlot = (Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT));
		Double len = (Double) rootFlattener.unflatten(inMap.get(LEN));
		Double ang = (Double) rootFlattener.unflatten(inMap.get(ANG));
		Boolean crossHair = (Boolean) rootFlattener.unflatten(inMap.get(CROSS_HAIR));
		LinearROI roiOut = new LinearROI(len, ang);
		roiOut.setPoint(spt);
		roiOut.setPlot(isPlot);
		roiOut.setCrossHair(crossHair);
		return roiOut;
	}
}
