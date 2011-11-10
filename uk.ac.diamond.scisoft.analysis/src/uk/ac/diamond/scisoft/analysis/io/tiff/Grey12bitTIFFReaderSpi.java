/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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
