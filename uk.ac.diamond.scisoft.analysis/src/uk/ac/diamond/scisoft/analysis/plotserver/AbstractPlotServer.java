/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.diamond.scisoft.analysis.PlotServer;

abstract public class AbstractPlotServer implements PlotServer {

	private Map<String, GuiBean> guiStore;
	private Map<String, DataBean> dataStore;

	public AbstractPlotServer() {
		super();
		guiStore = new HashMap<String, GuiBean>();
		dataStore = new HashMap<String, DataBean>();
	}

	@Override
	public DataBean getData(String guiName) throws Exception {
		return dataStore.get(guiName);
	}

	@Override
	public void setData(String guiName, DataBean data) throws Exception {
		dataStore.put(guiName, data);
	}

	@Override
	public GuiBean getGuiState(String guiName) throws Exception {
		return guiStore.get(guiName);
	}

	@Override
	public void updateGui(String guiName, GuiBean guiData) throws Exception {
		guiStore.put(guiName, guiData);
	}

	@Override
	public String[] getGuiNames() throws Exception {
		Set<String> names = new HashSet<String>();
		names.addAll(guiStore.keySet());
		names.addAll(dataStore.keySet());
		return names.toArray(new String[names.size()]);
	}
	
	/**
	 * Retrieve the Gui Store. Use with caution and understanding.
	 * @return guiStore
	 */
	protected Map<String, GuiBean> getGuiStore() {
		return guiStore;
	}

	/**
	 * Retrieve the Data Store. Use with caution and understanding.
	 * @return dataStore
	 */
	protected Map<String, DataBean> getDataStore() {
		return dataStore;
	}

}
