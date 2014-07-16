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

package uk.ac.diamond.scisoft.analysis.processing;

import java.io.Serializable;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;

/**
 * Deals with holding the data which a RichDataset requires.
 */
class RichDatasetBean implements Serializable {
	
	protected ILazyDataset         data;
	protected List<IDataset>       axes;
	protected ILazyDataset         mask;
	protected IMetaData            meta;
	
	public RichDatasetBean(ILazyDataset data, List<IDataset> axes, ILazyDataset mask, IMetaData meta) {
		this.data = data;
		this.axes = axes;
		this.mask = mask;
		this.meta = meta;
	}
	
	public ILazyDataset getData() {
		return data;
	}
	public void setData(ILazyDataset data) {
		this.data = data;
	}
	public List<IDataset> getAxes() {
		return axes;
	}
	public void setAxes(List<IDataset> axes) {
		this.axes = axes;
	}
	public ILazyDataset getMask() {
		return mask;
	}
	public void setMask(ILazyDataset mask) {
		this.mask = mask;
	}
	public IMetaData getMeta() throws Exception {
		return meta!=null ? meta : data.getMetadata();
	}
	public void setMeta(IMetaData meta) {
		this.meta = meta;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axes == null) ? 0 : axes.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RichDatasetBean other = (RichDatasetBean) obj;
		if (axes == null) {
			if (other.axes != null)
				return false;
		} else if (!axes.equals(other.axes))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (mask == null) {
			if (other.mask != null)
				return false;
		} else if (!mask.equals(other.mask))
			return false;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		return true;
	}

}
