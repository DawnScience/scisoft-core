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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

public interface ISDAPlotter {

	public final String LISTOFSUFFIX[] = { "png", "jpg", "tif{1,2}", "mar", "cbf", "dat", "img", "raw", "mccd", "cif",
			"imgcif" };

	/**
	 * Specify that the images are displayed in no particular order
	 */
	public final int IMAGEORDERNONE = 0;

	/**
	 * Specify that the images are displayed in alpha-numerical order
	 */
	public final int IMAGEORDERALPHANUMERICAL = 1;

	/**
	 * Specify that the images are displayed in chronological order
	 */
	public final int IMAGEORDERCHRONOLOGICAL = 2;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset yAxis) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset yAxis, final String xAxisName, final String yAxisName) throws Exception;


	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, final IDataset yAxis) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, final IDataset yAxis, final String xAxisName, final String yAxisName) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset xAxis, final IDataset yAxis) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param xAxis2
	 *            The dataset to use as the X values on the second X axis
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset xAxis, final IDataset xAxis2, final IDataset yAxis) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, final IDataset xAxis, final IDataset yAxis) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, final IDataset xAxis, final IDataset yAxis, final String xAxisName, final String yAxisName) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values (can be null)
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset xAxis, IDataset[] yAxes) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values (can be null)
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, final IDataset xAxis, IDataset[] yAxes, final String xAxisName, final String yAxisName) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, String title, final IDataset xAxis, IDataset[] yAxes) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, String title, final IDataset xAxis, IDataset[] yAxes, final String xAxisName, final String yAxisName) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxes
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxes
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, IDataset[] xAxes, IDataset[] yAxes) throws Exception;

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxes
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The datasets to use as the Y values
	 * @param xAxisName
	 *            The name of xAxis, null if none
	 * @param yAxisName
	 *            The name of dataset, null if none
	 * @throws Exception
	 */
	public void plot(String plotName, final String title, IDataset[] xAxes, IDataset[] yAxes, final String xAxisName, final String yAxisName) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public void updatePlot(String plotName, final IDataset yAxis) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxis
	 * @throws Exception
	 */
	public void updatePlot(String plotName, final IDataset xAxis, final IDataset yAxis) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param xAxis2
	 * @param yAxis
	 * @throws Exception
	 */
	public void updatePlot(String plotName, final IDataset xAxis, final IDataset xAxis2, final IDataset yAxis) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @throws Exception
	 */
	public void updatePlot(String plotName, final IDataset xAxis, IDataset[] yAxes) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public void updatePlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception;

	/**
	 * Update existing plot with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param title
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes) throws Exception;

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param imageFileName
	 * @throws Exception
	 */
	public void imagePlot(String plotName, String imageFileName) throws Exception;

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param image
	 * @throws Exception
	 */
	public void imagePlot(String plotName, IDataset image) throws Exception;

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param images
	 * @throws Exception
	 */
	public void imagesPlot(String plotName, IDataset[] images) throws Exception;

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param yAxis
	 *            can be null
	 * @param image
	 * @throws Exception
	 */

	public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception;

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param yAxis
	 *            can be null
	 * @param images
	 * @throws Exception
	 */

	public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception;

	/**
	 * Allows plotting of points of given size on a 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param size
	 * @throws Exception
	 */
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception;

	/**
	 * Allows plotting of multiple sets of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param coordPairs
	 * @param sizes
	 * @throws Exception
	 */
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, int[] sizes) throws Exception;

	/**
	 * Allows plotting of multiple sets of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param coordPairs
	 * @param sizes
	 * @throws Exception
	 */
	public void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, IDataset[] sizes) throws Exception;

	/**
	 * Allows plotting of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param sizes
	 * @throws Exception
	 */
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception;

	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes) throws Exception;

	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception;

	/**
	 * Allows plotting of points of given size on a 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param zCoords
	 * @param size
	 * @throws Exception
	 */
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception;

	/**
	 * Allows plotting of points of given sizes on a 3D volume
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param sizes
	 * @throws Exception
	 */
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception;

	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception;

	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, IDataset sizes)
			throws Exception;

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param data
	 * @throws Exception
	 */
	public void surfacePlot(String plotName, IDataset data) throws Exception;

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param data
	 * @throws Exception
	 */
	public void surfacePlot(String plotName, IDataset xAxis, IDataset data) throws Exception;

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param yAxis
	 *            can be null
	 * @param data
	 * @throws Exception
	 */
	public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception;

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @throws Exception
	 */
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception;

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes, IDataset zAxis) throws Exception;

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception;

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception;

	/**
	 * Update stack with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with any supported image formats
	 * 
	 * @param viewName
	 *            of image explorer
	 * @param pathname
	 * @return number of files found
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with any supported image formats
	 * 
	 * @param viewName
	 *            of image explorer
	 * @param pathname
	 *            name of the path/directory which images should be loaded from
	 * @param maxFiles
	 *            maximum number of files that should be loaded
	 * @param nthFile
	 *            only load every nth file
	 * @return number of files loaded
	 * @throws Exception
	 */

	public int scanForImages(String viewName, String pathname, int maxFiles, int nthFile) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with any supported image formats in specified order
	 * 
	 * @param viewName
	 * @param pathname
	 * @param order
	 * @return number of files loaded
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname, int order) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with images of given suffices
	 * 
	 * @param viewName
	 *            of image explorer
	 * @param pathname
	 * @param order
	 * @param suffices
	 * @param gridColumns
	 *            use -1 to indicate automatic configuration to square array
	 * @param rowMajor
	 *            if true, display images in row-major order
	 * @return number of files loaded
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with images of given suffices
	 * 
	 * @param viewName
	 *            of image explorer
	 * @param pathname
	 * @param order
	 * @param regex
	 * @param suffices
	 * @param gridColumns
	 *            use -1 to indicate automatic configuration to square array
	 * @param rowMajor
	 *            if true, display images in row-major order
	 * @return number of files loaded
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
			int gridColumns, boolean rowMajor) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with images of given suffices
	 * 
	 * @param viewName
	 * @param pathname
	 * @param order
	 * @param suffices
	 * @param gridColumns
	 * @param rowMajor
	 * @param maxFiles
	 * @param jumpBetween
	 * @return number of files loaded
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor, int maxFiles, int jumpBetween) throws Exception;

	/**
	 * Scan a directory and populate an image explorer view with images of given suffices
	 * 
	 * @param viewName
	 * @param pathname
	 * @param order
	 * @param nameregex
	 * @param suffices
	 * @param gridColumns
	 * @param rowMajor
	 * @param maxFiles
	 * @param jumpBetween
	 * @return number of files loaded
	 * @throws Exception
	 */
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception;

	/**
	 * Send volume data contained in given filename to remote renderer. Note the raw data needs
	 * written in little endian byte order
	 * @param viewName
	 * @param rawvolume
	 *            raw format filename
	 * @param headerSize
	 *            number of bytes to ignore in file
	 * @param voxelType
	 *            0,1,2,3 for byte, short, int and float (integer values are interpreted as unsigned values)
	 * @param xdim
	 *            number of voxels in x-dimension
	 * @param ydim
	 *            number of voxels in y-dimension
	 * @param zdim
	 *            number of voxels in z-dimension
	 * @throws Exception
	 */
	public void volumePlot(String viewName, String rawvolume, int headerSize, int voxelType, int xdim, int ydim,
			int zdim) throws Exception;

	/**
	 * Send volume data to remote renderer. Only single-element datasets of byte, short, int and float are directly
	 * supported. Other types are internally converted; first elements of compound datasets are extracted.
	 * 
	 * @param viewName
	 * @param volume
	 * @throws Exception
	 */
	public void volumePlot(String viewName, IDataset volume) throws Exception;

	/**
	 * Send volume data contained in given filename to remote renderer
	 * 
	 * @param viewName
	 * @param dsrvolume
	 *            Diamond Scisoft raw format filename
	 * @throws Exception
	 */
	public void volumePlot(String viewName, String dsrvolume) throws Exception;

	/**
	 * Clear/empty a current plot view
	 * 
	 * @throws Exception
	 */
	public void clearPlot(String viewName) throws Exception;

	/**
	 * Set up a new image grid for an image explorer view with the specified # rows and columns
	 * 
	 * @param viewName
	 * @param gridRows
	 *            number of start rows
	 * @param gridColumns
	 *            number of start columns
	 * @throws Exception
	 */
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception;

	/**
	 * Set up a new image grid for an image explorer view with the specified # images
	 * 
	 * @param viewName
	 * @param images
	 *            number of images
	 * @throws Exception
	 */
	public void setupNewImageGrid(String viewName, int images) throws Exception;

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param datasets
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception;

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param datasets
	 * @param store
	 *            if true, create copies of images as temporary files
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception;

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param filename
	 * @param gridX
	 *            X position in the grid
	 * @param gridY
	 *            Y position in the grid
	 * @throws Exception
	 */

	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception;

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param filename
	 * @throws Exception
	 */

	public void plotImageToGrid(String viewName, String filename) throws Exception;

	/**
	 * Plot an image to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param dataset
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset dataset) throws Exception;

	/**
	 * Plot an image to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param dataset
	 * @param store
	 *            if true, create a copy of image as a temporary file
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset dataset, boolean store) throws Exception;

	/**
	 * Plot an image to the grid of an image explorer view in specified position
	 * 
	 * @param viewName
	 * @param dataset
	 * @param gridX
	 * @param gridY
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY) throws Exception;

	/**
	 * Plot an image to the grid of an image explorer view in specified position
	 * 
	 * @param viewName
	 * @param dataset
	 * @param gridX
	 * @param gridY
	 * @param store
	 *            if true, create a copy of image as a temporary file
	 * @throws Exception
	 */
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception;

	/**
	 * @param viewer
	 * @param tree
	 * @throws Exception
	 */
	public void viewNexusTree(String viewer, HDF5File tree) throws Exception;

	/**
	 * @param viewer
	 * @param tree
	 * @throws Exception
	 */
	public void viewHDF5Tree(String viewer, HDF5File tree) throws Exception;

	/**
	 * General way to send a gui bean to plot server
	 * 
	 * @param plotName
	 * @param bean
	 * @throws Exception
	 */
	public void setGuiBean(String plotName, GuiBean bean) throws Exception;

	/**
	 * General way to grab a gui bean from plot server
	 * 
	 * @param plotName
	 * @return a GuiBean from a named plot
	 * @throws Exception
	 */
	public GuiBean getGuiBean(String plotName) throws Exception;

	/**
	 * General way to send a data bean to plot server
	 * 
	 * @param plotName
	 * @param bean
	 * @throws Exception
	 */
	public void setDataBean(String plotName, DataBean bean) throws Exception;

	/**
	 * General way to grab a data bean from plot server
	 * 
	 * @param plotName
	 * @return a DataBean from a named plot
	 * @throws Exception
	 */
	public DataBean getDataBean(String plotName) throws Exception;

	/**
	 * @return GUI bean for named view of given plot mode or create a new one
	 */
	public GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode);

	/**
	 * Get a list of all Gui Names the Plot Server knows about
	 * 
	 * @return array of all names
	 * @throws Exception
	 */
	public String[] getGuiNames() throws Exception;

}
