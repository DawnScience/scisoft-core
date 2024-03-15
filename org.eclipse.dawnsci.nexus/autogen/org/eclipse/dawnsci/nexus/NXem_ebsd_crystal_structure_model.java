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
 * Crystal structure phase models used for indexing Kikuchi pattern.
 * This base class contains key metadata relevant to every physical
 * kinematic or dynamic diffraction model to be used for simulating
 * Kikuchi diffraction pattern.
 * The actual indexing of Kikuchi pattern however maybe use different
 * algorithms which build on these simulation results but evaluate different
 * workflows of comparing simulated and measured Kikuchi pattern to make
 * suggestions which orientation is the most likely (if any) for each
 * scan point investigated.
 * Traditionally Hough transform based indexing has been the most frequently
 * used algorithm. More and more dictionary based alternatives are used.
 * Either way both algorithm need a crystal structure model.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_hkl</b>
 * Number of reflectors (Miller crystallographic plane triplets).</li>
 * <li><b>n_pos</b>
 * Number of atom positions.</li></ul></p>
 *
 */
public interface NXem_ebsd_crystal_structure_model extends NXobject {

	public static final String NX_CRYSTALLOGRAPHIC_DATABASE_IDENTIFIER = "crystallographic_database_identifier";
	public static final String NX_CRYSTALLOGRAPHIC_DATABASE = "crystallographic_database";
	public static final String NX_UNIT_CELL_ABC = "unit_cell_abc";
	public static final String NX_UNIT_CELL_ALPHABETAGAMMA = "unit_cell_alphabetagamma";
	public static final String NX_UNIT_CELL_VOLUME = "unit_cell_volume";
	public static final String NX_SPACE_GROUP = "space_group";
	public static final String NX_IS_CENTROSYMMETRIC = "is_centrosymmetric";
	public static final String NX_IS_CHIRAL = "is_chiral";
	public static final String NX_LAUE_GROUP = "laue_group";
	public static final String NX_POINT_GROUP = "point_group";
	public static final String NX_UNIT_CELL_CLASS = "unit_cell_class";
	public static final String NX_PHASE_IDENTIFIER = "phase_identifier";
	public static final String NX_PHASE_NAME = "phase_name";
	public static final String NX_ATOM_IDENTIFIER = "atom_identifier";
	public static final String NX_ATOM = "atom";
	public static final String NX_ATOM_POSITIONS = "atom_positions";
	public static final String NX_ATOM_OCCUPANCY = "atom_occupancy";
	public static final String NX_NUMBER_OF_PLANES = "number_of_planes";
	public static final String NX_PLANE_MILLER = "plane_miller";
	public static final String NX_DSPACING = "dspacing";
	public static final String NX_RELATIVE_INTENSITY = "relative_intensity";
	/**
	 * Identifier of an entry from crystallographic_database which was used
	 * for creating this structure model.
	 *
	 * @return  the value.
	 */
	public IDataset getCrystallographic_database_identifier();

	/**
	 * Identifier of an entry from crystallographic_database which was used
	 * for creating this structure model.
	 *
	 * @param crystallographic_database_identifierDataset the crystallographic_database_identifierDataset
	 */
	public DataNode setCrystallographic_database_identifier(IDataset crystallographic_database_identifierDataset);

	/**
	 * Identifier of an entry from crystallographic_database which was used
	 * for creating this structure model.
	 *
	 * @return  the value.
	 */
	public String getCrystallographic_database_identifierScalar();

	/**
	 * Identifier of an entry from crystallographic_database which was used
	 * for creating this structure model.
	 *
	 * @param crystallographic_database_identifier the crystallographic_database_identifier
	 */
	public DataNode setCrystallographic_database_identifierScalar(String crystallographic_database_identifierValue);

	/**
	 * Name of the crystallographic database to resolve
	 * crystallographic_database_identifier e.g. COD or others.
	 *
	 * @return  the value.
	 */
	public IDataset getCrystallographic_database();

	/**
	 * Name of the crystallographic database to resolve
	 * crystallographic_database_identifier e.g. COD or others.
	 *
	 * @param crystallographic_databaseDataset the crystallographic_databaseDataset
	 */
	public DataNode setCrystallographic_database(IDataset crystallographic_databaseDataset);

	/**
	 * Name of the crystallographic database to resolve
	 * crystallographic_database_identifier e.g. COD or others.
	 *
	 * @return  the value.
	 */
	public String getCrystallographic_databaseScalar();

