/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
