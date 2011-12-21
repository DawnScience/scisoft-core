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
				.runPythonFileBackground("test/uk/ac/diamond/scisoft/analysis/rpc/flattening/loopbackanalysisrpc.py");

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
