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

import java.util.Date;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A device blocking the beam in a temporal periodic pattern.
 * A disk which blocks the beam but has one or more slits to periodically
 * let neutrons through as the disk rotates. Often used in pairs, one
 * NXdisk_chopper should be defined for each disk.
 * The rotation of the disk is commonly monitored by recording a timestamp for
 * each full rotation of disk, by having a sensor in the stationary disk housing
 * sensing when it is aligned with a feature (such as a magnet) on the disk.
 * We refer to this below as the "top-dead-center signal".
 * Angles and positive rotation speeds are measured in an anticlockwise
 * direction when facing away from the source.
 * <p><b>Symbols:</b>
 * This symbol will be used below to coordinate datasets with the same shape.<ul>
 * <li><b>n</b>
 * Number of slits in the disk</li></ul></p>
 *
 */
public interface NXdisk_chopper extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_ROTATION_SPEED = "rotation_speed";
	public static final String NX_SLITS = "slits";
	public static final String NX_SLIT_ANGLE = "slit_angle";
	public static final String NX_PAIR_SEPARATION = "pair_separation";
	public static final String NX_SLIT_EDGES = "slit_edges";
	public static final String NX_TOP_DEAD_CENTER = "top_dead_center";
	public static final String NX_TOP_DEAD_CENTER_ATTRIBUTE_START = "start";
	public static final String NX_BEAM_POSITION = "beam_position";
	public static final String NX_RADIUS = "radius";
	public static final String NX_SLIT_HEIGHT = "slit_height";
	public static final String NX_PHASE = "phase";
	public static final String NX_DELAY = "delay";
	public static final String NX_RATIO = "ratio";
	public static final String NX_DISTANCE = "distance";
	public static final String NX_WAVELENGTH_RANGE = "wavelength_range";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Type of the disk-chopper: only one from the enumerated list (match text exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Chopper type single</b> </li>
	 * <li><b>contra_rotating_pair</b> </li>
	 * <li><b>synchro_pair</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of the disk-chopper: only one from the enumerated list (match text exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Chopper type single</b> </li>
	 * <li><b>contra_rotating_pair</b> </li>
	 * <li><b>synchro_pair</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of the disk-chopper: only one from the enumerated list (match text exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Chopper type single</b> </li>
	 * <li><b>contra_rotating_pair</b> </li>
	 * <li><b>synchro_pair</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of the disk-chopper: only one from the enumerated list (match text exactly)
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Chopper type single</b> </li>
	 * <li><b>contra_rotating_pair</b> </li>
	 * <li><b>synchro_pair</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Chopper rotation speed. Positive for anticlockwise rotation when
	 * facing away from the source, negative otherwise.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRotation_speed();

	/**
	 * Chopper rotation speed. Positive for anticlockwise rotation when
	 * facing away from the source, negative otherwise.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param rotation_speedDataset the rotation_speedDataset
	 */
	public DataNode setRotation_speed(IDataset rotation_speedDataset);

	/**
	 * Chopper rotation speed. Positive for anticlockwise rotation when
	 * facing away from the source, negative otherwise.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRotation_speedScalar();

	/**
	 * Chopper rotation speed. Positive for anticlockwise rotation when
	 * facing away from the source, negative otherwise.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param rotation_speed the rotation_speed
	 */
	public DataNode setRotation_speedScalar(Double rotation_speedValue);

	/**
	 * Number of slits
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlits();

	/**
	 * Number of slits
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param slitsDataset the slitsDataset
	 */
	public DataNode setSlits(IDataset slitsDataset);

	/**
	 * Number of slits
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getSlitsScalar();

	/**
	 * Number of slits
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param slits the slits
	 */
	public DataNode setSlitsScalar(Long slitsValue);

	/**
	 * Angular opening
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlit_angle();

	/**
	 * Angular opening
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param slit_angleDataset the slit_angleDataset
	 */
	public DataNode setSlit_angle(IDataset slit_angleDataset);

	/**
	 * Angular opening
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSlit_angleScalar();

	/**
	 * Angular opening
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param slit_angle the slit_angle
	 */
	public DataNode setSlit_angleScalar(Double slit_angleValue);

	/**
	 * Disk spacing in direction of beam
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPair_separation();

	/**
	 * Disk spacing in direction of beam
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param pair_separationDataset the pair_separationDataset
	 */
	public DataNode setPair_separation(IDataset pair_separationDataset);

	/**
	 * Disk spacing in direction of beam
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPair_separationScalar();

	/**
	 * Disk spacing in direction of beam
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param pair_separation the pair_separation
	 */
	public DataNode setPair_separationScalar(Double pair_separationValue);

	/**
	 * Angle of each edge of every slit from the position of the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * The first edge must be the opening edge of a slit, thus the last edge
	 * may have an angle greater than 360 degrees.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlit_edges();

	/**
	 * Angle of each edge of every slit from the position of the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * The first edge must be the opening edge of a slit, thus the last edge
	 * may have an angle greater than 360 degrees.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2n;
	 * </p>
	 *
	 * @param slit_edgesDataset the slit_edgesDataset
	 */
	public DataNode setSlit_edges(IDataset slit_edgesDataset);

	/**
	 * Angle of each edge of every slit from the position of the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * The first edge must be the opening edge of a slit, thus the last edge
	 * may have an angle greater than 360 degrees.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSlit_edgesScalar();

	/**
	 * Angle of each edge of every slit from the position of the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * The first edge must be the opening edge of a slit, thus the last edge
	 * may have an angle greater than 360 degrees.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: 2n;
	 * </p>
	 *
	 * @param slit_edges the slit_edges
	 */
	public DataNode setSlit_edgesScalar(Double slit_edgesValue);

	/**
	 * Timestamps of the top-dead-center signal. The times are relative
	 * to the "start" attribute and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTop_dead_center();

	/**
	 * Timestamps of the top-dead-center signal. The times are relative
	 * to the "start" attribute and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param top_dead_centerDataset the top_dead_centerDataset
	 */
	public DataNode setTop_dead_center(IDataset top_dead_centerDataset);

	/**
	 * Timestamps of the top-dead-center signal. The times are relative
	 * to the "start" attribute and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTop_dead_centerScalar();

	/**
	 * Timestamps of the top-dead-center signal. The times are relative
	 * to the "start" attribute and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param top_dead_center the top_dead_center
	 */
	public DataNode setTop_dead_centerScalar(Number top_dead_centerValue);

	/**
	 *
	 * @return  the value.
	 */
	public Date getTop_dead_centerAttributeStart();

	/**
	 *
	 * @param startValue the startValue
	 */
	public void setTop_dead_centerAttributeStart(Date startValue);

	/**
	 * Angular separation of the center of the beam and the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBeam_position();

	/**
	 * Angular separation of the center of the beam and the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param beam_positionDataset the beam_positionDataset
	 */
	public DataNode setBeam_position(IDataset beam_positionDataset);

	/**
	 * Angular separation of the center of the beam and the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBeam_positionScalar();

	/**
	 * Angular separation of the center of the beam and the
	 * top-dead-center timestamp sensor, anticlockwise when facing
	 * away from the source.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param beam_position the beam_position
	 */
	public DataNode setBeam_positionScalar(Double beam_positionValue);

	/**
	 * Radius of the disk
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadius();

	/**
	 * Radius of the disk
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * Radius of the disk
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRadiusScalar();

	/**
	 * Radius of the disk
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Double radiusValue);

	/**
	 * Total slit height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSlit_height();

	/**
	 * Total slit height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param slit_heightDataset the slit_heightDataset
	 */
	public DataNode setSlit_height(IDataset slit_heightDataset);

	/**
	 * Total slit height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSlit_heightScalar();

	/**
	 * Total slit height
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param slit_height the slit_height
	 */
	public DataNode setSlit_heightScalar(Double slit_heightValue);

	/**
	 * Chopper phase angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPhase();

	/**
	 * Chopper phase angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param phaseDataset the phaseDataset
	 */
	public DataNode setPhase(IDataset phaseDataset);

	/**
	 * Chopper phase angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPhaseScalar();

	/**
	 * Chopper phase angle
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * </p>
	 *
	 * @param phase the phase
	 */
	public DataNode setPhaseScalar(Double phaseValue);

	/**
	 * Time difference between timing system t0 and chopper driving clock signal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDelay();

	/**
	 * Time difference between timing system t0 and chopper driving clock signal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delayDataset the delayDataset
	 */
	public DataNode setDelay(IDataset delayDataset);

	/**
	 * Time difference between timing system t0 and chopper driving clock signal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDelayScalar();

	/**
	 * Time difference between timing system t0 and chopper driving clock signal
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param delay the delay
	 */
	public DataNode setDelayScalar(Number delayValue);

	/**
	 * Pulse reduction factor of this chopper in relation to other
	 * choppers/fastest pulse in the instrument
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRatio();

	/**
	 * Pulse reduction factor of this chopper in relation to other
	 * choppers/fastest pulse in the instrument
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param ratioDataset the ratioDataset
	 */
	public DataNode setRatio(IDataset ratioDataset);

	/**
	 * Pulse reduction factor of this chopper in relation to other
	 * choppers/fastest pulse in the instrument
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getRatioScalar();

	/**
	 * Pulse reduction factor of this chopper in relation to other
	 * choppers/fastest pulse in the instrument
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param ratio the ratio
	 */
	public DataNode setRatioScalar(Long ratioValue);

	/**
	 * Effective distance to the origin.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDistance();

	/**
	 * Effective distance to the origin.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Effective distance to the origin.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * Effective distance to the origin.
	 * Note, it is recommended to use NXtransformations instead.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * Low and high values of wavelength range transmitted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWavelength_range();

	/**
	 * Low and high values of wavelength range transmitted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param wavelength_rangeDataset the wavelength_rangeDataset
	 */
	public DataNode setWavelength_range(IDataset wavelength_rangeDataset);

	/**
	 * Low and high values of wavelength range transmitted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getWavelength_rangeScalar();

	/**
	 * Low and high values of wavelength range transmitted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_WAVELENGTH
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param wavelength_range the wavelength_range
	 */
	public DataNode setWavelength_rangeScalar(Double wavelength_rangeValue);

	/**
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the chopper and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


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
	 * The reference plane of the disk chopper includes the surface of the spinning disk which faces
	 * the source. The reference point in the x and y axis is the point on this surface which is the
	 * centre of the axle which the disk is spinning around. The reference plane is orthogonal to
	 * the z axis and its position is the reference point on that axis.
	 * Note: This reference point in almost all practical cases is not where the beam passes though.
	 * .. image:: disk_chopper/disk_chopper.png
	 * :width: 40%
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
	 * The reference plane of the disk chopper includes the surface of the spinning disk which faces
	 * the source. The reference point in the x and y axis is the point on this surface which is the
	 * centre of the axle which the disk is spinning around. The reference plane is orthogonal to
	 * the z axis and its position is the reference point on that axis.
	 * Note: This reference point in almost all practical cases is not where the beam passes though.
	 * .. image:: disk_chopper/disk_chopper.png
	 * :width: 40%
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
	 * The reference plane of the disk chopper includes the surface of the spinning disk which faces
	 * the source. The reference point in the x and y axis is the point on this surface which is the
	 * centre of the axle which the disk is spinning around. The reference plane is orthogonal to
	 * the z axis and its position is the reference point on that axis.
	 * Note: This reference point in almost all practical cases is not where the beam passes though.
	 * .. image:: disk_chopper/disk_chopper.png
	 * :width: 40%
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
	 * The reference plane of the disk chopper includes the surface of the spinning disk which faces
	 * the source. The reference point in the x and y axis is the point on this surface which is the
	 * centre of the axle which the disk is spinning around. The reference plane is orthogonal to
	 * the z axis and its position is the reference point on that axis.
	 * Note: This reference point in almost all practical cases is not where the beam passes though.
	 * .. image:: disk_chopper/disk_chopper.png
	 * :width: 40%
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
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
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
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
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}
