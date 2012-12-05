/*
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.python;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.dawb.common.util.eclipse.BundleUtils;
import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SCISOFT - added static method which returns a PythonInterpreter which can run scisoft scripts
This is for executing a script directly from the workflow tool when you do not want to
start a separate debug/run process to start the script.
 */
public class JythonInterpreterUtils {

	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";
	private static final String JYTHON_BUNDLE = "uk.ac.diamond.scisoft.python";
	private static Logger logger = LoggerFactory.getLogger(JythonInterpreterUtils.class);
	
	static {
		PySystemState.initialize();
	}
	
	/**
	 * scisoftpy is imported as dnp
	 * scisoftpy.core as scp
	 * 
	 * @return a new PythonInterpreter with scisoft scripts loaded.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static PythonInterpreter getInterpreter() throws Exception {
		
		final long start = System.currentTimeMillis();
		// FIXME switch back logging to debug
		logger.info("Starting new Jython Interpreter.");
		PySystemState     state       = new PySystemState();
		
		final ClassLoader classLoader = uk.ac.diamond.scisoft.python.PythonUtils.class.getClassLoader();
		state.setClassLoader(classLoader);
		System.err.println("Class loader is " + classLoader);
		if (classLoader instanceof URLClassLoader) {
			logger.info("URL classpath:");
			for (URL u : ((URLClassLoader) classLoader).getURLs()) {
				logger.info("\t{}", u.getPath());
			}
		}
		File jyBundleLoc;
		try {
			jyBundleLoc = BundleUtils.getBundleLocation(JYTHON_BUNDLE);
			logger.info("Jython bundle found: {}", jyBundleLoc.getAbsolutePath());
		} catch (Exception ignored) {
			jyBundleLoc = null;
		}
		if (jyBundleLoc == null) {
			if (System.getProperty("uk.ac.diamond.jython.location")==null) throw new Exception("Please set the property 'uk.ac.diamond.jython.location' for this test to work!");
			jyBundleLoc = new File(System.getProperty("uk.ac.diamond.jython.location"));
		}
		logger.info("Classpath:");
		for (String p : System.getProperty("java.class.path").split(":")) {
			logger.info("\t{}", p);
		}

		PyList path = state.path;
//		path.clear();
		path.append(new PyString(new File(jyBundleLoc, "jython.jar").getAbsolutePath()));
		File jyLib = new File(new File(jyBundleLoc, "jython2.5"), "Lib");
		path.append(new PyString(jyLib.getAbsolutePath()));
		path.append(new PyString(new File(jyLib, "distutils").getAbsolutePath()));
		File site = new File(jyLib, "site-packages");
		path.append(new PyString(site.getAbsolutePath())); // TODO? iterate over sub-directories

		try {
			File pythonPlugin = BundleUtils.getBundleLocation(SCISOFTPY);
			if (!pythonPlugin.exists()) {
				logger.info("No scisoftpy found at {} - now trying to find git workspace", pythonPlugin);
				File gitws = jyBundleLoc.getParentFile().getParentFile();
				if (gitws.exists()) {
					logger.info("Git workspace found: {}", gitws.getAbsolutePath());
					pythonPlugin = new File(new File(gitws, "scisoft-core.git"), SCISOFTPY);
					if (!pythonPlugin.exists()) {
						throw new IllegalStateException("Can't find scisoftpy at " + pythonPlugin);
					}
				} else {
					throw new IllegalStateException("No git workspace at " + gitws);
				}
			}
			logger.info("Found Scisoft Python plugin at {}", pythonPlugin);
			File bin = new File(pythonPlugin, "bin");
			if (bin.exists()) {
				logger.info("Found bin directory at {}", bin);
				path.append(new PyString(bin.getAbsolutePath()));
			} else {
				path.append(new PyString(pythonPlugin.getAbsolutePath()));
			}
		} catch (Exception e) {
			logger.error("Could not find Scisoft Python plugin", e);
		}
		
		PythonInterpreter interpreter = new PythonInterpreter(new PyStringMap(), state);
		interpreter.exec("import sys");
		interpreter.exec("for p in sys.path: print '\t%s' % p");
		interpreter.exec("import scisoftpy as dnp");
		
		final long end = System.currentTimeMillis();
		
		logger.info("Created new Jython Interpreter in {}ms.", end-start);
	
		return interpreter;
	}

}
