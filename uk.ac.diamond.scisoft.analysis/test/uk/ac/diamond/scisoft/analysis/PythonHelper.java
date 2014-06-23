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

/**
 * To run tests that require Python, the execution environment must have a Python with NumPy installed.
 */
public class PythonHelper {
	/**
	 * Set to true to enable python tests
	 */
	public static final String PYTHON_TEST = "org.dawnsci.python.test";

	/**
	 * Path to Python executable (by default, the python in the current PATH is used)
	 */
	public static final String PYTHON_EXECUTABLE = "org.dawnsci.python.executable";

	/**
	 * Content of PYTHONPATH environment variable (by default, it is left to inherit the current environment)
	 */
	public static final String PYTHON_PATH = "org.dawnsci.python.path";

	/**
	 * This is by default false but may be overridden by the {@link #PYTHON_TEST} property
	 */
	public static boolean enablePythonTests() {
		return "true".equalsIgnoreCase(System.getProperty(PYTHON_TEST, "false"));
	}

	private static String PYTHON_EXE = null;
	private static String[] PYTHON_ENV = null;

	private static void getDefaultPythonProperties() {
		PYTHON_EXE = System.getProperty(PYTHON_EXECUTABLE, "python");
		String path = System.getProperty(PYTHON_PATH);
		if (path != null) {
			PYTHON_ENV = new String[] {"PYTHONPATH=" + path};
		}
	}

	static {
		getDefaultPythonProperties();
	}

	/*
	 * We are re-implementing a call to python here, we really want to reuse PythonTest#exec or
	 * SimplePythonRunner#runAndGetOutput from PyDev. Any output on stderr means an error has occurred.
	 */
	public static String runPythonScript(String scriptContents, boolean failOnAnyOutput) throws Exception {
		return runPythonScript(scriptContents, PYTHON_ENV, failOnAnyOutput);
	}
	
	private static String runPythonScript(String scriptContents, String[] envp, boolean failOnAnyOutput) throws Exception {
		return readAndProcessOutput(failOnAnyOutput, new String[] { PYTHON_EXE, "-c", scriptContents }, envp);
	}

//	public static String runPythonFile(String file, boolean failOnAnyOutput) throws Exception {
//		return runPythonFile(file, null, failOnAnyOutput);
//	}
//	
//	public static String runPythonFile(String file, String[] args, boolean failOnAnyOutput) throws Exception {
//		return runPythonFile(file, args, PYTHON_ENV, failOnAnyOutput);
//	}
//	
	public static String runPythonFile(String file, String[] args, String[] envp, boolean failOnAnyOutput) throws Exception {
		if (args == null)
			args = new String[0];
		String[] allArgs = new String[2 + args.length];
		allArgs[0] = PYTHON_EXE;
		allArgs[1] = file;
		for (int i = 0; i < args.length; i++) {
			allArgs[i + 2] = args[i];
		}
		return readAndProcessOutput(failOnAnyOutput, allArgs, envp);
	}
	
//	public static PythonRunInfo runPythonScriptBackground(String scriptContents) throws Exception {
//		return launch(new String[] { PYTHON_EXE, "-c", scriptContents }, null);
//	}

	public static PythonRunInfo runPythonFileBackground(String file) throws Exception {
		return runPythonFileBackground(file, null);
	}
	
	private static PythonRunInfo runPythonFileBackground(String file, String[] args) throws Exception {
		return runPythonFileBackground(file, args, PYTHON_ENV);
	}
	
	public static PythonRunInfo runPythonFileBackground(String file, String[] args, String[] envp) throws Exception {
		if (args == null)
			args = new String[0];
		String[] allArgs = new String[2 + args.length];
		allArgs[0] = PYTHON_EXE;
		allArgs[1] = file;
		int i = 2;
		for (String s : args) {
			allArgs[i++] = s;
		}
		if (PYTHON_ENV != null) {
			String[] allEnvs = new String[PYTHON_ENV.length + envp.length];
			i = 0;
			for (String s : PYTHON_ENV) {
				allEnvs[i++] = s;
			}
			for (String s : envp) {
				allEnvs[i++] = s;
			}
			return launch(allArgs, allEnvs);
		}
		return launch(allArgs, envp);
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

	private static String readAndProcessOutput(boolean failOnAnyOutput, String[] args, String[] envp) throws Exception {
		PythonRunInfo pythonRunInfo = launch(args, envp);
		return pythonRunInfo.getStdout(failOnAnyOutput);
	}

	private static PythonRunInfo launch(String[] args, String[] envp) throws IOException {
		// Short circuit tests when python isn't available
		Assume.assumeTrue(enablePythonTests());

		PythonRunInfo pythonRunInfo = new PythonRunInfo();
		pythonRunInfo.process = Runtime.getRuntime().exec(args, envp);

		// No need to synchronize as we'll waitFor() the process before getting the contents.
		pythonRunInfo.std = new ThreadStreamReader(pythonRunInfo.process.getInputStream(), false);
		pythonRunInfo.err = new ThreadStreamReader(pythonRunInfo.process.getErrorStream(), false);

		pythonRunInfo.std.start();
		pythonRunInfo.err.start();
		try {
			// Allow the python process to start before continuing, this is required when starting a server in the Python side.
			// The sleep was originally 1000, but this was too low, resulting in intermittent failures during Jenkins testing (see SCI-1893).
			Thread.sleep(1500);
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
