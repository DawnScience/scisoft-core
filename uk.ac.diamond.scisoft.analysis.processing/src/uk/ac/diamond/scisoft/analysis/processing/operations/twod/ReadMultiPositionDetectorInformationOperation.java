/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Processing step to read DiffractionMetadata objects from nexus files where the detector position is scanned during the data collection
 *
 */
public class ReadMultiPositionDetectorInformationOperation extends AbstractOperation<ReadMultiPositionDetectorInformationModel, OperationData> {

	private static final Logger logger = LoggerFactory.getLogger(ReadMultiPositionDetectorInformationOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.ReadMultiPositionDetectorInformationOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		input = input.getSliceView();
		try {
			Tree tree = ProcessingUtils.getTree(this, ssm.getFilePath());
			ssm.getParent().getShape();
			int[] pos = getPosition(new SliceND(ssm.getParent().getShape(),ssm.getSliceFromInput()), ssm.getParent().getShape(), ssm.getDataDimensions());

			DetectorProperties[] p = NexusDiffractionCalibrationReader.getDetectors(ssm.getFilePath(), ssm.getParent(), pos);

			NodeLink beam = NexusTreeUtils.findFirstNode(tree.getGroupNode().getGroupNode("entry1").getGroupNode("sample"), NexusConstants.BEAM);
			DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment();
			NexusTreeUtils.parseBeam(beam, dce,pos);


			if (model.isOffsetSample()) {

				String filePath = input.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath();
				ILazyDataset lz = ProcessingUtils.getLazyDataset(this, filePath, model.getDatasetName());
				IDataset val = ssm.getMatchingSlice(lz);
				double d = val.squeeze().getDouble();

				if (model.isAddOffset()) {
					d = p[0].getBeamCentreDistance() + d;
				} else {
					d = p[0].getBeamCentreDistance() - d;
				}

				p[0].setBeamCentreDistance(d);
			}

			logger.debug("Detector properties are: {}", p[0].toString());

			DiffractionMetadata dm = new DiffractionMetadata(ssm.getFilePath(), p[0], dce);
			input.setMetadata(dm);

		} catch (Exception e) {
			throw new OperationException(this,e);
		}

		return new OperationData(input);
	}

	/**
	 * Get an int[] corresponding to the current slices position in the dataset.
	 * <p>
	 * For example, slice [1,2,3,:,:] for dataDimensions [3,4] would return [1,2,3]
	 * 
	 * @param currentSlice
	 * @param shape
	 * @param dataDimensions
	 * @return pos
	 */
	private int[] getPosition(SliceND currentSlice, int[] shape, int[] dataDimensions) {
		
		int[] array = ShapeUtils.getRemainingAxes(shape.length, dataDimensions);

		int[] start = currentSlice.getStart();
		for (int i = 0; i < array.length; i++) {
			array[i] = start[array[i]];
		}

		return array;
	}
}
