/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;

/**
 * This is a cache that uses the retrieves the nearest neighbour metadata for a
 * moving beam scan. It uses the datasets highlighted in the model to cache both
 * scan axes from the current scan, the scan axes from the calibration and the unique 
 * keys associated with the calibration scan. The cache assumes that
 * recalibrated frames have been named based on their 
 * unique keys-the path to which is hardcoded in {@link AbstractMovingBeamMetadataCache} 
 * <p>
 * When the metadata is retrieved from the cache it does a nearest neighbour
 * comparison to determine where to pull the cached metadata from in the list.
 * <p>
 * Note: no bound checking is performed yet to ensure scan falls within the
 * calibrated area.
 */
public class NearestMetadataCache extends AbstractMovingBeamMetadataCache {
	private Dataset calibrationX;
	private Dataset calibrationY;
	private Dataset calibrationFrameKeys;
	private File cFolder;
	private String[] cFrames;
	private String patternMatch;
	private String datasetName;
	 

	/**
	 * Construct the metadata cache
	 * 
	 * @param slice slice of data to process; assumed to have a
	 * SliceFromSeriesMetadata object containing SliceInformation and
	 * SourceInformation set.
	 * @param model corresponding model to the
	 * {@link NearestDiffractionMetadataImportOperation}
	 * @param dataFilePath path to the scan being processed
	 * @throws Exception if an error is raised during the cache construction
	 */
	public NearestMetadataCache(IDataset slice, NearestDiffractionMetadataImportModel model, String dataFilePath)
			throws Exception {

		axes = slice.getFirstMetadata(AxesMetadata.class);
		sourceAxesNames = new String[] { model.getPositionZeroDataset(), model.getPositionOneDataset() };

		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		isLive = ssm.getSourceInfo().isLive();
		
		
		IDataHolder cData = loadScan(model.getFilePath());
		IDataHolder scanData = loadScan(dataFilePath);
		
		
		
		// here the cached datasets should be the scan's axis datasets
		cachedSourceXPositions = scanData.getLazyDataset(sourceAxesNames[0]);
		cachedSourceYPositions = scanData.getLazyDataset(sourceAxesNames[1]);
		
		
		String frameKeys = buildFrameKeysPath(model.getFilePath(), model.getFrameKeyPath(),true);
		ILazyDataset frameKeysTmp = cData.getLazyDataset(frameKeys);
		if (frameKeysTmp == null) {
			frameKeys = buildFrameKeysPath(model.getFilePath(), model.getFrameKeyPath(),false);
			frameKeysTmp = cData.getLazyDataset(frameKeys);
			if (frameKeysTmp == null) frameKeys = model.getFrameKeyPath();
		}
		
		// get flat views of the calibrated position datasets and the frame keys
		calibrationX = DatasetUtils.sliceAndConvertLazyDataset(cData.getLazyDataset(sourceAxesNames[0])).flatten();
		calibrationY = DatasetUtils.sliceAndConvertLazyDataset(cData.getLazyDataset(sourceAxesNames[1])).flatten();
		calibrationFrameKeys = DatasetUtils.sliceAndConvertLazyDataset(cData.getLazyDataset(frameKeys)).flatten();
		
		if (Objects.isNull(calibrationX) || Objects.isNull(calibrationY)) {
			throw new CacheConstructionException(
					"could not retrieve specified calibration position datasets from scan");
		}

		cFolder = new File(model.getCalibsFolder());
		cFrames = cFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.matches(model.getRegex());
			}
		});

		if (cFrames.length == 0)
			throw new CacheConstructionException("No frames matching expression in selected folder");
		
		patternMatch = expressionToStringFormatter(model.getRegex());

		datasetName = (Objects.nonNull(model.getDetectorDataset())) ? model.getDetectorDataset()
				: ssm.getDatasetName();

	}
	
	/**
	 * Convert a regex to a format string by substituting a block of digits.  
	 * @param regex regular expression used to determine the padding 
	 * @return
	 */
	public static String expressionToStringFormatter(String regex) throws RuntimeException {
		
		String slashd = "\\d";
		
		if (!regex.contains(slashd)) throw new RuntimeException("String does not contain any integer substitutions!");
		
		int idx = regex.indexOf(slashd, 2);
		
		String mainString =  regex.substring(2, idx);
		
		String ext=".nxs";
		
		int lastDot = regex.lastIndexOf(".");
		
		if (lastDot > 0) {
			String testExt = regex.substring(regex.lastIndexOf("."));
			if (!testExt.isEmpty()) {
				ext = testExt;
			}
		}
		
		int count = 0;
		boolean charSame = true;
		
		while (charSame) {
			int start = idx + count*2;
			
			if (start+2 >= regex.length()) {
				break;
			}
			
			if (regex.subSequence(start,start+2).equals(slashd)) {
				count++;
			} else {
				break;
			}
		}
		
		return String.format("%s%%0%dd%s", mainString,count,ext);
		
	}

	@Override
	public IDiffractionMetadata getDiffractionMetadata(SliceFromSeriesMetadata ssm) throws DatasetException {
		int frameID = getFrameIDForPosition(ssm);
		return loadMetadata(frameID, ssm);

	}
	
	/**
	 * get the frame ID corresponding to the flattened 1D index position based on the minimised squared 
	 * distance between the current scan point and the calibrated points.
	 *  
	 * @param ssm
	 * @return
	 * @throws DatasetException
	 */
	public int getFrameIDForPosition(SliceFromSeriesMetadata ssm) throws DatasetException {
		return calibrationFrameKeys.getInt(getAbsoluteNeighbourPosition(ssm));
	}

	@Override
	public ReferencePosition2DMetadata getPositionMeta(SliceFromSeriesMetadata ssm) throws DatasetException {

		ReferencePosition2DMetadata pmeta;

		int pos = getAbsoluteNeighbourPosition(ssm);
		pmeta = new ReferencePosition2DMetadata();
		pmeta.setReferencePosition(DatasetFactory.createFromObject(
				new double[] { calibrationX.getElementDoubleAbs(pos), calibrationY.getElementDoubleAbs(pos) }));
		return pmeta;
	}

	private int getAbsoluteNeighbourPosition(SliceFromSeriesMetadata ssm) throws DatasetException {
		
		Dataset xdiff = Maths.subtract(ssm.getMatchingSlice(cachedSourceXPositions),calibrationX.clone());
		Dataset ydiff = Maths.subtract(ssm.getMatchingSlice(cachedSourceYPositions), calibrationY.clone());
		xdiff.ipower(2);
		ydiff.ipower(2);

		xdiff.iadd(ydiff);
		return xdiff.argMin(false); // find minimum on d^2
	}
	
	private IDiffractionMetadata loadMetadata(int frameKey, SliceFromSeriesMetadata ssm) throws DatasetException {
		 
		String pMatch = String.format(patternMatch, frameKey);
		Optional<String> cFile = Arrays.stream(cFrames).filter(s -> s.contains(pMatch)).findFirst();
		if (cFile.isEmpty()) throw new DatasetException(String.format("Unable to identify frame for key: %d ", frameKey));
		String cPath = new File(cFolder, cFile.get()).getAbsolutePath();

		try {
			return NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(cPath, ssm.getParent(),
					datasetName);
		} catch (DatasetException e) {
			throw new DatasetException("Error retrieving metadata from: " + cPath, e);
		}
	}
	
}
