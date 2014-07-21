/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Methods for slicing data using visit patterns.
 * This is Jakes algorithm moved out of the conversion API to make more use of it.
 */
public class Slicer {

	/**
	 * This method provides a way to slice over a lazydataset providing the values
	 * in each dimension for the slice using a visit pattern.
	 * 
	 * @param lz
	 * @param sliceDimensions
	 * @param visitor
	 * @throws Exception 
	 */
	public static void visitAll(ILazyDataset lz, Map<Integer, String> sliceDimensions, SliceVisitor visitor) throws Exception {
		visitAll(lz, sliceDimensions, null, visitor);
	}

	/**
	 * This method provides a way to slice over a lazydataset providing the values
	 * in each dimension for the slice using a visit pattern.
	 * 
	 * @param lz
	 * @param sliceDimensions
	 * @param nameFragment may be null
	 * @param visitor
	 * @throws Exception 
	 */
	public static void visitAll(ILazyDataset lz, Map<Integer, String> sliceDimensions, String nameFragment, SliceVisitor visitor) throws Exception {
		
		final int[] fullDims = lz.getShape();
		
		//Construct Slice String
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < fullDims.length; i++) {
			if (sliceDimensions.containsKey(i)) {
				String s = sliceDimensions.get(i);
				if (s.contains("all")) s = ":";
				sb.append(s);
				sb.append(",");
			} else {
				sb.append(":");
				sb.append(',');
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		Slice[] slices = Slice.convertFromString(sb.toString());
		
		//TODO all this horrible-ness could probably be reduced by taking an initial slice
		//view from the original lazy dataset
		
		//create array of ignored axes values
		Set<Integer> axesSet = new HashSet<Integer>();
		for (int i = 0; i < fullDims.length; i++) axesSet.add(i);
		axesSet.removeAll(sliceDimensions.keySet());
		int[] axes = new int[axesSet.size()];
		int count = 0;
		Iterator<Integer> iter = axesSet.iterator();
		while (iter.hasNext()) axes[count++] = iter.next(); 

		//determine shape of sliced dataset
		int[] slicedShape = lz.getShape().clone();
		for (int i = 0; i < slices.length; i++) {
			if (slices[i].getStop() == null && slices[i].getLength() ==-1) continue;
			slicedShape[i] = slices[i].getNumSteps();
		}

		PositionIterator pi = new PositionIterator(lz.getShape(), slices, axes);
		int[] pos = pi.getPos();

		while (pi.hasNext()) {

			int[] end = pos.clone();
			for (int i = 0; i<pos.length;i++) {
				end[i]++;
			}

			for (int i = 0; i < axes.length; i++){
				end[axes[i]] = fullDims[axes[i]];
			}

			int[] st = pos.clone();
			for (int i = 0; i < st.length;i++) st[i] = 1;

			Slice[] slice = Slice.convertToSlice(pos, end, st);
			String sliceName = Slice.createString(slice);

			Slice[] outSlice = new Slice[slices.length];
			for (int i = 0; i < slices.length; i++) {
				if (slice[i].getStop() == null && slice[i].getLength() ==-1) {
					outSlice[i] = new Slice();
				} else {
					int nSteps = slice[i].getNumSteps();
					int offset = (slice[i].getStart()-slices[i].getStart())/slices[i].getStep();
					outSlice[i] = new Slice(offset,offset+nSteps);
				}
			}

			IDataset data = lz.getSlice(slice);
			data = data.squeeze();
			data.setName((nameFragment!=null ? nameFragment : "") + " ("+ sliceName+")");
			visitor.visit(data, outSlice);
		}

	}

	
	/**
	 * This method provides a way to slice over a lazydataset providing the values
	 * in each dimension for the slice using a visit pattern. The call on to the 
	 * SliceVisitor is done in a parallel way by delegating the calling of the visit method
	 * to a thread pool.
	 * 
	 * @param lz
	 * @param sliceDimensions
	 * @param nameFragment may be null
	 * @param visitor - if used with visitAllParallel, visit should not normally throw an exception or if it does it will stop the execution but not throw back to the calling code. Instead an internal RuntimeException is thrown back to the fork/join API.
	 * @throws Exception 
	 */
	public static void visitAllParallel(ILazyDataset lz, Map<Integer, String> sliceDimensions, String nameFragment, final SliceVisitor visitor) throws Exception {

		// Just farm out each slice to a different runnable.
		final ForkJoinPool pool = new ForkJoinPool();
		
		final SliceVisitor parallel = new SliceVisitor() {

			@Override
			public void visit(final IDataset slice, final Slice... slices) throws Exception {
				
				pool.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
						    visitor.visit(slice, slices);
						} catch (Exception ne) {
							// TODO Fix me - should runtime exception really be thrown back to Fork/Join?
							throw new RuntimeException(ne.getMessage(), ne);
						}
					}
				});
			}
		};
		
		Slicer.visitAll(lz, sliceDimensions, nameFragment, parallel);
	}
}
