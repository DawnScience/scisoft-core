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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for an electro-magnetic lens or a compound lens.
 * For :ref:`NXtransformations` the origin of the coordinate system is placed
 * in the center of the lens its pole piece, pinhole, or another point of reference.
 * The origin should be specified in the :ref:`NXtransformations`.
 * For details of electro-magnetic lenses in the literature see e.g.
 * * `L. Reimer: Scanning Electron Microscopy <https://doi.org/10.1007/978-3-540-38967-5>`_
 * * `P. Hawkes: Magnetic Electron Lenses <https://link.springer.com/book/10.1007/978-3-642-81516-4>`_
 * * `Y. Liao: Practical Electron Microscopy and Database <https://www.globalsino.com/EM/>`_

 */
public class NXelectromagnetic_lensImpl extends NXcomponentImpl implements NXelectromagnetic_lens {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXelectromagnetic_lensImpl() {
		super();
	}

	public NXelectromagnetic_lensImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXelectromagnetic_lens.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ELECTROMAGNETIC_LENS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public Dataset getPower_setting() {
		return getDataset(NX_POWER_SETTING);
	}

	@Override
	public Object getPower_settingScalar() {
		return getDataset(NX_POWER_SETTING).getObject();
	}

	@Override
	public DataNode setPower_setting(IDataset power_settingDataset) {
		return setDataset(NX_POWER_SETTING, power_settingDataset);
	}

	@Override
	public DataNode setPower_settingScalar(Object power_settingValue) {
		if (power_settingValue instanceof Number) {
			return setField(NX_POWER_SETTING, power_settingValue);
		} else if (power_settingValue instanceof String) {
			return setString(NX_POWER_SETTING, (String) power_settingValue);
		} else {
			throw new IllegalArgumentException("Value must be String or Number");
		}
	}

	@Override
	public Dataset getMode() {
		return getDataset(NX_MODE);
	}

	@Override
	public String getModeScalar() {
		return getString(NX_MODE);
	}

	@Override
	public DataNode setMode(IDataset modeDataset) {
		return setDataset(NX_MODE, modeDataset);
	}

	@Override
	public DataNode setModeScalar(String modeValue) {
		return setString(NX_MODE, modeValue);
	}

	@Override
	public Dataset getVoltage() {
		return getDataset(NX_VOLTAGE);
	}

	@Override
	public Number getVoltageScalar() {
		return getNumber(NX_VOLTAGE);
	}

	@Override
	public DataNode setVoltage(IDataset voltageDataset) {
		return setDataset(NX_VOLTAGE, voltageDataset);
	}

	@Override
	public DataNode setVoltageScalar(Number voltageValue) {
		return setField(NX_VOLTAGE, voltageValue);
	}

	@Override
	public Dataset getCurrent() {
		return getDataset(NX_CURRENT);
	}

	@Override
	public Number getCurrentScalar() {
		return getNumber(NX_CURRENT);
	}

	@Override
	public DataNode setCurrent(IDataset currentDataset) {
		return setDataset(NX_CURRENT, currentDataset);
	}

	@Override
	public DataNode setCurrentScalar(Number currentValue) {
		return setField(NX_CURRENT, currentValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public Dataset getNumber_of_poles() {
		return getDataset(NX_NUMBER_OF_POLES);
	}

	@Override
	public Long getNumber_of_polesScalar() {
		return getLong(NX_NUMBER_OF_POLES);
	}

	@Override
	public DataNode setNumber_of_poles(IDataset number_of_polesDataset) {
		return setDataset(NX_NUMBER_OF_POLES, number_of_polesDataset);
	}

	@Override
	public DataNode setNumber_of_polesScalar(Long number_of_polesValue) {
		return setField(NX_NUMBER_OF_POLES, number_of_polesValue);
	}

}
