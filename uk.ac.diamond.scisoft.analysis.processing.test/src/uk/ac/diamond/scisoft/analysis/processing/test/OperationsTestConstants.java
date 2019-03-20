/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;

import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class OperationsTestConstants {
	
	public static final String PROCESSED_RESULTS_PATH = Tree.ROOT + NexusFileExecutionVisitor.ENTRY
		    + Node.SEPARATOR + NexusFileExecutionVisitor.RESULTS_GROUP
		    + Node.SEPARATOR;
	
	public static final String DATA = NexusFileExecutionVisitor.DATA_NAME;
	public static final String  PROCESSED_RESULTS_DATA_PATH = PROCESSED_RESULTS_PATH + DATA;
	public static final String AXIS0 = "Axis_0";
	public static final String AXIS2 = "Axis_2";
	public static final String JUNK_AXIS1D = "Junk1Dax";
	
}
