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

import org.junit.Assert;
import org.junit.BeforeClass;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener.FlattenedFormChecker;

abstract public class ExplicitFlatteningTestAbstract extends FlatteningTestAbstract {

	private static FlattenedFormChecker flattenedFormChecker;

	@BeforeClass
	public static void createChecker() {
		flattenedFormChecker = new FlattenedFormChecker();
	}

	/**
	 * Make sure the flattened object contains only legal types for XML RPC transmission.
	 * 
	 * @param flat
	 */
	protected void checkFlattenedState(Object flat) {

		Assert.assertTrue("Unexpected object type: " + flat.getClass().toString(),
				flattenedFormChecker.legal(flat));
	}

	protected Object flattenAndCheck(Object toFlatten) {
		Object flat = flattener.flatten(toFlatten);
		checkFlattenedState(flat);
		return flat;
	}

	protected abstract Object doAdditionalWorkOnFlattendForm(Object flat);

	@Override
	protected Object doActualFlattenAndUnflatten(Object inObj) {
		Assert.assertTrue(flattener.canFlatten(inObj));
		Object flat = flattenAndCheck(inObj);

		flat = doAdditionalWorkOnFlattendForm(flat);

		Assert.assertTrue(flattener.canUnFlatten(flat));
		return flattener.unflatten(flat);
	}

}
