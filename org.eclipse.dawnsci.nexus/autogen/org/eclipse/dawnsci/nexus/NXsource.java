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

import java.util.Date;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * The neutron or x-ray storage ring/facility.
 * 
 */
public interface NXsource extends NXobject {

	public static final String NX_DISTANCE = "distance";
	public static final String NX_NAME = "name";
	public static final String NX_NAME_ATTRIBUTE_SHORT_NAME = "short_name";
	public static final String NX_TYPE = "type";
	public static final String NX_PROBE = "probe";
	public static final String NX_POWER = "power";
	public static final String NX_EMITTANCE_X = "emittance_x";
	public static final String NX_EMITTANCE_Y = "emittance_y";
	public static final String NX_SIGMA_X = "sigma_x";
	public static final String NX_SIGMA_Y = "sigma_y";
	public static final String NX_FLUX = "flux";
	public static final String NX_ENERGY = "energy";
	public static final String NX_CURRENT = "current";
	public static final String NX_VOLTAGE = "voltage";
	public static final String NX_FREQUENCY = "frequency";
	public static final String NX_PERIOD = "period";
	public static final String NX_TARGET_MATERIAL = "target_material";
	public static final String NX_NUMBER_OF_BUNCHES = "number_of_bunches";
	public static final String NX_BUNCH_LENGTH = "bunch_length";
	public static final String NX_BUNCH_DISTANCE = "bunch_distance";
	public static final String NX_PULSE_WIDTH = "pulse_width";
	public static final String NX_MODE = "mode";
	public static final String NX_TOP_UP = "top_up";
	public static final String NX_LAST_FILL = "last_fill";
	public static final String NX_LAST_FILL_ATTRIBUTE_TIME = "time";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Effective distance from sample
	 * Distance as seen by radiation from sample. This number should be negative
	 * to signify that it is upstream of the sample.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDistance();
	
	/**
	 * Effective distance from sample
	 * Distance as seen by radiation from sample. This number should be negative
	 * to signify that it is upstream of the sample.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Effective distance from sample
	 * Distance as seen by radiation from sample. This number should be negative
	 * to signify that it is upstream of the sample.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * Effective distance from sample
	 * Distance as seen by radiation from sample. This number should be negative
	 * to signify that it is upstream of the sample.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * Name of source
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Name of source
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of source
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of source
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * short name for source, perhaps the acronym
	 * 
	 * @return  the value.
	 */
	public String getNameAttributeShort_name();
	
	/**
	 * short name for source, perhaps the acronym
	 * 
	 * @param short_nameValue the short_nameValue
	 */
	public void setNameAttributeShort_name(String short_nameValue);

