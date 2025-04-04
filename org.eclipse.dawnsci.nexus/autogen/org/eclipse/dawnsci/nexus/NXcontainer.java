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
 * State of a container holding the sample under investigation.
 * A container is any object in the beam path which absorbs the beam and
 * whose contribution to the overall attenuation/scattering needs to be
 * determined to process the experimental data. Examples of containers
 * include glass capillary tubes, vanadium cans, windows in furnaces or
 * diamonds in a Diamond Anvil Cell. The following figures show a complex
 * example of a container:
 * .. figure:: container/ComplexExampleContainer.png
 * A hypothetical capillary furnace. The beam passes from left to right
 * (blue dashes), passing through window 1, then window 2, before
 * passing through the downstream wall of the capillary. It is then
 * scattered by the sample with scattered beams passing through the
 * upstream wall of the capillary, then windows 4 and 5. As part of the
 * corrections for a PDF experiment it is necessary to subtract the PDF
 * of the empty container (i.e. each of the windows and the capillary).
 * To calculate the PDF of the empty container it is necessary to have
 * the measured scattering data and to know the nature (e.g. density,
 * elemental composition, etc.) of the portion of the container which
 * the beam passed through.
 * .. figure:: container/ComplexContainerBeampath.png
 * A complete description of the shapes of the container elements with
 * their orientation relative to the beam and also information on
 * whether they are upstream or downstream of the sample is also
 * therefore important. For example, although the windows 2 and 4 have
 * the same shape, the path taken through them by the beam is very
 * different and this needs to be modelled. Furthermore, it is not
 * inconceivable that windows might move during an experiment and thus
 * the changes to the beampath would need to be accounted for.
 * This class encodes the position of the container with respect to the
 * sample and allows the calculation of the beampath through the container.
 * It also includes sufficient data to model beam absorption of the
 * container and a link to a dataset containing a measurement of the
 * container with nothing inside, to allow data corrections (at a specific
 * beam energy/measurement time) to be made.
 *
 */
