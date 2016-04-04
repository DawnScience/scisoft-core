/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.*;

import java.io.File;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.hdf5.operation.HierarchicalFileExecutionVisitor;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.actor.runner.GraphRunner;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;

public class HierarchicalFileExVistWorkflowRunnerTest {
	
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
	
	@Test
	public void TestMultipleAxesNotWritten() throws Exception {
		
		int[] inputShape = new int[] {30,1000};
		
		ILazyDataset lazy = HierarchicalFileExVisitorTest.getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1});
		
		Junk1Dto1DOperation op1 = new Junk1Dto1DOperation();
		op1.setModel(new Junk1DModel());
		op1.setStoreOutput(true);
		Junk1Dto1DOperation op2 = new Junk1Dto1DOperation();
		op2.setModel(new Junk1DModel());
		op2.setSleep(100);
		Junk1Dto1DOperation op3 = new Junk1Dto1DOperation();
		op3.setModel(new Junk1DModel());
		op3.setSleep(100);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op1, op2, op3);
		context.setExecutionType(ExecutionType.PARALLEL);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/intermediate/0-Junk1Dto1DOperation/data"));
		assertTrue(dh.contains("/entry/intermediate/0-Junk1Dto1DOperation/Axis_0"));
		assertTrue(dh.contains("/entry/intermediate/0-Junk1Dto1DOperation/Junk1Dax"));
		assertFalse(dh.contains("/entry/intermediate/0-Junk1Dto1DOperation/Junk1Dax1"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		assertFalse(dh.contains("/entry/result/Junk1Dax1"));
		
		

			
	}

}
