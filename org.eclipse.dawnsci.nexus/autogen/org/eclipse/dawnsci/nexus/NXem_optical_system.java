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
 * Base class for qualifying an electron optical system.
 *
 */
public interface NXem_optical_system extends NXobject {

	public static final String NX_CAMERA_LENGTH = "camera_length";
	public static final String NX_MAGNIFICATION = "magnification";
	public static final String NX_DEFOCUS = "defocus";
	public static final String NX_SEMI_CONVERGENCE_ANGLE = "semi_convergence_angle";
	public static final String NX_FIELD_OF_VIEW = "field_of_view";
	public static final String NX_WORKING_DISTANCE = "working_distance";
	public static final String NX_PROBE = "probe";
	public static final String NX_PROBE_CURRENT = "probe_current";
	public static final String NX_DOSE_MANAGEMENT = "dose_management";
	public static final String NX_DOSE_RATE = "dose_rate";
	public static final String NX_ROTATION = "rotation";
	public static final String NX_FOCAL_LENGTH = "focal_length";
	public static final String NX_TILT_CORRECTION = "tilt_correction";
	public static final String NX_DYNAMIC_FOCUS_CORRECTION = "dynamic_focus_correction";
	public static final String NX_DYNAMIC_REFOCUSING = "dynamic_refocusing";
	/**
	 * Distance which is present between the specimen surface and the detector plane.
	 * This concept is related to term `Camera Length`_ of the EMglossary standard.
	 * .. _Camera Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000008
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCamera_length();

	/**
	 * Distance which is present between the specimen surface and the detector plane.
	 * This concept is related to term `Camera Length`_ of the EMglossary standard.
	 * .. _Camera Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000008
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param camera_lengthDataset the camera_lengthDataset
	 */
	public DataNode setCamera_length(IDataset camera_lengthDataset);

	/**
	 * Distance which is present between the specimen surface and the detector plane.
	 * This concept is related to term `Camera Length`_ of the EMglossary standard.
	 * .. _Camera Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000008
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCamera_lengthScalar();

	/**
	 * Distance which is present between the specimen surface and the detector plane.
	 * This concept is related to term `Camera Length`_ of the EMglossary standard.
	 * .. _Camera Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000008
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param camera_length the camera_length
	 */
	public DataNode setCamera_lengthScalar(Number camera_lengthValue);

	/**
	 * The factor of enlargement of the apparent size,
	 * not the physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMagnification();

	/**
	 * The factor of enlargement of the apparent size,
	 * not the physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnificationDataset the magnificationDataset
	 */
	public DataNode setMagnification(IDataset magnificationDataset);

	/**
	 * The factor of enlargement of the apparent size,
	 * not the physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMagnificationScalar();

	/**
	 * The factor of enlargement of the apparent size,
	 * not the physical size, of an object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param magnification the magnification
	 */
	public DataNode setMagnificationScalar(Number magnificationValue);

	/**
	 * The defocus aberration constant (oftentimes referred to as c_1_0).
	 * See respective details in :ref:`NXaberration` class instances.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDefocus();

	/**
	 * The defocus aberration constant (oftentimes referred to as c_1_0).
	 * See respective details in :ref:`NXaberration` class instances.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param defocusDataset the defocusDataset
	 */
	public DataNode setDefocus(IDataset defocusDataset);

	/**
	 * The defocus aberration constant (oftentimes referred to as c_1_0).
	 * See respective details in :ref:`NXaberration` class instances.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDefocusScalar();

	/**
	 * The defocus aberration constant (oftentimes referred to as c_1_0).
	 * See respective details in :ref:`NXaberration` class instances.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param defocus the defocus
	 */
	public DataNode setDefocusScalar(Number defocusValue);

	/**
	 * The angle which is given by the semi-opening angle of the cone in a convergent
	 * beam.
	 * This concept is related to term `Convergence Angle`_ of the EMglossary standard.
	 * .. _Convergence Angle: https://purls.helmholtz-metadaten.de/emg/EMG_00000010
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSemi_convergence_angle();

	/**
	 * The angle which is given by the semi-opening angle of the cone in a convergent
	 * beam.
	 * This concept is related to term `Convergence Angle`_ of the EMglossary standard.
	 * .. _Convergence Angle: https://purls.helmholtz-metadaten.de/emg/EMG_00000010
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param semi_convergence_angleDataset the semi_convergence_angleDataset
	 */
	public DataNode setSemi_convergence_angle(IDataset semi_convergence_angleDataset);

