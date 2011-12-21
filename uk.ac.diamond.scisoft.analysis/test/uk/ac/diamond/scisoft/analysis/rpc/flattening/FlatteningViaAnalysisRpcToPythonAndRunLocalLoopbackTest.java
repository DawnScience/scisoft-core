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

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;

public class FlatteningViaAnalysisRpcToPythonAndRunLocalLoopbackTest extends FlatteningViaAnalysisRpcToPythonTestAbstract {

	@Override
	protected Object doActualFlattenAndUnflatten(Object inObj) {
		checkPythonState();

		try {
			return client.request("loopback_after_local", new Object[] { inObj });
		} catch (AnalysisRpcException e) {
			if (e.getCause().getClass() == Exception.class)
				return e.getCause();
			throw new RuntimeException(e);
		}
	}

}
