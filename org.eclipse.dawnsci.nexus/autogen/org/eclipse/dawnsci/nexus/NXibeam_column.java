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
 * Base class for a set of components equipping an instrument with FIB capabilities.
 * Focused-ion-beam (FIB) capabilities turn especially scanning electron microscopes
 * into specimen preparation labs. FIB is a material preparation technique whereby
 * portions of the sample are illuminated with a focused ion beam with controlled
 * intensity. The beam is controlled such that it is intense, focused, and equipped
 * with sufficient ion having sufficient momentum to remove material in a controlled
 * manner.
 * The fact that an electron microscope with FIB capabilities achieves these functionalities
 * via a second component (aka the ion gun) that has its own relevant control circuits,
 * focusing lenses, and other components, warrants the definition of an own base class
 * to group these components and distinguish them from the lenses and components for creating
 * and shaping the electron beam.
 * For more details about the relevant physics and application examples
 * consult the literature, for example:
 * * `L. A. Giannuzzi et al. <https://doi.org/10.1007/b101190>`_
 * * `E. I. Preiß et al. <https://link.springer.com/content/pdf/10.1557/s43578-020-00045-w.pdf>`_
 * * `J. F. Ziegler et al. <https://www.sciencedirect.com/science/article/pii/S0168583X10001862>`_
 * * `J. Lili <https://www.osti.gov/servlets/purl/924801>`_
 * * `N. Yao <https://doi.org/10.1017/CBO9780511600302>`_
 *
 */
public interface NXibeam_column extends NXcomponent {

	public static final String NX_OPERATION_MODE = "operation_mode";
	/**
	 * Tech-partner, microscope-, and control-software-specific name of the
	 * specific operation mode how the ibeam_column and its components are
	 * controlled to achieve specific illumination conditions.
	 * In many cases the users of an instrument do not or can not be expected to know
	 * all intricate spatiotemporal dynamics of their hardware. Instead, they rely on
	 * assumptions that the instrument, its control software, and components work as
	 * expected to focus on their research questions.
	 * For these cases, having a place for documenting the operation_mode is useful
	 * in as much as at least some constraints on how the illumination conditions were
	 * is documented.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOperation_mode();

	/**
	 * Tech-partner, microscope-, and control-software-specific name of the
	 * specific operation mode how the ibeam_column and its components are
	 * controlled to achieve specific illumination conditions.
	 * In many cases the users of an instrument do not or can not be expected to know
	 * all intricate spatiotemporal dynamics of their hardware. Instead, they rely on
	 * assumptions that the instrument, its control software, and components work as
	 * expected to focus on their research questions.
	 * For these cases, having a place for documenting the operation_mode is useful
	 * in as much as at least some constraints on how the illumination conditions were
	 * is documented.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param operation_modeDataset the operation_modeDataset
	 */
	public DataNode setOperation_mode(IDataset operation_modeDataset);

	/**
	 * Tech-partner, microscope-, and control-software-specific name of the
	 * specific operation mode how the ibeam_column and its components are
	 * controlled to achieve specific illumination conditions.
	 * In many cases the users of an instrument do not or can not be expected to know
	 * all intricate spatiotemporal dynamics of their hardware. Instead, they rely on
	 * assumptions that the instrument, its control software, and components work as
	 * expected to focus on their research questions.
	 * For these cases, having a place for documenting the operation_mode is useful
	 * in as much as at least some constraints on how the illumination conditions were
	 * is documented.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOperation_modeScalar();

	/**
	 * Tech-partner, microscope-, and control-software-specific name of the
	 * specific operation mode how the ibeam_column and its components are
	 * controlled to achieve specific illumination conditions.
	 * In many cases the users of an instrument do not or can not be expected to know
	 * all intricate spatiotemporal dynamics of their hardware. Instead, they rely on
	 * assumptions that the instrument, its control software, and components work as
	 * expected to focus on their research questions.
	 * For these cases, having a place for documenting the operation_mode is useful
	 * in as much as at least some constraints on how the illumination conditions were
	 * is documented.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param operation_mode the operation_mode
	 */
	public DataNode setOperation_modeScalar(String operation_modeValue);

	/**
	 * The source which creates the ion beam.
	 *
	 * @return  the value.
	 */
	public NXsource getIon_source();

	/**
	 * The source which creates the ion beam.
	 *
	 * @param ion_sourceGroup the ion_sourceGroup
	 */
	public void setIon_source(NXsource ion_sourceGroup);
	// Unprocessed group:probe

