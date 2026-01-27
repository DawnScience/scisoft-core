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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class to describe structural aspects of an arrangement of
 * atoms or ions including a crystallographic unit cell.
 * Following recommendations of `CIF <https://www.iucr.org/resources/cif/spec/version1.1>`_ and `International Tables of Crystallography <https://it.iucr.org/>`_.
 * <p><b>Symbols:</b><ul>
 * <li><b>d</b>
 * Dimensionality of the lattice.</li></ul></p>
 *
 */
public interface NXunit_cell extends NXobject {

	public static final String NX_REFERENCE_FRAME = "reference_frame";
	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_A_B_C = "a_b_c";
	public static final String NX_A = "a";
	public static final String NX_B = "b";
	public static final String NX_C = "c";
	public static final String NX_ALPHA_BETA_GAMMA = "alpha_beta_gamma";
	public static final String NX_ALPHA = "alpha";
	public static final String NX_BETA = "beta";
	public static final String NX_GAMMA = "gamma";
	public static final String NX_CRYSTAL_SYSTEM = "crystal_system";
	public static final String NX_LAUE_GROUP = "laue_group";
	public static final String NX_POINT_GROUP = "point_group";
	public static final String NX_SPACE_GROUP = "space_group";
	public static final String NX_IS_CENTROSYMMETRIC = "is_centrosymmetric";
	public static final String NX_IS_CHIRAL = "is_chiral";
	public static final String NX_AREA = "area";
	public static final String NX_VOLUME = "volume";
	/**
	 * Path to a reference frame in which the unit cell is defined
	 * to resolve ambiguity in cases when e.g. a different reference frame
	 * than the NeXus default reference frame (McStas) was chosen.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReference_frame();

	/**
	 * Path to a reference frame in which the unit cell is defined
	 * to resolve ambiguity in cases when e.g. a different reference frame
	 * than the NeXus default reference frame (McStas) was chosen.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param reference_frameDataset the reference_frameDataset
	 */
	public DataNode setReference_frame(IDataset reference_frameDataset);

	/**
	 * Path to a reference frame in which the unit cell is defined
	 * to resolve ambiguity in cases when e.g. a different reference frame
	 * than the NeXus default reference frame (McStas) was chosen.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getReference_frameScalar();

	/**
	 * Path to a reference frame in which the unit cell is defined
	 * to resolve ambiguity in cases when e.g. a different reference frame
	 * than the NeXus default reference frame (McStas) was chosen.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param reference_frame the reference_frame
	 */
	public DataNode setReference_frameScalar(String reference_frameValue);

	/**
	 * Dimensionality of the structure.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * Dimensionality of the structure.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * Dimensionality of the structure.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * Dimensionality of the structure.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * Geometry of the unit cell quantified via parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getA_b_c();

	/**
	 * Geometry of the unit cell quantified via parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param a_b_cDataset the a_b_cDataset
	 */
	public DataNode setA_b_c(IDataset a_b_cDataset);

