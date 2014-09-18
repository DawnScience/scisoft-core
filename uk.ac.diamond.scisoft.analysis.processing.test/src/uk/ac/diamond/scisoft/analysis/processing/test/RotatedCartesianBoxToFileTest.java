package uk.ac.diamond.scisoft.analysis.processing.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.operations.RotatedCartesianBox;
import uk.ac.diamond.scisoft.analysis.processing.operations.RotatedCartesianBoxModel;
import uk.ac.diamond.scisoft.analysis.processing.visitors.HierarchicalFileExecutionVisitor;

public class RotatedCartesianBoxToFileTest {
	
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
		final IDataset innerDS = DoubleDataset.createRange(1000000);
		int[] dsShape = new int[]{100, 100, 100};
		innerDS.resize(100, 100, 100);
		ILazyDataset lz = new LazyDataset("test", Dataset.FLOAT64, dsShape, new ILazyLoader() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isFileReadable() {
				return true;
			}
			
			@Override
			public IDataset getDataset(IMonitor mon, int[] shape, int[] start,
					int[] stop, int[] step) throws Exception {
				return innerDS.getSlice(mon, start, stop, step);
			}
		});
		
		final RichDataset   richDataset = new RichDataset(lz, null, null, null, null);
		Map<Integer, String> sliceMap = new HashMap<Integer, String>();
		sliceMap.put(0, "all");
		
		richDataset.setSlicing(sliceMap);
		
		final IDataset axDataset1 = DatasetFactory.createRange(100,Dataset.INT16);
		axDataset1.setShape(new int[] {100,1,1});
		axDataset1.setName("z");
		
		final IDataset axDataset2 = DatasetFactory.createRange(100,Dataset.INT32);
		axDataset2.setShape(new int[] {1,100,1});
		axDataset2.setName("y");
		
		final IDataset axDataset3 = DatasetFactory.createRange(100,Dataset.INT32);
		axDataset3.setShape(new int[] {1,1,100});
		axDataset3.setName("x");
		
		AxesMetadataImpl am = new AxesMetadataImpl(3);
		am.addAxis(axDataset1, 0);
		am.addAxis(axDataset2, 1);
		am.addAxis(axDataset3, 2);
		
		lz.addMetadata(am);
		
		//pixel integration
		final IOperation rotatedCartesianBox = new RotatedCartesianBox();
		RotatedCartesianBoxModel parameters = new RotatedCartesianBoxModel();
		parameters.setRoi(new RectangularROI(30,30,40,40,0.0));
		rotatedCartesianBox.setModel(parameters);
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			long time =  System.currentTimeMillis();
			
			service.executeSeries(richDataset, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), rotatedCartesianBox);
			
			System.out.println( System.currentTimeMillis()  - time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("debug");
			
	}

}
