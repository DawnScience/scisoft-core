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
 * Description of a fit procedure using a scalar valued global function
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>dimRank</b>
 * Rank of the dependent and independent data arrays (for
 * multivariate scalar-valued fit.)</li></ul></p>
 *
 */
public interface NXfit extends NXprocess {

	public static final String NX_LABEL = "label";
	public static final String NX_FIGURE_OF_MERITMETRIC = "figure_of_meritmetric";
	public static final String NX_FIGURE_OF_MERITMETRIC_ATTRIBUTE_METRIC = "metric";
	/**
	 * Human-readable label for this fit procedure.
	 *
	 * @return  the value.
	 */
	public Dataset getLabel();

	/**
	 * Human-readable label for this fit procedure.
	 *
	 * @param labelDataset the labelDataset
	 */
	public DataNode setLabel(IDataset labelDataset);

	/**
	 * Human-readable label for this fit procedure.
	 *
	 * @return  the value.
	 */
	public String getLabelScalar();

	/**
	 * Human-readable label for this fit procedure.
	 *
	 * @param label the label
	 */
	public DataNode setLabelScalar(String labelValue);

	/**
	 * Data and results of the fit.
	 *
	 * @return  the value.
	 */
	public NXdata getData();

	/**
	 * Data and results of the fit.
	 *
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * An instance of the peak model.
	 * If there is no characteristic name for each peak component, the peaks could be
	 * labeled as peak_0, peak_1, and so on.
	 *
	 * @return  the value.
	 */
	public NXpeak getPeakpeak();

	/**
	 * An instance of the peak model.
	 * If there is no characteristic name for each peak component, the peaks could be
	 * labeled as peak_0, peak_1, and so on.
	 *
	 * @param peakpeakGroup the peakpeakGroup
	 */
	public void setPeakpeak(NXpeak peakpeakGroup);

	/**
	 * One fitted background (functional form, position (see :ref:`data/input_independent </NXfit/data/input_independent-field>`),
	 * and intensities) of the peak fit.
	 * If there is no characteristic name for each background component, it is envisioned that backgrounds are labeled
	 * as background_0, background_1, and so on.
	 *
	 * @return  the value.
	 */
	public NXpeak getBackgroundbackground();

	/**
	 * One fitted background (functional form, position (see :ref:`data/input_independent </NXfit/data/input_independent-field>`),
	 * and intensities) of the peak fit.
	 * If there is no characteristic name for each background component, it is envisioned that backgrounds are labeled
	 * as background_0, background_1, and so on.
	 *
	 * @param backgroundbackgroundGroup the backgroundbackgroundGroup
	 */
	public void setBackgroundbackground(NXpeak backgroundbackgroundGroup);

	/**
	 * Function used to describe the overall fit to the data, taking into account the parameters of the
	 * individual :ref:`NXpeak` components.
	 *
	 * @return  the value.
	 */
	public NXfit_function getGlobal_fit_function();

	/**
	 * Function used to describe the overall fit to the data, taking into account the parameters of the
	 * individual :ref:`NXpeak` components.
	 *
	 * @param global_fit_functionGroup the global_fit_functionGroup
	 */
	public void setGlobal_fit_function(NXfit_function global_fit_functionGroup);

	/**
	 * Function used to optimize the parameters during peak fitting.
	 *
	 * @return  the value.
	 */
	public NXfit_function getError_function();

	/**
	 * Function used to optimize the parameters during peak fitting.
	 *
	 * @param error_functionGroup the error_functionGroup
	 */
	public void setError_function(NXfit_function error_functionGroup);

	/**
	 * Figure-of-merit to determine the goodness of fit, i.e., how well the fit model (i.e., the set of peaks and backgrounds)
	 * fits the measured observations.
	 * This value (which is a single number) is often used to guide adjustments to the
	 * fitting parameters in the peak fitting process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFigure_of_meritmetric();

	/**
	 * Figure-of-merit to determine the goodness of fit, i.e., how well the fit model (i.e., the set of peaks and backgrounds)
	 * fits the measured observations.
	 * This value (which is a single number) is often used to guide adjustments to the
	 * fitting parameters in the peak fitting process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param figure_of_meritmetricDataset the figure_of_meritmetricDataset
	 */
	public DataNode setFigure_of_meritmetric(IDataset figure_of_meritmetricDataset);

	/**
	 * Figure-of-merit to determine the goodness of fit, i.e., how well the fit model (i.e., the set of peaks and backgrounds)
	 * fits the measured observations.
	 * This value (which is a single number) is often used to guide adjustments to the
	 * fitting parameters in the peak fitting process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFigure_of_meritmetricScalar();

	/**
	 * Figure-of-merit to determine the goodness of fit, i.e., how well the fit model (i.e., the set of peaks and backgrounds)
	 * fits the measured observations.
	 * This value (which is a single number) is often used to guide adjustments to the
	 * fitting parameters in the peak fitting process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param figure_of_meritmetric the figure_of_meritmetric
	 */
	public DataNode setFigure_of_meritmetricScalar(Number figure_of_meritmetricValue);

	/**
	 * Metric used to determine the goodness of fit. Examples include:
	 * - :math:`\chi^2`, the squared sum of the sigma-weighted residuals
	 * - reduced :math:`\chi^2`:, :math:`\chi^2`: per degree of freedom
	 * - :math:`R^2`, the coefficient of determination
	 *
	 * @return  the value.
	 */
	public String getFigure_of_meritmetricAttributeMetric();

	/**
	 * Metric used to determine the goodness of fit. Examples include:
	 * - :math:`\chi^2`, the squared sum of the sigma-weighted residuals
	 * - reduced :math:`\chi^2`:, :math:`\chi^2`: per degree of freedom
	 * - :math:`R^2`, the coefficient of determination
	 *
	 * @param metricValue the metricValue
	 */
	public void setFigure_of_meritmetricAttributeMetric(String metricValue);

}
