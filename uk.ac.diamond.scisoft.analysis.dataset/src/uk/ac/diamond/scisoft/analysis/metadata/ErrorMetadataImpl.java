/*-
 * Copyright 2014 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Implementation of error class
 */
public class ErrorMetadataImpl implements ErrorMetadata, Serializable {

	@Sliceable
	@Reshapeable
	private ILazyDataset error = null; // holds linear errors

	@Sliceable
	@Reshapeable
	transient private ILazyDataset sqError = null; // holds squared errors

	public ErrorMetadataImpl() {
	}

	public ErrorMetadataImpl(ErrorMetadataImpl error) {
		this.error = error.error == null ? null : error.error.getSliceView();
		this.sqError = error.sqError == null ? null : error.sqError.getSliceView();
	}

	@Override
	public MetadataType clone() {
		return new ErrorMetadataImpl(this);
	}

	@Override
	public ILazyDataset getError() {
		return error;
	}

	public void setError(ILazyDataset error) {
		this.error = error;
		this.sqError = null;
	}

	public Dataset getSquaredError() {
		if (sqError == null)
			sqError = error instanceof IDataset ? Maths.square(error) : null;
		return (Dataset) sqError;
	}

	public void setSquaredError(IDataset sqErrors) {
		sqError = DatasetUtils.convertToDataset(sqErrors);
		error = Maths.sqrt(sqErrors);
	}
}
