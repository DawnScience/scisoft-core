/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.dawnsci.nexus.validation.*;
		
/**
 * Enumeration of NeXus application definitions.
 */
public enum NexusApplicationDefinition {

	NX_ARPES("NXarpes", NXarpesValidator.class),
	NX_SASTOF("NXsastof", NXsastofValidator.class),
	NX_XROT("NXxrot", NXxrotValidator.class),
	NX_XNB("NXxnb", NXxnbValidator.class),
	NX_XBASE("NXxbase", NXxbaseValidator.class),
	NX_XASPROC("NXxasproc", NXxasprocValidator.class),
	NX_TOMOPHASE("NXtomophase", NXtomophaseValidator.class),
	NX_SCAN("NXscan", NXscanValidator.class),
	NX_IQPROC("NXiqproc", NXiqprocValidator.class),
	NX_REFSCAN("NXrefscan", NXrefscanValidator.class),
	NX_SAS("NXsas", NXsasValidator.class),
	NX_XLAUEPLATE("NXxlaueplate", NXxlaueplateValidator.class),
	NX_XEULER("NXxeuler", NXxeulerValidator.class),
	NX_ARCHIVE("NXarchive", NXarchiveValidator.class),
	NX_CANSAS("NXcanSAS", NXcanSASValidator.class),
	NX_XKAPPA("NXxkappa", NXxkappaValidator.class),
	NX_MONOPD("NXmonopd", NXmonopdValidator.class),
	NX_XLAUE("NXxlaue", NXxlaueValidator.class),
	NX_TOMO("NXtomo", NXtomoValidator.class),
	NX_TAS("NXtas", NXtasValidator.class),
	NX_SPE("NXspe", NXspeValidator.class),
	NX_TOFRAW("NXtofraw", NXtofrawValidator.class),
	NX_LAUETOF("NXlauetof", NXlauetofValidator.class),
	NX_INDIRECTTOF("NXindirecttof", NXindirecttofValidator.class),
	NX_REFTOF("NXreftof", NXreftofValidator.class),
	NX_TOFSINGLE("NXtofsingle", NXtofsingleValidator.class),
	NX_FLUO("NXfluo", NXfluoValidator.class),
	NX_TOFNPD("NXtofnpd", NXtofnpdValidator.class),
	NX_DIRECTTOF("NXdirecttof", NXdirecttofValidator.class),
	NX_XAS("NXxas", NXxasValidator.class),
	NX_STXM("NXstxm", NXstxmValidator.class),
	NX_TOMOPROC("NXtomoproc", NXtomoprocValidator.class),
	NX_SQOM("NXsqom", NXsqomValidator.class),
	NX_MX("NXmx", NXmxValidator.class);

	private String name;
	
	private Class<? extends NexusApplicationValidator> validatorClass; 
	
	private NexusApplicationDefinition(String name, Class<? extends NexusApplicationValidator> validatorClass) {
		this.name = name;
		this.validatorClass = validatorClass;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Returns the {@link NexusApplicationDefinition} constant for the given name string.
	 * @param name
	 * @return
	 */
	public static NexusApplicationDefinition fromName(String name) {
		final String enumName = (name.substring(0, 2) + '_' + name.substring(2)).toUpperCase();
		return valueOf(enumName);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends NexusApplicationValidator> T createNexusValidator() throws NexusException {
		try {
			return (T) validatorClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new NexusException("Could not create nexus validator class " + validatorClass.getSimpleName(), e);
		}
	}
	
}		
