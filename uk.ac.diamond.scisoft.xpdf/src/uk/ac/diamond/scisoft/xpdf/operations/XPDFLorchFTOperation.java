/*
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Perform the Lorch Fourier Transform.
 * <p>
 * This takes the th_soq data and return
 * the D(r) data. The function itself is a translation of DK's python code
 * into Java.
 * 
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFLorchFTOperation extends
		AbstractOperation<XPDFLorchFTModel, OperationData> {

	
	protected OperationData process(IDataset thSoq, IMonitor monitor) throws OperationException {
	
		// Number density and g0-1 from the sample material.
		double numberDensity = 0.0;
		double g0minus1 = 0.0;
		XPDFMetadata theXPDFMetadata = null;
		try {		
			if (thSoq.getMetadata(XPDFMetadata.class) != null &&
					!thSoq.getMetadata(XPDFMetadata.class).isEmpty() &&
					thSoq.getMetadata(XPDFMetadata.class).get(0) != null) {
				theXPDFMetadata = thSoq.getMetadata(XPDFMetadata.class).get(0);
				if (theXPDFMetadata.getSample() != null ) {
				numberDensity = thSoq.getMetadata(XPDFMetadata.class).get(0).getSample().getNumberDensity();
				g0minus1 = thSoq.getMetadata(XPDFMetadata.class).get(0).getSample().getG0Minus1();
				}
			}
		} catch (Exception e) {
			;
		}

		Dataset q, r;
		
		XPDFCoordinates coordinates = new XPDFCoordinates();
		coordinates.setTwoTheta(Maths.toRadians(DatasetUtils.convertToDataset(AbstractOperation.getFirstAxes(thSoq)[0])));
		coordinates.setBeamData(theXPDFMetadata.getBeam());
		q = coordinates.getQ();
		
		r = DoubleDataset.createRange(model.getrStep()/2, model.getrMax(), model.getrStep());
		Dataset hofr = doLorchFT(DatasetUtils.convertToDataset(thSoq), q, r, model.getLorchWidth(), numberDensity);
		Dataset gofr = Maths.divide(hofr, g0minus1);
		Dataset dofr = Maths.multiply(Maths.multiply(gofr, r), 4*Math.PI * numberDensity);
		
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
		lorch.set(0.0, 0);
		
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
