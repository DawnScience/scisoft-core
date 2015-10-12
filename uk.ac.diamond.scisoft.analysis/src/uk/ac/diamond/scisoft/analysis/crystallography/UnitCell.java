/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.util.Arrays;

import javax.vecmath.Vector3d;


/**
 * UnitCell class as specified by lengths and angles of cell
 */
public class UnitCell extends LatticeCell {
	private double[] lengths;
	private double[] angles;

	protected UnitCell() {
	}

	/**
	 * Cubic unit cell
	 * @param length
	 */
	public UnitCell(double length) {
		this(new double[] { length, length, length });
	}

	/**
	 * Cuboid unit cell or general unit cell
	 * 
	 * @param parameters can be lengths (for cuboid) or both lengths and angles
	 */
	public UnitCell(double[] parameters) {
		super();

		if (parameters.length >= 6) {
			setLengths(Arrays.copyOf(parameters, 3));
			setAngles(Arrays.copyOfRange(parameters, 3, 6));
			makeBasisVectors();
		} else if (parameters.length == 3) {
			setLengths(parameters);
			setAngles(new double[] { 90, 90, 90 });

			a = new Vector3d(lengths[0], 0, 0);
	
			b = new Vector3d(0, lengths[1], 0);
	
			c = new Vector3d(0, 0, lengths[2]);
		} else {
			throw new IllegalArgumentException("Insufficient number of parameters: need at least three");
		}
	}

	/**
	 * @param lengths
	 * @param angles (in degrees)
	 */
	public UnitCell(double[] lengths, double[] angles) {
		super();
		setLengths(lengths);
		setAngles(angles);

		makeBasisVectors();
	}

	private void makeBasisVectors()  {
		double alpha = Math.toRadians(angles[0]);
		double beta  = Math.toRadians(angles[1]);
		double gamma = Math.toRadians(angles[2]);

		a = new Vector3d(lengths[0], 0, 0);

		b = new Vector3d(lengths[1] * Math.cos(gamma), lengths[1] * Math.sin(gamma), 0);

		double sinBcosA = (Math.cos(alpha) - Math.cos(beta) * Math.cos(gamma)) / Math.sin(gamma);

		double sinA = sinBcosA / Math.sin(beta);
		sinA = 1.0 - sinA * sinA;
		if (sinA < 0.0)
			sinA = 0.0; // for numerical errors

		c = new Vector3d(lengths[2] * Math.cos(beta), lengths[2] * sinBcosA,
				lengths[2] * Math.sin(beta) * Math.sqrt(sinA));
	}

	/**
	 * @param oa
	 * @param ob
	 * @param oc
	 */
	public UnitCell(double[] oa, double[] ob, double[] oc) {
		super();
		a = new Vector3d(oa[0], oa[1], oa[2]);
		b = new Vector3d(ob[0], ob[1], ob[2]);
		c = new Vector3d(oc[0], oc[1], oc[2]);
		setLengths(new double[] { a.length(), b.length(), c.length() });
		setAngles(new double[] { 90, 90, 90 });
	}

	/**
	 * @param lengths of unit cell
	 */
	public void setLengths(double[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return lengths of unit cell
	 */
	public double[] getLengths() {
		return lengths;
	}

	/**
	 * @param angles (in degrees)
	 */
	public void setAngles(double[] angles) {
		this.angles = angles;
	}

	/**
	 * @return angles (in degrees) of unit cell
	 */
	public double[] getAngles() {
		return angles;
	}

	protected void calculateAll() {
		if (lengths == null)
			lengths = new double[3];
		lengths[0] = a.length();
		lengths[1] = b.length();
		lengths[2] = c.length();

		if (angles == null)
			angles = new double[3];
		angles[0] = Math.toDegrees(b.angle(c));
		angles[1] = Math.toDegrees(c.angle(a));
		angles[2] = Math.toDegrees(a.angle(b));
	}

	@Override
	public void setA(Vector3d a) {
		super.setA(a);
		lengths[0] = a.length();
		angles[1] = Math.toDegrees(c.angle(a));
		angles[2] = Math.toDegrees(a.angle(b));
	}

	@Override
	public void setB(Vector3d b) {
		super.setB(b);
		lengths[1] = b.length();
		angles[0] = Math.toDegrees(b.angle(c));
		angles[2] = Math.toDegrees(a.angle(b));
	}

	@Override
	public void setC(Vector3d c) {
		super.setC(c);
		lengths[2] = c.length();
		angles[0] = Math.toDegrees(b.angle(c));
		angles[1] = Math.toDegrees(c.angle(a));
	}
}
