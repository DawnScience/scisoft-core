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
 * A description of a feedback system in terms of the settings of a proportional-integral-derivative (PID) controller.
 * Automated control of a physical quantity is often achieved by connecting the output of a sensor to an actuator
 * (e.g. using a thermocouple to monitor the effect of a heater and influence the power provided to it). The physical
 * quantity being operated on is typically referred to as the "Process Variable", with the desired value being the
 * "Setpoint" (which may vary as a function of time) and the "Error Value" is the time-varying function of the difference
 * between the Setpoint value and the concurrent measurement of the Process Variable (Error Value = Setpoint - Process Variable).
 * A PID controller calculates an output value for use as an input signal to an actuator via the weighted sum of four terms:
 * * Proportional: the current Error Value
 * * Integral: the integral of the Error Value function
 * * Derivative: the first derivative of the Error Value function
 * * Feed Forward: A model of the physical system (optional)
 * The weightings of these terms are given by the corresponding constants:
 * * K_p
 * * K_i
 * * K_d
 * * K_ff
 * A classic PID controller only implements the P, I and D terms and the values of the K_p, K_i and K_d constants are sufficient to fully
 * describe the behavior of the feedback system implemented by such a PID controller. The inclusion of a Feed Forward term in a feedback system
 * is a modern adaptation that aids optimization of the automated control. It is not present in all PID controllers, but it is also not uncommon.
 * Note that the ``NXpid_controller`` is designed to be a child object of the actuator that its output is connected to. The parent object
 * representing the actuator is likely to be represented by an ``NXactuator`` or ``NXpositioner`` base class, but there is a wide variety
 * of possible applications for PID controllers.
 *
 */
public interface NXpid_controller extends NXcomponent {

	public static final String NX_SETPOINT = "setpoint";
	public static final String NX_K_P = "k_p";
	public static final String NX_K_I = "k_i";
	public static final String NX_K_D = "k_d";
	public static final String NX_K_FF = "k_ff";
	public static final String NX_FEED_FORWARD_MODEL = "feed_forward_model";
	public static final String NX_CONTROL_ACTION = "control_action";
	/**
	 * Description of how the Process Value for the PID controller is produced by sensor(s) in the setup.
	 * For example, a set of sensors could be averaged over before feeding it back into the loop.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

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
	 * It can also be a link to an ``NXsensor.value`` field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSetpoint();

	/**
	 * The Setpoint(s) used as an input for the PID controller.
	 * It can also be a link to an ``NXsensor.value`` field.
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
	 * It can also be a link to an ``NXsensor.value`` field.
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
	 * It can also be a link to an ``NXsensor.value`` field.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param setpoint the setpoint
	 */
	public DataNode setSetpointScalar(Double setpointValue);

	/**
	 * Proportional gain constant. This constant determines how strongly the output value
	 * directly follows the current Error Value. When this constant dominates, the output
	 * value is linearly proportional to the Error Value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getK_p();

	/**
	 * Proportional gain constant. This constant determines how strongly the output value
	 * directly follows the current Error Value. When this constant dominates, the output
	 * value is linearly proportional to the Error Value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_pDataset the k_pDataset
	 */
	public DataNode setK_p(IDataset k_pDataset);

	/**
	 * Proportional gain constant. This constant determines how strongly the output value
	 * directly follows the current Error Value. When this constant dominates, the output
	 * value is linearly proportional to the Error Value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_pScalar();

	/**
	 * Proportional gain constant. This constant determines how strongly the output value
	 * directly follows the current Error Value. When this constant dominates, the output
	 * value is linearly proportional to the Error Value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_p the k_p
	 */
	public DataNode setK_pScalar(Number k_pValue);

	/**
	 * Integral gain constant. This constant determines how strongly the output value
	 * should react to an accumulated offset in the Error Value that should have
	 * been corrected previously. since the integral term is proportional to both
	 * the magnitude and persistence of the Error Value over time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getK_i();

	/**
	 * Integral gain constant. This constant determines how strongly the output value
	 * should react to an accumulated offset in the Error Value that should have
	 * been corrected previously. since the integral term is proportional to both
	 * the magnitude and persistence of the Error Value over time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_iDataset the k_iDataset
	 */
	public DataNode setK_i(IDataset k_iDataset);

	/**
	 * Integral gain constant. This constant determines how strongly the output value
	 * should react to an accumulated offset in the Error Value that should have
	 * been corrected previously. since the integral term is proportional to both
	 * the magnitude and persistence of the Error Value over time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_iScalar();

	/**
	 * Integral gain constant. This constant determines how strongly the output value
	 * should react to an accumulated offset in the Error Value that should have
	 * been corrected previously. since the integral term is proportional to both
	 * the magnitude and persistence of the Error Value over time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_i the k_i
	 */
	public DataNode setK_iScalar(Number k_iValue);

