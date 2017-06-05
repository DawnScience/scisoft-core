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

	private ArrayList<FrameModel> fms;
	private String filepath;
	private GeometricParametersModel gm;
	private DirectoryModel drm;

	
	public RodObjectNexusBuilderModel(ArrayList<FrameModel> fms,
								      String filepath,
								      GeometricParametersModel gm,
								      DirectoryModel drm){

		this.fms =fms;
		this.drm =drm;
		this.gm =gm;
		this.filepath =filepath;
	
	}
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public GeometricParametersModel getGm() {
		return gm;
	}

	public void setGm(GeometricParametersModel gm) {
		this.gm = gm;
	}

	public ArrayList<FrameModel> getFms() {
		return fms;
	}

	public void setFms(ArrayList<FrameModel> fms) {
		this.fms = fms;
	}

	public DirectoryModel getDrm() {
		return drm;
	}

	public void setDrm(DirectoryModel drm) {
		this.drm = drm;
	}
	
}
