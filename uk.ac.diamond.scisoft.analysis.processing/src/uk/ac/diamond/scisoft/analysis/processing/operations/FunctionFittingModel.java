/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.fitting.FittingConstants.FIT_ALGORITHMS;

public class FunctionFittingModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the function file", file = FileType.EXISTING_FILE, label = "Function File")
	private String filePath = "";
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}

	@OperationModelField(hint="Select the optimiser to use for data fitting", label = "Optimisation algorithm")
	private FIT_ALGORITHMS optimiser = FIT_ALGORITHMS.APACHELEVENBERGMAQUARDT;

	public FIT_ALGORITHMS getOptimiser() {
		return optimiser;
	}

	public void setOptimiser(FIT_ALGORITHMS optimiser) {
		firePropertyChange("optimiser", this.optimiser, this.optimiser = optimiser);
	}
	
	@OperationModelField(description="Choose whether to use the inital or result parameters from the file", label = "Use Initial Parameters")
	private boolean useInitial = true;
	
	public boolean isUseInitial() {
		return useInitial;
	}
	
	public void setUseInitial(boolean useInitial) {
		firePropertyChange("useInitial", this.useInitial, this.useInitial = useInitial);
	}
	
	@OperationModelField(description="Choose whether to use the limits specified in the file. Caution: some optimizers may fail to converge when combined with limits", label = "Use Limits")
	private boolean useLimits = false;
	
	public boolean isUseLimits() {
		return useLimits;
	}
	
	public void setUseLimits(boolean useLimits) {
		firePropertyChange("useLimits", this.useLimits, this.useLimits = useLimits);
	}
	
}