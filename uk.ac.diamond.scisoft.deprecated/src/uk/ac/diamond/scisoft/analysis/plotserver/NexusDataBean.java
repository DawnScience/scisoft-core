/*
 * Copyright © 2009 Diamond Light Source Ltd.
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
