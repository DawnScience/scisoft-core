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
 * Contains the settings of a PID controller.
 *
 */
public interface NXpid extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_SETPOINT = "setpoint";
	public static final String NX_K_P_VALUE = "k_p_value";
	public static final String NX_K_I_VALUE = "k_i_value";
	public static final String NX_K_D_VALUE = "k_d_value";
	/**
	 * Description of how the Process Value for the PID controller is produced by sensor(s) in the setup.
	 * For example, a set of sensors could be averaged over before feeding it back into the loop.
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Description of how the Process Value for the PID controller is produced by sensor(s) in the setup.
	 * For example, a set of sensors could be averaged over before feeding it back into the loop.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of how the Process Value for the PID controller is produced by sensor(s) in the setup.
	 * For example, a set of sensors could be averaged over before feeding it back into the loop.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of how the Process Value for the PID controller is produced by sensor(s) in the setup.
	 * For example, a set of sensors could be averaged over before feeding it back into the loop.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * The sensor representing the Process Value used in the feedback loop for the PID.
	 * In case multiple sensors were used, this NXsensor should contain the proper calculated/aggregated value.
	 *
	 * @return  the value.
	 */
	public NXsensor getPv_sensor();

	/**
	 * The sensor representing the Process Value used in the feedback loop for the PID.
	 * In case multiple sensors were used, this NXsensor should contain the proper calculated/aggregated value.
	 *
	 * @param pv_sensorGroup the pv_sensorGroup
	 */
	public void setPv_sensor(NXsensor pv_sensorGroup);
	// Unprocessed group:value_log

	/**
	 * The Setpoint(s) used as an input for the PID controller.
	 * It can also be a link to an NXsensor.value field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSetpoint();

	/**
	 * The Setpoint(s) used as an input for the PID controller.
	 * It can also be a link to an NXsensor.value field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param setpointDataset the setpointDataset
	 */
	public DataNode setSetpoint(IDataset setpointDataset);

	/**
	 * The Setpoint(s) used as an input for the PID controller.
	 * It can also be a link to an NXsensor.value field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSetpointScalar();

	/**
	 * The Setpoint(s) used as an input for the PID controller.
	 * It can also be a link to an NXsensor.value field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param setpoint the setpoint
	 */
	public DataNode setSetpointScalar(Double setpointValue);

	/**
	 * Proportional term. The proportional term produces an output value
	 * that is proportional to the current error value.
	 * The proportional response can be adjusted by multiplying the error
	 * by a constant Kp, called the proportional gain constant.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getK_p_value();

	/**
	 * Proportional term. The proportional term produces an output value
	 * that is proportional to the current error value.
	 * The proportional response can be adjusted by multiplying the error
	 * by a constant Kp, called the proportional gain constant.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_p_valueDataset the k_p_valueDataset
	 */
	public DataNode setK_p_value(IDataset k_p_valueDataset);

	/**
	 * Proportional term. The proportional term produces an output value
	 * that is proportional to the current error value.
	 * The proportional response can be adjusted by multiplying the error
	 * by a constant Kp, called the proportional gain constant.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_p_valueScalar();

	/**
	 * Proportional term. The proportional term produces an output value
	 * that is proportional to the current error value.
	 * The proportional response can be adjusted by multiplying the error
	 * by a constant Kp, called the proportional gain constant.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_p_value the k_p_value
	 */
	public DataNode setK_p_valueScalar(Number k_p_valueValue);

	/**
	 * The contribution from the integral term is proportional to both
	 * the magnitude of the error and the duration of the error.
	 * The integral in a PID controller is the sum of the instantaneous
	 * error over time and gives the accumulated offset that should have
	 * been corrected previously. The accumulated error is then
	 * multiplied by the integral gain (Ki) and added to the
	 * controller output.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getK_i_value();

	/**
	 * The contribution from the integral term is proportional to both
	 * the magnitude of the error and the duration of the error.
	 * The integral in a PID controller is the sum of the instantaneous
	 * error over time and gives the accumulated offset that should have
	 * been corrected previously. The accumulated error is then
	 * multiplied by the integral gain (Ki) and added to the
	 * controller output.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_i_valueDataset the k_i_valueDataset
	 */
	public DataNode setK_i_value(IDataset k_i_valueDataset);

	/**
	 * The contribution from the integral term is proportional to both
	 * the magnitude of the error and the duration of the error.
	 * The integral in a PID controller is the sum of the instantaneous
	 * error over time and gives the accumulated offset that should have
	 * been corrected previously. The accumulated error is then
	 * multiplied by the integral gain (Ki) and added to the
	 * controller output.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_i_valueScalar();

	/**
	 * The contribution from the integral term is proportional to both
	 * the magnitude of the error and the duration of the error.
	 * The integral in a PID controller is the sum of the instantaneous
	 * error over time and gives the accumulated offset that should have
	 * been corrected previously. The accumulated error is then
	 * multiplied by the integral gain (Ki) and added to the
	 * controller output.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_i_value the k_i_value
	 */
	public DataNode setK_i_valueScalar(Number k_i_valueValue);

	/**
	 * The derivative of the process error is calculated by determining
	 * the slope of the error over time and multiplying this rate of
	 * change by the derivative gain K_d. The magnitude of the
	 * contribution of the derivative term to the overall control
	 * action is termed the derivative gain, K_d
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getK_d_value();

	/**
	 * The derivative of the process error is calculated by determining
	 * the slope of the error over time and multiplying this rate of
	 * change by the derivative gain K_d. The magnitude of the
	 * contribution of the derivative term to the overall control
	 * action is termed the derivative gain, K_d
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_d_valueDataset the k_d_valueDataset
	 */
	public DataNode setK_d_value(IDataset k_d_valueDataset);

	/**
	 * The derivative of the process error is calculated by determining
	 * the slope of the error over time and multiplying this rate of
	 * change by the derivative gain K_d. The magnitude of the
	 * contribution of the derivative term to the overall control
	 * action is termed the derivative gain, K_d
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_d_valueScalar();

	/**
	 * The derivative of the process error is calculated by determining
	 * the slope of the error over time and multiplying this rate of
	 * change by the derivative gain K_d. The magnitude of the
	 * contribution of the derivative term to the overall control
	 * action is termed the derivative gain, K_d
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_d_value the k_d_value
	 */
	public DataNode setK_d_valueScalar(Number k_d_valueValue);

}
