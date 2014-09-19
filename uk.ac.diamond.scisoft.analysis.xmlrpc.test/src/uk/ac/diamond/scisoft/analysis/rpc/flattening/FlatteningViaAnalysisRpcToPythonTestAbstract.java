/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

abstract public class FlatteningViaAnalysisRpcToPythonTestAbstract extends FlatteningTestAbstract {
	private static PythonRunInfo pythonRunInfo;
	protected static AnalysisRpcClient client;

	@BeforeClass
	public static void start() throws Exception {
		pythonRunInfo = PythonHelper
				.runPythonFileBackground("src/uk/ac/diamond/scisoft/analysis/rpc/flattening/loopbackanalysisrpc.py");

		Thread.sleep(SERVER_WAIT_TIME); // wait for server to start

		client = new AnalysisRpcClient(8714);
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

}
