/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MetadataType;


// 
class ReferencePosition2DMetadata implements MetadataType {
	
	

	private static final long serialVersionUID = -5460038069731356919L;
	private DoubleDataset referencePosition;
	
	
	public ReferencePosition2DMetadata() {
		referencePosition = DatasetFactory.zeros(new int[] {2});
	}
	
	private ReferencePosition2DMetadata(ReferencePosition2DMetadata toCopy) {
		referencePosition = toCopy.getReferencePosition();
	}
	
	public void setReferencePosition(IDataset pos) {
		referencePosition = DatasetUtils.cast(DoubleDataset.class, pos);
	}
	
	
	// get the reference position from a dataset
	DoubleDataset getReferencePosition() {
		return this.referencePosition;
	}
	
	
	
	
	public ReferencePosition2DMetadata clone() {
		return new ReferencePosition2DMetadata(this);
	}
	

}
