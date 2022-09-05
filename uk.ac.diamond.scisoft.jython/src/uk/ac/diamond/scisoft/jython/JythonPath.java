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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold methods to find files and directories to put on the PYTHONPATH for Jython
 */
public class JythonPath {
	private static Logger logger = LoggerFactory.getLogger(JythonPath.class);

	public static final String GIT_REPO_ENDING = ".git";
	private static final String JYTHON_BUNDLE = "uk.ac.diamond.jython";
	private static final String JYTHON_BUNDLE_LOC = JYTHON_BUNDLE + ".location";
	private static final String JYTHON_EXEC= "jython.jar";
	private static final String JYTHON_MAJOR_VERSION = "2";
	private static final String JANUARY_PREFIX = "org.eclipse.january";

	/**
	 * Name of bundle that contains ScisoftPy
	 */
	public static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";

	/*
	 * Lists of Jars we want to/don't want to include
	 */
	private static final String[] blockedJarDirs = {
		"ch.qos.logback.eclipse",
		"ch.qos.logback.beagle",
		"org.dawb.workbench.jmx",
		GIT_REPO_ENDING,
		"org.dawnsci.persistence.test", // Required for I11 LDE script (we don't want the tests!)
		"target", // for Maven builds
	};
	private static final String[] requiredJars = {
		"org.python.pydev",
		"cbflib-0.9",
		"org.apache.commons.math3", // math3 only now
		"uk.ac.diamond.CBFlib",
		"uk.ac.diamond.jama",
		"com.googlecode.efficient-java-matrix-library.core",
		"org.ddogleg",
		"org.apache.commons.lang",
		"org.eclipse.dawnsci.analysis", // Includes api, dataset, tree, etc
		"org.eclipse.dawnsci.nexus", // required for loading to work in client started from IDE
		"org.eclipse.dawnsci.plotting.api",
		"uk.ac.diamond.scisoft.analysis",
		"uk.ac.diamond.scisoft.diffraction.powder",
		"uk.ac.diamond.scisoft.python",
		"com.github.tschoonj.xraylib", // required by spectroscopy
		"uk.ac.diamond.scisoft.spectroscopy",
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
		"org.apache.ws.commons.util",
		"org.apache.xmlrpc.client",
		"org.apache.xmlrpc.common",
		"org.apache.xmlrpc.server",
		"javax.measure.unit-api",
		"si.uom.si-",
		"tec.uom.lib",
		"tec.units.indriya",
		"org.eclipse.equinox.common", // Required for IRemotePlottingSystem
		"org.dawnsci.boofcv", // Required for running boofcv image processing in jython
		"org.boofcv.feature",
		"org.boofcv.geo",
		"org.boofcv.ip",
		"org.boofcv.sfm",
		"org.dawnsci.persistence", // Required for I11 LDE script
		"com.fasterxml.jackson.core", // Required for MillerSpaceMapper
		"uk.ac.diamond.platform-observables", // Required for gda.observables
//		"org.eclipse.jdt.annotation", // XXX to test pruning
	};

	/*
	 * Plugins we want
	 */
	private final static String[] pluginKeys = {
		"org.eclipse.dawnsci.hdf5", // required for loading to work in client started from IDE
		"org.eclipse.dawnsci.analysis", // includes api, dataset, tree, etc
		"org.eclipse.dawnsci.nexus", // required for loading to work in client started from IDE
		"org.eclipse.dawnsci.plotting", // required to expose IRemotePlottingSystem to Jython
		"uk.ac.diamond.scisoft.analysis",
		"uk.ac.diamond.scisoft.diffraction.powder",
		"uk.ac.diamond.scisoft.python",
		"uk.ac.diamond.CBFlib",
		"org.dawnsci.boofcv", // required for boofcv services
		"org.ddogleg",
		"hdf.hdf5lib",
		"org.dawnsci.persistence" // Required for I11 LDE script
	};

	/**
	 * @param pluginsDir
	 * @return true if this plugins directory is not called "plugins" which indicates in it not in a deployed product
	 */
	public static boolean isRunningInEclipse(File pluginsDir) {
		return !pluginsDir.getName().equals("plugins");
	}

	/**
	 * @return directory where ScisoftPy plugin lives
	 */
	public static File getScisoftPyDirectory() {
		try {
			return getBundleLocation(SCISOFTPY);
		} catch (IOException e) {
			logger.error("Could not find Scisoft Python plugin", e);
		}
		return null;
	}

	/**
	 * Provides location of plugin files; behaviour depends whether we're running in eclipse
	 * @return directory where plugins live (defined as parent of current bundle) or where repos live (assumed to be grandparent)
	 */
	public static File getPluginsDirectory() {
		try {
			File scisoftParent = getBundleLocation(SCISOFTPY).getParentFile();
			if (isRunningInEclipse(scisoftParent)) {
				scisoftParent = scisoftParent.getParentFile();
			}
			return scisoftParent;
		} catch (Exception e) {
			logger.error("Could not find plugins or repos directory", e);
		}
		return null;
	}

