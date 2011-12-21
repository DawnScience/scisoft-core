/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
