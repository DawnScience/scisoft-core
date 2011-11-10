/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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
