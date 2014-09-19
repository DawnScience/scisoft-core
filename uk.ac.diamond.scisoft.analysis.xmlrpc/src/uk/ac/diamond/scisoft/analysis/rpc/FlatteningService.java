/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.RootFlattener;

public class FlatteningService {
	private static IRootFlattener instance = new RootFlattener();

	/**
	 * Get Root Flattener used by Analysis RPC
	 * 
	 * @return instance
	 */
	public static IRootFlattener getFlattener() {
		return instance;
	}

}
