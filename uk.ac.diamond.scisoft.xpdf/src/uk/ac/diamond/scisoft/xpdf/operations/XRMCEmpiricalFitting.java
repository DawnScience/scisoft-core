/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.EnumMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationBean;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;
import uk.ac.diamond.scisoft.xpdf.XPDFCoordinates;

public class XRMCEmpiricalFitting {

	protected static double getMeanRatioGain(Dataset input, Dataset incoherentScattering) {
		EmpiricalGainFitting egf = new EmpiricalGainFitting(input);

		Dataset inputHighQ1D = egf.highQAzimuthalIntegration(input);		
		Dataset incoherentHighQ1D = egf.highQAzimuthalIntegration(incoherentScattering);
		
		double meanRatio = (Double) Maths.divide(incoherentHighQ1D, inputHighQ1D).mean(true);
		
		return meanRatio;
	}
	
	protected static double getRatioOfMeansGain(Dataset input, Dataset incoherentScattering) {
		EmpiricalGainFitting egf = new EmpiricalGainFitting(input);

		Dataset inputHighQ1D = egf.highQAzimuthalIntegration(input);		
		Dataset incoherentHighQ1D = egf.highQAzimuthalIntegration(incoherentScattering);
		
		double ratioOfMeans = (Double) incoherentHighQ1D.mean()/(Double) inputHighQ1D.mean();

		return ratioOfMeans;
	}
	
	protected static double getWeightedMeanRatioGain(Dataset input, Dataset incoherentScattering) {
		EmpiricalGainFitting egf = new EmpiricalGainFitting(input);

		Dataset inputHighQ1D = egf.highQAzimuthalIntegration(input);		
		Dataset incoherentHighQ1D = egf.highQAzimuthalIntegration(incoherentScattering);
		Dataset highQ1D = egf.getAxis(input);
		
		double weightedMeanRatio = (Double) Maths.divide(incoherentHighQ1D, inputHighQ1D).imultiply(highQ1D).mean(true)/(Double) highQ1D.mean();
		
		return weightedMeanRatio;
	}
	
	protected static double getRatioOfWeightedMeansGain(Dataset input, Dataset incoherentScattering) {
		EmpiricalGainFitting egf = new EmpiricalGainFitting(input);

		Dataset inputHighQ1D = egf.highQAzimuthalIntegration(input);		
		Dataset incoherentHighQ1D = egf.highQAzimuthalIntegration(incoherentScattering);
		Dataset highQ1D = egf.getAxis(input);
		
		double ratioOfWeightedMeans = (Double) Maths.multiply(incoherentHighQ1D, highQ1D).mean()/(Double) Maths.multiply(inputHighQ1D, highQ1D).mean();

		return ratioOfWeightedMeans;
	}
	
	
	public enum GainEstimate {
		MEAN_RATIO,
		RATIO_OF_MEANS,
		WEIGHTED_MEAN_RATIO,
		RATIO_OF_WEIGHTED_MEANS,
	}
	
	public static double getEmpiricialGain(Dataset input, Dataset incoherentScattering, GainEstimate estimate) {
		switch (estimate) {
		case MEAN_RATIO:
			return getMeanRatioGain(input, incoherentScattering);
		case RATIO_OF_MEANS:
			return getRatioOfMeansGain(input, incoherentScattering);
		case WEIGHTED_MEAN_RATIO:
			return getWeightedMeanRatioGain(input, incoherentScattering);
		case RATIO_OF_WEIGHTED_MEANS:
			return getRatioOfWeightedMeansGain(input, incoherentScattering);
		default:
			return getMeanRatioGain(input, incoherentScattering);
		}
	}
	
	// Calculates the gain empirically to best remove the background
	public static double calculateEmpiricalGain(Dataset input, Dataset incoherentScattering, boolean isGainFinal) {

		Map<GainEstimate, Double> estimates = new EnumMap<>(GainEstimate.class);
		
		for (GainEstimate est : GainEstimate.values()) {
			estimates.put(est, getEmpiricialGain(input, incoherentScattering, est));
		}
		
		double meanRatio = estimates.get(GainEstimate.MEAN_RATIO);
		double ratioOfMeans = estimates.get(GainEstimate.RATIO_OF_MEANS);
		
		System.out.println("EmpiricalGainFitting: mean ratio = " + meanRatio + ", ratio of means = " + ratioOfMeans);
		
		// Q weighted means
		double weightedMeanRatio = estimates.get(GainEstimate.WEIGHTED_MEAN_RATIO);
		double ratioOfWeightedMeans = estimates.get(GainEstimate.RATIO_OF_WEIGHTED_MEANS);

		System.out.println("EmpiricalGainFitting: weighted mean ratio = " + weightedMeanRatio + ", ratio of weighted means = " + ratioOfWeightedMeans);

		return 1.0; 
	}
	

	protected static class EmpiricalGainFitting {
		private Dataset mask;
		private IPixelIntegrationCache lcache;
		
		public EmpiricalGainFitting() {}
		
		public EmpiricalGainFitting(Dataset input) {
			setMask(input);
			setPICache(input);
		}
		
		public void setMask(Dataset input) {
			mask = DatasetUtils.convertToDataset(AbstractOperationBase.getFirstMask(input));
		}
		
		public Dataset highQAzimuthalIntegration(Dataset input) {
			return PixelIntegration.integrate(input, mask, lcache).remove(1);
		}
		
		public Dataset getAxis(Dataset input) {
			return createQIntegrationRange(input);
		}
		
		private Dataset createQIntegrationRange(Dataset input) {
			
			// Get the highest unmasked value of Q
			XPDFCoordinates coords = new XPDFCoordinates(input);
			// apply the mask to the Q dataset
			Dataset qMasked = coords.getQ().clone();

			// set the masked pixels to NaN
			IndexIterator iter = qMasked.getIterator();
			while (iter.hasNext()) {
				if (mask.getElementBooleanAbs(iter.index))
					qMasked.setObjectAbs(iter.index, Double.NaN);
			}
			// Get the max and min values, ignoring the masked, NaN pixels
			double qmin = (double) qMasked.min(true);
			double qmax = (double) qMasked.max(true);
			
			// The range of Q to test will be the last 1/3 of the range, in 20 steps
			final double nSteps = 20;
			final double range = 0.67;

			return DatasetFactory.createRange(qmin + range*(qmax - qmin), qmax, range*(qmax - qmin)/nSteps);
		}
		
		public void setPICache(Dataset input) {
			Dataset qIntegration = createQIntegrationRange(input);
			lcache = getPICache(qIntegration, AbstractOperationBase.getFirstDiffractionMetadata(input), input.getShape());
		}
		
		// Creates the PixelIntegrationCache for the azimuthal integration
		private IPixelIntegrationCache getPICache(Dataset q, IDiffractionMetadata md, int[] shape) {
			PixelIntegrationBean pIBean = new PixelIntegrationBean();
			pIBean.setUsePixelSplitting(false);
			pIBean.setNumberOfBinsRadial(q.getSize());
			pIBean.setxAxis(XAxis.Q);
			pIBean.setRadialRange(new double[] {(double) q.min(), (double) q.max()});
			pIBean.setAzimuthalRange(null);
			pIBean.setTo1D(true);
			pIBean.setLog(false);
			pIBean.setShape(shape);

			return new PixelIntegrationCache(md, pIBean);
		}
	}
}
