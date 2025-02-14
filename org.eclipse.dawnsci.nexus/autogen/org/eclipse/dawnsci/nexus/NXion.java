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
 * Set of atoms of a molecular ion or fragment in e.g. ToF mass spectrometry.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_ivecmax</b>
 * Maximum number of atoms/isotopes allowed per (molecular) ion (fragment).</li>
 * <li><b>n_ranges</b>
 * Number of mass-to-charge-state-ratio range intervals for ion type.</li></ul></p>
 *
 */
public interface NXion extends NXobject {

	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_IDENTIFIER_TYPE = "identifier_type";
	public static final String NX_ION_TYPE = "ion_type";
	public static final String NX_ISOTOPE_VECTOR = "isotope_vector";
	public static final String NX_NUCLID_LIST = "nuclid_list";
	public static final String NX_COLOR = "color";
	public static final String NX_VOLUME = "volume";
	public static final String NX_CHARGE = "charge";
	public static final String NX_CHARGE_STATE = "charge_state";
	public static final String NX_NAME = "name";
	public static final String NX_MASS_TO_CHARGE_RANGE = "mass_to_charge_range";
	/**
	 * A unique identifier whereby such an ion can be referred to
	 * via the service offered as described in identifier_type.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * A unique identifier whereby such an ion can be referred to
	 * via the service offered as described in identifier_type.
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * A unique identifier whereby such an ion can be referred to
	 * via the service offered as described in identifier_type.
	 *
	 * @return  the value.
	 */
	public String getIdentifierScalar();

	/**
	 * A unique identifier whereby such an ion can be referred to
	 * via the service offered as described in identifier_type.
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(String identifierValue);

	/**
	 * How can the identifier be resolved?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_type();

	/**
	 * How can the identifier be resolved?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @param identifier_typeDataset the identifier_typeDataset
	 */
	public DataNode setIdentifier_type(IDataset identifier_typeDataset);

	/**
	 * How can the identifier be resolved?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_typeScalar();

	/**
	 * How can the identifier be resolved?
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @param identifier_type the identifier_type
	 */
	public DataNode setIdentifier_typeScalar(String identifier_typeValue);

	/**
	 * Ion type (ion species) identifier. The identifier zero
	 * is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIon_type();

	/**
	 * Ion type (ion species) identifier. The identifier zero
	 * is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param ion_typeDataset the ion_typeDataset
	 */
	public DataNode setIon_type(IDataset ion_typeDataset);

	/**
	 * Ion type (ion species) identifier. The identifier zero
	 * is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIon_typeScalar();

	/**
	 * Ion type (ion species) identifier. The identifier zero
	 * is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param ion_type the ion_type
	 */
	public DataNode setIon_typeScalar(Long ion_typeValue);

	/**
	 * A vector of isotope hash values.
	 * These values have to be stored in an array, sorted in decreasing order.
	 * The array is filled with zero hash values indicating unused places.
	 * The individual hash values are built with the following hash function:
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively.
	 * Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 1; 2: n_ivecmax;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIsotope_vector();

	/**
	 * A vector of isotope hash values.
	 * These values have to be stored in an array, sorted in decreasing order.
	 * The array is filled with zero hash values indicating unused places.
	 * The individual hash values are built with the following hash function:
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively.
	 * Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 1; 2: n_ivecmax;
	 * </p>
	 *
	 * @param isotope_vectorDataset the isotope_vectorDataset
	 */
	public DataNode setIsotope_vector(IDataset isotope_vectorDataset);

	/**
	 * A vector of isotope hash values.
	 * These values have to be stored in an array, sorted in decreasing order.
	 * The array is filled with zero hash values indicating unused places.
	 * The individual hash values are built with the following hash function:
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively.
	 * Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 1; 2: n_ivecmax;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIsotope_vectorScalar();

	/**
	 * A vector of isotope hash values.
	 * These values have to be stored in an array, sorted in decreasing order.
	 * The array is filled with zero hash values indicating unused places.
	 * The individual hash values are built with the following hash function:
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively.
	 * Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 1; 2: n_ivecmax;
	 * </p>
	 *
	 * @param isotope_vector the isotope_vector
	 */
	public DataNode setIsotope_vectorScalar(Long isotope_vectorValue);

