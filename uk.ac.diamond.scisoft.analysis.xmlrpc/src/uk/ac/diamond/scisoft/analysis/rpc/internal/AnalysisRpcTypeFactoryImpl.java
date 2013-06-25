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

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.TypeParser;
import org.apache.xmlrpc.serializer.DoubleSerializer;

/**
 * Custom {@link TypeFactoryImpl} that has our own type factory.
 * 
 * @see AnalysisRpcDoubleParser
 */
public class AnalysisRpcTypeFactoryImpl extends TypeFactoryImpl {

	public AnalysisRpcTypeFactoryImpl(XmlRpcController pController) {
		super(pController);
	}

	@Override
	public TypeParser getParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, String pURI,
			String pLocalName) {
		if (DoubleSerializer.DOUBLE_TAG.equals(pLocalName)) {
			return new AnalysisRpcDoubleParser();
		}
		return super.getParser(pConfig, pContext, pURI, pLocalName);
	}

}
