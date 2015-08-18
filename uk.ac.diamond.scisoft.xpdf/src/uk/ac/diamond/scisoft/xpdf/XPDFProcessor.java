package uk.ac.diamond.scisoft.xpdf;
//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

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
	XPDFBeamData beamData;
	XPDFTargetComponent sampleData;
	List<XPDFTargetComponent> containersData;
	
	
	public XPDFProcessor(){
		this.gofr = null;
		this.dofr = null;
		this.intermediateResults = new HashMap<String, Dataset>();

		beamData = null;
		sampleData = null;
		containersData = new ArrayList<XPDFTargetComponent>();
		
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

		XPDFMetadata theXPDFMetadata = null;
		// Get the metadata to fill the object.
		try {
			if (input.getMetadata(XPDFMetadata.class) != null &&
					!input.getMetadata(XPDFMetadata.class).isEmpty() && 
					input.getMetadata(XPDFMetadata.class).get(0) != null )
			theXPDFMetadata= input.getMetadata(XPDFMetadata.class).get(0);
		} catch (Exception e) {
		}
		this.beamData = theXPDFMetadata.getBeam();
		this.containersData = theXPDFMetadata.getContainers();
		this.sampleData = theXPDFMetadata.getSample();
		
		
//		// Set up the axes for the independent var
//		ILazyDataset[] axes = AbstractOperation.getFirstAxes(input);
//		IDataset ya = null;
//		if (axes != null && axes[0] != null) 
//			ya = axes[0].getSlice().squeeze();
//
//		this.setQ(DatasetUtils.convertToDataset(ya));
//		
//		
//		
		// TODO: Move to sample
		this.g0Minus1 = 0.522718594884;
		this.massDensity = 7.65;
		this.numberDensity = 0.08030;
		// TODO: Move to beam data
		this.beamData_energy = 76.6;
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

	static public Dataset getQFromMetadata(IDataset xpdfData) {
		final double hc_qe = 12.39841974;//(17)

		Dataset tthd = null;
		List<AxesMetadata> axesList = null;
		try {
			axesList = xpdfData.getMetadata(AxesMetadata.class);
			if (axesList == null || axesList.isEmpty())
				return null;
		} catch (Exception e) {
			return null;
		}
		if (axesList.get(0) != null && axesList.get(0).getAxes()[0] != null) {
			tthd = DatasetUtils.convertToDataset(axesList.get(0).getAxes()[0].getSlice());
		}
		
		Dataset q;
		try {
			 q = Maths.multiply(4*Math.PI*xpdfData.getMetadata(XPDFMetadata.class).get(0).getBeam().getBeamEnergy()/hc_qe, Maths.sin(Maths.multiply(0.5*Math.PI/180.0, tthd)));
		} catch (Exception e) {
			q = DoubleDataset.zeros(tthd);
		}
		return q;
	}
	
	public static void copyXPDFMetadata(IDataset oldDataset, Dataset newDataset, boolean copyXAxis) {

		// XPDF metadata copy.
		try {
			if (oldDataset.getMetadata(XPDFMetadata.class) != null &&
					!oldDataset.getMetadata(XPDFMetadata.class).isEmpty() &&
					oldDataset.getMetadata(XPDFMetadata.class).get(0) != null)
				newDataset.setMetadata(oldDataset.getMetadata(XPDFMetadata.class).get(0).clone());
		} catch (Exception e) {
			; // Do nothing
		}
		if (copyXAxis) {
			try {
				if (oldDataset.getMetadata(AxesMetadata.class) != null &&
						!oldDataset.getMetadata(AxesMetadata.class).isEmpty() &&
						oldDataset.getMetadata(AxesMetadata.class).get(0) != null) {
					newDataset.addMetadata(oldDataset.getMetadata(AxesMetadata.class).get(0).clone());
				}
			} catch (Exception e) {
				; // Do nothing
			}
		}
	}

	public void setLorchWidth(double lorchWidth) {
		this.lorchWidth = lorchWidth;
	}

	public void setTophatWidth(double tophatWidth) {
		this.tophatWidth = tophatWidth;
	}

	// Get the r coordinate from a Dataset.
	public static Dataset getR(Dataset fofr) {

		Dataset r = DoubleDataset.zeros(fofr); 
		try {
			if (fofr.getMetadata(AxesMetadata.class) != null &&
					!fofr.getMetadata(AxesMetadata.class).isEmpty() &&
					fofr.getMetadata(AxesMetadata.class).get(0) != null) {
				r = DatasetUtils.convertToDataset(fofr.getMetadata(AxesMetadata.class).get(0).getAxes()[0]);
			}
		} catch (Exception e) {
			;
		}
		return r;
	}

}
