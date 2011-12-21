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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;

/**
 * This bean contains all the information required by a GUI to perform a plot,
 * including the data, as well as the axis information.
 */
public class DataBean implements Serializable {

	private static final long serialVersionUID = -2033109932408452451L;

	protected List<DataSetWithAxisInformation> data;

	protected Map<String, AbstractDataset> axisData;

	protected List<HDF5File> hdf5Trees;
	
	protected GuiPlotMode guiPlotMode;
	
	protected GuiBean plotParameters;

	/**
	 * Constructor to initialise all the collection objects
	 */
	public DataBean(GuiPlotMode plotMode) {
		guiPlotMode = plotMode;
		data = new ArrayList<DataSetWithAxisInformation>();
		axisData = new HashMap<String, AbstractDataset>();
		hdf5Trees = new ArrayList<HDF5File>();
	}
	
	public DataBean() {
		guiPlotMode = null;
		data = new ArrayList<DataSetWithAxisInformation>();
		axisData = new HashMap<String, AbstractDataset>();
		hdf5Trees = new ArrayList<HDF5File>();
	}
	

	/**
	 * @return a shallow copy of data bean
	 */
	public DataBean copy() {
		DataBean bean = new DataBean();
		bean.data.addAll(data);
		bean.axisData.putAll(axisData);
		bean.hdf5Trees.addAll(hdf5Trees);
		bean.guiPlotMode = guiPlotMode;
		return bean;
	}
	
	/**
	 * Adds the provided data to the bean, one element at a time
	 * 
	 * @param dataToAdd
	 * @throws DataBeanException 
	 */
	public void addData(DataSetWithAxisInformation dataToAdd) throws DataBeanException {
		// check that the dataset's axis mapping has IDs that
		// correspond to ones in axisData
		AxisMapBean mapping = dataToAdd.getAxisMap();
		String[] axisID = mapping.getAxisID();
		if (axisID != null) {
			for (String s : axisID) {
				if (!axisData.containsKey(s)) {
					throw new DataBeanException();
				}
			}
		}
		data.add(dataToAdd);
	}

	/**
	 * Adds the provided axis data to the bean
	 * 
	 * @param axisName
	 * @param axisDataset
	 */
	public void addAxis(String axisName, IDataset axisDataset) {
		axisData.put(axisName, DatasetUtils.convertToAbstractDataset(axisDataset));
	}

	/**
	 * Adds the provided HDF5 tree to the bean, one element at a time
	 * 
	 * @param hdf5TreeToAdd
	 */
	public void addHDF5Tree(HDF5File hdf5TreeToAdd) {
		hdf5Trees.add(hdf5TreeToAdd);
	}

	/**
	 * gets the axis data of the specified name
	 * 
	 * @param axisName
	 * @return the axis dataset
	 */
	public AbstractDataset getAxis(String axisName) {
		return axisData.get(axisName);
	}

	/**
	 * gets all the data which is to be plotted as a collection
	 * 
	 * @return Returns the data.
	 */
	public List<DataSetWithAxisInformation> getData() {
		return data;
	}

	/**
	 * Fills the beans collection with one created external, useful for filling
	 * all the data in one go.
	 * 
	 * @param data
	 *            The data to set.
	 */
	public void setData(List<DataSetWithAxisInformation> data) {
		this.data = data;
	}

	/**
	 * gets all the nexusTrees as a collection
	 * 
	 * @return nexusTrees
	 */
	public List<HDF5File> getHDF5Trees() {
		return hdf5Trees;
	}
	
	/**
	 * 
	 * @param hdf5Trees
	 */
	public void setHDF5Trees(List<HDF5File> hdf5Trees) {
		this.hdf5Trees = hdf5Trees;
	}

	/**
	 * @return map of names to axis datasets
	 */
	public Map<String, AbstractDataset> getAxisData() {
		return axisData;
	}

	/**
	 * Set map of names to axis datasets
	 * @param axisData
	 */
	public void setAxisData(Map<String, AbstractDataset> axisData) {
		this.axisData = axisData;
	}

	public GuiPlotMode getGuiPlotMode() {
		return guiPlotMode;
	}

	public void setGuiPlotMode(GuiPlotMode guiPlotMode) {
		this.guiPlotMode = guiPlotMode;
	}

	public void putGuiParameter(GuiParameters key, Serializable value) {
		if (plotParameters == null) {
				plotParameters = new GuiBean();
		}
		plotParameters.put(key, value);
	}
	
	public GuiBean getGuiParameters() {
		return plotParameters;
	}
	
	@Override
	public String toString() {
		return "data =" + data.toString() + "\n" + "axisData = " + axisData.toString(); 
	}
}
