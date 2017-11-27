/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Model for fitting of the PDF of background pixels then subtracting
 * a level where given signal to background ratio occurs
 */
public class SubtractFittedBackgroundModel extends AbstractOperationModel {
	enum BackgroundPixelPDF {
		Gaussian;
	}

	@OperationModelField(label = "Positive Only (ignore negative values in data)")
	private boolean positiveOnly = true;

	@OperationModelField(label = "Background PDF")
	private BackgroundPixelPDF backgroundPDF = BackgroundPixelPDF.Gaussian;

	@OperationModelField(label = "Signal to background ratio", min=1, max=50)
	private double ratio = 3.0;

	public static final int HISTOGRAM_MAX_BINS = 1024*1024;

	public boolean isPositiveOnly() {
		return positiveOnly;
	}

	public void setPositiveOnly(boolean positiveOnly) {
		firePropertyChange("setPositiveOnly", this.positiveOnly, this.positiveOnly = positiveOnly);
	}

	public BackgroundPixelPDF getBackgroundPDF() {
		return backgroundPDF;
	}

	public void setBackgroundPDF(BackgroundPixelPDF pdf) {
		firePropertyChange("setBackgroundPDF", this.backgroundPDF, this.backgroundPDF = pdf);
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		firePropertyChange("setRatio", this.ratio, this.ratio = ratio);
	}
}