	/**
	 * Derivative gain constant. This constant determines how much the feedback system
	 * should anticipate the future value of the Error Value function through adjustment of the
	 * output value that is proportional to the rate of change (i.e. derivative) of the Error Value.
	 * This term is important for damping oscillations in the feedback system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getK_d();

	/**
	 * Derivative gain constant. This constant determines how much the feedback system
	 * should anticipate the future value of the Error Value function through adjustment of the
	 * output value that is proportional to the rate of change (i.e. derivative) of the Error Value.
	 * This term is important for damping oscillations in the feedback system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_dDataset the k_dDataset
	 */
	public DataNode setK_d(IDataset k_dDataset);

	/**
	 * Derivative gain constant. This constant determines how much the feedback system
	 * should anticipate the future value of the Error Value function through adjustment of the
	 * output value that is proportional to the rate of change (i.e. derivative) of the Error Value.
	 * This term is important for damping oscillations in the feedback system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_dScalar();

	/**
	 * Derivative gain constant. This constant determines how much the feedback system
	 * should anticipate the future value of the Error Value function through adjustment of the
	 * output value that is proportional to the rate of change (i.e. derivative) of the Error Value.
	 * This term is important for damping oscillations in the feedback system.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_d the k_d
	 */
	public DataNode setK_dScalar(Number k_dValue);

	/**
	 * Feed Forward gain constant. This constant determines how much the feedback system
	 * should rely on a calculated output value to achieve the desired Process Variable value.
	 * A Feed Forward system uses a model of the physical system to calculate an appropriate
	 * output value to achieve a desired Setpoint value. A description of this model should be provided
	 * in the ``feed_forward_model`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getK_ff();

	/**
	 * Feed Forward gain constant. This constant determines how much the feedback system
	 * should rely on a calculated output value to achieve the desired Process Variable value.
	 * A Feed Forward system uses a model of the physical system to calculate an appropriate
	 * output value to achieve a desired Setpoint value. A description of this model should be provided
	 * in the ``feed_forward_model`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_ffDataset the k_ffDataset
	 */
	public DataNode setK_ff(IDataset k_ffDataset);

	/**
	 * Feed Forward gain constant. This constant determines how much the feedback system
	 * should rely on a calculated output value to achieve the desired Process Variable value.
	 * A Feed Forward system uses a model of the physical system to calculate an appropriate
	 * output value to achieve a desired Setpoint value. A description of this model should be provided
	 * in the ``feed_forward_model`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getK_ffScalar();

	/**
	 * Feed Forward gain constant. This constant determines how much the feedback system
	 * should rely on a calculated output value to achieve the desired Process Variable value.
	 * A Feed Forward system uses a model of the physical system to calculate an appropriate
	 * output value to achieve a desired Setpoint value. A description of this model should be provided
	 * in the ``feed_forward_model`` field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param k_ff the k_ff
	 */
	public DataNode setK_ffScalar(Number k_ffValue);

	/**
	 * A description of the model used for the Feed Forward part of the feedback system. Note that such models typically
	 * involve the Setpoint value, but not the Error Value. The simplest model is simply proportional to the Setpoint value.
	 * For example, the position (Process Variable) of a sample is measured by a a linear optical encoder (sensor) and
	 * manipulated by a piezoelectric scanning stage (actuator). The corresponding Feed Forward model could be that the
	 * output value (voltage applied to the piezo) is proportional to the Setpoint value (measured position of the sample).
	 * A complex model could involve any number of input variables, mathematical functions, and coefficients in order to
	 * describe the physical system relevant to the PID controller.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFeed_forward_model();

	/**
	 * A description of the model used for the Feed Forward part of the feedback system. Note that such models typically
	 * involve the Setpoint value, but not the Error Value. The simplest model is simply proportional to the Setpoint value.
	 * For example, the position (Process Variable) of a sample is measured by a a linear optical encoder (sensor) and
	 * manipulated by a piezoelectric scanning stage (actuator). The corresponding Feed Forward model could be that the
	 * output value (voltage applied to the piezo) is proportional to the Setpoint value (measured position of the sample).
	 * A complex model could involve any number of input variables, mathematical functions, and coefficients in order to
	 * describe the physical system relevant to the PID controller.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param feed_forward_modelDataset the feed_forward_modelDataset
	 */
	public DataNode setFeed_forward_model(IDataset feed_forward_modelDataset);

