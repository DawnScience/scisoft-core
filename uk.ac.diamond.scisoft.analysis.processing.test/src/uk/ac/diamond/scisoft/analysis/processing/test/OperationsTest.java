package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;

/**
 * Works with ordinary junit but therefore does not test the extension points
 * 
 * @author fcp94556
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
		subtract.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 1024, 1024), null);
		
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(i,j)>0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		}, subtract);
	}

	@Test
	public void testSimpleAddAndSubtractUsingFind() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 1024, 1024), null);
		
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
		
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found! "+result.getData().getDouble(i,j));
					}
				}
			}			
		}, subtract, add);
	}

	private volatile int counter;
	
	@Test
	public void testSimpleAddAndSubtractOnStack() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 24, 1024, 1024), null);
		rand.setSlicing("all"); // 
		
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
		
		counter = 0;
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				System.out.println(result.getData().getName());
				counter++;
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					for (int j = 0; j < result.getData().getShape()[1]; j++) {
					    if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found!");
					}
				}
			}			
		}, subtract, add);
		
		if ( counter != 24 ) throw new Exception();
	}

	@Test
	public void testSimpleAddAndSubtractOnStackParallel() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 24, 1024, 1024), null);
		rand.setSlicing("all"); // 
		
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
		
		counter = 0;
		try {
			service.setParallelTimeout(Long.MAX_VALUE);
	
			service.executeParallelSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
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
	
					counter++;
					for (int i = 0; i < result.getData().getShape()[0]; i++) {
						for (int j = 0; j < result.getData().getShape()[1]; j++) {
						    if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found!");
						}
					}
				}			
			}, subtract, add);
			
		} finally {
			service.setParallelTimeout(5000);
		}
		
		if ( counter != 24 ) throw new Exception("Not all jobs completed before timeout in parallel run!");
	}

	@Test
	public void testParallelLongerThanDefault() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 24, 1024, 1024), null);
		rand.setSlicing("all"); // 
		
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
				
		try {
			service.executeParallelSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
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
			}, subtract, add);
		} catch (Exception shouldThrowException) {
			return;
		}
		
		throw new Exception("The default wait time of 5000ms should have been ");
	}

	@Test
	public void testParallelTimeout() throws Exception {
						
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 24, 1024, 1024), null);
		rand.setSlicing("all"); // 
		
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
				
		counter = 0;
		
		try {
			service.setParallelTimeout(Long.MAX_VALUE);
			service.executeParallelSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
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
						    if ( result.getData().getDouble(i,j)<0 ) throw new Exception("Incorrect value found!");
						}
					}
				}			
			}, subtract, add);
			
			if ( counter != 24 ) throw new Exception("Not all jobs completed before timeout in parallel run!");
			
		} finally {
			service.setParallelTimeout(5000);
		}

	}

}
