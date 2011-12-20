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
