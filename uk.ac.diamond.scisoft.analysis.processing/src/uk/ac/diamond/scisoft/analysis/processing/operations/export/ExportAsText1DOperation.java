package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataWithHeadingSaver;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadataImpl;

@Atomic
public class ExportAsText1DOperation extends AbstractOperation<ExportAsText1DModel, OperationData> implements IExportOperation {

	private static final String EXPORT = "export";
	private static final String DEFAULT_EXT = "dat";
	private static final String FOLDER_EXT = "_ascii";
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportAsText1DOperation";
	}
	

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		String fileName = getFilePath( model, input, this);
		
		ILazyDataset[] axes = getFirstAxes(input);
		
		ILazyDataset lx = axes != null ? axes[0] : null;
		
		IDataset outds = input.getSlice().clone();
		
		outds.squeeze().setShape(outds.getShape()[0],1);
		
		if (lx != null) {
			IDataset x;
			try {
				x = lx.getSliceView().getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			x.setShape(x.getShape()[0],1);
			int xtype = DTypeUtils.getDType(x);
			int ytype = DTypeUtils.getDType(outds);
			if (xtype != ytype) {
				if (xtype > ytype) {
					outds = DatasetUtils.cast(outds, xtype);
				} else {
					x = DatasetUtils.cast(x, ytype);
				}
			}
			outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
		}
		
		ILazyDataset error = input.getError();
		
		if (error != null) {
			IDataset e;
			try {
				e = error.getSlice();
			} catch (Exception e1) {
				throw new OperationException(this, e1);
			}
			e.setShape(e.getShape()[0],1);
			int etype = DTypeUtils.getDType(e);
			int ytype = DTypeUtils.getDType(outds);
			if (etype != ytype) {
				if (etype > ytype) {
					outds = DatasetUtils.cast(outds, etype);
				} else {
					e = DatasetUtils.cast(e, ytype);
				}
			}
			outds = DatasetUtils.concatenate(new IDataset[]{outds,e}, 1);
		}
		
		ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
		
		DataHolder dh = new DataHolder();
		dh.addDataset("Export", outds);
		try {
			saver.saveFile(dh);
		} catch (ScanFileHolderException e) {
			throw new OperationException(this, "Error saving text file! (Do you have write access?)");
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
	
	private static String getFileNameNoExtension(String fileName) {
		int posExt = fileName.lastIndexOf(".");
		// No File Extension
		return posExt == -1 ? fileName : fileName.substring(0, posExt);
	}
	
	public static String getFilePath(ExportAsText1DModel model, IDataset input, IOperation op) {
		String outputDirectory = null;
		
		if (model.getOutputDirectoryPath() != null && !model.getOutputDirectoryPath().isEmpty()) {
			outputDirectory = model.getOutputDirectoryPath();
		} else {
			OperationMetadata omd = input.getFirstMetadata(OperationMetadata.class);
			if (omd.getOutputFilename() != null) {
				File f = new File(omd.getOutputFilename());
				outputDirectory = f.getParent();
			}
		}
		
		if (outputDirectory == null || outputDirectory.isEmpty()) throw new OperationException(op, "Output directory not set!");
		
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		String filename = EXPORT;
		
		String fn = ssm.getSourceInfo().getFilePath();
		if (fn != null) {
			File f = new File(fn);
			filename = getFileNameNoExtension(f.getName());
		}
		
		if (model.isMakeFolder()) {
			String innerDirName = File.separator + filename + FOLDER_EXT;
			outputDirectory += innerDirName;
			File f = new File(outputDirectory);
			if (!f.exists()) f.mkdir();
		}
		
		String slice ="";
		String ext = DEFAULT_EXT;
		
		if (model.isIncludeSliceName()) {
			slice = Slice.createString(ssm.getSliceFromInput());
		}
		
		int c = ssm.getSliceInfo().getSliceNumber();
		String count = "";
		if (model.getZeroPad() != null && model.getZeroPad() >= 1) {
			count = String.format("%0" + String.valueOf(model.getZeroPad()) + "d", c);
		} else {
			count =String.valueOf(c);
		}
		
		if (model.getExtension() != null) ext = model.getExtension();
		
		
		
		String postfix = "";
		
		if (model.getSuffix() != null) {
			postfix = model.getSuffix();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(outputDirectory);
		sb.append(File.separator);
		sb.append(filename);
		sb.append("_");
		if (!slice.isEmpty()) {
			sb.append("[");
			slice = slice.replace(":", ";");
			sb.append(slice);
			sb.append("]");
			sb.append("_");
		}
		if (!postfix.isEmpty()) {
			sb.append(postfix);
			sb.append("_");
		}
		sb.append(count);
		sb.append(".");
		sb.append(ext);
		
		return sb.toString();
	}

}
