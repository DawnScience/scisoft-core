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

import org.eclipse.dawnsci.analysis.dataset.roi.ROIBase;

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
		roiBase.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiBase.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiBase.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		return roiBase;
	}
}
