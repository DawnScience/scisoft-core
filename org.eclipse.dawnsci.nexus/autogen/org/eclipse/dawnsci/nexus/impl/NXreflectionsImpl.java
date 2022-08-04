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

import org.eclipse.dawnsci.nexus.*;

/**
 * Reflection data from diffraction experiments
 * 
 */
public class NXreflectionsImpl extends NXobjectImpl implements NXreflections {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXreflectionsImpl() {
		super();
	}

	public NXreflectionsImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXreflections.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_REFLECTIONS;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getExperiments() {
		return getDataset(NX_EXPERIMENTS);
	}

	@Override
	public String getExperimentsScalar() {
		return getString(NX_EXPERIMENTS);
	}

	@Override
	public DataNode setExperiments(IDataset experimentsDataset) {
		return setDataset(NX_EXPERIMENTS, experimentsDataset);
	}

	@Override
	public DataNode setExperimentsScalar(String experimentsValue) {
		return setString(NX_EXPERIMENTS, experimentsValue);
	}

	@Override
	public IDataset getH() {
		return getDataset(NX_H);
	}

	@Override
	public Number getHScalar() {
		return getNumber(NX_H);
	}

	@Override
	public DataNode setH(IDataset hDataset) {
		return setDataset(NX_H, hDataset);
	}

	@Override
	public DataNode setHScalar(Number hValue) {
		return setField(NX_H, hValue);
	}

