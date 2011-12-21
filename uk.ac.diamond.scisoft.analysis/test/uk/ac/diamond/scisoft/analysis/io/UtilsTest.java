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
