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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * One of three tests that demonstrate creating custom flatteners and unflatteners.
 * <ul>
 * <li> {@link AddHelperSimpleFlatteningTest} - Flattener for a simple class.
 * <li> {@link AddHelperSpecializedMapFlatteningTest} - Flattener for a class that is a specialisation of a class that
 * flattening is already supported for, in this example a specialised {@link Map}.
 * <li> {@link AddHelperSimpleWithInterfaceFlatteningTest} - Flattener for a simple class. Demonstrates use of
 * {@link IFlattens}.
 * </ul>
 */
public class AddHelperSimpleFlatteningTest {

	/**
	 * Dummy class that doesn't do anything, only exists to verify flattening and unflattening
	 */
	private static class AddHelperMockClass {
		public AddHelperMockClass(int value) {
			this.value = value;
		}

		private int value;

		public int getValue() {
			return value;
		}
	}

	private static class AddHelperIFlattener implements IFlattener<AddHelperMockClass> {

		private static final String VALUE = "value";

		@Override
		public Object flatten(Object obj, IRootFlattener root) {
			AddHelperMockClass myObj = (AddHelperMockClass) obj;
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put(IFlattener.TYPE_KEY, AddHelperMockClass.class.getCanonicalName());
			hashMap.put(VALUE, myObj.getValue());
			return hashMap;
		}

		@Override
		public AddHelperMockClass unflatten(Object obj, IRootFlattener root) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			return new AddHelperMockClass((Integer) map.get(VALUE));
		}

		@Override
		public boolean canFlatten(Object obj) {
			return obj instanceof AddHelperMockClass;
		}

		@Override
		public boolean canUnFlatten(Object obj) {
			if (obj instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) obj;
				if (AddHelperMockClass.class.getCanonicalName().equals(map.get(IFlattener.TYPE_KEY))) {
					return true;
				}
			}
			return false;
		}

	}

	@Test
	public void testAddNewHelper() {
		IRootFlattener root = new RootFlattener();
		// make sure class can't be unflattened before we add it
		Assert.assertFalse(root.canFlatten(new AddHelperMockClass(23)));

		// add the new helper
		root.addHelper(new AddHelperIFlattener());

		// make sure we can flatten it now
		Assert.assertTrue(root.canFlatten(new AddHelperMockClass(56)));

		// make sure it flattens/unflattens to the correct type
		Object unflatten = root.unflatten(root.flatten(new AddHelperMockClass(78)));
		Assert.assertTrue(unflatten instanceof AddHelperMockClass);
		Assert.assertEquals(78, ((AddHelperMockClass) unflatten).getValue());
	}
}
