/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers.AbstractDatasetHelper;

/**
 * Test setting temporary file location properly affects {@link AbstractDatasetHelper}
 */
public class TempLocationTest {

	@Test
	public void testTempLocation() throws IOException {
		IRootFlattener root = new RootFlattener();

		File systemTemp = new File(System.getProperty("java.io.tmpdir"));
		
		// make sure by default the file ends up in the system temp directory
		Assert.assertEquals(systemTemp, flattenDataSetAndReturnContainingFolder(root));

		// create a temporary temporary directory to put temp files in
		File newTempDir = createTempDirectory();
		Assert.assertFalse(newTempDir.getCanonicalFile().equals(systemTemp.getCanonicalFile()));
		root.setTempLocation(newTempDir.toString());

		Assert.assertEquals(newTempDir, flattenDataSetAndReturnContainingFolder(root));
		
		// delete directory
		if (!newTempDir.delete())
			throw new IOException("Failed to delete temporary dir");

		// restore the default
		root.setTempLocation(null);
		Assert.assertEquals(systemTemp, flattenDataSetAndReturnContainingFolder(root));
	}

	
	/**
	 * Flatten a dataset and return the directory it was flattened to
	 */
	private File flattenDataSetAndReturnContainingFolder(IRootFlattener root) throws IOException {
		Object flatten = root.flatten(DatasetFactory.createRange(100, Dataset.INT));
		// check the internal form of Dataset to see where the file is
		@SuppressWarnings("unchecked")
		Map<String, Object> flatAD = (Map<String, Object>) flatten;
		File loc = new File((String) flatAD.get(AbstractDatasetHelper.FILENAME));
		Assert.assertTrue(loc.exists());
		if (!loc.delete())
			throw new IOException("Failed to delete temporary file");

		File tempDir = loc.getParentFile();
		return tempDir;
	}

	/**
	 * (the method used here to create the temp directory is potentially unsafe, but probably suitable for tests, see
	 * http://stackoverflow.com/questions/617414/create-a-temporary-directory-in-java)
	 * 
	 * @return the temp directory
	 * @throws IOException
	 */
	private File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("flattening-test-", null);

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		return (temp);
	}

}
