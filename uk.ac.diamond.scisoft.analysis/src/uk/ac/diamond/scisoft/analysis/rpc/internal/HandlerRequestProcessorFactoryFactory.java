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
