/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Reshapeable;
import org.eclipse.dawnsci.analysis.api.metadata.Sliceable;

public class AxesMetadataImpl implements AxesMetadata {

	@Reshapeable
	@Sliceable
	List<ILazyDataset>[] allAxes;

	@SuppressWarnings("unchecked")
	public AxesMetadataImpl(int rank) {
		allAxes = new List[rank];
	}

	@SuppressWarnings("unchecked")
	public AxesMetadataImpl(AxesMetadataImpl axesMetadataImpl) {
		int r = axesMetadataImpl.allAxes.length;
		allAxes = new List[r];
		for (int i = 0; i < r; i++) {
			List<ILazyDataset> ol = axesMetadataImpl.allAxes[i];
			if (ol == null)
				continue;
			List<ILazyDataset> list = new ArrayList<>();
			for (ILazyDataset l : ol) {
				list.add(l == null ? null : l.getSliceView());
			}
			allAxes[i] = list;
		}
	}

	public void setAxis(int axisDim, ILazyDataset[] axisData) {
		ArrayList<ILazyDataset> axisList = new ArrayList<ILazyDataset>(0);
		for (int i = 0; i < axisData.length; i++) {
			axisList.add(axisData[i]);
		}
		allAxes[axisDim] = axisList;
	}

	@Override
	public ILazyDataset[] getAxes() {
		ILazyDataset[] result = new ILazyDataset[allAxes.length];
		for (int i = 0; i < result.length; i++) {
			ILazyDataset[] ax = getAxis(i);
			if (ax != null) result[i] = ax[0];
		}
		return result;
	}

	@Override
	public ILazyDataset[] getAxis(int axisDim) {
		if (allAxes[axisDim] == null)
			return null;
		return allAxes[axisDim].toArray(new ILazyDataset[0]);
	}

	@Override
	public AxesMetadata clone() {
		return new AxesMetadataImpl(this);
	}

	/**
	 * Add axis data to given dimension
	 * @param axisData dataset for axis
	 * @param axisDim dimension (n.b. this is zero-based)
	 */
	public void addAxis(ILazyDataset axisData, int axisDim) {
		if (allAxes[axisDim] == null) {
			allAxes[axisDim] = new ArrayList<ILazyDataset>();
		}
		allAxes[axisDim].add(axisData);
	}

}
