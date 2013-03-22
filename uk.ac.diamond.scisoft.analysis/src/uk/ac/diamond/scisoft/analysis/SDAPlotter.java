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

/**
 * {@link SDAPlotter} provides convenience functions wrapping around the correct implementation
 * of {@link ISDAPlotter}.
 */
public class SDAPlotter {
	public static final String LISTOFSUFFIX[] = ISDAPlotter.LISTOFSUFFIX;
	
	/**
	 * Specify that the images are displayed in no particular order
	 */
	public static final int IMAGEORDERNONE = ISDAPlotter.IMAGEORDERNONE;

	/**
	 * Specify that the images are displayed in alpha-numerical order
	 */
	public static final int IMAGEORDERALPHANUMERICAL = ISDAPlotter.IMAGEORDERALPHANUMERICAL;

	/**
	 * Specify that the images are displayed in chronological order
	 */
	public static final int IMAGEORDERCHRONOLOGICAL = ISDAPlotter.IMAGEORDERCHRONOLOGICAL;

	/**
	 * @return the plotter implementation to delegate to
	 */
	private static ISDAPlotter getPlotterImpl() {
		return SDAPlotterImpl.getDefaultInstance();
	}

	/**
	 * Creates a new axis
	 * @param title
	 * @param side one of AxisOperation.TOP, AxisOperation.BOTTOM, AxisOperation.LEFT, AxisOperation.RIGHT
	 * @throws Exception if the title is used for an axis already
	 */
	public static void createAxis(String plotName, final String title, final int side) throws Exception {
		getPlotterImpl().createAxis(plotName, title, side);
	}

	/**
	 * Remove axis by title.
	 * @param axisTitle
	 * @throws Exception if the axisTitle is not a real axis
	 */
	public static void removeAxis(String plotName, final String axisTitle) throws Exception {
		getPlotterImpl().removeAxis(plotName, axisTitle);
	}

	/**
	 * Set the active axis which subsequent plots will plot to.
	 * @param xAxisTitle
	 * @throws Exception if the axis title is not an existing axis
	 */
	public static void setActiveXAxis(String plotName, String xAxisTitle) throws Exception {
		getPlotterImpl().setActiveXAxis(plotName, xAxisTitle);
	}