	@Override
	public String getHAttributeDescription() {
		return getAttrString(NX_H, NX_H_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setHAttributeDescription(String descriptionValue) {
		setAttribute(NX_H, NX_H_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getK() {
		return getDataset(NX_K);
	}

	@Override
	public Number getKScalar() {
		return getNumber(NX_K);
	}

	@Override
	public DataNode setK(IDataset kDataset) {
		return setDataset(NX_K, kDataset);
	}

	@Override
	public DataNode setKScalar(Number kValue) {
		return setField(NX_K, kValue);
	}

	@Override
	public String getKAttributeDescription() {
		return getAttrString(NX_K, NX_K_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setKAttributeDescription(String descriptionValue) {
		setAttribute(NX_K, NX_K_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getL() {
		return getDataset(NX_L);
	}

	@Override
	public Number getLScalar() {
		return getNumber(NX_L);
	}

	@Override
	public DataNode setL(IDataset lDataset) {
		return setDataset(NX_L, lDataset);
	}

	@Override
	public DataNode setLScalar(Number lValue) {
		return setField(NX_L, lValue);
	}

	@Override
	public String getLAttributeDescription() {
		return getAttrString(NX_L, NX_L_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setLAttributeDescription(String descriptionValue) {
		setAttribute(NX_L, NX_L_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getId() {
		return getDataset(NX_ID);
	}

	@Override
	public Long getIdScalar() {
		return getLong(NX_ID);
	}

	@Override
	public DataNode setId(IDataset idDataset) {
		return setDataset(NX_ID, idDataset);
	}

	@Override
	public DataNode setIdScalar(Long idValue) {
		return setField(NX_ID, idValue);
	}

	@Override
	public String getIdAttributeDescription() {
		return getAttrString(NX_ID, NX_ID_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setIdAttributeDescription(String descriptionValue) {
		setAttribute(NX_ID, NX_ID_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getReflection_id() {
		return getDataset(NX_REFLECTION_ID);
	}

	@Override
	public Long getReflection_idScalar() {
		return getLong(NX_REFLECTION_ID);
	}

	@Override
	public DataNode setReflection_id(IDataset reflection_idDataset) {
		return setDataset(NX_REFLECTION_ID, reflection_idDataset);
	}

	@Override
	public DataNode setReflection_idScalar(Long reflection_idValue) {
		return setField(NX_REFLECTION_ID, reflection_idValue);
	}

	@Override
	public String getReflection_idAttributeDescription() {
		return getAttrString(NX_REFLECTION_ID, NX_REFLECTION_ID_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setReflection_idAttributeDescription(String descriptionValue) {
		setAttribute(NX_REFLECTION_ID, NX_REFLECTION_ID_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getEntering() {
		return getDataset(NX_ENTERING);
	}

	@Override
	public Boolean getEnteringScalar() {
		return getBoolean(NX_ENTERING);
	}

	@Override
	public DataNode setEntering(IDataset enteringDataset) {
		return setDataset(NX_ENTERING, enteringDataset);
	}

	@Override
	public DataNode setEnteringScalar(Boolean enteringValue) {
		return setField(NX_ENTERING, enteringValue);
	}

	@Override
	public String getEnteringAttributeDescription() {
		return getAttrString(NX_ENTERING, NX_ENTERING_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setEnteringAttributeDescription(String descriptionValue) {
		setAttribute(NX_ENTERING, NX_ENTERING_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getDet_module() {
		return getDataset(NX_DET_MODULE);
	}

	@Override
	public Long getDet_moduleScalar() {
		return getLong(NX_DET_MODULE);
	}

	@Override
	public DataNode setDet_module(IDataset det_moduleDataset) {
		return setDataset(NX_DET_MODULE, det_moduleDataset);
	}

	@Override
	public DataNode setDet_moduleScalar(Long det_moduleValue) {
		return setField(NX_DET_MODULE, det_moduleValue);
	}

	@Override
	public String getDet_moduleAttributeDescription() {
		return getAttrString(NX_DET_MODULE, NX_DET_MODULE_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setDet_moduleAttributeDescription(String descriptionValue) {
		setAttribute(NX_DET_MODULE, NX_DET_MODULE_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getFlags() {
		return getDataset(NX_FLAGS);
	}

	@Override
	public Long getFlagsScalar() {
		return getLong(NX_FLAGS);
	}

	@Override
	public DataNode setFlags(IDataset flagsDataset) {
		return setDataset(NX_FLAGS, flagsDataset);
	}

	@Override
	public DataNode setFlagsScalar(Long flagsValue) {
		return setField(NX_FLAGS, flagsValue);
	}

	@Override
	public String getFlagsAttributeDescription() {
		return getAttrString(NX_FLAGS, NX_FLAGS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setFlagsAttributeDescription(String descriptionValue) {
		setAttribute(NX_FLAGS, NX_FLAGS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getD() {
		return getDataset(NX_D);
	}

	@Override
	public Double getDScalar() {
		return getDouble(NX_D);
	}

	@Override
	public DataNode setD(IDataset dDataset) {
		return setDataset(NX_D, dDataset);
	}

	@Override
	public DataNode setDScalar(Double dValue) {
		return setField(NX_D, dValue);
	}

	@Override
	public String getDAttributeDescription() {
		return getAttrString(NX_D, NX_D_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setDAttributeDescription(String descriptionValue) {
		setAttribute(NX_D, NX_D_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPartiality() {
		return getDataset(NX_PARTIALITY);
	}

	@Override
	public Double getPartialityScalar() {
		return getDouble(NX_PARTIALITY);
	}

	@Override
	public DataNode setPartiality(IDataset partialityDataset) {
		return setDataset(NX_PARTIALITY, partialityDataset);
	}

	@Override
	public DataNode setPartialityScalar(Double partialityValue) {
		return setField(NX_PARTIALITY, partialityValue);
	}

	@Override
	public String getPartialityAttributeDescription() {
		return getAttrString(NX_PARTIALITY, NX_PARTIALITY_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPartialityAttributeDescription(String descriptionValue) {
		setAttribute(NX_PARTIALITY, NX_PARTIALITY_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_frame() {
		return getDataset(NX_PREDICTED_FRAME);
	}

	@Override
	public Double getPredicted_frameScalar() {
		return getDouble(NX_PREDICTED_FRAME);
	}

	@Override
	public DataNode setPredicted_frame(IDataset predicted_frameDataset) {
		return setDataset(NX_PREDICTED_FRAME, predicted_frameDataset);
	}

	@Override
	public DataNode setPredicted_frameScalar(Double predicted_frameValue) {
		return setField(NX_PREDICTED_FRAME, predicted_frameValue);
	}

	@Override
	public String getPredicted_frameAttributeDescription() {
		return getAttrString(NX_PREDICTED_FRAME, NX_PREDICTED_FRAME_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_frameAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_FRAME, NX_PREDICTED_FRAME_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_x() {
		return getDataset(NX_PREDICTED_X);
	}

	@Override
	public Double getPredicted_xScalar() {
		return getDouble(NX_PREDICTED_X);
	}

	@Override
	public DataNode setPredicted_x(IDataset predicted_xDataset) {
		return setDataset(NX_PREDICTED_X, predicted_xDataset);
	}

	@Override
	public DataNode setPredicted_xScalar(Double predicted_xValue) {
		return setField(NX_PREDICTED_X, predicted_xValue);
	}

	@Override
	public String getPredicted_xAttributeDescription() {
		return getAttrString(NX_PREDICTED_X, NX_PREDICTED_X_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_xAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_X, NX_PREDICTED_X_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_y() {
		return getDataset(NX_PREDICTED_Y);
	}

	@Override
	public Double getPredicted_yScalar() {
		return getDouble(NX_PREDICTED_Y);
	}

	@Override
	public DataNode setPredicted_y(IDataset predicted_yDataset) {
		return setDataset(NX_PREDICTED_Y, predicted_yDataset);
	}

	@Override
	public DataNode setPredicted_yScalar(Double predicted_yValue) {
		return setField(NX_PREDICTED_Y, predicted_yValue);
	}

	@Override
	public String getPredicted_yAttributeDescription() {
		return getAttrString(NX_PREDICTED_Y, NX_PREDICTED_Y_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_yAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_Y, NX_PREDICTED_Y_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_phi() {
		return getDataset(NX_PREDICTED_PHI);
	}

	@Override
	public Double getPredicted_phiScalar() {
		return getDouble(NX_PREDICTED_PHI);
	}

	@Override
	public DataNode setPredicted_phi(IDataset predicted_phiDataset) {
		return setDataset(NX_PREDICTED_PHI, predicted_phiDataset);
	}

	@Override
	public DataNode setPredicted_phiScalar(Double predicted_phiValue) {
		return setField(NX_PREDICTED_PHI, predicted_phiValue);
	}

	@Override
	public String getPredicted_phiAttributeDescription() {
		return getAttrString(NX_PREDICTED_PHI, NX_PREDICTED_PHI_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_phiAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_PHI, NX_PREDICTED_PHI_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_px_x() {
		return getDataset(NX_PREDICTED_PX_X);
	}

	@Override
	public Double getPredicted_px_xScalar() {
		return getDouble(NX_PREDICTED_PX_X);
	}

	@Override
	public DataNode setPredicted_px_x(IDataset predicted_px_xDataset) {
		return setDataset(NX_PREDICTED_PX_X, predicted_px_xDataset);
	}

	@Override
	public DataNode setPredicted_px_xScalar(Double predicted_px_xValue) {
		return setField(NX_PREDICTED_PX_X, predicted_px_xValue);
	}

	@Override
	public String getPredicted_px_xAttributeDescription() {
		return getAttrString(NX_PREDICTED_PX_X, NX_PREDICTED_PX_X_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_px_xAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_PX_X, NX_PREDICTED_PX_X_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPredicted_px_y() {
		return getDataset(NX_PREDICTED_PX_Y);
	}

	@Override
	public Double getPredicted_px_yScalar() {
		return getDouble(NX_PREDICTED_PX_Y);
	}

	@Override
	public DataNode setPredicted_px_y(IDataset predicted_px_yDataset) {
		return setDataset(NX_PREDICTED_PX_Y, predicted_px_yDataset);
	}

	@Override
	public DataNode setPredicted_px_yScalar(Double predicted_px_yValue) {
		return setField(NX_PREDICTED_PX_Y, predicted_px_yValue);
	}

	@Override
	public String getPredicted_px_yAttributeDescription() {
		return getAttrString(NX_PREDICTED_PX_Y, NX_PREDICTED_PX_Y_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPredicted_px_yAttributeDescription(String descriptionValue) {
		setAttribute(NX_PREDICTED_PX_Y, NX_PREDICTED_PX_Y_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_frame() {
		return getDataset(NX_OBSERVED_FRAME);
	}

	@Override
	public Double getObserved_frameScalar() {
		return getDouble(NX_OBSERVED_FRAME);
	}

	@Override
	public DataNode setObserved_frame(IDataset observed_frameDataset) {
		return setDataset(NX_OBSERVED_FRAME, observed_frameDataset);
	}

	@Override
	public DataNode setObserved_frameScalar(Double observed_frameValue) {
		return setField(NX_OBSERVED_FRAME, observed_frameValue);
	}

	@Override
	public String getObserved_frameAttributeDescription() {
		return getAttrString(NX_OBSERVED_FRAME, NX_OBSERVED_FRAME_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_frameAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_FRAME, NX_OBSERVED_FRAME_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_frame_var() {
		return getDataset(NX_OBSERVED_FRAME_VAR);
	}

	@Override
	public Double getObserved_frame_varScalar() {
		return getDouble(NX_OBSERVED_FRAME_VAR);
	}

	@Override
	public DataNode setObserved_frame_var(IDataset observed_frame_varDataset) {
		return setDataset(NX_OBSERVED_FRAME_VAR, observed_frame_varDataset);
	}

	@Override
	public DataNode setObserved_frame_varScalar(Double observed_frame_varValue) {
		return setField(NX_OBSERVED_FRAME_VAR, observed_frame_varValue);
	}

	@Override
	public String getObserved_frame_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_FRAME_VAR, NX_OBSERVED_FRAME_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_frame_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_FRAME_VAR, NX_OBSERVED_FRAME_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_frame_errors() {
		return getDataset(NX_OBSERVED_FRAME_ERRORS);
	}

	@Override
	public Double getObserved_frame_errorsScalar() {
		return getDouble(NX_OBSERVED_FRAME_ERRORS);
	}

	@Override
	public DataNode setObserved_frame_errors(IDataset observed_frame_errorsDataset) {
		return setDataset(NX_OBSERVED_FRAME_ERRORS, observed_frame_errorsDataset);
	}

	@Override
	public DataNode setObserved_frame_errorsScalar(Double observed_frame_errorsValue) {
		return setField(NX_OBSERVED_FRAME_ERRORS, observed_frame_errorsValue);
	}

	@Override
	public String getObserved_frame_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_FRAME_ERRORS, NX_OBSERVED_FRAME_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_frame_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_FRAME_ERRORS, NX_OBSERVED_FRAME_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_x() {
		return getDataset(NX_OBSERVED_PX_X);
	}

	@Override
	public Double getObserved_px_xScalar() {
		return getDouble(NX_OBSERVED_PX_X);
	}

	@Override
	public DataNode setObserved_px_x(IDataset observed_px_xDataset) {
		return setDataset(NX_OBSERVED_PX_X, observed_px_xDataset);
	}

	@Override
	public DataNode setObserved_px_xScalar(Double observed_px_xValue) {
		return setField(NX_OBSERVED_PX_X, observed_px_xValue);
	}

	@Override
	public String getObserved_px_xAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_X, NX_OBSERVED_PX_X_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_xAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_X, NX_OBSERVED_PX_X_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_x_var() {
		return getDataset(NX_OBSERVED_PX_X_VAR);
	}

	@Override
	public Double getObserved_px_x_varScalar() {
		return getDouble(NX_OBSERVED_PX_X_VAR);
	}

	@Override
	public DataNode setObserved_px_x_var(IDataset observed_px_x_varDataset) {
		return setDataset(NX_OBSERVED_PX_X_VAR, observed_px_x_varDataset);
	}

	@Override
	public DataNode setObserved_px_x_varScalar(Double observed_px_x_varValue) {
		return setField(NX_OBSERVED_PX_X_VAR, observed_px_x_varValue);
	}

	@Override
	public String getObserved_px_x_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_X_VAR, NX_OBSERVED_PX_X_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_x_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_X_VAR, NX_OBSERVED_PX_X_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_x_errors() {
		return getDataset(NX_OBSERVED_PX_X_ERRORS);
	}

	@Override
	public Double getObserved_px_x_errorsScalar() {
		return getDouble(NX_OBSERVED_PX_X_ERRORS);
	}

	@Override
	public DataNode setObserved_px_x_errors(IDataset observed_px_x_errorsDataset) {
		return setDataset(NX_OBSERVED_PX_X_ERRORS, observed_px_x_errorsDataset);
	}

	@Override
	public DataNode setObserved_px_x_errorsScalar(Double observed_px_x_errorsValue) {
		return setField(NX_OBSERVED_PX_X_ERRORS, observed_px_x_errorsValue);
	}

	@Override
	public String getObserved_px_x_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_X_ERRORS, NX_OBSERVED_PX_X_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_x_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_X_ERRORS, NX_OBSERVED_PX_X_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_y() {
		return getDataset(NX_OBSERVED_PX_Y);
	}

	@Override
	public Double getObserved_px_yScalar() {
		return getDouble(NX_OBSERVED_PX_Y);
	}

	@Override
	public DataNode setObserved_px_y(IDataset observed_px_yDataset) {
		return setDataset(NX_OBSERVED_PX_Y, observed_px_yDataset);
	}

	@Override
	public DataNode setObserved_px_yScalar(Double observed_px_yValue) {
		return setField(NX_OBSERVED_PX_Y, observed_px_yValue);
	}

	@Override
	public String getObserved_px_yAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_Y, NX_OBSERVED_PX_Y_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_yAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_Y, NX_OBSERVED_PX_Y_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_y_var() {
		return getDataset(NX_OBSERVED_PX_Y_VAR);
	}

	@Override
	public Double getObserved_px_y_varScalar() {
		return getDouble(NX_OBSERVED_PX_Y_VAR);
	}

	@Override
	public DataNode setObserved_px_y_var(IDataset observed_px_y_varDataset) {
		return setDataset(NX_OBSERVED_PX_Y_VAR, observed_px_y_varDataset);
	}

	@Override
	public DataNode setObserved_px_y_varScalar(Double observed_px_y_varValue) {
		return setField(NX_OBSERVED_PX_Y_VAR, observed_px_y_varValue);
	}

	@Override
	public String getObserved_px_y_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_Y_VAR, NX_OBSERVED_PX_Y_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_y_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_Y_VAR, NX_OBSERVED_PX_Y_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_px_y_errors() {
		return getDataset(NX_OBSERVED_PX_Y_ERRORS);
	}

	@Override
	public Double getObserved_px_y_errorsScalar() {
		return getDouble(NX_OBSERVED_PX_Y_ERRORS);
	}

	@Override
	public DataNode setObserved_px_y_errors(IDataset observed_px_y_errorsDataset) {
		return setDataset(NX_OBSERVED_PX_Y_ERRORS, observed_px_y_errorsDataset);
	}

	@Override
	public DataNode setObserved_px_y_errorsScalar(Double observed_px_y_errorsValue) {
		return setField(NX_OBSERVED_PX_Y_ERRORS, observed_px_y_errorsValue);
	}

	@Override
	public String getObserved_px_y_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_PX_Y_ERRORS, NX_OBSERVED_PX_Y_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_px_y_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PX_Y_ERRORS, NX_OBSERVED_PX_Y_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_phi() {
		return getDataset(NX_OBSERVED_PHI);
	}

	@Override
	public Double getObserved_phiScalar() {
		return getDouble(NX_OBSERVED_PHI);
	}

	@Override
	public DataNode setObserved_phi(IDataset observed_phiDataset) {
		return setDataset(NX_OBSERVED_PHI, observed_phiDataset);
	}

	@Override
	public DataNode setObserved_phiScalar(Double observed_phiValue) {
		return setField(NX_OBSERVED_PHI, observed_phiValue);
	}

	@Override
	public String getObserved_phiAttributeDescription() {
		return getAttrString(NX_OBSERVED_PHI, NX_OBSERVED_PHI_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_phiAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PHI, NX_OBSERVED_PHI_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_phi_var() {
		return getDataset(NX_OBSERVED_PHI_VAR);
	}

	@Override
	public Double getObserved_phi_varScalar() {
		return getDouble(NX_OBSERVED_PHI_VAR);
	}

	@Override
	public DataNode setObserved_phi_var(IDataset observed_phi_varDataset) {
		return setDataset(NX_OBSERVED_PHI_VAR, observed_phi_varDataset);
	}

	@Override
	public DataNode setObserved_phi_varScalar(Double observed_phi_varValue) {
		return setField(NX_OBSERVED_PHI_VAR, observed_phi_varValue);
	}

	@Override
	public String getObserved_phi_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_PHI_VAR, NX_OBSERVED_PHI_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_phi_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PHI_VAR, NX_OBSERVED_PHI_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_phi_errors() {
		return getDataset(NX_OBSERVED_PHI_ERRORS);
	}

	@Override
	public Double getObserved_phi_errorsScalar() {
		return getDouble(NX_OBSERVED_PHI_ERRORS);
	}

	@Override
	public DataNode setObserved_phi_errors(IDataset observed_phi_errorsDataset) {
		return setDataset(NX_OBSERVED_PHI_ERRORS, observed_phi_errorsDataset);
	}

	@Override
	public DataNode setObserved_phi_errorsScalar(Double observed_phi_errorsValue) {
		return setField(NX_OBSERVED_PHI_ERRORS, observed_phi_errorsValue);
	}

	@Override
	public String getObserved_phi_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_PHI_ERRORS, NX_OBSERVED_PHI_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_phi_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_PHI_ERRORS, NX_OBSERVED_PHI_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_x() {
		return getDataset(NX_OBSERVED_X);
	}

	@Override
	public Double getObserved_xScalar() {
		return getDouble(NX_OBSERVED_X);
	}

	@Override
	public DataNode setObserved_x(IDataset observed_xDataset) {
		return setDataset(NX_OBSERVED_X, observed_xDataset);
	}

	@Override
	public DataNode setObserved_xScalar(Double observed_xValue) {
		return setField(NX_OBSERVED_X, observed_xValue);
	}

	@Override
	public String getObserved_xAttributeDescription() {
		return getAttrString(NX_OBSERVED_X, NX_OBSERVED_X_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_xAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_X, NX_OBSERVED_X_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_x_var() {
		return getDataset(NX_OBSERVED_X_VAR);
	}

	@Override
	public Double getObserved_x_varScalar() {
		return getDouble(NX_OBSERVED_X_VAR);
	}

	@Override
	public DataNode setObserved_x_var(IDataset observed_x_varDataset) {
		return setDataset(NX_OBSERVED_X_VAR, observed_x_varDataset);
	}

	@Override
	public DataNode setObserved_x_varScalar(Double observed_x_varValue) {
		return setField(NX_OBSERVED_X_VAR, observed_x_varValue);
	}

	@Override
	public String getObserved_x_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_X_VAR, NX_OBSERVED_X_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_x_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_X_VAR, NX_OBSERVED_X_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_x_errors() {
		return getDataset(NX_OBSERVED_X_ERRORS);
	}

	@Override
	public Double getObserved_x_errorsScalar() {
		return getDouble(NX_OBSERVED_X_ERRORS);
	}

	@Override
	public DataNode setObserved_x_errors(IDataset observed_x_errorsDataset) {
		return setDataset(NX_OBSERVED_X_ERRORS, observed_x_errorsDataset);
	}

	@Override
	public DataNode setObserved_x_errorsScalar(Double observed_x_errorsValue) {
		return setField(NX_OBSERVED_X_ERRORS, observed_x_errorsValue);
	}

	@Override
	public String getObserved_x_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_X_ERRORS, NX_OBSERVED_X_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_x_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_X_ERRORS, NX_OBSERVED_X_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_y() {
		return getDataset(NX_OBSERVED_Y);
	}

	@Override
	public Double getObserved_yScalar() {
		return getDouble(NX_OBSERVED_Y);
	}

	@Override
	public DataNode setObserved_y(IDataset observed_yDataset) {
		return setDataset(NX_OBSERVED_Y, observed_yDataset);
	}

	@Override
	public DataNode setObserved_yScalar(Double observed_yValue) {
		return setField(NX_OBSERVED_Y, observed_yValue);
	}

	@Override
	public String getObserved_yAttributeDescription() {
		return getAttrString(NX_OBSERVED_Y, NX_OBSERVED_Y_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_yAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_Y, NX_OBSERVED_Y_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_y_var() {
		return getDataset(NX_OBSERVED_Y_VAR);
	}

	@Override
	public Double getObserved_y_varScalar() {
		return getDouble(NX_OBSERVED_Y_VAR);
	}

	@Override
	public DataNode setObserved_y_var(IDataset observed_y_varDataset) {
		return setDataset(NX_OBSERVED_Y_VAR, observed_y_varDataset);
	}

	@Override
	public DataNode setObserved_y_varScalar(Double observed_y_varValue) {
		return setField(NX_OBSERVED_Y_VAR, observed_y_varValue);
	}

	@Override
	public String getObserved_y_varAttributeDescription() {
		return getAttrString(NX_OBSERVED_Y_VAR, NX_OBSERVED_Y_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_y_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_Y_VAR, NX_OBSERVED_Y_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getObserved_y_errors() {
		return getDataset(NX_OBSERVED_Y_ERRORS);
	}

	@Override
	public Double getObserved_y_errorsScalar() {
		return getDouble(NX_OBSERVED_Y_ERRORS);
	}

	@Override
	public DataNode setObserved_y_errors(IDataset observed_y_errorsDataset) {
		return setDataset(NX_OBSERVED_Y_ERRORS, observed_y_errorsDataset);
	}

	@Override
	public DataNode setObserved_y_errorsScalar(Double observed_y_errorsValue) {
		return setField(NX_OBSERVED_Y_ERRORS, observed_y_errorsValue);
	}

	@Override
	public String getObserved_y_errorsAttributeDescription() {
		return getAttrString(NX_OBSERVED_Y_ERRORS, NX_OBSERVED_Y_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setObserved_y_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OBSERVED_Y_ERRORS, NX_OBSERVED_Y_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getBounding_box() {
		return getDataset(NX_BOUNDING_BOX);
	}

	@Override
	public Long getBounding_boxScalar() {
		return getLong(NX_BOUNDING_BOX);
	}

	@Override
	public DataNode setBounding_box(IDataset bounding_boxDataset) {
		return setDataset(NX_BOUNDING_BOX, bounding_boxDataset);
	}

	@Override
	public DataNode setBounding_boxScalar(Long bounding_boxValue) {
		return setField(NX_BOUNDING_BOX, bounding_boxValue);
	}

	@Override
	public String getBounding_boxAttributeDescription() {
		return getAttrString(NX_BOUNDING_BOX, NX_BOUNDING_BOX_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setBounding_boxAttributeDescription(String descriptionValue) {
		setAttribute(NX_BOUNDING_BOX, NX_BOUNDING_BOX_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getBackground_mean() {
		return getDataset(NX_BACKGROUND_MEAN);
	}

	@Override
	public Double getBackground_meanScalar() {
		return getDouble(NX_BACKGROUND_MEAN);
	}

	@Override
	public DataNode setBackground_mean(IDataset background_meanDataset) {
		return setDataset(NX_BACKGROUND_MEAN, background_meanDataset);
	}

	@Override
	public DataNode setBackground_meanScalar(Double background_meanValue) {
		return setField(NX_BACKGROUND_MEAN, background_meanValue);
	}

	@Override
	public String getBackground_meanAttributeDescription() {
		return getAttrString(NX_BACKGROUND_MEAN, NX_BACKGROUND_MEAN_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setBackground_meanAttributeDescription(String descriptionValue) {
		setAttribute(NX_BACKGROUND_MEAN, NX_BACKGROUND_MEAN_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_prf() {
		return getDataset(NX_INT_PRF);
	}

	@Override
	public Double getInt_prfScalar() {
		return getDouble(NX_INT_PRF);
	}

	@Override
	public DataNode setInt_prf(IDataset int_prfDataset) {
		return setDataset(NX_INT_PRF, int_prfDataset);
	}

	@Override
	public DataNode setInt_prfScalar(Double int_prfValue) {
		return setField(NX_INT_PRF, int_prfValue);
	}

	@Override
	public String getInt_prfAttributeDescription() {
		return getAttrString(NX_INT_PRF, NX_INT_PRF_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_prfAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_PRF, NX_INT_PRF_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_prf_var() {
		return getDataset(NX_INT_PRF_VAR);
	}

	@Override
	public Double getInt_prf_varScalar() {
		return getDouble(NX_INT_PRF_VAR);
	}

	@Override
	public DataNode setInt_prf_var(IDataset int_prf_varDataset) {
		return setDataset(NX_INT_PRF_VAR, int_prf_varDataset);
	}

	@Override
	public DataNode setInt_prf_varScalar(Double int_prf_varValue) {
		return setField(NX_INT_PRF_VAR, int_prf_varValue);
	}

	@Override
	public String getInt_prf_varAttributeDescription() {
		return getAttrString(NX_INT_PRF_VAR, NX_INT_PRF_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_prf_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_PRF_VAR, NX_INT_PRF_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_prf_errors() {
		return getDataset(NX_INT_PRF_ERRORS);
	}

	@Override
	public Double getInt_prf_errorsScalar() {
		return getDouble(NX_INT_PRF_ERRORS);
	}

	@Override
	public DataNode setInt_prf_errors(IDataset int_prf_errorsDataset) {
		return setDataset(NX_INT_PRF_ERRORS, int_prf_errorsDataset);
	}

	@Override
	public DataNode setInt_prf_errorsScalar(Double int_prf_errorsValue) {
		return setField(NX_INT_PRF_ERRORS, int_prf_errorsValue);
	}

	@Override
	public String getInt_prf_errorsAttributeDescription() {
		return getAttrString(NX_INT_PRF_ERRORS, NX_INT_PRF_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_prf_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_PRF_ERRORS, NX_INT_PRF_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_sum() {
		return getDataset(NX_INT_SUM);
	}

	@Override
	public Double getInt_sumScalar() {
		return getDouble(NX_INT_SUM);
	}

	@Override
	public DataNode setInt_sum(IDataset int_sumDataset) {
		return setDataset(NX_INT_SUM, int_sumDataset);
	}

	@Override
	public DataNode setInt_sumScalar(Double int_sumValue) {
		return setField(NX_INT_SUM, int_sumValue);
	}

	@Override
	public String getInt_sumAttributeDescription() {
		return getAttrString(NX_INT_SUM, NX_INT_SUM_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_sumAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_SUM, NX_INT_SUM_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_sum_var() {
		return getDataset(NX_INT_SUM_VAR);
	}

	@Override
	public Double getInt_sum_varScalar() {
		return getDouble(NX_INT_SUM_VAR);
	}

	@Override
	public DataNode setInt_sum_var(IDataset int_sum_varDataset) {
		return setDataset(NX_INT_SUM_VAR, int_sum_varDataset);
	}

	@Override
	public DataNode setInt_sum_varScalar(Double int_sum_varValue) {
		return setField(NX_INT_SUM_VAR, int_sum_varValue);
	}

	@Override
	public String getInt_sum_varAttributeDescription() {
		return getAttrString(NX_INT_SUM_VAR, NX_INT_SUM_VAR_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_sum_varAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_SUM_VAR, NX_INT_SUM_VAR_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getInt_sum_errors() {
		return getDataset(NX_INT_SUM_ERRORS);
	}

	@Override
	public Double getInt_sum_errorsScalar() {
		return getDouble(NX_INT_SUM_ERRORS);
	}

	@Override
	public DataNode setInt_sum_errors(IDataset int_sum_errorsDataset) {
		return setDataset(NX_INT_SUM_ERRORS, int_sum_errorsDataset);
	}

	@Override
	public DataNode setInt_sum_errorsScalar(Double int_sum_errorsValue) {
		return setField(NX_INT_SUM_ERRORS, int_sum_errorsValue);
	}

	@Override
	public String getInt_sum_errorsAttributeDescription() {
		return getAttrString(NX_INT_SUM_ERRORS, NX_INT_SUM_ERRORS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setInt_sum_errorsAttributeDescription(String descriptionValue) {
		setAttribute(NX_INT_SUM_ERRORS, NX_INT_SUM_ERRORS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getLp() {
		return getDataset(NX_LP);
	}

	@Override
	public Double getLpScalar() {
		return getDouble(NX_LP);
	}

	@Override
	public DataNode setLp(IDataset lpDataset) {
		return setDataset(NX_LP, lpDataset);
	}

	@Override
	public DataNode setLpScalar(Double lpValue) {
		return setField(NX_LP, lpValue);
	}

	@Override
	public String getLpAttributeDescription() {
		return getAttrString(NX_LP, NX_LP_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setLpAttributeDescription(String descriptionValue) {
		setAttribute(NX_LP, NX_LP_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPrf_cc() {
		return getDataset(NX_PRF_CC);
	}

	@Override
	public Double getPrf_ccScalar() {
		return getDouble(NX_PRF_CC);
	}

	@Override
	public DataNode setPrf_cc(IDataset prf_ccDataset) {
		return setDataset(NX_PRF_CC, prf_ccDataset);
	}

	@Override
	public DataNode setPrf_ccScalar(Double prf_ccValue) {
		return setField(NX_PRF_CC, prf_ccValue);
	}

	@Override
	public String getPrf_ccAttributeDescription() {
		return getAttrString(NX_PRF_CC, NX_PRF_CC_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPrf_ccAttributeDescription(String descriptionValue) {
		setAttribute(NX_PRF_CC, NX_PRF_CC_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getOverlaps() {
		return getDataset(NX_OVERLAPS);
	}

	@Override
	public Long getOverlapsScalar() {
		return getLong(NX_OVERLAPS);
	}

	@Override
	public DataNode setOverlaps(IDataset overlapsDataset) {
		return setDataset(NX_OVERLAPS, overlapsDataset);
	}

	@Override
	public DataNode setOverlapsScalar(Long overlapsValue) {
		return setField(NX_OVERLAPS, overlapsValue);
	}

	@Override
	public String getOverlapsAttributeDescription() {
		return getAttrString(NX_OVERLAPS, NX_OVERLAPS_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setOverlapsAttributeDescription(String descriptionValue) {
		setAttribute(NX_OVERLAPS, NX_OVERLAPS_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getPolar_angle() {
		return getDataset(NX_POLAR_ANGLE);
	}

	@Override
	public Double getPolar_angleScalar() {
		return getDouble(NX_POLAR_ANGLE);
	}

	@Override
	public DataNode setPolar_angle(IDataset polar_angleDataset) {
		return setDataset(NX_POLAR_ANGLE, polar_angleDataset);
	}

	@Override
	public DataNode setPolar_angleScalar(Double polar_angleValue) {
		return setField(NX_POLAR_ANGLE, polar_angleValue);
	}

	@Override
	public String getPolar_angleAttributeDescription() {
		return getAttrString(NX_POLAR_ANGLE, NX_POLAR_ANGLE_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setPolar_angleAttributeDescription(String descriptionValue) {
		setAttribute(NX_POLAR_ANGLE, NX_POLAR_ANGLE_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public IDataset getAzimuthal_angle() {
		return getDataset(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public Double getAzimuthal_angleScalar() {
		return getDouble(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset) {
		return setDataset(NX_AZIMUTHAL_ANGLE, azimuthal_angleDataset);
	}

	@Override
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue) {
		return setField(NX_AZIMUTHAL_ANGLE, azimuthal_angleValue);
	}

	@Override
	public String getAttributeDescription() {
		return getAttrString(null, NX_ATTRIBUTE_DESCRIPTION);
	}

	@Override
	public void setAttributeDescription(String descriptionValue) {
		setAttribute(null, NX_ATTRIBUTE_DESCRIPTION, descriptionValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
