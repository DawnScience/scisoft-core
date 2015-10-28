package uk.ac.diamond.scisoft.xpdf.test;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import junit.framework.TestCase;

@SuppressWarnings("deprecation")
public class XPDFDetectorTest extends TestCase {

	public boolean doTestApplyCorrection(XPDFDetector tect, String photonEnergyeV) {
		String dataPath = "/home/rkl37156/ceria_dean_data/testData/";
		IDataHolder dh = null;
		try {
			dh = LoaderFactory.getData(dataPath+ "ceria"+photonEnergyeV+".before.xy");
		} catch (Exception e) {
			System.err.println("File "+ dataPath+ "ceria"+photonEnergyeV+".before.xy" + " not found!");
			return false;
		}
		Dataset before = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());

		try {
			dh = LoaderFactory.getData(dataPath+"ceria"+photonEnergyeV+".after.xy");
		} catch (Exception e) {
			System.err.println("File "+ dataPath+ "ceria"+photonEnergyeV+".before.xy" + " not found!");
			return false;
		}
		Dataset expected = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		Dataset after = tect.applyCorrection(before, Integer.parseInt(photonEnergyeV)*1e-3);
		
		final double maxError = 5e-2;
		Dataset ratio = Maths.divide(after, expected);
		Dataset fractionalDifference = Maths.subtract(ratio, 1.0);
		double rmsError = Math.sqrt((double) Maths.square(fractionalDifference).mean());

		return rmsError < maxError;
	}

	public void test76600() {
		String photonEnergyeV = "76600";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	public void test39258() {
		String photonEnergyeV = "39258";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	public void test34720() {
		String photonEnergyeV = "34720";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	public void test34279() {
		String photonEnergyeV = "34279";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	public void test5263() {
		String photonEnergyeV = "5263";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	public void test4840() {
		String photonEnergyeV = "4840";
		assertTrue("Error in detector correction at "+photonEnergyeV+" eV", doTestApplyCorrection(buildDetector(), photonEnergyeV));
	}
	
	
	public XPDFDetector buildDetector() {
		String dataPath = "/home/rkl37156/ceria_dean_data/testData/";
		IDataHolder dh = null;
		try {
			dh = LoaderFactory.getData(dataPath+ "ceria76600.before"+".xy");
		} catch (Exception e) {
		}
		Dataset delta = Maths.toRadians(DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice()));

		
		XPDFDetector tect = new XPDFDetector();
		tect.setSubstance(new XPDFSubstance("caesium iodide", "CsI", 4.51, 1.0));
		tect.setThickness(0.5);
//		XPDFBeamData theBeam = new XPDFBeamData();
//		theBeam.setBeamEnergy(76.6);
		
		XPDFCoordinates theCoords = new XPDFCoordinates();
//		theCoords.setBeamData(theBeam);
		theCoords.setEnergy(76.6);
		theCoords.setGammaDelta(DoubleDataset.zeros(delta), delta);
		tect.setCoords(theCoords);

		
		return tect;
	}
}
