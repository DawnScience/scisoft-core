package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
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
		subtract.setParameters(100);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 1024, 1024), null);
		
		service.executeSeries(rand, new IExecutionVisitor.Stub() {
			public void executed(IDataset result) {
				for (int i = 0; i < result.getShape()[0]; i++) {
					for (int j = 0; j < result.getShape()[0]; j++) {
					    assert result.getDouble(i,j)<0;
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
		
		subtract.setParameters(100);
		add.setParameters(101);
		
		service.executeSeries(rand, new IExecutionVisitor.Stub() {
			public void executed(IDataset result) {
				for (int i = 0; i < result.getShape()[0]; i++) {
					for (int j = 0; j < result.getShape()[0]; j++) {
					    assert result.getDouble(i,j)>0;
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
		
		subtract.setParameters(100);
		add.setParameters(101);
		
		counter = 0;
		service.executeSeries(rand, new IExecutionVisitor.Stub() {
			public void executed(IDataset result) {
				
				System.out.println(result.getName());
				counter++;
				for (int i = 0; i < result.getShape()[0]; i++) {
					for (int j = 0; j < result.getShape()[0]; j++) {
					    assert result.getDouble(i,j)>0;
					}
				}
			}			
		}, subtract, add);
		
		assert counter == 24;
	}


}
