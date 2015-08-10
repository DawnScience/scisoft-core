package uk.ac.diamond.scisoft.xpdf.test;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFContainerMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFProcessor;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFBeamMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFContainersMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetAbstractGeometryMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetComponentMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetCylinderMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetFormMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTargetPlateMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFTraceMetadataImpl;
import junit.framework.TestCase;

public class XPDFProcessorTest extends TestCase {

	@Test
	public void testXPDFProcessorLorchFT(){
		if (false) fail("Don't want to run this test right now");
		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"th_soq.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset tthd = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset thSoq = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		XPDFProcessor mrData = makeCeriaProcessor();
		mrData.setR(1.85, 50, 0.02);
		Dataset r = mrData.getR();
		
		mrData.setIntermediateResult("th_soq", thSoq);
		mrData.setQ(tthd);
		
		Dataset gofr = DatasetUtils.convertToDataset(mrData.getGofr());
		Dataset dofr = DatasetUtils.convertToDataset(mrData.getDofr());
		
		assertTrue("RMS error of dofr too large", checkDofr(r, dofr));
		assertTrue("RMS error of gofr too large", checkGofr(r, gofr));
		
		return;
	}
	
	
	@Test
	public void testXPDFProcessorTophatSubtraction() {
		if (false) fail("Don't want to run this test right now");
		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"th_DprimedoQ.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset tthd = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset dPrimedoQ = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		XPDFProcessor mrData = makeCeriaProcessor();
		Dataset r = mrData.getR();
		
		mrData.setIntermediateResult("DprimedoQ", dPrimedoQ);
		mrData.setQ(tthd);
		
		Dataset gofr = DatasetUtils.convertToDataset(mrData.getGofr());
		Dataset dofr = DatasetUtils.convertToDataset(mrData.getDofr());
		
		assertTrue("RMS error of dofr too large", checkDofr(r, dofr));
		assertTrue("RMS error of gofr too large", checkGofr(r, gofr));

		
	}
	
	@Test
	public void testXPDFProcessorTophatConvolution() {
		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"soq.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset tthd = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset soq = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		XPDFProcessor mrData = makeCeriaProcessor();
		Dataset r = mrData.getR();
		
		mrData.setIntermediateResult("soq", soq);
		mrData.setQ(tthd);
		
		Dataset gofr = DatasetUtils.convertToDataset(mrData.getGofr());
		Dataset dofr = DatasetUtils.convertToDataset(mrData.getDofr());
		
		assertTrue("RMS error of dofr too large", checkDofr(r, dofr));
		assertTrue("RMS error of gofr too large", checkGofr(r, gofr));

		
	}
	@Test
	public void testXPDFProcessorSelfScatterNormalise() {
		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"ABSCOR.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset tthd = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset absCor = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"fsquaredofx.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset fSquaredOfX = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"self_scattering.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset selfScattering = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		XPDFProcessor mrData = makeCeriaProcessor();
		Dataset r = mrData.getR();
		
		mrData.setIntermediateResult("ABSCOR", absCor);
		mrData.setIntermediateResult("fsquaredofx", fSquaredOfX);
		mrData.setIntermediateResult("self_scattering", selfScattering);
		mrData.setQ(tthd);
		
		Dataset gofr = DatasetUtils.convertToDataset(mrData.getGofr());
		Dataset dofr = DatasetUtils.convertToDataset(mrData.getDofr());
		
		assertTrue("RMS error of dofr too large", checkDofr(r, dofr));
		assertTrue("RMS error of gofr too large", checkGofr(r, gofr));

		
	}

	
	private boolean checkDofr(Dataset r, Dataset dofr) {

		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"dofr.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset dofrExp = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());

		return checkDofrGofr(r, dofr, dofrExp);
	}
	
	private boolean checkGofr(Dataset r, Dataset gofr) {

		String dataPath = "/home/rkl37156/ceria_dean_data/";

		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"gofr.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset gofrExp = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());

		return checkDofrGofr(r, gofr, gofrExp);
	}
	
	private boolean checkDofrGofr(Dataset r, Dataset actual, Dataset expected) {
		
		Dataset dActual = Maths.derivative(r, actual, 1);
		Dataset dExpected = Maths.derivative(r, expected, 1);
		Dataset fError = Maths.divide(Maths.square(Maths.subtract(dExpected, dActual)), Maths.square(dExpected));
		double totalError = Math.sqrt((double) fError.sum())/r.getSize();

		return totalError < 1e-6;
	}
	
	// Fill in the processor values and metadata for the ceria experiment
	private XPDFProcessor makeCeriaProcessor() {
		
		// Create the metadata to create the XPDFProcessor
		
		IDataHolder dh = null;
		String dataPath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceriaXPDF/";
		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"CeO2.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset ceria = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		Dataset tthd = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());

		ceria.setMetadata(createBeamMetadata());
		ceria.setMetadata(createContainersMetadata());
		ceria.setMetadata(createSampleMetadata());
		AxesMetadataImpl ax = new AxesMetadataImpl(1);
		ax.addAxis(0, (IDataset) tthd);
		ceria.addMetadata(ax);

		XPDFProcessor mrData = new XPDFProcessor(ceria);
		mrData.setR(1.85, 50, 0.02);
		mrData.setLorchWidth(0.2);
		mrData.setTophatWidth(3.0);
		
		return mrData;
		
	}


