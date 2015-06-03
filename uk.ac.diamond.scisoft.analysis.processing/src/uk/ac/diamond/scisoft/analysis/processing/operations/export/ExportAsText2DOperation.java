/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataWithHeadingSaver;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class ExportAsText2DOperation extends AbstractOperation<ExportAsText1DModel, OperationData> implements IExportOperation {

	private static final String EXPORT = "export";
	private static final String DEFAULT_EXT = "dat";
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportAsText2DOperation";
	}
	

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (model.getOutputDirectoryPath() == null || model.getOutputDirectoryPath().isEmpty()) throw new OperationException(this, "Output directory not set!");
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		String filename = EXPORT;
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
		
		String fn = ssm.getSourceInfo().getFilePath();
		if (fn != null) {
			File f = new File(fn);
			filename = getFileNameNoExtension(f.getName());
		}
		
		String postfix = "";
		
		if (model.getSuffix() != null) {
			postfix = model.getSuffix();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(model.getOutputDirectoryPath());
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
		
		String fileName = sb.toString();
		
		ILazyDataset[] axes = getFirstAxes(input);
		ILazyDataset lx = null;
		ILazyDataset ly = null;
		
		if (axes != null) lx = axes[1];
		if (axes != null) ly = axes[0];
		
		IDataset outds = input.getSlice().clone();
		outds.clearMetadata(null);
		outds = ((Dataset)outds).transpose();
		
//		outds.squeeze().setShape(outds.getShape()[0],1);
		
		if (lx != null) {
			IDataset x = lx.getSliceView().getSlice().squeeze();
			x.setShape(x.getShape()[0],1);
			int xtype = AbstractDataset.getDType(x);
			int ytype = AbstractDataset.getDType(outds);
			if (xtype != ytype) {
				if (xtype > ytype) {
					outds = DatasetUtils.cast(outds, xtype);
				} else {
					x = DatasetUtils.cast(x, ytype);
				}
			}
			outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
		}
		
		
		ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
		
//		if (ly != null) {
//			IDataset d = ly.getSlice();
//			String name = d.getName() == null ? "" : d.getName();
//			List<String> head = new ArrayList<String>();
//			if (lx != null) head.add(lx.getName());
//			for (int i = 0; i < ly.getSize(); i++) {
//				head.add(name.replace(" ", "_") + d.getObject(i).toString());
//			}
//			saver.setHeadings(head);
//		}
		
		
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
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	private String getFileNameNoExtension(String fileName) {
		int posExt = fileName.lastIndexOf(".");
		// No File Extension
		return posExt == -1 ? fileName : fileName.substring(0, posExt);
	}
	
}
