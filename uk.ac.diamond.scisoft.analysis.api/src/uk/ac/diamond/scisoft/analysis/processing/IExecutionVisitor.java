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

import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * This interface is designed to be called when a series of operations has been 
 * completed on a dataset which is being processed. It is a visitor pattern
 * which is notified of the processed data in the stack. The user then has the option
 * of keeping the full data in memory (if possible), writing to file or plotting and
 * then letting the data go out of scope.
 */
public interface IExecutionVisitor {
	
    /**
     * Called when an execution in the pipeline has run, before the end	but after a given operation.
     * Provides the option of saving the steps information to a file if required.
     * 
     * @param intermediateData
     * @param data
     * @param dataDims TODO
     */
	public void notify(IOperation intermediateData, OperationData data, Slice[] slices, int[] shape, int[] dataDims);
	
	/**
	 * Called when the series of operations has been done, with the 
	 * @param result
	 * @param dataDims TODO
	 */
	public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception;
	
	/**
	 * List of operations for which should not modify the data passing though the pipe
	 * @param operations
	 */
	public void passDataThroughUnmodified(IOperation... operations);
	
	/**
	 * check if operation should modify the data passing though the pipe
	 * @param operation
	 */
	public boolean isRequiredToModifyData(IOperation operation);
	
	
	public class Stub implements IExecutionVisitor {

		@Override
		public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notify(IOperation intermeadiateData, OperationData data, Slice[] slices, int[] shape, int[] dataDims) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void passDataThroughUnmodified(IOperation... operations) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isRequiredToModifyData(IOperation operation) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}



}
