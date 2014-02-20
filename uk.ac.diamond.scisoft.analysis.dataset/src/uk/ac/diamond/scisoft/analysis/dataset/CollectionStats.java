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
		double evaluate(AbstractDataset set);
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
			public double evaluate(AbstractDataset set) {
				return (Double)set.mean();
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
			public double evaluate(AbstractDataset set) {
				return (Double)Stats.median(set);
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
		
		int[] shape = assertShapes(sets);
		final DoubleDataset result = new DoubleDataset(shape);
        final double[] rData = result.getData();
        final IndexIterator iter = new PositionIterator(shape);
        final int[] pos = iter.getPos();

        final int len = sets.size();
		final DoubleDataset pixel = new DoubleDataset(len);
		final double[] pData = pixel.getData();
        for (int i = 0; iter.hasNext(); i++) {
			for (int ipix = 0; ipix < len; ipix++) {
				pData[ipix] = sets.get(ipix).getDouble(pos);
			}
			pixel.setDirty();
			rData[i] = function.evaluate(pixel);
		}
        
        return result;
	}

	private static int[] assertShapes(final Collection<IDataset> sets) throws Exception{
		
		if (sets.size()<2) throw new Exception("You must take the median of at least two sets!");
		
		final Iterator<IDataset> it = sets.iterator();
		final int[] shape = it.next().getShape();
		while (it.hasNext()) {
			IDataset d = it.next();
			final int[] nextShape = d.getShape();
			if (!Arrays.equals(shape, nextShape)) throw new Exception("All data sets should be the same shape!");
		}
		return shape;
	}
}
