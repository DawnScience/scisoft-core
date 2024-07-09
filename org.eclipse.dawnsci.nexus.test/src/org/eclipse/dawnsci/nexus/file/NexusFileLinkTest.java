/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.nexus.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.SliceND;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class NexusFileLinkTest {
	static String testScratchDirectoryName;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(NexusFileLinkTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
		NexusTestUtils.setUpServices();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ServiceProvider.reset();
	}

	@Test
	public void testAuthority() throws Exception {
		// explore how URI is decomposed
		printURI(new URI("nxfile://top/file#/node"));
		printURI(new URI("nxfile:///top/file#/node"));
		printURI(new URI("nxfile://./top/file#/node"));
		printURI(new URI("nxfile:///./top/file#/node"));
	}

	void printURI(URI uri) {
		System.out.println("URI      : " + uri);
		System.out.println("Authority: " + uri.getAuthority());
		System.out.println("Path     : " + uri.getPath());
		String a = uri.getAuthority();
		String p = uri.getPath();
		if (a != null) {
			p = a + p;
		}
		System.out.println("Combined : " + p);
		System.out.println("Fragment : " + uri.getFragment());
		System.out.println("");
	}

	@Test
	public void testExternalLinks() throws Exception {
		Dataset a = DatasetFactory.createRange(FloatDataset.class, 10).reshape(2, 5);
		a.setName("value");

		String ename;
		String tname;
		NexusFile nf;
		GroupNode g;

		// create file where /e/f/g -> a so /e/f/g/b/c is available
		ename = createExternalFile(testScratchDirectoryName + "external1.nxs", a);
		tname = testScratchDirectoryName + "test1.nxs";
		nf = NexusTestUtils.createNexusFile(tname);
		nf.linkExternal(new URI("nxfile://./" + ename + "#/a"), "/e/f/g", true);
		nf.close();

		nf = NexusTestUtils.openNexusFileReadOnly(tname);
		g = nf.getGroup("/e/f/g", false);
		assertNotNull(g);
		assertTrue(g.containsAttribute("top"));
		assertEquals("world", g.getAttribute("top").getFirstElement());
		g = nf.getGroup(g, "b", null, false);
		g = nf.getGroup("/e/f/g/b", false);
		assertNotNull(g);
		g = nf.getGroup("/e/f/g/b/c", false);
		assertNotNull(g);
		assertTrue(g.containsDataNode("value"));
		assertEquals(a, g.getDataNode("value").getDataset().getSlice());
		nf.close();

		// create file where /e/f/g/a -> a so /e/f/g/a/b/c is available
		ename = createExternalFile(testScratchDirectoryName + "external2.nxs", a);
		tname = testScratchDirectoryName + "test2.nxs";
		nf = NexusTestUtils.createNexusFile(tname);
		nf.linkExternal(new URI("nxfile://./" + ename + "#/a"), "/e/f/g/", true);
		nf.close();

		nf = NexusTestUtils.openNexusFileReadOnly(tname);
		g = nf.getGroup("/e/f/g", false);
		assertNotNull(g);
		assertFalse(g.containsAttribute("top"));
		g = nf.getGroup(g, "a", null, false);
		assertNotNull(g);
		assertTrue(g.containsAttribute("top"));
		assertEquals("world", g.getAttribute("top").getFirstElement());
		g = nf.getGroup(g, "b", null, false);
		assertNotNull(g);

		g = nf.getGroup("/e/f/g/a/b", false);
		assertNotNull(g);
		g = nf.getGroup("/e/f/g/a/b/c", false);
		assertNotNull(g);
		assertTrue(g.containsDataNode("value"));
		assertEquals(a, g.getDataNode("value").getDataset().getSlice());
		nf.close();

		// create file where /e/f/d -> value so /e/f/d is available
		ename = createExternalFile(testScratchDirectoryName + "external3.nxs", a);
		createExternalFile(ename, a);
		tname = testScratchDirectoryName + "test3.nxs";
		nf = NexusTestUtils.createNexusFile(tname);
		nf.linkExternal(new URI("nxfile://./" + ename + "#/a/b/c/value"), "/e/f/d", false);
		nf.close();

		nf = NexusTestUtils.openNexusFileReadOnly(tname);
		g = nf.getGroup("/e/f", false);
		assertNotNull(g);
		assertTrue(g.containsDataNode("d"));
		assertEquals(a, g.getDataNode("d").getDataset().getSlice());
		assertEquals(a, nf.getData(g, "d").getDataset().getSlice());
		assertEquals(a, nf.getData("/e/f/d").getDataset().getSlice());
		nf.close();

		// create file where /e/f/g -> c so /e/f/g/value is available
		ename = createExternalFile(testScratchDirectoryName + "external4.nxs", a);
		createExternalFile(ename, a);
		tname = testScratchDirectoryName + "test4.nxs";
		nf = NexusTestUtils.createNexusFile(tname);
		nf.linkExternal(new URI("nxfile://./" + ename + "#/a/b/c/value"), "/e/f/g/", false);
		nf.close();

		nf = NexusTestUtils.openNexusFileReadOnly(tname);
		g = nf.getGroup("/e/f/g", false);
		assertNotNull(g);
		assertTrue(g.containsDataNode("value"));
		assertEquals(a, g.getDataNode("value").getDataset().getSlice());
		assertEquals(a, nf.getData(g, "value").getDataset().getSlice());
		assertEquals(a, nf.getData("/e/f/g/value").getDataset().getSlice());
		nf.close();
	}

	@Test
	public void testInternalLinks() throws Exception {
		String filePath = testScratchDirectoryName + "testInternalLinks.nxs";
		int dataSize = 5;
		Dataset dataset = DatasetFactory.createRange(dataSize);

		long oid = 1;
		try (NexusFile nf = NexusTestUtils.createNexusFile(filePath)) {
			GroupNode root = TreeFactory.createGroupNode(oid++);
			GroupNode g = TreeFactory.createGroupNode(oid++);
			root.addGroupNode("g", g);

			/*
			 *  - 'l' is a SymbolicNode pointing to 'a' that occurs before 'a' in insertion order
			 *  - 'a' is the original DataNode with a dataset directly containing the data
			 *  - 'b' is the same DataNode instance as 'a' (a 'hard-link')
			 *  - 'c' is another SymbolicLink to 'a' that occurs after 'a' in insertion order
			 *  
			 *  - 's' is a SymbolicNode pointing to 'x' that occurs before 'x' in insertion order
			 *  - 'x' is a DataNode containing an ILazyWriteableDataset that is written to after the datasets are created on disk
			 *  - 'y' is the same DataNode instance as 'x' (a 'hard-link')
			 *  - 'z' is another SymbolicLink to 'x' that occurs after 'x' in insertion order
			 *  
			 *   Nodes are processed in insertion order as GroupNodeImpl uses a LinkedHashMap.
			 */

			SymbolicNode l = TreeFactory.createSymbolicNode(oid++, (URI) null, null, "/g/a");
			g.addSymbolicNode("l", l);

			DataNode a = TreeFactory.createDataNode(oid++);
			a.setDataset(dataset);
			g.addDataNode("a", a); 

			g.addDataNode("b", a); // same DataNode as 'a'

			SymbolicNode c = TreeFactory.createSymbolicNode(oid++, (URI) null, null, "/g/a");
			g.addSymbolicNode("c", c);

			SymbolicNode s = TreeFactory.createSymbolicNode(oid++, (URI) null, null, "/g/x");
			g.addSymbolicNode("s", s);

			int[] shape = { 0 };
			int[] maxShape = { ILazyWriteableDataset.UNLIMITED };
			ILazyWriteableDataset lazy = new LazyWriteableDataset("x", Double.class, shape, maxShape, null, null);

			DataNode x = TreeFactory.createDataNode(oid++);
			x.setDataset(lazy);
			g.addDataNode("x", x);

			g.addDataNode("y", x); // same DataNode as 'x'

			SymbolicNode z = TreeFactory.createSymbolicNode(oid++, (URI) null, null, "/g/x");
			g.addSymbolicNode("z", z);

			nf.addNode("/", root); // save the tree in one go
			nf.flush();

			// write to the lazy writeable dataset
			for (int i = 0; i < dataSize; i++) {
				int[] startStop = new int[] { i };
				IDataset dataToWrite = DatasetFactory.createFromObject(dataset.getDouble(i));
				SliceND slice = SliceND.createSlice(lazy, startStop, startStop);
				lazy.setSlice(null, dataToWrite, slice);
			}

			nf.flush();
		}

		try (NexusFile nf = NexusTestUtils.openNexusFileReadOnly(filePath)) {
			GroupNode g = nf.getGroup("/g", false);
			assertNotNull(g);

			Set<String> dataNodeNames = new LinkedHashSet<>(List.of("l", "a", "b", "c", "s", "x", "y", "z"));
			assertEquals(dataNodeNames, g.getDataNodeNames());

			for (String dataNodeName : dataNodeNames) {
				DataNode dataNode = nf.getData("/g/" + dataNodeName);
				assertNotNull(dataNode);
				assertEquals(dataset, dataNode.getDataset().getSlice());
			}
		}
	}

	// need to create a new external file as NAPI does not seem to cope well if an external file
	// is used in several files
	static boolean WORKAROUND_NAPI = true;
	private String createExternalFile(String ename, Dataset d) throws NexusException {
		if (!WORKAROUND_NAPI) {
			ename = "test-scratch/external.nxs";
			if (new File(ename).exists())
				return ename;
		}

		NexusFile nf = NexusTestUtils.createNexusFile(ename);
		GroupNode g = nf.getGroup("/a", true);
		Dataset a = DatasetFactory.createFromObject("world");
		a.setName("top");
		nf.addAttribute(g, nf.createAttribute(a));
		g = nf.getGroup("/a/b/c", true);
		a = DatasetFactory.createFromObject("world");
		a.setName("bottom");
		nf.addAttribute(g, nf.createAttribute(a));
		nf.createData(g, d);
		nf.close();

		return ename;
	}
}
