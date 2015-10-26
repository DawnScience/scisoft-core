/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

/**
 * Integrate a 2D image along one of its axes.
 * <p>
 * Sum an two dimensional image along a selected axis. Alternatively, calculate the average along that axis.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-10-14
 */
public class ImageIntegration extends AbstractIntegrationOperation<ImageIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.imageIntegration";
	}

	protected OperationData process(IDataset input, IMonitor imon) throws OperationException {
		
		Dataset nannyInput = DatasetUtils.convertToDataset(input);
		
		// Replace masked values by NaN, based on the Dataset type
		Object nannyValue = Double.NaN;
		if (input.getFirstMetadata(MaskMetadata.class) != null) {
			if (nannyInput.getClass() == DoubleDataset.class)
				nannyValue = Double.NaN;
			else if (nannyInput.getClass() == FloatDataset.class)
				nannyValue = Float.NaN;
			else
				nannyValue = 0;
			
			// Loop over the mask and the data and replace masked values by the
			// NaN value chosen above 
			Dataset mask = DatasetUtils.convertToDataset(input.getFirstMetadata(MaskMetadata.class).getMask());
			for (IndexIterator iter = nannyInput.getIterator(); iter.hasNext(); ) {
				if (!(boolean) mask.getElementBooleanAbs(iter.index))
					nannyInput.setObjectAbs(iter.index, nannyValue);
			}
		}

		// Sum or mean along the axis, according to the Model selections
		int axis = (model.getDirection() == Direction.X) ? 0 : 1;
		Dataset output = (model.isDoAverage()) ? ((Dataset) nannyInput).mean(true, axis) : ((Dataset) nannyInput).sum(true, axis);
		
		// copy axes to the new data
		ILazyDataset[] oldAxes = AbstractOperation.getFirstAxes(input);
		AxesMetadata newAxes = new AxesMetadataImpl(1);
		if (oldAxes[1-axis] != null) {
			newAxes.setAxis(0, oldAxes[1-axis].squeezeEnds());
			output.setMetadata(newAxes);
		}
		
		// Set some kind of name
		output.setName(input.getName() + ((model.getDirection()==Direction.X) ? " X" : "Y") + ((model.isDoAverage()) ? " average" : " sum"));
		
		return new OperationData(output);
	
	}

}
