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
import org.eclipse.january.dataset.Dataset;

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
public interface NXmonochromator extends NXcomponent {

	public static final String NX_WAVELENGTH = "wavelength";
	public static final String NX_WAVELENGTH_ERROR = "wavelength_error";
	public static final String NX_WAVELENGTH_ERRORS = "wavelength_errors";
	public static final String NX_ENERGY = "energy";
	public static final String NX_ENERGY_ERROR = "energy_error";
	public static final String NX_ENERGY_ERRORS = "energy_errors";
	public static final String NX_ENERGY_DISPERSION = "energy_dispersion";
	public static final String NX_WAVELENGTH_DISPERSION = "wavelength_dispersion";
	/**
	 * wavelength selected
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelength();

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
	public Dataset getWavelength_error();

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
	public Dataset getWavelength_errors();

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
	public Dataset getEnergy();

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
	public Dataset getEnergy_error();

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
	public Dataset getEnergy_errors();

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
	 * Energy dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> eV/mm
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnergy_dispersion();

	/**
	 * Energy dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> eV/mm
	 * </p>
	 *
	 * @param energy_dispersionDataset the energy_dispersionDataset
	 */
	public DataNode setEnergy_dispersion(IDataset energy_dispersionDataset);

	/**
	 * Energy dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> eV/mm
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getEnergy_dispersionScalar();

	/**
	 * Energy dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> eV/mm
	 * </p>
	 *
	 * @param energy_dispersion the energy_dispersion
	 */
	public DataNode setEnergy_dispersionScalar(Double energy_dispersionValue);

	/**
	 * Wavelength dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> nm/mm
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelength_dispersion();

	/**
	 * Wavelength dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> nm/mm
	 * </p>
	 *
	 * @param wavelength_dispersionDataset the wavelength_dispersionDataset
	 */
	public DataNode setWavelength_dispersion(IDataset wavelength_dispersionDataset);

	/**
	 * Wavelength dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> nm/mm
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWavelength_dispersionScalar();

	/**
	 * Wavelength dispersion at the exit slit.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> nm/mm
	 * </p>
	 *
	 * @param wavelength_dispersion the wavelength_dispersion
	 */
	public DataNode setWavelength_dispersionScalar(Double wavelength_dispersionValue);

	/**
	 * Size, position and shape of the entrance slit of the monochromator.
	 *
	 * @return  the value.
	 */
	public NXaperture getEntrance_slit();

	/**
	 * Size, position and shape of the entrance slit of the monochromator.
	 *
	 * @param entrance_slitGroup the entrance_slitGroup
	 */
	public void setEntrance_slit(NXaperture entrance_slitGroup);

	/**
	 * Size, position and shape of the exit slit of the monochromator.
	 *
	 * @return  the value.
	 */
	public NXaperture getExit_slit();

	/**
	 * Size, position and shape of the exit slit of the monochromator.
	 *
	 * @param exit_slitGroup the exit_slitGroup
	 */
	public void setExit_slit(NXaperture exit_slitGroup);

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
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monochromator and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monochromator and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);

	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);

	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


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
	 * .. todo::
	 * Add a definition for the reference point of a monochromator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a monochromator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * .. todo::
	 * Add a definition for the reference point of a monochromator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a monochromator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

}