	/**
	 * @param bundleName
	 * @return file this can return null if bundle is not found
	 * @throws IOException
	 */
	public static File getBundleLocation(final String bundleName) throws IOException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			return null;
		}
		return FileLocator.getBundleFile(bundle).getCanonicalFile();
	}


	/**
	 * Gets the interpreter directory using the bundle location
	 * @return directory path 
	 * @throws Exception when JYTHON_BUNDLE_LOC is not set (and no Jython bundle found)
	 */
	public static File getInterpreterDirectory() throws Exception {
		File jyBundleLoc = null;
		try {
			jyBundleLoc = getBundleLocation(JYTHON_BUNDLE);
		} catch (Exception ignored) {
		}
		if (jyBundleLoc == null) {
			if (System.getProperty(JYTHON_BUNDLE_LOC)==null)
				throw new Exception("Please set the property '" + JYTHON_BUNDLE_LOC + "' for this test to work!");
			jyBundleLoc = new File(System.getProperty(JYTHON_BUNDLE_LOC));
		}
		logger.info("Jython bundle found at: {}", jyBundleLoc);
		return jyBundleLoc;
	}

	/**
	 * Returns a set containing all of the plugins/jars found in the given 
	 * directories which are in the requiredJars, pluginKeys, extraPlugins and not
	 * in the blackListedJarDirs arrays.
	 * @param pluginsDir
	 * @return jyPaths Set containing all of the required plugins
	 */
	public static final Set<String> assembleJyPaths(File pluginsDir) {
		return assembleJyPaths(pluginsDir, null, null);
	}

	/**
	 * Returns a set containing all of the plugins/jars found in the given 
	 * directories which are in the requiredJars, pluginKeys, extraPlugins and not
	 * in the blackListedJarDirs arrays.
	 * @param pluginsDir
	 * @param extras
	 * @return jyPaths Set containing all of the required plugins
	 */
	public static final Set<String> assembleJyPaths(File pluginsDir, Collection<String> extras) {
		return assembleJyPaths(pluginsDir, null, extras);
	}

	/**
	 * Returns a set containing all of the plugins/jars found in the given 
	 * directories which are in the requiredJars, pluginKeys, extraPlugins and not
	 * in the blackListedJarDirs arrays.
	 * @param pluginsDir
	 * @param allPluginDirs can be null, in which case, they will be found from pluginsDir
	 * @param extras
	 * @return jyPaths Set containing all of the required plugins
	 */
	public static final Set<String> assembleJyPaths(File pluginsDir, List<File> allPluginDirs, Collection<String> extras) {
		final Set<String> jyPaths = new HashSet<String>();

		// Find third party jar files & add them all
		final List<File> thirdPartyJars = findJars(pluginsDir, extras);
		for (File jar : thirdPartyJars) {
			if (jyPaths.add(jar.getAbsolutePath())) {
				logger.debug("Adding jar file to jython path: {} ", jar.getAbsolutePath());
			}
		}
		
		// Find all the plugin directories
		boolean isRunningInEclipse = isRunningInEclipse(pluginsDir);
		if (allPluginDirs == null) {
			allPluginDirs = findDirs(pluginsDir, extras, isRunningInEclipse);
		}

		// Find other plugin directories. Where searched depends on if running in eclipse
		if (isRunningInEclipse) {
			// Now that we use Oomph, look in P2 bundle pool for required jars
			File wsDir = new File(System.getProperty("osgi.syspath"));
			if (wsDir.isDirectory()) {
				final List<File> tJars = findJars(wsDir, extras);
				for (File file : tJars) {
					if (jyPaths.add(file.getAbsolutePath())) {
						logger.debug("Adding jar file to jython path: {} ", file.getAbsolutePath());
					}
				}
			}
			// Add all plugin directories contained therein
			for (File dir: allPluginDirs) {
				File binDir = new File(dir, "bin");
				if (binDir.isDirectory()) {
					String binDirPath = binDir.getAbsolutePath();
					if (jyPaths.add(binDirPath)) {
						logger.debug("Adding directory to jython path: {}", binDirPath);
					}
				}
			}

			// add January
			List<File> janDirs = findJanuaryDirs(pluginsDir);
			if (janDirs != null) {
				for (File d : janDirs) {
					File binDir = new File(d, "bin");
					if (binDir.isDirectory()) {
						String binDirPath = binDir.getAbsolutePath();
						if (jyPaths.add(binDirPath)) {
							logger.debug("Adding January directory to jython path: {}", binDirPath);
						}
					}
				}
			} else { // find jar in bundle pool
				List<File> jJars = findJanuaryJars(wsDir);
				if (jJars != null) {
					for (File jar : jJars) {
						if (jyPaths.add(jar.getAbsolutePath())) {
							logger.debug("Adding January jar file to jython path: {} ", jar.getAbsolutePath());
						}
					}
				}
			}
		}
		else {
			// Only add directories to pyPaths
			for (File dir: allPluginDirs) {
				String dirPath = dir.getAbsolutePath();
				if (jyPaths.add(dirPath)) {
					logger.debug("Adding directory to jython path: {}", dirPath);
				}
			}

			// add January
			for (File jar: findJanuaryJars(pluginsDir)) {
				jyPaths.add(jar.getAbsolutePath());
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
		if (!directory.isDirectory()) {
			return Collections.emptyList();
		}

		final List<File> jarFiles = new ArrayList<File>();

		for (File file : directory.listFiles()) {
			final String name = file.getName();
			//If the file is a jar, then add it
			if (name.endsWith(".jar")) {
				if (isRequired(file, requiredJars, extraPlugins)) {
					jarFiles.add(file);
				}
			} else if (file.isDirectory() && !isRequired(file, blockedJarDirs)) {
				jarFiles.addAll(findJars(file, extraPlugins));
			}
		}
		return pruneJars(jarFiles);
	}

	/**
	 * Search through a given directory to locate all jar files provided 
	 * they begin with {@link JythonPath#JANUARY_PREFIX}
	 * @param directory location searched for jar files 
	 * @return List of jar files which will be added to the path or null
	 */
	private static final List<File> findJanuaryJars(File directory) {
		List<File> jarFiles = null;

		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				final String name = file.getName();
				//If the file is a jar, then add it
				if (name.startsWith(JANUARY_PREFIX) && name.endsWith(".jar")) {
					if (jarFiles == null) {
						jarFiles = new ArrayList<>();
					}
					jarFiles.add(file);
				}
			}
		}
		return pruneJars(jarFiles);
	}

	/**
	 * Prune list of jars of older versions
	 * @param jars
	 * @return list of jars of latest versions
	 */
	private static final List<File> pruneJars(List<File> jars) {
		if (jars == null) {
			jars = Collections.emptyList();
		}
		if (jars.isEmpty()) {
			return jars;
		}

		// relies on lexicographic sorting (where latest versions are later in order)
		// and last underscore character to separate version
		Collections.sort(jars);
		List<File> jarFiles = new ArrayList<>();
		String current = null;
		File last = null;
		for (File j : jars) {
			String n = j.getName();
			if (current == null || !n.startsWith(current)) {
				if (last != null) {
					jarFiles.add(last);
				}
				int i = n.lastIndexOf('_');
				current = i >= 0 ? n.substring(0, i + 1) : n;
			}
			last = j;
		}
		if (last != null) {
			jarFiles.add(last);
		}
		return jarFiles;
	}

	/**
	 * Method returns path to plugin directories (behaviour depends on whether in eclipse)
	 * @param directory Search location
	 * @param extras extra bundles to find
	 * @param isRunningInEclipse Boolean, true if running in eclipse
	 * @return list of directories
	 */
	public static List<File> findDirs(File directory, Collection<String> extras, boolean isRunningInEclipse) {
		final List<File> plugins = new ArrayList<File>();
		
		if (isRunningInEclipse) {
			// Look in git repos for plugin parents in given lists
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
			// Look in directory for plugin names in given lists
			for (File file : directory.listFiles()) {
				if (file.isDirectory()) {
					if (isRequired(file, pluginKeys, extras)) {
						logger.debug("Found plugin directory {}", file);
						plugins.add(file);
					}
				}
			}
		}
		// Return all the directories we found
		return plugins;
	}

	/**
	 * Method returns certain directories in the January repository
	 * @return directories or null
	 */
	private static List<File> findJanuaryDirs(File directory) {
		// Look in git repos for plugin parents in given lists
		for (File file : directory.listFiles()) {
			if (file.isDirectory() && file.getName().equals("january" + GIT_REPO_ENDING)) {
				final List<File> plugins = new ArrayList<File>();
				for (File f : file.listFiles()) {
					String fileName = f.getName();
					if (fileName.equals(JANUARY_PREFIX) || fileName.endsWith(".asserts")) {
						plugins.add(f);
					}
				}
				return plugins;
			}
		}

		return null;
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
		for (String key : keys) {
			if (filename.startsWith(key)) return true;
		}
		if (extraKeys != null) {
			for (String key : extraKeys) {
				if (filename.startsWith(key)) return true;
			}
		}
		return false;
	}

	/**
	 * @return name of Jython executable
	 */
	public static String getJythonExecutableName() {
		return JYTHON_EXEC;
	}

	/**
	 * @return major version number of Jython interpreter
	 */
	public static String getJythonMajorVersion() {
		return JYTHON_MAJOR_VERSION;
	}
}
