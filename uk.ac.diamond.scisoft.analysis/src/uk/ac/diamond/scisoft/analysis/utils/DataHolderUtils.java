/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.january.dataset.ILazyDataset;

public class DataHolderUtils {
	
	/**
	 * Deals with loaders which provide data names of size > 1
	 * 
	 * 
	 * @param holder
	 * @return names
	 */
	public static final List<String> getSlicableNames(IDataHolder holder) {

		return getSlicableNames(holder, 2, String.class);
	}
	
	
	/**
	 * Deals with loaders which provide data names of size 1
	 * 
	 * 
	 * @param holder
	 * @param minSize - min size of any one dimension
	 * @param elementClasses list of dTypes which are not slicable data or now required in the list of names.
	 * @return names
	 */
	public static final List<String> getSlicableNames(IDataHolder holder, int minSize, Class<?>... elementClasses) {
				
		if (minSize<=0) minSize = 2;
		
		Collection<String> names = Arrays.asList(holder.getNames());
		if (names==null||names.isEmpty()) return null;
		
		List<Class<?>> restrictions = new ArrayList<Class<?>>(10);
		if (elementClasses!=null) for (Class<?> clazz : elementClasses) restrictions.add(clazz);
		
		boolean isH5 = holder.getTree() != null;
		
		List<String> ret   = new ArrayList<String>(names.size());
		for (String name : names) {
			
			// Some funny keys are put in data holders with the 
			// 'local_name' attribute in nexus.
			if (isH5 && !name.startsWith(Tree.ROOT)) continue;
			
			ILazyDataset ls = holder.getLazyDataset(name);
			if (ls == null) continue;
			if (restrictions.contains(ls.getElementClass())) continue;
			int[] shape = ls.getShape(); 
			if (shape==null) continue;
			
			boolean foundDims = false;
			if (minSize==1 && shape.length==0) {
				foundDims = true;
			} else {
				for (int i = 0; i < shape.length; i++) {
					if (shape[i]>=minSize) {
						foundDims = true;
						break;
					}
				}
			}
			if (!foundDims) continue;
			ret.add(name);
		}
		return ret;
	}

}
