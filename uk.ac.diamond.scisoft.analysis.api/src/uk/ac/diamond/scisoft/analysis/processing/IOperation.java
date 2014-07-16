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

/**
 * Interface to encapsulate processing operations on an IRichDataset
 * 
 * For instance background correction, azimuthal integration.
 */
public interface IOperation {

	/**
	 * Provide the data required for this operation, generally one IRichDataset.
	 * 
	 * You may provide more than one IRichDataset for some operations but an exception
	 * can be thrown if the operation does not support the data passed into it.
	 * 
	 * An operation supports multiple setData and execute calls. Therefore the operation
	 * data can change, after setData(..) is called. If implementing an operation the programmer
	 * should be aware that data should not be cached, or at the least cleared if setData(...)
	 * is called.
	 * 
	 * @param data
	 * @throws IllegalArgumentException if the operation does not support this count or type of data.
	 */
	public void setData(IRichDataset... data) throws IllegalArgumentException;
		
	/**
	 * The execute method operates on the data set last send via setData() and the
	 * information specific to this operation provided and 
	 * returns the processed data in the form of an IRichDataset
	 * 
	 * Multiple setData(..) and execute methods may be called. In addition execute()
	 * may be called multiple times for operations such as differential. Each time
	 * operating on the result of the last execution.
	 * 
	 * @return dataset which is the result of this operation.
	 */
	public IRichDataset execute() throws OperationException;
	
	
	/**
	 * The list of parameters are specific to the operation we are doing. NOTE parameters should not
	 * be data contained in IRichDataset, like Datasets, Metadata and Regions. Parameter examples are:
	 * double values for tolerances, xml configurations, JSON strings etc.
	 * 
	 * @param parameters
	 * @throws IllegalArgumentException if the parameters are not those required by the operation.
	 */
	public void setParameters(Serializable... parameters) throws IllegalArgumentException;
}
