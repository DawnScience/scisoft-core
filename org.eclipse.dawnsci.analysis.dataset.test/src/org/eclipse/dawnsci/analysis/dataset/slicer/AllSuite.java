/*-
 *******************************************************************************
 * Copyright (c) 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.dataset.slicer;

import org.eclipse.january.asserts.TestUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(TestUtils.VerboseSuite.class)
@SuiteClasses({ DynamicSliceViewIteratorTest.class,DynamicSliceNDIteratorTest.class, SliceInformationTest.class, SliceNDGeneratorTest.class })
public class AllSuite {

}
