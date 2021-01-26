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

	NX_TOFSINGLE("NXtofsingle"),
	NX_STXM("NXstxm"),
	NX_TOMO("NXtomo"),
	NX_XEULER("NXxeuler"),
	NX_LAUETOF("NXlauetof"),
	NX_XLAUE("NXxlaue"),
	NX_XNB("NXxnb"),
	NX_REFTOF("NXreftof"),
	NX_ARCHIVE("NXarchive"),
	NX_SCAN("NXscan"),
	NX_SAS("NXsas"),
	NX_MONOPD("NXmonopd"),
	NX_TOMOPROC("NXtomoproc"),
	NX_TOFRAW("NXtofraw"),
	NX_SPE("NXspe"),
	NX_XAS("NXxas"),
	NX_MX("NXmx"),
	NX_FLUO("NXfluo"),
	NX_XLAUEPLATE("NXxlaueplate"),
	NX_DIRECTTOF("NXdirecttof"),
	NX_CANSAS("NXcanSAS"),
	NX_IQPROC("NXiqproc"),
	NX_INDIRECTTOF("NXindirecttof"),
	NX_XKAPPA("NXxkappa"),
	NX_XBASE("NXxbase"),
	NX_TAS("NXtas"),
	NX_TOMOPHASE("NXtomophase"),
	NX_XASPROC("NXxasproc"),
	NX_ARPES("NXarpes"),
	NX_SQOM("NXsqom"),
	NX_SASTOF("NXsastof"),
	NX_TOFNPD("NXtofnpd"),
	NX_REFSCAN("NXrefscan"),
	NX_XROT("NXxrot");

	private String name;
	
	private NexusApplicationDefinition(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}		
