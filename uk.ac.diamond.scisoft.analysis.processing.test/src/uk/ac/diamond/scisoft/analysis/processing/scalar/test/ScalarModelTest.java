/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.scalar.test;

//Import from org.junit
import static org.junit.Assert.*;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarModel;


// Now the testing class
public class ScalarModelTest {

	// Let's create some static numbers that we will use for the testing
	private final double newModelValue = 10.0;
	private final double defaultModelValue = 1;
	
	// Then test the getValue method of the ScalarModel, usually this wouldn't be tested but we're being exhaustive here
	@Test
	public void testGetValue() {
		// Create a new ScalarModel object
		ScalarModel scalarModel = new ScalarModel();
		
		// Before checking the result
		assertTrue("ScalarModel did not return the expected default value", defaultModelValue == scalarModel.getValue());
	}

	// Finally, test the setValue method of the ScalarModel, usually this wouldn't be tested but we're being exhaustive here
	@Test
	public void testSetValue() {
		// Create a new ScalarModel object
		ScalarModel scalarModel = new ScalarModel();
		
		// Set a new value for the internally held value
		scalarModel.setValue(newModelValue);
	
		// Before checking it
		assertTrue("ScalarModel did not set and then return the expected value", newModelValue == scalarModel.getValue());
	}
}

