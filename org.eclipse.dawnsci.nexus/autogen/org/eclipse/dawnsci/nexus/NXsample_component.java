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
 * One group like this per component can be recorded For a sample consisting of multiple components.
 * <p><b>Symbols:</b>
 * symbolic array lengths to be coordinated between various fields<ul>
 * <li><b>n_Temp</b>
 * number of temperatures</li>
 * <li><b>n_eField</b>
 * number of values in applied electric field</li>
 * <li><b>n_mField</b>
 * number of values in applied magnetic field</li>
 * <li><b>n_pField</b>
 * number of values in applied pressure field</li>
 * <li><b>n_sField</b>
 * number of values in applied stress field</li></ul></p>
 *
 */
public interface NXsample_component extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_CHEMICAL_FORMULA = "chemical_formula";
	public static final String NX_UNIT_CELL_ABC = "unit_cell_abc";
	public static final String NX_UNIT_CELL_ALPHABETAGAMMA = "unit_cell_alphabetagamma";
	public static final String NX_UNIT_CELL_VOLUME = "unit_cell_volume";
	public static final String NX_SAMPLE_ORIENTATION = "sample_orientation";
	public static final String NX_ORIENTATION_MATRIX = "orientation_matrix";
	public static final String NX_MASS = "mass";
	public static final String NX_DENSITY = "density";
	public static final String NX_RELATIVE_MOLECULAR_MASS = "relative_molecular_mass";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_VOLUME_FRACTION = "volume_fraction";
	public static final String NX_SCATTERING_LENGTH_DENSITY = "scattering_length_density";
	public static final String NX_UNIT_CELL_CLASS = "unit_cell_class";
	public static final String NX_SPACE_GROUP = "space_group";
	public static final String NX_POINT_GROUP = "point_group";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Descriptive name of sample component
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Descriptive name of sample component
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Descriptive name of sample component
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Descriptive name of sample component
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:
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
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @return  the value.
	 */
	public Dataset getChemical_formula();

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:
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
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @param chemical_formulaDataset the chemical_formulaDataset
	 */
	public DataNode setChemical_formula(IDataset chemical_formulaDataset);

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:
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
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @return  the value.
	 */
	public String getChemical_formulaScalar();

	/**
	 * The chemical formula specified using CIF conventions.
	 * Abbreviated version of CIF standard:
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
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @param chemical_formula the chemical_formula
	 */
	public DataNode setChemical_formulaScalar(String chemical_formulaValue);

	/**
	 * Crystallography unit cell parameters a, b, and c
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_abc();

	/**
	 * Crystallography unit cell parameters a, b, and c
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
	 * Crystallography unit cell parameters a, b, and c
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
	 * Crystallography unit cell parameters a, b, and c
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
	 * Crystallography unit cell parameters alpha, beta, and gamma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_alphabetagamma();

	/**
	 * Crystallography unit cell parameters alpha, beta, and gamma
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
	 * Crystallography unit cell parameters alpha, beta, and gamma
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
	 * Crystallography unit cell parameters alpha, beta, and gamma
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
	public Dataset getUnit_cell_volume();

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
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSample_orientation();

	/**
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param sample_orientationDataset the sample_orientationDataset
	 */
	public DataNode setSample_orientation(IDataset sample_orientationDataset);

	/**
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSample_orientationScalar();

	/**
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param sample_orientation the sample_orientation
	 */
	public DataNode setSample_orientationScalar(Double sample_orientationValue);

	/**
	 * Orientation matrix of single crystal sample component.
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation_matrix();

	/**
	 * Orientation matrix of single crystal sample component.
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @param orientation_matrixDataset the orientation_matrixDataset
	 */
	public DataNode setOrientation_matrix(IDataset orientation_matrixDataset);

	/**
	 * Orientation matrix of single crystal sample component.
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOrientation_matrixScalar();

	/**
	 * Orientation matrix of single crystal sample component.
	 * This will follow the Busing and Levy convention from Acta.Crysta v22, p457 (1967)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @param orientation_matrix the orientation_matrix
	 */
	public DataNode setOrientation_matrixScalar(Double orientation_matrixValue);

	/**
	 * Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMass();

	/**
	 * Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @param massDataset the massDataset
	 */
	public DataNode setMass(IDataset massDataset);

	/**
	 * Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMassScalar();

	/**
	 * Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @param mass the mass
	 */
	public DataNode setMassScalar(Double massValue);

	/**
	 * Density of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDensity();

	/**
	 * Density of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param densityDataset the densityDataset
	 */
	public DataNode setDensity(IDataset densityDataset);

	/**
	 * Density of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDensityScalar();

	/**
	 * Density of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param density the density
	 */
	public DataNode setDensityScalar(Double densityValue);

	/**
	 * Relative Molecular Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRelative_molecular_mass();

	/**
	 * Relative Molecular Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @param relative_molecular_massDataset the relative_molecular_massDataset
	 */
	public DataNode setRelative_molecular_mass(IDataset relative_molecular_massDataset);

	/**
	 * Relative Molecular Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRelative_molecular_massScalar();

	/**
	 * Relative Molecular Mass of sample component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * </p>
	 *
	 * @param relative_molecular_mass the relative_molecular_mass
	 */
	public DataNode setRelative_molecular_massScalar(Double relative_molecular_massValue);

	/**
	 * Description of the sample component
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Description of the sample component
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of the sample component
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of the sample component
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Volume fraction of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume_fraction();

	/**
	 * Volume fraction of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param volume_fractionDataset the volume_fractionDataset
	 */
	public DataNode setVolume_fraction(IDataset volume_fractionDataset);

	/**
	 * Volume fraction of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getVolume_fractionScalar();

	/**
	 * Volume fraction of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * </p>
	 *
	 * @param volume_fraction the volume_fraction
	 */
	public DataNode setVolume_fractionScalar(Double volume_fractionValue);

	/**
	 * Scattering length density of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SCATTERING_LENGTH_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScattering_length_density();

	/**
	 * Scattering length density of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SCATTERING_LENGTH_DENSITY
	 * </p>
	 *
	 * @param scattering_length_densityDataset the scattering_length_densityDataset
	 */
	public DataNode setScattering_length_density(IDataset scattering_length_densityDataset);

	/**
	 * Scattering length density of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SCATTERING_LENGTH_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScattering_length_densityScalar();

	/**
	 * Scattering length density of component
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_SCATTERING_LENGTH_DENSITY
	 * </p>
	 *
	 * @param scattering_length_density the scattering_length_density
	 */
	public DataNode setScattering_length_densityScalar(Double scattering_length_densityValue);

	/**
	 * In case it is all we know and we want to record/document it
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
	public Dataset getUnit_cell_class();

	/**
	 * In case it is all we know and we want to record/document it
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
	 * In case it is all we know and we want to record/document it
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
	 * In case it is all we know and we want to record/document it
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
	 * Crystallographic space group
	 *
	 * @return  the value.
	 */
	public Dataset getSpace_group();

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
	 * Crystallographic point group, deprecated if space_group present
	 *
	 * @return  the value.
	 */
	public Dataset getPoint_group();

	/**
	 * Crystallographic point group, deprecated if space_group present
	 *
	 * @param point_groupDataset the point_groupDataset
	 */
	public DataNode setPoint_group(IDataset point_groupDataset);

	/**
	 * Crystallographic point group, deprecated if space_group present
	 *
	 * @return  the value.
	 */
	public String getPoint_groupScalar();

	/**
	 * Crystallographic point group, deprecated if space_group present
	 *
	 * @param point_group the point_group
	 */
	public DataNode setPoint_groupScalar(String point_groupValue);

	/**
	 * As a function of Wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getTransmission();

	/**
	 * As a function of Wavelength
	 *
	 * @param transmissionGroup the transmissionGroup
	 */
	public void setTransmission(NXdata transmissionGroup);

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
