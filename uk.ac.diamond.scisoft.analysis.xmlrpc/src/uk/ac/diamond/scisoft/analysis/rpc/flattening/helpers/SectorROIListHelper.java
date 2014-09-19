/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROIList;

public class SectorROIListHelper extends ROIListHelper<SectorROIList, SectorROI> {

	public SectorROIListHelper() {
		super(SectorROIList.class);
	}

	@Override
	public SectorROIList newList() {
		return new SectorROIList();
	}
}
