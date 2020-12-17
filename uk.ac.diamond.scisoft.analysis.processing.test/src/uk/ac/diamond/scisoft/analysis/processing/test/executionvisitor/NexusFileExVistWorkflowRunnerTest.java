/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class NexusFileExVistWorkflowRunnerTest {
	
	private static IOperationService service;
	
	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		
		service = new OperationServiceImpl();
		service.createOperations(service.getClass(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	@Test
	public void TestMultipleAxesNotWritten() throws Exception {
		
		int[] inputShape = new int[] {30,1000};
		
		ILazyDataset lazy = NexusFileExecutionVisitorTest.getLazyDataset(inputShape,1);
		
		
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
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op1, op2, op3);
		context.setExecutionType(ExecutionType.PARALLEL);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/processed/intermediate/0-Junk1Dto1DOperation/data"));
		assertTrue(dh.contains("/processed/intermediate/0-Junk1Dto1DOperation/Axis_0"));
		assertTrue(dh.contains("/processed/intermediate/0-Junk1Dto1DOperation/Junk1Dax"));
		assertFalse(dh.contains("/processed/intermediate/0-Junk1Dto1DOperation/Junk1Dax1"));
		assertTrue(dh.contains("/processed/result/Junk1Dax"));
		assertFalse(dh.contains("/processed/result/Junk1Dax1"));
		
		

			
	}

}
