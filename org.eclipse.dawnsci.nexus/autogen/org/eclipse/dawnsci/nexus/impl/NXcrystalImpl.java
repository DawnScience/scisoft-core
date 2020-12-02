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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

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
 * 
 */
public class NXcrystalImpl extends NXobjectImpl implements NXcrystal {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_SHAPE);

	public NXcrystalImpl() {
		super();
	}

	public NXcrystalImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcrystal.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CRYSTAL;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public IDataset getUsage() {
		return getDataset(NX_USAGE);
	}

	@Override
	public String getUsageScalar() {
		return getString(NX_USAGE);
	}

	@Override
	public DataNode setUsage(IDataset usageDataset) {
		return setDataset(NX_USAGE, usageDataset);
	}

	@Override
	public DataNode setUsageScalar(String usageValue) {
		return setString(NX_USAGE, usageValue);
	}

	@Override
	public IDataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public IDataset getChemical_formula() {
		return getDataset(NX_CHEMICAL_FORMULA);
	}

	@Override
	public String getChemical_formulaScalar() {
		return getString(NX_CHEMICAL_FORMULA);
	}

	@Override
	public DataNode setChemical_formula(IDataset chemical_formulaDataset) {
		return setDataset(NX_CHEMICAL_FORMULA, chemical_formulaDataset);
	}

	@Override
	public DataNode setChemical_formulaScalar(String chemical_formulaValue) {
		return setString(NX_CHEMICAL_FORMULA, chemical_formulaValue);
	}

	@Override
	public IDataset getOrder_no() {
		return getDataset(NX_ORDER_NO);
	}

	@Override
	public Long getOrder_noScalar() {
		return getLong(NX_ORDER_NO);
	}

	@Override
	public DataNode setOrder_no(IDataset order_noDataset) {
		return setDataset(NX_ORDER_NO, order_noDataset);
	}

	@Override
	public DataNode setOrder_noScalar(Long order_noValue) {
		return setField(NX_ORDER_NO, order_noValue);
	}

	@Override
	public IDataset getCut_angle() {
		return getDataset(NX_CUT_ANGLE);
	}

	@Override
	public Double getCut_angleScalar() {
		return getDouble(NX_CUT_ANGLE);
	}

	@Override
	public DataNode setCut_angle(IDataset cut_angleDataset) {
		return setDataset(NX_CUT_ANGLE, cut_angleDataset);
	}

	@Override
	public DataNode setCut_angleScalar(Double cut_angleValue) {
		return setField(NX_CUT_ANGLE, cut_angleValue);
	}

	@Override
	public IDataset getSpace_group() {
		return getDataset(NX_SPACE_GROUP);
	}

	@Override
	public String getSpace_groupScalar() {
		return getString(NX_SPACE_GROUP);
	}

	@Override
	public DataNode setSpace_group(IDataset space_groupDataset) {
		return setDataset(NX_SPACE_GROUP, space_groupDataset);
	}

	@Override
	public DataNode setSpace_groupScalar(String space_groupValue) {
		return setString(NX_SPACE_GROUP, space_groupValue);
	}

	@Override
	public IDataset getUnit_cell() {
		return getDataset(NX_UNIT_CELL);
	}

	@Override
	public Double getUnit_cellScalar() {
		return getDouble(NX_UNIT_CELL);
	}

	@Override
	public DataNode setUnit_cell(IDataset unit_cellDataset) {
		return setDataset(NX_UNIT_CELL, unit_cellDataset);
	}

	@Override
	public DataNode setUnit_cellScalar(Double unit_cellValue) {
		return setField(NX_UNIT_CELL, unit_cellValue);
	}

	@Override
	public IDataset getUnit_cell_a() {
		return getDataset(NX_UNIT_CELL_A);
	}

	@Override
	public Double getUnit_cell_aScalar() {
		return getDouble(NX_UNIT_CELL_A);
	}

	@Override
	public DataNode setUnit_cell_a(IDataset unit_cell_aDataset) {
		return setDataset(NX_UNIT_CELL_A, unit_cell_aDataset);
	}

	@Override
	public DataNode setUnit_cell_aScalar(Double unit_cell_aValue) {
		return setField(NX_UNIT_CELL_A, unit_cell_aValue);
	}

	@Override
	public IDataset getUnit_cell_b() {
		return getDataset(NX_UNIT_CELL_B);
	}

	@Override
	public Double getUnit_cell_bScalar() {
		return getDouble(NX_UNIT_CELL_B);
	}

	@Override
	public DataNode setUnit_cell_b(IDataset unit_cell_bDataset) {
		return setDataset(NX_UNIT_CELL_B, unit_cell_bDataset);
	}

	@Override
	public DataNode setUnit_cell_bScalar(Double unit_cell_bValue) {
		return setField(NX_UNIT_CELL_B, unit_cell_bValue);
	}

	@Override
	public IDataset getUnit_cell_c() {
		return getDataset(NX_UNIT_CELL_C);
	}

	@Override
	public Double getUnit_cell_cScalar() {
		return getDouble(NX_UNIT_CELL_C);
	}

	@Override
	public DataNode setUnit_cell_c(IDataset unit_cell_cDataset) {
		return setDataset(NX_UNIT_CELL_C, unit_cell_cDataset);
	}

	@Override
	public DataNode setUnit_cell_cScalar(Double unit_cell_cValue) {
		return setField(NX_UNIT_CELL_C, unit_cell_cValue);
	}

	@Override
	public IDataset getUnit_cell_alpha() {
		return getDataset(NX_UNIT_CELL_ALPHA);
	}

	@Override
	public Double getUnit_cell_alphaScalar() {
		return getDouble(NX_UNIT_CELL_ALPHA);
	}

	@Override
	public DataNode setUnit_cell_alpha(IDataset unit_cell_alphaDataset) {
		return setDataset(NX_UNIT_CELL_ALPHA, unit_cell_alphaDataset);
	}

	@Override
	public DataNode setUnit_cell_alphaScalar(Double unit_cell_alphaValue) {
		return setField(NX_UNIT_CELL_ALPHA, unit_cell_alphaValue);
	}

	@Override
	public IDataset getUnit_cell_beta() {
		return getDataset(NX_UNIT_CELL_BETA);
	}

	@Override
	public Double getUnit_cell_betaScalar() {
		return getDouble(NX_UNIT_CELL_BETA);
	}

	@Override
	public DataNode setUnit_cell_beta(IDataset unit_cell_betaDataset) {
		return setDataset(NX_UNIT_CELL_BETA, unit_cell_betaDataset);
	}

	@Override
	public DataNode setUnit_cell_betaScalar(Double unit_cell_betaValue) {
		return setField(NX_UNIT_CELL_BETA, unit_cell_betaValue);
	}

	@Override
	public IDataset getUnit_cell_gamma() {
		return getDataset(NX_UNIT_CELL_GAMMA);
	}

	@Override
	public Double getUnit_cell_gammaScalar() {
		return getDouble(NX_UNIT_CELL_GAMMA);
	}

	@Override
	public DataNode setUnit_cell_gamma(IDataset unit_cell_gammaDataset) {
		return setDataset(NX_UNIT_CELL_GAMMA, unit_cell_gammaDataset);
	}

	@Override
	public DataNode setUnit_cell_gammaScalar(Double unit_cell_gammaValue) {
		return setField(NX_UNIT_CELL_GAMMA, unit_cell_gammaValue);
	}

	@Override
	public IDataset getUnit_cell_volume() {
		return getDataset(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public Double getUnit_cell_volumeScalar() {
		return getDouble(NX_UNIT_CELL_VOLUME);
	}

	@Override
	public DataNode setUnit_cell_volume(IDataset unit_cell_volumeDataset) {
		return setDataset(NX_UNIT_CELL_VOLUME, unit_cell_volumeDataset);
	}

	@Override
	public DataNode setUnit_cell_volumeScalar(Double unit_cell_volumeValue) {
		return setField(NX_UNIT_CELL_VOLUME, unit_cell_volumeValue);
	}

	@Override
	public IDataset getOrientation_matrix() {
		return getDataset(NX_ORIENTATION_MATRIX);
	}

	@Override
	public Double getOrientation_matrixScalar() {
		return getDouble(NX_ORIENTATION_MATRIX);
	}

	@Override
	public DataNode setOrientation_matrix(IDataset orientation_matrixDataset) {
		return setDataset(NX_ORIENTATION_MATRIX, orientation_matrixDataset);
	}

	@Override
	public DataNode setOrientation_matrixScalar(Double orientation_matrixValue) {
		return setField(NX_ORIENTATION_MATRIX, orientation_matrixValue);
	}

	@Override
	public IDataset getWavelength() {
		return getDataset(NX_WAVELENGTH);
	}

	@Override
	public Double getWavelengthScalar() {
		return getDouble(NX_WAVELENGTH);
	}

	@Override
	public DataNode setWavelength(IDataset wavelengthDataset) {
		return setDataset(NX_WAVELENGTH, wavelengthDataset);
	}

	@Override
	public DataNode setWavelengthScalar(Double wavelengthValue) {
		return setField(NX_WAVELENGTH, wavelengthValue);
	}

	@Override
	public IDataset getD_spacing() {
		return getDataset(NX_D_SPACING);
	}

	@Override
	public Double getD_spacingScalar() {
		return getDouble(NX_D_SPACING);
	}

	@Override
	public DataNode setD_spacing(IDataset d_spacingDataset) {
		return setDataset(NX_D_SPACING, d_spacingDataset);
	}

	@Override
	public DataNode setD_spacingScalar(Double d_spacingValue) {
		return setField(NX_D_SPACING, d_spacingValue);
	}

	@Override
	public IDataset getScattering_vector() {
		return getDataset(NX_SCATTERING_VECTOR);
	}

	@Override
	public Double getScattering_vectorScalar() {
		return getDouble(NX_SCATTERING_VECTOR);
	}

	@Override
	public DataNode setScattering_vector(IDataset scattering_vectorDataset) {
		return setDataset(NX_SCATTERING_VECTOR, scattering_vectorDataset);
	}

	@Override
	public DataNode setScattering_vectorScalar(Double scattering_vectorValue) {
		return setField(NX_SCATTERING_VECTOR, scattering_vectorValue);
	}

	@Override
	public IDataset getReflection() {
		return getDataset(NX_REFLECTION);
	}

	@Override
	public Long getReflectionScalar() {
		return getLong(NX_REFLECTION);
	}

	@Override
	public DataNode setReflection(IDataset reflectionDataset) {
		return setDataset(NX_REFLECTION, reflectionDataset);
	}

	@Override
	public DataNode setReflectionScalar(Long reflectionValue) {
		return setField(NX_REFLECTION, reflectionValue);
	}

	@Override
	public IDataset getThickness() {
		return getDataset(NX_THICKNESS);
	}

	@Override
	public Double getThicknessScalar() {
		return getDouble(NX_THICKNESS);
	}

	@Override
	public DataNode setThickness(IDataset thicknessDataset) {
		return setDataset(NX_THICKNESS, thicknessDataset);
	}

	@Override
	public DataNode setThicknessScalar(Double thicknessValue) {
		return setField(NX_THICKNESS, thicknessValue);
	}

	@Override
	public IDataset getDensity() {
		return getDataset(NX_DENSITY);
	}

	@Override
	public Number getDensityScalar() {
		return getNumber(NX_DENSITY);
	}

	@Override
	public DataNode setDensity(IDataset densityDataset) {
		return setDataset(NX_DENSITY, densityDataset);
	}

	@Override
	public DataNode setDensityScalar(Number densityValue) {
		return setField(NX_DENSITY, densityValue);
	}

	@Override
	public IDataset getSegment_width() {
		return getDataset(NX_SEGMENT_WIDTH);
	}

	@Override
	public Double getSegment_widthScalar() {
		return getDouble(NX_SEGMENT_WIDTH);
	}

	@Override
	public DataNode setSegment_width(IDataset segment_widthDataset) {
		return setDataset(NX_SEGMENT_WIDTH, segment_widthDataset);
	}

	@Override
	public DataNode setSegment_widthScalar(Double segment_widthValue) {
		return setField(NX_SEGMENT_WIDTH, segment_widthValue);
	}

	@Override
	public IDataset getSegment_height() {
		return getDataset(NX_SEGMENT_HEIGHT);
	}

	@Override
	public Double getSegment_heightScalar() {
		return getDouble(NX_SEGMENT_HEIGHT);
	}

	@Override
	public DataNode setSegment_height(IDataset segment_heightDataset) {
		return setDataset(NX_SEGMENT_HEIGHT, segment_heightDataset);
	}

	@Override
	public DataNode setSegment_heightScalar(Double segment_heightValue) {
		return setField(NX_SEGMENT_HEIGHT, segment_heightValue);
	}

	@Override
	public IDataset getSegment_thickness() {
		return getDataset(NX_SEGMENT_THICKNESS);
	}

	@Override
	public Double getSegment_thicknessScalar() {
		return getDouble(NX_SEGMENT_THICKNESS);
	}

	@Override
	public DataNode setSegment_thickness(IDataset segment_thicknessDataset) {
		return setDataset(NX_SEGMENT_THICKNESS, segment_thicknessDataset);
	}

	@Override
	public DataNode setSegment_thicknessScalar(Double segment_thicknessValue) {
		return setField(NX_SEGMENT_THICKNESS, segment_thicknessValue);
	}

	@Override
	public IDataset getSegment_gap() {
		return getDataset(NX_SEGMENT_GAP);
	}

	@Override
	public Double getSegment_gapScalar() {
		return getDouble(NX_SEGMENT_GAP);
	}

	@Override
	public DataNode setSegment_gap(IDataset segment_gapDataset) {
		return setDataset(NX_SEGMENT_GAP, segment_gapDataset);
	}

	@Override
	public DataNode setSegment_gapScalar(Double segment_gapValue) {
		return setField(NX_SEGMENT_GAP, segment_gapValue);
	}

	@Override
	public IDataset getSegment_columns() {
		return getDataset(NX_SEGMENT_COLUMNS);
	}

	@Override
	public Double getSegment_columnsScalar() {
		return getDouble(NX_SEGMENT_COLUMNS);
	}

	@Override
	public DataNode setSegment_columns(IDataset segment_columnsDataset) {
		return setDataset(NX_SEGMENT_COLUMNS, segment_columnsDataset);
	}

	@Override
	public DataNode setSegment_columnsScalar(Double segment_columnsValue) {
		return setField(NX_SEGMENT_COLUMNS, segment_columnsValue);
	}

	@Override
	public IDataset getSegment_rows() {
		return getDataset(NX_SEGMENT_ROWS);
	}

	@Override
	public Double getSegment_rowsScalar() {
		return getDouble(NX_SEGMENT_ROWS);
	}

	@Override
	public DataNode setSegment_rows(IDataset segment_rowsDataset) {
		return setDataset(NX_SEGMENT_ROWS, segment_rowsDataset);
	}

	@Override
	public DataNode setSegment_rowsScalar(Double segment_rowsValue) {
		return setField(NX_SEGMENT_ROWS, segment_rowsValue);
	}

	@Override
	public IDataset getMosaic_horizontal() {
		return getDataset(NX_MOSAIC_HORIZONTAL);
	}

	@Override
	public Double getMosaic_horizontalScalar() {
		return getDouble(NX_MOSAIC_HORIZONTAL);
	}

	@Override
	public DataNode setMosaic_horizontal(IDataset mosaic_horizontalDataset) {
		return setDataset(NX_MOSAIC_HORIZONTAL, mosaic_horizontalDataset);
	}

	@Override
	public DataNode setMosaic_horizontalScalar(Double mosaic_horizontalValue) {
		return setField(NX_MOSAIC_HORIZONTAL, mosaic_horizontalValue);
	}

	@Override
	public IDataset getMosaic_vertical() {
		return getDataset(NX_MOSAIC_VERTICAL);
	}

	@Override
	public Double getMosaic_verticalScalar() {
		return getDouble(NX_MOSAIC_VERTICAL);
	}

	@Override
	public DataNode setMosaic_vertical(IDataset mosaic_verticalDataset) {
		return setDataset(NX_MOSAIC_VERTICAL, mosaic_verticalDataset);
	}

	@Override
	public DataNode setMosaic_verticalScalar(Double mosaic_verticalValue) {
		return setField(NX_MOSAIC_VERTICAL, mosaic_verticalValue);
	}

	@Override
	public IDataset getCurvature_horizontal() {
		return getDataset(NX_CURVATURE_HORIZONTAL);
	}

	@Override
	public Double getCurvature_horizontalScalar() {
		return getDouble(NX_CURVATURE_HORIZONTAL);
	}

	@Override
	public DataNode setCurvature_horizontal(IDataset curvature_horizontalDataset) {
		return setDataset(NX_CURVATURE_HORIZONTAL, curvature_horizontalDataset);
	}

	@Override
	public DataNode setCurvature_horizontalScalar(Double curvature_horizontalValue) {
		return setField(NX_CURVATURE_HORIZONTAL, curvature_horizontalValue);
	}

	@Override
	public IDataset getCurvature_vertical() {
		return getDataset(NX_CURVATURE_VERTICAL);
	}

	@Override
	public Double getCurvature_verticalScalar() {
		return getDouble(NX_CURVATURE_VERTICAL);
	}

	@Override
	public DataNode setCurvature_vertical(IDataset curvature_verticalDataset) {
		return setDataset(NX_CURVATURE_VERTICAL, curvature_verticalDataset);
	}

	@Override
	public DataNode setCurvature_verticalScalar(Double curvature_verticalValue) {
		return setField(NX_CURVATURE_VERTICAL, curvature_verticalValue);
	}

	@Override
	public IDataset getIs_cylindrical() {
		return getDataset(NX_IS_CYLINDRICAL);
	}

	@Override
	public Boolean getIs_cylindricalScalar() {
		return getBoolean(NX_IS_CYLINDRICAL);
	}

	@Override
	public DataNode setIs_cylindrical(IDataset is_cylindricalDataset) {
		return setDataset(NX_IS_CYLINDRICAL, is_cylindricalDataset);
	}

	@Override
	public DataNode setIs_cylindricalScalar(Boolean is_cylindricalValue) {
		return setField(NX_IS_CYLINDRICAL, is_cylindricalValue);
	}

	@Override
	public IDataset getCylindrical_orientation_angle() {
		return getDataset(NX_CYLINDRICAL_ORIENTATION_ANGLE);
	}

	@Override
	public Number getCylindrical_orientation_angleScalar() {
		return getNumber(NX_CYLINDRICAL_ORIENTATION_ANGLE);
	}

	@Override
	public DataNode setCylindrical_orientation_angle(IDataset cylindrical_orientation_angleDataset) {
		return setDataset(NX_CYLINDRICAL_ORIENTATION_ANGLE, cylindrical_orientation_angleDataset);
	}

	@Override
	public DataNode setCylindrical_orientation_angleScalar(Number cylindrical_orientation_angleValue) {
		return setField(NX_CYLINDRICAL_ORIENTATION_ANGLE, cylindrical_orientation_angleValue);
	}

	@Override
	public IDataset getPolar_angle() {
		return getDataset(NX_POLAR_ANGLE);
	}

	@Override
	public Double getPolar_angleScalar() {
		return getDouble(NX_POLAR_ANGLE);
	}

	@Override
	public DataNode setPolar_angle(IDataset polar_angleDataset) {
		return setDataset(NX_POLAR_ANGLE, polar_angleDataset);
	}

	@Override
	public DataNode setPolar_angleScalar(Double polar_angleValue) {
		return setField(NX_POLAR_ANGLE, polar_angleValue);
	}

	@Override
	public IDataset getAzimuthal_angle() {
		return getDataset(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public Double getAzimuthal_angleScalar() {
		return getDouble(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset) {
		return setDataset(NX_AZIMUTHAL_ANGLE, azimuthal_angleDataset);
	}

	@Override
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue) {
		return setField(NX_AZIMUTHAL_ANGLE, azimuthal_angleValue);
	}

	@Override
	public IDataset getBragg_angle() {
		return getDataset(NX_BRAGG_ANGLE);
	}

	@Override
	public Double getBragg_angleScalar() {
		return getDouble(NX_BRAGG_ANGLE);
	}

	@Override
	public DataNode setBragg_angle(IDataset bragg_angleDataset) {
		return setDataset(NX_BRAGG_ANGLE, bragg_angleDataset);
	}

	@Override
	public DataNode setBragg_angleScalar(Double bragg_angleValue) {
		return setField(NX_BRAGG_ANGLE, bragg_angleValue);
	}

	@Override
	public IDataset getTemperature() {
		return getDataset(NX_TEMPERATURE);
	}

	@Override
	public Double getTemperatureScalar() {
		return getDouble(NX_TEMPERATURE);
	}

	@Override
	public DataNode setTemperature(IDataset temperatureDataset) {
		return setDataset(NX_TEMPERATURE, temperatureDataset);
	}

	@Override
	public DataNode setTemperatureScalar(Double temperatureValue) {
		return setField(NX_TEMPERATURE, temperatureValue);
	}

	@Override
	public IDataset getTemperature_coefficient() {
		return getDataset(NX_TEMPERATURE_COEFFICIENT);
	}

	@Override
	public Double getTemperature_coefficientScalar() {
		return getDouble(NX_TEMPERATURE_COEFFICIENT);
	}

	@Override
	public DataNode setTemperature_coefficient(IDataset temperature_coefficientDataset) {
		return setDataset(NX_TEMPERATURE_COEFFICIENT, temperature_coefficientDataset);
	}

	@Override
	public DataNode setTemperature_coefficientScalar(Double temperature_coefficientValue) {
		return setField(NX_TEMPERATURE_COEFFICIENT, temperature_coefficientValue);
	}

	@Override
	public NXlog getTemperature_log() {
		// dataNodeName = NX_TEMPERATURE_LOG
		return getChild("temperature_log", NXlog.class);
	}

	@Override
	public void setTemperature_log(NXlog temperature_logGroup) {
		putChild("temperature_log", temperature_logGroup);
	}

	@Override
	public NXdata getReflectivity() {
		// dataNodeName = NX_REFLECTIVITY
		return getChild("reflectivity", NXdata.class);
	}

	@Override
	public void setReflectivity(NXdata reflectivityGroup) {
		putChild("reflectivity", reflectivityGroup);
	}

	@Override
	public NXdata getTransmission() {
		// dataNodeName = NX_TRANSMISSION
		return getChild("transmission", NXdata.class);
	}

	@Override
	public void setTransmission(NXdata transmissionGroup) {
		putChild("transmission", transmissionGroup);
	}

	@Override
	public NXshape getShape() {
		// dataNodeName = NX_SHAPE
		return getChild("shape", NXshape.class);
	}

	@Override
	public void setShape(NXshape shapeGroup) {
		putChild("shape", shapeGroup);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
