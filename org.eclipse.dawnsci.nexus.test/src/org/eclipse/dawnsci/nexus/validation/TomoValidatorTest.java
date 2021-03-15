package org.eclipse.dawnsci.nexus.validation;

import static org.eclipse.dawnsci.nexus.validation.AbstractNexusValidator.ATTRIBUTE_NAME_UNITS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXmonitor;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXsource;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TomoValidatorTest {
	
	private static final int NUM_FRAMES = 5;
	private static final int[] DETECTOR_DATA_DIMENSIONS = { NUM_FRAMES, 10, 10 };
	
	private NXtomoValidator validator;
	
	@Before
	public void setUp() {
		validator = new NXtomoValidator();
	}
	
	@After
	public void tearDown() {
		validator = null;
	}
	
	private NXentry createNexusTree() {
		final NXentry entry = NexusNodeFactory.createNXentry();
		entry.setTitleScalar("Tomo Entry");
		entry.setStart_timeScalar(Date.from(Instant.now().minus(5, ChronoUnit.SECONDS)));
		entry.setEnd_timeScalar(Date.from(Instant.now()));
		entry.setDefinitionScalar("NXtomo");
		
		final NXinstrument instrument = NexusNodeFactory.createNXinstrument();
		entry.setInstrument(instrument);
		
		final NXsource source = NexusNodeFactory.createNXsource();
		instrument.setSource(source);
		source.setTypeScalar("Synchrotron X-ray Source");
		source.setNameScalar("Diamond Light Source");
		source.setProbeScalar("x-ray");
		
		final NXdetector detector = NexusNodeFactory.createNXdetector();
		instrument.setDetector(detector);
		detector.setData(Random.randint(0, 255, DETECTOR_DATA_DIMENSIONS));
		detector.setAttribute(NXdetector.NX_DATA, ATTRIBUTE_NAME_UNITS, "A");
		detector.setDataset("image_key", DatasetFactory.zeros(IntegerDataset.class, NUM_FRAMES));
		detector.setDistance(DatasetFactory.zeros(DETECTOR_DATA_DIMENSIONS).iadd(0.25)); // NXdetector.nxdl.xml required 3 dimensions!
		detector.setAttribute(NXdetector.NX_DISTANCE, ATTRIBUTE_NAME_UNITS, "mm"); // unit type is NXtomo.nxdl.xml is NX_ANY
		
		final NXsample sample = NexusNodeFactory.createNXsample();
		entry.setSample(sample);
		sample.setNameScalar("my sample");
		sample.setRotation_angle(DatasetFactory.createRange(0.0, 10.0, 10.0 / NUM_FRAMES));
		sample.setAttribute(NXsample.NX_ROTATION_ANGLE, ATTRIBUTE_NAME_UNITS, "rad"); 
		
		final NXmonitor controlMonitor = NexusNodeFactory.createNXmonitor();
		entry.setMonitor("control", controlMonitor);
		controlMonitor.setData(Random.rand(NUM_FRAMES));
		controlMonitor.setAttribute(NXmonitor.NX_DATA, ATTRIBUTE_NAME_UNITS, "A"); // unit type is NXtomo.nxdl.xml is NX_ANY
		
		final NXdata data = NexusNodeFactory.createNXdata();
		entry.setData(data);
		data.addDataNode(NXdata.NX_DATA, detector.getDataNode(NXdetector.NX_DATA));
		data.addDataNode(NXsample.NX_ROTATION_ANGLE, sample.getDataNode(NXsample.NX_ROTATION_ANGLE));
		data.addDataNode("image_key", detector.getDataNode("image_key"));
		
		return entry;
	}
	
	@Test
	public void testValidate_ok() throws Exception {
		final NXentry entry = createNexusTree();
		final ValidationReport report = validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(true));
	}
	
	@Test
	public void testValidate_requiredGroupNotPresent() throws Exception {
		final NXentry entry = createNexusTree();
		entry.removeGroupNode(entry.getSample());
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_optionalGroupInvalid() throws Exception {
		final NXentry entry = createNexusTree();
		entry.getMonitor("control").removeDataNode(NXmonitor.NX_DATA);
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_unnamedGroup_alternateName() throws Exception {
		// check that the NXsource is still validated when given a different name
		final NXentry entry = createNexusTree();
		final NXinstrument instrument = entry.getInstrument();
		final NXsource source = instrument.getSource();
		source.setProbeScalar("no such enum");
		instrument.removeGroupNode(source);
		instrument.setSource("newName", source);
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_unnamedMultipleGroup() throws Exception {
		// only one instance of NXsource is allowed
		final NXentry entry = createNexusTree();
		entry.getInstrument().setSource("newSource", NexusNodeFactory.createNXsource());
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	@Ignore
	public void testValidateTransformations() throws Exception {
		// TODO: DAQ-3305 test this with NXmx application definition
	}
	
	@Test
	public void testValidate_requiredFieldNotPresent() throws Exception {
		final NXentry entry = createNexusTree();
		entry.getSample().removeDataNode(NXsample.NX_ROTATION_ANGLE);
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_optionalFieldInvalid() throws Exception {
		final NXentry entry = createNexusTree();
		final NXinstrument instrument = entry.getInstrument();
		final NXsource source = instrument.getSource();
		source.setProbeScalar("no such enum");
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldType() throws Exception {
		final NXentry entry = createNexusTree();
		entry.setTitle(DatasetFactory.createFromObject(1.0));
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldUnits_notSpecified() throws Exception {
		final NXentry entry = createNexusTree();
		final NXdetector detector = entry.getInstrument().getDetector();
		detector.getDataNode(NXdetector.NX_DISTANCE).removeAttribute(ATTRIBUTE_NAME_UNITS);
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldUnits_invalid() throws Exception {
		final NXentry entry = createNexusTree();
		final NXdetector detector = entry.getInstrument().getDetector();
		detector.setAttribute(NXdetector.NX_DISTANCE, ATTRIBUTE_NAME_UNITS, "A");
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldEnumerationValue_appDef() throws Exception {
		// where the enumeration of permitted values is defined in the application definition
		final NXentry entry = createNexusTree();
		final NXsource source = entry.getInstrument().getSource();
		source.setProbeScalar("no such enum");
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldEnumerationValue_baseClass() throws Exception {
		// where the enumeration of permitted values is defined in the base class definition
		final NXentry entry = createNexusTree();
		final NXsource source = entry.getInstrument().getSource();
		source.setTypeScalar("Unknown type");
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldDimensions_incorrectRank() throws Exception {
		final NXentry entry = createNexusTree();
		entry.getSample().setRotation_angle(DatasetFactory.zeros(NUM_FRAMES, 3));
		validator.validate(entry);	
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	public void testValidate_invalidFieldDimensions_wrongSize() throws Exception {
		final NXentry entry = createNexusTree();
		entry.getSample().setRotation_angle(DatasetFactory.zeros(NUM_FRAMES + 1));
		validator.validate(entry);
		assertThat(validator.getValidationReport().isOk(), is(false));
	}
	
	@Test
	@Ignore
	public void testAttribute_requiredNotPresent() throws Exception {
		// TODO: DAQ-3305 test this with NXmx application definition
	}
	
	@Test
	@Ignore
	public void testAttribute_invalidType() throws Exception {
		// TODO: DAQ-3305 test this with NXmx application definition
	}
	
	@Test
	@Ignore
	public void testAttribute_invalidEnumeration() throws Exception {
		// TODO: DAQ-3305 test this with NXmx application definition
	}
	
}
