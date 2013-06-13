/*-
 * Copyright 2013 Diamond Light Source Ltd.
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
 * This interface will be used with the plotting system to mark
 * a given set of data to plot as having error. It needs to be adapted
 * to support non-symmetric error bounds. 
 * 
 * The plotting system will attempt to plot any data which is also IErrorDataset
 * including error bars unless setErrorBarEnabled(false) is explicitly called. This
 * is a detail of the plotting system not the analysis algorithms.
 */
public interface IErrorDataset extends IDataset {

	/**
	 * 
	 * @return the error dataset
	 */
	public IDataset getError();
	
	/**
	 * Get the error for a given position.
	 * @param pos
	 * @return error value (symmetric)
	 */
	public double getError(int... pos);
	
	/**
	 * Set the error, may be a single double or a whole dataset.
	 * @param error
	 */
	public void setError(Serializable error);

}
