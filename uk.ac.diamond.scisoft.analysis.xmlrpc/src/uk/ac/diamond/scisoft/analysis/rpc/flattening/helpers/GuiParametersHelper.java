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

import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class GuiParametersHelper extends SortOfEnumHelper<GuiParameters> {

	public GuiParametersHelper() {
		super(GuiParameters.class);
	}

	@Override
	public GuiParameters unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		return GuiParameters.valueOf(inMap.get(CONTENT).toString());
	}

}
