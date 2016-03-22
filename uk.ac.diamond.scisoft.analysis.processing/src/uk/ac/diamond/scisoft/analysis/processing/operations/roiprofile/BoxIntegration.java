/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

@Atomic
public class BoxIntegration extends AbstractIntegrationOperation<BoxIntegrationModel> {

	public enum Direction {
		X,Y,
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.boxIntegration";
	}

	
	@Override
    public String getName() {
		return "Box Integration";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		Dataset mask = null;
		try {
			mask = DatasetUtils.sliceAndConvertLazyDataset(getFirstMask(input));
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		RectangularROI rect = (RectangularROI)getRegion();


		final Dataset[] profile = ROIProfile.box(DatasetUtils.convertToDataset(input), mask, rect);

		Dataset x = profile[0];
		x.setName("Box X Profile "+rect.getName());

		Dataset y = profile[1];
		y.setName("Box Y Profile "+rect.getName());


		AxesMetadata metadata = input.getFirstMetadata(AxesMetadata.class);

		double ang = rect.getAngle();
		//TODO probably better to deal with this in ROIProfile class, but this will do for now.
		if (ang == 0) {

			IDataset local = input.getSliceView();

			if (metadata == null) {
				metadata = new AxesMetadataImpl(2);
				metadata.setAxis(0, DatasetFactory.createRange(input.getShape()[0],Dataset.INT32));
				metadata.setAxis(1, DatasetFactory.createRange(input.getShape()[1],Dataset.INT32));
			}

			if (metadata.getAxis(0) == null || metadata.getAxis(0).length == 0) {
				metadata.setAxis(0, DatasetFactory.createRange(input.getShape()[0],Dataset.INT32));
			}

			if (metadata.getAxis(1) == null || metadata.getAxis(1).length == 0) {
				metadata.setAxis(1, DatasetFactory.createRange(input.getShape()[1],Dataset.INT32));
			}

			local.setMetadata(metadata);

			int[] spt = rect.getIntPoint();
			int[] len = rect.getIntLengths();

			final int xstart  = Math.max(0,  spt[0]);
			final int xend   = Math.min(spt[0] + len[0],  input.getShape()[1]); 
			final int ystart = Math.max(0,  spt[1]);
			final int yend   = Math.min(spt[1] + len[1],  input.getShape()[0]);


			try {
				IDataset slicedData = local.getSlice(new int[]{ystart,   xstart}, 
						new int[]{yend,    xend},
						new int[]{1,1});

				AxesMetadata cutAxis = slicedData.getFirstMetadata(AxesMetadata.class);

				if (model.getDirection() == Direction.X) {
					AxesMetadataImpl xax = new AxesMetadataImpl(1);
					xax.setAxis(0, cutAxis.getAxis(1)[0].getSliceView().squeezeEnds());
					x.addMetadata(xax);
					return new OperationData(x);
				} else {

					AxesMetadataImpl yax = new AxesMetadataImpl(1);
					yax.setAxis(0, cutAxis.getAxis(0)[0].getSliceView().squeezeEnds());
					y.addMetadata(yax);
					return new OperationData(y);

				}

			} catch (Exception ne) {
				// We cannot process the profiles for a region totally outside the image!
				return new OperationData(model.getDirection() == Direction.X ? x : y);
			}




		}

		return new OperationData(model.getDirection() == Direction.X ? x : y);
	}
}
