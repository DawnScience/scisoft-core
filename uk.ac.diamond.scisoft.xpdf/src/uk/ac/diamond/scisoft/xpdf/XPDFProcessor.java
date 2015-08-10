package uk.ac.diamond.scisoft.xpdf;
//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFContainerMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFContainersMetadataImpl;

public class XPDFProcessor {

	// End results
	Dataset gofr;
	Dataset dofr;
	
	// Intermediate results are held in a map
	Map<String, Dataset> intermediateResults;
	
	double lorchWidth;
	double tophatWidth;
	
	// interatomic separation 
	double rMin;
	Dataset r;
	
	// momentum transfer
	Dataset q;
	
	// TODO: Move to sample
	double g0Minus1;
	double numberDensity;
	double massDensity;
	
	// TODO: Move to beam data
	double beamData_energy;
	
	// Additional data
	BeamData beamData;
	TargetComponent sampleData;
	List<TargetComponent> containersData;
	
	
	public XPDFProcessor(){
		this.gofr = null;
		this.dofr = null;
		this.intermediateResults = new HashMap<String, Dataset>();

		beamData = null;
		sampleData = null;
		containersData = new ArrayList<TargetComponent>();
		
		this.lorchWidth = 0.2;		
		this.tophatWidth = 3.0;
		
		// TODO: Move to sample
		this.g0Minus1 = 0.522718594884;
		this.massDensity = 7.65;
		this.numberDensity = 0.08030;
		// TODO: Move to beam data
		this.beamData_energy = 76.6;
	}
	
	// Initialize the processor from the XPDF dataset, including its metadata
	public XPDFProcessor(IDataset input) {
		this.gofr = null;
		this.dofr = null;
		this.intermediateResults = new HashMap<String, Dataset>();

		// Get the metadata to fill the object.
		try {
			beamData = new BeamData(input.getMetadata(XPDFBeamMetadata.class).get(0));
		} catch (Exception e) {
			beamData=null;
		}
		try {
			sampleData = new TargetComponent(input.getMetadata(XPDFTargetComponentMetadata.class).get(0));
		} catch (Exception e) {
			sampleData = null;
		}
		// The XPDFProcessing Op is the end of the line for the original data, so it is subsumed into the objects.
		sampleData.setTraceCounts(input.getSliceView());
		
		containersData = new ArrayList<TargetComponent>();
		XPDFContainerMetadata containersList;
		try {
			containersList = input.getMetadata(XPDFContainerMetadata.class).get(0);
		} catch (Exception e) {
			containersList = new XPDFContainersMetadataImpl();
		}
		for (int i = 0; i < containersList.size(); i++) {
			TargetComponent container = new TargetComponent((XPDFTargetComponentMetadata) containersList.getContainer(i));
			containersData.add(container);
		}
		
		// Set up the axes for the independent var
		ILazyDataset[] axes = AbstractOperation.getFirstAxes(input);
		IDataset ya = null;
		if (axes != null && axes[0] != null) 
			ya = axes[0].getSlice().squeeze();

		this.setQ(DatasetUtils.convertToDataset(ya));
		
		
		
		// TODO: Move to sample
		this.g0Minus1 = 0.522718594884;
		this.massDensity = 7.65;
		this.numberDensity = 0.08030;
		// TODO: Move to beam data
		this.beamData_energy = 76.6;
	}
	
	
	public IDataset getGofr() {
		if (gofr == null) {
			this.calculateDofrGofr();
		}
		return this.gofr;
	}

	public IDataset getDofr() {
		if (dofr == null) {
			this.calculateDofrGofr();
		}
		return this.dofr;
	}

	private void calculateDofrGofr() {
		if (!intermediateResults.containsKey("hofr") ||
				intermediateResults.get("hofr") == null){
			intermediateResults.put("hofr", doLorchFT());
		}
        // obj.Gofr = obj.hofr/obj.g_minus_1
		this.gofr = Maths.divide(intermediateResults.get("hofr"), this.g0Minus1);
        // obj.Dofr = obj.Gofr*obj.r*4*np.pi*obj.number_density
		this.dofr = Maths.multiply(Maths.multiply(this.gofr, this.r), 4*Math.PI * this.numberDensity);
		
	}

