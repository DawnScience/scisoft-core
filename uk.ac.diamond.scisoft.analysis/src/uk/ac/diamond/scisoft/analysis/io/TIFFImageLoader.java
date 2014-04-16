/*-
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReader;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReaderSpi;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReader;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

/**
 * This class loads a TIFF image file
 */
public class TIFFImageLoader extends JavaImageLoader implements IMetaLoader {

	protected Map<String, Serializable> metadata = null;
	private boolean loadData = true;
	
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

		// TODO cope with multiple images (tiff)
		ImageReader reader = null;
		try {
			// test to see if the filename passed will load
			f = new File(fileName);
			ImageInputStream iis = new FileImageInputStream(f);

			try {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
				reader.setInput(iis);
				if (loadData)
					input = reader.read(0);
			} catch (IllegalArgumentException e) { // catch bad number of bits
				logger.warn("Exception using TIFFImageReader for file:" + fileName,e);
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
				reader.setInput(iis);
				if (loadData)
					input = reader.read(0);
			}

			if (loadData && input == null) {
				throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read");
			}

			if (loadMetadata)
				metadata = createMetadata(reader.getImageMetadata(0));

			if (loadData) {
				data = createDataset(input);
				data.setName(DEF_IMAGE_NAME);
			}
		} catch (IOException e) {
			throw new ScanFileHolderException("IOException loading file '" + fileName + "'", e);
		} catch (IllegalArgumentException e) {
			throw new ScanFileHolderException("IllegalArgumentException interpreting file '" + fileName + "'", e);
		} finally {
			if (reader != null)
				reader.dispose();
		}


		if (!loadData || data == null) {
			return null;
		}

		DataHolder output = new DataHolder();
		output.addDataset(DEF_IMAGE_NAME, data, data.getMetadata());
		if (loadMetadata) {
			data.setMetadata(getMetaData(data));
			output.setMetadata(data.getMetadata());
		}
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
		
		try {
			Map<String, Serializable> metadataTable = new HashMap<String, Serializable>();
			String temp = "";
			TIFFDirectory tiffDir;
			try {
				tiffDir = TIFFDirectory.createFromMetadata(imageMetadata);
			} catch (IIOInvalidTreeException e) {
				throw new ScanFileHolderException("Problem creating TIFF directory from header", e);
			}
	
			TIFFField[] tiffField = tiffDir.getTIFFFields();
			int unknownNum = 0;
			boolean found = false;
			for (int i = 0; i < tiffField.length; i++) {
				TIFFField field = tiffField[i];
				metadataTable.put(field.getTag().getName(), field.getValueAsString(0));
			}
			return metadataTable;
		} catch (Throwable ne) {
			return null;
		}
	}

	@Override
	public void loadMetaData(IMonitor mon) throws Exception {
		loadData = false;
		loadFile();
		loadData = true;

	}

	@Override
	public IMetaData getMetaData() {
		return getMetaData(null);
	}

	public IMetaData getMetaData(AbstractDataset data) {
		if (metadata == null) {
			if (data!=null) return data.getMetadata(); // Might be null or might be set in AWTImageUtils.
			return null;
		}

		Metadata md = new Metadata(metadata);
		md.setFilePath(fileName);
		return md;
	}
}
