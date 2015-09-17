package uk.ac.diamond.scisoft.xpdf.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
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

}
