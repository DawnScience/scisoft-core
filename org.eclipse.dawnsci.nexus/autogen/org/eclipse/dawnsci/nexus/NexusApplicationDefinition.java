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
		
/**
 * Enumeration of NeXus application definitions.
 */
public enum NexusApplicationDefinition {

	NX_SPE("NXspe"),
	NX_STXM("NXstxm"),
	NX_TOMOPROC("NXtomoproc"),
	NX_XBASE("NXxbase"),
	NX_CANSAS("NXcanSAS"),
	NX_SASTOF("NXsastof"),
	NX_XAS("NXxas"),
	NX_TOFRAW("NXtofraw"),
	NX_SCAN("NXscan"),
	NX_FLUO("NXfluo"),
	NX_MONOPD("NXmonopd"),
	NX_TOFNPD("NXtofnpd"),
	NX_SQOM("NXsqom"),
	NX_TOMOPHASE("NXtomophase"),
	NX_REFTOF("NXreftof"),
	NX_LAUETOF("NXlauetof"),
	NX_XLAUE("NXxlaue"),
	NX_ARPES("NXarpes"),
	NX_XASPROC("NXxasproc"),
	NX_MX("NXmx"),
	NX_XNB("NXxnb"),
	NX_XKAPPA("NXxkappa"),
	NX_IQPROC("NXiqproc"),
	NX_REFSCAN("NXrefscan"),
	NX_TOMO("NXtomo"),
	NX_SAS("NXsas"),
	NX_DIRECTTOF("NXdirecttof"),
	NX_XEULER("NXxeuler"),
	NX_INDIRECTTOF("NXindirecttof"),
	NX_XLAUEPLATE("NXxlaueplate"),
	NX_TOFSINGLE("NXtofsingle"),
	NX_ARCHIVE("NXarchive"),
	NX_XROT("NXxrot"),
	NX_TAS("NXtas");

	private String name;
	
	private NexusApplicationDefinition(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}		
