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
 * Reflection data from diffraction experiments
 * <p><b>Symbols:</b> <ul>
 * <li><b>n</b> 
 * number of reflections</li>
 * <li><b>m</b> 
 * number of experiments</li></ul></p>
 * 
 */
public interface NXreflections extends NXobject {

	public static final String NX_EXPERIMENTS = "experiments";
	public static final String NX_H = "h";
	public static final String NX_H_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_K = "k";
	public static final String NX_K_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_L = "l";
	public static final String NX_L_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_ID = "id";
	public static final String NX_ID_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_REFLECTION_ID = "reflection_id";
	public static final String NX_REFLECTION_ID_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_ENTERING = "entering";
	public static final String NX_ENTERING_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_DET_MODULE = "det_module";
	public static final String NX_DET_MODULE_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_FLAGS = "flags";
	public static final String NX_FLAGS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_D = "d";
	public static final String NX_D_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PARTIALITY = "partiality";
	public static final String NX_PARTIALITY_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_FRAME = "predicted_frame";
	public static final String NX_PREDICTED_FRAME_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_X = "predicted_x";
	public static final String NX_PREDICTED_X_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_Y = "predicted_y";
	public static final String NX_PREDICTED_Y_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_PHI = "predicted_phi";
	public static final String NX_PREDICTED_PHI_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_PX_X = "predicted_px_x";
	public static final String NX_PREDICTED_PX_X_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PREDICTED_PX_Y = "predicted_px_y";
	public static final String NX_PREDICTED_PX_Y_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_FRAME = "observed_frame";
	public static final String NX_OBSERVED_FRAME_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_FRAME_VAR = "observed_frame_var";
	public static final String NX_OBSERVED_FRAME_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_FRAME_ERRORS = "observed_frame_errors";
	public static final String NX_OBSERVED_FRAME_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_X = "observed_px_x";
	public static final String NX_OBSERVED_PX_X_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_X_VAR = "observed_px_x_var";
	public static final String NX_OBSERVED_PX_X_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_X_ERRORS = "observed_px_x_errors";
	public static final String NX_OBSERVED_PX_X_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_Y = "observed_px_y";
	public static final String NX_OBSERVED_PX_Y_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_Y_VAR = "observed_px_y_var";
	public static final String NX_OBSERVED_PX_Y_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PX_Y_ERRORS = "observed_px_y_errors";
	public static final String NX_OBSERVED_PX_Y_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PHI = "observed_phi";
	public static final String NX_OBSERVED_PHI_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PHI_VAR = "observed_phi_var";
	public static final String NX_OBSERVED_PHI_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_PHI_ERRORS = "observed_phi_errors";
	public static final String NX_OBSERVED_PHI_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_X = "observed_x";
	public static final String NX_OBSERVED_X_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_X_VAR = "observed_x_var";
	public static final String NX_OBSERVED_X_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_X_ERRORS = "observed_x_errors";
	public static final String NX_OBSERVED_X_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_Y = "observed_y";
	public static final String NX_OBSERVED_Y_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_Y_VAR = "observed_y_var";
	public static final String NX_OBSERVED_Y_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OBSERVED_Y_ERRORS = "observed_y_errors";
	public static final String NX_OBSERVED_Y_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_BOUNDING_BOX = "bounding_box";
	public static final String NX_BOUNDING_BOX_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_BACKGROUND_MEAN = "background_mean";
	public static final String NX_BACKGROUND_MEAN_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_PRF = "int_prf";
	public static final String NX_INT_PRF_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_PRF_VAR = "int_prf_var";
	public static final String NX_INT_PRF_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_PRF_ERRORS = "int_prf_errors";
	public static final String NX_INT_PRF_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_SUM = "int_sum";
	public static final String NX_INT_SUM_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_SUM_VAR = "int_sum_var";
	public static final String NX_INT_SUM_VAR_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_INT_SUM_ERRORS = "int_sum_errors";
	public static final String NX_INT_SUM_ERRORS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_LP = "lp";
	public static final String NX_LP_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_PRF_CC = "prf_cc";
	public static final String NX_PRF_CC_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_OVERLAPS = "overlaps";
	public static final String NX_OVERLAPS_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_POLAR_ANGLE = "polar_angle";
	public static final String NX_POLAR_ANGLE_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_AZIMUTHAL_ANGLE = "azimuthal_angle";
	public static final String NX_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * The experiments from which the reflection data derives
	 * <p>
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getExperiments();
	