	private Dataset doLorchFT() {
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
		if (!intermediateResults.containsKey("th_soq") ||
				intermediateResults.get("th_soq") == null) {
			intermediateResults.put("th_soq", doTopHatConvolutionAndSubtraction(tophatWidth));
		}
		
		
		Dataset output = DoubleDataset.zeros(this.r);
		Dataset qhq = Maths.multiply(this.q, intermediateResults.get("th_soq"));
		Dataset qd = Maths.multiply(this.q, this.lorchWidth);
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
						2 * Math.pow(Math.PI, 2) * this.numberDensity,
						r)));
		
	    return output;
	}

	private Dataset doTopHatConvolutionAndSubtraction(double tophatWidth) {
		if (!intermediateResults.containsKey("DprimedoQ") ||
				intermediateResults.get("DprimedoQ") == null ) {
			intermediateResults.put("DprimedoQ", doTophatConvolution(tophatWidth));
		}
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
		Dataset DPrimedoQ = intermediateResults.get("DprimedoQ");
		Dataset thDofr = fourierQtoR(this.q, DPrimedoQ, this.numberDensity, this.r);
		Dataset rTophat = Maths.multiply(this.r, tophatWidth);
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
			thBofr.set(thDofr.getDouble(i) + this.g0Minus1, i);
		}
		Dataset thBoq = fourierRtoQ(this.r, thBofr, this.numberDensity, this.q);
		Dataset thSoq = Maths.subtract(DPrimedoQ, thBoq);
		thSoq.set(0.0, 0);
		
		return thSoq;
	}

	private Dataset fourierQtoR(Dataset q, Dataset fQ,
			double numberDensity, Dataset r) {
//	output = np.zeros(shape(r))
//	qhq = q*soq
//	for i in range(0,size(r)):
//	    output[i] = (np.sin(q*r[i])*qhq).sum()
//	output = output*(q[3]-q[2])*np.power(2.0*np.square(pi)*rho*r,-1)
		Dataset output = DoubleDataset.zeros(this.r);
		Dataset qhq = Maths.multiply(q, fQ);
		for (int i = 0; i<r.getSize(); i++){
			output.set(Maths.multiply(
					Maths.sin(Maths.multiply(q, r.getDouble(i))),
					qhq).sum(), i);
		}
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
		Dataset output = DoubleDataset.zeros(this.q);
		Dataset rhr = Maths.multiply(r, fR);
		for (int i = 0; i<q.getSize(); i++){
			output.set(Maths.multiply(
					Maths.sin(Maths.multiply(r, q.getDouble(i))),
					rhr).sum(), i);
		}
		output.imultiply(Maths.divide((r.getDouble(3) - r.getDouble(2))*4.0*Math.PI*numberDensity, q));
		return output;
	}

	private Dataset doTophatConvolution(double tophatWidthInQ) {
		if (!intermediateResults.containsKey("soq") ||
				intermediateResults.get("soq") == null) {
			intermediateResults.put("soq", doSelfScatteringNormalise());
		}
		Dataset soq = intermediateResults.get("soq");

//    step_size = q[1]-q[0]
//    w = top_hat_width_in_Q/step_size
//    intw = int((2*np.round(w/2))+1) # do we need to make this an interger? 
//    # step through the points of the output array. We'll define the value of 
//    # each, one by one. 
//    result = np.zeros(shape(soq))
//    w = float(intw)
//    intn = shape(soq)[0]

		double dQ = this.q.getDouble(1) - this.q.getDouble(0);
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
		
//        obj.th_DprimedoQ = obj.soq - XPDFFT.topHatConvolutionSubtraction(obj.Q,\
//                obj.soq,tophatwidth)
		return Maths.subtract(soq, hatted);
	}

	// Self scattering, and normalisation, derived entirely from intermediate results
	private Dataset doSelfScatteringNormalise() {
//		if (!intermediateResults.containsKey("AbsCor") ||
//				intermediateResults.get("AbsCor") == null) {
//			intermediateResults.put("AbsCor", doSelfScatteringNormalise());
//		}

		Dataset absCor = intermediateResults.get("ABSCOR");
		Dataset selfScattering = intermediateResults.get("self_scattering");
		Dataset fSquaredOfX = intermediateResults.get("fsquaredofx");

		return Maths.divide(Maths.subtract(absCor, selfScattering), fSquaredOfX);
	}

	/**
	 * 	 For testing purposes, insert something into the intermediate results map
	 *	
	 * @deprecated Do not use in the final processing. Development use only.
	 */
	public void setIntermediateResult(String intermediateName, Dataset intermediate) {
		intermediateResults.put(intermediateName, intermediate);
	}

	public void setR(double rMin, double rMax, double rStep) {
		this.rMin = rMin;
		this.r = DoubleDataset.createRange(rStep/2, rMax, rStep);		
	}

	public Dataset getR() {
		return this.r;
	}

	public void setQ(Dataset twoThetaD) {
		final double hc_qe = 12.39841974;//(17)
		this.q = Maths.multiply(4*Math.PI*beamData.getBeamEnergy()/hc_qe, Maths.sin(Maths.multiply(0.5*Math.PI/180.0, twoThetaD)));
	}

	public void setLorchWidth(double lorchWidth) {
		this.lorchWidth = lorchWidth;
	}

	public void setTophatWidth(double tophatWidth) {
		this.tophatWidth = tophatWidth;
	}

}
