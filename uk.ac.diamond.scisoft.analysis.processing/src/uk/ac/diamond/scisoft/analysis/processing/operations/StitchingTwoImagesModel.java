/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalDataModel;

public class StitchingTwoImagesModel extends ExternalDataModel {

//	@OperationModelField(label = "Use feature Association", hint = "Use feature association to perfom the stitching, mosaic operation otherwise")
//	private boolean featureAssociated = true;

	@OperationModelField(label = "Dataset Name", hint = "The name of the dataset or the node path of the data if a .NXS file")
	private String dataName = "";
	@OperationModelField(label = "X Translation", hint = "X translation between two images in pixels")
	private double xTransl = 25;
	@OperationModelField(label = "Y Translation", hint = "Y translation between two images in pixels")
	private double yTransl = 25;

//	public boolean isFeatureAssociated() {
//		return featureAssociated;
//	}
//	public void setFeatureAssociated(boolean featureAssociated) {
//		firePropertyChange("hasFeatureAssociation", this.featureAssociated, this.featureAssociated = featureAssociated);
//	}
	public double getxTransl() {
		return xTransl;
	}
	public void setxTransl(double xTransl) {
		firePropertyChange("xTranslation", this.xTransl, this.xTransl = xTransl);
	}
	public double getyTransl() {
		return yTransl;
	}
	public void setyTransl(double yTransl) {
		firePropertyChange("yTranslation", this.yTransl, this.yTransl = yTransl);
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataname(String dataName) {
		firePropertyChange("DataName", this.dataName, this.dataName = dataName);
	}

}
