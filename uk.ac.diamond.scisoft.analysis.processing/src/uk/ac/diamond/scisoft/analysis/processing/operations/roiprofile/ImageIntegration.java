/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

/**
 * Integrate a 2D image along one of its axes.
 * <p>
 * Sum an two dimensional image along a selected axis. Alternatively, calculate the average along that axis.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-10-14
 */
@Atomic
public class ImageIntegration extends AbstractOperation<ImageIntegrationModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.imageIntegration";
	}

	protected OperationData process(IDataset input, IMonitor imon) throws OperationException {
		
		if (input.getRank() == 1) {
			//in case someone has squeezed a [1,X] image
			return new OperationData(input);
		}
		
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
		Dataset output = (model.isDoAverage()) ? nannyInput.mean(axis, true) : nannyInput.sum(axis, true);
		Dataset outputErrors = DatasetFactory.zeros(nannyInput);
		
		if (model.isDoAverage()) {
			outputErrors = nannyInput.getErrors().rootMeanSquare(axis, true);
		} else {
			outputErrors = Maths.multiply(nannyInput.getErrors().rootMeanSquare(axis, true), nannyInput.getShapeRef()[axis]);
		}
		output.setErrors(outputErrors);
				
		// copy axes to the new data
		ILazyDataset[] oldAxes = AbstractOperation.getFirstAxes(input);
		AxesMetadata newAxes;
		try {
			newAxes = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		if (oldAxes != null && oldAxes[1-axis] != null) {
			newAxes.setAxis(0, oldAxes[1-axis].squeezeEnds());
			output.setMetadata(newAxes);
		}
		
		// Set some kind of name
		output.setName(input.getName() + ((model.getDirection()==Direction.X) ? " X" : "Y") + ((model.isDoAverage()) ? " average" : " sum"));
		
		return new OperationData(output);
	
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
