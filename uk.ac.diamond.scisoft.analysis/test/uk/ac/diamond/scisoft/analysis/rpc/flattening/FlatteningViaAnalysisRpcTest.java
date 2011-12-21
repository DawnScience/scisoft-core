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
