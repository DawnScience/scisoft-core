/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

/**
 * This bean holds a single dataset along with its axis information. The axis
 * data is actually provided by the DataBean, which is the object that contains
 * this one
 */
public class DatasetWithAxisInformation implements Serializable {

	private Dataset data;
	private AxisMapBean axisMap;

	/**
	 * @return Returns the data.
	 */
	public Dataset getData() {
		return data;
	}

	/**
	 * @param data
	 *            The data to set.
	 */
	public void setData(IDataset data) {
		this.data = DatasetUtils.convertToDataset(data);
	}

	/**
	 * Clears metadata from dataset
	 */
	public void clearMetadata() {
		data = data.getView();
		data.clearMetadata(null);
	}

	/**
	 * @return Returns the axisMap.
	 */
	public AxisMapBean getAxisMap() {
		return axisMap;
	}

	/**
	 * @param axisMap
	 *            The axisMap to set.
	 */
	public void setAxisMap(AxisMapBean axisMap) {
		this.axisMap = axisMap;
	}
	
	/**
	 * This method builds the axis information into the base dataset
	 * 
	 * @param yAxis
	 *            The dataset which is to be plotted
	 * @return The dataset with Axis info included
	 */
	public static DatasetWithAxisInformation createAxisDataSet(IDataset yAxis) {
		return createAxisDataSet(yAxis, AxisMapBean.XAXIS);
	}

	/**
	 * This method builds the axis information into the base dataset
	 * @param yAxis
	 * @param axisDatasetID
	 * @return dataset with axis info
	 */
	public static DatasetWithAxisInformation createAxisDataSet(IDataset yAxis, String axisDatasetID) {
		return createAxisDataSet(yAxis, new String[] {axisDatasetID}, null);
	}

	/**
	 * This method builds the axis information into the base dataset
	 * @param yAxis
	 * @param axisDatasetID
	 * @param axisName
	 * @return dataset with axis info
	 */
	public static DatasetWithAxisInformation createAxisDataSet(IDataset yAxis, String axisDatasetID, String axisName) {
		return createAxisDataSet(yAxis, new String[] {axisDatasetID}, new String[] {axisName});
	}

	/**
	 * This method builds the axis information into the base dataset
	 * @param yAxis
	 * @param axisDatasetIDs
	 * @param axisNames can be null or contain nulls
	 * @return dataset with axis info
	 */
	public static DatasetWithAxisInformation createAxisDataSet(IDataset yAxis, String[] axisDatasetIDs, String[] axisNames) {
		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean axisMapBean = new AxisMapBean();
		axisMapBean.setAxisID(axisDatasetIDs.clone());
		if (axisNames != null) {
			axisMapBean.setAxisNames(axisNames.clone());
		}
		axisData.setAxisMap(axisMapBean);
		axisData.setData(yAxis);
		return axisData;
	}	

	@Override
	public String toString() {
		return data + "; axisMap = " + axisMap;
	}
}
