/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.image.LocateInRowsModel.FeatureType;

/**
 * Locate where a given feature occurs on each row of an image
 */
@Atomic
public class LocateInRowsOperation extends AbstractOperation<LocateInRowsModel, OperationData> implements PropertyChangeListener {

	private static final Logger logger = LoggerFactory.getLogger(LocateInRowsOperation.class);

	private OperationLog log = new OperationLog();

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.image.LocateInRowsOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public void setModel(LocateInRowsModel model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		log.clear();
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		ILazyDataset[] firstAxes = getFirstAxes(input);
		Dataset in = DatasetUtils.convertToDataset(input);
		FeatureType type = model.getFeatureType();

		DoubleDataset position = DatasetFactory.zeros(in.getShapeRef()[0]);
		position.fill(Double.NaN);
		position.setName(type.toString());

		String logText;
		switch (type) {
		case CROSSING:
			if (Double.isNaN(model.getCrossing())) {
				throw new OperationException(this, "Crossing value must be defined");
			}
		case MEAN:
			logText = model.isUseFirst() ? "first " : "last ";
			break;
		case MAXIMUM:
		case MINIMUM:
		default:
			logText = "";
			break;
		}
		log.append("Locating %s", logText, type.getName());

		int count = 0;
		SliceND s = new SliceND(in.getShapeRef());
		SliceViewIterator it = new SliceViewIterator(input, s, 1);
		while (it.hasNext()) {
			try {
				
				Dataset next = DatasetUtils.convertToDataset(it.next().getSlice()).squeeze();
				ILazyDataset[] axes = getFirstAxes(next);
				Dataset ax = axes == null || axes[0] == null ? DatasetFactory.createRange(next.getSize()) :
						DatasetUtils.convertToDataset(axes[0].getSlice());

				double location;
				switch (type) {
				case CROSSING:
					location = locateCrossing(next, ax, model.isUseFirst(), model.getCrossing());
					break;
				case MEAN:
					location = locateCrossing(next, ax, model.isUseFirst(), ((Number) next.mean()).doubleValue());
					break;
				case MAXIMUM:
					location = findExtremun(next, ax, true);
					break;
				case MINIMUM:
				default:
					location = findExtremun(next, ax, false);
					break;
				}
				if (Double.isNaN(location)) {
					log.appendFailure("%d: locate failed", count);
				} else {
					log.appendSuccess("%d: %g", count, location);
				}
				position.setItem(location, count);
			} catch (Exception e) {
				log.appendFailure("Could not fit %d-th row", count, e);
			}
			count++;
		}

		if (firstAxes != null && firstAxes[0] != null) {
			AxesMetadata m;
			try {
				m = MetadataFactory.createMetadata(AxesMetadata.class,1);
				m.setAxis(0, firstAxes[0].getSlice().squeeze());
				position.addMetadata(m);
			} catch (Exception e) {
				logger.error("Metadata building failed");
			}
			
		}

		OperationData od = new OperationData();
		od.setData(position);
		od.setLog(log);
		return od;
	}

	private double findExtremun(Dataset y, Dataset ax, boolean max) {
		int pos = max ? y.argMax() : y.argMin();
		Slice s = new Slice(Math.max(0, pos-2), Math.min(y.getSize(), pos+2));
		Dataset sx = ax.getSlice(s);
		List<Double> cx = DatasetUtils.crossings(sx, Maths.derivative(sx, y.getSlice(s), 1), 0);
		return cx.size() == 0 ? pos : cx.get(0);
	}

	private double locateCrossing(Dataset y, Dataset ax, boolean useFirst, double crossing) {
		List<Double> cx = DatasetUtils.crossings(ax, y, crossing);
		int n = cx.size();
		if (n == 0) {
			return Double.NaN;
		}
		int i;
		if (n == 1) {
			i = 0;
		} else {
			i = useFirst ? 0 : n - 1;
		}
		return cx.get(i);
	}
}
