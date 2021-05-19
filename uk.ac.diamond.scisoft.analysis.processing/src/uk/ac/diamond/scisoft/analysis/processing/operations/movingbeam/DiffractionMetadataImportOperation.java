/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionMetadataUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

@Atomic
public class DiffractionMetadataImportOperation
		extends AbstractOperationBase<DiffractionMetadataImportModel, OperationData> {

	private volatile MovingBeamMetadataCache cache;
	private PropertyChangeListener listener;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam.DiffractionMetadataImportOperation";
	}
	
	
	/**
	 * Class to act as a cache in order to simplify initialisation.
	 * <p>
	 * The cache stores information obtained from the calibration and the scan data as well as the IDiffractionMetadata loaded from the specified 
	 * calibration.
	 * <p>
	 * The cache configuration works as follows: if the model is set to override the position of the calibration, the names provided by the model will be used directly;
	 * otherwise, the target of the NodeLinks associated with the axes set on the calibration data will be used to pull the position data from the scan file.
	 * 
	 */
	public class MovingBeamMetadataCache {
		private IDiffractionMetadata metadata;
		private Double calibrationX;
		private Double calibrationY;
		private ILazyDataset cachedSourceXPositions;
		private ILazyDataset cachedSourceYPositions;
		private boolean isLive = false;
		private boolean usingSetPositions = false;
		
		public MovingBeamMetadataCache(IDataset slice,  DiffractionMetadataImportModel model, String dataFilePath) throws Exception {
			
			AxesMetadata axes = slice.getFirstMetadata(AxesMetadata.class);
			SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
			isLive = ssm.getSourceInfo().isLive();
			String[] sourceAxesNames = new String[] {model.getPositionZeroDataset(),model.getPositionOneDataset()};
			
			IDataHolder cData = LoaderFactory.getData(model.getFilePath(), true, null);
			IDataHolder scanData = LoaderFactory.getData(dataFilePath, null);
			
			
			Dataset xData = DatasetUtils
					.sliceAndConvertLazyDataset(cData.getLazyDataset(model.getPositionZeroDataset()));
			Dataset yData = DatasetUtils
					.sliceAndConvertLazyDataset(cData.getLazyDataset(model.getPositionOneDataset()));

			if (Objects.isNull(xData) || Objects.isNull(yData)) {
				throw new CacheConstructionException("could not retrieve specified calibration position datasets");
			}
			
			calibrationX = (model.getOverridePosition())? model.getCurrentPosition()[0]:xData.squeeze().getDouble();
			calibrationY = (model.getOverridePosition())? model.getCurrentPosition()[1]:yData.squeeze().getDouble();

			
			
			if (!model.getOverridePosition()) {
				// retain only the target names (as these correspond to the
				// groups for the motor values used for calibration)
				Tree tree = cData.getTree();
				sourceAxesNames[0] = getNodeLinkTargetPath(model.getPositionZeroDataset(), tree);
				sourceAxesNames[1] = getNodeLinkTargetPath(model.getPositionOneDataset(), tree);
			
				boolean containsNull = Arrays.stream(sourceAxesNames).anyMatch(Objects::isNull);
				if (containsNull) throw new CacheConstructionException("could not read targets from calibration data");
				
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
				}
				if (axisName.equals(sourceAxesNames[1])) {
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
					Dataset[] reshapedData = datasets.stream().map(d->d.reshape(newShape)).toArray(Dataset[]::new);
					
					cachedSourceXPositions = reshapedData[0];
					cachedSourceYPositions = reshapedData[1];
					
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
			String datasetName = (Objects.isNull(model.getDetectorDataset())) ? model.getDetectorDataset() : ssm.getDatasetName();
			
			String path = ProcessingUtils.resolvePath(model.getFilePath(), slice);
			metadata = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(path, ssm.getParent(), datasetName);
		}
		
		
		/**
		 * Get target attribute from a specified Node in a Tree. If the node is not found, or the target attribute is missing null is returned.   
		 * @param NodePath
		 * @param tree
		 * @return string representation of the target attribute (or null if not node not found or target attribute doesn't exist);
		 */
		private String getNodeLinkTargetPath(String NodePath, Tree tree) {
			
			NodeLink nl = tree.findNodeLink(NodePath);
			if (Objects.isNull(nl)) return null;
			
			Attribute attr = nl.getDestination().getAttribute("target"); 
			if (Objects.isNull(attr)) return null;
			
			return attr.getFirstElement();
		}
		
		
		public ReferencePosition2DMetadata getPositionMeta(SliceFromSeriesMetadata ssm) throws DatasetException {
			ReferencePosition2DMetadata pnew;
			
			double[] frameSourceOffsetXY = new double[] {ssm.getMatchingSlice(cachedSourceXPositions).squeeze().getDouble() - calibrationX,
					ssm.getMatchingSlice(cachedSourceYPositions).squeeze().getDouble() - calibrationY};
			
			IDataset posData = DatasetFactory.createFromObject(frameSourceOffsetXY);

			pnew = new ReferencePosition2DMetadata();
			pnew.setReferencePosition(posData);

			return pnew;
		}
		
		/**
		 * get the metadata from the reference calibration and offset it in x and y according to the source offset between the 
		 * calibrated position and the current frame position
		 * 
		 * @param ssm
		 * @return
		 * @throws DatasetException
		 */
		public IDiffractionMetadata getMetadata(SliceFromSeriesMetadata ssm) throws DatasetException {
			ReferencePosition2DMetadata posMetadata = getPositionMeta(ssm);
			double[] pos = Arrays.copyOf(posMetadata.getReferencePosition().getData(), 3);
			return DiffractionMetadataUtils.getOffsetMetadata(metadata, pos);
			
		}
		
	}
	
		

	
	/**
	 * Custom exception class used to wrap errors generated during initialisation of the {@link MovingBeamMetadataCache}
	 */
	final class CacheConstructionException extends Exception {
		private static final long serialVersionUID = -5113506787527672829L;

		public CacheConstructionException(String error) {
			super(error);
		}
	
	}

	
	

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		if (Objects.isNull(model.getFilePath()) || model.getFilePath().isEmpty())
			throw new OperationException(this,"Calibration file path must be set");
		
		//additional checks include that all the offset paths must be set 
		if (Objects.isNull(model.getPositionZeroDataset()) || Objects.isNull(model.getPositionOneDataset()))
			throw new OperationException(this, "Dataset paths corresponding to the axes for dimension 0 and 1 must be set");

		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		MovingBeamMetadataCache lcache = cache;
		if (Objects.isNull(lcache)) {
			synchronized(this) {
				lcache =cache;
				if (Objects.isNull(lcache)) {
					MovingBeamMetadataCache newcache;
					try {
						newcache = new MovingBeamMetadataCache(slice, model, ssm.getFilePath());
					} catch (Exception e) {
						throw new OperationException(this, e);
					}
				if (Objects.isNull(newcache)) throw new OperationException(this, "Error initialising moving beam cache");
				cache = lcache = newcache;
				}
			}
		}
		
		
		
		try {
			
			slice.setMetadata(cache.getPositionMeta(ssm));
			slice.setMetadata(cache.getMetadata(ssm));
		} catch (Exception e){
			throw new OperationException(this, "Error retrieving metadata from cache");
		}
		
		
		return new OperationData(slice);
	}


	


	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	public void setModel(DiffractionMetadataImportModel model) {

		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					cache = null;
				}
			};
		} else {
			((AbstractOperationModel) this.model).removePropertyChangeListener(listener);
		}

		((AbstractOperationModel) this.model).addPropertyChangeListener(listener);
	}
	
	@Override
	public void init() {
		cache = null;
	}
}
