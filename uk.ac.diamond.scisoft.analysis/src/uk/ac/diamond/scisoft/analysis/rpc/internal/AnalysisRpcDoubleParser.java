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
