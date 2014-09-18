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

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class RectangularROIHelper extends ROIHelper<RectangularROI> {
	public static final String LEN = "len";
	public static final String ANG = "ang";
	public static final String CLIPPING_COMPENSATION = "clippingCompensation";

	public RectangularROIHelper() {
		super(RectangularROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		RectangularROI roi = (RectangularROI) obj;
		Map<String, Object> outMap = super.flatten(roi, RectangularROI.class.getCanonicalName(), rootFlattener);
		outMap.put(LEN, rootFlattener.flatten(roi.getLengths()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		outMap.put(CLIPPING_COMPENSATION, rootFlattener.flatten(roi.isClippingCompensation()));
		return outMap;
	}

	@Override
	public RectangularROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		RectangularROI roiOut = new RectangularROI();
		roiOut.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setLengths((double[]) rootFlattener.unflatten(inMap.get(LEN)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));
		roiOut.setClippingCompensation((Boolean) rootFlattener.unflatten(inMap.get(CLIPPING_COMPENSATION)));

		return roiOut;
	}
}
