/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.roi.IROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class ROIListHelper<ListType extends ArrayList<ItemType>, ItemType extends IROI> extends
		MapFlatteningHelper<ListType> {

	public ROIListHelper(Class<ListType> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		ListType roiList = (ListType) obj;
		Map<String, Object> returnMap = createMap(getTypeCanonicalName());
		Object[] flattenedContents = new Object[roiList.size()];
		int i = 0;
		for (Object element : roiList) {
			ItemType thisROI = (ItemType) element;
			flattenedContents[i++] = rootFlattener.flatten(thisROI);
		}
		returnMap.put(CONTENT, flattenedContents);
		return returnMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListType unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		ListType outList = newList();

		Object[] flattenedContent = (Object[]) inMap.get(CONTENT);
		for (Object element : flattenedContent) {
			ItemType e = (ItemType) (rootFlattener.unflatten(element));
			outList.add(e);
		}
		return outList;
	}

	public abstract ListType newList();
}
