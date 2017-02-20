package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;


import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.peakfinding.MexicanHatWavelet;

/**
 * 
 * 
 * Performs a continuous wavelet transformation peak finding algorithm. 
 * 
 * This requires a wavelet function to base of the peak finding. 
 * In this a Ricker(MexicanHat) Wavelet is used. 
 * 
 * Increasing widths will comb for more peaks and decreasing tends to decrease peaks.
 * 
 * Works best for finding peak with initial sharp area and irregular patterns. 
 * For example mass spectrometry data or powder diffraction data. 
 * Where a sharp initial peak area is present.
 * As oppose to most peak finding algorithms being adept to sinusoidal consistent market applications. 
 * 
 * NOTE: Peak finding is tricky. To find the RIGHT peaks in the RIGHT place is difficult to categorise. 
 * These solutions are only estimates considering the incoming data.
 * 
 * The defaults being used are based of python.scipy.waveletPeakFInd: 
 * -gapThresh = first of widths[0] 
 * -maxDistances = widths/ 4.0 
 * -wavelet -> ricket (because thats the one that has been implemented )
 * 
 * 
 * References - 
 *  [1] Bioinformatics (2006) 22 (17): 2059-2065.
 *       :doi:`10.1093/bioinformatics/btl355`
 *       http://bioinformatics.oxfordjournals.org/content/22/17/2059.long
 * 
 * @author Dean P. Ottewell
 *
 * 
 * 
 * TODO'S
 * TODO: this might be due to a ignore of the first unit... there is a initial added 0,0 that is passed that should not be analysed
 * TODO: fixed the algorithm is probably horribly ineffienct. Move to lazy dataset and point at problem areas. Perhaps functionalize to free up whats loaded.
 * TODO: FUNCTIONALISE REFACTOR - I say that lots, but what I mean is those functions for filtering and generating a matrix got out of hand. 
 * 
 */
public class WaveletTransformPeakFinds extends AbstractPeakFinder {

	public final static String NAME = "WaveletTransformPeaks";

	/*
	 * The width size the convulation of signal data will step through. 
	 * 
	 * 1 to n.
	 */
	private double widthSzParam;
	
	private double minSNR;// =1.0;
	private double noisePerc;//=10;
	private double minLength;
	private double gapThresh; 
	
	/*
	 * Ridgeline class structure to help with manipulation understanding
	 */
	class RidgeLine {
		public List<Integer> row = new ArrayList<Integer>();
		public List<Integer> col = new ArrayList<Integer>();
		public double gapVal = 0;
	}
	
	public void setWidthParam(double widthParam) {
		this.widthSzParam = widthParam;
	}

	@Override
	protected void setName() {
		this.name = NAME;
	}
	
	public void loadParam() {
		try {
			widthSzParam = (double) getParameterValue("widthSz");
			minSNR = (double) getParameterValue("minSNR");
			noisePerc = (double) getParameterValue("noisePerc");
			minLength = (double) getParameterValue("minLength");
			gapThresh = (double) getParameterValue("gapThresh");
		} catch (Exception e) {
			logger.error("Could not find specified peak finding parameters");
		}
	}

	
	public WaveletTransformPeakFinds() {
		super();
		try {
			initialiseParameter("widthSz", false, 1.0);
			initialiseParameter("minSNR", false, 1.0);
			initialiseParameter("noisePerc", false, 10.0);
			initialiseParameter("minLength", false, 0.0);
			initialiseParameter("gapThresh", false, 2.0);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Problem initialising " + this.getName() + " peak finder: e");
		}
		loadParam();
	}

	/**
	 * 
	 *  Performs a continuous wavelet transform. Components being a conv of wavelet data dependent on width[i] with the original data.
	 * 
	 * @param xData axis
	 * @param yData axis (match xData shape)
	 * @param widths to convolve over
	 * 
	 * 
	 * @return Dataset of  2D (0 - col, 1 - row) matrix to id ridge lines on. 
	 */
	public Dataset countinousWaveletTransform(IDataset yData, IDataset widths) {

		int dataSz = yData.getSize();
		Dataset output = DatasetFactory.zeros(new int[] { widths.getSize(), dataSz }, Dataset.FLOAT64);
		
		for (int width = 0; width < widths.getSize(); ++width) {

			double w = 10 * widths.getDouble(width);  //widths[width];
			double nPoints = dataSz > w ? w : yData.getSize();

			MexicanHatWavelet wave = new MexicanHatWavelet(nPoints, widths.getDouble(width));//widths[width]);

			Dataset points = DatasetFactory.createRange(nPoints);
			Dataset waveletData = wave.calculateValues(points);

			//TODO: were missing a initial point as seen in the python implementation. 
			//This is a problem with the convoleTOSameShape function. Flat line should convolve...
			//However, zero points does work according to the unit tests...
			Dataset convl = Signal.convolveToSameShape((Dataset) yData, waveletData, null); // might get away with null..
				
			output.setSlice(convl, new Slice(width,width+1));
		}

		return output;
	}

