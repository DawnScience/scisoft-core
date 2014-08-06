package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.ILazyLoader;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadataImpl;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportModel;
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
		
		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
		
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
		
		final IRichDataset   rand = new RichDataset(lz, null, null, null, null);
		rand.setSlicing("all"); // All 24 images in first dimension.
		
		final IDataset axDataset1 = DatasetFactory.createRange(24,AbstractDataset.INT16);
		axDataset1.setShape(new int[] {24,1,1});
		
		final IDataset axDataset2 = DatasetFactory.createRange(1000,AbstractDataset.INT32);
		axDataset2.setShape(new int[] {1,1000,1});
		
		final IDataset axDataset3 = DatasetFactory.createRange(1000,AbstractDataset.INT32);
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
		final IOperation di = service.findFirst("Diffraction");
		DiffractionMetadataImportModel model = new DiffractionMetadataImportModel();
		model.setMetadata(new DiffractionMetadata("", dp, ce));
		di.setModel(model);
		
		//pixel integration
		final IOperation azi = service.findFirst("Pixel");
		azi.setModel(new PowderIntegrationModel());
		
		
		service.executeSeries(rand, new IMonitor.Stub(),new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape) throws Exception {
				
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
