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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * A wavelength defining device.
 * This is a base class for everything which
 * selects a wavelength or energy, be it a
 * monochromator crystal, a velocity selector,
 * an undulator or whatever.
 * The expected units are:
 * * wavelength: angstrom
 * * energy: eV
 * 
 */
public interface NXmonochromator extends NXobject {

	public static final String NX_WAVELENGTH = "wavelength";
	public static final String NX_WAVELENGTH_ERROR = "wavelength_error";
	public static final String NX_WAVELENGTH_ERRORS = "wavelength_errors";
	public static final String NX_ENERGY = "energy";
	public static final String NX_ENERGY_ERROR = "energy_error";
	public static final String NX_ENERGY_ERRORS = "energy_errors";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * wavelength selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWavelength();
	
	/**
	 * wavelength selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelengthDataset the wavelengthDataset
	 */
	public DataNode setWavelength(IDataset wavelengthDataset);

	/**
	 * wavelength selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWavelengthScalar();

	/**
	 * wavelength selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength the wavelength
	 */
	public DataNode setWavelengthScalar(Double wavelengthValue);

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @return  the value.
	 */
	@Deprecated
	public IDataset getWavelength_error();
	
	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @param wavelength_errorDataset the wavelength_errorDataset
	 */
	@Deprecated
	public DataNode setWavelength_error(IDataset wavelength_errorDataset);

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @return  the value.
	 */
	@Deprecated
	public Double getWavelength_errorScalar();

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @param wavelength_error the wavelength_error
	 */
	@Deprecated
	public DataNode setWavelength_errorScalar(Double wavelength_errorValue);

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getWavelength_errors();
	
	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength_errorsDataset the wavelength_errorsDataset
	 */
	public DataNode setWavelength_errors(IDataset wavelength_errorsDataset);

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getWavelength_errorsScalar();

	/**
	 * wavelength standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 * 
	 * @param wavelength_errors the wavelength_errors
	 */
	public DataNode setWavelength_errorsScalar(Double wavelength_errorsValue);

	/**
	 * energy selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnergy();
	
	/**
	 * energy selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energyDataset the energyDataset
	 */
	public DataNode setEnergy(IDataset energyDataset);

	/**
	 * energy selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEnergyScalar();

	/**
	 * energy selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energy the energy
	 */
	public DataNode setEnergyScalar(Double energyValue);

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @return  the value.
	 */
	@Deprecated
	public IDataset getEnergy_error();
	
	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @param energy_errorDataset the energy_errorDataset
	 */
	@Deprecated
	public DataNode setEnergy_error(IDataset energy_errorDataset);

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @return  the value.
	 */
	@Deprecated
	public Double getEnergy_errorScalar();

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @deprecated see https://github.com/nexusformat/definitions/issues/820
	 * @param energy_error the energy_error
	 */
	@Deprecated
	public DataNode setEnergy_errorScalar(Double energy_errorValue);

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnergy_errors();
	
	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energy_errorsDataset the energy_errorsDataset
	 */
	public DataNode setEnergy_errors(IDataset energy_errorsDataset);

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEnergy_errorsScalar();

	/**
	 * energy standard deviation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energy_errors the energy_errors
	 */
	public DataNode setEnergy_errorsScalar(Double energy_errorsValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXdata getDistribution();
	
	/**
	 * 
	 * @param distributionGroup the distributionGroup
	 */
	public void setDistribution(NXdata distributionGroup);

	/**
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Use as many crystals as necessary to describe
	 * 
	 * @return  the value.
	 */
	public NXcrystal getCrystal();
	
	/**
	 * Use as many crystals as necessary to describe
	 * 
	 * @param crystalGroup the crystalGroup
	 */
	public void setCrystal(NXcrystal crystalGroup);

	/**
	 * Get a NXcrystal node by name:
	 * <ul>
	 * <li>
	 * Use as many crystals as necessary to describe</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcrystal for that node.
	 */
	public NXcrystal getCrystal(String name);
	
	/**
	 * Set a NXcrystal node by name:
	 * <ul>
	 * <li>
	 * Use as many crystals as necessary to describe</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param crystal the value to set
	 */
	public void setCrystal(String name, NXcrystal crystal);
	
	/**
	 * Get all NXcrystal nodes:
	 * <ul>
	 * <li>
	 * Use as many crystals as necessary to describe</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcrystal for that node.
	 */
	public Map<String, NXcrystal> getAllCrystal();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Use as many crystals as necessary to describe</li>
	 * </ul>
	 * 
	 * @param crystal the child nodes to add 
	 */
	
	public void setAllCrystal(Map<String, NXcrystal> crystal);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXvelocity_selector getVelocity_selector();
	
	/**
	 * 
	 * @param velocity_selectorGroup the velocity_selectorGroup
	 */
	public void setVelocity_selector(NXvelocity_selector velocity_selectorGroup);

	/**
	 * Get a NXvelocity_selector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXvelocity_selector for that node.
	 */
	public NXvelocity_selector getVelocity_selector(String name);
	
	/**
	 * Set a NXvelocity_selector node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param velocity_selector the value to set
	 */
	public void setVelocity_selector(String name, NXvelocity_selector velocity_selector);
	
	/**
	 * Get all NXvelocity_selector nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXvelocity_selector for that node.
	 */
	public Map<String, NXvelocity_selector> getAllVelocity_selector();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param velocity_selector the child nodes to add 
	 */
	
	public void setAllVelocity_selector(Map<String, NXvelocity_selector> velocity_selector);
	

	/**
	 * For diffraction grating based monochromators
	 * 
	 * @return  the value.
	 */
	public NXgrating getGrating();
	
	/**
	 * For diffraction grating based monochromators
	 * 
	 * @param gratingGroup the gratingGroup
	 */
	public void setGrating(NXgrating gratingGroup);

	/**
	 * Get a NXgrating node by name:
	 * <ul>
	 * <li>
	 * For diffraction grating based monochromators</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgrating for that node.
	 */
	public NXgrating getGrating(String name);
	
	/**
	 * Set a NXgrating node by name:
	 * <ul>
	 * <li>
	 * For diffraction grating based monochromators</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param grating the value to set
	 */
	public void setGrating(String name, NXgrating grating);
	
	/**
	 * Get all NXgrating nodes:
	 * <ul>
	 * <li>
	 * For diffraction grating based monochromators</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXgrating for that node.
	 */
	public Map<String, NXgrating> getAllGrating();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * For diffraction grating based monochromators</li>
	 * </ul>
	 * 
	 * @param grating the child nodes to add 
	 */
	
	public void setAllGrating(Map<String, NXgrating> grating);
	

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}
