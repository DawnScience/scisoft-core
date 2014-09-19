/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;
import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.PlotService;
import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotterImpl;
import uk.ac.diamond.scisoft.analysis.SDAPlotterTestAbstract;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcGenericInstanceDispatcher;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * We are going to test little snippets of python that perform the same plotting over AnalysisRpc as we do locally.
 * Therefore the tests in {@link SDAPlotterTestAbstract} call our {@link ReDirectOverRpcPlotterImpl} which bundles up
 * the call and has Python call us back through the normal route. We intercept the call on the return by overriding the
 * normal SDAPlotterImpl that {@link SDAPlotter} directs to to one that uses out mock plot server.
 * <p>
 */
public class SDAPlotterViaRpcTest extends SDAPlotterTestAbstract {
	private static PythonRunInfo pythonRunInfo;
	private static IAnalysisRpcHandler savedHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SDAPlotterImpl sdaPlotterRecvSide = new SDAPlotterImpl() {
			@Override
			protected PlotService getPlotService() {
				return testPlotServer;
			}
		};
		
		IAnalysisRpcHandler dispatcher = new AnalysisRpcGenericInstanceDispatcher(ISDAPlotter.class,
				sdaPlotterRecvSide);
		savedHandler = AnalysisRpcServerProvider.getInstance().getHandler(SDAPlotter.class.getSimpleName());
		AnalysisRpcServerProvider.getInstance().addHandler(SDAPlotter.class.getSimpleName(), dispatcher);

		// Launch the AnalysisRpc server that receives our requests and sends them back to us
		AnalysisRpcServerProvider.getInstance().startServer();
		// sleep as possible fix for SCI-1893
		Thread.sleep(1000);
		String[] envp = new String[] {"SCISOFT_RPC_PORT=" + AnalysisRpcServerProvider.getInstance().getPort()};
		pythonRunInfo = PythonHelper
				.runPythonFileBackground("../uk.ac.diamond.scisoft.python/test/scisoftpy/loopback.py", new String[] {"../uk.ac.diamond.scisoft.python/src/"}, envp);
		// sleep as possible fix for SCI-1893
		Thread.sleep(1000);
		sdaPlotterImplUnderTest = new ReDirectOverRpcPlotterImpl();
		// sleep as possible fix for SCI-1893
		Thread.sleep(1000);
	}

	private void checkPythonState() {
		// Before and after each test make sure the server is still there
		if (pythonRunInfo != null && pythonRunInfo.hasTerminated()) {
			// It has disappeared, so dump the stdout and stderr
			pythonRunInfo.getStdout(true);
			throw new RuntimeException("Python script unexpectedly terminated");
		}
	}

	@Before
	public void checkBefore() {
		checkPythonState();
	}

	@After
	public void checkAfter() {
		checkPythonState();
	}

	@AfterClass
	public static void tearDownAfterClass() {

		// Stop the server making sure no unexpected output is there
		if (pythonRunInfo != null) {
			pythonRunInfo.terminate();
			pythonRunInfo.getStdout(true);
		}
		pythonRunInfo = null;
		
		// Restore normal handler
		try {
			AnalysisRpcServerProvider.getInstance().addHandler(SDAPlotter.class.getSimpleName(), savedHandler);
		} catch (AnalysisRpcException e) {
			throw new RuntimeException("Failed to restore handler", e);
		}
	}

}
