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

package uk.ac.diamond.scisoft.analysis;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Assume;

public class PythonHelper {

	private static final String PYTHON = "python";

	/**
	 * Change this to return false to skip (aka {@link Assume} failure) all tests that require launching python
	 */
	public static boolean enablePythonTests() {
		return false;
	}


	/*
	 * We are re-implementing a call to python here, we really want to reuse PythonTest#exec or
	 * SimplePythonRunner#runAndGetOutput from PyDev. Any output on stderr means an error has occurred.
	 */
	public static String runPythonScript(String scriptContents, boolean failOnAnyOutput) throws Exception {
		return readAndProcessOutput(failOnAnyOutput, new String[] { PYTHON, "-c", scriptContents });
	}

	public static String runPythonFile(String file, boolean failOnAnyOutput) throws Exception {
		return readAndProcessOutput(failOnAnyOutput, new String[] { PYTHON, file });
	}
	
	public static PythonRunInfo runPythonScriptBackground(String scriptContents) throws Exception {
		return launch(new String[] { PYTHON, "-c", scriptContents });
	}

	public static PythonRunInfo runPythonFileBackground(String file) throws Exception {
		return launch(new String[] { PYTHON, file });
	}

	public static class PythonRunInfo {
		ThreadStreamReader std, err;
		Process process;

		public void terminate() {
			process.destroy();
		}

		public String getStdout(boolean failOnAnyOutput) {
			try {
				// wait until the process completion.
				process.waitFor();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			String stderr = err.getContents();
			String stdout = std.getContents();
			boolean runSuccess = (!failOnAnyOutput || "".equals(stdout)) && "".equals(stderr);
			Assert.assertTrue("Unexpected output on stderr:\n" + stderr + "\nor stdout:\n" + stdout + "\n", runSuccess);
			return stdout;
		}

		public boolean hasTerminated() {
			try {
				process.exitValue();
				return true;
			} catch (IllegalThreadStateException e) {
				return false;
			}
		}
	}

	private static String readAndProcessOutput(boolean failOnAnyOutput, String[] args) throws Exception {
		PythonRunInfo pythonRunInfo = launch(args);
		return pythonRunInfo.getStdout(failOnAnyOutput);
	}

	private static PythonRunInfo launch(String[] args) throws IOException {
		// Short circuit tests when python isn't available
		Assume.assumeTrue(enablePythonTests());

		PythonRunInfo pythonRunInfo = new PythonRunInfo();
		pythonRunInfo.process = Runtime.getRuntime().exec(args);

		// No need to synchronize as we'll waitFor() the process before getting the contents.
		pythonRunInfo.std = new ThreadStreamReader(pythonRunInfo.process.getInputStream(), false);
		pythonRunInfo.err = new ThreadStreamReader(pythonRunInfo.process.getErrorStream(), false);

		pythonRunInfo.std.start();
		pythonRunInfo.err.start();
		try {
			// Allow the python process to start before continuing, this is required
			// when starting a server in the Python side.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return pythonRunInfo;
	}

	/**
	 * Parse an array output by Python into a String[]
	 * <p>
	 * Splits on "', '" so that string cannot occur in input otherwise parse will fail to produce correct results
	 * 
	 * @param pythonStdout
	 *            output from Python
	 * @return array of strings, cannot be null, may be [0]
	 */
	public static String[] parseArray(String pythonStdout) {
		// simplistic parsing of print out of array
		String[] split = pythonStdout.split("', '");
		if (split.length > 0) {
			// take off open and closing square bracket
			split[0] = split[0].substring(1);
			int last = split.length - 1;
			split[last] = split[last].substring(0, split[last].length() - 1);
			if (last == 0 && split[last].length() == 0) {
				// nothing left, array was empty
				split = new String[0];
			} else {
				// take off open and closing quote
				split[0] = split[0].substring(1);
				split[last] = split[last].substring(0, split[last].length() - 1);
			}
		}
		return split;
	}



}
