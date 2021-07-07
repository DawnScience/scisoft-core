/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class LocateInRowsModel extends AbstractOperationModel {

	public enum FeatureType {
		MINIMUM("minimum"),
		MAXIMUM("maximum"),
		MEAN("mean"),
		CROSSING("crossing"),
		;
		private String name;
		private FeatureType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@OperationModelField(fieldPosition = 0, label = "Feature", description = "Feature type to locate")
	private FeatureType featureType = FeatureType.MAXIMUM;

	@OperationModelField(fieldPosition = 1, label = "Use first", description = "Use first location", hint = "If false, use last location", enableif = "featureType != 'MAXIMUM' && featureType != 'MINIMUM'")
	private boolean useFirst = true;

	@OperationModelField(fieldPosition = 2, label = "Crossing value", description = "Level for row values to cross", enableif = "featureType == 'CROSSING'")
	private double crossing = Double.NaN;

	/**
	 * @return type of feature to locate
	 */
	public FeatureType getFeatureType() {
		return featureType;
	}

	public void setFeatureType(FeatureType featureType) {
		firePropertyChange("featureType", this.featureType, this.featureType = featureType);
	}

	/**
	 * @return true if use first crossing
	 */
	public boolean isUseFirst() {
		return useFirst;
	}

	public void setUseFirst(boolean useFirst) {
		firePropertyChange("useFirst", this.useFirst, this.useFirst = useFirst);
	}

	/**
	 * @return value to cross
	 */
	public double getCrossing() {
		return crossing;
	}

	public void setCrossing(double crossing) {
		firePropertyChange("crossing", this.crossing, this.crossing = crossing);
	}
}
