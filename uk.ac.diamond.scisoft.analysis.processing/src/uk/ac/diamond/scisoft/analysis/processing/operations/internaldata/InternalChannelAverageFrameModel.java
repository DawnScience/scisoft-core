/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;


import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.MathematicalOperators;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


//@author: Tim Snow (tim.snow@diamond.ac.uk)


public class InternalChannelAverageFrameModel extends AbstractOperationModel {
	
	public enum AveragingDirection {
		Y(0,"Y"),
		X(1,"X");

		private final int averagingInt;
		private final String displayName;

		AveragingDirection(int averagingInt, String displayName) {
			this.averagingInt = averagingInt;
			this.displayName = displayName;
		}

		public int getInt() {
			return this.averagingInt;
		}

		public String getDisplayName() {
			return this.displayName;
		}

		@Override
		public String toString() {
			return this.displayName;
		}
	}

	
	@OperationModelField(label = "Dataset", hint = "Path to the dataset within the file containing the dataset the values to average and apply", dataset = "filePath", fieldPosition = 2)
	private String datasetName = "";
	
	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		firePropertyChange("datasetName", this.datasetName, this.datasetName = datasetName);
	}

	
	@OperationModelField(label = "Averaging Direction", hint = "Whether to average the frame's column (y) or row (x) data", fieldPosition = 3)
	private AveragingDirection averagingDirection = AveragingDirection.X;
	
	public AveragingDirection getAveragingDirection() {
		return averagingDirection;
	}
	
	public void setAveragingDirection(AveragingDirection averagingDirection) {
		firePropertyChange("averagingDirection", this.averagingDirection, this.averagingDirection = averagingDirection);
	}
	
	
	@OperationModelField(label = "Frame Channel", hint = "The frame's column (y) or row (x) to be averaged before applying this value to the primary dataset - Note: as this is an array index this series starts at zero not one", fieldPosition = 4)
	private int channelIndex = 0;
	
	public int getChannelIndex() {
		return channelIndex;
	}

	public void setChannelIndex(int channelIndex) {
		firePropertyChange("channelIndex", this.channelIndex, this.channelIndex = channelIndex);
	}
	
	
	@OperationModelField(label = "Mathematical Operator", hint = "The mathematical operator you wish to apply", fieldPosition = 5)
	private MathematicalOperators mathematicalOperator = MathematicalOperators.ADD;
	
	public MathematicalOperators getMathematicalOperator() {
		return mathematicalOperator;
	}
	
	public void setMathematicalOperator(MathematicalOperators mathematicalOperator) {
		firePropertyChange("mathematicalOperator", this.mathematicalOperator, this.mathematicalOperator = mathematicalOperator);
	}
	
	
	@OperationModelField(label="Scaling Factor", hint="Scaling of the multiplying data", fieldPosition = 6)
	private double scaling = 1.0;
	
	public double getScaling() {
		return scaling;
	}
	
	public void setScaling(double scaling) {
		firePropertyChange("scaling", this.scaling, this.scaling = scaling);
	}
}
