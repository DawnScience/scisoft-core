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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;

/**
 * This bean contains all the information required by a GUI to perform a plot,
 * including the data, as well as the axis information.
 */
public class DataBean implements Serializable {

	private static final long serialVersionUID = -2033109932408452451L;

	protected List<DataSetWithAxisInformation> data;

	protected Map<String, Dataset> axisData;

	protected List<HDF5File> hdf5Trees;
	
	protected GuiPlotMode guiPlotMode;
	
	protected GuiBean plotParameters;

	/**
	 * Constructor to initialise all the collection objects
	 */
	public DataBean(GuiPlotMode plotMode) {
		guiPlotMode = plotMode;
		data = new ArrayList<DataSetWithAxisInformation>();
		axisData = new HashMap<String, Dataset>();
		hdf5Trees = new ArrayList<HDF5File>();
	}
	
	public DataBean() {
		guiPlotMode = null;
		data = new ArrayList<DataSetWithAxisInformation>();
		axisData = new HashMap<String, Dataset>();
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
	 * @param axisID
	 * @param axisDataset
	 */
	public void addAxis(String axisID, IDataset axisDataset) {
		axisData.put(axisID, DatasetUtils.convertToDataset(axisDataset));
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
	 * gets the axis data of the specified ID
	 * 
	 * @param axisID
	 * @return the axis dataset
	 */
	public Dataset getAxis(String axisID) {
		return axisData.get(axisID);
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
	 * Add data from bean whilst avoiding axis ID clashes
	 * @param bean
	 */
	public void addData(DataBean bean) {
		Map<String, Dataset> nmap = bean.getAxisData();
		List<DataSetWithAxisInformation> ndata = bean.getData();
		for (String s : nmap.keySet()) {
			Dataset nd = nmap.get(s);
			Dataset od = axisData.get(s);
			if (od != null && !od.equals(nd)) { // a clash so need to rename
				String n = nd.getName();
				if (n != null && n.trim().length() > 0) {
					od = axisData.get(n);
					if (nd.equals(od)) {
						replaceAxisID(ndata, s, n);
						continue;
					}
					// another clash occurred so fall through
				}
				for (int i = 0;; i++) { // find unique name
					n = i == 0 ? AxisMapBean.XAXIS : AxisMapBean.XAXIS + i;
					if (!nmap.containsKey(n) && !axisData.containsKey(n)) {
						replaceAxisID(ndata, s, n);
						break;
					}
				}
				addAxis(n, nd);
			}
		}
		data.addAll(ndata);
	}

	private void replaceAxisID(List<DataSetWithAxisInformation> ndata, String oldID, String newID) {
		for (DataSetWithAxisInformation d : ndata) { // replace clashing name
			String[] ids = d.getAxisMap().getAxisID();
			for (int j = 0; j < ids.length; j++) {
				if (oldID.equals(ids[j])) {
					ids[j] = newID;
				}
			}
		}
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
	public Map<String, Dataset> getAxisData() {
		return axisData;
	}

	/**
	 * Set map of names to axis datasets
	 * @param axisData
	 */
	public void setAxisData(Map<String, Dataset> axisData) {
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

	/**
	 * @return GUI parameters as a GUI bean
	 */
	public GuiBean getGuiParameters() {
		return plotParameters;
	}

	@Override
	public String toString() {
		return "data =" + data.toString() + "\n" + "axisData = " + axisData.toString(); 
	}
}
