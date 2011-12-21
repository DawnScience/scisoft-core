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

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReader;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReaderSpi;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReader;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

/**
 * This class loads a TIFF image file
 */
public class TIFFImageLoader extends JavaImageLoader {

	
	public TIFFImageLoader() {
		this(null, false);
	}
	
	/**
	 * @param FileName
	 */
	public TIFFImageLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public TIFFImageLoader(String FileName, boolean convertToGrey) {
		super(FileName, "tiff", convertToGrey);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public TIFFImageLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "tiff", convertToGrey, keepBitWidth);
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		AbstractDataset data = null;
		File f = null;
		BufferedImage input = null;

		// Check for file
		f = new File(fileName);
		if (!f.exists()) {
			logger.warn("File, {}, did not exist. Now trying to replace suffix", fileName);
			f = findCorrectSuffix();
		}

		Map<String, Serializable> metadata = null;

		// TODO cope with multiple images (tiff)
		try {
			// test to see if the filename passed will load
			f = new File(fileName);
			ImageInputStream iis = new FileImageInputStream(f);

			ImageReader reader;
			try {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
				reader.setInput(iis);
				input = reader.read(0);
			} catch (IllegalArgumentException e) { // catch bad number of bits
//			} catch (Exception e) {
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
				reader.setInput(iis);
				input = reader.read(0);
			}

			if (input == null) {
				throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read");
			}

			if (loadMetadata)
				metadata = createMetadata(reader.getImageMetadata(0));
		} catch (IOException e) {
			throw new ScanFileHolderException("IOException loading file '" + fileName + "'", e);
		} catch (IllegalArgumentException e) {
			throw new ScanFileHolderException("IllegalArgumentException interpreting file '" + fileName + "'", e);
		}

		data = createDataset(input);

		DataHolder output = new DataHolder();
		output.addDataset(fileName, data);
		if (metadata != null)
			data.setMetadataMap(metadata);
		return output;
	}

	/**
	 * This can be overridden to add metadata
	 * @param imageMetadata
	 * @return GDA metadata map
	 * @throws ScanFileHolderException
	 */
	@SuppressWarnings("unused")
	protected Map<String, Serializable> createMetadata(IIOMetadata imageMetadata) throws ScanFileHolderException {
		return null;
	}
}
