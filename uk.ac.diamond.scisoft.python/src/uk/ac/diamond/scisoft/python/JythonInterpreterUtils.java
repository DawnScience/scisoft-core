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
import java.util.ArrayList;
import java.util.List;

import org.dawb.common.util.eclipse.BundleUtils;
import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.jython.util.JythonPath;

/**
 * SCISOFT - added static method which returns a PythonInterpreter which can run scisoft scripts
 * This is for executing a script directly from the workflow tool when you do not want to
 * start a separate debug/run process to start the script.
 */
public class JythonInterpreterUtils {
	
	 // Boolean to set to true if running jython scripts that utilise ScisoftPy in IDE
	public static final String RUN_IN_ECLIPSE = "run.in.eclipse";

	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";
	private static final String JYTHON_BUNDLE = "uk.ac.diamond.jython";
	private static final String JYTHON_BUNDLE_LOC = JYTHON_BUNDLE + ".location";
	
	private static Logger logger = LoggerFactory.getLogger(JythonInterpreterUtils.class);
	
	static {
		PySystemState.initialize();
	}

	
	
	/**
	 * Create Jython interpreter
	 * 
	 * @return a new PythonInterpreter.
	// * @throws IOException 
	// * @throws ClassNotFoundException 
	 */
	public static PythonInterpreter getBasicInterpreter() throws Exception {
		return getBasicInterpreter(null);
	}
	
	/**
	 * 
	 * @param extraPaths
	 * @return a new Jython Interpreter
	 * @throws Exception from getJythonInterpreterDirectory in case of missing JYTHON_BUNDLE_LOC when no Jython bundle found
	 */
	public static PythonInterpreter getBasicInterpreter(List<File> extraPaths) throws Exception {
		final long start = System.currentTimeMillis();
		
		//This was the major part of the getInterpreter method.
		//Idea is to separate interpreter creation and starting of scisoftpy
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
		File jyRoot = JythonPath.getInterpreterDirectory();
		File jyBundleLoc = jyRoot.getParentFile();
		logger.debug("Classpath:");
		for (String p : System.getProperty("java.class.path").split(File.pathSeparator)) {
			logger.debug("\t{}", p);
		}

		PyList path = state.path;
//		path.clear();
//		File jyRoot = new File(jyBundleLoc, "jython2.5");
		path.append(new PyString(new File(jyRoot, "jython.jar").getAbsolutePath()));
		File jyLib = new File(jyRoot, "Lib");
		path.append(new PyString(jyLib.getAbsolutePath()));
		path.append(new PyString(new File(jyLib, "distutils").getAbsolutePath()));
		File site = new File(jyLib, "site-packages");
		path.append(new PyString(site.getAbsolutePath())); // TODO? iterate over sub-directories
		
		//Add additional paths to sys.path in new interpreter
		if (extraPaths != null){
			for (File f : extraPaths){
				path.append(new PyString(f.getAbsolutePath()));
			}
		}
		
		PythonInterpreter interpreter = new PythonInterpreter(new PyStringMap(), state);
		
		final long end = System.currentTimeMillis();
		logger.debug("Created new Jython Interpreter in {}ms.", end-start);
		
		return interpreter;
	}
	
	/**
	 * scisoftpy is imported as dnp
	 * @return a new Jython Interpreter, with only scisoftpy in sys.path
	 * @throws Exception from getJythonInterpreterDirectory in case of missing JYTHON_BUNDLE_LOC when no Jython bundle found
	 */
	public static PythonInterpreter getscisoftpyInterpreter() throws Exception{
		final List<File> extraPaths = new ArrayList<File>();
		
		try {
			//This seems to work in git repo case (and presumably in binary)
			//Old code in 0f667dd and before (now deleted) was more verbose
			File pythonPlugin = BundleUtils.getBundleLocation(SCISOFTPY);
			logger.debug("Found Scisoft Python plugin at {}", pythonPlugin);
			File bin = new File(pythonPlugin, "bin");
			if (bin.exists()) {
				logger.debug("Found bin directory at {}", bin);
				extraPaths.add(bin);
			} else {
				extraPaths.add(pythonPlugin);
			}
		} catch (Exception e) {
			logger.error("Could not find Scisoft Python plugin", e);
		}
		
		PythonInterpreter interpreter = getBasicInterpreter(extraPaths);
		
		interpreter.exec("import sys");
		interpreter.exec("for p in sys.path: print '\t%s' % p");
		interpreter.exec("import scisoftpy as dnp");
		
		return interpreter;
	}
	
	public static PythonInterpreter getFullInterpreter() throws Exception {
		//Store for the additional things to go to sys.path
		final List<File> extraPaths = new ArrayList<File>();
		
		//Where we are searching for additional jars/plugins (affected by whether running in eclipse)
		boolean isRunningInEclipse = "true".equalsIgnoreCase(System.getProperty(RUN_IN_ECLIPSE));
		File pluginsDir = JythonPath.getPluginsDirectory(isRunningInEclipse); 
		
		//Find third party jars first
		logger.debug("Searching for 3rd party jars");
		extraPaths.addAll(JythonPath.findJars(pluginsDir));
		
		//Find all
		final List<File> allPluginDirs = JythonPath.findDirs(pluginsDir, isRunningInEclipse);
		if (isRunningInEclipse) {
			File wsDir = pluginsDir;
			if (!new File(wsDir, "tp").isDirectory()) {
				
			}
		}
		
		//If we've got everything in the extraPaths list, send it to the interpreter maker
		PythonInterpreter interpreter = getBasicInterpreter(extraPaths);
		return interpreter;
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
		
		PythonInterpreter interpreter = getscisoftpyInterpreter();
		
		final long end = System.currentTimeMillis();
		
		logger.debug("Created new Jython Interpreter in {}ms.", end-start);
	
		return interpreter;
	}

}
