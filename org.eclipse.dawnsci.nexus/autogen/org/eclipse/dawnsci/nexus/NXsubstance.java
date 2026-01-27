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
import org.eclipse.january.dataset.Dataset;

/**
 * A form of matter with a constant, definite chemical composition.
 * Examples can be single chemical elements, chemical compounds, or alloys.
 * For further information, see https://en.wikipedia.org/wiki/Chemical_substance.
 *
 */
public interface NXsubstance extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_MOLECULAR_MASS = "molecular_mass";
	public static final String NX_MOLECULAR_FORMULA_HILL = "molecular_formula_hill";
	public static final String NX_IDENTIFIER_CAS = "identifier_cas";
	public static final String NX_IDENTIFIER_CAS_ATTRIBUTE_TYPE = "type";
	public static final String NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NUMBER = "cas_number";
	public static final String NX_IDENTIFIER_CAS_ATTRIBUTE_CAS_NAME = "cas_name";
	public static final String NX_IDENTIFIER_INCHI_STR = "identifier_inchi_str";
	public static final String NX_IDENTIFIER_INCHI_KEY = "identifier_inchi_key";
	public static final String NX_IDENTIFIER_IUPAC_NAME = "identifier_iupac_name";
	public static final String NX_IDENTIFIER_SMILES = "identifier_smiles";
	public static final String NX_IDENTIFIER_CANONICAL_SMILES = "identifier_canonical_smiles";
	public static final String NX_IDENTIFIER_PUB_CHEM = "identifier_pub_chem";
	public static final String NX_IDENTIFIER_PUB_CHEM_ATTRIBUTE_PUB_CHEM_LINK = "pub_chem_link";
	/**
	 * User-defined chemical name of the substance
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * User-defined chemical name of the substance
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * User-defined chemical name of the substance
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * User-defined chemical name of the substance
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Molecular mass of the substance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MOLECULAR_WEIGHT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMolecular_mass();

	/**
	 * Molecular mass of the substance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MOLECULAR_WEIGHT
	 * </p>
	 *
	 * @param molecular_massDataset the molecular_massDataset
	 */
	public DataNode setMolecular_mass(IDataset molecular_massDataset);

	/**
	 * Molecular mass of the substance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MOLECULAR_WEIGHT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMolecular_massScalar();

	/**
	 * Molecular mass of the substance
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MOLECULAR_WEIGHT
	 * </p>
	 *
	 * @param molecular_mass the molecular_mass
	 */
	public DataNode setMolecular_massScalar(Double molecular_massValue);

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:107
	 * This is the *Hill* system used by Chemical Abstracts.
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the multiplier for the
	 * group must follow the closing parentheses. That is, all element and group
	 * multipliers are assumed to be printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to their chemical
	 * structure, the order of the elements within any group or moiety depends on
	 * whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of their symbol.
	 * - If carbon is not present, the elements are listed purely in alphabetic order of their symbol.
	 *
	 * @return  the value.
	 */
	public Dataset getMolecular_formula_hill();

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:107
	 * This is the *Hill* system used by Chemical Abstracts.
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the multiplier for the
	 * group must follow the closing parentheses. That is, all element and group
	 * multipliers are assumed to be printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to their chemical
	 * structure, the order of the elements within any group or moiety depends on
	 * whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of their symbol.
	 * - If carbon is not present, the elements are listed purely in alphabetic order of their symbol.
	 *
	 * @param molecular_formula_hillDataset the molecular_formula_hillDataset
	 */
	public DataNode setMolecular_formula_hill(IDataset molecular_formula_hillDataset);

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:107
	 * This is the *Hill* system used by Chemical Abstracts.
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the multiplier for the
	 * group must follow the closing parentheses. That is, all element and group
	 * multipliers are assumed to be printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to their chemical
	 * structure, the order of the elements within any group or moiety depends on
	 * whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of their symbol.
	 * - If carbon is not present, the elements are listed purely in alphabetic order of their symbol.
	 *
	 * @return  the value.
	 */
	public String getMolecular_formula_hillScalar();

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:107
	 * This is the *Hill* system used by Chemical Abstracts.
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the multiplier for the
	 * group must follow the closing parentheses. That is, all element and group
	 * multipliers are assumed to be printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to their chemical
	 * structure, the order of the elements within any group or moiety depends on
	 * whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of their symbol.
	 * - If carbon is not present, the elements are listed purely in alphabetic order of their symbol.
	 *
	 * @param molecular_formula_hill the molecular_formula_hill
	 */
	public DataNode setMolecular_formula_hillScalar(String molecular_formula_hillValue);

	/**
	 * Unique CAS REGISTRY URI.
	 * For further information, see https://www.cas.org/.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_cas();

	/**
	 * Unique CAS REGISTRY URI.
	 * For further information, see https://www.cas.org/.
	 *
	 * @param identifier_casDataset the identifier_casDataset
	 */
	public DataNode setIdentifier_cas(IDataset identifier_casDataset);

	/**
	 * Unique CAS REGISTRY URI.
	 * For further information, see https://www.cas.org/.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_casScalar();

	/**
	 * Unique CAS REGISTRY URI.
	 * For further information, see https://www.cas.org/.
	 *
	 * @param identifier_cas the identifier_cas
	 */
	public DataNode setIdentifier_casScalar(String identifier_casValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>URL</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_casAttributeType();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>URL</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeValue the typeValue
	 */
	public void setIdentifier_casAttributeType(String typeValue);

	/**
	 * Numeric CAS REGISTRY number associated with this identifier.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_casAttributeCas_number();

	/**
	 * Numeric CAS REGISTRY number associated with this identifier.
	 *
	 * @param cas_numberValue the cas_numberValue
	 */
	public void setIdentifier_casAttributeCas_number(String cas_numberValue);

	/**
	 * CAS REGISTRY name associated with this identifier.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_casAttributeCas_name();

	/**
	 * CAS REGISTRY name associated with this identifier.
	 *
	 * @param cas_nameValue the cas_nameValue
	 */
	public void setIdentifier_casAttributeCas_name(String cas_nameValue);

	/**
	 * CAS REGISTRY image
	 *
	 * @return  the value.
	 */
	public NXnote getCas_image();

	/**
	 * CAS REGISTRY image
	 *
	 * @param cas_imageGroup the cas_imageGroup
	 */
	public void setCas_image(NXnote cas_imageGroup);

	/**
	 * Standard string InChi identifier" (as per v1.02).
	 * The InChI identifier expresses chemical structures in terms of atomic connectivity,
	 * tautomeric state, isotopes, stereochemistry and electronic charge in order to
	 * produce a string of machine-readable characters unique to the respective molecule.
	 * For further information, see https://iupac.org/who-we-are/divisions/division-details/inchi/.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_inchi_str();

	/**
	 * Standard string InChi identifier" (as per v1.02).
	 * The InChI identifier expresses chemical structures in terms of atomic connectivity,
	 * tautomeric state, isotopes, stereochemistry and electronic charge in order to
	 * produce a string of machine-readable characters unique to the respective molecule.
	 * For further information, see https://iupac.org/who-we-are/divisions/division-details/inchi/.
	 *
	 * @param identifier_inchi_strDataset the identifier_inchi_strDataset
	 */
	public DataNode setIdentifier_inchi_str(IDataset identifier_inchi_strDataset);

	/**
	 * Standard string InChi identifier" (as per v1.02).
	 * The InChI identifier expresses chemical structures in terms of atomic connectivity,
	 * tautomeric state, isotopes, stereochemistry and electronic charge in order to
	 * produce a string of machine-readable characters unique to the respective molecule.
	 * For further information, see https://iupac.org/who-we-are/divisions/division-details/inchi/.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_inchi_strScalar();

	/**
	 * Standard string InChi identifier" (as per v1.02).
	 * The InChI identifier expresses chemical structures in terms of atomic connectivity,
	 * tautomeric state, isotopes, stereochemistry and electronic charge in order to
	 * produce a string of machine-readable characters unique to the respective molecule.
	 * For further information, see https://iupac.org/who-we-are/divisions/division-details/inchi/.
	 *
	 * @param identifier_inchi_str the identifier_inchi_str
	 */
	public DataNode setIdentifier_inchi_strScalar(String identifier_inchi_strValue);

	/**
	 * Condensed, 27 character InChI key.
	 * Hashed version of the full InChI (using the SHA-256 algorithm).
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_inchi_key();

	/**
	 * Condensed, 27 character InChI key.
	 * Hashed version of the full InChI (using the SHA-256 algorithm).
	 *
	 * @param identifier_inchi_keyDataset the identifier_inchi_keyDataset
	 */
	public DataNode setIdentifier_inchi_key(IDataset identifier_inchi_keyDataset);

	/**
	 * Condensed, 27 character InChI key.
	 * Hashed version of the full InChI (using the SHA-256 algorithm).
	 *
	 * @return  the value.
	 */
	public String getIdentifier_inchi_keyScalar();

	/**
	 * Condensed, 27 character InChI key.
	 * Hashed version of the full InChI (using the SHA-256 algorithm).
	 *
	 * @param identifier_inchi_key the identifier_inchi_key
	 */
	public DataNode setIdentifier_inchi_keyScalar(String identifier_inchi_keyValue);

	/**
	 * Name according to the IUPAC system (standard).
	 * For further information, see https://iupac.org/.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_iupac_name();

	/**
	 * Name according to the IUPAC system (standard).
	 * For further information, see https://iupac.org/.
	 *
	 * @param identifier_iupac_nameDataset the identifier_iupac_nameDataset
	 */
	public DataNode setIdentifier_iupac_name(IDataset identifier_iupac_nameDataset);

	/**
	 * Name according to the IUPAC system (standard).
	 * For further information, see https://iupac.org/.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_iupac_nameScalar();

	/**
	 * Name according to the IUPAC system (standard).
	 * For further information, see https://iupac.org/.
	 *
	 * @param identifier_iupac_name the identifier_iupac_name
	 */
	public DataNode setIdentifier_iupac_nameScalar(String identifier_iupac_nameValue);

	/**
	 * Identifier in the SMILES (Simplified Molecular Input Line Entry System) system
	 * For further information, see https://www.daylight.com/smiles/.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_smiles();

	/**
	 * Identifier in the SMILES (Simplified Molecular Input Line Entry System) system
	 * For further information, see https://www.daylight.com/smiles/.
	 *
	 * @param identifier_smilesDataset the identifier_smilesDataset
	 */
	public DataNode setIdentifier_smiles(IDataset identifier_smilesDataset);

	/**
	 * Identifier in the SMILES (Simplified Molecular Input Line Entry System) system
	 * For further information, see https://www.daylight.com/smiles/.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_smilesScalar();

	/**
	 * Identifier in the SMILES (Simplified Molecular Input Line Entry System) system
	 * For further information, see https://www.daylight.com/smiles/.
	 *
	 * @param identifier_smiles the identifier_smiles
	 */
	public DataNode setIdentifier_smilesScalar(String identifier_smilesValue);

	/**
	 * Canonical version of the SMILES identifier
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_canonical_smiles();

	/**
	 * Canonical version of the SMILES identifier
	 *
	 * @param identifier_canonical_smilesDataset the identifier_canonical_smilesDataset
	 */
	public DataNode setIdentifier_canonical_smiles(IDataset identifier_canonical_smilesDataset);

	/**
	 * Canonical version of the SMILES identifier
	 *
	 * @return  the value.
	 */
	public String getIdentifier_canonical_smilesScalar();

	/**
	 * Canonical version of the SMILES identifier
	 *
	 * @param identifier_canonical_smiles the identifier_canonical_smiles
	 */
	public DataNode setIdentifier_canonical_smilesScalar(String identifier_canonical_smilesValue);

	/**
	 * Standard PubChem identifier (CID).
	 * The PubChem Compound Identifier (CID) is a unique numerical identifier assigned to
	 * a compound in the PubChem database, which contains information on the biological activities
	 * of small molecules. The CID allows users to access detailed data about compounds, including
	 * their chemical structure, molecular formula, and biological properties.
	 * For further information, see https://pubchem.ncbi.nlm.nih.gov/.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_pub_chem();

	/**
	 * Standard PubChem identifier (CID).
	 * The PubChem Compound Identifier (CID) is a unique numerical identifier assigned to
	 * a compound in the PubChem database, which contains information on the biological activities
	 * of small molecules. The CID allows users to access detailed data about compounds, including
	 * their chemical structure, molecular formula, and biological properties.
	 * For further information, see https://pubchem.ncbi.nlm.nih.gov/.
	 *
	 * @param identifier_pub_chemDataset the identifier_pub_chemDataset
	 */
	public DataNode setIdentifier_pub_chem(IDataset identifier_pub_chemDataset);

	/**
	 * Standard PubChem identifier (CID).
	 * The PubChem Compound Identifier (CID) is a unique numerical identifier assigned to
	 * a compound in the PubChem database, which contains information on the biological activities
	 * of small molecules. The CID allows users to access detailed data about compounds, including
	 * their chemical structure, molecular formula, and biological properties.
	 * For further information, see https://pubchem.ncbi.nlm.nih.gov/.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_pub_chemScalar();

	/**
	 * Standard PubChem identifier (CID).
	 * The PubChem Compound Identifier (CID) is a unique numerical identifier assigned to
	 * a compound in the PubChem database, which contains information on the biological activities
	 * of small molecules. The CID allows users to access detailed data about compounds, including
	 * their chemical structure, molecular formula, and biological properties.
	 * For further information, see https://pubchem.ncbi.nlm.nih.gov/.
	 *
	 * @param identifier_pub_chem the identifier_pub_chem
	 */
	public DataNode setIdentifier_pub_chemScalar(String identifier_pub_chemValue);

	/**
	 * CAS REGISTRY name associated with this identifier.
	 *
	 * @return  the value.
	 */
	public String getIdentifier_pub_chemAttributePub_chem_link();

	/**
	 * CAS REGISTRY name associated with this identifier.
	 *
	 * @param pub_chem_linkValue the pub_chem_linkValue
	 */
	public void setIdentifier_pub_chemAttributePub_chem_link(String pub_chem_linkValue);

}
