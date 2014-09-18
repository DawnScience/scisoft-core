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

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.Reshapeable;
import org.eclipse.dawnsci.analysis.api.metadata.Sliceable;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;

public class SliceableTestMetadata implements MetadataType {

	@Reshapeable
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

	public SliceableTestMetadata(SliceableTestMetadata stm) {
		if (stm.ds == null) {
			ds = null;
		} else {
			ds = stm.ds.getSliceView();
		}

		if (stm.dds == null) {
			dds = null;
		} else {
			dds = new DoubleDataset[stm.dds.length];
			for (int i = 0; i < dds.length; i++) {
				dds[i] = stm.dds[i].getView();
			}
		}

		if (stm.lds == null) {
			lds = null;
		} else {
			lds = new ArrayList<>();
			for (ShortDataset d : stm.lds) {
				lds.add(d.getView());
			}
		}

		if (stm.mds == null) {
			mds = null;
		} else {
			mds = new HashMap<>();
			for (String s : stm.mds.keySet()) {
				mds.put(s, stm.mds.get(s).getView());
			}
		}

		if (stm.l2deep == null) {
			l2deep = null;
		} else {
			l2deep = new ArrayList<>();
			for (DoubleDataset[] da : stm.l2deep) {
				DoubleDataset[] ta = new DoubleDataset[da.length];
				for (int i = 0; i < ta.length; i++) {
					ta[i] = da[i].getView();
				}
				l2deep.add(ta);
			}
		}
	}

	@Override
	public MetadataType clone() {
		return new SliceableTestMetadata(this);
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
