/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

public class FlatteningViaAnalysisRpcTest extends FlatteningTestAbstract {
	private static AnalysisRpcServer server;
	private static AnalysisRpcClient client;

	@BeforeClass
	public static void start() throws AnalysisRpcException {
		server = new AnalysisRpcServer(8620);
		server.addHandler("loopback", new IAnalysisRpcHandler() {
			@Override
			public Object run(Object[] args) {
				return args[0];
			}
		});
		server.start();

		client = new AnalysisRpcClient(8620);
	}

	@AfterClass
	public static void stop() {
		if (server != null)
			server.shutdown();
		server = null;
		client = null;
	}

	@Override
	protected Object doActualFlattenAndUnflatten(Object inObj) {
		try {
			return client.request("loopback", new Object[] { inObj });
		} catch (AnalysisRpcException e) {
			if (e.getCause().getClass() == Exception.class)
				return e.getCause();
			throw new RuntimeException(e);
		}
	}
}