	/**
	 * type of radiation source (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Spallation Neutron Source</b> </li>
	 * <li><b>Pulsed Reactor Neutron Source</b> </li>
	 * <li><b>Reactor Neutron Source</b> </li>
	 * <li><b>Synchrotron X-ray Source</b> </li>
	 * <li><b>Pulsed Muon Source</b> </li>
	 * <li><b>Rotating Anode X-ray</b> </li>
	 * <li><b>Fixed Tube X-ray</b> </li>
	 * <li><b>UV Laser</b> </li>
	 * <li><b>Free-Electron Laser</b> </li>
	 * <li><b>Optical Laser</b> </li>
	 * <li><b>Ion Source</b> </li>
	 * <li><b>UV Plasma Source</b> </li>
	 * <li><b>Metal Jet X-ray</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * type of radiation source (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Spallation Neutron Source</b> </li>
	 * <li><b>Pulsed Reactor Neutron Source</b> </li>
	 * <li><b>Reactor Neutron Source</b> </li>
	 * <li><b>Synchrotron X-ray Source</b> </li>
	 * <li><b>Pulsed Muon Source</b> </li>
	 * <li><b>Rotating Anode X-ray</b> </li>
	 * <li><b>Fixed Tube X-ray</b> </li>
	 * <li><b>UV Laser</b> </li>
	 * <li><b>Free-Electron Laser</b> </li>
	 * <li><b>Optical Laser</b> </li>
	 * <li><b>Ion Source</b> </li>
	 * <li><b>UV Plasma Source</b> </li>
	 * <li><b>Metal Jet X-ray</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * type of radiation source (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Spallation Neutron Source</b> </li>
	 * <li><b>Pulsed Reactor Neutron Source</b> </li>
	 * <li><b>Reactor Neutron Source</b> </li>
	 * <li><b>Synchrotron X-ray Source</b> </li>
	 * <li><b>Pulsed Muon Source</b> </li>
	 * <li><b>Rotating Anode X-ray</b> </li>
	 * <li><b>Fixed Tube X-ray</b> </li>
	 * <li><b>UV Laser</b> </li>
	 * <li><b>Free-Electron Laser</b> </li>
	 * <li><b>Optical Laser</b> </li>
	 * <li><b>Ion Source</b> </li>
	 * <li><b>UV Plasma Source</b> </li>
	 * <li><b>Metal Jet X-ray</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * type of radiation source (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Spallation Neutron Source</b> </li>
	 * <li><b>Pulsed Reactor Neutron Source</b> </li>
	 * <li><b>Reactor Neutron Source</b> </li>
	 * <li><b>Synchrotron X-ray Source</b> </li>
	 * <li><b>Pulsed Muon Source</b> </li>
	 * <li><b>Rotating Anode X-ray</b> </li>
	 * <li><b>Fixed Tube X-ray</b> </li>
	 * <li><b>UV Laser</b> </li>
	 * <li><b>Free-Electron Laser</b> </li>
	 * <li><b>Optical Laser</b> </li>
	 * <li><b>Ion Source</b> </li>
	 * <li><b>UV Plasma Source</b> </li>
	 * <li><b>Metal Jet X-ray</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * type of radiation probe (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>neutron</b> </li>
	 * <li><b>x-ray</b> </li>
	 * <li><b>muon</b> </li>
	 * <li><b>electron</b> </li>
	 * <li><b>ultraviolet</b> </li>
	 * <li><b>visible light</b> </li>
	 * <li><b>positron</b> </li>
	 * <li><b>proton</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getProbe();
	
	/**
	 * type of radiation probe (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>neutron</b> </li>
	 * <li><b>x-ray</b> </li>
	 * <li><b>muon</b> </li>
	 * <li><b>electron</b> </li>
	 * <li><b>ultraviolet</b> </li>
	 * <li><b>visible light</b> </li>
	 * <li><b>positron</b> </li>
	 * <li><b>proton</b> </li></ul></p>
	 * </p>
	 * 
	 * @param probeDataset the probeDataset
	 */
	public DataNode setProbe(IDataset probeDataset);

	/**
	 * type of radiation probe (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>neutron</b> </li>
	 * <li><b>x-ray</b> </li>
	 * <li><b>muon</b> </li>
	 * <li><b>electron</b> </li>
	 * <li><b>ultraviolet</b> </li>
	 * <li><b>visible light</b> </li>
	 * <li><b>positron</b> </li>
	 * <li><b>proton</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getProbeScalar();

	/**
	 * type of radiation probe (pick one from the enumerated list and spell exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>neutron</b> </li>
	 * <li><b>x-ray</b> </li>
	 * <li><b>muon</b> </li>
	 * <li><b>electron</b> </li>
	 * <li><b>ultraviolet</b> </li>
	 * <li><b>visible light</b> </li>
	 * <li><b>positron</b> </li>
	 * <li><b>proton</b> </li></ul></p>
	 * </p>
	 * 
	 * @param probe the probe
	 */
	public DataNode setProbeScalar(String probeValue);

	/**
	 * Source power
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPower();
	
	/**
	 * Source power
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 * 
	 * @param powerDataset the powerDataset
	 */
	public DataNode setPower(IDataset powerDataset);

	/**
	 * Source power
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPowerScalar();

	/**
	 * Source power
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_POWER
	 * </p>
	 * 
	 * @param power the power
	 */
	public DataNode setPowerScalar(Double powerValue);

	/**
	 * Source emittance (nm-rad) in X (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEmittance_x();
	
	/**
	 * Source emittance (nm-rad) in X (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @param emittance_xDataset the emittance_xDataset
	 */
	public DataNode setEmittance_x(IDataset emittance_xDataset);

