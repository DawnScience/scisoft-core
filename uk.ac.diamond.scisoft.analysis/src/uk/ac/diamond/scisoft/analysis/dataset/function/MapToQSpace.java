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
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

/**
 * Map a 2D dataset from image coordinates to 3D q-space
 * 
 * q is the scattering wave-vector = difference of incident wave-vector
 * and final wave-vector
 * 
 * q-space is set up so each dimension has range [-qmax,qmax]
 */
public class MapToQSpace implements DatasetToDatasetFunction {
	private double qmax;
	private double qdel;
	private int qlen;
	private QSpace qspace;

	/**
	 * Set up mapping to q-space
	 * @param qSpace is q-space mapping
	 * @param qSize is number of points in one dimension. It should be
	 * odd to include the origin
	 * @param maxModQ is maximum value of |q|
	 */
	public MapToQSpace(QSpace qSpace, int qSize, double maxModQ) {
		qspace = qSpace;
		qlen = qSize;
		qmax = maxModQ;
		qdel = 2*qmax/qlen;
	}

	/**
	 * Set up mapping to q-space
	 * @param qSpace is q-space mapping
	 * @param qSize is number of points in one dimension
	 */
	public MapToQSpace(QSpace qSpace, int qSize) {
		this(qSpace, qSize, qSpace.maxModQ());
	}

	/**
	 * Set up mapping to q-space
	 * 
	 * This produces a grid that includes the q-space origin
	 * @param qSpace is q-space mapping
	 * @param qDel is separation of points in q
	 * @param maxModQ is maximum value of |q|
	 */
	public MapToQSpace(QSpace qSpace, double qDel, double maxModQ) {
		this(qSpace, (int) (2*Math.floor(maxModQ/qDel)+1), maxModQ);
	}

	/**
	 * @param datasets
	 *            input 2D dataset
	 * @return one 3D dataset
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		AbstractDataset inDS = DatasetUtils.convertToAbstractDataset(datasets[0]);
		// check if input is 2D
		int[] s = inDS.getShape();
		if (s.length != 2)
			return null;

		int[] os = new int[] {qlen, qlen, qlen};

		AbstractDataset newmap = AbstractDataset.zeros(os, inDS.getDtype());

		// how does voxel size map to pixel size?
		// q = -qmax, -qmax+qdel, ..., qmax-qdel, qmax
		// can qz be +ve? (depends on detector and sample orientation)
		// algorithm:
		//   iterate over image pixels
		//     map pixel coords to q-space
		//     find voxel coords and store
		//     map back from q-space to projected image coords
		//     put interpolated pixel value in voxel
		// 

		// need a 3d filling algorithm
		// choose voxels
		// want information about orientation or rotation? to build up 3d input
		// 4 2d points (8 dof) can define a 3d volume (>6 dof)?

		// pixel in image maps to curvilinear (spherical) quadrilateral surface in volume
		// recursive subdividing of quad to find least maximum subpixel size
		// find bounding box in voxels
		// add weighted bilinear interpolated value [point used is closest to centre]
		// where weighting depends on area of surface in voxel by approximating voxel
		// as a sphere and using 1 - (r/d)^2 [d is half side of voxel and r is normal distance to centre] 

		int[] qpos = new int[3];
		Vector3d t = new Vector3d(); // temporary
		double value;
		int y = 0;
		int[] minmax = new int[6];
		for (; y < s[0]-1; y++) {
			for (int x = 0; x < s[1]-1; x++) {

				double span = calcSpan(x, y, 1.0);
				vaabb(x, y, span, minmax);

				

//				qspace.qFromPixelPosition(x, y, q);
//				if (!qToVoxel(q, qpos))
//					continue;
//				qspace.pixelPosition(q, p, t);
				value = newmap.getDouble(qpos);
				value += Maths.interpolate(inDS, t.y, t.x);
				newmap.set(value, qpos);
			}
		}

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		result.add(newmap);
		return result;
	}

	/**
	 * Convert from q to a voxel coordinate (discretization)
	 * @param q
	 * @param pos (to be used for a dataset so is row-major: x->2, y->1, z->0)
	 * @return true if q is within bounds
	 */
	private boolean qToVoxel(final Vector3d q, int[] pos) {
		if (Math.abs(q.x) > qmax || Math.abs(q.y) > qmax || Math.abs(q.z)> qmax)
			return false;
		pos[0] = (int) Math.floor(q.z/qdel + qmax);
		pos[1] = (int) Math.floor(q.y/qdel + qmax);
		pos[2] = (int) Math.floor(q.x/qdel + qmax);
		return true;
	}

