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

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcDoubleParser;

abstract public class FlatteningViaXmlRpcToPythonTestAbstract extends ExplicitFlatteningTestAbstract {
	private static PythonRunInfo pythonRunInfo;
	protected static XmlRpcClient client;

	@BeforeClass
	public static void start() throws Exception {
		pythonRunInfo = PythonHelper
				.runPythonFileBackground("test/uk/ac/diamond/scisoft/analysis/rpc/flattening/loopbackxmlrpc.py");

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://127.0.0.1:8713/xmlrpc"));
		client = new XmlRpcClient();
		client.setConfig(config);
	}

	@AfterClass
	public static void stop() {
		if (pythonRunInfo != null) {
			pythonRunInfo.terminate();
			pythonRunInfo.getStdout(true);
		}
		pythonRunInfo = null;
		client = null;
	}

	protected void checkPythonState() {
		if (pythonRunInfo != null && pythonRunInfo.hasTerminated()) {
			pythonRunInfo.getStdout(true);
			throw new RuntimeException("Python script unexpectedly terminated");
		}
	}

	/**
	 * This test fails because the loopback XML-RPC Client and Server don't have the type managers written to deal with
	 * the special values. The "special" XML-RPC client and server used by AnalysisRpc solves the issue, hence the test
	 * is enabled with that server is on.
	 * 
	 * @see AnalysisRpcDoubleParser
	 */
	@Override
	@Test
	public void testDoubleSpecialValues() {
		// test does not apply
	}
}