	/**
	 * Source emittance (nm-rad) in X (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEmittance_xScalar();

	/**
	 * Source emittance (nm-rad) in X (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @param emittance_x the emittance_x
	 */
	public DataNode setEmittance_xScalar(Double emittance_xValue);

	/**
	 * Source emittance (nm-rad) in Y (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEmittance_y();
	
	/**
	 * Source emittance (nm-rad) in Y (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @param emittance_yDataset the emittance_yDataset
	 */
	public DataNode setEmittance_y(IDataset emittance_yDataset);

	/**
	 * Source emittance (nm-rad) in Y (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEmittance_yScalar();

	/**
	 * Source emittance (nm-rad) in Y (horizontal) direction.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_EMITTANCE
	 * </p>
	 * 
	 * @param emittance_y the emittance_y
	 */
	public DataNode setEmittance_yScalar(Double emittance_yValue);

	/**
	 * particle beam size in x
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSigma_x();
	
	/**
	 * particle beam size in x
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param sigma_xDataset the sigma_xDataset
	 */
	public DataNode setSigma_x(IDataset sigma_xDataset);

	/**
	 * particle beam size in x
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSigma_xScalar();

	/**
	 * particle beam size in x
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param sigma_x the sigma_x
	 */
	public DataNode setSigma_xScalar(Double sigma_xValue);

	/**
	 * particle beam size in y
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSigma_y();
	
	/**
	 * particle beam size in y
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param sigma_yDataset the sigma_yDataset
	 */
	public DataNode setSigma_y(IDataset sigma_yDataset);

	/**
	 * particle beam size in y
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSigma_yScalar();

	/**
	 * particle beam size in y
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param sigma_y the sigma_y
	 */
	public DataNode setSigma_yScalar(Double sigma_yValue);

	/**
	 * Source intensity/area (example: s-1 cm-2)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFlux();
	
	/**
	 * Source intensity/area (example: s-1 cm-2)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * </p>
	 * 
	 * @param fluxDataset the fluxDataset
	 */
	public DataNode setFlux(IDataset fluxDataset);

	/**
	 * Source intensity/area (example: s-1 cm-2)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFluxScalar();

	/**
	 * Source intensity/area (example: s-1 cm-2)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FLUX
	 * </p>
	 * 
	 * @param flux the flux
	 */
	public DataNode setFluxScalar(Double fluxValue);

	/**
	 * Source energy.
	 * For storage rings, this would be the particle beam energy.
	 * For X-ray tubes, this would be the excitation voltage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEnergy();
	
	/**
	 * Source energy.
	 * For storage rings, this would be the particle beam energy.
	 * For X-ray tubes, this would be the excitation voltage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energyDataset the energyDataset
	 */
	public DataNode setEnergy(IDataset energyDataset);

	/**
	 * Source energy.
	 * For storage rings, this would be the particle beam energy.
	 * For X-ray tubes, this would be the excitation voltage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getEnergyScalar();

	/**
	 * Source energy.
	 * For storage rings, this would be the particle beam energy.
	 * For X-ray tubes, this would be the excitation voltage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ENERGY
	 * </p>
	 * 
	 * @param energy the energy
	 */
	public DataNode setEnergyScalar(Double energyValue);

	/**
	 * Accelerator, X-ray tube, or storage ring current
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCurrent();
	
	/**
	 * Accelerator, X-ray tube, or storage ring current
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param currentDataset the currentDataset
	 */
	public DataNode setCurrent(IDataset currentDataset);

	/**
	 * Accelerator, X-ray tube, or storage ring current
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getCurrentScalar();

	/**
	 * Accelerator, X-ray tube, or storage ring current
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param current the current
	 */
	public DataNode setCurrentScalar(Double currentValue);

	/**
	 * Accelerator voltage
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getVoltage();
	
	/**
	 * Accelerator voltage
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param voltageDataset the voltageDataset
	 */
	public DataNode setVoltage(IDataset voltageDataset);

	/**
	 * Accelerator voltage
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getVoltageScalar();

	/**
	 * Accelerator voltage
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param voltage the voltage
	 */
	public DataNode setVoltageScalar(Double voltageValue);

	/**
	 * Frequency of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFrequency();
	
	/**
	 * Frequency of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param frequencyDataset the frequencyDataset
	 */
	public DataNode setFrequency(IDataset frequencyDataset);

