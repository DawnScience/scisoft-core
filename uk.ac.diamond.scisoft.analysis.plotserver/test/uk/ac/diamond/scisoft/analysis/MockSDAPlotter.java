/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import junit.framework.AssertionFailedError;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.CompoundDataset;
import org.eclipse.dawnsci.hdf5.api.HDF5File;

import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

public class MockSDAPlotter implements ISDAPlotter {

	/**
	 * Common creator of exception so a single breakpoint can be placed to catch any calls.
	 */
	private AssertionFailedError newShouldNotBeCalled() {
		return new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String[] yLabels, String[] xAxisNames, String[] yAxisNames)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void addPlot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String[] yLabels, String[] xAxisNames, String[] yAxisNames)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void updatePlot(String plotName, String title, IDataset[] xValues, IDataset[] yValues, String xAxisName, String yAxisName)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagePlot(String plotName, IDataset xValues, IDataset yValues, IDataset image, String xName, String yName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagesPlot(String plotName, IDataset xValues, IDataset yValues, IDataset[] images) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void scatter2DPlot(String plotName, CompoundDataset[] coordPairs, IDataset[] sizes) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void surfacePlot(String plotName, IDataset xValues, IDataset yValues, IDataset data) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void stackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void addStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void volumePlot(String viewName, String rawvolume, int headerSize, int voxelType, int xdim, int ydim,
			int zdim) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void volumePlot(String viewName, IDataset volume) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void volumePlot(String viewName, String dsrvolume) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void clearPlot(String plotName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void exportPlot(String plotName, String fileFormat,
			String saveFullPath) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void resetAxes(String plotName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void setGuiBean(String plotName, GuiBean bean) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public GuiBean getGuiBean(String plotName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void setDataBean(String plotName, DataBean bean) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public DataBean getDataBean(String plotName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode) {
		throw newShouldNotBeCalled();
	}

	@Override
	public String[] getGuiNames() throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void viewNexusTree(String viewer, HDF5File tree) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void viewHDF5Tree(String viewer, HDF5File tree) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void createAxis(String plotName, String title, int side) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void renameActiveXAxis(String plotName, String xAxisTitle) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void renameActiveYAxis(String plotName, String yAxisTitle) throws Exception {
		throw newShouldNotBeCalled();
	}

}
