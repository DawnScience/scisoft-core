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

import org.apache.xmlrpc.parser.DoubleParser;
import org.apache.xmlrpc.parser.TypeParser;
import org.xml.sax.SAXException;

/**
 * While we mostly handle custom data types through flattening, one particular issue is handled by using Apache
 * XML-RPC's builtin type system.
 * <p>
 * There is an imbalance in how NaN, -Inf and +Inf are handled between XML-RPC by apache and the one built-in to Python.
 * This class and the referenced handlers intercept the not normally conforming version and convert them to a type that
 * is supported by XML-RPC.
 * 
 * @see AnalysisRpcServerHandlerImpl
 * @see <a href="https://issues.apache.org/jira/browse/XMLRPC-146">Bug Report in XML-RPC Jira</a>
 */
public class AnalysisRpcDoubleParser extends DoubleParser implements TypeParser {
	@Override
	protected void setResult(String pResult) throws SAXException {
		// In Python nan, inf are always all lower case and java doesn't support that
		if ("nan".equals(pResult))
			super.setResult(Double.NaN);
		else if ("inf".equals(pResult))
			super.setResult(Double.POSITIVE_INFINITY);
		else if ("-inf".equals(pResult))
			super.setResult(Double.NEGATIVE_INFINITY);
		else
			super.setResult(pResult);
	}

}
