package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Random;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

/**
 
 *         Automatic Multiscale Peak Detector
 *
 *         Best for noisy and periodic peak data. Will not work in other
 *         environments.
 *
 *         Having large plateau regions will result in less peaks detected,
 *         increasing regression strength could help with this or sectioning
 *         data.
 *
 *         Reference Paper: Scholkmann, F., Boss, J., & Wolf, M. (2012). An
 *         efficient algorithm for automatic peak detection in noisy periodic
 *         and quasi-periodic signals. Algorithms, 5(4), 588–603.
 *         http://doi.org/10.3390/a5040588
 *
 * XXX: when large datasets are passed (10000 points) peak finder no longer performs well. 
 * 
 * TODO: laxyDataset
 * 
 * @author Dean P. Ottewell
 *
 *
 */
public class AutoPeakFinder extends AbstractPeakFinder {

	private Integer fitDegree;

	private DoubleDataset detrendSignal;
	private DoubleDataset fitSignal;

	private final static String NAME = "Auto Peakfinder";

	@Override
	protected void setName() {
		this.name = NAME;
	}

	public DoubleDataset getDetrendSignal() {
		return detrendSignal;
	}

	public AutoPeakFinder() {
		super();
		try {
			initialiseParameter("fitDegree", true, 6);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Problem initialising " + this.getName() + " peak finder: e");
		}

		loadParam();
	}

	public void loadParam() {
		try {
			fitDegree = (Integer) getParameterValue("fitDegree");
		} catch (Exception e) {
			logger.error("Could not find specified peak finding parameters");
		}
	}

	// TODO: maxPeaks is also not used in AMPD, nPeaks is used no where!??!???
	@Override
	public Map<Integer, Double> findPeaks(IDataset xData, IDataset yData, Integer maxPeaks) {

		detrendSignal = detrendSignal(xData, yData, fitDegree);

		int wHeight = detrendSignal.getSize();
		int wWidth = (int) Math.ceil(wHeight / 2.0) - 1; // L is max window width

		DoubleDataset mpk = generateLMS(yData, wWidth, wHeight);

		// Row wise sum of LMS 
		DoubleDataset gammaMatrix = (DoubleDataset) mpk.sum(1);

		// Find global minimum in the gamma sum
		int pkLambda = gammaMatrix.argMin();

		// Pick submatrix from LMS matrix
		DoubleDataset subMatrix = (DoubleDataset) mpk.getSlice(new int[] { 0, 0 }, new int[] { pkLambda, wHeight },
				new int[] { 1, 1 });

		DoubleDataset stdResultsTest = (DoubleDataset) subMatrix.stdDeviation(0);

		Map<Integer, Double> peakPosnsSigs = new TreeMap<Integer, Double>();

		// Pick indices values where standard devision==0
		for (int j = 0; j < stdResultsTest.getSize(); ++j) {
			if (stdResultsTest.getDouble(j) == 0) {
				// This is a pick position add to pick list
				peakPosnsSigs.put(j, yData.getDouble(j));
			}
		}

		return peakPosnsSigs;
	}

	/**
	 * Produces a local maxima scalogram of data set given a window to decide if
	 * value is significant.
	 * 
	 * @param dataSeries
				1D Data set of values wish to create matrix based on
	 * @param windowWidth
	 * @param windowHeight
	 * 
	 * @return 2D matrix of values from 0 - 1 filtered on local max conditions.
	 */
	public DoubleDataset generateLMS(IDataset dataSeries, int windowWidth, int windowHeight) {
		DoubleDataset mpk = generateDistributedMatrix(windowWidth, windowHeight);
		// Generate LMS of the signal
		for (int k = 1; k <= windowWidth; ++k) {
			for (int i = k + 1; i < windowHeight - k + 1; ++i) {
				// Check if exists outside window kernal bounds
				if ((dataSeries.getDouble(i - 1) > dataSeries.getDouble(i - k - 1))
						&& (dataSeries.getDouble(i - 1) > dataSeries.getDouble(i + k - 1))) {
					// set matrix val to zero
					mpk.set(0, k - 1, i - 1);
				}
			}
		}
		return mpk;
	}

	/**
	 * Creates uniformed distributed matrix from 0.0, 1.0
	 * 
	 * Then increases on a constant factor of aplha=1.
	 * 
	 * @param width
	 * @param height
	 * @return 2D matrix values 0 - 1
	 */
	public DoubleDataset generateDistributedMatrix(int width, int height) {
		// Require a uniformly random number set r from [0,1] to begin
		DoubleDataset mpk = Random.rand(0.0, 1.0, new int[] { width, height });
		// TODO: link with alpha constant parameter instead
		DoubleDataset ones = DatasetFactory.ones(new int[] { width, height });

		mpk.iadd(ones);

		return mpk;
	}

	/**
	 * 
	 * Linearly detrend signal using a polyfit as regression method.
	 * 
	 * @param xData
	 * @param yData
	 * @return dataset based on yData peaks that resultant should be regressed. xData values ultimately the same so are not changed
	 */
	public DoubleDataset detrendSignal(IDataset xData, IDataset yData, int degree) {
		// TODO: use different ployfit this is this in review below 

		ApacheOptimizer optimizer = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		Polynomial ply = new Polynomial(3);
		try {
			optimizer.optimize(new Dataset[] { (Dataset) xData },yData,ply);
			optimizer.getData();
			IDataset testResults = optimizer.calculateValues();
			
		} catch (Exception e) {
			logger.debug("Could not detrend signal ", e);
			e.printStackTrace();
		}
		
		// Polyfit not fitting
		Polynomial fit = Fitter.polyFit(new Dataset[] { (Dataset) xData }, (Dataset) yData, 1e-15, degree);
		fitSignal = fit.calculateValues(xData);

		List<Double> dSig = new ArrayList<Double>();
		for (int i = 0; i < fitSignal.getSize(); ++i) {
			Double detr = yData.getDouble(i) - fitSignal.getDouble(i);
			dSig.add(detr);
		}

		return fitSignal;
	}

}
