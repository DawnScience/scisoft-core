/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.sdaplotter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.MockSDAPlotter;
import uk.ac.diamond.scisoft.analysis.SDAPlotter;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;

/**
 * This tests that the scisoftpy.plot.* function lands in the right SDAPlotter function.
 * <p>
 * This way all method signatures that SDAPlotter require can be tested. The details of datatypes are tested in the
 * flattening tests, in this test only the type of the argument matters.
 */
public class AllPyPlotMethodsTest extends SDAPlotterTestsUsingLoopbackTestAbstract {
	// Parameters for each test
	private int size;
	private IDataset sizes;
	private IDataset data, xAxis, yAxis, zAxis, image, xCoords, yCoords, zCoords;
	private IDataset[] xAxes, yAxes, images;
	private String plotName, viewName;
	private String pathname, regex;
	private int scanForImagesFakedResult, order, gridColumns;
	private boolean rowMajor;
	private String[] suffices;
	private GuiBean bean;
	private DataBean dataBean;
	private String[] guiNames;

	private Boolean[] passed = new Boolean[1];

	

	public AllPyPlotMethodsTest() {
		// create some data sets and other obkects to use, this test does not use
		// the contents of the data set, except they are flattened
		// and unflattened. The type of the object is more important
		xCoords = yCoords = zCoords = xAxis = yAxis = zAxis = AbstractDataset.arange(100, AbstractDataset.INT);
		data = image = AbstractDataset.arange(100, AbstractDataset.INT).reshape(10, 10);
		xAxes = yAxes = new IDataset[] { xAxis, AbstractDataset.arange(100, AbstractDataset.FLOAT) };
		images = new IDataset[] { image, AbstractDataset.arange(100, AbstractDataset.FLOAT) };
		viewName = plotName = "Plot 1";
		size = 100;
		sizes = AbstractDataset.arange(100, AbstractDataset.INT);
		pathname = "/tmp/dir";
		regex = "a.*b";
		scanForImagesFakedResult = 21;
		order = SDAPlotter.IMAGEORDERNONE;
		suffices = SDAPlotter.LISTOFSUFFIX;
		gridColumns = 2;
		rowMajor = true;
		bean = new GuiBean();
		dataBean = new DataBean();
		guiNames = new String[] { "Plot 1", "Plot 2" };
	}

	@Test
	public void testPlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset yAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.plot(plotName, yAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.plot(plotName, xAxis, yAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetArrayIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.plot(plotName, xAxes, yAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.plot(plotName, xAxis, yAxes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetArrayIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void plot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.plot(plotName, xAxes, yAxes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, IDataset yAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, xAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, xAxis, yAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, IDataset xAxis, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, xAxis, yAxes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagePlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagePlot(String plotName, IDataset image) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.imagePlot(plotName, image);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagesPlotStringIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagesPlot(String plotName, IDataset[] images) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.imagesPlot(plotName, images);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagePlotStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.imagePlot(plotName, xAxis, yAxis, image);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagesPlotStringIDatasetIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.imagesPlot(plotName, xAxis, yAxis, images);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter2DPlotStringIDatasetIDatasetInt() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, int size) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter2DPlot(plotName, xCoords, yCoords, size);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter2DPlotStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter2DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
					throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter2DPlot(plotName, xCoords, yCoords, sizes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter2DPlotOverStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset sizes)
					throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter2DPlotOver(plotName, xCoords, yCoords, sizes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter2DPlotOverStringIDatasetIDatasetInt() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter2DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, int size)
					throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter2DPlotOver(plotName, xCoords, yCoords, size);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter3DPlotStringIDatasetIDatasetIDatasetInt() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords, int size)
					throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter3DPlot(plotName, xCoords, yCoords, zCoords, size);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter3DPlotStringIDatasetIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter3DPlot(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
					IDataset sizes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter3DPlot(plotName, xCoords, yCoords, zCoords, sizes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter3DPlotOverStringIDatasetIDatasetIDatasetInt() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
					int size) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter3DPlotOver(plotName, xCoords, yCoords, zCoords, size);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScatter3DPlotOverStringIDatasetIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void scatter3DPlotOver(String plotName, IDataset xCoords, IDataset yCoords, IDataset zCoords,
					IDataset sizes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.scatter3DPlotOver(plotName, xCoords, yCoords, zCoords, sizes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testSurfacePlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void surfacePlot(String plotName, IDataset data) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.surfacePlot(plotName, data);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testSurfacePlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void surfacePlot(String plotName, IDataset data) throws Exception {
				passed[0] = true;
			}
		});
		// XXX: xAxis is discarded in this call by plot.py#surface
		redirectPlotter.surfacePlot(plotName, xAxis, data);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testSurfacePlotStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.surfacePlot(plotName, xAxis, yAxis, data);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testStackPlotStringIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, xAxis, yAxes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testStackPlotStringIDatasetIDatasetArrayIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, xAxis, yAxes, zAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testStackPlotStringIDatasetArrayIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, xAxes, yAxes);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testStackPlotStringIDatasetArrayIDatasetArrayIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, xAxes, yAxes, zAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdateStackPlotStringIDatasetArrayIDatasetArrayIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updateStackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updateStackPlot(plotName, xAxes, yAxes, zAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testScanForImagesStringString() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
					int gridColumns, boolean rowMajor) throws Exception {
				passed[0] = true;
				return scanForImagesFakedResult;
			}
		});
		int scanForImages = redirectPlotter.scanForImages(viewName, pathname);
		Assert.assertTrue(passed[0]);
		Assert.assertEquals(scanForImagesFakedResult, scanForImages);
	}

