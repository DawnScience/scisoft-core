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

package uk.ac.diamond.scisoft.analysis.rpc.internal;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

/**
 * @see AnalysisRpcServerHandler
 */
public class AnalysisRpcServerHandlerImpl implements AnalysisRpcServerHandler {
	private final AnalysisRpcServer analysisRPC;

	public AnalysisRpcServerHandlerImpl(AnalysisRpcServer analysisRPC) {
		this.analysisRPC = analysisRPC;
	}

	@Override
	public Object handler(String destination, Object[] args) {
		IRootFlattener flattener = analysisRPC.getFlattener();
		try {
			IAnalysisRpcHandler handler = analysisRPC.getDestination(destination);
			if (handler == null) {
				throw new AnalysisRpcException("No handler registered for " + destination);
			}
			Object[] unflattened = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				unflattened[i] = flattener.unflatten(args[i]);
			}
			Object ret = handler.run(unflattened);
			Object flatret = flattener.flatten(ret);
			return flatret;
		} catch (Exception e) {
			return flattener.flatten(e);
		}
	}
}
