package uk.ac.diamond.scisoft.xpdf.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.dawnsci.persistence.internal.PersistJsonOperationsNode;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentFile;
import org.eclipse.dawnsci.analysis.api.processing.ExecutionType;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.ILazyDataset;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.OperationRunnerImpl;
import uk.ac.diamond.scisoft.analysis.processing.runner.SeriesRunner;
import uk.ac.diamond.scisoft.analysis.processing.visitor.NexusFileExecutionVisitor;

public class XPDFChainTest {

	private static OperationServiceImpl operationService = new OperationServiceImpl();
	
	@BeforeClass
	public static void before() throws Exception {
		
		OperationRunnerImpl.setRunner(ExecutionType.SERIES,   new SeriesRunner());
		operationService.createOperations(operationService.getClass().getClassLoader(), "uk.ac.diamond.scisoft.xpdf.operations");
		operationService.createOperations(operationService.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.operations");
		PersistJsonOperationsNode.setOperationService(operationService);
		LocalServiceManager.setLoaderService(new LoaderServiceImpl());
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		org.dawnsci.persistence.ServiceLoader.setNexusFactory(new NexusFileFactoryHDF5());
	}
	
	
	@Ignore
	@Test
	public void test() throws Exception{

		
		
		final IOperationContext context = operationService.createContext();
		ILazyDataset lazy = LoaderFactory.getData("/dls/science/groups/das/ExampleData/i15-1/NeXus/Ceria_Complete.nxs").getLazyDataset("/entry1/data/data");
		context.setData(lazy);
//		context.setSlicing("all");
		context.setDataDimensions(new int[]{2,3});

		IPersistenceService ps = PersistenceServiceCreator.createPersistenceService();
		
		
		
		IPersistentFile pf = ps.getPersistentFile("/dls/science/groups/das/ExampleData/i15-1/pipelines/ceria.2D_data_2D_processing_pipeline.nxs");
		IOperation<? extends IOperationModel, ? extends OperationData>[] ops = pf.getOperations();
		
		
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
