package uk.ac.diamond.scisoft.xpdf.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dawnsci.persistence.PersistenceServiceCreator;
import org.dawnsci.persistence.internal.PersistJsonOperationsNode;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.ILiveOperationInfo;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class XPDFOldPipelineTest {

	private static OperationServiceImpl operationService = new OperationServiceImpl();
	private static IOperationContext context;
	private static IOperation<? extends IOperationModel, ? extends OperationData>[] ops;
	private static File tmp;
	
	private static final String pipeline = "/dls/science/groups/das/ExampleData/i15-1/pipelines/ceria.2D_data_2D_processing_pipeline.nxs";
	private static enum TestingOperations {INSERT_SAMPLE, INSERT_CONTAINER, INSERT_BEAM, DEFINE_DETECTOR, NORMALISE_DATA, SUBTRACT_BACKGROUND, XICCO, SELF_SCATTER, TOPHAT, LORCH};
	private static Map<TestingOperations, Integer> opIndices;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		operationService.createOperations(operationService.getClass(), "uk.ac.diamond.scisoft.xpdf.operations");
		operationService.createOperations(operationService.getClass(), "uk.ac.diamond.scisoft.analysis.processing.operations");
		new PersistJsonOperationsNode().setOperationService(operationService);
		new LocalServiceManager().setLoaderService(new LoaderServiceImpl());
		ServiceProvider.setService(INexusFileFactory.class, new NexusFileFactoryHDF5());
	
		// Set up the pipeline and its context
		context = operationService.createContext();
		ILazyDataset lazy = LoaderFactory.getData("/dls/science/groups/das/ExampleData/i15-1/NeXus/Ceria_Complete.nxs").getLazyDataset("/entry1/data/data");
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{2,3});
	
		IPersistenceService ps = PersistenceServiceCreator.createPersistenceService();
		IPersistentFile pf = ps.getPersistentFile(pipeline);
		ops = pf.getOperations();

		// temporary output file
		tmp = File.createTempFile("Test", ".nxs");
		tmp.deleteOnExit();
		tmp.createNewFile();

		opIndices = new HashMap<>();
		opIndices.put(TestingOperations.INSERT_SAMPLE, 3);
		opIndices.put(TestingOperations.INSERT_CONTAINER, 4);
		opIndices.put(TestingOperations.INSERT_BEAM, 5);
		opIndices.put(TestingOperations.DEFINE_DETECTOR, 6);
		opIndices.put(TestingOperations.NORMALISE_DATA, 7);
		opIndices.put(TestingOperations.SUBTRACT_BACKGROUND, 8);
		opIndices.put(TestingOperations.XICCO, 9);
		opIndices.put(TestingOperations.SELF_SCATTER, 10);
		opIndices.put(TestingOperations.TOPHAT, 12);
		opIndices.put(TestingOperations.LORCH, 13);
		
	}
	
	@AfterClass
	public static void tearDownClass() {
		ServiceProvider.reset();
	}
	
	
	@Test
	@Ignore("Test development in progress")
	public void testSampleMetadata() {
		
		boolean beforeInsert = true;
		Dataset startData = null;
		try {
			startData = DatasetUtils.sliceAndConvertLazyDataset(context.getData());
		} catch (Exception e) {
			fail("Exception on reading and slicing data: " + e);
		}
		
		beforeInsert = XPDFOperationChecker.hasMetadata(startData) && XPDFOperationChecker.hasSampleMetadata(startData);
		assertFalse("Sample Metadata already present.", beforeInsert);
		
		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(Arrays.copyOfRange(ops, 0, opIndices.get(TestingOperations.INSERT_SAMPLE) + 1));
		context.setExecutionType(ExecutionType.SERIES);
		long t = System.currentTimeMillis();
		operationService.execute(context);
		System.out.println("Time: " + ((System.currentTimeMillis()-t)/1000));
		
		
		// get the resultant data
		ILiveOperationInfo liveOpInfo = context.getLiveInfo();
		System.err.println(liveOpInfo.getKeys());
		
		
		Dataset endData = null;
		try {
			ILazyDataset r = LoaderFactory.getData(tmp.getAbsolutePath()).getLazyDataset("/entry/result/data");
			endData = DatasetUtils.convertToDataset(r.getSlice());
		} catch (Exception e) {
			fail("Exception on reading and slicing data");
		}
		boolean hasMetadata = XPDFOperationChecker.hasMetadata(endData);
		assertTrue("XPDF metadata not found.", hasMetadata);
		boolean hasSampleMetadata = XPDFOperationChecker.hasSampleMetadata(endData);
		assertTrue("Sample Metadata not present.", hasSampleMetadata);
		
	}
	
	
	@Test
	@Ignore
	public void test() throws Exception{

		final File tmp = File.createTempFile("Test", ".h5");
		tmp.deleteOnExit();
		tmp.createNewFile();

		context.setVisitor(new NexusFileExecutionVisitor(tmp.getAbsolutePath()));
		context.setSeries(ops);
		context.setExecutionType(ExecutionType.SERIES);
		long t = System.currentTimeMillis();
		operationService.execute(context);
		System.out.println("Time: " + ((System.currentTimeMillis()-t)/1000));
		
		ILazyDataset r = LoaderFactory.getData(tmp.getAbsolutePath()).getLazyDataset("/entry/result/data");
		Dataset d = DatasetUtils.convertToDataset(r.getSlice());
		
		double max = d.max().doubleValue();
		double min = d.min().doubleValue();
		
		assertEquals(35.28712797375911, max,0.00000001);
		assertEquals(-14.501890542153754, min,0.00000001);
		
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		System.out.println(totalMemory/Math.pow(10, 9));
	}

}
