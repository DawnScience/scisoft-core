package uk.ac.diamond.scisoft.analysis.processing.test;

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
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.PowderIntegrationModel;

public class PixelIntegrationTest {

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
	
	@Test
	public void testIntegration() throws Exception {
		
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
		
		final RichDataset   rand = new RichDataset(lz, null, null, null, null);
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IDataset axDataset1 = DatasetFactory.createRange(24,Dataset.INT16);
		axDataset1.setShape(new int[] {24,1,1});
		
		final IDataset axDataset2 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset2.setShape(new int[] {1,1000,1});
		
		final IDataset axDataset3 = DatasetFactory.createRange(1000,Dataset.INT32);
		axDataset3.setShape(new int[] {1,1,1000});
		
		AxesMetadataImpl am = new AxesMetadataImpl(3);
		am.addAxis(axDataset1, 0);
		am.addAxis(axDataset2, 1);
		am.addAxis(axDataset3, 2);
		
		lz.addMetadata(am);
		
		final IDataset masDataset = BooleanDataset.ones(new int[] {1000, 1000}, Dataset.BOOL);
		masDataset.setShape(new int[] {1,1000,1000});
		
		for (int i = 100 ; i < 200; i++) masDataset.set(0, new int[]{0,i,i});
		
		lz.addMetadata(new MaskMetadataImpl(masDataset));
		
		//Import metadata
		final IOperation di = new DiffractionMetadataTestImportOperation();
		
		//pixel integration
		final IOperation azi = new PixelIntegrationOperation();
		azi.setModel(new PowderIntegrationModel());
		
		
		service.executeSeries(rand, new IMonitor.Stub(),new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
				
				final IDataset integrated = result.getData();
				if (integrated.getSize()!=1000) {
					throw new Exception("Unexpected azimuthal integration size! Size is "+integrated.getSize());
				}
				
				List<AxesMetadata> axes = integrated.getMetadata(AxesMetadata.class);
				ILazyDataset[] ax = axes.get(0).getAxes();
				
				assertEquals(ax.length, 2);
				IDataset t = ax[0].getSlice();
				t.squeeze();
				assertEquals(t.getShort(), count++);
				assertEquals(integrated.getSize(), ax[1].getSlice().getSize());
			}
		}, di,azi);
		
	}
	
	
}
