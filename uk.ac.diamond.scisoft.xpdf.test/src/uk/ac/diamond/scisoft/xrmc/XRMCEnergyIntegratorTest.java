package uk.ac.diamond.scisoft.xrmc;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCEnergyIntegrator;

public class XRMCEnergyIntegratorTest {

	private Dataset xrmcData;
	private IMetadata meta;
	XRMCEnergyIntegrator integrator;
	XPDFDetector det;
	
	@Before
	public void setUp() throws Exception {
		IDataHolder iDH = LoaderFactory.getData("/dls/science/groups/das/ExampleData/i15-1/xrmc/example_output_image.xrmc");
		xrmcData = DatasetUtils.convertToDataset(iDH.getDataset(0));
		int[] dataShape = xrmcData.getShape();
		// Here, as in most cases, we only want the final scattering order
		xrmcData = xrmcData.getSliceView(new int[]{dataShape[0]-1, 0, 0, 0}, dataShape, new int[]{1, 1, 1, 1});
		xrmcData.squeeze();
		
		meta = iDH.getMetadata();
		integrator = new XRMCEnergyIntegrator();
		integrator.setXRMCData(xrmcData);
		
		// Set up the detector
		det = new XPDFDetector();
		det.setSubstance(new XPDFSubstance("Caesium Iodide", "CsI", 4.51, 1.0));
		det.setThickness(0.5); // mm
		det.setSolidAngle(2.094); // steradians. The size of the whole 'detector' plan in this case
		det.setEulerAngles(0, 0, 0); // radians. Just for completeness
		
	}

	@Test
	public void testGetDetectorCounts() {
		int expX = 40, expY = 40;
		
		Dataset xrmcIntegrated = integrator.getDetectorCounts();
		assertArrayEquals(new int[]{expX, expY}, xrmcIntegrated.getShape());
		assertFalse(xrmcIntegrated.containsNans());
		assertEquals(625349., xrmcIntegrated.getElementDoubleAbs(expX/2 + expX*expY/2), 1.);

	}
	
	@Test
	public void testSetDetectorGetDetectorCounts() {
		// Now set the parameters to do the integration properly
		integrator.setDetector(det);
		int expX = 40, expY = 40;
		
		// energies of the centre of the energy bins
		int nEnergies = 1000;
		double minEnergy = 0., maxEnergy = 80.;// keV
		Dataset energies = DatasetFactory.createRange(nEnergies);
		energies.iadd(0.5);
		energies.idivide(nEnergies); // centred, normalized
		energies.imultiply(maxEnergy-minEnergy); // correct range
		energies.iadd(minEnergy); // correct offset
		
		integrator.setEnergies(energies);
		
		// geometry of the detector
		integrator.setGeometry(new Vector3d(20.,  20.,  20.), DatasetFactory.createFromList(Arrays.asList(new Double[]{0., 0., 0.})), DatasetFactory.createFromList(Arrays.asList(new Double[]{10., 10.})));
		
		
		Dataset xrmcIntegrated = integrator.getDetectorCounts();
		assertNotNull(xrmcIntegrated);
		assertArrayEquals(new int[]{expX, expY}, xrmcIntegrated.getShape());
		assertFalse(xrmcIntegrated.containsNans());
		// Check that the integration is not just a direct sum
		assertNotEquals(625349., xrmcIntegrated.getElementDoubleAbs(expX/2 + expX*expY/2), 1.);
		
	}

}
