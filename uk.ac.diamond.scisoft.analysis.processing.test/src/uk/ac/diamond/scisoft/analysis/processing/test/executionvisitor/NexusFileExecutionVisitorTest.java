package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.IOperationService;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.test.OperationsTestConstants;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;


@RunWith(Parameterized.class)
public class NexusFileExecutionVisitorTest {
	
	private ExecutionType type;
	
	public NexusFileExecutionVisitorTest(ExecutionType type) {
		this.type = type;
	}
	
	private static IOperationService service;
	
	private static final String PROCESS_PATH = OperationsTestConstants.PROCESSED_RESULTS_PATH;
	private static final String PROCESS_DATA_PATH = OperationsTestConstants.PROCESSED_RESULTS_DATA_PATH;
	private static final String AXIS0 = OperationsTestConstants.AXIS0;
	private static final String JUNK_AXIS1D = OperationsTestConstants.JUNK_AXIS1D;

	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		OperationRunnerImpl.setRunner(ExecutionType.PARALLEL, new SeriesRunner());
		
		service = new OperationServiceImpl();
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor");
	}
	
	@Parameterized.Parameters
	public static Collection<?> params() {
		return Arrays
				.asList(new Object[] { new Object[] { ExecutionType.SERIES }, new Object[] { ExecutionType.PARALLEL }
				});
	}
	
	@Test
	public void Process3DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));

		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset(op21, "9,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		testDataset(op21, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		testDataset(op21, "0,:", dh.getLazyDataset(PROCESS_DATA_PATH));

		
	}
	
	@Test
	public void Process3DStackAsAnyTo1D() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		JunkAnyto1DOperation op = new JunkAnyto1DOperation();
		op.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));

		assertArrayEquals(new int[]{inputShape[0],op.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset(op, "9,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		testDataset(op, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		testDataset(op, "0,:", dh.getLazyDataset(PROCESS_DATA_PATH));

		
	}
	
	@Test
	public void Process4DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2,3});
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset(PROCESS_PATH + "Axis_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset( op21, "3,5,:",dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}

	@Test
	public void Process4DStackAsAnyTo1D() throws Exception {
		
		int[] inputShape = new int[] {5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2,3});
		
		JunkAnyto1DOperation op = new JunkAnyto1DOperation();
		op.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset(PROCESS_PATH + "Axis_1").getShape());
		assertArrayEquals(new int[]{op.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset(op, "3,5,:",dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}
	
	@Test
	public void Process5DStackAs2DTo1D() throws Exception {
		
		int[] inputShape = new int[] {2,5,10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all","all");
		context.setDataDimensions(new int[]{3,4});
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);

					
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],inputShape[2],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset(PROCESS_PATH + "Axis_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset(op21, "1,3,5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}

	@Test
	public void process3DStackAs1DTo1D() throws Exception {
		process3DStackAs1DTo1D(false);
	}

	@Test
	public void process3DStackAs1DTo1DSWMR() throws Exception {
		process3DStackAs1DTo1D(true);
	}

	private void process3DStackAs1DTo1D(boolean swmr) throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath(), swmr));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		
		testDataset(op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DError() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + "errors"));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + "errors").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);

		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		assertArrayEquals(new int[]{inputShape[0],inputShape[1]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1").getShape());

		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		
		IDataset slice = dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getSlice();
		slice.toString();
			
	}
	
	@Test
	public void Process3DStackAs1DTo1DAux2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{2,3};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);			
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],auxShape[0],auxShape[1]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());

		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}

	@Test
	public void process3DStackAs1DTo1DAux1D() throws Exception {
		process3DStackAs1DTo1DAux1D(false);
	}

	@Test
	public void process3DStackAs1DTo1DAux1DSWMR() throws Exception {
		process3DStackAs1DTo1DAux1D(true);
	}

	private void process3DStackAs1DTo1DAux1D(boolean swmr) throws Exception {
		int[] inputShape = new int[] {10,30,1100};
		int[] auxShape = new int[]{3};

		ILazyDataset lazy = getLazyDataset(inputShape,1);

		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{2});

		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);

		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath(), swmr));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_1"));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],auxShape[0]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());

		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}

	@Test
	public void process3DStackAs1DTo1DSum1D() throws Exception {
		process3DStackAs1DTo1DSum1D(false);
	}

	@Test
	public void process3DStackAs1DTo1DSum1DSWMR() throws Exception {
		process3DStackAs1DTo1DSum1D(true);
	}

	private void process3DStackAs1DTo1DSum1D(boolean swmr) throws Exception {
		int[] inputShape = new int[] {10,30,1100};
		int[] sumShape = new int[]{3};

		ILazyDataset lazy = getLazyDataset(inputShape,1);

		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{2});

		Junk1Dto1DSumOperation op11 = new Junk1Dto1DSumOperation();
		op11.setModel(new Junk1DModel());
		op11.setSumShape(sumShape);

		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath(), swmr));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/summary/0-Junk1Dto1DSumOperation/singlevalue/data"));

		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		IDataset ld = dh.getLazyDataset("/processed/summary/0-Junk1Dto1DSumOperation/singlevalue/data").getSlice();
		assertArrayEquals(sumShape, ld.getShape());
		int end = inputShape[0]*inputShape[1] - 1;
		if (type == ExecutionType.PARALLEL) {
			// as last set of slices may not have executed in order
			end = Math.max(1, end - Runtime.getRuntime().availableProcessors());
		}
		assertTrue(ld.getInt(0) >= end);

		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}

	@Test
	public void Process3DStackAs2DTo1DAux() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());

		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op21,op11);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/Axis_0"));
		assertTrue(dh.contains("/processed/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/data"));

		assertArrayEquals(new int[]{inputShape[0],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/processed/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/Axis_0").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset("/processed/auxiliary/1-Junk1Dto1DAuxOperation/singlevalue/data").getShape());

		testDataset( op11, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
		
	}
	
	
	@Test
	public void Process3DStackAs2DTo1DNoAxes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
//			assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
//			assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset( op21, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
			
	}
	
	@Test
	public void Process3DStackAs1DTo2D() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		Junk1Dto2DOperation op11 = new Junk1Dto2DOperation();
		op11.setModel(new Junk2Dto2Dmodel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx1"));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx2"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim(),op11.getModel().getyDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[1]}, dh.getLazyDataset(PROCESS_PATH + "Axis_1").getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + "Junk2Dto2DAx1").getShape());
		assertArrayEquals(new int[]{op11.getModel().getyDim()}, dh.getLazyDataset(PROCESS_PATH + "Junk2Dto2DAx2").getShape());

		testDataset( op11, "5,10,:,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}

	@Test
	public void Process3DStackAs2DTo2Dwith2Axes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,2);
		final IOperationContext context = service.createContext();
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		context.setData(lazy);
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		op22.setNumberOfAxes(2);
		context.setSeries(op22);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_0_1"));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx2"));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx2_1"));
		
		assertArrayEquals(new int[]{inputShape[0],op22.getModel().getxDim(),op22.getModel().getyDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + "Axis_0_1").getShape());
		assertArrayEquals(new int[]{op22.getModel().getyDim()}, dh.getLazyDataset(PROCESS_PATH + "Junk2Dto2DAx2").getShape());
		
		IDataset a1 = dh.getLazyDataset(PROCESS_PATH + AXIS0).getSlice();
		IDataset a2 = dh.getLazyDataset(PROCESS_PATH + "Axis_0_1").getSlice();
		
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
		
		testDataset( op22, "5,:,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}

	@Test
	public void Process3DStackAs2DTo1Dwith2Axes() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,2);
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		context.setSeries(op21);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_0_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + "Axis_0_1").getShape());
		assertArrayEquals(new int[]{op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		testDataset( op21, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}
	
	@Test
	public void Process3DStackAs2DTo1DNoAxesOperations() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		lazy.addMetadata(MetadataFactory.createMetadata(AxesMetadata.class, 3));
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setWithAxes(false);
		op22.setModel(new Junk2Dto2Dmodel());
		Junk2Dto1DOperation op21 = new Junk2Dto1DOperation();
		op21.setModel(new Junk1DModel());
		op21.setWithAxes(false);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertFalse(dh.contains(PROCESS_PATH + AXIS0));
		assertFalse(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		
		assertArrayEquals(new int[]{inputShape[0],op21.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		
		testDataset( op21, "5,:", dh.getLazyDataset(PROCESS_DATA_PATH));
	}
	
	@Test
	public void Process3DStackAs1DTo1DErrorAxes() throws Exception {
		
		int[] inputShape = new int[] {10,30,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		AxesMetadata ax = lazy.getMetadata(AxesMetadata.class).get(0);
		IDataset ae1 = DatasetFactory.createRange(ShortDataset.class, 10);
		ae1.setShape(new int[] {10,1,1});
		IDataset ae2 = DatasetFactory.createRange(ShortDataset.class, 30);
		ae2.setShape(new int[] {1,30,1});
		
		ax.getAxis(0)[0].setErrors(ae1);
		ax.getAxis(1)[0].setErrors(ae2);

		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all","all");
		context.setDataDimensions(new int[]{2});
		
		Junk1Dto1DOperation op11 = new Junk1Dto1DOperation();
		op11.setWithErrors(true);
		op11.setWithAxes(true);
		op11.setModel(new Junk1DModel());
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + "errors"));
		assertTrue(dh.contains(PROCESS_PATH + AXIS0));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1"));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_0_errors"));
		assertTrue(dh.contains(PROCESS_PATH + "Axis_1_errors"));
		
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[0],inputShape[1],op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + "errors").getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + AXIS0).getShape());
		assertArrayEquals(new int[]{inputShape[0]}, dh.getLazyDataset(PROCESS_PATH + "Axis_0_errors").getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());
		
		testDataset( op11, "0,1,:", dh.getLazyDataset(PROCESS_DATA_PATH));
