/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.actor.actors.OperationTransformer;
import uk.ac.diamond.scisoft.analysis.processing.actor.runner.GraphRunner;
import uk.ac.diamond.scisoft.analysis.processing.operations.SectorIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ThresholdMaskModel;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;

public class IntegrationTest {

	
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
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.GRAPH,    new GraphRunner());
	
		OperationTransformer.setOperationService(service);
	}

	private volatile int count;
	@Test
	public void testAzimuthalSimpleMask() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 1000.0, 24, 1000, 1000));
//		context.setSlicing("all"); // All 24 images in first dimension.
		context.setDataDimensions(new int[]{1,2});
		
		final IOperation maskOp = getMaskOperation(mask);
		
		final IOperation azi = service.findFirst("uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration");
		azi.setModel(new SectorIntegrationModel(sector));
		
		count = 0;
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getSize()!=472) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}
				
				count++;
			}
		});
		
		context.setSeries(maskOp,azi);
		service.execute(context);
		
		if (count!=24) throw new Exception("Size of integrated results incorrect!");
	}
	
	@Test
	public void testAzimuthalThresholdMask() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000, 1000);
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 1000.0, 24, 1000, 1000));
//		context.setSlicing("all"); // All 24 images in first dimension.
		context.setDataDimensions(new int[]{1,2});
		
		final IOperation thresh = service.findFirst("threshold");
		thresh.setModel(new ThresholdMaskModel(750d, 250d));
		
		final IOperation azi    = service.findFirst("uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration");
		azi.setModel(new SectorIntegrationModel(sector));
		
		final IOperation maskOp = getMaskOperation(mask);
		
		count = 0;
		context.setVisitor(new IExecutionVisitor.Stub() {
			
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				count++;
				final IDataset integrated = result.getData();
				if (integrated.getSize()!=472) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}
				
			}
		});
		context.setSeries(maskOp, thresh, azi);
		
		service.execute(context);
		
		if (count!=24) throw new Exception("Size of integrated results incorrect!");

	}

	@Test
	public void testAzimuthalThresholdMaskParallel() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000, 1000);
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 1000.0, 24, 1000, 1000));
//		context.setSlicing("all"); // All 24 images in first dimension.
		context.setDataDimensions(new int[]{1,2});
		
		final IOperation thresh = service.findFirst("threshold");
		thresh.setModel(new ThresholdMaskModel(750d, 250d));
		
		final IOperation azi    = service.findFirst("uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration");
		azi.setModel(new SectorIntegrationModel(sector));
		
		final IOperation maskOp = getMaskOperation(mask);
		
		count = 0;
		context.setParallelTimeout(Long.MAX_VALUE);

		context.setVisitor(new IExecutionVisitor.Stub() {

			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				final IDataset integrated = result.getData();
				if (integrated.getSize()!=472) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}

				count++;
			}
		});
		
		context.setSeries(maskOp, thresh, azi);
		context.setExecutionType(ExecutionType.PARALLEL);
		service.execute(context);

		Thread.sleep(5000);
		assertEquals("Size of integrated results incorrect! Results found: " + count + ", expected: 24", 24, count);

	}

	private IOperation getMaskOperation(final IDataset mask) {
		
		final MaskMetadata mmd = new MaskMetadata() {
			
			@Override
			public ILazyDataset getMask() {
				// TODO Auto-generated method stub
				return mask;
			}
			
			@Override
			public MaskMetadata clone() {
				return null;
			}
		};
		
		final IOperation maskOp = new AbstractOperation<IOperationModel, OperationData>() {
			
			@Override
			public void setModel(IOperationModel parameters) {
			}
			
			@Override
			public OperationRank getOutputRank() {
				return OperationRank.TWO;
			}
			
			@Override
			public OperationRank getInputRank() {
				return OperationRank.TWO;
			}
			
			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return "uk.ac.diamond.scisoft.analysis.processing.test.maskimport";
			}
			
			@Override
			public OperationData execute(IDataset slice, IMonitor monitor)
					throws OperationException {
					slice.addMetadata(mmd);
				return new OperationData(slice);
			}
		};
		
		return maskOp;
	}

}
