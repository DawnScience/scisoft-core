package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor.Stub;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class IntegrationTest {

	
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
	public void testAzimuthal() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 24, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IOperation azi = service.findFirst("azimuthal");
		
		service.executeSeries(rand, new IMonitor.Stub(),new IExecutionVisitor.Stub() {
			public void executed(OperationData result) throws Exception {
				
				final IDataset integrated = result.getData();
				System.out.println(Arrays.toString(integrated.getShape()));
			}
		}, azi);
	}
}
