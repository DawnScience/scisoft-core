/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;

@Atomic
public class Junk2Dto1DNullOperation extends Junk2Dto1DOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor.Junk2Dto1DOperation";
	}
	
	public OperationData getTestData() {
		return null;
	}
}
