/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;

public class SDAPlotInformer {

	private static final String IMAGE_EXPLORER_DIRECTORY = ".";
	private static final String IMAGE_EXPLORER_VIEW = "Image Explorer";
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
