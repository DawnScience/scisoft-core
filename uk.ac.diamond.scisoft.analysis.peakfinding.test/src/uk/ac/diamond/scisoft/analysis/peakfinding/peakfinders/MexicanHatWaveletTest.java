package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import static org.junit.Assert.assertEquals;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.peakfinding.MexicanHatWavelet;

/**
 * TODO: Wavelet function should integrate to zero test 
 * 
 * @author Dean P. Ottewell
 *
 */
public class MexicanHatWaveletTest {

	private final double ABS_TOL = 1e-7;
	private final double WIDTH = 4.0;
	private final double SIZE = 100;

	@Test
	public void parameterTest() {
		AFunction f = new MexicanHatWavelet(SIZE,WIDTH);		
		
		//Test Parameters
		Assert.assertEquals(2, f.getNoOfParameters());
		double[] values = f.getParameterValues();
		Assert.assertArrayEquals(new double[] {SIZE,WIDTH}, values, ABS_TOL);
	}
	
	@Test
	public void centerValueAmplitudeTest(){
		AFunction f = new MexicanHatWavelet(SIZE,WIDTH);		
		
		//Check center value
		double peakVal = 2 / (Math.sqrt(3 * WIDTH) * (Math.pow(Math.PI, 0.25)));
		
		Assert.assertEquals(peakVal, f.val(0), ABS_TOL); //center should be equal to highest value 
	}
	
	@Test
	public void shapePeaksTrough(){
		AFunction f = new MexicanHatWavelet(SIZE,WIDTH);		
		//Test is a mexican hat at key points
		Dataset testPoints = DatasetFactory.createLinearSpace(1, 100, 100, Dataset.FLOAT64);
		Dataset dx;
		dx = f.calculateValues(testPoints);
				
		double maxVal = dx.getDouble(dx.maxPos());
		int count = 0;
		for (int i = 0; i < dx.getSize(); ++i){
			if(dx.getDouble(i) == maxVal){
				count++;
			}
		}
		assertEquals(2, count); //2 as a result of curve
		
		double minVal = Math.abs(dx.getDouble(dx.minPos()));
		count = 0;
		for (int i = 0; i < dx.getSize(); ++i){
			if(Math.abs(dx.getDouble(i)) == minVal){
				count++;
			}
		}
		assertEquals(2, count);
	}	

}
