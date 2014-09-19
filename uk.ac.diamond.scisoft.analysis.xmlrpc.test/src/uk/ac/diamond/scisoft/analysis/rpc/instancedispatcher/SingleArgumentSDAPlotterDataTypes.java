/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

/**
 * Tests for all the currently supported (i.e. flattenable) data types
 * used in SDAPlotter (apart from primitives and arrays of primitives tested elsewhere)
 */
public class SingleArgumentSDAPlotterDataTypes {
	public Class<GuiBean> call(GuiBean param) {
		return GuiBean.class;
	}
	public Class<GuiPlotMode> call(GuiPlotMode param) {
		return GuiPlotMode.class;
	}
	public Class<IDataset> call(IDataset param) {
		return IDataset.class;
	}
	public Class<IDataset[]> call(IDataset[] param) {
		return IDataset[].class;
	}
	public Class<String[]> call(String[] param) {
		return String[].class;
	}
	public Class<String> call(String param) {
		return String.class;
	}

}
