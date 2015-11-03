/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

class XPDFOperationChecker {

	public static void checkXPDFMetadata(IOperation<?,?> parentOp, IDataset theData, boolean needsSample, boolean needsBeam, boolean needsDetector) 
	throws OperationException {
		if (theData.getFirstMetadata(XPDFMetadata.class) == null)
			throw new OperationException(parentOp, "XPDFMetadata not found.");
		
		XPDFMetadata theMetadata = theData.getFirstMetadata(XPDFMetadata.class);
		
		if (needsSample && theMetadata.getSample() == null)
				throw new OperationException(parentOp, "XPDF sample metadata not found.");
			
		if (needsBeam && theMetadata.getBeam() == null)
			throw new OperationException(parentOp, "XPDF beam metadata not found.");
		
		if (needsDetector && theMetadata.getDetector() == null)
			throw new OperationException(parentOp, "XPDF detector metadata not found.");
	return;
	}	
}