	/**
	 *
	 * @return  the value.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens();

	/**
	 *
	 * @param electromagnetic_lensGroup the electromagnetic_lensGroup
	 */
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup);

	/**
	 * Get a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public NXelectromagnetic_lens getElectromagnetic_lens(String name);

	/**
	 * Set a NXelectromagnetic_lens node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param electromagnetic_lens the value to set
	 */
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens);

	/**
	 * Get all NXelectromagnetic_lens nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXelectromagnetic_lens for that node.
	 */
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param electromagnetic_lens the child nodes to add
	 */

	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens);


	/**
	 *
	 * @return  the value.
	 */
	public NXaperture getAperture();

	/**
	 *
	 * @param apertureGroup the apertureGroup
	 */
	public void setAperture(NXaperture apertureGroup);

	/**
	 * Get a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public NXaperture getAperture(String name);

	/**
	 * Set a NXaperture node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param aperture the value to set
	 */
	public void setAperture(String name, NXaperture aperture);

	/**
	 * Get all NXaperture nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXaperture for that node.
	 */
	public Map<String, NXaperture> getAllAperture();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param aperture the child nodes to add
	 */

	public void setAllAperture(Map<String, NXaperture> aperture);


	/**
	 *
	 * @return  the value.
	 */
	public NXdeflector getDeflector();

	/**
	 *
	 * @param deflectorGroup the deflectorGroup
	 */
	public void setDeflector(NXdeflector deflectorGroup);

	/**
	 * Get a NXdeflector node by name:
	 * <ul>
	 * <li></li>
	 * <li>
	 * A component for blanking the ion beam or generating pulsed ion beams.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public NXdeflector getDeflector(String name);

	/**
	 * Set a NXdeflector node by name:
	 * <ul>
	 * <li></li>
	 * <li>
	 * A component for blanking the ion beam or generating pulsed ion beams.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param deflector the value to set
	 */
	public void setDeflector(String name, NXdeflector deflector);

	/**
	 * Get all NXdeflector nodes:
	 * <ul>
	 * <li></li>
	 * <li>
	 * A component for blanking the ion beam or generating pulsed ion beams.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdeflector for that node.
	 */
	public Map<String, NXdeflector> getAllDeflector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * <li>
	 * A component for blanking the ion beam or generating pulsed ion beams.</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * A component for blanking the ion beam or generating pulsed ion beams.
	 *
	 * @return  the value.
	 */
	public NXdeflector getBlankerid();

	/**
	 * A component for blanking the ion beam or generating pulsed ion beams.
	 *
	 * @param blankeridGroup the blankeridGroup
	 */
	public void setBlankerid(NXdeflector blankeridGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXmonochromator getMonochromator();

	/**
	 *
	 * @param monochromatorGroup the monochromatorGroup
	 */
	public void setMonochromator(NXmonochromator monochromatorGroup);

	/**
	 * Get a NXmonochromator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public NXmonochromator getMonochromator(String name);

	/**
	 * Set a NXmonochromator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param monochromator the value to set
	 */
	public void setMonochromator(String name, NXmonochromator monochromator);

	/**
	 * Get all NXmonochromator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public Map<String, NXmonochromator> getAllMonochromator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param monochromator the child nodes to add
	 */

	public void setAllMonochromator(Map<String, NXmonochromator> monochromator);


	/**
	 *
	 * @return  the value.
	 */
	public NXsensor getSensor();

	/**
	 *
	 * @param sensorGroup the sensorGroup
	 */
	public void setSensor(NXsensor sensorGroup);

	/**
	 * Get a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public NXsensor getSensor(String name);

	/**
	 * Set a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param sensor the value to set
	 */
	public void setSensor(String name, NXsensor sensor);

	/**
	 * Get all NXsensor nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public Map<String, NXsensor> getAllSensor();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param sensor the child nodes to add
	 */

	public void setAllSensor(Map<String, NXsensor> sensor);


	/**
	 *
	 * @return  the value.
	 */
	public NXactuator getActuator();

	/**
	 *
	 * @param actuatorGroup the actuatorGroup
	 */
	public void setActuator(NXactuator actuatorGroup);

	/**
	 * Get a NXactuator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXactuator for that node.
	 */
	public NXactuator getActuator(String name);

	/**
	 * Set a NXactuator node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param actuator the value to set
	 */
	public void setActuator(String name, NXactuator actuator);

	/**
	 * Get all NXactuator nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXactuator for that node.
	 */
	public Map<String, NXactuator> getAllActuator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param actuator the child nodes to add
	 */

	public void setAllActuator(Map<String, NXactuator> actuator);


	/**
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.
	 *
	 * @return  the value.
	 */
	public NXbeam getBeam();

	/**
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.
	 *
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Get a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
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
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
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
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public Map<String, NXbeam> getAllBeam();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * :ref:`NXtransformations` should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 *
	 * @param beam the child nodes to add
	 */

	public void setAllBeam(Map<String, NXbeam> beam);


	/**
	 *
	 * @return  the value.
	 */
	public NXcomponent getComponent();

	/**
	 *
	 * @param componentGroup the componentGroup
	 */
	public void setComponent(NXcomponent componentGroup);

	/**
	 * Get a NXcomponent node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcomponent for that node.
	 */
	public NXcomponent getComponent(String name);

	/**
	 * Set a NXcomponent node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param component the value to set
	 */
	public void setComponent(String name, NXcomponent component);

	/**
	 * Get all NXcomponent nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcomponent for that node.
	 */
	public Map<String, NXcomponent> getAllComponent();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param component the child nodes to add
	 */

	public void setAllComponent(Map<String, NXcomponent> component);


	/**
	 *
	 * @return  the value.
	 */
	public NXscan_controller getScan_controller();

	/**
	 *
	 * @param scan_controllerGroup the scan_controllerGroup
	 */
	public void setScan_controller(NXscan_controller scan_controllerGroup);

}
