/*
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Perform the Lorch Fourier Transform.
 * <p>
 * This takes the th_soq data and return
 * the D(r) data. The function itself is a translation of DK's python code
 * into Java.
 * 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFLorchFTOperation extends
		AbstractOperation<XPDFLorchFTModel, OperationData> {

	
	protected OperationData process(IDataset thSoq, IMonitor monitor) throws OperationException {
	
		XPDFOperationChecker.checkXPDFMetadata(this, thSoq, true, false, false);
		
		XPDFMetadata theXPDFMetadata = thSoq.getFirstMetadata(XPDFMetadata.class);

		// Number density and g0-1 from the sample material.
		double numberDensity = theXPDFMetadata.getSample().getNumberDensity();
		double g0minus1 = theXPDFMetadata.getSample().getG0Minus1();

		XPDFCoordinates coordinates = new XPDFCoordinates(DatasetUtils.convertToDataset(thSoq));
		Dataset q = coordinates.getQ();
		// Apply a Q cut-off, if defined
		int iCutoff = q.getSize()-1;
		if (model.getMaxQ() < q.max().doubleValue()) {
			// Find the point closest to the selected maximum Q value.
			iCutoff= Maths.abs(Maths.subtract(q, model.getMaxQ())).minPos()[0];
			if (model.isSeekNextZero()) {
				// Find the next sign change. The minimum value of the array of
				// numbers with the same value as the decrementing index
				// counter, and the same sign as the data.
				int iZeroCrossing = Maths.multiply(Maths.multiply(Maths.signum(thSoq).getSlice(new int[]{iCutoff}, new int[]{thSoq.getSize()}, new int[]{1}), DoubleDataset.createRange(q.getSize(), iCutoff, -1)), Math.signum(thSoq.getDouble(iCutoff-1))).minPos()[0];
				// Otherwise, use the point closest to zero
				if (iZeroCrossing == thSoq.getSize()-1-iCutoff) {
					Dataset absVal = Maths.abs(thSoq.getSlice(new int[]{iCutoff}, new int[]{thSoq.getSize()}, new int[]{1})); 
					iZeroCrossing = absVal.minPos()[0];
				}
				iCutoff += iZeroCrossing;
			}
		}	
		
		
		Dataset r = DoubleDataset.createRange(model.getrStep()/2, model.getrMax(), model.getrStep());
		Dataset hofr = doLorchFT(DatasetUtils.convertToDataset(thSoq).getSliceView(new int[]{0}, new int[]{iCutoff}, new int[]{1}),
					q.getSliceView(new int[]{0}, new int[]{iCutoff}, new int[]{1}), r, model.getLorchWidth(), numberDensity);
		// Error propagation: through the Fourier transform
		if (thSoq.getError() != null) {
			// Prepare the Datasets to receive the transformed values. They all
			// need to be held at the same time to allow for the (logical)
			// transpose
			List<Dataset> sigmaF = new ArrayList<Dataset>(q.getSize());
			
			for (int iq = 0; iq < q.getSize(); iq++) {
				Dataset covarQ = new DoubleDataset(4);
				// The vector to transform is zero, except at element iq it
				// holds the uncertainty variance (error squared) of the data
				// point at iq
				covarQ.set(Maths.square(thSoq.getError().getSlice().getDouble(iq)), 0);
				Dataset qSlice = Maths.add(q.getDouble(iq), Maths.multiply(q.getDouble(3)-q.getDouble(2), DoubleDataset.createRange(0, 4, 1))); 
						
				// Transform this vector exactly as the data
				sigmaF.add(iq, doLorchFT(covarQ, qSlice, r, model.getLorchWidth(), numberDensity));
			}

			Dataset hofrError = new DoubleDataset(hofr);
			for (int ir = 0; ir < r.getSize(); ir++) {
				DoubleDataset covarQR = (DoubleDataset) DoubleDataset.zeros(q);
				// Create the vector to be transformed
				for (int iq = 0; iq < q.getSize(); iq++) {
					covarQR.setAbs(iq, sigmaF.get(iq).getDouble(ir));
				}
				// Transform the semi-transformed variance. Take the ir-th 
				// element, and assign the square root to the ir-th element of
				// the final error vector. 
				
				// Only a subset of r is needed
				Dataset rSubset = r.getSlice(new int[] {ir}, new int[] {ir+1}, new int[] {1});
				
				hofrError.set(Maths.sqrt(doLorchFT(covarQR, q, rSubset, model.getLorchWidth(), numberDensity).getDouble(0)), ir);
			}
			hofr.setError(hofrError);
		}
		
		Dataset gofr = Maths.divide(hofr, g0minus1);
		if (hofr.getError() != null)
			gofr.setError(Maths.divide(hofr.getError(), g0minus1));

		Dataset dofr = Maths.multiply(Maths.multiply(gofr, r), 4*Math.PI * numberDensity);
		if (gofr.getError() != null)
			dofr.setError(Maths.multiply(gofr.getError(), Maths.multiply(r, 4*Math.PI*numberDensity)));
		
		dofr.setMetadata(theXPDFMetadata);
		
		// Not copying the x-axis metadata, so create new x-axis from the 
		// r coordinate metadata
		AxesMetadataImpl ax = new AxesMetadataImpl(1);
		ax.addAxis(0, r);
		dofr.addMetadata(ax);
		
		return new OperationData(dofr);
	}
	
	private Dataset doLorchFT(Dataset thSoq, Dataset q, Dataset r, double lorchWidth, double numberDensity) {
		//	    # based heavily on deanFT above
		//	    # Seems to work, at least produces something that resembles an FT. 
		//	    # The only thing is that the peak is in the wrong place. 
		//	    output = np.zeros(shape(r))
		//	    qhq = q*soq
		//	    QD = q*Soper_Lorch_width
		//	    Lorch = 3*np.power(QD,-3)*(np.sin(QD)-QD*np.cos(QD))
		//	    Lorch[0] = 0
		//
		//	    for i in range(0,size(r)):
		//	        output[i] = (np.sin(q*r[i])*qhq*Lorch).sum()
		//	    output = output*(q[3]-q[2])*np.power(2.0*np.square(pi)*rho*r,-1)

		// Calculate th_soq, if it does not exist
		
		
		Dataset output = DoubleDataset.zeros(r);
		Dataset qhq = Maths.multiply(q, thSoq);
		Dataset qd = Maths.multiply(q, lorchWidth);
		Dataset lorch = 
				Maths.multiply(
					Maths.multiply(3, Maths.power(qd, -3)), 
					Maths.subtract(
							Maths.sin(qd), 
							Maths.multiply(qd, Maths.cos(qd))));
		if (q.getDouble(0) <= 0.0) lorch.set(0.0, 0);
		
		// Something resembling a Discrete Sine Transform
		for (int i = 0; i < output.getSize(); i++) {
			output.set(
					Maths.multiply(
							Maths.multiply(
									Maths.sin(
											Maths.multiply(q, r.getDouble(i))), 
									qhq),
							lorch).sum(true),
						i);
		}
		output.imultiply(Maths.divide(
				(q.getDouble(3) - q.getDouble(2)), 
				Maths.multiply(
						2 * Math.pow(Math.PI, 2) * numberDensity,
						r)));
		
	    return output;
	}

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFLorchFTOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
