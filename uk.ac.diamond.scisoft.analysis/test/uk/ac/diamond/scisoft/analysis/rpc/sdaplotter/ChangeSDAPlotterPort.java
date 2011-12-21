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

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;
import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.MockSDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
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

	@Test
	public void testChangePort() throws Exception {
		final boolean[] receivedAtDefaultHandler = new boolean[1];
		final boolean[] receivedAtAlternatePortHandler = new boolean[1];
		
		// Set a handler that we can detect we arrived at ok on the default port
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset yAxis) throws Exception {
				receivedAtDefaultHandler[0] = true;
			}
		});
		
		// Set an alternate handler on a different port
		AnalysisRpcServer altServer = new AnalysisRpcServer(9876); 
		altServer.start();
		IAnalysisRpcHandler dispatcher = new AnalysisRpcGenericInstanceDispatcher(ISDAPlotter.class, new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset yAxis) throws Exception {
				receivedAtAlternatePortHandler[0] = true;
			}
		});
		altServer.addHandler(SDAPlotter.class.getSimpleName(), dispatcher);
	
		
		// make sure by default we arrive at the default port (the one provided in AnalysisRpcServerProvider)
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", AbstractDataset.arange(100, AbstractDataset.INT));
		Assert.assertTrue(receivedAtDefaultHandler[0]);
		Assert.assertFalse(receivedAtAlternatePortHandler[0]);
		
		// change port to something else
		Assert.assertTrue(AnalysisRpcServerProvider.getInstance().getPort() != 9876); // test is invalid if already on 9876!
		redirectPlotter.setRemotePortRpc(9876);
		
		// make sure we arrive at the alternate handler
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", AbstractDataset.arange(100, AbstractDataset.INT));
		Assert.assertFalse(receivedAtDefaultHandler[0]);
		Assert.assertTrue(receivedAtAlternatePortHandler[0]);
		

		// restore default by setting to 0
		redirectPlotter.setRemotePortRpc(0);
		
		// make sure by default we arrive at the default port (the one provided in AnalysisRpcServerProvider)
		receivedAtAlternatePortHandler[0] = receivedAtDefaultHandler[0] = false;
		redirectPlotter.plot("Plot 1", AbstractDataset.arange(100, AbstractDataset.INT));
		Assert.assertTrue(receivedAtDefaultHandler[0]);
		Assert.assertFalse(receivedAtAlternatePortHandler[0]);
		

	}

}
