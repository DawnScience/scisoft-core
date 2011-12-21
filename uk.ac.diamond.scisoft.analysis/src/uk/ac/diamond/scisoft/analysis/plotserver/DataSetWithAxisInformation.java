/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * This bean holds a single dataset along with its axis information. The axis
 * data is actually provided by the DataBean, which is the object that contains
 * this one
 */
public class DataSetWithAxisInformation implements Serializable {

	private AbstractDataset data;
	private AxisMapBean axisMap;

	/**
	 * @return Returns the data.
	 */
	public AbstractDataset getData() {
		return data;
	}

	/**
	 * @param data
	 *            The data to set.
	 */
	public void setData(IDataset data) {
		this.data = DatasetUtils.convertToAbstractDataset(data);
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
	public static DataSetWithAxisInformation createAxisDataSet(IDataset yAxis) {
		DataSetWithAxisInformation axisData = new DataSetWithAxisInformation();
		AxisMapBean axisMapBean = new AxisMapBean(AxisMapBean.DIRECT);
		axisMapBean.setAxisID(new String[] { AxisMapBean.XAXIS });
		axisData.setAxisMap(axisMapBean);
		axisData.setData(yAxis);
		return axisData;
	}

	public static DataSetWithAxisInformation createAxisDataSet(IDataset yAxis, String axisName) {
		DataSetWithAxisInformation axisData = new DataSetWithAxisInformation();
		AxisMapBean axisMapBean = new AxisMapBean(AxisMapBean.DIRECT);
		axisMapBean.setAxisID(new String[] { axisName });
		axisData.setAxisMap(axisMapBean);
		axisData.setData(yAxis);
		return axisData;
	}	

	@Override
	public String toString() {
		return data.toString() + "axisMap = " + axisMap.toString();
	}
}
