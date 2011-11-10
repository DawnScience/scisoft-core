/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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
