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
 * A crystal monochromator or analyzer.
 * Permits double bent
 * monochromator comprised of multiple segments with anisotropic
 * Gaussian mosaic.
 * If curvatures are set to zero or are absent, array
 * is considered to be flat.
 * Scattering vector is perpendicular to surface. Crystal is oriented
 * parallel to beam incident on crystal before rotation, and lies in
 * vertical plane.
 * <p><b>Symbols:</b>
 * These symbols will be used below to coordinate dimensions with the same lengths.<ul>
 * <li><b>n_comp</b>
 * number of different unit cells to be described</li>
 * <li><b>i</b>
 * number of wavelengths</li></ul></p>
 *
 */
public interface NXcrystal extends NXobject {

	public static final String NX_USAGE = "usage";
	public static final String NX_TYPE = "type";
	public static final String NX_CHEMICAL_FORMULA = "chemical_formula";
	public static final String NX_ORDER_NO = "order_no";
	public static final String NX_CUT_ANGLE = "cut_angle";
	public static final String NX_SPACE_GROUP = "space_group";
	public static final String NX_UNIT_CELL = "unit_cell";
	public static final String NX_UNIT_CELL_A = "unit_cell_a";
	public static final String NX_UNIT_CELL_B = "unit_cell_b";
	public static final String NX_UNIT_CELL_C = "unit_cell_c";
	public static final String NX_UNIT_CELL_ALPHA = "unit_cell_alpha";
	public static final String NX_UNIT_CELL_BETA = "unit_cell_beta";
	public static final String NX_UNIT_CELL_GAMMA = "unit_cell_gamma";
	public static final String NX_UNIT_CELL_VOLUME = "unit_cell_volume";
	public static final String NX_ORIENTATION_MATRIX = "orientation_matrix";
	public static final String NX_WAVELENGTH = "wavelength";
	public static final String NX_D_SPACING = "d_spacing";
	public static final String NX_SCATTERING_VECTOR = "scattering_vector";
	public static final String NX_REFLECTION = "reflection";
	public static final String NX_THICKNESS = "thickness";
	public static final String NX_DENSITY = "density";
	public static final String NX_SEGMENT_WIDTH = "segment_width";
	public static final String NX_SEGMENT_HEIGHT = "segment_height";
	public static final String NX_SEGMENT_THICKNESS = "segment_thickness";
	public static final String NX_SEGMENT_GAP = "segment_gap";
	public static final String NX_SEGMENT_COLUMNS = "segment_columns";
	public static final String NX_SEGMENT_ROWS = "segment_rows";
	public static final String NX_MOSAIC_HORIZONTAL = "mosaic_horizontal";
	public static final String NX_MOSAIC_VERTICAL = "mosaic_vertical";
	public static final String NX_CURVATURE_HORIZONTAL = "curvature_horizontal";
	public static final String NX_CURVATURE_VERTICAL = "curvature_vertical";
	public static final String NX_IS_CYLINDRICAL = "is_cylindrical";
	public static final String NX_CYLINDRICAL_ORIENTATION_ANGLE = "cylindrical_orientation_angle";
	public static final String NX_POLAR_ANGLE = "polar_angle";
	public static final String NX_AZIMUTHAL_ANGLE = "azimuthal_angle";
	public static final String NX_BRAGG_ANGLE = "bragg_angle";
	public static final String NX_TEMPERATURE = "temperature";
	public static final String NX_TEMPERATURE_COEFFICIENT = "temperature_coefficient";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Position of crystal
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * Position of crystal
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Position of crystal</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Position of crystal</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * Position of crystal</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Position of crystal</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the crystal and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * How this crystal is used. Choices are in the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Bragg</b> 
	 * reflection geometry</li>
	 * <li><b>Laue</b> 
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
	 * This is the *Hill* system used by Chemical Abstracts.
	 * See, for example:
	 * http://www.iucr.org/__data/iucr/cif/standard/cifstd15.html or
	 * http://www.cas.org/training/stneasytips/subinforformula1.html.</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUsage();

