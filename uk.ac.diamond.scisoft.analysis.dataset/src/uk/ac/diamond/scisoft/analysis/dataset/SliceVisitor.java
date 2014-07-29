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

package uk.ac.diamond.scisoft.analysis.dataset;

/**
 * Used with 
 */
public interface SliceVisitor {

	/**
	 * Visit each slice
	 * @param data - the data sliced from the lazy data set
	 * @param selectedSlice the selected slice from the view of the original lazy dataset
	 * @param selectedShape the shaped of the view of the original lazy dataset
	 * @throws Exception
	 */
	public void visit(IDataset data, Slice[] selectedSlice, int[] selectedShape) throws Exception;
}
