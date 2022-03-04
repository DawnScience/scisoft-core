/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.RGBByteDataset;
import org.eclipse.january.dataset.RGBDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.RawBinarySaver;
import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.plotserver.AxisOperation;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBeanException;
import uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

/**
 * Normal implementation of {@link ISDAPlotter} used by delegator class {@link SDAPlotter} 
 */
public class SDAPlotterImpl implements ISDAPlotter {
	private static final Logger logger = LoggerFactory.getLogger(SDAPlotterImpl.class);

	private static ISDAPlotter instance;
	/**
	 * Get the default or "normal" instance of the SDAPlotter to use.
	 * @return instance of SDAPlotter
	 */
	public synchronized static ISDAPlotter getDefaultInstance() {
		if (instance == null) {
			instance = new SDAPlotterImpl();
		}
		return instance;
	}

	/**
	 * All locations within SDAPlotter that need a PlotService should get it with this method. This method will collect
	 * a PlotService from the provider.
	 * <p>
	 * Note this method exists to funnel all accesses to the PlotService via here, its purpose is to allow subclasses of
	 * SDAPlotterImpl to provide their own PlotService and keep all dependencies on a static method calls to one place.
	 * 
	 * @return PlotService
	 */
	protected PlotService getPlotService() {
		return PlotServiceProvider.getPlotService();
	}

	/** 
	 * Not strictly private to enable other implementation of ISDAPlotter to
	 * extend this one, such as versions used in test.
	 */
	protected SDAPlotterImpl() {
	}

	private boolean isDataND(IDataset data, int dim) {
		return data.getRank() == dim;
	}

	static IDataset[] validateXValues(final IDataset xValues, final IDataset... yValues) {
		if (xValues == null) {
			if (yValues == null || yValues.length == 0) {
				logger.error("No datasets specified");
				throw new IllegalArgumentException("No datasets specified");
			}
			int max = 0;
			for (IDataset y : yValues) {
				if (y != null) {
					int s = y.getSize();
					if (s > max)
						max = s;
				}
			}
			return new IDataset[] { DatasetFactory.createRange(IntegerDataset.class, max) };
		}

		return new IDataset[] { xValues };
	}

	static IDataset[] validateAllXValues(final IDataset[] xValues, final IDataset... yValues) {
		if (xValues == null) {
			return validateXValues(null, yValues);
		}

		return xValues;
	}

