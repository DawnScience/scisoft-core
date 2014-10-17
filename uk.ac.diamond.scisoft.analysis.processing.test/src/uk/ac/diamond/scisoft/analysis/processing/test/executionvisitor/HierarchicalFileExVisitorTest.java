package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.dawb.common.services.IPersistenceService;
import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.api.processing.ISliceConfiguration;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.visitors.HierarchicalFileExecutionVisitor;

public class HierarchicalFileExVisitorTest {
	
	private static IOperationService service;

	@BeforeClass
	public static void before() throws Exception {
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		service = (IOperationService)Activator.getService(IOperationService.class);
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	@Test
	public void Process3DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape);
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op22,op21);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process4DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op22,op21);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/entry/result/Axis_1").getShape());
			assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process5DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {2,5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all","all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op22,op21);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],inputShape[2],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/entry/result/Axis_1").getShape());
			assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op11);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Axis_1"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	public ILazyDataset getLazyDataset(int[] dsShape) {
		
		final IDataset innerDS = Random.rand(dsShape);
		
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
				return innerDS.getSlice(mon, start, stop, step);
			}
		});
		
		AxesMetadataImpl am = new AxesMetadataImpl(dsShape.length);
		
		for (int i = 0; i < dsShape.length; i++) {
			IDataset ax = DatasetFactory.createRange(0, dsShape[i], 1, Dataset.INT16);
			int[] shape = new int[dsShape.length];
			Arrays.fill(shape, 1);
			shape[i] = dsShape[i];
			ax.setShape(shape);
			ax.setName("Axis_" + String.valueOf(i));
			am.setAxis(i, new ILazyDataset[]{ax});
		}
		
		lz.setMetadata(am);
		
		lz.setName("mainlazy");
		
		return lz;
	}
	
}