	/**
	 * How this crystal is used. Choices are in the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Bragg</b> 
	 * reflection geometry</li>
	 * <li><b>Laue</b> 
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
	 * This is the *Hill* system used by Chemical Abstracts.
	 * See, for example:
	 * http://www.iucr.org/__data/iucr/cif/standard/cifstd15.html or
	 * http://www.cas.org/training/stneasytips/subinforformula1.html.</li></ul></p>
	 * </p>
	 *
	 * @param usageDataset the usageDataset
	 */
	public DataNode setUsage(IDataset usageDataset);

	/**
	 * How this crystal is used. Choices are in the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Bragg</b> 
	 * reflection geometry</li>
	 * <li><b>Laue</b> 
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
	 * This is the *Hill* system used by Chemical Abstracts.
	 * See, for example:
	 * http://www.iucr.org/__data/iucr/cif/standard/cifstd15.html or
	 * http://www.cas.org/training/stneasytips/subinforformula1.html.</li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getUsageScalar();

	/**
	 * How this crystal is used. Choices are in the list.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Bragg</b> 
	 * reflection geometry</li>
	 * <li><b>Laue</b> 
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
	 * This is the *Hill* system used by Chemical Abstracts.
	 * See, for example:
	 * http://www.iucr.org/__data/iucr/cif/standard/cifstd15.html or
	 * http://www.cas.org/training/stneasytips/subinforformula1.html.</li></ul></p>
	 * </p>
	 *
	 * @param usage the usage
	 */
	public DataNode setUsageScalar(String usageValue);

	/**
	 * Type or material of monochromating substance.
	 * Chemical formula can be specified separately.
	 * Use the "reflection" field to indicate the (hkl) orientation.
	 * Use the "d_spacing" field to record the lattice plane spacing.
	 * This field was changed (2010-11-17) from an enumeration to
	 * a string since common usage showed a wider variety of use
	 * than a simple list. These are the items in the list at
	 * the time of the change: PG (Highly Oriented Pyrolytic Graphite) |
	 * Ge | Si | Cu | Fe3Si | CoFe | Cu2MnAl (Heusler) | Multilayer |
	 * Diamond.
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type or material of monochromating substance.
	 * Chemical formula can be specified separately.
	 * Use the "reflection" field to indicate the (hkl) orientation.
	 * Use the "d_spacing" field to record the lattice plane spacing.
	 * This field was changed (2010-11-17) from an enumeration to
	 * a string since common usage showed a wider variety of use
	 * than a simple list. These are the items in the list at
	 * the time of the change: PG (Highly Oriented Pyrolytic Graphite) |
	 * Ge | Si | Cu | Fe3Si | CoFe | Cu2MnAl (Heusler) | Multilayer |
	 * Diamond.
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type or material of monochromating substance.
	 * Chemical formula can be specified separately.
	 * Use the "reflection" field to indicate the (hkl) orientation.
	 * Use the "d_spacing" field to record the lattice plane spacing.
	 * This field was changed (2010-11-17) from an enumeration to
	 * a string since common usage showed a wider variety of use
	 * than a simple list. These are the items in the list at
	 * the time of the change: PG (Highly Oriented Pyrolytic Graphite) |
	 * Ge | Si | Cu | Fe3Si | CoFe | Cu2MnAl (Heusler) | Multilayer |
	 * Diamond.
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type or material of monochromating substance.
	 * Chemical formula can be specified separately.
	 * Use the "reflection" field to indicate the (hkl) orientation.
	 * Use the "d_spacing" field to record the lattice plane spacing.
	 * This field was changed (2010-11-17) from an enumeration to
	 * a string since common usage showed a wider variety of use
	 * than a simple list. These are the items in the list at
	 * the time of the change: PG (Highly Oriented Pyrolytic Graphite) |
	 * Ge | Si | Cu | Fe3Si | CoFe | Cu2MnAl (Heusler) | Multilayer |
	 * Diamond.
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
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
	 * C, then H, then the other elements in alphabetical order of their symbol.
	 * If carbon is not present, the elements are listed purely in alphabetic
	 * order of their symbol.
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @param chemical_formula the chemical_formula
	 */
	public DataNode setChemical_formulaScalar(String chemical_formulaValue);

