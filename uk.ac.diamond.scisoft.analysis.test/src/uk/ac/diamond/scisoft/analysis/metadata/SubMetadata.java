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

import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.Sliceable;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;

public class SubMetadata extends SliceableTestMetadata {
	@Sliceable
	ILazyDataset ldb;

	public SubMetadata(ILazyDataset ld, DoubleDataset[] array, List<ShortDataset> list, Map<String, BooleanDataset> map, List<DoubleDataset[]> l2) {
		super(ld, array, list, map, l2);
		ldb = new DoubleDataset(ld.getShape());
	}

	public ILazyDataset getLazyDataset2() {
		return ldb;
	}

	@Override
	public MetadataType clone() {
		SubMetadata c = new SubMetadata(ldb, getArray(), getList(), getMap(), getListOfArrays());
		c.ldb = ldb;
		return c;
	}
}
