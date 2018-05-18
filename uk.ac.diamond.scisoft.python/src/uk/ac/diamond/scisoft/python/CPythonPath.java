/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import java.io.File;
import java.nio.file.Paths;

import org.dawb.common.util.eclipse.BundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold methods to find files and directories to put on the PYTHONPATH for CPython
 */
public class CPythonPath {

	
	private CPythonPath() {}
	
	private static Logger logger = LoggerFactory.getLogger(CPythonPath.class);

	private static final String GIT_REPO_ENDING = ".git";
	private static final String CPYTHON_BUNDLE;// = "uk.ac.diamond.cpython"; // this may need to be changed to something platform dependent
	static {
		String arch = System.getProperty("os.arch");
		if (!arch.endsWith("64")) {
			CPYTHON_BUNDLE = "uk.ac.diamond.cpython.unsupported_arch";
		} else  {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.contains("windows")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.win32.x86_64";
			} else if (osName.contains("linux")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.linux.x86_64";
			} else if (osName.contains("mac os")) {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.macosx.x86_64";
			} else {
				CPYTHON_BUNDLE = "uk.ac.diamond.cpython.unsupported_os.x86_64";
			}
		}
	}
	private static final String CPYTHON_BUNDLE_LOC = CPYTHON_BUNDLE + ".location";
	private static final String CPYTHON_MAJOR_VERSION = "2";
	private static final String CPYTHON_MINOR_VERSION = "7";
	private static final String CPYTHON_VERSION = CPYTHON_MAJOR_VERSION + "." + CPYTHON_MINOR_VERSION;
	private static final String CPYTHON_EXEC= "python" + CPYTHON_VERSION; // needs .exe on Windows
	private static final String CPYTHON_DIR = "cpython" + CPYTHON_VERSION;
	private static final String SCISOFTPY = "uk.ac.diamond.scisoft.python";

	
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
			// Need to include a logging statement
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
		File cpyBundleLoc = null;
		try {
			cpyBundleLoc = BundleUtils.getBundleLocation(CPYTHON_BUNDLE);
		} catch (Exception ignored) {
		}
		if (cpyBundleLoc == null) {
			if (System.getProperty(CPYTHON_BUNDLE_LOC)==null)
				throw new Exception("Please set the property '" + CPYTHON_BUNDLE_LOC + "' for this test to work!");
			cpyBundleLoc = new File(System.getProperty(CPYTHON_BUNDLE_LOC));
		}
		cpyBundleLoc = new File(cpyBundleLoc, Paths.get(CPYTHON_DIR, "bin").toString());
		
		// Test whether we're running in 
		if (!isRunningInEclipse && cpyBundleLoc.getAbsolutePath().contains(GIT_REPO_ENDING)) {
			logger.error("Using cpython from git, but -Drun.in.eclipse set false. This will cause errors.");
			return null;
		}
		logger.info("CPython interpreter directory found at: {}", cpyBundleLoc);
		return cpyBundleLoc;
	}

	/**
	 * @return name of CPython executable
	 */
	public static String getCPythonExecutableName() {
		return CPYTHON_EXEC;
	}

	/**
	 * @return major version number of CPython interpreter
	 */
	public static String getCPythonMajorVersion() {
		return CPYTHON_MAJOR_VERSION;
	}
	
	/**
	 * @return version number of CPython interpreter
	 */
	public static String getCPythonVersion() {
		return CPYTHON_VERSION;
	}
}
