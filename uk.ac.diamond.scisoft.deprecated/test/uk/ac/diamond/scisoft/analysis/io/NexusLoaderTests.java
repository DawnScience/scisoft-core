/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;


import gda.data.nexus.extractor.NexusExtractorException;
import gda.data.nexus.tree.INexusTree;
import gda.data.nexus.tree.NexusTreeNodeSelection;
import uk.ac.diamond.scisoft.analysis.TestUtils;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.hdf5.nexus.NexusException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

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
		Dataset energyDs = dh.getDataset("Energy");
		Assert.assertEquals(561, energyDs.getSize());
		Dataset ds2 = dh.getDataset("xspress2system_element_9.data");
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
