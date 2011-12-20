/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
