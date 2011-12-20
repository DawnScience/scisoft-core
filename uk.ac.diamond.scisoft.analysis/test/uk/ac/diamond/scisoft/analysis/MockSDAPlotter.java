/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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

	@Override
	public void plot(String plotName, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, String title, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, IDataset[] xAxis, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset[] yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, String title, IDataset xAxis, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void plot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plot(String plotName, String title, IDataset[] xAxis, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void updatePlot(String plotName, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset yAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void updatePlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void imagePlot(String plotName, IDataset image) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void imagesPlot(String plotName, IDataset[] images) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, int[] sizes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, IDataset[] sizes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void surfacePlot(String plotName, IDataset data) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset data) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public int scanForImages(String viewName, String pathname) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int maxFiles, int nthFile) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
			int gridColumns, boolean rowMajor) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void volumePlot(String viewName, String rawvolume, int headerSize, int voxelType, int xdim, int ydim,
			int zdim) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void volumePlot(String viewName, IDataset volume) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void volumePlot(String viewName, String dsrvolume) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void clearPlot(String viewName) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void setupNewImageGrid(String viewName, int images) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, String filename) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, boolean store) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public void setGuiBean(String plotName, GuiBean bean) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public GuiBean getGuiBean(String plotName) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void setDataBean(String plotName, DataBean bean) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");

	}

	@Override
	public DataBean getDataBean(String plotName) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode) {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public String[] getGuiNames() throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void viewNexusTree(String viewer, HDF5File tree) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

	@Override
	public void viewHDF5Tree(String viewer, HDF5File tree) throws Exception {
		throw new AssertionFailedError("Methods in MockSDAPlotter should not be called");
	}

}
