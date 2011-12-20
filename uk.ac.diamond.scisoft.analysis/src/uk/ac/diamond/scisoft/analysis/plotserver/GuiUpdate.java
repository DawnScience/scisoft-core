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

import java.io.Serializable;

/**
 * This class is used by the IObservable interface to pass information to all
 * the GUIs on a GUI update, it contains the name of the panel which is being
 * updated, along with all the information about the update in a GuiBean
 */
public class GuiUpdate implements Serializable {

	private String guiName;
	private GuiBean guiData;

	/**
	 * constructor to allow for simplistic filling
	 * 
	 * @param name
	 * @param data
	 */
	public GuiUpdate(String name, GuiBean data) {
		guiName = name;
		guiData = data;
	}

	/**
	 * @return Returns the guiName.
	 */
	public String getGuiName() {
		return guiName;
	}

	/**
	 * @param guiName
	 *            The guiName to set.
	 */
	public void setGuiName(String guiName) {
		this.guiName = guiName;
	}

	/**
	 * @return Returns the guiData.
	 */
	public GuiBean getGuiData() {
		return guiData;
	}

	/**
	 * @param guiData
	 *            The guiData to set.
	 */
	public void setGuiData(GuiBean guiData) {
		this.guiData = guiData;
	}

}
