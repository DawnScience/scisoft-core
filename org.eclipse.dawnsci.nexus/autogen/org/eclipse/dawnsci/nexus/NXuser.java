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
 * Contact information for a user.
 * The format allows more
 * than one user with the same affiliation and contact information,
 * but a second :ref:`NXuser` group should be used if they have different
 * affiliations, etc.
 * 
 */
public interface NXuser extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_ROLE = "role";
	public static final String NX_AFFILIATION = "affiliation";
	public static final String NX_ADDRESS = "address";
	public static final String NX_TELEPHONE_NUMBER = "telephone_number";
	public static final String NX_FAX_NUMBER = "fax_number";
	public static final String NX_EMAIL = "email";
	public static final String NX_FACILITY_USER_ID = "facility_user_id";
	public static final String NX_ORCID = "orcid";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Name of user responsible for this entry
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Name of user responsible for this entry
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of user responsible for this entry
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of user responsible for this entry
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Role of user responsible for this entry.
	 * Suggested roles are "local_contact",
	 * "principal_investigator", and "proposer"
	 * 
	 * @return  the value.
	 */
	public IDataset getRole();
	
	/**
	 * Role of user responsible for this entry.
	 * Suggested roles are "local_contact",
	 * "principal_investigator", and "proposer"
	 * 
	 * @param roleDataset the roleDataset
	 */
	public DataNode setRole(IDataset roleDataset);

	/**
	 * Role of user responsible for this entry.
	 * Suggested roles are "local_contact",
	 * "principal_investigator", and "proposer"
	 * 
	 * @return  the value.
	 */
	public String getRoleScalar();

	/**
	 * Role of user responsible for this entry.
	 * Suggested roles are "local_contact",
	 * "principal_investigator", and "proposer"
	 * 
	 * @param role the role
	 */
	public DataNode setRoleScalar(String roleValue);

	/**
	 * Affiliation of user
	 * 
	 * @return  the value.
	 */
	public IDataset getAffiliation();
	
	/**
	 * Affiliation of user
	 * 
	 * @param affiliationDataset the affiliationDataset
	 */
	public DataNode setAffiliation(IDataset affiliationDataset);

	/**
	 * Affiliation of user
	 * 
	 * @return  the value.
	 */
	public String getAffiliationScalar();

	/**
	 * Affiliation of user
	 * 
	 * @param affiliation the affiliation
	 */
	public DataNode setAffiliationScalar(String affiliationValue);

	/**
	 * Address of user
	 * 
	 * @return  the value.
	 */
	public IDataset getAddress();
	
	/**
	 * Address of user
	 * 
	 * @param addressDataset the addressDataset
	 */
	public DataNode setAddress(IDataset addressDataset);

	/**
	 * Address of user
	 * 
	 * @return  the value.
	 */
	public String getAddressScalar();

	/**
	 * Address of user
	 * 
	 * @param address the address
	 */
	public DataNode setAddressScalar(String addressValue);

	/**
	 * Telephone number of user
	 * 
	 * @return  the value.
	 */
	public IDataset getTelephone_number();
	
	/**
	 * Telephone number of user
	 * 
	 * @param telephone_numberDataset the telephone_numberDataset
	 */
	public DataNode setTelephone_number(IDataset telephone_numberDataset);

	/**
	 * Telephone number of user
	 * 
	 * @return  the value.
	 */
	public String getTelephone_numberScalar();

	/**
	 * Telephone number of user
	 * 
	 * @param telephone_number the telephone_number
	 */
	public DataNode setTelephone_numberScalar(String telephone_numberValue);

	/**
	 * Fax number of user
	 * 
	 * @return  the value.
	 */
	public IDataset getFax_number();
	
	/**
	 * Fax number of user
	 * 
	 * @param fax_numberDataset the fax_numberDataset
	 */
	public DataNode setFax_number(IDataset fax_numberDataset);

	/**
	 * Fax number of user
	 * 
	 * @return  the value.
	 */
	public String getFax_numberScalar();

	/**
	 * Fax number of user
	 * 
	 * @param fax_number the fax_number
	 */
	public DataNode setFax_numberScalar(String fax_numberValue);

	/**
	 * Email of user
	 * 
	 * @return  the value.
	 */
	public IDataset getEmail();
	
	/**
	 * Email of user
	 * 
	 * @param emailDataset the emailDataset
	 */
	public DataNode setEmail(IDataset emailDataset);

	/**
	 * Email of user
	 * 
	 * @return  the value.
	 */
	public String getEmailScalar();

	/**
	 * Email of user
	 * 
	 * @param email the email
	 */
	public DataNode setEmailScalar(String emailValue);

	/**
	 * facility based unique identifier for this person
	 * e.g. their identification code on the facility
	 * address/contact database
	 * 
	 * @return  the value.
	 */
	public IDataset getFacility_user_id();
	
	/**
	 * facility based unique identifier for this person
	 * e.g. their identification code on the facility
	 * address/contact database
	 * 
	 * @param facility_user_idDataset the facility_user_idDataset
	 */
	public DataNode setFacility_user_id(IDataset facility_user_idDataset);

	/**
	 * facility based unique identifier for this person
	 * e.g. their identification code on the facility
	 * address/contact database
	 * 
	 * @return  the value.
	 */
	public String getFacility_user_idScalar();

	/**
	 * facility based unique identifier for this person
	 * e.g. their identification code on the facility
	 * address/contact database
	 * 
	 * @param facility_user_id the facility_user_id
	 */
	public DataNode setFacility_user_idScalar(String facility_user_idValue);

	/**
	 * an author code, Open Researcher and Contributor ID,
	 * defined by https://orcid.org and expressed as a URI
	 * 
	 * @return  the value.
	 */
	public IDataset getOrcid();
	
	/**
	 * an author code, Open Researcher and Contributor ID,
	 * defined by https://orcid.org and expressed as a URI
	 * 
	 * @param orcidDataset the orcidDataset
	 */
	public DataNode setOrcid(IDataset orcidDataset);

	/**
	 * an author code, Open Researcher and Contributor ID,
	 * defined by https://orcid.org and expressed as a URI
	 * 
	 * @return  the value.
	 */
	public String getOrcidScalar();

	/**
	 * an author code, Open Researcher and Contributor ID,
	 * defined by https://orcid.org and expressed as a URI
	 * 
	 * @param orcid the orcid
	 */
	public DataNode setOrcidScalar(String orcidValue);

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
