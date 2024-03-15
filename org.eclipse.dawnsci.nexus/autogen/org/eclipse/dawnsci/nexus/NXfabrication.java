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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Details about a component as defined by its manufacturer.
 *
 */
public interface NXfabrication extends NXobject {

	public static final String NX_VENDOR = "vendor";
	public static final String NX_MODEL = "model";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_CAPABILITY = "capability";
	/**
	 * Company name of the manufacturer.
	 *
	 * @return  the value.
	 */
	public IDataset getVendor();

	/**
	 * Company name of the manufacturer.
	 *
	 * @param vendorDataset the vendorDataset
	 */
	public DataNode setVendor(IDataset vendorDataset);

	/**
	 * Company name of the manufacturer.
	 *
	 * @return  the value.
	 */
	public String getVendorScalar();

	/**
	 * Company name of the manufacturer.
	 *
	 * @param vendor the vendor
	 */
	public DataNode setVendorScalar(String vendorValue);

	/**
	 * Version or model of the component named by the manufacturer.
	 *
	 * @return  the value.
	 */
	public IDataset getModel();

	/**
	 * Version or model of the component named by the manufacturer.
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * Version or model of the component named by the manufacturer.
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * Version or model of the component named by the manufacturer.
	 *
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 * Ideally, (globally) unique persistent identifier, i.e.
	 * a serial number or hash identifier of the component.
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier();

	/**
	 * Ideally, (globally) unique persistent identifier, i.e.
	 * a serial number or hash identifier of the component.
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Ideally, (globally) unique persistent identifier, i.e.
	 * a serial number or hash identifier of the component.
	 *
	 * @return  the value.
	 */
	public String getIdentifierScalar();

	/**
	 * Ideally, (globally) unique persistent identifier, i.e.
	 * a serial number or hash identifier of the component.
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(String identifierValue);

	/**
	 * Free-text list with eventually multiple terms of
	 * functionalities which the component offers.
	 *
	 * @return  the value.
	 */
	public IDataset getCapability();

	/**
	 * Free-text list with eventually multiple terms of
	 * functionalities which the component offers.
	 *
	 * @param capabilityDataset the capabilityDataset
	 */
	public DataNode setCapability(IDataset capabilityDataset);

	/**
	 * Free-text list with eventually multiple terms of
	 * functionalities which the component offers.
	 *
	 * @return  the value.
	 */
	public String getCapabilityScalar();

	/**
	 * Free-text list with eventually multiple terms of
	 * functionalities which the component offers.
	 *
	 * @param capability the capability
	 */
	public DataNode setCapabilityScalar(String capabilityValue);

}
