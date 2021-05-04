/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

@Atomic
public class MovingBeamCakePixelIntgrationOperation extends CakePixelIntegrationOperation {
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (model.getAzimuthalRange() == null || model.getRadialRange() == null) {
			throw new OperationException(this, "Moving beam integrations require azimuthal and radial ranges to be specified");
		}
		
		return super.process(input, monitor);
	}
	
	protected boolean useCache() {
		return false;
	}

}
