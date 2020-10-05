/*-
 * Copyright © 2020 Diamond Light Source Ltd.
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

package org.eclipse.dawnsci.nexus;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.SliceND;


/**
 * An {@link INexusDevice} that can be write data to its dataset at each point in the scan. This is
 * required if the device does already not write its data at each point in the scan in the method
 * that the scan algorithm calls when it is either moved to (scannables) or exposed (detectors).
 * The method called would depend on the scanning API being used.
 *
 * @param <N> the type of nexus object to be created, a sub-interface of {@link NXobject},
 *   e.g. {@link NXdetector}
 */
public interface IWritableNexusDevice<N extends NXobject> extends INexusDevice<N> {

	/**
	 * Write the given data object at the given scan slice.
	 *
	 * @param data data object to write
	 * @param scanSlice scan slice specifying where in the dataset to write to.
	 * @throws NexusException if the position could not be written for any reason
	 */
	public void writePosition(Object data, SliceND scanSlice) throws NexusException;

	/**
	 * Called at the end of a scan. The implementation should use this to clear up any
	 * state, such as closing any files and releasing any cached datasets so that this
	 * device can be used in subsequent scans.
	 *
	 * @throws NexusException if an error occurred for any reason
	 */
	public void scanEnd() throws NexusException;

	/**
	 * A convenience method useful for implementing classes to write a value to a dataset at the position in
	 * the scan represented by the given {@link SliceND} scanSlice. Call this from {@link #writePosition(Object, SliceND)}
	 * with the {@link SliceND} passed in.
	 * @param toWrite the dataset to write to
	 * @param data the data to write, can be any object or already a dataset
	 * @param scanSlice specifying where to write to in the scan
	 * @throws DatasetException if an error occurs writing to the dataset
	 */
	public static void writeDataset(ILazyWriteableDataset toWrite, Object data, SliceND scanSlice) throws DatasetException {
		final Dataset dataset = data instanceof Dataset ? (Dataset) data : DatasetFactory.createFromObject(data);

		final SliceND sliceND;
		if (dataset.getRank() == 0) {
			sliceND = scanSlice;
		} else {
			// append zeros to the start array, and the dataset shape to the stop array
			final int[] dataShape = dataset.getShape();
			final int[] start = IntStream.concat(Arrays.stream(scanSlice.getStart()),
					Collections.nCopies(dataShape.length, 0).stream().mapToInt(Integer::valueOf)).toArray();
			final int[] stop = IntStream.concat(Arrays.stream(scanSlice.getStop()), Arrays.stream(dataShape)).toArray();
			sliceND = new SliceND(toWrite.getShape(), toWrite.getMaxShape(), start, stop, null);
		}

		toWrite.setSlice(null, dataset, sliceND);
	}

}