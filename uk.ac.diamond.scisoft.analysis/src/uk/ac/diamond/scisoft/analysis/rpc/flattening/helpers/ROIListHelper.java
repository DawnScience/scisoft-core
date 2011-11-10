/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.ArrayList;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.roi.ROIBase;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class ROIListHelper<ListType extends ArrayList<ItemType>, ItemType extends ROIBase> extends
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