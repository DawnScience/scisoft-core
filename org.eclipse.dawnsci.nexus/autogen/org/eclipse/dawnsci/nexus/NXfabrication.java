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
import org.eclipse.january.dataset.Dataset;

/**
 * Details about a component as it is defined by its manufacturer.
 *
 */
public interface NXfabrication extends NXobject {

	public static final String NX_VENDOR = "vendor";
	public static final String NX_MODEL = "model";
	public static final String NX_MODEL_ATTRIBUTE_VERSION = "version";
	public static final String NX_SERIAL_NUMBER = "serial_number";
	public static final String NX_CONSTRUCTION_DATE = "construction_date";
	public static final String NX_CAPABILITY = "capability";
	/**
	 * Company name of the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVendor();

	/**
	 * Company name of the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param vendorDataset the vendorDataset
	 */
	public DataNode setVendor(IDataset vendorDataset);

	/**
	 * Company name of the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getVendorScalar();

	/**
	 * Company name of the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param vendor the vendor
	 */
	public DataNode setVendorScalar(String vendorValue);

	/**
	 * Version or model of the component named by the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getModel();

	/**
	 * Version or model of the component named by the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * Version or model of the component named by the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * Version or model of the component named by the manufacturer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 * If it is possible that different versions exist, the value in this field should be made
	 * specific enough to resolve the version.
	 *
	 * @return  the value.
	 */
	public String getModelAttributeVersion();

	/**
	 * If it is possible that different versions exist, the value in this field should be made
	 * specific enough to resolve the version.
	 *
	 * @param versionValue the versionValue
	 */
	public void setModelAttributeVersion(String versionValue);

	/**
	 * Serial number of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSerial_number();

	/**
	 * Serial number of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param serial_numberDataset the serial_numberDataset
	 */
	public DataNode setSerial_number(IDataset serial_numberDataset);

	/**
	 * Serial number of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSerial_numberScalar();

	/**
	 * Serial number of the component.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param serial_number the serial_number
	 */
	public DataNode setSerial_numberScalar(String serial_numberValue);

	/**
	 * Datetime of component's initial construction. This refers to the date of
	 * first measurement after new construction or to the relocation date,
	 * if it describes a multicomponent/custom-build setup.
	 * Just the year is often sufficient, but if a full date/time is used,
	 * it is recommended to add an explicit time zone.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getConstruction_date();

	/**
	 * Datetime of component's initial construction. This refers to the date of
	 * first measurement after new construction or to the relocation date,
	 * if it describes a multicomponent/custom-build setup.
	 * Just the year is often sufficient, but if a full date/time is used,
	 * it is recommended to add an explicit time zone.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param construction_dateDataset the construction_dateDataset
	 */
	public DataNode setConstruction_date(IDataset construction_dateDataset);

	/**
	 * Datetime of component's initial construction. This refers to the date of
	 * first measurement after new construction or to the relocation date,
	 * if it describes a multicomponent/custom-build setup.
	 * Just the year is often sufficient, but if a full date/time is used,
	 * it is recommended to add an explicit time zone.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getConstruction_dateScalar();

	/**
	 * Datetime of component's initial construction. This refers to the date of
	 * first measurement after new construction or to the relocation date,
	 * if it describes a multicomponent/custom-build setup.
	 * Just the year is often sufficient, but if a full date/time is used,
	 * it is recommended to add an explicit time zone.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param construction_date the construction_date
	 */
	public DataNode setConstruction_dateScalar(Date construction_dateValue);

	/**
	 * Free-text list of functionalities which the component offers.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCapability();

	/**
	 * Free-text list of functionalities which the component offers.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param capabilityDataset the capabilityDataset
	 */
	public DataNode setCapability(IDataset capabilityDataset);

	/**
	 * Free-text list of functionalities which the component offers.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCapabilityScalar();

	/**
	 * Free-text list of functionalities which the component offers.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param capability the capability
	 */
	public DataNode setCapabilityScalar(String capabilityValue);

}