	/**
	 * Frequency of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFrequencyScalar();

	/**
	 * Frequency of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param frequency the frequency
	 */
	public DataNode setFrequencyScalar(Double frequencyValue);

	/**
	 * Period of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PERIOD
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPeriod();
	
	/**
	 * Period of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PERIOD
	 * </p>
	 * 
	 * @param periodDataset the periodDataset
	 */
	public DataNode setPeriod(IDataset periodDataset);

	/**
	 * Period of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PERIOD
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPeriodScalar();

	/**
	 * Period of pulsed source
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PERIOD
	 * </p>
	 * 
	 * @param period the period
	 */
	public DataNode setPeriodScalar(Double periodValue);

	/**
	 * Pulsed source target material
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Ta</b> </li>
	 * <li><b>W</b> </li>
	 * <li><b>depleted_U</b> </li>
	 * <li><b>enriched_U</b> </li>
	 * <li><b>Hg</b> </li>
	 * <li><b>Pb</b> </li>
	 * <li><b>C</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTarget_material();
	
	/**
	 * Pulsed source target material
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Ta</b> </li>
	 * <li><b>W</b> </li>
	 * <li><b>depleted_U</b> </li>
	 * <li><b>enriched_U</b> </li>
	 * <li><b>Hg</b> </li>
	 * <li><b>Pb</b> </li>
	 * <li><b>C</b> </li></ul></p>
	 * </p>
	 * 
	 * @param target_materialDataset the target_materialDataset
	 */
	public DataNode setTarget_material(IDataset target_materialDataset);

	/**
	 * Pulsed source target material
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Ta</b> </li>
	 * <li><b>W</b> </li>
	 * <li><b>depleted_U</b> </li>
	 * <li><b>enriched_U</b> </li>
	 * <li><b>Hg</b> </li>
	 * <li><b>Pb</b> </li>
	 * <li><b>C</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTarget_materialScalar();

	/**
	 * Pulsed source target material
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Ta</b> </li>
	 * <li><b>W</b> </li>
	 * <li><b>depleted_U</b> </li>
	 * <li><b>enriched_U</b> </li>
	 * <li><b>Hg</b> </li>
	 * <li><b>Pb</b> </li>
	 * <li><b>C</b> </li></ul></p>
	 * </p>
	 * 
	 * @param target_material the target_material
	 */
	public DataNode setTarget_materialScalar(String target_materialValue);

	/**
	 * any source/facility related messages/events that
	 * occurred during the experiment
	 * 
	 * @return  the value.
	 */
	public NXnote getNotes();
	
	/**
	 * any source/facility related messages/events that
	 * occurred during the experiment
	 * 
	 * @param notesGroup the notesGroup
	 */
	public void setNotes(NXnote notesGroup);

	/**
	 * For storage rings, description of the bunch pattern.
	 * This is useful to describe irregular bunch patterns.
	 * 
	 * @return  the value.
	 */
	public NXdata getBunch_pattern();
	
	/**
	 * For storage rings, description of the bunch pattern.
	 * This is useful to describe irregular bunch patterns.
	 * 
	 * @param bunch_patternGroup the bunch_patternGroup
	 */
	public void setBunch_pattern(NXdata bunch_patternGroup);

	/**
	 * For storage rings, the number of bunches in use.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getNumber_of_bunches();
	
	/**
	 * For storage rings, the number of bunches in use.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param number_of_bunchesDataset the number_of_bunchesDataset
	 */
	public DataNode setNumber_of_bunches(IDataset number_of_bunchesDataset);

	/**
	 * For storage rings, the number of bunches in use.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getNumber_of_bunchesScalar();

	/**
	 * For storage rings, the number of bunches in use.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param number_of_bunches the number_of_bunches
	 */
	public DataNode setNumber_of_bunchesScalar(Long number_of_bunchesValue);

	/**
	 * For storage rings, temporal length of the bunch
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBunch_length();
	
	/**
	 * For storage rings, temporal length of the bunch
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param bunch_lengthDataset the bunch_lengthDataset
	 */
	public DataNode setBunch_length(IDataset bunch_lengthDataset);

	/**
	 * For storage rings, temporal length of the bunch
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBunch_lengthScalar();

	/**
	 * For storage rings, temporal length of the bunch
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param bunch_length the bunch_length
	 */
	public DataNode setBunch_lengthScalar(Double bunch_lengthValue);

