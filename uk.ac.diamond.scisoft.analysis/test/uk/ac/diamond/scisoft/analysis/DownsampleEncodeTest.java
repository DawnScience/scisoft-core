/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;

public class DownsampleEncodeTest {

	@Test
	public void testValidDecodings() throws Exception {
		
		testDecodeEncode("MEAN:1",         DownsampleMode.MEAN, new int[]{1});
		testDecodeEncode("MEAN:5",         DownsampleMode.MEAN, new int[]{5});
		testDecodeEncode("MEAN:1x1x1x1x1", DownsampleMode.MEAN, new int[]{1,1,1,1,1});
		testDecodeEncode("MEAN:1x2x3x4x5", DownsampleMode.MEAN, new int[]{1,2,3,4,5});
		testDecodeEncode("POINT:1x2x3x4x5",DownsampleMode.POINT, new int[]{1,2,3,4,5});
		testDecodeEncode("MAXIMUM:1",      DownsampleMode.MAXIMUM, new int[]{1});
		testDecodeEncode("POINT:1",        DownsampleMode.POINT, new int[]{1});
	}
	
	@Test
	public void testInvalidDecodings() throws Exception {
		
		testInvalidDecode("1");
		testInvalidDecode("2x2");
		testInvalidDecode(":2x2");
		testInvalidDecode("MEAN:");
		testInvalidDecode("MEAN:1xF");
		testInvalidDecode("MEAN:1x2i");
		testInvalidDecode("MEAN:1x1.1");
		testInvalidDecode("MEAN:1.1");
		testInvalidDecode("POINT:MEAN");
		testInvalidDecode("POINT:POINT");
	}


	private void testInvalidDecode(String invalid)  throws Exception {
		try {
			Downsample d = Downsample.decode(invalid);
			if (d==null) throw new Exception("Unexpected decode to null!"); 
		} catch (Exception expected) {
			return;
		}
		throw new Exception(invalid +" is not a valid downsample string and should have thrown an exception!");
	}

	private void testDecodeEncode(String encoded, DownsampleMode mode, int[] shape) throws Exception {
		
		Downsample d = Downsample.decode(encoded);
		if (d.getMode()!=mode) throw new Exception("Incorrect mode parsed!");
		if (!Arrays.equals(d.getBshape(), shape)) throw new Exception("Incorrect shape parsed!");
		
		String e = Downsample.encode(d);
		if (!e.equals(encoded)) throw new Exception(e+" does not equal "+encoded);
	}
}