	/**
	 * A number which describes if this is the first, second,..
	 * :math:`n^{th}` crystal in a multi crystal monochromator
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrder_no();

	/**
	 * A number which describes if this is the first, second,..
	 * :math:`n^{th}` crystal in a multi crystal monochromator
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param order_noDataset the order_noDataset
	 */
	public DataNode setOrder_no(IDataset order_noDataset);

	/**
	 * A number which describes if this is the first, second,..
	 * :math:`n^{th}` crystal in a multi crystal monochromator
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getOrder_noScalar();

	/**
	 * A number which describes if this is the first, second,..
	 * :math:`n^{th}` crystal in a multi crystal monochromator
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param order_no the order_no
	 */
	public DataNode setOrder_noScalar(Long order_noValue);

	/**
	 * Cut angle of reflecting Bragg plane and plane of crystal surface
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCut_angle();

	/**
	 * Cut angle of reflecting Bragg plane and plane of crystal surface
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param cut_angleDataset the cut_angleDataset
	 */
	public DataNode setCut_angle(IDataset cut_angleDataset);

	/**
	 * Cut angle of reflecting Bragg plane and plane of crystal surface
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCut_angleScalar();

	/**
	 * Cut angle of reflecting Bragg plane and plane of crystal surface
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param cut_angle the cut_angle
	 */
	public DataNode setCut_angleScalar(Double cut_angleValue);

	/**
	 * Space group of crystal structure
	 *
	 * @return  the value.
	 */
	public Dataset getSpace_group();

	/**
	 * Space group of crystal structure
	 *
	 * @param space_groupDataset the space_groupDataset
	 */
	public DataNode setSpace_group(IDataset space_groupDataset);

	/**
	 * Space group of crystal structure
	 *
	 * @return  the value.
	 */
	public String getSpace_groupScalar();

	/**
	 * Space group of crystal structure
	 *
	 * @param space_group the space_group
	 */
	public DataNode setSpace_groupScalar(String space_groupValue);

	/**
	 * Unit cell parameters (lengths and angles)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_comp; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell();

	/**
	 * Unit cell parameters (lengths and angles)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_comp; 2: 6;
	 * </p>
	 *
	 * @param unit_cellDataset the unit_cellDataset
	 */
	public DataNode setUnit_cell(IDataset unit_cellDataset);

	/**
	 * Unit cell parameters (lengths and angles)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_comp; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cellScalar();

	/**
	 * Unit cell parameters (lengths and angles)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_comp; 2: 6;
	 * </p>
	 *
	 * @param unit_cell the unit_cell
	 */
	public DataNode setUnit_cellScalar(Double unit_cellValue);

	/**
	 * Unit cell lattice parameter: length of side a
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_a();

	/**
	 * Unit cell lattice parameter: length of side a
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_aDataset the unit_cell_aDataset
	 */
	public DataNode setUnit_cell_a(IDataset unit_cell_aDataset);

	/**
	 * Unit cell lattice parameter: length of side a
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_aScalar();

	/**
	 * Unit cell lattice parameter: length of side a
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_a the unit_cell_a
	 */
	public DataNode setUnit_cell_aScalar(Double unit_cell_aValue);

	/**
	 * Unit cell lattice parameter: length of side b
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_b();

	/**
	 * Unit cell lattice parameter: length of side b
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_bDataset the unit_cell_bDataset
	 */
	public DataNode setUnit_cell_b(IDataset unit_cell_bDataset);

	/**
	 * Unit cell lattice parameter: length of side b
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_bScalar();

	/**
	 * Unit cell lattice parameter: length of side b
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_b the unit_cell_b
	 */
	public DataNode setUnit_cell_bScalar(Double unit_cell_bValue);

	/**
	 * Unit cell lattice parameter: length of side c
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_c();

	/**
	 * Unit cell lattice parameter: length of side c
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_cDataset the unit_cell_cDataset
	 */
	public DataNode setUnit_cell_c(IDataset unit_cell_cDataset);

	/**
	 * Unit cell lattice parameter: length of side c
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_cScalar();

	/**
	 * Unit cell lattice parameter: length of side c
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param unit_cell_c the unit_cell_c
	 */
	public DataNode setUnit_cell_cScalar(Double unit_cell_cValue);

