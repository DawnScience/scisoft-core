/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.actor.actors.OperationTransformer;
import uk.ac.diamond.scisoft.analysis.processing.actor.runner.GraphRunner;
import uk.ac.diamond.scisoft.analysis.processing.operations.ValueModel;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;

/**
 * Works with ordinary junit but therefore does not test the extension points
 * 
 * @author Matthew Gerring
 *
 */
public class OperationsTest {
	
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
	
	
	@Test
	public void testGetService() throws Exception {
		if (service == null) throw new Exception("Cannot get the service!");
	}
	
	@Test
	public void testServiceHasOperations() throws Exception {
		
		final Collection<String> operations = service.getRegisteredOperations();
		if (operations==null || operations.isEmpty()) throw new Exception("No operations were registered!");
	}

	@Test
	public void testSimpleSubtract() throws Exception {
				
		final IOperation subtract = service.create("uk.ac.diamond.scisoft.analysis.processing.subtractOperation");
		subtract.setModel(new ValueModel(100));
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 1024, 1024));
		context.setDataDimensions(new int[]{0,1});
		
		counter = 0;
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				++counter;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(i,j)>0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		});
		context.setSeries(subtract);
		service.execute(context);
		if (counter!=1) throw new Exception("Unexpected execution amount "+counter);
		
		// Test in graph mode
		counter = 0;
		context.setExecutionType(ExecutionType.GRAPH);
		service.execute(context);
		if (counter!=1) throw new Exception("Unexpected execution amount "+counter);
		
	}

	@Test
	public void testSimpleAddAndSubtractUsingFind() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtractOperation");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 1024, 1024));
		context.setDataDimensions(new int[]{0,1});
		
		subtract.setModel(new ValueModel(100));
		add.setModel(new ValueModel(101));
		
		counter = 0;
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				++counter;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found! "+result.getData().getDouble(i,j));
					}
				}
			}			
		});
		context.setSeries(subtract, add);
		service.execute(context);
		if (counter!=1) throw new Exception("Unexpected execution amount "+counter);
		
	
		// Test in graph mode
		counter = 0;
		context.setExecutionType(ExecutionType.GRAPH);
		service.execute(context);
		if (counter!=1) throw new Exception("Unexpected execution amount "+counter);

	}

	private volatile int counter;
	
	@Test
	public void testSimpleAddAndSubtractOnStack() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtractOperation");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 24, 1024, 1024));
//		context.setSlicing("all"); // 
		context.setDataDimensions(new int[]{1,2});
		
		subtract.setModel(new ValueModel(100));
		add.setModel(new ValueModel(101));
		
		counter = 0;
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				System.out.println(result.getData().getName());
				counter++;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(0,i,j)<0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		});
		context.setSeries(subtract, add);
		service.execute(context);	
		if ( counter != 24 ) throw new Exception("The counter is "+counter);
		
	
		// Test in graph mode
		counter = 0;
		context.setExecutionType(ExecutionType.GRAPH);
		service.execute(context);
		if ( counter != 24 ) throw new Exception("The counter is "+counter);
	}

	@Test
	public void testSimpleAddAndSubtractOnStackParallel() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtractOperation");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 24, 1024, 1024));
//		context.setSlicing("all"); // 
		context.setDataDimensions(new int[]{1,2});
		
		subtract.setModel(new ValueModel(100));
		add.setModel(new ValueModel(101));
		
		counter = 0;
		context.setParallelTimeout(Long.MAX_VALUE);
		context.setExecutionType(ExecutionType.PARALLEL);

		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				try {
					// This sleep simply introduces some random behaviour
					// on the parallel jobs so that we definitely get a different order.
					final long time = Math.round(Math.random()*1000);
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				++counter;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
						if ( result.getData().getDouble(0,i,j)<0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		});
		context.setSeries(subtract, add);
		service.execute(context);

		Thread.sleep(5000);
		if ( counter < 23 ) throw new Exception("Not all jobs completed before timeout in parallel run! Loop count was : "+counter);
	
	
		// Test in graph mode
		counter = 0;
		context.setExecutionType(ExecutionType.GRAPH);
		context.setPoolSize(Runtime.getRuntime().availableProcessors());
		service.execute(context);
		
		Thread.sleep(5000);
		if ( counter != 24 ) throw new Exception("The counter is "+counter);

	}


	@Test
	public void testParallelLongerThanDefault() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtractOperation");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 1024, 1024));
//		context.setSlicing("all"); // 
		context.setDataDimensions(new int[]{1,2});
		
		subtract.setModel(new ValueModel(100));
		add.setModel(new ValueModel(101));
			
		context.setExecutionType(ExecutionType.GRAPH);
		context.setParallelTimeout(5000);
	    context.setPoolSize(Runtime.getRuntime().availableProcessors());
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				try {
					// This sleep simply introduces some random behaviour
					// on the parallel jobs so that we 
					final long time = Math.round(Math.random()*10000);
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
						if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		});

		context.setSeries(subtract, add);
		try {
		    service.execute(context);
		} catch (OperationException ne) {
			return;
		}

		throw new Exception("The default wait time of 5000ms should have been reached!");
	}

	@Test
	public void testParallelTimeout() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtractOperation");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 24, 1024, 1024));
//		context.setSlicing("all"); // 
		context.setDataDimensions(new int[]{1,2});
		
		subtract.setModel(new ValueModel(100));
		add.setModel(new ValueModel(101));

		counter = 0;

		context.setParallelTimeout(Long.MAX_VALUE);
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				try {
					// This sleep simply introduces some random behaviour
					// on the parallel jobs so that we 
					final long time = Math.round(Math.random()*10000);
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter++;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
						if ( result.getData().getDouble(0,i,j)<0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		});
		
		context.setSeries(subtract, add);	
		context.setExecutionType(ExecutionType.PARALLEL);
		service.execute(context);
		Thread.sleep(3000);
		assertEquals("Not all jobs completed before timeout in parallel run! counter: " + counter + ", expected: 24", 24, counter);

		counter=0;
	    context.setExecutionType(ExecutionType.GRAPH);
	    context.setPoolSize(Runtime.getRuntime().availableProcessors());
	    service.execute(context);
		Thread.sleep(3000);
		assertEquals("Not all jobs completed before timeout in parallel run! counter: " + counter + ", expected: 24", 24, counter);
	}

}