	/**
	 * Name of the crystallographic database to resolve
	 * crystallographic_database_identifier e.g. COD or others.
	 *
	 * @param crystallographic_database the crystallographic_database
	 */
	public DataNode setCrystallographic_databaseScalar(String crystallographic_databaseValue);

	/**
	 * Crystallography unit cell parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUnit_cell_abc();

	/**
	 * Crystallography unit cell parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param unit_cell_abcDataset the unit_cell_abcDataset
	 */
	public DataNode setUnit_cell_abc(IDataset unit_cell_abcDataset);

	/**
	 * Crystallography unit cell parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_abcScalar();

	/**
	 * Crystallography unit cell parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param unit_cell_abc the unit_cell_abc
	 */
	public DataNode setUnit_cell_abcScalar(Double unit_cell_abcValue);

	/**
	 * Crystallography unit cell parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUnit_cell_alphabetagamma();

	/**
	 * Crystallography unit cell parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param unit_cell_alphabetagammaDataset the unit_cell_alphabetagammaDataset
	 */
	public DataNode setUnit_cell_alphabetagamma(IDataset unit_cell_alphabetagammaDataset);

	/**
	 * Crystallography unit cell parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_alphabetagammaScalar();

	/**
	 * Crystallography unit cell parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param unit_cell_alphabetagamma the unit_cell_alphabetagamma
	 */
	public DataNode setUnit_cell_alphabetagammaScalar(Double unit_cell_alphabetagammaValue);

	/**
	 * Volume of the unit cell
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUnit_cell_volume();

	/**
	 * Volume of the unit cell
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param unit_cell_volumeDataset the unit_cell_volumeDataset
	 */
	public DataNode setUnit_cell_volume(IDataset unit_cell_volumeDataset);

	/**
	 * Volume of the unit cell
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_volumeScalar();

	/**
	 * Volume of the unit cell
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param unit_cell_volume the unit_cell_volume
	 */
	public DataNode setUnit_cell_volumeScalar(Double unit_cell_volumeValue);

	/**
	 * Crystallographic space group
	 *
	 * @return  the value.
	 */
	public IDataset getSpace_group();

	/**
	 * Crystallographic space group
	 *
	 * @param space_groupDataset the space_groupDataset
	 */
	public DataNode setSpace_group(IDataset space_groupDataset);

	/**
	 * Crystallographic space group
	 *
	 * @return  the value.
	 */
	public String getSpace_groupScalar();

	/**
	 * Crystallographic space group
	 *
	 * @param space_group the space_group
	 */
	public DataNode setSpace_groupScalar(String space_groupValue);

	/**
	 * True if space group is considered a centrosymmetric one.
	 * False if space group is considered a non-centrosymmetric one.
	 * Centrosymmetric has all types and combinations of symmetry elements
	 * (translation, rotational axis, mirror planes, center of inversion)
	 * Non-centrosymmetric compared to centrosymmetric is constrained (no inversion).
	 * Chiral compared to non-centrosymmetric is constrained (no mirror planes).
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIs_centrosymmetric();

	/**
	 * True if space group is considered a centrosymmetric one.
	 * False if space group is considered a non-centrosymmetric one.
	 * Centrosymmetric has all types and combinations of symmetry elements
	 * (translation, rotational axis, mirror planes, center of inversion)
	 * Non-centrosymmetric compared to centrosymmetric is constrained (no inversion).
	 * Chiral compared to non-centrosymmetric is constrained (no mirror planes).
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_centrosymmetricDataset the is_centrosymmetricDataset
	 */
	public DataNode setIs_centrosymmetric(IDataset is_centrosymmetricDataset);

	/**
	 * True if space group is considered a centrosymmetric one.
	 * False if space group is considered a non-centrosymmetric one.
	 * Centrosymmetric has all types and combinations of symmetry elements
	 * (translation, rotational axis, mirror planes, center of inversion)
	 * Non-centrosymmetric compared to centrosymmetric is constrained (no inversion).
	 * Chiral compared to non-centrosymmetric is constrained (no mirror planes).
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_centrosymmetricScalar();

	/**
	 * True if space group is considered a centrosymmetric one.
	 * False if space group is considered a non-centrosymmetric one.
	 * Centrosymmetric has all types and combinations of symmetry elements
	 * (translation, rotational axis, mirror planes, center of inversion)
	 * Non-centrosymmetric compared to centrosymmetric is constrained (no inversion).
	 * Chiral compared to non-centrosymmetric is constrained (no mirror planes).
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_centrosymmetric the is_centrosymmetric
	 */
	public DataNode setIs_centrosymmetricScalar(Boolean is_centrosymmetricValue);