	/**
	 * Unit cell lattice parameter: angle alpha
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_alpha();

	/**
	 * Unit cell lattice parameter: angle alpha
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_alphaDataset the unit_cell_alphaDataset
	 */
	public DataNode setUnit_cell_alpha(IDataset unit_cell_alphaDataset);

	/**
	 * Unit cell lattice parameter: angle alpha
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_alphaScalar();

	/**
	 * Unit cell lattice parameter: angle alpha
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_alpha the unit_cell_alpha
	 */
	public DataNode setUnit_cell_alphaScalar(Double unit_cell_alphaValue);

	/**
	 * Unit cell lattice parameter: angle beta
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_beta();

	/**
	 * Unit cell lattice parameter: angle beta
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_betaDataset the unit_cell_betaDataset
	 */
	public DataNode setUnit_cell_beta(IDataset unit_cell_betaDataset);

	/**
	 * Unit cell lattice parameter: angle beta
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_betaScalar();

	/**
	 * Unit cell lattice parameter: angle beta
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_beta the unit_cell_beta
	 */
	public DataNode setUnit_cell_betaScalar(Double unit_cell_betaValue);

	/**
	 * Unit cell lattice parameter: angle gamma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnit_cell_gamma();

	/**
	 * Unit cell lattice parameter: angle gamma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_gammaDataset the unit_cell_gammaDataset
	 */
	public DataNode setUnit_cell_gamma(IDataset unit_cell_gammaDataset);

	/**
	 * Unit cell lattice parameter: angle gamma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getUnit_cell_gammaScalar();

	/**
	 * Unit cell lattice parameter: angle gamma
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param unit_cell_gamma the unit_cell_gamma
	 */
	public DataNode setUnit_cell_gammaScalar(Double unit_cell_gammaValue);

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
	 * Orientation matrix of single crystal sample using Busing-Levy convention:
	 * W. R. Busing and H. A. Levy (1967). Acta Cryst. 22, 457-464
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation_matrix();

	/**
	 * Orientation matrix of single crystal sample using Busing-Levy convention:
	 * W. R. Busing and H. A. Levy (1967). Acta Cryst. 22, 457-464
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @param orientation_matrixDataset the orientation_matrixDataset
	 */
	public DataNode setOrientation_matrix(IDataset orientation_matrixDataset);

	/**
	 * Orientation matrix of single crystal sample using Busing-Levy convention:
	 * W. R. Busing and H. A. Levy (1967). Acta Cryst. 22, 457-464
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOrientation_matrixScalar();

	/**
	 * Orientation matrix of single crystal sample using Busing-Levy convention:
	 * W. R. Busing and H. A. Levy (1967). Acta Cryst. 22, 457-464
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: 3; 2: 3;
	 * </p>
	 *
	 * @param orientation_matrix the orientation_matrix
	 */
	public DataNode setOrientation_matrixScalar(Double orientation_matrixValue);

	/**
	 * Optimum diffracted wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelength();

	/**
	 * Optimum diffracted wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param wavelengthDataset the wavelengthDataset
	 */
	public DataNode setWavelength(IDataset wavelengthDataset);

	/**
	 * Optimum diffracted wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWavelengthScalar();

	/**
	 * Optimum diffracted wavelength
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param wavelength the wavelength
	 */
	public DataNode setWavelengthScalar(Double wavelengthValue);

	/**
	 * spacing between crystal planes of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getD_spacing();

	/**
	 * spacing between crystal planes of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param d_spacingDataset the d_spacingDataset
	 */
	public DataNode setD_spacing(IDataset d_spacingDataset);

	/**
	 * spacing between crystal planes of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getD_spacingScalar();

	/**
	 * spacing between crystal planes of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param d_spacing the d_spacing
	 */
	public DataNode setD_spacingScalar(Double d_spacingValue);

	/**
	 * Scattering vector, Q, of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getScattering_vector();

	/**
	 * Scattering vector, Q, of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @param scattering_vectorDataset the scattering_vectorDataset
	 */
	public DataNode setScattering_vector(IDataset scattering_vectorDataset);

