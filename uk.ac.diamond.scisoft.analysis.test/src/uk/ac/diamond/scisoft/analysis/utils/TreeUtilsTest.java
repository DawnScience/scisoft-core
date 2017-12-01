/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Random;
import org.junit.Test;

public class TreeUtilsTest {

	@Test
	public void testRecursiveLoad() {
		
		GroupNode first = TreeFactory.createGroupNode(0);
		GroupNode second = TreeFactory.createGroupNode(0);
		GroupNode third = TreeFactory.createGroupNode(0);
		
		ILazyDataset lazyfirst = Random.lazyRand(new int[] {});
		ILazyDataset lazysecond = Random.lazyRand(new int[] {10});
		ILazyDataset lazythird = Random.lazyRand(new int[] {10,10});
		
		DataNode dfirst = TreeFactory.createDataNode(0);
		dfirst.setDataset(lazyfirst);
		DataNode dsecond = TreeFactory.createDataNode(0);
		dsecond.setDataset(lazysecond);
		DataNode dthird = TreeFactory.createDataNode(0);
		dthird.setDataset(lazythird);
		
		third.addDataNode("dthird", dthird);
		second.addGroupNode("third", third);
		second.addDataNode("dsecond", dsecond);
		first.addDataNode("dfirst", dfirst);
		first.addGroupNode("second", second);

		assertFalse(dfirst.getDataset() instanceof IDataset);
		
		TreeUtils.recursivelyLoadDataNodes(first);
		
		assertTrue(dfirst.getDataset() instanceof IDataset);
		assertTrue(dsecond.getDataset() instanceof IDataset);
		assertTrue(dthird.getDataset() instanceof IDataset);
	}

}
