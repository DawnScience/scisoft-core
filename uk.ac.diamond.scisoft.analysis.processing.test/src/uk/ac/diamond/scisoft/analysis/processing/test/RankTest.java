package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
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
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
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
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation");
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public IFunction getFunction() {
				return poly;
			}
		});
		
		final RichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
		rand.setSlicing("all");
		
		count=0;
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=2) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with iterating first dimension gave "+count+ "of rank 2");

		count=0;
		rand.setSlicing("all", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first and second dimension gave "+count+ "of rank 1");

		
		count=0;
		rand.setSlicing("8", "500", "500");
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=0) throw new Exception("Unexpcected rank found!");
				count++;
			}			
		}, function);
		System.out.println("Run with slicing first, second and third dimension gave "+count+ "of rank 0");

	}

	@Test
	public void testInvalidSlice() throws Exception {
		
		try {
			final IOperation box      = service.findFirst("box");

			final RichDataset   rand = new RichDataset(Random.rand(0.0, 10.0, 10, 1024, 1024), null);
			rand.setSlicing("all", "500");
					
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, box);
			
		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}
	
	@Test
	public void testInvalidRankOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi = service.findFirst("azimuthal");
		azi.setModel(new AbstractOperationModel() {
			
			public IROI getRegion() {
				return sector;
			}
			
		});
		final IOperation box = service.findFirst("box");
		final IOperation add = service.findFirst("add");
		add.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});

		// This order is ok
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Add followed by azi should give a 1D result!");
			}			
		}, add, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, azi, box);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

	
	@Test
	public void testComplexInvalidOrder() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation azi      = service.findFirst("azimuthal");
		azi.setModel(new AbstractOperationModel() {
			
			public IROI getRegion() {
				return sector;
			}
			
		});
		final IOperation add      = service.findFirst("add");
		final IOperation sub      = service.findFirst("subtract");
		final IOperation function = service.create("uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation");
		final IOperation box      = service.findFirst("box");
		
		// Parameters
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		function.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public IFunction getFunction() {
				return poly;
			}
		});
		add.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});
		sub.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public double getValue() {
				return 100;
			}
		});

		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				if (result.getData().getRank()!=1) throw new Exception("Azi should give a 1D result!");
			}			
		}, add, sub, function, azi);

		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add, sub, function, azi, box);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

	
	@Test
	public void testFindByRank() throws Exception {

		Collection<IOperation> ones = service.find(OperationRank.ONE, true);
		if (ones.isEmpty()) throw new Exception("No one dimensional inputs found but there should be fitting!");
		
		Collection<IOperation> twos = service.find(OperationRank.TWO, true);
		if (twos.isEmpty()) throw new Exception("No two dimensional inputs found but there should be integration!");
		
		ones = service.find(OperationRank.ONE, false);
		if (ones.isEmpty()) throw new Exception("No one dimensional outputs found but there should be integration!");
		
		twos = service.find(OperationRank.SAME, false);
		if (twos.isEmpty()) throw new Exception("No twos dimensional outputs found but there should be add/subtract!");

	}
	
	
	@Test
	public void testFittingImages() throws Exception {

		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);

		final RichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 2, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 2 images in first dimension.

		final IOperation add      = service.findFirst("add");
		final IOperation fitting  = service.findFirst("fitting");
		
		// This order is not ok.
		try {
			service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
					throw new Exception("Unexpected execution of invalid pipeline!");
				}			
			}, add, fitting);

		} catch (InvalidRankException expected) {
			return;
		}

		throw new Exception("A invalid slice rank not detected!");
	}

}
