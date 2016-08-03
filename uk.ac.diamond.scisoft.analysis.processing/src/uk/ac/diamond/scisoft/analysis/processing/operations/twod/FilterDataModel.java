/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class FilterDataModel extends AbstractOperationModel {

	enum FilterDataAxis {
		X("X", 0), Y("Y", 1), X_THEN_Y("X then Y"), Y_THEN_X("Y then X");
		
		private String name;
		private int axis;
		
		FilterDataAxis(String name) {
			this(name, -1);
		}
		
		FilterDataAxis(String name, int axis) {
			this.name = name;
			this.axis = axis;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public int getAxis() {
			return axis;
		}
		
	}
	
	enum FilterDataOperator {
		GREATERTHAN(">"),
		GREATERTHANOREQUAL("\u2265"),
		LESSTHAN("<"),
		LESSTHANOREQUAL("\u2264"),
		EQUAL("="),
		NOTEQUAL("\u2260");
		
		private String name;
		
		FilterDataOperator(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	enum FilterDataSource {
		SUM("sum"),
		AVERAGE("average"),
		MEDIAN("median"),
		MINIMUM("minimum"),
		MAXIMUM("maximum");
		
		private String name;
		
		FilterDataSource (String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	@OperationModelField(fieldPosition = 0, hint = "Axis along which data may be removed", label = "Axis")
	private FilterDataAxis axis = FilterDataAxis.X;
	
	@OperationModelField(fieldPosition = 1, hint = "Data source that will be used in the comparison", label = "Source")
	private FilterDataSource source = FilterDataSource.AVERAGE;
	
	@OperationModelField(fieldPosition = 2, hint = "Relational operator that will be used to compare against the threshold value", label = "Operator")
	private FilterDataOperator operator = FilterDataOperator.GREATERTHAN;
	
	@OperationModelField(fieldPosition = 3, hint = "Value that will be compared against", label = "Threshold")
	private double threshold = 1E-10;

	public FilterDataAxis getAxis() {
		return axis;
	}

	public void setAxis(FilterDataAxis axis) {
		firePropertyChange("axis", this.axis, this.axis = axis);
	}

	public FilterDataSource getSource() {
		return source;
	}

	public void setSource(FilterDataSource source) {
		firePropertyChange("source", this.source, this.source = source);
	}

	public FilterDataOperator getOperator() {
		return operator;
	}

	public void setOperator(FilterDataOperator operator) {
		firePropertyChange("operator", this.operator, this.operator = operator);
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		firePropertyChange("threshold", this.threshold, this.threshold = threshold);
	}
	
}
