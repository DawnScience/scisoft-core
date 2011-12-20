/*
 * Copyright © 2009 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;


import gda.data.nexus.extractor.NexusExtractorException;
import gda.data.nexus.tree.INexusTree;
import gda.data.nexus.tree.NexusTreeNodeSelection;
import gda.util.TestUtils;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nexusformat.NexusException;
import org.xml.sax.InputSource;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class NexusLoaderTests {
	final static String TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
	

	@Before
	public void setUp() {
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation returned null - test aborted");
		}
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetDataSetNames() throws NexusException, NexusExtractorException, Exception {
		List<String> dataSetNames = NexusLoader.getDatasetNames(TestFileFolder + "FeKedge_1_103.nxs", null);

		Collections.sort(dataSetNames, String.CASE_INSENSITIVE_ORDER);
		Assert.assertEquals("counterTimer01.I0", dataSetNames.get(0));
		Assert.assertEquals("xspress2system_element_9.data", dataSetNames.get(134));

		Vector<String> wanted = new Vector<String>();
		wanted.add("xspress2system_element_9.data");
		wanted.add("Energy");
		NexusLoader nl = new NexusLoader(TestFileFolder + "FeKedge_1_103.nxs", wanted);
		DataHolder dh = nl.loadFile();
		AbstractDataset energyDs = dh.getDataset("Energy");
		Assert.assertEquals(561, energyDs.getSize());
		AbstractDataset ds2 = dh.getDataset("xspress2system_element_9.data");
		Assert.assertEquals(5, ds2.getShape().length);

		Map<String, INexusTree> trees = NexusLoader.getDatasetNexusTrees(TestFileFolder + "FeKedge_1_103.nxs", null, false, null);
		Assert.assertEquals(561, trees.get("Energy").getData().dimensions[0]);

		wanted = new Vector<String>();
		wanted.add("Energy");		
		trees = NexusLoader.getDatasetNexusTrees(TestFileFolder + "FeKedge_1_103.nxs", wanted, true, null);
		Assert.assertEquals(26366.4, ((double [])trees.get("Energy").getData().getBuffer())[560],0.01);
		
	}

	@Test
	public void testSpeedOfLoadingTreeWithNoData() throws Exception {
		long before = System.nanoTime();
		new NexusLoader(TestFileFolder + "327.nxs", NexusTreeNodeSelection.SKIP, getSel(), null).loadFile();
		Assert.assertTrue((System.nanoTime()-before)*1e-9 < 5.0);
	}
	
	private static NexusTreeNodeSelection getSel() throws Exception{
		String xml = "<?xml version='1.0' encoding='UTF-8'?>" +
		"<nexusTreeNodeSelection>" +
		"<nexusTreeNodeSelection><nxClass>NXentry</nxClass><wanted>2</wanted><dataType>2</dataType>" +
		"<nexusTreeNodeSelection><nxClass>NXdata</nxClass><wanted>2</wanted><dataType>2</dataType>" +
		"<nexusTreeNodeSelection><nxClass>SDS</nxClass><wanted>2</wanted><dataType>1</dataType>" +
		"</nexusTreeNodeSelection>" +
		"</nexusTreeNodeSelection>" +
		"<nexusTreeNodeSelection><nxClass>NXinstrument</nxClass><wanted>2</wanted><dataType>2</dataType>" +
		"<nexusTreeNodeSelection><nxClass>NXdetector</nxClass><wanted>2</wanted><dataType>2</dataType>" +
		"<nexusTreeNodeSelection><nxClass>SDS</nxClass><wanted>2</wanted><dataType>1</dataType>" +
		"</nexusTreeNodeSelection>" +
		"</nexusTreeNodeSelection>" +
		"</nexusTreeNodeSelection>" +
		"</nexusTreeNodeSelection>" +
		"</nexusTreeNodeSelection>";
		return NexusTreeNodeSelection.createFromXML(new InputSource(new StringReader(xml)));
	}
	
}
