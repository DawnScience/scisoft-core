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
import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.io.IMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.MetadataType;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

/**
 * A dataset which holds several bits of information to do with real data.
 */
public class RichDataset extends RichDatasetBean implements IRichDataset, ILazyDataset {

	
	public RichDataset(ILazyDataset data, List<IDataset> axes, ILazyDataset mask, IMetadata meta, List<IROI> rois) {
		super(data, axes, mask, meta, rois);
	}

	public RichDataset(ILazyDataset data, List<IDataset> axes, ILazyDataset mask, IMetadata meta) {
		super(data, axes, mask, meta);
	}

	public RichDataset(ILazyDataset data, List<IDataset> axes) {
		super(data, axes);
	}
	
	// TODO add methods operating on the rich data for instance:
	// 
	// o get values taking mask into consideration
	// o process mask over all data or return an ILaztDataset implementation which will mask on the fly
	// o get value from axis location (axis lookup followed by using those indices with real data)
	// etc.


	@Override
	public IMetadata getMetadata() throws Exception {
		return getMeta();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws Exception {
		if (IMetadata.class.isAssignableFrom(clazz)) {
			List<T> result = new ArrayList<T>();
			result.add((T) getMetadata());
			return result;
		}
		throw new UnsupportedOperationException("getMetadata(clazz) does not currently support anything other than IMetadata");
		// If it should only support this, simply return null here, otherwise implement the method fully
	}

	@Override
	public void addMetadata(MetadataType metadata) {
	}

	/**
	 * Clones the underlying data but not the mask and axes.
	 */
	@Override
	public RichDataset clone() {
		return new RichDataset(data.clone(), axes, mask, meta);
	}

	@Override
	public Class<?> elementClass() {
		return data.elementClass();
	}

	@Override
	public int getElementsPerItem() {
		return data.getElementsPerItem();
	}

	@Override
	public String getName() {
		return data.getName();
	}

	@Override
	public void setName(String name) {
		data.setName(name);
	}

	@Override
	public int getSize() {
   	    return data.getSize();
	}

	@Override
	public int[] getShape() {
		return data.getShape();
	}

	@Override
	public void setShape(int... shape) {
		data.setShape(shape);
		// TODO FIXME Is the mask really always the same size as the data as the design doc says?
		// It could be the size of n-1 where n is the dimensions of the data (or less?) 
		mask.setShape(shape);
	}

	@Override
	public int getRank() {
		return data.getRank();
	}

	@Override
	public ILazyDataset squeeze() {
		return data.squeeze();
	}

	@Override
	public ILazyDataset squeeze(boolean onlyFromEnd) {
		return data.squeeze(onlyFromEnd);
	}

	@Override
	public IDataset getSlice(int[] start, int[] stop, int[] step) {
		return data.getSlice(start, stop, step);
	}

	@Override
	public IDataset getSlice(IMonitor monitor, int[] start, int[] stop, int[] step) throws Exception {
		return data.getSlice(monitor, start, stop, step);
	}

	@Override
	public IDataset getSlice(Slice... slice) {
		return data.getSlice(slice);
	}

	@Override
	public IDataset getSlice(IMonitor monitor, Slice... slice) throws Exception {
		return data.getSlice(monitor, slice);
	}

	@Override
	public ILazyDataset getSliceView(int[] start, int[] stop, int[] step) {
		final ILazyDataset d = data.getSliceView(start, stop, step);
		final ILazyDataset m = mask!=null ? mask.getSliceView(start, stop, step) : null;
		// TODO Slice axes?
		return new RichDataset(d, axes, m, meta, rois);
	}

	@Override
	public ILazyDataset getSliceView(Slice... slice) {
		final ILazyDataset d = data.getSliceView(slice);
		final ILazyDataset m = mask!=null ? mask.getSliceView(slice) : null;
		// TODO Slice axes?
		return new RichDataset(d, axes, m, meta, rois);
	}

	@Override
	public void setMetadata(MetadataType metadata) {
		if (metadata instanceof IMetadata)
			meta = (IMetadata) metadata;
	}

	@Override
	public void setError(Serializable errors) {
		data.setError(errors);
	}

	@Override
	public ILazyDataset getError() {
		return data.getError();
	}
	
	@Override
	public <T extends MetadataType> void clearMetadata(Class<T> clazz) {
		data.clearMetadata(clazz);
	}
}
