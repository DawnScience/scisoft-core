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

package uk.ac.diamond.scisoft.analysis;

import junit.framework.AssertionFailedError;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;
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
	public void plot(String plotName, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset xAxis2, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset[] yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plot(String plotName, String title, IDataset[] xAxis, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plot(String plotName, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset yAxis, String xAxisName, String yAxisName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset[] yAxes, String xAxisName, String yAxisName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset[] yAxes, String xAxisName, String yAxisName)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void plot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName, String yAxisName)
			throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void updatePlot(String plotName, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset xAxis2, IDataset yAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void updatePlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void imagePlotProfile(String plotName, String imageFileName) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagePlotProfile(String plotName, IDataset image) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagePlotProfile(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void imagePlot(String plotName, IDataset image) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void imagesPlot(String plotName, IDataset[] images) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, int[] sizes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, IDataset[] sizes) throws Exception {
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
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void surfacePlot(String plotName, IDataset data) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset data) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public int scanForImages(String viewName, String pathname) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int maxFiles, int nthFile) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
			int gridColumns, boolean rowMajor) throws Exception {
		throw newShouldNotBeCalled();
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
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
	public void clearPlot(String viewName) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void setupNewImageGrid(String viewName, int images) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception {
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
	public void plotImageToGrid(String viewName, String filename) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, boolean store) throws Exception {
		throw newShouldNotBeCalled();

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY) throws Exception {
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
}
