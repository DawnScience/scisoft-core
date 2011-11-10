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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataSetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DataBeanHelper extends MapFlatteningHelper<DataBean> {

	public static final String DATA = "data";
	public static final String AXISDATA = "axisData";

	public DataBeanHelper() {
		super(DataBean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataBean unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		DataBean outBean = new DataBean();
		Object[] dataGeneric = (Object[]) rootFlattener.unflatten(thisMap.get(DATA));
		Map<Object, Object> axisDataGeneric = (Map<Object, Object>) rootFlattener.unflatten(thisMap.get(AXISDATA));

		List<DataSetWithAxisInformation> data = new ArrayList<DataSetWithAxisInformation>();
		for (Object object : dataGeneric) {
			data.add((DataSetWithAxisInformation) object);
		}

		Map<String, AbstractDataset> axisData = new HashMap<String, AbstractDataset>();
		for (Entry<Object, Object> entry : axisDataGeneric.entrySet()) {
			axisData.put((String) entry.getKey(), (AbstractDataset) entry.getValue());
		}

		outBean.setData(data);
		outBean.setAxisData(axisData);

		return outBean;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		DataBean dataBean = (DataBean) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DATA, rootFlattener.flatten(dataBean.getData()));
		outMap.put(AXISDATA, rootFlattener.flatten(dataBean.getAxisData()));
		return outMap;
	}

}