	/**
	 * A supplementary row vector which decodes the isotope_vector into
	 * a human-readable matrix of nuclids with the following formatting:
	 * The first row specifies the isotope mass number, i.e. using the hashvalues
	 * from the isotope_vector this is :math:`Z + N`. As an example for a
	 * carbon-14 isotope the number is 14.
	 * The second row specifies the number of protons :math:`Z`, e.g. 6 for the
	 * carbon-14 example. This row matrix is thus a mapping the notation of
	 * using superscribed isotope mass and subscripted number of protons to
	 * identify isotopes.
	 * Unused places filling up to n_ivecmax need to be filled with zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2; 2: n_ivecmax;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNuclid_list();

	/**
	 * A supplementary row vector which decodes the isotope_vector into
	 * a human-readable matrix of nuclids with the following formatting:
	 * The first row specifies the isotope mass number, i.e. using the hashvalues
	 * from the isotope_vector this is :math:`Z + N`. As an example for a
	 * carbon-14 isotope the number is 14.
	 * The second row specifies the number of protons :math:`Z`, e.g. 6 for the
	 * carbon-14 example. This row matrix is thus a mapping the notation of
	 * using superscribed isotope mass and subscripted number of protons to
	 * identify isotopes.
	 * Unused places filling up to n_ivecmax need to be filled with zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2; 2: n_ivecmax;
	 * </p>
	 *
	 * @param nuclid_listDataset the nuclid_listDataset
	 */
	public DataNode setNuclid_list(IDataset nuclid_listDataset);

	/**
	 * A supplementary row vector which decodes the isotope_vector into
	 * a human-readable matrix of nuclids with the following formatting:
	 * The first row specifies the isotope mass number, i.e. using the hashvalues
	 * from the isotope_vector this is :math:`Z + N`. As an example for a
	 * carbon-14 isotope the number is 14.
	 * The second row specifies the number of protons :math:`Z`, e.g. 6 for the
	 * carbon-14 example. This row matrix is thus a mapping the notation of
	 * using superscribed isotope mass and subscripted number of protons to
	 * identify isotopes.
	 * Unused places filling up to n_ivecmax need to be filled with zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2; 2: n_ivecmax;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNuclid_listScalar();

	/**
	 * A supplementary row vector which decodes the isotope_vector into
	 * a human-readable matrix of nuclids with the following formatting:
	 * The first row specifies the isotope mass number, i.e. using the hashvalues
	 * from the isotope_vector this is :math:`Z + N`. As an example for a
	 * carbon-14 isotope the number is 14.
	 * The second row specifies the number of protons :math:`Z`, e.g. 6 for the
	 * carbon-14 example. This row matrix is thus a mapping the notation of
	 * using superscribed isotope mass and subscripted number of protons to
	 * identify isotopes.
	 * Unused places filling up to n_ivecmax need to be filled with zero.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 2; 2: n_ivecmax;
	 * </p>
	 *
	 * @param nuclid_list the nuclid_list
	 */
	public DataNode setNuclid_listScalar(Long nuclid_listValue);

	/**
	 * Color code used for visualizing such ions.
	 *
	 * @return  the value.
	 */
	public Dataset getColor();

	/**
	 * Color code used for visualizing such ions.
	 *
	 * @param colorDataset the colorDataset
	 */
	public DataNode setColor(IDataset colorDataset);

	/**
	 * Color code used for visualizing such ions.
	 *
	 * @return  the value.
	 */
	public String getColorScalar();

	/**
	 * Color code used for visualizing such ions.
	 *
	 * @param color the color
	 */
	public DataNode setColorScalar(String colorValue);

