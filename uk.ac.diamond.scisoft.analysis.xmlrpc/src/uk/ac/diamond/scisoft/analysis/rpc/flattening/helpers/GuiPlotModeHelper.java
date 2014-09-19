/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class GuiPlotModeHelper extends SortOfEnumHelper<GuiPlotMode> {

	public GuiPlotModeHelper() {
		super(GuiPlotMode.class);
	}

	@Override
	public GuiPlotMode unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		return GuiPlotMode.valueOf(inMap.get(CONTENT).toString());
	}

}