	/**
	 * Creates a 2D relative extreme matrix determining which points could be considered extremes
	 * 
	 * @param mtx 2D dataset
	 * @param axis to determine the relative extremes on
	 * 
	 * @return boolean matrix that is True with each point classed as a extrema, Shape matches @param mtx
	 */
	private Dataset boolRelativeExtrema(Dataset mtx, int axis) {
		int order = 1; //The windows size to check against 
		int size = mtx.getShape()[axis];
		Dataset locs = DatasetFactory.createRange(size, Dataset.INT64);

		Dataset resultsMtx = DatasetFactory.ones(mtx, Dataset.INT64);
		Dataset main = DatasetUtils.take(mtx,locs, axis);

		for (int shift = 1; shift < order + 1; ++shift) {
			
			Dataset above = DatasetUtils.take(mtx, Maths.add(locs,shift),  axis);

			Dataset below = DatasetUtils.take(mtx, Maths.subtract(locs, shift), axis); // TODO:
			
			// Compare values and set true results value true -
			Dataset cmp = null;
			cmp = Comparisons.greaterThan(main, above);
			resultsMtx = Maths.bitwiseAnd(resultsMtx, cmp.cast(Dataset.INT64));

			cmp = Comparisons.greaterThan(main, below);
			resultsMtx = Maths.bitwiseAnd(resultsMtx, cmp.cast(Dataset.INT64));

//			if (Maths.bitwiseInvert(resultsMtx).any())
//				return resultsMtx;
		}

		return resultsMtx;
	}

