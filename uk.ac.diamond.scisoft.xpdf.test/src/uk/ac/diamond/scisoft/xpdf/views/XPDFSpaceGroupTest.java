/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class XPDFSpaceGroupTest {

	static final private int p1_number = 1, fm_3m_number = 225;
	private XPDFSpaceGroup p1, fm_3m;

	@Before
	public void setUp() throws Exception {
		p1 = XPDFSpaceGroup.get(p1_number);
		fm_3m = XPDFSpaceGroup.get(fm_3m_number);
		
	}

	@Test
	public void testGetInt() {
		assertEquals(p1, XPDFSpaceGroup.get(p1_number));
		assertEquals(fm_3m, XPDFSpaceGroup.get(fm_3m_number));
	}

	@Test
	public void testGetString() {
		assertEquals(p1, XPDFSpaceGroup.get("P 1"));
		// Long name
		assertEquals(fm_3m, XPDFSpaceGroup.get("F 4/m 3̅ 2/m"));
		// short name
		assertEquals(fm_3m, XPDFSpaceGroup.get("F m -3 m"));
		// mangled name, but should still work
		assertEquals(fm_3m, XPDFSpaceGroup.get("Fm3m"));

	}

	@Test
	public void testGetNumber() {
		assertEquals(p1_number, p1.getNumber());
		assertEquals(fm_3m_number, fm_3m.getNumber());
	}

	@Test
	public void testGetName() {
		assertEquals("P 1", p1.getName());
		assertEquals("F 4/m 3̅ 2/m", fm_3m.getName());
	}

	@Test
	public void testGetShortName() {
		assertEquals("P1", p1.getShortName());
		assertEquals("Fm-3m", fm_3m.getShortName());
	}

	@Test
	public void testGetSystem() {
		assertEquals(CrystalSystem.get(0), p1.getSystem());
		assertEquals(CrystalSystem.get(6), fm_3m.getSystem());
	}

	@Test
	public void testAsRhombohedral() {
		assertEquals(p1, p1.asRhombohedral());
		assertEquals(XPDFSpaceGroup.get(231), XPDFSpaceGroup.get(146).asRhombohedral());
	}

	@Test
	public void testAsHexagonal() {
		assertEquals(p1, p1.asHexagonal());
		assertEquals(XPDFSpaceGroup.get(146), XPDFSpaceGroup.get(231).asHexagonal());
	}

	@Test
	public void testHasRhombohedral() {
		assertFalse(p1.hasRhombohedral());
		assertTrue(XPDFSpaceGroup.get(146).hasRhombohedral());
	}

	@Test
	public void testIsRhombohedral() {
		assertFalse(p1.isRhombohedral());
		assertFalse(XPDFSpaceGroup.get(146).isRhombohedral());
		assertTrue(XPDFSpaceGroup.get(231).isRhombohedral());
	}

	@Test
	public void testGetNWyckoffLetters() {
		assertEquals(1, p1.getNWyckoffLetters());
		assertEquals(12, fm_3m.getNWyckoffLetters());
	}

	@Test
	public void testGetSiteMultiplicity() {
		assertEquals(1, p1.getSiteMultiplicity("a"));
		assertEquals(4, fm_3m.getSiteMultiplicity("a"));
		assertEquals(192, fm_3m.getSiteMultiplicity("l"));
	}

}
