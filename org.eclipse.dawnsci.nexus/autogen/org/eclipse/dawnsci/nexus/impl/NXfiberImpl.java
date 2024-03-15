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

import org.eclipse.dawnsci.nexus.*;

/**
 * An optical fiber, e.g. glass fiber.
 * Specify the quantities that define the fiber. Fiber optics are described in
 * detail [here](https://www.photonics.com/Article.aspx?AID=25151&PID=4), for
 * example.

 */
public class NXfiberImpl extends NXobjectImpl implements NXfiber {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE);

	public NXfiberImpl() {
		super();
	}

	public NXfiberImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfiber.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FIBER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getDescription() {
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
	public IDataset getType() {
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
	public IDataset getDispersion_type() {
		return getDataset(NX_DISPERSION_TYPE);
	}

	@Override
	public String getDispersion_typeScalar() {
		return getString(NX_DISPERSION_TYPE);
	}

	@Override
	public DataNode setDispersion_type(IDataset dispersion_typeDataset) {
		return setDataset(NX_DISPERSION_TYPE, dispersion_typeDataset);
	}

	@Override
	public DataNode setDispersion_typeScalar(String dispersion_typeValue) {
		return setString(NX_DISPERSION_TYPE, dispersion_typeValue);
	}

	@Override
	public IDataset getDispersion() {
		return getDataset(NX_DISPERSION);
	}

	@Override
	public Double getDispersionScalar() {
		return getDouble(NX_DISPERSION);
	}

	@Override
	public DataNode setDispersion(IDataset dispersionDataset) {
		return setDataset(NX_DISPERSION, dispersionDataset);
	}

	@Override
	public DataNode setDispersionScalar(Double dispersionValue) {
		return setField(NX_DISPERSION, dispersionValue);
	}

	@Override
	public NXsample getCore() {
		// dataNodeName = NX_CORE
		return getChild("core", NXsample.class);
	}

	@Override
	public void setCore(NXsample coreGroup) {
		putChild("core", coreGroup);
	}

	@Override
	public NXsample getCladding() {
		// dataNodeName = NX_CLADDING
		return getChild("cladding", NXsample.class);
	}

	@Override
	public void setCladding(NXsample claddingGroup) {
		putChild("cladding", claddingGroup);
	}

	@Override
	public NXsample getCoating() {
		// dataNodeName = NX_COATING
		return getChild("coating", NXsample.class);
	}

	@Override
	public void setCoating(NXsample coatingGroup) {
		putChild("coating", coatingGroup);
	}

	@Override
	public IDataset getLength() {
		return getDataset(NX_LENGTH);
	}

	@Override
	public Double getLengthScalar() {
		return getDouble(NX_LENGTH);
	}

	@Override
	public DataNode setLength(IDataset lengthDataset) {
		return setDataset(NX_LENGTH, lengthDataset);
	}

	@Override
	public DataNode setLengthScalar(Double lengthValue) {
		return setField(NX_LENGTH, lengthValue);
	}

	@Override
	public IDataset getSpectral_range() {
		return getDataset(NX_SPECTRAL_RANGE);
	}

	@Override
	public Double getSpectral_rangeScalar() {
		return getDouble(NX_SPECTRAL_RANGE);
	}

	@Override
	public DataNode setSpectral_range(IDataset spectral_rangeDataset) {
		return setDataset(NX_SPECTRAL_RANGE, spectral_rangeDataset);
	}

	@Override
	public DataNode setSpectral_rangeScalar(Double spectral_rangeValue) {
		return setField(NX_SPECTRAL_RANGE, spectral_rangeValue);
	}

	@Override
	public String getSpectral_rangeAttributeUnits() {
		return getAttrString(NX_SPECTRAL_RANGE, NX_SPECTRAL_RANGE_ATTRIBUTE_UNITS);
	}

	@Override
	public void setSpectral_rangeAttributeUnits(String unitsValue) {
		setAttribute(NX_SPECTRAL_RANGE, NX_SPECTRAL_RANGE_ATTRIBUTE_UNITS, unitsValue);
	}

	@Override
	public IDataset getTransfer_rate() {
		return getDataset(NX_TRANSFER_RATE);
	}

	@Override
	public Double getTransfer_rateScalar() {
		return getDouble(NX_TRANSFER_RATE);
	}

	@Override
	public DataNode setTransfer_rate(IDataset transfer_rateDataset) {
		return setDataset(NX_TRANSFER_RATE, transfer_rateDataset);
	}

	@Override
	public DataNode setTransfer_rateScalar(Double transfer_rateValue) {
		return setField(NX_TRANSFER_RATE, transfer_rateValue);
	}

	@Override
	public String getTransfer_rateAttributeUnits() {
		return getAttrString(NX_TRANSFER_RATE, NX_TRANSFER_RATE_ATTRIBUTE_UNITS);
	}

	@Override
	public void setTransfer_rateAttributeUnits(String unitsValue) {
		setAttribute(NX_TRANSFER_RATE, NX_TRANSFER_RATE_ATTRIBUTE_UNITS, unitsValue);
	}

	@Override
	public IDataset getNumerical_aperture() {
		return getDataset(NX_NUMERICAL_APERTURE);
	}

	@Override
	public Double getNumerical_apertureScalar() {
		return getDouble(NX_NUMERICAL_APERTURE);
	}

	@Override
	public DataNode setNumerical_aperture(IDataset numerical_apertureDataset) {
		return setDataset(NX_NUMERICAL_APERTURE, numerical_apertureDataset);
	}

	@Override
	public DataNode setNumerical_apertureScalar(Double numerical_apertureValue) {
		return setField(NX_NUMERICAL_APERTURE, numerical_apertureValue);
	}

	@Override
	public IDataset getAttenuation() {
		return getDataset(NX_ATTENUATION);
	}

	@Override
	public Double getAttenuationScalar() {
		return getDouble(NX_ATTENUATION);
	}

	@Override
	public DataNode setAttenuation(IDataset attenuationDataset) {
		return setDataset(NX_ATTENUATION, attenuationDataset);
	}

	@Override
	public DataNode setAttenuationScalar(Double attenuationValue) {
		return setField(NX_ATTENUATION, attenuationValue);
	}

	@Override
	public String getAttenuationAttributeUnits() {
		return getAttrString(NX_ATTENUATION, NX_ATTENUATION_ATTRIBUTE_UNITS);
	}

	@Override
	public void setAttenuationAttributeUnits(String unitsValue) {
		setAttribute(NX_ATTENUATION, NX_ATTENUATION_ATTRIBUTE_UNITS, unitsValue);
	}

	@Override
	public IDataset getPower_loss() {
		return getDataset(NX_POWER_LOSS);
	}

	@Override
	public Double getPower_lossScalar() {
		return getDouble(NX_POWER_LOSS);
	}

	@Override
	public DataNode setPower_loss(IDataset power_lossDataset) {
		return setDataset(NX_POWER_LOSS, power_lossDataset);
	}

	@Override
	public DataNode setPower_lossScalar(Double power_lossValue) {
		return setField(NX_POWER_LOSS, power_lossValue);
	}

	@Override
	public IDataset getAcceptance_angle() {
		return getDataset(NX_ACCEPTANCE_ANGLE);
	}

	@Override
	public Double getAcceptance_angleScalar() {
		return getDouble(NX_ACCEPTANCE_ANGLE);
	}

	@Override
	public DataNode setAcceptance_angle(IDataset acceptance_angleDataset) {
		return setDataset(NX_ACCEPTANCE_ANGLE, acceptance_angleDataset);
	}

	@Override
	public DataNode setAcceptance_angleScalar(Double acceptance_angleValue) {
		return setField(NX_ACCEPTANCE_ANGLE, acceptance_angleValue);
	}

}
