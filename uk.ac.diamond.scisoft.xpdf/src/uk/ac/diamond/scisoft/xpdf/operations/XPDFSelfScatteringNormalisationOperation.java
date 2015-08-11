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

public class XPDFSelfScatteringNormalisationOperation extends
		AbstractOperation<EmptyModel, OperationData> {

	protected OperationData process(IDataset absCor, IMonitor monitor) throws OperationException {
		
		// TODO: read from internal metadata, not from external files
		String xyFilePath = "/home/rkl37156/ceria_dean_data/";
		Dataset selfScattering = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"self_scattering.xy", "Column_2").getSlice());
		Dataset fSquaredOfX = DatasetUtils.convertToDataset(ProcessingUtils.getLazyDataset(this, xyFilePath+"fsquaredofx.xy", "Column_2").getSlice());
		
		Dataset soq = Maths.divide(Maths.subtract(absCor, selfScattering), fSquaredOfX);
		
		copyMetadata(absCor, soq);
				
		return new OperationData(soq);
	}
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFSelfScatteringNormalisationOperation";
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