	/**
	 * True if space group is considered a chiral one.
	 * False if space group is consider a non-chiral one.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIs_chiral();

	/**
	 * True if space group is considered a chiral one.
	 * False if space group is consider a non-chiral one.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_chiralDataset the is_chiralDataset
	 */
	public DataNode setIs_chiral(IDataset is_chiralDataset);

	/**
	 * True if space group is considered a chiral one.
	 * False if space group is consider a non-chiral one.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_chiralScalar();

	/**
	 * True if space group is considered a chiral one.
	 * False if space group is consider a non-chiral one.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_chiral the is_chiral
	 */
	public DataNode setIs_chiralScalar(Boolean is_chiralValue);

	/**
	 * Laue group
	 *
	 * @return  the value.
	 */
	public IDataset getLaue_group();

	/**
	 * Laue group
	 *
	 * @param laue_groupDataset the laue_groupDataset
	 */
	public DataNode setLaue_group(IDataset laue_groupDataset);

	/**
	 * Laue group
	 *
	 * @return  the value.
	 */
	public String getLaue_groupScalar();

	/**
	 * Laue group
	 *
	 * @param laue_group the laue_group
	 */
	public DataNode setLaue_groupScalar(String laue_groupValue);

	/**
	 * Point group using International Notation.
	 *
	 * @return  the value.
	 */
	public IDataset getPoint_group();

	/**
	 * Point group using International Notation.
	 *
	 * @param point_groupDataset the point_groupDataset
	 */
	public DataNode setPoint_group(IDataset point_groupDataset);

	/**
	 * Point group using International Notation.
	 *
	 * @return  the value.
	 */
	public String getPoint_groupScalar();

	/**
	 * Point group using International Notation.
	 *
	 * @param point_group the point_group
	 */
	public DataNode setPoint_groupScalar(String point_groupValue);

	/**
	 * Crystal system
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>rhombohedral</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getUnit_cell_class();

	/**
	 * Crystal system
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>rhombohedral</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param unit_cell_classDataset the unit_cell_classDataset
	 */
	public DataNode setUnit_cell_class(IDataset unit_cell_classDataset);

	/**
	 * Crystal system
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>rhombohedral</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getUnit_cell_classScalar();

	/**
	 * Crystal system
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>rhombohedral</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param unit_cell_class the unit_cell_class
	 */
	public DataNode setUnit_cell_classScalar(String unit_cell_classValue);

	/**
	 * Numeric identifier for each phase.
	 * The value 0 is reserved for the unknown phase essentially representing the
	 * null-model that no phase model was sufficiently significantly confirmed.
	 * Consequently, the value 0 must not be used as a phase_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPhase_identifier();

	/**
	 * Numeric identifier for each phase.
	 * The value 0 is reserved for the unknown phase essentially representing the
	 * null-model that no phase model was sufficiently significantly confirmed.
	 * Consequently, the value 0 must not be used as a phase_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param phase_identifierDataset the phase_identifierDataset
	 */
	public DataNode setPhase_identifier(IDataset phase_identifierDataset);

	/**
	 * Numeric identifier for each phase.
	 * The value 0 is reserved for the unknown phase essentially representing the
	 * null-model that no phase model was sufficiently significantly confirmed.
	 * Consequently, the value 0 must not be used as a phase_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPhase_identifierScalar();

	/**
	 * Numeric identifier for each phase.
	 * The value 0 is reserved for the unknown phase essentially representing the
	 * null-model that no phase model was sufficiently significantly confirmed.
	 * Consequently, the value 0 must not be used as a phase_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param phase_identifier the phase_identifier
	 */
	public DataNode setPhase_identifierScalar(Long phase_identifierValue);

	/**
	 * Name of the phase/alias.
	 *
	 * @return  the value.
	 */
	public IDataset getPhase_name();

	/**
	 * Name of the phase/alias.
	 *
	 * @param phase_nameDataset the phase_nameDataset
	 */
	public DataNode setPhase_name(IDataset phase_nameDataset);

	/**
	 * Name of the phase/alias.
	 *
	 * @return  the value.
	 */
	public String getPhase_nameScalar();

	/**
	 * Name of the phase/alias.
	 *
	 * @param phase_name the phase_name
	 */
	public DataNode setPhase_nameScalar(String phase_nameValue);

	/**
	 * Labels for each atom position
	 * <p>
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAtom_identifier();

	/**
	 * Labels for each atom position
	 * <p>
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atom_identifierDataset the atom_identifierDataset
	 */
	public DataNode setAtom_identifier(IDataset atom_identifierDataset);

