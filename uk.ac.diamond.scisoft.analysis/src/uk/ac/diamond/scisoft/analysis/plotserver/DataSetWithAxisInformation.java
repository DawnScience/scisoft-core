/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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