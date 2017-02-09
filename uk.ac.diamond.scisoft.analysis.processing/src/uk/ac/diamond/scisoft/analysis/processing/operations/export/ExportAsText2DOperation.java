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

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataWithHeadingSaver;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

@Atomic
public class ExportAsText2DOperation extends AbstractOperation<ExportAsText2DModel, OperationData> implements IExportOperation {

	private static final String EXPORT = "export";
	private static final String DEFAULT_EXT = "dat";
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportAsText2DOperation";
	}
	

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] axes = getFirstAxes(input);
		ILazyDataset lx = null;
//		ILazyDataset ly = null;
		
		if (axes != null) lx = axes[1];
//		if (axes != null) ly = axes[0];
		
		Dataset outds = DatasetUtils.convertToDataset(input.getSlice()).clone();
		outds.clearMetadata(null);
		outds = outds.getTransposedView();
		
//		outds.squeeze().setShape(outds.getShape()[0],1);
		IDataset x = null;
		if (lx != null) {
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
		}
		
		
		if (model.isSplitColumns()) {
			
			int count = 0;
			SliceViewIterator it = new SliceViewIterator(input, null, new int[]{1});
			while (it.hasNext()) {
				String fileName = ExportAsText1DOperation.getFilePath( model, input, this, count++);
				ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
				try {
					outds = DatasetUtils.convertToDataset(it.next().getSlice()).transpose();
					outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
					DataHolder dh = new DataHolder();
					dh.addDataset("Export", outds);
					saver.saveFile(dh);
				} catch (Exception e) {
					throw new OperationException(this, "Error saving text file! (Do you have write access?)");
				}
			}
			
		} else {
			String fileName = ExportAsText1DOperation.getFilePath( model, input, this, -1);
			outds = DatasetUtils.concatenate(new IDataset[]{x,outds}, 1);
			ASCIIDataWithHeadingSaver saver = new ASCIIDataWithHeadingSaver(fileName);
			
			DataHolder dh = new DataHolder();
			dh.addDataset("Export", outds);
			try {
				saver.saveFile(dh);
			} catch (ScanFileHolderException e) {
				throw new OperationException(this, "Error saving text file! (Do you have write access?)");
			}
			
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
}
