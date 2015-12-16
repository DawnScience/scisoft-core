package uk.ac.diamond.scisoft.xpdf.test;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.XPDFElectronCrossSections;
import junit.framework.TestCase;

public class XPDFEXSTest extends TestCase {

	static double re2 = 7.940787684842103e-30*1e28;
	
	public void testGetThomsonCrossSection() {

		// 1D testing
		Dataset twoTheta = DoubleDataset.createRange(0, Math.toRadians(45.0), Math.toRadians(0.05));
		XPDFCoordinates coords = new XPDFCoordinates();
		coords.setTwoTheta(twoTheta);
		XPDFElectronCrossSections xECS = new XPDFElectronCrossSections();
		xECS.setCoordinates(coords);
		xECS.setBeamEnergy(76.6);
		
		Dataset oldThomson = Maths.multiply(xECS.getThomsonCrossSection(), re2);
		Dataset newThomson = XPDFElectronCrossSections.getThomsonCrossSection(coords);
		
		Dataset ratioExcess = Maths.subtract(Maths.divide(newThomson, oldThomson), 1);
		double rms = Math.sqrt((double) Maths.square(ratioExcess).mean());
		double maxError = 1e-2;
		
		assertTrue("Ratio of old and new Thomson cross-sections too large: " + rms, rms < maxError);
	}

	public void testGetKleinNishinaCrossSection() {
		// 1D testing
		Dataset twoTheta = DoubleDataset.createRange(0, Math.toRadians(45.0), Math.toRadians(0.05));
		XPDFCoordinates coords = new XPDFCoordinates();
		coords.setTwoTheta(twoTheta);
		XPDFElectronCrossSections xECS = new XPDFElectronCrossSections();
		xECS.setCoordinates(coords);
		xECS.setBeamEnergy(76.6);

		Dataset oldKN = Maths.multiply(xECS.getKleinNishinaCrossSection(), re2);
		Dataset newKN = XPDFElectronCrossSections.getKleinNishinaCrossSection(coords, 76.6);
		
		Dataset ratioExcess = Maths.subtract(Maths.divide(newKN, oldKN), 1);
		double rms = Math.sqrt((double) Maths.square(ratioExcess).mean());
		double maxError = 1e-2;
		
		assertTrue("Ratio of old and new Klein-Nishina cross-sections too large: " + rms, rms < maxError);
	}


}
