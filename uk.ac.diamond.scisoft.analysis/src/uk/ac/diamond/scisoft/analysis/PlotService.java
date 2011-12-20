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

package uk.ac.diamond.scisoft.analysis;

import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;

/**
 * Plot service provides a means to communicate with plotting clients
 */
public interface PlotService {

	/**
	 * This method should be called whenever some GUI information changes, this
	 * will then set the GUI's bean which contains all the state information, so
	 * that it can be obtained from other GUIs and interested parties. This also
	 * fires off a message to all observers to let them know there has been a
	 * change of the GUI data, and this information is also sent with the update
	 * 
	 * @param guiName
	 *            The name of the GUI which is being updated
	 * @param guiData
	 *            The data which specifies that GUI panel
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public void updateGui(String guiName, GuiBean guiData)
			throws Exception;

	/**
	 * This method should set the data that is to be plotted on the client of a
	 * specific name
	 * 
	 * @param guiName
	 *            The name of the GUI panel to plot to
	 * @param plotData
	 *            The data which requires plotting
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public void setData(String guiName, DataBean plotData) throws Exception;

	/**
	 * This method should report to all observers that there is some data
	 * available on the server for plotting. It just sends out the name of the
	 * panel for which additional data is available, and it is up to the specific
	 * observers as to whether they try to retrieve that data or not using the
	 * getPlotData method
	 * 
	 * @param guiName
	 * @throws Exception
	 */
	public void updateData(String guiName) throws Exception;

	/**
	 * This method allows for interested parties to get relevant GUI information
	 * about a particular GUI panel
	 * 
	 * @param guiName
	 *            The name of the GUI panel from which the data is to be
	 *            retrieved
	 * @return The data which specifies the requested GUI panels attributes
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public GuiBean getGuiState(String guiName) throws Exception;

	/**
	 * @return true if plot server is local (i.e. sees the same file system)
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public boolean isServerLocal() throws Exception;

	/**
	 * Return a list of all the names known to the plot server. That is any guiName
	 * that has a data or gui bean
	 * 
	 * @return the list of names
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public String[] getGuiNames() throws Exception;

	/**
	 * This method should return the plotData which is currently connected to a
	 * particular GUI
	 * 
	 * @param guiName
	 *            the name of the GUI which is requesting the data, or the GUI
	 *            name of the data which a third party is trying to look at
	 * @return The GUI data for the named GUI
	 * @throws Exception
	 *             If there is a problem with communication with the object.
	 */
	public DataBean getData(String guiName) throws Exception;
}
