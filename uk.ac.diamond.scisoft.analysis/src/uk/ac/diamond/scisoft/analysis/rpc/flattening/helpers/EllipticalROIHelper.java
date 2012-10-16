/*
 * Copyright 2012 Diamond Light Source Ltd.
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

import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class EllipticalROIHelper extends ROIHelper<EllipticalROI> {
	public static final String SAXIS = "saxis";
	public static final String ANG = "ang";

	public EllipticalROIHelper() {
		super(EllipticalROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		EllipticalROI roi = (EllipticalROI) obj;
		Map<String, Object> outMap = super.flatten(roi, EllipticalROI.class.getCanonicalName(), rootFlattener);
		outMap.put(SAXIS, rootFlattener.flatten(roi.getSemiAxes()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		return outMap;
	}

	@Override
	public EllipticalROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		EllipticalROI roiOut = new EllipticalROI();
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setSemiAxes((double[]) rootFlattener.unflatten(inMap.get(SAXIS)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));

		return roiOut;
	}
}
