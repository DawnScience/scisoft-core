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
 * Base class for instrument-related details of a real or simulated electron microscope.
 * For collecting data and experiments which are simulations of an electron
 * microscope (or such session) use the :ref:`NXem` application definition and
 * the :ref:`NXem_event_data` groups it provides.
 * This base class implements the concept of :ref:`NXem` whereby (meta)data are distinguished
 * whether these typically change during a session (dynamic) or not (static metadata).
 * This design allows to store e.g. hardware related concepts only once instead of demanding
 * that each image or spectrum from the session needs to be stored also with the static metadata.
 *
 */
public interface NXem_instrument extends NXinstrument {

	public static final String NX_LOCATION = "location";
	public static final String NX_TYPE = "type";
	/**
	 * Given name of the microscope at the hosting institution.
	 * This is an alias. Examples could be NionHermes, Titan, JEOL,
	 * Gemini, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name of the microscope at the hosting institution.
	 * This is an alias. Examples could be NionHermes, Titan, JEOL,
	 * Gemini, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name of the microscope at the hosting institution.
	 * This is an alias. Examples could be NionHermes, Titan, JEOL,
	 * Gemini, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name of the microscope at the hosting institution.
	 * This is an alias. Examples could be NionHermes, Titan, JEOL,
	 * Gemini, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Location of the lab or place where the instrument is installed.
	 * Using GEOREF is preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLocation();

	/**
	 * Location of the lab or place where the instrument is installed.
	 * Using GEOREF is preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param locationDataset the locationDataset
	 */
	public DataNode setLocation(IDataset locationDataset);

	/**
	 * Location of the lab or place where the instrument is installed.
	 * Using GEOREF is preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLocationScalar();

	/**
	 * Location of the lab or place where the instrument is installed.
	 * Using GEOREF is preferred.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param location the location
	 */
	public DataNode setLocationScalar(String locationValue);

	/**
	 * Different types of electron microscopes exist:
	 * * sem, a scanning electron microscope without focused-ion beam capabilities
	 * * fib, a scanning electron microscope with focused-ion beam capabilities
	 * irrespective whether these were used or not
	 * * tem, a transmission electron microscope
	 * NXem is one joint data model that can be used to document research that is performed
	 * with several of these types of microscopes (SEM, TEM, or FIB). The NXem data model
	 * stresses that these types of instruments despite having several differences are still all
	 * electron beamlines with which to probe electron and/or ion matter interaction and in fact
	 * in practice have many similarities in how they are used, the components, they contain, etc.
	 * This field can be used in research data management systems for enabling a categorization
	 * or tagging of experiments without having to analyze if groups like NXibeam_column are present
	 * (which would indicate type is fib) or if certain lens configurations or instrument models are used
	 * which suggests the microscope is a scanning (sem) or transmission electron microscope (tem):
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>sem</b> </li>
	 * <li><b>fib</b> </li>
	 * <li><b>tem</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Different types of electron microscopes exist:
	 * * sem, a scanning electron microscope without focused-ion beam capabilities
	 * * fib, a scanning electron microscope with focused-ion beam capabilities
	 * irrespective whether these were used or not
	 * * tem, a transmission electron microscope
	 * NXem is one joint data model that can be used to document research that is performed
	 * with several of these types of microscopes (SEM, TEM, or FIB). The NXem data model
	 * stresses that these types of instruments despite having several differences are still all
	 * electron beamlines with which to probe electron and/or ion matter interaction and in fact
	 * in practice have many similarities in how they are used, the components, they contain, etc.
	 * This field can be used in research data management systems for enabling a categorization
	 * or tagging of experiments without having to analyze if groups like NXibeam_column are present
	 * (which would indicate type is fib) or if certain lens configurations or instrument models are used
	 * which suggests the microscope is a scanning (sem) or transmission electron microscope (tem):
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>sem</b> </li>
	 * <li><b>fib</b> </li>
	 * <li><b>tem</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Different types of electron microscopes exist:
	 * * sem, a scanning electron microscope without focused-ion beam capabilities
	 * * fib, a scanning electron microscope with focused-ion beam capabilities
	 * irrespective whether these were used or not
	 * * tem, a transmission electron microscope
	 * NXem is one joint data model that can be used to document research that is performed
	 * with several of these types of microscopes (SEM, TEM, or FIB). The NXem data model
	 * stresses that these types of instruments despite having several differences are still all
	 * electron beamlines with which to probe electron and/or ion matter interaction and in fact
	 * in practice have many similarities in how they are used, the components, they contain, etc.
	 * This field can be used in research data management systems for enabling a categorization
	 * or tagging of experiments without having to analyze if groups like NXibeam_column are present
	 * (which would indicate type is fib) or if certain lens configurations or instrument models are used
	 * which suggests the microscope is a scanning (sem) or transmission electron microscope (tem):
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>sem</b> </li>
	 * <li><b>fib</b> </li>
	 * <li><b>tem</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Different types of electron microscopes exist:
	 * * sem, a scanning electron microscope without focused-ion beam capabilities
	 * * fib, a scanning electron microscope with focused-ion beam capabilities
	 * irrespective whether these were used or not
	 * * tem, a transmission electron microscope
	 * NXem is one joint data model that can be used to document research that is performed
	 * with several of these types of microscopes (SEM, TEM, or FIB). The NXem data model
	 * stresses that these types of instruments despite having several differences are still all
	 * electron beamlines with which to probe electron and/or ion matter interaction and in fact
	 * in practice have many similarities in how they are used, the components, they contain, etc.
	 * This field can be used in research data management systems for enabling a categorization
	 * or tagging of experiments without having to analyze if groups like NXibeam_column are present
	 * (which would indicate type is fib) or if certain lens configurations or instrument models are used
	 * which suggests the microscope is a scanning (sem) or transmission electron microscope (tem):
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>sem</b> </li>
	 * <li><b>fib</b> </li>
	 * <li><b>tem</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

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
	 *
	 * @return  the value.
	 */
	public NXebeam_column getEbeam_column();

