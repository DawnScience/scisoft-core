/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.IDynamicDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDynamicDataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.metadata.DynamicMetadataUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.actor.runner.GraphRunner;
import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.OriginalDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.SleepModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.SleepOperation;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class NexusFileSWMRTest {

	private static IOperationService service;
	
	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.GRAPH,    new GraphRunner());
		
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		service = (IOperationService)Activator.getService(IOperationService.class);
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	private void startRunning(int[] shape, int sleep, ExecutorService ste, File tmp) throws Exception{
		
		
		ILazyDataset lazy = HierarchicalFileExVisitorTest.getLazyDataset(shape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{1,2});
		
		SleepOperation ops = new SleepOperation();
		ops.setModel(new SleepModel());
		ops.getModel().setMilliseconds(sleep);
		OriginalDataOperation odo = new OriginalDataOperation();
		odo.setModel(new EmptyModel());

		//FIXME or rather fix swmr. Not currently testing swmr since wont read from a 
		//different thread to writing thread
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath(),false));
		context.setSeries(ops,odo);
		context.setExecutionType(ExecutionType.SERIES);
		
		ste.submit(new Runnable() {
			
			@Override
			public void run() {
				service.execute(context);
				
			}
		});
		
		
	}
	
	@Test
	public void test() throws Exception {
		boolean tested = false;
		boolean shapeChanged = false;
		int[] initShape = null;
		
		int sleep = 100;
		int[] dataShape = {1,200,200};
		int[] inputShape = new int[] {30,200,200};
		ExecutorService ste = Executors.newSingleThreadExecutor();
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		startRunning(inputShape, sleep,ste,tmp);
		
		IDataHolder dh = null;
		int count = 0;
		while (count < 100 &&  (dh == null || !dh.contains("/entry/result/data"))){
			Thread.sleep(sleep);
			count++;
			dh = LoaderFactory.getData(tmp.getAbsolutePath());
		}
		
		if (count == 100) Assert.fail("Couldnt read file!");
		
		LazyDynamicDataset ldd  = null;
		
		ste.shutdown();
		
		while (!ste.awaitTermination(200,TimeUnit.MILLISECONDS)) {
			if (ldd != null) {
				ldd.refreshShape();
				if (initShape == null) {
					initShape = ldd.getShape().clone();
				} else {
					if (!Arrays.equals(initShape, ldd.getShape())) shapeChanged = true;
				}
				SliceND test = new SliceND(ldd.getShape());
				if (ldd.getShape()[0] > 1) {
					test.setSlice(0, ldd.getShape()[0]-1, ldd.getShape()[0], 1);
					Dataset slice = ldd.getSlice(test);
					Assert.assertTrue(slice != null);
					Assert.assertArrayEquals(dataShape, slice.getShape());
					tested = true;
				}
				
				
			} else {
				ldd = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/data");
			}
		}
		Assert.assertTrue(tested);
		Assert.assertTrue(shapeChanged);
		IDynamicDataset output = (IDynamicDataset)dh.getLazyDataset("/entry/result/data");
		output.refreshShape();
		Assert.assertArrayEquals(inputShape, output.getShape());
	}

	
	@Test
	public void testWithAxes() throws Exception {
		
		boolean tested = false;
		int sleep = 100;
		int[] dataShape = {1,200,200};
		int[] axShape = {1,1,1};
		int[] inputShape = new int[] {30,200,200};
		boolean shapeChanged = false;
		int[] initShape = null;
		ExecutorService ste = Executors.newSingleThreadExecutor();
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		startRunning(inputShape, sleep,ste,tmp);

		IDataHolder dh = null;
		int count = 0;
		while (count < 100 &&  (dh == null || !dh.contains("/entry/result/data"))){
			Thread.sleep(sleep);
			count++;
			dh = LoaderFactory.getData(tmp.getAbsolutePath());
		}
		
		if (count == 100) Assert.fail("Couldnt read file!");
		
		LazyDynamicDataset ldd  = null;
		
		ste.shutdown();
		
		ldd = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/data");
		LazyDynamicDataset ld = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/Axis_0");
		Assert.assertTrue(ld != null);
		AxesMetadataImpl am = new AxesMetadataImpl(ldd.getRank());
		am.addAxis(0, ld);
		ldd.setMetadata(am);

		while (!ste.awaitTermination(200,TimeUnit.MILLISECONDS)) {
			if (ldd != null) {
				ldd.refreshShape();
				if (initShape == null) {
					initShape = ldd.getShape().clone();
				} else {
					if (!Arrays.equals(initShape, ldd.getShape())) shapeChanged = true;
				}
				
				List<AxesMetadata> metadata = ldd.getMetadata(AxesMetadata.class);
				int[] mmshape = DynamicMetadataUtils.refreshDynamicAxesMetadata(metadata, ldd.getShape());
				ldd.resize(mmshape);
				
				SliceND test = new SliceND(ldd.getShape());
				if (ldd.getShape()[0] > 1) {
					test.setSlice(0, ldd.getShape()[0]-1, ldd.getShape()[0], 1);
					Dataset slice = ldd.getSlice(test);
					Assert.assertTrue(slice != null);
					Assert.assertArrayEquals(dataShape, slice.getShape());
					AxesMetadata m = slice.getFirstMetadata(AxesMetadata.class);
					IDataset as = m.getAxis(0)[0].getSlice();
					Assert.assertTrue(as != null);
					Assert.assertArrayEquals(axShape, as.getShape());
					tested = true;
				}
				
				
			} else {
				ldd = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/data");
			}
		}
		Assert.assertTrue(tested);
		LazyDynamicDataset output = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/data");
		output.refreshShape();
		List<AxesMetadata> m = output.getMetadata(AxesMetadata.class);
		DynamicMetadataUtils.refreshDynamicAxesMetadata(m, output.getShape());
		IDataset as = m.get(0).getAxis(0)[0].getSlice();
		Assert.assertArrayEquals(inputShape, output.getShape());
		Assert.assertArrayEquals(new int[] {30,1,1}, as.getShape());
		
		
//		while (!ste.awaitTermination(500,TimeUnit.MILLISECONDS)) {
//			if (ldd != null) {
////				System.out.println("Ax SHAPE " + Arrays.toString(ldd.getFirstMetadata(AxesMetadataImpl.class).getAxis(0)[0].getShape()));
////				ldd.refreshShape();
////				List<AxesMetadata> metadata = ldd.getMetadata(AxesMetadata.class);
////				int[] mmshape = DynamicMetadataUtils.refreshDynamicAxesMetadata(metadata, ldd.getShape());
////				ldd.resize(mmshape);
////				System.out.println("Ax SHAPE " + Arrays.toString(ldd.getFirstMetadata(AxesMetadataImpl.class).getAxis(0)[0].getShape()));
////				
////				System.out.println("SHAPE " + Arrays.toString(ldd.getShape()));
//				
//				SliceND test = new SliceND(ldd.getShape());
//				if (ldd.getShape()[0] > 1) {
//					test.setSlice(0, ldd.getShape()[0]-1, ldd.getShape()[0], 1);
//					Dataset slice = ldd.getSlice(test);
//					System.out.println("Slice SHAPE " + Arrays.toString(slice.getShape()));
//				}
//				
//				
//			} else {
//				ldd = (LazyDynamicDataset)dh.getLazyDataset("/entry/result/data");
//				if (ldd == null) System.err.println("STILL NOT HERE");
//			}
//		}
//		
//		ILazyDataset output = dh.getLazyDataset("/entry/result/data");
	}
}
