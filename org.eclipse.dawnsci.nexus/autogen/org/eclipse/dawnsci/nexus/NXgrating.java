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

/**
 * A diffraction grating, as could be used in a soft X-ray monochromator
 * 
 */
public interface NXgrating extends NXobject {

	public static final String NX_ANGLES = "angles";
	public static final String NX_PERIOD = "period";
	public static final String NX_DUTY_CYCLE = "duty_cycle";
	public static final String NX_DEPTH = "depth";
	public static final String NX_DIFFRACTION_ORDER = "diffraction_order";
	public static final String NX_DEFLECTION_ANGLE = "deflection_angle";
	public static final String NX_INTERIOR_ATMOSPHERE = "interior_atmosphere";
	public static final String NX_SUBSTRATE_MATERIAL = "substrate_material";
	public static final String NX_SUBSTRATE_DENSITY = "substrate_density";
	public static final String NX_SUBSTRATE_THICKNESS = "substrate_thickness";
	public static final String NX_COATING_MATERIAL = "coating_material";
	public static final String NX_SUBSTRATE_ROUGHNESS = "substrate_roughness";
	public static final String NX_COATING_ROUGHNESS = "coating_roughness";
	public static final String NX_LAYER_THICKNESS = "layer_thickness";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Blaze or trapezoidal angles, with the angle of the upstream facing edge listed first. Blazed gratings can be identified by the low value of the first-listed angle.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAngles();
	
	/**
	 * Blaze or trapezoidal angles, with the angle of the upstream facing edge listed first. Blazed gratings can be identified by the low value of the first-listed angle.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @param anglesDataset the anglesDataset
	 */
	public DataNode setAngles(IDataset anglesDataset);

	/**
	 * Blaze or trapezoidal angles, with the angle of the upstream facing edge listed first. Blazed gratings can be identified by the low value of the first-listed angle.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getAnglesScalar();

	/**
	 * Blaze or trapezoidal angles, with the angle of the upstream facing edge listed first. Blazed gratings can be identified by the low value of the first-listed angle.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 * 
	 * @param angles the angles
	 */
	public DataNode setAnglesScalar(Double anglesValue);

	/**
	 * List of polynomial coefficients describing the spatial separation of lines/grooves as a function of position along the grating, in increasing powers of position. Gratings which do not have variable line spacing will only have a single coefficient (constant).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPeriod();
	
	/**
	 * List of polynomial coefficients describing the spatial separation of lines/grooves as a function of position along the grating, in increasing powers of position. Gratings which do not have variable line spacing will only have a single coefficient (constant).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b>
	 * </p>
	 * 
	 * @param periodDataset the periodDataset
	 */
	public DataNode setPeriod(IDataset periodDataset);

