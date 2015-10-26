package uk.ac.diamond.scisoft.xpdf.test;

import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import junit.framework.TestCase;

public class XPDFSubstanceTest extends TestCase {

	public void testGetAttenuationCoefficient() {
		XPDFSubstance ceria = new XPDFSubstance("ceria", "CeO2", 7.65, 0.6);
		XPDFSubstance bto = new XPDFSubstance("BTO", "BaTiO3", 6.05, 0.6);
		XPDFSubstance nickel = new XPDFSubstance("Nickel", "Ni", 8.95, 0.6);
		
		double maxError = 3e-2;
		
		double ceria34_7196 = ceria.getAttenuationCoefficient(34.7196);
		assertTrue("Too large an error in ceria at 34.7196 keV", Math.abs(ceria34_7196/2.85619-1) < maxError);
		
		double bto32_1936 = bto.getAttenuationCoefficient(32.1936);
		assertTrue("Too large an error in BTO at 32.1936 keV", Math.abs(bto32_1936/2.07118-1) < maxError);

		double nickel7_4781 = nickel.getAttenuationCoefficient(7.4781);
		assertTrue("Too large an error in nickel at 7.4781 keV", Math.abs(nickel7_4781/31.8248-1) < maxError);

	}

}
