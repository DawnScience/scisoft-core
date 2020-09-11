/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver.rpc.sdaplotter;

import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.ISDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

/**
 * Note, if you add a new method here, make sure you add a test for it in {@link AllPyPlotMethodsTest}
 */
public class ReDirectOverRpcPlotterImpl implements ISDAPlotter {

	private AnalysisRpcClient rpcClient;

	public ReDirectOverRpcPlotterImpl() {
		this(8912);
	}

	public ReDirectOverRpcPlotterImpl(int port) {
		rpcClient = new AnalysisRpcClient(port);
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
	public void plot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String[] yLabels, String[] xAxisNames, String[] yAxisNames)
			throws Exception {
		request("line", xValues, yValues, title, plotName);
	}

	@Override
	public void addPlot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String[] yLabels, String[] xAxisNames, String[] yAxisNames)
			throws Exception {
		request("line", xValues, yValues, title, plotName);
	}

	@Override
	public void updatePlot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String xAxisName, String yAxisName)
			throws Exception {
		request("updateline", xValues, yValues, title, plotName);
	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to imageFileName argument");
	}

	@Override
	public void imagePlot(String plotName, IDataset xValues, IDataset yValues, IDataset image, String xName, String yName) throws Exception {
		request("image", image, xValues, yValues, plotName);
	}

	@Override
	public void imagesPlot(String plotName, IDataset xValues, IDataset yValues, IDataset[] images) throws Exception {
		request("images", images, xValues, yValues, plotName);
	}

	@Override
	public void scatter2DPlot(String plotName, CompoundDataset[] coordPairs, IDataset[] sizes) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to CompoundDataset argument");
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
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		request("points", xCoords, yCoords, zCoords, sizes, plotName);
	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		request("addpoints", xCoords, yCoords, zCoords, sizes, plotName);
	}

	@Override
	public void surfacePlot(String plotName, IDataset xValues, IDataset yValues, IDataset data) throws Exception {
		request("surface", data, xValues, yValues, plotName);
	}

	@Override
	public void stackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues) throws Exception {
		request("stack", xValues, yValues, zValues, plotName);
	}

	@Override
	public void addStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues) throws Exception {
		request("addstack", xValues, yValues, zValues, plotName);
	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues) throws Exception {
		request("updatestack", xValues, yValues, zValues, plotName);
	}

	private String getOrderStr(int order) throws UnsupportedOperationException {
		String orderstr;
		if (order == SDAPlotter.IMAGEORDERNONE)
			orderstr = "none";
		else if (order == SDAPlotter.IMAGEORDERCHRONOLOGICAL)
			orderstr = "chrono";
		else if (order == SDAPlotter.IMAGEORDERALPHANUMERICAL)
			orderstr = "alpha";
		else
			throw new UnsupportedOperationException("Unknown order string");
		return orderstr;
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		return (Integer) request("scanforimages", pathname, getOrderStr(order), nameregex, suffices, gridColumns, rowMajor, viewName);
	}

	@Override
	public void clearPlot(String plotName) throws Exception {
		request("clear", plotName);
	}

	@Override
	public void exportPlot(String plotName, String fileFormat,
			String saveFullPath) throws Exception {
		request("export", plotName, fileFormat, saveFullPath);
	}

	@Override
	public void resetAxes(String plotName) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public void viewTree(String viewer, Tree tree) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
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
		throw new UnsupportedOperationException("Method unsupported in python due to not being in plot.py");
	}

	@Override
	public String[] getGuiNames() throws Exception {
		return (String[]) request("getguinames");
	}

	@Override
	public void createAxis(String plotName, String title, int side) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python, please use the py4j connection to maniplulate axes from cpython!");
	}

	@Override
	public void renameActiveXAxis(String plotName, String xAxisTitle) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python, please use the py4j connection to maniplulate axes from cpython!");
	}

	@Override
	public void renameActiveYAxis(String plotName, String yAxisTitle) throws Exception {
		throw new UnsupportedOperationException("Method unsupported in python, please use the py4j connection to maniplulate axes from cpython!");
	}

	@Override
	public void volumePlot(String plotName, IDataset xValues, IDataset yValues, IDataset zValues, IDataset volume)
			throws Exception {
		request("volume", volume, xValues, yValues, zValues, plotName);
		
	}
}
