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

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

/**
 * Map a 2D dataset from image coordinates to Miller space
 */
public class MapToMillerSpace implements DatasetToDatasetFunction {
	private double hmax; // max Miller index value
	private double hdel; // spacing between voxels in Miller space
	private int mlen;    // number of voxel sides in cube
	private MillerSpace mspace;
	private QSpace qspace;
	private Dataset newmap;
	private int[] mshape;

	/**
	 * Set up mapping to Miller space
	 * @param mSpace is Miller space mapping
	 * @param qSpace is q-space mapping
	 * @param mMax is maximum Miller index
	 * @param mDelta is voxel side length
	 */
	public MapToMillerSpace(MillerSpace mSpace, QSpace qSpace, int mMax, double mDelta) {
		mspace = mSpace;
		qspace = qSpace;
		hdel = mDelta;
		hmax = mMax;
		mlen = (int) ((2*mMax)/mDelta + 1);
		mshape = new int[3];
		mshape[0] = mshape[1] = mshape[2] = mlen;
	}

	/**
	 * 
	 */
	public void createDataset(int dType) {
		newmap = DatasetFactory.zeros(mshape, dType);
	}

	/**
	 * 
	 */
	public void clearDataset() {
		newmap.fill(0);
	}

	/**
	 * 
	 */
	public void setDetectorOrientation(Matrix3d orientation) {
		qspace.getDetectorProperties().setOrientation(orientation);
	}

	/**
	 * @param datasets
	 *            input 2D dataset
	 * @return one 3D dataset
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

		for (IDataset ids : datasets) {
			Dataset ds = DatasetUtils.convertToDataset(ids);
			// check if input is 2D
			int[] s = ds.getShape();
			if (s.length != 2)
				return null;

			// how does voxel size map to pixel size?
			// h = -hmax, -hmax+hdel, ..., hmax-hdel, hmax
			// algorithm:
			// iterate over image pixels
			// map pixel coords to Miller space
			// find voxel coords and store
			// map back from Miller space to projected image coords
			// put interpolated pixel value in voxel
			// 
			int[] hpos = new int[3];
			Vector3d p = new Vector3d(); // position of pixel
			Vector3d t = new Vector3d(); // temporary
			Vector3d q = new Vector3d();
			Vector3d h = new Vector3d();
			Vector3d dh = new Vector3d();
			double value;
			for (int y = 0; y < s[0]; y++) {
				for (int x = 0; x < s[1]; x++) {
					qspace.qFromPixelPosition(x, y, q);
					mspace.h(q, null, h);
					if (!hToVoxel(h, hpos))
						continue;

					mspace.q(h, q);
					qspace.pixelPosition(q, p, t);
					value = Maths.interpolate(ds, t.y, t.x);

					// Steve Collin's algorithm implemented as first attempt
					// Assumes a pixel maps to a curvilinear patch that is
					// not bigger than a voxel
					hFromVoxel(dh, hpos);
					dh.sub(h, dh);
					spreadValue(dh, hpos, value);
				}
			}
		}
		result.add(newmap);
		return result;
	}

	private boolean hToVoxel(final Vector3d h, int[] pos) {
		if (Math.abs(h.x) > hmax || Math.abs(h.y) > hmax || Math.abs(h.z)> hmax)
			return false;
		pos[0] = (int) Math.floor(h.z/hdel + hmax);
		pos[1] = (int) Math.floor(h.y/hdel + hmax);
		pos[2] = (int) Math.floor(h.x/hdel + hmax);
		return true;
	}

	private void hFromVoxel(final Vector3d h, final int[] pos) {
		h.x = (pos[0] - hmax)*hdel;
		h.y = (pos[1] - hmax)*hdel;
		h.z = (pos[2] - hmax)*hdel;
	}

	/**
	 * Spread the value over nearest voxels on positive octant
	 * 
	 * The value is shared with weighting of inverse distance to centre of voxels
	 * @param dh
	 * @param pos
	 * @param value
	 */
	private void spreadValue(final Vector3d dh, final int[] pos, final double value) {
		int[] lpos = pos.clone();

		final double[] weights = new double[8];
		double old, f, tx, ty, tz;

		final double sx = dh.x*dh.x;
		final double sy = dh.y*dh.y;
		final double sz = dh.z*dh.z;

		tx = hdel - dh.x;
		tx *= tx;
		ty = hdel - dh.y;
		ty *= ty;
		tz = hdel - dh.z;
		tz *= tz;

		if (lpos[0] == mlen) { // corner, face and edge cases
			if (lpos[1] == mlen) {
				if (lpos[2] == mlen) {
					old = newmap.getDouble(lpos);
					newmap.set(old + value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sz);
					weights[1] = 1./Math.sqrt(tz);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);					
				}
			} else {
				if (lpos[2] == mlen) {
					weights[0] = 1./Math.sqrt(sy);
					weights[1] = 1./Math.sqrt(ty);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sy + sz);
					weights[1] = 1./Math.sqrt(ty + sz);
					weights[2] = 1./Math.sqrt(sy + tz);
					weights[3] = 1./Math.sqrt(ty + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[1]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
				}				
			}
		} else {
			if (lpos[1] == mlen) {
				if (lpos[2] == mlen) {
					weights[0] = 1./Math.sqrt(sx);
					weights[1] = 1./Math.sqrt(tx);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sx + sz);
					weights[1] = 1./Math.sqrt(tx + sz);
					weights[2] = 1./Math.sqrt(sx + tz);
					weights[3] = 1./Math.sqrt(tx + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
				}
			} else {
				if (lpos[2] == mlen) {
					weights[0] = 1./Math.sqrt(sx + sy);
					weights[1] = 1./Math.sqrt(tx + sy);
					weights[2] = 1./Math.sqrt(sx + ty);
					weights[3] = 1./Math.sqrt(tx + ty);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sx + sy + sz);
					weights[1] = 1./Math.sqrt(tx + sy + sz);
					weights[2] = 1./Math.sqrt(sx + ty + sz);
					weights[3] = 1./Math.sqrt(tx + ty + sz);

					weights[4] = 1./Math.sqrt(sx + sy + tz);
					weights[5] = 1./Math.sqrt(tx + sy + tz);
					weights[6] = 1./Math.sqrt(sx + ty + tz);
					weights[7] = 1./Math.sqrt(tx + ty + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
					lpos[1]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[4]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[5]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[6]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[7]*value, lpos);
				}
			}
		}

	}
}
