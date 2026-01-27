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

	NX_APM("NXapm", NXapmValidator.class),
	NX_APM_COMPOSITIONSPACE_CONFIG("NXapm_compositionspace_config", NXapm_compositionspace_configValidator.class),
	NX_APM_COMPOSITIONSPACE_RESULTS("NXapm_compositionspace_results", NXapm_compositionspace_resultsValidator.class),
	NX_APM_PARAPROBE_CLUSTERER_CONFIG("NXapm_paraprobe_clusterer_config", NXapm_paraprobe_clusterer_configValidator.class),
	NX_APM_PARAPROBE_CLUSTERER_RESULTS("NXapm_paraprobe_clusterer_results", NXapm_paraprobe_clusterer_resultsValidator.class),
	NX_APM_PARAPROBE_DISTANCER_CONFIG("NXapm_paraprobe_distancer_config", NXapm_paraprobe_distancer_configValidator.class),
	NX_APM_PARAPROBE_DISTANCER_RESULTS("NXapm_paraprobe_distancer_results", NXapm_paraprobe_distancer_resultsValidator.class),
	NX_APM_PARAPROBE_INTERSECTOR_CONFIG("NXapm_paraprobe_intersector_config", NXapm_paraprobe_intersector_configValidator.class),
	NX_APM_PARAPROBE_INTERSECTOR_RESULTS("NXapm_paraprobe_intersector_results", NXapm_paraprobe_intersector_resultsValidator.class),
	NX_APM_PARAPROBE_NANOCHEM_CONFIG("NXapm_paraprobe_nanochem_config", NXapm_paraprobe_nanochem_configValidator.class),
	NX_APM_PARAPROBE_NANOCHEM_RESULTS("NXapm_paraprobe_nanochem_results", NXapm_paraprobe_nanochem_resultsValidator.class),
	NX_APM_PARAPROBE_RANGER_CONFIG("NXapm_paraprobe_ranger_config", NXapm_paraprobe_ranger_configValidator.class),
	NX_APM_PARAPROBE_RANGER_RESULTS("NXapm_paraprobe_ranger_results", NXapm_paraprobe_ranger_resultsValidator.class),
	NX_APM_PARAPROBE_SELECTOR_CONFIG("NXapm_paraprobe_selector_config", NXapm_paraprobe_selector_configValidator.class),
	NX_APM_PARAPROBE_SELECTOR_RESULTS("NXapm_paraprobe_selector_results", NXapm_paraprobe_selector_resultsValidator.class),
	NX_APM_PARAPROBE_SPATSTAT_CONFIG("NXapm_paraprobe_spatstat_config", NXapm_paraprobe_spatstat_configValidator.class),
	NX_APM_PARAPROBE_SPATSTAT_RESULTS("NXapm_paraprobe_spatstat_results", NXapm_paraprobe_spatstat_resultsValidator.class),
	NX_APM_PARAPROBE_SURFACER_CONFIG("NXapm_paraprobe_surfacer_config", NXapm_paraprobe_surfacer_configValidator.class),
	NX_APM_PARAPROBE_SURFACER_RESULTS("NXapm_paraprobe_surfacer_results", NXapm_paraprobe_surfacer_resultsValidator.class),
	NX_APM_PARAPROBE_TESSELLATOR_CONFIG("NXapm_paraprobe_tessellator_config", NXapm_paraprobe_tessellator_configValidator.class),
	NX_APM_PARAPROBE_TESSELLATOR_RESULTS("NXapm_paraprobe_tessellator_results", NXapm_paraprobe_tessellator_resultsValidator.class),
	NX_APM_PARAPROBE_TOOL_CONFIG("NXapm_paraprobe_tool_config", NXapm_paraprobe_tool_configValidator.class),
	NX_APM_PARAPROBE_TOOL_RESULTS("NXapm_paraprobe_tool_results", NXapm_paraprobe_tool_resultsValidator.class),
	NX_ARCHIVE("NXarchive", NXarchiveValidator.class),
	NX_ARPES("NXarpes", NXarpesValidator.class),
	NX_AZINT1D("NXazint1d", NXazint1dValidator.class),
	NX_AZINT2D("NXazint2d", NXazint2dValidator.class),
	NX_CANSAS("NXcanSAS", NXcanSASValidator.class),
	NX_CLASSIC_SCAN("NXclassic_scan", NXclassic_scanValidator.class),
	NX_CXI_PTYCHO("NXcxi_ptycho", NXcxi_ptychoValidator.class),
	NX_DIAMOND("NXdiamond", NXdiamondValidator.class),
	NX_DIRECTTOF("NXdirecttof", NXdirecttofValidator.class),
	NX_DISPERSIVE_MATERIAL("NXdispersive_material", NXdispersive_materialValidator.class),
	NX_ELLIPSOMETRY("NXellipsometry", NXellipsometryValidator.class),
	NX_EM("NXem", NXemValidator.class),
	NX_EM_CALORIMETRY("NXem_calorimetry", NXem_calorimetryValidator.class),
	NX_FLUO("NXfluo", NXfluoValidator.class),
	NX_INDIRECTTOF("NXindirecttof", NXindirecttofValidator.class),
	NX_IQPROC("NXiqproc", NXiqprocValidator.class),
	NX_IV_TEMP("NXiv_temp", NXiv_tempValidator.class),
	NX_LAUETOF("NXlauetof", NXlauetofValidator.class),
	NX_MICROSTRUCTURE_KANAPY_RESULTS("NXmicrostructure_kanapy_results", NXmicrostructure_kanapy_resultsValidator.class),
	NX_MICROSTRUCTURE_SCORE_CONFIG("NXmicrostructure_score_config", NXmicrostructure_score_configValidator.class),
	NX_MICROSTRUCTURE_SCORE_RESULTS("NXmicrostructure_score_results", NXmicrostructure_score_resultsValidator.class),
	NX_MONOPD("NXmonopd", NXmonopdValidator.class),
	NX_MPES("NXmpes", NXmpesValidator.class),
	NX_MPES_ARPES("NXmpes_arpes", NXmpes_arpesValidator.class),
	NX_MX("NXmx", NXmxValidator.class),
	NX_OPTICAL_SPECTROSCOPY("NXoptical_spectroscopy", NXoptical_spectroscopyValidator.class),
	NX_PEEM("NXpeem", NXpeemValidator.class),
	NX_RAMAN("NXraman", NXramanValidator.class),
	NX_REFSCAN("NXrefscan", NXrefscanValidator.class),
	NX_REFTOF("NXreftof", NXreftofValidator.class),
	NX_SAS("NXsas", NXsasValidator.class),
	NX_SASTOF("NXsastof", NXsastofValidator.class),
	NX_SCAN("NXscan", NXscanValidator.class),
	NX_SENSOR_SCAN("NXsensor_scan", NXsensor_scanValidator.class),
	NX_SNSEVENT("NXsnsevent", NXsnseventValidator.class),
	NX_SNSHISTO("NXsnshisto", NXsnshistoValidator.class),
	NX_SPE("NXspe", NXspeValidator.class),
	NX_SQOM("NXsqom", NXsqomValidator.class),
	NX_STRESS("NXstress", NXstressValidator.class),
	NX_STXM("NXstxm", NXstxmValidator.class),
	NX_TAS("NXtas", NXtasValidator.class),
	NX_TOFNPD("NXtofnpd", NXtofnpdValidator.class),
	NX_TOFRAW("NXtofraw", NXtofrawValidator.class),
	NX_TOFSINGLE("NXtofsingle", NXtofsingleValidator.class),
	NX_TOMO("NXtomo", NXtomoValidator.class),
	NX_TOMOPHASE("NXtomophase", NXtomophaseValidator.class),
	NX_TOMOPROC("NXtomoproc", NXtomoprocValidator.class),
	NX_TRANSMISSION("NXtransmission", NXtransmissionValidator.class),
	NX_XAS("NXxas", NXxasValidator.class),
	NX_XASPROC("NXxasproc", NXxasprocValidator.class),
	NX_XBASE("NXxbase", NXxbaseValidator.class),
	NX_XEULER("NXxeuler", NXxeulerValidator.class),
	NX_XKAPPA("NXxkappa", NXxkappaValidator.class),
	NX_XLAUE("NXxlaue", NXxlaueValidator.class),
	NX_XLAUEPLATE("NXxlaueplate", NXxlaueplateValidator.class),
	NX_XNB("NXxnb", NXxnbValidator.class),
	NX_XPCS("NXxpcs", NXxpcsValidator.class),
	NX_XPS("NXxps", NXxpsValidator.class),
	NX_XRD("NXxrd", NXxrdValidator.class),
	NX_XRD_PAN("NXxrd_pan", NXxrd_panValidator.class),
	NX_XROT("NXxrot", NXxrotValidator.class);

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
	public <T extends NexusApplicationValidator> T createNexusValidator() {
		try {
			return (T) validatorClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("Could not create nexus validator class: " + validatorClass.getSimpleName(), e);
		}
	}
	
}		
