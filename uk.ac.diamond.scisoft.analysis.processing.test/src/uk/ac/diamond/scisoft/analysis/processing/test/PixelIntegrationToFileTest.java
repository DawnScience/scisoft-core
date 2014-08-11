package uk.ac.diamond.scisoft.analysis.processing.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.hdf5.H5Utils;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.ILazyLoader;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.PowderIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.visitors.HierarchicalFileExecutionVisitor;

public class PixelIntegrationToFileTest {
	
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
	public void testIntegration() throws Exception {
		
		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
		final IDataset innerDS = Random.rand(0.0, 1000.0, 24, 1000, 1000);
		int[] dsShape = new int[]{24, 1000, 1000};
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
		Map<Integer, String> slMap = new HashMap<Integer, String>();
		slMap.put(0, "all");
////		slMap.put(1, "all");
//		slMap.put(0, "2:10:2");
//		slMap.put(1, "0:10:3");
		
		rand.setSlicing(slMap);
		
		final IDataset axDataset1 = DatasetFactory.createRange(24,AbstractDataset.INT16);
		axDataset1.setShape(new int[] {24,1,1});
		axDataset1.setName("z");
		
		final IDataset axDataset2 = DatasetFactory.createRange(1000,AbstractDataset.INT32);
		axDataset2.setShape(new int[] {1,1000,1});
		axDataset2.setName("y");
		
		final IDataset axDataset3 = DatasetFactory.createRange(1000,AbstractDataset.INT32);
		axDataset3.setShape(new int[] {1,1,1000});
		axDataset3.setName("x");
		
		AxesMetadataImpl am = new AxesMetadataImpl(3);
		am.addAxis(axDataset1, 0);
		am.addAxis(axDataset2, 1);
		am.addAxis(axDataset3, 2);
		
		lz.addMetadata(am);
		
		
		//Import metadata
		final IOperation di = service.findFirst("Diffraction");
		DiffractionMetadataImportModel model = new DiffractionMetadataImportModel();
		model.setMetadata(new DiffractionMetadata("", dp, ce));
		di.setModel(model);
		
		//pixel integration
		final IOperation azi = new PixelIntegrationOperation();
		azi.setModel(new PowderIntegrationModel());
		
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			final IHierarchicalDataFile file = HierarchicalDataFactory.getWriter(tmp.getAbsolutePath());
			
			long time =  System.currentTimeMillis();
			
			service.executeSeries(rand, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(file), di,azi);
			
			System.out.println( System.currentTimeMillis()  - time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("debug");
			
	}

}
