/*-
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

package uk.ac.diamond.scisoft.analysis.optimize;

import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * The interface for the optimizers
 */
public interface IOptimizer {

	/**
	 * The standard optimize function
	 * 
	 * @param coords
	 *            An array of datasets containing the coordinate positions
	 * @param data
	 *            A dataset containing the data to be fitted to
	 * @param function
	 *            A (possibly composite) function to fit
	 * @throws Exception 
	 */
	public void optimize(IDataset[] coords, IDataset data, IFunction function) throws Exception;
}
