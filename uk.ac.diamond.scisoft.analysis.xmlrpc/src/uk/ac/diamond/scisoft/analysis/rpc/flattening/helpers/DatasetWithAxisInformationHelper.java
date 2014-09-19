/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DatasetWithAxisInformationHelper extends MapFlatteningHelper<DatasetWithAxisInformation> {

	public static final String DATA = "data";
	public static final String AXISMAP = "axisMap";

	public DatasetWithAxisInformationHelper() {
		super(DatasetWithAxisInformation.class);
	}

	@Override
	public DatasetWithAxisInformation unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		DatasetWithAxisInformation out = new DatasetWithAxisInformation();
		Dataset data = (Dataset) rootFlattener.unflatten(thisMap.get(DATA));
		AxisMapBean axisMap = (AxisMapBean) rootFlattener.unflatten(thisMap.get(AXISMAP));

		out.setData(data);
		out.setAxisMap(axisMap);

		return out;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		DatasetWithAxisInformation in = (DatasetWithAxisInformation) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DATA, rootFlattener.flatten(in.getData()));
		outMap.put(AXISMAP, rootFlattener.flatten(in.getAxisMap()));
		return outMap;
	}

}