	private XPDFContainerMetadata createContainersMetadata() {
		XPDFTargetComponentMetadataImpl compMeta = new XPDFTargetComponentMetadataImpl();
		XPDFTargetFormMetadataImpl formMeta = new XPDFTargetFormMetadataImpl();
		XPDFTargetAbstractGeometryMetadataImpl geomMeta = null;

		// Read shape from the Model
		String shape = "cylinder";

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFTargetCylinderMetadataImpl();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFTargetPlateMetadataImpl();
		}
		// Read size data from the Model
		double inner = 0.15;
		double outer = 0.16;
		boolean is_up = true;
		boolean is_down = true;
		
		geomMeta.setDistances(inner, outer);
		geomMeta.setStreamality(is_up, is_down);
				
		// Get the material data from the Model
		String material = "SiO2";
		double density = 2.65;
		double packingFraction = 1.0;

		formMeta.setMaterialName(material);
		formMeta.setDensity(density);
		formMeta.setPackingFraction(packingFraction);
		formMeta.setGeometry(geomMeta);

		compMeta.setForm(formMeta);

		// Get sample name from the Model
		String name = "SiO2 capillary";

		compMeta.setName(name);

		// Trace metadata
		IDataHolder dh = null;
		String dataPath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceriaXPDF/";
		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"Empty_cap.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset contTrace = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());

		// The counting time and monitor relative flux are set directly on the
		// input Dataset, since they pertain to the data it holds
		XPDFTraceMetadataImpl containerTraceMeta = new XPDFTraceMetadataImpl();
		containerTraceMeta.setCountingTime(240.0);
		containerTraceMeta.setMonitorRelativeFlux(1.0);
		containerTraceMeta.setTrace(contTrace);

		compMeta.setTrace(containerTraceMeta);		

		compMeta.setSample(false);

		// compMeta is complete. Add it to the list of containers in input

		XPDFContainerMetadata containerList = new XPDFContainersMetadataImpl();
		containerList.addContainer(compMeta);

		return containerList;
	}


	private XPDFTargetComponentMetadata createSampleMetadata() {
		XPDFTargetComponentMetadataImpl compMeta = new XPDFTargetComponentMetadataImpl();
		XPDFTargetFormMetadataImpl formMeta = new XPDFTargetFormMetadataImpl();
		XPDFTargetAbstractGeometryMetadataImpl geomMeta = null;

		String shape = "cylinder";

		if (shape.equals("cylinder")) {
			geomMeta = new XPDFTargetCylinderMetadataImpl();
		} else if (shape.equals("plate")) {
			geomMeta = new XPDFTargetPlateMetadataImpl();
		}
		double inner = 0.0;
		double outer = 0.15;
		// samples have no streamality

		geomMeta.setDistances(inner, outer);

		String material = "CeO2";
		double density = 7.65;
		double packingFraction = 0.6;

		formMeta.setMaterialName(material);
		formMeta.setDensity(density);
		formMeta.setPackingFraction(packingFraction);
		formMeta.setGeometry(geomMeta);

		compMeta.setForm(formMeta);

		String name = "Ceria SRM";

		compMeta.setName(name);

		XPDFTraceMetadataImpl sampleTraceMeta = new XPDFTraceMetadataImpl();
		sampleTraceMeta.setCountingTime(240.0);
		sampleTraceMeta.setMonitorRelativeFlux(1.0);
		sampleTraceMeta.setTrace(null);
		
		compMeta.setTrace(sampleTraceMeta);
		
		compMeta.setSample(true);
		return compMeta;
	}


	private XPDFBeamMetadata createBeamMetadata() {
		XPDFBeamMetadataImpl beamMetadata = new XPDFBeamMetadataImpl();
		// Get the properties of the beam
		beamMetadata.setBeamEnergy(76.6);
		beamMetadata.setBeamHeight(0.07);
		beamMetadata.setBeamWidth(0.07);
		
		IDataHolder dh = null;
		String dataPath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceriaXPDF/";
		try {
			// No access to services in JUnit Tests
			dh = LoaderFactory.getData(dataPath+"Empty_I15.xy");
		} catch (Exception e) {
			//ignore
		}
		Dataset bgTrace = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		XPDFTraceMetadataImpl bgMetadata = new XPDFTraceMetadataImpl();
		bgMetadata.setCountingTime(240.0);
		bgMetadata.setMonitorRelativeFlux(1.0);
		bgMetadata.setTrace(bgTrace);
		
		beamMetadata.setTrace(bgMetadata);
		return beamMetadata;
	}
	
}
