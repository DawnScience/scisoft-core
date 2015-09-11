package uk.ac.diamond.scisoft.xpdf.test;

import uk.ac.diamond.scisoft.xpdf.XPDFMassAttenuation;
import junit.framework.TestCase;

public class XPDFMassAttenuationTest extends TestCase {

	public void testGet() {
//		double ceriaMuExp76 = 4.0599040920479297;
//		double silicaMuExp76 = 0.19991818715584905;
		double ceriumMuExp76 = 4.94810247034;
		double siliconMuExp76 = 0.233209344572;
		double oxygenMuExp76 = 0.170698378863;
		double ceriumMuExp40 = 5.21450473204;
		
		double ceriumMu76 = XPDFMassAttenuation.get(76600, 58);
		double siliconMu76 = XPDFMassAttenuation.get(76600, 14);
		double oxygenMu76 = XPDFMassAttenuation.get(76600, 8);
		
		double ceriumMu40 = XPDFMassAttenuation.get(40000, 58);
		
		double accuTarget = 1e-6;
		
		assertTrue("Difference in cerium attenuation coefficient too large", Math.abs(ceriumMuExp76 - ceriumMu76) < accuTarget);
		assertTrue("Difference in cerium attenuation coefficient too large", Math.abs(ceriumMuExp40 - ceriumMu40) < accuTarget);
		assertTrue("Difference in cerium attenuation coefficient too large", Math.abs(siliconMuExp76 - siliconMu76) < accuTarget);
		assertTrue("Difference in cerium attenuation coefficient too large", Math.abs(oxygenMuExp76 - oxygenMu76) < accuTarget);
	
	}

}