	/**
	 *
	 * @param ebeam_columnGroup the ebeam_columnGroup
	 */
	public void setEbeam_column(NXebeam_column ebeam_columnGroup);

	/**
	 * Get a NXebeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXebeam_column for that node.
	 */
	public NXebeam_column getEbeam_column(String name);

	/**
	 * Set a NXebeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param ebeam_column the value to set
	 */
	public void setEbeam_column(String name, NXebeam_column ebeam_column);

	/**
	 * Get all NXebeam_column nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXebeam_column for that node.
	 */
	public Map<String, NXebeam_column> getAllEbeam_column();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param ebeam_column the child nodes to add
	 */

	public void setAllEbeam_column(Map<String, NXebeam_column> ebeam_column);


	/**
	 *
	 * @return  the value.
	 */
	public NXibeam_column getIbeam_column();

	/**
	 *
	 * @param ibeam_columnGroup the ibeam_columnGroup
	 */
	public void setIbeam_column(NXibeam_column ibeam_columnGroup);

	/**
	 * Get a NXibeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXibeam_column for that node.
	 */
	public NXibeam_column getIbeam_column(String name);

	/**
	 * Set a NXibeam_column node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param ibeam_column the value to set
	 */
	public void setIbeam_column(String name, NXibeam_column ibeam_column);

	/**
	 * Get all NXibeam_column nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXibeam_column for that node.
	 */
	public Map<String, NXibeam_column> getAllIbeam_column();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param ibeam_column the child nodes to add
	 */

	public void setAllIbeam_column(Map<String, NXibeam_column> ibeam_column);


	/**
	 *
	 * @return  the value.
	 */
	public NXem_optical_system getEm_optical_system();

	/**
	 *
	 * @param em_optical_systemGroup the em_optical_systemGroup
	 */
	public void setEm_optical_system(NXem_optical_system em_optical_systemGroup);

	/**
	 * Get a NXem_optical_system node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXem_optical_system for that node.
	 */
	public NXem_optical_system getEm_optical_system(String name);

	/**
	 * Set a NXem_optical_system node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param em_optical_system the value to set
	 */
	public void setEm_optical_system(String name, NXem_optical_system em_optical_system);

	/**
	 * Get all NXem_optical_system nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXem_optical_system for that node.
	 */
	public Map<String, NXem_optical_system> getAllEm_optical_system();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param em_optical_system the child nodes to add
	 */

	public void setAllEm_optical_system(Map<String, NXem_optical_system> em_optical_system);


	/**
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.
	 *
	 * @return  the value.
	 */
	public NXdetector getDetector();

