/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.jython;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dawb.common.util.eclipse.BundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JythonPath {
	
	private static Logger logger = LoggerFactory.getLogger(JythonPath.class);
	
	private static final String GIT_REPO_ENDING = ".git";
	private static final String GIT_SUFFIX = "_git";
	private static final String JYTHON_BUNDLE = "uk.ac.diamond.jython";
	private static final String JYTHON_BUNDLE_LOC = JYTHON_BUNDLE + ".location";
	private static final String JYTHON_EXEC= "jython.jar";
	private static final String JYTHON_VERSION = "2.5";
	private static final String JYTHON_DIR = "jython" + JYTHON_VERSION;
	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";
	
	/*
	 * Lists of Jars we want to/don't want to include
	 */
	private static final String[] blackListedJarDirs = {
		"uk.ac.gda.libs",
		"ch.qos.logback.eclipse",
		"ch.qos.logback.beagle",
		"org.dawb.workbench.jmx",
		GIT_REPO_ENDING,
		JYTHON_DIR,
		"org.dawnsci.persistence.test" // Required for I11 LDE script (we don't want the tests!)
	};
	private static final String[] requiredJars = {
		"org.python.pydev",
		"cbflib-0.9",
		"org.apache.commons.codec",
		"org.apache.commons.math", // Includes math3
		"uk.ac.diamond.CBFlib",
		"uk.ac.diamond.jama",
		"uk.ac.diamond.ejml",
		"uk.ac.diamond.ddogleg",
		"org.apache.commons.lang",
		"org.eclipse.dawnsci.analysis", // Includes api, dataset, tree, etc
		"org.eclipse.dawnsci.nexus", // required for loading to work in client started from IDE
		"org.eclipse.dawnsci.plotting.api",
		"uk.ac.diamond.scisoft.analysis",
		"uk.ac.diamond.scisoft.diffraction.powder",
		"uk.ac.diamond.scisoft.python",
		"uk.ac.diamond.scisoft.spectroscopy",
		"uk.ac.gda.common",
		"org.eclipse.dawnsci.hdf5", // Fix to http://jira.diamond.ac.uk/browse/SCI-1467
		"slf4j.api",
		"jcl.over.slf4j",
		"log4j.over.slf4j",
		"ch.qos.logback.core",
		"ch.qos.logback.classic",
		"com.springsource.org.apache.commons",
		"com.springsource.javax.media.jai.core",
		"com.springsource.javax.media.jai.codec",
		"JTransforms",
		"jai_imageio",
		"it.tidalwave.imageio.raw",
		"javax.vecmath",
		"uk.ac.diamond.org.apache.ws.commons.util",
		"uk.ac.diamond.org.apache.xmlrpc.client",
		"uk.ac.diamond.org.apache.xmlrpc.common",
		"uk.ac.diamond.org.apache.xmlrpc.server",
		"com.thoughtworks.xstream",
		"uk.ac.diamond.org.jscience4",
		"org.eclipse.equinox.common", // Required for IRemotePlottingSystem
		"org.dawnsci.boofcv", // Required for running boofcv image processing in jython
		"org.boofcv.feature",
		"org.boofcv.geo",
		"org.boofcv.ip",
		"org.boofcv.sfm",
		"org.ddogleg",
		"org.dawnsci.persistence" // Required for I11 LDE script
	};
	/*
	 * Plugins we want/don't want
	 */
	private final static String[] pluginKeys = {
		"org.eclipse.dawnsci.hdf5", // required for loading to work in client started from IDE
		"org.eclipse.dawnsci.analysis.*", // includes api, dataset, tree, etc
		"org.eclipse.dawnsci.plotting.*", // required to expose IRemotePlottingSystem to Jython
		"uk.ac.diamond.scisoft.analysis",
		"uk.ac.diamond.scisoft.diffraction.powder",
		"uk.ac.diamond.scisoft.python",
		"uk.ac.diamond.CBFlib",
		"uk.ac.gda.common",
		"org.dawnsci.boofcv", //required for boofcv services
		"org.ddogleg",
		"ncsa.hdf",
		"org.dawnsci.persistence" // Required for I11 LDE script
	};
	
	/**
	 * Provides location of plugin files; behaviour depends whether we're running in eclipse
	 * @param isRunningInEclipse Boolean, true if running in eclipse
	 * @return Directory where plugins live (defined as parent of current bundle)
	 */
	public static File getPluginsDirectory(boolean isRunningInEclipse) {
		try {
			File scisoftParent = BundleUtils.getBundleLocation(SCISOFTPY).getParentFile();
			if (isRunningInEclipse) {
				scisoftParent = scisoftParent.getParentFile();
			}
			//Need to include a logging statement
			return scisoftParent;
		} catch (Exception e) {
			logger.error("Could not find Scisoft Python plugin", e);
		}
		return null;
	}
	
	/**
	 * Gets the interpreter directory using the bundle location
	 * @return directory path 
	 * @throws Exception when JYTHON_BUNDLE_LOC is not set (and no Jython bundle found)
	 */
	public static File getInterpreterDirectory(boolean isRunningInEclipse) throws Exception {
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
		jyBundleLoc = new File(jyBundleLoc, JYTHON_DIR);
		
		// Test whether we're running in 
		if (!isRunningInEclipse && jyBundleLoc.getAbsolutePath().contains(GIT_REPO_ENDING)) {
			logger.error("Using jython from git, but -Drun.in.eclipse set false. This will cause errors.");
			return null;
		}
		logger.info("Jython bundle found at: {}", jyBundleLoc);
		return jyBundleLoc;
	}
	
	/**
	 * Returns a set containing all of the plugins/jars found in the given 
	 * directories which are in the requiredJars, pluginKeys, extraPlugins and not
	 * in the blackListedJarDirs arrays.
	 * @param pluginsDir
	 * @param isRunningInEclipse
	 * @return jyPaths Set containing all of the required plugins
	 */
	public static final Set<String> assembleJyPaths(File pluginsDir, boolean isRunningInEclipse) {
		return assembleJyPaths(pluginsDir, null, isRunningInEclipse);
	}
	
	/**
	 * Returns a set containing all of the plugins/jars found in the given 
	 * directories which are in the requiredJars, pluginKeys, extraPlugins and not
	 * in the blackListedJarDirs arrays.
	 * @param pluginsDir
	 * @param extras
	 * @param isRunningInEclipse
	 * @return jyPaths Set containing all of the required plugins
	 */
	public static final Set<String> assembleJyPaths(File pluginsDir, Collection<String> extras, boolean isRunningInEclipse) {
		
		final Set<String> jyPaths = new HashSet<String>();
		
		//Find third party jar files & add them all
		final List<File> thirdPartyJars = findJars(pluginsDir, extras);
		for (File jar : thirdPartyJars) {
			if (jyPaths.add(jar.getAbsolutePath())) {
				logger.debug("Adding jar file to jython path: {} ", jar.getAbsolutePath());
			}
		}
		
		//Find all the plugin directories
		List<File> allPluginDirs = findDirs(pluginsDir, extras, isRunningInEclipse);
		
		//Find other plugin directories. Where searched depends on if running in eclipse
		if (isRunningInEclipse) {
			//Locate wsdir (w/o GIT_SUFFIX)
			File wsDir = pluginsDir;
			if (!new File(wsDir, "tp").isDirectory()) {
				String ws = wsDir.getName();
				int i = ws.indexOf(GIT_SUFFIX);
				if (i >= 0) {
					wsDir = new File(wsDir.getParentFile(), ws.substring(0, i));
				}
			}
			//Add dirs inside the wsDir/plugins directory
			final File wsPluginsDir = new File(wsDir, "plugins");
			if (wsPluginsDir.isDirectory()) {
				allPluginDirs.addAll(findDirs(wsPluginsDir, extras, isRunningInEclipse));
			}
			//Add jars inside the wsdir/plugins directory
			wsDir = new File(wsDir, "tp");
			if (wsDir.isDirectory()) {
				wsDir = new File(wsDir, "plugins");
				final List<File> tJars = findJars(wsDir, extras);
				for (File file : tJars) {
					if (jyPaths.add(file.getAbsolutePath())) {
						logger.debug("Adding jar file to jython path: {} ", file.getAbsolutePath());
					}
				}
			}
			//Add all plugin directories & jars contained therein
			for (File dir: allPluginDirs){
				File binDir = new File(dir,"bin");
				if (binDir.isDirectory()) {
					String binDirPath = binDir.getAbsolutePath();
					if (jyPaths.add(binDirPath)) {
						logger.debug("Adding directory to jython path: {}", binDirPath);
					}
				}
				final List<File> tJars = findJars(dir, extras);
				for (File jar : tJars) {
					if (jyPaths.add(jar.getAbsolutePath())) {
						logger.debug("Adding jar file to jython path: {} ", jar.getAbsolutePath());
					}
				}
			}
		}
		else {
			//Only add directories to pyPaths
			for (File dir: allPluginDirs) {
				String dirPath = dir.getAbsolutePath();
				if (jyPaths.add(dirPath)) {
					logger.debug("Adding directory to jython path: {}", dirPath);
				}
			}
		}
		return jyPaths;
		
	}
	/**
	 * Recursively search through a given directory to locate all jar files provided 
	 * they are in the requiredJars/extraPlugins lists
	 * @param directory location searched for jar files 
	 * @return List of jar files which will be added to the path
	 */
	private static final List<File> findJars(File directory, Collection<String> extraPlugins) {
		final List<File> jarFiles = new ArrayList<File>();
	
		
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				final String name = file.getName();
				//If the file is a jar, then add it
				if (name.endsWith(".jar")) {
					if (isRequired(file, requiredJars, extraPlugins)) {
						jarFiles.add(file);
					}
				} else if (file.isDirectory() && !isRequired(file, blackListedJarDirs)) {
					jarFiles.addAll(findJars(file, extraPlugins));
				}
			}
		}
		return jarFiles;
	}
	
	
	/**
	 * Method returns path to plugin directories (behaviour depends on whether in eclipse
	 * @param directory Search location
	 * @param isRunningInEclipse Boolean, true if running in eclipse
	 * @return list of directories
	 */
	public static List<File> findDirs(File directory, Collection<String> extras, boolean isRunningInEclipse) {
		final List<File> plugins = new ArrayList<File>();
		
		// TODO This could be shortened code-wise (move for loop outside if), but that might be slower in execution
		if (isRunningInEclipse) {
			//Look in git repos for plugin parents in given lists
			for (File file : directory.listFiles()) {
				if (file.isDirectory()) {
					String fileName = file.getName();
					if (fileName.endsWith(GIT_REPO_ENDING)) {
						//Look in plugin parent for actual plugin directories
						plugins.addAll(findDirs(file, extras, false));
					}
				}
			}
		} else {
			//Look in directory for plugin names in given lists
			for (File file : directory.listFiles()) {
				if (file.isDirectory()) {
					if (isRequired(file, pluginKeys, extras)) {
						logger.debug("Found plugin directory {}", file);
						plugins.add(file);
					}
				}
			}
		}
		//Return all the directories we found
		return plugins;
	}
	
	/**
	 * Check whether a file is in a given list
	 * @param file Filename to search for
	 * @param keys List to search against
	 * @return Boolean, true if file is in list
	 */
	private static boolean isRequired(File file, String[] keys) {
		return isRequired(file, keys, null);
	}
	/**
	 * Check whether a file is in given lists
	 * @param file Filename to search for
	 * @param keys List to search against
	 * @return Boolean, true if file is in list
	 */
	private static boolean isRequired(File file, String[] keys, Collection<String> extraKeys) {
		String filename = file.getName();
//		logger.debug("Jar/dir found: {}", filename);
		for (String key : keys) {
			if (key!=null && key.endsWith(".*")) key = key.substring(0,key.length()-1);
			if (filename.startsWith(key)) return true;
		}
		if (extraKeys != null) {
			for (String key : extraKeys) {
				if (key!=null && key.endsWith(".*")) key = key.substring(0,key.length()-1);
				if (filename.startsWith(key)) return true;
			}
		}
		return false;
	}
		
	/**
	 * Returns name of Jython executable.
	 * @return JYTHON_EXEC
	 */
	public static String getJythonExecutableName() {
		return JYTHON_EXEC;
	}
	
	public static String getJythonVersion() {
		return JYTHON_VERSION;
	}

}
