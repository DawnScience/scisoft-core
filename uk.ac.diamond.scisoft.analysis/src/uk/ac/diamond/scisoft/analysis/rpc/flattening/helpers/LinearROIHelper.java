/*
 * Copyright 2011 Diamond Light Source Ltd.
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
		roiOut.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiOut.setPoint(spt);
		roiOut.setPlot(isPlot);
		roiOut.setCrossHair(crossHair);
		return roiOut;
	}
}
