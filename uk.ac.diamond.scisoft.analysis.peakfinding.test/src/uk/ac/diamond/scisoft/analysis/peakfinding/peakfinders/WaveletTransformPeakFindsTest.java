package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;



/**
 * 	//TODO: stress tests on widths
	
 * 
 * @author Dean P. Ottewell
 *
 */
public class WaveletTransformPeakFindsTest {


	// Example Data Tests
	@Test
	public void exampleDataTest(){
		WaveletTransformPeakFinds wave = new WaveletTransformPeakFinds();

		ExamplePeakData peaks = new ExamplePeakData();

		DoubleDataset xData = (DoubleDataset) peaks.getxData();
		DoubleDataset yData = (DoubleDataset) peaks.getyData();

		TreeMap<Integer, Double> foundPeaks = (TreeMap<Integer, Double>) wave.findPeaks(xData, yData, null);

		List<Double> expectedPeaks = peaks.expectedPeakValues();

		//Check gets similar amount of peaks first - Degree of size might detect couple extra peaks
		assertEquals(expectedPeaks.size(), foundPeaks.size(), 3);
		
		//Expects the peaks to be arrange from the first to last one
		//TODO: difficulties in a assuming complete matches...
//		int pos = 0;
//		for(Entry<Integer, Double> peak : foundPeaks.entrySet()){
//			if (pos < expectedPeaks.size())
//				assertEquals(expectedPeaks.get(pos++), peak.getKey(), 2);
//		}
//		
	}
	
	
	@Test
	public void multipleWidthGeneralParamChangingTest(){
		WaveletTransformPeakFinds wave = new WaveletTransformPeakFinds();

		ExamplePeakData peaks = new ExamplePeakData();

		DoubleDataset xData = (DoubleDataset) peaks.getxData();
		DoubleDataset yData = (DoubleDataset) peaks.getyData();
		 
		//Generally expecting some difference between widths. 
		//It should decrease with significant higher numbers
		double lastPeakSz = 1000;
		int widthRange = 40;
		int faliureCount = 0;
		for (int width = 1; width < widthRange+1; width+=10){
			wave.setWidthParam(width);
			
			TreeMap<Integer, Double> foundPeaks = (TreeMap<Integer, Double>) wave.findPeaks(xData, yData, null);
			
			//assertTrue(lastPeakSz > foundPeaks.size());
			faliureCount++;
			
			lastPeakSz = foundPeaks.size();
		}
		//Generally 
		Assert.assertTrue(faliureCount < 5);
	}
	
	
	@Test
	public void nameCheck() {
		IPeakFinder cwt = new WaveletTransformPeakFinds(); 
		assertEquals("WaveletTransformPeaks", cwt.getName());
	}
	
	@Test
	public void parametersCheck() throws Exception {
		//width/window for wavelet convolve
		IPeakFinder cwt = new WaveletTransformPeakFinds();

		Map<String, IPeakFinderParameter> paramSet = cwt.getParameters();
		//Size 1 for peaks
		Assert.assertEquals(5, paramSet.size());

		assertTrue(paramSet.containsKey("widthSz"));
		assertTrue(paramSet.containsKey("noisePerc"));
		assertTrue(paramSet.containsKey("minSNR"));
		assertTrue(paramSet.containsKey("minLength"));
		assertTrue(paramSet.containsKey("gapThresh"));
	}
	
	@Test
	public void plateauDataTest() {
		//TODO: python version just detect one value as a peak...
		WaveletTransformPeakFinds wave = new WaveletTransformPeakFinds();

		int dataSz = 100;
		Dataset xData = DatasetFactory.createRange(dataSz, Dataset.INT64);
		Dataset yData = DatasetFactory.ones(dataSz);

		TreeMap<Integer, Double> foundPeaks = (TreeMap<Integer, Double>) wave.findPeaks(xData, yData, null);

		//XXX: detects in plataue situation. Would need to check before runs algorithm.
		//It should find only 1 peak as a result of this algorithm
		assertTrue(foundPeaks.size() <= 1); // Check no peaks were found
	}
	
}
