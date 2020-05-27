/*-
 * Copyright 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class IOTestUtils {

	/**
	 * Prefix to folder in which test files are to be generated
	 */
	private static final String OUTPUT_FOLDER_PREFIX = "test-scratch/";

	/**
	 * Generates a (relative) directory name based on a class name. Uses the appropriate separators for the platform.
	 * 
	 * @param classname
	 *            - the name of the class on which to base the directory name (a value something like
	 *            "gda.analysis.io.JPEGTest").
	 * @return - the derived directory name.
	 */
	public static String generateDirectorynameFromClassname(String classname) {
		// the generated directory name is usable on both Linux and Windows
		// (which uses \ as a separator).
		return OUTPUT_FOLDER_PREFIX + classname.replace('.', '/') + '/';
	}

	/**
	 * Helper function to (recursively) delete a directory and all its contents
	 * 
	 * @param dir
	 *            - path to directory to delete
	 * @return - boolean True if directory no longer exists
	 */
	static boolean deleteDir(File dir) {
		if (!dir.exists()) {
			return true;
		}
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (String element : children) {
				boolean success = deleteDir(new File(dir, element));
				if (!success) {
					System.out.println("1. deleteDir could not delete: " + new File(dir, element));
					return false;
				}
			}
		}
		if (dir.delete()){
			return true;
		}
		System.out.println("2. deleteDir could not delete: " + dir +". Make sure each test creates a uniquely named folder. Otherwise stale NFS locks may prevent folder deletion.");
		return false;
	}

	/**
	 * Creates a scratch directory for test files created by the specified class.
	 * 
	 * @param clazz
	 *            the class
	 * @return the directory
	 */
	public static File createClassScratchDirectory(Class<?> clazz) {
		File dir = new File(generateDirectorynameFromClassname(clazz.getCanonicalName()));
		dir.mkdirs();
		return dir;
	}

	/**
	 * Creates an empty directory for use by test code. If the directory exists (from a previous test), the directory
	 * and all its contents are first deleted.
	 * 
	 * @param testScratchDirectoryname
	 *            - the name of the directory to create.
	 * @throws Exception
	 */
	public static void makeScratchDirectory(String testScratchDirectoryname) throws Exception {
		// delete any remains from a previous run of this test
		if (!deleteDir(new File(testScratchDirectoryname))) {
			throw new Exception("Unable to delete old test scratch directory " + testScratchDirectoryname);
		}
		// set up for a new run of this test
		if (!(new File(testScratchDirectoryname)).mkdirs()) {
			throw new Exception("Unable to create new test scratch directory " + testScratchDirectoryname);
		}
	}

	/**
	 * Returns a {@link File} for the specified resource, associated with the
	 * specified class.
	 * 
	 * @param clazz the class with which the resource is associated
	 * @param name the desired resource
	 * 
	 * @return a {@link File} for the resource, if it is found
	 * 
	 * @throws FileNotFoundException if the resource cannot be found
	 */
	public static File getResourceAsFile(Class<?> clazz, String name) throws FileNotFoundException {
		URL url = clazz.getResource(name);
		if (url == null) {
			throw new FileNotFoundException(name + " (resource not found)");
		}
		return new File(url.getFile());
	}

	/**
	 * Sets up of environment for the a test Set property so that output is to Nexus format file Uses
	 * MockJythonServerFacade and MockJythonServer to configure InterfaceProvider Configure logging so that DEBUG and
	 * above go to log.txt in output folder
	 * 
	 * @param testClass
	 *            e.g. gda.data.nexus.ScanToNexusTest
	 * @param nameOfTest
	 *            name of test method which the testClass e.g. testCreateScanFile
	 * @param makedir
	 *            if true the scratch dir is deleted and constructed
	 * @return The directory into which output will be sent
	 * @throws Exception
	 *             if setup fails
	 */
	public static String setUpTest(Class<?> testClass, String nameOfTest, boolean makedir) throws Exception {
		String name = testClass.getCanonicalName();
		if (name == null) {
			throw new IllegalArgumentException("getCanonicalName failed for class " + testClass.toString());
		}
		String testScratchDirectoryName = generateDirectorynameFromClassname(testClass.getCanonicalName())
				+ nameOfTest;
	
		if (makedir) {
			makeScratchDirectory(testScratchDirectoryName);
		}
	
		
		return testScratchDirectoryName;
	}

	/**
	 * Name of property used pointing to the top of the hierarchy of large test files used in GDA tests.
	 * Set using vmarg e.g. -DGDALargeTestFilesLocation=/scratch/largetestfiles/.
	 * Note that the value should end with a File.separatorChar so simply concatenate the result with
	 * the filename required to get the full path.
	 */
	public final static String GDA_LARGE_TEST_FILES_LOCATION = "GDALargeTestFilesLocation";

	/**
	 * @return String from the {@link #GDA_LARGE_TEST_FILES_LOCATION} property.
	 * For running tests within the Eclipse IDE:
	 * <ol>
	 *   <li>set GDALargeTestFiles_loc in Windows --> Preferences --> Run/Debug --> String Substitution
	 *   <li>set "-DGDALargeTestFilesLocation=${GDALargeTestFiles_loc}" in the test launcher's VM Arguments section
	 *  </ol>
	 * @throws AssertionError if the path is not defined, does not exist or is not readable
	 */
	public static String getGDALargeTestFilesLocation() throws AssertionError {
		String path = System.getProperty(GDA_LARGE_TEST_FILES_LOCATION);
		if (path ==  null) {
			throw new AssertionError("The Java property " + GDA_LARGE_TEST_FILES_LOCATION + " has not been defined");
		}
		File file = new File(path);
		if (!file.isDirectory()) {
			throw new AssertionError("The Java property  " + GDA_LARGE_TEST_FILES_LOCATION + "'" + path + "' does not exist or is not a directory");
		}

		return path.endsWith(File.separator) ? path : path + File.separator;
	}
}
