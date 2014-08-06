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

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;

public class SliceableTestMetadata implements MetadataType {

	@Sliceable
	private ILazyDataset ds;
	@Sliceable
	private DoubleDataset[] dds;
	@Sliceable
	private List<ShortDataset> lds;
	@Sliceable
	private Map<String, BooleanDataset> mds;
	@Sliceable
	private List<DoubleDataset[]> l2deep;

	public SliceableTestMetadata(ILazyDataset ld, DoubleDataset[] array, List<ShortDataset> list, Map<String, BooleanDataset> map, List<DoubleDataset[]> l2) {
		ds = ld;
		dds = array;
		lds = list;
		mds = map;
		l2deep = l2;
	}

	@Override
	public MetadataType clone() {
		
		List<DoubleDataset[]> l2clone = new ArrayList<DoubleDataset[]>(l2deep.size());
		
		for (int i = 0; i< l2deep.size(); i++) l2clone.add(l2deep.get(i).clone());
		
		return new SliceableTestMetadata(ds, dds.clone(), new ArrayList<ShortDataset>(lds), new HashMap<String, BooleanDataset>(mds), l2clone);
	}

	public ILazyDataset getLazyDataset() {
		return ds;
	}

	public DoubleDataset[] getArray() {
		return dds;
	}
	public List<ShortDataset> getList() {
		return lds;
	}
	public Map<String, BooleanDataset> getMap() {
		return mds;
	}
	
	public List<DoubleDataset[]> getListOfArrays() {
		return l2deep;
	}
}
