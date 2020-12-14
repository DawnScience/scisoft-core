package org.eclipse.dawnsci.nexus.validation;

import static org.eclipse.dawnsci.nexus.validation.AbstractNexusValidator.ATTRIBUTE_NAME_UNITS;

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
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Random;
import org.junit.Test;

public class TomoValidatorTest {
	
	private static final int NUM_FRAMES = 5;
	private static final int[] DETECTOR_DATA_DIMENSIONS = { NUM_FRAMES, 10, 10 };
	
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
		detector.setAttribute(NXdetector.NX_DISTANCE, ATTRIBUTE_NAME_UNITS, "mm");
		
		final NXsample sample = NexusNodeFactory.createNXsample();
		entry.setSample(sample);
		sample.setNameScalar("my sample");
		sample.setRotation_angle(DatasetFactory.createRange(0.0, 10.0, 10.0 / NUM_FRAMES));
		sample.setAttribute(NXsample.NX_ROTATION_ANGLE, ATTRIBUTE_NAME_UNITS, "rad"); 
		
		final NXmonitor controlMonitor = NexusNodeFactory.createNXmonitor();
		entry.setMonitor("control", controlMonitor);
		controlMonitor.setData(Random.rand(NUM_FRAMES));
		
		final NXdata data = NexusNodeFactory.createNXdata();
		entry.setData(data);
		data.addDataNode(NXdata.NX_DATA, detector.getDataNode(NXdetector.NX_DATA));
		data.addDataNode(NXsample.NX_ROTATION_ANGLE, sample.getDataNode(NXsample.NX_ROTATION_ANGLE));
		data.addDataNode("image_key", detector.getDataNode("image_key"));
		
		return entry;
	}
	
	private void validate(NXentry entry) throws NexusValidationException {
		final NexusApplicationValidator validator = new NXtomoValidator();
		validator.validate(entry);
	}

	@Test
	public void testValidateOk() throws Exception {
		final NXentry entry = createNexusTree();
		validate(entry);
	}
	
	@Test(expected=NexusValidationException.class)
	public void testValidateRequiredGroupNotPresent() throws Exception {
		final NXentry entry = createNexusTree();
		entry.removeGroupNode(entry.getSample());
		validate(entry);
	}
	
}
