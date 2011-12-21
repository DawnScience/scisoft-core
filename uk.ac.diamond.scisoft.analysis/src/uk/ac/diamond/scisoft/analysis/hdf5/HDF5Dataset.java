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

package uk.ac.diamond.scisoft.analysis.hdf5;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.StringDataset;

/**
 * Leaf node to hold a (lazy) dataset or string
 */
public class HDF5Dataset extends HDF5Node {
	private boolean string = false;
	private boolean supported = false; // compound datasets are not currently supported

	private ILazyDataset dataset;
	private long[] maxShape;
	private String text;
	private String type;

	public HDF5Dataset(final long oid) {
		super(oid);
	}

	public ILazyDataset getDataset() {
		return dataset;
	}

	public void setDataset(final ILazyDataset lazyDataset) {
		dataset = lazyDataset;
		supported = true;
		string = (lazyDataset instanceof StringDataset);
	}

	public void setString(final String text) {
		this.text = text;
		string = true;
		supported = true;
	}

	public boolean isString() {
		return string;
	}

	public boolean isSupported() {
		return supported;
	}

	public String getString() {
		if (!string)
			return null;
		if (text != null)
			return text;
		StringDataset a = (StringDataset) dataset;
		StringBuilder out = new StringBuilder();
		IndexIterator it = a.getIterator();
		while (it.hasNext()) {
			out.append(a.getAbs(it.index));
			out.append('\n');
		}
		int end = out.length();
		out.delete(end-1, end);
		return out.toString();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(super.toString());

		out.append(INDENT);
		if (string) {
			out.append(getString());
		} else if (supported) {
			out.append(dataset.toString());
		} else {
			out.append("unsupported");
		}
		return out.toString();
	}

	public void setTypeName(String name) {
		type = name;
	}

	public String getTypeName() {
		return type;
	}

	public void setMaxShape(long[] maxShape) {
		this.maxShape = maxShape;
	}

	public long[] getMaxShape() {
		return maxShape;
	}

}