	@Override
	public void plot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String[] yLabels, final String[] xAxisNames, final String[] yAxisNames) throws Exception {
		lplot(plotName, title, validateAllXValues(xValues, yValues), yValues, yLabels, xAxisNames, yAxisNames, GuiParameters.PLOTOP_NONE);
	}

	@Override
	public void addPlot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String[] yLabels, final String[] xAxisNames, final String[] yAxisNames) throws Exception {
		lplot(plotName, title, validateAllXValues(xValues, yValues), yValues, yLabels, xAxisNames, yAxisNames, GuiParameters.PLOTOP_ADD);
	}

	@Override
	public void updatePlot(String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String xAxisName, final String yAxisName) throws Exception {
		lplot(plotName, title, validateAllXValues(xValues, yValues), yValues, null, new String[] {xAxisName}, new String[] {yAxisName}, GuiParameters.PLOTOP_UPDATE);
	}

	private final static String EMPTY = "";

	/**
	 * Plot line(s) in named view
	 * @param plotName
	 * @param title (can be null)
	 * @param xValues
	 * @param yValues
	 * @param yLabels (can be null)
	 * @param xAxisNames (can be null)
	 * @param yAxisNames (can be null)
	 * @param plotOperation one of GuiParameters.PLOTOP_*
	 * @throws Exception
	 */
	private void lplot(final String plotName, final String title, IDataset[] xValues, IDataset[] yValues, final String[] yLabels, final String[] xAxisNames, final String[] yAxisNames, final String plotOperation) throws Exception {
		if (yValues.length == 0) {
			return;
		}
		for (IDataset x : xValues) {
			if (!isDataND(x, 1)) {
				logger.error("Input x dataset has incorrect rank: it has {} dimensions when it should be 1",
						x.getRank());
				throw new Exception("Input x dataset has incorrect rank: it should be 1");
			}
		}
		for (IDataset y : yValues) {
			if (!isDataND(y, 1)) {
				logger.error("Input y dataset has incorrect rank: it has {} dimensions when it should be 1",
						y.getRank());
				throw new Exception("Input y dataset has incorrect rank: it should be 1");
			}
		}
		if (yLabels != null && yLabels.length != yValues.length) {
			logger.error("Number of y labels ({}) should match number of y datasets ({})", yLabels.length, yValues.length);
			throw new Exception("Number of y labels should match number of y datasets");
		}

		logger.info("Plot sent to {}", plotName);

		// Create the beans to transfer the data
		DataBean dataBean = new DataBean(GuiPlotMode.ONED);
		if (xValues.length == 1) {
			String xid = AxisMapBean.XAXIS;
			String xan = null;
			if (xAxisNames != null && xAxisNames.length > 0) {
				xan = xAxisNames[0];
				xValues[0].setName(EMPTY); // empty dataset name to avoid confusion
			} else {
				xan = AxisMapBean.XAXIS;
			}
			dataBean.addAxis(xid, xValues[0]);
			for (int i = 0; i < yValues.length; i++) {
				try {
					String yan = null;
					if (yAxisNames != null && yAxisNames.length >= yValues.length) {
						yan = yAxisNames[i];
					} else {
						yan = AxisMapBean.YAXIS;
					}
					IDataset yd = renameDataset(yValues[i], yLabels == null ? null : yLabels[i]);
					dataBean.addData(DatasetWithAxisInformation.createAxisDataSet(yd, new String[] {xid}, new String[] {xan, yan}));
				} catch (DataBeanException e) {
					logger.error("Problem adding data to bean as axis key does not exist");
					e.printStackTrace();
				}
			}
		} else {
			if (xValues.length != yValues.length)
				throw new IllegalArgumentException("# xValues does not match # yValues");
			Map<String, IDataset> cache = new HashMap<String, IDataset>();
			int l = 0; // last axis number
			for (int i = 0; i < xValues.length; i++) {
				IDataset x = xValues[i];
				String xid = null;
				for (String s : cache.keySet()) {
					if (cache.get(s) == x) {
						xid = s;
						break;
					}
				}
				if (xid == null) {
					xid = l == 0 ? AxisMapBean.XAXIS : AxisMapBean.XAXIS + l;
					l++;
					cache.put(xid, x);
				}
				String xan = null;
				if (xAxisNames != null) {
					xan = xAxisNames.length >= xValues.length ? xAxisNames[i] : xAxisNames[0];
					x.setName(EMPTY); 
				} else {
					xan = AxisMapBean.XAXIS; // single axis
				}
				String yan = null;
				if (yAxisNames != null && yAxisNames.length >= yValues.length) {
					yan = yAxisNames[i];
				} else {
					yan = AxisMapBean.YAXIS; // single axis
				}
				IDataset yd = renameDataset(yValues[i], yLabels == null ? null : yLabels[i]);
				dataBean.addAxis(xid, x);
				// now add it to the plot data
				try {
					dataBean.addData(DatasetWithAxisInformation.createAxisDataSet(yd, new String[] {xid}, new String[] {xan, yan}));
				} catch (DataBeanException e) {
					logger.error("Problem adding data to bean as axis key does not exist");
					e.printStackTrace();
				}
			}
			cache.clear();
		}

		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, plotOperation);

		if (title != null)
			dataBean.putGuiParameter(GuiParameters.TITLE, title);

		setDataBean(plotName, dataBean);
	}

	private static IDataset renameDataset(IDataset d, String n) {
		if (n != null && n.length() > 0) {
			d = d.clone();
			d.setName(n);
		}
		return d;
	}

	@Override
	public void imagePlot(String plotName, String imageFileName) throws Exception {

		IDataHolder dh = null;

		try {
			// This is the bit that can take a while.
			dh = LoaderFactory.getData(imageFileName, null);

		} catch (Exception ne) {
			logger.error("Cannot load file " + imageFileName, ne);
			throw ne;
		}

		try {
			IDataset dataSet = dh.getDataset(0);
			dataSet.setName(imageFileName);
			imagePlot(plotName, null, null, dataSet, null, null);
		} catch (Exception e) {
			logger.error("Cannot plot non-image file from  " + imageFileName, e);
			throw e;
		}
	}

	@Override
	public void imagePlot(String plotName, IDataset xValues, IDataset yValues, IDataset image, String xAxisName, String yAxisName) throws Exception {
		if (isDataND(image, 3) && image.getShape()[2] == 3) { // hack for RGB ndarrays from python
			CompoundDataset compound = DatasetUtils.createCompoundDatasetFromLastAxis(DatasetUtils.convertToDataset(image), true);
			image = compound.getElementClass().equals(Byte.class) ? RGBByteDataset.createFromCompoundDataset(compound) :
				RGBDataset.createFromCompoundDataset(compound);
		}
		if (!isDataND(image, 2)) {
			logger.error("Input dataset has incorrect rank: it has {} dimensions when it should be 2", image.getRank());
			throw new Exception("Input dataset has incorrect rank: it should be 2");
		}
		if (xValues != null && !isDataND(xValues, 1)) {
			String msg = String.format("X axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					xValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}
		if (yValues != null && !isDataND(yValues, 1)) {
			String msg = String.format("Y axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					yValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}
		DataBean dataBean = new DataBean(GuiPlotMode.TWOD);

		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		axisData.setData(image);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xValues != null) {
			IDataset xd = renameDataset(xValues, xAxisName);
			dataBean.addAxis(AxisMapBean.XAXIS, xd);
		}
		if (yValues != null) {
			IDataset yd = renameDataset(yValues, yAxisName);
			dataBean.addAxis(AxisMapBean.YAXIS, yd);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void imagesPlot(String plotName, IDataset xValues, IDataset yValues, IDataset[] images) throws Exception {
		if (!isDataND(images[0], 2)) {
			logger.error("Input dataset has incorrect rank: it has {} dimensions when it should be 2",
					images[0].getRank());
			throw new Exception("Input dataset has incorrect rank: it should be 2");
		}
		if (xValues != null && !isDataND(xValues, 1)) {
			String msg = String.format("X axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					xValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yValues != null && !isDataND(yValues, 1)) {
			String msg = String.format("Y axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					yValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}
		DataBean dataBean = new DataBean(GuiPlotMode.MULTI2D);
		for (int i = 0; i < images.length; i++) {
			DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
			AxisMapBean amb = new AxisMapBean();
			axisData.setAxisMap(amb);
			axisData.setData(images[i]);
			try {
				dataBean.addData(axisData);
			} catch (DataBeanException e) {
				e.printStackTrace();
			}
		}
		if (xValues != null) {
			dataBean.addAxis(AxisMapBean.XAXIS, xValues);
		}
		if (yValues != null) {
			dataBean.addAxis(AxisMapBean.YAXIS, yValues);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void scatter2DPlot(String plotName, CompoundDataset[] coordPairs, IDataset[] sizes)
			throws Exception {
		if (coordPairs.length != sizes.length) {
			String msg = String.format("# of coordPairs does not match # of sizes (%d != %d)", coordPairs.length,
					sizes.length);
			logger.error(msg);
			throw new Exception(msg);
		}
		DataBean dataBean = new DataBean(GuiPlotMode.SCATTER2D);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, GuiParameters.PLOTOP_NONE);
		for (int i = 0; i < sizes.length; i++) {
			CompoundDataset coordData = coordPairs[i];
			if (coordData.getElementsPerItem() != 2) {
				String msg = String.format("# of elements of coordPair does not equal two it is %d",
						coordData.getElementsPerItem());
				logger.error(msg);
				throw new Exception(msg);
			}
			if (coordData.getShape().length != 1) {
				String msg = String.format("shape of coordpair does not match (expected one but got %d)",
						coordData.getShape().length);
				logger.error(msg);
				throw new Exception(msg);
			}
			Dataset xCoord = coordData.getElements(0);
			Dataset yCoord = coordData.getElements(1);
			DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
			AxisMapBean amb = new AxisMapBean();
			axisData.setAxisMap(amb);
			axisData.setData(sizes[i]);
			dataBean.addData(axisData);
			dataBean.addAxis(AxisMapBean.XAXIS + Integer.toString(i), xCoord);
			dataBean.addAxis(AxisMapBean.YAXIS + Integer.toString(i), yCoord);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
			throws Exception {
		if (xCoords != null && !isDataND(xCoords, 1)) {
			String msg = String.format("X coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					xCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yCoords != null && !isDataND(yCoords, 1)) {
			String msg = String.format("Y coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					yCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		DataBean dataBean = new DataBean(GuiPlotMode.SCATTER2D);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, GuiParameters.PLOTOP_NONE);
		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		axisData.setData(sizes);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xCoords != null) {
			dataBean.addAxis(AxisMapBean.XAXIS + "0", xCoords);
		}
		if (yCoords != null) {
			dataBean.addAxis(AxisMapBean.YAXIS + "0", yCoords);
		}

		setDataBean(plotName, dataBean);
	}

	// temporary(?) fix for plotting over too quickly when some additional points are omitted
	private static final int REST_BETWEEN_PLOTOVERS = 6;

	@Override
	public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
			throws Exception {
		if (xCoords != null && !isDataND(xCoords, 1) || xCoords == null) {
			String msg = String.format("X coords dataset has incorrect rank or is null");
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yCoords != null && !isDataND(yCoords, 1) || yCoords == null) {
			String msg = String.format("Y coords dataset has incorrect rank or is null");
			logger.error(msg);
			throw new Exception(msg);
		}

		Thread.sleep(REST_BETWEEN_PLOTOVERS);

		DataBean dataBean = new DataBean(GuiPlotMode.SCATTER2D);
		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, GuiParameters.PLOTOP_UPDATE);
		axisData.setData(sizes);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		dataBean.addAxis(AxisMapBean.XAXIS + "0", xCoords);
		dataBean.addAxis(AxisMapBean.YAXIS + "0", yCoords);

		setDataBean(plotName, dataBean);
	}

	@Override
	public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
			IDataset sizes) throws Exception {
		if (xCoords != null && !isDataND(xCoords, 1)) {
			String msg = String.format("X coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					xCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yCoords != null && !isDataND(yCoords, 1)) {
			String msg = String.format("Y coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					yCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (zCoords != null && !isDataND(zCoords, 1)) {
			String msg = String.format("Z coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					zCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		DataBean dataBean = new DataBean(GuiPlotMode.SCATTER3D);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, GuiParameters.PLOTOP_NONE);
		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		axisData.setData(sizes);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xCoords != null) {
			dataBean.addAxis(AxisMapBean.XAXIS, xCoords);
		}
		if (yCoords != null) {
			dataBean.addAxis(AxisMapBean.YAXIS, yCoords);
		}
		if (zCoords != null) {
			dataBean.addAxis(AxisMapBean.ZAXIS, zCoords);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
			IDataset sizes) throws Exception {
		if (xCoords != null && !isDataND(xCoords, 1)) {
			String msg = String.format("X coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					xCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yCoords != null && !isDataND(yCoords, 1)) {
			String msg = String.format("Y coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					yCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (zCoords != null && !isDataND(zCoords, 1)) {
			String msg = String.format("Z coords dataset has incorrect rank: it has %d dimensions when it should be 1",
					zCoords.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		Thread.sleep(REST_BETWEEN_PLOTOVERS);

		DataBean dataBean = new DataBean(GuiPlotMode.SCATTER3D);
		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, GuiParameters.PLOTOP_UPDATE);
		axisData.setData(sizes);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xCoords != null) {
			dataBean.addAxis(AxisMapBean.XAXIS, xCoords);
		}
		if (yCoords != null) {
			dataBean.addAxis(AxisMapBean.YAXIS, yCoords);
		}
		if (zCoords != null) {
			dataBean.addAxis(AxisMapBean.ZAXIS, zCoords);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void surfacePlot(String plotName, IDataset xValues, IDataset yValues, IDataset data) throws Exception {
		if (!isDataND(data, 2)) {
			logger.error("Input dataset has incorrect rank: it has {} dimensions when it should be 2", data.getRank());
			throw new Exception("Input dataset has incorrect rank: it should be 2");
		}

		if (xValues != null && !isDataND(xValues, 1)) {
			String msg = String.format("X axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					xValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		if (yValues != null && !isDataND(yValues, 1)) {
			String msg = String.format("Y axis dataset has incorrect rank: it has %d dimensions when it should be 1",
					yValues.getRank());
			logger.error(msg);
			throw new Exception(msg);
		}

		AxisMapBean amb = new AxisMapBean();

		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		axisData.setAxisMap(amb);
		axisData.setData(data);

		DataBean dataBean = new DataBean(GuiPlotMode.SURF2D);

		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xValues != null) {
			dataBean.addAxis(AxisMapBean.XAXIS, xValues);
		}
		if (yValues != null) {
			dataBean.addAxis(AxisMapBean.YAXIS, yValues);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public void stackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, final IDataset zValues) throws Exception {
		lstackPlot(plotName, validateAllXValues(xValues, yValues), yValues, zValues, GuiParameters.PLOTOP_NONE);
	}

	@Override
	public void addStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, final IDataset zValues) throws Exception {
		lstackPlot(plotName, validateAllXValues(xValues, yValues), yValues, zValues, GuiParameters.PLOTOP_ADD);
	}

	@Override
	public void updateStackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, final IDataset zValues) throws Exception {
		lstackPlot(plotName, validateAllXValues(xValues, yValues), yValues, zValues, GuiParameters.PLOTOP_UPDATE);
	}

	/**
	 * Plot a stack in 3D of 1D line plots to named view
	 * @param plotName
	 * @param xValues
	 * @param yValues
	 * @param zValues
	 * @param plotOperation one of GuiParameters.PLOTOP_*
	 * @param updateMode if true, keep zoom settings
	 * @throws Exception
	 */
	private void lstackPlot(String plotName, IDataset[] xValues, IDataset[] yValues, IDataset zValues, String plotOperation) throws Exception {
		for (IDataset x : xValues) {
			if (!isDataND(x, 1)) {
				logger.error("Input x dataset has incorrect rank: it has {} dimensions when it should be 1",
						x.getRank());
				throw new Exception("Input x dataset has incorrect rank: it should be 1");
			}
		}

		for (IDataset y : yValues) {
			if (!isDataND(y, 1)) {
				logger.error("Input y dataset has incorrect rank: it has {} dimensions when it should be 1",
						y.getRank());
				throw new Exception("Input y dataset has incorrect rank: it should be 1");
			}
		}

		DataBean dataBean = new DataBean(GuiPlotMode.ONED_THREED);
		dataBean.putGuiParameter(GuiParameters.PLOTOPERATION, plotOperation);
		if (xValues.length == 1) {
			dataBean.addAxis(AxisMapBean.XAXIS, xValues[0]);
			for (int i = 0; i < yValues.length; i++) {
				// now add it to the plot data
				try {
					dataBean.addData(DatasetWithAxisInformation.createAxisDataSet(yValues[i]));
				} catch (DataBeanException e) {
					e.printStackTrace();
				}
			}

		} else {
			if (xValues.length != yValues.length)
				throw new Exception("# xValues does not match # yValues");
			for (int i = 0; i < xValues.length; i++) {
				String axisStr = AxisMapBean.XAXIS + i;
				dataBean.addAxis(axisStr, xValues[i]);
				// now add it to the plot data
				try {
					dataBean.addData(DatasetWithAxisInformation.createAxisDataSet(yValues[i], axisStr));
				} catch (DataBeanException e) {
					e.printStackTrace();
				}
			}
		}

		if (zValues != null) {
			if (!isDataND(zValues, 1)) {
				logger.error("Input z dataset has incorrect rank: it has {} dimensions when it should be 1",
						zValues.getRank());
				throw new Exception("Input z dataset has incorrect rank: it should be 1");
			}
			dataBean.addAxis(AxisMapBean.ZAXIS, zValues);
		}

		setDataBean(plotName, dataBean);
	}

	@Override
	public int scanForImages(String viewName, String pathname, int order, String nameregex, String[] suffices,
			int gridColumns, boolean rowMajor, int maxFiles, int jumpBetween) throws Exception {
		File file = new File(pathname);
		PlotService plotServer = getPlotService();
		int filesPushed = 0;
		if (plotServer != null) {
			int numFiles = 0;
			if (file.isDirectory()) {
				// GuiBean guiBean = getGuiStateForPlotMode(viewName, GuiPlotMode.MULTI2D);
				GuiBean guiBean = new GuiBean();
				guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.IMGEXPL);
				File[] files = file.listFiles();
				if (suffices == null)
					suffices = LISTOFSUFFIX;
				List<String> imageFiles = filterImages(files, nameregex, suffices);
				int nImages = imageFiles.size();

				int gridRows = (int) (gridColumns > 0 ? Math.ceil(nImages / (double) gridColumns) : Math.ceil(Math
						.sqrt(nImages)));
				if (gridRows == 0)
					gridRows = 1;
				int gridCols = gridColumns > 0 ? gridColumns : gridRows;
				guiBean.put(GuiParameters.IMAGEGRIDSIZE, new Integer[] { gridRows, gridCols });
				plotServer.updateGui(viewName, guiBean);
				guiBean.remove(GuiParameters.IMAGEGRIDSIZE);

				if (nImages == 0)
					return filesPushed;

				switch (order) {
				case IMAGEORDERALPHANUMERICAL:
					Collections.sort(imageFiles); // could make faster by removing dirname first
					break;
				case IMAGEORDERCHRONOLOGICAL:
					chronoSort(imageFiles);
					break;
				case IMAGEORDERNONE:
					break;
				}
				Iterator<String> iter = imageFiles.iterator();
				if (rowMajor) {
					while (iter.hasNext() && numFiles < maxFiles) {
						String filename = iter.next();
						if (numFiles % jumpBetween == 0) {
							guiBean.put(GuiParameters.FILENAME, filename);
							plotServer.updateGui(viewName, guiBean);
							filesPushed++;
						}
						numFiles++;
					}
				} else {
					int x = 0;
					int y = 0;
					while (iter.hasNext() && numFiles < maxFiles) {
						String filename = iter.next();
						if (numFiles % jumpBetween == 0) {
							guiBean.put(GuiParameters.IMAGEGRIDXPOS, Integer.valueOf(x));
							guiBean.put(GuiParameters.IMAGEGRIDYPOS, Integer.valueOf(y));
							guiBean.put(GuiParameters.FILENAME, filename);
							plotServer.updateGui(viewName, guiBean);
							filesPushed++;
							y++;
						}
						numFiles++;
						if (y == gridRows) {
							y = 0;
							x++;
						}
					}
				}
			} else {
				logger.warn("Given path was not a directory");
			}
		}
		return filesPushed;
	}

	private List<String> filterImages(File[] files, String regex, String[] suffices) {
		List<String> listOfImages = new ArrayList<String>();
		if (suffices == null && regex == null) {
			for (int i = 0; i < files.length; i++) {
				listOfImages.add(files[i].getAbsolutePath());
			}
		} else {
			StringBuilder fullregex = new StringBuilder(regex == null ? ".*" : regex);
			if (suffices != null && suffices.length > 0) {
				if (suffices.length == 1) {
					fullregex.append(suffices[0]);
				} else {
					fullregex.append('(');
					for (String s : suffices) {
						fullregex.append(s);
						fullregex.append('|');
					}
					final int end = fullregex.length();
					fullregex.replace(end - 1, end, ")");
				}

				Pattern p = Pattern.compile(fullregex.toString());

				for (File f : files) {
					Matcher m = p.matcher(f.getName().toLowerCase());
					if (m.matches()) {
						listOfImages.add(f.getAbsolutePath());
						continue;
					}
				}
			}

		}
		return listOfImages;
	}

	private void chronoSort(List<String> imageFiles) {
		TreeMap<Long, String> imap = new TreeMap<Long, String>();

		for (String s : imageFiles) {
			imap.put(new File(s).lastModified(), s);
		}
		imageFiles.clear();
		imageFiles.addAll(imap.values());
	}

	public void volumePlot(String plotName, IDataset xValues, IDataset yValues, IDataset zValues, IDataset volume) throws Exception{
		DataBean dataBean = new DataBean(GuiPlotMode.VOLUME);

		DatasetWithAxisInformation axisData = new DatasetWithAxisInformation();
		AxisMapBean amb = new AxisMapBean();
		axisData.setAxisMap(amb);
		axisData.setData(volume);
		try {
			dataBean.addData(axisData);
		} catch (DataBeanException e) {
			e.printStackTrace();
		}

		if (xValues != null) {
			dataBean.addAxis(AxisMapBean.XAXIS, xValues);
		}
		if (yValues != null) {
			dataBean.addAxis(AxisMapBean.YAXIS, yValues);
		}
		
		if (zValues != null) {
			dataBean.addAxis(AxisMapBean.ZAXIS, zValues);
		}

		setDataBean(plotName, dataBean);
	}
	
	@Override
	public void clearPlot(String viewName) throws Exception {
		PlotService plotServer = getPlotService();
		if (plotServer != null) {
			GuiBean guiBean = new GuiBean();
			guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.EMPTY);
			plotServer.updateGui(viewName, guiBean);
			plotServer.setData(viewName, null); // remove data too
		}
	}

	@Override
	public void exportPlot(String viewName, String fileFormat, String saveFullPath) throws Exception {
		GuiBean guiBean = new GuiBean();
		guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.EXPORT);
		guiBean.put(GuiParameters.FILEFORMAT, fileFormat);
		guiBean.put(GuiParameters.SAVEPATH, saveFullPath);
		setGuiBean(viewName, guiBean);
	}

	@Override
	public void resetAxes(String viewName) throws Exception {
		GuiBean guiBean = new GuiBean();
		guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.RESETAXES);
		setGuiBean(viewName, guiBean);
	}

	@Override
	public void setupNewImageGrid(String viewName, int gridRows, int gridColumns) throws Exception {
//		GuiBean guiBean = new GuiBean();
//		guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.IMGEXPL);
//		guiBean.put(GuiParameters.IMAGEGRIDSIZE, new Integer[] { gridRows, gridColumns });
//		setGuiBean(viewName, guiBean);
		
		DataBean databean = new DataBean();
		databean.putGuiParameter(GuiParameters.PLOTMODE, GuiPlotMode.IMGEXPL);
		databean.putGuiParameter(GuiParameters.IMAGEGRIDSIZE, new Integer[] { gridRows, gridColumns });
		setDataBean(viewName, databean);
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset[] datasets, boolean store) throws Exception {
		for (int i = 0; i < datasets.length; i++) {
			setDataBean(viewName, "", datasets[i], -1, -1);
		}
	}

	@Override
	public void plotImageToGrid(String viewName, String filename, int gridX, int gridY) throws Exception {
		setDataBean(viewName, filename, null, gridX, gridY);
	}

	@Override
	public void plotImageToGrid(String viewName, IDataset dataset, int gridX, int gridY, boolean store)
			throws Exception {
		setDataBean(viewName, "", dataset, gridX, gridY);
	}

	private void setDataBean(String viewName, String filename, IDataset dataset, int gridX, int gridY) throws Exception {
		DataBean databean = new DataBean();
		List<DatasetWithAxisInformation> data = new ArrayList<DatasetWithAxisInformation>();
		DatasetWithAxisInformation d = new DatasetWithAxisInformation();
		if (dataset != null) {
			d.setData(dataset);
			data.add(d);
			databean.setData(data);
			databean.putGuiParameter(GuiParameters.FILENAME, dataset.getName());
		} else {
			databean.putGuiParameter(GuiParameters.FILENAME, filename);
		}
		databean.putGuiParameter(GuiParameters.PLOTMODE, GuiPlotMode.IMGEXPL);
		if (gridX >= 0 && gridY >= 0) {
			databean.putGuiParameter(GuiParameters.IMAGEGRIDXPOS, Integer.valueOf(gridX));
			databean.putGuiParameter(GuiParameters.IMAGEGRIDYPOS, Integer.valueOf(gridY));
		}
		setDataBean(viewName, databean);
	}

	protected String saveTempFile(DataHolder tHolder, IDataset dataset) throws ScanFileHolderException {
		try {
			File tmpFile = File.createTempFile("image-explorer-store-", ".raw");
			String rawFilename = tmpFile.getAbsolutePath();
			tHolder.setDataset("Data", dataset);
			new RawBinarySaver(rawFilename).saveFile(tHolder);
			return rawFilename;
		} catch (IOException e) {
			throw new ScanFileHolderException("Couldn't save file", e);
		}
	}

	@Override
	public void viewTree(String viewer, Tree tree) throws Exception {
		logger.info("Tree sent to {}", viewer);

		DataBean db = new DataBean(null);
		db.addTree(tree);
		setDataBean(viewer, db);
	}

	/**
	 * Simple method which forwards the data to the plot server
	 * 
	 * @param plotName
	 *            The name of the plot
	 * @param dataBean
	 *            The data to be passed to the plot server (can be null)
	 * @param guiBean
	 *            The gui data which is included for the Plot legend
	 * @throws Exception
	 */
	private void sendBeansToServer(String plotName, DataBean dataBean, GuiBean guiBean) throws Exception {
		PlotService plotServer = getPlotService();
		if (plotServer == null)
			return;

		if (guiBean != null) {
			guiBean.remove(GuiParameters.PLOTID); // remove any previous ID now it is being pushed to all the clients
			plotServer.updateGui(plotName, guiBean);
		}

		if (dataBean != null) {
			try {
				plotServer.setData(plotName, dataBean);
			} catch (Exception e) {
				try {
					logger.trace("Could not send data bean. Trying again without metadata", e);
					dataBean.removeMetadata();
					plotServer.setData(plotName, dataBean);
					logger.trace("Succeeded sending data bean without metadata.");
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("Could not send data bean", e1);
					throw e1;
				}
			}
		}
	}

	@Override
	public void setGuiBean(String plotName, GuiBean bean) throws Exception {
		sendBeansToServer(plotName, null, bean);
	}

	@Override
	public GuiBean getGuiBean(String plotName) throws Exception {
		PlotService plotService = getPlotService();

		return plotService == null ? null : plotService.getGuiState(plotName);
	}

	@Override
	public void setDataBean(String plotName, DataBean bean) throws Exception {
		sendBeansToServer(plotName, bean, null);
	}

	@Override
	public DataBean getDataBean(String plotName) throws Exception {
		PlotService plotService = getPlotService();

		return plotService == null ? null : plotService.getData(plotName);
	}

	@Override
	public GuiBean getGuiStateForPlotMode(String plotName, GuiPlotMode plotMode) {
		GuiBean bean;
		try {
			bean = getGuiBean(plotName);
			if (bean != null) {
				if (bean.containsKey(GuiParameters.PLOTMODE)) {
					if (!bean.get(GuiParameters.PLOTMODE).equals(plotMode)) {
						bean = null;
					} else {
						bean.remove(GuiParameters.PLOTID);
					}
				}
			}
		} catch (Exception e) {
			bean = null;
		}

		if (bean == null) {
			bean = new GuiBean();
			bean.put(GuiParameters.PLOTMODE, plotMode);
		}
		return bean;
	}

	@Override
	public String[] getGuiNames() throws Exception {
		return getPlotService().getGuiNames();
	}

	@Override
	public void createAxis(String plotName, final String title, final int side) throws Exception {
		GuiBean guiBean = new GuiBean();
		guiBean.put(GuiParameters.AXIS_OPERATION, new AxisOperation(AxisOperation.CREATE, title, side));

		setGuiBean(plotName, guiBean);
	}

	@Override
	public void renameActiveXAxis(String plotName, String xAxisTitle) throws Exception {
		GuiBean guiBean = new GuiBean();
		guiBean.put(GuiParameters.AXIS_OPERATION, new AxisOperation(AxisOperation.RENAMEX, xAxisTitle));

		setGuiBean(plotName, guiBean);
	}

	@Override
	public void renameActiveYAxis(String plotName, String yAxisTitle) throws Exception {
		GuiBean guiBean = new GuiBean();
		guiBean.put(GuiParameters.AXIS_OPERATION, new AxisOperation(AxisOperation.RENAMEY, yAxisTitle));

		setGuiBean(plotName, guiBean);
	}
}
