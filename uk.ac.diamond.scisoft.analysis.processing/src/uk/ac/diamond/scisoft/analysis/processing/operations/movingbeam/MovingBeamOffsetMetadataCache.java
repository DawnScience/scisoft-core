/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.vecmath.Point3d;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionMetadataUtils;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * A cache object to provide IDiffractionMetadata at the requested position based on extrapolation from a known position.
 * <p>
 * The extrapolation is based on raw positions of the assigned motors. However, a transformation step has been included in-case future implementations
 * require an transformation of the raw motor offsets. 
 * <p>
 * The cache configuration works as follows: if the model is set to override the position of the calibration, the names provided by the model will be used directly;
 * otherwise, the target of the NodeLinks associated with the axes set on the calibration data will be used to pull the position data from the scan file.
 */
public class MovingBeamOffsetMetadataCache extends AbstractMovingBeamMetadataCache {
	
	public MovingBeamOffsetMetadataCache(IDataset slice, DiffractionMetadataImportModel model, String dataFilePath) throws Exception {
		
		
		this.transform.setIdentity();  
		
		axes = slice.getFirstMetadata(AxesMetadata.class);
		sourceAxesNames = new String[] {model.getPositionZeroDataset(),model.getPositionOneDataset()};
		
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		if (Objects.isNull(ssm)) throw new CacheConstructionException("Dataset must have a SliceFromSeriesMetadata set.");
		isLive = ssm.getSourceInfo().isLive();
		
		IDataHolder cData = loadScan(model.getFilePath());
		IDataHolder scanData = loadScan(dataFilePath);
		
		
		Dataset xData = DatasetUtils
				.sliceAndConvertLazyDataset(cData.getLazyDataset(model.getPositionZeroDataset()));
		Dataset yData = DatasetUtils
				.sliceAndConvertLazyDataset(cData.getLazyDataset(model.getPositionOneDataset()));
	
		if (Objects.isNull(xData) || Objects.isNull(yData)) {
			throw new CacheConstructionException("could not retrieve specified calibration position datasets");
		}
		
		
		if (!model.getOverridePosition()) {
			double[] transformedPositions = transformRawMotorValues(xData, yData);
			calibrationX = transformedPositions[0];
			calibrationY = transformedPositions[1];
			calibrationZ = transformedPositions[2];
			
			// retain only the target names for the calibration motor position groups; these correspond to the
			// groups for the motor values used for calibration and will be used to identify the source names in other scans!
			Tree tree = cData.getTree();
			sourceAxesNames[0] = getNodeLinkTargetPath(model.getPositionZeroDataset(), tree);
			sourceAxesNames[1] = getNodeLinkTargetPath(model.getPositionOneDataset(), tree);
		
			boolean containsNull = Objects.isNull(sourceAxesNames[0]) || Objects.isNull(sourceAxesNames[1]);
			
			if (containsNull) throw new CacheConstructionException("could not read targets from calibration data");
			
		}else {
			calibrationX =  model.getCurrentPosition()[0];
			calibrationY =  model.getCurrentPosition()[1];
			calibrationZ = 0.;
		}
				
		ILazyDataset sourceX = scanData.getLazyDataset(sourceAxesNames[0]);
		ILazyDataset sourceY = scanData.getLazyDataset(sourceAxesNames[1]);
		
		
		Dataset[] cacheSets = new Dataset[2];
		for (ILazyDataset axisDSet : axes.getAxes()) {
			if (Objects.isNull(axisDSet))
				continue;
			
			String axisName = axisDSet.getName(); 
			if (axisName.equals(sourceAxesNames[0])) {
				cacheSets[0] = DatasetUtils.sliceAndConvertLazyDataset(sourceX);
				usingSetPositions = true;
			}else if (axisName.equals(sourceAxesNames[1])) {
				cacheSets[1] = DatasetUtils.sliceAndConvertLazyDataset(sourceY);
				usingSetPositions = true;
			}
		}
		
		
		if (usingSetPositions) {
			boolean containsNull = Arrays.stream(cacheSets).anyMatch(Objects::isNull);
			if (containsNull) throw new CacheConstructionException( "error obtaining axes data names from scan");
			if (cacheSets[0].getRank() == 1) { // corresponding to motor set
												// value arrays
				List<Dataset> datasets = DatasetUtils.meshGrid(cacheSets[0], cacheSets[1]);
				
				int[] newShape = Arrays.copyOf(datasets.get(0).getShape(), 4); //add 2 dimensions for the detector signal axes 
				newShape[2]= newShape[3] = 1;
				
				cachedSourceXPositions = datasets.get(0).reshape(newShape);
				cachedSourceYPositions = datasets.get(1).reshape(newShape);
				
			} else {
				throw new IllegalArgumentException(
						String.format("Unexpected rank of requested position dataset, expected 1  got %d",
								cacheSets[0].getRank()));
			}
	
		} else {
			cachedSourceXPositions = (isLive)? sourceX : DatasetUtils.sliceAndConvertLazyDataset(sourceX);
			cachedSourceYPositions = (isLive)? sourceY : DatasetUtils.sliceAndConvertLazyDataset(sourceY);
		}
		
		// Look for the user defined detector dataset, if present
		String datasetName = (Objects.nonNull(model.getDetectorDataset())) ? model.getDetectorDataset() : ssm.getDatasetName();
		
		String path = ProcessingUtils.resolvePath(model.getFilePath(), slice);
		metadata.add(NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(path, ssm.getParent(), datasetName));
	}

	
	/**
	 * get the metadata from the reference calibration and offset it in x and y according to the source offset between the 
	 * calibrated position and the current frame position
	 * 
	 * @param ssm
	 * @return
	 * @throws DatasetException
	 */
	public IDiffractionMetadata getDiffractionMetadata(SliceFromSeriesMetadata ssm) throws DatasetException {
		ReferencePosition2DMetadata posMetadata = getPositionMeta(ssm);
		double[] pos = Arrays.copyOf(posMetadata.getReferencePosition().getData(), 3);
		return DiffractionMetadataUtils.getOffsetMetadata(metadata.get(0), pos);
		
	}


	@Override
	public ReferencePosition2DMetadata getPositionMeta(SliceFromSeriesMetadata ssm) throws DatasetException {
				ReferencePosition2DMetadata pnew;
				//TODO add a check if the position metadata has already been set and if it has retrieve it
				
				double[] frameSourceOffsetXY = transformRawMotorValues(DatasetUtils.cast(DoubleDataset.class,ssm.getMatchingSlice(cachedSourceXPositions)) ,
						DatasetUtils.cast(DoubleDataset.class,ssm.getMatchingSlice(cachedSourceYPositions)));
						
				frameSourceOffsetXY[0] -=  calibrationX;
				frameSourceOffsetXY[1] -=  calibrationY;
				frameSourceOffsetXY[2] -=  calibrationZ;
				IDataset posData = DatasetFactory.createFromObject(frameSourceOffsetXY);

				pnew = new ReferencePosition2DMetadata();
				pnew.setReferencePosition(posData);

				return pnew;

	}
	
	/**
	 * Transform the x and y positions into a sample reference coordinate system, assumes that the rawX and rawY are actually single value
	 * datasets.
	 * 
	 * @param rawX
	 * @param rawY
	 * @return
	 */
	private double[] transformRawMotorValues(Dataset rawX, Dataset rawY) {
		Point3d rawVec = new Point3d(rawX.getDouble(),rawY.getDouble(),0 ); 	
		this.transform.transform(rawVec);
		return new double[] { rawVec.getX(), rawVec.getY(),rawVec.getZ()};
		
	}
	

}
