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

		sdaPlotterImplUnderTest.plot("MyTest Line", null, null, new IDataset[] {ds}, null, null);
	
		Assert.assertEquals("MyTest Line", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.ONED, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagePlot() throws Exception {
		AbstractDataset ds = AbstractDataset.arange(100, AbstractDataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagePlot("MyTest Image", null, null, ds);
	
		Assert.assertEquals("MyTest Image", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.TWOD, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagesPlot() throws Exception {
		AbstractDataset ds = AbstractDataset.arange(100, AbstractDataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagesPlot("MyTest Images", null, null, new IDataset[] {ds, ds});
	
		Assert.assertEquals("MyTest Images", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.MULTI2D, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(1).getData());
	}

	
}