	/**
	 * The experiments from which the reflection data derives
	 * <p>
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param experimentsDataset the experimentsDataset
	 */
	public DataNode setExperiments(IDataset experimentsDataset);

	/**
	 * The experiments from which the reflection data derives
	 * <p>
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getExperimentsScalar();

	/**
	 * The experiments from which the reflection data derives
	 * <p>
	 * <b>Dimensions:</b> 1: m;
	 * </p>
	 * 
	 * @param experiments the experiments
	 */
	public DataNode setExperimentsScalar(String experimentsValue);

	/**
	 * The h component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getH();
	
	/**
	 * The h component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param hDataset the hDataset
	 */
	public DataNode setH(IDataset hDataset);

	/**
	 * The h component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getHScalar();

	/**
	 * The h component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param h the h
	 */
	public DataNode setHScalar(Number hValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getHAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setHAttributeDescription(String descriptionValue);

	/**
	 * The k component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getK();
	
	/**
	 * The k component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param kDataset the kDataset
	 */
	public DataNode setK(IDataset kDataset);

	/**
	 * The k component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getKScalar();

	/**
	 * The k component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param k the k
	 */
	public DataNode setKScalar(Number kValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getKAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setKAttributeDescription(String descriptionValue);

	/**
	 * The l component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getL();
	
	/**
	 * The l component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param lDataset the lDataset
	 */
	public DataNode setL(IDataset lDataset);

	/**
	 * The l component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getLScalar();

	/**
	 * The l component of the miller index
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param l the l
	 */
	public DataNode setLScalar(Number lValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getLAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setLAttributeDescription(String descriptionValue);

	/**
	 * The id of the experiment which resulted in the reflection. If the value
	 * is greater than 0, the experiments must link to a multi-experiment NXmx
	 * group
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getId();
	
	/**
	 * The id of the experiment which resulted in the reflection. If the value
	 * is greater than 0, the experiments must link to a multi-experiment NXmx
	 * group
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param idDataset the idDataset
	 */
	public DataNode setId(IDataset idDataset);

	/**
	 * The id of the experiment which resulted in the reflection. If the value
	 * is greater than 0, the experiments must link to a multi-experiment NXmx
	 * group
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getIdScalar();

	/**
	 * The id of the experiment which resulted in the reflection. If the value
	 * is greater than 0, the experiments must link to a multi-experiment NXmx
	 * group
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param id the id
	 */
	public DataNode setIdScalar(Long idValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getIdAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setIdAttributeDescription(String descriptionValue);

	/**
	 * The id of the reflection. Multiple partials from the same reflection
	 * should all have the same id
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getReflection_id();
	
	/**
	 * The id of the reflection. Multiple partials from the same reflection
	 * should all have the same id
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param reflection_idDataset the reflection_idDataset
	 */
	public DataNode setReflection_id(IDataset reflection_idDataset);

	/**
	 * The id of the reflection. Multiple partials from the same reflection
	 * should all have the same id
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getReflection_idScalar();

	/**
	 * The id of the reflection. Multiple partials from the same reflection
	 * should all have the same id
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param reflection_id the reflection_id
	 */
	public DataNode setReflection_idScalar(Long reflection_idValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getReflection_idAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setReflection_idAttributeDescription(String descriptionValue);

	/**
	 * Is the reflection entering or exiting the Ewald sphere
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEntering();
	
	/**
	 * Is the reflection entering or exiting the Ewald sphere
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param enteringDataset the enteringDataset
	 */
	public DataNode setEntering(IDataset enteringDataset);

	/**
	 * Is the reflection entering or exiting the Ewald sphere
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Boolean getEnteringScalar();

	/**
	 * Is the reflection entering or exiting the Ewald sphere
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param entering the entering
	 */
	public DataNode setEnteringScalar(Boolean enteringValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getEnteringAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setEnteringAttributeDescription(String descriptionValue);

	/**
	 * The detector module on which the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDet_module();
	
	/**
	 * The detector module on which the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param det_moduleDataset the det_moduleDataset
	 */
	public DataNode setDet_module(IDataset det_moduleDataset);

	/**
	 * The detector module on which the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getDet_moduleScalar();

	/**
	 * The detector module on which the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param det_module the det_module
	 */
	public DataNode setDet_moduleScalar(Long det_moduleValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getDet_moduleAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setDet_moduleAttributeDescription(String descriptionValue);

	/**
	 * Status flags describing the reflection.
	 * This is a bit mask. The bits in the mask follow the convention
	 * used by DIALS, and have the following names:
	 * === ==========================================
	 * bit name
	 * === ==========================================
	 * 0 ``predicted``
	 * 1 ``observed``
	 * 2 ``indexed``
	 * 3 ``used_in_refinement``
	 * 4 ``strong``
	 * 5 ``reference_spot``
	 * 6 ``dont_integrate``
	 * 7 ``integrated_sum``
	 * 8 ``integrated_prf``
	 * 9 ``integrated``
	 * 10 ``overloaded``
	 * 11 ``overlapped``
	 * 12 ``overlapped_fg``
	 * 13 ``in_powder_ring``
	 * 14 ``foreground_includes_bad_pixels``
	 * 15 ``background_includes_bad_pixels``
	 * 16 ``includes_bad_pixels``
	 * 17 ``bad_shoebox``
	 * 18 ``bad_spot``
	 * 19 ``used_in_modelling``
	 * 20 ``centroid_outlier``
	 * 21 ``failed_during_background_modelling``
	 * 22 ``failed_during_summation``
	 * 23 ``failed_during_profile_fitting``
	 * 24 ``bad_reference``
	 * === ==========================================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFlags();
	
	/**
	 * Status flags describing the reflection.
	 * This is a bit mask. The bits in the mask follow the convention
	 * used by DIALS, and have the following names:
	 * === ==========================================
	 * bit name
	 * === ==========================================
	 * 0 ``predicted``
	 * 1 ``observed``
	 * 2 ``indexed``
	 * 3 ``used_in_refinement``
	 * 4 ``strong``
	 * 5 ``reference_spot``
	 * 6 ``dont_integrate``
	 * 7 ``integrated_sum``
	 * 8 ``integrated_prf``
	 * 9 ``integrated``
	 * 10 ``overloaded``
	 * 11 ``overlapped``
	 * 12 ``overlapped_fg``
	 * 13 ``in_powder_ring``
	 * 14 ``foreground_includes_bad_pixels``
	 * 15 ``background_includes_bad_pixels``
	 * 16 ``includes_bad_pixels``
	 * 17 ``bad_shoebox``
	 * 18 ``bad_spot``
	 * 19 ``used_in_modelling``
	 * 20 ``centroid_outlier``
	 * 21 ``failed_during_background_modelling``
	 * 22 ``failed_during_summation``
	 * 23 ``failed_during_profile_fitting``
	 * 24 ``bad_reference``
	 * === ==========================================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param flagsDataset the flagsDataset
	 */
	public DataNode setFlags(IDataset flagsDataset);

	/**
	 * Status flags describing the reflection.
	 * This is a bit mask. The bits in the mask follow the convention
	 * used by DIALS, and have the following names:
	 * === ==========================================
	 * bit name
	 * === ==========================================
	 * 0 ``predicted``
	 * 1 ``observed``
	 * 2 ``indexed``
	 * 3 ``used_in_refinement``
	 * 4 ``strong``
	 * 5 ``reference_spot``
	 * 6 ``dont_integrate``
	 * 7 ``integrated_sum``
	 * 8 ``integrated_prf``
	 * 9 ``integrated``
	 * 10 ``overloaded``
	 * 11 ``overlapped``
	 * 12 ``overlapped_fg``
	 * 13 ``in_powder_ring``
	 * 14 ``foreground_includes_bad_pixels``
	 * 15 ``background_includes_bad_pixels``
	 * 16 ``includes_bad_pixels``
	 * 17 ``bad_shoebox``
	 * 18 ``bad_spot``
	 * 19 ``used_in_modelling``
	 * 20 ``centroid_outlier``
	 * 21 ``failed_during_background_modelling``
	 * 22 ``failed_during_summation``
	 * 23 ``failed_during_profile_fitting``
	 * 24 ``bad_reference``
	 * === ==========================================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getFlagsScalar();

	/**
	 * Status flags describing the reflection.
	 * This is a bit mask. The bits in the mask follow the convention
	 * used by DIALS, and have the following names:
	 * === ==========================================
	 * bit name
	 * === ==========================================
	 * 0 ``predicted``
	 * 1 ``observed``
	 * 2 ``indexed``
	 * 3 ``used_in_refinement``
	 * 4 ``strong``
	 * 5 ``reference_spot``
	 * 6 ``dont_integrate``
	 * 7 ``integrated_sum``
	 * 8 ``integrated_prf``
	 * 9 ``integrated``
	 * 10 ``overloaded``
	 * 11 ``overlapped``
	 * 12 ``overlapped_fg``
	 * 13 ``in_powder_ring``
	 * 14 ``foreground_includes_bad_pixels``
	 * 15 ``background_includes_bad_pixels``
	 * 16 ``includes_bad_pixels``
	 * 17 ``bad_shoebox``
	 * 18 ``bad_spot``
	 * 19 ``used_in_modelling``
	 * 20 ``centroid_outlier``
	 * 21 ``failed_during_background_modelling``
	 * 22 ``failed_during_summation``
	 * 23 ``failed_during_profile_fitting``
	 * 24 ``bad_reference``
	 * === ==========================================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param flags the flags
	 */
	public DataNode setFlagsScalar(Long flagsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getFlagsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setFlagsAttributeDescription(String descriptionValue);

	/**
	 * The resolution of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getD();
	
	/**
	 * The resolution of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param dDataset the dDataset
	 */
	public DataNode setD(IDataset dDataset);

	/**
	 * The resolution of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDScalar();

	/**
	 * The resolution of the reflection
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param d the d
	 */
	public DataNode setDScalar(Double dValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getDAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setDAttributeDescription(String descriptionValue);

	/**
	 * The partiality of the reflection.
	 * Dividing by this number will inflate the measured
	 * intensity to the full reflection equivalent.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPartiality();
	
	/**
	 * The partiality of the reflection.
	 * Dividing by this number will inflate the measured
	 * intensity to the full reflection equivalent.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param partialityDataset the partialityDataset
	 */
	public DataNode setPartiality(IDataset partialityDataset);

	/**
	 * The partiality of the reflection.
	 * Dividing by this number will inflate the measured
	 * intensity to the full reflection equivalent.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPartialityScalar();

	/**
	 * The partiality of the reflection.
	 * Dividing by this number will inflate the measured
	 * intensity to the full reflection equivalent.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param partiality the partiality
	 */
	public DataNode setPartialityScalar(Double partialityValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPartialityAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPartialityAttributeDescription(String descriptionValue);

	/**
	 * The frame on which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_frame();
	
	/**
	 * The frame on which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_frameDataset the predicted_frameDataset
	 */
	public DataNode setPredicted_frame(IDataset predicted_frameDataset);

	/**
	 * The frame on which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_frameScalar();

	/**
	 * The frame on which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_frame the predicted_frame
	 */
	public DataNode setPredicted_frameScalar(Double predicted_frameValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_frameAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_frameAttributeDescription(String descriptionValue);

	/**
	 * The x position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_x();
	
	/**
	 * The x position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_xDataset the predicted_xDataset
	 */
	public DataNode setPredicted_x(IDataset predicted_xDataset);

	/**
	 * The x position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_xScalar();

	/**
	 * The x position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_x the predicted_x
	 */
	public DataNode setPredicted_xScalar(Double predicted_xValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_xAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_xAttributeDescription(String descriptionValue);

	/**
	 * The y position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_y();
	
	/**
	 * The y position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_yDataset the predicted_yDataset
	 */
	public DataNode setPredicted_y(IDataset predicted_yDataset);

	/**
	 * The y position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_yScalar();

	/**
	 * The y position at which the bragg peak of the reflection
	 * is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_y the predicted_y
	 */
	public DataNode setPredicted_yScalar(Double predicted_yValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_yAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_yAttributeDescription(String descriptionValue);

	/**
	 * The phi angle at which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_phi();
	
	/**
	 * The phi angle at which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_phiDataset the predicted_phiDataset
	 */
	public DataNode setPredicted_phi(IDataset predicted_phiDataset);

	/**
	 * The phi angle at which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_phiScalar();

	/**
	 * The phi angle at which the bragg peak of the reflection is predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_phi the predicted_phi
	 */
	public DataNode setPredicted_phiScalar(Double predicted_phiValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_phiAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_phiAttributeDescription(String descriptionValue);

	/**
	 * The x pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_px_x();
	
	/**
	 * The x pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_px_xDataset the predicted_px_xDataset
	 */
	public DataNode setPredicted_px_x(IDataset predicted_px_xDataset);

	/**
	 * The x pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_px_xScalar();

	/**
	 * The x pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_px_x the predicted_px_x
	 */
	public DataNode setPredicted_px_xScalar(Double predicted_px_xValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_px_xAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_px_xAttributeDescription(String descriptionValue);

	/**
	 * The y pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPredicted_px_y();
	
	/**
	 * The y pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_px_yDataset the predicted_px_yDataset
	 */
	public DataNode setPredicted_px_y(IDataset predicted_px_yDataset);

	/**
	 * The y pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPredicted_px_yScalar();

	/**
	 * The y pixel position at which the bragg peak of the reflection is
	 * predicted
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param predicted_px_y the predicted_px_y
	 */
	public DataNode setPredicted_px_yScalar(Double predicted_px_yValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPredicted_px_yAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPredicted_px_yAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the frame at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_frame();
	
	/**
	 * The estimate of the frame at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frameDataset the observed_frameDataset
	 */
	public DataNode setObserved_frame(IDataset observed_frameDataset);

	/**
	 * The estimate of the frame at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_frameScalar();

	/**
	 * The estimate of the frame at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frame the observed_frame
	 */
	public DataNode setObserved_frameScalar(Double observed_frameValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_frameAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_frameAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_frame_var();
	
	/**
	 * The variance on the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frame_varDataset the observed_frame_varDataset
	 */
	public DataNode setObserved_frame_var(IDataset observed_frame_varDataset);

	/**
	 * The variance on the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_frame_varScalar();

	/**
	 * The variance on the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frame_var the observed_frame_var
	 */
	public DataNode setObserved_frame_varScalar(Double observed_frame_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_frame_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_frame_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_frame_errors();
	
	/**
	 * The standard deviation of the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frame_errorsDataset the observed_frame_errorsDataset
	 */
	public DataNode setObserved_frame_errors(IDataset observed_frame_errorsDataset);

	/**
	 * The standard deviation of the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_frame_errorsScalar();

	/**
	 * The standard deviation of the estimate of the frame at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_frame_errors the observed_frame_errors
	 */
	public DataNode setObserved_frame_errorsScalar(Double observed_frame_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_frame_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_frame_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the pixel x position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_x();
	
	/**
	 * The estimate of the pixel x position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_xDataset the observed_px_xDataset
	 */
	public DataNode setObserved_px_x(IDataset observed_px_xDataset);

	/**
	 * The estimate of the pixel x position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_xScalar();

	/**
	 * The estimate of the pixel x position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_x the observed_px_x
	 */
	public DataNode setObserved_px_xScalar(Double observed_px_xValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_xAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_xAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_x_var();
	
	/**
	 * The variance on the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_x_varDataset the observed_px_x_varDataset
	 */
	public DataNode setObserved_px_x_var(IDataset observed_px_x_varDataset);

	/**
	 * The variance on the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_x_varScalar();

	/**
	 * The variance on the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_x_var the observed_px_x_var
	 */
	public DataNode setObserved_px_x_varScalar(Double observed_px_x_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_x_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_x_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_x_errors();
	
	/**
	 * The standard deviation of the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_x_errorsDataset the observed_px_x_errorsDataset
	 */
	public DataNode setObserved_px_x_errors(IDataset observed_px_x_errorsDataset);

	/**
	 * The standard deviation of the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_x_errorsScalar();

	/**
	 * The standard deviation of the estimate of the pixel x position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_x_errors the observed_px_x_errors
	 */
	public DataNode setObserved_px_x_errorsScalar(Double observed_px_x_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_x_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_x_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the pixel y position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_y();
	
	/**
	 * The estimate of the pixel y position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_yDataset the observed_px_yDataset
	 */
	public DataNode setObserved_px_y(IDataset observed_px_yDataset);

	/**
	 * The estimate of the pixel y position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_yScalar();

	/**
	 * The estimate of the pixel y position at which the central impact of
	 * the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_y the observed_px_y
	 */
	public DataNode setObserved_px_yScalar(Double observed_px_yValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_yAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_yAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_y_var();
	
	/**
	 * The variance on the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_y_varDataset the observed_px_y_varDataset
	 */
	public DataNode setObserved_px_y_var(IDataset observed_px_y_varDataset);

	/**
	 * The variance on the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_y_varScalar();

	/**
	 * The variance on the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_y_var the observed_px_y_var
	 */
	public DataNode setObserved_px_y_varScalar(Double observed_px_y_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_y_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_y_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_px_y_errors();
	
	/**
	 * The standard deviation of the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_y_errorsDataset the observed_px_y_errorsDataset
	 */
	public DataNode setObserved_px_y_errors(IDataset observed_px_y_errorsDataset);

	/**
	 * The standard deviation of the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_px_y_errorsScalar();

	/**
	 * The standard deviation of the estimate of the pixel y position at which the
	 * central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_px_y_errors the observed_px_y_errors
	 */
	public DataNode setObserved_px_y_errorsScalar(Double observed_px_y_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_px_y_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_px_y_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the phi angle at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_phi();
	
	/**
	 * The estimate of the phi angle at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phiDataset the observed_phiDataset
	 */
	public DataNode setObserved_phi(IDataset observed_phiDataset);

	/**
	 * The estimate of the phi angle at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_phiScalar();

	/**
	 * The estimate of the phi angle at which the central impact of the
	 * reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phi the observed_phi
	 */
	public DataNode setObserved_phiScalar(Double observed_phiValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_phiAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_phiAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_phi_var();
	
	/**
	 * The variance on the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phi_varDataset the observed_phi_varDataset
	 */
	public DataNode setObserved_phi_var(IDataset observed_phi_varDataset);

	/**
	 * The variance on the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_phi_varScalar();

	/**
	 * The variance on the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phi_var the observed_phi_var
	 */
	public DataNode setObserved_phi_varScalar(Double observed_phi_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_phi_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_phi_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_phi_errors();
	
	/**
	 * The standard deviation of the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phi_errorsDataset the observed_phi_errorsDataset
	 */
	public DataNode setObserved_phi_errors(IDataset observed_phi_errorsDataset);

	/**
	 * The standard deviation of the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_phi_errorsScalar();

	/**
	 * The standard deviation of the estimate of the phi angle at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_phi_errors the observed_phi_errors
	 */
	public DataNode setObserved_phi_errorsScalar(Double observed_phi_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_phi_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_phi_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the x position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_x();
	
	/**
	 * The estimate of the x position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_xDataset the observed_xDataset
	 */
	public DataNode setObserved_x(IDataset observed_xDataset);

	/**
	 * The estimate of the x position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_xScalar();

	/**
	 * The estimate of the x position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_x the observed_x
	 */
	public DataNode setObserved_xScalar(Double observed_xValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_xAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_xAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_x_var();
	
	/**
	 * The variance on the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_x_varDataset the observed_x_varDataset
	 */
	public DataNode setObserved_x_var(IDataset observed_x_varDataset);

	/**
	 * The variance on the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_x_varScalar();

	/**
	 * The variance on the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_x_var the observed_x_var
	 */
	public DataNode setObserved_x_varScalar(Double observed_x_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_x_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_x_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_x_errors();
	
	/**
	 * The standard deviation of the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_x_errorsDataset the observed_x_errorsDataset
	 */
	public DataNode setObserved_x_errors(IDataset observed_x_errorsDataset);

	/**
	 * The standard deviation of the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_x_errorsScalar();

	/**
	 * The standard deviation of the estimate of the x position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_x_errors the observed_x_errors
	 */
	public DataNode setObserved_x_errorsScalar(Double observed_x_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_x_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_x_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the y position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_y();
	
	/**
	 * The estimate of the y position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_yDataset the observed_yDataset
	 */
	public DataNode setObserved_y(IDataset observed_yDataset);

	/**
	 * The estimate of the y position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_yScalar();

	/**
	 * The estimate of the y position at which the central
	 * impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_y the observed_y
	 */
	public DataNode setObserved_yScalar(Double observed_yValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_yAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_yAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_y_var();
	
	/**
	 * The variance on the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_y_varDataset the observed_y_varDataset
	 */
	public DataNode setObserved_y_var(IDataset observed_y_varDataset);

	/**
	 * The variance on the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_y_varScalar();

	/**
	 * The variance on the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_y_var the observed_y_var
	 */
	public DataNode setObserved_y_varScalar(Double observed_y_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_y_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_y_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getObserved_y_errors();
	
	/**
	 * The standard deviation of the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_y_errorsDataset the observed_y_errorsDataset
	 */
	public DataNode setObserved_y_errors(IDataset observed_y_errorsDataset);

	/**
	 * The standard deviation of the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getObserved_y_errorsScalar();

	/**
	 * The standard deviation of the estimate of the y position at which
	 * the central impact of the reflection was recorded
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param observed_y_errors the observed_y_errors
	 */
	public DataNode setObserved_y_errorsScalar(Double observed_y_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getObserved_y_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setObserved_y_errorsAttributeDescription(String descriptionValue);

	/**
	 * The bounding box around the recorded recorded reflection.
	 * Should be an integer array of length 6, where the 6 values
	 * are pixel positions or frame numbers, as follows:
	 * ===== ===========================
	 * index meaning
	 * ===== ===========================
	 * 0 The lower pixel x position
	 * 1 The upper pixel x position
	 * 2 The lower pixel y position
	 * 3 The upper pixel y position
	 * 4 The lower frame number
	 * 5 The upper frame number
	 * ===== ===========================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: 6;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBounding_box();
	
	/**
	 * The bounding box around the recorded recorded reflection.
	 * Should be an integer array of length 6, where the 6 values
	 * are pixel positions or frame numbers, as follows:
	 * ===== ===========================
	 * index meaning
	 * ===== ===========================
	 * 0 The lower pixel x position
	 * 1 The upper pixel x position
	 * 2 The lower pixel y position
	 * 3 The upper pixel y position
	 * 4 The lower frame number
	 * 5 The upper frame number
	 * ===== ===========================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: 6;
	 * </p>
	 * 
	 * @param bounding_boxDataset the bounding_boxDataset
	 */
	public DataNode setBounding_box(IDataset bounding_boxDataset);

	/**
	 * The bounding box around the recorded recorded reflection.
	 * Should be an integer array of length 6, where the 6 values
	 * are pixel positions or frame numbers, as follows:
	 * ===== ===========================
	 * index meaning
	 * ===== ===========================
	 * 0 The lower pixel x position
	 * 1 The upper pixel x position
	 * 2 The lower pixel y position
	 * 3 The upper pixel y position
	 * 4 The lower frame number
	 * 5 The upper frame number
	 * ===== ===========================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: 6;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getBounding_boxScalar();

	/**
	 * The bounding box around the recorded recorded reflection.
	 * Should be an integer array of length 6, where the 6 values
	 * are pixel positions or frame numbers, as follows:
	 * ===== ===========================
	 * index meaning
	 * ===== ===========================
	 * 0 The lower pixel x position
	 * 1 The upper pixel x position
	 * 2 The lower pixel y position
	 * 3 The upper pixel y position
	 * 4 The lower frame number
	 * 5 The upper frame number
	 * ===== ===========================
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: 6;
	 * </p>
	 * 
	 * @param bounding_box the bounding_box
	 */
	public DataNode setBounding_boxScalar(Long bounding_boxValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getBounding_boxAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setBounding_boxAttributeDescription(String descriptionValue);

	/**
	 * The mean background under the reflection peak
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBackground_mean();
	
	/**
	 * The mean background under the reflection peak
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param background_meanDataset the background_meanDataset
	 */
	public DataNode setBackground_mean(IDataset background_meanDataset);

	/**
	 * The mean background under the reflection peak
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBackground_meanScalar();

	/**
	 * The mean background under the reflection peak
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param background_mean the background_mean
	 */
	public DataNode setBackground_meanScalar(Double background_meanValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getBackground_meanAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setBackground_meanAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the reflection intensity by profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_prf();
	
	/**
	 * The estimate of the reflection intensity by profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prfDataset the int_prfDataset
	 */
	public DataNode setInt_prf(IDataset int_prfDataset);

	/**
	 * The estimate of the reflection intensity by profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_prfScalar();

	/**
	 * The estimate of the reflection intensity by profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prf the int_prf
	 */
	public DataNode setInt_prfScalar(Double int_prfValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_prfAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_prfAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_prf_var();
	
	/**
	 * The variance on the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prf_varDataset the int_prf_varDataset
	 */
	public DataNode setInt_prf_var(IDataset int_prf_varDataset);

	/**
	 * The variance on the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_prf_varScalar();

	/**
	 * The variance on the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prf_var the int_prf_var
	 */
	public DataNode setInt_prf_varScalar(Double int_prf_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_prf_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_prf_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_prf_errors();
	
	/**
	 * The standard deviation of the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prf_errorsDataset the int_prf_errorsDataset
	 */
	public DataNode setInt_prf_errors(IDataset int_prf_errorsDataset);

	/**
	 * The standard deviation of the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_prf_errorsScalar();

	/**
	 * The standard deviation of the estimate of the reflection intensity by profile
	 * fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_prf_errors the int_prf_errors
	 */
	public DataNode setInt_prf_errorsScalar(Double int_prf_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_prf_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_prf_errorsAttributeDescription(String descriptionValue);

	/**
	 * The estimate of the reflection intensity by summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_sum();
	
	/**
	 * The estimate of the reflection intensity by summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sumDataset the int_sumDataset
	 */
	public DataNode setInt_sum(IDataset int_sumDataset);

	/**
	 * The estimate of the reflection intensity by summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_sumScalar();

	/**
	 * The estimate of the reflection intensity by summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sum the int_sum
	 */
	public DataNode setInt_sumScalar(Double int_sumValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_sumAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_sumAttributeDescription(String descriptionValue);

	/**
	 * The variance on the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_sum_var();
	
	/**
	 * The variance on the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sum_varDataset the int_sum_varDataset
	 */
	public DataNode setInt_sum_var(IDataset int_sum_varDataset);

	/**
	 * The variance on the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_sum_varScalar();

	/**
	 * The variance on the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sum_var the int_sum_var
	 */
	public DataNode setInt_sum_varScalar(Double int_sum_varValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_sum_varAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_sum_varAttributeDescription(String descriptionValue);

	/**
	 * The standard deviation of the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getInt_sum_errors();
	
	/**
	 * The standard deviation of the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sum_errorsDataset the int_sum_errorsDataset
	 */
	public DataNode setInt_sum_errors(IDataset int_sum_errorsDataset);

	/**
	 * The standard deviation of the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getInt_sum_errorsScalar();

	/**
	 * The standard deviation of the estimate of the reflection intensity by
	 * summation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param int_sum_errors the int_sum_errors
	 */
	public DataNode setInt_sum_errorsScalar(Double int_sum_errorsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getInt_sum_errorsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setInt_sum_errorsAttributeDescription(String descriptionValue);

	/**
	 * The LP correction factor to be applied to the reflection intensities
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getLp();
	
	/**
	 * The LP correction factor to be applied to the reflection intensities
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param lpDataset the lpDataset
	 */
	public DataNode setLp(IDataset lpDataset);

	/**
	 * The LP correction factor to be applied to the reflection intensities
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getLpScalar();

	/**
	 * The LP correction factor to be applied to the reflection intensities
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param lp the lp
	 */
	public DataNode setLpScalar(Double lpValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getLpAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setLpAttributeDescription(String descriptionValue);

	/**
	 * The correlation of the reflection profile with the reference profile
	 * used in profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPrf_cc();
	
	/**
	 * The correlation of the reflection profile with the reference profile
	 * used in profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param prf_ccDataset the prf_ccDataset
	 */
	public DataNode setPrf_cc(IDataset prf_ccDataset);

	/**
	 * The correlation of the reflection profile with the reference profile
	 * used in profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPrf_ccScalar();

	/**
	 * The correlation of the reflection profile with the reference profile
	 * used in profile fitting
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param prf_cc the prf_cc
	 */
	public DataNode setPrf_ccScalar(Double prf_ccValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPrf_ccAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPrf_ccAttributeDescription(String descriptionValue);

	/**
	 * An adjacency list specifying the spatial overlaps of reflections. The
	 * adjacency list is specified using an array data type where the elements
	 * of the array are the indices of the adjacent overlapped reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getOverlaps();
	
	/**
	 * An adjacency list specifying the spatial overlaps of reflections. The
	 * adjacency list is specified using an array data type where the elements
	 * of the array are the indices of the adjacent overlapped reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param overlapsDataset the overlapsDataset
	 */
	public DataNode setOverlaps(IDataset overlapsDataset);

	/**
	 * An adjacency list specifying the spatial overlaps of reflections. The
	 * adjacency list is specified using an array data type where the elements
	 * of the array are the indices of the adjacent overlapped reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getOverlapsScalar();

	/**
	 * An adjacency list specifying the spatial overlaps of reflections. The
	 * adjacency list is specified using an array data type where the elements
	 * of the array are the indices of the adjacent overlapped reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param overlaps the overlaps
	 */
	public DataNode setOverlapsScalar(Long overlapsValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getOverlapsAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setOverlapsAttributeDescription(String descriptionValue);

	/**
	 * Polar angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPolar_angle();
	
	/**
	 * Polar angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param polar_angleDataset the polar_angleDataset
	 */
	public DataNode setPolar_angle(IDataset polar_angleDataset);

	/**
	 * Polar angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPolar_angleScalar();

	/**
	 * Polar angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param polar_angle the polar_angle
	 */
	public DataNode setPolar_angleScalar(Double polar_angleValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getPolar_angleAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setPolar_angleAttributeDescription(String descriptionValue);

	/**
	 * Azimuthal angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getAzimuthal_angle();
	
	/**
	 * Azimuthal angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param azimuthal_angleDataset the azimuthal_angleDataset
	 */
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset);

	/**
	 * Azimuthal angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getAzimuthal_angleScalar();

	/**
	 * Azimuthal angle of reflection centroid, following the NeXus simple (spherical polar) coordinate system
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 * 
	 * @param azimuthal_angle the azimuthal_angle
	 */
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue);

	/**
	 * Describes the dataset
	 * 
	 * @return  the value.
	 */
	public String getAttributeDescription();
	
	/**
	 * Describes the dataset
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setAttributeDescription(String descriptionValue);

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
