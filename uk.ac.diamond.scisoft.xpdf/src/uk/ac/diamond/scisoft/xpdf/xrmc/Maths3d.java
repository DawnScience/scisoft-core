package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.util.Arrays;

import javax.vecmath.Vector3d;

class Maths3d {
	Vector3d value;
	
	public Maths3d(Vector3d v) {
		value = v;
	}
	
	public Maths3d(Maths3d vobj) {
		this(vobj.value);
	}
	
	public Maths3d(double[] darr) {
		this(new Vector3d(Arrays.copyOf(darr, 3)));
	}
	
	public Vector3d get() {
		return value;
	}
	
	public Maths3d plus(Maths3d b) {
		return this.plus(b.value);
	}
	
	public Maths3d plus(Vector3d b) {
		Maths3d a = new Maths3d(new Vector3d(value));
		a.value.add(b);
		return a;
	}
	
	public Maths3d plus(Double b) {
		return this.plus(b);
	}

	public Maths3d plus(double b) {
		Maths3d a = new Maths3d(new Vector3d(value));
		a.value.add(new Vector3d(b, b, b));
		return a;
	}
	
	public Maths3d minus(Maths3d b) {
		return this.minus(b.value);
	}
	
	public Maths3d minus(Vector3d b) {
		Maths3d a = new Maths3d(new Vector3d(value));
		a.value.sub(b);
		return a;
	}
	
	public Maths3d minus(Double b) {
		return this.minus(b);
	}

	public Maths3d minus(double b) {
		Maths3d a = new Maths3d(new Vector3d(value));
		a.value.add(new Vector3d(-b, -b, -b));
		return a;
	}
	
	public Double dot(Maths3d b) {
		return this.dot(b.value);
	}
	
	public Double dot(Vector3d b) {
		return new Double(this.value.dot(b));
	}
	
	
	public Maths3d cross(Maths3d b) {
		return this.cross(b.value);
	}
	
	public Maths3d cross(Vector3d b) {
		Vector3d a = new Vector3d();
		a.cross(this.value, b);
		return new Maths3d(a);
	}
	
	public Maths3d times(double b) {
		Maths3d a = new Maths3d(new Vector3d(value));
		a.value.scale(b);
		return a;
	}
	
}