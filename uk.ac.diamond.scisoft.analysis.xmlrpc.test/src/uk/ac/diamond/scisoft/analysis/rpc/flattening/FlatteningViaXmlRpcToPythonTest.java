/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import org.apache.xmlrpc.XmlRpcException;

public class FlatteningViaXmlRpcToPythonTest extends FlatteningViaXmlRpcToPythonTestAbstract {

	@Override
	protected Object doAdditionalWorkOnFlattendForm(Object flat) {
		checkPythonState();
		try {
			return client.execute("loopback", new Object[] { new Object[] { flat } });
		} catch (XmlRpcException e) {
			throw new RuntimeException(e);
		}
	}


}
