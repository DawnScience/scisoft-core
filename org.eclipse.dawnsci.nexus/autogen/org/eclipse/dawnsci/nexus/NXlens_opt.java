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
 * Description of an optical lens.
 * <p><b>Symbols:</b><ul>
 * <li><b>N_spectrum</b>
 * Size of the wavelength array for which the refractive index of the material
 * is given.</li>
 * <li><b>N_spectrum_coating</b>
 * Size of the wavelength array for which the refractive index of the coating
 * is given.</li>
 * <li><b>N_spectrum_RT</b>
 * Size of the wavelength array for which the reflectance or transmission of
 * the lens is given.</li></ul></p>
 *
 */
public interface NXlens_opt extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_OTHER_TYPE = "other_type";
	public static final String NX_CHROMATIC = "chromatic";
	public static final String NX_LENS_DIAMETER = "lens_diameter";
	public static final String NX_REFLECTANCE = "reflectance";
	public static final String NX_TRANSMISSION = "transmission";
	public static final String NX_FOCAL_LENGTH = "focal_length";
	public static final String NX_CURVATURE_RADIUS_FACE = "curvature_radius_face";
	public static final String NX_ABBE_NUMBER = "abbe_number";
	/**
	 * Type of the lens (e.g. concave, convex etc.).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>biconcave</b> </li>
	 * <li><b>plano-concave</b> </li>
	 * <li><b>convexo-concave</b> </li>
	 * <li><b>biconvex</b> </li>
	 * <li><b>plano-convex</b> </li>
	 * <li><b>concavo-convex</b> </li>
	 * <li><b>Fresnel lens</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getType();

	/**
	 * Type of the lens (e.g. concave, convex etc.).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>biconcave</b> </li>
	 * <li><b>plano-concave</b> </li>
	 * <li><b>convexo-concave</b> </li>
	 * <li><b>biconvex</b> </li>
	 * <li><b>plano-convex</b> </li>
	 * <li><b>concavo-convex</b> </li>
	 * <li><b>Fresnel lens</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of the lens (e.g. concave, convex etc.).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>biconcave</b> </li>
	 * <li><b>plano-concave</b> </li>
	 * <li><b>convexo-concave</b> </li>
	 * <li><b>biconvex</b> </li>
	 * <li><b>plano-convex</b> </li>
	 * <li><b>concavo-convex</b> </li>
	 * <li><b>Fresnel lens</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of the lens (e.g. concave, convex etc.).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>biconcave</b> </li>
	 * <li><b>plano-concave</b> </li>
	 * <li><b>convexo-concave</b> </li>
	 * <li><b>biconvex</b> </li>
	 * <li><b>plano-convex</b> </li>
	 * <li><b>concavo-convex</b> </li>
	 * <li><b>Fresnel lens</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * If you chose 'other' as type specify what it is.
	 *
	 * @return  the value.
	 */
	public IDataset getOther_type();

	/**
	 * If you chose 'other' as type specify what it is.
	 *
	 * @param other_typeDataset the other_typeDataset
	 */
	public DataNode setOther_type(IDataset other_typeDataset);

	/**
	 * If you chose 'other' as type specify what it is.
	 *
	 * @return  the value.
	 */
	public String getOther_typeScalar();

	/**
	 * If you chose 'other' as type specify what it is.
	 *
	 * @param other_type the other_type
	 */
	public DataNode setOther_typeScalar(String other_typeValue);

	/**
	 * Is it a chromatic lens?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getChromatic();

	/**
	 * Is it a chromatic lens?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param chromaticDataset the chromaticDataset
	 */
	public DataNode setChromatic(IDataset chromaticDataset);

	/**
	 * Is it a chromatic lens?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getChromaticScalar();

	/**
	 * Is it a chromatic lens?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param chromatic the chromatic
	 */
	public DataNode setChromaticScalar(Boolean chromaticValue);

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLens_diameter();

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param lens_diameterDataset the lens_diameterDataset
	 */
	public DataNode setLens_diameter(IDataset lens_diameterDataset);

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLens_diameterScalar();

	/**
	 * Diameter of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param lens_diameter the lens_diameter
	 */
	public DataNode setLens_diameterScalar(Number lens_diameterValue);

	/**
	 * Properties of the substrate material of the lens. If the lens has a
	 * coating specify the coating material and its properties in 'coating'.
	 *
	 * @return  the value.
	 */
	public NXsample getSubstrate();

	/**
	 * Properties of the substrate material of the lens. If the lens has a
	 * coating specify the coating material and its properties in 'coating'.
	 *
	 * @param substrateGroup the substrateGroup
	 */
	public void setSubstrate(NXsample substrateGroup);

	/**
	 * If the lens has a coating describe the material and its properties.
	 * Some basic information can be found e.g. [here]
	 * (https://www.opto-e.com/basics/reflection-transmission-and-coatings).
	 * If the back and front side of the lens are coated with different
	 * materials, use separate COATING(NXsample) fields to describe the coatings
	 * on the front and back side, respectively. For example:
	 * coating_front(NXsample) and coating_back(NXsample).
	 *
	 * @return  the value.
	 */
	public NXsample getCoating();

	/**
	 * If the lens has a coating describe the material and its properties.
	 * Some basic information can be found e.g. [here]
	 * (https://www.opto-e.com/basics/reflection-transmission-and-coatings).
	 * If the back and front side of the lens are coated with different
	 * materials, use separate COATING(NXsample) fields to describe the coatings
	 * on the front and back side, respectively. For example:
	 * coating_front(NXsample) and coating_back(NXsample).
	 *
	 * @param coatingGroup the coatingGroup
	 */
	public void setCoating(NXsample coatingGroup);

	/**
	 * Reflectance of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getReflectance();

	/**
	 * Reflectance of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflectanceDataset the reflectanceDataset
	 */
	public DataNode setReflectance(IDataset reflectanceDataset);

	/**
	 * Reflectance of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getReflectanceScalar();

	/**
	 * Reflectance of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param reflectance the reflectance
	 */
	public DataNode setReflectanceScalar(String reflectanceValue);

	/**
	 * Transmission of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTransmission();

	/**
	 * Transmission of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmissionDataset the transmissionDataset
	 */
	public DataNode setTransmission(IDataset transmissionDataset);

	/**
	 * Transmission of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTransmissionScalar();

	/**
	 * Transmission of the lens at given spectral values.
	 * <p>
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: N_spectrum_RT;
	 * </p>
	 *
	 * @param transmission the transmission
	 */
	public DataNode setTransmissionScalar(String transmissionValue);

	/**
	 * Focal length of the lens on the front side (first value), i.e. where the
	 * beam is incident, and on the back side (second value).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getFocal_length();

	/**
	 * Focal length of the lens on the front side (first value), i.e. where the
	 * beam is incident, and on the back side (second value).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param focal_lengthDataset the focal_lengthDataset
	 */
	public DataNode setFocal_length(IDataset focal_lengthDataset);

	/**
	 * Focal length of the lens on the front side (first value), i.e. where the
	 * beam is incident, and on the back side (second value).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFocal_lengthScalar();

	/**
	 * Focal length of the lens on the front side (first value), i.e. where the
	 * beam is incident, and on the back side (second value).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param focal_length the focal_length
	 */
	public DataNode setFocal_lengthScalar(Number focal_lengthValue);

	/**
	 * Curvature radius of the lens.
	 * Instead of 'FACE' in the name of this field, the user is advised to
	 * specify for which surface (e.g. front or back) the curvature is provided:
	 * e.g. curvature_front or curvature_back. The front face is the surface on
	 * which the light beam is incident, while the back face is the one from
	 * which the light beam exits the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCurvature_radius_face();

	/**
	 * Curvature radius of the lens.
	 * Instead of 'FACE' in the name of this field, the user is advised to
	 * specify for which surface (e.g. front or back) the curvature is provided:
	 * e.g. curvature_front or curvature_back. The front face is the surface on
	 * which the light beam is incident, while the back face is the one from
	 * which the light beam exits the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param curvature_radius_faceDataset the curvature_radius_faceDataset
	 */
	public DataNode setCurvature_radius_face(IDataset curvature_radius_faceDataset);

	/**
	 * Curvature radius of the lens.
	 * Instead of 'FACE' in the name of this field, the user is advised to
	 * specify for which surface (e.g. front or back) the curvature is provided:
	 * e.g. curvature_front or curvature_back. The front face is the surface on
	 * which the light beam is incident, while the back face is the one from
	 * which the light beam exits the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCurvature_radius_faceScalar();

	/**
	 * Curvature radius of the lens.
	 * Instead of 'FACE' in the name of this field, the user is advised to
	 * specify for which surface (e.g. front or back) the curvature is provided:
	 * e.g. curvature_front or curvature_back. The front face is the surface on
	 * which the light beam is incident, while the back face is the one from
	 * which the light beam exits the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param curvature_radius_face the curvature_radius_face
	 */
	public DataNode setCurvature_radius_faceScalar(Number curvature_radius_faceValue);

	/**
	 * Abbe number (or V-number) of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getAbbe_number();

	/**
	 * Abbe number (or V-number) of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param abbe_numberDataset the abbe_numberDataset
	 */
	public DataNode setAbbe_number(IDataset abbe_numberDataset);

	/**
	 * Abbe number (or V-number) of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAbbe_numberScalar();

	/**
	 * Abbe number (or V-number) of the lens.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param abbe_number the abbe_number
	 */
	public DataNode setAbbe_numberScalar(Number abbe_numberValue);

}
