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
 * A form of matter with a constant, definite chemical composition.
 * Examples can be single chemical elements, chemical compounds, or alloys.
 * For further information, see https://en.wikipedia.org/wiki/Chemical_substance.

 */
public class NXsubstanceImpl extends NXobjectImpl implements NXsubstance {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE);

	public NXsubstanceImpl() {
		super();
	}

	public NXsubstanceImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsubstance.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SUBSTANCE;
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
	public Dataset getMolecular_mass() {
		return getDataset(NX_MOLECULAR_MASS);
	}

	@Override
	public Double getMolecular_massScalar() {
		return getDouble(NX_MOLECULAR_MASS);
	}

	@Override
	public DataNode setMolecular_mass(IDataset molecular_massDataset) {
		return setDataset(NX_MOLECULAR_MASS, molecular_massDataset);
	}

	@Override
	public DataNode setMolecular_massScalar(Double molecular_massValue) {
		return setField(NX_MOLECULAR_MASS, molecular_massValue);
	}

	@Override
	public Dataset getMolecular_formula_hill() {
		return getDataset(NX_MOLECULAR_FORMULA_HILL);
	}

	@Override
	public String getMolecular_formula_hillScalar() {
		return getString(NX_MOLECULAR_FORMULA_HILL);
	}

	@Override
	public DataNode setMolecular_formula_hill(IDataset molecular_formula_hillDataset) {
		return setDataset(NX_MOLECULAR_FORMULA_HILL, molecular_formula_hillDataset);
	}

	@Override
	public DataNode setMolecular_formula_hillScalar(String molecular_formula_hillValue) {
		return setString(NX_MOLECULAR_FORMULA_HILL, molecular_formula_hillValue);
	}

	@Override
	public Dataset getIdentifier_cas() {
		return getDataset(NX_IDENTIFIER_CAS);
	}

	@Override
	public String getIdentifier_casScalar() {
		return getString(NX_IDENTIFIER_CAS);
	}

	@Override
	public DataNode setIdentifier_cas(IDataset identifier_casDataset) {
		return setDataset(NX_IDENTIFIER_CAS, identifier_casDataset);
	}

	@Override
	public DataNode setIdentifier_casScalar(String identifier_casValue) {
		return setString(NX_IDENTIFIER_CAS, identifier_casValue);
	}

	@Override
	public String getIdentifier_casAttributeType() {
		return getAttrString(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_TYPE);
	}

	@Override
	public void setIdentifier_casAttributeType(String typeValue) {
		setAttribute(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_TYPE, typeValue);
	}

	@Override
	public String getIdentifier_casAttributeCas_number() {
		return getAttrString(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NUMBER);
	}

	@Override
	public void setIdentifier_casAttributeCas_number(String cas_numberValue) {
		setAttribute(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NUMBER, cas_numberValue);
	}

	@Override
	public String getIdentifier_casAttributeCas_name() {
		return getAttrString(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NAME);
	}

	@Override
	public void setIdentifier_casAttributeCas_name(String cas_nameValue) {
		setAttribute(NX_IDENTIFIER_CAS, NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NAME, cas_nameValue);
	}

	@Override
	public NXnote getCas_image() {
		// dataNodeName = NX_CAS_IMAGE
		return getChild("cas_image", NXnote.class);
	}

	@Override
	public void setCas_image(NXnote cas_imageGroup) {
		putChild("cas_image", cas_imageGroup);
	}

	@Override
	public Dataset getIdentifier_inchi_str() {
		return getDataset(NX_IDENTIFIER_INCHI_STR);
	}

	@Override
	public String getIdentifier_inchi_strScalar() {
		return getString(NX_IDENTIFIER_INCHI_STR);
	}

	@Override
	public DataNode setIdentifier_inchi_str(IDataset identifier_inchi_strDataset) {
		return setDataset(NX_IDENTIFIER_INCHI_STR, identifier_inchi_strDataset);
	}

	@Override
	public DataNode setIdentifier_inchi_strScalar(String identifier_inchi_strValue) {
		return setString(NX_IDENTIFIER_INCHI_STR, identifier_inchi_strValue);
	}

	@Override
	public Dataset getIdentifier_inchi_key() {
		return getDataset(NX_IDENTIFIER_INCHI_KEY);
	}

	@Override
	public String getIdentifier_inchi_keyScalar() {
		return getString(NX_IDENTIFIER_INCHI_KEY);
	}

	@Override
	public DataNode setIdentifier_inchi_key(IDataset identifier_inchi_keyDataset) {
		return setDataset(NX_IDENTIFIER_INCHI_KEY, identifier_inchi_keyDataset);
	}

	@Override
	public DataNode setIdentifier_inchi_keyScalar(String identifier_inchi_keyValue) {
		return setString(NX_IDENTIFIER_INCHI_KEY, identifier_inchi_keyValue);
	}

	@Override
	public Dataset getIdentifier_iupac_name() {
		return getDataset(NX_IDENTIFIER_IUPAC_NAME);
	}

	@Override
	public String getIdentifier_iupac_nameScalar() {
		return getString(NX_IDENTIFIER_IUPAC_NAME);
	}

	@Override
	public DataNode setIdentifier_iupac_name(IDataset identifier_iupac_nameDataset) {
		return setDataset(NX_IDENTIFIER_IUPAC_NAME, identifier_iupac_nameDataset);
	}

	@Override
	public DataNode setIdentifier_iupac_nameScalar(String identifier_iupac_nameValue) {
		return setString(NX_IDENTIFIER_IUPAC_NAME, identifier_iupac_nameValue);
	}

	@Override
	public Dataset getIdentifier_smiles() {
		return getDataset(NX_IDENTIFIER_SMILES);
	}

	@Override
	public String getIdentifier_smilesScalar() {
		return getString(NX_IDENTIFIER_SMILES);
	}

	@Override
	public DataNode setIdentifier_smiles(IDataset identifier_smilesDataset) {
		return setDataset(NX_IDENTIFIER_SMILES, identifier_smilesDataset);
	}

	@Override
	public DataNode setIdentifier_smilesScalar(String identifier_smilesValue) {
		return setString(NX_IDENTIFIER_SMILES, identifier_smilesValue);
	}

	@Override
	public Dataset getIdentifier_canonical_smiles() {
		return getDataset(NX_IDENTIFIER_CANONICAL_SMILES);
	}

	@Override
	public String getIdentifier_canonical_smilesScalar() {
		return getString(NX_IDENTIFIER_CANONICAL_SMILES);
	}

	@Override
	public DataNode setIdentifier_canonical_smiles(IDataset identifier_canonical_smilesDataset) {
		return setDataset(NX_IDENTIFIER_CANONICAL_SMILES, identifier_canonical_smilesDataset);
	}

	@Override
	public DataNode setIdentifier_canonical_smilesScalar(String identifier_canonical_smilesValue) {
		return setString(NX_IDENTIFIER_CANONICAL_SMILES, identifier_canonical_smilesValue);
	}

	@Override
	public Dataset getIdentifier_pub_chem() {
		return getDataset(NX_IDENTIFIER_PUB_CHEM);
	}

	@Override
	public String getIdentifier_pub_chemScalar() {
		return getString(NX_IDENTIFIER_PUB_CHEM);
	}

	@Override
	public DataNode setIdentifier_pub_chem(IDataset identifier_pub_chemDataset) {
		return setDataset(NX_IDENTIFIER_PUB_CHEM, identifier_pub_chemDataset);
	}

	@Override
	public DataNode setIdentifier_pub_chemScalar(String identifier_pub_chemValue) {
		return setString(NX_IDENTIFIER_PUB_CHEM, identifier_pub_chemValue);
	}

	@Override
	public String getIdentifier_pub_chemAttributePub_chem_link() {
		return getAttrString(NX_IDENTIFIER_PUB_CHEM, NX_IDENTIFIER_PUB_CHEM_ATTRIBUTE_PUB_CHEM_LINK);
	}

	@Override
	public void setIdentifier_pub_chemAttributePub_chem_link(String pub_chem_linkValue) {
		setAttribute(NX_IDENTIFIER_PUB_CHEM, NX_IDENTIFIER_PUB_CHEM_ATTRIBUTE_PUB_CHEM_LINK, pub_chem_linkValue);
	}

}