	/**
	 * @param convolveMtx
	 *            2D (0 - col, 1 - row) matrix to id ridge lines on. THis is
	 *            typically the result of the convolution of wavelet and signal
	 *            data
	 * @param maxDistances
	 *            between points to connect the dots in becoming lines. Must
	 *            have at least as many rows as input @param convoleMtx
	 * @param gapThresh
	 *            the threshold by which a connection between ridge lines would not be made
	 *
	 * @return needs to return back a set of x,y coords but also there is a gap
	 *         number given that is then used in filterRidgesToPeaks
	 *         
	 */
	private List<IDataset> identifyRidgeLines(Dataset convolveMtx, IDataset maxDistances, double gapThresh) {

		/*Gather extrame based on neighbour values away from a given order*/
 		Dataset allRelMaxCols = boolRelativeExtrema(convolveMtx, 1);

		int startRow = allRelMaxCols.getShape()[0] -1; 

		List<RidgeLine> ridgeLines = new ArrayList<RidgeLine>();

		Dataset ridgeStartSlice = allRelMaxCols.getSlice(new Slice(startRow,startRow+1));	
		
		/*
		 * Generate a slice based on the starting point. Based on these create starting ridges to then be adjusted and filtered on.
		 * */
		IndexIterator all = ridgeStartSlice.getIterator();
		while (all.hasNext()) {
			int pos = all.index;
			
			if (ridgeStartSlice.getElementBooleanAbs(pos)) {
				RidgeLine ridge = new RidgeLine();
				ridge.row.add(startRow);
				ridge.col.add(all.index);
				ridgeLines.add(ridge);
			}
		}
		
		
		List<RidgeLine> finalRidges = new ArrayList<RidgeLine>();

		Dataset rows = (startRow > 1) ? DatasetFactory.createRange(startRow, -1, -1,Dataset.INT64) : DatasetFactory.createRange(0);
		Dataset cols = convolveMtx.all(0).getIndices();

		/*
		 * The bulk of the conditioning. Will go through each ridge line attempting to connect with neighbours or other rows.
		 * 
		*/
		IndexIterator rowItr = rows.getIterator();
		while (rowItr.hasNext()) {
			
			int rowIdx = rows.getInt(rowItr.index);
			
			//Get all true max vol
			Dataset thisRow = allRelMaxCols.getSlice(new Slice(rowIdx, rowIdx+1));
			Dataset thisMaxCols = cols.getByBoolean(thisRow);

			// Increment all gap values
			for (RidgeLine lineIt : ridgeLines) {
				lineIt.gapVal += 1;
			}

			// XXX these should always be allMaxCols[row]
			List<Integer> prevRidgesCols = new ArrayList<Integer>();
			for (RidgeLine lineItr : ridgeLines) {
				List<Integer> ridgeCols = lineItr.col;
				int col = ridgeCols.get(ridgeCols.size()-1);
				prevRidgesCols.add(col);
			}

			// Look through every relative maximum found at current row
			// attempt to connect them with existing ridge lines
			IndexIterator colItr = thisMaxCols.getIterator();
			while (colItr.hasNext()) {
				// if previous ridge line within the maxDistance to connect to,
				// do so otherwise start a new one
				RidgeLine line = null;
				int colIdx = thisMaxCols.getInt(colItr.index);
				
				if (prevRidgesCols.size() > 0) {
					List<Integer> diffsLst =  new ArrayList<Integer>();
					for (int i = 0; i < prevRidgesCols.size(); ++i){
						int diff = Math.abs(colIdx - prevRidgesCols.get(i));
						diffsLst.add(diff);
					}
					
					int closest =  diffsLst.indexOf(Collections.min(diffsLst));
					
					if (diffsLst.get(closest) <= maxDistances.getDouble(rowIdx)) {
						line = ridgeLines.get(closest);
					}
				}

				if (line != null) {
					// Found a point close enough extend current ridge lines
					line.col.add(colIdx);
					line.row.add(rowIdx);
					line.gapVal = 0;
				} else {
					// newLine=[[row]
					RidgeLine nLine = new RidgeLine();
					nLine.col.add(colIdx);
					nLine.row.add(rowIdx);
					nLine.gapVal = 0;
					ridgeLines.add(nLine);
				}
			}

			// Remove the ridge lines with gapNumber too high
			// XXX modifying a list whilst iterating
			for (int i = ridgeLines.size()-1; i > 0; --i) {
				RidgeLine line = ridgeLines.get(i);
				if (line.gapVal > gapThresh) {
					finalRidges.add(line);
					ridgeLines.remove(i);
				}
			}
		}

		List<RidgeLine> entireLines = new ArrayList<RidgeLine>();
		entireLines.addAll(finalRidges);
		entireLines.addAll(ridgeLines);
		
		List<IDataset> output = new ArrayList<IDataset>();
 		
		/*
		 * Filter out low gapValue lines and 
		 * format these ridglines into output.
		 * */
		for(RidgeLine line : entireLines){
	
			List<Integer> sortArgs = new ArrayList<Integer>(line.row);
			Collections.sort(sortArgs);	

			Dataset ridgeLine = DatasetFactory.zeros(new int[]{sortArgs.size(),2}, Dataset.FLOAT64);
			
			//TODO: not the most efficent, just needed them to be ordered for filtering later
			for (int i = 0; i < sortArgs.size(); ++i){
				
				int rVal = sortArgs.get(i);
				int idxOrg = line.row.indexOf(rVal);
				int cVal = line.col.get(idxOrg); 
				
				ridgeLine.set(rVal, i, 0);
				ridgeLine.set(cVal, i, 1);
			}

			output.add(ridgeLine);
		}
		
		return output;
	}

	/**
	 * 
	 * Take peaks from the ridges top value
	 * 
	 * RidgeLines -> line -> Points -> x,y,gap val
	 * 
	 * 
	 *@param mtx
	 *            2D (0 - col, 1 - row) matrix to id ridge lines on. THis is
	 *            typically the result of the convolution of wavelet and signal
	 *            data.
	 *
	 * @param ridges
	 * 		Sequence of ridges contains 2 sequences of the respective rows and columns that creates the ridge line respectively. 
	 * 
	 * @param windowSz
	 * 		Size of window to use to calculate noise floor. 
	 * 		Default is col /20 -> TODO: why?
	 * 
	 * @param minLength
	 * 		min length a ridge line need to be acceptable.
	 * 
	 * @param minSignalNoiseRatio
	 * 		Min signal to noise ratio. The signal is a value of the cwt matrix at the shortest length scale (cwt[0.loc]) the 
	 * 		noise the 'noise perc'th percentiles of data points contained within a window of 'window_size' around 'cwt[0,loc]'	
	 * 
	 * @param noisePerc
	 * 		When calculating the noise floor, percentile of data points examined below which to consider noise. 
	 * 		Calculated using HOPEFULLY apache math3 percentile functions.
	 * 
	 * 
	 * 
	 * @return List of 2D dataset (0 - col, 1 - row) with respect to the original @mtx 
	 */
	private List<IDataset> filterRidgesToPeaks(Dataset mtx, List<IDataset> ridges, int windowSz, double minLength, double minSignalNoiseRatio, double noisePerc) {
		
		//Conditions generated based on parameter input
		int numPoints = mtx.getShape()[1];
		int odd = windowSz % 2;
		int hfWindow = windowSz / 2;
		
		Dataset startRow = mtx.getSlice(new Slice(0, 1)).flatten();
		Dataset noises = DatasetFactory.zeros(startRow, Dataset.FLOAT64);
		
		/*
		 * Generate noise at slices about rows and on the windowsSz given
		 * */
		IndexIterator itr= startRow.getIterator();
		while(itr.hasNext()){
			int idx = itr.index;
			
			int windowStart= Math.max(idx - hfWindow, 0);
			int windowEnd = Math.min(idx + hfWindow + odd, numPoints);

			Dataset sliceWindow = startRow.getSlice(new Slice(windowStart,windowEnd));
			
			double[] args = (double[]) DatasetUtils.createJavaArray(sliceWindow);
			
			//Calculate percentile based on this slice. This can be compared later to see if this area was too low to be considered significant.
			double score = new Percentile().evaluate(args, noisePerc);
	
			noises.set(score, idx);
			
		}
		
		/*
		 * Based on noises parameter filter out ridges.
		 * */
		List<IDataset> filteredRidges = new ArrayList<IDataset>();
		for(int i = 0; i < ridges.size(); ++i){
			IDataset ridgeLine = ridges.get(i);
			if (isFilteredRidgeAPeak(mtx, ridgeLine, noises, minLength, minSignalNoiseRatio)){
				filteredRidges.add(ridgeLine);
			}
		}
		
		return filteredRidges;
	}
	
	
	
