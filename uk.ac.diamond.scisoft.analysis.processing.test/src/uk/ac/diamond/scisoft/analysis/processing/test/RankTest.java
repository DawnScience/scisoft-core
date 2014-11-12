/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.InvalidRankException;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.operations.FunctionModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.SectorIntegrationModel;

public class RankTest {

	private static IOperationService service;
	
	/**
	 * Manually creates the service so that no extension points have to be read.
	 * 
	 * We do this use annotations
	 * @throws Exception 
	 */
	@BeforeClass
	public static void before() throws Exception {
		service = (IOperationService)Activator.getService(IOperationService.class);
		
		// Just read all these operations.
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.operations");
	}
	
	private volatile int count;

	@Test
	public void testAnyRank() throws Exception {
		
		System.out.println("Testing function which can run with any rank of data");
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation");
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setModel(new FunctionModel(poly));
		
		final RichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
		rand.setSlicing("all");
		
		count=0;
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=rand.getRank()) throw new Exception("Unexpected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with iterating first dimension gave "+count+ "of rank 2");

		count=0;
		rand.setSlicing("all", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=rand.getRank()) throw new Exception("Unexpected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first and second dimension gave "+count+ "of rank 1");

		
		count=0;
		rand.setSlicing("8", "500", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=rand.getRank()) throw new Exception("Unexpected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first, second and third dimension gave "+count+ "of rank 0");

	}

	@Test
	public void testInvalidSlice() throws Exception {
		
		try {
			final IOperation box      = service.findFirst("box");

			final RichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
			rand.setSlicing("all", "500");
					
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, box);
			
		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}
	
	@Test
	public void testInvalidRankOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi = service.findFirst("azimuthal");
		azi.setModel(new SectorIntegrationModel(sector));
		
		final IOperation box = service.findFirst("box");
		final IOperation add = service.findFirst("add");
		add.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});

		// This order is ok
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Add followed by azi should give a 1D result!");
			}			
		}, add, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, azi, box);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

	
	@Test
	public void testComplexInvalidOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi      = service.findFirst("azimuthal");
		azi.setModel(new SectorIntegrationModel(sector));
		
		final IOperation add      = service.findFirst("add");
		final IOperation sub      = service.findFirst("subtractOperation");
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation");
		final IOperation box      = service.findFirst("box");
		
		// Parameters
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setModel(new FunctionModel(poly));
		add.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});
		sub.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});

		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Azi should give a 1D result!");
			}			
		}, add, sub, function, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add, sub, function, azi, box);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

	
	@Test
	public void testFindByRank() throws Exception {

		Collection<IOperation<? extends IOperationModel, ? extends OperationData>> ones = service.find(OperationRank.ONE, true);
		if (ones.isEmpty()) throw new Exception("No one dimensional inputs found but there should be fitting!");
		
		Collection<IOperation<? extends IOperationModel, ? extends OperationData>> twos = service.find(OperationRank.TWO, true);
		if (twos.isEmpty()) throw new Exception("No two dimensional inputs found but there should be integration!");
		
		ones = service.find(OperationRank.ONE, false);
		if (ones.isEmpty()) throw new Exception("No one dimensional outputs found but there should be integration!");
		
		twos = service.find(OperationRank.SAME, false);
		if (twos.isEmpty()) throw new Exception("No twos dimensional outputs found but there should be add/subtract!");

	}
	
	
	@Test
	public void testFittingImages() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation add      = service.findFirst("add");
		final IOperation fitting  = service.findFirst("fitting");
		
		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add, fitting);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

}
