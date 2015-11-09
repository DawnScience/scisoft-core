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
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Subtract a top-hat convolution from the data.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFTophatOperation extends AbstractOperation<XPDFTophatModel, OperationData> {

	
	protected OperationData process(IDataset soq, IMonitor monitor) throws OperationException {
	Dataset thSoq = null;

	XPDFOperationChecker.checkXPDFMetadata(this, soq, true, false, false);
	// Number density and g0-1 from the sample material.
	XPDFMetadata theXPDFMetadata = soq.getFirstMetadata(XPDFMetadata.class);

	double numberDensity = theXPDFMetadata.getSample().getNumberDensity();
	double g0minus1 = theXPDFMetadata.getSample().getG0Minus1();
	
	double rMin = model.getrMin();
	
	XPDFCoordinates coordinates = new XPDFCoordinates(DatasetUtils.convertToDataset(soq));
	Dataset q = coordinates.getQ();

	// Here r is merely a temporary coordinate system.
	Dataset r = DoubleDataset.createRange(model.getrStep()/2, model.getrMax(), model.getrStep());

	double tophatWidth = model.getTophatWidth();
	
	Dataset DPrimedoQ = doTophatConvolution(DatasetUtils.convertToDataset(soq), q, tophatWidth);
	thSoq = doTopHatConvolutionAndSubtraction(DPrimedoQ, q, r, rMin, tophatWidth, numberDensity, g0minus1);

	return new OperationData(thSoq);
	}
	
	private Dataset doTopHatConvolutionAndSubtraction(Dataset dPrimedoQ, Dataset q, Dataset r, double rMin, double tophatWidth, double numberDensity, double g0Minus1) {
//obj.th_dofr = XPDFFT.FT_qtor(obj.Q,obj.th_DprimedoQ,\
//        obj.number_density,r)
//# Need to know the following twice for the following equation
//fqt = 3*np.power(tophatwidth*r,-3)*(np.sin(tophatwidth*r)-\
//        tophatwidth*r*np.cos(tophatwidth*r))
//# see? told you. calculate b(r)
//obj.th_bofr = -obj.th_dofr*fqt/(1-fqt)
//# and set b(r) to be d(r)+g0_minus_1 at r < r_min
//obj.th_bofr[r<r_min] = obj.th_dofr[r<r_min]+obj.g0_minus_1
//# FT b(r) back to real-space to make B(Q)
//obj.th_Boq = XPDFFT.FT_rtoq(r,obj.th_bofr,obj.number_density,obj.Q)
//# S(Q) now equals D'(Q)-B(Q)
//obj.th_soq = obj.th_DprimedoQ-obj.th_Boq
//# S(0) probably equals nan or something. 
//obj.th_soq[0] = 0

		// Get from the model
		Dataset thDofr = fourierQtoR(q, dPrimedoQ, numberDensity, r);
		Dataset rTophat = Maths.multiply(r, tophatWidth);
		Dataset fQT = Maths.multiply(
							Maths.multiply(3, Maths.power( rTophat, -3)),
							Maths.subtract(
									Maths.sin(rTophat),
									Maths.multiply(rTophat, Maths.cos(rTophat))));
		Dataset thBofr = Maths.multiply(
								thDofr,
								Maths.divide(fQT, Maths.subtract(fQT, 1.0)));
		int iAboveRMin = DatasetUtils.findIndexGreaterThan(r, rMin);
		for (int i = 0; i < iAboveRMin; i++) {
			thBofr.set(thDofr.getDouble(i) + g0Minus1, i);
		}
		Dataset thBoq = fourierRtoQ(r, thBofr, numberDensity, q);

		// Error propagation
		// Calculate thBoq error.
		// Just pass-through at the moment
		if (dPrimedoQ.getError() != null) {
			thBoq.setError(dPrimedoQ.getError());
		}
		
		Dataset thSoq = Maths.subtract(dPrimedoQ, thBoq);
		// Error propagation: assume that thBoq has a valid error iff dPrimedoQ does
		Dataset thSoqError = (dPrimedoQ.getError() != null) ?
				Maths.sqrt(Maths.add(Maths.square(dPrimedoQ.getError()), Maths.square(thBoq.getError()))) :
					null;
		thSoq.set(0.0, 0);

		// copy metadata
		copyMetadata(dPrimedoQ, thSoq);
		if (thSoqError != null ) thSoq.setError(thSoqError);
		
		return thSoq;
	}

	private Dataset fourierQtoR(Dataset q, Dataset fQ,
			double numberDensity, Dataset r) {
//	output = np.zeros(shape(r))
//	qhq = q*soq
//	for i in range(0,size(r)):
//	    output[i] = (np.sin(q*r[i])*qhq).sum()
//	output = output*(q[3]-q[2])*np.power(2.0*np.square(pi)*rho*r,-1)
		Dataset output = DoubleDataset.zeros(r);
		Dataset qhq = Maths.multiply(q, fQ);
		output = fourierGeneric(qhq, q, r);
		output.imultiply(
				Maths.divide(
						q.getDouble(3) - q.getDouble(2), 
						Maths.multiply(
								2.0*Math.pow(Math.PI,  2)*numberDensity,
								r)));
		return output;
	}

	
	private Dataset fourierRtoQ(Dataset r, Dataset fR,
			double numberDensity, Dataset q) {
//  output = np.zeros(shape(q))
//	rhr = r*gofr
//	for i in range(0,size(q)):
//	    output[i] = (np.sin(r*q[i])*rhr).sum()
//	output = output*(r[3]-r[2])*4*pi*rho/q
		Dataset output = DoubleDataset.zeros(q);
		Dataset rhr = Maths.multiply(r, fR);
		output = fourierGeneric(rhr, r, q);
		output.imultiply(Maths.divide((r.getDouble(3) - r.getDouble(2))*4.0*Math.PI*numberDensity, q));
		return output;
	}

	/**
	 * Discrete sine transform common code. The summed functions are denoted by the u variable, the resultants by x
	 * @param functionOnU
	 * 				A function on the u axis to be transformed
	 * @return
	 * 		The DST on the x axis
	 */
	private Dataset fourierGeneric(Dataset functionOnU, Dataset coordinateU, Dataset coordinateX) {
		DoubleDataset dst = new DoubleDataset(coordinateX);
		IndexIterator iterX = coordinateX.getIterator();
		while (iterX.hasNext()) {
			IndexIterator iterU = coordinateU.getIterator();
			double accumulator = 0.0;
			while (iterU.hasNext()) {
				accumulator += functionOnU.getElementDoubleAbs(iterU.index)*
						Math.sin(coordinateU.getElementDoubleAbs(iterU.index)*
								coordinateX.getElementDoubleAbs(iterX.index));
			}
			dst.setAbs(iterX.index, accumulator);
		}
		return dst;
	}
	
	private Dataset doTophatConvolution(Dataset soq, Dataset q, double tophatWidthInQ) {

//    step_size = q[1]-q[0]
//    w = top_hat_width_in_Q/step_size
//    intw = int((2*np.round(w/2))+1) # do we need to make this an integer? 
//    # step through the points of the output array. We'll define the value of 
//    # each, one by one. 
//    result = np.zeros(shape(soq))
//    w = float(intw)
//    intn = shape(soq)[0]

		double dQ = q.getDouble(1) - q.getDouble(0);
		// tophat function width in grid points
		double w = tophatWidthInQ/dQ;
		// Round to nearest odd integer
		w = (2*Math.floor(w/2+0.5) + 1);
		// Dataset to hold the result
		Dataset hatted = DoubleDataset.zeros(soq);		
		// Length of the array
//		int intn = soq.getShape()[0];
		int intn = soq.getSize();
		
//
//    soq_extended = np.append(soq,ones(w+1)*soq[-1])
//	  soq_extended = np.insert(soq_extended,0,ones(w+1)*soq[0])
//	  # now we have w points extra on the start and the end. 

		// Add an extra w(+1?) points on the end and start of soq
		Dataset wExtension = new DoubleDataset((int) w+1);
		Dataset soqX = DatasetUtils.append(soq, Maths.add(wExtension, soq.getDouble(intn-1)), 0);
		soqX = DatasetUtils.append(Maths.add(wExtension, soq.getDouble(0)), soqX, 0);
//
//    oneovertwowplus1cubed = np.power(2*float(w)+1,-3)
		double oneOver2Plus1Cubed = Math.pow(2*w+1, -3);

//	    	    
//    for intr in range(intw,intn+intw):
//	    	        
//        c_range = np.array([intr-intw,intr+intw])
//        c_range = c_range.clip(0,1000000).astype(int)
//	    	        
//        r = float(intr)
//
//        intc = np.arange(c_range[0],c_range[1]+1)
//	    	        
//        c = intc.astype('float')
//        last_bit = 3*c*(4*np.square(c)+4*np.square(r)-np.square(2*w+1)+1)/2/r
//        c_use = c > w - r # is true for the long equation. 
//
//
//        prefactor = (2-integerKroneckerDelta(c,0.0)) * ~c_use + 1.0 * c_use
//	    	        
//	      weighting = prefactor*(12*np.square(c)-1-c_use*last_bit)*oneovertwowplus1cubed
//
//	      try:
//	          result[intr-intw] = sum(weighting*soq_extended[intc-1])
		for (long intr = Math.round(w); intr < intn+w; intr++) {
			// This seems the most complex tophat convolution in the history of numerical analysis.
			// I cannot understand it, I can only duplicate it.
			
			Dataset c = DoubleDataset.createRange(intr-w, intr+w+1, 1);
			double r = intr;
			// No mathematical operator overloading ;_;			
			Dataset leadingFactor = Maths.multiply(3.0/2.0, Maths.divide(c, r));
			Dataset bracketed = Maths.multiply(4, Maths.square(c));
			bracketed.iadd(4*r*r+1);
			bracketed.isubtract(Math.pow(2*w+1, 2));
			Dataset lastBit = Maths.multiply(leadingFactor, bracketed);
			
			// c_use in the python seems to only ever be False for the first element during the first iteration
			// Use 1 and 0 instead of true and false
			int firstValid = DatasetUtils.findIndexGreaterThan(c, w-r);
			Dataset cUse = DoubleDataset.ones(c);
			Dataset prefactor = DoubleDataset.ones(c);
			for (int i = 0; i < firstValid; i++) {
				// TODO: Find a better way to do this
				cUse.set(0.0, i);
				// prefactor is 1.0 when cUse is true; no change
				//              1.0 when cUse is false and c==0.0
				//				2.0 when cUse is false and c!=0.0
				if (c.getDouble(i) != 0.0) {
					prefactor.set(2.0, i);
				}
			}
			Dataset weighting = Maths.multiply(
					prefactor.imultiply(oneOver2Plus1Cubed),
					Maths.subtract(
							Maths.square(c).imultiply(12),
							Maths.multiply(cUse, lastBit).iadd(1)
							)
					);
			// The actual convolution. Cannot index a Dataset by an IntegerDataset?
			double convSum = 0.0;
			for (int i=0; i < c.getSize(); i++) {
				convSum += weighting.getDouble(i)*soqX.getDouble(c.getInt(i)-1);				
			}
			hatted.set(convSum, (int) Math.round(intr-w)); 
		}
		
		// Error propagation: error on hatted
		if (soq.getError() != null) {
			// Pass-through
			hatted.setError(soq.getError());
		}
		
//        obj.th_DprimedoQ = obj.soq - XPDFFT.topHatConvolutionSubtraction(obj.Q,\
//                obj.soq,tophatwidth)
		Dataset result = Maths.subtract(soq, hatted);
		copyMetadata(soq, result);
		// Error propagation: assume that hatted has a valid error iff soq does
		if (soq.getError() != null) {
			result.setError(Maths.sqrt(Maths.add(Maths.square(soq.getError()), Maths.square(hatted.getError()))));
		}
		
		return result;
		
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFTophatOperation";
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