	@Test
	public void testScanForImagesStringStringInt() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
					int gridColumns, boolean rowMajor) throws Exception {
				passed[0] = true;
				return scanForImagesFakedResult;
			}
		});
		int scanForImages = redirectPlotter.scanForImages(viewName, pathname, order);
		Assert.assertTrue(passed[0]);
		Assert.assertEquals(scanForImagesFakedResult, scanForImages);
	}

	@Test
	public void testScanForImagesStringStringIntStringArrayIntBoolean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
					int gridColumns, boolean rowMajor) throws Exception {
				passed[0] = true;
				return scanForImagesFakedResult;
			}
		});
		int scanForImages = redirectPlotter.scanForImages(viewName, pathname, order, suffices, gridColumns, rowMajor);
		Assert.assertTrue(passed[0]);
		Assert.assertEquals(scanForImagesFakedResult, scanForImages);
	}

	@Test
	public void testScanForImagesStringStringIntStringStringArrayIntBoolean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public int scanForImages(String viewName, String pathname, int order, String regex, String[] suffices,
					int gridColumns, boolean rowMajor) throws Exception {
				passed[0] = true;
				return scanForImagesFakedResult;
			}
		});
		int scanForImages = redirectPlotter.scanForImages(viewName, pathname, order, regex, suffices, gridColumns,
				rowMajor);
		Assert.assertTrue(passed[0]);
		Assert.assertEquals(scanForImagesFakedResult, scanForImages);
	}

	@Test
	public void testSetGuiBean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void setGuiBean(String plotName, GuiBean bean) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.setGuiBean(plotName, bean);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testGetGuiBean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public GuiBean getGuiBean(String plotName) throws Exception {
				passed[0] = true;
				return bean;
			}
		});
		GuiBean b = redirectPlotter.getGuiBean(plotName);
		Assert.assertTrue(passed[0]);
		Assert.assertEquals(bean, b);
	}

	@Test
	public void testSetDataBean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void setDataBean(String plotName, DataBean bean) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.setDataBean(plotName, dataBean);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testGetDataBean() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public DataBean getDataBean(String plotName) throws Exception {
				passed[0] = true;
				return dataBean;
			}
		});
		DataBean b = redirectPlotter.getDataBean(plotName);
		Assert.assertTrue(passed[0]);
		Assert.assertTrue(EqualsBuilder.reflectionEquals(dataBean, b));
	}

	@Test
	public void testGetGuiNames() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public String[] getGuiNames() throws Exception {
				passed[0] = true;
				return guiNames;
			}
		});
		String[] n = redirectPlotter.getGuiNames();
		Assert.assertTrue(passed[0]);
		Assert.assertArrayEquals(guiNames, n);
	}

}
