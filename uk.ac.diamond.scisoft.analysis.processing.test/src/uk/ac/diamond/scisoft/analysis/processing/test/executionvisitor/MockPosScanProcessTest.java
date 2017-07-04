/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.ILiveOperationInfo;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.model.SleepModel;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.operations.DataWrittenModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.DataWrittenOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.NoDataOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.SleepOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxMeanOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DownsampleImageModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DownsampleImageOperation;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class MockPosScanProcessTest {

	private static IOperationService service;
	
	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		NexusFileExecutionVisitor.setPersistenceService(PersistenceServiceCreator.createPersistenceService());
		service = (IOperationService)Activator.getService(IOperationService.class);
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	@Test
	public void test() throws Exception {
		int sleep = 1000;
		int[] dataShape = {15,101,102};
		ExecutorService ste = Executors.newSingleThreadExecutor();
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		startMockScan(dataShape, sleep,ste,tmp);
		ste.shutdown();
		
		ExecutorService ste2 = Executors.newSingleThreadExecutor();
		final File tmpProc = File.createTempFile("Test", ".h5");
		tmpProc.deleteOnExit();
		tmpProc.createNewFile();
		
		starProcessing(ste2, tmpProc, tmp, false);
		ste2.shutdown();
		
		while (!ste.awaitTermination(200,TimeUnit.MILLISECONDS) || !ste2.awaitTermination(200,TimeUnit.MILLISECONDS)) {
			//nothing
		}

		IDataHolder dhOrigProc = LoaderFactory.getData(tmpProc.getAbsolutePath());
		
		ILazyDataset procLz = dhOrigProc.getLazyDataset("/entry/result/data");
		assertArrayEquals(new int[]{5, 11, 11}, procLz.getShape());
		
		Dataset slice = DatasetUtils.convertToDataset(procLz.getSlice());
		
		slice.toString();
		assertEquals(21067.5, slice.getElementDoubleAbs(0), 0.0000001);
		assertEquals(154528.5, slice.getElementDoubleAbs(slice.getSize()-1), 0.0000001);

	}
	
	
	
	private void startMockScan(int[] shape, int sleep, ExecutorService ste, File tmp) throws Exception{
		
		
		ILazyDataset lazy = ExecutionVisitorTestUtils.getLazyDataset(shape, -1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{1,2});
		
		SleepOperation ops = new SleepOperation();
		ops.setModel(new SleepModel());
		ops.getModel().setMilliseconds(sleep);
		DataWrittenOperation odo = new DataWrittenOperation();
		DataWrittenModel dataWrittenModel = new DataWrittenModel();
		dataWrittenModel.setnFramesOverwrite(3);
		odo.setModel(dataWrittenModel);

		//FIXME or rather fix swmr. Not currently testing swmr since wont read from a 
		//different thread to writing thread
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath(),true));
		context.setSeries(ops,odo);
		context.setExecutionType(ExecutionType.SERIES);
		
		ste.submit(new Runnable() {
			
			@Override
			public void run() {
				service.execute(context);
				
			}
		});
		
		
	}
	
	private void starProcessing(ExecutorService ste, File tmpProc, File tmp, boolean noData) throws Exception{
		
		String data = "/entry/result/data";
		String key = "/entry/auxiliary/1-DataWritten/key/data";
		
		
		final IOperationContext context = service.createContext();
		
		IDataHolder dh = null;
		int count = 0;
		while (count < 100 &&  (dh == null || !dh.contains("/entry/result/data"))){
			Thread.sleep(100);
			count++;
			dh = LoaderFactory.getData(tmp.getAbsolutePath());
		}
		
		if (count == 100) Assert.fail("Couldnt read file!");
		
		final IDataHolder fdh = dh;
		
		ILazyDataset lz = dh.getLazyDataset(data);
		
		context.setData(lz);
		context.setDataDimensions(new int[]{2,3});
		context.setLiveInfo(new ILiveOperationInfo() {
			
			@Override
			public IDynamicDataset[] getKeys() {
				ILazyDataset lazyDataset = fdh.getLazyDataset("/entry/auxiliary/1-DataWritten/key/data");
				return new IDynamicDataset[]{(IDynamicDataset)lazyDataset};
			}
			
			@Override
			public IDynamicDataset getComplete() {
				return (IDynamicDataset)fdh.getLazyDataset("/entry/live/finished");
			}

			@Override
			public boolean isMonitorForOverwrite() {
				return true;
			}
		});
		
		context.setParallelTimeout(10000);
		
		IOperation[] ops = new IOperation[noData ? 3 : 2];
		
		BoxMeanOperation bmo = new BoxMeanOperation();
		bmo.setName("BoxMean");
		BoxModel bmm = new BoxModel();
		bmm.setBox(new RectangularROI(10,10, 10, 10, 0));
		bmo.setModel(bmm);
		
		ops[0] = bmo;
		
		DownsampleImageOperation dso = new DownsampleImageOperation();
		DownsampleImageModel dsm = new DownsampleImageModel();
		dsm.setDownsampleSizeY(10);
		dsm.setDownsampleSizeX(10);
		dso.setModel(dsm);
		
		ops[1] = dso;
		
		if (noData) {
			NoDataOperation ndo = new NoDataOperation();
			ndo.setModel(new EmptyModel());
			ops[2] = ndo;
		}
		
		context.setVisitor(new NexusFileExecutionVisitor(tmpProc.getAbsolutePath(),true));
		context.setSeries(ops);
		context.setExecutionType(ExecutionType.SERIES);
		
		ste.submit(new Runnable() {
			
			@Override
			public void run() {
				service.execute(context);
				
			}
		});
		
		
	}

}
