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

import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;

public class SDAPlotInformer {

	private static final String IMAGE_EXPLORER_DIRECTORY = ".";
	private static final String IMAGE_EXPLORER_VIEW = "ImageExplorer View";
	private static final String IMAGE_EXPLORER_HOST = "localhost";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("SDA Plot Informer called");
		String directoryToMonitor = IMAGE_EXPLORER_DIRECTORY;
		try {
			directoryToMonitor = args[0];
		} catch (Exception e) {
			System.out.println("No Direcotry Specified");
		}
		
		System.out.println("Directory to Monitor is " + directoryToMonitor);
		
		
		String viewToUpdate = IMAGE_EXPLORER_VIEW;
		try {
			viewToUpdate = args[1];
		} catch (Exception e) {
			System.out.println("No Plot View Specified");
		}
		
		System.out.println("View to update is " + viewToUpdate);
		
		
		String hostLocation = IMAGE_EXPLORER_HOST;
		try {
			hostLocation = args[2];
		} catch (Exception e) {
			System.out.println("No Host Location defined");
		}
		
		System.out.println("Host to update is " + hostLocation);
		
		PlotService plotServer = PlotServiceProvider.getPlotService(hostLocation);
		
		if (plotServer != null) {		
			
			GuiBean guiBean = new GuiBean();
			guiBean.put(GuiParameters.IMAGEGRIDLIVEVIEW, directoryToMonitor);
			try {
				plotServer.updateGui(viewToUpdate, guiBean);
			} catch (Exception e) {
				System.err.println("Cannot communicate with the PlotServer");
				return;
			}
	
		}
		System.out.println("Update provided");
		return;

	}

}
