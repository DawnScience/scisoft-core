package org.eclipse.dawnsci.nexus.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class NexusValidationServiceTest {
	
	private NexusValidationService validationService = new NexusValidationServiceImpl();
	
	private static String testDir; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testDir = TestUtils.generateDirectorynameFromClassname(NexusValidationServiceTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testDir);
		NexusTestUtils.setUpServices();
	}
	
	@Test
	public void testValidateSubentry() {
		final NXsubentry subentry = NexusNodeFactory.createNXsubentry();
		subentry.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		final ValidationReport report = validationService.validateSubentry(subentry);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(1)); // empty NXmx entry has 1 validation error
		assertThat(report.getErrors(), hasSize(1));
	}
	
	@Test
	public void testValidateEntry_noAppDef() {
		final NXentry entry = NexusNodeFactory.createNXentry();
		final ValidationReport report = validationService.validateEntry(entry);
		assertThat(report.isOk(), is(true));
		assertThat(report.isError(), is(false));
		assertThat(report.getNumberOfEntries(), is(0));
		assertThat(report.getErrors(), is(empty()));
	}
	
	@Test
	public void testValidateEntry_invalid() {
		final NXentry entry = NexusNodeFactory.createNXentry();
		entry.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		final ValidationReport report = validationService.validateEntry(entry);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(1));
		assertThat(report.getErrors(), hasSize(1));
	}
	
	@Test
	public void testValidateEntryWithSubentries() {
		final NXentry entry = NexusNodeFactory.createNXentry();
		final NXsubentry tomoSubentry = NexusNodeFactory.createNXsubentry();
		tomoSubentry.setDefinitionScalar(NexusApplicationDefinition.NX_TOMO.toString());
		entry.setSubentry("tomo", tomoSubentry);
		final NXsubentry mxSubentry = NexusNodeFactory.createNXsubentry();
		mxSubentry.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		entry.setSubentry("mx", mxSubentry);

		final ValidationReport report = validationService.validateEntry(entry);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(4)); // empty NXtomo group has 3 validation errors
		assertThat(report.getErrors(), hasSize(4));
	}
	
	private NXroot createNexusRoot() {
		final NXroot root = NexusNodeFactory.createNXroot();
		final NXentry entry1 = NexusNodeFactory.createNXentry();
		entry1.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		root.setEntry("entry1", entry1);
		final NXentry entry2 = NexusNodeFactory.createNXentry();
		entry2.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		root.setEntry("entry2", entry2);
		final NXsubentry subentry = NexusNodeFactory.createNXsubentry();
		subentry.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		entry2.setSubentry(subentry);
		final NXentry entry3 = NexusNodeFactory.createNXentry();
		entry3.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		root.setEntry("entry3", entry3);
		return root;
	}
	
	@Test
	public void testValidateTree_root() {
		final NXroot root = createNexusRoot();
		
		final ValidationReport report = validationService.validateNexusTree(root);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(4));
		assertThat(report.getErrors(), hasSize(4));
	}

	@Test
	public void testValidateTree_treeFile() {
		final NXroot root = createNexusRoot();
		final TreeFile treeFile = NexusNodeFactory.createTreeFile("nexusFile.nxs");
		treeFile.setGroupNode(root);
		
		final ValidationReport report = validationService.validateNexusTree(treeFile);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(4));
		assertThat(report.getErrors(), hasSize(4));
	}

	@Test
	public void testValidateFile() throws Exception {
		final NXroot root = createNexusRoot();
		
		final String filePath = testDir + "test-" + UUID.randomUUID().toString() + ".nxs";
		final TreeFile treeFile = NexusNodeFactory.createTreeFile(filePath);
		treeFile.setGroupNode(root);
		NexusTestUtils.saveNexusFile(treeFile);
		
		final ValidationReport report = validationService.validateNexusFile(filePath);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(4));
		assertThat(report.getErrors(), hasSize(4));
	}

}
