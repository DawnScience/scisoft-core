package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;

import org.dawb.common.services.IPersistenceService;
import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.processing.RichDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.metadata.OriginMetadataImpl;
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
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
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
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
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
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
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
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
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
	
	@Test
	public void Process3DStackAs1DTo1DError() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op11);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/errors"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Axis_1"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/errors").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
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
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());
			assertArrayEquals(new int[]{inputShape[0],inputShape[1]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{2,3};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
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
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],auxShape[0],auxShape[1]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux1D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{3};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
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
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
			assertTrue(dh.contains("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],auxShape[0]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs2DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all");
		
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op21,op11);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			assertTrue(dh.contains("/entry/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
			assertTrue(dh.contains("/entry/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/data"));
			
			assertArrayEquals(new int[]{inputShape[0],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/data").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	
	@Test
	public void Process3DStackAs2DTo1DNoAxes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,false);
		
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
//			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
//			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto2DOperation op11 = new Junk1Dto2DOperation();
		op11.setModel(new Junk2Dto2Dmodel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op11);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Axis_1"));
			assertTrue(dh.contains("/entry/result/Junk2Dto2DAx1"));
			assertTrue(dh.contains("/entry/result/Junk2Dto2DAx2"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim(),op11.getModel().getyDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/entry/result/Axis_1").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk2Dto2DAx1").getShape());
			assertArrayEquals(new int[]{op11.getModel().getyDim()}, dh.getLazyDataset("/entry/result/Junk2Dto2DAx2").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs2DTo1DNoAxesOperations() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,false);
		lazy.addMetadata(new AxesMetadataImpl(3));
		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setWithAxes(false);
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		op21.setWithAxes(false);
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op22,op21);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertFalse(dh.contains("/entry/result/Axis_0"));
			assertFalse(dh.contains("/entry/result/Junk1Dax"));
			
			assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void Process3DStackAs1DTo1DErrorAxes() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,true);
		
		AxesMetadata ax = lazy.getMetadata(AxesMetadata.class).get(0);
		IDataset ae1 = DatasetFactory.createRange(10, Dataset.INT16);
		ae1.setShape(new int[] {10,1,1});
		IDataset ae2 = DatasetFactory.createRange(30, Dataset.INT16);
		ae2.setShape(new int[] {1,30,1});
		
		((Dataset)ax.getAxis(0)[0]).setError(ae1);
		((Dataset)ax.getAxis(1)[0]).setError(ae2);

		
		RichDataset rich = new RichDataset(lazy, null);
		rich.setSlicing("all","all");
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setWithAxes(true);
		op11.setModel(new Junk1DModel());
		
		try {

			final File tmp = File.createTempFile("Test", ".h5");
			tmp.deleteOnExit();
			tmp.createNewFile();
			
			service.executeSeries(rich, new IMonitor.Stub(),new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()), op11);
			
			
			IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
			assertTrue(dh.contains("/entry/result/data"));
			assertTrue(dh.contains("/entry/result/errors"));
			assertTrue(dh.contains("/entry/result/Axis_0"));
			assertTrue(dh.contains("/entry/result/Axis_1"));
			assertTrue(dh.contains("/entry/result/Junk1Dax"));
			assertTrue(dh.contains("/entry/result/Axis_0_errors"));
			assertTrue(dh.contains("/entry/result/Axis_1_errors"));
			
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
			assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/errors").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0_errors").getShape());
			assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	public ILazyDataset getLazyDataset(int[] dsShape, boolean withAxes) {
		
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
		
		if (withAxes) {
			AxesMetadataImpl am = new AxesMetadataImpl(dsShape.length);
			
			for (int i = 0; i < dsShape.length; i++) {
				IDataset ax = DatasetFactory.createRange(0, dsShape[i], 1, Dataset.INT16);
				int[] shape = new int[dsShape.length];
				Arrays.fill(shape, 1);
				shape[i] = dsShape[i];
				ax.setShape(shape);
				ax.setName("Axis_" + String.valueOf(i));
				am.setAxis(i, ax);
			}
			
			lz.setMetadata(am);
		}
		
		
		lz.setName("mainlazy");
		
		return lz;
	}
	
	@Test
	public void AbstractOperationCounter() {
	
		int[] shape = new int[]{1,2,3,4,5,10,10};
		int[] dd = new int[]{2,3};
		final IDataset parent = Random.rand(shape);
		Slice[] init = new Slice[shape.length];
		PositionIterator pi = new PositionIterator(shape,dd);
		
		OriginMetadataImpl om = new OriginMetadataImpl(parent,init,dd,"test","test");
		
		int[] pos = pi.getPos();

		int count = 0;
		while (pi.hasNext()) {

			int[] end = pos.clone();
			for (int i = 0; i<pos.length;i++) {
				end[i]++;
			}

			for (int i = 0; i < dd.length; i++){
				end[dd[i]] = shape[dd[i]];
			}

			int[] st = pos.clone();
			for (int i = 0; i < st.length;i++) st[i] = 1;

			Slice[] slice = Slice.convertToSlice(pos, end, st);
			
			om.setCurrentSlice(slice);
			int currentSliceNumber = AbstractOperation.getCurrentSliceNumber(om);
			assertTrue(currentSliceNumber == count++);
			
		}
		
		
	}
	
}
