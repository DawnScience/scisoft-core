package org.eclipse.dawnsci.nexus.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinsertion_device;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXmonochromator;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NXuser;
import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.test.utilities.NexusTestUtils;
import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NexusValidationServiceTest {
	
	private static final String ATTR_NAME_UNITS = "units";
	private static final String UNITS_VAL_MILLIS = "mm";
	private static final String UNITS_VAL_RADIANS = "rad";
	private static final String UNITS_VAL_ELECTRON_VOLTS = "eV";
	
	private NexusValidationService validationService;
	
	private static String testDir; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testDir = TestUtils.generateDirectorynameFromClassname(NexusValidationServiceTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testDir);
		NexusTestUtils.setUpServices();
	}
	
	@Before
	public void setUp() {
		validationService = new NexusValidationServiceImpl();
		validationService.setValidateDiamond(false);
	}
	
	@After
	public void tearDown() {
		validationService = null;
	}
	
	@Test
	public void testValidateSubentry() {
		final NXsubentry subentry = NexusNodeFactory.createNXsubentry();
		subentry.setDefinitionScalar(NexusApplicationDefinition.NX_MX.toString());
		final ValidationReport report = validationService.validateSubentry(subentry);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(6));
		assertThat(report.getErrors(), hasSize(6));
	}
	
	@Test
	public void testValidateEntry_noAppDef() {
		final NXentry entry = NexusNodeFactory.createNXentry();
		final ValidationReport report = validationService.validateEntry(entry);
//		assertThat(report.isOk(), is(true));
//		assertThat(report.isError(), is(false));
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
		assertThat(report.getNumberOfEntries(), is(6));
		assertThat(report.getErrors(), hasSize(6));
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
		assertThat(report.getNumberOfEntries(), is(9));
		assertThat(report.getErrors(), hasSize(9));
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
		assertThat(report.getNumberOfEntries(), is(24));
		assertThat(report.getErrors(), hasSize(24));
	}

	@Test
	public void testValidateTree_treeFile() {
		final NXroot root = createNexusRoot();
		final TreeFile treeFile = NexusNodeFactory.createTreeFile("nexusFile.nxs");
		treeFile.setGroupNode(root);
		
		final ValidationReport report = validationService.validateNexusTree(treeFile);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(24));
		assertThat(report.getErrors(), hasSize(24));
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
		assertThat(report.getNumberOfEntries(), is(24));
		assertThat(report.getErrors(), hasSize(24));
	}
	
	@Test
	public void testValidateTree_root_diamond() {
		validationService.setValidateDiamond(true);
		
		final NXroot root = createNexusRoot();
		final ValidationReport report= validationService.validateNexusTree(root);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(30));
		assertThat(report.getErrors(), hasSize(30));
	}
	
	@Test
	public void testValidateTree_treeFile_diamond() {
		validationService.setValidateDiamond(true);
		
		final NXroot root = createNexusRoot();
		final TreeFile treeFile = NexusNodeFactory.createTreeFile("nexusFile.nxs");
		treeFile.setGroupNode(root);
		
		final ValidationReport report = validationService.validateNexusTree(treeFile);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(30));
		assertThat(report.getErrors(), hasSize(30));
	}
	
	@Test
	public void testValidateEntry_diamond() {
		validationService.setValidateDiamond(true);
		
		final NXentry entry = NexusNodeFactory.createNXentry();
		final ValidationReport report= validationService.validateEntry(entry);
		assertThat(report.isOk(), is(false));
		assertThat(report.isError(), is(true));
		assertThat(report.getNumberOfEntries(), is(6));
		assertThat(report.getErrors(), hasSize(6));
	} 
	
	@Test
	public void testValidateEntry_diamond_valid() {
		validationService.setValidateDiamond(true);
		
		final NXentry entry = NexusNodeFactory.createNXentry();
		entry.setStart_timeScalar(Date.from(Instant.now().minus(5, ChronoUnit.SECONDS)));
		entry.setEnd_timeScalar(Date.from(Instant.now()));
		entry.setProgram_nameScalar("Test");
		
		final NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		entry.setInstrument(instrument);
		
		final NXsource source = NexusNodeFactory.createNXsource();
		instrument.setSource(source);
		source.setNameScalar("Diamond Light Source");
		source.setTypeScalar("Synchrotron X-ray Source");
		source.setProbeScalar("x-ray");
		
		final NXinsertion_device insertionDevice = NexusNodeFactory.createNXinsertion_device();
		instrument.setInsertion_device(insertionDevice);
		insertionDevice.setTypeScalar("wiggler");
		insertionDevice.setGapScalar(5.43);
		insertionDevice.setAttribute(NXinsertion_device.NX_GAP, ATTR_NAME_UNITS, UNITS_VAL_MILLIS);
		insertionDevice.setTaperScalar(5.93);
		insertionDevice.setAttribute(NXinsertion_device.NX_TAPER, ATTR_NAME_UNITS, UNITS_VAL_RADIANS);
		insertionDevice.setHarmonicScalar(3l);
		
		final NXmonochromator monochromator = NexusNodeFactory.createNXmonochromator();
		
		monochromator.setEnergyScalar(12500.00);
		monochromator.setAttribute(NXmonochromator.NX_ENERGY, ATTR_NAME_UNITS, UNITS_VAL_ELECTRON_VOLTS);
		instrument.setMonochromator(monochromator);
		
		final NXuser user = NexusNodeFactory.createNXuser();
		entry.setUser("user01", user);
		user.setNameScalar("John Smith");
		user.setFacility_user_idScalar("abc12345");
		
		final NXdata data = NexusNodeFactory.createNXdata();
		entry.setData(data);
		
		final ValidationReport report= validationService.validateEntry(entry);
		assertThat(report.isOk(), is(true));
		assertThat(report.isError(), is(false));
	}

}
