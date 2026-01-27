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
 * Base class to detail a set of rotations, orientations, and disorientations.
 * For getting a more detailed insight into the discussion of the
 * parameterized description of orientations in materials science see:
 * * `H.-J. Bunge <https://doi.org/10.1016/C2013-0-11769-2>`_
 * * `T. B. Britton et al. <https://doi.org/10.1016/j.matchar.2016.04.008>`_
 * * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_
 * * `A. Morawiec <https://doi.org/10.1007/978-3-662-09156-2>`_
 * Once orientations are defined, one can continue to characterize the
 * misorientation and specifically the disorientation. The misorientation describes
 * the rotation that is required to register the lattices of two oriented objects
 * (like crystal lattice) into a crystallographic equivalent orientation:
 * * `R. Bonnet <https://doi.org/10.1107/S0567739480000186>`_
 * The concepts of mis- and disorientation are relevant when analyzing the
 * crystallography of interfaces.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of value tuples.</li>
 * <li><b>n_phases</b>
 * How many phases with usually different crystal and symmetry are distinguished.</li></ul></p>
 *
 */
public interface NXrotations extends NXobject {

	public static final String NX_REFERENCE_FRAME = "reference_frame";
	public static final String NX_CRYSTAL_SYMMETRY = "crystal_symmetry";
	public static final String NX_SAMPLE_SYMMETRY = "sample_symmetry";
	public static final String NX_ROTATION_QUATERNION = "rotation_quaternion";
	public static final String NX_ROTATION_EULER = "rotation_euler";
	public static final String NX_IS_ANTIPODAL = "is_antipodal";
	public static final String NX_ORIENTATION_QUATERNION = "orientation_quaternion";
	public static final String NX_ORIENTATION_EULER = "orientation_euler";
	public static final String NX_MISORIENTATION_QUATERNION = "misorientation_quaternion";
	public static final String NX_MISORIENTATION_ANGLE = "misorientation_angle";
	public static final String NX_MISORIENTATION_AXIS = "misorientation_axis";
	public static final String NX_DISORIENTATION_QUATERNION = "disorientation_quaternion";
	public static final String NX_DISORIENTATION_ANGLE = "disorientation_angle";
	public static final String NX_DISORIENTATION_AXIS = "disorientation_axis";
	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` which contextualizes
	 * how the here reported parameterized quantities can be interpreted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReference_frame();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` which contextualizes
	 * how the here reported parameterized quantities can be interpreted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param reference_frameDataset the reference_frameDataset
	 */
	public DataNode setReference_frame(IDataset reference_frameDataset);

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` which contextualizes
	 * how the here reported parameterized quantities can be interpreted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getReference_frameScalar();

	/**
	 * Reference to an instance of :ref:`NXcoordinate_system` which contextualizes
	 * how the here reported parameterized quantities can be interpreted.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param reference_frame the reference_frame
	 */
	public DataNode setReference_frameScalar(String reference_frameValue);

	/**
	 * Point group which defines the symmetry of the crystal.
	 * This has to be at least a single string. If crystal_symmetry is not
	 * provided, point group 1 is assumed.
	 * In the case that misorientation or disorientation fields are used
	 * and the two crystal sets resolve for phases with a different
	 * crystal symmetry, this field needs to encode two strings:
	 * The first string is for phase A. The second string is for phase B.
	 * An example of this most complex case is the description of the
	 * disorientation between crystals adjoining a hetero-phase boundary.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCrystal_symmetry();

	/**
	 * Point group which defines the symmetry of the crystal.
	 * This has to be at least a single string. If crystal_symmetry is not
	 * provided, point group 1 is assumed.
	 * In the case that misorientation or disorientation fields are used
	 * and the two crystal sets resolve for phases with a different
	 * crystal symmetry, this field needs to encode two strings:
	 * The first string is for phase A. The second string is for phase B.
	 * An example of this most complex case is the description of the
	 * disorientation between crystals adjoining a hetero-phase boundary.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @param crystal_symmetryDataset the crystal_symmetryDataset
	 */
	public DataNode setCrystal_symmetry(IDataset crystal_symmetryDataset);

	/**
	 * Point group which defines the symmetry of the crystal.
	 * This has to be at least a single string. If crystal_symmetry is not
	 * provided, point group 1 is assumed.
	 * In the case that misorientation or disorientation fields are used
	 * and the two crystal sets resolve for phases with a different
	 * crystal symmetry, this field needs to encode two strings:
	 * The first string is for phase A. The second string is for phase B.
	 * An example of this most complex case is the description of the
	 * disorientation between crystals adjoining a hetero-phase boundary.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCrystal_symmetryScalar();

	/**
	 * Point group which defines the symmetry of the crystal.
	 * This has to be at least a single string. If crystal_symmetry is not
	 * provided, point group 1 is assumed.
	 * In the case that misorientation or disorientation fields are used
	 * and the two crystal sets resolve for phases with a different
	 * crystal symmetry, this field needs to encode two strings:
	 * The first string is for phase A. The second string is for phase B.
	 * An example of this most complex case is the description of the
	 * disorientation between crystals adjoining a hetero-phase boundary.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @param crystal_symmetry the crystal_symmetry
	 */
	public DataNode setCrystal_symmetryScalar(String crystal_symmetryValue);

	/**
	 * Point group which defines an assumed symmetry imprinted upon processing
	 * the material/sample which could give rise to or may justify to use a
	 * simplified description of rotations, orientations, misorientations,
	 * and disorientations via numerical procedures that are known as
	 * symmetrization.
	 * If sample_symmetry is not provided, point group 1 is assumed.
	 * The traditionally used symmetrization operations within the texture
	 * community in Materials Science, though, have become obsolete thanks
	 * to improvements in methods, software, and available computing power.
	 * Therefore, users are encouraged to set the sample_symmetry to 1 (triclinic).
	 * In practice one often faces situations where indeed these assumed
	 * symmetries are anyway not fully observed, and thus an accepting of
	 * eventual inaccuracies just for the sake of reporting a simplified
	 * symmetrized description should be avoided.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSample_symmetry();

	/**
	 * Point group which defines an assumed symmetry imprinted upon processing
	 * the material/sample which could give rise to or may justify to use a
	 * simplified description of rotations, orientations, misorientations,
	 * and disorientations via numerical procedures that are known as
	 * symmetrization.
	 * If sample_symmetry is not provided, point group 1 is assumed.
	 * The traditionally used symmetrization operations within the texture
	 * community in Materials Science, though, have become obsolete thanks
	 * to improvements in methods, software, and available computing power.
	 * Therefore, users are encouraged to set the sample_symmetry to 1 (triclinic).
	 * In practice one often faces situations where indeed these assumed
	 * symmetries are anyway not fully observed, and thus an accepting of
	 * eventual inaccuracies just for the sake of reporting a simplified
	 * symmetrized description should be avoided.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @param sample_symmetryDataset the sample_symmetryDataset
	 */
	public DataNode setSample_symmetry(IDataset sample_symmetryDataset);

	/**
	 * Point group which defines an assumed symmetry imprinted upon processing
	 * the material/sample which could give rise to or may justify to use a
	 * simplified description of rotations, orientations, misorientations,
	 * and disorientations via numerical procedures that are known as
	 * symmetrization.
	 * If sample_symmetry is not provided, point group 1 is assumed.
	 * The traditionally used symmetrization operations within the texture
	 * community in Materials Science, though, have become obsolete thanks
	 * to improvements in methods, software, and available computing power.
	 * Therefore, users are encouraged to set the sample_symmetry to 1 (triclinic).
	 * In practice one often faces situations where indeed these assumed
	 * symmetries are anyway not fully observed, and thus an accepting of
	 * eventual inaccuracies just for the sake of reporting a simplified
	 * symmetrized description should be avoided.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSample_symmetryScalar();

	/**
	 * Point group which defines an assumed symmetry imprinted upon processing
	 * the material/sample which could give rise to or may justify to use a
	 * simplified description of rotations, orientations, misorientations,
	 * and disorientations via numerical procedures that are known as
	 * symmetrization.
	 * If sample_symmetry is not provided, point group 1 is assumed.
	 * The traditionally used symmetrization operations within the texture
	 * community in Materials Science, though, have become obsolete thanks
	 * to improvements in methods, software, and available computing power.
	 * Therefore, users are encouraged to set the sample_symmetry to 1 (triclinic).
	 * In practice one often faces situations where indeed these assumed
	 * symmetries are anyway not fully observed, and thus an accepting of
	 * eventual inaccuracies just for the sake of reporting a simplified
	 * symmetrized description should be avoided.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: n_phases;
	 * </p>
	 *
	 * @param sample_symmetry the sample_symmetry
	 */
	public DataNode setSample_symmetryScalar(String sample_symmetryValue);

	/**
	 * The set of rotations expressed in quaternion parameterization considering
	 * crystal_symmetry and sample_symmetry. Rotations which should be
	 * interpreted as antipodal are not marked as such.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRotation_quaternion();

	/**
	 * The set of rotations expressed in quaternion parameterization considering
	 * crystal_symmetry and sample_symmetry. Rotations which should be
	 * interpreted as antipodal are not marked as such.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param rotation_quaternionDataset the rotation_quaternionDataset
	 */
	public DataNode setRotation_quaternion(IDataset rotation_quaternionDataset);

	/**
	 * The set of rotations expressed in quaternion parameterization considering
	 * crystal_symmetry and sample_symmetry. Rotations which should be
	 * interpreted as antipodal are not marked as such.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRotation_quaternionScalar();

	/**
	 * The set of rotations expressed in quaternion parameterization considering
	 * crystal_symmetry and sample_symmetry. Rotations which should be
	 * interpreted as antipodal are not marked as such.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param rotation_quaternion the rotation_quaternion
	 */
	public DataNode setRotation_quaternionScalar(Number rotation_quaternionValue);

	/**
	 * The set of rotations expressed in Euler angle parameterization considering
	 * the same applied symmetries as detailed for the field rotation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many possible
	 * Euler-angle conventions (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRotation_euler();

	/**
	 * The set of rotations expressed in Euler angle parameterization considering
	 * the same applied symmetries as detailed for the field rotation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many possible
	 * Euler-angle conventions (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param rotation_eulerDataset the rotation_eulerDataset
	 */
	public DataNode setRotation_euler(IDataset rotation_eulerDataset);

	/**
	 * The set of rotations expressed in Euler angle parameterization considering
	 * the same applied symmetries as detailed for the field rotation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many possible
	 * Euler-angle conventions (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRotation_eulerScalar();

	/**
	 * The set of rotations expressed in Euler angle parameterization considering
	 * the same applied symmetries as detailed for the field rotation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many possible
	 * Euler-angle conventions (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param rotation_euler the rotation_euler
	 */
	public DataNode setRotation_eulerScalar(Number rotation_eulerValue);

	/**
	 * True for all those value tuples which have assumed antipodal symmetry.
	 * False for all others.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_antipodal();

	/**
	 * True for all those value tuples which have assumed antipodal symmetry.
	 * False for all others.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_antipodalDataset the is_antipodalDataset
	 */
	public DataNode setIs_antipodal(IDataset is_antipodalDataset);

	/**
	 * True for all those value tuples which have assumed antipodal symmetry.
	 * False for all others.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_antipodalScalar();

	/**
	 * True for all those value tuples which have assumed antipodal symmetry.
	 * False for all others.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_antipodal the is_antipodal
	 */
	public DataNode setIs_antipodalScalar(Boolean is_antipodalValue);

	/**
	 * The set of orientations expressed in quaternion parameterization and
	 * obeying symmetry for equivalent cases as detailed in crystal_symmetry
	 * and sample_symmetry. The supplementary field is_antipodal can be used
	 * to mark orientations with the antipodal property.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation_quaternion();

	/**
	 * The set of orientations expressed in quaternion parameterization and
	 * obeying symmetry for equivalent cases as detailed in crystal_symmetry
	 * and sample_symmetry. The supplementary field is_antipodal can be used
	 * to mark orientations with the antipodal property.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param orientation_quaternionDataset the orientation_quaternionDataset
	 */
	public DataNode setOrientation_quaternion(IDataset orientation_quaternionDataset);

	/**
	 * The set of orientations expressed in quaternion parameterization and
	 * obeying symmetry for equivalent cases as detailed in crystal_symmetry
	 * and sample_symmetry. The supplementary field is_antipodal can be used
	 * to mark orientations with the antipodal property.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOrientation_quaternionScalar();

	/**
	 * The set of orientations expressed in quaternion parameterization and
	 * obeying symmetry for equivalent cases as detailed in crystal_symmetry
	 * and sample_symmetry. The supplementary field is_antipodal can be used
	 * to mark orientations with the antipodal property.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param orientation_quaternion the orientation_quaternion
	 */
	public DataNode setOrientation_quaternionScalar(Number orientation_quaternionValue);

	/**
	 * The set of orientations expressed in Euler angle parameterization following
	 * the same assumptions like for orientation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many Euler-angle
	 * conventions possible (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation_euler();

	/**
	 * The set of orientations expressed in Euler angle parameterization following
	 * the same assumptions like for orientation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many Euler-angle
	 * conventions possible (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param orientation_eulerDataset the orientation_eulerDataset
	 */
	public DataNode setOrientation_euler(IDataset orientation_eulerDataset);

	/**
	 * The set of orientations expressed in Euler angle parameterization following
	 * the same assumptions like for orientation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many Euler-angle
	 * conventions possible (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOrientation_eulerScalar();

	/**
	 * The set of orientations expressed in Euler angle parameterization following
	 * the same assumptions like for orientation_quaternion.
	 * To interpret Euler angles correctly, it is necessary to inspect the rotation
	 * conventions behind reference_frame to resolve which of the many Euler-angle
	 * conventions possible (Bunge ZXZ, XYZ, Kocks, Tait, etc.) were used.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param orientation_euler the orientation_euler
	 */
	public DataNode setOrientation_eulerScalar(Number orientation_eulerValue);

	/**
	 * The set of misorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent misorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * The misorientation should not be confused with the disorientation,
	 * as for the latter the angular argument is expected to be the minimal
	 * obeying symmetries.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMisorientation_quaternion();

	/**
	 * The set of misorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent misorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * The misorientation should not be confused with the disorientation,
	 * as for the latter the angular argument is expected to be the minimal
	 * obeying symmetries.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param misorientation_quaternionDataset the misorientation_quaternionDataset
	 */
	public DataNode setMisorientation_quaternion(IDataset misorientation_quaternionDataset);

	/**
	 * The set of misorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent misorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * The misorientation should not be confused with the disorientation,
	 * as for the latter the angular argument is expected to be the minimal
	 * obeying symmetries.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMisorientation_quaternionScalar();

	/**
	 * The set of misorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent misorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * The misorientation should not be confused with the disorientation,
	 * as for the latter the angular argument is expected to be the minimal
	 * obeying symmetries.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param misorientation_quaternion the misorientation_quaternion
	 */
	public DataNode setMisorientation_quaternionScalar(Number misorientation_quaternionValue);

	/**
	 * Misorientation angular argument (eventually signed) following the same
	 * symmetry assumptions as expressed for the field misorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMisorientation_angle();

	/**
	 * Misorientation angular argument (eventually signed) following the same
	 * symmetry assumptions as expressed for the field misorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param misorientation_angleDataset the misorientation_angleDataset
	 */
	public DataNode setMisorientation_angle(IDataset misorientation_angleDataset);

	/**
	 * Misorientation angular argument (eventually signed) following the same
	 * symmetry assumptions as expressed for the field misorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMisorientation_angleScalar();

	/**
	 * Misorientation angular argument (eventually signed) following the same
	 * symmetry assumptions as expressed for the field misorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param misorientation_angle the misorientation_angle
	 */
	public DataNode setMisorientation_angleScalar(Number misorientation_angleValue);

	/**
	 * Misorientation axis (normalized) and signed following the same
	 * symmetry assumptions as expressed for the field misorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMisorientation_axis();

	/**
	 * Misorientation axis (normalized) and signed following the same
	 * symmetry assumptions as expressed for the field misorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param misorientation_axisDataset the misorientation_axisDataset
	 */
	public DataNode setMisorientation_axis(IDataset misorientation_axisDataset);

	/**
	 * Misorientation axis (normalized) and signed following the same
	 * symmetry assumptions as expressed for the field misorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMisorientation_axisScalar();

	/**
	 * Misorientation axis (normalized) and signed following the same
	 * symmetry assumptions as expressed for the field misorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param misorientation_axis the misorientation_axis
	 */
	public DataNode setMisorientation_axisScalar(Number misorientation_axisValue);

	/**
	 * The set of disorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent disorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDisorientation_quaternion();

	/**
	 * The set of disorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent disorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param disorientation_quaternionDataset the disorientation_quaternionDataset
	 */
	public DataNode setDisorientation_quaternion(IDataset disorientation_quaternionDataset);

	/**
	 * The set of disorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent disorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDisorientation_quaternionScalar();

	/**
	 * The set of disorientations expressed in quaternion parameterization
	 * obeying symmetry operations for equivalent disorientations
	 * as defined by crystal_symmetry and sample_symmetry.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param disorientation_quaternion the disorientation_quaternion
	 */
	public DataNode setDisorientation_quaternionScalar(Number disorientation_quaternionValue);

	/**
	 * Disorientations angular argument (should not be signed, see
	 * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_)
	 * following the same symmetry assumptions as expressed for the field
	 * disorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDisorientation_angle();

	/**
	 * Disorientations angular argument (should not be signed, see
	 * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_)
	 * following the same symmetry assumptions as expressed for the field
	 * disorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param disorientation_angleDataset the disorientation_angleDataset
	 */
	public DataNode setDisorientation_angle(IDataset disorientation_angleDataset);

	/**
	 * Disorientations angular argument (should not be signed, see
	 * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_)
	 * following the same symmetry assumptions as expressed for the field
	 * disorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDisorientation_angleScalar();

	/**
	 * Disorientations angular argument (should not be signed, see
	 * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_)
	 * following the same symmetry assumptions as expressed for the field
	 * disorientation_quaternion.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param disorientation_angle the disorientation_angle
	 */
	public DataNode setDisorientation_angleScalar(Number disorientation_angleValue);

	/**
	 * Disorientations axis (normalized) following the same symmetry assumptions
	 * as expressed for the field disorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDisorientation_axis();

	/**
	 * Disorientations axis (normalized) following the same symmetry assumptions
	 * as expressed for the field disorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param disorientation_axisDataset the disorientation_axisDataset
	 */
	public DataNode setDisorientation_axis(IDataset disorientation_axisDataset);

	/**
	 * Disorientations axis (normalized) following the same symmetry assumptions
	 * as expressed for the field disorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDisorientation_axisScalar();

	/**
	 * Disorientations axis (normalized) following the same symmetry assumptions
	 * as expressed for the field disorientation_angle.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param disorientation_axis the disorientation_axis
	 */
	public DataNode setDisorientation_axisScalar(Number disorientation_axisValue);

}
