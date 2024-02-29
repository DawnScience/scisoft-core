/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.nexus;

import static org.eclipse.dawnsci.nexus.test.utilities.NexusAssert.assertNexusTreesEqual;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.osgi.services.ServiceProvider;

public abstract class AbstractNexusFileTestBase {

	protected static String testScratchDirectoryName;

	protected String filePath;

	@BeforeClass
	public static void setUpClass(){
		NexusTestUtils.setUpServices();
	}

	@AfterClass
	public static void tearDownClass() {
		ServiceProvider.reset();
	}

	@Before
	public void setUp() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(getClass().getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
		filePath = testScratchDirectoryName + getFilename();
	}

	protected abstract String getFilename();

	protected abstract NXroot createNXroot();

	private TreeFile createNexusTree() {
		final TreeFile treeFile = NexusNodeFactory.createTreeFile(filePath);
		final NXroot root = createNXroot();
		treeFile.setGroupNode(root);

		return treeFile;
	}
	
	@Test
	public void testNexusFile() throws Exception {
		TreeFile createdNexusTree = createNexusTree();
		NexusTestUtils.saveNexusFile(createdNexusTree);
		TreeFile loadedNexusTree = NexusTestUtils.loadNexusFile(filePath, true);

		checkNexusFile(createdNexusTree, loadedNexusTree);
	}

	protected void checkNexusFile(TreeFile createdNexusTree, TreeFile loadedNexusTree) throws Exception {
		assertNexusTreesEqual(createdNexusTree, loadedNexusTree);
	}
}