	/**
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.
	 *
	 * @param detectorGroup the detectorGroup
	 */
	public void setDetector(NXdetector detectorGroup);

	/**
	 * Get a NXdetector node by name:
	 * <ul>
	 * <li>
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public NXdetector getDetector(String name);

	/**
	 * Set a NXdetector node by name:
	 * <ul>
	 * <li>
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param detector the value to set
	 */
	public void setDetector(String name, NXdetector detector);

	/**
	 * Get all NXdetector nodes:
	 * <ul>
	 * <li>
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdetector for that node.
	 */
	public Map<String, NXdetector> getAllDetector();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Description of the type of the detector.
	 * Electron microscopes have typically multiple detectors.
	 * Different technologies are in use like CCD, scintillator,
	 * direct electron, CMOS, or image plate to name but a few.</li>
	 * </ul>
	 *
	 * @param detector the child nodes to add
	 */

	public void setAllDetector(Map<String, NXdetector> detector);


	/**
	 * Stages in an electron microscope are multi-functional devices.
	 * Stages enable experimentalists the application of controlled external stimuli
	 * on the specimen. Modern stages realize a hierarchy of components.
	 * A multi-axial tilt rotation holder is a good example where the control of
	 * each degree of freedom is technically implemented via providing instances
	 * of e.g. :ref:`NXpositioner` or :ref:`NXactuator` that achieve the rotating
	 * and positioning of the specimen.
	 * The physical process of mounting a specimen on a stage in practice often
	 * comes with an own hierarchy of fixtures to bridge e.g. length scales technically.
	 * An example from atom probe microscopy is that researchers may work
	 * with wire samples which are clipped into a larger fixing unit to enable
	 * careful specimen handling. Alternatively, a microtip is a silicon post
	 * upon which e.g. an atom probe specimen is mounted. Multiple of such microtips
	 * are then grouped into a microtip array to conveniently enable loading of multiple
	 * specimens into the instrument with fewer operations. There are further scenarios
	 * typically encountered related to mounting and locating specimens inside an
	 * electron microscope, a few examples follow:
	 * * A nanoparticle on a copper grid. The copper grid is the holder.
	 * This grid itself is fixed to a stage.
	 * * An atom probe specimen fixed in a stub. In this case the stub can be
	 * considered the holder, while the cryostat temperature control unit is
	 * a component of the stage.
	 * * For in-situ experiments with e.g. chips with read-out electronics
	 * as actuators, the chips are again placed in a larger unit. A typical
	 * example are in-situ experiments using e.g. the tools of `Protochips <https://www.protochips.com>`_.
	 * * Other examples are (quasi) in-situ experiments where experimentalists
	 * anneal or deform the specimen via e.g. in-situ tensile testing machines
	 * which are mounted on the specimen holder.
	 * For specific details and inspiration about stages in electron microscopes:
	 * * `Holders with multiple axes <https://www.nanotechnik.com/e5as.html>`_
	 * * `Chip-based designs <https://www.protochips.com/products/fusion/fusion-select-components/>`_
	 * * `Further chip-based designs <https://www.nanoprobetech.com/about>`_
	 * * `Stages in transmission electron microscopy <https://doi.org/10.1007/978-3-662-14824-2>`_ (page 103, table 4.2)
	 * * `Further stages in transmission electron microscopy <https://doi.org/10.1007/978-1-4757-2519-3>`_ (page 124ff)
	 * * `Specimens in atom probe <https://doi.org/10.1007/978-1-4614-8721-0>`_ (page 47ff)
	 * * `Exemplar micro-manipulators <https://nano.oxinst.com/products/omniprobe/omniprobe-200>`_
	 *
	 * @return  the value.
	 */
	public NXmanipulator getStageid();

