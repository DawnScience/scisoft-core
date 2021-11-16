/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Victor Rogalev

package uk.ac.diamond.scisoft.analysis.spectroscopy;

import java.util.List;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.junit.Assert;
import org.junit.Test;

public class RxesRemapMathTest {
	
	private final int m = 50;
	private final int n = 100;
	private final int xStart = 10000;
	private final int xStop = 11000;
	private final int yStart = 9000;
	private final int yStop = 9500;
	private final double lossMax = Math.max(xStart, xStop)-Math.min(yStart, yStop);
	private final double lossMin = Math.min(xStart, xStop)-Math.max(yStart, yStop);
	private final double TOL = 0.0001;
	
	private IDataset data =  DatasetUtils.eye(DoubleDataset.class, m, n, 0);
	private List<Dataset> mg = DatasetUtils.meshGrid(
			DatasetFactory.createLinearSpace(DoubleDataset.class, xStart, xStop, m),
			DatasetFactory.createLinearSpace(DoubleDataset.class, yStart, yStop, n));
	private final double lossStep = Math.abs(mg.get(1).getDouble(0, 1)-mg.get(1).getDouble(0, 0));
	private final int newSize = (int) ((lossMax-lossMin)/lossStep+1);

	private IDataset outputAxesX = null;
	private IDataset outputAxesY = null;
	
	@Test
	public void testShapesAndAxes() {
		IDataset result = null;
		// use standard input - check result shape
		try {
			result = RxesRemapMath.RemapLinearInterp(data, mg.get(0), mg.get(1));
		} catch (Exception e) {
			System.out.println("Whoooaa! "+e);
		}
		Assert.assertArrayEquals("Case standard: Output shape is not as expected! ", new int[] {newSize, m}, result.getShape());
		Assert.assertEquals(result.max().doubleValue(), data.max().doubleValue(), TOL);
		
		//check axes
		try {
			outputAxesX = result.getFirstMetadata(AxesMetadata.class).getAxis(1)[0].getSlice();
			outputAxesY = result.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice();
		} catch (DatasetException e) {
			System.out.println(e);
		}
		Assert.assertArrayEquals(outputAxesX.getShape(), new int[] {n,m});
		Assert.assertEquals(outputAxesX.min().doubleValue(), mg.get(0).min().doubleValue(), TOL);
		Assert.assertEquals(outputAxesX.max().doubleValue(), mg.get(0).max().doubleValue(), TOL);
		Assert.assertArrayEquals(outputAxesY.getShape(), new int[] {newSize,1});
		Assert.assertEquals(outputAxesY.min().doubleValue(), lossMin, TOL);
		Assert.assertEquals(outputAxesY.max().doubleValue(), lossMax, TOL);
	}
	@Test
	public void testShapesAndAxesTransposed() {
		// use transposed input
		IDataset result = null;
		try {
			result = RxesRemapMath.RemapLinearInterp(data.getTransposedView(), mg.get(0).transpose(), mg.get(1).transpose());
		} catch (Exception e) {
			System.out.println("Whoooaa! "+e);
		}
		Assert.assertArrayEquals("Case transposed: Output shape is not as expected! ", new int[] {newSize, m}, result.getShape());
		Assert.assertEquals(result.max().doubleValue(), data.max().doubleValue(), TOL);
		
		//check axes
		try {
			outputAxesX = result.getFirstMetadata(AxesMetadata.class).getAxis(1)[0].getSlice();
			outputAxesY = result.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice();
		} catch (DatasetException e) {
			System.out.println(e);
		}
		
		Assert.assertArrayEquals(outputAxesX.getShape(), new int[] {n,m});
		Assert.assertEquals(outputAxesX.min().doubleValue(), mg.get(0).min().doubleValue(), TOL);
		Assert.assertEquals(outputAxesX.max().doubleValue(), mg.get(0).max().doubleValue(), TOL);
		Assert.assertArrayEquals(outputAxesY.getShape(), new int[] {newSize,1});
		Assert.assertEquals(outputAxesY.min().doubleValue(), lossMin, TOL);
		Assert.assertEquals(outputAxesY.max().doubleValue(), lossMax, TOL);
	}
}
