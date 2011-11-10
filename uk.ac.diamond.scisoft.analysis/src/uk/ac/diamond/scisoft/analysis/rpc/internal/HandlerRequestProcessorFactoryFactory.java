/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.internal;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;

/**
 * We have our own FactoryFactory so we can use an instantiated AnalysisRpcServerHandler with state. The alternative is
 * to do the default XML-RPC behaviour which creates a new instance of the given type to service each request.
 */
public class HandlerRequestProcessorFactoryFactory implements RequestProcessorFactoryFactory {
	private final RequestProcessorFactory factory = new HandlerRequestProcessorFactory();
	private final AnalysisRpcServerHandler handler;

	public HandlerRequestProcessorFactoryFactory(AnalysisRpcServerHandler handler) {
		this.handler = handler;
	}

	@Override
	public RequestProcessorFactory getRequestProcessorFactory(@SuppressWarnings("rawtypes") Class aClass)
			throws XmlRpcException {
		return factory;
	}

	private class HandlerRequestProcessorFactory implements RequestProcessorFactory {
		@Override
		public Object getRequestProcessor(XmlRpcRequest xmlRpcRequest) throws XmlRpcException {
			return handler;
		}
	}
}