	/**
	 * Labels for each atom position
	 * <p>
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAtom_identifierScalar();

	/**
	 * Labels for each atom position
	 * <p>
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atom_identifier the atom_identifier
	 */
	public DataNode setAtom_identifierScalar(String atom_identifierValue);

	/**
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively. Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAtom();

	/**
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively. Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atomDataset the atomDataset
	 */
	public DataNode setAtom(IDataset atomDataset);

	/**
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively. Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getAtomScalar();

	/**
	 * The hash value :math:`H` is :math:`H = Z + N*256` with :math:`Z`
	 * the number of protons and :math:`N` the number of neutrons
	 * of each isotope respectively. Z and N have to be 8-bit unsigned integers.
	 * For the rationale behind this `M. Kühbach et al. (2021) <https://doi.org/10.1017/S1431927621012241>`_
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atom the atom
	 */
	public DataNode setAtomScalar(Long atomValue);

	/**
	 * Atom positions x, y, z.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_pos; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAtom_positions();

	/**
	 * Atom positions x, y, z.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_pos; 2: 3;
	 * </p>
	 *
	 * @param atom_positionsDataset the atom_positionsDataset
	 */
	public DataNode setAtom_positions(IDataset atom_positionsDataset);

	/**
	 * Atom positions x, y, z.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_pos; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAtom_positionsScalar();

	/**
	 * Atom positions x, y, z.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_pos; 2: 3;
	 * </p>
	 *
	 * @param atom_positions the atom_positions
	 */
	public DataNode setAtom_positionsScalar(Double atom_positionsValue);

	/**
	 * Relative occupancy of the atom position.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAtom_occupancy();

	/**
	 * Relative occupancy of the atom position.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atom_occupancyDataset the atom_occupancyDataset
	 */
	public DataNode setAtom_occupancy(IDataset atom_occupancyDataset);

	/**
	 * Relative occupancy of the atom position.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAtom_occupancyScalar();

	/**
	 * Relative occupancy of the atom position.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_pos;
	 * </p>
	 *
	 * @param atom_occupancy the atom_occupancy
	 */
	public DataNode setAtom_occupancyScalar(Double atom_occupancyValue);

	/**
	 * How many reflectors are distinguished. Value has to be n_hkl.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_planes();

	/**
	 * How many reflectors are distinguished. Value has to be n_hkl.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_planesDataset the number_of_planesDataset
	 */
	public DataNode setNumber_of_planes(IDataset number_of_planesDataset);

	/**
	 * How many reflectors are distinguished. Value has to be n_hkl.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_planesScalar();

	/**
	 * How many reflectors are distinguished. Value has to be n_hkl.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_planes the number_of_planes
	 */
	public DataNode setNumber_of_planesScalar(Long number_of_planesValue);

	/**
	 * Miller indices :math:`(hkl)`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_hkl; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPlane_miller();

	/**
	 * Miller indices :math:`(hkl)`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_hkl; 2: 3;
	 * </p>
	 *
	 * @param plane_millerDataset the plane_millerDataset
	 */
	public DataNode setPlane_miller(IDataset plane_millerDataset);

	/**
	 * Miller indices :math:`(hkl)`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_hkl; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPlane_millerScalar();

	/**
	 * Miller indices :math:`(hkl)`.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_hkl; 2: 3;
	 * </p>
	 *
	 * @param plane_miller the plane_miller
	 */
	public DataNode setPlane_millerScalar(Number plane_millerValue);

	/**
	 * D-spacing.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDspacing();

	/**
	 * D-spacing.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @param dspacingDataset the dspacingDataset
	 */
	public DataNode setDspacing(IDataset dspacingDataset);

	/**
	 * D-spacing.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDspacingScalar();

	/**
	 * D-spacing.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @param dspacing the dspacing
	 */
	public DataNode setDspacingScalar(Double dspacingValue);

	/**
	 * Relative intensity of the signal for the plane.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getRelative_intensity();

	/**
	 * Relative intensity of the signal for the plane.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @param relative_intensityDataset the relative_intensityDataset
	 */
	public DataNode setRelative_intensity(IDataset relative_intensityDataset);

	/**
	 * Relative intensity of the signal for the plane.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRelative_intensityScalar();

	/**
	 * Relative intensity of the signal for the plane.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_hkl;
	 * </p>
	 *
	 * @param relative_intensity the relative_intensity
	 */
	public DataNode setRelative_intensityScalar(Double relative_intensityValue);

}
