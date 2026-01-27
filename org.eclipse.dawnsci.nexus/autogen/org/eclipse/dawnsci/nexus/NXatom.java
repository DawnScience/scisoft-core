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
 * Base class for documenting a set of atoms.
 * Atoms in the set may be bonded. The set may have
 * a net charge to represent an ion.
 * An ion can be a molecular ion.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_pos</b>
 * Number of atom positions.</li>
 * <li><b>d</b>
 * Dimensionality</li>
 * <li><b>n_ivec_max</b>
 * Maximum number of atoms/isotopes allowed per ion.</li>
 * <li><b>n_ranges</b>
 * Number of mass-to-charge-state-ratio range intervals for ion type.</li></ul></p>
 *
 */
public interface NXatom extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_ID = "id";
	public static final String NX_IDENTIFIER_CHEMICAL = "identifier_chemical";
	public static final String NX_CHARGE = "charge";
	public static final String NX_CHARGE_STATE = "charge_state";
	public static final String NX_VOLUME = "volume";
	public static final String NX_INDICES = "indices";
	public static final String NX_TYPE = "type";
	public static final String NX_POSITION = "position";
	public static final String NX_POSITION_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_OCCUPANCY = "occupancy";
	public static final String NX_NUCLIDE_HASH = "nuclide_hash";
	public static final String NX_NUCLIDE_LIST = "nuclide_list";
	public static final String NX_MASS_TO_CHARGE_RANGE = "mass_to_charge_range";
	/**
	 * Given name for the set.
	 * This field could for example be used in the research field
	 * of atom probe tomography to store a standardized human-readable
	 * name of the element or ion like such as Al +++ or 12C +.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name for the set.
	 * This field could for example be used in the research field
	 * of atom probe tomography to store a standardized human-readable
	 * name of the element or ion like such as Al +++ or 12C +.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name for the set.
	 * This field could for example be used in the research field
	 * of atom probe tomography to store a standardized human-readable
	 * name of the element or ion like such as Al +++ or 12C +.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name for the set.
	 * This field could for example be used in the research field
	 * of atom probe tomography to store a standardized human-readable
	 * name of the element or ion like such as Al +++ or 12C +.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Given numerical identifier for the set.
	 * The identifier zero is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getId();

	/**
	 * Given numerical identifier for the set.
	 * The identifier zero is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param idDataset the idDataset
	 */
	public DataNode setId(IDataset idDataset);

	/**
	 * Given numerical identifier for the set.
	 * The identifier zero is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdScalar();

	/**
	 * Given numerical identifier for the set.
	 * The identifier zero is reserved for the special unknown ion type.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param id the id
	 */
	public DataNode setIdScalar(Long idValue);

	/**
	 * Identifier used to refer to if the set of atoms represents a substance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_chemical();

	/**
	 * Identifier used to refer to if the set of atoms represents a substance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @param identifier_chemicalDataset the identifier_chemicalDataset
	 */
	public DataNode setIdentifier_chemical(IDataset identifier_chemicalDataset);

	/**
	 * Identifier used to refer to if the set of atoms represents a substance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIdentifier_chemicalScalar();

	/**
	 * Identifier used to refer to if the set of atoms represents a substance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>inchi</b> </li></ul></p>
	 * </p>
	 *
	 * @param identifier_chemical the identifier_chemical
	 */
	public DataNode setIdentifier_chemicalScalar(String identifier_chemicalValue);

	/**
	 * Signed net (partial) charge of the (molecular) ion.
	 * Different methods for computing charge are in use.
	 * Care needs to be exercised with respect to the integration.
	 * `T. A. Manz <10.1039/c6ra04656h>`_ and `N. G. Limas <10.1039/C6RA05507A>`_ discuss computational details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCharge();

	/**
	 * Signed net (partial) charge of the (molecular) ion.
	 * Different methods for computing charge are in use.
	 * Care needs to be exercised with respect to the integration.
	 * `T. A. Manz <10.1039/c6ra04656h>`_ and `N. G. Limas <10.1039/C6RA05507A>`_ discuss computational details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @param chargeDataset the chargeDataset
	 */
	public DataNode setCharge(IDataset chargeDataset);

	/**
	 * Signed net (partial) charge of the (molecular) ion.
	 * Different methods for computing charge are in use.
	 * Care needs to be exercised with respect to the integration.
	 * `T. A. Manz <10.1039/c6ra04656h>`_ and `N. G. Limas <10.1039/C6RA05507A>`_ discuss computational details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getChargeScalar();

	/**
	 * Signed net (partial) charge of the (molecular) ion.
	 * Different methods for computing charge are in use.
	 * Care needs to be exercised with respect to the integration.
	 * `T. A. Manz <10.1039/c6ra04656h>`_ and `N. G. Limas <10.1039/C6RA05507A>`_ discuss computational details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CHARGE
	 * </p>
	 *
	 * @param charge the charge
	 */
	public DataNode setChargeScalar(Number chargeValue);

	/**
	 * Charge reported in multiples of the charge of an electron.
	 * For research using atom probe tomography the value should be set to
	 * zero if the charge_state is unknown and irrecoverable. This can happen
	 * when classical ranging definition files in formats like RNG, RRNG are used.
	 * These file formats do not document the charge state explicitly but only
	 * the number of atoms of each element per molecular ion surplus the
	 * respective mass-to-charge-state-ratio interval.
	 * Details on ranging definition files in the literature are `M. K. Miller <https://doi.org/10.1002/sia.1719>`_.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCharge_state();

	/**
	 * Charge reported in multiples of the charge of an electron.
	 * For research using atom probe tomography the value should be set to
	 * zero if the charge_state is unknown and irrecoverable. This can happen
	 * when classical ranging definition files in formats like RNG, RRNG are used.
	 * These file formats do not document the charge state explicitly but only
	 * the number of atoms of each element per molecular ion surplus the
	 * respective mass-to-charge-state-ratio interval.
	 * Details on ranging definition files in the literature are `M. K. Miller <https://doi.org/10.1002/sia.1719>`_.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param charge_stateDataset the charge_stateDataset
	 */
	public DataNode setCharge_state(IDataset charge_stateDataset);

	/**
	 * Charge reported in multiples of the charge of an electron.
	 * For research using atom probe tomography the value should be set to
	 * zero if the charge_state is unknown and irrecoverable. This can happen
	 * when classical ranging definition files in formats like RNG, RRNG are used.
	 * These file formats do not document the charge state explicitly but only
	 * the number of atoms of each element per molecular ion surplus the
	 * respective mass-to-charge-state-ratio interval.
	 * Details on ranging definition files in the literature are `M. K. Miller <https://doi.org/10.1002/sia.1719>`_.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCharge_stateScalar();

	/**
	 * Charge reported in multiples of the charge of an electron.
	 * For research using atom probe tomography the value should be set to
	 * zero if the charge_state is unknown and irrecoverable. This can happen
	 * when classical ranging definition files in formats like RNG, RRNG are used.
	 * These file formats do not document the charge state explicitly but only
	 * the number of atoms of each element per molecular ion surplus the
	 * respective mass-to-charge-state-ratio interval.
	 * Details on ranging definition files in the literature are `M. K. Miller <https://doi.org/10.1002/sia.1719>`_.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param charge_state the charge_state
	 */
	public DataNode setCharge_stateScalar(Number charge_stateValue);

	/**
	 * Assumed volume affected by the set of atoms.
	 * Neither individual atoms nor a set of cluster of these have a volume
	 * that is unique as a some cut-off criterion is required.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Assumed volume affected by the set of atoms.
	 * Neither individual atoms nor a set of cluster of these have a volume
	 * that is unique as a some cut-off criterion is required.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Assumed volume affected by the set of atoms.
	 * Neither individual atoms nor a set of cluster of these have a volume
	 * that is unique as a some cut-off criterion is required.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * Assumed volume affected by the set of atoms.
	 * Neither individual atoms nor a set of cluster of these have a volume
	 * that is unique as a some cut-off criterion is required.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 * Index for each atom at locations as detailed by position.
	 * Indices can be used as identifier and thus names for individual atoms.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndices();

	/**
	 * Index for each atom at locations as detailed by position.
	 * Indices can be used as identifier and thus names for individual atoms.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param indicesDataset the indicesDataset
	 */
	public DataNode setIndices(IDataset indicesDataset);

	/**
	 * Index for each atom at locations as detailed by position.
	 * Indices can be used as identifier and thus names for individual atoms.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getIndicesScalar();

	/**
	 * Index for each atom at locations as detailed by position.
	 * Indices can be used as identifier and thus names for individual atoms.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param indices the indices
	 */
	public DataNode setIndicesScalar(String indicesValue);

	/**
	 * Nuclide information for each atom at locations as detailed by position.
	 * One `approach <https://doi.org/10.1017/S1431927621012241>`_ for storing nuclide information
	 * efficiently is via individual hash values.
	 * Consult the docstring of ``nuclide_hash`` for further details.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Nuclide information for each atom at locations as detailed by position.
	 * One `approach <https://doi.org/10.1017/S1431927621012241>`_ for storing nuclide information
	 * efficiently is via individual hash values.
	 * Consult the docstring of ``nuclide_hash`` for further details.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Nuclide information for each atom at locations as detailed by position.
	 * One `approach <https://doi.org/10.1017/S1431927621012241>`_ for storing nuclide information
	 * efficiently is via individual hash values.
	 * Consult the docstring of ``nuclide_hash`` for further details.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getTypeScalar();

	/**
	 * Nuclide information for each atom at locations as detailed by position.
	 * One `approach <https://doi.org/10.1017/S1431927621012241>`_ for storing nuclide information
	 * efficiently is via individual hash values.
	 * Consult the docstring of ``nuclide_hash`` for further details.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(Long typeValue);

	/**
	 * Position of each atom.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_pos; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPosition();

	/**
	 * Position of each atom.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_pos; 2: d;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * Position of each atom.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_pos; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * Position of each atom.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_pos; 2: d;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * Path to an instance of :ref:`NXcoordinate_system` to document
	 * the reference frame in which the positions are defined.
	 * This resolves ambiguity when the reference frame is different
	 * to the NeXus default reference frame (McStas).
	 *
	 * @return  the value.
	 */
	public String getPositionAttributeDepends_on();

	/**
	 * Path to an instance of :ref:`NXcoordinate_system` to document
	 * the reference frame in which the positions are defined.
	 * This resolves ambiguity when the reference frame is different
	 * to the NeXus default reference frame (McStas).
	 *
	 * @param depends_onValue the depends_onValue
	 */
	public void setPositionAttributeDepends_on(String depends_onValue);

	/**
	 * Relative occupancy of the atom position.
	 * This field is useful for specifying the atomic motif in
	 * instances of :ref:`NXunit_cell`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOccupancy();

	/**
	 * Relative occupancy of the atom position.
	 * This field is useful for specifying the atomic motif in
	 * instances of :ref:`NXunit_cell`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param occupancyDataset the occupancyDataset
	 */
	public DataNode setOccupancy(IDataset occupancyDataset);

	/**
	 * Relative occupancy of the atom position.
	 * This field is useful for specifying the atomic motif in
	 * instances of :ref:`NXunit_cell`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOccupancyScalar();

	/**
	 * Relative occupancy of the atom position.
	 * This field is useful for specifying the atomic motif in
	 * instances of :ref:`NXunit_cell`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param occupancy the occupancy
	 */
	public DataNode setOccupancyScalar(Number occupancyValue);

	/**
	 * Vector of nuclide hash values. The vector is sorted in decreasing order.
	 * Individual hash values :math:`H` `encode <https://doi.org/10.1017/S1431927621012241>`_
	 * for each nuclide or element the number of protons :math:`Z` and a constant :math:`c`
	 * via the following hashing rule :math:`H = Z + c \cdot 256`. :math:`Z` and :math:`c` must be 8-bit unsigned integers.
	 * The constant :math:`c` is either set to number of neutrons :math:`N` or to the special value 255.
	 * The special value 255 is used to refer to all isotopes of an element from the IUPAC periodic table.
	 * Some examples:
	 * * The element hydrogen (meaning irrespective which isotope), its hash value is :math:`H = 1 + 255 \cdot 256 = 65281`.
	 * * The :math:`^{1}H` hydrogen isotope (:math:`Z = 1, N = 0`), its hash value is :math:`H = 1 + 0 \cdot 256 = 1`.
	 * * The :math:`^{2}H` deuterium isotope (:math:`Z = 1, N = 1`), its hash value is :math:`H = 1 + 1 \cdot 256 = 257`.
	 * * The :math:`^{3}H` tritium isotope (:math:`Z = 1, N = 2`), its hash value is :math:`H = 1 + 2 \cdot 256 = 513`.
	 * * The :math:`^{99}Tc` technetium isotope (:math:`Z = 43, N = 56`), its hash value is :math:`H = 43 + 56 \cdot 256 = 14379`.
	 * The special hash value :math:`0` is a placeholder.
	 * This hashing rule implements a bitshift operation. The benefit is that this enables encoding of all
	 * currently known nuclides and elements efficiently into an 16-bit unsigned integer. Sufficient
	 * unused indices remain to case situations when new elements will be discovered.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNuclide_hash();

	/**
	 * Vector of nuclide hash values. The vector is sorted in decreasing order.
	 * Individual hash values :math:`H` `encode <https://doi.org/10.1017/S1431927621012241>`_
	 * for each nuclide or element the number of protons :math:`Z` and a constant :math:`c`
	 * via the following hashing rule :math:`H = Z + c \cdot 256`. :math:`Z` and :math:`c` must be 8-bit unsigned integers.
	 * The constant :math:`c` is either set to number of neutrons :math:`N` or to the special value 255.
	 * The special value 255 is used to refer to all isotopes of an element from the IUPAC periodic table.
	 * Some examples:
	 * * The element hydrogen (meaning irrespective which isotope), its hash value is :math:`H = 1 + 255 \cdot 256 = 65281`.
	 * * The :math:`^{1}H` hydrogen isotope (:math:`Z = 1, N = 0`), its hash value is :math:`H = 1 + 0 \cdot 256 = 1`.
	 * * The :math:`^{2}H` deuterium isotope (:math:`Z = 1, N = 1`), its hash value is :math:`H = 1 + 1 \cdot 256 = 257`.
	 * * The :math:`^{3}H` tritium isotope (:math:`Z = 1, N = 2`), its hash value is :math:`H = 1 + 2 \cdot 256 = 513`.
	 * * The :math:`^{99}Tc` technetium isotope (:math:`Z = 43, N = 56`), its hash value is :math:`H = 43 + 56 \cdot 256 = 14379`.
	 * The special hash value :math:`0` is a placeholder.
	 * This hashing rule implements a bitshift operation. The benefit is that this enables encoding of all
	 * currently known nuclides and elements efficiently into an 16-bit unsigned integer. Sufficient
	 * unused indices remain to case situations when new elements will be discovered.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max;
	 * </p>
	 *
	 * @param nuclide_hashDataset the nuclide_hashDataset
	 */
	public DataNode setNuclide_hash(IDataset nuclide_hashDataset);

	/**
	 * Vector of nuclide hash values. The vector is sorted in decreasing order.
	 * Individual hash values :math:`H` `encode <https://doi.org/10.1017/S1431927621012241>`_
	 * for each nuclide or element the number of protons :math:`Z` and a constant :math:`c`
	 * via the following hashing rule :math:`H = Z + c \cdot 256`. :math:`Z` and :math:`c` must be 8-bit unsigned integers.
	 * The constant :math:`c` is either set to number of neutrons :math:`N` or to the special value 255.
	 * The special value 255 is used to refer to all isotopes of an element from the IUPAC periodic table.
	 * Some examples:
	 * * The element hydrogen (meaning irrespective which isotope), its hash value is :math:`H = 1 + 255 \cdot 256 = 65281`.
	 * * The :math:`^{1}H` hydrogen isotope (:math:`Z = 1, N = 0`), its hash value is :math:`H = 1 + 0 \cdot 256 = 1`.
	 * * The :math:`^{2}H` deuterium isotope (:math:`Z = 1, N = 1`), its hash value is :math:`H = 1 + 1 \cdot 256 = 257`.
	 * * The :math:`^{3}H` tritium isotope (:math:`Z = 1, N = 2`), its hash value is :math:`H = 1 + 2 \cdot 256 = 513`.
	 * * The :math:`^{99}Tc` technetium isotope (:math:`Z = 43, N = 56`), its hash value is :math:`H = 43 + 56 \cdot 256 = 14379`.
	 * The special hash value :math:`0` is a placeholder.
	 * This hashing rule implements a bitshift operation. The benefit is that this enables encoding of all
	 * currently known nuclides and elements efficiently into an 16-bit unsigned integer. Sufficient
	 * unused indices remain to case situations when new elements will be discovered.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNuclide_hashScalar();

	/**
	 * Vector of nuclide hash values. The vector is sorted in decreasing order.
	 * Individual hash values :math:`H` `encode <https://doi.org/10.1017/S1431927621012241>`_
	 * for each nuclide or element the number of protons :math:`Z` and a constant :math:`c`
	 * via the following hashing rule :math:`H = Z + c \cdot 256`. :math:`Z` and :math:`c` must be 8-bit unsigned integers.
	 * The constant :math:`c` is either set to number of neutrons :math:`N` or to the special value 255.
	 * The special value 255 is used to refer to all isotopes of an element from the IUPAC periodic table.
	 * Some examples:
	 * * The element hydrogen (meaning irrespective which isotope), its hash value is :math:`H = 1 + 255 \cdot 256 = 65281`.
	 * * The :math:`^{1}H` hydrogen isotope (:math:`Z = 1, N = 0`), its hash value is :math:`H = 1 + 0 \cdot 256 = 1`.
	 * * The :math:`^{2}H` deuterium isotope (:math:`Z = 1, N = 1`), its hash value is :math:`H = 1 + 1 \cdot 256 = 257`.
	 * * The :math:`^{3}H` tritium isotope (:math:`Z = 1, N = 2`), its hash value is :math:`H = 1 + 2 \cdot 256 = 513`.
	 * * The :math:`^{99}Tc` technetium isotope (:math:`Z = 43, N = 56`), its hash value is :math:`H = 43 + 56 \cdot 256 = 14379`.
	 * The special hash value :math:`0` is a placeholder.
	 * This hashing rule implements a bitshift operation. The benefit is that this enables encoding of all
	 * currently known nuclides and elements efficiently into an 16-bit unsigned integer. Sufficient
	 * unused indices remain to case situations when new elements will be discovered.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max;
	 * </p>
	 *
	 * @param nuclide_hash the nuclide_hash
	 */
	public DataNode setNuclide_hashScalar(Long nuclide_hashValue);

	/**
	 * Table which decodes the entries in nuclide_hash into a human-readable matrix
	 * instances for either nuclides or elements. Specifically, the first row specifies the
	 * nuclide mass number. When the nuclide_hash values are used this means
	 * the row should report the sum :math:`Z + N` or 0. The value 0 documents that
	 * an element from the IUPAC periodic table is meant.
	 * The second row specifies the number of protons :math:`Z`.
	 * The value 0 in this case documents a placeholder or that no element-specific
	 * information is relevant.
	 * Taking a carbon-14 nuclide as an example the mass number is 14.
	 * That is encoded as a column vector (14, 6).
	 * The array is stored matching the order of nuclide_hash.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNuclide_list();

	/**
	 * Table which decodes the entries in nuclide_hash into a human-readable matrix
	 * instances for either nuclides or elements. Specifically, the first row specifies the
	 * nuclide mass number. When the nuclide_hash values are used this means
	 * the row should report the sum :math:`Z + N` or 0. The value 0 documents that
	 * an element from the IUPAC periodic table is meant.
	 * The second row specifies the number of protons :math:`Z`.
	 * The value 0 in this case documents a placeholder or that no element-specific
	 * information is relevant.
	 * Taking a carbon-14 nuclide as an example the mass number is 14.
	 * That is encoded as a column vector (14, 6).
	 * The array is stored matching the order of nuclide_hash.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max; 2: 2;
	 * </p>
	 *
	 * @param nuclide_listDataset the nuclide_listDataset
	 */
	public DataNode setNuclide_list(IDataset nuclide_listDataset);

	/**
	 * Table which decodes the entries in nuclide_hash into a human-readable matrix
	 * instances for either nuclides or elements. Specifically, the first row specifies the
	 * nuclide mass number. When the nuclide_hash values are used this means
	 * the row should report the sum :math:`Z + N` or 0. The value 0 documents that
	 * an element from the IUPAC periodic table is meant.
	 * The second row specifies the number of protons :math:`Z`.
	 * The value 0 in this case documents a placeholder or that no element-specific
	 * information is relevant.
	 * Taking a carbon-14 nuclide as an example the mass number is 14.
	 * That is encoded as a column vector (14, 6).
	 * The array is stored matching the order of nuclide_hash.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNuclide_listScalar();

	/**
	 * Table which decodes the entries in nuclide_hash into a human-readable matrix
	 * instances for either nuclides or elements. Specifically, the first row specifies the
	 * nuclide mass number. When the nuclide_hash values are used this means
	 * the row should report the sum :math:`Z + N` or 0. The value 0 documents that
	 * an element from the IUPAC periodic table is meant.
	 * The second row specifies the number of protons :math:`Z`.
	 * The value 0 in this case documents a placeholder or that no element-specific
	 * information is relevant.
	 * Taking a carbon-14 nuclide as an example the mass number is 14.
	 * That is encoded as a column vector (14, 6).
	 * The array is stored matching the order of nuclide_hash.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ivec_max; 2: 2;
	 * </p>
	 *
	 * @param nuclide_list the nuclide_list
	 */
	public DataNode setNuclide_listScalar(Long nuclide_listValue);

	/**
	 * Associated lower :math:`{\frac{m}{q}}_{min}` and upper :math:`{\frac{m}{q}}_{max}` bounds of the
	 * mass-to-charge-state ratio interval(s) :math:`[{\frac{m}{q}}_{min}, {\frac{m}{q}}_{max}]`.
	 * (boundaries inclusive). This field is primarily of interest for documenting :ref:`NXprocess`
	 * steps of indexing a ToF/mass-to-charge-state ratio histogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMass_to_charge_range();

	/**
	 * Associated lower :math:`{\frac{m}{q}}_{min}` and upper :math:`{\frac{m}{q}}_{max}` bounds of the
	 * mass-to-charge-state ratio interval(s) :math:`[{\frac{m}{q}}_{min}, {\frac{m}{q}}_{max}]`.
	 * (boundaries inclusive). This field is primarily of interest for documenting :ref:`NXprocess`
	 * steps of indexing a ToF/mass-to-charge-state ratio histogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @param mass_to_charge_rangeDataset the mass_to_charge_rangeDataset
	 */
	public DataNode setMass_to_charge_range(IDataset mass_to_charge_rangeDataset);

	/**
	 * Associated lower :math:`{\frac{m}{q}}_{min}` and upper :math:`{\frac{m}{q}}_{max}` bounds of the
	 * mass-to-charge-state ratio interval(s) :math:`[{\frac{m}{q}}_{min}, {\frac{m}{q}}_{max}]`.
	 * (boundaries inclusive). This field is primarily of interest for documenting :ref:`NXprocess`
	 * steps of indexing a ToF/mass-to-charge-state ratio histogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMass_to_charge_rangeScalar();

	/**
	 * Associated lower :math:`{\frac{m}{q}}_{min}` and upper :math:`{\frac{m}{q}}_{max}` bounds of the
	 * mass-to-charge-state ratio interval(s) :math:`[{\frac{m}{q}}_{min}, {\frac{m}{q}}_{max}]`.
	 * (boundaries inclusive). This field is primarily of interest for documenting :ref:`NXprocess`
	 * steps of indexing a ToF/mass-to-charge-state ratio histogram.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_ranges; 2: 2;
	 * </p>
	 *
	 * @param mass_to_charge_range the mass_to_charge_range
	 */
	public DataNode setMass_to_charge_rangeScalar(Number mass_to_charge_rangeValue);

}
