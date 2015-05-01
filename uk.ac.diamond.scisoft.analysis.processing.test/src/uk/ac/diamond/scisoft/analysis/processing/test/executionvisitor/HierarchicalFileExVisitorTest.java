package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.hdf5.operation.HierarchicalFileExecutionVisitor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.actor.runner.GraphRunner;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;

@RunWith(Parameterized.class)
public class HierarchicalFileExVisitorTest {
	
	private ExecutionType type;
	
	public HierarchicalFileExVisitorTest(ExecutionType type) {
		this.type = type;
	}
	
	private static IOperationService service;

	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.GRAPH,    new GraphRunner());
		
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		service = (IOperationService)Activator.getService(IOperationService.class);
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	@Parameterized.Parameters
	   public static Collection<?> params() {
	      return Arrays.asList(new Object[] {new Object[]
	         {ExecutionType.SERIES},new Object[]{ ExecutionType.GRAPH}
	         
	      });
	   }
	
	@Test
	public void Process3DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));

		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

		testDataset(op21, "9,:", dh.getLazyDataset("/entry/result/data"));
		testDataset(op21, "5,:", dh.getLazyDataset("/entry/result/data"));
		testDataset(op21, "0,:", dh.getLazyDataset("/entry/result/data"));

		
	}
	
	@Test
	public void Process4DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/entry/result/Axis_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

		testDataset( op21, "3,5,:",dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process5DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {2,5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all","all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);

					
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],inputShape[2],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/entry/result/Axis_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

		testDataset(op21, "1,3,5,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Axis_1"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());
		
		testDataset(op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DError() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
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

		testDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);

		
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

		testDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{2,3};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);			
		
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

		testDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux1D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{3};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
				
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
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

		testDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs2DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());

		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op21,op11);
		context.setExecutionType(type);
		service.execute(context);

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

		testDataset( op11, "5,:", dh.getLazyDataset("/entry/result/data"));
		
	}
	
	
	@Test
	public void Process3DStackAs2DTo1DNoAxes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
