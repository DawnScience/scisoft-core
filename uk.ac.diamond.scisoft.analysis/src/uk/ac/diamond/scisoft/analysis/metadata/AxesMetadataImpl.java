/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

public class AxesMetadataImpl implements AxesMetadata {

	Map<Integer, List<ILazyDataset>> axesMap = new HashMap<Integer, List<ILazyDataset>>(0);
	int dims;

	public AxesMetadataImpl(int axisDimensions) {
		dims = axisDimensions;
	}

	public AxesMetadataImpl(AxesMetadataImpl axesMetadataImpl) {
		axesMap.putAll(axesMetadataImpl.axesMap);
		dims = axesMetadataImpl.dims;
	}

	public void setAxis(int axisDim, ILazyDataset[] axisData) {
		ArrayList<ILazyDataset> axisList = new ArrayList<ILazyDataset>(0);
		for (int i = 0; i < axisData.length; i++) {
			axisList.add(axisData[i]);
		}
		axesMap.put(axisDim, axisList);
	}

	@Override
	public ILazyDataset[] getAxes() {
		ILazyDataset[] result = new ILazyDataset[dims];
		for (int i = 0; i < dims; i++) {
			result[i] = getAxis(i)[0];
		}
		return null;
	}

	@Override
	public ILazyDataset[] getAxis(int axisDim) {
		return axesMap.get(axisDim).toArray(new ILazyDataset[0]);
	}

	@Override
	public AxesMetadata clone() {
		return new AxesMetadataImpl(this);
	}

	public void addAxis(ILazyDataset axisData, Integer axisDim) {
		if (!axesMap.containsKey(axisDim)) {
			ArrayList<ILazyDataset> axisList = new ArrayList<ILazyDataset>(0);
			axesMap.put(axisDim, axisList);
		}
		axesMap.get(axisDim).add(axisData);
	}

}
