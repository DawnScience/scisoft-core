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
 * A container for qualifying an electron optical system.
 *
 */
public interface NXoptical_system_em extends NXobject {

	public static final String NX_CAMERA_LENGTH = "camera_length";
	public static final String NX_MAGNIFICATION = "magnification";
	public static final String NX_DEFOCUS = "defocus";
	public static final String NX_SEMI_CONVERGENCE_ANGLE = "semi_convergence_angle";
	public static final String NX_FIELD_OF_VIEW = "field_of_view";
	public static final String NX_WORKING_DISTANCE = "working_distance";
	public static final String NX_BEAM_CURRENT = "beam_current";
	public static final String NX_BEAM_CURRENT_DESCRIPTION = "beam_current_description";
	/**
	 * Citing the JEOL TEM glossary this is *an effective distance from a specimen
	 * to a plane where an observed diffraction pattern is formed*.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCamera_length();

	/**
	 * Citing the JEOL TEM glossary this is *an effective distance from a specimen
	 * to a plane where an observed diffraction pattern is formed*.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param camera_lengthDataset the camera_lengthDataset
	 */
	public DataNode setCamera_length(IDataset camera_lengthDataset);

	/**
	 * Citing the JEOL TEM glossary this is *an effective distance from a specimen
	 * to a plane where an observed diffraction pattern is formed*.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCamera_lengthScalar();

	/**
	 * Citing the JEOL TEM glossary this is *an effective distance from a specimen
	 * to a plane where an observed diffraction pattern is formed*.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param camera_length the camera_length
	 */
	public DataNode setCamera_lengthScalar(Number camera_lengthValue);

	/**
	 * The factor of enlargement of the apparent size, not physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMagnification();

	/**
	 * The factor of enlargement of the apparent size, not physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnificationDataset the magnificationDataset
	 */
	public DataNode setMagnification(IDataset magnificationDataset);

	/**
	 * The factor of enlargement of the apparent size, not physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMagnificationScalar();

	/**
	 * The factor of enlargement of the apparent size, not physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnification the magnification
	 */
	public DataNode setMagnificationScalar(Number magnificationValue);

	/**
	 * The defocus aberration constant oftentimes taken as the C_1_0 which
	 * is described in more details in NXaberration.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDefocus();

	/**
	 * The defocus aberration constant oftentimes taken as the C_1_0 which
	 * is described in more details in NXaberration.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param defocusDataset the defocusDataset
	 */
	public DataNode setDefocus(IDataset defocusDataset);

	/**
	 * The defocus aberration constant oftentimes taken as the C_1_0 which
	 * is described in more details in NXaberration.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDefocusScalar();

	/**
	 * The defocus aberration constant oftentimes taken as the C_1_0 which
	 * is described in more details in NXaberration.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param defocus the defocus
	 */
	public DataNode setDefocusScalar(Number defocusValue);

	/**
	 * Citing the JEOL TEM glosssary this is the value *when a cone shaped,
	 * convergent electron beam illuminates a specimen, the semi-angle of the cone
	 * is termed convergence angle.*
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSemi_convergence_angle();

	/**
	 * Citing the JEOL TEM glosssary this is the value *when a cone shaped,
	 * convergent electron beam illuminates a specimen, the semi-angle of the cone
	 * is termed convergence angle.*
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param semi_convergence_angleDataset the semi_convergence_angleDataset
	 */
	public DataNode setSemi_convergence_angle(IDataset semi_convergence_angleDataset);

	/**
	 * Citing the JEOL TEM glosssary this is the value *when a cone shaped,
	 * convergent electron beam illuminates a specimen, the semi-angle of the cone
	 * is termed convergence angle.*
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSemi_convergence_angleScalar();

	/**
	 * Citing the JEOL TEM glosssary this is the value *when a cone shaped,
	 * convergent electron beam illuminates a specimen, the semi-angle of the cone
	 * is termed convergence angle.*
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param semi_convergence_angle the semi_convergence_angle
	 */
	public DataNode setSemi_convergence_angleScalar(Number semi_convergence_angleValue);

	/**
	 * The extent of the observable parts of the specimen given the current
	 * magnification and other settings of the instrument.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getField_of_view();

	/**
	 * The extent of the observable parts of the specimen given the current
	 * magnification and other settings of the instrument.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param field_of_viewDataset the field_of_viewDataset
	 */
	public DataNode setField_of_view(IDataset field_of_viewDataset);

	/**
	 * The extent of the observable parts of the specimen given the current
	 * magnification and other settings of the instrument.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getField_of_viewScalar();

	/**
	 * The extent of the observable parts of the specimen given the current
	 * magnification and other settings of the instrument.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param field_of_view the field_of_view
	 */
	public DataNode setField_of_viewScalar(Number field_of_viewValue);

	/**
	 * Citing `Globalsino <https://www.globalsino.com/EM/page4586.html>`_ this is
	 * *a distance between the specimen and the lower pole piece in SEM system*.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWorking_distance();

	/**
	 * Citing `Globalsino <https://www.globalsino.com/EM/page4586.html>`_ this is
	 * *a distance between the specimen and the lower pole piece in SEM system*.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distanceDataset the working_distanceDataset
	 */
	public DataNode setWorking_distance(IDataset working_distanceDataset);

	/**
	 * Citing `Globalsino <https://www.globalsino.com/EM/page4586.html>`_ this is
	 * *a distance between the specimen and the lower pole piece in SEM system*.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWorking_distanceScalar();

	/**
	 * Citing `Globalsino <https://www.globalsino.com/EM/page4586.html>`_ this is
	 * *a distance between the specimen and the lower pole piece in SEM system*.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distance the working_distance
	 */
	public DataNode setWorking_distanceScalar(Double working_distanceValue);

	/**
	 * Beam current as measured relevant for the illumination of the specimen.
	 * Users should specify further details like how the beam current was measured
	 * using the beam_current_description field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getBeam_current();

	/**
	 * Beam current as measured relevant for the illumination of the specimen.
	 * Users should specify further details like how the beam current was measured
	 * using the beam_current_description field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param beam_currentDataset the beam_currentDataset
	 */
	public DataNode setBeam_current(IDataset beam_currentDataset);

	/**
	 * Beam current as measured relevant for the illumination of the specimen.
	 * Users should specify further details like how the beam current was measured
	 * using the beam_current_description field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBeam_currentScalar();

	/**
	 * Beam current as measured relevant for the illumination of the specimen.
	 * Users should specify further details like how the beam current was measured
	 * using the beam_current_description field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param beam_current the beam_current
	 */
	public DataNode setBeam_currentScalar(Double beam_currentValue);

	/**
	 * Specify further details how the beam current was measured or estimated.
	 *
	 * @return  the value.
	 */
	public IDataset getBeam_current_description();

	/**
	 * Specify further details how the beam current was measured or estimated.
	 *
	 * @param beam_current_descriptionDataset the beam_current_descriptionDataset
	 */
	public DataNode setBeam_current_description(IDataset beam_current_descriptionDataset);

	/**
	 * Specify further details how the beam current was measured or estimated.
	 *
	 * @return  the value.
	 */
	public String getBeam_current_descriptionScalar();

	/**
	 * Specify further details how the beam current was measured or estimated.
	 *
	 * @param beam_current_description the beam_current_description
	 */
	public DataNode setBeam_current_descriptionScalar(String beam_current_descriptionValue);

}
