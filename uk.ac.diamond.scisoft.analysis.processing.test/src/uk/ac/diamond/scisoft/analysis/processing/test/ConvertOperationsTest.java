package uk.ac.diamond.scisoft.analysis.processing.test;

import java.io.File;

import org.dawb.common.services.ServiceManager;
import org.dawb.common.services.conversion.IConversionContext;
import org.dawb.common.services.conversion.IProcessingConversionInfo;
import org.dawb.common.services.conversion.IConversionContext.ConversionScheme;
import org.dawb.common.services.conversion.IConversionService;
import org.dawnsci.conversion.ConversionServiceImpl;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.OperationServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.PowderIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.visitors.HierarchicalFileExecutionVisitor;

public class ConvertOperationsTest {

	static IConversionService service;
	
	@BeforeClass
	public static void before() throws Exception {
		
		ServiceManager.setService(IOperationService.class, new OperationServiceImpl());
		ServiceManager.setService(IConversionService.class, new ConversionServiceImpl());
		
		service = (IConversionService)ServiceManager.getService(IConversionService.class);
		
	}
	
	@Test
	public void testConvertProcess() throws Exception {
		//TODO uncomment when added test data
//		if (service == null) throw new Exception("Cannot get the service!");
//		
//		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(1000,1000);
//		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1);
//		
//		//Import metadata
//		final IOperation di = new DiffractionMetadataImportOperation();
//		DiffractionMetadataImportModel model = new DiffractionMetadataImportModel();
//		model.setFilePath("/dls/science/groups/das/ExampleData/powder/i18/dataCollectionCal.nxs");
//		di.setModel(model);
//		
//		//pixel integration
//		final IOperation azi = new PixelIntegrationOperation();
//		azi.setModel(new PowderIntegrationModel());
//		
//		final IOperation[] ops = new IOperation[]{di,azi};
//		
//		IConversionContext open = service.open("/dls/science/groups/das/presentation_data/i18/dataCollection.dat");
//		
//		open.setConversionScheme(ConversionScheme.PROCESS);
//		
//		open.setDatasetName("Pilatus");
//		open.addSliceDimension(0, "all");
//		open.setOutputPath(System.getProperty("java.io.tmpdir"));
//		open.setUserObject(new IProcessingConversionInfo() {
//			
//			@Override
//			public IOperation[] getOperationSeries() {
//				return ops;
//			}
//
//			@Override
//			public IExecutionVisitor getExecutionVisitor(String fileName) {
//				return new HierarchicalFileExecutionVisitor(fileName);
//			}
//
//		});
//		
//		service.process(open);
	}
	
}
