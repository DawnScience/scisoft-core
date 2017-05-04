/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package org.dawnsci.surfacescatter;


import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// Let's find somewhere to save this file!
public class RodObjectNexusBuilderModel extends AbstractOperationModel{

	private ArrayList<ExampleModel> models;
	private ArrayList<DataModel> dms;
	private SuperModel sm;
	private String filepath;
	private GeometricParametersModel gm;

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	// Now the getters and setters
	public ArrayList<ExampleModel> getModels() {
		return models;
	}
	
	public void setModels(ArrayList<ExampleModel> m) {
		models = m;
	}
	
	public ArrayList<DataModel> getDms() {
		return dms;
	}

	public void setDms(ArrayList<DataModel> dms) {
		this.dms = dms;
	}

	public SuperModel getSm() {
		return sm;
	}

	public void setSm(SuperModel sm) {
		this.sm = sm;
	}

	public GeometricParametersModel getGm() {
		return gm;
	}

	public void setGm(GeometricParametersModel gm) {
		this.gm = gm;
	}

	
}
