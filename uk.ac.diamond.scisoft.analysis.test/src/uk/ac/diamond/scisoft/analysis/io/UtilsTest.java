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
import java.math.BigInteger;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;

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
	public void testLeIntSigns() {
		int attempt = Utils.leIntSE(255, -1);
		assertEquals(-1, attempt, 1e-8);
		attempt = Utils.leInt(255, -1);
		assertEquals(256*255+255, attempt, 1e-8);
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

	@Test
	public void testParsing() {
		// check
		checkNumber("9223372036854775807", 9223372036854775807L);
		checkNumber("-9223372036854775808", -9223372036854775808L);
		checkNumber("4000000001", 4000000001L);
		checkNumber("-4000000001", -4000000001L);
		checkNumber("2147483648", 2147483648L);
		checkNumber("-2147483649", -2147483649L);

		checkNumber("2147483647", 2147483647);
		checkNumber("-2147483648", -2147483648);
		checkNumber("4000001", 4000001);
		checkNumber("-4000001", -4000001);
		checkNumber("32768", 32768);
		checkNumber("-32769", -32769);

		checkNumber("32767", (short) 32767);
		checkNumber("-32768", (short) -32768);
		checkNumber("4001", (short) 4001);
		checkNumber("-4001", (short) -4001);
		checkNumber("128", (short) 128);
		checkNumber("-129", (short) -129);

		checkNumber("127", (byte) 127);
		checkNumber("-128", (byte) -128);
		checkNumber("40", (byte) 40);
		checkNumber("-40", (byte) -40);

		checkNumber("9223372036854775808", 9223372036854775808.);
		checkNumber("-9223372036854775809", -9223372036854775809.);
		checkNumber("101.1", 101.1);
		checkNumber("-101.1", -101.1);
		checkNumber("301.1e16", 301.1e16);
		checkNumber("-301.1e16", -301.1e16);
		checkNumber("301.1e-16", 301.1e-16);
		checkNumber("-301.1e-16", -301.1e-16);

		String bis;
		BigInteger bi;
		bis = "123456789012345678901";
		bi = new BigInteger(bis);
		double x = bi.doubleValue();
		Assert.assertEquals("Double", bi.doubleValue(), Utils.parseValue(bis).doubleValue(), Math.abs(x)*1e-14);
		bis = "-123456789012345678901";
		bi = new BigInteger(bis);
		x = bi.doubleValue();
		Assert.assertEquals("Double", bi.doubleValue(), Utils.parseValue(bis).doubleValue(), Math.abs(x)*1e-14);
	}

	private void checkNumber(String text, Number value) {
		Number n = Utils.parseValue(text);
		if (value instanceof Long) {
			Assert.assertTrue("Long", n instanceof Long);
			long l = (Long) value;
			Assert.assertEquals("Long", l, n.longValue());
		} else if (value instanceof Integer) {
			Assert.assertTrue("Integer", n instanceof Integer);
			int i = (Integer) value;
			Assert.assertEquals("Integer", i, n.intValue());
		} else if (value instanceof Short) {
			Assert.assertTrue("Short", n instanceof Short);
			short s = (Short) value;
			Assert.assertEquals("Short", s, n.shortValue());
		} else if (value instanceof Byte) {
			Assert.assertTrue("Byte", n instanceof Byte);
			byte b = (Byte) value;
			Assert.assertEquals("Byte", b, n.byteValue());
		} else if (value instanceof Double) {
			Assert.assertTrue("Double", n instanceof Double);
			double d = (Double) value;
			Assert.assertEquals("Double", d, n.doubleValue(), 1e-14*Math.abs(d));
		}
	}

	@Test
	public void testParsingTiming() {

		Dataset a;
		Random.seed(12371);
		int LENGTH = 10240;
		a = Random.randint(-2000000000, 2000000000, new int[] {LENGTH});
		String[] intText = new String[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			intText[i] = a.getString(i);
		}
		Assert.assertEquals("Int", a.getInt(LENGTH/2), Utils.parseValue(intText[LENGTH/2]).intValue());

		a = Random.randint(-60000, 60000, new int[] {LENGTH});
		String[] shortText = new String[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			shortText[i] = a.getString(i);
		}
		Assert.assertEquals("Short", a.getShort(LENGTH/2), Utils.parseValue(shortText[LENGTH/2]).shortValue());

		a = Random.randint(-128, 128, new int[] {LENGTH});
		String[] byteText = new String[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			byteText[i] = a.getString(i);
		}
		Assert.assertEquals("Byte", a.getByte(LENGTH/2), Utils.parseValue(byteText[LENGTH/2]).byteValue());

		a = Random.rand(-1e28, 1e28, new int[] {LENGTH});
		String[] doubleText = new String[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			doubleText[i] = a.getString(i);
		}
		double x = a.getDouble(LENGTH/2);
		Assert.assertEquals("Double", x, Utils.parseValue(doubleText[LENGTH/2]).doubleValue(), 1e-7*Math.abs(x));

		// timing
		int REPEAT = 5;
		long[] times = new long[REPEAT]; // in nanoseconds

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Utils.parseValue(intText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Ints took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Utils.parseValue(shortText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Shorts took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Utils.parseValue(byteText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Bytes took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Utils.parseValue(doubleText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Doubles took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Utils.parseDouble(doubleText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Doubles took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				Double.parseDouble(doubleText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Doubles took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				oldParseValue(intText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Ints took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				oldParseValue(shortText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Shorts took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				oldParseValue(byteText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Bytes took %.2fms\n", times[0]/1e6);

		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			for (int j = 0; j < LENGTH; j++) {
				oldParseValue(doubleText[j]);
			}
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Doubles took %.2fms\n", times[0]/1e6);
	}

	public static Number oldParseValue(String text) {
 		try {
			return Byte.parseByte(text);
		} catch (NumberFormatException be) {
			try {
				return Short.parseShort(text);
			} catch (NumberFormatException se) {
				try {
					return Integer.parseInt(text);
				} catch (NumberFormatException ie) {
					try {
						return Long.parseLong(text);
					} catch (NumberFormatException le) {
						try { // nb no float as precision
							return Double.parseDouble(text);
						} catch (NumberFormatException de) {
							SRSLoader.logger.info("Value {} is not a number", text);
						}
					}
				}
			}
		}
 		return null;
 	}
}