	/**
	 * Assumed volume of the ion.
	 * In atom probe microscopy this field can be used to store the reconstructed
	 * volume per ion (average) which is typically stored in range files and will
	 * be used when building a tomographic reconstruction of an atom probe
	 * dataset.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Assumed volume of the ion.
	 * In atom probe microscopy this field can be used to store the reconstructed
	 * volume per ion (average) which is typically stored in range files and will
	 * be used when building a tomographic reconstruction of an atom probe
	 * dataset.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Assumed volume of the ion.
	 * In atom probe microscopy this field can be used to store the reconstructed
	 * volume per ion (average) which is typically stored in range files and will
	 * be used when building a tomographic reconstruction of an atom probe
	 * dataset.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getVolumeScalar();

	/**
	 * Assumed volume of the ion.
	 * In atom probe microscopy this field can be used to store the reconstructed
	 * volume per ion (average) which is typically stored in range files and will
	 * be used when building a tomographic reconstruction of an atom probe
	 * dataset.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Double volumeValue);

	/**
	 * Charge of the ion.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCharge();

	/**
	 * Charge of the ion.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @param chargeDataset the chargeDataset
	 */
	public DataNode setCharge(IDataset chargeDataset);

	/**
	 * Charge of the ion.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getChargeScalar();

	/**
	 * Charge of the ion.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @param charge the charge
	 */
	public DataNode setChargeScalar(Double chargeValue);

	/**
	 * Signed charge state of the ion in multiples of electron charge.
	 * Only positive values will be measured in atom probe microscopy as the
	 * ions are accelerated by a negatively signed bias electric field.
	 * In the case that the charge state is not explicitly recoverable,
	 * the value should be set to zero.
	 * In atom probe microscopy this is for example the case when using
	 * classical range file formats like RNG, RRNG for atom probe data.
	 * These file formats do not document the charge state explicitly.
	 * They report the number of atoms of each element per molecular ion
	 * surplus the mass-to-charge-state-ratio interval.
	 * With this it is possible to recover the charge state only for
	 * specific molecular ions as the accumulated mass of the molecular ion
	 * is defined by the isotopes, which without knowing the charge leads
	 * to an underconstrained problem.
	 * Details on ranging can be found in the literature: `M. K. Miller <https://doi.org/10.1002/sia.1719>`_
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCharge_state();

	/**
	 * Signed charge state of the ion in multiples of electron charge.
	 * Only positive values will be measured in atom probe microscopy as the
	 * ions are accelerated by a negatively signed bias electric field.
	 * In the case that the charge state is not explicitly recoverable,
	 * the value should be set to zero.
	 * In atom probe microscopy this is for example the case when using
	 * classical range file formats like RNG, RRNG for atom probe data.
	 * These file formats do not document the charge state explicitly.
	 * They report the number of atoms of each element per molecular ion
	 * surplus the mass-to-charge-state-ratio interval.
	 * With this it is possible to recover the charge state only for
	 * specific molecular ions as the accumulated mass of the molecular ion
	 * is defined by the isotopes, which without knowing the charge leads
	 * to an underconstrained problem.
	 * Details on ranging can be found in the literature: `M. K. Miller <https://doi.org/10.1002/sia.1719>`_
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param charge_stateDataset the charge_stateDataset
	 */
	public DataNode setCharge_state(IDataset charge_stateDataset);

	/**
	 * Signed charge state of the ion in multiples of electron charge.
	 * Only positive values will be measured in atom probe microscopy as the
	 * ions are accelerated by a negatively signed bias electric field.
	 * In the case that the charge state is not explicitly recoverable,
	 * the value should be set to zero.
	 * In atom probe microscopy this is for example the case when using
	 * classical range file formats like RNG, RRNG for atom probe data.
	 * These file formats do not document the charge state explicitly.
	 * They report the number of atoms of each element per molecular ion
	 * surplus the mass-to-charge-state-ratio interval.
	 * With this it is possible to recover the charge state only for
	 * specific molecular ions as the accumulated mass of the molecular ion
	 * is defined by the isotopes, which without knowing the charge leads
	 * to an underconstrained problem.
	 * Details on ranging can be found in the literature: `M. K. Miller <https://doi.org/10.1002/sia.1719>`_
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCharge_stateScalar();

	/**
	 * Signed charge state of the ion in multiples of electron charge.
	 * Only positive values will be measured in atom probe microscopy as the
	 * ions are accelerated by a negatively signed bias electric field.
	 * In the case that the charge state is not explicitly recoverable,
	 * the value should be set to zero.
	 * In atom probe microscopy this is for example the case when using
	 * classical range file formats like RNG, RRNG for atom probe data.
	 * These file formats do not document the charge state explicitly.
	 * They report the number of atoms of each element per molecular ion
	 * surplus the mass-to-charge-state-ratio interval.
	 * With this it is possible to recover the charge state only for
	 * specific molecular ions as the accumulated mass of the molecular ion
	 * is defined by the isotopes, which without knowing the charge leads
	 * to an underconstrained problem.
	 * Details on ranging can be found in the literature: `M. K. Miller <https://doi.org/10.1002/sia.1719>`_
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param charge_state the charge_state
	 */
	public DataNode setCharge_stateScalar(Long charge_stateValue);

