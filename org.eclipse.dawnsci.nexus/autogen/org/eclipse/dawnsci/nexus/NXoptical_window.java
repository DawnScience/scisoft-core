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
 * A window of a cryostat, heater, vacuum chamber or a simple glass slide.
 * This describes cryostat windows and other possible influences for ellipsometry
 * measurements.
 * For environmental measurements, the environment (liquid, vapor
 * etc.) is enclosed in a cell, which has windows both in the
 * direction of the source (entry window) and the detector (exit
 * window) (looking from the sample).
 * The windows also add a phase shift to the light altering the
 * measured signal. This shift has to be corrected based on measuring
 * a known sample (reference sample) or the actual sample of interest
 * in the environmental cell. State if a window correction has been
 * performed in 'window_effects_corrected'. Reference measurements should be
 * considered as a separate experiment (with a separate NeXus file), and
 * the reference data shall be :ref:`linked <Design-Links>` in
 * ``reference_data_link``.
 * The window is considered to be a part of the sample stage but also
 * beam path. Hence, its position within the beam path should be
 * defined by the 'depends_on' field.
 *
 */
public interface NXoptical_window extends NXaperture {

	public static final String NX_WINDOW_EFFECTS_CORRECTED = "window_effects_corrected";
	public static final String NX_WINDOW_EFFECTS_TYPE = "window_effects_type";
	public static final String NX_MATERIAL_OTHER = "material_other";
	public static final String NX_THICKNESS = "thickness";
	public static final String NX_ORIENTATION_ANGLE = "orientation_angle";
	/**
	 * Was a window correction performed? If so, describe the window
	 * correction procedure in ``window_correction/procedure``.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWindow_effects_corrected();

	/**
	 * Was a window correction performed? If so, describe the window
	 * correction procedure in ``window_correction/procedure``.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param window_effects_correctedDataset the window_effects_correctedDataset
	 */
	public DataNode setWindow_effects_corrected(IDataset window_effects_correctedDataset);

	/**
	 * Was a window correction performed? If so, describe the window
	 * correction procedure in ``window_correction/procedure``.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getWindow_effects_correctedScalar();

	/**
	 * Was a window correction performed? If so, describe the window
	 * correction procedure in ``window_correction/procedure``.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param window_effects_corrected the window_effects_corrected
	 */
	public DataNode setWindow_effects_correctedScalar(Boolean window_effects_correctedValue);

	/**
	 * Type of effects due to this window on the measurement.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>interference effects</b> </li>
	 * <li><b>light absorption</b> </li>
	 * <li><b>light scattering</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWindow_effects_type();

	/**
	 * Type of effects due to this window on the measurement.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>interference effects</b> </li>
	 * <li><b>light absorption</b> </li>
	 * <li><b>light scattering</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param window_effects_typeDataset the window_effects_typeDataset
	 */
	public DataNode setWindow_effects_type(IDataset window_effects_typeDataset);

	/**
	 * Type of effects due to this window on the measurement.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>interference effects</b> </li>
	 * <li><b>light absorption</b> </li>
	 * <li><b>light scattering</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getWindow_effects_typeScalar();

	/**
	 * Type of effects due to this window on the measurement.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>interference effects</b> </li>
	 * <li><b>light absorption</b> </li>
	 * <li><b>light scattering</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param window_effects_type the window_effects_type
	 */
	public DataNode setWindow_effects_typeScalar(String window_effects_typeValue);

	/**
	 * Group to describe any window correction - if none performed, then omit this
	 *
	 * @return  the value.
	 */
	public NXprocess getWindow_correction();

	/**
	 * Group to describe any window correction - if none performed, then omit this
	 *
	 * @param window_correctionGroup the window_correctionGroup
	 */
	public void setWindow_correction(NXprocess window_correctionGroup);

	/**
	 * The material of the window.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>quartz</b> </li>
	 * <li><b>diamond</b> </li>
	 * <li><b>calcium fluoride</b> </li>
	 * <li><b>zinc selenide</b> </li>
	 * <li><b>thallium bromoiodide</b> </li>
	 * <li><b>alkali halide compound</b> </li>
	 * <li><b>Mylar</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMaterial();

	/**
	 * The material of the window.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>quartz</b> </li>
	 * <li><b>diamond</b> </li>
	 * <li><b>calcium fluoride</b> </li>
	 * <li><b>zinc selenide</b> </li>
	 * <li><b>thallium bromoiodide</b> </li>
	 * <li><b>alkali halide compound</b> </li>
	 * <li><b>Mylar</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param materialDataset the materialDataset
	 */
	public DataNode setMaterial(IDataset materialDataset);

	/**
	 * The material of the window.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>quartz</b> </li>
	 * <li><b>diamond</b> </li>
	 * <li><b>calcium fluoride</b> </li>
	 * <li><b>zinc selenide</b> </li>
	 * <li><b>thallium bromoiodide</b> </li>
	 * <li><b>alkali halide compound</b> </li>
	 * <li><b>Mylar</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMaterialScalar();

	/**
	 * The material of the window.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>quartz</b> </li>
	 * <li><b>diamond</b> </li>
	 * <li><b>calcium fluoride</b> </li>
	 * <li><b>zinc selenide</b> </li>
	 * <li><b>thallium bromoiodide</b> </li>
	 * <li><b>alkali halide compound</b> </li>
	 * <li><b>Mylar</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param material the material
	 */
	public DataNode setMaterialScalar(String materialValue);

	/**
	 * If you specified 'other' as material, describe here what it is.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMaterial_other();

	/**
	 * If you specified 'other' as material, describe here what it is.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param material_otherDataset the material_otherDataset
	 */
	public DataNode setMaterial_other(IDataset material_otherDataset);

	/**
	 * If you specified 'other' as material, describe here what it is.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMaterial_otherScalar();

	/**
	 * If you specified 'other' as material, describe here what it is.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param material_other the material_other
	 */
	public DataNode setMaterial_otherScalar(String material_otherValue);

	/**
	 * Thickness of the window.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getThickness();

	/**
	 * Thickness of the window.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param thicknessDataset the thicknessDataset
	 */
	public DataNode setThickness(IDataset thicknessDataset);

	/**
	 * Thickness of the window.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getThicknessScalar();

	/**
	 * Thickness of the window.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param thickness the thickness
	 */
	public DataNode setThicknessScalar(Double thicknessValue);

	/**
	 * Angle of the window normal (outer) vs. the substrate normal
	 * (similar to the angle of incidence).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation_angle();

	/**
	 * Angle of the window normal (outer) vs. the substrate normal
	 * (similar to the angle of incidence).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param orientation_angleDataset the orientation_angleDataset
	 */
	public DataNode setOrientation_angle(IDataset orientation_angleDataset);

	/**
	 * Angle of the window normal (outer) vs. the substrate normal
	 * (similar to the angle of incidence).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getOrientation_angleScalar();

	/**
	 * Angle of the window normal (outer) vs. the substrate normal
	 * (similar to the angle of incidence).
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param orientation_angle the orientation_angle
	 */
	public DataNode setOrientation_angleScalar(Double orientation_angleValue);

}