	/**
	 * Calculate (recursively) least size of sub-pixel that spans a voxel
	 * @param x
	 * @param y
	 * @param d span
	 * @return span
	 */
	private double calcSpan(double x, double y, double d) {
		Vector3d qa = new Vector3d();
		Vector3d qb = new Vector3d();
		Vector3d qc = new Vector3d();
		Vector3d qd = new Vector3d();

		qspace.qFromPixelPosition(x,     y,     qa);
		qspace.qFromPixelPosition(x + d, y,     qb);
		qspace.qFromPixelPosition(x,     y + d, qc);
		qspace.qFromPixelPosition(x + d, y + d, qd);
		if (isSurfaceSpanGreaterThanVoxel(qa, qb, qc, qd))
			return d;

		d /= 2; // halve size for recursion

		double[] ds = new double[4];
		ds[0] = calcSpan(x,     y,     d);
		ds[1] = calcSpan(x + d, y,     d);
		ds[2] = calcSpan(x,     y + d, d);
		ds[3] = calcSpan(x + d, y + d, d);
		Arrays.sort(ds);
		return ds[0]; // return smallest span
	}

	private boolean isSurfaceSpanGreaterThanVoxel(final Vector3d a, final Vector3d b, final Vector3d c, final Vector3d d) {
		return checkEnds(a, b) || checkEnds(b, c) || checkEnds(c, d) || checkEnds(d, a);
	}

	private boolean checkEnds(final Vector3d a, final Vector3d b) {
		return Math.abs(a.x-b.x) > qdel || Math.abs(a.y-b.y) > qdel || Math.abs(a.z-b.z) > qdel;
	}

	/**
	 * calculate axis-aligned bounding box in volume for a pixel
	 * @param minmax as pairs of integers for each axis
	 */
	private void vaabb(int x, int y, double d, int[] minmax) {
		int steps = (int) Math.ceil(1./d);

		double px = x;
		double py = y;
		Vector3d q = new Vector3d();
		qspace.qFromPixelPosition(px, py, q);
		int[] qpos = new int[3];
		qToVoxel(q, qpos);
		minmax[0] = qpos[0];
		minmax[1] = qpos[0]+1;
		minmax[2] = qpos[1];
		minmax[3] = qpos[1]+1;
		minmax[4] = qpos[2];
		minmax[5] = qpos[2]+1;
		for (int j = 0; j < steps; j++) {
			py = y + j*d;
			for (int i = 0; i < steps; i++) {
				px = x + i*d;
				qspace.qFromPixelPosition(px, py, q);
				qToVoxel(q, qpos);

				if (qpos[0] < minmax[0])
					minmax[0] = qpos[0];
				if (qpos[0] >= minmax[1])
					minmax[1] = qpos[0] + 1;
				if (qpos[1] < minmax[2])
					minmax[2] = qpos[1];
				if (qpos[1] >= minmax[3])
					minmax[3] = qpos[1] + 1;
				if (qpos[2] < minmax[4])
					minmax[4] = qpos[2];
				if (qpos[2] >= minmax[5])
					minmax[5] = qpos[2] + 1;
			}
		}
	}

//	private void qFromVoxel(final int[] pos, Vector3d q) {
//		q.x = pos[2] * qdel - qmax;
//		q.y = pos[1] * qdel - qmax;
//		q.z = pos[0] * qdel - qmax;
//	}


}
