package uk.ac.diamond.scisoft.xpdf.test;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFQSquaredIntegrator;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import junit.framework.TestCase;

public class XPDFTargetComponentTest extends TestCase {

	public void testGetKroghMoeSum() {
		double KroghMoeSumExpected = 964.425331596;
		XPDFTargetComponent ceriaComponent = generateCeriaComponent();
		
		double KroghMoeSum = ceriaComponent.getKroghMoeSum();
		
		double difference = KroghMoeSum - KroghMoeSumExpected;
		assertTrue("Difference in Krogh-Moe sum too large: "+difference, Math.abs(difference/KroghMoeSumExpected) < 1e-2);
		
	}

	public void testGetSelfScattering() {
		XPDFTargetComponent ceriaComponent = generateCeriaComponent();

//		double selfScatteringIntegralExpected = 463628.661954;
		double selfScatteringIntegralExpected = 544918.666;
		
		String dataPath = "/home/rkl37156/ceria_dean_data/";
		IDataHolder dh = null;
		String scattererName, attenuatorName;
		try {
			dh = LoaderFactory.getData(dataPath+"self_scattering.xy");
		} catch (Exception e) {
		}
		Dataset twoTheta = Maths.toRadians(DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSliceView(new int[] {0},  new int[]{1951},  new int[] {1})));

		XPDFBeamData beamData = new XPDFBeamData();
		beamData.setBeamEnergy(76.6);
		
		XPDFCoordinates coords = new XPDFCoordinates();
		coords.setBeamData(beamData);
		coords.setTwoTheta(twoTheta);
		
		// Set up the q² integrator class
		XPDFQSquaredIntegrator qSquaredIntegrator = new XPDFQSquaredIntegrator(coords);
		
		// Difference ofKrogh-Moe sum and integral of Thomson self-scattering
		double selfScatteringIntegral = qSquaredIntegrator.ThomsonIntegral(ceriaComponent.getSelfScattering(coords));

		double difference = selfScatteringIntegral/selfScatteringIntegralExpected - 1;
		assertTrue("Difference in self scattering integral too large: "+difference*100+"%", Math.abs(difference/selfScatteringIntegralExpected) < 1e-6);
	}

	private XPDFTargetComponent generateCeriaComponent() {
		
		XPDFTargetComponent ceriaComponent = new XPDFTargetComponent();
		XPDFComponentForm formMeta = new XPDFComponentForm();
		XPDFComponentGeometry geomMeta = null;

		
		geomMeta = new XPDFComponentCylinder();
		geomMeta.setDistances(0.0, 0.5);
		
		formMeta.setMatName("CeO2");
		formMeta.setDensity(7.65);
		formMeta.setPackingFraction(0.6);
		formMeta.setGeom(geomMeta);

		ceriaComponent.setForm(formMeta);
		ceriaComponent.setName("Ceria powder");
		
		ceriaComponent.setSample(true);
		
		return ceriaComponent;
	}
}