	/**
	 * List of polynomial coefficients describing the spatial separation of lines/grooves as a function of position along the grating, in increasing powers of position. Gratings which do not have variable line spacing will only have a single coefficient (constant).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPeriodScalar();

	/**
	 * List of polynomial coefficients describing the spatial separation of lines/grooves as a function of position along the grating, in increasing powers of position. Gratings which do not have variable line spacing will only have a single coefficient (constant).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b>
	 * </p>
	 * 
	 * @param period the period
	 */
	public DataNode setPeriodScalar(Double periodValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDuty_cycle();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param duty_cycleDataset the duty_cycleDataset
	 */
	public DataNode setDuty_cycle(IDataset duty_cycleDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDuty_cycleScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param duty_cycle the duty_cycle
	 */
	public DataNode setDuty_cycleScalar(Double duty_cycleValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepth();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param depthDataset the depthDataset
	 */
	public DataNode setDepth(IDataset depthDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDepthScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param depth the depth
	 */
	public DataNode setDepthScalar(Double depthValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDiffraction_order();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param diffraction_orderDataset the diffraction_orderDataset
	 */
	public DataNode setDiffraction_order(IDataset diffraction_orderDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getDiffraction_orderScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param diffraction_order the diffraction_order
	 */
	public DataNode setDiffraction_orderScalar(Long diffraction_orderValue);

	/**
	 * Angle between the incident beam and the utilised outgoing beam.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDeflection_angle();
	
	/**
	 * Angle between the incident beam and the utilised outgoing beam.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param deflection_angleDataset the deflection_angleDataset
	 */
	public DataNode setDeflection_angle(IDataset deflection_angleDataset);

	/**
	 * Angle between the incident beam and the utilised outgoing beam.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDeflection_angleScalar();

	/**
	 * Angle between the incident beam and the utilised outgoing beam.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 * 
	 * @param deflection_angle the deflection_angle
	 */
	public DataNode setDeflection_angleScalar(Double deflection_angleValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInterior_atmosphere();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 * 
	 * @param interior_atmosphereDataset the interior_atmosphereDataset
	 */
	public DataNode setInterior_atmosphere(IDataset interior_atmosphereDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getInterior_atmosphereScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>helium</b> </li>
	 * <li><b>argon</b> </li></ul></p>
	 * </p>
	 * 
	 * @param interior_atmosphere the interior_atmosphere
	 */
	public DataNode setInterior_atmosphereScalar(String interior_atmosphereValue);

	/**
	 * 
	 * @return  the value.
	 */
	public IDataset getSubstrate_material();
	
	/**
	 * 
	 * @param substrate_materialDataset the substrate_materialDataset
	 */
	public DataNode setSubstrate_material(IDataset substrate_materialDataset);

	/**
	 * 
	 * @return  the value.
	 */
	public String getSubstrate_materialScalar();

	/**
	 * 
	 * @param substrate_material the substrate_material
	 */
	public DataNode setSubstrate_materialScalar(String substrate_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSubstrate_density();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 * 
	 * @param substrate_densityDataset the substrate_densityDataset
	 */
	public DataNode setSubstrate_density(IDataset substrate_densityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSubstrate_densityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS_DENSITY
	 * </p>
	 * 
	 * @param substrate_density the substrate_density
	 */
	public DataNode setSubstrate_densityScalar(Double substrate_densityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSubstrate_thickness();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param substrate_thicknessDataset the substrate_thicknessDataset
	 */
	public DataNode setSubstrate_thickness(IDataset substrate_thicknessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSubstrate_thicknessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param substrate_thickness the substrate_thickness
	 */
	public DataNode setSubstrate_thicknessScalar(Double substrate_thicknessValue);

	/**
	 * 
	 * @return  the value.
	 */
	public IDataset getCoating_material();
	
	/**
	 * 
	 * @param coating_materialDataset the coating_materialDataset
	 */
	public DataNode setCoating_material(IDataset coating_materialDataset);

	/**
	 * 
	 * @return  the value.
	 */
	public String getCoating_materialScalar();

	/**
	 * 
	 * @param coating_material the coating_material
	 */
	public DataNode setCoating_materialScalar(String coating_materialValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSubstrate_roughness();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param substrate_roughnessDataset the substrate_roughnessDataset
	 */
	public DataNode setSubstrate_roughness(IDataset substrate_roughnessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSubstrate_roughnessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param substrate_roughness the substrate_roughness
	 */
	public DataNode setSubstrate_roughnessScalar(Double substrate_roughnessValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCoating_roughness();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param coating_roughnessDataset the coating_roughnessDataset
	 */
	public DataNode setCoating_roughness(IDataset coating_roughnessDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getCoating_roughnessScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param coating_roughness the coating_roughness
	 */
	public DataNode setCoating_roughnessScalar(Double coating_roughnessValue);

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLayer_thickness();
	
	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param layer_thicknessDataset the layer_thicknessDataset
	 */
	public DataNode setLayer_thickness(IDataset layer_thicknessDataset);

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getLayer_thicknessScalar();

	/**
	 * An array describing the thickness of each layer
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param layer_thickness the layer_thickness
	 */
	public DataNode setLayer_thicknessScalar(Double layer_thicknessValue);

	/**
	 * A NXshape group describing the shape of the mirror
	 * 
	 * @return  the value.
	 */
	public NXshape getShape();
	
	/**
	 * A NXshape group describing the shape of the mirror
	 * 
	 * @param shapeGroup the shapeGroup
	 */
	public void setShape(NXshape shapeGroup);

	/**
	 * Numerical description of the surface figure of the mirror.
	 * 
	 * @return  the value.
	 */
	public NXdata getFigure_data();
	
	/**
	 * Numerical description of the surface figure of the mirror.
	 * 
	 * @param figure_dataGroup the figure_dataGroup
	 */
	public void setFigure_data(NXdata figure_dataGroup);

	/**
	 * "Engineering" position of the grating
	 * 
	 * @return  the value.
	 */
	public NXtransformations getTransformations();
	
	/**
	 * "Engineering" position of the grating
	 * 
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * "Engineering" position of the grating</li>
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
	 * "Engineering" position of the grating</li>
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
	 * "Engineering" position of the grating</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * "Engineering" position of the grating</li>
	 * </ul>
	 * 
	 * @param transformations the child nodes to add 
	 */
	
	public void setAllTransformations(Map<String, NXtransformations> transformations);
	

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
