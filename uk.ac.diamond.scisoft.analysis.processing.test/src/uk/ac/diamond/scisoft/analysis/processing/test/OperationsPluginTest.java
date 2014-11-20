/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.Activator;

/**
 * Must have OSGI so this is a junit plugin test.
 * @author Matthew Gerring
 *
 */
public class OperationsPluginTest {

	@Test
	public void testGetService() throws Exception {
		final IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		if (service == null) throw new Exception("Cannot get the service!");
	}
	
	@Test
	public void testServiceHasOperations() throws Exception {
		final IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		if (service == null) throw new Exception("Cannot get the service!");
		
		final Collection<String> operations = service.getRegisteredOperations();
		if (operations==null || operations.isEmpty()) throw new Exception("No operations were registered!");
	}

	@Test
	public void testSimpleSubtract() throws Exception {
		
		final IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		if (service == null) throw new Exception("Cannot get the service!");
				
		final IOperation subtract = service.create("uk.ac.diamond.scisoft.analysis.processing.subtractOperation");
		subtract.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 1024, 1024));
		
		context.setVisitor(new IExecutionVisitor.Stub() {
			public void executed(OperationData result, IMonitor monitor) {
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    assert result.getData().getDouble(i,j)<0;
					}
				}
			}			
		});
		context.setSeries(subtract);
		service.execute(context);

	}

	@Test
	public void testSimpleAddAndSubtractUsingFind() throws Exception {
		
		final IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		if (service == null) throw new Exception("Cannot get the service!");
				
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IOperationContext context = service.createContext();
		context.setData(Random.rand(0.0, 10.0, 1024, 1024));
		
		subtract.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});
		add.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 101;
			}
		});
		
		context.setVisitor(new IExecutionVisitor.Stub() {
			public void executed(OperationData result, IMonitor monitor) {
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    assert result.getData().getDouble(i,j)>0;
					}
				}
			}			
		});
		context.setSeries(subtract, add);
		service.execute(context);
	}

}
