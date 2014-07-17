package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Collection;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;

/**
 * Must have OSGI so this is a junit plugin test.
 * @author fcp94556
 *
 */
public class OperationsTest {

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
		
		final Collection<String> operations = service.getRegisteredOperations();
		if (operations==null || operations.isEmpty()) throw new Exception("No operations were registered!");
		
		final IOperation subtract = service.create("uk.ac.diamond.scisoft.analysis.processing.subtractOperation");
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 1024, 1024), null);
		subtract.setData(rand);
		subtract.setParameters(100);
		
		final IRichDataset   result = service.executeSeries(subtract);
		IDataset all = result.getData().getSlice((Slice)null);
		
		for (int i = 0; i < all.getShape()[0]; i++) {
			for (int j = 0; j < all.getShape()[0]; j++) {
			    assert all.getDouble(i,j)<0;
			}
		}
	}

	@Test
	public void testSimpleAddAndSubtractUsingFind() throws Exception {
		
		final IOperationService service = (IOperationService)Activator.getService(IOperationService.class);
		if (service == null) throw new Exception("Cannot get the service!");
		
		final Collection<String> operations = service.getRegisteredOperations();
		if (operations==null || operations.isEmpty()) throw new Exception("No operations were registered!");
		
		final IOperation add      = service.findFirst("add");
		final IOperation subtract = service.findFirst("subtract");
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 1024, 1024), null);
		subtract.setData(rand);
		subtract.setParameters(100);
		add.setParameters(101);
		
		final IRichDataset   result = service.executeSeries(subtract, add);
		IDataset all = result.getData().getSlice((Slice)null);
		
		for (int i = 0; i < all.getShape()[0]; i++) {
			for (int j = 0; j < all.getShape()[0]; j++) {
			    assert all.getDouble(i,j)>0;
			}
		}
	}

}
