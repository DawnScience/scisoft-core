/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.tiff;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageReader;

import it.tidalwave.imageio.io.RAWImageInputStream;
import it.tidalwave.imageio.raw.RAWImageReaderSpiSupport;
import it.tidalwave.imageio.tiff.IFD;
import it.tidalwave.imageio.tiff.TIFFImageReaderSupport;

public class Grey12bitTIFFReaderSpi extends RAWImageReaderSpiSupport {

	public Grey12bitTIFFReaderSpi() {
        super("TIFF", "tif", "image/x-dls-tif", Grey12bitTIFFReader.class);
	}

	@Override
	protected boolean canDecodeInput(RAWImageInputStream iis) throws IOException {
        iis.seek(0);
        long ifdOffset = TIFFImageReaderSupport.processHeader(iis, null);
        IFD primaryIFD = new IFD();
        primaryIFD.load(iis, ifdOffset);
        return true;
	}

	@Override
	public ImageReader createReaderInstance(Object extension) throws IOException {
		return new Grey12bitTIFFReader(this);
	}

	@Override
	public String getDescription(Locale arg0) {
		return "DLS TIFF reader";
	}

}
