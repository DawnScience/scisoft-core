/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.python;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.util.PythonInterpreter;

public class JythonInterpreterUtilsPluginTest {
	private PythonInterpreter jyTestInt;
	
	@Before
	public void setup() {
		jyTestInt = null;
		System.setProperty("run.in.eclipse", "true");
	}
	
	@Test
	public void basicInterpreterShouldStartAndExecutePython() {
		
		try{
			jyTestInt = JythonInterpreterUtils.getBasicInterpreter(null);
		} catch (Exception e) {
			fail("Starting Jython interpreter failed!");
		}
		
		// Have we *really* got a python interpreter???
		assertTrue("jyTestInt not a PythonInterpreter", jyTestInt != null);

		// Call some very basic python
		jyTestInt.exec("import os");
		jyTestInt.exec("myOs = str(os.name)");
		PyString jyVar = (PyString) jyTestInt.get(new String("myOs"));
		String jyVarStr = jyVar.toString();
		assertEquals(new String("java"), jyVarStr);

		jyTestInt.exec("import sys");
		jyTestInt.exec("myExec = str(sys.executable)");
		PyString jyVar2 = (PyString) jyTestInt.get(new String("myExec"));
		String jyVar2Str = jyVar2.toString();
		if (!jyVar2Str.endsWith("/jython.jar")) {
			fail("Executable name wrong or not set.");
		}

		// And a quick maths check
		jyTestInt.exec("import math");
		jyTestInt.exec("myVal = round(math.cos(math.radians(60)), 5)");
		PyFloat jyVar3 = (PyFloat) jyTestInt.get(new String("myVal"));
		assertEquals(new PyFloat(0.5), jyVar3);
	}
	
	@Test
	public void interpreterShouldStartWithSciSoftPyLibs() {
		try{
			jyTestInt = JythonInterpreterUtils.getScisoftpyInterpreter();
		} catch (Exception e) {
			fail("Starting Jython interpreter failed!");
		}
		
		// Have we *really* got a python interpreter???
		assertTrue("jyTestInt not a PythonInterpreter", jyTestInt != null);

		// Do some numpy type stuff to see that everything is behaving.
		// Check scisoftpy libs are loaded and we can do stuff
		jyTestInt.exec("myArray = dnp.zeros((4,5))");
		jyTestInt.exec("myShape = myArray.shape");
		PyTuple jyVar = (PyTuple) jyTestInt.get(new String("myShape"));
		PyTuple expectedShape = new PyTuple(new PyInteger[] { new PyInteger(4), new PyInteger(5) });
		assertEquals(expectedShape, jyVar);

		// Make a new array with some fairly simple filler
		jyTestInt.exec("myArray = dnp.array([[1,2,3,4,5],[6,7,8,9,10],[10,9,8,7,6],[5,4,3,2,1]])");
		jyTestInt.exec("myShape2 = myArray.shape");
		PyTuple jyVar2 = (PyTuple) jyTestInt.get(new String("myShape2"));
		assertEquals("myArray has wrong shape", expectedShape, jyVar2);
		jyTestInt.exec("rowSum = sum(myArray[1][...])");
		PyInteger jyVar3 = (PyInteger) jyTestInt.get(new String("rowSum"));
		assertEquals("Sum of row in myArray incorrect", new PyInteger(40), jyVar3);

		jyTestInt.exec("myArray2 = myArray.transpose()");
		jyTestInt.exec("myShape4 = myArray.shape");
		PyTuple jyVar4 = (PyTuple) jyTestInt.get(new String("myShape4"));
		assertEquals("myArray2 has wrong shape", expectedShape, jyVar4);
		jyTestInt.exec("rowSum5 = sum(myArray2[1][...])");
		PyInteger jyVar5 = (PyInteger) jyTestInt.get(new String("rowSum5"));
		assertEquals("Sum of row in myArray2 incorrect", new PyInteger(22), jyVar5);

	}
	
	@Test
	public void interpreterShouldLoadDLSLibs() {
		try{
			jyTestInt = JythonInterpreterUtils.getFullInterpreter(JythonInterpreterUtils.class.getClassLoader());
		} catch (Exception e) {
			fail("Starting Jython interpreter failed!");
		}
	}
}
