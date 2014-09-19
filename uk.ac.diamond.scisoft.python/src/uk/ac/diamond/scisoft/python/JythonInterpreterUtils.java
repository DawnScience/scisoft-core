/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * This is for executing a script directly from the workflow tool when you do not want to
 * start a separate debug/run process to start the script.
 */
public class JythonInterpreterUtils {

	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";
	private static final String JYTHON_BUNDLE = "uk.ac.diamond.jython";
	private static final String JYTHON_BUNDLE_LOC = JYTHON_BUNDLE + ".location";
	private static Logger logger = LoggerFactory.getLogger(JythonInterpreterUtils.class);
	
	static {
		PySystemState.initialize();
	}
	
	/**
	 * scisoftpy is imported as dnp
	 * 
	 * @return a new PythonInterpreter with scisoft scripts loaded.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static PythonInterpreter getInterpreter() throws Exception {
		
		final long start = System.currentTimeMillis();
		logger.debug("Starting new Jython Interpreter.");
		PySystemState     state       = new PySystemState();
		
		final ClassLoader classLoader = JythonInterpreterUtils.class.getClassLoader();
		state.setClassLoader(classLoader);
		logger.info("Class loader is {}", classLoader);
		if (classLoader instanceof URLClassLoader) {
			logger.debug("URL classpath:");
			for (URL u : ((URLClassLoader) classLoader).getURLs()) {
				logger.debug("\t{}", u.getPath());
			}
		}
		File jyBundleLoc = null;
		try {
			jyBundleLoc = BundleUtils.getBundleLocation(JYTHON_BUNDLE);
		} catch (Exception ignored) {
		}
		if (jyBundleLoc == null) {
			if (System.getProperty(JYTHON_BUNDLE_LOC)==null)
				throw new Exception("Please set the property '" + JYTHON_BUNDLE_LOC + "' for this test to work!");
			jyBundleLoc = new File(System.getProperty(JYTHON_BUNDLE_LOC));
		}
		logger.info("Jython bundle found: {}", jyBundleLoc.getAbsolutePath());
		logger.debug("Classpath:");
		for (String p : System.getProperty("java.class.path").split(File.pathSeparator)) {
			logger.debug("\t{}", p);
		}

		PyList path = state.path;
//		path.clear();
		File jyRoot = new File(jyBundleLoc, "jython2.5");
		path.append(new PyString(new File(jyRoot, "jython.jar").getAbsolutePath()));
		File jyLib = new File(jyRoot, "Lib");
		path.append(new PyString(jyLib.getAbsolutePath()));
		path.append(new PyString(new File(jyLib, "distutils").getAbsolutePath()));
		File site = new File(jyLib, "site-packages");
		path.append(new PyString(site.getAbsolutePath())); // TODO? iterate over sub-directories

		try {
			File pythonPlugin = BundleUtils.getBundleLocation(SCISOFTPY);
			if (pythonPlugin == null || !pythonPlugin.exists()) {
				logger.debug("No scisoftpy found at {} - now trying to find git workspace", pythonPlugin);
				File gitws = jyBundleLoc.getParentFile().getParentFile();
				if (gitws.exists()) {
					logger.debug("Git workspace found: {}", gitws.getAbsolutePath());
					pythonPlugin = new File(new File(gitws, "scisoft-core.git"), SCISOFTPY);
					if (!pythonPlugin.exists()) {
						throw new IllegalStateException("Can't find scisoftpy at " + pythonPlugin);
					}
				} else {
					throw new IllegalStateException("No git workspace at " + gitws);
				}
			}
			logger.debug("Found Scisoft Python plugin at {}", pythonPlugin);
			File bin = new File(pythonPlugin, "bin");
			if (bin.exists()) {
				logger.debug("Found bin directory at {}", bin);
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
		
		logger.debug("Created new Jython Interpreter in {}ms.", end-start);
	
		return interpreter;
	}

}
