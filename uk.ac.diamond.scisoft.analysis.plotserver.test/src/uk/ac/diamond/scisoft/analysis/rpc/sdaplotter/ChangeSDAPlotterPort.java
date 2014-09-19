/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;
import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.MockSDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcGenericInstanceDispatcher;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * This test is to make sure we can change the analysis rpc port that scisoftpy connects to.
 * <p>
 * The server port cannot be changed without restarting the JVM, so that requires a manual test, this test verifies that
 * the scisoftpy change does take effect. Therefore we set up two servers on different ports both pretending to be
 * "normal" SDAPlotters and make sure we can switch between them.
 */
public class ChangeSDAPlotterPort extends SDAPlotterTestsUsingLoopbackTestAbstract {

	class MyMockPlotter extends MockSDAPlotter {
		private boolean[] flag;
		public MyMockPlotter(final boolean[] status) {
			flag = status;
		}

		@Override
		public void plot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String[] yLabels, String[] xAxisNames,
				String[] yAxisNames) throws Exception {
			flag[0] = true;
		}

		@Override
		public void clearPlot(String plotName) throws Exception {
		}
	}

	@Test
	public void testChangePort() throws Exception {
		final boolean[] receivedAtDefaultHandler = new boolean[1];
		final boolean[] receivedAtAlternatePortHandler = new boolean[1];
		
		// Set a handler that we can detect we arrived at ok on the default port
		registerHandler(new MyMockPlotter(receivedAtDefaultHandler));	

		// Set an alternate handler on a different port
		AnalysisRpcServer altServer = new AnalysisRpcServer(9876); 
		altServer.start();
		IAnalysisRpcHandler dispatcher = new AnalysisRpcGenericInstanceDispatcher(ISDAPlotter.class, new MyMockPlotter(receivedAtAlternatePortHandler));
		altServer.addHandler(SDAPlotter.class.getSimpleName(), dispatcher);
	
		
		// make sure by default we arrive at the default port (the one provided in AnalysisRpcServerProvider)
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", null, null, new IDataset[] {DatasetFactory.createRange(100, Dataset.INT)},
				null, null, null);
		Assert.assertTrue(receivedAtDefaultHandler[0]);
		Assert.assertFalse(receivedAtAlternatePortHandler[0]);
		
		// change port to something else
		Assert.assertTrue(AnalysisRpcServerProvider.getInstance().getPort() != 9876); // test is invalid if already on 9876!
		redirectPlotter.setRemotePortRpc(9876);
		
		// make sure we arrive at the alternate handler
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", null, null, new IDataset[] {DatasetFactory.createRange(100, Dataset.INT)},
				null, null, null);
		Assert.assertFalse(receivedAtDefaultHandler[0]);
		Assert.assertTrue(receivedAtAlternatePortHandler[0]);
		

		// restore default by setting to 0
		redirectPlotter.setRemotePortRpc(0);
		
		// make sure by default we arrive at the default port (the one provided in AnalysisRpcServerProvider)
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", null, null, new IDataset[] {DatasetFactory.createRange(100, Dataset.INT)},
				null, null, null);
		Assert.assertTrue(receivedAtDefaultHandler[0]);
		Assert.assertFalse(receivedAtAlternatePortHandler[0]);
		

	}

}
