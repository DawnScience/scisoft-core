package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration1D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.AbstractPixelIntegration2D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.NonPixelSplittingIntegration2D;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelSplittingIntegration2D;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class PixelIntegrationOperation extends AbstractOperation {

	AbstractPixelIntegration integrator;
	PowderIntegrationModel model;
	IDiffractionMetadata metadata;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.PixelIntegrationOperation";
	}


	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(slice);
		
		if (metadata == null || !metadata.equals(md)) {
			metadata = md;
			integrator = null;
		}
		
		if (integrator == null) integrator = createIntegrator(model, metadata);
		
		ILazyDataset mask = getFirstMask(slice);
		if (mask != null) {
			IDataset m = mask.getSlice().squeeze();
			integrator.setMask((AbstractDataset)m);
		}
		
		ILazyDataset[] axes = getFirstAxes(slice);
		
		final List<AbstractDataset> out = integrator.integrate(slice);
		
		AbstractDataset data = out.remove(1);
		
		AxesMetadataImpl amd = new AxesMetadataImpl(out.size());
		
		for (int i = 0 ; i < out.size();  i ++) amd.addAxis(out.get(i), i);
		
		data.addMetadata(amd);
		
		return new OperationData(data);
	}
	
	private AbstractPixelIntegration createIntegrator(PowderIntegrationModel model, IDiffractionMetadata md) {
		
		switch (model.getIntegrationMode()) {
		case NONSPLITTING:
			integrator = new NonPixelSplittingIntegration(md, model.getNumberOfPrimaryBins());
			break;
		case SPLITTING:
			integrator = new PixelSplittingIntegration(md, model.getNumberOfPrimaryBins());
			break;
		case SPLITTING2D:
			integrator = new PixelSplittingIntegration2D(md, model.getNumberOfPrimaryBins(),model.getNumberOfSecondaryBins());
			break;
		case NONSPLITTING2D:
			integrator = new NonPixelSplittingIntegration2D(md, model.getNumberOfPrimaryBins(),model.getNumberOfSecondaryBins());
			break;
		}
		
		integrator.setAxisType(model.getAxisType());
		
		if (model.getRadialRange() == null) integrator.setRadialRange(null);
		else integrator.setRadialRange(model.getRadialRange().clone());
		
		if (model.getAzimuthalRange() == null) integrator.setAzimuthalRange(null);
		else integrator.setAzimuthalRange(model.getAzimuthalRange().clone());
		
		integrator.setNumberOfBins(model.getNumberOfPrimaryBins());
		
		if (integrator instanceof AbstractPixelIntegration2D) {
			((AbstractPixelIntegration2D)integrator).setNumberOfAzimuthalBins(model.getNumberOfSecondaryBins());
		}
		
		if (integrator instanceof AbstractPixelIntegration1D) {
			((AbstractPixelIntegration1D)integrator).setAzimuthalIntegration(model.isAzimuthal());
		}
		
		return integrator;
	}

	@Override
	public void setModel(IOperationModel parameters) throws Exception {
		if (!(parameters instanceof PowderIntegrationModel)) {
			throw new IllegalArgumentException("Incorrect model");
		}
		
		model = (PowderIntegrationModel) parameters;

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