	/**
	 * Stages in an electron microscope are multi-functional devices.
	 * Stages enable experimentalists the application of controlled external stimuli
	 * on the specimen. Modern stages realize a hierarchy of components.
	 * A multi-axial tilt rotation holder is a good example where the control of
	 * each degree of freedom is technically implemented via providing instances
	 * of e.g. :ref:`NXpositioner` or :ref:`NXactuator` that achieve the rotating
	 * and positioning of the specimen.
	 * The physical process of mounting a specimen on a stage in practice often
	 * comes with an own hierarchy of fixtures to bridge e.g. length scales technically.
	 * An example from atom probe microscopy is that researchers may work
	 * with wire samples which are clipped into a larger fixing unit to enable
	 * careful specimen handling. Alternatively, a microtip is a silicon post
	 * upon which e.g. an atom probe specimen is mounted. Multiple of such microtips
	 * are then grouped into a microtip array to conveniently enable loading of multiple
	 * specimens into the instrument with fewer operations. There are further scenarios
	 * typically encountered related to mounting and locating specimens inside an
	 * electron microscope, a few examples follow:
	 * * A nanoparticle on a copper grid. The copper grid is the holder.
	 * This grid itself is fixed to a stage.
	 * * An atom probe specimen fixed in a stub. In this case the stub can be
	 * considered the holder, while the cryostat temperature control unit is
	 * a component of the stage.
	 * * For in-situ experiments with e.g. chips with read-out electronics
	 * as actuators, the chips are again placed in a larger unit. A typical
	 * example are in-situ experiments using e.g. the tools of `Protochips <https://www.protochips.com>`_.
	 * * Other examples are (quasi) in-situ experiments where experimentalists
	 * anneal or deform the specimen via e.g. in-situ tensile testing machines
	 * which are mounted on the specimen holder.
	 * For specific details and inspiration about stages in electron microscopes:
	 * * `Holders with multiple axes <https://www.nanotechnik.com/e5as.html>`_
	 * * `Chip-based designs <https://www.protochips.com/products/fusion/fusion-select-components/>`_
	 * * `Further chip-based designs <https://www.nanoprobetech.com/about>`_
	 * * `Stages in transmission electron microscopy <https://doi.org/10.1007/978-3-662-14824-2>`_ (page 103, table 4.2)
	 * * `Further stages in transmission electron microscopy <https://doi.org/10.1007/978-1-4757-2519-3>`_ (page 124ff)
	 * * `Specimens in atom probe <https://doi.org/10.1007/978-1-4614-8721-0>`_ (page 47ff)
	 * * `Exemplar micro-manipulators <https://nano.oxinst.com/products/omniprobe/omniprobe-200>`_
	 *
	 * @param stageidGroup the stageidGroup
	 */
	public void setStageid(NXmanipulator stageidGroup);

	/**
	 * In contrast to the stage, the nanoprobe is an additional manipulator that is a specifically
	 * frequently found component of FIB/SEM instruments. A nanoprobe is used to pick up and
	 * relocated portions of the specimen that have been cut off during site-specific lift-outs
	 * and specimen preparation.
	 *
	 * @return  the value.
	 */
	public NXmanipulator getNanoprobeid();

	/**
	 * In contrast to the stage, the nanoprobe is an additional manipulator that is a specifically
	 * frequently found component of FIB/SEM instruments. A nanoprobe is used to pick up and
	 * relocated portions of the specimen that have been cut off during site-specific lift-outs
	 * and specimen preparation.
	 *
	 * @param nanoprobeidGroup the nanoprobeidGroup
	 */
	public void setNanoprobeid(NXmanipulator nanoprobeidGroup);

	/**
	 * Gas injection systems (GIS) are components of microscopes that are equipped with focused-ion beam
	 * capabilities. The component is used to introduce reactive neutral gases to the sample surface for
	 * enhanced etching, preferential etching, or material deposition.
	 *
	 * @return  the value.
	 */
	public NXcomponent getGas_injector();

	/**
	 * Gas injection systems (GIS) are components of microscopes that are equipped with focused-ion beam
	 * capabilities. The component is used to introduce reactive neutral gases to the sample surface for
	 * enhanced etching, preferential etching, or material deposition.
	 *
	 * @param gas_injectorGroup the gas_injectorGroup
	 */
	public void setGas_injector(NXcomponent gas_injectorGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXpump getPump();

	/**
	 *
	 * @param pumpGroup the pumpGroup
	 */
	public void setPump(NXpump pumpGroup);

	/**
	 * Get a NXpump node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpump for that node.
	 */
	public NXpump getPump(String name);

	/**
	 * Set a NXpump node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param pump the value to set
	 */
	public void setPump(String name, NXpump pump);

	/**
	 * Get all NXpump nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpump for that node.
	 */
	public Map<String, NXpump> getAllPump();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param pump the child nodes to add
	 */

	public void setAllPump(Map<String, NXpump> pump);


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


}
