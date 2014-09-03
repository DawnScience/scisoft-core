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

import java.util.Collection;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

/**
 * This is a service for creating and returning operations.
 * 
 * The service is provided by another plugin and returned using OSGI.
 */
public interface IOperationService {
	
	/**
	 * Get the name of the operation with this id
	 * @param id
	 * @return name
	 */
	public String getName(String id) throws Exception;
	
	/**
	 * Get the description of the operation with this id
	 * @param id
	 * @return description
	 */
	public String getDescription(String id) throws Exception;

	/**
	 * Finds all operations by doing a search
	 * using the regex passed in. All operations whose descriptions/ids
	 * match the regex are returned. For instance:
	 * 
	 * find("correction");  // Might give operations "Dark correction", "Flat correction"
	 * find("integration"); // Might give operations "Azimuthal integration", "Radial integration", "Box integration", "Line integration"
	 * 
	 * NOTE the regex will be matched as follows on the id of the operation:
	 * 1. if matching on the id
	 * 2. if matching the description in lower case.
	 * 3. if indexOf the regex in the id is >0
	 * 4. if indexOf the regex in the description is >0
	 * 
	 * @param operationRegex
	 * @return list of operations which match
	 */
	public Collection<IOperation> find(String operationRegex)  throws Exception;
	
	/**
	 * Finds all operations with either the input rank or output rank (when isInput=false)
	 * is passed in.
	 * 
	 * If rank of ANY is used then only those matching exactly ANY are returned, not all operations.
	 * If rank of SAME is used then only those matching exactly SAME are returned, not all operations.
	 * 
	 * @param rank
	 * @param isInput - true to search inputs, false to search outputs.
	 * @return list of operations which match
	 */
	public Collection<IOperation> find(OperationRank rank, boolean isInput)  throws Exception;

	/**
	 * Finds the first operation matcing a search
	 * using the regex passed in. All operations whose descriptions/ids
	 * match the regex are returned. For instance:
	 * 
	 * find("correction");  // Might give operations "Dark correction", "Flat correction"
	 * find("integration"); // Might give operations "Azimuthal integration", "Radial integration", "Box integration", "Line integration"
	 * 
	 * NOTE the regex will be matched as follows on the id of the operation:
	 * 1. if matching on the id
	 * 2. if matching the description in lower case.
	 * 3. if indexOf the regex in the id is >0
	 * 4. if indexOf the regex in the description is >0
	 * 
	 * @param operationRegex
	 * @return list of operations which match
	 */
	public IOperation findFirst(String operationRegex)  throws Exception;
	
	/**
	 * Gets the ids of all the operations. The id is defined in the extension point.
	 * @return all the ids which have been contributed via extensions
	 */
	public Collection<String> getRegisteredOperations()  throws Exception;

	/**
	 * Creates an operation by using its type. This method will create a new
	 * operation using the no argument constructor.
	 * 
	 * @return IOperation
	 */
	public IOperation create(String operationId) throws Exception;
	
	
	/**
	 * Checks the extension point for the model class pertinent to this operation.
	 * 
	 * @return IOperation
	 */
	public Class<? extends IOperationModel> getModelClass(String operationId) throws Exception;

	/**
	 * Executes a chain of operations in series. NOTE the fist operation must have
	 * its data set and other operations should have their parameters set before
	 * execution.
	 * 
	 * @param dataset
	 * @param visitor - notified of the result of each slice result after processing
	 * @param series
	 * @throws OperationException
	 */
	public void executeSeries(IRichDataset dataset, IMonitor monitor, IExecutionVisitor visitor, IOperation... series) throws OperationException;
	

	/**
	 * Executes a chain of operations in series the same as executeSeries however the
	 * data slices are parallel and will not be sliced necessarily in order. To do this
	 * a ForkJoinPool is used the size of the available local CPUs.
	 * 
	 * NOTE the fist operation must have
	 * its data set and other operations should have their parameters set before
	 * execution.
	 * 
	 * @param dataset
	 * @param visitor - notified of the result of each slice result after processing
	 * @param series
	 * @throws OperationException
	 */
	public void executeParallelSeries(IRichDataset dataset, IMonitor monitor, IExecutionVisitor visitor, IOperation... series) throws OperationException;

	/**
	 * Method to validate a pipeline, throwing an exception if the pipeline is not valid.
	 * 
	 * @param firstSlice
	 * @param series
	 * @throws InvalidRankException
	 */
	public void validate(IDataset firstSlice, IOperation... series) throws InvalidRankException, OperationException;

	/**
	 * Optionally configure the parallel timeout of this service.
	 * 
	 * @param timeoutMs
	 */
	public void setParallelTimeout(long timeoutMs);

	/**
	 * Runs a set of operations by following a graph chaining the operations together.
	 * This run uses a recursive method and 
	 * @param root
	 * @return IRichDataset
	 */
	// If we start to need things like this:
	//public IRichDataset executeGraph(IOperationNode root) throws OperationException;
	// Then this service has inadvertently become a workflow tool and we are reinventing ptolemy
	
	
	/**
	 * Call this method to load some operations not by extension point but by parsing the classes
	 * in a package which implement IOperation. This is useful if your code for the operations is
	 * not eclipse or you are writing a unit test.
	 * 
	 * @param l classloader where pacakage can be found.
	 * @param pakage separated by dots.
	 */
	public void createOperations(ClassLoader l, String pakage)  throws Exception;

	
}