	/**
	 * Scattering vector, Q, of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getScattering_vectorScalar();

	/**
	 * Scattering vector, Q, of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVENUMBER
	 * </p>
	 *
	 * @param scattering_vector the scattering_vector
	 */
	public DataNode setScattering_vectorScalar(Double scattering_vectorValue);

	/**
	 * Miller indices (hkl) values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReflection();

	/**
	 * Miller indices (hkl) values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param reflectionDataset the reflectionDataset
	 */
	public DataNode setReflection(IDataset reflectionDataset);

	/**
	 * Miller indices (hkl) values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getReflectionScalar();

	/**
	 * Miller indices (hkl) values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param reflection the reflection
	 */
	public DataNode setReflectionScalar(Long reflectionValue);

	/**
	 * Thickness of the crystal. (Required for Laue orientations - see "usage" field)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getThickness();

	/**
	 * Thickness of the crystal. (Required for Laue orientations - see "usage" field)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param thicknessDataset the thicknessDataset
	 */
	public DataNode setThickness(IDataset thicknessDataset);

	/**
	 * Thickness of the crystal. (Required for Laue orientations - see "usage" field)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getThicknessScalar();

	/**
	 * Thickness of the crystal. (Required for Laue orientations - see "usage" field)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param thickness the thickness
	 */
	public DataNode setThicknessScalar(Double thicknessValue);

	/**
	 * mass density of the crystal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDensity();

	/**
	 * mass density of the crystal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param densityDataset the densityDataset
	 */
	public DataNode setDensity(IDataset densityDataset);

	/**
	 * mass density of the crystal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDensityScalar();

	/**
	 * mass density of the crystal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 *
	 * @param density the density
	 */
	public DataNode setDensityScalar(Number densityValue);

	/**
	 * Horizontal width of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_width();

	/**
	 * Horizontal width of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_widthDataset the segment_widthDataset
	 */
	public DataNode setSegment_width(IDataset segment_widthDataset);

	/**
	 * Horizontal width of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_widthScalar();

	/**
	 * Horizontal width of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_width the segment_width
	 */
	public DataNode setSegment_widthScalar(Double segment_widthValue);

	/**
	 * Vertical height of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_height();

	/**
	 * Vertical height of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_heightDataset the segment_heightDataset
	 */
	public DataNode setSegment_height(IDataset segment_heightDataset);

	/**
	 * Vertical height of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_heightScalar();

	/**
	 * Vertical height of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_height the segment_height
	 */
	public DataNode setSegment_heightScalar(Double segment_heightValue);

	/**
	 * Thickness of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_thickness();

	/**
	 * Thickness of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_thicknessDataset the segment_thicknessDataset
	 */
	public DataNode setSegment_thickness(IDataset segment_thicknessDataset);

	/**
	 * Thickness of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_thicknessScalar();

	/**
	 * Thickness of individual segment
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_thickness the segment_thickness
	 */
	public DataNode setSegment_thicknessScalar(Double segment_thicknessValue);

	/**
	 * Typical gap between adjacent segments
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_gap();

	/**
	 * Typical gap between adjacent segments
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_gapDataset the segment_gapDataset
	 */
	public DataNode setSegment_gap(IDataset segment_gapDataset);

	/**
	 * Typical gap between adjacent segments
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_gapScalar();

	/**
	 * Typical gap between adjacent segments
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_gap the segment_gap
	 */
	public DataNode setSegment_gapScalar(Double segment_gapValue);

	/**
	 * number of segment columns in horizontal direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_columns();

	/**
	 * number of segment columns in horizontal direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_columnsDataset the segment_columnsDataset
	 */
	public DataNode setSegment_columns(IDataset segment_columnsDataset);

	/**
	 * number of segment columns in horizontal direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_columnsScalar();

	/**
	 * number of segment columns in horizontal direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_columns the segment_columns
	 */
	public DataNode setSegment_columnsScalar(Double segment_columnsValue);

	/**
	 * number of segment rows in vertical direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSegment_rows();

	/**
	 * number of segment rows in vertical direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_rowsDataset the segment_rowsDataset
	 */
	public DataNode setSegment_rows(IDataset segment_rowsDataset);

