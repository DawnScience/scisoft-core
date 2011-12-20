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
