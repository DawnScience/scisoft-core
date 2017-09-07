/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.decomposition;

import org.eclipse.january.dataset.IDataset;

public class PCAResult {
	
	private IDataset loadings;
	private IDataset scores;
	private IDataset varianceRatio;
	private IDataset mean;

	PCAResult(IDataset loadings, IDataset scores, IDataset mean, IDataset varianceRatio) {
		this.loadings = loadings;
		this.scores = scores;
		this.varianceRatio = varianceRatio;
		this.mean = mean;
	}
	
	public IDataset getLoadings(){
		return loadings;
	}

	public IDataset getScores() {
		return scores;
	}

	public IDataset getVarianceRatio() {
		return varianceRatio;
	}

	public IDataset getMean() {
		return mean;
	}
}