	/**
	 * number of segment rows in vertical direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSegment_rowsScalar();

	/**
	 * number of segment rows in vertical direction
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param segment_rows the segment_rows
	 */
	public DataNode setSegment_rowsScalar(Double segment_rowsValue);

	/**
	 * horizontal mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMosaic_horizontal();

	/**
	 * horizontal mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param mosaic_horizontalDataset the mosaic_horizontalDataset
	 */
	public DataNode setMosaic_horizontal(IDataset mosaic_horizontalDataset);

	/**
	 * horizontal mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMosaic_horizontalScalar();

	/**
	 * horizontal mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param mosaic_horizontal the mosaic_horizontal
	 */
	public DataNode setMosaic_horizontalScalar(Double mosaic_horizontalValue);

	/**
	 * vertical mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMosaic_vertical();

	/**
	 * vertical mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param mosaic_verticalDataset the mosaic_verticalDataset
	 */
	public DataNode setMosaic_vertical(IDataset mosaic_verticalDataset);

	/**
	 * vertical mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMosaic_verticalScalar();

	/**
	 * vertical mosaic Full Width Half Maximum
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param mosaic_vertical the mosaic_vertical
	 */
	public DataNode setMosaic_verticalScalar(Double mosaic_verticalValue);

	/**
	 * Horizontal curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCurvature_horizontal();

	/**
	 * Horizontal curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param curvature_horizontalDataset the curvature_horizontalDataset
	 */
	public DataNode setCurvature_horizontal(IDataset curvature_horizontalDataset);

	/**
	 * Horizontal curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCurvature_horizontalScalar();

	/**
	 * Horizontal curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param curvature_horizontal the curvature_horizontal
	 */
	public DataNode setCurvature_horizontalScalar(Double curvature_horizontalValue);

	/**
	 * Vertical curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCurvature_vertical();

	/**
	 * Vertical curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param curvature_verticalDataset the curvature_verticalDataset
	 */
	public DataNode setCurvature_vertical(IDataset curvature_verticalDataset);

	/**
	 * Vertical curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCurvature_verticalScalar();

	/**
	 * Vertical curvature of focusing crystal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param curvature_vertical the curvature_vertical
	 */
	public DataNode setCurvature_verticalScalar(Double curvature_verticalValue);

	/**
	 * Is this crystal bent cylindrically?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_cylindrical();

	/**
	 * Is this crystal bent cylindrically?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_cylindricalDataset the is_cylindricalDataset
	 */
	public DataNode setIs_cylindrical(IDataset is_cylindricalDataset);

	/**
	 * Is this crystal bent cylindrically?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_cylindricalScalar();

	/**
	 * Is this crystal bent cylindrically?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param is_cylindrical the is_cylindrical
	 */
	public DataNode setIs_cylindricalScalar(Boolean is_cylindricalValue);

	/**
	 * If cylindrical: cylinder orientation angle
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCylindrical_orientation_angle();

	/**
	 * If cylindrical: cylinder orientation angle
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param cylindrical_orientation_angleDataset the cylindrical_orientation_angleDataset
	 */
	public DataNode setCylindrical_orientation_angle(IDataset cylindrical_orientation_angleDataset);

	/**
	 * If cylindrical: cylinder orientation angle
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCylindrical_orientation_angleScalar();

	/**
	 * If cylindrical: cylinder orientation angle
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param cylindrical_orientation_angle the cylindrical_orientation_angle
	 */
	public DataNode setCylindrical_orientation_angleScalar(Number cylindrical_orientation_angleValue);

	/**
	 * Polar (scattering) angle at which crystal assembly is positioned.
	 * Note: some instrument geometries call this term 2theta.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPolar_angle();

	/**
	 * Polar (scattering) angle at which crystal assembly is positioned.
	 * Note: some instrument geometries call this term 2theta.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param polar_angleDataset the polar_angleDataset
	 */
	public DataNode setPolar_angle(IDataset polar_angleDataset);