//		testDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_DATA_PATH));
//		testAxesDataset( op11, "5,10,:", dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D),0);
		
		compareDatasets(dh.getLazyDataset(PROCESS_PATH + "Axis_0_errors").getSlice(), ae1);
		
	}
	
	@Test
	public void Process3DStackAs2DTo1DToNull() throws Exception {
		
		int[] inputShape = new int[] {10,1000,1100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,0);
		lazy.addMetadata(MetadataFactory.createMetadata(AxesMetadata.class, 3));
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{1,2});
		
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
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op22,op21);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertFalse(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains("/processed/intermediate/0-Junk2Dto2DOperation/Junk2Dto2DAx1"));
		assertFalse(dh.contains("/processed/intermediate/0-Junk2Dto2DOperation/Junk2Dto2DAx11"));
	}

	@Test
	public void Process1DLineAs1DTo1DAux1D() throws Exception {
		
		int[] inputShape = new int[] {1100};
		int[] auxShape = new int[]{1};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{0});

		Junk1Dto1DAuxOperation op11 = new Junk1Dto1DAuxOperation();
		op11.setModel(new Junk1DModel());
		op11.setAuxShape(auxShape);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);
		
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data"));
		
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		IDataset slice = dh.getLazyDataset("/processed/auxiliary/0-Junk1Dto1DAuxOperation/singlevalue/data").getSlice();
		double d = slice.getDouble(0);
		assertTrue(d != 0);
		assertFalse(Double.isNaN(d));
	}

	@Test
	public void Process1DLineAs1DTo1DSum1D() throws Exception {
		
		int[] inputShape = new int[] {1100};
		int[] sumShape = new int[]{1};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		
		final IOperationContext context = service.createContext();
		context.setData(lazy);
		context.setDataDimensions(new int[]{0});

		Junk1Dto1DSumOperation op11 = new Junk1Dto1DSumOperation();
		op11.setModel(new Junk1DModel());
		op11.setSumShape(sumShape);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(op11);
		context.setExecutionType(type);
		service.execute(context);

		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + JUNK_AXIS1D));
		assertTrue(dh.contains("/processed/summary/0-Junk1Dto1DSumOperation/singlevalue/data"));

		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{op11.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + JUNK_AXIS1D).getShape());

		IDataset slice = dh.getLazyDataset("/processed/summary/0-Junk1Dto1DSumOperation/singlevalue/data").getSlice();
		assertEquals(0, slice.getInt(0));
	}

	@Test
	public void Process3DStackAs2DTo2DSlowAxis() throws Exception {
		
		int[] inputShape = new int[] {64,64,100};
		
		ILazyDataset lazy = getLazyDataset(inputShape,1);
		final IOperationContext context = service.createContext();
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{0,1});
		context.setData(lazy);
		
		Junk2Dto2DOperation op22 = new Junk2Dto2DOperation();
		op22.setModel(new Junk2Dto2Dmodel());
		context.setSeries(op22);
		
		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setExecutionType(type);
		service.execute(context);
		
		IDataHolder dh = LoaderFactory.getData(tmp.getAbsolutePath());
		assertTrue(dh.contains(PROCESS_DATA_PATH));
		assertTrue(dh.contains(PROCESS_PATH + OperationsTestConstants.AXIS2));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx2"));
		assertTrue(dh.contains(PROCESS_PATH + "Junk2Dto2DAx1"));
		
		assertArrayEquals(new int[]{op22.getModel().getxDim(),op22.getModel().getyDim(),inputShape[2]}, dh.getLazyDataset(PROCESS_DATA_PATH).getShape());
		assertArrayEquals(new int[]{inputShape[2]}, dh.getLazyDataset(PROCESS_PATH + OperationsTestConstants.AXIS2).getShape());
		assertArrayEquals(new int[]{op22.getModel().getxDim()}, dh.getLazyDataset(PROCESS_PATH + "Junk2Dto2DAx1").getShape());
		assertArrayEquals(new int[]{op22.getModel().getyDim()}, dh.getLazyDataset(PROCESS_PATH + "Junk2Dto2DAx2").getShape());

	}
	
	private void testDataset(ITestOperation op, String slice, ILazyDataset lz) throws DatasetException {
		Slice[] slices = Slice.convertFromString(slice);
		IDataset data = op.getTestData().getData();
		IDataset out = lz.getSlice(slices);
		out = out.squeeze();
		compareDatasets(out, data);
		
	}
	
	private void compareDatasets(IDataset a, IDataset b) {
		double sum = ((Number) DatasetUtils.convertToDataset(a).sum()).doubleValue();
		assertFalse(sum == 0);
		double sum1 = ((Number) DatasetUtils.convertToDataset(b).sum()).doubleValue();
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
			AxesMetadata am  = null;
			try {
				am = MetadataFactory.createMetadata(AxesMetadata.class, dsShape.length);

				for (int j = 0; j < withAxes; j++) {
					for (int i = 0; i < dsShape.length; i++) {
						Dataset ax = DatasetFactory.createRange(ShortDataset.class, 0, dsShape[i], 1);
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
			} catch (MetadataException e) {
				// do nothing
			}

			lz.setMetadata(am);
		}
		
		
		lz.setName("mainlazy");
		
		return lz;
	}
}
