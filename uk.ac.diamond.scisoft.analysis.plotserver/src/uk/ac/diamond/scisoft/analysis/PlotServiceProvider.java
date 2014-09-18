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

package uk.ac.diamond.scisoft.analysis;

import org.eclipse.dawnsci.analysis.api.RMIClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.plotserver.RMIPlotServer;

/**
 * Use the PlotServiceProvider to obtain a handle to the PlotServer when the primary purpose is to send data to the plot
 * server, such as when accessed from Jython over RMI or via any of the plotting methods in {@link SDAPlotter}
 * <p>
 * If you want the "real" PlotServer, so you can add IObservers to it, use {@link PlotServerProvider}
 */
public class PlotServiceProvider {

	private static final Logger logger = LoggerFactory.getLogger(PlotServiceProvider.class);
	private static PlotService plotService = null;

	public static PlotService getPlotService() {
		return getPlotService(null);
	}

	/**
	 * Get a PlotService that can be used to do Plotting. It first tries to get the existing PlotServer. If it fails
	 * it will try to get the Java RMI plotService
	 * <p>
	 * The returned PlotService should not be held, but refetched each time it is used to allow the port to be changed.
	 * 
	 * @param hostname
	 *            host to connect to, or <code>null</code> for local host
	 */
	public static PlotService getPlotService(String hostname) {
		if (plotService == null) {
			try {
				plotService=PlotServerProvider.getExistingPlotServer();
				if (plotService == null)
					throw new NullPointerException("No registered plotserver");
				logger.info("Using registered plotserver");
			} catch (Exception e) {
				try {
					plotService = (PlotService) RMIClientProvider.getInstance().lookup(hostname,
							RMIPlotServer.RMI_SERVICE_NAME);
					logger.info("Using RMIPlotService");
				} catch (Exception e1) {
					logger.info("Couldn't get any PlotService ", e1);
				}
			}
		}
		return plotService;
	}

	/**
	 * Override automatically calculated plot service with a known good plot service, or to <code>null</code> to refetch
	 * plot server (useful after changing the port number).
	 * 
	 * @param service
	 */
	public static void setPlotService(PlotService service) {
		plotService = service;
	}

}