	/**
	 * Polar (scattering) angle at which crystal assembly is positioned.
	 * Note: some instrument geometries call this term 2theta.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPolar_angleScalar();

	/**
	 * Polar (scattering) angle at which crystal assembly is positioned.
	 * Note: some instrument geometries call this term 2theta.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param polar_angle the polar_angle
	 */
	public DataNode setPolar_angleScalar(Double polar_angleValue);

	/**
	 * Azimuthal angle at which crystal assembly is positioned.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAzimuthal_angle();

	/**
	 * Azimuthal angle at which crystal assembly is positioned.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param azimuthal_angleDataset the azimuthal_angleDataset
	 */
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset);

	/**
	 * Azimuthal angle at which crystal assembly is positioned.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAzimuthal_angleScalar();

	/**
	 * Azimuthal angle at which crystal assembly is positioned.
	 * Note: it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param azimuthal_angle the azimuthal_angle
	 */
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue);

	/**
	 * Bragg angle of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBragg_angle();

	/**
	 * Bragg angle of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param bragg_angleDataset the bragg_angleDataset
	 */
	public DataNode setBragg_angle(IDataset bragg_angleDataset);

	/**
	 * Bragg angle of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBragg_angleScalar();

	/**
	 * Bragg angle of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 *
	 * @param bragg_angle the bragg_angle
	 */
	public DataNode setBragg_angleScalar(Double bragg_angleValue);

	/**
	 * average/nominal crystal temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTemperature();

	/**
	 * average/nominal crystal temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param temperatureDataset the temperatureDataset
	 */
	public DataNode setTemperature(IDataset temperatureDataset);

	/**
	 * average/nominal crystal temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTemperatureScalar();

	/**
	 * average/nominal crystal temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 *
	 * @param temperature the temperature
	 */
	public DataNode setTemperatureScalar(Double temperatureValue);

	/**
	 * how lattice parameter changes with temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTemperature_coefficient();

	/**
	 * how lattice parameter changes with temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param temperature_coefficientDataset the temperature_coefficientDataset
	 */
	public DataNode setTemperature_coefficient(IDataset temperature_coefficientDataset);

	/**
	 * how lattice parameter changes with temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTemperature_coefficientScalar();

	/**
	 * how lattice parameter changes with temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param temperature_coefficient the temperature_coefficient
	 */
	public DataNode setTemperature_coefficientScalar(Double temperature_coefficientValue);

	/**
	 * log file of crystal temperature
	 *
	 * @return  the value.
	 */
	public NXlog getTemperature_log();

	/**
	 * log file of crystal temperature
	 *
	 * @param temperature_logGroup the temperature_logGroup
	 */
	public void setTemperature_log(NXlog temperature_logGroup);

	/**
	 * crystal reflectivity versus wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getReflectivity();

	/**
	 * crystal reflectivity versus wavelength
	 *
	 * @param reflectivityGroup the reflectivityGroup
	 */
	public void setReflectivity(NXdata reflectivityGroup);

	/**
	 * crystal transmission versus wavelength
	 *
	 * @return  the value.
	 */
	public NXdata getTransmission();

	/**
	 * crystal transmission versus wavelength
	 *
	 * @param transmissionGroup the transmissionGroup
	 */
	public void setTransmission(NXdata transmissionGroup);

	/**
	 * A NXshape group describing the shape of the crystal arrangement
	 *
	 * @deprecated Use NXoff_geometry instead to describe the shape of the monochromator
	 * @return  the value.
	 */
	@Deprecated
	public NXshape getShape();

	/**
	 * A NXshape group describing the shape of the crystal arrangement
	 *
	 * @deprecated Use NXoff_geometry instead to describe the shape of the monochromator
	 * @param shapeGroup the shapeGroup
	 */
	@Deprecated
	public void setShape(NXshape shapeGroup);

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);

	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);

	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


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

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a crystal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a crystal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a crystal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a crystal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Transformations used by this component to define its position and orientation.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Transformations used by this component to define its position and orientation.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Transformations used by this component to define its position and orientation.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Transformations used by this component to define its position and orientation.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Transformations used by this component to define its position and orientation.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Transformations used by this component to define its position and orientation.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
