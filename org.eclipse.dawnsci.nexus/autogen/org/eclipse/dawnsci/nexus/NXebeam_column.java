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
 * Container for components to form a controlled beam in electron microscopy.
 *
 */
public interface NXebeam_column extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


	/**
	 * The source which creates the electron beam.
	 *
	 * @return  the value.
	 */
	public NXsource getElectron_source();

	/**
	 * The source which creates the electron beam.
	 *
	 * @param electron_sourceGroup the electron_sourceGroup
	 */
	public void setElectron_source(NXsource electron_sourceGroup);
	// Unprocessed group:
	// Unprocessed group:

	/**
	 *
	 * @return  the value.
	 */
	public NXaperture_em getAperture_em();

	/**
	 *
	 * @param aperture_emGroup the aperture_emGroup
	 */
	public void setAperture_em(NXaperture_em aperture_emGroup);

	/**
	 * Get a NXaperture_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture_em for that node.
	 */
	public NXaperture_em getAperture_em(String name);

	/**
	 * Set a NXaperture_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param aperture_em the value to set
	 */
	public void setAperture_em(String name, NXaperture_em aperture_em);

	/**
	 * Get all NXaperture_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaperture_em for that node.
	 */
	public Map<String, NXaperture_em> getAllAperture_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param aperture_em the child nodes to add
	 */

	public void setAllAperture_em(Map<String, NXaperture_em> aperture_em);


	/**
	 *
	 * @return  the value.
	 */
	public NXlens_em getLens_em();

	/**
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public NXlens_em getLens_em(String name);

	/**
	 * Set a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param lens_em the value to set
	 */
	public void setLens_em(String name, NXlens_em lens_em);

	/**
	 * Get all NXlens_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


	/**
	 *
	 * @return  the value.
	 */
	public NXcorrector_cs getCorrector_cs();

	/**
	 *
	 * @param corrector_csGroup the corrector_csGroup
	 */
	public void setCorrector_cs(NXcorrector_cs corrector_csGroup);

	/**
	 * Get a NXcorrector_cs node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcorrector_cs for that node.
	 */
	public NXcorrector_cs getCorrector_cs(String name);

	/**
	 * Set a NXcorrector_cs node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param corrector_cs the value to set
	 */
	public void setCorrector_cs(String name, NXcorrector_cs corrector_cs);

	/**
	 * Get all NXcorrector_cs nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcorrector_cs for that node.
	 */
	public Map<String, NXcorrector_cs> getAllCorrector_cs();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param corrector_cs the child nodes to add
	 */

	public void setAllCorrector_cs(Map<String, NXcorrector_cs> corrector_cs);


	/**
	 *
	 * @return  the value.
	 */
	public NXstage_lab getStage_lab();

	/**
	 *
	 * @param stage_labGroup the stage_labGroup
	 */
	public void setStage_lab(NXstage_lab stage_labGroup);

	/**
	 * Get a NXstage_lab node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXstage_lab for that node.
	 */
	public NXstage_lab getStage_lab(String name);

	/**
	 * Set a NXstage_lab node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param stage_lab the value to set
	 */
	public void setStage_lab(String name, NXstage_lab stage_lab);

	/**
	 * Get all NXstage_lab nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXstage_lab for that node.
	 */
	public Map<String, NXstage_lab> getAllStage_lab();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param stage_lab the child nodes to add
	 */

	public void setAllStage_lab(Map<String, NXstage_lab> stage_lab);


	/**
	 * A sensor used to monitor an external or internal condition.
	 *
	 * @return  the value.
	 */
	public NXsensor getSensor();

	/**
	 * A sensor used to monitor an external or internal condition.
	 *
	 * @param sensorGroup the sensorGroup
	 */
	public void setSensor(NXsensor sensorGroup);

	/**
	 * Get a NXsensor node by name:
	 * <ul>
	 * <li>
	 * A sensor used to monitor an external or internal condition.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public NXsensor getSensor(String name);

	/**
	 * Set a NXsensor node by name:
	 * <ul>
	 * <li>
	 * A sensor used to monitor an external or internal condition.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param sensor the value to set
	 */
	public void setSensor(String name, NXsensor sensor);

	/**
	 * Get all NXsensor nodes:
	 * <ul>
	 * <li>
	 * A sensor used to monitor an external or internal condition.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public Map<String, NXsensor> getAllSensor();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A sensor used to monitor an external or internal condition.</li>
	 * </ul>
	 *
	 * @param sensor the child nodes to add
	 */

	public void setAllSensor(Map<String, NXsensor> sensor);


	/**
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.
	 *
	 * @return  the value.
	 */
	public NXbeam getBeam();

	/**
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.
	 *
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Get a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public NXbeam getBeam(String name);

	/**
	 * Set a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param beam the value to set
	 */
	public void setBeam(String name, NXbeam beam);

	/**
	 * Get all NXbeam nodes:
	 * <ul>
	 * <li>
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public Map<String, NXbeam> getAllBeam();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual ocharacterization results for the position, shape,
	 * and characteristics of the electron beam.
	 * NXtransformations should be used to specify the location
	 * of the position at which the beam was probed.</li>
	 * </ul>
	 *
	 * @param beam the child nodes to add
	 */

	public void setAllBeam(Map<String, NXbeam> beam);


}
