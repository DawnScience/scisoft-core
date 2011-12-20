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

import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class AxisMapBeanHelper extends MapFlatteningHelper<AxisMapBean> {

	public static final String AXIS_ID = "axisID";
	public static final String MAP_MODE = "mapMode";

	public AxisMapBeanHelper() {
		super(AxisMapBean.class);
	}

	@Override
	public AxisMapBean unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		AxisMapBean outBean = new AxisMapBean((Integer) thisMap.get(MAP_MODE));
		Object[] objIds = (Object[]) thisMap.get(AXIS_ID);
		String[] ids = new String[objIds.length];
		System.arraycopy(objIds, 0, ids, 0, objIds.length);
		outBean.setAxisID(ids);
		return outBean;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		AxisMapBean thisAxisMap = (AxisMapBean) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(AXIS_ID, rootFlattener.flatten(thisAxisMap.getAxisID()));
		outMap.put(MAP_MODE, rootFlattener.flatten(thisAxisMap.getMapMode()));
		return outMap;
	}

}
