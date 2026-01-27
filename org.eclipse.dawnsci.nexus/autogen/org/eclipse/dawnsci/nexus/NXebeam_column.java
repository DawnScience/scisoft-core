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
 * Base class for a set of components providing a controllable electron beam.
 * The idea behind defining :ref:`NXebeam_column` as an own base class vs. adding these
 * concepts in :ref:`NXem_instrument` is that the electron beam generating component
 * might be worthwhile to use also in other types of experiments.
 *
 */
public interface NXebeam_column extends NXcomponent {

	public static final String NX_OPERATION_MODE = "operation_mode";
	/**
	 * Tech-partner, microscope-, and control-software-specific name of the
	 * specific operation mode how the ebeam_column and its components are
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
	 * specific operation mode how the ebeam_column and its components are
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
	 * specific operation mode how the ebeam_column and its components are
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
	 * specific operation mode how the ebeam_column and its components are
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
	 * A physical part of an electron or ion microscope from which
	 * the particles that form the beam are emitted.
	 * The hardware for an electron source in an electron microscope
	 * may contain several components which affect the beam path.
	 * This concept is related to term `Source`_ of the EMglossary standard.
	 * .. _Source: https://purls.helmholtz-metadaten.de/emg/EMG_00000045
	 *
	 * @return  the value.
	 */
	public NXsource getElectron_source();

	/**
	 * A physical part of an electron or ion microscope from which
	 * the particles that form the beam are emitted.
	 * The hardware for an electron source in an electron microscope
	 * may contain several components which affect the beam path.
	 * This concept is related to term `Source`_ of the EMglossary standard.
	 * .. _Source: https://purls.helmholtz-metadaten.de/emg/EMG_00000045
	 *
	 * @param electron_sourceGroup the electron_sourceGroup
	 */
	public void setElectron_source(NXsource electron_sourceGroup);

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
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.</li>
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
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.</li>
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
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.</li>
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
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.</li>
	 * </ul>
	 *
	 * @param deflector the child nodes to add
	 */

	public void setAllDeflector(Map<String, NXdeflector> deflector);


	/**
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.
	 *
	 * @return  the value.
	 */
	public NXdeflector getBlankerid();

	/**
	 * A component for blanking the beam or generating pulsed electron beams.
	 * See e.g . `I. G. C. Weppelman et al. <https://doi.org/10.1016/j.ultramic.2017.10.002>`_
	 * or `Y. Liao <https://www.globalsino.com/EM/page2464.html>`_ for details.
	 *
	 * @param blankeridGroup the blankeridGroup
	 */
	public void setBlankerid(NXdeflector blankeridGroup);

	/**
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_
	 *
	 * @return  the value.
	 */
	public NXmonochromator getMonochromator();

	/**
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_
	 *
	 * @param monochromatorGroup the monochromatorGroup
	 */
	public void setMonochromator(NXmonochromator monochromatorGroup);

	/**
	 * Get a NXmonochromator node by name:
	 * <ul>
	 * <li>
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public NXmonochromator getMonochromator(String name);

	/**
	 * Set a NXmonochromator node by name:
	 * <ul>
	 * <li>
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param monochromator the value to set
	 */
	public void setMonochromator(String name, NXmonochromator monochromator);

	/**
	 * Get all NXmonochromator nodes:
	 * <ul>
	 * <li>
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXmonochromator for that node.
	 */
	public Map<String, NXmonochromator> getAllMonochromator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Device to improve energy resolution or chromatic aberration.
	 * Examples are Wien, $\textalpha$-, or $\Omega$- energy filter or `cc corrector
	 * like <https://www.ceos-gmbh.de/en/basics/cc-corrector>`_</li>
	 * </ul>
	 *
	 * @param monochromator the child nodes to add
	 */

	public void setAllMonochromator(Map<String, NXmonochromator> monochromator);

	// Unprocessed group:

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
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.
	 *
	 * @return  the value.
	 */
	public NXcomponent getCorrector_ax();

	/**
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.
	 *
	 * @param corrector_axGroup the corrector_axGroup
	 */
	public void setCorrector_ax(NXcomponent corrector_axGroup);

	/**
	 * Electron biprism as it is used e.g. for electron holography.
	 *
	 * @return  the value.
	 */
	public NXcomponent getBiprismid();

	/**
	 * Electron biprism as it is used e.g. for electron holography.
	 *
	 * @param biprismidGroup the biprismidGroup
	 */
	public void setBiprismid(NXcomponent biprismidGroup);

	/**
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_
	 *
	 * @return  the value.
	 */
	public NXcomponent getPhaseplateid();

	/**
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_
	 *
	 * @param phaseplateidGroup the phaseplateidGroup
	 */
	public void setPhaseplateid(NXcomponent phaseplateidGroup);

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
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021
	 *
	 * @return  the value.
	 */
	public NXbeam getBeam();

	/**
	 * Individual characterization results for the position, shape,
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021
	 *
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Get a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021</li>
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
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021</li>
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
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021</li>
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
	 * and characteristics of the electron beam at a given location.
	 * :ref:`NXtransformations` should be used to specify the location
	 * or the position at which details about the beam were probed.
	 * This concept is related to term `Electron Beam`_ of the EMglossary standard.
	 * .. _Electron Beam: https://purls.helmholtz-metadaten.de/emg/EMG_00000021</li>
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
	 * <li>
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.</li>
	 * <li>
	 * Electron biprism as it is used e.g. for electron holography.</li>
	 * <li>
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_</li>
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
	 * <li>
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.</li>
	 * <li>
	 * Electron biprism as it is used e.g. for electron holography.</li>
	 * <li>
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_</li>
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
	 * <li>
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.</li>
	 * <li>
	 * Electron biprism as it is used e.g. for electron holography.</li>
	 * <li>
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_</li>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcomponent for that node.
	 */
	public Map<String, NXcomponent> getAllComponent();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Component that reshapes an ellipse-shaped electron beam into a circular one.
	 * * `L. Reimer 1998, Springer, 1998 <https://dx.doi.org/10.1007/978-3-540-3896>`_
	 * * `M. Tanaka et al., Electron Microscopy Glossary, 2024 <https://www.jeol.com/words/semterms/20201020.111014.php#gsc.tab=0>`_
	 * Stigmator is an exact synonym.</li>
	 * <li>
	 * Electron biprism as it is used e.g. for electron holography.</li>
	 * <li>
	 * Device that causes a change in the phase of an electron wave.
	 * * `M. Malac et al. <https://doi.org/10.1093/jmicro/dfaa070>`_
	 * * `R. R. Schröder et al. <https://www.lem.kit.edu/152.php>`_</li>
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
