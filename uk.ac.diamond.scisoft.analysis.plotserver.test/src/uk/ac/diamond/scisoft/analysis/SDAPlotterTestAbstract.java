/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		Dataset ds = DatasetFactory.createRange(100, Dataset.INT64);

		sdaPlotterImplUnderTest.plot("MyTest Line", null, null, new IDataset[] {ds}, null, null, null);
	
		Assert.assertEquals("MyTest Line", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.ONED, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagePlot() throws Exception {
		Dataset ds = DatasetFactory.createRange(100, Dataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagePlot("MyTest Image", null, null, ds, null, null);
	
		Assert.assertEquals("MyTest Image", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.TWOD, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
	}

	@Test
	public void testImagesPlot() throws Exception {
		Dataset ds = DatasetFactory.createRange(100, Dataset.INT64);
		ds.setShape(10, 10);

		sdaPlotterImplUnderTest.imagesPlot("MyTest Images", null, null, new IDataset[] {ds, ds});
	
		Assert.assertEquals("MyTest Images", testPlotServer.getLastPlotname());
		Assert.assertEquals(GuiPlotMode.MULTI2D, testPlotServer.getLastDataBean().getGuiPlotMode());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(0).getData());
		Assert.assertEquals(ds, testPlotServer.getLastDataBean().getData().get(1).getData());
	}

	
}
