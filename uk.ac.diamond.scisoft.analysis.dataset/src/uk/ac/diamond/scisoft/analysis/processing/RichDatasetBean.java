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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

/**
 * Deals with holding the data which a RichDataset requires.
 */
class RichDatasetBean implements Serializable {
	
	protected ILazyDataset         data;
	protected List<IDataset>       axes;
	protected ILazyDataset         mask;
	protected IMetaData            meta;
	protected List<IROI>           rois;
	protected Map<Integer, String> slicing;

	RichDatasetBean(ILazyDataset data, List<IDataset> axes) {
        this(data, axes, null, null, null);
	}
	RichDatasetBean(ILazyDataset data, List<IDataset> axes, ILazyDataset mask, IMetaData meta) {
        this(data, axes, mask, meta, null);
	}
	RichDatasetBean(ILazyDataset data, List<IDataset> axes, ILazyDataset mask, IMetaData meta, List<IROI> rois) {
		this.data = data;
		this.axes = axes;
		this.mask = mask;
		this.meta = meta;
		this.rois = rois;
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
	public List<IROI> getRegions() {
		return rois;
	}
	public void setRegions(List<IROI> rois) {
		this.rois = rois;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axes == null) ? 0 : axes.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((rois == null) ? 0 : rois.hashCode());
		result = prime * result + ((slicing == null) ? 0 : slicing.hashCode());
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
		if (rois == null) {
			if (other.rois != null)
				return false;
		} else if (!rois.equals(other.rois))
			return false;
		if (slicing == null) {
			if (other.slicing != null)
				return false;
		} else if (!slicing.equals(other.slicing))
			return false;
		return true;
	}
	
	public List<IROI> getRois() {
		return rois;
	}
	public void setRois(List<IROI> rois) {
		this.rois = rois;
	}
	public Map<Integer, String> getSlicing() {
		return slicing;
	}
	public void setSlicing(Map<Integer, String> slicing) {
		this.slicing = slicing;
	}
    public void setSlicing(String... slices) {
    	if (slicing==null) slicing= new HashMap<Integer, String>(slices.length);
    	slicing.clear();
    	for (int i = 0; i < slices.length; i++) {
    		slicing.put(i, slices[i]);
		}
    }
}
