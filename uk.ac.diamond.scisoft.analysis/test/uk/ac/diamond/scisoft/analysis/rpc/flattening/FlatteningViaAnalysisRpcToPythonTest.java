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

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;

public class FlatteningViaAnalysisRpcToPythonTest extends FlatteningViaAnalysisRpcToPythonTestAbstract {

	@Override
	protected Object doActualFlattenAndUnflatten(Object inObj) {
		checkPythonState();

		try {
			return client.request("loopback", new Object[] { inObj });
		} catch (AnalysisRpcException e) {
			if (e.getCause().getClass() == Exception.class)
				return e.getCause();
			throw new RuntimeException(e);
		}

	}

}
