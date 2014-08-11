package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;


public class AzimuthalPixelIntegrationOperation extends AbstractPixelIntegrationOperation {

	AbstractPixelIntegration integrator;
	IDiffractionMetadata metadata;
	AzimuthalPixelIntegrationModel model;

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected void setAxes(IDataset data, ILazyDataset[] axes, int[] dataDims, List<Dataset> out) {

		AxesMetadataImpl amd = new AxesMetadataImpl(axes.length-1);

		boolean first = true;
		for (int i = 0; i < axes.length; i++) {
			boolean contained = false;
			for (int j : dataDims) {
				if (i == j){
					contained = true;
					if (first) {
						amd.setAxis(i, new ILazyDataset[]{out.get(0)});
						first = false;
					}
					break;
				}

			}
			if (!contained) {
				amd.setAxis(i, new ILazyDataset[] {axes[i]});
			}
		}

	}

	@Override
	protected AbstractPixelIntegration createIntegrator(
			PixelIntegrationModel model, IDiffractionMetadata md) {
		
		AbstractPixelIntegration integ = null;
		
		if (model.isPixelSplitting()) {
			integ = new PixelSplittingIntegration(md, model.getNumberOfBins());
		} else {
			integ = new NonPixelSplittingIntegration(md, model.getNumberOfBins());
		}
		
		integ.setAxisType(((AzimuthalPixelIntegrationModel)model).getAxisType());
		
		if (model.getRadialRange() == null) integ.setRadialRange(null);
		else integ.setRadialRange(model.getRadialRange().clone());
		
		if (model.getAzimuthalRange() == null) integ.setAzimuthalRange(null);
		else integ.setAzimuthalRange(model.getAzimuthalRange().clone());
		
		
		((AbstractPixelIntegration1D)integ).setAzimuthalIntegration(true);
		
		return integ;
	}

}