	/**
	 * The angle which is given by the semi-opening angle of the cone in a convergent
	 * beam.
	 * This concept is related to term `Convergence Angle`_ of the EMglossary standard.
	 * .. _Convergence Angle: https://purls.helmholtz-metadaten.de/emg/EMG_00000010
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSemi_convergence_angleScalar();

	/**
	 * The angle which is given by the semi-opening angle of the cone in a convergent
	 * beam.
	 * This concept is related to term `Convergence Angle`_ of the EMglossary standard.
	 * .. _Convergence Angle: https://purls.helmholtz-metadaten.de/emg/EMG_00000010
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
	public Dataset getField_of_view();

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
	 * Distance which is determined along the optical axis within the column from (1) the
	 * lower end of the final optical element between the source and the specimen stage;
	 * to (2) the point where the beam is focused.
	 * This concept is related to term `Working Distance`_ of the EMglossary standard.
	 * .. _Working Distance: https://purls.helmholtz-metadaten.de/emg/EMG_00000050
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWorking_distance();

	/**
	 * Distance which is determined along the optical axis within the column from (1) the
	 * lower end of the final optical element between the source and the specimen stage;
	 * to (2) the point where the beam is focused.
	 * This concept is related to term `Working Distance`_ of the EMglossary standard.
	 * .. _Working Distance: https://purls.helmholtz-metadaten.de/emg/EMG_00000050
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distanceDataset the working_distanceDataset
	 */
	public DataNode setWorking_distance(IDataset working_distanceDataset);

	/**
	 * Distance which is determined along the optical axis within the column from (1) the
	 * lower end of the final optical element between the source and the specimen stage;
	 * to (2) the point where the beam is focused.
	 * This concept is related to term `Working Distance`_ of the EMglossary standard.
	 * .. _Working Distance: https://purls.helmholtz-metadaten.de/emg/EMG_00000050
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getWorking_distanceScalar();

	/**
	 * Distance which is determined along the optical axis within the column from (1) the
	 * lower end of the final optical element between the source and the specimen stage;
	 * to (2) the point where the beam is focused.
	 * This concept is related to term `Working Distance`_ of the EMglossary standard.
	 * .. _Working Distance: https://purls.helmholtz-metadaten.de/emg/EMG_00000050
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param working_distance the working_distance
	 */
	public DataNode setWorking_distanceScalar(Number working_distanceValue);

	/**
	 * Geometry of the cross-section formed when the primary beam shines onto the
	 * specimen surface. Reported as length of the semiaxes of the ellipsoidal
	 * cross-section with semiaxes values sorted by decreasing length.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProbe();

	/**
	 * Geometry of the cross-section formed when the primary beam shines onto the
	 * specimen surface. Reported as length of the semiaxes of the ellipsoidal
	 * cross-section with semiaxes values sorted by decreasing length.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param probeDataset the probeDataset
	 */
	public DataNode setProbe(IDataset probeDataset);

	/**
	 * Geometry of the cross-section formed when the primary beam shines onto the
	 * specimen surface. Reported as length of the semiaxes of the ellipsoidal
	 * cross-section with semiaxes values sorted by decreasing length.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getProbeScalar();

	/**
	 * Geometry of the cross-section formed when the primary beam shines onto the
	 * specimen surface. Reported as length of the semiaxes of the ellipsoidal
	 * cross-section with semiaxes values sorted by decreasing length.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param probe the probe
	 */
	public DataNode setProbeScalar(Number probeValue);

	/**
	 * Electrical current which arrives at the specimen.
	 * This concept is related to term `Probe Current`_ of the EMglossary standard.
	 * .. _Probe Current: https://purls.helmholtz-metadaten.de/emg/EMG_00000041
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProbe_current();

	/**
	 * Electrical current which arrives at the specimen.
	 * This concept is related to term `Probe Current`_ of the EMglossary standard.
	 * .. _Probe Current: https://purls.helmholtz-metadaten.de/emg/EMG_00000041
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param probe_currentDataset the probe_currentDataset
	 */
	public DataNode setProbe_current(IDataset probe_currentDataset);

	/**
	 * Electrical current which arrives at the specimen.
	 * This concept is related to term `Probe Current`_ of the EMglossary standard.
	 * .. _Probe Current: https://purls.helmholtz-metadaten.de/emg/EMG_00000041
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getProbe_currentScalar();

	/**
	 * Electrical current which arrives at the specimen.
	 * This concept is related to term `Probe Current`_ of the EMglossary standard.
	 * .. _Probe Current: https://purls.helmholtz-metadaten.de/emg/EMG_00000041
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param probe_current the probe_current
	 */
	public DataNode setProbe_currentScalar(Number probe_currentValue);

