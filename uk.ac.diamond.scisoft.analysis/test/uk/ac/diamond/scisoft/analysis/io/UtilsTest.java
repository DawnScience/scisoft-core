/*
 * Copyright © 2011 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

/**
 * Test IO utils
 *
 */
public class UtilsTest {
	static int answer = 0xdeadbeef;
	static int sLeAnswer = 0xbeef;
	static int sBeAnswer = 0xdead;
	static byte[] leAnswer = {(byte) 0xef, (byte) 0xbe, (byte) 0xad, (byte) 0xde};
	static byte[] beAnswer = {(byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef};

	/**
	 * 
	 */
	@Test
	public void testLeInt() {
		int attempt = Utils.leInt(0xef, 0xbe, 0xad, 0xde);
		assertEquals(answer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testBeInt() {
		int attempt = Utils.beInt(0xde, 0xad, 0xbe, 0xef);
		assertEquals(answer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testReadLeInt() throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(leAnswer);

		int attempt = Utils.readLeInt(is);
		assertEquals(answer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testReadBeInt()  throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(beAnswer);
		int attempt = Utils.readBeInt(is);
		assertEquals(answer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testReadLeShort()  throws Exception{
		ByteArrayInputStream is = new ByteArrayInputStream(leAnswer);
		int attempt = Utils.readLeShort(is);
		assertEquals(sLeAnswer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testReadBeShort()  throws Exception{
		ByteArrayInputStream is = new ByteArrayInputStream(beAnswer);
		int attempt = Utils.readBeShort(is);
		assertEquals(sBeAnswer, attempt, 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testWriteLeInt()  throws Exception{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Utils.writeLeInt(os, answer);
		byte[] attempt = os.toByteArray();
		assertArrayEquals(leAnswer, attempt);
	}

	/**
	 * 
	 */
	@Test
	public void testWriteBeInt()  throws Exception{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Utils.writeBeInt(os, answer);
		byte[] attempt = os.toByteArray();
		assertArrayEquals(beAnswer, attempt);
	}
}
