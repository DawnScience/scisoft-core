package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.Activator;

public class OperationDataAxesRankTest {

	private static IOperationService service;
	private int count = 0;

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

	@SuppressWarnings("unchecked")
	@Test
	public void test2Dto2D() throws Exception {
		
		ILazyDataset ds = getDataset();
		
		final IOperationContext context = service.createContext();
		context.setData(ds);
		context.setSlicing("all"); // All 24 images in first dimension.
		

		final IOperation di = new Op2dto2d();

		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getRank() != 3) {
					throw new Exception("Unexpected data rank");
				}
				
				List<AxesMetadata> axes = integrated.getMetadata(AxesMetadata.class);
				ILazyDataset[] ax = axes.get(0).getAxes();
				
				assertEquals(ax.length, 3);
				assertArrayEquals(new int[]{1,1,1}, ax[0].getShape());
				assertArrayEquals(new int[]{1,5,1}, ax[1].getShape());
				assertArrayEquals(new int[]{1,1,10}, ax[2].getShape());
				
			}
		});
		context.setSeries(di);
		service.execute(context);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test2Dto1D() throws Exception {
		
		ILazyDataset ds = getDataset();
		
		final IOperationContext context = service.createContext();
		context.setData(ds);
		context.setSlicing("all"); // All 24 images in first dimension.
		
		final IOperation di = new Op2dto1d();

		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getRank() != 2) {
					throw new Exception("Unexpected data rank");
				}
				
				List<AxesMetadata> axes = integrated.getMetadata(AxesMetadata.class);
				ILazyDataset[] ax = axes.get(0).getAxes();
				
				assertEquals(ax.length, 2);
				assertArrayEquals(new int[]{1,1}, ax[0].getShape());
				assertArrayEquals(new int[]{1,10}, ax[1].getShape());
			}
		});
		context.setSeries(di);
		service.execute(context);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test1Dto1D() throws Exception {
		
		ILazyDataset ds = getDataset();
		
		final IOperationContext context = service.createContext();
		context.setData(ds);
		context.setSlicing("all","all"); // All 24 images in first dimension.		


		final IOperation di = new Op1dto1d();
//		
//		//pixel integration
//		final IOperation azi = new PixelIntegrationOperation();
//		azi.setModel(new PowderIntegrationModel());
//		
//		
		context.setVisitor(new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getRank() != 3) {
					throw new Exception("Unexpected data rank");
				}
				
				List<AxesMetadata> axes = integrated.getMetadata(AxesMetadata.class);
				ILazyDataset[] ax = axes.get(0).getAxes();
				
				assertEquals(ax.length, 3);
				assertArrayEquals(new int[]{1,1,1}, ax[0].getShape());
				assertArrayEquals(new int[]{1,1,1}, ax[1].getShape());
				assertArrayEquals(new int[]{1,1,10}, ax[2].getShape());
			}
		});
		context.setSeries(di);
		service.execute(context);
	}
	
