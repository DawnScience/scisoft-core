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

package uk.ac.diamond.scisoft.analysis.rpc;


// AnalysisRpcSyncExecDispatcher isn't a link because it is not in a dependent bundle.
/**
 * Interface that must be implemented by a class that wants to register itself with
 * {@link AnalysisRpcServer#addHandler(String, IAnalysisRpcHandler)}
 * <p>
 * Consider using one of the provided implementations of {@link IAnalysisRpcHandler} which can delegate to a class:
 * <ul>
 * <li> {@link AnalysisRpcGenericInstanceDispatcher} for dispatching to all instance methods in a class</li>
 * <li> {@link AnalysisRpcGenericStaticClassDispatcher} for dispatching to all static methods in a class</li>
 * <li>AnalysisRpcSyncExecDispatcher for dispatching to all instance methods in a class, but wrapping the call in a
 * SyncExec so that it runs in the main Eclipse UI thread</li>
 * </ul>
 */
public interface IAnalysisRpcHandler {

	/**
	 * Receive am RPC call issued by a client.
	 * <p>
	 * All arguments passed the server are "flattened" for transport, and automatically unflattened by the server before
	 * delivery to this method. The return value is similarly flattened and unflattened.
	 * <p>
	 * If this method throws an exception, it is re-thrown on the client wrapped in an AnalysisRpcException. This method
	 * should not return an Exception because it will be thrown on the client.
	 * <p>
	 * 
	 * @param args
	 *            arguments from the client
	 * @return value to send back to the client. This value must be flattenable otherwise the client will only receive
	 *         an AnalysisRpcException indicating that the return value was unflattenable.
	 * @throws AnalysisRpcException wrapped around any checked exceptions
	 */
	public Object run(Object[] args) throws AnalysisRpcException;

}
