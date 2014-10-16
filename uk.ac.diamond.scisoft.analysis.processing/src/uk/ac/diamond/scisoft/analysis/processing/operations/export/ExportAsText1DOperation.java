package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import java.io.File;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataWithHeadingSaver;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class ExportAsText1DOperation extends AbstractOperation<ExportAsText1DModel, OperationData> implements IExportOperation {

	private int counter = 0;
	private ILazyDataset currentLazy = null;
	private static final String EXPORT = "export";
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportAsText1DOperation";
	}
	

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		String filename = EXPORT;
		
		//TODO include way to export with filename/path
//		try {
//			List<OriginMetadata> metadata = input.getMetadata(OriginMetadata.class);
//			if (metadata != null && !metadata.isEmpty()) {
//				OriginMetadata om = metadata.get(0);
//				if (currentLazy != om.getParent()) {
//					counter=0;
//					currentLazy = om.getParent();
//					IMetadata metadata2 = currentLazy.getMetadata();
//					
//				}
//			}
//		} catch (Exception e1) {
//			throw new OperationException(this,e1);
//		}
		
		ILazyDataset[] axes = getFirstAxes(input);
		
		ILazyDataset lx = axes[0];
		
		IDataset outds = input.getSlice().clone();
		
		outds.squeeze().setShape(outds.getShape()[0],1);
		
		if (lx != null) {
			IDataset x = lx.getSliceView().getSlice().squeeze();
			x.setShape(x.getShape()[0],1);
			outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
		}
		
		String fileName = model.getOutputDirectoryPath() + File.separator + filename + String.valueOf(counter) + ".dat";
		counter++;
		
		ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
		
		DataHolder dh = new DataHolder();
		dh.addDataset("Export", outds);
		try {
			saver.saveFile(dh);
		} catch (ScanFileHolderException e) {
			e.printStackTrace();
		}
		
		return new OperationData(input);

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
