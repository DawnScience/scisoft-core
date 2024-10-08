/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.json.test;

import static org.eclipse.dawnsci.json.test.JsonUtils.assertJsonEquals;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.dawnsci.analysis.api.persistence.IMarshallerService;
import org.eclipse.dawnsci.json.MarshallerService;
import org.eclipse.dawnsci.json.internal.MarshallerServiceClassRegistry.ClassRegistryDuplicateIdException;
import org.eclipse.dawnsci.json.test.classregistry.TestObjectAlternativeClassRegistry;
import org.eclipse.dawnsci.json.test.classregistry.TestObjectClashingIdClassRegistry;
import org.eclipse.dawnsci.json.test.classregistry.TestObjectClassRegistry;
import org.eclipse.dawnsci.json.test.testobject.TestTypeBean;
import org.eclipse.dawnsci.json.test.testobject.TestTypeRegisteredAlternativeImpl;
import org.eclipse.dawnsci.json.test.testobject.TestTypeRegisteredImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonMarshallerClassRegistryTest {

	private static final String JSON_FOR_TTRegBean = "{\"@type\":\"jsontest.testtypebean\",\"ttreg\": {\"@type\" : \"jsontest.testtyperegistered\", \"string\" : \"Registered test type.\"} }";
	private static final String JSON_FOR_TTRegAltBean = "{\"@type\":\"jsontest.testtypebean\",\"ttreg\": {\"@type\" : \"jsontest.testtyperegisteredalt\", \"string\" : \"Alternative Registered test type.\"} }";

	private IMarshallerService marshaller;
	private String json;

	// Test objects
	TestTypeBean ttbean;

	@Before
	public void setUp() throws Exception {
		createTestObjects();
	}

	private void createTestObjects() {
		ttbean = new TestTypeBean();
	}

	@After
	public void tearDown() throws Exception {
		if (json != null) {
			// So we can see what's going on
//			System.out.println("JSON: " + json);

			// To make it easy to replace expected JSON values in the code when we're sure they're correct
			@SuppressWarnings("unused")
			String javaLiteralForJSONString = '"' + StringEscapeUtils.escapeJava(json) + '"';
//			System.out.println("Java literal:\n" + javaLiteralForJSONString);
		}
		json = null;
		marshaller = null;
	}

	@Test
	public void testGivenTwoClassRegistriesWithNoIdOrClassClashThenNoExceptionRaised() throws Exception {
		marshaller = new MarshallerService(new TestObjectClassRegistry(), new TestObjectAlternativeClassRegistry());

		ttbean.setTTReg(new TestTypeRegisteredImpl("Registered test type."));
		json = marshaller.marshal(ttbean);

		assertJsonEquals(JSON_FOR_TTRegBean, json);

		ttbean.setTTReg(new TestTypeRegisteredAlternativeImpl("Registered test type."));
		json = marshaller.marshal(ttbean);

		assertJsonEquals(JSON_FOR_TTRegAltBean, json);
	}

	@Test
	public void testGivenTwoClassRegistriesWithIdClashThenExceptionRaisedWithCorrectMessage() throws Exception {
		marshaller = new MarshallerService(new TestObjectClassRegistry(), new TestObjectClashingIdClassRegistry());
		ClassRegistryDuplicateIdException e = assertThrows(ClassRegistryDuplicateIdException.class,
				() -> marshaller.marshal(null));
		assertThat(e.getMessage(), both(containsString("jsontest.animal")).and(containsString("clash")));
	}

	@Test
	public void testGivenTwoClassRegistriesWithIdClashButSameReferencedClassThenNoExceptionRaised() throws Exception {
		marshaller = new MarshallerService(new TestObjectClassRegistry(), new TestObjectAlternativeClassRegistry(), new TestObjectAlternativeClassRegistry());

		ttbean.setTTReg(new TestTypeRegisteredAlternativeImpl("Registered test type."));
		json = marshaller.marshal(ttbean);

		assertJsonEquals(JSON_FOR_TTRegAltBean, json);
	}

}
