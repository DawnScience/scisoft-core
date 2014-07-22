package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.InvalidRankException;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class RankTest {

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
	
	private volatile int count;

	@Test
	public void testAnyRank() throws Exception {
		
		System.out.println("Testing function which can run with any rank of data");
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.fuctionOperation");
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setParameters(poly);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
		rand.setSlicing("all");
		
		count=0;
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				if (result.getData().getRank()!=2) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with iterating first dimension gave "+count+ "of rank 2");

		count=0;
		rand.setSlicing("all", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first and second dimension gave "+count+ "of rank 1");

		
		count=0;
		rand.setSlicing("8", "500", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				if (result.getData().getRank()!=0) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first, second and third dimension gave "+count+ "of rank 0");

	}

	@Test
	public void testInvalidSlice() throws Exception {
		
		try {
			final IOperation add      = service.findFirst("add");
			add.setParameters(100);
			
			final IRichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
			rand.setSlicing("all", "500");
					
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add);
			
		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}
	
	@Test
	public void testInvalidRankOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi = service.findFirst("azimuthal");
		final IOperation add = service.findFirst("add");
		add.setParameters(100);

		// This order is ok
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Add followed by azi should give a 1D result!");
			}			
		}, add, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, azi, add);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

	
	@Test
	public void testComplexInvalidOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi = service.findFirst("azimuthal");
		final IOperation add = service.findFirst("add");
		final IOperation sub = service.findFirst("subtract");
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.fuctionOperation");
		
		// Parameters
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setParameters(poly);
		add.setParameters(100);
		sub.setParameters(100);

		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Add followed by azi should give a 1D result!");
			}			
		}, add, sub, function, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add, sub, function, azi, add);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

}
