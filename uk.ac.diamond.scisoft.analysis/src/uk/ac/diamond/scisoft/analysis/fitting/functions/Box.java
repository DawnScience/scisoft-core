/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class that wrappers the box function calculated as a difference of two Fermi functions
 * y(x) = Fermi(mu1, kT1, scale) - Fermi(mu2,kT2, scale)
 */
public class Box extends AFunction {

	private static final String cname = "Box";

	private static final String cdescription = "y(x) = Fermi(mu1, kT1, scale) - Fermi(mu2,kT2, scale)";
	private static final String[] paramNames = new String[]{"mu1", "kT1", "mu2", "kT2", "scale"};
	private static final double[] params = new double[]{0,0,0,0,0};

	private double mu1, kT1, mu2, kT2, scale;
	private Fermi fermi1, fermi2; 

	public Box(){
		this(params);
	}

	public Box(double... params) {
		super(params);
		
		double[] fermi1Params = {params[0], params[1], params[4], 0.0};
		double[] fermi2Params = {params[2], params[3], params[4], 0.0};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);

		setNames();
	}

	public Box(IParameter... params) {
		super(params);
		
		IParameter[] fermi1Params = {getParameter(0), getParameter(1), getParameter(4), new Parameter(0.0)};
		IParameter[] fermi2Params = {getParameter(2), getParameter(3), getParameter(4), new Parameter(0.0)};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);

		setNames();
	}

	private void setNames() {
		name = cname;
		description = cdescription;
		for (int i = 0; i < paramNames.length; i++) {
			IParameter p = getParameter(i);
			p.setName(paramNames[i]);
		}
	}

	private void calcCachedParameters() {
		mu1 = getParameterValue(0);
		kT1 = getParameterValue(1);
		mu2 = getParameterValue(2);
		kT2 = getParameterValue(3);
		scale = getParameterValue(4);
    
		fermi1 = new Fermi(new double[] {mu1, kT1, scale, 0.0});
		fermi2 = new Fermi(new double[] {mu2, kT2, scale, 0.0});

		setDirty(false);
	}

	
	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];
		
		double valFermi1 = fermi1.val(position);
		double valFermi2 = fermi2.val(position);
		
		return valFermi2 - valFermi1;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		fermi1.fillWithValues(data, it);

		DoubleDataset temp = new DoubleDataset(it.getShape());
		fermi1.fillWithValues(temp, it);
		data.isubtract(temp);
	}

	public double getMu1() {
		return mu1;
	}

	public void setMu1(double mu1) {
		this.mu1 = mu1;
	}

	public double getkT1() {
		return kT1;
	}

	public void setkT1(double kT1) {
		this.kT1 = kT1;
	}

	public double getMu2() {
		return mu2;
	}

	public void setMu2(double mu2) {
		this.mu2 = mu2;
	}

	public double getkT2() {
		return kT2;
	}

	public void setkT2(double kT2) {
		this.kT2 = kT2;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Fermi getFermi1() {
		return fermi1;
	}

	public void setFermi1(Fermi fermi1) {
		this.fermi1 = fermi1;
	}

	public Fermi getFermi2() {
		return fermi2;
	}

	public void setFermi2(Fermi fermi2) {
		this.fermi2 = fermi2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fermi1 == null) ? 0 : fermi1.hashCode());
		result = prime * result + ((fermi2 == null) ? 0 : fermi2.hashCode());
		long temp;
		temp = Double.doubleToLongBits(kT1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(kT2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mu1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mu2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(scale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Box other = (Box) obj;
		if (fermi1 == null) {
			if (other.fermi1 != null)
				return false;
		} else if (!fermi1.equals(other.fermi1))
			return false;
		if (fermi2 == null) {
			if (other.fermi2 != null)
				return false;
		} else if (!fermi2.equals(other.fermi2))
			return false;
		if (Double.doubleToLongBits(kT1) != Double.doubleToLongBits(other.kT1))
			return false;
		if (Double.doubleToLongBits(kT2) != Double.doubleToLongBits(other.kT2))
			return false;
		if (Double.doubleToLongBits(mu1) != Double.doubleToLongBits(other.mu1))
			return false;
		if (Double.doubleToLongBits(mu2) != Double.doubleToLongBits(other.mu2))
			return false;
		if (Double.doubleToLongBits(scale) != Double.doubleToLongBits(other.scale))
			return false;
		return true;
	}
}
