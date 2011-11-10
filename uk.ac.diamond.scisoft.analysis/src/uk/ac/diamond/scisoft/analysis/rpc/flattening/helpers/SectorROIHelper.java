/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import uk.ac.diamond.scisoft.analysis.roi.SectorROI;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class SectorROIHelper extends ROIHelper<SectorROI> {
	public static final String ANG = "ang";
	public static final String RAD = "rad";
	public static final String CLIPPING_COMPENSATION = "clippingCompensation";
	public static final String SYMMETRY = "symmetry";
	public static final String COMBINE_SYMMETRY = "combineSymmetry";
	public static final String AVERAGE_AREA = "averageArea";

	public SectorROIHelper() {
		super(SectorROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		SectorROI roi = (SectorROI) obj;
		Map<String, Object> outMap = super.flatten(roi, SectorROI.class.getCanonicalName(), rootFlattener);
		outMap.put(ANG, rootFlattener.flatten(roi.getAngles()));
		Object flatten = rootFlattener.flatten(roi.getRadii());
		outMap.put(RAD, flatten);
		outMap.put(CLIPPING_COMPENSATION, rootFlattener.flatten(roi.isClippingCompensation()));
		outMap.put(SYMMETRY, rootFlattener.flatten(roi.getSymmetry()));
		outMap.put(COMBINE_SYMMETRY, rootFlattener.flatten(roi.isCombineSymmetry()));
		outMap.put(AVERAGE_AREA, rootFlattener.flatten(roi.isAverageArea()));

		return outMap;
	}

	@Override
	public SectorROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		SectorROI roiOut = new SectorROI();
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setAngles((double[]) rootFlattener.unflatten(inMap.get(ANG)));
		double[] unflatten = (double[]) rootFlattener.unflatten(inMap.get(RAD));
		roiOut.setRadii(unflatten);
		roiOut.setClippingCompensation((Boolean) rootFlattener.unflatten(inMap.get(CLIPPING_COMPENSATION)));
		roiOut.setSymmetry((Integer) rootFlattener.unflatten(inMap.get(SYMMETRY)));
		roiOut.setCombineSymmetry((Boolean) rootFlattener.unflatten(inMap.get(COMBINE_SYMMETRY)));
		roiOut.setAverageArea((Boolean) rootFlattener.unflatten(inMap.get(AVERAGE_AREA)));
		return roiOut;
	}
}