	/**
	 * Human-readable ion type name (e.g. Al +++)
	 * The string should consists of ASCII UTF-8 characters,
	 * ideally using LaTeX notation to specify the isotopes, ions, and charge
	 * state. Examples are 12C + or Al +++.
	 * Although this name may be human-readable and intuitive, parsing such
	 * names becomes impractical for more complicated cases. Therefore, for the
	 * field of atom probe microscopy the isotope_vector should be the
	 * preferred machine-readable format to use.
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Human-readable ion type name (e.g. Al +++)
	 * The string should consists of ASCII UTF-8 characters,
	 * ideally using LaTeX notation to specify the isotopes, ions, and charge
	 * state. Examples are 12C + or Al +++.
	 * Although this name may be human-readable and intuitive, parsing such
	 * names becomes impractical for more complicated cases. Therefore, for the
	 * field of atom probe microscopy the isotope_vector should be the
	 * preferred machine-readable format to use.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Human-readable ion type name (e.g. Al +++)
	 * The string should consists of ASCII UTF-8 characters,
	 * ideally using LaTeX notation to specify the isotopes, ions, and charge
	 * state. Examples are 12C + or Al +++.
	 * Although this name may be human-readable and intuitive, parsing such
	 * names becomes impractical for more complicated cases. Therefore, for the
	 * field of atom probe microscopy the isotope_vector should be the
	 * preferred machine-readable format to use.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Human-readable ion type name (e.g. Al +++)
	 * The string should consists of ASCII UTF-8 characters,
	 * ideally using LaTeX notation to specify the isotopes, ions, and charge
	 * state. Examples are 12C + or Al +++.
	 * Although this name may be human-readable and intuitive, parsing such
	 * names becomes impractical for more complicated cases. Therefore, for the
	 * field of atom probe microscopy the isotope_vector should be the
	 * preferred machine-readable format to use.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Associated lower (mqmin) and upper (mqmax) bounds of
	 * mass-to-charge-state ratio interval(s) [mqmin, mqmax]
	 * (boundaries included) for which the respective ion is one to be labelled
	 * with ion_identifier. The field is primarily of interest to document the
	 * result of indexing a ToF/mass spectrum.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMass_to_charge_range();

	/**
	 * Associated lower (mqmin) and upper (mqmax) bounds of
	 * mass-to-charge-state ratio interval(s) [mqmin, mqmax]
	 * (boundaries included) for which the respective ion is one to be labelled
	 * with ion_identifier. The field is primarily of interest to document the
	 * result of indexing a ToF/mass spectrum.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @param mass_to_charge_rangeDataset the mass_to_charge_rangeDataset
	 */
	public DataNode setMass_to_charge_range(IDataset mass_to_charge_rangeDataset);

	/**
	 * Associated lower (mqmin) and upper (mqmax) bounds of
	 * mass-to-charge-state ratio interval(s) [mqmin, mqmax]
	 * (boundaries included) for which the respective ion is one to be labelled
	 * with ion_identifier. The field is primarily of interest to document the
	 * result of indexing a ToF/mass spectrum.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMass_to_charge_rangeScalar();

	/**
	 * Associated lower (mqmin) and upper (mqmax) bounds of
	 * mass-to-charge-state ratio interval(s) [mqmin, mqmax]
	 * (boundaries included) for which the respective ion is one to be labelled
	 * with ion_identifier. The field is primarily of interest to document the
	 * result of indexing a ToF/mass spectrum.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @param mass_to_charge_range the mass_to_charge_range
	 */
	public DataNode setMass_to_charge_rangeScalar(Double mass_to_charge_rangeValue);

}
