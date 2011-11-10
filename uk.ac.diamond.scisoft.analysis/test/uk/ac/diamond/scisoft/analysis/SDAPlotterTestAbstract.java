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

package uk.ac.diamond.scisoft.analysis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;

abstract public class SDAPlotterTestAbstract {

	protected static ISDAPlotter sdaPlotterImplUnderTest;
	protected static MockPlotServer testPlotServer = new MockPlotServer();

	@Before
	public void clearPlotServer() {
		testPlotServer.clear();
	}
	
	@Test
	public void testLinePlot() throws Exception {
		AbstractDataset ds = AbstractDataset.arange(100, AbstractDataset.INT64);

		sdaPlotterImplUnderTest.plot("MyTest Line", ds);
	
		Assert.assertEquals("MyTest Line", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.ONED, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagePlot() throws Exception {
		AbstractDataset ds = AbstractDataset.arange(100, AbstractDataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagePlot("MyTest Image", ds);
	
		Assert.assertEquals("MyTest Image", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.TWOD, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagesPlot() throws Exception {
		AbstractDataset ds = AbstractDataset.arange(100, AbstractDataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagesPlot("MyTest Images", new IDataset[] {ds, ds});
	
		Assert.assertEquals("MyTest Images", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.MULTI2D, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(1).getData());
	}

	
}
