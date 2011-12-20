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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.ArrayList;
import java.util.List;


/**
 * Signal processing class
 */
public class Signal {

	private static int[] paddedShape(final int[] ashape, final int[] bshape, final int[] axes) {
		int[] s = ashape.clone();
		if (axes == null) {
			 // pad all axes
			for (int i = 0; i < s.length; i++) {
				s[i] += bshape[i] - 1; // pad 
			}
		} else {
			 // pad chosen axes
			for (int i = 0; i < s.length; i++) {
				int j = 0;
				for (; j < axes.length; j++) {
					if (i == axes[j])
						break;
				}
				if (j < axes.length) {
					s[i] += bshape[i] - 1;
				}
			}
		}
		return s;
	}

	/**
	 * Perform a linear convolution of two input datasets 
	 * @param f
	 * @param g
	 * @param axes
	 * @return linear convolution
	 */
	public static AbstractDataset convolve(final AbstractDataset f, final AbstractDataset g, final int[] axes) {
		// compute using circular (DFT) algorithm
		// to get a linear version, need to pad out axes to f-axes + g-axes - 1 before DFTs
		
		if(f.shape.length != g.shape.length) {
			f.checkCompatibility(g);
		}
		
		AbstractDataset c = null, d = null;
		int[] s = paddedShape(f.shape, g.shape, axes);
		c = FFT.fftn(f, s, axes);
		d = FFT.fftn(g, s, axes);
		c = Maths.multiply(c, d);

		AbstractDataset conv = FFT.ifftn(c, s, axes);
		if (f.isComplex() || g.isComplex())
			return conv;
		return conv.real();
	}

	/**
	 * Perform a linear auto-correlation on a dataset
	 * @param f
	 * @param axes
	 * @return linear auto-correlation
	 */
	public static AbstractDataset correlate(final AbstractDataset f, final int[] axes) {
		AbstractDataset c = null;
		int[] s = paddedShape(f.shape, f.shape, axes);
		c = FFT.fftn(f, s, axes);
		c = Maths.multiply(c, Maths.conjugate(c));

		AbstractDataset corr = FFT.ifftn(c, s, axes);
		if (f.isComplex())
			return corr;
		return corr.real();
	}

	/**
	 * Perform a linear cross-correlation on two input datasets
	 * @param f
	 * @param g
	 * @param axes
	 * @return linear cross-correlation
	 */
	public static AbstractDataset correlate(final AbstractDataset f, final AbstractDataset g, final int[] axes) {
		AbstractDataset c = null, d = null;
		int[] s = paddedShape(f.shape, g.shape, axes);
		c = FFT.fftn(f, s, axes);
		d = FFT.fftn(g, s, axes);
		c = Maths.multiply(c, Maths.conjugate(d));

		AbstractDataset corr = FFT.ifftn(c, s, axes);
		if (f.isComplex() || g.isComplex())
			return corr;
		return corr.real();
	}

	/**
	 * Perform a linear phase cross-correlation on two input datasets
	 * 
	 * The inverse of the normalized cross-power spectrum is {@code IFFT(F/G)}
	 * 
	 * @param f
	 * @param g
	 * @param axes
	 * @param includeInverse 
	 * @return linear phase cross-correlation and inverse of the normalized cross-power spectrum
	 */
	public static List<AbstractDataset> phaseCorrelate(final AbstractDataset f, final AbstractDataset g, final int[] axes, boolean includeInverse) {
		AbstractDataset c = null, d = null;
		int[] s = paddedShape(f.shape, g.shape, axes);
		c = FFT.fftn(f, s, axes);
		d = FFT.fftn(g, s, axes);
		c.idivide(d);

		AbstractDataset corr;
		ArrayList<AbstractDataset> results = new ArrayList<AbstractDataset>();

		d = Maths.phaseAsComplexNumber(c, true);

		corr = FFT.ifftn(d, s, axes);
		if (f.isComplex() || g.isComplex())
			results.add(corr);
		else
			results.add(corr.real());

		if (includeInverse) {
			corr = FFT.ifftn(c, s, axes);
			if (f.isComplex() || g.isComplex())
				results.add(corr);
			else
				results.add(corr.real());
		}

		return results;
	}

}
