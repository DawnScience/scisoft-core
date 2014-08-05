package uk.ac.diamond.scisoft.analysis.processing.metadata;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.Sliceable;

public class MaskMetadataImpl implements MaskMetadata {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Sliceable
	ILazyDataset mask;
	
	public MaskMetadataImpl(ILazyDataset mask) {
		this.mask = mask;
	}

	@Override
	public ILazyDataset getMask() {
		return mask;
	}
	
	@Override
	public MaskMetadata clone() {
		return new MaskMetadataImpl(mask);
	}

}