	/**
	 * Set the active axis which subsequent plots will plot to.
	 * @param yAxisTitle
	 * @throws Exception if the axis title is not an existing axis
	 */
	public static void setActiveYAxis(String plotName, String yAxisTitle) throws Exception {
		getPlotterImpl().setActiveYAxis(plotName, yAxisTitle);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y axis
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset yAxis) throws Exception {
		getPlotterImpl().plot(plotName, yAxis);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y axis
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset yAxis, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, yAxis, xAxisName, yAxisName);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, final IDataset yAxis) throws Exception {
		getPlotterImpl().plot(plotName, title, yAxis);
	}

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
	public static void plot(String plotName, final String title, final IDataset yAxis, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, title, yAxis, xAxisName, yAxisName);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xAxis, final IDataset yAxis) throws Exception {
		getPlotterImpl().plot(plotName, xAxis, yAxis);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param xAxis2
	 *            The dataset to use as the X values for the second X axis
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xAxis, final IDataset xAxis2, final IDataset yAxis) throws Exception {
		getPlotterImpl().plot(plotName, xAxis, xAxis2, yAxis);
	}

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
	public static void plot(String plotName, final String title, final IDataset xAxis, final IDataset yAxis) throws Exception {
		getPlotterImpl().plot(plotName, title, xAxis, yAxis);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values (can be null)
	 * @param yAxes
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xAxis, final IDataset[] yAxes) throws Exception {
		getPlotterImpl().plot(plotName, xAxis, yAxes);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values (can be null)
	 * @param yAxes
	 *            The dataset to use as the Y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xAxis, final IDataset[] yAxes, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, xAxis, yAxes, xAxisName, yAxisName);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxis
	 *            The dataset to use as the X values
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, String title, final IDataset xAxis, IDataset[] yAxis) throws Exception {
		getPlotterImpl().plot(plotName, title, xAxis, yAxis);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
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
	public static void plot(String plotName, String title, final IDataset xAxis, IDataset[] yAxis, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, title, xAxis, yAxis, xAxisName, yAxisName);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xAxes
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		getPlotterImpl().plot(plotName, xAxes, yAxes);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xAxes
	 *            The dataset to use as the X values
	 * @param yAxes
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		getPlotterImpl().plot(plotName, title, xAxes, yAxes);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yAxis
	 *            The dataset to use as the Y values
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset yAxis) throws Exception {
		getPlotterImpl().updatePlot(plotName, yAxis);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxis
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset xAxis, final IDataset yAxis) throws Exception {
		getPlotterImpl().updatePlot(plotName, xAxis, yAxis);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param xAxis2
	 * @param yAxis
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset xAxis, final IDataset xAxis2, final IDataset yAxis) throws Exception {
		getPlotterImpl().updatePlot(plotName, xAxis, xAxis2, yAxis);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset xAxis, IDataset[] yAxes) throws Exception {
		getPlotterImpl().updatePlot(plotName, xAxis, yAxes);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		getPlotterImpl().updatePlot(plotName, xAxes, yAxes);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		getPlotterImpl().updatePlot(plotName, title, xAxes, yAxes);
	}

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param imageFileName
	 * @throws Exception
	 */
	public static void imagePlot(String plotName, String imageFileName) throws Exception {
		getPlotterImpl().imagePlot(plotName, imageFileName);
	}

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param image
	 * @throws Exception
	 */
	public static void imagePlot(String plotName, IDataset image) throws Exception {
		getPlotterImpl().imagePlot(plotName, image);
	}

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

	public static void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
		getPlotterImpl().imagePlot(plotName, xAxis, yAxis, image);
	}

	/**
	 * Allows the plotting of images to the defined view
	 * 
	 * @param plotName
	 * @param images
	 * @throws Exception
	 */
	public static void imagesPlot(String plotName, IDataset[] images) throws Exception {
		getPlotterImpl().imagesPlot(plotName, images);
	}

	/**
	 * Allows the plotting of images to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param yAxis
	 *            can be null
	 * @param images
	 * @throws Exception
	 */

	public static void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
		getPlotterImpl().imagesPlot(plotName, xAxis, yAxis, images);
	}

	/**
	 * Allows plotting of points of given size on a 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param size
	 * @throws Exception
	 */
	public static void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
		getPlotterImpl().scatter2DPlot(plotName, xCoords, yCoords, size);
	}

	/**
	 * Allows plotting of multiple sets of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param coordPairs
	 * @param sizes
	 * @throws Exception
	 */
	public static void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, int[] sizes)
			throws Exception {
		getPlotterImpl().scatter2DPlot(plotName, coordPairs, sizes);
	}

	/**
	 * Allows plotting of multiple sets of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param coordPairs
	 * @param sizes
	 * @throws Exception
	 */
	public static void scatter2DPlot(String plotName, AbstractCompoundDataset[] coordPairs, IDataset[] sizes)
			throws Exception {
		getPlotterImpl().scatter2DPlot(plotName, coordPairs, sizes);
	}

	/**
	 * Allows plotting of points of given sizes on a 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param sizes
	 * @throws Exception
	 */
	public static void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
			throws Exception {
		getPlotterImpl().scatter2DPlot(plotName, xCoords, yCoords, sizes);
	}

	public static void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
			throws Exception {
		getPlotterImpl().scatter2DPlotOver(plotName, xCoords, yCoords, sizes);
	}

	public static void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size)
			throws Exception {
		getPlotterImpl().scatter2DPlotOver(plotName, xCoords, yCoords, size);
	}

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
	public static void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		getPlotterImpl().scatter3DPlot(plotName, xCoords, yCoords, zCoords, size);
	}

	/**
	 * Allows plotting of points of given sizes on a 3D volume
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param sizes
	 * @throws Exception
	 */
	public static void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
			IDataset sizes) throws Exception {
		getPlotterImpl().scatter3DPlot(plotName, xCoords, yCoords, zCoords, sizes);
	}

	public static void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		getPlotterImpl().scatter3DPlotOver(plotName, xCoords, yCoords, zCoords, size);
	}

	public static void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
			IDataset sizes) throws Exception {
		getPlotterImpl().scatter3DPlotOver(plotName, xCoords, yCoords, zCoords, sizes);
	}

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param data
	 * @throws Exception
	 */
	public static void surfacePlot(String plotName, IDataset data) throws Exception {
		getPlotterImpl().surfacePlot(plotName, data);
	}

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 *            can be null
	 * @param data
	 * @throws Exception
	 */
	public static void surfacePlot(String plotName, IDataset xAxis, IDataset data) throws Exception {
		getPlotterImpl().surfacePlot(plotName, xAxis, data);
	}

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
	public static void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
		getPlotterImpl().surfacePlot(plotName, xAxis, yAxis, data);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
		getPlotterImpl().stackPlot(plotName, xAxis, yAxes);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxis
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset xAxis, IDataset[] yAxes, IDataset zAxis) throws Exception {
		getPlotterImpl().stackPlot(plotName, xAxis, yAxes, zAxis);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
		getPlotterImpl().stackPlot(plotName, xAxes, yAxes);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		getPlotterImpl().stackPlot(plotName, xAxes, yAxes, zAxis);
	}

	/**
	 * Update stack with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xAxes
	 * @param yAxes
	 * @param zAxis
	 * @throws Exception
	 */
	public static void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
		getPlotterImpl().updateStackPlot(plotName, xAxes, yAxes, zAxis);
	}

	/**
	 * Scan a directory and populate an image explorer view with any supported image formats
	 * 
	 * @param viewName
	 *            of image explorer
	 * @param pathname
	 * @return number of files found
	 * @throws Exception
	 */
	public static int scanForImages(String viewName, String pathname) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname);
	}

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

	public static int scanForImages(String viewName, String pathname, int maxFiles, int nthFile) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, maxFiles, nthFile);
	}

	/**
	 * Scan a directory and populate an image explorer view with any supported image formats in specified order
	 * 
	 * @param viewName
	 * @param pathname
	 * @param order
	 * @return number of files loaded
	 * @throws Exception
	 */
	public static int scanForImages(String viewName, String pathname, int order) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, order);
	}

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
	public static int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, order, suffices, gridColumns, rowMajor);
	}

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
	public static int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
			int gridColumns, boolean rowMajor) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, order, regex, suffices, gridColumns, rowMajor);
	}

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
	public static int scanForImages(String viewName, String pathname, int order, String[] suffices, int gridColumns,
			boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, order, suffices, gridColumns, rowMajor, maxFiles, jumpBetween);
	}

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
	public static int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		return getPlotterImpl().scanForImages(viewName, pathname, order, nameregex, suffices, gridColumns, rowMajor, maxFiles, jumpBetween);
	}

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
	public static void volumePlot(String viewName, String rawvolume, int headerSize, int voxelType, int xdim, int ydim,
			int zdim) throws Exception {
		getPlotterImpl().volumePlot(viewName, rawvolume, headerSize, voxelType, xdim, ydim, zdim);
	}

	/**
	 * Send volume data to remote renderer. Only single-element datasets of byte, short, int and float are directly
	 * supported. Other types are internally converted; first elements of compound datasets are extracted.
	 * 
	 * @param viewName
	 * @param volume
	 * @throws Exception
	 */
	public static void volumePlot(String viewName, IDataset volume) throws Exception {
		getPlotterImpl().volumePlot(viewName, volume);
	}

	/**
	 * Send volume data contained in given filename to remote renderer
	 * 
	 * @param viewName
	 * @param dsrvolume
	 *            Diamond Scisoft raw format filename
	 * @throws Exception
	 */
	public static void volumePlot(String viewName, String dsrvolume) throws Exception {
		getPlotterImpl().volumePlot(viewName, dsrvolume);
	}

	/**
	 * Clear/empty a current plot view
	 * 
	 * @throws Exception
	 */
	public static void clearPlot(String viewName) throws Exception {
		getPlotterImpl().clearPlot(viewName);
	}

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
	public static void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
		getPlotterImpl().setupNewImageGrid(viewName, gridRows, gridColumns);
	}

	/**
	 * Set up a new image grid for an image explorer view with the specified # images
	 * 
	 * @param viewName
	 * @param images
	 *            number of images
	 * @throws Exception
	 */
	public static void setupNewImageGrid(String viewName, int images) throws Exception {
		getPlotterImpl().setupNewImageGrid(viewName, images);
	}

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param datasets
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, datasets);
	}

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param datasets
	 * @param store
	 *            if true, create copies of images as temporary files
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, datasets, store);
	}

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

	public static void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, filename, gridX, gridY);
	}

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param filename
	 * @throws Exception
	 */

	public static void plotImageToGrid(String viewName, String filename) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, filename);
	}

	/**
	 * Plot an image to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param dataset
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset dataset) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, dataset);
	}

	/**
	 * Plot an image to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param dataset
	 * @param store
	 *            if true, create a copy of image as a temporary file
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset dataset, boolean store) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, dataset, store);
	}

	/**
	 * Plot an image to the grid of an image explorer view in specified position
	 * 
	 * @param viewName
	 * @param dataset
	 * @param gridX
	 * @param gridY
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, dataset, gridX, gridY);
	}

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
	public static void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, dataset, gridX, gridY, store);
	}

	/**
	 * @param viewer
	 * @param tree
	 * @throws Exception
	 */
	public static void viewNexusTree(String viewer, HDF5File tree) throws Exception {
		getPlotterImpl().viewHDF5Tree(viewer, tree);
	}

	/**
	 * @param viewer
	 * @param tree
	 * @throws Exception
	 */
	public static void viewHDF5Tree(String viewer, HDF5File tree) throws Exception {
		getPlotterImpl().viewHDF5Tree(viewer, tree);
	}

	/**
	 * General way to send a gui bean to plot server
	 * 
	 * @param plotName
	 * @param bean
	 * @throws Exception
	 */
	public static void setGuiBean(String plotName, GuiBean bean) throws Exception {
		getPlotterImpl().setGuiBean(plotName, bean);
	}

	/**
	 * General way to grab a gui bean from plot server
	 * 
	 * @param plotName
	 * @return a GuiBean from a named plot
	 * @throws Exception
	 */
	public static GuiBean getGuiBean(String plotName) throws Exception {
		return getPlotterImpl().getGuiBean(plotName);
	}

	/**
	 * General way to send a data bean to plot server
	 * 
	 * @param plotName
	 * @param bean
	 * @throws Exception
	 */
	public static void setDataBean(String plotName, DataBean bean) throws Exception {
		getPlotterImpl().setDataBean(plotName, bean);
	}

	/**
	 * General way to grab a data bean from plot server
	 * 
	 * @param plotName
	 * @return a DataBean from a named plot
	 * @throws Exception
	 */
	public static DataBean getDataBean(String plotName) throws Exception {
		return getPlotterImpl().getDataBean(plotName);
	}

	/**
	 * @return GUI bean for named view of given plot mode or create a new one
	 */
	public static GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode) {
		return getPlotterImpl().getGuiStateForPlotMode(plotName, plotMode);
	}

	/**
	 * Get a list of all Gui Names the Plot Server knows about
	 * 
	 * @return array of all names
	 * @throws Exception
	 */
	public static String[] getGuiNames() throws Exception {
		return getPlotterImpl().getGuiNames();
	}

}