//			assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
//			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

		testDataset( op21, "5,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		Junk1Dto2DOperation op11 = new Junk1Dto2DOperation();
		op11.setModel(new Junk2Dto2Dmodel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
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

		testDataset( op11, "5,10,:,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs2DTo2Dwith2Axes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,2);
		final IOperationContext context = service.createContext();
		context.setSlicing("all");
		context.setData(lazy);
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		op22.setNumberOfAxes(2);
		context.setSeries(op22);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Axis_0_1"));
		assertTrue(dh.contains("/entry/result/Junk2Dto2DAx2"));
		assertTrue(dh.contains("/entry/result/Junk2Dto2DAx2_1"));
		
		assertArrayEquals(new int[]{inputShape[0],op22.getModel().getxDim(),op22.getModel().getyDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0_1").getShape());
		assertArrayEquals(new int[]{op22.getModel().getyDim()}, dh.getLazyDataset("/entry/result/Junk2Dto2DAx2").getShape());
		
		IDataset a1 = dh.getLazyDataset("/entry/result/Axis_0").getSlice();
		IDataset a2 = dh.getLazyDataset("/entry/result/Axis_0_1").getSlice();
		
		for (int i = 0; i < 10;i++) {
			if (a2.getInt(i) != i+1) {
				fail("axes2 not equal");
				break;
			}
		}
		for (int i = 0; i < 10;i++) {
			if (a1.getInt(i) != i) {
				fail("axes1 not equal");
				break;
			}
		}
		
		testDataset( op22, "5,:,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	
	@Test
	public void Process3DStackAs2DTo1Dwith2Axes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,2);
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		context.setSeries(op21);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/result/Axis_0"));
		assertTrue(dh.contains("/entry/result/Axis_0_1"));
		assertTrue(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/entry/result/Axis_0_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/Junk1Dax").getShape());

		testDataset( op21, "5,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs2DTo1DNoAxesOperations() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		lazy.addMetadata(new AxesMetadataImpl(3));
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setWithAxes(false);
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		op21.setWithAxes(false);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains("/entry/result/data"));
		assertFalse(dh.contains("/entry/result/Axis_0"));
		assertFalse(dh.contains("/entry/result/Junk1Dax"));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset("/entry/result/data").getShape());
		
		testDataset( op21, "5,:", dh.getLazyDataset("/entry/result/data"));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DErrorAxes() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		AxesMetadata ax = lazy.getMetadata(AxesMetadata.class).get(0);
		IDataset ae1 = DatasetFactory.createRange(10, Dataset.INT16);
		ae1.setShape(new int[] {10,1,1});
		IDataset ae2 = DatasetFactory.createRange(30, Dataset.INT16);
		ae2.setShape(new int[] {1,30,1});
		
		((Dataset)ax.getAxis(0)[0]).setError(ae1);
		((Dataset)ax.getAxis(1)[0]).setError(ae2);

		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all","all");
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setWithAxes(true);
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		
		service.execute(context);
		
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
		
		testDataset( op11, "0,1,:", dh.getLazyDataset("/entry/result/data"));
//		testDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/data"));
//		testAxesDataset( op11, "5,10,:", dh.getLazyDataset("/entry/result/Junk1Dax"),0);
		
		compareDatasets(dh.getLazyDataset("/entry/result/Axis_0_errors").getSlice(), ae1);
		
	}
	
	@Test
	public void Process3DStackAs2DTo1DToNull() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		lazy.addMetadata(new AxesMetadataImpl(3));
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setSlicing("all");
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setWithAxes(true);
		op22.setModel(new Junk2Dto2Dmodel());
		op22.setStoreOutput(true);
		Junk2Dto1DNullOperation op21 = new Junk2Dto1DNullOperation();
		op21.setModel(new Junk1DModel());
		op21.setWithAxes(false);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new HierarchicalFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertFalse(dh.contains("/entry/result/data"));
		assertTrue(dh.contains("/entry/intermediate/0-Junk2Dto2DOperation/Junk2Dto2DAx1"));
		assertFalse(dh.contains("/entry/intermediate/0-Junk2Dto2DOperation/Junk2Dto2DAx11"));
		
			
	}
	
	private void testDataset(ITestOperation op, String slice, ILazyDataset lz) {
		Slice[] slices = Slice.convertFromString(slice);
		IDataset data = op.getTestData().getData();
		IDataset out = lz.getSlice(slices);
		out = out.squeeze();
		compareDatasets(out, data);
		
	}
	
	private void compareDatasets(IDataset a, IDataset b) {
		double sum = (Double)((Dataset)a).sum();
		assertFalse(sum == 0);
		double sum1 = (Double)((Dataset)b).sum();
		assertTrue(sum1 == sum);
	}
	
	private void testAxesDataset(ITestOperation op, String slice, ILazyDataset lz, int dim) throws Exception {
		IDataset data = op.getTestData().getData().getMetadata(AxesMetadata.class).get(0).getAxes()[dim].getSlice();
		IDataset out = lz.getSlice();
		out = out.squeeze();
		compareDatasets(out, data);
	}
	
	public static ILazyDataset getLazyDataset(int[] dsShape, int withAxes) {
		
		ILazyDataset lz = Random.lazyRand("test", dsShape);
		
		if (withAxes > 0) {
			AxesMetadataImpl am = new AxesMetadataImpl(dsShape.length);
			
			for (int j = 0; j < withAxes; j++) {
				for (int i = 0; i < dsShape.length; i++) {
					Dataset ax = DatasetFactory.createRange(0, dsShape[i], 1, Dataset.INT16);
					ax.iadd(j);
					int[] shape = new int[dsShape.length];
					Arrays.fill(shape, 1);
					shape[i] = dsShape[i];
					ax.setShape(shape);
					if (j == 0) ax.setName("Axis_" + String.valueOf(i));
					else ax.setName("Axis_" + String.valueOf(i)+"_"+String.valueOf(j));
					am.addAxis(i, ax);
				}
			}
			
			
			
			lz.setMetadata(am);
		}
		
		
		lz.setName("mainlazy");
		
		return lz;
	}
}
