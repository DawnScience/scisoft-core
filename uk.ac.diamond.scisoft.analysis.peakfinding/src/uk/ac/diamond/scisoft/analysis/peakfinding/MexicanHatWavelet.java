package uk.ac.diamond.scisoft.analysis.peakfinding;


import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;

/**
 * Mexican wavelet (Ricker) Wavelet function discrete wavelet
 * A (1 - x^2/a^2) exp(-x^2/2 a^2), where A = 2/sqrt(3a)pi^1/4"
 * 
 * @author Dean P. Ottewell
 *
 *
 *TODO: Almost certain should extend APeak... then hav ethe top peak be the amplitude calucalted.
 *However, should the humps be classed as peaks too?
 *
 *TODO: Then after that should have a IWavelet - containg point num 
 *
 */
public class MexicanHatWavelet extends AFunction {

	private static final String NAME = "Mexican Hat";
	
	private static final String DESC = "A Mexican Hat (Ricker) Wavelet."
			+ "\n	A (1 - x^2/a^2) exp(-x^2/2 a^2), where A = 2/sqrt(3a)pi^1/4";
	
	private static final String[] PARAM_NAMES = new String[]{"width", "posn"};
	
	public MexicanHatWavelet(double numPoints,double width ){
		super(numPoints,width);
		getParameter(0).setValue(numPoints);
		getParameter(1).setValue(width);
	}
	
	@Override
	public double val(double... values) {
		double timePos = values[0];
		double w = getParameterValue(1);
		double tsq = timePos * timePos; //TODO: isnt Math.pow ineffecient?
		double variance = w * w;
		
		//the python version said this was a amplitude calulation, however it could be considered a constant...
		double c = 2 / (Math.sqrt(3 * w) * (Math.pow(Math.PI, 0.25))); 
		double mod = 1 - (tsq)/(variance);
		double gauss = Math.exp((-tsq) / (2 * variance));
		double val =  c * mod * gauss;
		
		return val; 
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		//Fills with coords based on data. Centered around a 0 point based on numPoints
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		
		
		double numPoints = getParameterValue(0);
		double w = getParameterValue(1);
		double variance = w * w;
		double c = 2 / (Math.sqrt(3 * w) * (Math.pow(Math.PI, 0.25)));
		
		while (it.hasNext()) {
			//TODO: this exits in a world where everything is 1D...
			double itVal = it.getCoordinates()[0];
			double nVal = itVal - (numPoints-1.0) / 2;
			
			double xsq = nVal*nVal;
			
			double nMod = 1 - (xsq)/(variance);
			
			double nGauss = Math.exp((-xsq) / (2 * variance));
			
			double nTotal =  c * nMod * nGauss;
			
			buffer[i++] = nTotal;

		}
	}

}
