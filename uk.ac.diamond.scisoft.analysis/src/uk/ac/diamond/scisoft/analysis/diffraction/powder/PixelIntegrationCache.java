/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationCache implements IPixelIntegrationCache {
	
	PixelIntegrationBean bean;
	
	private DoubleDataset binEdgesAzimuthal = null;
	private DoubleDataset binEdgesRadial = null;
	
	private Dataset[] azimuthalArray;
	private Dataset[] radialArray;
	
	private Dataset azimuthalAxis;
	private Dataset radialAxis;
	
	private int longestOnDetector;
	
	QSpace qSpace = null;
	
	public PixelIntegrationCache(IDiffractionMetadata metadata, PixelIntegrationBean bean) {
		this.qSpace = new QSpace(metadata.getDetector2DProperties(), 
				 metadata.getDiffractionCrystalEnvironment());
		this.bean = bean;
		longestOnDetector = calculateNumberOfBins();
		initialize();
	}


	private void initialize() {
		
		boolean to1D = bean.isTo1D();
		boolean isAz = bean.isAzimuthalIntegration();
		if (!to1D) isAz = true;
		
		if (bean.getNumberOfBinsAzimuthal() < 0) bean.setNumberOfBinsAzimuthal(longestOnDetector);
		if (bean.getNumberOfBinsRadial() < 0) bean.setNumberOfBinsRadial(longestOnDetector);
		
		int nBinsAz = bean.getNumberOfBinsAzimuthal();
		int nBinsRad = bean.getNumberOfBinsRadial();
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();
		int[] shape = new int[]{qSpace.getDetectorProperties().getPy(),
				qSpace.getDetectorProperties().getPx()};
	
		if (bean.isUsePixelSplitting()) {
			
			if (!to1D || isAz || (!isAz && bean.getRadialRange() != null)) {
				
				XAxis x = bean.getxAxis() != XAxis.RESOLUTION ? bean.getxAxis() : XAxis.Q;
				radialArray = PixelIntegrationUtils.generateMinMaxRadialArray(shape, qSpace, x);
				if (bean.isLog()) {
					radialArray[0] = Maths.log10(radialArray[0]);
					radialArray[1] = Maths.log10(radialArray[1]);
				}
				binEdgesRadial = calculateBins(radialArray, bean.getRadialRange(), nBinsRad);
				
			}
			
			if (!to1D || !isAz || (isAz && bean.getAzimuthalRange() != null)) {
				
				azimuthalArray = PixelIntegrationUtils.generateMinMaxAzimuthalArray(beamCentre, shape, false);
				binEdgesAzimuthal = calculateBins(azimuthalArray, bean.getAzimuthalRange(), nBinsAz);
			}
			
		} else {
			
			if (!to1D || isAz || (!isAz && bean.getRadialRange() != null)) {
				
				XAxis x = bean.getxAxis() != XAxis.RESOLUTION ? bean.getxAxis() : XAxis.Q;
				radialArray = new Dataset[]{PixelIntegrationUtils.generateRadialArray(shape, qSpace, x)};
				if (bean.isLog()) radialArray[0] = Maths.log10(radialArray[0]);
				binEdgesRadial = calculateBins(radialArray, bean.getRadialRange(), nBinsRad);
				
			}
			
			if (!to1D || !isAz || (isAz && bean.getAzimuthalRange() != null)) {
				
				azimuthalArray = new Dataset[]{PixelIntegrationUtils.generateAzimuthalArray(beamCentre, shape, false)};
				binEdgesAzimuthal = calculateBins(azimuthalArray, bean.getAzimuthalRange(), nBinsAz);
			}
		}
		
		if (!to1D || !isAz) azimuthalAxis = calculateAzimuthalAxis(nBinsAz, bean.getAzimuthalRange(), binEdgesAzimuthal);
		
		if (!to1D || isAz) radialAxis = calculateRadialAxis(bean.getxAxis(), nBinsRad, bean.getRadialRange() , binEdgesRadial);
		
	}
	
	@Override
	public Dataset[] getXAxisArray() {
		return bean.isAzimuthalIntegration() ? radialArray : azimuthalArray;
	}


	@Override
	public Dataset[] getYAxisArray() {
		return bean.isAzimuthalIntegration() ? azimuthalArray : radialArray;
	}


	@Override
	public double getXBinEdgeMax() {
		return bean.isAzimuthalIntegration() ? binEdgesRadial.get(bean.getNumberOfBinsRadial()) :
			binEdgesAzimuthal.get(bean.getNumberOfBinsAzimuthal());
	}


	@Override
	public double getXBinEdgeMin() {
		return bean.isAzimuthalIntegration() ? binEdgesRadial.get(0) : binEdgesAzimuthal.get(0);
	}


	@Override
	public double getYBinEdgeMax() {
		return bean.isAzimuthalIntegration() ?  binEdgesAzimuthal.get(bean.getNumberOfBinsAzimuthal()) :
			binEdgesRadial.get(bean.getNumberOfBinsRadial());
	}


	@Override
	public double getYBinEdgeMin() {
		return bean.isAzimuthalIntegration() ? binEdgesAzimuthal.get(0) : binEdgesRadial.get(0);
	}


	@Override
	public int getNumberOfBinsXAxis() {
		return bean.isAzimuthalIntegration() ? bean.getNumberOfBinsRadial() :
			bean.getNumberOfBinsAzimuthal();
	}
	
	@Override
	public int getNumberOfBinsYAxis() {
		return bean.getNumberOfBinsAzimuthal();

	}

	@Override
	public double[] getYAxisRange() {
		return bean.isAzimuthalIntegration() ? bean.getAzimuthalRange() : 
			bean.getRadialRange();
	}


	@Override
	public double[] getXAxisRange() {
		return bean.isAzimuthalIntegration() ? bean.getRadialRange() : bean.getAzimuthalRange();
	}


	@Override
	public Dataset getXAxis() {
		return bean.isAzimuthalIntegration() ? radialAxis : azimuthalAxis;
	}


	@Override
	public Dataset getYAxis() {
		return bean.isAzimuthalIntegration() ? azimuthalAxis : radialAxis;
	}
	
	@Override
	public boolean isPixelSplitting() {
		return bean.isUsePixelSplitting();
	}


	@Override
	public boolean isTo1D() {
		return bean.isTo1D();
	}
	
	private static DoubleDataset calculateBins(Dataset[] arrays, double[] binRange, int numBins) {
		
		if (binRange != null) {
			//range corresponds to bin centres
			double shift = (binRange[1]- binRange[0])/(2*numBins);
			return (DoubleDataset) DatasetUtils.linSpace(binRange[0]-shift, binRange[1]+shift, numBins + 1, Dataset.FLOAT64);
		}
		
			
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		for (Dataset a : arrays) {

			Dataset data = a;
			
			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;
		}

		return (DoubleDataset) DatasetUtils.linSpace(min, max, numBins + 1, Dataset.FLOAT64);
	}
	
	private static Dataset calculateRadialAxis(XAxis xAxis, int nBins, double[] binRange, DoubleDataset binEdges) {
		Dataset axis = null;
		
		if (binRange == null) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(binRange[0], binRange[1], nBins, Dataset.FLOAT64);
		}
		
		switch (xAxis) {
		case Q:
			axis.setName("q");
			break;
		case ANGLE:
			axis.setName("2theta");
			break;
		case RESOLUTION:
			axis = Maths.divide((2*Math.PI), axis);
			axis.setName("d-spacing");
			break;
		case PIXEL:
			axis.setName("pixel");
			break;
		}
		
		return axis;
	}
	
	private static Dataset calculateAzimuthalAxis(int nBins, double[] binRange, DoubleDataset binEdges){
		
		Dataset axis = null;

		if (binRange == null) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetUtils.linSpace(binRange[0], binRange[1], nBins, Dataset.FLOAT64);
		}

		axis.setName("azimuthal angle (degrees)");

		return axis;
	}
	
	private int calculateNumberOfBins() {
		
		int[] shape = new int[]{qSpace.getDetectorProperties().getPy(), qSpace.getDetectorProperties().getPx()};
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();

		if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& beamCentre[0] < shape[1] && beamCentre[0] > 0) {
			double[] farCorner = new double[]{0,0};
			if (beamCentre[1] < shape[0]/2.0) farCorner[0] = shape[0];
			if (beamCentre[0] < shape[1]/2.0) farCorner[1] = shape[1];
			
			return (int)Math.hypot(beamCentre[0]-farCorner[1], beamCentre[1]-farCorner[0]);
		} else if (beamCentre[1] < shape[0] && beamCentre[1] > 0
				&& (beamCentre[0] > shape[1] || beamCentre[0] < 0)) {
				return shape[1];
		} else if (beamCentre[0] < shape[1] && beamCentre[0] > 0
				&& (beamCentre[1] > shape[0] || beamCentre[1] < 0)) {
				return shape[0];
		} else {
			return (int)Math.hypot(shape[1], shape[0]);
		}
	}
}
