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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataSetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DataSetWithAxisInformationHelper extends MapFlatteningHelper<DataSetWithAxisInformation> {

	public static final String DATA = "data";
	public static final String AXISMAP = "axisMap";

	public DataSetWithAxisInformationHelper() {
		super(DataSetWithAxisInformation.class);
	}

	@Override
	public DataSetWithAxisInformation unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		DataSetWithAxisInformation out = new DataSetWithAxisInformation();
		AbstractDataset data = (AbstractDataset) rootFlattener.unflatten(thisMap.get(DATA));
		AxisMapBean axisMap = (AxisMapBean) rootFlattener.unflatten(thisMap.get(AXISMAP));

		out.setData(data);
		out.setAxisMap(axisMap);

		return out;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		DataSetWithAxisInformation in = (DataSetWithAxisInformation) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DATA, rootFlattener.flatten(in.getData()));
		outMap.put(AXISMAP, rootFlattener.flatten(in.getAxisMap()));
		return outMap;
	}

}