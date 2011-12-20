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

import uk.ac.diamond.scisoft.analysis.roi.ROIBase;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ROIBaseHelper extends ROIHelper<ROIBase> {
	public ROIBaseHelper() {
		super(ROIBase.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		return super.flatten((ROIBase) obj, ROIBase.class.getCanonicalName(), rootFlattener);
	}

	@Override
	public ROIBase unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		ROIBase roiBase = new ROIBase();
		roiBase.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiBase.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		return roiBase;
	}
}
