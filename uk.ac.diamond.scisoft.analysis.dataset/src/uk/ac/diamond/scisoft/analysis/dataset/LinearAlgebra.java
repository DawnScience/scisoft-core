/*-
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
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class LinearAlgebra {

	private static final int CROSSOVERPOINT = 16; // point at which using slice iterators for inner loop is faster 

	/**
	 * Calculate the tensor dot product over given axes. This is the sum of products of elements selected
	 * from the given axes in each dataset
	 * @param a
	 * @param b
	 * @param axisa axis dimension in a to sum over (can be -ve)
	 * @param axisb axis dimension in b to sum over (can be -ve)
	 * @return tensor dot product
	 */
	public static Dataset tensorDotProduct(final Dataset a, final Dataset b, final int axisa, final int axisb) {
		// this is slower for summing lengths < ~15
		final int[] ashape = a.getShapeRef();
		final int[] bshape = b.getShapeRef();
		final int arank = ashape.length;
		final int brank = bshape.length;
		int aaxis = axisa;
		if (aaxis < 0)
			aaxis += arank;
		if (aaxis < 0 || aaxis >= arank)
			throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");

		if (ashape[aaxis] < CROSSOVERPOINT) { // faster to use position iteration
			return tensorDotProduct(a, b, new int[] {axisa}, new int[] {axisb});
		}
		int baxis = axisb;
		if (baxis < 0)
			baxis += arank;
		if (baxis < 0 || baxis >= arank)
			throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		achoice[aaxis] = false; // flag which axes not to iterate over
		bchoice[baxis] = false;

		final boolean[] notachoice = new boolean[arank];
		final boolean[] notbchoice = new boolean[brank];
		notachoice[aaxis] = true; // flag which axes to iterate over
		notbchoice[baxis] = true;

		int drank = arank + brank - 2;
		int[] dshape = new int[drank];
		int d = 0;
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
		int dtype = AbstractDataset.getBestDType(a.getDtype(), b.getDtype());
		Dataset data = DatasetFactory.zeros(dshape, dtype);

		SliceIterator ita = a.getSliceIteratorFromAxes(null, achoice);
		int l = 0;
		final int[] apos = ita.getPos();
		while (ita.hasNext()) {
			SliceIterator itb = b.getSliceIteratorFromAxes(null, bchoice);
			final int[] bpos = itb.getPos();
			while (itb.hasNext()) {
				SliceIterator itaa = a.getSliceIteratorFromAxes(apos, notachoice);
				SliceIterator itba = b.getSliceIteratorFromAxes(bpos, notbchoice);
				double sum = 0.0;
				double com = 0.0;
				while (itaa.hasNext() && itba.hasNext()) {
					final double y = a.getElementDoubleAbs(itaa.index) * b.getElementDoubleAbs(itba.index) - com;
					final double t = sum + y;
					com = (t - sum) - y;
					sum = t;
				}
				data.setObjectAbs(l++, sum);
			}
		}

		return data;
	}

	/**
	 * Calculate the tensor dot product over given axes. This is the sum of products of elements selected
	 * from the given axes in each dataset
	 * @param a
	 * @param b
	 * @param axisa axis dimensions in a to sum over (can be -ve)
	 * @param axisb axis dimensions in b to sum over (can be -ve)
	 * @return tensor dot product
	 */
	public static Dataset tensorDotProduct(final Dataset a, final Dataset b, final int[] axisa, final int[] axisb) {
		if (axisa.length != axisb.length) {
			throw new IllegalArgumentException("Numbers of summing axes must be same");
		}
		final int[] ashape = a.getShapeRef();
		final int[] bshape = b.getShapeRef();
		final int arank = ashape.length;
		final int brank = bshape.length;
		final int[] aaxes = new int[axisa.length];
		final int[] baxes = new int[axisa.length];
		for (int i = 0; i < axisa.length; i++) {
			int n;

			n = axisa[i];
			if (n < 0) n += arank;
			if (n < 0 || n >= arank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");
			aaxes[i] = n;

			n = axisb[i];
			if (n < 0) n += brank;
			if (n < 0 || n >= brank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");
			baxes[i] = n;

			if (ashape[aaxes[i]] != bshape[n])
				throw new IllegalArgumentException("Summing axes do not have matching lengths");
		}

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		for (int i = 0; i < aaxes.length; i++) { // flag which axes to iterate over
			achoice[aaxes[i]] = false;
			bchoice[baxes[i]] = false;
		}

		int drank = arank + brank - 2*aaxes.length;
		int[] dshape = new int[drank];
		int d = 0;
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
		int dtype = AbstractDataset.getBestDType(a.getDtype(), b.getDtype());
		Dataset data = DatasetFactory.zeros(dshape, dtype);

		SliceIterator ita = a.getSliceIteratorFromAxes(null, achoice);
		int l = 0;
		final int[] apos = ita.getPos();
		while (ita.hasNext()) {
			SliceIterator itb = b.getSliceIteratorFromAxes(null, bchoice);
			final int[] bpos = itb.getPos();
			while (itb.hasNext()) {
				double sum = 0.0;
				double com = 0.0;
				apos[aaxes[aaxes.length - 1]] = -1;
				bpos[baxes[aaxes.length - 1]] = -1;
				while (true) { // step through summing axes
					int e = aaxes.length - 1;
					for (; e >= 0; e--) {
						int ai = aaxes[e];
						int bi = baxes[e];

						apos[ai]++;
						bpos[bi]++;
						if (apos[ai] == ashape[ai]) {
							apos[ai] = 0;
							bpos[bi] = 0;
						} else
							break;
					}
					if (e == -1) break;
					final double y = a.getDouble(apos) * b.getDouble(bpos) - com;
					final double t = sum + y;
					com = (t - sum) - y;
					sum = t;
				}
				data.setObjectAbs(l++, sum);
			}
		}

		return data;
	}

	/**
	 * Calculate the dot product of two datasets. When <b>b</b> is a 1D dataset, the sum product over
	 * the last axis of <b>a</b> and <b>b</b> is returned. Where <b>a</b> is also a 1D dataset, a zero-rank dataset
	 * is returned. If <b>b</b> is 2D or higher, its second-to-last axis is used
	 * @param a
	 * @param b
	 * @return dot product
	 */
	public static Dataset dotProduct(Dataset a, Dataset b) {
		if (b.getRank() < 2)
			return tensorDotProduct(a, b, -1, 0);
		return tensorDotProduct(a, b, -1, -2);
	}

	/**
	 * Calculate the outer product of two datasets
	 * @param a
	 * @param b
	 * @return outer product
	 */
	public static Dataset outerProduct(Dataset a, Dataset b) {
		int[] as = a.getShapeRef();
		int[] bs = b.getShapeRef();
		int rank = as.length + bs.length;
		int[] shape = new int[rank];
		for (int i = 0; i < as.length; i++) {
			shape[i] = as[i];
		}
		for (int i = 0; i < bs.length; i++) {
			shape[as.length + i] = bs[i];
		}
		int isa = a.getElementsPerItem();
		int isb = b.getElementsPerItem();
		if (isa != 1 || isb != 1) {
			throw new UnsupportedOperationException("Compound datasets not supported");
		}
		Dataset o = DatasetFactory.zeros(shape, AbstractDataset.getBestDType(a.getDtype(), b.getDtype()));

		IndexIterator ita = a.getIterator();
		IndexIterator itb = b.getIterator();
		int j = 0;
		while (ita.hasNext()) {
			double va = a.getElementDoubleAbs(ita.index);
			while (itb.hasNext()) {
				o.setObjectAbs(j++, va * b.getElementDoubleAbs(itb.index));
			}
			itb.reset();
		}
		return o;
	}

	/**
	 * Calculate the cross product of two datasets. Datasets must be broadcastable and
	 * possess last dimensions of length 2 or 3
	 * @param a
	 * @param b
	 * @return cross product
	 */
	public static Dataset crossProduct(Dataset a, Dataset b) {
		return crossProduct(a, b, -1, -1, -1);
	}

	/**
	 * Calculate the cross product of two datasets. Datasets must be broadcastable and
	 * possess dimensions of length 2 or 3. The axis parameters can be negative to indicate
	 * dimensions from the end of their shapes
	 * @param a
	 * @param b
	 * @param axisA dimension to be used a vector (must have length of 2 or 3)
	 * @param axisB dimension to be used a vector (must have length of 2 or 3)
	 * @param axisC dimension to assign as cross-product
	 * @return cross product
	 */
	public static Dataset crossProduct(Dataset a, Dataset b, int axisA, int axisB, int axisC) {
		final int rankA = a.getRank();
		final int rankB = b.getRank();
		if (rankA == 0 || rankB == 0) {
			throw new IllegalArgumentException("Datasets must have one or more dimensions");
		}
		if (axisA < 0) {
			axisA += rankA;
		}
		if (axisA < 0 || axisA >= rankA) {
			throw new IllegalArgumentException("Axis A argument exceeds rank");
		}
		if (axisB < 0) {
			axisB += rankB;
		}
		if (axisB < 0 || axisB >= rankB) {
			throw new IllegalArgumentException("Axis B argument exceeds rank");
		}

		final int[] shapeA = a.getShape();
		final int[] shapeB = b.getShape();
		int la = shapeA[axisA];
		int lb = shapeB[axisB];
		if (Math.min(la,  lb) < 2 || Math.max(la, lb) > 3) {
			throw new IllegalArgumentException("Chosen dimension of A & B must be 2 or 3");
		}

		if (Math.max(la,  lb) == 2) {
			return crossProduct2D(a, b, axisA, axisB);
		}

		return crossProduct3D(a, b, axisA, axisB, axisC);
	}

	private static int[] removeAxisFromShape(int[] shape, int axis) {
		int[] s = new int[shape.length - 1];
		for (int i = 0, j = 0; i < shape.length; i++) {
			if (i != axis) {
				s[j++] = shape[i];
			}
		}
		return s;
	}

	private static int[] addAxisToShape(int[] shape, int axis, int length) {
		int[] s = new int[shape.length + 1];
		s[axis] = length;
		for (int i = 0, j = 0; j < s.length; j++) {
			if (j != axis) {
				s[j] = shape[i++];
			}
		}
		return s;
	}

	private static Dataset crossProduct2D(Dataset a, Dataset b, int axisA, int axisB) {
		// need to broadcast and omit given axes
		int[] shapeA = removeAxisFromShape(a.getShapeRef(), axisA);
		int[] shapeB = removeAxisFromShape(b.getShapeRef(), axisB);

		List<int[]> fullShapes = BroadcastIterator.broadcastShapes(shapeA, shapeB);

		int[] maxShape = fullShapes.get(0);
		Dataset c = DatasetFactory.zeros(maxShape, AbstractDataset.getBestDType(a.getDtype(), b.getDtype()));

		PositionIterator ita = a.getPositionIterator(axisA);
		PositionIterator itb = b.getPositionIterator(axisB);
		IndexIterator itc = c.getIterator();

		final int[] pa = ita.getPos();
		final int[] pb = itb.getPos();
		while (itc.hasNext()) {
			if (!ita.hasNext()) // TODO use broadcasting...
				ita.reset();
			if (!itb.hasNext())
				itb.reset();
			pa[axisA] = 0;
			pb[axisB] = 1;
			double cv = a.getDouble(pa) * b.getDouble(pb);
			pa[axisA] = 1;
			pb[axisB] = 0;
			cv -= a.getDouble(pa) * b.getDouble(pb);

			c.setObjectAbs(itc.index, cv);
		}
		return c;
	}
	private static Dataset crossProduct3D(Dataset a, Dataset b, int axisA, int axisB, int axisC) {
		int[] shapeA = removeAxisFromShape(a.getShapeRef(), axisA);
		int[] shapeB = removeAxisFromShape(b.getShapeRef(), axisB);

		List<int[]> fullShapes = BroadcastIterator.broadcastShapes(shapeA, shapeB);

		int[] maxShape = fullShapes.get(0);
		int rankC = maxShape.length + 1;
		if (axisC < 0) {
			axisC += rankC;
		}
		if (axisC < 0 || axisC >= rankC) {
			throw new IllegalArgumentException("Axis C argument exceeds rank");
		}
		maxShape = addAxisToShape(maxShape, axisC, 3);
		Dataset c = DatasetFactory.zeros(maxShape, AbstractDataset.getBestDType(a.getDtype(), b.getDtype()));

		PositionIterator ita = a.getPositionIterator(axisA);
		PositionIterator itb = b.getPositionIterator(axisB);
		PositionIterator itc = c.getPositionIterator(axisC);

		final int[] pa = ita.getPos();
		final int[] pb = itb.getPos();
		final int[] pc = itc.getPos();
		final int la = a.getShapeRef()[axisA];
		final int lb = b.getShapeRef()[axisB];

		if (la == 2) {
			while (itc.hasNext()) {
				if (!ita.hasNext()) // TODO use broadcasting...
					ita.reset();
				if (!itb.hasNext())
					itb.reset();
				double cv;
				pa[axisA] = 1;
				pb[axisB] = 2;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 0;
				c.set(cv, pc);

				pa[axisA] = 0;
				pb[axisB] = 2;
				cv = -a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 1;
				c.set(cv, pc);

				pa[axisA] = 0;
				pb[axisB] = 1;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pa[axisA] = 1;
				pb[axisB] = 0;
				cv -= a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 2;
				c.set(cv, pc);
			}
		} else if (lb == 2) {
			while (itc.hasNext()) {
				if (!ita.hasNext()) // TODO use broadcasting...
					ita.reset();
				if (!itb.hasNext())
					itb.reset();
				double cv;
				pa[axisA] = 2;
				pb[axisB] = 1;
				cv = -a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 0;
				c.set(cv, pc);

				pa[axisA] = 2;
				pb[axisB] = 0;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 1;
				c.set(cv, pc);

				pa[axisA] = 0;
				pb[axisB] = 1;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pa[axisA] = 1;
				pb[axisB] = 0;
				cv -= a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 2;
				c.set(cv, pc);
			}
			
		} else {
			while (itc.hasNext()) {
				if (!ita.hasNext()) // TODO use broadcasting...
					ita.reset();
				if (!itb.hasNext())
					itb.reset();
				double cv;
				pa[axisA] = 1;
				pb[axisB] = 2;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pa[axisA] = 2;
				pb[axisB] = 1;
				cv -= a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 0;
				c.set(cv, pc);

				pa[axisA] = 2;
				pb[axisB] = 0;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pa[axisA] = 0;
				pb[axisB] = 2;
				cv -= a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 1;
				c.set(cv, pc);

				pa[axisA] = 0;
				pb[axisB] = 1;
				cv = a.getDouble(pa) * b.getDouble(pb);
				pa[axisA] = 1;
				pb[axisB] = 0;
				cv -= a.getDouble(pa) * b.getDouble(pb);
				pc[axisC] = 2;
				c.set(cv, pc);
			}
		}
		return c;
	}

	/**
	 * Order value for norm
	 */
	public enum NormOrder {
		/**
		 * 2-norm for vectors and Frobenius for matrices
		 */
		DEFAULT,
		/**
		 * Frobenius (not allowed for vectors)
		 */
		FROBENIUS,
		/**
		 * Zero-order (not allowed for matrices)
		 */
		ZERO,
		/**
		 * Positive infinity
		 */
		POS_INFINITY,
		/**
		 * Negative infinity
		 */
		NEG_INFINITY;
	}

	/**
	 * @param a
	 * @return norm of dataset
	 */
	public static double norm(Dataset a) {
		return norm(a, NormOrder.DEFAULT);
	}

	/**
	 * @param a
	 * @param order
	 * @return norm of dataset
	 */
	public static double norm(Dataset a, NormOrder order) {
		int r = a.getRank();
		if (r == 1) {
			return vectorNorm(a, order);
		} else if (r == 2) {
			return matrixNorm(a, order);
		}
		throw new IllegalArgumentException("Rank of dataset must be one or two");
	}

	private static double vectorNorm(Dataset a, NormOrder order) {
		double n;
		IndexIterator it;
		switch (order) {
		case FROBENIUS:
			throw new IllegalArgumentException("Not allowed for vectors");
		case NEG_INFINITY:
		case POS_INFINITY:
			it = a.getIterator();
			if (order == NormOrder.POS_INFINITY) {
				n = Double.NEGATIVE_INFINITY;
				if (a.isComplex()) {
					while (it.hasNext()) {
						double v = ((Complex) a.getObjectAbs(it.index)).abs();
						n = Math.max(n, v);
					}
				} else {
					while (it.hasNext()) {
						double v = Math.abs(a.getElementDoubleAbs(it.index));
						n = Math.max(n, v);
					}
				}
			} else {
				n = Double.POSITIVE_INFINITY;
				if (a.isComplex()) {
					while (it.hasNext()) {
						double v = ((Complex) a.getObjectAbs(it.index)).abs();
						n = Math.min(n, v);
					}
				} else {
					while (it.hasNext()) {
						double v = Math.abs(a.getElementDoubleAbs(it.index));
						n = Math.min(n, v);
					}
				}
			}
			break;
		case ZERO:
			it = a.getIterator();
			n = 0;
			if (a.isComplex()) {
				while (it.hasNext()) {
					if (!((Complex) a.getObjectAbs(it.index)).equals(Complex.ZERO))
						n++;
				}
			} else {
				while (it.hasNext()) {
					if (a.getElementBooleanAbs(it.index))
						n++;
				}
			}
			
			break;
		default:
			n = vectorNorm(a, 2);
			break;
		}
		return n;
	}

	private static double matrixNorm(Dataset a, NormOrder order) {
		double n;
		IndexIterator it;
		switch (order) {
		case NEG_INFINITY:
		case POS_INFINITY:
			n = maxMinMatrixNorm(a, 1, order == NormOrder.POS_INFINITY);
			break;
		case ZERO:
			throw new IllegalArgumentException("Not allowed for matrices");
		default:
		case FROBENIUS:
			it = a.getIterator();
			n = 0;
			if (a.isComplex()) {
				while (it.hasNext()) {
					double v = ((Complex) a.getObjectAbs(it.index)).abs();
					n += v*v;
				}
			} else {
				while (it.hasNext()) {
					double v = a.getElementDoubleAbs(it.index);
					n += v*v;
				}
			}
			n = Math.sqrt(n);
			break;
		}
		return n;
	}

	/**
	 * @param a
	 * @param p
	 * @return p-norm of dataset
	 */
	public static double norm(Dataset a, final double p) {
		if (p == 0) {
			return norm(a, NormOrder.ZERO);
		}
		int r = a.getRank();
		if (r == 1) {
			return vectorNorm(a, p);
		} else if (r == 2) {
			return matrixNorm(a, p);
		}
		throw new IllegalArgumentException("Rank of dataset must be one or two");
	}

	private static double vectorNorm(Dataset a, final double p) {
		IndexIterator it = a.getIterator();
		double n = 0;
		if (a.isComplex()) {
			while (it.hasNext()) {
				double v = ((Complex) a.getObjectAbs(it.index)).abs();
				if (p == 2) {
					v *= v;
				} else if (p != 1) {
					v = Math.pow(v, p);
				}
				n += v;
			}
		} else {
			while (it.hasNext()) {
				double v = a.getElementDoubleAbs(it.index);
				if (p == 1) {
					v = Math.abs(v);
				} else if (p == 2) {
					v *= v;
				} else {
					v = Math.pow(Math.abs(v), p);
				}
				n += v;
			}
		}
		return Math.pow(n, 1./p);
	}

	private static double matrixNorm(Dataset a, final double p) {
		double n;
		if (Math.abs(p) == 1) {
			n = maxMinMatrixNorm(a, 0, p > 0);
		} else if (Math.abs(p) == 2) {
			double[] s = calcSingularValues(a);
			n = p > 0 ? s[0] : s[s.length - 1];
		} else {
			throw new IllegalArgumentException("Order not allowed");
		}

		return n;
	}

	private static double maxMinMatrixNorm(Dataset a, int d, boolean max) {
		double n;
		IndexIterator it;
		int[] pos;
		int l;
		it = a.getPositionIterator(d);
		pos = it.getPos();
		l = a.getShapeRef()[d];
		if (max) {
			n = Double.NEGATIVE_INFINITY;
			if (a.isComplex()) {
				while (it.hasNext()) {
					double v = ((Complex) a.getObject(pos)).abs();
					for (int i = 1; i < l; i++) {
						pos[d] = i;
						v += ((Complex) a.getObject(pos)).abs();
					}
					pos[d] = 0;
					n = Math.max(n, v);
				}
			} else {
				while (it.hasNext()) {
					double v = Math.abs(a.getDouble(pos));
					for (int i = 1; i < l; i++) {
						pos[d] = i;
						v += Math.abs(a.getDouble(pos));
					}
					pos[d] = 0;
					n = Math.max(n, v);
				}
			}
		} else {
			n = Double.POSITIVE_INFINITY;
			if (a.isComplex()) {
				while (it.hasNext()) {
					double v = ((Complex) a.getObject(pos)).abs();
					for (int i = 1; i < l; i++) {
						pos[d] = i;
						v += ((Complex) a.getObject(pos)).abs();
					}
					pos[d] = 0;
					n = Math.min(n, v);
				}
			} else {
				while (it.hasNext()) {
					double v = Math.abs(a.getDouble(pos));
					for (int i = 1; i < l; i++) {
						pos[d] = i;
						v += Math.abs(a.getDouble(pos));
					}
					pos[d] = 0;
					n = Math.min(n, v);
				}
			}
		}
		return n;
	}

	/**
	 * @param a
	 * @return array of singular values
	 */
	public static double[] calcSingularValues(Dataset a) {
		if (a.getRank() != 2) {
			throw new IllegalArgumentException("Dataset must be rank 2");
		}
		int[] shape = a.getShapeRef();
		IndexIterator it = a.getIterator(true);
		int[] pos = it.getPos();
		RealMatrix m = MatrixUtils.createRealMatrix(shape[0], shape[1]);
		while (it.hasNext()) {
			m.setEntry(pos[0], pos[1], a.getElementDoubleAbs(it.index));
		}

		double[] s = new SingularValueDecomposition(m).getSingularValues();
		return s;
	}
}
