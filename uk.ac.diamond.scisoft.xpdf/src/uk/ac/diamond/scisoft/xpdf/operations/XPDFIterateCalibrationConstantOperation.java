package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

public class XPDFIterateCalibrationConstantOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		String xyFilePath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceria_dean_data/";
		// Load the reference background subtracted traces from the designated xy file
		Dataset subBakRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"SUBBAK.xy", "Column_2"));
		Dataset subBakCapRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"SUBBAK_cap.xy", "Column_2"));
		Dataset absCorRef = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"ABSCOR.xy", "Column_2"));
		
		Dataset subBak = DatasetUtils.convertToDataset(input);
		Dataset subBakCap = null;
		
		try {
			if (input.getMetadata(XPDFMetadata.class) != null &&
				!input.getMetadata(XPDFMetadata.class).isEmpty() &&
				input.getMetadata(XPDFMetadata.class).get(0) != null &&
				input.getMetadata(XPDFMetadata.class).get(0).getContainers() != null &&
				!input.getMetadata(XPDFMetadata.class).get(0).getContainers().isEmpty() &&
				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0) != null &&
				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace() != null &&
				input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace().isBackgroundSubtracted()) 
				subBakCap = input.getMetadata(XPDFMetadata.class).get(0).getContainers().get(0).getTrace().getTrace();
		} catch (Exception e) {
			;
		}

		copyMetadata(input, absCorRef);
		
		double delta = 1e-6;
		double sampleDelta = (double) Maths.divide(Maths.square(Maths.subtract(subBakRef, subBak)), subBakRef).sum(true) / subBakRef.getSize();
		boolean sampleMatch = sampleDelta < delta;
		double containerDelta = (double) Maths.divide(Maths.square(Maths.subtract(subBakCapRef, subBakCap)), subBakCapRef).sum(true) / subBakRef.getSize();
		boolean containerMatch = containerDelta < delta;
		
		return new OperationData((sampleMatch && containerMatch) ? absCorRef : input);
	}
	
	
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFIterateCalibrationConstantOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
