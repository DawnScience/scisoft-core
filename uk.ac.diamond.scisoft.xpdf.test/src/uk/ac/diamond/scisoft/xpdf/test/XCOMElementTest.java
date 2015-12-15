package uk.ac.diamond.scisoft.xpdf.test;

//import uk.ac.diamond.scisoft.xpdf.XCOMElement;
//import junit.framework.TestCase;
//
//public class XCOMElementTest extends TestCase {
//
//	public void testXCOMElementInt() {
//		
//		XCOMElement xcomOxygen = new XCOMElement(8); 
//		final double diff = 5e-4;
//		
//		double xsect = xcomOxygen.getCrossSection(1.0, "coherent");
//		System.out.println(""+xsect);
//		assertTrue("Error in 1 keV oxygen coherent scattering too large", Math.abs(xsect/39.89-1) < diff);
//		xsect = xcomOxygen.getCrossSection(76.6, "total");
//		System.out.println(""+xsect);
//		assertTrue("Error in 76.6 keV oxygen total scattering too large", Math.abs(xsect/4.535-1) < diff);
//
//		XCOMElement xcomSi = new XCOMElement(14);
//		xsect = xcomSi.getCrossSection(76.6, "total");
//		System.out.println(""+xsect);
//		assertTrue("Error in 76.6 keV silicon total scattering too large", Math.abs(xsect/10.88-1) < diff);
//		
//		XCOMElement xcomCe = new XCOMElement(58);
//		xsect = xcomCe.getCrossSection(65.3, "total");
//		System.out.println(""+xsect);
//		assertTrue("Error in 65.3 keV cerium total scattering too large", Math.abs(xsect/1758.0-1) < diff);
//
//		xsect = xcomCe.getCrossSection(40.0, "total");
//		System.out.println(""+xsect);
//		assertTrue("Error in 40 keV cerium total scattering too large", Math.abs(xsect/1213.0-1) < diff);
//		
//	}
//
//}
