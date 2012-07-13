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

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import junit.framework.AssertionFailedError;
import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;

/**
 * Note, if you add a new method here, make sure you add a test for it in {@link AllPyPlotMethodsTest}
 */
public class ReDirectOverRpcPlotterImpl implements ISDAPlotter {

	private AnalysisRpcClient rpcClient;

	public ReDirectOverRpcPlotterImpl() {
		rpcClient = new AnalysisRpcClient(8912);
	}

	private Object request(String dest, Object... args) throws AnalysisRpcException {
		return rpcClient.request(dest, args);
	}

	/**
	 * Not part of ISDAPlotter, but rather a test method available in loopback.py to change
	 * the port.
	 * @param port to tell dnp.plot to connect to
	 * @throws Exception
	 */
	public void setRemotePortRpc(int port) throws Exception {
		request("setremoteport_rpc", port);
	}
	
	@Override
	public void plot(String plotName, IDataset yAxis) throws Exception {
		request("line", yAxis, null, plotName);
	}

	@Override
	public void plot(String plotName, String title, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		request("line", xAxis, yAxis, plotName);
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset xAxis2, IDataset yAxis) throws Exception {
		request("line", xAxis, xAxis2, yAxis, plotName);
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset[] yAxis) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		request("line", xAxis, yAxes, plotName);
	}

	@Override
	public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		request("line", xAxes, yAxes, plotName);
	}

	@Override
	public void plot(String plotName, String title, IDataset[] xAxis, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		request("line", yAxis, plotName);
	}

	@Override
	public void plot(String plotName, String title, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset[] yAxes, String xAxisName, String yAxisName) throws Exception {
		request("line", xAxis, yAxes, plotName);
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset[] yAxes, String xAxisName, String yAxisName)
			throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void plot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName, String yAxisName)
			throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to title argument");
	}

	@Override
	public void updatePlot(String plotName, IDataset yAxis) throws Exception {
		request("updateline", yAxis, null, plotName);
	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		request("updateline", xAxis, yAxis, plotName);
	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset xAxis2, IDataset yAxis) throws Exception {
		request("updateline", xAxis, xAxis2, yAxis, plotName);
	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		request("updateline", xAxis, yAxes, plotName);
	}

	@Override
	public void updatePlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		request("updateline", xAxes, yAxes, plotName);
	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to imageFileName argument");
	}

	@Override
	public void imagePlot(String plotName, IDataset image) throws Exception {
		request("image", image, null, null, plotName);
	}

	@Override
	public void imagesPlot(String plotName, IDataset[] images) throws Exception {
		request("images", images, null, null, plotName);
	}

	@Override
	public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
		request("image", image, xAxis, yAxis, plotName);
	}

	@Override
	public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
		request("images", images, xAxis, yAxis, plotName);
	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		request("points", xCoords, yCoords, null, size, plotName);
	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, int[] sizes) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to AbstractCompoundDataset argument");
	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, IDataset[] sizes) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to AbstractCompoundDataset argument");
	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		request("points", xCoords, yCoords, null, sizes, plotName);
	}

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		request("addpoints", xCoords, yCoords, null, sizes, plotName);
	}

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		request("addpoints", xCoords, yCoords, null, size, plotName);
	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		request("points", xCoords, yCoords, zCoords, size, plotName);
	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		request("points", xCoords, yCoords, zCoords, sizes, plotName);
	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		request("addpoints", xCoords, yCoords, zCoords, size, plotName);
	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		request("addpoints", xCoords, yCoords, zCoords, sizes, plotName);
	}

	@Override
	public void surfacePlot(String plotName, IDataset data) throws Exception {
		request("surface", data, null, null, plotName);
	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset data) throws Exception {
		request("surface", data, xAxis, null, plotName);
	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
		request("surface", data, xAxis, yAxis, plotName);
	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		request("stack", xAxis, yAxes, null, plotName);
	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes, IDataset zAxis) throws Exception {
		request("stack", xAxis, yAxes, zAxis, plotName);
	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		request("stack", xAxes, yAxes, null, plotName);
	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		request("stack", xAxes, yAxes, zAxis, plotName);
	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		request("updatestack", xAxes, yAxes, zAxis, plotName);
	}

	@Override
	public int scanForImages(String viewName, String pathname) throws Exception {
		return (Integer) request("scanforimages", pathname, "none", null, null, -1, true, viewName);
	}

	@Override
	public int scanForImages(String viewName, String pathname, int maxFiles, int nthFile) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to maxFiles, nthFile argument");
	}

	private String getOrderStr(int order) throws AssertionFailedError {
		String orderstr;
		if (order == SDAPlotter.IMAGEORDERNONE)
			orderstr = "none";
		else if (order == SDAPlotter.IMAGEORDERCHRONOLOGICAL)
			orderstr = "chrono";
		else if (order == SDAPlotter.IMAGEORDERALPHANUMERICAL)
			orderstr = "alpha";
		else
			throw new AssertionFailedError("Unknown order string");
		return orderstr;
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order) throws Exception {
		return (Integer) request("scanforimages", pathname, getOrderStr(order), null, null, -1, true, viewName);
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor) throws Exception {
		return (Integer) request("scanforimages", pathname, getOrderStr(order), null, suffices, -1, rowMajor, viewName);
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
			int gridColumns, boolean rowMajor) throws Exception {
		return (Integer) request("scanforimages", pathname, getOrderStr(order), regex, suffices, -1, rowMajor, viewName);
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to maxFiles, jumpBetween argument");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to maxFiles, jumpBetween argument");
	}

	@Override
	public void volumePlot(String viewName, String rawvolume, int headerSize, int voxelType, int xdim, int ydim,
			int zdim) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to arguments other than data set and viewName");
	}

	@Override
	public void volumePlot(String viewName, IDataset volume) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to io save not implemented, see volume in plot.py");
	}

	@Override
	public void volumePlot(String viewName, String dsrvolume) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to arguments other than data set and viewName");
	}

	@Override
	public void clearPlot(String viewName) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void setupNewImageGrid(String viewName, int images) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, String filename) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, boolean store) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void viewNexusTree(String viewer, HDF5File tree) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void viewHDF5Tree(String viewer, HDF5File tree) throws Exception {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void setGuiBean(String plotName, GuiBean bean) throws Exception {
		request("setbean", bean, plotName);
	}

	@Override
	public GuiBean getGuiBean(String plotName) throws Exception {
		return (GuiBean) request("getbean", plotName);
	}

	@Override
	public void setDataBean(String plotName, DataBean bean) throws Exception {
		request("setdatabean", bean, plotName);
	}

	@Override
	public DataBean getDataBean(String plotName) throws Exception {
		return (DataBean) request("getdatabean", plotName);
	}

	@Override
	public GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode) {
		throw new AssertionFailedError("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public String[] getGuiNames() throws Exception {
		return (String[]) request("getguinames");
	}

}
