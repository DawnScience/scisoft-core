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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.MockSDAPlotter;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;


/**
 * This tests that the scisoftpy.plot.* function lands in the right SDAPlotter function.
 * <p>
 * This way all method signatures that SDAPlotter require can be tested. The details of datatypes are tested in the
 * flattening tests, in this test only the type of the argument matters.
 */
public class AllPyPlotMethodsTest extends SDAPlotterTestsUsingLoopbackTestAbstract {
	private IDataset sizes;
	private IDataset data, xAxis, yAxis, zAxis, image, xCoords, yCoords, zCoords;
	private IDataset[] xAxes, yAxes, images;
	private String plotName;
	private GuiBean bean;
	private DataBean dataBean;
	private String[] guiNames;

	private Boolean[] passed = new Boolean[1];

	class MyMockPlotter extends MockSDAPlotter {
		private Boolean[] flag;
		public MyMockPlotter(final Boolean[] status) {
			flag = status;
		}

		@Override
		public void plot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String[] yLabels, String[] xAxisNames,
				String[] yAxisNames) throws Exception {
			flag[0] = true;
		}

		@Override
		public void addPlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String[] yLabels, String[] xAxisNames,
				String[] yAxisNames) throws Exception {
			flag[0] = true;
		}

		@Override
		public void clearPlot(String plotName) throws Exception {
		}
	}

	public AllPyPlotMethodsTest() {
		// create some data sets and other objects to use, this test does not use
		// the contents of the data set, except they are flattened
		// and unflattened. The type of the object is more important
		xCoords = yCoords = zCoords = xAxis = yAxis = zAxis = DatasetFactory.createRange(10, Dataset.INT);
		data = image = DatasetFactory.createRange(100, Dataset.INT).reshape(10, 10);
		xAxes = yAxes = new IDataset[] { xAxis, DatasetFactory.createRange(10, Dataset.FLOAT) };
		images = new IDataset[] { image, DatasetFactory.createRange(100, Dataset.FLOAT) };
		plotName = "Plot 1";
		sizes = DatasetFactory.createRange(100, Dataset.INT);
		bean = new GuiBean();
		dataBean = new DataBean();
		guiNames = new String[] { "Plot 1", "Plot 2" };
	}

	@Test
	public void testPlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MyMockPlotter(passed));
		redirectPlotter.plot(plotName, null, new IDataset[] {xAxis}, new IDataset[] {yAxis}, null, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MyMockPlotter(passed));
		redirectPlotter.plot(plotName, null, new IDataset[] {xAxis}, new IDataset[] {yAxis}, null, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MyMockPlotter(passed));
		redirectPlotter.plot(plotName, null, new IDataset[] {xAxis}, new IDataset[] {yAxis}, null, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MyMockPlotter(passed));
		redirectPlotter.plot(plotName, null, new IDataset[] {xAxis}, yAxes, null, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testPlotStringIDatasetArrayIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MyMockPlotter(passed));
		redirectPlotter.plot(plotName, null, xAxes, yAxes, null, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName,
					String yAxisName) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, null, new IDataset[] {xAxis}, new IDataset[] {yAxis}, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName,
					String yAxisName) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, null, new IDataset[] {xAxis}, new IDataset[] {yAxis}, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDatasetIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName,
					String yAxisName) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, null, new IDataset[] {xAxis}, yAxes, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testUpdatePlotStringIDatasetArrayIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void updatePlot(String plotName, String title, IDataset[] xAxes, IDataset[] yAxes, String xAxisName,
					String yAxisName) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.updatePlot(plotName, null, xAxes, yAxes, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagePlotStringIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image, String xName, String yName) throws Exception {
				passed[0] = true;
			}

			@Override
			public void clearPlot(String plotName) throws Exception {
			}
		});
		redirectPlotter.imagePlot(plotName, null, null, image, null, null);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagesPlotStringIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagesPlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset[] images) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.imagesPlot(plotName, null, null, images);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testImagePlotStringIDatasetIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void imagePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset image, String xName, String yName) throws Exception {
				passed[0] = true;
			}

			@Override
			public void clearPlot(String plotName) throws Exception {
			}
		});
		redirectPlotter.imagePlot(plotName, xAxis, yAxis, image, null, null);
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
			public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.surfacePlot(plotName, null, null, data);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testSurfacePlotStringIDatasetIDataset() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void surfacePlot(String plotName, IDataset xAxis, IDataset yAxis, IDataset data) throws Exception {
				passed[0] = true;
			}
		});
		// XXX: xAxis is discarded in this call by plot.py#surface
		redirectPlotter.surfacePlot(plotName, xAxis, null, data);
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
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, new IDataset[] {xAxis}, yAxes, null);
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
		redirectPlotter.stackPlot(plotName, new IDataset[] {xAxis}, yAxes, zAxis);
		Assert.assertTrue(passed[0]);
	}

	@Test
	public void testStackPlotStringIDatasetArrayIDatasetArray() throws Exception {
		passed[0] = false;
		registerHandler(new MockSDAPlotter() {
			@Override
			public void stackPlot(String plotName, IDataset[] xAxes, IDataset[] yAxes, IDataset zAxis) throws Exception {
				passed[0] = true;
			}
		});
		redirectPlotter.stackPlot(plotName, xAxes, yAxes, null);
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
