/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.junit.Test;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class NexusBuilderPluginTest {
	
	@Test
	public void testNexusBuilderDS() {
		assertThat(ServiceProvider.getService(NexusBuilderFactory.class), is(notNullValue()));
		assertThat(ServiceProvider.getService(INexusFileFactory.class), is(notNullValue()));
	}
	
}
