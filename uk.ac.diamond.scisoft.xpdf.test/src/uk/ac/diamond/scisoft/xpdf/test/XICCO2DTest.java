package uk.ac.diamond.scisoft.xpdf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.dawb.common.services.ServiceManager;
import org.dawnsci.persistence.PersistenceServiceCreator;
import org.dawnsci.persistence.ServiceLoader;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.LoaderServiceImpl;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ImportMaskModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.mask.ImportMaskOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DiffractionMetadataImportModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DiffractionMetadataImportOperation;
import uk.ac.diamond.scisoft.xpdf.XPDFGeometryEnum;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFDefineDetectorModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFDefineDetectorOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertBeamMetadataModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertBeamMetadataOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertContainerMetadataModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertContainerMetadataOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertSampleMetadataModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFInsertSampleMetadataOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFNormalizeTracesModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFNormalizeTracesOperation;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFSubtractBackgroundModel;
import uk.ac.diamond.scisoft.xpdf.operations.XPDFSubtractBackgroundOperation;

public class XICCO2DTest {

	@Before
	public void setUp() throws Exception {
		// Set factory for test
		ServiceLoader.setNexusFactory(new NexusFileFactoryHDF5());
		// Set up a File Service for ImportMaskMetadata
		ServiceManager.setService(IPersistenceService.class, PersistenceServiceCreator.createPersistenceService());
		LocalServiceManager.setLoaderService(new LoaderServiceImpl());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcess() {
		String ceriaDataPath = "/dls/science/groups/das/ExampleData/i15-1/2015Oct.Standards/CeO2_NIST_8s_19slices_averaged.tif";
		// Load the data
		IDataset ceriaData = null;
		try {
			IDataHolder dh = LoaderFactory.getData(ceriaDataPath);
			ceriaData = dh.getDataset(0);
		} catch (Exception e) {
			fail("Could not load data from " + ceriaDataPath);
		}
		
		// Create the useless monitor for the pretend pipeline
		IMonitor mon = new IMonitor.Stub();
		
		// Create the Source and Slice metadata
		SliceFromSeriesMetadata sourceSlice = new SliceFromSeriesMetadata(new SourceInformation(ceriaDataPath, "ceriaData", ceriaData),
				new SliceInformation(new SliceND(new int[]{2048,  2048}),
						new SliceND(new int[]{2048,  2048}),
						new SliceND(new int[]{2048,  2048}),
						new int[] {0, 1},  1, 0));
		
		ceriaData.addMetadata(sourceSlice);
		
		// Add the mask
		ImportMaskOperation<FixedMaskModel> maskOp = new ImportMaskOperation<FixedMaskModel>();
		maskOp.setModel(new FixedMaskModel());
		IDataset maskedData = maskOp.execute(ceriaData, mon).getData();
		
		// Import the detector calibration
		DiffractionMetadataImportOperation dMIO = new DiffractionMetadataImportOperation();
		dMIO.setModel(new FixedDiffractionMetadataImportModel());
		maskedData = dMIO.execute(maskedData, mon).getData();
		
		// XPDF Metadata
		XPDFInsertSampleMetadataOperation xISMO = new XPDFInsertSampleMetadataOperation();
		xISMO.setModel(new CeriaSampleMetadataModel());
		IDataset dataWithMetadata = xISMO.execute(maskedData, mon).getData();
		XPDFInsertContainerMetadataOperation xICMO = new XPDFInsertContainerMetadataOperation();
		xICMO.setModel(new OneMMContainerMetadataModel());
		dataWithMetadata = xICMO.execute(dataWithMetadata, mon).getData();
		XPDFInsertBeamMetadataOperation xIBMO = new XPDFInsertBeamMetadataOperation();
		xIBMO.setModel(new I15BeamMetadataModel());
		dataWithMetadata = xIBMO.execute(dataWithMetadata, mon).getData();
		XPDFDefineDetectorOperation xDDO = new XPDFDefineDetectorOperation();
		xDDO.setModel(new DefineCsIDetector());
		dataWithMetadata = xDDO.execute(dataWithMetadata, mon).getData();
		
		// XPDF processing
		XPDFNormalizeTracesOperation xNTO = new XPDFNormalizeTracesOperation();
		xNTO.setModel(new YesNormalizeTracesModel());
		IDataset normedData = xNTO.execute(dataWithMetadata, mon).getData();
		XPDFSubtractBackgroundOperation xSBO = new XPDFSubtractBackgroundOperation();
		xSBO.setModel(new YesSubtractBackgroundModel());
		IDataset subBak = xSBO.execute(normedData, mon).getData();
		
		// Iterate the calibration constant
		long startTime = System.currentTimeMillis();
		XPDFIterateCalibrationConstantOperation xICCO = new XPDFIterateCalibrationConstantOperation();
		xICCO.setModel(new FakeXICCOModel());
		IDataset abscor = xICCO.execute(subBak, mon).getData();
		long finishTime = System.currentTimeMillis();
		long diffTime = finishTime - startTime;
		System.out.println("XPDFIterateCalcibrationConstantOperation took " + diffTime*0.001 + " s.");
		
//		String referenceDataPath = "/dls/science/groups/das/ExampleData/i15-1/2015Oct.Standards/optimization reference/CeO2_NIST_8s_19slices_averaged_processed_160104_154754.nxs"; 
		String referenceDataPath = "/dls/science/groups/das/ExampleData/i15-1/2015Oct.Standards/optimization reference/CeO2_NIST_8s_19slices_averaged_processed_160204_122450.nxs";
		IDataset referenceData = null;
		try {
			IDataHolder dh = LoaderFactory.getData(referenceDataPath);
			referenceData = dh.getLazyDataset("/entry/result/data").getSlice();
			
//			IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
//			IPersistentFile pf = service.getPersistentFile(referenceDataPath);
//			List<String> dataNames = pf.getDataNames(mon);
//			List<String> maskNames = pf.getMaskNames(mon);
//			referenceData = pf.getData("/result/data", mon).getSlice();
//			referenceData = pf.getData(pf.getDataNames(mon), mon);
		} catch (Exception e) {
			fail("Could not load data from " + referenceDataPath + ", " + e.toString());
		}
		
		Dataset ratio = Maths.divide(abscor, referenceData);
		double meanRatio = (double) Maths.abs(ratio).mean();
		double meanRationalDifference = meanRatio - 1;
		double rationalDifferenceLimit = 13e-2;
		
		assertTrue("ABSCOR rational difference too large.", meanRationalDifference < rationalDifferenceLimit);

//		assertTrue("A dummy assertion shouldn't return false.", true);
		
	}

	@Test
	public void testGetId() {
		assertEquals("XICCO has the incorrect ID String.", "uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation", new XPDFIterateCalibrationConstantOperation().getId());
	}

	@Test
	public void testGetInputRank() {
		assertEquals("XICCO input can be of ANY rank.", OperationRank.ANY, new XPDFIterateCalibrationConstantOperation().getInputRank());
	}

	@Test
	public void testGetOutputRank() {
		assertEquals("XICCO output should have the SAME rank as the input.", OperationRank.SAME, new XPDFIterateCalibrationConstantOperation().getOutputRank());
	}

	XPDFMetadata createCeriaMetadata() {
		XPDFMetadata theMetadata = new XPDFMetadataImpl();
		
		
		
		
		return theMetadata;
	}	
	
}


class FixedMaskModel extends ImportMaskModel {
	@Override
	public String getFilePath() {
		return "/dls/science/groups/das/ExampleData/i15-1/integration/128991_mask_min1000_max30000.nxs";
	}
}

class FixedDiffractionMetadataImportModel extends DiffractionMetadataImportModel {
	@Override
	public String getFilePath() {
		return "/dls/science/groups/das/ExampleData/i15-1/integration/CeO2_NIST_8s_19slices_averaged_fixedE_calibration.nxs";
	}
}

class CeriaSampleMetadataModel extends XPDFInsertSampleMetadataModel {
	@Override
	public String getErrorFilePath() {
		return "";
	}
	@Override
	public String getErrorDataset() {
		return "";
	}
	@Override
	public double getCountingTime() {
		return 60.0;
	}
	@Override
	public double getMonitorRelativeFlux() {
		return 1.0;
	}
	@Override
	public String getMaterial() {
		return "CeO2";
	}
	@Override
	public double getDensity() {
		return 7.65;
	}
	@Override
	public double getPackingFraction() {
		return 0.6;
	}
	@Override
	public XPDFGeometryEnum getShape() {
		return XPDFGeometryEnum.CYLINDER;
	}
	@Override
	public double getInner() {
		return 0.0;
	}
	@Override
	public double getOuter() {
		return 0.5;
	}
	@Override
	public String getSampleName() {
		return "Ceria SRM";
	}
	@Override
	public boolean isAxisAngle() {
		return true;
	}
}

class OneMMContainerMetadataModel extends XPDFInsertContainerMetadataModel {
	@Override
	public String getFilePath() {
		return "/dls/science/groups/das/ExampleData/i15-1/2015Oct.Standards/Empty_1mmQGCT_8s_20slices_averaged.tif";
	}
	@Override
	public String getDataset() {
		return "image-01";
	}
	@Override
	public String getErrorFilePath() {
		return "";
	}
	@Override
	public String getErrorDataset() {
		return "";
	}
	@Override
	public double getCountingTime() {
		return 60.0;
	}
	@Override
	public double getMonitorRelativeFlux() {
		return 1.0;
	}
	@Override
	public String getMaterial() {
		return "SiO2";
	}
	@Override
	public double getDensity() {
		return 2.65;
	}
	@Override
	public double getPackingFraction() {
		return 1.0;
	}
	@Override
	public XPDFGeometryEnum getShape() {
		return XPDFGeometryEnum.CYLINDER;
	}
	@Override
	public double getInner() {
		return 0.5;
	}
	@Override
	public double getOuter() {
		return 0.51;
	}
	@Override
	public String getContainerName() {
		return "SiO2 capillary";
	}
	@Override
	public boolean isUpstream() {
		return true;
	}
	@Override
	public boolean isDownstream() {
		return true;
	}
}

class I15BeamMetadataModel extends XPDFInsertBeamMetadataModel {
	@Override
	public String getFilePath() {
		return "/dls/science/groups/das/ExampleData/i15-1/2015Oct.Standards/EmptyI15_8s_20slices_averaged.tif";
	}
	@Override
	public String getDataset() {
		return "image-01";
	}
	@Override
	public String getErrorFilePath() {
		return "";
	}
	@Override
	public String getErrorDataset() {
		return "";
	}
	@Override
	public double getBeamEnergy() {
		return 76.6;
	}
	@Override
	public double getBeamHeight() {
		return 0.07;
	}
	@Override
	public double getBeamWidth() {
		return 0.07;
	}
	@Override
	public double getCountingTime() {
		return 60.0;
	}
	@Override
	public double getMonitorRelativeFlux() {
		return 1.0;
	}
}

class DefineCsIDetector extends XPDFDefineDetectorModel {
	@Override
	public String getDetectorName() {
		return "Perkin Elmer";
	}
	@Override
	public String getDetectorMaterial() {
		return "CsI";
	}
	@Override
	public double getDensity() {
		return 4.51;
	}
	@Override
	public double getThickness() {
		return 0.5;
	}
	@Override
	public double getSolidAngle() {
		return 0.1;
	}
}

class YesNormalizeTracesModel extends XPDFNormalizeTracesModel {
	@Override
	public boolean isNormalizeSample() {
		return true;
	}
	@Override
	public boolean isNormalizeContainers() {
		return true;
	}
	@Override
	public boolean isNormalizeBeam() {
		return true;
	}
}

class YesSubtractBackgroundModel extends XPDFSubtractBackgroundModel {
	@Override
	public boolean isSubtractSampleBackground() {
		return true;
	}
	@Override
	public boolean isSubtractContainersBackground() {
		return true;
	}

}

class FakeXICCOModel extends XPDFIterateCalibrationConstantModel {
	@Override
	public int getnIterations() {
		return 5;
	}
	@Override
	public boolean isSortContainers() {
		return false;
	}
	@Override
	public boolean isDoingFluorescence() {
		return true;
//		return false;
	}
}