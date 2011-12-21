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

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

/**
 * Project a 2D dataset from image coordinates to a plane (through origin) in q-space
 * <p>
 * q is the scattering wave-vector = difference of incident wave-vector
 * and final wave-vector
 */
public class ProjectToQSpacePlane implements DatasetToDatasetFunction {
	private QSpace qspace;
	private Vector3d row;
	private Vector3d col;
	private double rdel;
	private double cdel;
	private int roff; // row offset
	private int coff; // column offset
	private int[] pshape; // shape of projected dataset
	private AbstractDataset image; // projected dataset
	private AbstractDataset count; // tally dataset - counts number of datasets have added pixels

	/**
	 * Set up projection to plane in q-space
	 * @param qNormal normal to projection plane
	 * @param qRow vector to describe direction of a row
	 * @param qDeltas array of pixel sizes in q-space
	 * @param rowLimits array containing start and (exclusive) stop values of row index
	 * @param colLimits array containing start and (exclusive) stop values of columns index
	 */
	public ProjectToQSpacePlane(Vector3d qNormal, Vector3d qRow, double[] qDeltas, int[] rowLimits, int[] colLimits) {
		qNormal.normalize();
		row = qRow;
		row.normalize();
		col = new Vector3d();
		col.cross(qNormal, col);
		col.normalize();
		rdel = qDeltas[0];
		cdel = qDeltas[1];
		pshape = new int[2];
		pshape[0] = rowLimits[1] - rowLimits[0];
		pshape[1] = colLimits[1] - colLimits[0];
		roff = rowLimits[0];
		coff = colLimits[0];
	}

	/**
	 * 
	 */
	public void createDataset(int dType) {
		image = AbstractDataset.zeros(pshape, dType);
		count = AbstractDataset.zeros(pshape, AbstractDataset.INT16);
	}

	/**
	 * 
	 */
	public void setQSpace(QSpace qSpace) {
		qspace = qSpace;
	}

	/**
	 * 
	 */
	public void clearDataset() {
		image.fill(0);
		count.fill(0);
	}

	/**
	 * @param datasets
	 *            input 2D dataset
	 * @return one 2D dataset
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			AbstractDataset ds = DatasetUtils.convertToAbstractDataset(ids);
			// check if input is 2D
			int[] s = ds.getShape();
			if (s.length != 2)
				return null;

			// find extent of projected dataset
			int[] pos = new int[2];
			int[] start = pshape.clone();
			int[] stop = new int[2];

			qToPixel(qspace.qFromPixelPosition(0, 0), pos);
			adjustBoundingBox(start, stop, pos);
			qToPixel(qspace.qFromPixelPosition(s[0], 0), pos);
			adjustBoundingBox(start, stop, pos);
			qToPixel(qspace.qFromPixelPosition(0, s[1]), pos);
			adjustBoundingBox(start, stop, pos);
			qToPixel(qspace.qFromPixelPosition(s[0], s[1]), pos);
			adjustBoundingBox(start, stop, pos);
			stop[0]++;
			stop[1]++;

			// iterate over bounding box in project dataset
			//     find image point
			//     calculate interpolated value
			//     store running average
			// (could have done this using a slice iterator)
			Vector3d qy = new Vector3d();
			Vector3d q = new Vector3d();
			Vector3d t = new Vector3d();
			double delta, value;
			int i = 0, n;
			for (int y = start[0]; y < stop[0]; y++) {
				qy.scale((y + roff) * cdel, col);
				for (int x = start[1]; x < stop[1]; x++) {
					q.scaleAdd((x + coff) * rdel, row, qy);
					qspace.pixelPosition(q, t);
					n = (int) (count.getElementLongAbs(i) + 1);
					value = image.getElementDoubleAbs(i);
					delta = Maths.getBilinear(ds, t.y, t.x) - value;
					image.setObjectAbs(i, value + (delta / n));
					count.setObjectAbs(i, n);
					i++;
				}
			}

			result.add(image);
			result.add(count);
		}
		return result;
	}

	/**
	 * Convert from q to a pixel coordinate (discretization)
	 * @param q
	 * @param pos (to be used for a dataset so is row-major: x->2, y->1)
	 */
	private void qToPixel(final Vector3d q, int[] pos) {
		pos[0] = -1;
		pos[1] = -1;

		final int x = (int) Math.floor(q.dot(row)/rdel) - roff;
		pos[1] = (x < 0 || x > pshape[1]) ? -1: x;

		final int y = (int) Math.floor(q.dot(col)/cdel) - coff;
		pos[0] = (y < 0 || y > pshape[0]) ? -1: y;
	}

	private void adjustBoundingBox(int[] start, int[] stop, int[] pos) {
		if (pos[0] >= 0) {
			if (pos[0] < start[0])
				start[0] = pos[0];
			if (pos[0] > stop[0])
				stop[0] = pos[0];
		}
		if (pos[1] >= 0) {
			if (pos[1] < start[1])
				start[1] = pos[1];
			if (pos[1] > stop[1])
				stop[1] = pos[1];
		}
	}
}