public interface NXcontainer extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_CHEMICAL_FORMULA = "chemical_formula";
	public static final String NX_DENSITY = "density";
	public static final String NX_PACKING_FRACTION = "packing_fraction";
	public static final String NX_RELATIVE_MOLECULAR_MASS = "relative_molecular_mass";
	/**
	 * Descriptive name of container.
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Descriptive name of container.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Descriptive name of container.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Descriptive name of container.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Verbose description of container and how it fits into the wider
	 * experimental set up.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Verbose description of container and how it fits into the wider
	 * experimental set up.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Verbose description of container and how it fits into the wider
	 * experimental set up.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Verbose description of container and how it fits into the wider
	 * experimental set up.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Chemical composition of the material the container is made from.
	 * Specified using CIF conventions. Abbreviated version of CIF
	 * standard:
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of
	 * '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element
	 * symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the
	 * multiplier for the group must follow the closing parentheses.
	 * That is, all element and group multipliers are assumed to be
	 * printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to
	 * their chemical structure, the order of the elements within any
	 * group or moiety depends on whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of
	 * their symbol.
	 * - If carbon is not present, the elements are listed purely in
	 * alphabetic order of their symbol.
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @return  the value.
	 */
	public Dataset getChemical_formula();

	/**
	 * Chemical composition of the material the container is made from.
	 * Specified using CIF conventions. Abbreviated version of CIF
	 * standard:
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of
	 * '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element
	 * symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the
	 * multiplier for the group must follow the closing parentheses.
	 * That is, all element and group multipliers are assumed to be
	 * printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to
	 * their chemical structure, the order of the elements within any
	 * group or moiety depends on whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of
	 * their symbol.
	 * - If carbon is not present, the elements are listed purely in
	 * alphabetic order of their symbol.
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @param chemical_formulaDataset the chemical_formulaDataset
	 */
	public DataNode setChemical_formula(IDataset chemical_formulaDataset);

	/**
	 * Chemical composition of the material the container is made from.
	 * Specified using CIF conventions. Abbreviated version of CIF
	 * standard:
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of
	 * '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element
	 * symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the
	 * multiplier for the group must follow the closing parentheses.
	 * That is, all element and group multipliers are assumed to be
	 * printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to
	 * their chemical structure, the order of the elements within any
	 * group or moiety depends on whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of
	 * their symbol.
	 * - If carbon is not present, the elements are listed purely in
	 * alphabetic order of their symbol.
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @return  the value.
	 */
	public String getChemical_formulaScalar();

	/**
	 * Chemical composition of the material the container is made from.
	 * Specified using CIF conventions. Abbreviated version of CIF
	 * standard:
	 * * Only recognized element symbols may be used.
	 * * Each element symbol is followed by a 'count' number. A count of
	 * '1' may be omitted.
	 * * A space or parenthesis must separate each cluster of (element
	 * symbol + count).
	 * * Where a group of elements is enclosed in parentheses, the
	 * multiplier for the group must follow the closing parentheses.
	 * That is, all element and group multipliers are assumed to be
	 * printed as subscripted numbers.
	 * * Unless the elements are ordered in a manner that corresponds to
	 * their chemical structure, the order of the elements within any
	 * group or moiety depends on whether or not carbon is present.
	 * * If carbon is present, the order should be:
	 * - C, then H, then the other elements in alphabetical order of
	 * their symbol.
	 * - If carbon is not present, the elements are listed purely in
	 * alphabetic order of their symbol.
	 * * This is the *Hill* system used by Chemical Abstracts.
	 *
	 * @param chemical_formula the chemical_formula
	 */
	public DataNode setChemical_formulaScalar(String chemical_formulaValue);

	/**
	 * Density of the material the container is made from.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDensity();

	/**
	 * Density of the material the container is made from.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param densityDataset the densityDataset
	 */
	public DataNode setDensity(IDataset densityDataset);

	/**
	 * Density of the material the container is made from.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDensityScalar();

	/**
	 * Density of the material the container is made from.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param density the density
	 */
	public DataNode setDensityScalar(Double densityValue);

	/**
	 * Fraction of the volume of the container occupied by the material
	 * forming the container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPacking_fraction();

	/**
	 * Fraction of the volume of the container occupied by the material
	 * forming the container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param packing_fractionDataset the packing_fractionDataset
	 */
	public DataNode setPacking_fraction(IDataset packing_fractionDataset);

	/**
	 * Fraction of the volume of the container occupied by the material
	 * forming the container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPacking_fractionScalar();

	/**
	 * Fraction of the volume of the container occupied by the material
	 * forming the container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param packing_fraction the packing_fraction
	 */
	public DataNode setPacking_fractionScalar(Double packing_fractionValue);

	/**
	 * Relative molecular mass of container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRelative_molecular_mass();

	/**
	 * Relative molecular mass of container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param relative_molecular_massDataset the relative_molecular_massDataset
	 */
	public DataNode setRelative_molecular_mass(IDataset relative_molecular_massDataset);

	/**
	 * Relative molecular mass of container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRelative_molecular_massScalar();

	/**
	 * Relative molecular mass of container.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_comp;
	 * </p>
	 *
	 * @param relative_molecular_mass the relative_molecular_mass
	 */
	public DataNode setRelative_molecular_massScalar(Double relative_molecular_massValue);

	/**
	 * Details of beam incident on container, including the position
	 * relative to the sample (to determine whether the container is
	 * upstream or downstream of the sample).
	 *
	 * @return  the value.
	 */
	public NXbeam getBeam();

	/**
	 * Details of beam incident on container, including the position
	 * relative to the sample (to determine whether the container is
	 * upstream or downstream of the sample).
	 *
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Shape of the container. In combination with orientation this
	 * should allow the beampath through the container to be modelled to
	 * allow the adsorption to be calculated.
	 *
	 * @return  the value.
	 */
	public NXshape getShape();

	/**
	 * Shape of the container. In combination with orientation this
	 * should allow the beampath through the container to be modelled to
	 * allow the adsorption to be calculated.
	 *
	 * @param shapeGroup the shapeGroup
	 */
	public void setShape(NXshape shapeGroup);

	/**
	 * The angle the container makes to the beam and how it may change
	 * during the experiment.In combination with shape this should allow
	 * the beampath through the container to be modelled to allow the
	 * adsorption of the container to be calculated.
	 *
	 * @return  the value.
	 */
	public NXtransformations getOrientation();

	/**
	 * The angle the container makes to the beam and how it may change
	 * during the experiment.In combination with shape this should allow
	 * the beampath through the container to be modelled to allow the
	 * adsorption of the container to be calculated.
	 *
	 * @param orientationGroup the orientationGroup
	 */
	public void setOrientation(NXtransformations orientationGroup);
	// Unprocessed link:reference_measurement

}
