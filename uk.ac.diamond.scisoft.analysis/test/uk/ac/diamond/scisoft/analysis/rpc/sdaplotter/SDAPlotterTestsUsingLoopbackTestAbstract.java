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

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;
import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcGenericInstanceDispatcher;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

public class SDAPlotterTestsUsingLoopbackTestAbstract {
	protected static PythonRunInfo pythonRunInfo;
	private static IAnalysisRpcHandler savedHandler;
	protected static ReDirectOverRpcPlotterImpl redirectPlotter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		redirectPlotter = new ReDirectOverRpcPlotterImpl();

		// Launch the AnalysisRpc server that receives our requests and sends them back to us
		pythonRunInfo = PythonHelper
				.runPythonFileBackground("../uk.ac.diamond.scisoft.python/test/scisoftpy/loopback.py");

		savedHandler = AnalysisRpcServerProvider.getInstance().getHandler(SDAPlotter.class.getSimpleName());
	}

	private void checkPythonState() {
		// Before and after each test make sure the server is still there
		if (pythonRunInfo != null && pythonRunInfo.hasTerminated()) {
			// It has disappeared, so dump the stdout and stderr
			pythonRunInfo.getStdout(true);
			throw new RuntimeException("Python script unexpectedly terminated");
		}
	}

	@Before
	public void checkBefore() {
		checkPythonState();
	}

	@After
	public void checkAfter() {
		checkPythonState();
	}

	@AfterClass
	public static void tearDownAfterClass() {

		// Stop the server making sure no unexpected output is there
		if (pythonRunInfo != null) {
			pythonRunInfo.terminate();
			pythonRunInfo.getStdout(true);
		}
		pythonRunInfo = null;

		// Restore normal handler
		AnalysisRpcServerProvider.getInstance().addHandler(SDAPlotter.class.getSimpleName(), savedHandler);
	}

	protected void registerHandler(ISDAPlotter handler) {
		IAnalysisRpcHandler dispatcher = new AnalysisRpcGenericInstanceDispatcher(ISDAPlotter.class, handler);
		AnalysisRpcServerProvider.getInstance().addHandler(SDAPlotter.class.getSimpleName(), dispatcher);
	}



}
