/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.junit.Test;
import org.python.core.PyFloat;
import org.python.core.PyObjectDerived;
import org.python.util.PythonInterpreter;

import uk.ac.diamond.scisoft.python.JythonInterpreterUtils;

public class JythonInterpreterUtilsTest {

	
	/**
	 * This test is used to ensure that a Jython interpreter
	 * importing the scisoft python files can be created.
	 * 
	 * It checks that the .py files created can be instantiated
	 * without errors. The method JythonInterpreterUtils.getInterpreter()
	 * is used within the workflow tool to run jython nodes directly
	 * in the same VM.
	 * 
	 */
	@Test
	public void test() throws Exception {
		
		PythonInterpreter interpreter = JythonInterpreterUtils.getInterpreter();
		if (interpreter == null) throw new Exception("No Jython interpreter found!");

		interpreter.set("fred", 10d);
		interpreter.exec("fred = dnp.Sciwrap(fred)");
        
		final Object fred = interpreter.get("fred");
		if (fred==null) throw new Exception("Cannot read object 'fred'!");
		if (!(fred instanceof PyFloat)) throw new Exception("Fred should be a float!");
		
		final Dataset set = DatasetFactory.createRange(FloatDataset.class, 0, 100, 1);
		interpreter.set("x", set);
		interpreter.exec("x = dnp.Sciwrap(x)");
		final Object x = interpreter.get("x");
		if (x==null) throw new Exception("Cannot read object 'x'!");
		if (!(x instanceof PyObjectDerived)) throw new Exception("x should be a PyObjectDerived!");
		
		interpreter.exec("sum = x.sum()");
		final Object sumX = interpreter.get("sum");
		if (sumX==null) throw new Exception("Cannot read object 'sumX'!");
		if (!(sumX instanceof PyFloat)) throw new Exception("sumX should be a float!");
        if (((PyFloat)sumX).getValue()!=4950.0d) throw new Exception("sumX should be 4950.0!");
	}
}
