/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

import gda.observable.IObservable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.plotserver.RMIPlotServer;
import uk.ac.diamond.scisoft.analysis.plotserver.SimplePlotServer;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcGenericInstanceDispatcher;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * Class that provides a PlotServer to the rest of the gui. The PlotServer instance is set by the plugin activator if
 * found as an OSGI registered service.
 * <p>
 * If you want to simply send plot data to the server, use {@link PlotServiceProvider} as using this class is intended
 * for use by the classes which actually do the plotting and need to be notified of changes via the {@link IObservable}
 * mechanism which is implemented by {@link PlotServer} but not {@link PlotService}
 */
public class PlotServerProvider {
	public static final String PLOT_SERVER_NAME = "plot_server";
	private static final Logger logger = LoggerFactory.getLogger(PlotServerProvider.class);
	static PlotServer plotServer;

	public synchronized static PlotServer getPlotServer() {
		if (plotServer == null) {
			logger.info("PlotServer instance not set - using local created PlotServer instead");
			try {
				plotServer = new RMIPlotServer();
			} catch (Exception e) {
				logger.info("RMIPlotserver can't be created using SimplePlotServer instead", e);
				plotServer = new SimplePlotServer();
			}

			// Start the Analysis RPC wrapper around the SDAPlotter
			// This only happens on SDA
			IAnalysisRpcHandler dispatcher = new AnalysisRpcGenericInstanceDispatcher(ISDAPlotter.class,
					SDAPlotterImpl.getDefaultInstance());
			AnalysisRpcServerProvider.getInstance().addHandler(SDAPlotter.class.getSimpleName(), dispatcher);

			// Because we are making a "real" PlotServer (as opposed to a proxy to one, such as over Corba)
			// set the PlotService to be the same server, otherwise the PlotService will attempt connection
			// via RMI
			PlotServiceProvider.setPlotService(plotServer);
		}
		return plotServer;
	}

	public static void setPlotServer(PlotServer plotServer) {
		PlotServerProvider.plotServer = plotServer;
		logger.info("setPlotServer called with plotServer " + plotServer);
	}
}