	/**
	 * 
	 * Check against value and conditions to see if could consider point a peak.
	 * 
	 * @param mtx
	 * 		2D (0 - col, 1 - row) matrix to id ridge lines on. This is
	 * 		typically the result of the convolution of wavelet and signal data.
	 * 
	 * @param ridgeLine
	 * 		Sequence of ridges contains 2 sequences of the respective rows and columns that creates the ridge line respectively. 
	 * 
	 * 
	 * @param noises
	 * 		1D set of noise values produced based on @param mtx(1) 
	 * 
	 * @param minLength
	 * 		min lenght a ridge line need to be acceptable.
	 * 
	 * @param minSignalNoiseRatio
	 * 		Min signal to noise ratio. The signal is a value of the cwt matrix at the shortest length scale (cwt[0.loc]) the 
	 * 		noise the 'noise perc'th percentiles of data points contained within a window of 'window_size' around 'cwt[0,loc]'	
	 * 
	 * 
	 * @return 
	 */
	private Boolean isFilteredRidgeAPeak(IDataset mtx, IDataset ridgeLine, IDataset noises, double minLength, double minSignalNoiseRatio){

		if ((ridgeLine.getShape()[0]) < minLength){
			return false;
		}
		
		//TODO: need to take a better slice of the noise mtx
		int c = ridgeLine.getInt(0,0);
		int r = ridgeLine.getInt(0,1);
		
		double signal =  mtx.getDouble(c,r);
		double noise = noises.getDouble(ridgeLine.getInt(0,1));
		double snr  = Math.abs( signal/noise);

		if (snr < minSignalNoiseRatio){
			//Not a significant enough noise val to be considered a peak
			return false;
		}
		
		return true;
	}
	
	
	
	
	@Override
	public Map<Integer, Double> findPeaks(IDataset xData, IDataset yData, Integer maxPeaks) {
		//TODO: shouldnt be reloading params inside find peaks...
		loadParam();
		
		//Generate width set to step through for convoluation 
		IDataset widths = DatasetFactory.createRange(1.0, widthSzParam+1.0, 1.0, Dataset.FLOAT64);
		IDataset maxDis = Maths.divide(widths, 4.0);
		
		// Pass over data given width and produce waveletConvolve
		Dataset mtx = countinousWaveletTransform(yData, widths);
		
 		List<IDataset> ridges = identifyRidgeLines(mtx, maxDis,gapThresh);
		
		if (minLength == 0)
			minLength = Math.ceil(mtx.getShape()[0] /4.0);
		
		
		int windowSz = (int) Math.ceil((double) mtx.getShape()[1] / 20.0);
		
		List<IDataset> ridgesFiltered = filterRidgesToPeaks(mtx, ridges, windowSz, minLength, minSNR, noisePerc);
		
		Map<Integer, Double> peaks = new TreeMap<Integer,Double>();
		for(IDataset ridge : ridgesFiltered){
			int peakPos = ridge.getInt(0,1);
			peaks.put(peakPos, yData.getDouble(peakPos));
		}
		
		return peaks;
	}

}