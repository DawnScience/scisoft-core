package uk.ac.diamond.scisoft.xpdf.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;
import junit.framework.TestCase;

public class XPDFMetadataTest extends TestCase {

	public void testReorderContainers() {
		
		// Create the implementation
		XPDFMetadataImpl theMetadataImpl = new XPDFMetadataImpl();
		// Create a load of fake containers
		XPDFTargetComponent container;
		container = new XPDFTargetComponent();
		container.setName("C0,0");
		theMetadataImpl.addContainer(container);
		container = new XPDFTargetComponent();
		container.setName("C1,3");
		theMetadataImpl.addContainer(container);
		container = new XPDFTargetComponent();
		container.setName("C2,1");
		theMetadataImpl.addContainer(container);
		container = new XPDFTargetComponent();
		container.setName("C3,2");
		theMetadataImpl.addContainer(container);
		
		Map<Integer, Integer> reorderMapping = new HashMap<Integer, Integer>();
		reorderMapping.put(0, 0);
		reorderMapping.put(1, 2);
		reorderMapping.put(2, 3);
		reorderMapping.put(3, 1);
		
		theMetadataImpl.reorderContainers(reorderMapping);
		
		List<XPDFTargetComponent> theContainers = theMetadataImpl.getContainers();

		assertTrue("container 0 mismatch", theContainers.get(0).getName() == "C0,0");
		assertTrue("container 1 mismatch", theContainers.get(1).getName() == "C2,1");
		assertTrue("container 2 mismatch", theContainers.get(2).getName() == "C3,2");
		assertTrue("container 3 mismatch", theContainers.get(3).getName() == "C1,3");
		
	}

	
	public void testFluorescence() {
		// Test the fluorescence of the NIST ceria standards, obtained 2015-10
		XPDFMetadata meta = buildNistCeria();
		
		String dataPath = "/home/rkl37156/ceria_dean_data/testData/";
		IDataHolder dh = null;
		try {
			dh = LoaderFactory.getData(dataPath+"ceria_total_fluorescence" + ".xy");
		} catch (Exception e) {
		}
		Dataset delta1D = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset fluorExp = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		Dataset delta = new DoubleDataset(delta1D.getSize(), 1);
		for (int i = 0; i<delta1D.getSize(); i++)
			delta.set(delta1D.getDouble(i), i, 0);
		Dataset gamma = DoubleDataset.zeros(delta);
		
		Dataset fluor = meta.getSampleFluorescence(gamma, delta);
		Dataset squaredDiff = Maths.square(Maths.subtract(Maths.divide(fluor, fluorExp), 1));
		double rmsError = Math.sqrt((double) squaredDiff.mean());
		double maxError = 1e-2;
		assertTrue("Too large an error in ceria total fluorescence, " + rmsError, rmsError < maxError);
	}
	
	public XPDFMetadata buildNistCeria() {
		XPDFMetadataImpl meta = new XPDFMetadataImpl();
		
		// ceria powder sample
		XPDFComponentGeometry powder = new XPDFComponentCylinder();
		powder.setDistances(0.0, 0.5);
		powder.setStreamality(true, true);
		XPDFComponentForm powderForm = new XPDFComponentForm();
		powderForm.setGeom(powder);
		powderForm.setDensity(7.65);
		powderForm.setMatName("CeO2");
		powderForm.setPackingFraction(0.6);

		XPDFTargetComponent sample = new XPDFTargetComponent();
		sample.setForm(powderForm);
		sample.setSample(true);
		
		meta.setSampleData(sample);
		
		// quartz capillary
		XPDFComponentGeometry capillary = new XPDFComponentCylinder();
		capillary.setDistances(0.5, 0.51);
		capillary.setStreamality(true, true);
		XPDFComponentForm capillaryForm = new XPDFComponentForm();
		capillaryForm.setGeom(capillary);
		capillaryForm.setDensity(2.65);
		capillaryForm.setMatName("SiO2");
		capillaryForm.setPackingFraction(1.0);
		
		XPDFTargetComponent capCom = new XPDFTargetComponent();
		capCom.setForm(capillaryForm);
		
		meta.addContainer(capCom);
		
		// the beam
		XPDFBeamData beamData = new XPDFBeamData();
		beamData.setBeamEnergy(76.6);
		beamData.setBeamHeight(0.07);
		beamData.setBeamWidth(0.07);
		
		meta.setBeamData(beamData);
		
		// the detector
		XPDFDetector tect = new XPDFDetector();
		tect.setThickness(0.5);
		tect.setSubstance(new XPDFSubstance("caesium iodide", "CsI", 4.51, 1.0));
		
		meta.setDetector(tect);
		
		return meta;
	}
}
