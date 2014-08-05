package uk.ac.diamond.scisoft.analysis.processing.metadata;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.Sliceable;

public class AxesMetadataImpl implements AxesMetadata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Sliceable
	ILazyDataset[] axes;
	
	public AxesMetadataImpl(ILazyDataset[] axes) {
		this.axes = axes;
	}
	
	@Override
	public ILazyDataset[] getAxes() {
		return axes;
	}

	@Override
	public ILazyDataset[] getAxis(int axisDim) {
		return axes;
	}

	@Override
	public AxesMetadata clone() {
		return new AxesMetadataImpl(axes);
	}
}
