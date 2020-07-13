/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GIFLoader extends JavaImageLoader {

	private static final Logger logger = LoggerFactory.getLogger(GIFLoader.class);

	public GIFLoader() {
		this(null);
	}

	public GIFLoader(String FileName) {
		super(FileName, "gif");
	}
	

	public GIFLoader(String FileName, boolean convertToGrey) {
		super(FileName, "gif", convertToGrey);
	}
	
	public GIFLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "gif", convertToGrey, keepBitWidth);
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		DataHolder dh = super.loadFile();
		
		//dont fail if cant build stack
		try {
			if (dh.size() > 1) {

				int[] shape = dh.getDataset(0).getShape();
				boolean shapesEqual = true;

				for (int i = 1; i < dh.size(); i++) {
					ILazyDataset dataset = dh.getLazyDataset(i);
					if (!Arrays.equals(shape, dataset.getShape())) {
						shapesEqual = false;
					}
				}

				if (shapesEqual) {

					List<ILazyDataset> list = dh.getList();
					AggregateDataset ad = new AggregateDataset(true, list.toArray(new ILazyDataset[dh.size()]));
					ad.setName(STACK_NAME);
					dh.addDataset(STACK_NAME, ad);
					dh.getMetadata().addDataInfo(STACK_NAME, ad.getShape());

				}
			}
		} catch (Exception e) {
			logger.warn("Could not stack images",e);
		}
		
		return dh;
	}

}
