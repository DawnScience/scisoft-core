package uk.ac.diamond.scisoft.analysis.processing.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.dataset.Random;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
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
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class PixelIntegrationTest {

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
		final IRichDataset   rand = new RichDataset(Random.rand(0.0, 1000.0, 24, 1000, 1000), null, null, null, null);
		rand.setSlicing("all"); // All 24 images in first dimension.
		
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
				
			}
		}, di,azi);
		
	}
	
	
}