//	@Test
//	public void test1Dto2D() throws Exception {
//		
//		ILazyDataset ds = getDataset();
//		
//		final RichDataset   rand = new RichDataset(ds, null, null, null, null);
//		rand.setSlicing("all","all"); // All 24 images in first dimension.
//		
//
//
//		final IOperation di = new Op1dto2d();
////		
////		//pixel integration
////		final IOperation azi = new PixelIntegrationOperation();
////		azi.setModel(new PowderIntegrationModel());
////		
////		
//		service.executeSeries(rand, new IMonitor.Stub(),new IExecutionVisitor.Stub() {
//			@Override
//			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
//				
//				final IDataset integrated = result.getData();
//				if (integrated.getRank() != 3) {
//					throw new Exception("Unexpected data rank");
//				}
//				
//				List<AxesMetadata> axes = integrated.getMetadata(AxesMetadata.class);
//				ILazyDataset[] ax = axes.get(0).getAxes();
//				
//				assertEquals(ax.length, 2);
//				IDataset t = ax[0].getSlice();
//				t.squeeze();
//				assertEquals(t.getShort(), count++);
//				assertEquals(integrated.getSize(), ax[1].getSlice().getSize());
//			}
//		}, di);
//	}
	
	private ILazyDataset getDataset() {
		int[] dsShape = new int[]{24, 1000, 1000};

		final IDataset innerDS = Random.rand(0.0, 1000.0, 24, 1000, 1000);

		ILazyDataset lz = new LazyDataset("test", Dataset.FLOAT64, dsShape, new ILazyLoader() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isFileReadable() {
				return true;
			}

			@Override
			public IDataset getDataset(IMonitor mon, int[] shape, int[] start,
					int[] stop, int[] step) throws Exception {
				// TODO Auto-generated method stub
				return innerDS.getSlice(mon, start, stop, step);
			}
		});

		final IDataset axDataset1 = DatasetFactory.createRange(24,Dataset.INT16);
		axDataset1.setShape(new int[] {24,1,1});

		final IDataset axDataset2 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset2.setShape(new int[] {1,1000,1});

		final IDataset axDataset3 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset3.setShape(new int[] {1,1,1000});

		AxesMetadataImpl am = new AxesMetadataImpl(3);
		am.addAxis(0, axDataset1);
		am.addAxis(1, axDataset2);
		am.addAxis(2, axDataset3);

		lz.addMetadata(am);

		return lz;
	}
	
	private class Op2dto2d extends AbstractOperation<IOperationModel, OperationData> {

		
		protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
			
			Dataset ones = DatasetFactory.ones(new int[] {5, 10}, Dataset.INT16);
			Dataset ax1 = DatasetFactory.createRange(5, Dataset.INT16);
			ax1.setShape(new int[]{5,1});
			Dataset ax2 = DatasetFactory.createRange(10, Dataset.INT16);
			ax2.setShape(new int[]{1,10});
			AxesMetadataImpl md = new AxesMetadataImpl(2);
			md.addAxis(0, ax1);
			md.addAxis(1, ax2);
			ones.setMetadata(md);
			
			return new OperationData(ones);
		}
		
		@Override
		public String getId() {
			return "junk";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.TWO;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.TWO;
		}
		
	}
	
	private class Op1dto2d extends AbstractOperation<IOperationModel, OperationData> {

		
		protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
			
			Dataset ones = DatasetFactory.ones(new int[] {5, 10}, Dataset.INT16);
			Dataset ax1 = DatasetFactory.createRange(5, Dataset.INT16);
			ax1.setShape(new int[]{5,1});
			Dataset ax2 = DatasetFactory.createRange(10, Dataset.INT16);
			ax2.setShape(new int[]{1,10});
			AxesMetadataImpl md = new AxesMetadataImpl(2);
			md.addAxis(0, ax1);
			md.addAxis(1, ax2);
			ones.setMetadata(md);
			
			return new OperationData(ones);
		}
		
		@Override
		public String getId() {
			return "junk";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.ONE;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.TWO;
		}
		
	}
	
	private class Op2dto1d extends AbstractOperation<IOperationModel, OperationData> {

		
		protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
			
			Dataset ones = DatasetFactory.ones(new int[] {10}, Dataset.INT16);
			Dataset ax1 = DatasetFactory.createRange(10, Dataset.INT16);
			ax1.setShape(new int[]{10});
			AxesMetadataImpl md = new AxesMetadataImpl(1);
			md.addAxis(0, ax1);
			ones.setMetadata(md);
			
			return new OperationData(ones);
		}
		
		@Override
		public String getId() {
			return "junk";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.TWO;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.ONE;
		}
		
	}
	
private class Op1dto1d extends AbstractOperation<IOperationModel, OperationData> {

		
		protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
			
			Dataset ones = DatasetFactory.ones(new int[] {10}, Dataset.INT16);
			Dataset ax1 = DatasetFactory.createRange(10, Dataset.INT16);
			ax1.setShape(new int[]{10});
			AxesMetadataImpl md = new AxesMetadataImpl(1);
			md.addAxis(0, ax1);
			ones.setMetadata(md);
			
			return new OperationData(ones);
		}
		
		@Override
		public String getId() {
			return "junk";
		}

		@Override
		public OperationRank getInputRank() {
			return OperationRank.ONE;
		}

		@Override
		public OperationRank getOutputRank() {
			return OperationRank.ONE;
		}
		
	}
	
}
