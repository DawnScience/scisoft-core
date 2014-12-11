/*-
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.dawb.common.util.eclipse.BundleUtils;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.jython.JythonPath;

/**
 * SCISOFT - added static method which returns a PythonInterpreter which can run scisoft scripts
 * This is for executing a script directly from the workflow tool when you do not want to
 * start a separate debug/run process to start the script.
 */
public class JythonInterpreterUtils {

	// Boolean to set to true if running jython scripts that utilise ScisoftPy in IDE
	public static final String RUN_IN_ECLIPSE = "run.in.eclipse";

	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";
	
	private static Logger logger = LoggerFactory.getLogger(JythonInterpreterUtils.class);
	
	/**
	 * Create Jython interpreter
	 * 
	 * @return a new PythonInterpreter.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static PythonInterpreter getBasicInterpreter() throws Exception {
		return getBasicInterpreter(null);
	}
	
	public static PythonInterpreter getBasicInterpreter(Set<String> extraPaths) throws Exception {
		return getBasicInterpreter(extraPaths, JythonInterpreterUtils.class.getClassLoader(), Boolean.getBoolean(RUN_IN_ECLIPSE));
	}
	
	/**
	 * Create Jython interpreter with extra java libraries
	 * 
	 * @param extraPaths set of paths to extra libraries to load
	 * @return a new Jython Interpreter
	 * @throws Exception from getJythonInterpreterDirectory in case of missing JYTHON_BUNDLE_LOC when no Jython bundle found
	 */
	public static PythonInterpreter getBasicInterpreter(Set<String> extraPaths, ClassLoader classLoader, boolean isRunningInEclipse) throws Exception {
		final long start = System.currentTimeMillis();
		
		Properties preProperties = System.getProperties();
		Properties postProperties = new Properties();
		
		//Set some useful parameters for the jython environment
		File jyRoot = JythonPath.getInterpreterDirectory(isRunningInEclipse);
		postProperties.setProperty("python.home", jyRoot.getAbsolutePath());
		postProperties.setProperty("python.executable", new File(jyRoot, JythonPath.getJythonExecutableName()).getAbsolutePath());
		
		//Set the cache for java classes loaded by Jython
		String cacheDir = System.getProperty("python.cachedir");
		if (cacheDir == null) {
			cacheDir = "/scratch/.jython_consumer_cachedir";
			postProperties.setProperty("python.cachedir", cacheDir);
		}
		File cacheDirPath = new File(cacheDir);
		if (!cacheDirPath.exists()) {
			try {
				logger.debug("Creating jython cachedir", cacheDir);
				cacheDirPath.mkdirs();
			} catch (Exception e){
				logger.warn("Could not create python.cachedir. Resetting to cachedir");
				postProperties.setProperty("python.cachedir", "cachedir");
			}
		}
		
		//Set up the path environmental variable & send it to properties
		StringBuilder allPaths = new StringBuilder();
		allPaths.append(new File(jyRoot, "jython.jar").getAbsolutePath()+File.pathSeparatorChar);
		File jyLib = new File(jyRoot, "Lib");
		allPaths.append(jyLib.getAbsolutePath()+File.pathSeparatorChar);
		allPaths.append(new File(jyLib, "distutils").getAbsolutePath()+File.pathSeparatorChar);
		allPaths.append(new File(jyLib, "site-packages").getAbsolutePath()+File.pathSeparatorChar);
		//If there's anything else to add to the path, add it.
		if (extraPaths != null) {
			for (String path : extraPaths) {
				allPaths.append(path);
				allPaths.append(File.pathSeparatorChar);
			}
		}
		String pythonPath = allPaths.toString();
		postProperties.setProperty("python.path", pythonPath);

		logger.debug("Starting new Jython Interpreter.");
		PythonInterpreter.initialize(preProperties, postProperties, null);
		
		//Create object to give access to python system
		PySystemState state = new PySystemState();
		
		//This adds an external classloader & reports classpath
		if (classLoader!=null) state.setClassLoader(classLoader);
		logger.info("Class loader is {}", classLoader);
		if (classLoader instanceof URLClassLoader) {
			logger.debug("URL classpath:");
			for (URL u : ((URLClassLoader) classLoader).getURLs()) {
				logger.debug("\t{}", u.getPath());
			}
		}
		logger.debug("Classpath:");
		for (String p : System.getProperty("java.class.path").split(File.pathSeparator)) {
			logger.debug("\t{}", p);
		}
		
		//All set? Create the interpreter!
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
	public static PythonInterpreter getScisoftpyInterpreter() throws Exception{
		final Set<String> extraPaths = new HashSet<String>();
		
		try {
			//This seems to work in git repo case (and presumably in binary)
			//Old code in 0f667dd and before (now deleted) was more verbose
			File pythonPlugin = BundleUtils.getBundleLocation(SCISOFTPY);
			logger.debug("Found Scisoft Python (Jython) plugin: {}", pythonPlugin);
			File binDir = new File(pythonPlugin, "bin");
			if (binDir.exists()) {
				logger.debug("Found bin directory at {}", binDir);
				extraPaths.add(binDir.getAbsolutePath());
			} else {
				extraPaths.add(pythonPlugin.getAbsolutePath());
			}
		} catch (Exception e) {
			logger.error("Errors encountered getting paths to Scisoft Python (Jython) plugin", e);
		}
		
		PythonInterpreter interpreter = getBasicInterpreter(extraPaths);
		
		interpreter.exec("import sys");
		interpreter.exec("for p in sys.path: print '\t%s' % p");
		interpreter.exec("import scisoftpy as dnp");
		
		return interpreter;
	}
	
	/**
	 * Provide an interpreter with same plugin/lib paths as the one in DAWN,
	 * with possibility to add extra paths 
	 * 
	 * @param classLoader
	 * @param extras
	 * @return PythonInterpreter
	 * @throws Exception
	 */
	public static PythonInterpreter getFullInterpreter(ClassLoader classLoader, String... extras) throws Exception {

		//Where we are searching for additional jars/plugins (affected by whether running in eclipse)
		boolean isRunningInEclipse = "true".equalsIgnoreCase(System.getProperty(RUN_IN_ECLIPSE));
		File pluginsDir = JythonPath.getPluginsDirectory(isRunningInEclipse); 
		if (pluginsDir == null) {
			logger.error("Failed to find the plugins directory! Cannot start jython interpreter.");
			return null;
		}
		logger.debug("Plugins directory set to: {}", pluginsDir);

		//TODO Move this to where ever it's being called - I'd rather add paths from calling method rather than
		//forcing all callers of this method to have this set of paths. MTW
//		final Set<String> extras = new HashSet<String>();
//		extras.add("org.eclipse.dawnsci.*");
		
		//Instantiate the jyPaths HashSet and get its contents
		Set<String> jyPaths = JythonPath.assembleJyPaths(pluginsDir, Arrays.asList(extras), isRunningInEclipse);
			
		//If we've got everything in the extraPaths list, send it to the interpreter maker
		PythonInterpreter interpreter = getBasicInterpreter(jyPaths, classLoader, isRunningInEclipse);
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
		
		PythonInterpreter interpreter = getScisoftpyInterpreter();
		
		final long end = System.currentTimeMillis();
		
		logger.debug("Created new Jython Interpreter in {}ms.", end-start);
	
		return interpreter;
	}

}