	/**
	 * For storage rings, time between bunches
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBunch_distance();
	
	/**
	 * For storage rings, time between bunches
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param bunch_distanceDataset the bunch_distanceDataset
	 */
	public DataNode setBunch_distance(IDataset bunch_distanceDataset);

	/**
	 * For storage rings, time between bunches
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBunch_distanceScalar();

	/**
	 * For storage rings, time between bunches
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param bunch_distance the bunch_distance
	 */
	public DataNode setBunch_distanceScalar(Double bunch_distanceValue);

	/**
	 * temporal width of source pulse
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulse_width();
	
	/**
	 * temporal width of source pulse
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param pulse_widthDataset the pulse_widthDataset
	 */
	public DataNode setPulse_width(IDataset pulse_widthDataset);

	/**
	 * temporal width of source pulse
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPulse_widthScalar();

	/**
	 * temporal width of source pulse
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param pulse_width the pulse_width
	 */
	public DataNode setPulse_widthScalar(Double pulse_widthValue);

	/**
	 * source pulse shape
	 * 
	 * @return  the value.
	 */
	public NXdata getPulse_shape();
	
	/**
	 * source pulse shape
	 * 
	 * @param pulse_shapeGroup the pulse_shapeGroup
	 */
	public void setPulse_shape(NXdata pulse_shapeGroup);

	/**
	 * source operating mode
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Single Bunch</b> 
	 * for storage rings</li>
	 * <li><b>Multi Bunch</b> 
	 * for storage rings</li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getMode();
	
	/**
	 * source operating mode
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Single Bunch</b> 
	 * for storage rings</li>
	 * <li><b>Multi Bunch</b> 
	 * for storage rings</li></ul></p>
	 * </p>
	 * 
	 * @param modeDataset the modeDataset
	 */
	public DataNode setMode(IDataset modeDataset);

	/**
	 * source operating mode
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Single Bunch</b> 
	 * for storage rings</li>
	 * <li><b>Multi Bunch</b> 
	 * for storage rings</li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getModeScalar();

	/**
	 * source operating mode
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Single Bunch</b> 
	 * for storage rings</li>
	 * <li><b>Multi Bunch</b> 
	 * for storage rings</li></ul></p>
	 * </p>
	 * 
	 * @param mode the mode
	 */
	public DataNode setModeScalar(String modeValue);

	/**
	 * Is the synchrotron operating in top_up mode?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTop_up();
	
	/**
	 * Is the synchrotron operating in top_up mode?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param top_upDataset the top_upDataset
	 */
	public DataNode setTop_up(IDataset top_upDataset);

	/**
	 * Is the synchrotron operating in top_up mode?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Boolean getTop_upScalar();

	/**
	 * Is the synchrotron operating in top_up mode?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param top_up the top_up
	 */
	public DataNode setTop_upScalar(Boolean top_upValue);

	/**
	 * For storage rings, the current at the end of the most recent injection.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLast_fill();
	
	/**
	 * For storage rings, the current at the end of the most recent injection.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param last_fillDataset the last_fillDataset
	 */
	public DataNode setLast_fill(IDataset last_fillDataset);

	/**
	 * For storage rings, the current at the end of the most recent injection.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getLast_fillScalar();

	/**
	 * For storage rings, the current at the end of the most recent injection.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param last_fill the last_fill
	 */
	public DataNode setLast_fillScalar(Number last_fillValue);

	/**
	 * date and time of the most recent injection.
	 * 
	 * @return  the value.
	 */
	public Date getLast_fillAttributeTime();
	
	/**
	 * date and time of the most recent injection.
	 * 
	 * @param timeValue the timeValue
	 */
	public void setLast_fillAttributeTime(Date timeValue);

	/**
	 * "Engineering" location of source
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * "Engineering" location of source
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * The wavelength or energy distribution of the source
	 * 
	 * @return  the value.
	 */
	public NXdata getDistribution();
	
	/**
	 * The wavelength or energy distribution of the source
	 * 
	 * @param distributionGroup the distributionGroup
	 */
	public void setDistribution(NXdata distributionGroup);

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
