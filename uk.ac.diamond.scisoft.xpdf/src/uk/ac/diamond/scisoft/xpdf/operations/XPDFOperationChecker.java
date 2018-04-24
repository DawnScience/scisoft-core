/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * A helper class to check for the presence of features of the {@link XPDFMetadata} metadata.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class XPDFOperationChecker {

	private XPDFOperationChecker() {}

	public static boolean hasMetadata(IDataset theData) {
		return theData.getFirstMetadata(XPDFMetadata.class) != null;
	}
	
	public static boolean hasSampleMetadata(IDataset theData) {
		return hasSampleMetadata(getMetadata(theData));
	}
	public static boolean hasSampleMetadata(XPDFMetadata theMetadata) {
		return theMetadata.getSample() != null;
	}

	public static boolean hasBeamMetadata(IDataset theData) {
		return hasBeamMetadata(getMetadata(theData));
	}
	public static boolean hasBeamMetadata(XPDFMetadata theMetadata) {
		return theMetadata.getBeam() != null;
	}

	public static boolean hasDetectorMetadata(IDataset theData) {
		return hasDetectorMetadata(getMetadata(theData));
	}
	public static boolean hasDetectorMetadata(XPDFMetadata theMetadata) {
		return theMetadata.getDetector() != null;
	}

	
	public static void checkXPDFMetadata(IOperation<?,?> parentOp, IDataset theData, boolean needsSample, boolean needsBeam, boolean needsDetector) 
	{
		if (!hasMetadata(theData))
			throw new OperationException(parentOp, "XPDFMetadata not found.");
		
		XPDFMetadata theMetadata = getMetadata(theData);
		
		if (needsSample && !hasSampleMetadata(theMetadata))
				throw new OperationException(parentOp, "XPDF sample metadata not found.");
			
		if (needsBeam && !hasBeamMetadata(theMetadata))
			throw new OperationException(parentOp, "XPDF beam metadata not found.");
		
		if (needsDetector && !hasDetectorMetadata(theMetadata))
			throw new OperationException(parentOp, "XPDF detector metadata not found.");
	return;
	}	

	public static boolean isAllIncoherentScatterPresent(IDataset theData) {
		XPDFMetadata theMetadata = theData.getFirstMetadata(XPDFMetadata.class);
		return theMetadata.isAllIncoherentScatterPresent();
	}

	private static XPDFMetadata getMetadata(IDataset theData) {
		return theData.getFirstMetadata(XPDFMetadata.class);
	}
}