	/**
	 * Specify further details how incipient electron or ion dose was quantified
	 * (using beam_current, probe_current).
	 * `Reference <https://doi.org/10.1017/S1551929522000840>`_ discusses
	 * an approach for (electron) dose monitoring in an electron microscope.
	 * The unit of the nominal dose rate is e-/(angstrom^2*s).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDose_management();

	/**
	 * Specify further details how incipient electron or ion dose was quantified
	 * (using beam_current, probe_current).
	 * `Reference <https://doi.org/10.1017/S1551929522000840>`_ discusses
	 * an approach for (electron) dose monitoring in an electron microscope.
	 * The unit of the nominal dose rate is e-/(angstrom^2*s).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param dose_managementDataset the dose_managementDataset
	 */
	public DataNode setDose_management(IDataset dose_managementDataset);

	/**
	 * Specify further details how incipient electron or ion dose was quantified
	 * (using beam_current, probe_current).
	 * `Reference <https://doi.org/10.1017/S1551929522000840>`_ discusses
	 * an approach for (electron) dose monitoring in an electron microscope.
	 * The unit of the nominal dose rate is e-/(angstrom^2*s).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDose_managementScalar();

	/**
	 * Specify further details how incipient electron or ion dose was quantified
	 * (using beam_current, probe_current).
	 * `Reference <https://doi.org/10.1017/S1551929522000840>`_ discusses
	 * an approach for (electron) dose monitoring in an electron microscope.
	 * The unit of the nominal dose rate is e-/(angstrom^2*s).
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param dose_management the dose_management
	 */
	public DataNode setDose_managementScalar(String dose_managementValue);

	/**
	 * Nominal dose rate.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> 1/(angstrom^2*s)
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDose_rate();

	/**
	 * Nominal dose rate.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> 1/(angstrom^2*s)
	 * </p>
	 *
	 * @param dose_rateDataset the dose_rateDataset
	 */
	public DataNode setDose_rate(IDataset dose_rateDataset);

	/**
	 * Nominal dose rate.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> 1/(angstrom^2*s)
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDose_rateScalar();

	/**
	 * Nominal dose rate.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> 1/(angstrom^2*s)
	 * </p>
	 *
	 * @param dose_rate the dose_rate
	 */
	public DataNode setDose_rateScalar(Number dose_rateValue);

	/**
	 * In the process of passing through an :ref:`NXelectromagnetic_lens` electrons are typically accelerated
	 * on a helical path about the optical axis. This causes an image rotation whose strength
	 * is affected by the magnification.
	 * Microscopes may be equipped with compensation methods (implemented in hardware
	 * or software) that reduce but not necessarily eliminate this rotation.
	 * See `L. Reimer <https://doi.org/10.1007/978-3-540-38967-5>`_ for details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRotation();

	/**
	 * In the process of passing through an :ref:`NXelectromagnetic_lens` electrons are typically accelerated
	 * on a helical path about the optical axis. This causes an image rotation whose strength
	 * is affected by the magnification.
	 * Microscopes may be equipped with compensation methods (implemented in hardware
	 * or software) that reduce but not necessarily eliminate this rotation.
	 * See `L. Reimer <https://doi.org/10.1007/978-3-540-38967-5>`_ for details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param rotationDataset the rotationDataset
	 */
	public DataNode setRotation(IDataset rotationDataset);

	/**
	 * In the process of passing through an :ref:`NXelectromagnetic_lens` electrons are typically accelerated
	 * on a helical path about the optical axis. This causes an image rotation whose strength
	 * is affected by the magnification.
	 * Microscopes may be equipped with compensation methods (implemented in hardware
	 * or software) that reduce but not necessarily eliminate this rotation.
	 * See `L. Reimer <https://doi.org/10.1007/978-3-540-38967-5>`_ for details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRotationScalar();

	/**
	 * In the process of passing through an :ref:`NXelectromagnetic_lens` electrons are typically accelerated
	 * on a helical path about the optical axis. This causes an image rotation whose strength
	 * is affected by the magnification.
	 * Microscopes may be equipped with compensation methods (implemented in hardware
	 * or software) that reduce but not necessarily eliminate this rotation.
	 * See `L. Reimer <https://doi.org/10.1007/978-3-540-38967-5>`_ for details.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param rotation the rotation
	 */
	public DataNode setRotationScalar(Number rotationValue);

	/**
	 * Distance which lies between the principal plane of the lens and the focal point
	 * along the optical axis.
	 * This concept is related to term `Focal Length`_ of the EMglossary standard.
	 * .. _Focal Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000029
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFocal_length();

	/**
	 * Distance which lies between the principal plane of the lens and the focal point
	 * along the optical axis.
	 * This concept is related to term `Focal Length`_ of the EMglossary standard.
	 * .. _Focal Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000029
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param focal_lengthDataset the focal_lengthDataset
	 */
	public DataNode setFocal_length(IDataset focal_lengthDataset);

