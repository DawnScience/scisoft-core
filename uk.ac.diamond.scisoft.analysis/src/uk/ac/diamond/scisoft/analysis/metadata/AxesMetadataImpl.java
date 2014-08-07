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
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

public class AxesMetadataImpl implements AxesMetadata {

	@Sliceable
	List<ILazyDataset>[] allAxes;

	@SuppressWarnings("unchecked")
	public AxesMetadataImpl(int rank) {
		allAxes = new List[rank];
	}

	@SuppressWarnings("unchecked")
	public AxesMetadataImpl(AxesMetadataImpl axesMetadataImpl) {
		int r = axesMetadataImpl.allAxes.length;
		allAxes = new List[r];
		for (int i = 0; i < r; i++) {
			List<ILazyDataset> list = axesMetadataImpl.allAxes[i];
			if (list != null) allAxes[i] = new ArrayList<ILazyDataset>(list);
		}
	}

	public void setAxis(int axisDim, ILazyDataset[] axisData) {
		ArrayList<ILazyDataset> axisList = new ArrayList<ILazyDataset>(0);
		for (int i = 0; i < axisData.length; i++) {
			axisList.add(axisData[i]);
		}
		allAxes[axisDim] = axisList;
	}

	@Override
	public ILazyDataset[] getAxes() {
		ILazyDataset[] result = new ILazyDataset[allAxes.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = getAxis(i)[0];
		}
		return result;
	}

	@Override
	public ILazyDataset[] getAxis(int axisDim) {
		if (allAxes[axisDim] == null)
			return null;
		return allAxes[axisDim].toArray(new ILazyDataset[0]);
	}

	@Override
	public AxesMetadata clone() {
		return new AxesMetadataImpl(this);
	}

	/**
	 * Add axis data to given dimension
	 * @param axisData dataset for axis
	 * @param axisDim dimension (n.b. this is zero-based)
	 */
	public void addAxis(ILazyDataset axisData, int axisDim) {
		if (allAxes[axisDim] == null) {
			allAxes[axisDim] = new ArrayList<ILazyDataset>();
		}
		allAxes[axisDim].add(axisData);
	}

}