	/**
	 * A description of the model used for the Feed Forward part of the feedback system. Note that such models typically
	 * involve the Setpoint value, but not the Error Value. The simplest model is simply proportional to the Setpoint value.
	 * For example, the position (Process Variable) of a sample is measured by a a linear optical encoder (sensor) and
	 * manipulated by a piezoelectric scanning stage (actuator). The corresponding Feed Forward model could be that the
	 * output value (voltage applied to the piezo) is proportional to the Setpoint value (measured position of the sample).
	 * A complex model could involve any number of input variables, mathematical functions, and coefficients in order to
	 * describe the physical system relevant to the PID controller.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getFeed_forward_modelScalar();

	/**
	 * A description of the model used for the Feed Forward part of the feedback system. Note that such models typically
	 * involve the Setpoint value, but not the Error Value. The simplest model is simply proportional to the Setpoint value.
	 * For example, the position (Process Variable) of a sample is measured by a a linear optical encoder (sensor) and
	 * manipulated by a piezoelectric scanning stage (actuator). The corresponding Feed Forward model could be that the
	 * output value (voltage applied to the piezo) is proportional to the Setpoint value (measured position of the sample).
	 * A complex model could involve any number of input variables, mathematical functions, and coefficients in order to
	 * describe the physical system relevant to the PID controller.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param feed_forward_model the feed_forward_model
	 */
	public DataNode setFeed_forward_modelScalar(String feed_forward_modelValue);

	/**
	 * The Error Value of PID feedback system is normally constructed in terms of the correction needed to bring
	 * the Process Variable towards a match with the Setpoint. This "direct" control action means that a measurement of
	 * the Process Variable that is lower than the Setpoint results in a positive Error Value and a generally positive
	 * control output that tells the actuator to push the value of the Process Variable upwards. In some implementations,
	 * the actuator will respond to a more positive control output by pushing the Process Variable towards lower values (e.g.
	 * a Peltier cooler) and so the output of the feedback system must be reversed to match the behavior of the physical system.
	 * A feedback system may also be implemented with reverse action in order to ensure that failures (e.g. disconnected sensor
	 * output or actuator input) result in a safe state (e.g. a valve should be left open to release pressure).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>direct</b> </li>
	 * <li><b>reverse</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getControl_action();

	/**
	 * The Error Value of PID feedback system is normally constructed in terms of the correction needed to bring
	 * the Process Variable towards a match with the Setpoint. This "direct" control action means that a measurement of
	 * the Process Variable that is lower than the Setpoint results in a positive Error Value and a generally positive
	 * control output that tells the actuator to push the value of the Process Variable upwards. In some implementations,
	 * the actuator will respond to a more positive control output by pushing the Process Variable towards lower values (e.g.
	 * a Peltier cooler) and so the output of the feedback system must be reversed to match the behavior of the physical system.
	 * A feedback system may also be implemented with reverse action in order to ensure that failures (e.g. disconnected sensor
	 * output or actuator input) result in a safe state (e.g. a valve should be left open to release pressure).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>direct</b> </li>
	 * <li><b>reverse</b> </li></ul></p>
	 * </p>
	 *
	 * @param control_actionDataset the control_actionDataset
	 */
	public DataNode setControl_action(IDataset control_actionDataset);

	/**
	 * The Error Value of PID feedback system is normally constructed in terms of the correction needed to bring
	 * the Process Variable towards a match with the Setpoint. This "direct" control action means that a measurement of
	 * the Process Variable that is lower than the Setpoint results in a positive Error Value and a generally positive
	 * control output that tells the actuator to push the value of the Process Variable upwards. In some implementations,
	 * the actuator will respond to a more positive control output by pushing the Process Variable towards lower values (e.g.
	 * a Peltier cooler) and so the output of the feedback system must be reversed to match the behavior of the physical system.
	 * A feedback system may also be implemented with reverse action in order to ensure that failures (e.g. disconnected sensor
	 * output or actuator input) result in a safe state (e.g. a valve should be left open to release pressure).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>direct</b> </li>
	 * <li><b>reverse</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getControl_actionScalar();

	/**
	 * The Error Value of PID feedback system is normally constructed in terms of the correction needed to bring
	 * the Process Variable towards a match with the Setpoint. This "direct" control action means that a measurement of
	 * the Process Variable that is lower than the Setpoint results in a positive Error Value and a generally positive
	 * control output that tells the actuator to push the value of the Process Variable upwards. In some implementations,
	 * the actuator will respond to a more positive control output by pushing the Process Variable towards lower values (e.g.
	 * a Peltier cooler) and so the output of the feedback system must be reversed to match the behavior of the physical system.
	 * A feedback system may also be implemented with reverse action in order to ensure that failures (e.g. disconnected sensor
	 * output or actuator input) result in a safe state (e.g. a valve should be left open to release pressure).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>direct</b> </li>
	 * <li><b>reverse</b> </li></ul></p>
	 * </p>
	 *
	 * @param control_action the control_action
	 */
	public DataNode setControl_actionScalar(String control_actionValue);

}
