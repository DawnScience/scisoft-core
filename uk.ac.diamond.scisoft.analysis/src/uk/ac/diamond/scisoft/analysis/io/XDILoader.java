/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class XDILoader extends AbstractFileLoader {

	protected Map<String, Serializable> metadataMap;
	
	public XDILoader() {
		metadataMap = new HashMap<>();
	}
	
	public XDILoader(final String filename) {
		this();
		setFile(filename);
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor)null);
	}
	
	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
		DataHolder result = new DataHolder();
		
		try {
			XDIFile xdifile = XDI.readfile(fileName);
			
			// process metadata
			for (int i = 0 ; i < xdifile.nmetadata ; i++) {
				String key = xdifile.meta_families[i] + "." + xdifile.meta_keywords[i];
				String val = xdifile.meta_values[i];
				metadataMap.put(key, val);
			}
			if (!xdifile.element.isEmpty())
				metadataMap.put("element", xdifile.element);
			if (!xdifile.edge.isEmpty())
				metadataMap.put("edge", xdifile.edge);
			if (xdifile.dspacing > 0)
				metadataMap.put("dspacing", xdifile.dspacing);
			
			// process data
			// X-data
			Dataset xData = DatasetFactory.createFromObject(xdifile.array[0]);
			xData.setName(xdifile.array_labels[0]);
			result.addDataset(xdifile.array_labels[0], xData);
			metadata = new ExtendedMetadata(new File(fileName));
			metadata.addDataInfo(xdifile.array_labels[0], xData.getShape());
			for (int i = 1 ; i < xdifile.narrays ; i++) {
				Dataset yData = DatasetFactory.createFromObject(xdifile.array[i]);
				try {
					AxesMetadata amd = MetadataFactory.createMetadata(AxesMetadata.class, 1);
					amd.setAxis(0, xData);
					yData.addMetadata(amd);
				} catch (MetadataException e) {
					
				}
				yData.setName(xdifile.array_labels[i]);
				metadata.addDataInfo(xdifile.array_labels[i], yData.getShape()); 
				result.addDataset(xdifile.array_labels[i], yData);
			}
			metadata.setMetadata(metadataMap);
			
			result.setMetadata(metadata);
			
		} catch (Exception e) {
			throw new ScanFileHolderException("XDILoader: cannot load from file" + fileName, e);
		}
		
		return result;
	}

	@Override
	protected void clearMetadata() {
		metadataMap.clear();
	}

}
