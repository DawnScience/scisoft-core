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
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
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
	 * Rename the active X-axis
	 * @param xAxisTitle
	 * @throws Exception if the axis title is not an existing axis
	 */
	public static void renameActiveXAxis(String plotName, String xAxisTitle) throws Exception {
		getPlotterImpl().renameActiveXAxis(plotName, xAxisTitle);
	}

	/**
	 * Rename the active Y-axis
	 * @param yAxisTitle
	 * @throws Exception if the axis title is not an existing axis
	 */
	public static void renameActiveYAxis(String plotName, String yAxisTitle) throws Exception {
		getPlotterImpl().renameActiveYAxis(plotName, yAxisTitle);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset yValues) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateXValues(null, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateXValues(null, yValues), new IDataset[] { yValues }, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, final IDataset yValues) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateXValues(null, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, final IDataset yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateXValues(null, yValues), new IDataset[] { yValues }, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xValues, final IDataset yValues) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateXValues(xValues, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, final IDataset xValues, final IDataset yValues) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateXValues(xValues, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values (can be null)
	 * @param yValues
	 *            The datasets to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xValues, final IDataset[] yValues) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values (can be null)
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final IDataset xValues, final IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateXValues(xValues, yValues), yValues, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The datasets to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, String title, final IDataset xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The datasets to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, String title, final IDataset xValues, IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateXValues(xValues, yValues), yValues, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, IDataset[] xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().plot(plotName, null, SDAPlotterImpl.validateAllXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().plot(plotName, title, SDAPlotterImpl.validateAllXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().plot(plotName, title, xValues, yValues, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisNames
	 *            The names of x axes, null if none
	 * @param yAxisNames
	 *            The names of y axes, null if none
	 * @throws Exception
	 */
	public static void plot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String[] xAxisNames, final String[] yAxisNames) throws Exception {
		getPlotterImpl().plot(plotName, title, xValues, yValues, xAxisNames, yAxisNames);
	}

	/**
	 * Add plots to existing plots
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisNames
	 *            The names of x axes, null if none
	 * @param yAxisNames
	 *            The names of y axes, null if none
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void addPlot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().addPlot(plotName, title, xValues, yValues, new String[] {xAxisName}, new String[] {yAxisName});
	}

	/**
	 * Add plots to existing plots
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisNames
	 *            The names of x axes, null if none
	 * @param yAxisNames
	 *            The names of y axes, null if none
	 * @throws Exception
	 */
	public static void addPlot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String[] xAxisNames, final String[] yAxisNames) throws Exception {
		getPlotterImpl().addPlot(plotName, title, xValues, yValues, xAxisNames, yAxisNames);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 *            The name of the view to plot to
	 * @param yValues
	 *            The dataset to use as the y values
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset yValues) throws Exception {
		getPlotterImpl().updatePlot(plotName, null, SDAPlotterImpl.validateXValues(null, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset xValues, final IDataset yValues) throws Exception {
		getPlotterImpl().updatePlot(plotName, null, SDAPlotterImpl.validateXValues(xValues, yValues), new IDataset[] { yValues }, null, null);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final IDataset xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().updatePlot(plotName, null, SDAPlotterImpl.validateXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, IDataset[] xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().updatePlot(plotName, null, SDAPlotterImpl.validateAllXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * Update existing plot with new data
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, String title, IDataset[] xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().updatePlot(plotName, title, SDAPlotterImpl.validateAllXValues(xValues, yValues), yValues, null, null);
	}

	/**
	 * Update existing plot with new data
	 * @param plotName
	 *            The name of the view to plot to
	 * @param title
	 *            The title of the plot
	 * @param xValues
	 *            The dataset to use as the x values
	 * @param yValues
	 *            The dataset to use as the y values
	 * @param xAxisName
	 *            The name of x-Axis, null if none
	 * @param yAxisName
	 *            The name of the dataset, null if none
	 * @throws Exception
	 */
	public static void updatePlot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		getPlotterImpl().updatePlot(plotName, title, xValues, yValues, xAxisName, yAxisName);
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
		getPlotterImpl().imagePlot(plotName, null, null, image);
	}

	/**
	 * Allows the plotting of an image to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 *            can be null
	 * @param yValues
	 *            can be null
	 * @param image
	 * @throws Exception
	 */

	public static void imagePlot(String plotName, IDataset xValues, IDataset yValues, IDataset image) throws Exception {
		getPlotterImpl().imagePlot(plotName, xValues, yValues, image);
	}

	/**
	 * Allows the plotting of images to the defined view
	 * 
	 * @param plotName
	 * @param images
	 * @throws Exception
	 */
	public static void imagesPlot(String plotName, IDataset[] images) throws Exception {
		getPlotterImpl().imagesPlot(plotName, null, null, images);
	}

	/**
	 * Allows the plotting of images to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 *            can be null
	 * @param yValues
	 *            can be null
	 * @param images
	 * @throws Exception
	 */
	public static void imagesPlot(String plotName, IDataset xValues, IDataset yValues, IDataset[] images) throws Exception {
		getPlotterImpl().imagesPlot(plotName, xValues, yValues, images);
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
		if (xCoords != null && xCoords.getRank() != 1 || xCoords == null) {
			String msg = String.format("X axis dataset is wrong format or null");
			throw new Exception(msg);
		}

		IntegerDataset sizes = new IntegerDataset(xCoords.getSize());
		sizes.fill(size);

		getPlotterImpl().scatter2DPlot(plotName, xCoords, yCoords, sizes);
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
		IntegerDataset[] pSizes = new IntegerDataset[sizes.length];
		for (int i = 0; i < sizes.length; i++) {
			pSizes[i] = new IntegerDataset(coordPairs[i].getShape()[0]);
			pSizes[i].fill(sizes[i]);
		}

		getPlotterImpl().scatter2DPlot(plotName, coordPairs, pSizes);
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
	 * Allows plotting of points of given sizes over an existing 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param sizes
	 * @throws Exception
	 */
	public static void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
			throws Exception {
		getPlotterImpl().scatter2DPlotOver(plotName, xCoords, yCoords, sizes);
	}

	/**
	 * Allows plotting of points of given sizes over an existing 2D grid
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param size
	 * @throws Exception
	 */
	public static void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size)
			throws Exception {
		if (xCoords != null && xCoords.getRank() != 1 || xCoords == null) {
			String msg = String.format("X axis dataset is wrong format or null");
			throw new Exception(msg);
		}

		IntegerDataset sizes = new IntegerDataset(xCoords.getSize());
		sizes.fill(size);
		getPlotterImpl().scatter2DPlotOver(plotName, xCoords, yCoords, sizes);
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
		if (xCoords != null && xCoords.getRank() != 1 || xCoords == null) {
			String msg = String.format("X axis dataset is wrong format or null");
			throw new Exception(msg);
		}

		IntegerDataset sizes = new IntegerDataset(xCoords.getSize());
		sizes.fill(size);
		getPlotterImpl().scatter3DPlot(plotName, xCoords, yCoords, zCoords, sizes);
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

	/**
	 * Allows plotting of points of given sizes over an existing 3D volume
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param zCoords
	 * @param size
	 * @throws Exception
	 */
	public static void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
			throws Exception {
		if (xCoords != null && xCoords.getRank() != 1 || xCoords == null) {
			String msg = String.format("X axis dataset is wrong format or null");
			throw new Exception(msg);
		}

		IntegerDataset sizes = new IntegerDataset(xCoords.getSize());
		sizes.fill(size);
		getPlotterImpl().scatter3DPlotOver(plotName, xCoords, yCoords, zCoords, sizes);
	}

	/**
	 * Allows plotting of points of given sizes over an existing 3D volume
	 * 
	 * @param plotName
	 * @param xCoords
	 * @param yCoords
	 * @param zCoords
	 * @param sizes
	 * @throws Exception
	 */
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
		getPlotterImpl().surfacePlot(plotName, null, null, data);
	}

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 *            can be null
	 * @param data
	 * @throws Exception
	 */
	public static void surfacePlot(String plotName, IDataset xValues, IDataset data) throws Exception {
		getPlotterImpl().surfacePlot(plotName, xValues, null, data);
	}

	/**
	 * Allows the plotting of a 2D dataset as a surface to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 *            can be null
	 * @param yValues
	 *            can be null
	 * @param data
	 * @throws Exception
	 */
	public static void surfacePlot(String plotName, IDataset xValues, IDataset yValues, IDataset data) throws Exception {
		getPlotterImpl().surfacePlot(plotName, xValues, yValues, data);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().stackPlot(plotName, new IDataset[] {xValues}, yValues, null);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @param zAxis
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset xValues, IDataset[] yValues, IDataset zAxis) throws Exception {
		getPlotterImpl().stackPlot(plotName, new IDataset[] {xValues}, yValues, zAxis);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset[] xValues, IDataset[] yValues) throws Exception {
		getPlotterImpl().stackPlot(plotName, xValues, yValues, null);
	}

	/**
	 * Plot a stack in 3D of single 1D plots to the defined view
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @param zAxis
	 * @throws Exception
	 */
	public static void stackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zAxis) throws Exception {
		getPlotterImpl().stackPlot(plotName, xValues, yValues, zAxis);
	}

	/**
	 * Update stack with new data, keeping zoom level
	 * 
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @param zAxis
	 * @throws Exception
	 */
	public static void updateStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zAxis) throws Exception {
		getPlotterImpl().updateStackPlot(plotName, xValues, yValues, zAxis);
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
		return getPlotterImpl().scanForImages(viewName, pathname, ISDAPlotter.IMAGEORDERNONE, null, ISDAPlotter.LISTOFSUFFIX, -1, true, Integer.MAX_VALUE, 1);
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
		return getPlotterImpl().scanForImages(viewName, pathname, ISDAPlotter.IMAGEORDERNONE, null, ISDAPlotter.LISTOFSUFFIX, -1, true, maxFiles, nthFile);
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
		return getPlotterImpl().scanForImages(viewName, pathname, order, null, ISDAPlotter.LISTOFSUFFIX, -1, true, Integer.MAX_VALUE, 1);
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
		return getPlotterImpl().scanForImages(viewName, pathname, order, null, suffices, gridColumns, rowMajor, Integer.MAX_VALUE, 1);
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
		return getPlotterImpl().scanForImages(viewName, pathname, order, regex, suffices, gridColumns, rowMajor, Integer.MAX_VALUE, 1);
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
		return getPlotterImpl().scanForImages(viewName, pathname, order, null, suffices, gridColumns, rowMajor, maxFiles, jumpBetween);
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
	 * Clear/empty a named plot view
	 * 
	 * @param plotName
	 * @throws Exception
	 */
	public static void clearPlot(String plotName) throws Exception {
		getPlotterImpl().clearPlot(plotName);
	}

	/**
	 * Export a named plot view
	 * 
	 * @param plotName
	 * @param fileFormat
	 * @param saveFullPath
	 * @throws Exception
	 */
	public static void exportPlot(String plotName, String fileFormat, String saveFullPath) throws Exception {
		getPlotterImpl().exportPlot(plotName, fileFormat, saveFullPath);
	}

	/**
	 * Reset axes in named plot view
	 * 
	 * @param plotName
	 * @throws Exception
	 */
	public static void resetAxes(String plotName) throws Exception {
		getPlotterImpl().resetAxes(plotName);
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
		int gridRows = (int) Math.ceil(Math.sqrt(images));
		if (gridRows == 0)
			gridRows = 1;
		getPlotterImpl().setupNewImageGrid(viewName, gridRows, gridRows);
	}

	/**
	 * Plot images to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param datasets
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset[] datasets) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, datasets, false);
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
		getPlotterImpl().plotImageToGrid(viewName, filename, -1, -1);
	}

	/**
	 * Plot an image to the grid of an image explorer view
	 * 
	 * @param viewName
	 * @param dataset
	 * @throws Exception
	 */
	public static void plotImageToGrid(String viewName, IDataset dataset) throws Exception {
		getPlotterImpl().plotImageToGrid(viewName, dataset, -1, -1, false);
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
		getPlotterImpl().plotImageToGrid(viewName, dataset, -1, -1, store);
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
		getPlotterImpl().plotImageToGrid(viewName, dataset, gridX, gridY, false);
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
