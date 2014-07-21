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

/**
 * This interface is designed to be called when a series of operations has been 
 * completed on a dataset which is being processed. It is a visitor pattern
 * which is notified of the processed data in the stack. The user then has the option
 * of keeping the full data in memory (if possible), writing to file or plotting and
 * then letting the data go out of scope.
 */
public interface IExecutionVisitor {

	/**
	 * Called before the execution happens
	 * @param slice
	 * @param operations
	 * @return false to ignore this slice
	 */
	public boolean isRequired(IDataset slice, IOperation... operations);
	
	/**
	 * Called when the series of operations has been done
	 * @param result
	 */
	public void executed(OperationData result) throws Exception;
	
	public class Stub implements IExecutionVisitor {

		@Override
		public boolean isRequired(IDataset slice, IOperation... operations) {
			return true; // TODO Auto-generated method stub
			
		}

		@Override
		public void executed(OperationData result) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
}
