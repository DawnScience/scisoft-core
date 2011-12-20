/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
