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

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

/**
 * Interface to encapsulate processing operations on an IRichDataset
 * The idea of this class is that its data context, the data it is operating
 * on is passed in as an IRichDataset. Then each slice is operated on in the
 * execute method.
 * 
 * For instance background correction, azimuthal integration.
 * 
 * Your operation many be contributed via an extension point from any plugin. 
 * In this case the operation will be instantiated through a no argument constructor
 * so that the operation type can be retrieved. 
 * 
 * When you create an IOperation is must be marked with @Operation so that 
 * it can be read into the operation service.
 */
public interface IOperation {
	
	/**
	 * The operation name. The operation service allows operations to be 
	 * found by name which can be used to determine available operations.
	 * 
	 * This is usually set by the extension point, so there is no need to implement this
	 * if extending AbstractOperation
	 * 
	 * @return OperationType
	 */
	public String getName();

	/**
	 * The operation description. The operation service allows operations to be 
	 * found by name which can be used to determine available operations.
	 * 
	 * This is usually set by the extension point, so there is no need to implement this
	 * if extending AbstractOperation
	 * 
	 * @return OperationType
	 */
	public String getDescription();

	/**
	 * 
	 * @return The unique id for this operation.
	 */
	public String getId();

		
	/**
	 * The execute method operates on the data set last send via setDataset() and the
	 * information specific to this operation provided and 
	 * returns the processed data in the form of an IDataset
	 * 
	 * Multiple setData(..) and execute methods may be called. In addition execute()
	 * may be called multiple times for operations such as differential. Each time
	 * operating on the result of the last execution.
	 * 
	 * @return dataset which is the result of this operation.
	 */
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException;
	
	
	/**
	 * The list of parameters are specific to the operation we are doing. NOTE parameters should not
	 * be data contained in IRichDataset, like Datasets, Metadata and Regions. Parameter examples are:
	 * double values for tolerances, xml configurations, JSON strings etc.
	 * 
	 * @param parameters
	 * @throws IllegalArgumentException if the parameters are not those required by the operation.
	 */
	public void setModel(IOperationModel parameters) throws Exception;
	
	/**
	 * 
	 * @return the model which is to be used or null if no model has been set.
	 */
	public IOperationModel getModel();


	/**
	 * Gets the rank of input data for the operations algorithm. You might be iterating
	 * 4D data but when this operation is run on a slice of it, it must be done with a
	 * fixed input rank.
	 * 
	 * @return rank of slice data which we accept. For instance for integration we input 2(image) and output 1(integration)
	 */
	public OperationRank getInputRank();
	
	
	/**
	 * Gets the rank of output data for the operations algorithm. You might be iterating
	 * 4D data but when this operation is run on a slice of it, it must be done with a
	 * fixed input rank and this input will result in a fixed output rank.
	 * 
	 * @return rank of output data which we return. For instance for integration we input 2(image) and output 1(integration)
	 */
	public OperationRank getOutputRank();


}
