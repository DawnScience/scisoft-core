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

import uk.ac.diamond.scisoft.analysis.roi.ROIBase;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class ROIHelper<T> extends MapFlatteningHelper<T> {

	public ROIHelper(Class<T> type) {
		super(type);
	}

	public static final String SPT = "spt";
	public static final String PLOT = "plot";

	public Map<String, Object> flatten(ROIBase roi, String typeName, IRootFlattener rootFlattener) {
		Map<String, Object> outMap = createMap(typeName);
		outMap.put(SPT, rootFlattener.flatten(roi.getPoint()));
		outMap.put(PLOT, rootFlattener.flatten(roi.isPlot()));
		return outMap;
	}
}