	/**
	 * Distance which lies between the principal plane of the lens and the focal point
	 * along the optical axis.
	 * This concept is related to term `Focal Length`_ of the EMglossary standard.
	 * .. _Focal Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000029
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFocal_lengthScalar();

	/**
	 * Distance which lies between the principal plane of the lens and the focal point
	 * along the optical axis.
	 * This concept is related to term `Focal Length`_ of the EMglossary standard.
	 * .. _Focal Length: https://purls.helmholtz-metadaten.de/emg/EMG_00000029
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param focal_length the focal_length
	 */
	public DataNode setFocal_lengthScalar(Number focal_lengthValue);

	/**
	 * Details about an imaging setting used during acquisition to correct perspective
	 * distortion when imaging a tilted surface or cross section.
	 * This concept is related to term `Tilt Correction`_ of the EMglossary standard.
	 * .. _Tilt Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000047
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTilt_correction();

	/**
	 * Details about an imaging setting used during acquisition to correct perspective
	 * distortion when imaging a tilted surface or cross section.
	 * This concept is related to term `Tilt Correction`_ of the EMglossary standard.
	 * .. _Tilt Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000047
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param tilt_correctionDataset the tilt_correctionDataset
	 */
	public DataNode setTilt_correction(IDataset tilt_correctionDataset);

	/**
	 * Details about an imaging setting used during acquisition to correct perspective
	 * distortion when imaging a tilted surface or cross section.
	 * This concept is related to term `Tilt Correction`_ of the EMglossary standard.
	 * .. _Tilt Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000047
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getTilt_correctionScalar();

	/**
	 * Details about an imaging setting used during acquisition to correct perspective
	 * distortion when imaging a tilted surface or cross section.
	 * This concept is related to term `Tilt Correction`_ of the EMglossary standard.
	 * .. _Tilt Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000047
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param tilt_correction the tilt_correction
	 */
	public DataNode setTilt_correctionScalar(Boolean tilt_correctionValue);

	/**
	 * Details about a dynamic focus correction used.
	 * This concept is related to term `Dynamic Focus Correction`_ of the EMglossary standard.
	 * .. _Dynamic Focus Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000016
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDynamic_focus_correction();

	/**
	 * Details about a dynamic focus correction used.
	 * This concept is related to term `Dynamic Focus Correction`_ of the EMglossary standard.
	 * .. _Dynamic Focus Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000016
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param dynamic_focus_correctionDataset the dynamic_focus_correctionDataset
	 */
	public DataNode setDynamic_focus_correction(IDataset dynamic_focus_correctionDataset);

	/**
	 * Details about a dynamic focus correction used.
	 * This concept is related to term `Dynamic Focus Correction`_ of the EMglossary standard.
	 * .. _Dynamic Focus Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000016
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getDynamic_focus_correctionScalar();

	/**
	 * Details about a dynamic focus correction used.
	 * This concept is related to term `Dynamic Focus Correction`_ of the EMglossary standard.
	 * .. _Dynamic Focus Correction: https://purls.helmholtz-metadaten.de/emg/EMG_00000016
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param dynamic_focus_correction the dynamic_focus_correction
	 */
	public DataNode setDynamic_focus_correctionScalar(Boolean dynamic_focus_correctionValue);

	/**
	 * Details about a workflow used to keep the specimen in focus by automatic means.
	 * This concept is related to term `Dynamic Refocusing`_ of the EMglossary standard.
	 * .. _Dynamic Refocusing: https://purls.helmholtz-metadaten.de/emg/EMG_00000017
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDynamic_refocusing();

	/**
	 * Details about a workflow used to keep the specimen in focus by automatic means.
	 * This concept is related to term `Dynamic Refocusing`_ of the EMglossary standard.
	 * .. _Dynamic Refocusing: https://purls.helmholtz-metadaten.de/emg/EMG_00000017
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param dynamic_refocusingDataset the dynamic_refocusingDataset
	 */
	public DataNode setDynamic_refocusing(IDataset dynamic_refocusingDataset);

	/**
	 * Details about a workflow used to keep the specimen in focus by automatic means.
	 * This concept is related to term `Dynamic Refocusing`_ of the EMglossary standard.
	 * .. _Dynamic Refocusing: https://purls.helmholtz-metadaten.de/emg/EMG_00000017
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDynamic_refocusingScalar();

	/**
	 * Details about a workflow used to keep the specimen in focus by automatic means.
	 * This concept is related to term `Dynamic Refocusing`_ of the EMglossary standard.
	 * .. _Dynamic Refocusing: https://purls.helmholtz-metadaten.de/emg/EMG_00000017
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param dynamic_refocusing the dynamic_refocusing
	 */
	public DataNode setDynamic_refocusingScalar(String dynamic_refocusingValue);

}