	/**
	 * Geometry of the unit cell quantified via parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getA_b_cScalar();

	/**
	 * Geometry of the unit cell quantified via parameters a, b, and c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param a_b_c the a_b_c
	 */
	public DataNode setA_b_cScalar(Number a_b_cValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter a.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getA();

	/**
	 * Geometry of the unit cell quantified individually via parameter a.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param aDataset the aDataset
	 */
	public DataNode setA(IDataset aDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter a.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter a.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param a the a
	 */
	public DataNode setAScalar(Number aValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter b.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getB();

	/**
	 * Geometry of the unit cell quantified individually via parameter b.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param bDataset the bDataset
	 */
	public DataNode setB(IDataset bDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter b.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getBScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter b.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param b the b
	 */
	public DataNode setBScalar(Number bValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getC();

	/**
	 * Geometry of the unit cell quantified individually via parameter c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param cDataset the cDataset
	 */
	public DataNode setC(IDataset cDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter c.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param c the c
	 */
	public DataNode setCScalar(Number cValue);

	/**
	 * Geometry of the unit cell quantified via parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAlpha_beta_gamma();

	/**
	 * Geometry of the unit cell quantified via parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param alpha_beta_gammaDataset the alpha_beta_gammaDataset
	 */
	public DataNode setAlpha_beta_gamma(IDataset alpha_beta_gammaDataset);

	/**
	 * Geometry of the unit cell quantified via parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAlpha_beta_gammaScalar();

	/**
	 * Geometry of the unit cell quantified via parameters alpha, beta, and gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param alpha_beta_gamma the alpha_beta_gamma
	 */
	public DataNode setAlpha_beta_gammaScalar(Number alpha_beta_gammaValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter alpha.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAlpha();

	/**
	 * Geometry of the unit cell quantified individually via parameter alpha.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param alphaDataset the alphaDataset
	 */
	public DataNode setAlpha(IDataset alphaDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter alpha.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAlphaScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter alpha.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param alpha the alpha
	 */
	public DataNode setAlphaScalar(Number alphaValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter beta.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBeta();

	/**
	 * Geometry of the unit cell quantified individually via parameter beta.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param betaDataset the betaDataset
	 */
	public DataNode setBeta(IDataset betaDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter beta.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getBetaScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter beta.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param beta the beta
	 */
	public DataNode setBetaScalar(Number betaValue);

	/**
	 * Geometry of the unit cell quantified individually via parameter gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getGamma();

	/**
	 * Geometry of the unit cell quantified individually via parameter gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param gammaDataset the gammaDataset
	 */
	public DataNode setGamma(IDataset gammaDataset);

	/**
	 * Geometry of the unit cell quantified individually via parameter gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getGammaScalar();

	/**
	 * Geometry of the unit cell quantified individually via parameter gamma.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param gamma the gamma
	 */
	public DataNode setGammaScalar(Number gammaValue);

	/**
	 * Crystal system.
	 * For a crystal system in 2D space monoclinic is an exact synonym for oblique.
	 * For a crystal system in 2D space orthorhombic is an exact synonym for rectangular.
	 * For a crystal system in 2D space tetragonal is an exact synonym for square.
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	public Dataset getCrystal_system();

	/**
	 * Crystal system.
	 * For a crystal system in 2D space monoclinic is an exact synonym for oblique.
	 * For a crystal system in 2D space orthorhombic is an exact synonym for rectangular.
	 * For a crystal system in 2D space tetragonal is an exact synonym for square.
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	 * @param crystal_systemDataset the crystal_systemDataset
	 */
	public DataNode setCrystal_system(IDataset crystal_systemDataset);

	/**
	 * Crystal system.
	 * For a crystal system in 2D space monoclinic is an exact synonym for oblique.
	 * For a crystal system in 2D space orthorhombic is an exact synonym for rectangular.
	 * For a crystal system in 2D space tetragonal is an exact synonym for square.
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	public String getCrystal_systemScalar();

	/**
	 * Crystal system.
	 * For a crystal system in 2D space monoclinic is an exact synonym for oblique.
	 * For a crystal system in 2D space orthorhombic is an exact synonym for rectangular.
	 * For a crystal system in 2D space tetragonal is an exact synonym for square.
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	 * @param crystal_system the crystal_system
	 */
	public DataNode setCrystal_systemScalar(String crystal_systemValue);

	/**
	 * Laue group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLaue_group();

	/**
	 * Laue group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param laue_groupDataset the laue_groupDataset
	 */
	public DataNode setLaue_group(IDataset laue_groupDataset);

	/**
	 * Laue group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLaue_groupScalar();

	/**
	 * Laue group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param laue_group the laue_group
	 */
	public DataNode setLaue_groupScalar(String laue_groupValue);

	/**
	 * Point group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPoint_group();

	/**
	 * Point group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param point_groupDataset the point_groupDataset
	 */
	public DataNode setPoint_group(IDataset point_groupDataset);

	/**
	 * Point group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getPoint_groupScalar();

	/**
	 * Point group using International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param point_group the point_group
	 */
	public DataNode setPoint_groupScalar(String point_groupValue);

	/**
	 * Space group from the International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSpace_group();

	/**
	 * Space group from the International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param space_groupDataset the space_groupDataset
	 */
	public DataNode setSpace_group(IDataset space_groupDataset);

	/**
	 * Space group from the International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSpace_groupScalar();

	/**
	 * Space group from the International Table of Crystallography notation.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
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
	public Dataset getIs_centrosymmetric();

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
	public Dataset getIs_chiral();

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
	 * Area of the unit cell if dimensionality is 2.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getArea();

	/**
	 * Area of the unit cell if dimensionality is 2.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * </p>
	 *
	 * @param areaDataset the areaDataset
	 */
	public DataNode setArea(IDataset areaDataset);

	/**
	 * Area of the unit cell if dimensionality is 2.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAreaScalar();

	/**
	 * Area of the unit cell if dimensionality is 2.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * </p>
	 *
	 * @param area the area
	 */
	public DataNode setAreaScalar(Number areaValue);

	/**
	 * Volume of the unit cell if dimensionality is 3.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Volume of the unit cell if dimensionality is 3.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Volume of the unit cell if dimensionality is 3.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * Volume of the unit cell if dimensionality is 3.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXatom getAtom();

	/**
	 *
	 * @param atomGroup the atomGroup
	 */
	public void setAtom(NXatom atomGroup);

	/**
	 * Get a NXatom node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXatom for that node.
	 */
	public NXatom getAtom(String name);

	/**
	 * Set a NXatom node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param atom the value to set
	 */
	public void setAtom(String name, NXatom atom);

	/**
	 * Get all NXatom nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXatom for that node.
	 */
	public Map<String, NXatom> getAllAtom();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param atom the child nodes to add
	 */

	public void setAllAtom(Map<String, NXatom> atom);


}
