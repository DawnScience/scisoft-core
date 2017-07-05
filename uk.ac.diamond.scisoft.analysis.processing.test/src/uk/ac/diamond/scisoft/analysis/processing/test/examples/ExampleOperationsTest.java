/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.conversion.ConversionServiceImpl;
import org.dawnsci.conversion.converters.util.LocalServiceManager;
import org.dawnsci.conversion.schemes.ProcessConversionScheme;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.conversion.IConversionContext;
import org.eclipse.dawnsci.analysis.api.conversion.IConversionService;
import org.eclipse.dawnsci.analysis.api.conversion.IProcessingConversionInfo;
import org.eclipse.dawnsci.analysis.api.conversion.ProcessingOutputType;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.january.dataset.IDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class ExampleOperationsTest {
	
	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		ServiceManager.setService(IConversionService.class, new ConversionServiceImpl());
		IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.examples");
		ServiceManager.setService(IOperationService.class, service);
		
		LocalServiceManager.setLoaderService(new LoaderServiceImpl());
		LocalServiceManager.setOperationService(service);
		
		uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager.setLoaderService(new LoaderServiceImpl());
	}
	
	
	@Test
	public void sumExample() throws Exception{
		
		String path = ExampleDataUtils.createExampleDataFile("myfilesum", new int[] {10, 100, 200});
		
		if (path == null) fail("Could not create data file");

		IConversionService service = (IConversionService)ServiceManager.getService(IConversionService.class);
		IConversionContext context = service.open(path);

		context.setConversionScheme(new ProcessConversionScheme());
		context.setDatasetName("/entry1/data/data");
		context.addSliceDimension(0, "all");
		Map<Integer, String> axesNames = new HashMap<Integer,String>();
		axesNames.put(1, "/entry1/data/axis0");
		axesNames.put(2, "/entry1/data/axis1");
		axesNames.put(3, "/entry1/data/axis2");
		context.setAxesNames(axesNames);

		File f = new File(path);
		String outpath = f.getParentFile().getAbsolutePath() + File.separator + "output" + File.separator;
		File o = new File(outpath);
		o.mkdirs();
		o.deleteOnExit();

		context.setOutputPath(outpath);
		context.setUserObject(new IProcessingConversionInfo() {

			@Override
			public IOperation[] getOperationSeries() {
				ExampleSumOperation sumop = new ExampleSumOperation();
				sumop.setModel(new EmptyModel());
				return new IOperation[]{sumop};
			}

			@Override
			public IExecutionVisitor getExecutionVisitor(String fileName) {
				return new NexusFileExecutionVisitor(fileName);
			}

			@Override
			public ProcessingOutputType getProcessingOutputType() {
				return ProcessingOutputType.PROCESSING_ONLY;
			}

			@Override
			public ExecutionType getExecutionType() {
				return ExecutionType.SERIES;
			}

			@Override
			public int getPoolSize() {
				return 1;
			}

			@Override
			public boolean isTryParallel() {
				// TODO Auto-generated method stub
				return false;
			}
		});

		service.process(context);

		File f1 = new File(outpath);
		File[] matchingFiles = f1.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("myfilesum") && name.endsWith("nxs");
			}
		});

		File result = matchingFiles[0];
		result.deleteOnExit();

		IDataHolder dh = LoaderFactory.getData(result.getAbsolutePath());

		IDataset d1 = dh.getLazyDataset("/entry/result/data").getSlice();
		double val = d1.getDouble(0, 0, 0);
		assertEquals(100, val, 0);

		IDataset a1 = dh.getLazyDataset("/entry/result/axis1").getSlice();
		val = a1.getDouble(1);
		assertEquals(1, val, 0);

		IDataset a2 = dh.getLazyDataset("/entry/result/axis2").getSlice();
		val = a2.getDouble(1);
		assertEquals(2, val, 0);

		
	}
	
	@Test
	public void subtractionExample() throws Exception {
		
		String path = ExampleDataUtils.createExampleDataFile("myfilesub", new int[] {2, 5, 100, 200});
		final String pathsub = ExampleDataUtils.createExampleDataFile("myfile", new int[] {3, 100, 200});
		
		if (path == null) fail("Could not create data file");
		
		IConversionService service = (IConversionService)ServiceManager.getService(IConversionService.class);
		IConversionContext context = service.open(path);

		context.setConversionScheme(new ProcessConversionScheme());
		context.setDatasetName("/entry1/data/data");
		context.addSliceDimension(0, "all");
		context.addSliceDimension(1, "all");
		Map<Integer, String> axesNames = new HashMap<Integer,String>();
		axesNames.put(1, "/entry1/data/axis0");
		axesNames.put(2, "/entry1/data/axis1");
		axesNames.put(3, "/entry1/data/axis2");
		axesNames.put(4, "/entry1/data/axis3");
		context.setAxesNames(axesNames);

		File f = new File(path);
		String outpath = f.getParentFile().getAbsolutePath() + File.separator + "output" + File.separator;
		File o = new File(outpath);
		o.mkdirs();
		o.deleteOnExit();

		context.setOutputPath(outpath);
		context.setUserObject(new IProcessingConversionInfo() {

			@Override
			public IOperation[] getOperationSeries() {
				ExampleExternalDataSubtractionOperation subop = new ExampleExternalDataSubtractionOperation();
				ExampleExternalDataModel mod = new ExampleExternalDataModel();
				mod.setFilePath(pathsub);
				mod.setDatasetName("/entry1/data/data");
				subop.setModel(mod);
				return new IOperation[]{subop};
			}

			@Override
			public IExecutionVisitor getExecutionVisitor(String fileName) {
				return new NexusFileExecutionVisitor(fileName);
			}

			@Override
			public ProcessingOutputType getProcessingOutputType() {
				return ProcessingOutputType.PROCESSING_ONLY;
			}
			
			@Override
			public ExecutionType getExecutionType() {
				return ExecutionType.SERIES;
			}
			
			@Override
			public int getPoolSize() {
				return 1;
			}

			@Override
			public boolean isTryParallel() {
				// TODO Auto-generated method stub
				return false;
			}
		});

		service.process(context);

		File f1 = new File(outpath);
		File[] matchingFiles = f1.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("myfilesub") && name.endsWith("nxs");
			}
		});

		File result = matchingFiles[0];
		result.deleteOnExit();
		IDataHolder dh = LoaderFactory.getData(result.getAbsolutePath());

		IDataset d1 = dh.getLazyDataset("/entry/result/data").getSlice();
		double val = d1.getDouble(0, 0, 0, 0);
		assertEquals(0, val, 0);
		
	}
	
	@Test
	public void normExample() throws Exception {
		
		String path = ExampleDataUtils.createExampleDataFile("myfilenorm", new int[] {10, 100, 200});
		final String pathn = ExampleDataUtils.createExampleDataFile("myfile", new int[] {10});

		if (path == null) fail("Could not create data file");

		IConversionService service = (IConversionService)ServiceManager.getService(IConversionService.class);
		IConversionContext context = service.open(path);

		context.setConversionScheme(new ProcessConversionScheme());
		context.setDatasetName("/entry1/data/data");
		context.addSliceDimension(0, "all");
		Map<Integer, String> axesNames = new HashMap<Integer,String>();
		axesNames.put(1, "/entry1/data/axis0");
		axesNames.put(2, "/entry1/data/axis1");
		axesNames.put(3, "/entry1/data/axis2");
		context.setAxesNames(axesNames);

		File f = new File(path);
		String outpath = f.getParentFile().getAbsolutePath() + File.separator + "output" + File.separator;
		File o = new File(outpath);
		o.mkdirs();
		o.deleteOnExit();

		context.setOutputPath(outpath);
		context.setUserObject(new IProcessingConversionInfo() {

			@Override
			public IOperation[] getOperationSeries() {
				ExampleExternalDataNormalisationOperation subop = new ExampleExternalDataNormalisationOperation();
				ExampleExternalDataModel mod = new ExampleExternalDataModel();
				mod.setFilePath(pathn);
				mod.setDatasetName("/entry1/data/data");
				subop.setModel(mod);
				return new IOperation[]{subop};
			}

			@Override
			public IExecutionVisitor getExecutionVisitor(String fileName) {
				return new NexusFileExecutionVisitor(fileName);
			}

			@Override
			public ProcessingOutputType getProcessingOutputType() {
				return ProcessingOutputType.PROCESSING_ONLY;
			}
			
			@Override
			public ExecutionType getExecutionType() {
				return ExecutionType.SERIES;
			}
			
			@Override
			public int getPoolSize() {
				return 1;
			}

			@Override
			public boolean isTryParallel() {
				// TODO Auto-generated method stub
				return false;
			}
		});

		service.process(context);

		File f1 = new File(outpath);
		File[] matchingFiles = f1.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("myfilenorm") && name.endsWith("nxs");
			}
		});

		File result = matchingFiles[0];
		result.deleteOnExit();
		IDataHolder dh = LoaderFactory.getData(result.getAbsolutePath());

		IDataset d1 = dh.getLazyDataset("/entry/result/data").getSlice();
		double val = d1.getDouble(0, 0, 0);
		assertEquals(1, val, 0);
		
	}

}
