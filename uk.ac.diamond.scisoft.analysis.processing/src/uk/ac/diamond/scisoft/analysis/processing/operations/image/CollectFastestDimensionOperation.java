/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DataUtils;

public class CollectFastestDimensionOperation extends AbstractOperationBase<EmptyModel, OperationData>
		implements IExportOperation {

	List<IDataset> list;
	private int fastestDimension = -1;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.CollectFastestDimensionOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);

		if (list == null) {
			list = new ArrayList<IDataset>();
			if (fastestDimension < 0)
				fastestDimension = DataUtils.calculateFastestDimension(ssm.getDataDimensions(),
						ssm.getSubSampledShape());
			if (fastestDimension < 0)
				throw new OperationException(this, "Cannot collect this data, it has no, non-data dimensions!");
		}
		list.add(slice);

		if (isLastSlice(ssm.getSliceInOutput(), ssm.getSubSampledShape(), fastestDimension)) {
			IDataset out = DatasetUtils.concatenate(list.toArray(new IDataset[list.size()]), fastestDimension);
			SliceFromSeriesMetadata outsmm = ssm.clone();
			SliceInformation sliceInfo = outsmm.getSliceInfo();
			sliceInfo.convertSliceDimensionToFull(fastestDimension);
			out.setMetadata(outsmm);

			AxesMetadata ax = slice.getFirstMetadata(AxesMetadata.class);

			try {

				if (ax != null) {
					AxesMetadata output = (AxesMetadata) ax.clone();

					int[] dd = ssm.getDataDimensions().clone();
					Arrays.sort(dd);

					for (int i = 0; i < slice.getRank(); i++) {
						if (Arrays.binarySearch(dd, i) < 0) {
							List<IDataset> axl = new ArrayList<>();
							for (IDataset l : list) {
								AxesMetadata m = l.getFirstMetadata(AxesMetadata.class);
								ILazyDataset[] axes = m.getAxes();
								if (axes[i] != null)
									axl.add(axes[i].getSlice());
							}

							if (axl.size() == list.size()) {
								IDataset axout = DatasetUtils.concatenate(axl.toArray(new IDataset[axl.size()]),
										fastestDimension);
								axout.setName(axl.get(0).getName());
								output.setAxis(i, axout);
							}
						}
					}

					out.setMetadata(output);
				}
			} catch (Exception e) {
				// logger
			}

			list = null;
			return new OperationData(out);
		}

		return null;
	}

	private boolean isLastSlice(Slice[] outputSlice, int[] outputShape, int dim) {
		return (outputSlice[dim].getStart().intValue() == outputShape[dim] - 1);
	}

}
