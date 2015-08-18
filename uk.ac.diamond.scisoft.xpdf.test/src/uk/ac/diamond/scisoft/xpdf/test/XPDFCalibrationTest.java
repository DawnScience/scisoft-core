package uk.ac.diamond.scisoft.xpdf.test;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFCalibration;
import junit.framework.TestCase;

public class XPDFCalibrationTest extends TestCase {

	public void testKroghMoeSum() {
		Map<Integer, Integer> ceriaCompo = new HashMap<Integer, Integer>();
		ceriaCompo.put(58, 1);
		ceriaCompo.put(8, 2);
		double KMSumExpected = 964.425331596;
		double numDen = 0.0803004424021;
		double KMSum = XPDFCalibration.KroghMoeSum(ceriaCompo, numDen, 0.0);
		assertTrue("Error in ceria Krogh-Moe sum too large. Expected:" + KMSumExpected + ", got:" + KMSum, Math.abs(KMSum - KMSumExpected) < 1e-6*KMSumExpected);
	}

	public void testQQIntegral() {
		String dataPath = "/home/rkl37156/ceria_dean_data/";
		// Load up the th_soq data obtained from the python version 
		IDataHolder dh = null;

		try {
			dh = LoaderFactory.getData(dataPath+"q.xy");
		} catch (Exception e) {
		}
		Dataset q = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		try {
			dh = LoaderFactory.getData(dataPath+"self_scattering.xy");
		} catch (Exception e) {
		}
		Dataset selfScattering = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		try {
			dh = LoaderFactory.getData(dataPath+"thomson.xy");
		} catch (Exception e) {
		}
		Dataset thomson = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());

		double integralExpected = 463628.661954;
		
		double integral = XPDFCalibration.QQIntegral(q, selfScattering, thomson);
		
		assertTrue("Difference in Q squared integral too large", Math.abs(integral - integralExpected) < 1e-6*integralExpected);
		
	}
	
}
