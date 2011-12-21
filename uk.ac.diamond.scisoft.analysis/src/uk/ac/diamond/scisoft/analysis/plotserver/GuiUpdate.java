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
