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

import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;
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
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setLengths((double[]) rootFlattener.unflatten(inMap.get(LEN)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));
		roiOut.setClippingCompensation((Boolean) rootFlattener.unflatten(inMap.get(CLIPPING_COMPENSATION)));

		return roiOut;
	}
}
