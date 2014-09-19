/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;
import java.util.UUID;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class UUIDHelper extends SortOfEnumHelper<UUID> {

	public UUIDHelper() {
		super(UUID.class);
	}

	@Override
	public UUID unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		return UUID.fromString(inMap.get(CONTENT).toString());
	}

}
