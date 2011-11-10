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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

/**
 * Tests for all the currently supported (i.e. flattenable) data types
 * used in SDAPlotter (apart from primitives and arrays of primitives tested elsewhere)
 */
@SuppressWarnings("unused")
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
