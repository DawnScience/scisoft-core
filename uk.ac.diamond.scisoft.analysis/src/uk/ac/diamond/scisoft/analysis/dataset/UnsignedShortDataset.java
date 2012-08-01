/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.io.Serializable;

/**
 * Like an IntegerDataset in terms of internal storage but
 * the getBuffer() method returns a byte array using only two
 * bytes for the int.
 */
public class UnsignedShortDataset extends ShortDataset {

	public UnsignedShortDataset() {
		super();
	}

	public UnsignedShortDataset(AbstractDataset dataset) {
		super(dataset);
	}

	public UnsignedShortDataset(int... shape) {
		super(shape);
	}

	public UnsignedShortDataset(short[] data, int... shape) {
		super(data, shape);
	}


	public UnsignedShortDataset(IntegerDataset dataset) {
		super(dataset);
	}

	
	@Override
	public ShortDataset getView() {
		UnsignedShortDataset view = new UnsignedShortDataset();
		view.name = new String(name);
		view.size = size;
		view.dataSize = dataSize;
		view.shape = shape.clone();
		if (dataShape != null)
			view.dataShape = dataShape.clone();
		view.odata = view.data = data;
		view.metadataStructure = metadataStructure;
		return view;
	}

}
