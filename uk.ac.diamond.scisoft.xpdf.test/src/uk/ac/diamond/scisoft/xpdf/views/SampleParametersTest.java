/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.builder.impl.DefaultNexusFileBuilder;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;

/**
 * A unit test class for the XPDFSampleParameters class, a package private 
 * class in the XPDF views package  
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class SampleParametersTest {

	private XPDFSampleParameters sample;
	private XPDFSampleParameters cap;
	@Before
	public void setUp() {
		sample = new XPDFSampleParameters(true);
		// ceria, with a hint of beryllium
		sample.addPhase(SampleTestData.createTestPhase("ceria"), 0.95);
		// beryllium needs atoms added to it
		XPDFPhase beryllium = SampleTestData.createTestPhase("beryllium");
		beryllium.addAtom(new XPDFAtom("Be1", 4, 1.0, new double[]{0.0, 0.0, 0.0}, "a"));
		sample.addPhase(beryllium, 0.05);
		sample.setName("Contaminated ceria");
		sample.setPackingFraction(0.6);

		cap = new XPDFSampleParameters(false);

	}
	
	@Test
	public void testXPDFSampleParameters() {
		// Is it a good idea to use this test to instantiate a new object?
		// 'Cause that's what I am going to do.
		sample = new XPDFSampleParameters();
		
		testBasicFieldCreation();
	}

	private void testBasicFieldCreation() {
		assertTrue("XPDFSampleParameters was not successfully created", sample != null);
		assertTrue("Phases list not successfully created", sample.getPhases() != null);
		assertTrue("Fractions list not successfully created", sample.getPhaseWeightings() != null);
	}
	
	@Test
	public void testXPDFSampleParametersBoolean() {
		sample = new XPDFSampleParameters(true);
		testBasicFieldCreation();
		assertTrue("XPDFSampleParameters sample parameter not set correctly on construction", sample.isSample());
		sample = null;
		sample = new XPDFSampleParameters(false);
		testBasicFieldCreation();
		assertTrue("XPDFSampleParameters sample parameter not unset correctly on construction", !sample.isSample());
	}

	@Test
	public void testXPDFSampleParametersXPDFSampleParameters() {
		XPDFSampleParameters copy = new XPDFSampleParameters(sample);
		assertEquals(sample.getName(), copy.getName());
		assertEquals(sample.getPhases(), copy.getPhases());
		assertEquals(sample.getPhaseWeightings(), copy.getPhaseWeightings());
		assertEquals(sample.isSample(), copy.isSample());
		assertEquals(sample.getComposition(), copy.getComposition());
		assertEquals(sample.getDensity(), copy.getDensity(), 1e-3);
		assertEquals(sample.getPackingFraction(), copy.getPackingFraction(), 1e-3);
		assertEquals(sample.getShapeName(), copy.getShapeName());
	}

	@Test
	public void testSetGetName() {
		String newName = "Highly contaminated beryllium";
		sample.setName(newName);
		assertEquals("New name not set/got correctly", newName, sample.getName());
	}

	@Test
	public void testSetGetId() {
		int id = 1;
		sample.setId(id);
		assertEquals("New ID not set/got correctly", id, sample.getId());
	}

	@Test
	public void testSetGetPhases() {
		List<XPDFPhase> newPhases = new ArrayList<XPDFPhase>();
		newPhases.add(SampleTestData.createTestPhase("ilmenite"));
		sample.setPhases(newPhases);
		assertEquals(newPhases, sample.getPhases());
	}

	@Test
	public void testClearPhases() {
		sample.clearPhases();
		assertTrue("XPDFSampleParameters phase list not cleared", sample.getPhases().isEmpty());
	}

	@Test
	public void testAddPhaseXPDFPhase() {
		XPDFPhase rutile = SampleTestData.createTestPhase("rutile"); 
		sample.addPhase(rutile);
		assertTrue("New phase not successfully added", sample.getPhases().contains(rutile));
	}

	@Test
	public void testAddPhaseXPDFPhaseDouble() {
		XPDFPhase rutile = SampleTestData.createTestPhase("rutile"); 
		double rutileWeight = 0.1;
		sample.addPhase(rutile, rutileWeight);
		assertTrue("New weighted phase not successfully added", sample.getPhases().contains(rutile));
		assertEquals("New phase weighting not successfully retrieved", sample.getPhaseWeighting(rutile), rutileWeight, 1e-3);
	}

	@Test
	public void testSetGetPhaseWeightings() {
		List<Double> newWeightings = Arrays.asList(new Double[] {0.9, 0.1});
		sample.setPhaseWeightings(newWeightings);
		for (int i = 0; i < newWeightings.size(); i++)
			assertEquals("Phase " + i + " incorrectly weighted", newWeightings.get(i), sample.getPhaseWeightings().get(i), 1e-3);
	}

	@Test
	public void testSetGetPhaseWeighting() {
		XPDFPhase firstPhase = sample.getPhases().get(0);
		double newWeight = 0.9;
		sample.setPhaseWeighting(firstPhase, newWeight);
		assertEquals("Phase weighting not set/got correctly", newWeight, sample.getPhaseWeighting(firstPhase), 1e-3);
	}

	@Test
	public void testSetIsSample() {
		sample.setAsContainer();
		sample.setAsSample();
		assertTrue("Not successfully set as a sample", sample.isSample());
		
	}

	@Test
	public void testSetAsContainer() {
		sample.setAsContainer();
		assertFalse("Not successfully set as a container", sample.isSample());
	}

	@Test
	public void testSetGetSubstance() {
		// For samples/containers without phases
		XPDFSubstance quartz = new XPDFSubstance("SiO2", "SiO2", 1.65, 1.0);
		cap.setSubstance(quartz);
		assertEquals("Substance not set/got correctly", quartz.getMaterialName(), cap.getSubstance().getMaterialName());
		
	}

	@Test
	public void testSetGetComposition() {
		String silica = "SiO2";
		cap.setComposition(silica);
		String silicaHall = "O₂Si";
		assertEquals("Composition not set/got correctly", silicaHall, cap.getComposition());
	}

	@Test
	public void testSetGetDensity() {
		testSetGetComposition();
		double silicaDensity = 1.65;
		cap.setDensity(silicaDensity);
		assertEquals("Density not set/got correctly", silicaDensity, cap.getDensity(), 1e-3);
	}

	@Test
	public void testSetGetPackingFraction() {
		testSetGetComposition();
		double silicaPack = 1.0;
		cap.setPackingFraction(silicaPack);
		assertEquals("Packing fraction not set/got correctly", silicaPack, cap.getPackingFraction(), 1e-3);
	}

//	@Test
	public void testGetMu() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGetShapeName() {
		testSetGetComposition();
		String cyl = "Cylinder", plat = "Plate";
		for (String shape : new String[] {cyl, plat}) {
			cap.setShape(shape);
			assertEquals("Shape not set/got correctly", shape, cap.getShapeName());
		}
		// check set by container
		sample.setShape(cyl);
		sample.setShape(null);
		assertEquals("Non-shape not set/got correctly", "Defined by container", sample.getShapeName());
	}

	@Test
	public void testSetGetDimensions() {
		testSetGetComposition();
		cap.setShape("cylinder");
		double[] dims = new double[] {0.15, 0.16};
		cap.setDimensions(dims);
		assertEquals("New dimensions not set/got correctly", Arrays.asList(ArrayUtils.toObject(dims)), Arrays.asList(ArrayUtils.toObject(cap.getDimensions())));
	}

	@Test
	public void testSetDimensionsDoubleDouble() {
		testSetGetDimensions();
		double newInner = 0.5, newOuter = 0.51;
		cap.setDimensions(newInner, newOuter);
		assertEquals("New dimension not set correctly", newInner, cap.getDimensions()[0], 1e-3);
		assertEquals("New dimension not set correctly", newOuter, cap.getDimensions()[1], 1e-3);
	}

//	@Test
	public void testToString() {
		int id = 1;
		cap.setId(id);
		String name = "Quartz capillary"; 
		cap.setName(name);
		String silica = "SiO2";
		cap.setComposition(silica);
		double density = 1.65;
		cap.setDensity(density);
		double pack = 0.6;
		cap.setPackingFraction(pack);
	}

	@Test
	public void testWriteNX() {
		
		String filename = "/tmp/sample.nxs";
		NexusFileBuilder builder = new DefaultNexusFileBuilder(filename);
		NXsample nxample = sample.getNXsample(builder);
		
		int nCompo = sample.getPhases().size();
		
		assertEquals("NX name incorrect", sample.getName(), nxample.getNameScalar());
		assertEquals("NX description incorrect", sample.getName() + ", " + sample.getComposition() + ", " + sample.getShapeName(), nxample.getDescriptionScalar());
		assertEquals("NX component names incorrect", new StringDataset(sample.getPhases().stream().map(a -> a.getName()).collect(Collectors.toList()).toArray(new String[nCompo])), nxample.getComponent());
		assertEquals("NX component formulae incorrect", new StringDataset(sample.getPhases().stream().map(a -> a.getComposition().getHallNotation(false)).collect(Collectors.toList()).toArray(new String[nCompo]), new int[]{nCompo, 1}), nxample.getChemical_formula()); 
		assertEquals("NX formula weight incorrect", new DoubleDataset(ArrayUtils.toPrimitive(sample.getPhases().stream().map(a -> a.getComposition().getFormulaMass()).collect(Collectors.toList()).toArray(new Double[nCompo])), new int[]{nCompo}), nxample.getDataset("chemical_formula_weight"));
		// unit cell parameters...
		assertEquals("NX unit cell volume incorrect", new DoubleDataset(ArrayUtils.toPrimitive(sample.getPhases().stream().map(a -> a.getUnitCellVolume()).collect(Collectors.toList()).toArray(new Double[nCompo])), new int[]{nCompo}), nxample.getUnit_cell_volume());
		assertEquals("NX unit cell class incorrect", new StringDataset(sample.getPhases().stream().map(a -> a.getCrystalSystem().getName()).collect(Collectors.toList()).toArray(new String[nCompo]), new int[]{nCompo}), nxample.getUnit_cell_class());
		assertEquals("NX unit cell space group incorrect", new StringDataset(sample.getPhases().stream().map(a -> a.getSpaceGroup().getNumber() + ": " + a.getSpaceGroup().getName()).collect(Collectors.toList()).toArray(new String[nCompo]), new int[]{nCompo}), nxample.getUnit_cell_group());
		assertEquals("NX theoretical densities incorrect", new DoubleDataset(ArrayUtils.toPrimitive(sample.getPhases().stream().map(a -> a.getDensity()).collect(Collectors.toList()).toArray(new Double[nCompo])), new int[]{nCompo}), nxample.getDataset("theoretical_density"));
		
	}

}
