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
import java.nio.file.Files;
import java.util.Map;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IntegerDataset;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers.DatasetHelper;

/**
 * Test setting temporary file location properly affects {@link DatasetHelper}
 */
public class TempLocationTest {

	@Test
	public void testTempLocation() throws IOException {
		IRootFlattener root = new RootFlattener();

		File systemTemp = new File(System.getProperty("java.io.tmpdir"));
		
		// make sure by default the file ends up in the system temp directory
		Assert.assertEquals(systemTemp, flattenDataSetAndReturnContainingFolder(root));

		// create a temporary temporary directory to put temp files in
		File newTempDir = Files.createTempDirectory("flattening-test-").toFile();
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
		Object flatten = root.flatten(DatasetFactory.createRange(IntegerDataset.class, 100));
		// check the internal form of Dataset to see where the file is
		@SuppressWarnings("unchecked")
		Map<String, Object> flatAD = (Map<String, Object>) flatten;
		File loc = new File((String) flatAD.get(DatasetHelper.FILENAME));
		Assert.assertTrue(loc.exists());
		if (!loc.delete())
			throw new IOException("Failed to delete temporary file");

		File tempDir = loc.getParentFile();
		return tempDir;
	}
}
