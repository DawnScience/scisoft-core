/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

public class HDF5LazyLoader implements ILazyLoader, Serializable {
	public static final long serialVersionUID = 5057544213374303912L;

	private String host;
	private String filePath;
	private String nodePath;
	private int[] trueShape;
	private int dtype;
	private int isize;
	private boolean extendUnsigned;
	private String name;

	public HDF5LazyLoader(String hostname, String filename, String node, String name, int[] trueShape2, int isize2, int dtype2,
			boolean extendUnsigned2) {
		host = hostname;
		filePath = filename;
		nodePath = node;
		this.name = name;
		trueShape = trueShape2;
		isize = isize2;
		dtype = dtype2;
		extendUnsigned = extendUnsigned2;
	}

	@Override
	public boolean isFileReadable() {
		try {
			if (host != null && host.length() > 0 && !host.equals(InetAddress.getLocalHost().getHostName()))
				return false;
		} catch (UnknownHostException e) {
			HDF5Loader.logger.warn("Problem finding local host so ignoring check", e);
		}
		return new File(filePath).canRead();
	}

	@Override
	public String toString() {
		return filePath + ":" + nodePath;
	}

	@Override
	public Dataset getDataset(IMonitor mon, SliceND slice) throws ScanFileHolderException {
		int[] lstart = slice.getStart();
		int[] lstep  = slice.getStep();
		int[] newShape = slice.getShape();
		int[] shape = slice.getSourceShape();
		final int rank = shape.length;

		Dataset d = null;
		try {
			if (!Arrays.equals(trueShape, shape)) {
				final int trank = trueShape.length;
				int[] tstart = new int[trank];
				int[] tsize = new int[trank];
				int[] tstep = new int[trank];

				if (rank > trank) { // shape was extended (from left) then need to translate to true slice
					int j = 0;
					for (int i = 0; i < trank; i++) {
						if (trueShape[i] == 1) {
							tstart[i] = 0;
							tsize[i] = 1;
							tstep[i] = 1;
						} else {
							while (shape[j] == 1 && (rank - j) > (trank - i))
								j++;

							tstart[i] = lstart[j];
							tsize[i] = newShape[j];
							tstep[i] = lstep[j];
							j++;
						}
					}
				} else { // shape was squeezed (and could have been extended again) then need to translate to true slice
					int j = 0;
					while (shape[j] == 1 && j < rank)
						j++;

					for (int i = 0; i < trank; i++) {
						if (trueShape[i] == 1) {
							tstart[i] = 0;
							tsize[i] = 1;
							tstep[i] = 1;
						} else {
							tstart[i] = lstart[j];
							tsize[i] = newShape[j];
							tstep[i] = lstep[j];
							j++;
						}
					}
				}

				d = HDF5Loader.loadData(filePath, nodePath, tstart, tsize, tstep, dtype, isize, extendUnsigned);
				d.setShape(newShape); // squeeze shape back
			} else {
				d = HDF5Loader.loadData(filePath, nodePath, lstart, newShape, lstep, dtype, isize, extendUnsigned);
			}
			if (d != null) {
				d.setName(name);
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem loading dataset", e);
		}
		return d;
	}
}
