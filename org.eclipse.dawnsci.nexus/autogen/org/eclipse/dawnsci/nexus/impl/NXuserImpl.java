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
 * Contact information for a user.
 * The format allows more
 * than one user with the same affiliation and contact information,
 * but a second :ref:`NXuser` group should be used if they have different
 * affiliations, etc.
 * 
 */
public class NXuserImpl extends NXobjectImpl implements NXuser {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXuserImpl() {
		super();
	}

	public NXuserImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXuser.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_USER;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getName() {
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
	public IDataset getRole() {
		return getDataset(NX_ROLE);
	}

	@Override
	public String getRoleScalar() {
		return getString(NX_ROLE);
	}

	@Override
	public DataNode setRole(IDataset roleDataset) {
		return setDataset(NX_ROLE, roleDataset);
	}

	@Override
	public DataNode setRoleScalar(String roleValue) {
		return setString(NX_ROLE, roleValue);
	}

	@Override
	public IDataset getAffiliation() {
		return getDataset(NX_AFFILIATION);
	}

	@Override
	public String getAffiliationScalar() {
		return getString(NX_AFFILIATION);
	}

	@Override
	public DataNode setAffiliation(IDataset affiliationDataset) {
		return setDataset(NX_AFFILIATION, affiliationDataset);
	}

	@Override
	public DataNode setAffiliationScalar(String affiliationValue) {
		return setString(NX_AFFILIATION, affiliationValue);
	}

	@Override
	public IDataset getAddress() {
		return getDataset(NX_ADDRESS);
	}

	@Override
	public String getAddressScalar() {
		return getString(NX_ADDRESS);
	}

	@Override
	public DataNode setAddress(IDataset addressDataset) {
		return setDataset(NX_ADDRESS, addressDataset);
	}

	@Override
	public DataNode setAddressScalar(String addressValue) {
		return setString(NX_ADDRESS, addressValue);
	}

	@Override
	public IDataset getTelephone_number() {
		return getDataset(NX_TELEPHONE_NUMBER);
	}

	@Override
	public String getTelephone_numberScalar() {
		return getString(NX_TELEPHONE_NUMBER);
	}

	@Override
	public DataNode setTelephone_number(IDataset telephone_numberDataset) {
		return setDataset(NX_TELEPHONE_NUMBER, telephone_numberDataset);
	}

	@Override
	public DataNode setTelephone_numberScalar(String telephone_numberValue) {
		return setString(NX_TELEPHONE_NUMBER, telephone_numberValue);
	}

	@Override
	public IDataset getFax_number() {
		return getDataset(NX_FAX_NUMBER);
	}

	@Override
	public String getFax_numberScalar() {
		return getString(NX_FAX_NUMBER);
	}

	@Override
	public DataNode setFax_number(IDataset fax_numberDataset) {
		return setDataset(NX_FAX_NUMBER, fax_numberDataset);
	}

	@Override
	public DataNode setFax_numberScalar(String fax_numberValue) {
		return setString(NX_FAX_NUMBER, fax_numberValue);
	}

	@Override
	public IDataset getEmail() {
		return getDataset(NX_EMAIL);
	}

	@Override
	public String getEmailScalar() {
		return getString(NX_EMAIL);
	}

	@Override
	public DataNode setEmail(IDataset emailDataset) {
		return setDataset(NX_EMAIL, emailDataset);
	}

	@Override
	public DataNode setEmailScalar(String emailValue) {
		return setString(NX_EMAIL, emailValue);
	}

	@Override
	public IDataset getFacility_user_id() {
		return getDataset(NX_FACILITY_USER_ID);
	}

	@Override
	public String getFacility_user_idScalar() {
		return getString(NX_FACILITY_USER_ID);
	}

	@Override
	public DataNode setFacility_user_id(IDataset facility_user_idDataset) {
		return setDataset(NX_FACILITY_USER_ID, facility_user_idDataset);
	}

	@Override
	public DataNode setFacility_user_idScalar(String facility_user_idValue) {
		return setString(NX_FACILITY_USER_ID, facility_user_idValue);
	}

	@Override
	public IDataset getOrcid() {
		return getDataset(NX_ORCID);
	}

	@Override
	public String getOrcidScalar() {
		return getString(NX_ORCID);
	}

	@Override
	public DataNode setOrcid(IDataset orcidDataset) {
		return setDataset(NX_ORCID, orcidDataset);
	}

	@Override
	public DataNode setOrcidScalar(String orcidValue) {
		return setString(NX_ORCID, orcidValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
