/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import gda.data.nexus.tree.INexusTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This bean contains all the information required by a GUI to perform a plot,
 * including the data, as well as the axis information.
 */
public class NexusDataBean extends DataBean implements Serializable {

	private static final long serialVersionUID = -2033109932408452451L;

	private List<INexusTree> nexusTrees;
	
	/**
	 * Constructor to initialise all the collection objects
	 */
	public NexusDataBean(GuiPlotMode plotMode) {
		super(plotMode);
		nexusTrees = new ArrayList<INexusTree>();
	}
	
	public NexusDataBean() {
		nexusTrees = new ArrayList<INexusTree>();
	}
	

	/**
	 * @return a shallow copy of data bean
	 */
	@Override
	public NexusDataBean copy() {
		NexusDataBean bean = new NexusDataBean();
		bean.data.addAll(data);
		bean.axisData.putAll(axisData);
		bean.nexusTrees.addAll(nexusTrees);
		bean.hdf5Trees.addAll(hdf5Trees);
		bean.guiPlotMode = guiPlotMode;
		return bean;
	}
	
	/**
	 * Adds the provided NeXus tree to the bean, one element at a time
	 * 
	 * @param nexusTreeToAdd
	 */
	public void addNexusTree(INexusTree nexusTreeToAdd) {
		nexusTrees.add(nexusTreeToAdd);
	}

	/**
	 * 
	 * @param nexusTrees
	 */
	public void setNexusTrees(List<INexusTree> nexusTrees) {
		this.nexusTrees = nexusTrees;
	}

	/**
	 * gets all the nexusTrees as a collection
	 * 
	 * @return nexusTrees
	 */
	public List<INexusTree> getNexusTrees() {
		return nexusTrees;
	}

}
