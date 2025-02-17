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
import org.eclipse.dawnsci.analysis.api.unit.UnitUtils;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;

import si.uom.NonSI;
import tec.units.indriya.unit.Units;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationCache implements IPixelIntegrationCache {
	
	private final PixelIntegrationBean bean;
	
	private DoubleDataset binEdgesAzimuthal = null;
	private DoubleDataset binEdgesRadial = null;
	
	private Dataset[] azimuthalArray;
	private Dataset[] radialArray;
	
	private Dataset azimuthalAxis;
	private Dataset radialAxis;
	
	private int longestOnDetector;
	
	private final boolean sanitise;
	
	QSpace qSpace = null;
	
	
	public PixelIntegrationCache(IDiffractionMetadata metadata, PixelIntegrationBean bean) {
		this.qSpace = new QSpace(metadata.getDetector2DProperties(), 
				 metadata.getDiffractionCrystalEnvironment());
		this.bean = bean;
		
		sanitise = bean.getSanitise();
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
		
		double[] radialRange = bean.getRadialRange();
		if (bean.isLog() && radialRange != null) {
			radialRange = bean.getRadialRange().clone();
			radialRange[0] = Math.log10(radialRange[0]);
			radialRange[1] = Math.log10(radialRange[1]);
		}
		
		double[] beamCentre = qSpace.getDetectorProperties().getBeamCentreCoords();
		int[] shape = bean.getShape();
		if (shape == null) shape = new int[]{qSpace.getDetectorProperties().getPy(),
				qSpace.getDetectorProperties().getPx()};
	
		if (bean.isUsePixelSplitting()) {
			
			if (!to1D || isAz || (!isAz && radialRange != null)) {
				
				XAxis x = bean.getxAxis() != XAxis.RESOLUTION ? bean.getxAxis() : XAxis.Q;
				radialArray = PixelIntegrationUtils.generateMinMaxRadialArray(shape, qSpace, x);
				if (bean.isLog()) {
					radialArray[0] = Maths.log10(radialArray[0]);
					radialArray[1] = Maths.log10(radialArray[1]);
				}
				binEdgesRadial = calculateBins(radialArray, radialRange, nBinsRad,isAz);
				
			}
			
			if (!to1D || !isAz || (isAz && bean.getAzimuthalRange() != null)) {
				
				if (bean.getAzimuthalRange() == null) azimuthalArray = PixelIntegrationUtils.generateMinMaxAzimuthalArray(beamCentre, shape, false);
				else {
					double[] r = bean.getAzimuthalRange();
					double min = Math.min(r[0], r[1]);
					azimuthalArray = PixelIntegrationUtils.generateMinMaxAzimuthalArray(beamCentre, shape, min);
					
				}
				binEdgesAzimuthal = calculateBins(azimuthalArray, bean.getAzimuthalRange(), nBinsAz,!isAz);
			}
			
		} else {
			
			if (!to1D || isAz || (!isAz && radialRange != null)) {
				
				XAxis x = bean.getxAxis() != XAxis.RESOLUTION ? bean.getxAxis() : XAxis.Q;
				radialArray = new Dataset[]{PixelIntegrationUtils.generateRadialArray(shape, qSpace, x)};
				if (bean.isLog()) radialArray[0] = Maths.log10(radialArray[0]);
				binEdgesRadial = calculateBins(radialArray, radialRange, nBinsRad,isAz);
				
			}
			
			if (!to1D || !isAz || (isAz && bean.getAzimuthalRange() != null)) {
				azimuthalArray = new Dataset[1];
				if (bean.getAzimuthalRange() == null){
					azimuthalArray[0] = PixelIntegrationUtils.generateAzimuthalArray(beamCentre, shape, false);
				}else {
					double[] r = bean.getAzimuthalRange();
					double min = Math.min(r[0], r[1]);
					azimuthalArray[0] = PixelIntegrationUtils.generateAzimuthalArray(beamCentre, shape, min);
					
				}
				binEdgesAzimuthal = calculateBins(azimuthalArray, bean.getAzimuthalRange(), nBinsAz,!isAz);
			}
		}

		if (!to1D || !isAz) azimuthalAxis = calculateAzimuthalAxis(nBinsAz, bean.getAzimuthalRange(), binEdgesAzimuthal,!isAz);

		if (!to1D || isAz) {
			try {
				radialAxis = calculateRadialAxis(bean.getxAxis(), nBinsRad, radialRange, binEdgesRadial, bean.isLog(),isAz);
			} catch (MetadataException e) {
			}
		}
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
		if (bean.isAzimuthalIntegration()) {

			return binEdgesRadial.get(bean.getNumberOfBinsRadial());
			
		}

		return binEdgesAzimuthal.get(bean.getNumberOfBinsAzimuthal());

	}


	@Override
	public double getXBinEdgeMin() {
		
		if (bean.isAzimuthalIntegration()) {
			return binEdgesRadial.get(0);

		}

		return binEdgesAzimuthal.get(0);
	}


	@Override
	public double getYBinEdgeMax() {
		
		if (!bean.isAzimuthalIntegration()) {
			return binEdgesRadial.get(bean.getNumberOfBinsRadial());
		}

		return binEdgesAzimuthal.get(bean.getNumberOfBinsAzimuthal());
	}


	@Override
	public double getYBinEdgeMin() {
		
		if (!bean.isAzimuthalIntegration()) {
			return binEdgesRadial.get(0);
		}
		
		return binEdgesAzimuthal.get(0);
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
	
	private static DoubleDataset calculateBins(Dataset[] arrays, double[] binRange, int numBins, boolean isCentre) {
		
		if (binRange != null) {
			double shift = 0;
//			range corresponds to bin centres
			if (isCentre) shift = (binRange[1]- binRange[0])/(2*numBins);
			return DatasetFactory.createLinearSpace(DoubleDataset.class, binRange[0]-shift, binRange[1]+shift, numBins + 1);
		}

		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		
		for (Dataset a : arrays) {

			Dataset data = a;
			
			double n = data.min(true).doubleValue();
			double x = data.max(true).doubleValue();
			min = n < min ? n : min;
			max = x > max ? x : max;
		}
		//default range corresponds to bin edges
		return DatasetFactory.createLinearSpace(DoubleDataset.class, min, max, numBins + 1);
	}

	private static Dataset calculateRadialAxis(XAxis xAxis, int nBins, double[] binRange, DoubleDataset binEdges, boolean isLog, boolean isCentre) throws MetadataException {
		Dataset axis = null;

		if (binRange == null || !isCentre) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetFactory.createLinearSpace(DoubleDataset.class, binRange[0], binRange[1], nBins);
		}

		if (isLog) {
			IndexIterator it = axis.getIterator();
			while (it.hasNext()) {
				axis.setObjectAbs(it.index,Math.pow(10,axis.getElementDoubleAbs(it.index)));
			}
		}

		String name = null;
		UnitMetadata unit = null;

		switch (xAxis) {
		case Q:
			name = "q";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.ANGSTROM.inverse());
			break;
		case Qnm:
			name = "q";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, UnitUtils.NANOMETRE.inverse());
			break;
		case Qm:
			name = "q";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, Units.METRE.inverse());
			break;
		case ANGLE:
			name = "2-theta";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.DEGREE_ANGLE);
			break;
		case RESOLUTION:
			axis = Maths.divide((2*Math.PI), axis);
			name = "d-spacing";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, NonSI.ANGSTROM);
			break;
		case PIXEL: // TODO support pixel pitch with size from detector
			name = "pixel";
			unit = MetadataFactory.createMetadata(UnitMetadata.class, UnitUtils.PIXEL);
			break;
		}
		axis.setMetadata(unit);
		axis.setName(name);
		
		return axis;
	}
	
	private static Dataset calculateAzimuthalAxis(int nBins, double[] binRange, DoubleDataset binEdges, boolean isCentre){
		
		Dataset axis = null;

		if (binRange == null || !isCentre) {
			axis = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
		} else {
			axis = DatasetFactory.createLinearSpace(DoubleDataset.class, binRange[0], binRange[1], nBins);
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


	@Override
	public boolean sanitise() {
		return this.sanitise;
	}


	@Override
	public boolean provideLookup() {
		return false;
	}
	
	private void setUpPixelSplitting() {
		
		
		
	}
}
