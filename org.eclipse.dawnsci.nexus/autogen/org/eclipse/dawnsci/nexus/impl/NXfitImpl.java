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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Description of a fit procedure using a scalar valued global function

 */
public class NXfitImpl extends NXprocessImpl implements NXfit {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_PEAK,
		NexusBaseClass.NX_PEAK,
		NexusBaseClass.NX_FIT_FUNCTION,
		NexusBaseClass.NX_FIT_FUNCTION);

	public NXfitImpl() {
		super();
	}

	public NXfitImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfit.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FIT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getLabel() {
		return getDataset(NX_LABEL);
	}

	@Override
	public String getLabelScalar() {
		return getString(NX_LABEL);
	}

	@Override
	public DataNode setLabel(IDataset labelDataset) {
		return setDataset(NX_LABEL, labelDataset);
	}

	@Override
	public DataNode setLabelScalar(String labelValue) {
		return setString(NX_LABEL, labelValue);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXpeak getPeakpeak() {
		// dataNodeName = NX_PEAKPEAK
		return getChild("peakpeak", NXpeak.class);
	}

	@Override
	public void setPeakpeak(NXpeak peakpeakGroup) {
		putChild("peakpeak", peakpeakGroup);
	}

	@Override
	public NXpeak getBackgroundbackground() {
		// dataNodeName = NX_BACKGROUNDBACKGROUND
		return getChild("backgroundbackground", NXpeak.class);
	}

	@Override
	public void setBackgroundbackground(NXpeak backgroundbackgroundGroup) {
		putChild("backgroundbackground", backgroundbackgroundGroup);
	}

	@Override
	public NXfit_function getGlobal_fit_function() {
		// dataNodeName = NX_GLOBAL_FIT_FUNCTION
		return getChild("global_fit_function", NXfit_function.class);
	}

	@Override
	public void setGlobal_fit_function(NXfit_function global_fit_functionGroup) {
		putChild("global_fit_function", global_fit_functionGroup);
	}

	@Override
	public NXfit_function getError_function() {
		// dataNodeName = NX_ERROR_FUNCTION
		return getChild("error_function", NXfit_function.class);
	}

	@Override
	public void setError_function(NXfit_function error_functionGroup) {
		putChild("error_function", error_functionGroup);
	}

	@Override
	public Dataset getFigure_of_meritmetric() {
		return getDataset(NX_FIGURE_OF_MERITMETRIC);
	}

	@Override
	public Number getFigure_of_meritmetricScalar() {
		return getNumber(NX_FIGURE_OF_MERITMETRIC);
	}

	@Override
	public DataNode setFigure_of_meritmetric(IDataset figure_of_meritmetricDataset) {
		return setDataset(NX_FIGURE_OF_MERITMETRIC, figure_of_meritmetricDataset);
	}

	@Override
	public DataNode setFigure_of_meritmetricScalar(Number figure_of_meritmetricValue) {
		return setField(NX_FIGURE_OF_MERITMETRIC, figure_of_meritmetricValue);
	}

	@Override
	public String getFigure_of_meritmetricAttributeMetric() {
		return getAttrString(NX_FIGURE_OF_MERITMETRIC, NX_FIGURE_OF_MERITMETRIC_ATTRIBUTE_METRIC);
	}

	@Override
	public void setFigure_of_meritmetricAttributeMetric(String metricValue) {
		setAttribute(NX_FIGURE_OF_MERITMETRIC, NX_FIGURE_OF_MERITMETRIC_ATTRIBUTE_METRIC, metricValue);
	}

}
