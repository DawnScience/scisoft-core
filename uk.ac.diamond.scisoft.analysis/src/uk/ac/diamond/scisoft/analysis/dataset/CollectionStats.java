/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Stats of data set lists. Used for image processing.
 */
public class CollectionStats {

	private static interface StatFunction {
		double evaluate(IDataset set);
	}

	/**
	 * Used to get a mean image from a set of images for instance.
	 * 
	 * @param sets
	 * @return mean data set of the same shape as those passed in.
	 * @throws Exception
	 */
	public static AbstractDataset mean(final List<IDataset> sets) throws Exception {
		
		return process(sets, new StatFunction() {
			@Override
			public double evaluate(IDataset set) {
				return (Double)((AbstractDataset)set).mean();
			}
		});
	}
	
	/**
	 * Used to get a median image from a set of images for instance.
	 * 
	 * @param sets
	 * @return median data set of the same shape as those passed in.
	 * @throws Exception
	 */
	public static AbstractDataset median(final List<IDataset> sets) throws Exception {
		
		return process(sets, new StatFunction() {
			@Override
			public double evaluate(IDataset set) {
				return (Double)Stats.median((AbstractDataset)set);
			}
		});
	}

	/**
	 * Used to get a median image from a set of images for instance.
	 * 
	 * @param sets
	 * @return median data set of the same shape as those passed in.
	 * @throws Exception
	 */
	private static AbstractDataset process(final List<IDataset> sets,
			                               final StatFunction   function) throws Exception {
		
		assertSize(sets);
		
        final double[] data = new double[sets.get(0).getSize()];
        for (int i = 0; i < data.length; i++) {
			final double[] pixel = new double[sets.size()];
			for (int ipix = 0; ipix < pixel.length; ipix++) {
				pixel[ipix] = ((AbstractDataset)sets.get(ipix)).getElementDoubleAbs(i);
			}
			
			final DoubleDataset dbleDs = new DoubleDataset(pixel, pixel.length);
			data[i] = function.evaluate(dbleDs);
		}
        
        return new DoubleDataset(data, sets.get(0).getShape());
	}

	private static void assertSize(final Collection<IDataset> sets) throws Exception{
		
		if (sets.size()<2) throw new Exception("You must take the median of at least two sets!");
		
		final Iterator<IDataset> it = sets.iterator();
		final int[] shape = it.next().getShape();
		while(it.hasNext()) {
			final int[] nextShape = it.next().getShape();
			if (!Arrays.equals(shape, nextShape)) throw new Exception("All data sets should be the same shape!");
		}
	}
}
