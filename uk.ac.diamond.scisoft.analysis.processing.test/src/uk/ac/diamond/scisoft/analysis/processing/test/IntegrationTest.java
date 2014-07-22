package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
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

	private volatile int count;
	@Test
	public void testAzimuthalSimpleMask() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(1000,1000);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 24, 1000, 1000), null, mask, null, Arrays.asList(sector));
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IOperation azi = service.findFirst("azimuthal");
		
		count = 0;
		service.executeSeries(rand, new IMonitor.Stub(),new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getSize()!=472) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}
				
				count++;
			}
		}, azi);
		
		if (count!=24) throw new Exception("Size of integrated results incorrect!");
	}
	
	@Test
	public void testAzimuthalThresholdMask() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(24, 1000, 1000);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 24, 1000, 1000), null, mask, null, Arrays.asList(sector));
				
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IOperation azi = service.findFirst("azimuthal");
		
		count = 0;
		service.executeSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			
			@Override
			public OperationData filter(OperationData od, IMonitor monitor) throws Exception {
				
				final BooleanDataset mask = (BooleanDataset)od.getMask();
				if (!isCompatible(od.getData().getShape(), mask.getShape())) {
					throw new Exception("Mask is incorrect shape!");
				}
				
				// Apply a threshold
				PositionIterator it = new PositionIterator(mask.getShape());
				while (it.hasNext()) {
									
					int[] pos = it.getPos();
					if (od.getData().getDouble(pos)>750 || od.getData().getDouble(pos)<250) {
						mask.set(false, pos);
					}
				}
				monitor.worked(1);
				return od;
			}

			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {

				final IDataset integrated = result.getData();
				if (integrated.getSize()!=472) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}
				
				count++;
			}
		}, azi);
		
		if (count!=24) throw new Exception("Size of integrated results incorrect!");

	}

	@Test
	public void testAzimuthalThresholdMaskParallel() throws Exception {
		
		final IROI         sector = new SectorROI(500.0, 500.0, 20.0, 300.0,  Math.toRadians(90.0), Math.toRadians(180.0));
		final BooleanDataset mask = BooleanDataset.ones(24, 1000, 1000);
		
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 24, 1000, 1000), null, mask, null, Arrays.asList(sector));
				
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IOperation azi = service.findFirst("azimuthal");
		
		count = 0;
		try {
			service.setParallelTimeout(Long.MAX_VALUE);
			
			service.executeParallelSeries(rand, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				
				@Override
				public OperationData filter(OperationData od, IMonitor monitor) throws Exception {
					
					final BooleanDataset mask = (BooleanDataset)od.getMask();
					if (!isCompatible(od.getData().getShape(), mask.getShape())) {
						throw new Exception("Mask is incorrect shape!");
					}
					
					// Apply a threshold
					PositionIterator it = new PositionIterator(mask.getShape());
					while (it.hasNext()) {
										
						int[] pos = it.getPos();
						if (od.getData().getDouble(pos)>750 || od.getData().getDouble(pos)<250) {
							mask.set(false, pos);
						}
					}
					monitor.worked(1);
					return od;
				}
	
				@Override
				public void executed(OperationData result, IMonitor monitor) throws Exception {
	
					final IDataset integrated = result.getData();
					if (integrated.getSize()!=472) {
						throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
					}
					
					count++;
				}
			}, azi);
		} finally {
			service.setParallelTimeout(5000);
		}
		
		if (count!=24) throw new Exception("Size of integrated results incorrect! Results found was "+count);

	}

	protected static boolean isCompatible(final int[] ashape, final int[] bshape) {

		List<Integer> alist = new ArrayList<Integer>();

		for (int a : ashape) {
			if (a > 1) alist.add(a);
		}

		final int imax = alist.size();
		int i = 0;
		for (int b : bshape) {
			if (b == 1)
				continue;
			if (i >= imax || b != alist.get(i++))
				return false;
		}

		return i == imax;
	}

}
