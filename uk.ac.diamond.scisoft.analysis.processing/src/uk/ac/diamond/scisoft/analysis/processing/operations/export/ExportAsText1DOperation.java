package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
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

	int counter = 0;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportAsText1DOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		int[] dd = getOriginalDataDimensions(slice);
		ILazyDataset[] axes = getFirstAxes(slice);
		
		ILazyDataset lx = axes[dd[0]];
		
		IDataset outds = DatasetFactory.createFromObject(slice);
		
		outds.squeeze().setShape(outds.getShape()[0],1);
		
		if (lx != null) {
			IDataset x = lx.getSlice().squeeze();
			x.setShape(x.getShape()[0],1);
			outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
		}
		
		String fileName = model.getOutputDirectoryPath() + File.separator + "export" + String.valueOf(counter) + ".dat";
		counter++;
		
		ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
		
		DataHolder dh = new DataHolder();
		dh.addDataset("Export", outds);
		try {
			saver.saveFile(dh);
		} catch (ScanFileHolderException e) {
			e.printStackTrace();
		}
		
		return new OperationData(slice);
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
