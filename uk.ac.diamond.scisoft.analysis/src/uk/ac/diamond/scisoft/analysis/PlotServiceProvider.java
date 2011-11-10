/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis;

import java.lang.reflect.Method;

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
	private static final String CORBAPLOTSERVER = "uk.ac.diamond.scisoft.analysis.plotserver.PlotServerBase";
	private static PlotService plotService = null;

	public static PlotService getPlotService() {
		return getPlotService(null);
	}

	/**
	 * Get a PlotService that can be used to do Plotting. It first tries to get the CORBArized PlotService if it fails
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
				Class<?> returnClass = Class.forName(CORBAPLOTSERVER);
				Method member = returnClass.getMethod("getPlotServer", new Class<?>[] {});
				plotService = (PlotServer) member.invoke(null, new Object[] {});
				if (plotService == null)
					throw new NullPointerException("Did not really work did it?");
				logger.info("Using PlotServerBase CORBA");
			} catch (Exception e) {
				try {
					plotService = (PlotService) RMIServerProvider.getInstance().lookup(hostname,
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
