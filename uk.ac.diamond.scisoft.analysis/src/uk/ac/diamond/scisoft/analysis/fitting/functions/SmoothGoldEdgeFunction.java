package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IDataBasedFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.optimize.ApachePolynomial;

public class SmoothGoldEdgeFunction extends AFunction implements
		IDataBasedFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String NAME = "Smooth Gold Edge";
	private static final String DESC = "y(x) = Ployline with tails";
	private static final String[] PARAM_NAMES = new String[]{"smoothing", "tail_proportion"};
	private static final double[] PARAMS = new double[]{5, 0.2};

	Dataset xds;
	Dataset yds;
	
	private transient DoubleDataset smoothed;
	private transient PolynomialSplineFunction polySplineFunction;
	private transient SimpleRegression lowerFit;
	private transient SimpleRegression upperFit;
	
	public SmoothGoldEdgeFunction() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- FWHM (full width at half maximum)
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public SmoothGoldEdgeFunction(double... params) {
		super(PARAMS.length);
		// make sure that there are 2 parameters, otherwise, throw a sensible error
		if (params.length != PARAMS.length) 
			throw new IllegalArgumentException("A SmoothGoldEdgeFunction requires 2 parameters, and it has only been given "+params.length);
		fillParameters(params);

		setNames();
	}

	public SmoothGoldEdgeFunction(IParameter... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) 
			throw new IllegalArgumentException("A SmoothGoldEdgeFunction requires 2 parameters, and it has only been given "+params.length);
		fillParameters(params);

		setNames();
	}
	
	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < PARAM_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAM_NAMES[i]);
		}
	}

	@Override
	public double val(double... values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setData(IDataset x, IDataset data) {
		this.xds = DatasetUtils.convertToDataset(x);
		this.yds = DatasetUtils.convertToDataset(data);
		try {
			// Smooth the data by the real ammount for the smoothed section of the process
			this.smoothed = (DoubleDataset) ApachePolynomial.getPolynomialSmoothed(xds, yds, (int) Math.round(getParameterValue(0)), 3);
			// Fit a polyline to this to allow for easy interpolation
			this.polySplineFunction = new LinearInterpolator().interpolate(new DoubleDataset(xds).getData(), smoothed.getData());
			
			lowerFit = new SimpleRegression();
			double lowerProp = xds.getShape()[0]*getParameterValue(1);
			for (int i = 0; i < lowerProp; i++ ) {
				lowerFit.addData(xds.getDouble(i), yds.getDouble(i));
			}
			lowerFit.regress();
			
			upperFit = new SimpleRegression();
			double upperProp = xds.getShape()[0]*(1.0-getParameterValue(1));
			for (int i = xds.getShape()[0]-1; i > upperProp; i-- ) {
				upperFit.addData(xds.getDouble(i), yds.getDouble(i));
			}
			upperFit.regress();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		
		double midpoint = (double) xds.mean();
		
		double lowerPredict = lowerFit.predict(xds.getDouble(0));
		double lowerEndValue = yds.getDouble(0);
		double lowerMatch = lowerPredict - lowerEndValue;
		double upperPredict = upperFit.predict(xds.getDouble(xds.getShape()[0]-1));
		double upperEndValue = yds.getDouble(yds.getShape()[0]-1);
		double upperMatch = upperPredict - upperEndValue;
		
		while (it.hasNext()) {
			
			double pos = coords[0];
			
			try {
				buffer[i] = polySplineFunction.value(pos);				
			} catch (Exception e) {
				if (pos < midpoint) {
					buffer[i] = lowerFit.predict(pos) - lowerMatch;
				} else {
					buffer[i] = upperFit.predict(pos) - upperMatch;
				}
			}
			i++;
		}
		
	}

}
